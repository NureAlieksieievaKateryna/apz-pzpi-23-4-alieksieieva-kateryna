package org.example.reminder.iot.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record IotConfigResponse(
        String deviceId,
        String businessName,
        List<ActiveSubscription> activeSubscriptions
) {
    @Builder
    public record ActiveSubscription(
            Long subscriptionId,
            String templateName,
            String status,
            Long userId
    ) {}
}
