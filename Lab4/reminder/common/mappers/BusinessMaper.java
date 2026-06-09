package org.example.reminder.common.mappers;

import org.example.reminder.business.facade.dto.BusinessResponse;
import org.example.reminder.business.model.BusinessEntity;
import org.springframework.stereotype.Component;

@Component
public class BusinessMaper {

    public BusinessResponse toDto(BusinessEntity business){
        return BusinessResponse.builder()
                .id(business.getId())
                .name(business.getName())
                .description(business.getDescription())
                .isVerified(business.getBusinessVerified())
                .build();
    }
}
