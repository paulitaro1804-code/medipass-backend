package com.medipass.dto.doctor;

import java.util.List;

public class FullClinicalRecordDto {

    private String id;
    private String pacienteId;
    private String pacienteNombre;
    private String medicoNombre;
    private String fecha;
    private String tipoConsulta;
    private String anamnesis;
    private String diagnosticoCodigo;
    private String diagnosticoDescripcion;
    private String planTratamiento;
    private String evolucion;
    private List<RecetaDto> recetas;
    private List<String> examenesSolicitados;

    public static class RecetaDto {
        private String medicamento;
        private String dosis;
        private String duracion;
        private String indicaciones;

        public String getMedicamento() { return medicamento; }
        public void setMedicamento(String v) { this.medicamento = v; }
        public String getDosis() { return dosis; }
        public void setDosis(String v) { this.dosis = v; }
        public String getDuracion() { return duracion; }
        public void setDuracion(String v) { this.duracion = v; }
        public String getIndicaciones() { return indicaciones; }
        public void setIndicaciones(String v) { this.indicaciones = v; }
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final FullClinicalRecordDto d = new FullClinicalRecordDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder pacienteId(String v) { d.pacienteId = v; return this; }
        public Builder pacienteNombre(String v) { d.pacienteNombre = v; return this; }
        public Builder medicoNombre(String v) { d.medicoNombre = v; return this; }
        public Builder fecha(String v) { d.fecha = v; return this; }
        public Builder tipoConsulta(String v) { d.tipoConsulta = v; return this; }
        public Builder anamnesis(String v) { d.anamnesis = v; return this; }
        public Builder diagnosticoCodigo(String v) { d.diagnosticoCodigo = v; return this; }
        public Builder diagnosticoDescripcion(String v) { d.diagnosticoDescripcion = v; return this; }
        public Builder planTratamiento(String v) { d.planTratamiento = v; return this; }
        public Builder evolucion(String v) { d.evolucion = v; return this; }
        public Builder recetas(List<RecetaDto> v) { d.recetas = v; return this; }
        public Builder examenesSolicitados(List<String> v) { d.examenesSolicitados = v; return this; }
        public FullClinicalRecordDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String v) { this.pacienteNombre = v; }
    public String getMedicoNombre() { return medicoNombre; }
    public void setMedicoNombre(String v) { this.medicoNombre = v; }
    public String getFecha() { return fecha; }
    public void setFecha(String v) { this.fecha = v; }
    public String getTipoConsulta() { return tipoConsulta; }
    public void setTipoConsulta(String v) { this.tipoConsulta = v; }
    public String getAnamnesis() { return anamnesis; }
    public void setAnamnesis(String v) { this.anamnesis = v; }
    public String getDiagnosticoCodigo() { return diagnosticoCodigo; }
    public void setDiagnosticoCodigo(String v) { this.diagnosticoCodigo = v; }
    public String getDiagnosticoDescripcion() { return diagnosticoDescripcion; }
    public void setDiagnosticoDescripcion(String v) { this.diagnosticoDescripcion = v; }
    public String getPlanTratamiento() { return planTratamiento; }
    public void setPlanTratamiento(String v) { this.planTratamiento = v; }
    public String getEvolucion() { return evolucion; }
    public void setEvolucion(String v) { this.evolucion = v; }
    public List<RecetaDto> getRecetas() { return recetas; }
    public void setRecetas(List<RecetaDto> v) { this.recetas = v; }
    public List<String> getExamenesSolicitados() { return examenesSolicitados; }
    public void setExamenesSolicitados(List<String> v) { this.examenesSolicitados = v; }
}
