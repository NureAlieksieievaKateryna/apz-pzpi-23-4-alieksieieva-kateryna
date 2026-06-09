package org.example.reminder.controllers;

import lombok.RequiredArgsConstructor;
import org.example.reminder.iot.facade.IotFacade;
import org.example.reminder.iot.model.dto.IotConfigResponse;
import org.example.reminder.iot.model.dto.IotUsageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("iot")
@RequiredArgsConstructor
public class IotController {
    private final IotFacade iotFacade;

    @GetMapping("config/{deviceId}")
    public IotConfigResponse getConfig(@PathVariable String deviceId){
        return iotFacade.getActiveSubscriptions(deviceId);
    }

    @PostMapping("log")
    public void logUsage(@RequestBody IotUsageRequest request){
        iotFacade.saveLog(request);
    }
}
