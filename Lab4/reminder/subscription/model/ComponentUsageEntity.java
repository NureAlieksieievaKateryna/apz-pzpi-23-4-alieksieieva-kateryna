package org.example.reminder.subscription.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "component_usage")
public class ComponentUsageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_subscription_id", nullable = false)
    private UserSubscriptionEntity subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    private SubscriptionComponentEntity component;

    @Column(name = "used_count", nullable = false)
    private Integer usedCount = 0;
}
