package com.example.mysubsc.data.model;


import java.math.BigDecimal;
import java.util.List;

public class CreateSubscriptionRequest {

    private String name;
    private String description;
    private BigDecimal basePrice;
    private String billingPeriod;
    private List<ComponentRequest> components;

    public CreateSubscriptionRequest(
            String name,
            String description,
            BigDecimal basePrice,
            String billingPeriod,
            List<ComponentRequest> components
    ) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.billingPeriod = billingPeriod;
        this.components = components;
    }
}
