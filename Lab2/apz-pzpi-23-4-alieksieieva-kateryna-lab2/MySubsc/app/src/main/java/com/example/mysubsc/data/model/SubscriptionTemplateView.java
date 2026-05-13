package com.example.mysubsc.data.model;

public class SubscriptionTemplateView {

    private Long id;

    private Long businessId;

    private String businessName;

    private String name;

    private String description;

    private double basePrice;

    private Integer durationDays;

    private String status;

    public Long getId() {
        return id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public String getStatus() {
        return status;
    }
}