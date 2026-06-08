package com.medipass.entity;

import com.medipass.enums.LabStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "lab_results")
public class LabResult {

    @Id
    private String id;

    private String pacienteId;
    private String medicoSolicitanteId;
    private String tipoExamen;
    private LabStatus estado;
    private String prioridad;

    private Map<String, Object> valoresResultado;
    private String archivoUrl;
    private boolean validado;
    private boolean alertaCritica;
    private boolean pacienteNotificado;

    private String valorResultado;
    private String rangoNormal;

    private List<ResultItem> resultItems;
    private String observaciones;
    private String laboratorioNombre;

    private String pacienteNombre;
    private String medicoNombre;

    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaPublicacion;

    private LocalDateTime fechaCreacion;

    public LabResult() { this.fechaCreacion = LocalDateTime.now(); }

    // ── Nested result item ─────────────────────────────────────────────────────
    public static class ResultItem {
        private String parametro;
        private String valor;
        private String unidad;
        private String rangoNormal;
        private boolean critico;

        public ResultItem() {}
        public ResultItem(String parametro, String valor, String unidad, String rangoNormal, boolean critico) {
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
    // ──────────────────────────────────────────────────────────────────────────

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final LabResult l = new LabResult();
        public Builder id(String v) { l.id = v; return this; }
        public Builder pacienteId(String v) { l.pacienteId = v; return this; }
        public Builder medicoSolicitanteId(String v) { l.medicoSolicitanteId = v; return this; }
        public Builder tipoExamen(String v) { l.tipoExamen = v; return this; }
        public Builder estado(LabStatus v) { l.estado = v; return this; }
        public Builder prioridad(String v) { l.prioridad = v; return this; }
        public Builder valoresResultado(Map<String, Object> v) { l.valoresResultado = v; return this; }
        public Builder archivoUrl(String v) { l.archivoUrl = v; return this; }
        public Builder validado(boolean v) { l.validado = v; return this; }
        public Builder alertaCritica(boolean v) { l.alertaCritica = v; return this; }
        public Builder pacienteNotificado(boolean v) { l.pacienteNotificado = v; return this; }
        public Builder valorResultado(String v) { l.valorResultado = v; return this; }
        public Builder rangoNormal(String v) { l.rangoNormal = v; return this; }
        public Builder pacienteNombre(String v) { l.pacienteNombre = v; return this; }
        public Builder medicoNombre(String v) { l.medicoNombre = v; return this; }
        public Builder resultItems(List<ResultItem> v) { l.resultItems = v; return this; }
        public Builder observaciones(String v) { l.observaciones = v; return this; }
        public Builder laboratorioNombre(String v) { l.laboratorioNombre = v; return this; }
        public Builder fechaSolicitud(LocalDateTime v) { l.fechaSolicitud = v; return this; }
        public Builder fechaPublicacion(LocalDateTime v) { l.fechaPublicacion = v; return this; }
        public Builder fechaCreacion(LocalDateTime v) { l.fechaCreacion = v; return this; }
        public LabResult build() { return l; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getMedicoSolicitanteId() { return medicoSolicitanteId; }
    public void setMedicoSolicitanteId(String v) { this.medicoSolicitanteId = v; }
    public String getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(String v) { this.tipoExamen = v; }
    public LabStatus getEstado() { return estado; }
    public void setEstado(LabStatus v) { this.estado = v; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String v) { this.prioridad = v; }
    public Map<String, Object> getValoresResultado() { return valoresResultado; }
    public void setValoresResultado(Map<String, Object> v) { this.valoresResultado = v; }
    public String getArchivoUrl() { return archivoUrl; }
    public void setArchivoUrl(String v) { this.archivoUrl = v; }
    public boolean isValidado() { return validado; }
    public void setValidado(boolean v) { this.validado = v; }
    public boolean isAlertaCritica() { return alertaCritica; }
    public void setAlertaCritica(boolean v) { this.alertaCritica = v; }
    public boolean isPacienteNotificado() { return pacienteNotificado; }
    public void setPacienteNotificado(boolean v) { this.pacienteNotificado = v; }
    public String getValorResultado() { return valorResultado; }
    public void setValorResultado(String v) { this.valorResultado = v; }
    public String getRangoNormal() { return rangoNormal; }
    public void setRangoNormal(String v) { this.rangoNormal = v; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String v) { this.pacienteNombre = v; }
    public String getMedicoNombre() { return medicoNombre; }
    public void setMedicoNombre(String v) { this.medicoNombre = v; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime v) { this.fechaSolicitud = v; }
    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime v) { this.fechaPublicacion = v; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime v) { this.fechaCreacion = v; }
    public List<ResultItem> getResultItems() { return resultItems; }
    public void setResultItems(List<ResultItem> v) { this.resultItems = v; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String v) { this.observaciones = v; }
    public String getLaboratorioNombre() { return laboratorioNombre; }
    public void setLaboratorioNombre(String v) { this.laboratorioNombre = v; }
}
