package com.medipass.dto.lab;

public class CriticalAlertDto {
    private String id;
    private String patient;
    private String exam;
    private String value;
    private String normal;
    private String doctor;
    private String time;

    public CriticalAlertDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, patient, exam, value, normal, doctor, time;
        public Builder id(String v) { this.id = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder exam(String v) { this.exam = v; return this; }
        public Builder value(String v) { this.value = v; return this; }
        public Builder normal(String v) { this.normal = v; return this; }
        public Builder doctor(String v) { this.doctor = v; return this; }
        public Builder time(String v) { this.time = v; return this; }
        public CriticalAlertDto build() {
            CriticalAlertDto d = new CriticalAlertDto();
            d.id = id; d.patient = patient; d.exam = exam; d.value = value;
            d.normal = normal; d.doctor = doctor; d.time = time;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getExam() { return exam; }
    public void setExam(String v) { this.exam = v; }
    public String getValue() { return value; }
    public void setValue(String v) { this.value = v; }
    public String getNormal() { return normal; }
    public void setNormal(String v) { this.normal = v; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String v) { this.doctor = v; }
    public String getTime() { return time; }
    public void setTime(String v) { this.time = v; }
}
