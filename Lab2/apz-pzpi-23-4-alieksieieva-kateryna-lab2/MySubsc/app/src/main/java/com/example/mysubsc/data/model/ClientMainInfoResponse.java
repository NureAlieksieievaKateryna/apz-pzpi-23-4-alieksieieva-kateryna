package com.example.mysubsc.data.model;

public class ClientMainInfoResponse {

    private String userName;
    private String currentPlan;
    private double price;
    private String nextPaymentDate;

    public String getUserName() {
        return userName;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public double getPrice() {
        return price;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }
}
