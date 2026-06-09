package org.example.reminder.subscription.facade.dto;

import lombok.Builder;
import org.example.reminder.common.model.UserSubscriptionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record UserSubscriptionResponse(Long id,
                                       Long userId,
                                       Long businessId,
                                       Long templateId,
                                       LocalDateTime startDate,
                                       LocalDateTime endDate,
                                       UserSubscriptionStatus status,
                                       BigDecimal finalPrice) {
}
