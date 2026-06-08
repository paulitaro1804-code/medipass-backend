package com.medipass.dto.patient;

import java.util.List;

public class PolicyDetailDto {
    private String id;
    private String numeroPóliza;
    private String aseguradora;
    private String tipoPlan;
    private String fechaVencimiento;
    private String estadoPago;
    private String estadoValidacion;
    private long diasRestantes;
    private List<String> beneficios;

    public PolicyDetailDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final PolicyDetailDto d = new PolicyDetailDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder numeroPóliza(String v) { d.numeroPóliza = v; return this; }
        public Builder aseguradora(String v) { d.aseguradora = v; return this; }
        public Builder tipoPlan(String v) { d.tipoPlan = v; return this; }
        public Builder fechaVencimiento(String v) { d.fechaVencimiento = v; return this; }
        public Builder estadoPago(String v) { d.estadoPago = v; return this; }
        public Builder estadoValidacion(String v) { d.estadoValidacion = v; return this; }
        public Builder diasRestantes(long v) { d.diasRestantes = v; return this; }
        public Builder beneficios(List<String> v) { d.beneficios = v; return this; }
        public PolicyDetailDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getNumeroPóliza() { return numeroPóliza; }
    public void setNumeroPóliza(String v) { this.numeroPóliza = v; }
    public String getAseguradora() { return aseguradora; }
    public void setAseguradora(String v) { this.aseguradora = v; }
    public String getTipoPlan() { return tipoPlan; }
    public void setTipoPlan(String v) { this.tipoPlan = v; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String v) { this.fechaVencimiento = v; }
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String v) { this.estadoPago = v; }
    public String getEstadoValidacion() { return estadoValidacion; }
    public void setEstadoValidacion(String v) { this.estadoValidacion = v; }
    public long getDiasRestantes() { return diasRestantes; }
    public void setDiasRestantes(long v) { this.diasRestantes = v; }
    public List<String> getBeneficios() { return beneficios; }
    public void setBeneficios(List<String> v) { this.beneficios = v; }
}
