package com.medipass.controller;

import com.medipass.dto.ApiResponse;
import com.medipass.dto.admin.*;
import com.medipass.dto.admin.CreateUserDto;
import com.medipass.dto.admin.UpdateUserDto;
import com.medipass.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administrador", description = "Panel de administración del sistema")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadísticas generales del sistema")
    public ResponseEntity<ApiResponse<AdminStatsDto>> getStats() {
        return ResponseEntity.ok(ApiResponse.ok("OK", adminService.getStats()));
    }

    @GetMapping("/activity")
    @Operation(summary = "Actividad semanal")
    public ResponseEntity<ApiResponse<List<ActivityPointDto>>> getActivity() {
        return ResponseEntity.ok(ApiResponse.ok("OK", adminService.getWeeklyActivity()));
    }

    @GetMapping("/modules-usage")
    @Operation(summary = "Uso de módulos del sistema")
    public ResponseEntity<ApiResponse<List<ModuleUsageDto>>> getModulesUsage() {
        return ResponseEntity.ok(ApiResponse.ok("OK", adminService.getModulesUsage()));
    }

    @GetMapping("/audit")
    @Operation(summary = "Últimas entradas de auditoría")
    public ResponseEntity<ApiResponse<List<AuditEntryDto>>> getAudit() {
        return ResponseEntity.ok(ApiResponse.ok("OK", adminService.getRecentAudit()));
    }

    @GetMapping("/users")
    @Operation(summary = "Lista de todos los usuarios del sistema")
    public ResponseEntity<ApiResponse<List<UserListDto>>> getUsers() {
        return ResponseEntity.ok(ApiResponse.ok("OK", adminService.getUsers()));
    }

    @PostMapping("/users")
    @Operation(summary = "Crear un nuevo usuario")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody CreateUserDto dto) {
        adminService.createUser(dto.getNombres(), dto.getApellidos(), dto.getEmail(),
                dto.getPassword(), dto.getRole(), dto.getDocumento());
        return ResponseEntity.ok(ApiResponse.ok("Usuario creado", "ok"));
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Actualizar datos de un usuario")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable String id,
                                                          @RequestBody UpdateUserDto dto) {
        adminService.updateUser(id, dto.getNombres(), dto.getApellidos(), dto.getEmail(),
                dto.getRole(), dto.getDocumento(), dto.getEstado());
        return ResponseEntity.ok(ApiResponse.ok("Usuario actualizado", "ok"));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Eliminar un usuario")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.ok("Usuario eliminado", "ok"));
    }

    @PatchMapping("/users/{id}/toggle-status")
    @Operation(summary = "Activar o suspender un usuario")
    public ResponseEntity<ApiResponse<String>> toggleStatus(@PathVariable String id) {
        adminService.toggleUserStatus(id);
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado", "ok"));
    }

    @GetMapping("/reports")
    @Operation(summary = "Reportes generales del sistema")
    public ResponseEntity<ApiResponse<AdminReportDto>> getReports() {
        return ResponseEntity.ok(ApiResponse.ok("OK", adminService.getReports()));
    }

    @PostMapping("/cleanup")
    @Operation(summary = "Eliminar datos huérfanos (usuarios eliminados)")
    public ResponseEntity<ApiResponse<String>> cleanupOrphanedData() {
        long deleted = adminService.cleanupOrphanedData();
        return ResponseEntity.ok(ApiResponse.ok("Limpieza completada", deleted + " registros eliminados"));
    }
}
