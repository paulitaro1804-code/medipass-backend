package com.medipass.dto.patient;

public class LabResultDto {
    private String id;
    private String name;
    private String date;
    private String status;
    private boolean alert;
    private String downloadUrl;

    public LabResultDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, name, date, status, downloadUrl;
        private boolean alert;
        public Builder id(String v) { this.id = v; return this; }
        public Builder name(String v) { this.name = v; return this; }
        public Builder date(String v) { this.date = v; return this; }
        public Builder status(String v) { this.status = v; return this; }
        public Builder alert(boolean v) { this.alert = v; return this; }
        public Builder downloadUrl(String v) { this.downloadUrl = v; return this; }
        public LabResultDto build() {
            LabResultDto d = new LabResultDto();
            d.id = id; d.name = name; d.date = date; d.status = status;
            d.alert = alert; d.downloadUrl = downloadUrl;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getDate() { return date; }
    public void setDate(String v) { this.date = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public boolean isAlert() { return alert; }
    public void setAlert(boolean v) { this.alert = v; }
    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String v) { this.downloadUrl = v; }
}
