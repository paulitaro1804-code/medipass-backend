package com.medipass.dto.coordinator;

public class CoordinatorStatsDto {
    private long todayAppointments;
    private long completedAppointments;
    private long waitingPatients;
    private long priorityWaiting;
    private long activeDoctors;
    private long totalDoctors;
    private long waitlistCount;
    private long urgentWaitlist;

    public CoordinatorStatsDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long todayAppointments, completedAppointments, waitingPatients, priorityWaiting;
        private long activeDoctors, totalDoctors, waitlistCount, urgentWaitlist;
        public Builder todayAppointments(long v) { this.todayAppointments = v; return this; }
        public Builder completedAppointments(long v) { this.completedAppointments = v; return this; }
        public Builder waitingPatients(long v) { this.waitingPatients = v; return this; }
        public Builder priorityWaiting(long v) { this.priorityWaiting = v; return this; }
        public Builder activeDoctors(long v) { this.activeDoctors = v; return this; }
        public Builder totalDoctors(long v) { this.totalDoctors = v; return this; }
        public Builder waitlistCount(long v) { this.waitlistCount = v; return this; }
        public Builder urgentWaitlist(long v) { this.urgentWaitlist = v; return this; }
        public CoordinatorStatsDto build() {
            CoordinatorStatsDto d = new CoordinatorStatsDto();
            d.todayAppointments = todayAppointments; d.completedAppointments = completedAppointments;
            d.waitingPatients = waitingPatients; d.priorityWaiting = priorityWaiting;
            d.activeDoctors = activeDoctors; d.totalDoctors = totalDoctors;
            d.waitlistCount = waitlistCount; d.urgentWaitlist = urgentWaitlist;
            return d;
        }
    }

    public long getTodayAppointments() { return todayAppointments; }
    public void setTodayAppointments(long v) { this.todayAppointments = v; }
    public long getCompletedAppointments() { return completedAppointments; }
    public void setCompletedAppointments(long v) { this.completedAppointments = v; }
    public long getWaitingPatients() { return waitingPatients; }
    public void setWaitingPatients(long v) { this.waitingPatients = v; }
    public long getPriorityWaiting() { return priorityWaiting; }
    public void setPriorityWaiting(long v) { this.priorityWaiting = v; }
    public long getActiveDoctors() { return activeDoctors; }
    public void setActiveDoctors(long v) { this.activeDoctors = v; }
    public long getTotalDoctors() { return totalDoctors; }
    public void setTotalDoctors(long v) { this.totalDoctors = v; }
    public long getWaitlistCount() { return waitlistCount; }
    public void setWaitlistCount(long v) { this.waitlistCount = v; }
    public long getUrgentWaitlist() { return urgentWaitlist; }
    public void setUrgentWaitlist(long v) { this.urgentWaitlist = v; }
}
