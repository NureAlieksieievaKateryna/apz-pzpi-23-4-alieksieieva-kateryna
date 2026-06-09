package org.example.reminder.business.facade.dto;

import org.example.reminder.subscription.facade.dto.SubscriptionTemplateResponse;

import java.util.List;

public record BusinessDashboardResponse(
        Long businessId,
        String businessName,
        String description,
        String type,
        Boolean verified,
        String ownerLogin,
        String ownerEmail,
        List<SubscriptionTemplateResponse> templates
) {}
