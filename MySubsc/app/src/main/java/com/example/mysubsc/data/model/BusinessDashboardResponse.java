package com.example.mysubsc.data.model;

public class BusinessDashboardResponse {

    private Long businessId;
    private String businessName;
    private String description;
    private String type;
    private Boolean verified;
    private String ownerLogin;
    private String ownerEmail;

    public Long getBusinessId() {
        return businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Boolean getVerified() {
        return verified;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }
}