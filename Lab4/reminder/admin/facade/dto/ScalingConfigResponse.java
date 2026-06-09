package org.example.reminder.admin.facade.dto;

import lombok.Builder;

@Builder
public record ScalingConfigResponse(
        String kubernetes,
        String dockerCompose,
        String k6Script
) {
}
