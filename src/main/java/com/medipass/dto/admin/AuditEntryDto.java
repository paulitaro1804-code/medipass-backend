package com.medipass.dto.admin;

public class AuditEntryDto {
    private String action;
    private String user;
    private String time;
    private String type;

    public AuditEntryDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String action, user, time, type;
        public Builder action(String v) { this.action = v; return this; }
        public Builder user(String v) { this.user = v; return this; }
        public Builder time(String v) { this.time = v; return this; }
        public Builder type(String v) { this.type = v; return this; }
        public AuditEntryDto build() {
            AuditEntryDto d = new AuditEntryDto();
            d.action = action; d.user = user; d.time = time; d.type = type;
            return d;
        }
    }

    public String getAction() { return action; }
    public void setAction(String v) { this.action = v; }
    public String getUser() { return user; }
    public void setUser(String v) { this.user = v; }
    public String getTime() { return time; }
    public void setTime(String v) { this.time = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
}
