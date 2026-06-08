package com.medipass.dto.lab;

import java.util.List;

public class SubmitResultRequest {

    private List<ResultItemRequest> items;
    private String observaciones;
    private boolean alertaCritica;
    private String laboratorioNombre;

    public SubmitResultRequest() {}

    public List<ResultItemRequest> getItems() { return items; }
    public void setItems(List<ResultItemRequest> v) { this.items = v; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String v) { this.observaciones = v; }
    public boolean isAlertaCritica() { return alertaCritica; }
    public void setAlertaCritica(boolean v) { this.alertaCritica = v; }
    public String getLaboratorioNombre() { return laboratorioNombre; }
    public void setLaboratorioNombre(String v) { this.laboratorioNombre = v; }

    public static class ResultItemRequest {
        private String parametro;
        private String valor;
        private String unidad;
        private String rangoNormal;
        private boolean critico;

        public ResultItemRequest() {}
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
