package com.medipass.dto.lab;

import java.util.List;

public class LabResultDetailDto {
    private String id;
    private String patient;
    private String patientId;
    private String exam;
    private String doctor;
    private String doctorId;
    private String laboratorioNombre;
    private String requested;
    private String published;
    private List<ResultItemDto> items;
    private String observaciones;
    private boolean alertaCritica;
    private String status;

    public LabResultDetailDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final LabResultDetailDto d = new LabResultDetailDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder patient(String v) { d.patient = v; return this; }
        public Builder patientId(String v) { d.patientId = v; return this; }
        public Builder exam(String v) { d.exam = v; return this; }
        public Builder doctor(String v) { d.doctor = v; return this; }
        public Builder doctorId(String v) { d.doctorId = v; return this; }
        public Builder laboratorioNombre(String v) { d.laboratorioNombre = v; return this; }
        public Builder requested(String v) { d.requested = v; return this; }
        public Builder published(String v) { d.published = v; return this; }
        public Builder items(List<ResultItemDto> v) { d.items = v; return this; }
        public Builder observaciones(String v) { d.observaciones = v; return this; }
        public Builder alertaCritica(boolean v) { d.alertaCritica = v; return this; }
        public Builder status(String v) { d.status = v; return this; }
        public LabResultDetailDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String v) { this.patientId = v; }
    public String getExam() { return exam; }
    public void setExam(String v) { this.exam = v; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String v) { this.doctor = v; }
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String v) { this.doctorId = v; }
    public String getLaboratorioNombre() { return laboratorioNombre; }
    public void setLaboratorioNombre(String v) { this.laboratorioNombre = v; }
    public String getRequested() { return requested; }
    public void setRequested(String v) { this.requested = v; }
    public String getPublished() { return published; }
    public void setPublished(String v) { this.published = v; }
    public List<ResultItemDto> getItems() { return items; }
    public void setItems(List<ResultItemDto> v) { this.items = v; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String v) { this.observaciones = v; }
    public boolean isAlertaCritica() { return alertaCritica; }
    public void setAlertaCritica(boolean v) { this.alertaCritica = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }

    // ── Nested result item DTO ────────────────────────────────────────────────
    public static class ResultItemDto {
        private String parametro;
        private String valor;
        private String unidad;
        private String rangoNormal;
        private boolean critico;

        public ResultItemDto() {}
        public ResultItemDto(String parametro, String valor, String unidad, String rangoNormal, boolean critico) {
            this.parametro = parametro;
            this.valor = valor;
            this.unidad = unidad;
            this.rangoNormal = rangoNormal;
            this.critico = critico;
        }
        public String getParametro() { return parametro; }
        public void setParametro(String v) { this.parametro = v; }
        public String getValor() { return valor; }
        public void setValor(String v) { this.valor = v; }
        public String getUnidad() { return unidad; }
        public void setUnidad(String v) { this.unidad = v; }
        public String getRangoNormal() { return rangoNormal; }
        public void setRangoNormal(String v) { this.rangoNormal = v; }
        public boolean isCritico() { return critico; }
        public void setCritico(boolean v) { this.critico = v; }
    }
}
