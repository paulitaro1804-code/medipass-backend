package com.medipass.dto.doctor;

public class PatientScheduleEntryDto {
    private String id;
    private String pacienteId;
    private String time;
    private String name;
    private int age;
    private String reason;
    private String status;
    private boolean hasHistory;

    public PatientScheduleEntryDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final PatientScheduleEntryDto d = new PatientScheduleEntryDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder pacienteId(String v) { d.pacienteId = v; return this; }
        public Builder time(String v) { d.time = v; return this; }
        public Builder name(String v) { d.name = v; return this; }
        public Builder age(int v) { d.age = v; return this; }
        public Builder reason(String v) { d.reason = v; return this; }
        public Builder status(String v) { d.status = v; return this; }
        public Builder hasHistory(boolean v) { d.hasHistory = v; return this; }
        public PatientScheduleEntryDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String v) { this.pacienteId = v; }
    public String getTime() { return time; }
    public void setTime(String v) { this.time = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public int getAge() { return age; }
    public void setAge(int v) { this.age = v; }
    public String getReason() { return reason; }
    public void setReason(String v) { this.reason = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public boolean isHasHistory() { return hasHistory; }
    public void setHasHistory(boolean v) { this.hasHistory = v; }
}
