package com.example.mysubsc.data.model;

public class CreateUserSubscriptionRequest {

    private Long userId;

    private Long templateId;

    public CreateUserSubscriptionRequest(
            Long userId,
            Long templateId
    ) {
        this.userId = userId;
        this.templateId = templateId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTemplateId() {
        return templateId;
    }
}