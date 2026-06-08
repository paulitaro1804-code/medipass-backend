package com.medipass.dto.coordinator;

public class PatientOptionDto {

    private String id;
    private String name;
    private String email;

    public PatientOptionDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final PatientOptionDto d = new PatientOptionDto();
        public Builder id(String v)    { d.id = v;    return this; }
        public Builder name(String v)  { d.name = v;  return this; }
        public Builder email(String v) { d.email = v; return this; }
        public PatientOptionDto build() { return d; }
    }

    public String getId()    { return id; }
    public void setId(String v)    { this.id = v; }
    public String getName()  { return name; }
    public void setName(String v)  { this.name = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
}
