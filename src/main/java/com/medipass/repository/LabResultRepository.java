package com.medipass.repository;

import com.medipass.entity.LabResult;
import com.medipass.enums.LabStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabResultRepository extends MongoRepository<LabResult, String> {
    List<LabResult> findByPacienteId(String pacienteId);
    List<LabResult> findByEstado(LabStatus estado);
    List<LabResult> findByAlertaCriticaTrue();
    List<LabResult> findByEstadoOrderByFechaSolicitudAsc(LabStatus estado);
    List<LabResult> findByValidadoTrue();
    long countByEstado(LabStatus estado);
    long countByAlertaCriticaTrue();
}
