package com.medipass.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "clinical_records")
public class ClinicalRecord {

    @Id
    private String id;

    private String pacienteId;
    private String medicoId;
    private String citaId;
    private LocalDateTime fecha;

    private Diagnostico diagnostico;
    private String anamnesis;
    private String planTratamiento;
    private String evolucion;
    private String tipoConsulta;

    private List<Receta> recetas;
    private List<String> examenesSolicitados;

    private String pacienteNombre;
    private String medicoNombre;

    private LocalDateTime fechaCreacion;

    public ClinicalRecord() { this.fechaCreacion = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final ClinicalRecord r = new ClinicalRecord();
        public Builder id(String v) { r.id = v; return this; }
        public Builder pacienteId(String v) { r.pacienteId = v; return this; }
        public Builder medicoId(String v) { r.medicoId = v; return this; }
        public Builder citaId(String v) { r.citaId = v; return this; }
        public Builder fecha(LocalDateTime v) { r.fecha = v; return this; }
        public Builder diagnostico(Diagnostico v) { r.diagnostico = v; return this; }
        public Builder anamnesis(String v) { r.anamnesis = v; return this; }
        public Builder planTratamiento(String v) { r.planTratamiento = v; return this; }
        public Builder evolucion(String v) { r.evolucion = v; return this; }
        public Builder tipoConsulta(String v) { r.tipoConsulta = v; return this; }
        public Builder recetas(List<Receta> v) { r.recetas = v; return this; }
        public Builder examenesSolicitados(List<String> v) { r.examenesSolicitados = v; return this; }
        public Builder pacienteNombre(String v) { r.pacienteNombre = v; return this; }
        public Builder medicoNombre(String v) { r.medicoNombre = v; return this; }
        public Builder fechaCreacion(LocalDateTime v) { r.fechaCreacion = v; return this; }
        public ClinicalRecord build() { return r; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String v) { this.medicoId = v; }
    public String getCitaId() { return citaId; }
    public void setCitaId(String v) { this.citaId = v; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime v) { this.fecha = v; }
    public Diagnostico getDiagnostico() { return diagnostico; }
    public void setDiagnostico(Diagnostico v) { this.diagnostico = v; }
    public String getAnamnesis() { return anamnesis; }
    public void setAnamnesis(String v) { this.anamnesis = v; }
    public String getPlanTratamiento() { return planTratamiento; }
    public void setPlanTratamiento(String v) { this.planTratamiento = v; }
    public String getEvolucion() { return evolucion; }
    public void setEvolucion(String v) { this.evolucion = v; }
    public String getTipoConsulta() { return tipoConsulta; }
    public void setTipoConsulta(String v) { this.tipoConsulta = v; }
    public List<Receta> getRecetas() { return recetas; }
    public void setRecetas(List<Receta> v) { this.recetas = v; }
    public List<String> getExamenesSolicitados() { return examenesSolicitados; }
    public void setExamenesSolicitados(List<String> v) { this.examenesSolicitados = v; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String v) { this.pacienteNombre = v; }
    public String getMedicoNombre() { return medicoNombre; }
    public void setMedicoNombre(String v) { this.medicoNombre = v; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime v) { this.fechaCreacion = v; }

    public static class Diagnostico {
        private String codigo;
        private String descripcion;
        public Diagnostico() {}
        public String getCodigo() { return codigo; }
        public void setCodigo(String v) { this.codigo = v; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String v) { this.descripcion = v; }
    }

    public static class Receta {
        private String medicamento;
        private String dosis;
        private String duracion;
        private String indicaciones;
        public Receta() {}
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
