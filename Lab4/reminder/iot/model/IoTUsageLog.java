package org.example.reminder.iot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.reminder.subscription.model.SubscriptionComponentEntity;
import org.example.reminder.subscription.model.UserSubscriptionEntity;

import java.time.LocalDateTime;
@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "iot_usage_log")
public class IoTUsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private IoTDevice device;

    @ManyToOne
    @JoinColumn(name = "user_subscription_id", nullable = false)
    private UserSubscriptionEntity subscription;

    @ManyToOne
    @JoinColumn(name = "component_id", nullable = false)
    private SubscriptionComponentEntity component;

    private LocalDateTime usedAt;

    private Boolean success;
}

