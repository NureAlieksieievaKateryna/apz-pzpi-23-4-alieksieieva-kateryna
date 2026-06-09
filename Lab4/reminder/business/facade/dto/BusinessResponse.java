package org.example.reminder.business.facade.dto;

import lombok.Builder;

@Builder
public record BusinessResponse(Long id, String name, String description, Boolean isVerified) {
}
