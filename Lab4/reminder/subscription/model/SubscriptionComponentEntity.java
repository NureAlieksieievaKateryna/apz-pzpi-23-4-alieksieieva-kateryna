package org.example.reminder.subscription.model;


import jakarta.persistence.*;
import lombok.*;
import org.example.reminder.common.model.ComponentType;
import jakarta.persistence.Id;


import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscription_component")
public class SubscriptionComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private SubscriptionTemplateEntity template;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "component_type", nullable = false)
    private ComponentType componentType;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "price_modifier", precision = 10, scale = 2)
    private BigDecimal priceModifier;
}
