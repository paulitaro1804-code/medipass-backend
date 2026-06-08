package com.medipass.dto.doctor;

public class CreateLabOrderRequest {

    private String pacienteId;
    private String pacienteNombre;
    private String tipoExamen;
    private String prioridad;

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String v) { this.pacienteNombre = v; }
    public String getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(String v) { this.tipoExamen = v; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String v) { this.prioridad = v; }
}
