package com.medipass.repository;

import com.medipass.entity.Appointment;
import com.medipass.enums.AppointmentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByPacienteId(String pacienteId);
    List<Appointment> findByMedicoId(String medicoId);
    List<Appointment> findByEstado(AppointmentStatus estado);
    List<Appointment> findByPacienteIdOrderByFechaHoraAsc(String pacienteId);
    List<Appointment> findByMedicoIdOrderByFechaHoraAsc(String medicoId);
    List<Appointment> findByFechaHoraBetween(LocalDateTime from, LocalDateTime to);
    List<Appointment> findByEnListaEsperaTrue();
    List<Appointment> findByPrioridadAndEnListaEsperaFalse(String prioridad);
    long countByEstado(AppointmentStatus estado);
    long countByFechaHoraBetween(LocalDateTime from, LocalDateTime to);
    long countByFechaCreacionBetween(LocalDateTime from, LocalDateTime to);
    List<Appointment> findByMedicoIdAndFechaHoraBetween(String medicoId, LocalDateTime from, LocalDateTime to);
}
