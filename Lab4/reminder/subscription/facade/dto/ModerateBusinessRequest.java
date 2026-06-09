package org.example.reminder.subscription.facade.dto;

import lombok.Builder;

@Builder
public record ModerateBusinessRequest(Long businessId, Boolean businessValue) {
}
