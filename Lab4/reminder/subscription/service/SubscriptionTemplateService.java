package org.example.reminder.subscription.service;

import lombok.RequiredArgsConstructor;
import org.example.reminder.business.model.BusinessEntity;
import org.example.reminder.common.model.SubscriptionStatus;
import org.example.reminder.subscription.facade.dto.UpdateSubscriptionTemplateRequest;
import org.example.reminder.subscription.model.SubscriptionTemplateEntity;
import org.example.reminder.subscription.repository.SubscriptionTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionTemplateService {
    private final SubscriptionTemplateRepository subscriptionTemplateRepository;

    public SubscriptionTemplateEntity findById(Long templateId){
        return subscriptionTemplateRepository.findById(templateId).orElseThrow(()->new RuntimeException("Subscription template doesnt exist %s".formatted(templateId)));
    }

    public List<SubscriptionTemplateEntity> findAll(){
        return subscriptionTemplateRepository.findAll();
    }


    public SubscriptionTemplateEntity create(
            BusinessEntity business,
            String name,
            String description,
            BigDecimal basePrice,
            Integer durationDays
    ) {
        SubscriptionTemplateEntity template =
                SubscriptionTemplateEntity.builder()
                        .business(business)
                        .name(name)
                        .description(description)
                        .basePrice(basePrice)
                        .durationDays(durationDays)
                        .status(SubscriptionStatus.ACTIVE)
                        .build();

        return subscriptionTemplateRepository.save(template);
    }

    public List<SubscriptionTemplateEntity> findByBusinessId(Long businessId) {
        return subscriptionTemplateRepository.findByBusinessId(businessId);
    }

    public SubscriptionTemplateEntity getActiveTemplate(Long id) {
        return subscriptionTemplateRepository.findByIdAndStatus(id, SubscriptionStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("Active template not found"));
    }

    @Transactional
    public void changeStatus(Long templateId, SubscriptionStatus status) {
        SubscriptionTemplateEntity template =
                subscriptionTemplateRepository.findById(templateId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Template not found"));

        template.setStatus(status);
        subscriptionTemplateRepository.save(template);
    }

    public SubscriptionTemplateEntity updateTemplate(UpdateSubscriptionTemplateRequest request, SubscriptionTemplateEntity subscriptionTemplate) {
        subscriptionTemplate.setBasePrice(request.basePrice());
        subscriptionTemplate.setStatus(request.status());
        subscriptionTemplate.setDescription(request.description());
        subscriptionTemplate.setDurationDays(request.durationDays());

        return subscriptionTemplateRepository.save(subscriptionTemplate);
    }

    public List<SubscriptionTemplateEntity> findAllActive() {
        return subscriptionTemplateRepository.findAllByStatus();
    }
}
