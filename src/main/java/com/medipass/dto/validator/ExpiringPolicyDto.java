package com.medipass.dto.validator;

public class ExpiringPolicyDto {
    private String id;
    private String patient;
    private String policy;
    private long daysLeft;
    private String plan;

    public ExpiringPolicyDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, patient, policy, plan;
        private long daysLeft;
        public Builder id(String v) { this.id = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder policy(String v) { this.policy = v; return this; }
        public Builder daysLeft(long v) { this.daysLeft = v; return this; }
        public Builder plan(String v) { this.plan = v; return this; }
        public ExpiringPolicyDto build() {
            ExpiringPolicyDto d = new ExpiringPolicyDto();
            d.id = id; d.patient = patient; d.policy = policy;
            d.daysLeft = daysLeft; d.plan = plan;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getPolicy() { return policy; }
    public void setPolicy(String v) { this.policy = v; }
    public long getDaysLeft() { return daysLeft; }
    public void setDaysLeft(long v) { this.daysLeft = v; }
    public String getPlan() { return plan; }
    public void setPlan(String v) { this.plan = v; }
}
