package com.medipass.dto.doctor;

public class PatientListDto {
    private String id;
    private String name;
    private String lastVisit;
    private String specialty;
    private String status;
    private int age;

    public PatientListDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final PatientListDto d = new PatientListDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder name(String v) { d.name = v; return this; }
        public Builder lastVisit(String v) { d.lastVisit = v; return this; }
        public Builder specialty(String v) { d.specialty = v; return this; }
        public Builder status(String v) { d.status = v; return this; }
        public Builder age(int v) { d.age = v; return this; }
        public PatientListDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getLastVisit() { return lastVisit; }
    public void setLastVisit(String v) { this.lastVisit = v; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String v) { this.specialty = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public int getAge() { return age; }
    public void setAge(int v) { this.age = v; }
}
