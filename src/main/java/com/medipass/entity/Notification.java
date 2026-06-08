package com.medipass.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    private String usuarioDestinoId;
    private String tipo;
    private String titulo;
    private String mensaje;
    private boolean leida;
    private String canal;
    private String pqrsId;
    private boolean calificado;
    private int calificacion;

    private LocalDateTime fechaEnvio;

    public Notification() { this.fechaEnvio = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Notification n = new Notification();
        public Builder id(String v) { n.id = v; return this; }
        public Builder usuarioDestinoId(String v) { n.usuarioDestinoId = v; return this; }
        public Builder tipo(String v) { n.tipo = v; return this; }
        public Builder titulo(String v) { n.titulo = v; return this; }
        public Builder mensaje(String v) { n.mensaje = v; return this; }
        public Builder leida(boolean v) { n.leida = v; return this; }
        public Builder canal(String v) { n.canal = v; return this; }
        public Builder pqrsId(String v) { n.pqrsId = v; return this; }
        public Builder calificado(boolean v) { n.calificado = v; return this; }
        public Builder calificacion(int v) { n.calificacion = v; return this; }
        public Builder fechaEnvio(LocalDateTime v) { n.fechaEnvio = v; return this; }
        public Notification build() { return n; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getUsuarioDestinoId() { return usuarioDestinoId; }
    public void setUsuarioDestinoId(String v) { this.usuarioDestinoId = v; }
    public String getTipo() { return tipo; }
    public void setTipo(String v) { this.tipo = v; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String v) { this.titulo = v; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String v) { this.mensaje = v; }
    public boolean isLeida() { return leida; }
    public void setLeida(boolean v) { this.leida = v; }
    public String getCanal() { return canal; }
    public void setCanal(String v) { this.canal = v; }
    public String getPqrsId() { return pqrsId; }
    public void setPqrsId(String v) { this.pqrsId = v; }
    public boolean isCalificado() { return calificado; }
    public void setCalificado(boolean v) { this.calificado = v; }
    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int v) { this.calificacion = v; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime v) { this.fechaEnvio = v; }
}
