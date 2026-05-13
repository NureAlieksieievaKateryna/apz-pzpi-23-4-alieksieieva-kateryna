package com.buy.factorymethod;

// Product
interface Notification {
    void send();
}

// Concrete Product 1
class EmailNotification implements Notification {

    @Override
    public void send() {
        System.out.println("Send Email Notification");
    }
}

// Concrete Product 2
class SMSNotification implements Notification {

    @Override
    public void send() {
        System.out.println("Send SMS Notification");
    }
}

// Creator
abstract class NotificationFactory {

    // Factory Method
    abstract Notification createNotification();

    public void notifyUser() {
        Notification notification = createNotification();
        notification.send();
    }
}

// Concrete Creator 1
class EmailFactory extends NotificationFactory {

    @Override
    Notification createNotification() {
        return new EmailNotification();
    }
}

// Concrete Creator 2
class SMSFactory extends NotificationFactory {

    @Override
    Notification createNotification() {
        return new SMSNotification();
    }
}

// Main
public class Main {

    public static void main(String[] args) {

        NotificationFactory emailFactory = new EmailFactory();
        emailFactory.notifyUser();

        NotificationFactory smsFactory = new SMSFactory();
        smsFactory.notifyUser();
    }
}