package com.medipass.dto.lab;

public class LabOrderDto {
    private String id;
    private String patient;
    private String exam;
    private String doctor;
    private String requested;
    private String priority;

    public LabOrderDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, patient, exam, doctor, requested, priority;
        public Builder id(String v) { this.id = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder exam(String v) { this.exam = v; return this; }
        public Builder doctor(String v) { this.doctor = v; return this; }
        public Builder requested(String v) { this.requested = v; return this; }
        public Builder priority(String v) { this.priority = v; return this; }
        public LabOrderDto build() {
            LabOrderDto d = new LabOrderDto();
            d.id = id; d.patient = patient; d.exam = exam;
            d.doctor = doctor; d.requested = requested; d.priority = priority;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getExam() { return exam; }
    public void setExam(String v) { this.exam = v; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String v) { this.doctor = v; }
    public String getRequested() { return requested; }
    public void setRequested(String v) { this.requested = v; }
    public String getPriority() { return priority; }
    public void setPriority(String v) { this.priority = v; }
}
