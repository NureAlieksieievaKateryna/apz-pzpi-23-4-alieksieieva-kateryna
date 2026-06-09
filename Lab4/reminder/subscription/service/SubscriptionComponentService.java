package org.example.reminder.subscription.service;

import lombok.RequiredArgsConstructor;
import org.example.reminder.subscription.model.SubscriptionComponentEntity;
import org.example.reminder.subscription.repository.SubscriptionComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionComponentService {

    private final SubscriptionComponentRepository subscriptionComponentRepository;

    public List<SubscriptionComponentEntity> findByTemplateId(Long templateId){
      return subscriptionComponentRepository.findByTemplateId(templateId)
              .orElseThrow(()-> new RuntimeException("Subscription component doesnt exist by this template Id %s".formatted(templateId)));
    }

}
