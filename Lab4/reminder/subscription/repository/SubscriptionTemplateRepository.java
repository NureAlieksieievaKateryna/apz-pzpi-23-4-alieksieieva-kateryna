package org.example.reminder.subscription.repository;

import org.example.reminder.common.model.SubscriptionStatus;
import org.example.reminder.subscription.model.SubscriptionTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionTemplateRepository extends JpaRepository<SubscriptionTemplateEntity, Long> {
    List<SubscriptionTemplateEntity> findByBusinessId(Long businessId);

    Optional<SubscriptionTemplateEntity> findByIdAndStatus(
            Long id,
            SubscriptionStatus status
    );

    @Query("""
       SELECT s
       FROM SubscriptionTemplateEntity s
       WHERE s.status = 'ACTIVE'
       """)
    List<SubscriptionTemplateEntity> findAllByStatus();
}
