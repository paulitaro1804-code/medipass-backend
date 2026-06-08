package com.medipass.controller;

import com.medipass.dto.ApiResponse;
import com.medipass.dto.coordinator.*;
import com.medipass.service.CoordinatorService;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinator")
@Tag(name = "Coordinador Médico", description = "Portal del coordinador médico")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('COORDINATOR', 'ADMIN')")
public class CoordinatorController {

    private final CoordinatorService coordinatorService;

    public CoordinatorController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadísticas del coordinador")
    public ResponseEntity<ApiResponse<CoordinatorStatsDto>> getStats() {
        return ResponseEntity.ok(ApiResponse.ok("OK", coordinatorService.getStats()));
    }

    @GetMapping("/schedule/today")
    @Operation(summary = "Agenda del día")
    public ResponseEntity<ApiResponse<List<ScheduleEntryDto>>> getTodaySchedule() {
        return ResponseEntity.ok(ApiResponse.ok("OK", coordinatorService.getTodaySchedule()));
    }

    @GetMapping("/doctors")
    @Operation(summary = "Estado de médicos")
    public ResponseEntity<ApiResponse<List<DoctorStatusDto>>> getDoctors() {
        return ResponseEntity.ok(ApiResponse.ok("OK", coordinatorService.getDoctors()));
    }

    @GetMapping("/patients")
    @Operation(summary = "Pacientes activos registrados")
    public ResponseEntity<ApiResponse<List<PatientOptionDto>>> getPatients() {
        return ResponseEntity.ok(ApiResponse.ok("OK", coordinatorService.getPatients()));
    }

    @GetMapping("/waitlist")
    @Operation(summary = "Lista de espera")
    public ResponseEntity<ApiResponse<List<WaitlistEntryDto>>> getWaitlist() {
        return ResponseEntity.ok(ApiResponse.ok("OK", coordinatorService.getWaitlist()));
    }

    @GetMapping("/priority")
    @Operation(summary = "Citas de prioridad alta confirmadas")
    public ResponseEntity<ApiResponse<List<WaitlistEntryDto>>> getPriorityAppointments() {
        return ResponseEntity.ok(ApiResponse.ok("OK", coordinatorService.getPriorityAppointments()));
    }

    @PostMapping("/waitlist/{id}/assign")
    @Operation(summary = "Asignar paciente de lista de espera")
    public ResponseEntity<ApiResponse<String>> assignAppointment(
            @PathVariable String id,
            @RequestParam(required = false) String medicoId,
            @RequestParam(required = false) String prioridad) {
        return ResponseEntity.ok(ApiResponse.ok(coordinatorService.assignAppointment(id, medicoId, prioridad), null));
    }

    @PostMapping("/appointments")
    @Operation(summary = "Crear nueva cita")
    public ResponseEntity<ApiResponse<ScheduleEntryDto>> createAppointment(
            @Valid @RequestBody CreateAppointmentRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Cita creada correctamente", coordinatorService.createAppointment(request)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/appointments/{id}/status")
    @Operation(summary = "Actualizar estado de una cita")
    public ResponseEntity<ApiResponse<String>> updateStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.ok(coordinatorService.updateAppointmentStatus(id, status), null));
    }

    @GetMapping("/appointments/random-doctor")
    @Operation(summary = "Asignar medico aleatorio disponible")
    public ResponseEntity<ApiResponse<DoctorStatusDto>> getRandomDoctor(
            @RequestParam(required = false) String especialidad) {
        return ResponseEntity.ok(ApiResponse.ok("Medico sugerido", coordinatorService.assignRandomDoctor(especialidad)));
    }

    @PutMapping("/appointments/{id}/doctor")
    @Operation(summary = "Cambiar medico asignado a una cita")
    public ResponseEntity<ApiResponse<ScheduleEntryDto>> updateDoctor(
            @PathVariable String id,
            @RequestParam(required = false) String medicoId) {
        return ResponseEntity.ok(ApiResponse.ok("Medico actualizado", coordinatorService.updateAppointmentDoctor(id, medicoId)));
    }
}
