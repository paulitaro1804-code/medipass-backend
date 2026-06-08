package com.medipass.controller.mvc;

import com.medipass.dto.auth.RegisterRequest;
import com.medipass.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class AuthMvcController {

    private final AuthService authService;

    public AuthMvcController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String homeRedirect() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String disabled,
            @RequestParam(required = false) String locked,
            @RequestParam(required = false) String logout,
            @RequestParam(required = false) String registered,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos. Verifica tus credenciales.");
        } else if (disabled != null) {
            model.addAttribute("error", "Tu cuenta está pendiente de validación. Un administrador debe aprobarla antes de que puedas ingresar.");
        } else if (locked != null) {
            model.addAttribute("error", "Tu cuenta ha sido suspendida. Por favor contacta al soporte.");
        }
        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión correctamente.");
        } else if (registered != null) {
            model.addAttribute("message", "Solicitud enviada correctamente. Un validador revisará tu póliza pronto.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String document,
            @RequestParam String policyNumber,
            @RequestParam String password) {
        try {
            RegisterRequest req = new RegisterRequest();
            req.setFirstName(firstName);
            req.setLastName(lastName);
            req.setEmail(email);
            req.setDocument(document);
            req.setPolicyNumber(policyNumber);
            req.setPassword(password);
            authService.register(req);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            String msg = URLEncoder.encode(e.getMessage() != null ? e.getMessage() : "Error al registrar", StandardCharsets.UTF_8);
            return "redirect:/register?error=" + msg;
        }
    }
}
