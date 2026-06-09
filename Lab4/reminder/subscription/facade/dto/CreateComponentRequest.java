package org.example.reminder.subscription.facade.dto;

import lombok.Builder;
import org.hibernate.type.ComponentType;

import java.math.BigDecimal;

@Builder
public record CreateComponentRequest(String name,
                                        ComponentType type,
                                        Integer usageLimit,
                                        BigDecimal priceModifier) {
}
