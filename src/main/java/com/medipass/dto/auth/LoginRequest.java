package com.medipass.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "El correo es requerido")
    @Email(message = "Formato de correo inv\u00e1lido")
    private String email;

    @NotBlank(message = "La contrase\u00f1a es requerida")
    private String password;

    public LoginRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getPassword() { return password; }
    public void setPassword(String v) { this.password = v; }
}
