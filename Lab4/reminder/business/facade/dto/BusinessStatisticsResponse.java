package org.example.reminder.business.facade.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BusinessStatisticsResponse(Long totalSubscriptions,
                                         Long activeSubscriptions,
                                         BigDecimal totalRevenue) {
}
