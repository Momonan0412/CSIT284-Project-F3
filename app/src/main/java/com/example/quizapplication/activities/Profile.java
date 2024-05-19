package com.example.quizapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.quizapplication.R;
import com.example.quizapplication.designpattern.User;
import com.example.quizapplication.utils.AndroidUtil;
import com.example.quizapplication.utils.DatabaseUtilities;
import com.github.dhaval2404.imagepicker.ImagePicker;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Profile extends AppCompatActivity {
    ImageView imageView;
    TextView[] textViewFrequencyLevel;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;
    User user;
    TextView txtUsername;
    TextView textViewUserStreak;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = User.getInstance();
        textViewFrequencyLevel = new TextView[10];
        textViewFrequencyLevel[0] = findViewById(R.id.textViewFrequencyLevel1);
        textViewFrequencyLevel[1] = findViewById(R.id.textViewFrequencyLevel2);
        textViewFrequencyLevel[2] = findViewById(R.id.textViewFrequencyLevel3);
        textViewFrequencyLevel[3] = findViewById(R.id.textViewFrequencyLevel4);
        textViewFrequencyLevel[4] = findViewById(R.id.textViewFrequencyLevel5);
        textViewFrequencyLevel[5] = findViewById(R.id.textViewFrequencyLevel6);
        textViewFrequencyLevel[6] = findViewById(R.id.textViewFrequencyLevel7);
        textViewFrequencyLevel[7] = findViewById(R.id.textViewFrequencyLevel8);
        textViewFrequencyLevel[8] = findViewById(R.id.textViewFrequencyLevel9);
        textViewFrequencyLevel[9] = findViewById(R.id.textViewFrequencyLevel10);
        DatabaseUtilities.populateFrequency(user.getUsername(), (o) -> {
            user.setLevelFrequency(o);
            for(int i = 0; i < 10; i++){
                textViewFrequencyLevel[i].setText(
                        user.getLevelFrequency()[i] + " " + (user.getLevelFrequency()[i] == 1 ? "Attempt" : "Attempts"));
            }
        });
        imageView = findViewById(R.id.imgUserPP);
        txtUsername = findViewById(R.id.txtUsername);
        textViewUserStreak = findViewById(R.id.textViewUserStreak);
        textViewUserStreak.setText(user.getUserStreak() + (user.getUserStreak() == 1 ? " Day" : " Days"));
        imagePickLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if(data != null && data.getData() != null) {
                            selectedImageUri = data.getData();
                            AndroidUtil.setProfilePicture(
                                    getApplicationContext(),
                                    selectedImageUri,
                                    imageView
                            );
                        }
                    }
                }
        );
        txtUsername.setText(user.getUsername());
        AndroidUtil.loadProfilePicture(getApplicationContext(), user.getProfilePictureURL(), imageView);
    }
    public void onClickUploadImage(View view) {
        ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512)
                .createIntent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        imagePickLauncher.launch(intent);
                        return null;
                    }
                });
    }
}