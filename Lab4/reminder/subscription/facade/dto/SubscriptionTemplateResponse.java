package org.example.reminder.subscription.facade.dto;

import lombok.Builder;
import org.example.reminder.common.model.SubscriptionStatus;

import java.math.BigDecimal;

@Builder
public record SubscriptionTemplateResponse(Long id,
                                           Long businessId,
                                           String name,
                                           String description,
                                           BigDecimal basePrice,
                                           Integer durationDays,
                                           SubscriptionStatus status) {
}
