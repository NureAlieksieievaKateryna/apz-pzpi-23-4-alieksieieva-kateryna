package org.example.reminder.subscription.facade.dto;

import lombok.Builder;
import org.example.reminder.business.model.BusinessEntity;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CreateTemplateRequest(BusinessEntity business,
                                    String name,
                                    String description,
                                    BigDecimal basePrice,
                                    Integer durationDays,
                                    List<CreateComponentRequest> components) {
}
