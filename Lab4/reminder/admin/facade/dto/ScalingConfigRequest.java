package org.example.reminder.admin.facade.dto;

import lombok.Builder;

@Builder
public record ScalingConfigRequest(
        Integer replicas,
        Integer cpu,
        Integer memory,
        Boolean hpaEnabled,
        Integer minReplicas,
        Integer maxReplicas,
        Integer targetCpu,
        Integer users,
        Integer duration,
        String loadProfile
) {
}
