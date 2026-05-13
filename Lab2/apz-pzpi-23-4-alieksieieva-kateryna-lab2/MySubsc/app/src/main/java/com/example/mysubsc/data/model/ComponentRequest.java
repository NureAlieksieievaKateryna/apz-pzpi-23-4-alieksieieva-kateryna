package com.example.mysubsc.data.model;

import java.math.BigDecimal;

public class ComponentRequest {

    private String name;
    private String componentType;
    private Integer usageLimit;
    private BigDecimal priceModifier;

    public ComponentRequest(
            String name,
            String componentType,
            Integer usageLimit,
            BigDecimal priceModifier
    ) {
        this.name = name;
        this.componentType = componentType;
        this.usageLimit = usageLimit;
        this.priceModifier = priceModifier;
    }
}