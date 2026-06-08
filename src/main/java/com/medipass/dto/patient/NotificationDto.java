package com.medipass.dto.patient;

public class NotificationDto {
    private String id;
    private String title;
    private String message;
    private String time;
    private String type;
    private boolean read;
    private String pqrsId;
    private boolean ratable;
    private int rating;

    public NotificationDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, title, message, time, type, pqrsId;
        private boolean read, ratable;
        private int rating;
        public Builder id(String v) { this.id = v; return this; }
        public Builder title(String v) { this.title = v; return this; }
        public Builder message(String v) { this.message = v; return this; }
        public Builder time(String v) { this.time = v; return this; }
        public Builder type(String v) { this.type = v; return this; }
        public Builder read(boolean v) { this.read = v; return this; }
        public Builder pqrsId(String v) { this.pqrsId = v; return this; }
        public Builder ratable(boolean v) { this.ratable = v; return this; }
        public Builder rating(int v) { this.rating = v; return this; }
        public NotificationDto build() {
            NotificationDto d = new NotificationDto();
            d.id = id; d.title = title; d.message = message;
            d.time = time; d.type = type; d.read = read;
            d.pqrsId = pqrsId; d.ratable = ratable; d.rating = rating;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getTitle() { return title; }
    public void setTitle(String v) { this.title = v; }
    public String getMessage() { return message; }
    public void setMessage(String v) { this.message = v; }
    public String getTime() { return time; }
    public void setTime(String v) { this.time = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
    public boolean isRead() { return read; }
    public void setRead(boolean v) { this.read = v; }
    public String getPqrsId() { return pqrsId; }
    public void setPqrsId(String v) { this.pqrsId = v; }
    public boolean isRatable() { return ratable; }
    public void setRatable(boolean v) { this.ratable = v; }
    public int getRating() { return rating; }
    public void setRating(int v) { this.rating = v; }
}
