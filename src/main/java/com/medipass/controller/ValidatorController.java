package com.medipass.controller;

import com.medipass.dto.ApiResponse;
import com.medipass.dto.validator.*;
import com.medipass.service.ValidatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validator")
@Tag(name = "Validador de Pólizas", description = "Portal del validador de pólizas")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('VALIDATOR', 'ADMIN')")
public class ValidatorController {

    private final ValidatorService validatorService;

    public ValidatorController(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadísticas del validador")
    public ResponseEntity<ApiResponse<ValidatorStatsDto>> getStats() {
        return ResponseEntity.ok(ApiResponse.ok("OK", validatorService.getStats()));
    }

    @GetMapping("/requests")
    @Operation(summary = "Solicitudes de validación pendientes")
    public ResponseEntity<ApiResponse<List<ValidationRequestDto>>> getRequests() {
        return ResponseEntity.ok(ApiResponse.ok("OK", validatorService.getPendingRequests()));
    }

    @GetMapping("/requests/approved")
    @Operation(summary = "Solicitudes aprobadas")
    public ResponseEntity<ApiResponse<List<ValidationRequestDto>>> getApproved() {
        return ResponseEntity.ok(ApiResponse.ok("OK", validatorService.getApprovedRequests()));
    }

    @GetMapping("/requests/rejected")
    @Operation(summary = "Solicitudes rechazadas")
    public ResponseEntity<ApiResponse<List<ValidationRequestDto>>> getRejected() {
        return ResponseEntity.ok(ApiResponse.ok("OK", validatorService.getRejectedRequests()));
    }

    @GetMapping("/policies/expiring")
    @Operation(summary = "Pólizas próximas a vencer")
    public ResponseEntity<ApiResponse<List<ExpiringPolicyDto>>> getExpiringPolicies() {
        return ResponseEntity.ok(ApiResponse.ok("OK", validatorService.getExpiringPolicies()));
    }

    @PostMapping("/requests/{id}/approve")
    @Operation(summary = "Aprobar solicitud de póliza")
    public ResponseEntity<ApiResponse<String>> approve(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(validatorService.approvePolicy(id), null));
    }

    @PostMapping("/requests/{id}/reject")
    @Operation(summary = "Rechazar solicitud de póliza")
    public ResponseEntity<ApiResponse<String>> reject(
            @PathVariable String id,
            @Valid @RequestBody RejectRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(validatorService.rejectPolicy(id, request.getReason()), null));
    }
}
