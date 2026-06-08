package com.medipass.dto.patient;

public class ClinicalRecordSummaryDto {
    private String id;
    private String date;
    private String doctor;
    private String diagnosis;
    private String diagnosisCode;
    private String type;
    private String plan;

    public ClinicalRecordSummaryDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final ClinicalRecordSummaryDto d = new ClinicalRecordSummaryDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder date(String v) { d.date = v; return this; }
        public Builder doctor(String v) { d.doctor = v; return this; }
        public Builder diagnosis(String v) { d.diagnosis = v; return this; }
        public Builder diagnosisCode(String v) { d.diagnosisCode = v; return this; }
        public Builder type(String v) { d.type = v; return this; }
        public Builder plan(String v) { d.plan = v; return this; }
        public ClinicalRecordSummaryDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getDate() { return date; }
    public void setDate(String v) { this.date = v; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String v) { this.doctor = v; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String v) { this.diagnosis = v; }
    public String getDiagnosisCode() { return diagnosisCode; }
    public void setDiagnosisCode(String v) { this.diagnosisCode = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
    public String getPlan() { return plan; }
    public void setPlan(String v) { this.plan = v; }
}
