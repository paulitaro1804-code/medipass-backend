package com.medipass.controller;

import com.medipass.dto.ApiResponse;
import com.medipass.dto.lab.LabResultDetailDto;
import com.medipass.dto.patient.*;
import org.springframework.web.bind.annotation.RequestBody;
import com.medipass.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@Tag(name = "Paciente", description = "Portal del paciente")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadísticas del paciente autenticado")
    public ResponseEntity<ApiResponse<PatientStatsDto>> getStats() {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getStats()));
    }

    @GetMapping("/appointments")
    @Operation(summary = "Citas del paciente")
    public ResponseEntity<ApiResponse<List<AppointmentDto>>> getAppointments() {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getAppointments()));
    }

    @GetMapping("/lab-results")
    @Operation(summary = "Resultados de laboratorio del paciente")
    public ResponseEntity<ApiResponse<List<LabResultDto>>> getLabResults() {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getLabResults()));
    }

    @GetMapping("/notifications")
    @Operation(summary = "Notificaciones del paciente")
    public ResponseEntity<ApiResponse<List<NotificationDto>>> getNotifications() {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getNotifications()));
    }

    @GetMapping("/policy")
    @Operation(summary = "Póliza del paciente")
    public ResponseEntity<ApiResponse<PolicyDetailDto>> getPolicy() {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getPolicy()));
    }

    @GetMapping("/records")
    @Operation(summary = "Historial clínico del paciente")
    public ResponseEntity<ApiResponse<List<ClinicalRecordSummaryDto>>> getRecords() {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getClinicalRecords()));
    }

    @GetMapping("/records/{id}")
    @Operation(summary = "Detalle de una historia clinica del paciente")
    public ResponseEntity<ApiResponse<ClinicalRecordDetailDto>> getRecordDetail(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getClinicalRecordDetail(id)));
    }

    @GetMapping("/prescriptions")
    @Operation(summary = "Recetas del paciente")
    public ResponseEntity<ApiResponse<List<PrescriptionDto>>> getPrescriptions() {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getPrescriptions()));
    }

    @PostMapping("/appointments")
    @Operation(summary = "Solicitar nueva cita")
    public ResponseEntity<ApiResponse<String>> createAppointment(@RequestBody CreateAppointmentDto dto) {
        patientService.createAppointment(dto);
        return ResponseEntity.ok(ApiResponse.ok("Cita solicitada", "ok"));
    }

    @PutMapping("/appointments/{id}/cancel")
    @Operation(summary = "Cancelar una cita del paciente")
    public ResponseEntity<ApiResponse<String>> cancelAppointment(@PathVariable String id) {
        patientService.cancelAppointment(id);
        return ResponseEntity.ok(ApiResponse.ok("Cita cancelada", "ok"));
    }

    @PutMapping("/appointments/{id}/reschedule")
    @Operation(summary = "Reprogramar una cita del paciente")
    public ResponseEntity<ApiResponse<String>> rescheduleAppointment(
            @PathVariable String id, @RequestBody RescheduleAppointmentDto dto) {
        patientService.rescheduleAppointment(id, dto.getFechaHora());
        return ResponseEntity.ok(ApiResponse.ok("Cita reprogramada", "ok"));
    }

    @GetMapping("/lab-results/{id}")
    @Operation(summary = "Detalle completo de un resultado de laboratorio del paciente")
    public ResponseEntity<ApiResponse<LabResultDetailDto>> getLabResultDetail(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", patientService.getLabResultDetail(id)));
    }

    @PostMapping("/pqrs/{id}/rate")
    @Operation(summary = "Calificar la atención recibida en una solicitud")
    public ResponseEntity<ApiResponse<String>> ratePqrs(@PathVariable String id, @RequestBody RateRequest request) {
        patientService.ratePqrs(id, request.getRating());
        return ResponseEntity.ok(ApiResponse.ok("Calificación registrada", "ok"));
    }

    @PostMapping("/pqrs")
    @Operation(summary = "Crear una nueva solicitud PQRS")
    public ResponseEntity<ApiResponse<String>> createPqrs(@RequestBody java.util.Map<String, String> body) {
        patientService.createPqrs(body.get("tipo"), body.get("asunto"), body.get("descripcion"));
        return ResponseEntity.ok(ApiResponse.ok("Solicitud enviada", "ok"));
    }
}
