package com.medipass.service;

import com.medipass.dto.doctor.*;
import com.medipass.dto.lab.LabResultDetailDto;
import com.medipass.entity.*;
import com.medipass.enums.*;
import com.medipass.exception.ResourceNotFoundException;
import com.medipass.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DoctorService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicalRecordRepository clinicalRecordRepository;
    private final LabResultRepository labResultRepository;

    public DoctorService(UserRepository userRepository, AppointmentRepository appointmentRepository,
                         ClinicalRecordRepository clinicalRecordRepository, LabResultRepository labResultRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.labResultRepository = labResultRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    // ─── Stats ────────────────────────────────────────────────────────────────

    public DoctorStatsDto getStats() {
        User doctor = getCurrentUser();
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay   = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        List<Appointment> todayAppts = appointmentRepository
                .findByMedicoIdOrderByFechaHoraAsc(doctor.getId())
                .stream()
                .filter(a -> a.getFechaHora() != null
                        && a.getFechaHora().isAfter(startOfDay)
                        && a.getFechaHora().isBefore(endOfDay))
                .toList();

        long attended = todayAppts.stream()
                .filter(a -> a.getEstado() == AppointmentStatus.COMPLETED).count();
        long waiting = todayAppts.stream()
                .filter(a -> a.getEstado() == AppointmentStatus.WAITING
                        || a.getEstado() == AppointmentStatus.IN_PROGRESS).count();

        long recordsToday = clinicalRecordRepository.findByMedicoIdOrderByFechaDesc(doctor.getId())
                .stream()
                .filter(r -> r.getFecha() != null && r.getFecha().isAfter(startOfDay))
                .count();

        long pendingLab = labResultRepository.findByEstado(LabStatus.PENDIENTE)
                .stream()
                .filter(l -> doctor.getId().equals(l.getMedicoSolicitanteId()))
                .count();

        return DoctorStatsDto.builder()
                .patientsToday(todayAppts.size())
                .patientsAttended(attended)
                .waitingPatients(waiting)
                .recordsToday(recordsToday)
                .pendingLabResults(pendingLab)
                .build();
    }

    // ─── Schedule ─────────────────────────────────────────────────────────────

    public List<PatientScheduleEntryDto> getTodaySchedule() {
        User doctor = getCurrentUser();
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime endOfDay   = LocalDateTime.now().withHour(23).withMinute(59);

        return appointmentRepository.findByMedicoIdOrderByFechaHoraAsc(doctor.getId())
                .stream()
                .filter(a -> a.getFechaHora() != null
                        && a.getFechaHora().isAfter(startOfDay)
                        && a.getFechaHora().isBefore(endOfDay))
                .map(a -> PatientScheduleEntryDto.builder()
                        .id(a.getId())
                        .pacienteId(a.getPacienteId() != null ? a.getPacienteId() : "")
                        .time(a.getFechaHora().format(timeFmt))
                        .name(a.getPacienteNombre() != null ? a.getPacienteNombre() : "Paciente")
                        .age(a.getPacienteEdad() > 0 ? a.getPacienteEdad() : 35)
                        .reason(a.getMotivoConsulta() != null ? a.getMotivoConsulta() : "Consulta general")
                        .status(mapStatus(a.getEstado()))
                        .hasHistory(!clinicalRecordRepository
                                .findByPacienteIdOrderByFechaDesc(a.getPacienteId() != null ? a.getPacienteId() : "")
                                .isEmpty())
                        .build())
                .toList();
    }

    public void updateAppointmentStatus(String id, String status) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada: " + id));
        AppointmentStatus newStatus = switch (status.toUpperCase()) {
            case "IN_PROGRESS" -> AppointmentStatus.IN_PROGRESS;
            case "COMPLETED"   -> AppointmentStatus.COMPLETED;
            case "CANCELLED"   -> AppointmentStatus.CANCELLED;
            case "WAITING"     -> AppointmentStatus.WAITING;
            case "CONFIRMED"   -> AppointmentStatus.CONFIRMED;
            default            -> AppointmentStatus.IN_PROGRESS;
        };
        appt.setEstado(newStatus);
        appointmentRepository.save(appt);
    }

    // ─── Clinical Records ─────────────────────────────────────────────────────

    public FullClinicalRecordDto createClinicalRecord(CreateClinicalRecordRequest req) {
        User doctor = getCurrentUser();

        ClinicalRecord.Diagnostico diag = new ClinicalRecord.Diagnostico();
        diag.setCodigo(req.getDiagnosticoCodigo() != null ? req.getDiagnosticoCodigo() : "");
        diag.setDescripcion(req.getDiagnosticoDescripcion());

        List<ClinicalRecord.Receta> recetas = null;
        if (req.getRecetas() != null && !req.getRecetas().isEmpty()) {
            recetas = req.getRecetas().stream().map(ri -> {
                ClinicalRecord.Receta r = new ClinicalRecord.Receta();
                r.setMedicamento(ri.getMedicamento());
                r.setDosis(ri.getDosis());
                r.setDuracion(ri.getDuracion());
                r.setIndicaciones(ri.getIndicaciones());
                return r;
            }).toList();
        }

        ClinicalRecord record = ClinicalRecord.builder()
                .pacienteId(req.getPacienteId())
                .medicoId(doctor.getId())
                .citaId(req.getCitaId())
                .fecha(LocalDateTime.now())
                .diagnostico(diag)
                .anamnesis(req.getAnamnesis())
                .planTratamiento(req.getPlanTratamiento())
                .evolucion(req.getEvolucion())
                .tipoConsulta(req.getTipoConsulta())
                .recetas(recetas)
                .examenesSolicitados(req.getExamenesSolicitados())
                .pacienteNombre(req.getPacienteNombre())
                .medicoNombre(doctor.getFullName())
                .build();

        clinicalRecordRepository.save(record);

        // Create a LabResult for each requested exam
        if (req.getExamenesSolicitados() != null) {
            for (String exam : req.getExamenesSolicitados()) {
                LabResult lab = LabResult.builder()
                        .pacienteId(req.getPacienteId())
                        .pacienteNombre(req.getPacienteNombre())
                        .medicoSolicitanteId(doctor.getId())
                        .medicoNombre(doctor.getFullName())
                        .tipoExamen(exam)
                        .estado(LabStatus.PENDIENTE)
                        .prioridad("normal")
                        .fechaSolicitud(LocalDateTime.now())
                        .validado(false)
                        .alertaCritica(false)
                        .build();
                labResultRepository.save(lab);
            }
        }

        // Mark appointment as COMPLETED
        if (req.getCitaId() != null && !req.getCitaId().isBlank()) {
            appointmentRepository.findById(req.getCitaId()).ifPresent(a -> {
                a.setEstado(AppointmentStatus.COMPLETED);
                appointmentRepository.save(a);
            });
        }

        return mapToFullDto(record);
    }

    public FullClinicalRecordDto getRecord(String id) {
        ClinicalRecord r = clinicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia clínica no encontrada: " + id));
        return mapToFullDto(r);
    }

    public List<FullClinicalRecordDto> getPatientRecords(String pacienteId) {
        return clinicalRecordRepository.findByPacienteIdOrderByFechaDesc(pacienteId)
                .stream()
                .map(this::mapToFullDto)
                .toList();
    }

    public List<ClinicalRecordDto> getRecentRecords() {
        User doctor = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return clinicalRecordRepository.findByMedicoIdOrderByFechaDesc(doctor.getId())
                .stream()
                .limit(10)
                .map(r -> ClinicalRecordDto.builder()
                        .id(r.getId())
                        .patient(r.getPacienteNombre() != null ? r.getPacienteNombre() : "Paciente")
                        .date(r.getFecha() != null ? r.getFecha().format(fmt) : "—")
                        .diagnosis(r.getDiagnostico() != null ? r.getDiagnostico().getDescripcion() : "Sin diagnóstico")
                        .diagnosisCode(r.getDiagnostico() != null ? r.getDiagnostico().getCodigo() : "")
                        .type(r.getTipoConsulta() != null ? r.getTipoConsulta() : "Consulta general")
                        .planTratamiento(r.getPlanTratamiento())
                        .build())
                .toList();
    }

    public List<ClinicalRecordDto> getAllRecords() {
        User doctor = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return clinicalRecordRepository.findByMedicoIdOrderByFechaDesc(doctor.getId()).stream()
                .map(r -> ClinicalRecordDto.builder()
                        .id(r.getId())
                        .patient(r.getPacienteNombre() != null ? r.getPacienteNombre() : "Paciente")
                        .date(r.getFecha() != null ? r.getFecha().format(fmt) : "—")
                        .diagnosis(r.getDiagnostico() != null ? r.getDiagnostico().getDescripcion() : "—")
                        .diagnosisCode(r.getDiagnostico() != null ? r.getDiagnostico().getCodigo() : "")
                        .type(r.getTipoConsulta() != null ? r.getTipoConsulta() : "Consulta")
                        .planTratamiento(r.getPlanTratamiento())
                        .build())
                .toList();
    }

    public List<FullClinicalRecordDto> getAllFullRecords() {
        User doctor = getCurrentUser();
        return clinicalRecordRepository.findByMedicoIdOrderByFechaDesc(doctor.getId()).stream()
                .map(this::mapToFullDto)
                .toList();
    }

    // ─── Lab Orders ───────────────────────────────────────────────────────────

    public PendingLabOrderDto createLabOrder(CreateLabOrderRequest req) {
        User doctor = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LabResult lab = LabResult.builder()
                .pacienteId(req.getPacienteId())
                .pacienteNombre(req.getPacienteNombre())
                .medicoSolicitanteId(doctor.getId())
                .medicoNombre(doctor.getFullName())
                .tipoExamen(req.getTipoExamen())
                .estado(LabStatus.PENDIENTE)
                .prioridad(req.getPrioridad() != null ? req.getPrioridad() : "normal")
                .fechaSolicitud(LocalDateTime.now())
                .validado(false)
                .alertaCritica(false)
                .build();

        labResultRepository.save(lab);

        return PendingLabOrderDto.builder()
                .id(lab.getId())
                .patient(lab.getPacienteNombre())
                .exam(lab.getTipoExamen())
                .requested(lab.getFechaSolicitud().format(fmt))
                .status("PENDIENTE")
                .build();
    }

    public List<PendingLabOrderDto> getPendingLabOrders() {
        User doctor = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return labResultRepository.findByEstado(LabStatus.PENDIENTE)
                .stream()
                .filter(l -> doctor.getId().equals(l.getMedicoSolicitanteId()))
                .map(l -> PendingLabOrderDto.builder()
                        .id(l.getId())
                        .patient(l.getPacienteNombre() != null ? l.getPacienteNombre() : "Paciente")
                        .exam(l.getTipoExamen())
                        .requested(l.getFechaSolicitud() != null ? l.getFechaSolicitud().format(fmt) : "—")
                        .status(l.getEstado() != null ? l.getEstado().name() : "PENDIENTE")
                        .build())
                .toList();
    }

    public List<PendingLabOrderDto> getAllLabOrders() {
        User doctor = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return labResultRepository.findAll().stream()
                .filter(l -> doctor.getId().equals(l.getMedicoSolicitanteId()))
                .map(l -> PendingLabOrderDto.builder()
                        .id(l.getId())
                        .patient(l.getPacienteNombre() != null ? l.getPacienteNombre() : "Paciente")
                        .exam(l.getTipoExamen())
                        .requested(l.getFechaSolicitud() != null ? l.getFechaSolicitud().format(fmt) : "—")
                        .status(l.getEstado() != null ? l.getEstado().name() : "PENDIENTE")
                        .build())
                .toList();
    }

    public LabResultDetailDto getLabOrderResult(String id) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LabResult lab = labResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resultado de laboratorio no encontrado: " + id));
        java.util.List<LabResultDetailDto.ResultItemDto> items = new java.util.ArrayList<>();
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

    // ─── Patients ─────────────────────────────────────────────────────────────

    public List<PatientListDto> getPatients() {
        User doctor = getCurrentUser();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Dedup key: use pacienteId when present, otherwise fall back to lowercase name.
        // This ensures patients created by the coordinator without a linked account still appear.
        return appointmentRepository.findByMedicoIdOrderByFechaHoraAsc(doctor.getId()).stream()
                .filter(a -> a.getPacienteNombre() != null && !a.getPacienteNombre().isBlank())
                .collect(java.util.stream.Collectors.toMap(
                        a -> a.getPacienteId() != null ? a.getPacienteId()
                                                       : a.getPacienteNombre().toLowerCase().trim(),
                        a -> a,
                        (a1, a2) -> a1.getFechaHora() != null && a2.getFechaHora() != null
                                && a1.getFechaHora().isAfter(a2.getFechaHora()) ? a1 : a2))
                .values().stream()
                .map(a -> PatientListDto.builder()
                        .id(a.getPacienteId() != null ? a.getPacienteId() : "")
                        .name(a.getPacienteNombre())
                        .lastVisit(a.getFechaHora() != null ? a.getFechaHora().format(fmt) : "—")
                        .specialty(a.getEspecialidad() != null ? a.getEspecialidad() : "General")
                        .status(mapStatus(a.getEstado()))
                        .age(a.getPacienteEdad() > 0 ? a.getPacienteEdad() : 0)
                        .build())
                .toList();
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private String mapStatus(AppointmentStatus status) {
        if (status == null) return "scheduled";
        return switch (status) {
            case IN_PROGRESS                    -> "in-progress";
            case WAITING                        -> "waiting";
            case SCHEDULED, CONFIRMED, PENDING  -> "scheduled";
            case COMPLETED                      -> "completed";
            case CANCELLED                      -> "cancelled";
        };
    }

    private FullClinicalRecordDto mapToFullDto(ClinicalRecord r) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        List<FullClinicalRecordDto.RecetaDto> recetaDtos = null;
        if (r.getRecetas() != null) {
            recetaDtos = r.getRecetas().stream().map(rec -> {
                FullClinicalRecordDto.RecetaDto rd = new FullClinicalRecordDto.RecetaDto();
                rd.setMedicamento(rec.getMedicamento());
                rd.setDosis(rec.getDosis());
                rd.setDuracion(rec.getDuracion());
                rd.setIndicaciones(rec.getIndicaciones());
                return rd;
            }).toList();
        }

        return FullClinicalRecordDto.builder()
                .id(r.getId())
                .pacienteId(r.getPacienteId())
                .pacienteNombre(r.getPacienteNombre() != null ? r.getPacienteNombre() : "Paciente")
                .medicoNombre(r.getMedicoNombre())
                .fecha(r.getFecha() != null ? r.getFecha().format(fmt) : "—")
                .tipoConsulta(r.getTipoConsulta())
                .anamnesis(r.getAnamnesis())
                .diagnosticoCodigo(r.getDiagnostico() != null ? r.getDiagnostico().getCodigo() : "")
                .diagnosticoDescripcion(r.getDiagnostico() != null ? r.getDiagnostico().getDescripcion() : "")
                .planTratamiento(r.getPlanTratamiento())
                .evolucion(r.getEvolucion())
                .recetas(recetaDtos)
                .examenesSolicitados(r.getExamenesSolicitados())
                .build();
    }
}
