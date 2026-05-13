package com.example.mysubsc.data.model;

public class BusinessRegisterRequest {

    private String name;
    private String login;
    private String description;
    private String email;
    private String type;
    private String password;

    public BusinessRegisterRequest(String name, String login, String description,
                                   String email, String type, String password) {
        this.name = name;
        this.login = login;
        this.description = description;
        this.email = email;
        this.type = type;
        this.password = password;
    }
}