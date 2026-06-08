package com.medipass.dto.admin;

public class ActivityPointDto {
    private String name;
    private long pacientes;
    private long citas;
    private long polizas;

    public ActivityPointDto() {}

    public ActivityPointDto(String name, long pacientes, long citas, long polizas) {
        this.name = name;
        this.pacientes = pacientes;
        this.citas = citas;
        this.polizas = polizas;
    }

    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public long getPacientes() { return pacientes; }
    public void setPacientes(long v) { this.pacientes = v; }
    public long getCitas() { return citas; }
    public void setCitas(long v) { this.citas = v; }
    public long getPolizas() { return polizas; }
    public void setPolizas(long v) { this.polizas = v; }
}