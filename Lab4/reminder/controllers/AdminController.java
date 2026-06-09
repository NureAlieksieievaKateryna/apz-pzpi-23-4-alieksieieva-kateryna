package org.example.reminder.controllers;

import lombok.RequiredArgsConstructor;
import org.example.reminder.admin.facade.AdminFacade;
import org.example.reminder.admin.facade.AdminScalingFacade;
import org.example.reminder.admin.facade.dto.ScalingConfigRequest;
import org.example.reminder.admin.facade.dto.ScalingConfigResponse;
import org.example.reminder.business.facade.BusinessFacade;
import org.example.reminder.business.facade.dto.BusinessDashboardResponse;
import org.example.reminder.business.facade.dto.BusinessResponse;
import org.example.reminder.business.facade.dto.BusinessStatisticsResponse;
import org.example.reminder.subscription.facade.BusinessSubscriptionFacade;
import org.example.reminder.subscription.facade.UserSubscriptionFacade;
import org.example.reminder.subscription.facade.dto.ModerateBusinessRequest;
import org.example.reminder.subscription.facade.dto.SubscriptionTemplateResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminFacade adminFacade;
    private final AdminScalingFacade adminScalingFacade;
    private final BusinessFacade businessFacade;
    private final UserSubscriptionFacade userSubscriptionFacade;
    private final BusinessSubscriptionFacade businessSubscriptionFacade;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("checkAllNotVerifiedBusiness")
    public List<BusinessResponse> getAllNotVerifiedBusiness(){
        return adminFacade.getAllNotVerifiedBusiness();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("moderate-business")
    public void moderateBusiness(@RequestBody ModerateBusinessRequest moderateBusinessrequest){
        adminFacade.moderateBusiness(moderateBusinessrequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/business/all")
    public List<BusinessResponse> findAllBusinesses() {
        return businessFacade.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/business/{businessId}/statistics")
    public BusinessStatisticsResponse getBusinessStatistics(@PathVariable Long businessId) {
        return userSubscriptionFacade.getBusinessStatistics(businessId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/business/{businessId}/dashboard")
    public BusinessDashboardResponse getBusinessDashboard(@PathVariable Long businessId) {
        return businessSubscriptionFacade.getBusinessDashboard(businessId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/business/{businessId}/templates")
    public List<SubscriptionTemplateResponse> getBusinessTemplates(@PathVariable Long businessId) {
        return businessSubscriptionFacade.getBusinessTemplates(businessId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/subscriptions/templates")
    public List<SubscriptionTemplateResponse> getAllTemplates() {
        return userSubscriptionFacade.getAllTemplates();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subscriptions/process-expirations")
    public void processExpirations() {
        userSubscriptionFacade.processExpirations();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/scaling/generate")
    public ScalingConfigResponse generateScalingConfig(@RequestBody ScalingConfigRequest request) {
        return adminScalingFacade.generate(request);
    }

}
