package com.medipass.controller;

import com.medipass.dto.ApiResponse;
import com.medipass.dto.lab.*;
import com.medipass.service.LabService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab")
@Tag(name = "Laboratorio", description = "Portal del laboratorio")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('LAB', 'ADMIN')")
public class LabController {

    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadísticas del laboratorio")
    public ResponseEntity<ApiResponse<LabStatsDto>> getStats() {
        return ResponseEntity.ok(ApiResponse.ok("OK", labService.getStats()));
    }

    @GetMapping("/orders/pending")
    @Operation(summary = "Órdenes de laboratorio pendientes")
    public ResponseEntity<ApiResponse<List<LabOrderDto>>> getPendingOrders() {
        return ResponseEntity.ok(ApiResponse.ok("OK", labService.getPendingOrders()));
    }

    @GetMapping("/alerts/critical")
    @Operation(summary = "Alertas críticas activas")
    public ResponseEntity<ApiResponse<List<CriticalAlertDto>>> getCriticalAlerts() {
        return ResponseEntity.ok(ApiResponse.ok("OK", labService.getCriticalAlerts()));
    }

    @GetMapping("/results/published")
    @Operation(summary = "Resultados publicados")
    public ResponseEntity<ApiResponse<List<PublishedResultDto>>> getPublishedResults() {
        return ResponseEntity.ok(ApiResponse.ok("OK", labService.getPublishedResults()));
    }

    @GetMapping("/results/{id}")
    @Operation(summary = "Detalle completo de un resultado de laboratorio")
    @PreAuthorize("hasAnyRole('LAB', 'ADMIN', 'DOCTOR', 'PATIENT')")
    public ResponseEntity<ApiResponse<LabResultDetailDto>> getResultDetail(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok("OK", labService.getResultDetail(id)));
    }

    @PostMapping("/notify-doctor/{alertId}")
    @Operation(summary = "Notificar médico por alerta crítica")
    public ResponseEntity<ApiResponse<String>> notifyDoctor(@PathVariable String alertId) {
        return ResponseEntity.ok(ApiResponse.ok(labService.notifyDoctor(alertId), null));
    }

    @PostMapping("/notify-patient/{alertId}")
    @Operation(summary = "Notificar paciente por alerta crítica")
    public ResponseEntity<ApiResponse<String>> notifyPatient(@PathVariable String alertId) {
        return ResponseEntity.ok(ApiResponse.ok(labService.notifyPatient(alertId), null));
    }

    @PostMapping("/orders/{orderId}/submit")
    @Operation(summary = "Ingresar resultado de laboratorio directamente en el sistema")
    public ResponseEntity<ApiResponse<String>> submitResult(
            @PathVariable String orderId,
            @RequestBody SubmitResultRequest req) {
        return ResponseEntity.ok(ApiResponse.ok(labService.submitResult(orderId, req), null));
    }
}
