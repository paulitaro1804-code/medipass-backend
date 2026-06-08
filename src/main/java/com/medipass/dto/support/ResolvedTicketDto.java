package com.medipass.dto.support;

public class ResolvedTicketDto {
    private String id;
    private String patient;
    private String subject;
    private String description;
    private String type;
    private String resolved;
    private String agentName;
    private int satisfaction;

    public ResolvedTicketDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, patient, subject, description, type, resolved, agentName;
        private int satisfaction;
        public Builder id(String v) { this.id = v; return this; }
        public Builder patient(String v) { this.patient = v; return this; }
        public Builder subject(String v) { this.subject = v; return this; }
        public Builder description(String v) { this.description = v; return this; }
        public Builder type(String v) { this.type = v; return this; }
        public Builder resolved(String v) { this.resolved = v; return this; }
        public Builder agentName(String v) { this.agentName = v; return this; }
        public Builder satisfaction(int v) { this.satisfaction = v; return this; }
        public ResolvedTicketDto build() {
            ResolvedTicketDto d = new ResolvedTicketDto();
            d.id = id; d.patient = patient; d.subject = subject; d.description = description;
            d.type = type; d.resolved = resolved; d.agentName = agentName; d.satisfaction = satisfaction;
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
    public String getResolved() { return resolved; }
    public void setResolved(String v) { this.resolved = v; }
    public String getAgentName() { return agentName; }
    public void setAgentName(String v) { this.agentName = v; }
    public int getSatisfaction() { return satisfaction; }
    public void setSatisfaction(int v) { this.satisfaction = v; }
}
