package com.medipass.dto.doctor;

public class ClinicalRecordDto {
    private String id;
    private String patient;
    private String date;
    private String diagnosis;
    private String diagnosisCode;
    private String type;
    private String planTratamiento;

    public ClinicalRecordDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final ClinicalRecordDto d = new ClinicalRecordDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder patient(String v) { d.patient = v; return this; }
        public Builder date(String v) { d.date = v; return this; }
        public Builder diagnosis(String v) { d.diagnosis = v; return this; }
        public Builder diagnosisCode(String v) { d.diagnosisCode = v; return this; }
        public Builder type(String v) { d.type = v; return this; }
        public Builder planTratamiento(String v) { d.planTratamiento = v; return this; }
        public ClinicalRecordDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getDate() { return date; }
    public void setDate(String v) { this.date = v; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String v) { this.diagnosis = v; }
    public String getDiagnosisCode() { return diagnosisCode; }
    public void setDiagnosisCode(String v) { this.diagnosisCode = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
    public String getPlanTratamiento() { return planTratamiento; }
    public void setPlanTratamiento(String v) { this.planTratamiento = v; }
}
