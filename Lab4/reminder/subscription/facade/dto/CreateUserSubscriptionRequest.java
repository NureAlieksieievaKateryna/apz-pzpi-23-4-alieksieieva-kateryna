package org.example.reminder.subscription.facade.dto;

import lombok.Builder;

@Builder
public record CreateUserSubscriptionRequest(Long userId,
                                            Long templateId) {
}
