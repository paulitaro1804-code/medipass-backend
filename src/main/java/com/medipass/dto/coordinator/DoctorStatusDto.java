package com.medipass.dto.coordinator;

public class DoctorStatusDto {
    private String id;
    private String name;
    private String specialty;
    private boolean available;
    private int patients;

    public DoctorStatusDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, name, specialty;
        private boolean available;
        private int patients;
        public Builder id(String v) { this.id = v; return this; }
        public Builder name(String v) { this.name = v; return this; }
        public Builder specialty(String v) { this.specialty = v; return this; }
        public Builder available(boolean v) { this.available = v; return this; }
        public Builder patients(int v) { this.patients = v; return this; }
        public DoctorStatusDto build() {
            DoctorStatusDto d = new DoctorStatusDto();
            d.id = id; d.name = name; d.specialty = specialty;
            d.available = available; d.patients = patients;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String v) { this.specialty = v; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean v) { this.available = v; }
    public int getPatients() { return patients; }
    public void setPatients(int v) { this.patients = v; }
}
