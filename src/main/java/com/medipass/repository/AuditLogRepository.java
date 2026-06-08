package com.medipass.repository;

import com.medipass.entity.AuditLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    List<AuditLog> findByOrderByTimestampDesc(Pageable pageable);
    List<AuditLog> findByUsuarioId(String usuarioId);
    List<AuditLog> findTop20ByOrderByTimestampDesc();
}
