package com.medipass.service;

import com.medipass.dto.lab.LabResultDetailDto;
import com.medipass.dto.patient.*;
import com.medipass.entity.*;
import com.medipass.enums.*;
import com.medipass.exception.ResourceNotFoundException;
import com.medipass.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final LabResultRepository labResultRepository;
    private final NotificationRepository notificationRepository;
    private final PolicyRepository policyRepository;
    private final ClinicalRecordRepository clinicalRecordRepository;
    private final PqrsRepository pqrsRepository;

    public PatientService(UserRepository userRepository, AppointmentRepository appointmentRepository,
                          LabResultRepository labResultRepository, NotificationRepository notificationRepository,
                          PolicyRepository policyRepository, ClinicalRecordRepository clinicalRecordRepository,
                          PqrsRepository pqrsRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.labResultRepository = labResultRepository;
        this.notificationRepository = notificationRepository;
        this.policyRepository = policyRepository;
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.pqrsRepository = pqrsRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public PatientStatsDto getStats() {
        User user = getCurrentUser();
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        // Next appointment
        List<Appointment> upcoming = appointmentRepository
                .findByPacienteIdOrderByFechaHoraAsc(user.getId())
                .stream()
                .filter(a -> a.getFechaHora() != null
                        && a.getFechaHora().isAfter(java.time.LocalDateTime.now())
                        && a.getEstado() != AppointmentStatus.CANCELLED)
                .toList();

        String nextDate = "Sin citas";
        String nextTime = "—";
        String nextDoctor = "—";

        if (!upcoming.isEmpty()) {
            Appointment next = upcoming.get(0);
            nextDate = next.getFechaHora().format(dateFmt);
            nextTime = next.getFechaHora().format(timeFmt);
            nextDoctor = next.getMedicoNombre() != null ? next.getMedicoNombre() : "Médico asignado";
        }

        // Lab results
        List<LabResult> labResults = labResultRepository.findByPacienteId(user.getId());
        long newResults = labResults.stream()
                .filter(l -> l.getEstado() == LabStatus.PUBLICADO).count();
        long alertResults = labResults.stream()
                .filter(LabResult::isAlertaCritica).count();

        // Policy
        String policyStatus = "Sin póliza";
        String policyPlan = "—";
        long policyDaysLeft = 0;
        String policyExpiry = "—";

        if (user.getPolicyId() != null) {
            policyRepository.findById(user.getPolicyId()).ifPresent(p -> {});
            var policyOpt = policyRepository.findByPacienteId(user.getId());
            if (policyOpt.isPresent()) {
                Policy policy = policyOpt.get();
                policyStatus = policy.getEstadoValidacion().name();
                policyPlan = policy.getTipoPlan();
                if (policy.getFechaVencimiento() != null) {
                    policyExpiry = policy.getFechaVencimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    policyDaysLeft = java.time.temporal.ChronoUnit.DAYS.between(
                            LocalDate.now(), policy.getFechaVencimiento());
                }
            }
        }

        return PatientStatsDto.builder()
                .nextAppointmentDate(nextDate)
                .nextAppointmentTime(nextTime)
                .nextAppointmentDoctor(nextDoctor)
                .newLabResults(newResults)
                .labResultsAlert(alertResults)
                .policyStatus(policyStatus)
                .policyPlan(policyPlan)
                .policyDaysLeft(Math.max(0, policyDaysLeft))
                .policyExpiryDate(policyExpiry)
                .build();
    }

    public List<AppointmentDto> getAppointments() {
        User user = getCurrentUser();
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter isoFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        return appointmentRepository.findByPacienteIdOrderByFechaHoraAsc(user.getId())
                .stream()
                .map(a -> AppointmentDto.builder()
                        .id(a.getId())
                        .doctor(a.getMedicoNombre() != null ? a.getMedicoNombre() : "Médico asignado")
                        .specialty(a.getEspecialidad())
                        .date(a.getFechaHora() != null ? a.getFechaHora().format(dateFmt) : "—")
                        .time(a.getFechaHora() != null ? a.getFechaHora().format(timeFmt) : "—")
                        .status(mapAppointmentStatus(a.getEstado()))
                        .modalidad(a.getModalidad() != null ? a.getModalidad() : "presencial")
                        .fechaHoraIso(a.getFechaHora() != null ? a.getFechaHora().format(isoFmt) : "")
                        .build())
                .toList();
    }

    public void cancelAppointment(String id) {
        User user = getCurrentUser();
        Appointment appt = appointmentRepository.findById(id)
                .filter(a -> user.getId().equals(a.getPacienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        appt.setEstado(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appt);
    }

    public void rescheduleAppointment(String id, String fechaHora) {
        User user = getCurrentUser();
        Appointment appt = appointmentRepository.findById(id)
                .filter(a -> user.getId().equals(a.getPacienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        try {
            appt.setFechaHora(java.time.LocalDateTime.parse(fechaHora));
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido");
        }
        appt.setEstado(AppointmentStatus.PENDING);
        appointmentRepository.save(appt);
    }

    public List<LabResultDto> getLabResults() {
        User user = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return labResultRepository.findByPacienteId(user.getId())
                .stream()
                .map(l -> LabResultDto.builder()
                        .id(l.getId())
                        .name(l.getTipoExamen())
                        .date(l.getFechaSolicitud() != null ? l.getFechaSolicitud().format(fmt) : "—")
                        .status(mapLabStatus(l.getEstado()))
                        .alert(l.isAlertaCritica())
                        .downloadUrl(l.getArchivoUrl())
                        .build())
                .toList();
    }

    public List<NotificationDto> getNotifications() {
        User user = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm dd/MM");

        return notificationRepository
                .findByUsuarioDestinoIdOrderByFechaEnvioDesc(user.getId())
                .stream()
                .map(n -> NotificationDto.builder()
                        .id(n.getId())
                        .title(n.getTitulo())
                        .message(n.getMensaje())
                        .time(n.getFechaEnvio() != null ? n.getFechaEnvio().format(fmt) : "—")
                        .type(n.getTipo())
                        .read(n.isLeida())
                        .pqrsId(n.getPqrsId())
                        .ratable("ticket_response".equals(n.getTipo()) && !n.isCalificado())
                        .rating(n.getCalificacion())
                        .build())
                .toList();
    }

    public void ratePqrs(String pqrsId, int rating) {
        User user = getCurrentUser();
        Pqrs pqrs = pqrsRepository.findById(pqrsId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));
        if (pqrs.getPacienteId() == null || !pqrs.getPacienteId().equals(user.getId())) {
            throw new ResourceNotFoundException("No autorizado");
        }
        pqrs.setSatisfaccion(rating);
        pqrsRepository.save(pqrs);
        notificationRepository.findByPqrsIdAndUsuarioDestinoId(pqrsId, user.getId())
                .ifPresent(n -> {
                    n.setCalificado(true);
                    n.setCalificacion(rating);
                    notificationRepository.save(n);
                });
    }

    private String mapAppointmentStatus(AppointmentStatus status) {
        return switch (status) {
            case CONFIRMED   -> "confirmed";
            case PENDING     -> "pending";
            case CANCELLED   -> "cancelled";
            case COMPLETED   -> "completed";
            case IN_PROGRESS -> "in-progress";
            case WAITING     -> "waiting";
            case SCHEDULED   -> "scheduled";
        };
    }

    private String mapLabStatus(LabStatus status) {
        return switch (status) {
            case PUBLICADO -> "ready";
            case PENDIENTE -> "pending";
            case EN_PROCESO -> "processing";
            default -> "pending";
        };
    }

    public PolicyDetailDto getPolicy() {
        User user = getCurrentUser();
        var opt = policyRepository.findByPacienteId(user.getId());
        if (opt.isEmpty()) return null;
        Policy p = opt.get();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        long days = 0;
        if (p.getFechaVencimiento() != null)
            days = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), p.getFechaVencimiento());
        return PolicyDetailDto.builder()
                .id(p.getId())
                .numeroPóliza(p.getNumeroPóliza())
                .aseguradora(p.getAseguradora())
                .tipoPlan(p.getTipoPlan())
                .fechaVencimiento(p.getFechaVencimiento() != null ? p.getFechaVencimiento().format(fmt) : "—")
                .estadoPago(p.getEstadoPago())
                .estadoValidacion(p.getEstadoValidacion() != null ? p.getEstadoValidacion().name() : "PENDIENTE")
                .diasRestantes(Math.max(0, days))
                .beneficios(p.getBeneficios())
                .build();
    }

    public List<ClinicalRecordSummaryDto> getClinicalRecords() {
        User user = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return clinicalRecordRepository.findByPacienteIdOrderByFechaDesc(user.getId()).stream()
                .map(r -> ClinicalRecordSummaryDto.builder()
                        .id(r.getId())
                        .date(r.getFecha() != null ? r.getFecha().format(fmt) : "—")
                        .doctor(r.getMedicoNombre() != null ? r.getMedicoNombre() : "—")
                        .diagnosis(r.getDiagnostico() != null ? r.getDiagnostico().getDescripcion() : "—")
                        .diagnosisCode(r.getDiagnostico() != null ? r.getDiagnostico().getCodigo() : "—")
                        .type(r.getTipoConsulta() != null ? r.getTipoConsulta() : "Consulta")
                        .plan(r.getPlanTratamiento())
                        .build())
                .toList();
    }

    public List<PrescriptionDto> getPrescriptions() {
        User user = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return clinicalRecordRepository.findByPacienteIdOrderByFechaDesc(user.getId()).stream()
                .filter(r -> r.getRecetas() != null && !r.getRecetas().isEmpty())
                .flatMap(r -> r.getRecetas().stream().map(rx -> PrescriptionDto.builder()
                        .id(r.getId() + "-" + rx.getMedicamento())
                        .medicamento(rx.getMedicamento())
                        .dosis(rx.getDosis())
                        .duracion(rx.getDuracion())
                        .indicaciones(rx.getIndicaciones())
                        .doctor(r.getMedicoNombre() != null ? r.getMedicoNombre() : "—")
                        .date(r.getFecha() != null ? r.getFecha().format(fmt) : "—")
                        .build()))
                .toList();
    }

    public void createAppointment(CreateAppointmentDto dto) {
        User user = getCurrentUser();
        Appointment a = new Appointment();
        a.setPacienteId(user.getId());
        a.setPacienteNombre((user.getNombres() != null ? user.getNombres() : "") + " " +
                (user.getApellidos() != null ? user.getApellidos() : ""));
        a.setEspecialidad(dto.getEspecialidad());
        a.setMotivoConsulta(dto.getMotivoConsulta());
        a.setModalidad(dto.getModalidad() != null ? dto.getModalidad() : "presencial");
        a.setEstado(AppointmentStatus.PENDING);
        a.setEnListaEspera(true);
        a.setCodigoCita("MP-" + System.currentTimeMillis());
        if (dto.getFechaHora() != null && !dto.getFechaHora().isBlank()) {
            try {
                a.setFechaHora(java.time.LocalDateTime.parse(dto.getFechaHora()));
            } catch (Exception ignored) {}
        }
        appointmentRepository.save(a);
    }

    public ClinicalRecordDetailDto getClinicalRecordDetail(String recordId) {
        User user = getCurrentUser();
        ClinicalRecord r = clinicalRecordRepository.findById(recordId)
                .filter(rec -> rec.getPacienteId() != null && rec.getPacienteId().equals(user.getId()))
                .orElseThrow(() -> new com.medipass.exception.ResourceNotFoundException("Historia clinica no encontrada"));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        java.util.List<ClinicalRecordDetailDto.RecetaDto> recetas = r.getRecetas() == null
                ? java.util.List.of()
                : r.getRecetas().stream().map(rx -> new ClinicalRecordDetailDto.RecetaDto(
                        rx.getMedicamento(), rx.getDosis(), rx.getDuracion(), rx.getIndicaciones()))
                  .toList();
        return ClinicalRecordDetailDto.builder()
                .id(r.getId())
                .date(r.getFecha() != null ? r.getFecha().format(fmt) : "--")
                .doctor(r.getMedicoNombre() != null ? r.getMedicoNombre() : "--")
                .tipoConsulta(r.getTipoConsulta())
                .anamnesis(r.getAnamnesis())
                .diagnosis(r.getDiagnostico() != null ? r.getDiagnostico().getDescripcion() : "--")
                .diagnosisCode(r.getDiagnostico() != null ? r.getDiagnostico().getCodigo() : "--")
                .planTratamiento(r.getPlanTratamiento())
                .evolucion(r.getEvolucion())
                .recetas(recetas)
                .examenesSolicitados(r.getExamenesSolicitados() != null ? r.getExamenesSolicitados() : java.util.List.of())
                .build();
    }

    public LabResultDetailDto getLabResultDetail(String id) {
        User user = getCurrentUser();
        LabResult lab = labResultRepository.findById(id)
                .filter(l -> user.getId().equals(l.getPacienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Resultado no encontrado"));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<LabResultDetailDto.ResultItemDto> items = new ArrayList<>();
        if (lab.getResultItems() != null) {
            for (LabResult.ResultItem ri : lab.getResultItems()) {
                items.add(new LabResultDetailDto.ResultItemDto(
                        ri.getParametro(), ri.getValor(), ri.getUnidad(), ri.getRangoNormal(), ri.isCritico()));
            }
        }
        return LabResultDetailDto.builder()
                .id(lab.getId())
                .patient(lab.getPacienteNombre() != null ? lab.getPacienteNombre() : "Paciente")
                .patientId(lab.getPacienteId())
                .exam(lab.getTipoExamen())
                .doctor(lab.getMedicoNombre() != null ? lab.getMedicoNombre() : "Médico")
                .doctorId(lab.getMedicoSolicitanteId())
                .laboratorioNombre(lab.getLaboratorioNombre() != null ? lab.getLaboratorioNombre() : "Laboratorio MediPass")
                .requested(lab.getFechaSolicitud() != null ? lab.getFechaSolicitud().format(fmt) : "—")
                .published(lab.getFechaPublicacion() != null ? lab.getFechaPublicacion().format(fmt) : "—")
                .items(items)
                .observaciones(lab.getObservaciones())
                .alertaCritica(lab.isAlertaCritica())
                .status(lab.getEstado() != null ? lab.getEstado().name() : "PENDIENTE")
                .build();
    }

    public void createPqrs(String tipo, String asunto, String descripcion) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Pqrs pqrs = Pqrs.builder()
                .pacienteId(patient.getId())
                .pacienteNombre(patient.getFullName())
                .tipo(tipo)
                .asunto(asunto)
                .descripcion(descripcion)
                .estado(PqrsStatus.NUEVO)
                .prioridad(Priority.MEDIA)
                .satisfaccion(0)
                .build();
        pqrsRepository.save(pqrs);
    }
}
