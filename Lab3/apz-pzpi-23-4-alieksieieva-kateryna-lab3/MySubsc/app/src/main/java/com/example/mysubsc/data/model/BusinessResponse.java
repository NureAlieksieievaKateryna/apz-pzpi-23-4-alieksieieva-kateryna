package com.example.mysubsc.data.model;

public class BusinessResponse {

    private Long id;
    private String name;
    private String description;
    private String type;
    private Boolean isVerified;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Boolean isVerified() {
        return isVerified;
    }
}