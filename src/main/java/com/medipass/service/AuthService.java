package com.medipass.service;

import com.medipass.dto.auth.LoginRequest;
import com.medipass.dto.auth.LoginResponse;
import com.medipass.dto.auth.RegisterRequest;
import com.medipass.entity.Policy;
import com.medipass.entity.User;
import com.medipass.enums.PolicyStatus;
import com.medipass.enums.Role;
import com.medipass.enums.UserStatus;
import com.medipass.exception.BadRequestException;
import com.medipass.jwt.JwtService;
import com.medipass.repository.PolicyRepository;
import com.medipass.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuditService auditService;

    public AuthService(UserRepository userRepository, PolicyRepository policyRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager, AuditService auditService) {
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.auditService = auditService;
    }

    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String role = user.getRole().name().toLowerCase();
        String token = jwtService.generateToken(user.getEmail(), role);

        auditService.log(user.getId(), user.getFullName(), user.getRole().name(),
                "Inicio de sesión exitoso", "users", user.getId(), "create");

        return LoginResponse.builder()
                .token(token)
                .role(role)
                .userId(user.getId())
                .name(user.getFullName())
                .build();
    }

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El correo ya está registrado en el sistema");
        }

        User user = User.builder()
                .nombres(request.getFirstName())
                .apellidos(request.getLastName())
                .documento(request.getDocument())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getPhone())
                .role(Role.PATIENT)
                .estado(UserStatus.PENDIENTE)
                .build();

        User saved = userRepository.save(user);

        Policy policy = Policy.builder()
                .numeroPóliza(request.getPolicyNumber())
                .pacienteId(saved.getId())
                .nombrePaciente(saved.getFullName())
                .documentoPaciente(request.getDocument())
                .emailPaciente(request.getEmail())
                .aseguradora(request.getInsurer() != null && !request.getInsurer().isBlank()
                        ? request.getInsurer() : "Por verificar")
                .tipoPlan(request.getPlanDuration() != null && !request.getPlanDuration().isBlank()
                        ? request.getPlanDuration() : "Por verificar")
                .estadoPago("AL_DIA")
                .estadoValidacion(PolicyStatus.PENDIENTE)
                .beneficios(List.of("Consulta general"))
                .fechaVencimiento(parseFechaVencimiento(request.getPlanDuration()))
                .build();

        Policy savedPolicy = policyRepository.save(policy);

        user.setPolicyId(savedPolicy.getId());
        userRepository.save(user);

        auditService.log(saved.getId(), saved.getFullName(), "PATIENT",
                "Nuevo paciente registrado: " + saved.getEmail(), "users", saved.getId(), "create");

        return "Solicitud enviada correctamente. Un validador revisará tu póliza y recibirás un correo cuando tu cuenta sea aprobada.";
    }

    private LocalDate parseFechaVencimiento(String planDuration) {
        if (planDuration == null || planDuration.isBlank()) return LocalDate.now().plusYears(1);
        return switch (planDuration) {
            case "Plan Estándar"   -> LocalDate.now().plusYears(2);
            case "Plan Premium"    -> LocalDate.now().plusYears(3);
            case "Plan Empresarial"-> LocalDate.now().plusYears(5);
            default                -> LocalDate.now().plusYears(1); // Plan Básico
        };
    }
}
