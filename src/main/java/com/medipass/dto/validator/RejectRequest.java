package com.medipass.dto.validator;

import jakarta.validation.constraints.NotBlank;

public class RejectRequest {
    @NotBlank(message = "El motivo de rechazo es requerido")
    private String reason;

    public RejectRequest() {}

    public String getReason() { return reason; }
    public void setReason(String v) { this.reason = v; }
}
