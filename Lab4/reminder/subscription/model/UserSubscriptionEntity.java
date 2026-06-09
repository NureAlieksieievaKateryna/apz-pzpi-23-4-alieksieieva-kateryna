package org.example.reminder.subscription.model;
import jakarta.persistence.*;
import lombok.*;
import org.example.reminder.business.model.BusinessEntity;
import org.example.reminder.common.model.UserSubscriptionStatus;
import org.example.reminder.user.model.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "user_subscription")
@AllArgsConstructor
public class UserSubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private BusinessEntity business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private SubscriptionTemplateEntity template;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserSubscriptionStatus status;

    @Column(name = "final_price", precision = 10, scale = 2)
    private BigDecimal finalPrice;
}
