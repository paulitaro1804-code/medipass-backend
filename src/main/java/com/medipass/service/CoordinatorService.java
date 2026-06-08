package com.medipass.service;

import com.medipass.dto.coordinator.*;
import com.medipass.entity.*;
import com.medipass.enums.*;
import com.medipass.exception.ResourceNotFoundException;
import com.medipass.repository.*;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CoordinatorService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public CoordinatorService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public CoordinatorStatsDto getStats() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime endOfDay   = LocalDateTime.now().withHour(23).withMinute(59);

        List<Appointment> todayAppts = appointmentRepository.findByFechaHoraBetween(startOfDay, endOfDay);
        long completed    = todayAppts.stream().filter(a -> a.getEstado() == AppointmentStatus.COMPLETED).count();
        long waiting      = todayAppts.stream().filter(a -> a.getEstado() == AppointmentStatus.WAITING).count();
        long priority     = todayAppts.stream().filter(a -> "alta".equalsIgnoreCase(a.getPrioridad())).count();

        List<User> doctors   = userRepository.findByRoleAndEstado(Role.DOCTOR, UserStatus.ACTIVO);
        long waitlist        = appointmentRepository.findByEnListaEsperaTrue().size();
        long urgentWaitlist  = appointmentRepository.findByEnListaEsperaTrue().stream()
                                .filter(a -> "alta".equalsIgnoreCase(a.getPrioridad())).count();

        return CoordinatorStatsDto.builder()
                .todayAppointments(todayAppts.size())
                .completedAppointments(completed)
                .waitingPatients(waiting)
                .priorityWaiting(priority)
                .activeDoctors(doctors.size())
                .totalDoctors(userRepository.countByRole(Role.DOCTOR))
                .waitlistCount(waitlist)
                .urgentWaitlist(urgentWaitlist)
                .build();
    }

    public List<ScheduleEntryDto> getTodaySchedule() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime endOfDay   = LocalDateTime.now().withHour(23).withMinute(59);
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        return appointmentRepository.findByFechaHoraBetween(startOfDay, endOfDay)
                .stream()
                .map(a -> ScheduleEntryDto.builder()
                        .id(a.getId())
                        .time(a.getFechaHora() != null ? a.getFechaHora().format(timeFmt) : "--")
                        .patient(a.getPacienteNombre() != null ? a.getPacienteNombre() : "Paciente")
                        .doctor(a.getMedicoNombre() != null ? a.getMedicoNombre() : "Sin asignar")
                        .specialty(a.getEspecialidad())
                        .status(mapStatus(a.getEstado()))
                        .build())
                .toList();
    }

    public List<DoctorStatusDto> getDoctors() {
        List<User> doctors = userRepository.findByRole(Role.DOCTOR);
        return doctors.stream()
                .map((User d) -> {
                    int patients = (int) appointmentRepository.findByMedicoId(d.getId()).stream()
                            .filter(a -> a.getFechaHora() != null
                                    && a.getFechaHora().isAfter(LocalDateTime.now().withHour(0)))
                            .count();
                    return DoctorStatusDto.builder()
                            .id(d.getId())
                            .name(d.getFullName())
                            .specialty(d.getEspecialidad() != null ? d.getEspecialidad() : "Medicina General")
                            .available(d.getEstado() == UserStatus.ACTIVO)
                            .patients(patients)
                            .build();
                })
                .toList();
    }

    public List<WaitlistEntryDto> getPriorityAppointments() {
        return appointmentRepository.findByPrioridadAndEnListaEsperaFalse("alta")
                .stream()
                .filter(a -> a.getEstado() != AppointmentStatus.COMPLETED
                          && a.getEstado() != AppointmentStatus.CANCELLED)
                .map(a -> WaitlistEntryDto.builder()
                        .id(a.getId())
                        .patient(a.getPacienteNombre() != null ? a.getPacienteNombre() : "Paciente")
                        .priority("alta")
                        .specialty(a.getEspecialidad())
                        .waitTime(a.getMedicoNombre() != null ? a.getMedicoNombre() : "Sin médico")
                        .plan(a.getPlanPaciente() != null ? a.getPlanPaciente() : "Plan Basico")
                        .build())
                .toList();
    }

    public List<WaitlistEntryDto> getWaitlist() {
        return appointmentRepository.findByEnListaEsperaTrue()
                .stream()
                .map(a -> WaitlistEntryDto.builder()
                        .id(a.getId())
                        .patient(a.getPacienteNombre() != null ? a.getPacienteNombre() : "Paciente")
                        .priority(a.getPrioridad() != null ? a.getPrioridad().toLowerCase() : "normal")
                        .specialty(a.getEspecialidad())
                        .waitTime(a.getTiempoEspera() != null ? a.getTiempoEspera() : "En espera")
                        .plan(a.getPlanPaciente() != null ? a.getPlanPaciente() : "Plan Basico")
                        .build())
                .toList();
    }

    public String assignAppointment(String waitlistId, String medicoId, String prioridad) {
        Appointment appt = appointmentRepository.findById(waitlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        appt.setEnListaEspera(false);
        appt.setEstado(AppointmentStatus.CONFIRMED);
        if (medicoId != null && !medicoId.isBlank()) {
            userRepository.findById(medicoId).ifPresent(doc -> {
                appt.setMedicoId(doc.getId());
                appt.setMedicoNombre(doc.getFullName());
            });
        }
        if (prioridad != null && !prioridad.isBlank()) {
            appt.setPrioridad(prioridad);
        }
        appointmentRepository.save(appt);
        return "Cita asignada correctamente";
    }

    public ScheduleEntryDto createAppointment(CreateAppointmentRequest req) {
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        // Conflict check: same doctor cannot have appointment within +-45 minutes
        if (req.getMedicoId() != null && !req.getMedicoId().isBlank() && req.getFechaHora() != null) {
            LocalDateTime from = req.getFechaHora().minusMinutes(45);
            LocalDateTime to   = req.getFechaHora().plusMinutes(45);
            boolean conflict = appointmentRepository
                    .findByMedicoIdAndFechaHoraBetween(req.getMedicoId(), from, to)
                    .stream()
                    .anyMatch(a -> a.getEstado() != AppointmentStatus.CANCELLED);
            if (conflict) {
                throw new IllegalArgumentException(
                    "El medico ya tiene una cita asignada en ese horario (margen de 45 minutos). " +
                    "Por favor elige otro horario u otro medico.");
            }
        }

        String medicoNombre = req.getMedicoNombre();
        if (req.getMedicoId() != null && !req.getMedicoId().isBlank()) {
            medicoNombre = userRepository.findById(req.getMedicoId())
                    .map(User::getFullName).orElse(medicoNombre);
        }
        Appointment appt = Appointment.builder()
                .pacienteId(req.getPacienteId())
                .pacienteNombre(req.getPacienteNombre())
                .medicoId(req.getMedicoId())
                .medicoNombre(medicoNombre)
                .especialidad(req.getEspecialidad())
                .fechaHora(req.getFechaHora())
                .estado(AppointmentStatus.SCHEDULED)
                .modalidad(req.getModalidad() != null ? req.getModalidad() : "presencial")
                .motivoConsulta(req.getMotivoConsulta())
                .prioridad(req.getPrioridad() != null ? req.getPrioridad() : "normal")
                .planPaciente(req.getPlanPaciente())
                .enListaEspera(false)
                .build();
        appt = appointmentRepository.save(appt);
        return ScheduleEntryDto.builder()
                .id(appt.getId())
                .time(appt.getFechaHora().format(timeFmt))
                .patient(appt.getPacienteNombre())
                .doctor(appt.getMedicoNombre() != null ? appt.getMedicoNombre() : "Sin asignar")
                .specialty(appt.getEspecialidad())
                .status(mapStatus(appt.getEstado()))
                .build();
    }

    /** Returns all active registered patients. */
    public List<PatientOptionDto> getPatients() {
        return userRepository.findByRoleAndEstado(Role.PATIENT, UserStatus.ACTIVO).stream()
                .map(u -> PatientOptionDto.builder()
                        .id(u.getId())
                        .name(u.getFullName())
                        .email(u.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    /** Returns a random available doctor for a given specialty. */
    public DoctorStatusDto assignRandomDoctor(String especialidad) {
        List<User> candidates = userRepository.findByRole(Role.DOCTOR).stream()
                .filter(d -> d.getEstado() == UserStatus.ACTIVO)
                .filter(d -> especialidad == null || especialidad.isBlank()
                        || (d.getEspecialidad() != null
                            && d.getEspecialidad().equalsIgnoreCase(especialidad)))
                .toList();
        if (candidates.isEmpty()) {
            candidates = userRepository.findByRoleAndEstado(Role.DOCTOR, UserStatus.ACTIVO);
        }
        if (candidates.isEmpty()) throw new ResourceNotFoundException("No hay medicos disponibles");
        List<User> shuffled = new ArrayList<>(candidates);
        Collections.shuffle(shuffled);
        User chosen = shuffled.get(0);
        return DoctorStatusDto.builder()
                .id(chosen.getId())
                .name(chosen.getFullName())
                .specialty(chosen.getEspecialidad() != null ? chosen.getEspecialidad() : "Medicina General")
                .available(true)
                .patients(0)
                .build();
    }

    /** Updates the assigned doctor on an existing appointment (with conflict check). */
    public ScheduleEntryDto updateAppointmentDoctor(String apptId, String medicoId) {
        Appointment appt = appointmentRepository.findById(apptId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        if (medicoId != null && !medicoId.isBlank() && appt.getFechaHora() != null) {
            LocalDateTime from = appt.getFechaHora().minusMinutes(45);
            LocalDateTime to   = appt.getFechaHora().plusMinutes(45);
            boolean conflict = appointmentRepository
                    .findByMedicoIdAndFechaHoraBetween(medicoId, from, to)
                    .stream()
                    .filter(a -> !a.getId().equals(apptId))
                    .anyMatch(a -> a.getEstado() != AppointmentStatus.CANCELLED);
            if (conflict) {
                throw new IllegalArgumentException(
                    "El medico ya tiene una cita en ese horario. Elige otro medico.");
            }
            userRepository.findById(medicoId).ifPresent(doc -> {
                appt.setMedicoId(doc.getId());
                appt.setMedicoNombre(doc.getFullName());
            });
        } else {
            appt.setMedicoId(null);
            appt.setMedicoNombre(null);
        }
        appointmentRepository.save(appt);
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        return ScheduleEntryDto.builder()
                .id(appt.getId())
                .time(appt.getFechaHora() != null ? appt.getFechaHora().format(timeFmt) : "")
                .patient(appt.getPacienteNombre())
                .doctor(appt.getMedicoNombre() != null ? appt.getMedicoNombre() : "Sin asignar")
                .specialty(appt.getEspecialidad())
                .status(mapStatus(appt.getEstado()))
                .build();
    }

    public String updateAppointmentStatus(String id, String status) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        AppointmentStatus newStatus = switch (status.toLowerCase()) {
            case "in-progress", "in_progress" -> AppointmentStatus.IN_PROGRESS;
            case "completed"                  -> AppointmentStatus.COMPLETED;
            case "cancelled"                  -> AppointmentStatus.CANCELLED;
            case "waiting"                    -> AppointmentStatus.WAITING;
            case "confirmed"                  -> AppointmentStatus.CONFIRMED;
            default                           -> AppointmentStatus.SCHEDULED;
        };
        appt.setEstado(newStatus);
        appointmentRepository.save(appt);
        return "Estado actualizado correctamente";
    }

    private String mapStatus(AppointmentStatus status) {
        if (status == null) return "scheduled";
        return switch (status) {
            case COMPLETED   -> "completed";
            case IN_PROGRESS -> "in-progress";
            case WAITING     -> "waiting";
            case CANCELLED   -> "cancelled";
            case CONFIRMED   -> "confirmed";
            default          -> "scheduled";
        };
    }
}