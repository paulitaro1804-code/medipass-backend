package com.medipass.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "El nombre es requerido")
    private String firstName;

    @NotBlank(message = "El apellido es requerido")
    private String lastName;

    @NotBlank(message = "El documento es requerido")
    private String document;

    @NotBlank(message = "El número de póliza es requerido")
    private String policyNumber;

    @NotBlank(message = "El correo es requerido")
    @Email(message = "Formato de correo inválido")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private String insurer;

    private String planDuration;

    private String phone;

    public RegisterRequest() {}

    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }
    public String getLastName() { return lastName; }
    public void setLastName(String v) { this.lastName = v; }
    public String getDocument() { return document; }
    public void setDocument(String v) { this.document = v; }
    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String v) { this.policyNumber = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getPassword() { return password; }
    public void setPassword(String v) { this.password = v; }
    public String getInsurer() { return insurer; }
    public void setInsurer(String v) { this.insurer = v; }
    public String getPlanDuration() { return planDuration; }
    public void setPlanDuration(String v) { this.planDuration = v; }
    public String getPhone() { return phone; }
    public void setPhone(String v) { this.phone = v; }
}
