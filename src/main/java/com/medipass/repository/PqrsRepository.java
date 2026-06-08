package com.medipass.repository;

import com.medipass.entity.Pqrs;
import com.medipass.enums.Priority;
import com.medipass.enums.PqrsStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PqrsRepository extends MongoRepository<Pqrs, String> {
    List<Pqrs> findByPacienteId(String pacienteId);
    List<Pqrs> findByEstado(PqrsStatus estado);
    List<Pqrs> findByPrioridadAndEstado(Priority prioridad, PqrsStatus estado);
    List<Pqrs> findByEstadoNot(PqrsStatus estado);
    long countByEstado(PqrsStatus estado);
    long countByPrioridad(Priority prioridad);
}
