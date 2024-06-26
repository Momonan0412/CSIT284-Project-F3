package com.example.quizapplication.designpattern;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.example.quizapplication.models.JapaneseData;

import java.util.HashSet;

public class User {
    @SuppressLint("StaticFieldLeak")
    private static User instance;
    private String username;
    private String hashedPassword;
    private ImageView userProfilePicture;
    private String profilePictureURL;
    private int[] userScore;
    private HashSet<JapaneseData> userJapaneseReviewData;
    private int userStreak;
    private int[] levelFrequency;
    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    public ImageView getUserProfilePicture() {
        return userProfilePicture;
    }
    public void setUserProfilePicture(ImageView userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }
    public HashSet<JapaneseData> getUserJapaneseReviewData() {
        return userJapaneseReviewData;
    }
    public void setUserJapaneseReviewData(HashSet<JapaneseData> userJapaneseReviewData) {
        this.userJapaneseReviewData = userJapaneseReviewData;
    }
    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public int[] getUserScore() {
        return userScore;
    }

    public void setUserScore(int[] userScore) {
        this.userScore = userScore;
    }

    public int getUserStreak() {
        return userStreak;
    }
    public void setUserStreak(int userStreak) {
        this.userStreak = userStreak;
    }

    public int[] getLevelFrequency() {
        return levelFrequency;
    }

    public void setLevelFrequency(int [] levelFrequency) {
        this.levelFrequency = levelFrequency;
    }
}
