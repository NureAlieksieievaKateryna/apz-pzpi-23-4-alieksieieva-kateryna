package org.example.reminder.subscription.facade;

import lombok.RequiredArgsConstructor;
import org.example.reminder.business.facade.dto.BusinessStatisticsResponse;
import org.example.reminder.business.model.BusinessEntity;
import org.example.reminder.common.mappers.SubscriptionTemplateMapper;
import org.example.reminder.common.mappers.UserSubscriptionMapper;
import org.example.reminder.common.model.UserSubscriptionStatus;
import org.example.reminder.subscription.facade.dto.CreateUserSubscriptionRequest;
import org.example.reminder.subscription.facade.dto.SubscriptionTemplateResponse;
import org.example.reminder.subscription.facade.dto.UserSubscriptionResponse;
import org.example.reminder.subscription.facade.dto.UserSubscriptionStatusResponse;
import org.example.reminder.subscription.model.SubscriptionComponentEntity;
import org.example.reminder.subscription.model.SubscriptionTemplateEntity;
import org.example.reminder.subscription.model.UserSubscriptionEntity;
import org.example.reminder.subscription.service.ComponentUsageService;
import org.example.reminder.subscription.service.SubscriptionComponentService;
import org.example.reminder.subscription.service.SubscriptionTemplateService;
import org.example.reminder.subscription.service.UserSubscriptionService;
import org.example.reminder.user.model.UserEntity;
import org.example.reminder.user.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserSubscriptionFacade {
    private final UserSubscriptionService userSubscriptionService;
    private final SubscriptionComponentService subscriptionComponentService;
    private final SubscriptionTemplateService subscriptionTemplateService;
    private final ComponentUsageService componentUsageService;
    private final UserService userService;
    private final UserSubscriptionMapper userSubscriptionMapper;
    private final SubscriptionTemplateMapper subscriptionTemplateMapper;


    @Transactional
    public UserSubscriptionResponse createUserSubscription(CreateUserSubscriptionRequest request) {

        UserEntity user = userService.findUserById(request.userId());

        SubscriptionTemplateEntity template = subscriptionTemplateService.getActiveTemplate(request.templateId());

        BusinessEntity business = template.getBusiness();

        List<SubscriptionComponentEntity> components =
                subscriptionComponentService.findByTemplateId(template.getId());

        BigDecimal finalPrice = template.getBasePrice();
        for (SubscriptionComponentEntity component : components) {
            if (component.getPriceModifier() != null) {
                finalPrice = finalPrice.add(component.getPriceModifier());
            }
        }

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(template.getDurationDays());

        UserSubscriptionEntity subscription = userSubscriptionService.create(
                user,
                business,
                template,
                startDate,
                endDate,
                finalPrice
        );


        componentUsageService.initUsage(subscription, components);

        return userSubscriptionMapper.toDto(subscription);
    }

    public List<SubscriptionTemplateResponse> getAllTemplates(){
        return subscriptionTemplateService.findAll().stream().map(subscriptionTemplateMapper::toDto)
                .toList();
    }

    public BusinessStatisticsResponse getBusinessStatistics(Long businessId) {
        return userSubscriptionService.getStatistics(businessId);
    }

    private BigDecimal calculateFinalPrice(
            BigDecimal basePrice,
            List<SubscriptionComponentEntity> components
    ) {
        BigDecimal result = basePrice;

        for (SubscriptionComponentEntity component : components) {
            if (component.getPriceModifier() != null) {
                result = result.add(component.getPriceModifier());
            }
        }

        return result;
    }
    public void processExpirations() {
        userSubscriptionService.processSubscriptionExpirations();
    }


    @Transactional
    public void updateUsageLimit(Long componentId, Integer limit) {
        componentUsageService.updateLimit(componentId, limit);
    }

    public List<UserSubscriptionResponse> getActiveSubscriptions(Long userId) {
       return userSubscriptionService.getActiveSubscriptions(userId).stream().map(userSubscriptionMapper::toDto).toList();
    }

    public UserSubscriptionStatusResponse getUserSubscriptionStatus(Long userSubscriptionId) {
        return UserSubscriptionStatusResponse.builder()
                .status(userSubscriptionService.getUserSubscriptionStatus(userSubscriptionId).getStatus().name()).build();

    }
}
