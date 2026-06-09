package org.example.reminder.admin.facade;

import lombok.RequiredArgsConstructor;
import org.example.reminder.admin.facade.dto.BusinessCreateRequest;
import org.example.reminder.admin.facade.dto.BusinessSignInRequest;
import org.example.reminder.business.facade.dto.BusinessResponse;
import org.example.reminder.business.model.BusinessEntity;
import org.example.reminder.business.service.BusinessService;
import org.example.reminder.common.mappers.AdminMapper;
import org.example.reminder.common.mappers.BusinessMaper;
import org.example.reminder.common.model.TokenResponse;
import org.example.reminder.security.config.jwt.JwtService;
import org.example.reminder.security.config.jwt.dto.TokenCreateRequest;
import org.example.reminder.subscription.facade.dto.ModerateBusinessRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminFacade {
    private final AdminMapper adminMapper;
    private final BusinessService businessService;
    private final BusinessMaper businessMaper;

    public List<BusinessResponse> getAllNotVerifiedBusiness(){
        return  businessService.getAllNotVerifiedBusiness().stream().map(businessMaper::toDto).toList();
    }

    public void moderateBusiness(ModerateBusinessRequest moderateBusinessrequest){
     BusinessEntity entity = businessService.findById(moderateBusinessrequest.businessId());
        businessService.verifyBusiness(entity, moderateBusinessrequest.businessValue());
    }

}
