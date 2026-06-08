package com.medipass.dto.coordinator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateAppointmentRequest {

    @NotBlank(message = "El nombre del paciente es obligatorio")
    private String pacienteNombre;

    private String pacienteId;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    private String medicoId;
    private String medicoNombre;
    private String motivoConsulta;
    private String prioridad;
    private String modalidad;
    private String planPaciente;

    public CreateAppointmentRequest() {}

    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String v) { this.pacienteNombre = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String v) { this.especialidad = v; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime v) { this.fechaHora = v; }
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String v) { this.medicoId = v; }
    public String getMedicoNombre() { return medicoNombre; }
    public void setMedicoNombre(String v) { this.medicoNombre = v; }
    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String v) { this.motivoConsulta = v; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String v) { this.prioridad = v; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String v) { this.modalidad = v; }
    public String getPlanPaciente() { return planPaciente; }
    public void setPlanPaciente(String v) { this.planPaciente = v; }
}
