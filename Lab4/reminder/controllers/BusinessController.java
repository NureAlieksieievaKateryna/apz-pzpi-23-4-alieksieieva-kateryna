package org.example.reminder.controllers;

import lombok.RequiredArgsConstructor;
import org.example.reminder.business.facade.BusinessFacade;
import org.example.reminder.business.facade.dto.BusinessDashboardResponse;
import org.example.reminder.business.facade.dto.BusinessResponse;
import org.example.reminder.business.facade.dto.BusinessStatisticsResponse;
import org.example.reminder.subscription.facade.UserSubscriptionFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("business")
@RequiredArgsConstructor
public class BusinessController {

    private final UserSubscriptionFacade userSubscriptionFacade;
    private final BusinessFacade businessFacade;

    @GetMapping("findAll")
    public List<BusinessResponse> findAllBusinesses() {
        return businessFacade.findAll();
    }


    @GetMapping("statistics/{businessId}")
    public BusinessStatisticsResponse getStatistics(@PathVariable Long businessId) {
        return userSubscriptionFacade.getBusinessStatistics(businessId);
    }
}
