package com.medipass.dto.admin;

public class AdminReportDto {
    private long totalUsers;
    private long totalAppointments;
    private long totalPolicies;
    private long totalLabResults;
    private long totalPqrs;
    private long resolvedPqrs;
    private long activePolicies;
    private long expiredPolicies;

    public AdminReportDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final AdminReportDto d = new AdminReportDto();
        public Builder totalUsers(long v) { d.totalUsers = v; return this; }
        public Builder totalAppointments(long v) { d.totalAppointments = v; return this; }
        public Builder totalPolicies(long v) { d.totalPolicies = v; return this; }
        public Builder totalLabResults(long v) { d.totalLabResults = v; return this; }
        public Builder totalPqrs(long v) { d.totalPqrs = v; return this; }
        public Builder resolvedPqrs(long v) { d.resolvedPqrs = v; return this; }
        public Builder activePolicies(long v) { d.activePolicies = v; return this; }
        public Builder expiredPolicies(long v) { d.expiredPolicies = v; return this; }
        public AdminReportDto build() { return d; }
    }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long v) { this.totalUsers = v; }
    public long getTotalAppointments() { return totalAppointments; }
    public void setTotalAppointments(long v) { this.totalAppointments = v; }
    public long getTotalPolicies() { return totalPolicies; }
    public void setTotalPolicies(long v) { this.totalPolicies = v; }
    public long getTotalLabResults() { return totalLabResults; }
    public void setTotalLabResults(long v) { this.totalLabResults = v; }
    public long getTotalPqrs() { return totalPqrs; }
    public void setTotalPqrs(long v) { this.totalPqrs = v; }
    public long getResolvedPqrs() { return resolvedPqrs; }
    public void setResolvedPqrs(long v) { this.resolvedPqrs = v; }
    public long getActivePolicies() { return activePolicies; }
    public void setActivePolicies(long v) { this.activePolicies = v; }
    public long getExpiredPolicies() { return expiredPolicies; }
    public void setExpiredPolicies(long v) { this.expiredPolicies = v; }
}
