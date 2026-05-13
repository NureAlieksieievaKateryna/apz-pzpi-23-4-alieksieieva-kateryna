package com.example.mysubsc.data.model;

public class LoginResponse {

    private String token;

    private Long userId;

    private String userName;

    private UserRoles role;

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public UserRoles getRole() {
        return role;
    }
}