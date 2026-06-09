package org.example.reminder.business.facade.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StatsDto(
        Long activeSubscriptions,
        Long users,
        BigDecimal revenue,
        Long pendingPayments
) {}
