package org.example.reminder.subscription.service;

import lombok.RequiredArgsConstructor;
import org.example.reminder.subscription.model.ComponentUsageEntity;
import org.example.reminder.subscription.model.SubscriptionComponentEntity;
import org.example.reminder.subscription.model.UserSubscriptionEntity;
import org.example.reminder.subscription.repository.ComponentUsageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentUsageService {

    private final ComponentUsageRepository componentUsageRepository;

    /**
     * Initialize for all usage of subscription
     *
     * @param subscription - subscription
     * @param components - components
     */
    @Transactional
    public void initUsage(UserSubscriptionEntity subscription,
                          List<SubscriptionComponentEntity> components) {

        for (SubscriptionComponentEntity component : components) {
            ComponentUsageEntity usage = new ComponentUsageEntity();
            usage.setSubscription(subscription);
            usage.setComponent(component);
            usage.setUsedCount(0);

            componentUsageRepository.save(usage);
        }
    }

    @Transactional
    public void updateLimit(Long id, Integer limit) {
        SubscriptionComponentEntity component =
                componentUsageRepository.findById(id).orElseThrow().getComponent();

        component.setUsageLimit(limit);
    }
}

