package com.example.mysubsc.data.model;

public class UserSubscriptionResponse {

    private Long id;
    private String templateName;
    private Double price;
    private String status;

    public Long getId() {
        return id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}