package com.example.mysubsc.data.model;

public class ModerateBusinessRequest {

    private Long businessId;
    private Boolean businessValue;

    public ModerateBusinessRequest(Long businessId, Boolean businessValue) {
        this.businessId = businessId;
        this.businessValue = businessValue;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public Boolean getBusinessValue() {
        return businessValue;
    }
}