package org.example.reminder.subscription.repository;

import org.example.reminder.common.model.UserSubscriptionStatus;
import org.example.reminder.subscription.model.UserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionEntity, Long> {
    List<UserSubscriptionEntity> findByUserId(Long userId);

    @Query("""
        SELECT COUNT(us)
        FROM UserSubscriptionEntity us
        WHERE us.business.id = :businessId
    """)
    Long countAllByBusiness(@Param("businessId") Long businessId);
    @Query("""
        SELECT us
        FROM UserSubscriptionEntity us
        WHERE us.business.id = :businessId
          AND us.status = 'ACTIVE'
    """)
    List<UserSubscriptionEntity> findActiveByBusinessId(
            @Param("businessId") Long businessId
    );

    @Query("SELECT u FROM UserSubscriptionEntity u WHERE u.endDate <= CURRENT_TIMESTAMP AND u.status = 'ACTIVE'")
    List<UserSubscriptionEntity> findExpiredSubscriptions();

    @Modifying
    @Query("UPDATE UserSubscriptionEntity u SET u.status = :status WHERE u.id = :subscriptionId")
    void updateStatus(@Param("subscriptionId") Long subscriptionId, @Param("status") UserSubscriptionStatus status);

    @Query("""
        SELECT COUNT(us)
        FROM UserSubscriptionEntity us
        WHERE us.business.id = :businessId
          AND us.status = :status
    """)
    Long countByBusinessAndStatus(
            @Param("businessId") Long businessId,
            @Param("status") UserSubscriptionStatus status
    );

    List<UserSubscriptionEntity> findByUserIdAndStatus(Long userId, UserSubscriptionStatus status);

    @Query("""
        SELECT SUM(us.finalPrice)
        FROM UserSubscriptionEntity us
        WHERE us.business.id = :businessId
    """)
    BigDecimal sumRevenueByBusiness(@Param("businessId") Long businessId);
}
