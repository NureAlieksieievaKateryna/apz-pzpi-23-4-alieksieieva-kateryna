package org.example.reminder.security.config.jwt.dto;

import lombok.Builder;
import lombok.NonNull;
import org.example.reminder.common.model.UserRoles;

@Builder
public record TokenCreateRequest(@NonNull String login, @NonNull UserRoles userRoles) {
}
