package com.medipass.entity;

import com.medipass.enums.Role;
import com.medipass.enums.UserStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String nombres;
    private String apellidos;
    private String passwordHash;
    private String documento;
    private String especialidad;
    private String telefono;

    private Role role;
    private UserStatus estado;

    private String policyId;

    private LocalDateTime fechaCreacion;

    public User() { this.fechaCreacion = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final User u = new User();
        public Builder id(String v) { u.id = v; return this; }
        public Builder email(String v) { u.email = v; return this; }
        public Builder nombres(String v) { u.nombres = v; return this; }
        public Builder apellidos(String v) { u.apellidos = v; return this; }
        public Builder passwordHash(String v) { u.passwordHash = v; return this; }
        public Builder documento(String v) { u.documento = v; return this; }
        public Builder especialidad(String v) { u.especialidad = v; return this; }
        public Builder telefono(String v) { u.telefono = v; return this; }
        public Builder role(Role v) { u.role = v; return this; }
        public Builder estado(UserStatus v) { u.estado = v; return this; }
        public Builder policyId(String v) { u.policyId = v; return this; }
        public Builder fechaCreacion(LocalDateTime v) { u.fechaCreacion = v; return this; }
        public User build() { return u; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getNombres() { return nombres; }
    public void setNombres(String v) { this.nombres = v; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String v) { this.apellidos = v; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String v) { this.passwordHash = v; }
    public String getDocumento() { return documento; }
    public void setDocumento(String v) { this.documento = v; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String v) { this.especialidad = v; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String v) { this.telefono = v; }
    public Role getRole() { return role; }
    public void setRole(Role v) { this.role = v; }
    public UserStatus getEstado() { return estado; }
    public void setEstado(UserStatus v) { this.estado = v; }
    public String getPolicyId() { return policyId; }
    public void setPolicyId(String v) { this.policyId = v; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime v) { this.fechaCreacion = v; }

    public String getFullName() { return nombres + " " + apellidos; }
}
