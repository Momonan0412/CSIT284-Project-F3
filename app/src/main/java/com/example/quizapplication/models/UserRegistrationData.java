package com.example.quizapplication.models;

public class UserRegistrationData {
    public String username;
    public String hashedPassword;
    public UserRegistrationData(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }
}
