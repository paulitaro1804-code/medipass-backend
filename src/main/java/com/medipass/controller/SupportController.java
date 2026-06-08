package com.medipass.controller;

import com.medipass.dto.ApiResponse;
import com.medipass.dto.support.*;
import com.medipass.service.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@Tag(name = "Soporte", description = "Portal de soporte al cliente")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('SUPPORT', 'ADMIN')")
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadísticas de soporte")
    public ResponseEntity<ApiResponse<SupportStatsDto>> getStats() {
        return ResponseEntity.ok(ApiResponse.ok("OK", supportService.getStats()));
    }

    @GetMapping("/tickets/open")
    @Operation(summary = "Tickets abiertos")
    public ResponseEntity<ApiResponse<List<TicketDto>>> getOpenTickets() {
        return ResponseEntity.ok(ApiResponse.ok("OK", supportService.getOpenTickets()));
    }

    @GetMapping("/tickets/resolved")
    @Operation(summary = "Tickets resueltos")
    public ResponseEntity<ApiResponse<List<ResolvedTicketDto>>> getResolvedTickets() {
        return ResponseEntity.ok(ApiResponse.ok("OK", supportService.getResolvedTickets()));
    }

    @GetMapping("/pqrs")
    @Operation(summary = "Todas las PQRS (todos los estados)")
    public ResponseEntity<ApiResponse<List<TicketDto>>> getAllPqrs() {
        return ResponseEntity.ok(ApiResponse.ok("OK", supportService.getAllPqrs()));
    }

    @GetMapping("/metrics")
    @Operation(summary = "Métricas semanales de soporte")
    public ResponseEntity<ApiResponse<List<TicketMetricDto>>> getMetrics() {
        return ResponseEntity.ok(ApiResponse.ok("OK", supportService.getMetrics()));
    }

    @PostMapping("/tickets/{id}/respond")
    @Operation(summary = "Responder un ticket")
    public ResponseEntity<ApiResponse<String>> respond(
            @PathVariable String id,
            @Valid @RequestBody RespondRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(supportService.respondTicket(id, request.getMessage()), null));
    }

    @PostMapping("/tickets/{id}/resolve")
    @Operation(summary = "Marcar ticket como resuelto")
    public ResponseEntity<ApiResponse<String>> resolve(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok(supportService.resolveTicket(id), null));
    }
}
