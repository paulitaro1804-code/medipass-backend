package com.medipass.dto.coordinator;

public class WaitlistEntryDto {
    private String id;
    private String patient;
    private String priority;
    private String specialty;
    private String waitTime;
    private String plan;

    public WaitlistEntryDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, patient, priority, specialty, waitTime, plan;
        public Builder id(String v) { this.id = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder priority(String v) { this.priority = v; return this; }
        public Builder specialty(String v) { this.specialty = v; return this; }
        public Builder waitTime(String v) { this.waitTime = v; return this; }
        public Builder plan(String v) { this.plan = v; return this; }
        public WaitlistEntryDto build() {
            WaitlistEntryDto d = new WaitlistEntryDto();
            d.id = id; d.patient = patient; d.priority = priority;
            d.specialty = specialty; d.waitTime = waitTime; d.plan = plan;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getPriority() { return priority; }
    public void setPriority(String v) { this.priority = v; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String v) { this.specialty = v; }
    public String getWaitTime() { return waitTime; }
    public void setWaitTime(String v) { this.waitTime = v; }
    public String getPlan() { return plan; }
    public void setPlan(String v) { this.plan = v; }
}
