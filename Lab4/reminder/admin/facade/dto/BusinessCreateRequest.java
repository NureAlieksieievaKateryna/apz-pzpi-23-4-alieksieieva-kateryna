package org.example.reminder.admin.facade.dto;

import lombok.Builder;
import lombok.NonNull;
import org.example.reminder.common.model.BusinessType;

@Builder
public record BusinessCreateRequest(@NonNull String name, @NonNull String login, @NonNull String description, @NonNull String email,@NonNull BusinessType type, @NonNull String password) {
}
