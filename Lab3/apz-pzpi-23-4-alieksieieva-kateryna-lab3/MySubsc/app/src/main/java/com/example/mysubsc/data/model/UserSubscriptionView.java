package com.example.mysubsc.data.model;

public class UserSubscriptionView {

    private Long id;

    private Long templateId;

    private String templateName;

    private String businessName;

    private String startDate;

    private String endDate;

    private String status;

    private double finalPrice;

    public Long getId() {
        return id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}