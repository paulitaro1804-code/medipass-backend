package com.medipass.dto.patient;

import java.util.List;

public class ClinicalRecordDetailDto {
    private String id;
    private String date;
    private String doctor;
    private String tipoConsulta;
    private String anamnesis;
    private String diagnosis;
    private String diagnosisCode;
    private String planTratamiento;
    private String evolucion;
    private List<RecetaDto> recetas;
    private List<String> examenesSolicitados;

    public ClinicalRecordDetailDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final ClinicalRecordDetailDto d = new ClinicalRecordDetailDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder date(String v) { d.date = v; return this; }
        public Builder doctor(String v) { d.doctor = v; return this; }
        public Builder tipoConsulta(String v) { d.tipoConsulta = v; return this; }
        public Builder anamnesis(String v) { d.anamnesis = v; return this; }
        public Builder diagnosis(String v) { d.diagnosis = v; return this; }
        public Builder diagnosisCode(String v) { d.diagnosisCode = v; return this; }
        public Builder planTratamiento(String v) { d.planTratamiento = v; return this; }
        public Builder evolucion(String v) { d.evolucion = v; return this; }
        public Builder recetas(List<RecetaDto> v) { d.recetas = v; return this; }
        public Builder examenesSolicitados(List<String> v) { d.examenesSolicitados = v; return this; }
        public ClinicalRecordDetailDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getDate() { return date; }
    public void setDate(String v) { this.date = v; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String v) { this.doctor = v; }
    public String getTipoConsulta() { return tipoConsulta; }
    public void setTipoConsulta(String v) { this.tipoConsulta = v; }
    public String getAnamnesis() { return anamnesis; }
    public void setAnamnesis(String v) { this.anamnesis = v; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String v) { this.diagnosis = v; }
    public String getDiagnosisCode() { return diagnosisCode; }
    public void setDiagnosisCode(String v) { this.diagnosisCode = v; }
    public String getPlanTratamiento() { return planTratamiento; }
    public void setPlanTratamiento(String v) { this.planTratamiento = v; }
    public String getEvolucion() { return evolucion; }
    public void setEvolucion(String v) { this.evolucion = v; }
    public List<RecetaDto> getRecetas() { return recetas; }
    public void setRecetas(List<RecetaDto> v) { this.recetas = v; }
    public List<String> getExamenesSolicitados() { return examenesSolicitados; }
    public void setExamenesSolicitados(List<String> v) { this.examenesSolicitados = v; }

    public static class RecetaDto {
        private String medicamento;
        private String dosis;
        private String duracion;
        private String indicaciones;
        public RecetaDto() {}
        public RecetaDto(String medicamento, String dosis, String duracion, String indicaciones) {
            this.medicamento = medicamento;
            this.dosis = dosis;
            this.duracion = duracion;
            this.indicaciones = indicaciones;
        }
        public String getMedicamento() { return medicamento; }
        public void setMedicamento(String v) { this.medicamento = v; }
        public String getDosis() { return dosis; }
        public void setDosis(String v) { this.dosis = v; }
        public String getDuracion() { return duracion; }
        public void setDuracion(String v) { this.duracion = v; }
        public String getIndicaciones() { return indicaciones; }
        public void setIndicaciones(String v) { this.indicaciones = v; }
    }
}
