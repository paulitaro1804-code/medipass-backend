package com.medipass.dto.patient;

public class CreateAppointmentDto {
    private String especialidad;
    private String motivoConsulta;
    private String modalidad;
    private String fechaHora; // ISO string

    public CreateAppointmentDto() {}

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String v) { this.especialidad = v; }
    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String v) { this.motivoConsulta = v; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String v) { this.modalidad = v; }
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String v) { this.fechaHora = v; }
}
