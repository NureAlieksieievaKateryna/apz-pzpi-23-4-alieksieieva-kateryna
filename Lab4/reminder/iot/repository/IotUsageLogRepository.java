package org.example.reminder.iot.repository;

import org.example.reminder.iot.model.IoTUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IotUsageLogRepository extends JpaRepository<IoTUsageLog, Long> {
}
