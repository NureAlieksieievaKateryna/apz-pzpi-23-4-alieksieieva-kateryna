package org.example.reminder.business.facade.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CurrentPlanDto(
        String name,
        BigDecimal price,
        Integer durationDays,
        LocalDate nextBillingDate
) {}