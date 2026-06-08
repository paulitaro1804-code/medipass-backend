package com.medipass.dto.doctor;

public class DoctorStatsDto {
    private long patientsToday;
    private long patientsAttended;
    private long waitingPatients;
    private long recordsToday;
    private long pendingLabResults;

    public DoctorStatsDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long patientsToday, patientsAttended, waitingPatients, recordsToday, pendingLabResults;
        public Builder patientsToday(long v) { this.patientsToday = v; return this; }
        public Builder patientsAttended(long v) { this.patientsAttended = v; return this; }
        public Builder waitingPatients(long v) { this.waitingPatients = v; return this; }
        public Builder recordsToday(long v) { this.recordsToday = v; return this; }
        public Builder pendingLabResults(long v) { this.pendingLabResults = v; return this; }
        public DoctorStatsDto build() {
            DoctorStatsDto d = new DoctorStatsDto();
            d.patientsToday = patientsToday; d.patientsAttended = patientsAttended;
            d.waitingPatients = waitingPatients; d.recordsToday = recordsToday;
            d.pendingLabResults = pendingLabResults;
            return d;
        }
    }

    public long getPatientsToday() { return patientsToday; }
    public void setPatientsToday(long v) { this.patientsToday = v; }
    public long getPatientsAttended() { return patientsAttended; }
    public void setPatientsAttended(long v) { this.patientsAttended = v; }
    public long getWaitingPatients() { return waitingPatients; }
    public void setWaitingPatients(long v) { this.waitingPatients = v; }
    public long getRecordsToday() { return recordsToday; }
    public void setRecordsToday(long v) { this.recordsToday = v; }
    public long getPendingLabResults() { return pendingLabResults; }
    public void setPendingLabResults(long v) { this.pendingLabResults = v; }
}
