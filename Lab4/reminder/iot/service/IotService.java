package org.example.reminder.iot.service;

import lombok.RequiredArgsConstructor;
import org.example.reminder.iot.model.IoTDevice;
import org.example.reminder.iot.model.IoTUsageLog;
import org.example.reminder.iot.model.dto.IotUsageRequest;
import org.example.reminder.iot.repository.IotDeviceRepository;
import org.example.reminder.iot.repository.IotUsageLogRepository;
import org.example.reminder.subscription.model.SubscriptionComponentEntity;
import org.example.reminder.subscription.model.UserSubscriptionEntity;
import org.example.reminder.subscription.repository.SubscriptionComponentRepository;
import org.example.reminder.subscription.repository.UserSubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IotService {
    private final IotDeviceRepository iotDeviceRepository;
    private final IotUsageLogRepository iotUsageLogRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionComponentRepository subscriptionComponentRepository;

    @Transactional
    public void saveUsageLog(IotUsageRequest request) {
        IoTDevice device = iotDeviceRepository.findByDeviceKey(request.deviceId())
                .orElseThrow(() -> new RuntimeException("Device not found"));

        UserSubscriptionEntity userSubscription = userSubscriptionRepository.findById(request.userSubscriptionId())
                .orElseThrow(() -> new RuntimeException("User subscription not found"));

        SubscriptionComponentEntity component = subscriptionComponentRepository.findById(request.componentId())
                .orElseThrow(() -> new RuntimeException("Component not found"));

        IoTUsageLog log = IoTUsageLog.builder()
                .device(device)
                .subscription(userSubscription)
                .component(component)
                .usedAt(LocalDateTime.now())
                .success(request.success())
                .build();

        iotUsageLogRepository.save(log);
    }
}
