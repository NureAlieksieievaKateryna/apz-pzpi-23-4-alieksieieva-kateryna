package org.example.reminder.subscription.repository;

import org.example.reminder.subscription.model.ComponentUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComponentUsageRepository extends JpaRepository<ComponentUsageEntity, Long> {

    Optional<ComponentUsageEntity> findById(Long componentId);

    Optional<ComponentUsageEntity> findBySubscriptionIdAndComponentId(Long subscriptionId, Long componentId);

}
