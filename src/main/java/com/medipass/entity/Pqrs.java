package com.medipass.entity;

import com.medipass.enums.Priority;
import com.medipass.enums.PqrsStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "pqrs")
public class Pqrs {

    @Id
    private String id;

    private String pacienteId;
    private String agenteId;
    private String tipo;
    private String asunto;
    private String descripcion;
    private PqrsStatus estado;
    private Priority prioridad;

    private int satisfaccion;

    private String pacienteNombre;
    private String agenteName;

    private List<Interaccion> historialInteracciones;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaResolucion;

    public Pqrs() {
        this.historialInteracciones = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Pqrs p = new Pqrs();
        public Builder id(String v) { p.id = v; return this; }
        public Builder pacienteId(String v) { p.pacienteId = v; return this; }
        public Builder agenteId(String v) { p.agenteId = v; return this; }
        public Builder tipo(String v) { p.tipo = v; return this; }
        public Builder asunto(String v) { p.asunto = v; return this; }
        public Builder descripcion(String v) { p.descripcion = v; return this; }
        public Builder estado(PqrsStatus v) { p.estado = v; return this; }
        public Builder prioridad(Priority v) { p.prioridad = v; return this; }
        public Builder satisfaccion(int v) { p.satisfaccion = v; return this; }
        public Builder pacienteNombre(String v) { p.pacienteNombre = v; return this; }
        public Builder agenteName(String v) { p.agenteName = v; return this; }
        public Builder historialInteracciones(List<Interaccion> v) { p.historialInteracciones = v; return this; }
        public Builder fechaCreacion(LocalDateTime v) { p.fechaCreacion = v; return this; }
        public Builder fechaResolucion(LocalDateTime v) { p.fechaResolucion = v; return this; }
        public Pqrs build() { return p; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getAgenteId() { return agenteId; }
    public void setAgenteId(String v) { this.agenteId = v; }
    public String getTipo() { return tipo; }
    public void setTipo(String v) { this.tipo = v; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String v) { this.asunto = v; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String v) { this.descripcion = v; }
    public PqrsStatus getEstado() { return estado; }
    public void setEstado(PqrsStatus v) { this.estado = v; }
    public Priority getPrioridad() { return prioridad; }
    public void setPrioridad(Priority v) { this.prioridad = v; }
    public int getSatisfaccion() { return satisfaccion; }
    public void setSatisfaccion(int v) { this.satisfaccion = v; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String v) { this.pacienteNombre = v; }
    public String getAgenteName() { return agenteName; }
    public void setAgenteName(String v) { this.agenteName = v; }
    public List<Interaccion> getHistorialInteracciones() { return historialInteracciones; }
    public void setHistorialInteracciones(List<Interaccion> v) { this.historialInteracciones = v; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime v) { this.fechaCreacion = v; }
    public LocalDateTime getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(LocalDateTime v) { this.fechaResolucion = v; }

    public static class Interaccion {
        private String agenteId;
        private String agenteName;
        private String mensaje;
        private LocalDateTime timestamp;
        public Interaccion() {}
        public String getAgenteId() { return agenteId; }
        public void setAgenteId(String v) { this.agenteId = v; }
        public String getAgenteName() { return agenteName; }
        public void setAgenteName(String v) { this.agenteName = v; }
        public String getMensaje() { return mensaje; }
        public void setMensaje(String v) { this.mensaje = v; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime v) { this.timestamp = v; }
    }
}
