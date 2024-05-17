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
import com.example.quizapplication.utils.DatabaseUtilities;

import java.util.Iterator;

public class Review extends AppCompatActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        user = User.getInstance();
        DatabaseUtilities.getReviewData(user.getUsername(), (data) -> {
            for(JapaneseData d : data){
                System.out.println(d.getKanji() + " " + d.getFurigana() + " " + d.getEnglish());
            }
        });
    }
}