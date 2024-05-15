package com.example.quizapplication.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.quizapplication.R;
import com.example.quizapplication.designpattern.User;
import com.example.quizapplication.utils.AndroidUtil;
import com.example.quizapplication.utils.DatabaseUtilities;
import com.github.dhaval2404.imagepicker.ImagePicker;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainMenu extends AppCompatActivity {
    Button btnStudy, btnReview, btnQuiz, btnExit, btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnStudy = findViewById(R.id.btnStudy);
        btnReview = findViewById(R.id.btnReview);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnExit = findViewById(R.id.btnExit);
        btnProfile = findViewById(R.id.btnProfile);

        btnStudy.setOnClickListener(v -> {startActivity(new Intent(getApplicationContext(), Study.class));});
        btnReview.setOnClickListener(v -> {startActivity(new Intent(getApplicationContext(), Review.class));});
        btnQuiz.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ChooseLevel.class)));
        btnProfile.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Profile.class)));
        btnExit.setOnClickListener(v -> finish());
    }
}