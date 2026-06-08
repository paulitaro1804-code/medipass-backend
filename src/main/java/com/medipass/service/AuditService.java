package com.medipass.service;

import com.medipass.entity.AuditLog;
import com.medipass.repository.AuditLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(String accion, String entidad, String entidadId, String type) {
        String email = "sistema";
        String rol = "SYSTEM";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            email = auth.getName();
        }

        AuditLog log = AuditLog.builder()
                .usuarioId(email)
                .userName(email)
                .rol(rol)
                .accion(accion)
                .entidad(entidad)
                .entidadId(entidadId)
                .ip("127.0.0.1")
                .type(type)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }

    public void log(String userId, String userName, String rol, String accion,
                    String entidad, String entidadId, String type) {
        AuditLog entry = AuditLog.builder()
                .usuarioId(userId)
                .userName(userName)
                .rol(rol)
                .accion(accion)
                .entidad(entidad)
                .entidadId(entidadId)
                .ip("127.0.0.1")
                .type(type)
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(entry);
    }
}
