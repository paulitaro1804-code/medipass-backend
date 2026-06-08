package com.medipass.dto.support;

public class SupportStatsDto {
    private long openTickets;
    private long newToday;
    private long inProgress;
    private long resolvedToday;
    private long highPriority;

    public SupportStatsDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long openTickets, newToday, inProgress, resolvedToday, highPriority;
        public Builder openTickets(long v) { this.openTickets = v; return this; }
        public Builder newToday(long v) { this.newToday = v; return this; }
        public Builder inProgress(long v) { this.inProgress = v; return this; }
        public Builder resolvedToday(long v) { this.resolvedToday = v; return this; }
        public Builder highPriority(long v) { this.highPriority = v; return this; }
        public SupportStatsDto build() {
            SupportStatsDto d = new SupportStatsDto();
            d.openTickets = openTickets; d.newToday = newToday; d.inProgress = inProgress;
            d.resolvedToday = resolvedToday; d.highPriority = highPriority;
            return d;
        }
    }

    public long getOpenTickets() { return openTickets; }
    public void setOpenTickets(long v) { this.openTickets = v; }
    public long getNewToday() { return newToday; }
    public void setNewToday(long v) { this.newToday = v; }
    public long getInProgress() { return inProgress; }
    public void setInProgress(long v) { this.inProgress = v; }
    public long getResolvedToday() { return resolvedToday; }
    public void setResolvedToday(long v) { this.resolvedToday = v; }
    public long getHighPriority() { return highPriority; }
    public void setHighPriority(long v) { this.highPriority = v; }
}
