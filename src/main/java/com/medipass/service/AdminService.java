package com.medipass.service;

import com.medipass.dto.admin.*;
import com.medipass.entity.*;
import com.medipass.enums.*;
import com.medipass.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final PolicyRepository policyRepository;
    private final PqrsRepository pqrsRepository;
    private final AuditLogRepository auditLogRepository;
    private final LabResultRepository labResultRepository;
    private final ClinicalRecordRepository clinicalRecordRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, AppointmentRepository appointmentRepository,
                        PolicyRepository policyRepository, PqrsRepository pqrsRepository,
                        AuditLogRepository auditLogRepository, LabResultRepository labResultRepository,
                        ClinicalRecordRepository clinicalRecordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.policyRepository = policyRepository;
        this.pqrsRepository = pqrsRepository;
        this.auditLogRepository = auditLogRepository;
        this.labResultRepository = labResultRepository;
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminStatsDto getStats() {
        try {
            // Solo pacientes con póliza aprobada (estado ACTIVO)
            long activePatients = userRepository.countByRoleAndEstado(Role.PATIENT, UserStatus.ACTIVO);

            // Citas agendadas para HOY, excluyendo canceladas
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay   = LocalDate.now().atTime(23, 59, 59);
            long todayAppointments = appointmentRepository.findByFechaHoraBetween(startOfDay, endOfDay)
                    .stream().filter(a -> a.getEstado() != AppointmentStatus.CANCELLED).count();

            // Citas de hoy en estado de espera / pendiente
            long waiting = appointmentRepository.findByFechaHoraBetween(startOfDay, endOfDay)
                    .stream().filter(a -> a.getEstado() == AppointmentStatus.WAITING
                                      || a.getEstado() == AppointmentStatus.PENDING
                                      || a.getEstado() == AppointmentStatus.SCHEDULED).count();

            // Solo pólizas APROBADAS que vencen en los próximos 30 días
            long expiring = policyRepository.findByEstadoValidacionAndFechaVencimientoBetween(
                    PolicyStatus.APROBADA, LocalDate.now(), LocalDate.now().plusDays(30)).size();

            // PQRS abiertos (NUEVO + EN_PROCESO)
            long openTickets    = pqrsRepository.countByEstado(PqrsStatus.NUEVO)
                                + pqrsRepository.countByEstado(PqrsStatus.EN_PROCESO);
            long priorityTickets = pqrsRepository.countByPrioridad(Priority.ALTA);

            return AdminStatsDto.builder()
                    .activePatients(activePatients)
                    .todayAppointments(todayAppointments)
                    .pendingWaiting(waiting)
                    .expiringPolicies(expiring)
                    .openTickets(openTickets)
                    .priorityTickets(priorityTickets)
                    .build();
        } catch (Exception e) {
            return AdminStatsDto.builder()
                    .activePatients(0)
                    .todayAppointments(0)
                    .pendingWaiting(0)
                    .expiringPolicies(0)
                    .openTickets(0)
                    .priorityTickets(0)
                    .build();
        }
    }

    public List<ActivityPointDto> getWeeklyActivity() {
        try {
            String[] DAYS = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
            LocalDate today = LocalDate.now();
            // Inicio de la semana actual (lunes)
            LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);

            List<ActivityPointDto> result = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                LocalDate day = monday.plusDays(i);
                LocalDateTime from = day.atStartOfDay();
                LocalDateTime to   = day.atTime(23, 59, 59);

                long pacientes   = userRepository.findByRoleAndFechaCreacionBetween(Role.PATIENT, from, to).size();
                long citas       = appointmentRepository.countByFechaCreacionBetween(from, to);
                long polizas     = policyRepository.findByFechaCreacionBetween(from, to).size();

                result.add(new ActivityPointDto(DAYS[i], (int) pacientes, (int) citas, (int) polizas));
            }
            return result;
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<ModuleUsageDto> getModulesUsage() {
        try {
            // Citas activas (excluye completadas y canceladas)
            long citas = appointmentRepository.count()
                       - appointmentRepository.countByEstado(AppointmentStatus.COMPLETED)
                       - appointmentRepository.countByEstado(AppointmentStatus.CANCELLED);
            // Pólizas activas = pacientes con estado ACTIVO (aprobación activa la cuenta)
            long polizas        = userRepository.findByRoleAndEstado(Role.PATIENT, UserStatus.ACTIVO).size();
            // Laboratorio pendiente
            long laboratorio    = labResultRepository.countByEstado(LabStatus.PENDIENTE)
                                + labResultRepository.countByEstado(LabStatus.EN_PROCESO);
            // Historia clínica total
            long historiaClinica = clinicalRecordRepository.count();
            // PQRS abiertos
            long pqrs           = pqrsRepository.countByEstado(PqrsStatus.NUEVO)
                                + pqrsRepository.countByEstado(PqrsStatus.EN_PROCESO);

            return List.of(
                    new ModuleUsageDto("Citas activas",      (int) Math.max(0, citas)),
                    new ModuleUsageDto("Pólizas aprobadas",  (int) polizas),
                    new ModuleUsageDto("Lab pendiente",      (int) laboratorio),
                    new ModuleUsageDto("Historia Clínica",   (int) historiaClinica),
                    new ModuleUsageDto("PQRS abiertos",      (int) pqrs)
            );
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<AuditEntryDto> getRecentAudit() {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
            List<AuditLog> logs = auditLogRepository.findTop20ByOrderByTimestampDesc();
            if (logs == null) return List.of();
            return logs.stream()
                    .map(log -> AuditEntryDto.builder()
                            .action(log.getAccion())
                            .user(log.getUserName())
                            .time(log.getTimestamp() != null
                                    ? log.getTimestamp().format(fmt)
                                    : "00:00")
                            .type(log.getType() != null ? log.getType() : "update")
                            .build())
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<UserListDto> getUsers() {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return userRepository.findAll().stream()
                    .map(u -> UserListDto.builder()
                            .id(u.getId())
                            .name((u.getNombres() != null ? u.getNombres() : "") + " " +
                                  (u.getApellidos() != null ? u.getApellidos() : ""))
                            .email(u.getEmail())
                            .role(u.getRole() != null ? u.getRole().name().toLowerCase() : "—")
                            .status(u.getEstado() != null ? u.getEstado().name().toLowerCase() : "activo")
                            .createdAt(u.getFechaCreacion() != null ? u.getFechaCreacion().format(fmt) : "—")
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void createUser(String nombres, String apellidos, String email,
                           String password, String roleStr, String documento) {
        User user = new User();
        user.setNombres(nombres);
        user.setApellidos(apellidos);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setDocumento(documento);
        user.setRole(Role.valueOf(roleStr.toUpperCase()));
        user.setEstado(UserStatus.ACTIVO);
        user.setFechaCreacion(LocalDateTime.now());
        userRepository.save(user);
    }

    public void updateUser(String id, String nombres, String apellidos, String email,
                           String roleStr, String documento, String estado) {
        userRepository.findById(id).ifPresent(user -> {
            if (nombres != null && !nombres.isBlank()) user.setNombres(nombres);
            if (apellidos != null && !apellidos.isBlank()) user.setApellidos(apellidos);
            if (email != null && !email.isBlank()) user.setEmail(email);
            if (documento != null && !documento.isBlank()) user.setDocumento(documento);
            if (roleStr != null && !roleStr.isBlank()) {
                try { user.setRole(Role.valueOf(roleStr.toUpperCase())); } catch (Exception ignored) {}
            }
            if (estado != null && !estado.isBlank()) {
                try { user.setEstado(UserStatus.valueOf(estado.toUpperCase())); } catch (Exception ignored) {}
            }
            userRepository.save(user);
        });
    }

    public void deleteUser(String id) {
        // Eliminar todos los datos del paciente en cascada
        policyRepository.findByPacienteId(id).ifPresent(policyRepository::delete);
        appointmentRepository.deleteAll(appointmentRepository.findByPacienteId(id));
        labResultRepository.deleteAll(labResultRepository.findByPacienteId(id));
        clinicalRecordRepository.deleteAll(clinicalRecordRepository.findByPacienteIdOrderByFechaDesc(id));
        pqrsRepository.deleteAll(pqrsRepository.findByPacienteId(id));
        userRepository.deleteById(id);
    }

    public void toggleUserStatus(String id) {
        userRepository.findById(id).ifPresent(user -> {
            if (user.getEstado() == UserStatus.ACTIVO) {
                user.setEstado(UserStatus.SUSPENDIDO);
            } else {
                user.setEstado(UserStatus.ACTIVO);
            }
            userRepository.save(user);
        });
    }

    public long cleanupOrphanedData() {
        Set<String> validUserIds = userRepository.findAll()
                .stream().map(User::getId).collect(Collectors.toSet());

        List<Policy> orphanPolicies = policyRepository.findAll().stream()
                .filter(p -> p.getPacienteId() == null || !validUserIds.contains(p.getPacienteId()))
                .collect(Collectors.toList());

        List<Appointment> orphanAppts = appointmentRepository.findAll().stream()
                .filter(a -> a.getPacienteId() == null || !validUserIds.contains(a.getPacienteId()))
                .collect(Collectors.toList());

        List<LabResult> orphanLabs = labResultRepository.findAll().stream()
                .filter(l -> l.getPacienteId() == null || !validUserIds.contains(l.getPacienteId()))
                .collect(Collectors.toList());

        List<ClinicalRecord> orphanClinical = clinicalRecordRepository.findAll().stream()
                .filter(c -> c.getPacienteId() == null || !validUserIds.contains(c.getPacienteId()))
                .collect(Collectors.toList());

        List<Pqrs> orphanPqrs = pqrsRepository.findAll().stream()
                .filter(p -> p.getPacienteId() == null || !validUserIds.contains(p.getPacienteId()))
                .collect(Collectors.toList());

        policyRepository.deleteAll(orphanPolicies);
        appointmentRepository.deleteAll(orphanAppts);
        labResultRepository.deleteAll(orphanLabs);
        clinicalRecordRepository.deleteAll(orphanClinical);
        pqrsRepository.deleteAll(orphanPqrs);

        return orphanPolicies.size() + orphanAppts.size() + orphanLabs.size()
             + orphanClinical.size() + orphanPqrs.size();
    }

    public AdminReportDto getReports() {
        try {
            long totalUsers     = userRepository.count();
            long totalAppts     = appointmentRepository.count()
                                - appointmentRepository.countByEstado(AppointmentStatus.COMPLETED)
                                - appointmentRepository.countByEstado(AppointmentStatus.CANCELLED);
            long totalPolicies  = policyRepository.count();
            long activePolicies = policyRepository.countByEstadoValidacion(PolicyStatus.APROBADA);
            long totalPqrs      = pqrsRepository.count();
            long resolvedPqrs   = pqrsRepository.countByEstado(PqrsStatus.RESUELTO);
            return AdminReportDto.builder()
                    .totalUsers(totalUsers)
                    .totalAppointments(totalAppts)
                    .totalPolicies(totalPolicies)
                    .activePolicies(activePolicies)
                    .expiredPolicies(policyRepository.countByEstadoValidacion(PolicyStatus.VENCIDA))
                    .totalPqrs(totalPqrs)
                    .resolvedPqrs(resolvedPqrs)
                    .build();
        } catch (Exception e) {
            return AdminReportDto.builder().build();
        }
    }
}