package com.medipass.service;

import com.medipass.dto.validator.*;
import com.medipass.entity.Policy;
import com.medipass.enums.PolicyStatus;
import com.medipass.enums.UserStatus;
import com.medipass.exception.ResourceNotFoundException;
import com.medipass.repository.PolicyRepository;
import com.medipass.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ValidatorService {

    private final PolicyRepository policyRepository;
    private final AuditService auditService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public ValidatorService(PolicyRepository policyRepository, AuditService auditService,
                            EmailService emailService, UserRepository userRepository) {
        this.policyRepository = policyRepository;
        this.auditService = auditService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public ValidatorStatsDto getStats() {
        long pending = policyRepository.countByEstadoValidacion(PolicyStatus.PENDIENTE);
        long approved = policyRepository.countByEstadoValidacion(PolicyStatus.APROBADA);
        long rejected = policyRepository.countByEstadoValidacion(PolicyStatus.RECHAZADA);

        long expiring = policyRepository.findByFechaVencimientoBetween(
                LocalDate.now(), LocalDate.now().plusDays(30)).size();

        return ValidatorStatsDto.builder()
                .pendingRequests(pending)
                .newToday(pending)
                .approvedToday(approved)
                .rejectedToday(rejected)
                .rejectedReason("N/A")
                .expiringPolicies(expiring)
                .expiringDays(30L)
                .build();
    }

    public List<ValidationRequestDto> getPendingRequests() {
        return getRequestsByStatus(PolicyStatus.PENDIENTE);
    }

    public List<ValidationRequestDto> getApprovedRequests() {
        return getRequestsByStatus(PolicyStatus.APROBADA);
    }

    public List<ValidationRequestDto> getRejectedRequests() {
        return getRequestsByStatus(PolicyStatus.RECHAZADA);
    }

    private List<ValidationRequestDto> getRequestsByStatus(PolicyStatus status) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return policyRepository.findByEstadoValidacion(status)
                .stream()
                .map(p -> ValidationRequestDto.builder()
                        .id(p.getId())
                        .patient(p.getNombrePaciente() != null ? p.getNombrePaciente() : "Paciente")
                        .document(p.getDocumentoPaciente())
                        .policy(p.getNumeroPóliza())
                        .insurer(p.getAseguradora())
                        .plan(p.getTipoPlan())
                        .submitted(p.getFechaCreacion() != null ? p.getFechaCreacion().format(fmt) : "—")
                        .status(status.name().toLowerCase())
                        .build())
                .toList();
    }

    public List<ExpiringPolicyDto> getExpiringPolicies() {
        return policyRepository
                .findByFechaVencimientoBetween(LocalDate.now(), LocalDate.now().plusDays(30))
                .stream()
                .map(p -> ExpiringPolicyDto.builder()
                        .id(p.getId())
                        .patient(p.getNombrePaciente() != null ? p.getNombrePaciente() : "Paciente")
                        .policy(p.getNumeroPóliza())
                        .daysLeft(p.getFechaVencimiento() != null
                                ? ChronoUnit.DAYS.between(LocalDate.now(), p.getFechaVencimiento())
                                : 0L)
                        .plan(p.getTipoPlan())
                        .build())
                .toList();
    }

    public String approvePolicy(String id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Póliza no encontrada"));

        String validator = SecurityContextHolder.getContext().getAuthentication().getName();
        policy.setEstadoValidacion(PolicyStatus.APROBADA);
        policy.setValidadoPor(validator);
        policy.setFechaValidacion(java.time.LocalDateTime.now());
        policyRepository.save(policy);

        // Activar la cuenta del paciente
        if (policy.getEmailPaciente() != null) {
            userRepository.findByEmail(policy.getEmailPaciente()).ifPresent(user -> {
                user.setEstado(UserStatus.ACTIVO);
                userRepository.save(user);
            });
        }

        auditService.log("Póliza aprobada: " + policy.getNumeroPóliza(), "policies", id, "approve");

        // Notificar al paciente por correo
        if (policy.getEmailPaciente() != null) {
            emailService.sendPolicyApproved(
                    policy.getEmailPaciente(),
                    policy.getNombrePaciente() != null ? policy.getNombrePaciente() : "Paciente",
                    policy.getNumeroPóliza()
            );
        }

        return "Póliza aprobada correctamente";
    }

    public String rejectPolicy(String id, String reason) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Póliza no encontrada"));

        String validator = SecurityContextHolder.getContext().getAuthentication().getName();
        policy.setEstadoValidacion(PolicyStatus.RECHAZADA);
        policy.setMotivoRechazo(reason);
        policy.setValidadoPor(validator);
        policy.setFechaValidacion(java.time.LocalDateTime.now());
        policyRepository.save(policy);

        auditService.log("Póliza rechazada: " + policy.getNumeroPóliza() + ". Motivo: " + reason,
                "policies", id, "cancel");

        // Notificar al paciente por correo
        if (policy.getEmailPaciente() != null) {
            emailService.sendPolicyRejected(
                    policy.getEmailPaciente(),
                    policy.getNombrePaciente() != null ? policy.getNombrePaciente() : "Paciente",
                    policy.getNumeroPóliza(),
                    reason
            );
        }

        return "Póliza rechazada";
    }
}
