package com.medipass.dto.validator;

public class ValidatorStatsDto {
    private long pendingRequests;
    private long newToday;
    private long approvedToday;
    private long rejectedToday;
    private String rejectedReason;
    private long expiringPolicies;
    private long expiringDays;

    public ValidatorStatsDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long pendingRequests, newToday, approvedToday, rejectedToday;
        private String rejectedReason;
        private long expiringPolicies, expiringDays;
        public Builder pendingRequests(long v) { this.pendingRequests = v; return this; }
        public Builder newToday(long v) { this.newToday = v; return this; }
        public Builder approvedToday(long v) { this.approvedToday = v; return this; }
        public Builder rejectedToday(long v) { this.rejectedToday = v; return this; }
        public Builder rejectedReason(String v) { this.rejectedReason = v; return this; }
        public Builder expiringPolicies(long v) { this.expiringPolicies = v; return this; }
        public Builder expiringDays(long v) { this.expiringDays = v; return this; }
        public ValidatorStatsDto build() {
            ValidatorStatsDto d = new ValidatorStatsDto();
            d.pendingRequests = pendingRequests; d.newToday = newToday;
            d.approvedToday = approvedToday; d.rejectedToday = rejectedToday;
            d.rejectedReason = rejectedReason; d.expiringPolicies = expiringPolicies;
            d.expiringDays = expiringDays;
            return d;
        }
    }

    public long getPendingRequests() { return pendingRequests; }
    public void setPendingRequests(long v) { this.pendingRequests = v; }
    public long getNewToday() { return newToday; }
    public void setNewToday(long v) { this.newToday = v; }
    public long getApprovedToday() { return approvedToday; }
    public void setApprovedToday(long v) { this.approvedToday = v; }
    public long getRejectedToday() { return rejectedToday; }
    public void setRejectedToday(long v) { this.rejectedToday = v; }
    public String getRejectedReason() { return rejectedReason; }
    public void setRejectedReason(String v) { this.rejectedReason = v; }
    public long getExpiringPolicies() { return expiringPolicies; }
    public void setExpiringPolicies(long v) { this.expiringPolicies = v; }
    public long getExpiringDays() { return expiringDays; }
    public void setExpiringDays(long v) { this.expiringDays = v; }
}
