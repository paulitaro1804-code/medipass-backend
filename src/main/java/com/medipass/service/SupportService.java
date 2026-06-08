package com.medipass.service;

import com.medipass.dto.support.*;
import com.medipass.entity.Notification;
import com.medipass.entity.Pqrs;
import com.medipass.entity.User;
import com.medipass.enums.Priority;
import com.medipass.enums.PqrsStatus;
import com.medipass.exception.ResourceNotFoundException;
import com.medipass.repository.NotificationRepository;
import com.medipass.repository.PqrsRepository;
import com.medipass.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupportService {

    private final PqrsRepository pqrsRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final AuditService auditService;

    public SupportService(PqrsRepository pqrsRepository, UserRepository userRepository,
                          NotificationRepository notificationRepository, AuditService auditService) {
        this.pqrsRepository = pqrsRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.auditService = auditService;
    }

    public SupportStatsDto getStats() {
        long open = pqrsRepository.countByEstado(PqrsStatus.NUEVO)
                + pqrsRepository.countByEstado(PqrsStatus.EN_PROCESO);
        long inProgress = pqrsRepository.countByEstado(PqrsStatus.EN_PROCESO);
        long highPriority = pqrsRepository.countByPrioridad(Priority.ALTA);

        LocalDate today = LocalDate.now();
        long resolvedToday = pqrsRepository.findByEstado(PqrsStatus.RESUELTO).stream()
                .filter(p -> p.getFechaResolucion() != null
                        && p.getFechaResolucion().toLocalDate().equals(today))
                .count();
        long newToday = pqrsRepository.findByEstado(PqrsStatus.NUEVO).stream()
                .filter(p -> p.getFechaCreacion() != null
                        && p.getFechaCreacion().toLocalDate().equals(today))
                .count();

        return SupportStatsDto.builder()
                .openTickets(open)
                .newToday(newToday)
                .inProgress(inProgress)
                .resolvedToday(resolvedToday)
                .highPriority(highPriority)
                .build();
    }

    public List<TicketDto> getOpenTickets() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Pqrs> open = new ArrayList<>();
        open.addAll(pqrsRepository.findByEstado(PqrsStatus.NUEVO));
        open.addAll(pqrsRepository.findByEstado(PqrsStatus.EN_PROCESO));

        return open.stream()
                .sorted((a, b) -> {
                    if (a.getFechaCreacion() == null) return 1;
                    if (b.getFechaCreacion() == null) return -1;
                    return b.getFechaCreacion().compareTo(a.getFechaCreacion());
                })
                .map(p -> TicketDto.builder()
                        .id(p.getId())
                        .patient(p.getPacienteNombre() != null ? p.getPacienteNombre() : "Paciente")
                        .subject(p.getAsunto())
                        .description(p.getDescripcion())
                        .type(p.getTipo() != null ? p.getTipo() : "Solicitud")
                        .priority(p.getPrioridad() != null ? p.getPrioridad().name().toLowerCase() : "media")
                        .created(p.getFechaCreacion() != null ? p.getFechaCreacion().format(fmt) : "—")
                        .status(p.getEstado() == PqrsStatus.NUEVO ? "new" : "in-progress")
                        .build())
                .toList();
    }

    public List<ResolvedTicketDto> getResolvedTickets() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return pqrsRepository.findByEstado(PqrsStatus.RESUELTO)
                .stream()
                .sorted((a, b) -> {
                    if (a.getFechaResolucion() == null) return 1;
                    if (b.getFechaResolucion() == null) return -1;
                    return b.getFechaResolucion().compareTo(a.getFechaResolucion());
                })
                .map(p -> ResolvedTicketDto.builder()
                        .id(p.getId())
                        .patient(p.getPacienteNombre() != null ? p.getPacienteNombre() : "Paciente")
                        .subject(p.getAsunto())
                        .description(p.getDescripcion())
                        .type(p.getTipo() != null ? p.getTipo() : "Solicitud")
                        .resolved(p.getFechaResolucion() != null ? p.getFechaResolucion().format(fmt) : "—")
                        .agentName(p.getAgenteName() != null ? p.getAgenteName() : "Soporte")
                        .satisfaction(p.getSatisfaccion())
                        .build())
                .toList();
    }

    public List<TicketMetricDto> getMetrics() {
        LocalDateTime now = LocalDateTime.now();
        List<Pqrs> all = pqrsRepository.findAll();

        String[] dayLabels = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        List<TicketMetricDto> result = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate day = now.toLocalDate().minusDays(i);
            String label = dayLabels[day.getDayOfWeek().getValue() - 1];

            long opened = all.stream()
                    .filter(p -> p.getFechaCreacion() != null
                            && p.getFechaCreacion().toLocalDate().equals(day))
                    .count();
            long resolved = all.stream()
                    .filter(p -> p.getFechaResolucion() != null
                            && p.getFechaResolucion().toLocalDate().equals(day))
                    .count();

            result.add(new TicketMetricDto(label, opened, resolved));
        }
        return result;
    }

    public List<TicketDto> getAllPqrs() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> pqrsTypes = List.of("peticion", "petición", "queja", "reclamo", "sugerencia");

        return pqrsRepository.findAll().stream()
                .filter(p -> p.getTipo() != null &&
                        pqrsTypes.stream().anyMatch(t -> t.equalsIgnoreCase(p.getTipo())))
                .sorted((a, b) -> {
                    if (a.getFechaCreacion() == null) return 1;
                    if (b.getFechaCreacion() == null) return -1;
                    return b.getFechaCreacion().compareTo(a.getFechaCreacion());
                })
                .map(p -> TicketDto.builder()
                        .id(p.getId())
                        .patient(p.getPacienteNombre() != null ? p.getPacienteNombre() : "Paciente")
                        .subject(p.getAsunto())
                        .description(p.getDescripcion())
                        .type(p.getTipo() != null ? p.getTipo() : "Solicitud")
                        .priority(p.getPrioridad() != null ? p.getPrioridad().name().toLowerCase() : "media")
                        .created(p.getFechaCreacion() != null ? p.getFechaCreacion().format(fmt) : "—")
                        .status(p.getEstado() == null ? "new" :
                                switch (p.getEstado()) {
                                    case NUEVO -> "new";
                                    case EN_PROCESO -> "in-progress";
                                    case RESUELTO, CERRADO -> "resolved";
                                })
                        .build())
                .toList();
    }

    public String respondTicket(String id, String message) {
        Pqrs ticket = pqrsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        String agentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> agentOpt = userRepository.findByEmail(agentEmail);
        String agentDisplayName = agentOpt.map(User::getFullName).orElse(agentEmail);

        Pqrs.Interaccion interaction = new Pqrs.Interaccion();
        interaction.setAgenteId(agentEmail);
        interaction.setAgenteName(agentDisplayName);
        interaction.setMensaje(message);
        interaction.setTimestamp(LocalDateTime.now());

        if (ticket.getHistorialInteracciones() == null) {
            ticket.setHistorialInteracciones(new ArrayList<>());
        }
        ticket.getHistorialInteracciones().add(interaction);
        ticket.setEstado(PqrsStatus.EN_PROCESO);
        ticket.setAgenteId(agentEmail);
        ticket.setAgenteName(agentDisplayName);
        pqrsRepository.save(ticket);

        // Notify patient via in-system notification
        if (ticket.getPacienteId() != null) {
            userRepository.findById(ticket.getPacienteId()).ifPresent(patient -> {
                Notification notif = Notification.builder()
                        .usuarioDestinoId(patient.getId())
                        .tipo("ticket_response")
                        .titulo("Respuesta a tu solicitud: " + ticket.getAsunto())
                        .mensaje(message)
                        .canal("sistema")
                        .pqrsId(ticket.getId())
                        .calificado(false)
                        .build();
                notificationRepository.save(notif);
            });
        }

        auditService.log("Respuesta enviada al ticket: " + ticket.getAsunto(), "pqrs", id, "update");
        return "Respuesta enviada correctamente";
    }

    public String resolveTicket(String id) {
        Pqrs ticket = pqrsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        String agentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> agentOpt = userRepository.findByEmail(agentEmail);
        String agentDisplayName = agentOpt.map(User::getFullName).orElse(agentEmail);

        ticket.setEstado(PqrsStatus.RESUELTO);
        ticket.setFechaResolucion(LocalDateTime.now());
        ticket.setAgenteId(agentEmail);
        ticket.setAgenteName(agentDisplayName);
        pqrsRepository.save(ticket);

        // Notify patient via in-system notification
        if (ticket.getPacienteId() != null) {
            userRepository.findById(ticket.getPacienteId()).ifPresent(patient -> {
                Notification notif = Notification.builder()
                        .usuarioDestinoId(patient.getId())
                        .tipo("ticket_resolved")
                        .titulo("Tu solicitud ha sido resuelta")
                        .mensaje("Tu solicitud \"" + ticket.getAsunto() + "\" ha sido marcada como resuelta.")
                        .canal("sistema")
                        .pqrsId(ticket.getId())
                        .calificado(false)
                        .build();
                notificationRepository.save(notif);
            });
        }

        auditService.log("Ticket resuelto: " + ticket.getAsunto(), "pqrs", id, "update");
        return "Ticket marcado como resuelto";
    }
}
