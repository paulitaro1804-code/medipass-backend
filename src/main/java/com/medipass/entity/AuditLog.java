package com.medipass.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit_logs")
public class AuditLog {

    @Id
    private String id;

    private String usuarioId;
    private String userName;
    private String rol;
    private String accion;
    private String entidad;
    private String entidadId;
    private Object datosPrevios;
    private Object datosNuevos;
    private String ip;
    private String type;

    private LocalDateTime timestamp;

    public AuditLog() { this.timestamp = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final AuditLog a = new AuditLog();
        public Builder id(String v) { a.id = v; return this; }
        public Builder usuarioId(String v) { a.usuarioId = v; return this; }
        public Builder userName(String v) { a.userName = v; return this; }
        public Builder rol(String v) { a.rol = v; return this; }
        public Builder accion(String v) { a.accion = v; return this; }
        public Builder entidad(String v) { a.entidad = v; return this; }
        public Builder entidadId(String v) { a.entidadId = v; return this; }
        public Builder datosPrevios(Object v) { a.datosPrevios = v; return this; }
        public Builder datosNuevos(Object v) { a.datosNuevos = v; return this; }
        public Builder ip(String v) { a.ip = v; return this; }
        public Builder type(String v) { a.type = v; return this; }
        public Builder timestamp(LocalDateTime v) { a.timestamp = v; return this; }
        public AuditLog build() { return a; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String v) { this.usuarioId = v; }
    public String getUserName() { return userName; }
    public void setUserName(String v) { this.userName = v; }
    public String getRol() { return rol; }
    public void setRol(String v) { this.rol = v; }
    public String getAccion() { return accion; }
    public void setAccion(String v) { this.accion = v; }
    public String getEntidad() { return entidad; }
    public void setEntidad(String v) { this.entidad = v; }
    public String getEntidadId() { return entidadId; }
    public void setEntidadId(String v) { this.entidadId = v; }
    public Object getDatosPrevios() { return datosPrevios; }
    public void setDatosPrevios(Object v) { this.datosPrevios = v; }
    public Object getDatosNuevos() { return datosNuevos; }
    public void setDatosNuevos(Object v) { this.datosNuevos = v; }
    public String getIp() { return ip; }
    public void setIp(String v) { this.ip = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime v) { this.timestamp = v; }
}
