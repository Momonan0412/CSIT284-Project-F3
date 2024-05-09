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
    private HashSet<JapaneseData> userJapaneseReviewData;
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
}
