package com.example.mysubsc.data.model;


public class UserRegisterRequest {

    private String login;
    private String name;
    private String email;
    private String password;

    public UserRegisterRequest(String login, String name, String email, String password) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}