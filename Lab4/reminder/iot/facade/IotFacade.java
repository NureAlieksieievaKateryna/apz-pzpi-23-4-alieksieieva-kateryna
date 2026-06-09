package org.example.reminder.iot.facade;

import lombok.RequiredArgsConstructor;
import org.example.reminder.iot.model.IoTUsageLog;
import org.example.reminder.iot.model.dto.IotConfigResponse;
import org.example.reminder.iot.model.dto.IotUsageRequest;
import org.example.reminder.iot.repository.IotDeviceRepository;
import org.example.reminder.iot.repository.IotUsageLogRepository;
import org.example.reminder.subscription.repository.ComponentUsageRepository;
import org.example.reminder.subscription.repository.UserSubscriptionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IotFacade {

    private final IotDeviceRepository iotDeviceRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final ComponentUsageRepository componentUsageRepository;
    private final IotUsageLogRepository iotUsageLogRepository;

    public IotConfigResponse getActiveSubscriptions(String deviceKey) {
        var device = iotDeviceRepository.findByDeviceKey(deviceKey)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        var activeSubscriptions = userSubscriptionRepository.findActiveByBusinessId(device.getBusiness().getId())
                .stream()
                .map(sub -> IotConfigResponse.ActiveSubscription.builder()
                        .subscriptionId(sub.getId())
                        .templateName(sub.getTemplate().getName())
                        .status(sub.getStatus().name())
                        .userId(sub.getUser().getId())
                        .build())
                .collect(Collectors.toList());

        return IotConfigResponse.builder()
                .deviceId(deviceKey)
                .businessName(device.getBusiness().getName())
                .activeSubscriptions(activeSubscriptions)
                .build();
    }

    @Transactional
    public void saveLog(IotUsageRequest request) {
        var device = iotDeviceRepository.findByDeviceKey(request.deviceId())
                .orElseThrow(() -> new RuntimeException("Device not found"));

        var subscription = userSubscriptionRepository.findById(request.userSubscriptionId())
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        var component = componentUsageRepository.findBySubscriptionIdAndComponentId(
                        request.userSubscriptionId(), request.componentId())
                .orElseThrow(() -> new RuntimeException("Component usage not found"))
                .getComponent();

        var log = IoTUsageLog.builder()
                .device(device)
                .subscription(subscription)
                .component(component)
                .usedAt(LocalDateTime.now())
                .success(request.success())
                .build();

        iotUsageLogRepository.save(log);

        var usage = componentUsageRepository.findBySubscriptionIdAndComponentId(
                        request.userSubscriptionId(), request.componentId())
                .orElseThrow(() -> new RuntimeException("Component usage not found"));

        usage.setUsedCount(usage.getUsedCount() + 1);
        componentUsageRepository.save(usage);
    }
}
