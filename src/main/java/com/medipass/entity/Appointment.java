package com.medipass.entity;

import com.medipass.enums.AppointmentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "appointments")
public class Appointment {

    @Id
    private String id;

    private String pacienteId;
    private String medicoId;
    private String coordinadorId;

    private String especialidad;
    private LocalDateTime fechaHora;
    private AppointmentStatus estado;
    private String codigoCita;
    private String modalidad;
    private String motivoConsulta;

    private String pacienteNombre;
    private String medicoNombre;
    private int pacienteEdad;
    private String prioridad;
    private boolean enListaEspera;
    private String tiempoEspera;
    private String planPaciente;

    private LocalDateTime fechaCreacion;

    public Appointment() { this.fechaCreacion = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Appointment a = new Appointment();
        public Builder id(String v) { a.id = v; return this; }
        public Builder pacienteId(String v) { a.pacienteId = v; return this; }
        public Builder medicoId(String v) { a.medicoId = v; return this; }
        public Builder coordinadorId(String v) { a.coordinadorId = v; return this; }
        public Builder especialidad(String v) { a.especialidad = v; return this; }
        public Builder fechaHora(LocalDateTime v) { a.fechaHora = v; return this; }
        public Builder estado(AppointmentStatus v) { a.estado = v; return this; }
        public Builder codigoCita(String v) { a.codigoCita = v; return this; }
        public Builder modalidad(String v) { a.modalidad = v; return this; }
        public Builder motivoConsulta(String v) { a.motivoConsulta = v; return this; }
        public Builder pacienteNombre(String v) { a.pacienteNombre = v; return this; }
        public Builder medicoNombre(String v) { a.medicoNombre = v; return this; }
        public Builder pacienteEdad(int v) { a.pacienteEdad = v; return this; }
        public Builder prioridad(String v) { a.prioridad = v; return this; }
        public Builder enListaEspera(boolean v) { a.enListaEspera = v; return this; }
        public Builder tiempoEspera(String v) { a.tiempoEspera = v; return this; }
        public Builder planPaciente(String v) { a.planPaciente = v; return this; }
        public Builder fechaCreacion(LocalDateTime v) { a.fechaCreacion = v; return this; }
        public Appointment build() { return a; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String v) { this.medicoId = v; }
    public String getCoordinadorId() { return coordinadorId; }
    public void setCoordinadorId(String v) { this.coordinadorId = v; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String v) { this.especialidad = v; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime v) { this.fechaHora = v; }
    public AppointmentStatus getEstado() { return estado; }
    public void setEstado(AppointmentStatus v) { this.estado = v; }
    public String getCodigoCita() { return codigoCita; }
    public void setCodigoCita(String v) { this.codigoCita = v; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String v) { this.modalidad = v; }
    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String v) { this.motivoConsulta = v; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String v) { this.pacienteNombre = v; }
    public String getMedicoNombre() { return medicoNombre; }
    public void setMedicoNombre(String v) { this.medicoNombre = v; }
    public int getPacienteEdad() { return pacienteEdad; }
    public void setPacienteEdad(int v) { this.pacienteEdad = v; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String v) { this.prioridad = v; }
    public boolean isEnListaEspera() { return enListaEspera; }
    public void setEnListaEspera(boolean v) { this.enListaEspera = v; }
    public String getTiempoEspera() { return tiempoEspera; }
    public void setTiempoEspera(String v) { this.tiempoEspera = v; }
    public String getPlanPaciente() { return planPaciente; }
    public void setPlanPaciente(String v) { this.planPaciente = v; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime v) { this.fechaCreacion = v; }
}
