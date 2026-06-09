package org.example.reminder.subscription.facade.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateSubscriptionTemplateRequest(Long businessId,
                                                String name,
                                                String description,
                                                BigDecimal basePrice,
                                                Integer durationDays) {
}
