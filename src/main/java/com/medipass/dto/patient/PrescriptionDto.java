package com.medipass.dto.patient;

public class PrescriptionDto {
    private String id;
    private String medicamento;
    private String dosis;
    private String duracion;
    private String indicaciones;
    private String doctor;
    private String date;

    public PrescriptionDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final PrescriptionDto d = new PrescriptionDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder medicamento(String v) { d.medicamento = v; return this; }
        public Builder dosis(String v) { d.dosis = v; return this; }
        public Builder duracion(String v) { d.duracion = v; return this; }
        public Builder indicaciones(String v) { d.indicaciones = v; return this; }
        public Builder doctor(String v) { d.doctor = v; return this; }
        public Builder date(String v) { d.date = v; return this; }
        public PrescriptionDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String v) { this.medicamento = v; }
    public String getDosis() { return dosis; }
    public void setDosis(String v) { this.dosis = v; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String v) { this.duracion = v; }
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String v) { this.indicaciones = v; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String v) { this.doctor = v; }
    public String getDate() { return date; }
    public void setDate(String v) { this.date = v; }
}
