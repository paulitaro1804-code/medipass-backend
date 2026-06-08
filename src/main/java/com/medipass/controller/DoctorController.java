package com.medipass.controller;

import com.medipass.dto.ApiResponse;
import com.medipass.dto.doctor.*;
import com.medipass.dto.lab.LabResultDetailDto;
import com.medipass.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@Tag(name = "Medico", description = "Portal del medico")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadisticas del medico autenticado")
    public ResponseEntity<ApiResponse<DoctorStatsDto>> getStats() {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getStats()));
    }

    @GetMapping("/schedule/today")
    @Operation(summary = "Agenda del dia")
    public ResponseEntity<ApiResponse<List<PatientScheduleEntryDto>>> getTodaySchedule() {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getTodaySchedule()));
    }

    @PutMapping("/appointments/{id}/status")
    @Operation(summary = "Actualizar estado de una cita")
    public ResponseEntity<ApiResponse<Void>> updateAppointmentStatus(
            @PathVariable String id, @RequestParam String status) {
        doctorService.updateAppointmentStatus(id, status);
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado", null));
    }

    @PostMapping("/records")
    @Operation(summary = "Crear historia clinica")
    public ResponseEntity<ApiResponse<FullClinicalRecordDto>> createRecord(
            @RequestBody CreateClinicalRecordRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Historia clinica guardada", doctorService.createClinicalRecord(req)));
    }

    @GetMapping("/records/recent")
    @Operation(summary = "Historias clinicas recientes")
    public ResponseEntity<ApiResponse<List<ClinicalRecordDto>>> getRecentRecords() {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getRecentRecords()));
    }

    @GetMapping("/records/{id}")
    @Operation(summary = "Obtener historia clinica por ID")
    public ResponseEntity<ApiResponse<FullClinicalRecordDto>> getRecord(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getRecord(id)));
    }

    @GetMapping("/records")
    @Operation(summary = "Todas las historias clinicas del medico")
    public ResponseEntity<ApiResponse<List<ClinicalRecordDto>>> getAllRecords() {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getAllRecords()));
    }

    @GetMapping("/records/full")
    @Operation(summary = "Todas las historias clinicas completas del medico")
    public ResponseEntity<ApiResponse<List<FullClinicalRecordDto>>> getAllFullRecords() {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getAllFullRecords()));
    }

    @GetMapping("/patients/{pacienteId}/records")
    @Operation(summary = "Historias clinicas de un paciente")
    public ResponseEntity<ApiResponse<List<FullClinicalRecordDto>>> getPatientRecords(@PathVariable String pacienteId) {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getPatientRecords(pacienteId)));
    }

    @PostMapping("/lab-orders")
    @Operation(summary = "Crear orden de laboratorio")
    public ResponseEntity<ApiResponse<PendingLabOrderDto>> createLabOrder(
            @RequestBody CreateLabOrderRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Orden creada", doctorService.createLabOrder(req)));
    }

    @GetMapping("/lab-orders/pending")
    @Operation(summary = "Ordenes de laboratorio pendientes")
    public ResponseEntity<ApiResponse<List<PendingLabOrderDto>>> getPendingLabOrders() {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getPendingLabOrders()));
    }

    @GetMapping("/lab-orders")
    @Operation(summary = "Todas las ordenes de laboratorio del medico")
    public ResponseEntity<ApiResponse<List<PendingLabOrderDto>>> getAllLabOrders() {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getAllLabOrders()));
    }

    @GetMapping("/lab-orders/{id}")
    @Operation(summary = "Detalle del resultado de laboratorio para un medico")
    public ResponseEntity<ApiResponse<LabResultDetailDto>> getLabOrderResult(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getLabOrderResult(id)));
    }

    @GetMapping("/patients")
    @Operation(summary = "Lista de pacientes del medico")
    public ResponseEntity<ApiResponse<List<PatientListDto>>> getPatients() {
        return ResponseEntity.ok(ApiResponse.ok("OK", doctorService.getPatients()));
    }
}
