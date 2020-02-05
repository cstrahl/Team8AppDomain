package com.appdomain.accesscontrol.security.contracts;


public class UserRegistrationRequest {

    private String userEmail;

    private boolean approved;

    private String denialReason;

    private String role;

    public UserRegistrationRequest() {
    }

    public UserRegistrationRequest(String userEmail, boolean approved, String denialReason, String role) {
        this.userEmail = userEmail;
        this.approved = approved;
        this.denialReason = denialReason;
        this.role = role;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getDenialReason() {
        return denialReason;
    }

    public String getRole() {
        return role;
    }
}
