package com.example.quizapplication.models;

public class UserRegistrationData {
    public String username; // CHANGING THIS WILL CHANGE THE "NAME LABEL" IN THE DATABASE!
    public String hashedPassword; // CHANGING THIS WILL CHANGE THE "NAME LABEL" IN THE DATABASE!
    String dateStreakDecider;
    int userStreak;
    public UserRegistrationData(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }
    public UserRegistrationData(String dateStreakDecider, int userStreak) {
        this.dateStreakDecider = dateStreakDecider;
        this.userStreak = userStreak;
    }
}
