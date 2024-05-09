package com.example.quizapplication.models;

public class UserRegistrationData {
    public String username; // CHANGING THIS WILL CHANGE THE "NAME LABEL" IN THE DATABASE!
    public String hashedPassword; // CHANGING THIS WILL CHANGE THE "NAME LABEL" IN THE DATABASE!
    public UserRegistrationData(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }
}
