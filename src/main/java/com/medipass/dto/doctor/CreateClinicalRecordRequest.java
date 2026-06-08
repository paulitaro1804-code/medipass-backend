package com.medipass.dto.doctor;

import java.util.List;

public class CreateClinicalRecordRequest {

    private String citaId;
    private String pacienteId;
    private String pacienteNombre;
    private String tipoConsulta;
    private String anamnesis;
    private String diagnosticoCodigo;
    private String diagnosticoDescripcion;
    private String planTratamiento;
    private String evolucion;
    private List<RecetaItem> recetas;
    private List<String> examenesSolicitados;

    public static class RecetaItem {
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

    public String getCitaId() { return citaId; }
    public void setCitaId(String v) { this.citaId = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String v) { this.pacienteNombre = v; }
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
    public List<RecetaItem> getRecetas() { return recetas; }
    public void setRecetas(List<RecetaItem> v) { this.recetas = v; }
    public List<String> getExamenesSolicitados() { return examenesSolicitados; }
    public void setExamenesSolicitados(List<String> v) { this.examenesSolicitados = v; }
}
