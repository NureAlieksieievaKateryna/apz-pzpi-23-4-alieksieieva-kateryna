package org.example.reminder.subscription.repository;

import org.example.reminder.subscription.model.SubscriptionComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionComponentRepository  extends JpaRepository<SubscriptionComponentEntity, Long> {

    Optional<List<SubscriptionComponentEntity>> findByTemplateId(Long templateId);
}
