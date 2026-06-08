package com.medipass.dto.support;

public class TicketDto {
    private String id;
    private String patient;
    private String subject;
    private String description;
    private String type;
    private String priority;
    private String created;
    private String status;

    public TicketDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, patient, subject, description, type, priority, created, status;
        public Builder id(String v) { this.id = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder subject(String v) { this.subject = v; return this; }
        public Builder description(String v) { this.description = v; return this; }
        public Builder type(String v) { this.type = v; return this; }
        public Builder priority(String v) { this.priority = v; return this; }
        public Builder created(String v) { this.created = v; return this; }
        public Builder status(String v) { this.status = v; return this; }
        public TicketDto build() {
            TicketDto d = new TicketDto();
            d.id = id; d.patient = patient; d.subject = subject; d.description = description;
            d.type = type; d.priority = priority; d.created = created; d.status = status;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getPatient() { return patient; }
    public void setPatient(String v) { this.patient = v; }
    public String getSubject() { return subject; }
    public void setSubject(String v) { this.subject = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
    public String getPriority() { return priority; }
    public void setPriority(String v) { this.priority = v; }
    public String getCreated() { return created; }
    public void setCreated(String v) { this.created = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
}
