package org.example.reminder.common.mappers;

import org.example.reminder.subscription.facade.dto.UserSubscriptionResponse;
import org.example.reminder.subscription.model.UserSubscriptionEntity;
import org.example.reminder.user.dto.UserSubscriptionView;
import org.springframework.stereotype.Component;

@Component
public class UserSubscriptionMapper {

    public UserSubscriptionResponse toDto(UserSubscriptionEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserSubscriptionResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .businessId(entity.getBusiness().getId())
                .templateId(entity.getTemplate().getId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .finalPrice(entity.getFinalPrice())
                .build();
    }

    public UserSubscriptionView toView(UserSubscriptionEntity entity) {
        return UserSubscriptionView.builder()
                .id(entity.getId())
                .templateId(entity.getTemplate().getId())
                .templateName(entity.getTemplate().getName())
                .businessName(entity.getBusiness().getName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus().name())
                .finalPrice(entity.getFinalPrice())
                .build();
    }
}
