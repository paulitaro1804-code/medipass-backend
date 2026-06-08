package com.medipass.dto.lab;

public class LabStatsDto {
    private long pendingOrders;
    private long urgentOrders;
    private long processing;
    private long publishedToday;
    private long criticalAlerts;

    public LabStatsDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long pendingOrders, urgentOrders, processing, publishedToday, criticalAlerts;
        public Builder pendingOrders(long v) { this.pendingOrders = v; return this; }
        public Builder urgentOrders(long v) { this.urgentOrders = v; return this; }
        public Builder processing(long v) { this.processing = v; return this; }
        public Builder publishedToday(long v) { this.publishedToday = v; return this; }
        public Builder criticalAlerts(long v) { this.criticalAlerts = v; return this; }
        public LabStatsDto build() {
            LabStatsDto d = new LabStatsDto();
            d.pendingOrders = pendingOrders; d.urgentOrders = urgentOrders;
            d.processing = processing; d.publishedToday = publishedToday;
            d.criticalAlerts = criticalAlerts;
            return d;
        }
    }

    public long getPendingOrders() { return pendingOrders; }
    public void setPendingOrders(long v) { this.pendingOrders = v; }
    public long getUrgentOrders() { return urgentOrders; }
    public void setUrgentOrders(long v) { this.urgentOrders = v; }
    public long getProcessing() { return processing; }
    public void setProcessing(long v) { this.processing = v; }
    public long getPublishedToday() { return publishedToday; }
    public void setPublishedToday(long v) { this.publishedToday = v; }
    public long getCriticalAlerts() { return criticalAlerts; }
    public void setCriticalAlerts(long v) { this.criticalAlerts = v; }
}
