package com.medipass.repository;

import com.medipass.entity.ClinicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicalRecordRepository extends MongoRepository<ClinicalRecord, String> {
    List<ClinicalRecord> findByPacienteIdOrderByFechaDesc(String pacienteId);
    List<ClinicalRecord> findByMedicoIdOrderByFechaDesc(String medicoId);
    List<ClinicalRecord> findByCitaId(String citaId);
    long countByMedicoId(String medicoId);
}
