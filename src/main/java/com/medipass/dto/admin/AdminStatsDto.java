package com.medipass.dto.admin;

public class AdminStatsDto {
    private long activePatients;
    private long todayAppointments;
    private long pendingWaiting;
    private long expiringPolicies;
    private long openTickets;
    private long priorityTickets;

    public AdminStatsDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long activePatients, todayAppointments, pendingWaiting;
        private long expiringPolicies, openTickets, priorityTickets;
        public Builder activePatients(long v) { this.activePatients = v; return this; }
        public Builder todayAppointments(long v) { this.todayAppointments = v; return this; }
        public Builder pendingWaiting(long v) { this.pendingWaiting = v; return this; }
        public Builder expiringPolicies(long v) { this.expiringPolicies = v; return this; }
        public Builder openTickets(long v) { this.openTickets = v; return this; }
        public Builder priorityTickets(long v) { this.priorityTickets = v; return this; }
        public AdminStatsDto build() {
            AdminStatsDto d = new AdminStatsDto();
            d.activePatients = activePatients; d.todayAppointments = todayAppointments;
            d.pendingWaiting = pendingWaiting; d.expiringPolicies = expiringPolicies;
            d.openTickets = openTickets; d.priorityTickets = priorityTickets;
            return d;
        }
    }

    public long getActivePatients() { return activePatients; }
    public void setActivePatients(long v) { this.activePatients = v; }
    public long getTodayAppointments() { return todayAppointments; }
    public void setTodayAppointments(long v) { this.todayAppointments = v; }
    public long getPendingWaiting() { return pendingWaiting; }
    public void setPendingWaiting(long v) { this.pendingWaiting = v; }
    public long getExpiringPolicies() { return expiringPolicies; }
    public void setExpiringPolicies(long v) { this.expiringPolicies = v; }
    public long getOpenTickets() { return openTickets; }
    public void setOpenTickets(long v) { this.openTickets = v; }
    public long getPriorityTickets() { return priorityTickets; }
    public void setPriorityTickets(long v) { this.priorityTickets = v; }
}
