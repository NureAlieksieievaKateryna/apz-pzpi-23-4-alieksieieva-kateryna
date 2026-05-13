package com.example.mysubsc.data.model;

import java.util.List;

public class UserHomeResponse {

    private Long userId;

    private String username;

    private UserSubscriptionView activeSubscription;

    private List<SubscriptionTemplateView> availableTemplates;

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public UserSubscriptionView getActiveSubscription() {
        return activeSubscription;
    }

    public List<SubscriptionTemplateView> getAvailableTemplates() {
        return availableTemplates;
    }
}