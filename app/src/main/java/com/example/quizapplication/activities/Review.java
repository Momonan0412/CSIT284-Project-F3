package com.example.quizapplication.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizapplication.R;
import com.example.quizapplication.designpattern.User;
import com.example.quizapplication.models.JapaneseData;

import java.util.Iterator;

public class Review extends AppCompatActivity {

    Iterator <JapaneseData> reviewKanjiData;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        // TODO: Implement this activity, user the @User's userJapaneseReviewData and populate it base on ?
        user = User.getInstance();
        reviewKanjiData = user.getUserJapaneseReviewData().iterator();
        while(reviewKanjiData.hasNext()){
            JapaneseData japaneseData = reviewKanjiData.next();
            
        }
    }
}