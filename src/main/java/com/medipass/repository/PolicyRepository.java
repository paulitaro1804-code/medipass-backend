package com.medipass.repository;

import com.medipass.entity.Policy;
import com.medipass.enums.PolicyStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends MongoRepository<Policy, String> {
    Optional<Policy> findByNumeroPóliza(String numeroPóliza);
    Optional<Policy> findByPacienteId(String pacienteId);
    boolean existsByNumeroPóliza(String numeroPóliza);
    List<Policy> findByEstadoValidacion(PolicyStatus estadoValidacion);
    List<Policy> findByFechaVencimientoBetween(LocalDate from, LocalDate to);
    long countByEstadoValidacion(PolicyStatus estadoValidacion);
    List<Policy> findByFechaCreacionBetween(java.time.LocalDateTime from, java.time.LocalDateTime to);
    List<Policy> findByEstadoValidacionAndFechaVencimientoBetween(PolicyStatus estadoValidacion, LocalDate from, LocalDate to);
}
