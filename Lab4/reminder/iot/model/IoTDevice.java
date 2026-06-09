package org.example.reminder.iot.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.reminder.business.model.BusinessEntity;
import org.example.reminder.common.model.IoTDeviceType;

@Data
@Entity
@Table(name = "iot_device")
public class IoTDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BusinessEntity business;

    @Column(nullable = false, unique = true)
    private String deviceKey;

    private String name;

    @Enumerated(EnumType.STRING)
    private IoTDeviceType type;

    private Boolean active;
}
