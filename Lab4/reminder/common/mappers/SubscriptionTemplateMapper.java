package org.example.reminder.common.mappers;

import org.example.reminder.subscription.facade.dto.SubscriptionTemplateResponse;
import org.example.reminder.subscription.model.SubscriptionTemplateEntity;
import org.example.reminder.user.dto.SubscriptionTemplateView;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionTemplateMapper {

    public SubscriptionTemplateResponse toDto(
            SubscriptionTemplateEntity entity) {
        if (entity == null) {
            return null;
        }

        return SubscriptionTemplateResponse.builder()
                .id(entity.getId())
                .businessId(entity.getBusiness().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .basePrice(entity.getBasePrice())
                .durationDays(entity.getDurationDays())
                .status(entity.getStatus())
                .build();
    }
    public SubscriptionTemplateView toView(SubscriptionTemplateEntity entity) {
        return SubscriptionTemplateView.builder()
                .id(entity.getId())
                .businessId(entity.getBusiness().getId())
                .businessName(entity.getBusiness().getName())
                .name(entity.getName())
                .description(entity.getDescription())
                .basePrice(entity.getBasePrice())
                .durationDays(entity.getDurationDays())
                .status(entity.getStatus().name())
                .build();
    }
}
