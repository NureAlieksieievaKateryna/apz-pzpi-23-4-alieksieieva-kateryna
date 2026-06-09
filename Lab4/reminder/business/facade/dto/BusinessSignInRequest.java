package org.example.reminder.business.facade.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record BusinessSignInRequest(@NonNull String login, @NonNull String password) {
}
