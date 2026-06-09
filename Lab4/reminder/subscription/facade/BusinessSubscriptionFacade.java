package org.example.reminder.subscription.facade;

import lombok.RequiredArgsConstructor;
import org.example.reminder.business.facade.dto.BusinessDashboardResponse;
import org.example.reminder.business.model.BusinessEntity;
import org.example.reminder.business.service.BusinessService;
import org.example.reminder.common.mappers.SubscriptionTemplateMapper;
import org.example.reminder.common.model.SubscriptionStatus;
import org.example.reminder.subscription.facade.dto.CreateSubscriptionTemplateRequest;
import org.example.reminder.subscription.facade.dto.SubscriptionTemplateResponse;
import org.example.reminder.subscription.facade.dto.UpdateSubscriptionTemplateRequest;
import org.example.reminder.subscription.model.SubscriptionTemplateEntity;
import org.example.reminder.subscription.service.SubscriptionTemplateService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
    public class BusinessSubscriptionFacade {
    private final SubscriptionTemplateService subscriptionTemplateService;
    private final BusinessService businessService;
    private final SubscriptionTemplateMapper templateMapper;
    private final UserSubscriptionFacade userSubscriptionFacade;

    @Transactional
    public SubscriptionTemplateResponse createTemplate(CreateSubscriptionTemplateRequest request) {
        BusinessEntity business = businessService.findById(request.businessId());

        SubscriptionTemplateEntity template =
                subscriptionTemplateService.create(
                        business,
                        request.name(),
                        request.description(),
                        request.basePrice(),
                        request.durationDays()
                );

        return templateMapper.toDto(template);
    }



    public List<SubscriptionTemplateResponse> getBusinessTemplates(Long businessId) {
        return subscriptionTemplateService.findByBusinessId(businessId)
                .stream()
                .map(templateMapper::toDto)
                .toList();
    }

    @Transactional
    public void activate(Long templateId) {
        subscriptionTemplateService.changeStatus(templateId, SubscriptionStatus.ACTIVE);
    }

    @Transactional
    public void deactivate(Long templateId) {
        subscriptionTemplateService.changeStatus(templateId, SubscriptionStatus.INACTIVE);
    }

    public SubscriptionTemplateResponse updateTemplate(Long templateId, UpdateSubscriptionTemplateRequest request) {
        SubscriptionTemplateEntity subscriptionTemplate = subscriptionTemplateService.findById(templateId);
        return templateMapper.toDto(subscriptionTemplateService.updateTemplate(request, subscriptionTemplate));
    }

    public BusinessDashboardResponse getBusinessDashboard(Long businessId) {
        BusinessEntity business = businessService.findById(businessId);

        List<SubscriptionTemplateResponse> templates =
                subscriptionTemplateService.findByBusinessId(businessId)
                        .stream()
                        .map(templateMapper::toDto)
                        .toList();

        return new BusinessDashboardResponse(
                business.getId(),
                business.getName(),
                business.getDescription(),
                business.getType().name(),
                business.getBusinessVerified(),
                business.getUser().getLogin(),
                business.getUser().getEmail(),
                templates
        );
    }

//    public Integer getAllUsersByBusinessId(Long businessId) {
//        BusinessEntity business = businessService.findById(businessId);
//        businessService.
//    }
}
