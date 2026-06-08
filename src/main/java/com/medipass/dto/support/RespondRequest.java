package com.medipass.dto.support;

import jakarta.validation.constraints.NotBlank;

public class RespondRequest {
    @NotBlank(message = "El mensaje es requerido")
    private String message;

    public RespondRequest() {}

    public String getMessage() { return message; }
    public void setMessage(String v) { this.message = v; }
}
