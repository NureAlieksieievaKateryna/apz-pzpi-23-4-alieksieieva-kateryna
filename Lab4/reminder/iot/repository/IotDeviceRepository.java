package org.example.reminder.iot.repository;

import org.example.reminder.iot.model.IoTDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IotDeviceRepository extends JpaRepository<IoTDevice, Long> {
    Optional<IoTDevice> findByDeviceKey(String deviceKey);
}
