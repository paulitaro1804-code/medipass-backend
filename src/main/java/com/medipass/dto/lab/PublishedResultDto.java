package com.medipass.dto.lab;

public class PublishedResultDto {
    private String id;
    private String patient;
    private String exam;
    private String date;
    private String status;

    public PublishedResultDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, patient, exam, date, status;
        public Builder id(String v) { this.id = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder exam(String v) { this.exam = v; return this; }
        public Builder date(String v) { this.date = v; return this; }
        public Builder status(String v) { this.status = v; return this; }
        public PublishedResultDto build() {
            PublishedResultDto d = new PublishedResultDto();
            d.id = id; d.patient = patient; d.exam = exam; d.date = date; d.status = status;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getExam() { return exam; }
    public void setExam(String v) { this.exam = v; }
    public String getDate() { return date; }
    public void setDate(String v) { this.date = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
}
