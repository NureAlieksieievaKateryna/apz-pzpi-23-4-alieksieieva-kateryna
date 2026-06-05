package com.example.mysubsc.data.api;

import java.util.List;

import com.example.mysubsc.data.model.BusinessDashboardResponse;
import com.example.mysubsc.data.model.BusinessRegisterRequest;
import com.example.mysubsc.data.model.BusinessResponse;
import com.example.mysubsc.data.model.ClientMainInfoResponse;
import com.example.mysubsc.data.model.CreateSubscriptionRequest;
import com.example.mysubsc.data.model.CreateUserSubscriptionRequest;
import com.example.mysubsc.data.model.LoginRequest;
import com.example.mysubsc.data.model.LoginResponse;
import com.example.mysubsc.data.model.ModerateBusinessRequest;
import com.example.mysubsc.data.model.SubscriptionDto;
import com.example.mysubsc.data.model.BusinessDto;
import com.example.mysubsc.data.model.SubscriptionTemplateResponse;
import com.example.mysubsc.data.model.UserHomeResponse;
import com.example.mysubsc.data.model.UserRegisterRequest;
import com.example.mysubsc.data.model.UserSubscriptionResponse;
import com.example.mysubsc.data.model.UserSubscriptionStatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("client/login")
    Call<LoginResponse> login(@Body LoginRequest request);


    @POST("/subscriptions/business/template/create")
    Call<Void> createSubscriptionTemplate(@Body CreateSubscriptionRequest request);

    @POST("client/register/business")
    Call<Void> registerBusiness(@Body BusinessRegisterRequest request);

    @POST("client/register")
    Call<Void> registerClient(@Body UserRegisterRequest request);

    @GET("subscriptions/user/{userId}/active")
    Call<List<UserSubscriptionResponse>> getActiveSubscriptions(
            @Path("userId") Long userId
    );

    @GET("subscriptions/user/templates")
    Call<List<SubscriptionTemplateResponse>> getAllTemplates();

    @GET("subscriptions/user/status/{userSubscriptionId}")
    Call<UserSubscriptionStatusResponse> getUserSubscriptionStatus(
            @Path("userSubscriptionId") Long userSubscriptionId
    );

    @GET("client/{userId}/home")
    Call<UserHomeResponse> getUserHome(
            @Path("userId") Long userId
    );

    @POST("subscriptions/user/create-subscription")
    Call<UserSubscriptionResponse> createUserSubscription(
            @Body CreateUserSubscriptionRequest request
    );

    @GET("/subscriptions/business/{businessId}/dashboard")
    Call<BusinessDashboardResponse> getBusinessDashboard(
            @Path("businessId") Long businessId
    );

    @PATCH("subscriptions/user/{componentId}/update-limit")
    Call<Void> updateUsageLimit(
            @Path("componentId") Long componentId,
            @Query("limit") Integer limit
    );

    @POST("subscriptions/user/process-expirations")
    Call<Void> processExpirations();

    @GET("/admin/checkAllNotVerifiedBusiness")
    Call<List<BusinessResponse>> getAllNotVerifiedBusiness();

    @POST("/admin/moderate-business")
    Call<Void> moderateBusiness(@Body ModerateBusinessRequest request);

}
