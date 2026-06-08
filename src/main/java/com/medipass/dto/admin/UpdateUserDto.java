package com.medipass.dto.admin;

public class UpdateUserDto {
    private String nombres;
    private String apellidos;
    private String email;
    private String role;
    private String documento;
    private String estado;

    public UpdateUserDto() {}

    public String getNombres()    { return nombres; }
    public void   setNombres(String v)    { this.nombres = v; }
    public String getApellidos()  { return apellidos; }
    public void   setApellidos(String v)  { this.apellidos = v; }
    public String getEmail()      { return email; }
    public void   setEmail(String v)      { this.email = v; }
    public String getRole()       { return role; }
    public void   setRole(String v)       { this.role = v; }
    public String getDocumento()  { return documento; }
    public void   setDocumento(String v)  { this.documento = v; }
    public String getEstado()     { return estado; }
    public void   setEstado(String v)     { this.estado = v; }
}
