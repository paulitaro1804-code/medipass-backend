package com.medipass.dto.auth;

public class LoginResponse {
    private String token;
    private String role;
    private String userId;
    private String name;

    public LoginResponse() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token, role, userId, name;
        public Builder token(String v) { this.token = v; return this; }
        public Builder role(String v) { this.role = v; return this; }
        public Builder userId(String v) { this.userId = v; return this; }
        public Builder name(String v) { this.name = v; return this; }
        public LoginResponse build() {
            LoginResponse r = new LoginResponse();
            r.token = token; r.role = role; r.userId = userId; r.name = name;
            return r;
        }
    }

    public String getToken() { return token; }
    public void setToken(String v) { this.token = v; }
    public String getRole() { return role; }
    public void setRole(String v) { this.role = v; }
    public String getUserId() { return userId; }
    public void setUserId(String v) { this.userId = v; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
}
