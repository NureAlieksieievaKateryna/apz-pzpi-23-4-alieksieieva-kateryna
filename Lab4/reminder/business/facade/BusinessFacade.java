package org.example.reminder.business.facade;

import lombok.RequiredArgsConstructor;
import org.example.reminder.business.facade.dto.BusinessCreateRequest;
import org.example.reminder.business.facade.dto.BusinessResponse;
import org.example.reminder.business.facade.dto.BusinessSignInRequest;
import org.example.reminder.business.model.BusinessEntity;
import org.example.reminder.business.service.BusinessService;
import org.example.reminder.common.mappers.BusinessMaper;
import org.example.reminder.common.model.TokenResponse;
import org.example.reminder.security.config.jwt.JwtService;

import org.example.reminder.user.model.UserEntity;
import org.example.reminder.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BusinessFacade {
    private final BusinessMaper businessMaper;
    private final BusinessService businessService;
    private final UserService userService;

    public void createBusiness(BusinessCreateRequest businessCreateRequest) throws Exception {
        //userService.existByUserName(businessCreateRequest.login());
        UserEntity user = userService.registerBusiness(businessCreateRequest);
        businessService.createBusiness(businessCreateRequest, user);
    }

    public List<BusinessResponse> findAll(){
        return businessService.findAll().stream().map(v->businessMaper.toDto(v)).toList();
    }

}
