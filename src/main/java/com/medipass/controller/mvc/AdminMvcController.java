package com.medipass.controller.mvc;

import com.medipass.dto.admin.ActivityPointDto;
import com.medipass.dto.admin.AuditEntryDto;
import com.medipass.dto.admin.ModuleUsageDto;
import com.medipass.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/admin")
public class AdminMvcController extends BaseController {

    private final AdminService adminService;

    public AdminMvcController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        addCommonAttributes(model, "dashboard", "Dashboard", "Resumen del sistema");
        model.addAttribute("stats", adminService.getStats());

        List<ActivityPointDto> activity = adminService.getWeeklyActivity();
        model.addAttribute("weeklyActivity", activity != null ? activity : List.of());

        List<ModuleUsageDto> modules = adminService.getModulesUsage();
        model.addAttribute("modulesUsage", modules != null ? modules : List.of());

        List<AuditEntryDto> audit = adminService.getRecentAudit();
        model.addAttribute("recentAudit", audit != null ? audit : List.of());

        return "dashboard/admin/index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        addCommonAttributes(model, "users", "Gestión de Usuarios", "Administra los usuarios del sistema");
        model.addAttribute("users", adminService.getUsers());
        return "dashboard/admin/users";
    }

    @GetMapping("/audit")
    public String audit(Model model) {
        addCommonAttributes(model, "audit", "Auditoría", "Registro de actividad del sistema");
        model.addAttribute("auditLogs", adminService.getRecentAudit());
        return "dashboard/admin/audit";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        addCommonAttributes(model, "reports", "Reportes", "Estadísticas del sistema");
        model.addAttribute("stats", adminService.getStats());

        List<ActivityPointDto> activity = adminService.getWeeklyActivity();
        model.addAttribute("weeklyActivity", activity != null ? activity : List.of());

        List<ModuleUsageDto> modules = adminService.getModulesUsage();
        model.addAttribute("modulesUsage", modules != null ? modules : List.of());

        return "dashboard/admin/reports";
    }

    // ==================== USER CRUD ====================

    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        addCommonAttributes(model, "users", "Nuevo Usuario", "Crear un nuevo usuario en el sistema");
        return "dashboard/admin/users-form";
    }

    @PostMapping("/users/new")
    public String createUser(@RequestParam String nombres,
                             @RequestParam String apellidos,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String role,
                             @RequestParam(required = false) String documento,
                             RedirectAttributes redirectAttributes) {
        try {
            adminService.createUser(nombres, apellidos, email, password, role, documento);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario creado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al crear usuario: " + e.getMessage());
        }
        return "redirect:/dashboard/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable String id, Model model) {
        addCommonAttributes(model, "users", "Editar Usuario", "Modificar datos del usuario");
        adminService.getUserById(id).ifPresent(user -> model.addAttribute("editUser", user));
        return "dashboard/admin/users-form";
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable String id,
                             @RequestParam String nombres,
                             @RequestParam String apellidos,
                             @RequestParam String email,
                             @RequestParam(required = false) String role,
                             @RequestParam(required = false) String documento,
                             @RequestParam(required = false) String estado,
                             RedirectAttributes redirectAttributes) {
        try {
            adminService.updateUser(id, nombres, apellidos, email, role, documento, estado);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario actualizado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar usuario: " + e.getMessage());
        }
        return "redirect:/dashboard/admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar usuario.");
        }
        return "redirect:/dashboard/admin/users";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggleUser(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            adminService.toggleUserStatus(id);
            redirectAttributes.addFlashAttribute("successMessage", "Estado del usuario actualizado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al cambiar estado.");
        }
        return "redirect:/dashboard/admin/users";
    }
}