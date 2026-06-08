package com.medipass.dto.admin;

public class CreateUserDto {
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private String role;
    private String documento;

    public CreateUserDto() {}

    public String getNombres()    { return nombres; }
    public void   setNombres(String v)    { this.nombres = v; }
    public String getApellidos()  { return apellidos; }
    public void   setApellidos(String v)  { this.apellidos = v; }
    public String getEmail()      { return email; }
    public void   setEmail(String v)      { this.email = v; }
    public String getPassword()   { return password; }
    public void   setPassword(String v)   { this.password = v; }
    public String getRole()       { return role; }
    public void   setRole(String v)       { this.role = v; }
    public String getDocumento()  { return documento; }
    public void   setDocumento(String v)  { this.documento = v; }
}
