package com.medipass.dto.coordinator;

public class ScheduleEntryDto {
    private String id;
    private String time;
    private String patient;
    private String doctor;
    private String specialty;
    private String status;

    public ScheduleEntryDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, time, patient, doctor, specialty, status;
        public Builder id(String v) { this.id = v; return this; }
        public Builder time(String v) { this.time = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder doctor(String v) { this.doctor = v; return this; }
        public Builder specialty(String v) { this.specialty = v; return this; }
        public Builder status(String v) { this.status = v; return this; }
        public ScheduleEntryDto build() {
            ScheduleEntryDto d = new ScheduleEntryDto();
            d.id = id; d.time = time; d.patient = patient;
            d.doctor = doctor; d.specialty = specialty; d.status = status;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getTime() { return time; }
    public void setTime(String v) { this.time = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String v) { this.doctor = v; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String v) { this.specialty = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
}
