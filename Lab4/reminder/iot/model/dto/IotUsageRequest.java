package org.example.reminder.iot.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record IotUsageRequest(String deviceId,
                              Long userSubscriptionId,
                              Long componentId,
                              Boolean success,
                              LocalDateTime usedAt) {
}
