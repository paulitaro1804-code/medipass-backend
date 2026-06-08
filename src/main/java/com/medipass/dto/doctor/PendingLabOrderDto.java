package com.medipass.dto.doctor;

public class PendingLabOrderDto {
    private String id;
    private String patient;
    private String exam;
    private String requested;
    private String status;

    public PendingLabOrderDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final PendingLabOrderDto d = new PendingLabOrderDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder patient(String v) { d.patient = v; return this; }
        public Builder exam(String v) { d.exam = v; return this; }
        public Builder requested(String v) { d.requested = v; return this; }
        public Builder status(String v) { d.status = v; return this; }
        public PendingLabOrderDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getExam() { return exam; }
    public void setExam(String v) { this.exam = v; }
    public String getRequested() { return requested; }
    public void setRequested(String v) { this.requested = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
}
