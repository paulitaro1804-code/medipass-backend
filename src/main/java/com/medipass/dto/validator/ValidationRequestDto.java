package com.medipass.dto.validator;

public class ValidationRequestDto {
    private String id;
    private String patient;
    private String document;
    private String policy;
    private String insurer;
    private String plan;
    private String submitted;
    private String status;

    public ValidationRequestDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, patient, document, policy, insurer, plan, submitted, status;
        public Builder id(String v) { this.id = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder document(String v) { this.document = v; return this; }
        public Builder policy(String v) { this.policy = v; return this; }
        public Builder insurer(String v) { this.insurer = v; return this; }
        public Builder plan(String v) { this.plan = v; return this; }
        public Builder submitted(String v) { this.submitted = v; return this; }
        public Builder status(String v) { this.status = v; return this; }
        public ValidationRequestDto build() {
            ValidationRequestDto d = new ValidationRequestDto();
            d.id = id; d.patient = patient; d.document = document; d.policy = policy;
            d.insurer = insurer; d.plan = plan; d.submitted = submitted; d.status = status;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getDocument() { return document; }
    public void setDocument(String v) { this.document = v; }
    public String getPolicy() { return policy; }
    public void setPolicy(String v) { this.policy = v; }
    public String getInsurer() { return insurer; }
    public void setInsurer(String v) { this.insurer = v; }
    public String getPlan() { return plan; }
    public void setPlan(String v) { this.plan = v; }
    public String getSubmitted() { return submitted; }
    public void setSubmitted(String v) { this.submitted = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
}
