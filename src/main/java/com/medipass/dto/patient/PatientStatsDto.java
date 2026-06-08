package com.medipass.dto.patient;

public class PatientStatsDto {
    private String nextAppointmentDate;
    private String nextAppointmentTime;
    private String nextAppointmentDoctor;
    private long newLabResults;
    private long labResultsAlert;
    private String policyStatus;
    private String policyPlan;
    private long policyDaysLeft;
    private String policyExpiryDate;

    public PatientStatsDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String nextAppointmentDate, nextAppointmentTime, nextAppointmentDoctor;
        private long newLabResults, labResultsAlert, policyDaysLeft;
        private String policyStatus, policyPlan, policyExpiryDate;
        public Builder nextAppointmentDate(String v) { this.nextAppointmentDate = v; return this; }
        public Builder nextAppointmentTime(String v) { this.nextAppointmentTime = v; return this; }
        public Builder nextAppointmentDoctor(String v) { this.nextAppointmentDoctor = v; return this; }
        public Builder newLabResults(long v) { this.newLabResults = v; return this; }
        public Builder labResultsAlert(long v) { this.labResultsAlert = v; return this; }
        public Builder policyStatus(String v) { this.policyStatus = v; return this; }
        public Builder policyPlan(String v) { this.policyPlan = v; return this; }
        public Builder policyDaysLeft(long v) { this.policyDaysLeft = v; return this; }
        public Builder policyExpiryDate(String v) { this.policyExpiryDate = v; return this; }
        public PatientStatsDto build() {
            PatientStatsDto d = new PatientStatsDto();
            d.nextAppointmentDate = nextAppointmentDate; d.nextAppointmentTime = nextAppointmentTime;
            d.nextAppointmentDoctor = nextAppointmentDoctor; d.newLabResults = newLabResults;
            d.labResultsAlert = labResultsAlert; d.policyStatus = policyStatus;
            d.policyPlan = policyPlan; d.policyDaysLeft = policyDaysLeft;
            d.policyExpiryDate = policyExpiryDate;
            return d;
        }
    }

    public String getNextAppointmentDate() { return nextAppointmentDate; }
    public void setNextAppointmentDate(String v) { this.nextAppointmentDate = v; }
    public String getNextAppointmentTime() { return nextAppointmentTime; }
    public void setNextAppointmentTime(String v) { this.nextAppointmentTime = v; }
    public String getNextAppointmentDoctor() { return nextAppointmentDoctor; }
    public void setNextAppointmentDoctor(String v) { this.nextAppointmentDoctor = v; }
    public long getNewLabResults() { return newLabResults; }
    public void setNewLabResults(long v) { this.newLabResults = v; }
    public long getLabResultsAlert() { return labResultsAlert; }
    public void setLabResultsAlert(long v) { this.labResultsAlert = v; }
    public String getPolicyStatus() { return policyStatus; }
    public void setPolicyStatus(String v) { this.policyStatus = v; }
    public String getPolicyPlan() { return policyPlan; }
    public void setPolicyPlan(String v) { this.policyPlan = v; }
    public long getPolicyDaysLeft() { return policyDaysLeft; }
    public void setPolicyDaysLeft(long v) { this.policyDaysLeft = v; }
    public String getPolicyExpiryDate() { return policyExpiryDate; }
    public void setPolicyExpiryDate(String v) { this.policyExpiryDate = v; }
}
