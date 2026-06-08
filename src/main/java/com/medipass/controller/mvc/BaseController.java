package com.medipass.controller.mvc;

import com.medipass.dto.admin.ActivityPointDto;
import com.medipass.dto.admin.AuditEntryDto;
import com.medipass.dto.admin.ModuleUsageDto;
import com.medipass.entity.User;
import com.medipass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public abstract class BaseController {

    @Autowired
    protected UserRepository userRepository;

    @ModelAttribute
    public void defaultAttributes(Model model) {
        if (!model.containsAttribute("weeklyActivity")) {
            model.addAttribute("weeklyActivity", List.of());
        }
        if (!model.containsAttribute("modulesUsage")) {
            model.addAttribute("modulesUsage", List.of());
        }
        if (!model.containsAttribute("recentAudit")) {
            model.addAttribute("recentAudit", List.of());
        }
    }

    protected void addCommonAttributes(Model model, String activeNav,
                                       String pageTitle, String pageSubtitle) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (auth != null) ? auth.getName() : null;
        String userName = "Usuario";
        String userEmail = email != null ? email : "usuario@medipass.co";
        String role = "admin";
        if (email != null) {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                String nombres = user.getNombres() != null ? user.getNombres() : "";
                String apellidos = user.getApellidos() != null ? user.getApellidos() : "";
                userName = (nombres + " " + apellidos).trim();
                if (userName.isEmpty()) userName = email;
                userEmail = user.getEmail();
                if (user.getRole() != null) {
                    role = user.getRole().name().toLowerCase();
                }
            }
        }
        model.addAttribute("role", role);
        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("activeNav", activeNav);
        model.addAttribute("pageTitle", pageTitle);
        if (pageSubtitle != null) {
            model.addAttribute("pageSubtitle", pageSubtitle);
        }
        model.addAttribute("notifCount", 0);
    }
}