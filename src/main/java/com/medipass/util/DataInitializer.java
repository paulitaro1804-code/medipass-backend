package com.medipass.util;

import com.medipass.entity.*;
import com.medipass.enums.*;
import com.medipass.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicalRecordRepository clinicalRecordRepository;
    private final LabResultRepository labResultRepository;
    private final PqrsRepository pqrsRepository;
    private final AuditLogRepository auditLogRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PolicyRepository policyRepository,
                           AppointmentRepository appointmentRepository, ClinicalRecordRepository clinicalRecordRepository,
                           LabResultRepository labResultRepository, PqrsRepository pqrsRepository,
                           AuditLogRepository auditLogRepository, NotificationRepository notificationRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.appointmentRepository = appointmentRepository;
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.labResultRepository = labResultRepository;
        this.pqrsRepository = pqrsRepository;
        this.auditLogRepository = auditLogRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@medipass.com").isPresent()) {
            log.info("Demo users already exist — skipping DataInitializer");
            return;
        }

        log.info("Seeding demo data...");

        // ─── Users ───────────────────────────────────────────────────────────
        User admin = save(user("Admin", "Sistema", "ADMIN001", "admin@medipass.com",
                "admin123", Role.ADMIN, UserStatus.ACTIVO, null));

        User doctor1 = save(user("Carlos", "Rodríguez", "DOC001", "doctor@medipass.com",
                "doctor123", Role.DOCTOR, UserStatus.ACTIVO, "Medicina General"));

        User doctor2 = save(user("Ana", "Martínez", "DOC002", "doctor2@medipass.com",
                "doctor123", Role.DOCTOR, UserStatus.ACTIVO, "Cardiología"));

        User coordinator = save(user("Laura", "Pérez", "COORD001", "coordinador@medipass.com",
                "coord123", Role.COORDINATOR, UserStatus.ACTIVO, null));

        User validator = save(user("Miguel", "Torres", "VAL001", "validador@medipass.com",
                "valid123", Role.VALIDATOR, UserStatus.ACTIVO, null));

        User lab = save(user("Sandra", "López", "LAB001", "lab@medipass.com",
                "lab123", Role.LAB, UserStatus.ACTIVO, null));

        User support = save(user("Pedro", "García", "SUP001", "soporte@medipass.com",
                "soporte123", Role.SUPPORT, UserStatus.ACTIVO, null));

        User patient1 = save(user("Carlos", "Ramírez", "CC12345678", "carlos.ramirez@gmail.com",
                "paciente123", Role.PATIENT, UserStatus.ACTIVO, null));

        User patient2 = save(user("María", "García", "CC87654321", "maria.garcia@gmail.com",
                "paciente123", Role.PATIENT, UserStatus.ACTIVO, null));

        User patient3 = save(user("Juan", "Pérez", "CC11223344", "juan.perez@gmail.com",
                "paciente123", Role.PATIENT, UserStatus.PENDIENTE, null));

        // ─── Policies ────────────────────────────────────────────────────────
        Policy policy1 = policyRepository.save(Policy.builder()
                .numeroPóliza("POL-2024-001")
                .pacienteId(patient1.getId())
                .aseguradora("Sura")
                .tipoPlan("Plan Premium")
                .fechaVencimiento(LocalDate.now().plusMonths(8))
                .estadoPago("AL_DIA")
                .estadoValidacion(PolicyStatus.APROBADA)
                .beneficios(List.of("Consultas generales", "Especialistas", "Laboratorios", "Hospitalización"))
                .nombrePaciente(patient1.getFullName())
                .documentoPaciente("CC12345678")
                .emailPaciente("carlos.ramirez@gmail.com")
                .validadoPor("validador@medipass.com")
                .fechaValidacion(LocalDateTime.now().minusMonths(1))
                .build());

        Policy policy2 = policyRepository.save(Policy.builder()
                .numeroPóliza("POL-2024-002")
                .pacienteId(patient2.getId())
                .aseguradora("Compensar")
                .tipoPlan("Plan Básico")
                .fechaVencimiento(LocalDate.now().plusDays(20))
                .estadoPago("AL_DIA")
                .estadoValidacion(PolicyStatus.APROBADA)
                .beneficios(List.of("Consultas generales", "Laboratorios"))
                .nombrePaciente(patient2.getFullName())
                .documentoPaciente("CC87654321")
                .emailPaciente("maria.garcia@gmail.com")
                .validadoPor("validador@medipass.com")
                .fechaValidacion(LocalDateTime.now().minusMonths(2))
                .build());

        Policy policy3 = policyRepository.save(Policy.builder()
                .numeroPóliza("POL-2024-003")
                .pacienteId(patient3.getId())
                .aseguradora("Colsanitas")
                .tipoPlan("Plan Familiar")
                .fechaVencimiento(LocalDate.now().plusMonths(3))
                .estadoPago("AL_DIA")
                .estadoValidacion(PolicyStatus.PENDIENTE)
                .beneficios(List.of("Consultas generales"))
                .nombrePaciente(patient3.getFullName())
                .documentoPaciente("CC11223344")
                .emailPaciente("juan.perez@gmail.com")
                .build());

        // Link policies to patients
        patient1.setPolicyId(policy1.getId());
        patient2.setPolicyId(policy2.getId());
        patient3.setPolicyId(policy3.getId());
        userRepository.saveAll(List.of(patient1, patient2, patient3));

        // ─── Appointments ────────────────────────────────────────────────────
        Appointment apt1 = appointmentRepository.save(Appointment.builder()
                .pacienteId(patient1.getId())
                .medicoId(doctor1.getId())
                .especialidad("Medicina General")
                .fechaHora(LocalDateTime.now().plusDays(2).withHour(9).withMinute(0))
                .estado(AppointmentStatus.CONFIRMED)
                .codigoCita("CITA-001")
                .modalidad("PRESENCIAL")
                .motivoConsulta("Control anual")
                .pacienteNombre(patient1.getFullName())
                .medicoNombre(doctor1.getFullName())
                .pacienteEdad(32)
                .prioridad("media")
                .enListaEspera(false)
                .planPaciente("Plan Premium")
                .build());

        Appointment apt2 = appointmentRepository.save(Appointment.builder()
                .pacienteId(patient2.getId())
                .medicoId(doctor2.getId())
                .especialidad("Cardiología")
                .fechaHora(LocalDateTime.now().withHour(10).withMinute(30))
                .estado(AppointmentStatus.IN_PROGRESS)
                .codigoCita("CITA-002")
                .modalidad("PRESENCIAL")
                .motivoConsulta("Dolor en el pecho")
                .pacienteNombre(patient2.getFullName())
                .medicoNombre(doctor2.getFullName())
                .pacienteEdad(45)
                .prioridad("alta")
                .enListaEspera(false)
                .planPaciente("Plan Básico")
                .build());

        Appointment apt3 = appointmentRepository.save(Appointment.builder()
                .pacienteId(patient1.getId())
                .medicoId(doctor1.getId())
                .especialidad("Medicina General")
                .fechaHora(LocalDateTime.now().minusDays(5).withHour(11).withMinute(0))
                .estado(AppointmentStatus.COMPLETED)
                .codigoCita("CITA-003")
                .modalidad("VIRTUAL")
                .motivoConsulta("Seguimiento")
                .pacienteNombre(patient1.getFullName())
                .medicoNombre(doctor1.getFullName())
                .pacienteEdad(32)
                .prioridad("baja")
                .enListaEspera(false)
                .planPaciente("Plan Premium")
                .build());

        Appointment waitlistAppt = appointmentRepository.save(Appointment.builder()
                .pacienteId(patient2.getId())
                .especialidad("Cardiología")
                .estado(AppointmentStatus.PENDING)
                .pacienteNombre(patient2.getFullName())
                .pacienteEdad(45)
                .prioridad("alta")
                .enListaEspera(true)
                .tiempoEspera("45 min")
                .planPaciente("Plan Básico")
                .build());

        // ─── Clinical Records ─────────────────────────────────────────────────
        ClinicalRecord.Diagnostico diag = new ClinicalRecord.Diagnostico();
        diag.setCodigo("J06.9");
        diag.setDescripcion("Infección respiratoria aguda");

        ClinicalRecord.Receta receta = new ClinicalRecord.Receta();
        receta.setMedicamento("Amoxicilina 500mg");
        receta.setDosis("1 cada 8 horas");
        receta.setDuracion("7 días");
        receta.setIndicaciones("Tomar con comida");

        clinicalRecordRepository.save(ClinicalRecord.builder()
                .pacienteId(patient1.getId())
                .medicoId(doctor1.getId())
                .citaId(apt3.getId())
                .fecha(LocalDateTime.now().minusDays(5))
                .diagnostico(diag)
                .anamnesis("Paciente refiere tos y fiebre desde hace 3 días")
                .planTratamiento("Antibiótico y reposo")
                .tipoConsulta("Consulta general")
                .recetas(List.of(receta))
                .examenesSolicitados(List.of("Hemograma completo", "PCR"))
                .pacienteNombre(patient1.getFullName())
                .medicoNombre(doctor1.getFullName())
                .build());

        // ─── Lab Results ──────────────────────────────────────────────────────
        LabResult lab1 = labResultRepository.save(LabResult.builder()
                .pacienteId(patient1.getId())
                .medicoSolicitanteId(doctor1.getId())
                .tipoExamen("Hemograma completo")
                .estado(LabStatus.PUBLICADO)
                .prioridad("NORMAL")
                .validado(true)
                .alertaCritica(false)
                .valorResultado("Leucocitos: 8.500/µL")
                .rangoNormal("4.000 - 11.000/µL")
                .pacienteNombre(patient1.getFullName())
                .medicoNombre(doctor1.getFullName())
                .fechaSolicitud(LocalDateTime.now().minusDays(5))
                .fechaPublicacion(LocalDateTime.now().minusDays(2))
                .build());

        LabResult lab2 = labResultRepository.save(LabResult.builder()
                .pacienteId(patient2.getId())
                .medicoSolicitanteId(doctor2.getId())
                .tipoExamen("Troponina I")
                .estado(LabStatus.PUBLICADO)
                .prioridad("URGENTE")
                .validado(true)
                .alertaCritica(true)
                .valorResultado("2.8 ng/mL")
                .rangoNormal("< 0.04 ng/mL")
                .pacienteNombre(patient2.getFullName())
                .medicoNombre(doctor2.getFullName())
                .fechaSolicitud(LocalDateTime.now().minusHours(4))
                .fechaPublicacion(LocalDateTime.now().minusHours(1))
                .build());

        LabResult lab3 = labResultRepository.save(LabResult.builder()
                .pacienteId(patient1.getId())
                .medicoSolicitanteId(doctor1.getId())
                .tipoExamen("PCR")
                .estado(LabStatus.PENDIENTE)
                .prioridad("NORMAL")
                .validado(false)
                .alertaCritica(false)
                .pacienteNombre(patient1.getFullName())
                .medicoNombre(doctor1.getFullName())
                .fechaSolicitud(LocalDateTime.now().minusDays(1))
                .build());

        // ─── PQRS ─────────────────────────────────────────────────────────────
        pqrsRepository.save(Pqrs.builder()
                .pacienteId(patient1.getId())
                .tipo("Queja")
                .asunto("Demora en asignación de cita")
                .descripcion("Llevo 2 semanas esperando mi cita con el especialista")
                .estado(PqrsStatus.NUEVO)
                .prioridad(Priority.ALTA)
                .pacienteNombre(patient1.getFullName())
                .satisfaccion(0)
                .build());

        pqrsRepository.save(Pqrs.builder()
                .pacienteId(patient2.getId())
                .tipo("Solicitud")
                .asunto("Solicitud de historia clínica")
                .descripcion("Necesito copia de mi historia clínica para trámite")
                .estado(PqrsStatus.EN_PROCESO)
                .prioridad(Priority.MEDIA)
                .pacienteNombre(patient2.getFullName())
                .satisfaccion(0)
                .build());

        pqrsRepository.save(Pqrs.builder()
                .pacienteId(patient1.getId())
                .tipo("Petición")
                .asunto("Cambio de médico de cabecera")
                .descripcion("Deseo cambiar mi médico tratante")
                .estado(PqrsStatus.RESUELTO)
                .prioridad(Priority.BAJA)
                .pacienteNombre(patient1.getFullName())
                .satisfaccion(5)
                .fechaResolucion(LocalDateTime.now().minusDays(3))
                .build());

        // ─── Audit Logs ───────────────────────────────────────────────────────
        auditLogRepository.save(AuditLog.builder()
                .usuarioId(admin.getId())
                .userName(admin.getFullName())
                .rol("ADMIN")
                .accion("Sistema inicializado con datos de prueba")
                .entidad("system")
                .entidadId("init")
                .type("create")
                .timestamp(LocalDateTime.now())
                .build());

        auditLogRepository.save(AuditLog.builder()
                .usuarioId(validator.getId())
                .userName(validator.getFullName())
                .rol("VALIDATOR")
                .accion("Póliza aprobada: POL-2024-001")
                .entidad("policies")
                .entidadId(policy1.getId())
                .type("approve")
                .timestamp(LocalDateTime.now().minusHours(2))
                .build());

        auditLogRepository.save(AuditLog.builder()
                .usuarioId(patient1.getId())
                .userName(patient1.getFullName())
                .rol("PATIENT")
                .accion("Nuevo paciente registrado: " + patient1.getEmail())
                .entidad("users")
                .entidadId(patient1.getId())
                .type("create")
                .timestamp(LocalDateTime.now().minusDays(10))
                .build());

        // ─── Notifications ────────────────────────────────────────────────────
        notificationRepository.save(Notification.builder()
                .usuarioDestinoId(patient1.getId())
                .tipo("reminder")
                .titulo("Recordatorio de cita")
                .mensaje("Tienes una cita con el Dr. " + doctor1.getFullName() + " en 2 días")
                .leida(false)
                .canal("APP")
                .fechaEnvio(LocalDateTime.now().minusHours(1))
                .build());

        notificationRepository.save(Notification.builder()
                .usuarioDestinoId(patient1.getId())
                .tipo("result")
                .titulo("Resultado disponible")
                .mensaje("Tu resultado de Hemograma completo ya está disponible")
                .leida(false)
                .canal("APP")
                .fechaEnvio(LocalDateTime.now().minusDays(2))
                .build());

        notificationRepository.save(Notification.builder()
                .usuarioDestinoId(patient2.getId())
                .tipo("alert")
                .titulo("Alerta crítica en laboratorio")
                .mensaje("Se detectaron valores críticos en tu examen de Troponina I")
                .leida(false)
                .canal("APP")
                .fechaEnvio(LocalDateTime.now().minusHours(1))
                .build());

        log.info("Demo data seeded successfully. Roles: admin, doctor, coordinator, validator, lab, support, patient");
        log.info("Credentials: admin@medipass.com/admin123, doctor@medipass.com/doctor123, etc.");
    }

    private User save(User u) {
        return userRepository.save(u);
    }

    private User user(String nombres, String apellidos, String documento, String email,
                      String password, Role role, UserStatus estado, String especialidad) {
        return User.builder()
                .nombres(nombres)
                .apellidos(apellidos)
                .documento(documento)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .role(role)
                .estado(estado)
                .especialidad(especialidad)
                .build();
    }
}
