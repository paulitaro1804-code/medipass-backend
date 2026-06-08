package com.medipass.dto.admin;

public class UserListDto {
    private String id;
    private String name;
    private String email;
    private String role;
    private String status;
    private String createdAt;

    public UserListDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final UserListDto d = new UserListDto();
        public Builder id(String v) { d.id = v; return this; }
        public Builder name(String v) { d.name = v; return this; }
        public Builder email(String v) { d.email = v; return this; }
        public Builder role(String v) { d.role = v; return this; }
        public Builder status(String v) { d.status = v; return this; }
        public Builder createdAt(String v) { d.createdAt = v; return this; }
        public UserListDto build() { return d; }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getRole() { return role; }
    public void setRole(String v) { this.role = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String v) { this.createdAt = v; }
}
