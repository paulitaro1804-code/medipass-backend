package com.medipass.entity;

import com.medipass.enums.PolicyStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "policies")
public class Policy {

    @Id
    private String id;

    @Indexed(unique = true)
    private String numeroPóliza;

    private String pacienteId;
    private String aseguradora;
    private String tipoPlan;

    private LocalDate fechaVencimiento;
    private String estadoPago;

    private List<String> beneficios;
    private PolicyStatus estadoValidacion;
    private String motivoRechazo;

    private String nombrePaciente;
    private String documentoPaciente;
    private String emailPaciente;

    private String validadoPor;
    private LocalDateTime fechaValidacion;

    private LocalDateTime fechaCreacion;

    public Policy() { this.fechaCreacion = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Policy p = new Policy();
        public Builder id(String v) { p.id = v; return this; }
        public Builder numeroPóliza(String v) { p.numeroPóliza = v; return this; }
        public Builder pacienteId(String v) { p.pacienteId = v; return this; }
        public Builder aseguradora(String v) { p.aseguradora = v; return this; }
        public Builder tipoPlan(String v) { p.tipoPlan = v; return this; }
        public Builder fechaVencimiento(LocalDate v) { p.fechaVencimiento = v; return this; }
        public Builder estadoPago(String v) { p.estadoPago = v; return this; }
        public Builder beneficios(List<String> v) { p.beneficios = v; return this; }
        public Builder estadoValidacion(PolicyStatus v) { p.estadoValidacion = v; return this; }
        public Builder motivoRechazo(String v) { p.motivoRechazo = v; return this; }
        public Builder nombrePaciente(String v) { p.nombrePaciente = v; return this; }
        public Builder documentoPaciente(String v) { p.documentoPaciente = v; return this; }
        public Builder emailPaciente(String v) { p.emailPaciente = v; return this; }
        public Builder validadoPor(String v) { p.validadoPor = v; return this; }
        public Builder fechaValidacion(LocalDateTime v) { p.fechaValidacion = v; return this; }
        public Builder fechaCreacion(LocalDateTime v) { p.fechaCreacion = v; return this; }
        public Policy build() { return p; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getNumeroPóliza() { return numeroPóliza; }
    public void setNumeroPóliza(String v) { this.numeroPóliza = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getAseguradora() { return aseguradora; }
    public void setAseguradora(String v) { this.aseguradora = v; }
    public String getTipoPlan() { return tipoPlan; }
    public void setTipoPlan(String v) { this.tipoPlan = v; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate v) { this.fechaVencimiento = v; }
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String v) { this.estadoPago = v; }
    public List<String> getBeneficios() { return beneficios; }
    public void setBeneficios(List<String> v) { this.beneficios = v; }
    public PolicyStatus getEstadoValidacion() { return estadoValidacion; }
    public void setEstadoValidacion(PolicyStatus v) { this.estadoValidacion = v; }
    public String getMotivoRechazo() { return motivoRechazo; }
    public void setMotivoRechazo(String v) { this.motivoRechazo = v; }
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String v) { this.nombrePaciente = v; }
    public String getDocumentoPaciente() { return documentoPaciente; }
    public void setDocumentoPaciente(String v) { this.documentoPaciente = v; }
    public String getEmailPaciente() { return emailPaciente; }
    public void setEmailPaciente(String v) { this.emailPaciente = v; }
    public String getValidadoPor() { return validadoPor; }
    public void setValidadoPor(String v) { this.validadoPor = v; }
    public LocalDateTime getFechaValidacion() { return fechaValidacion; }
    public void setFechaValidacion(LocalDateTime v) { this.fechaValidacion = v; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime v) { this.fechaCreacion = v; }
}
