package com.example.quizapplication.activities;

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
import com.github.dhaval2404.imagepicker.ImagePicker;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Profile extends AppCompatActivity {
    ImageView imageView;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;
    User user;
    TextView txtUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView = findViewById(R.id.imgUserPP);
        txtUsername = findViewById(R.id.txtUsername);
        user = User.getInstance();

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
        Glide.with(getApplicationContext())
                .load(user.getProfilePictureURL())
                .preload();
        Glide.with(getApplicationContext())
                .load(user.getProfilePictureURL())
                .into(imageView);
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
    private void reloadActivity() {
        // Finish the current activity
        finish();
        // Start a new instance of the same activity
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // Optional: To prevent animation
        startActivity(intent);
    }
    private void reloadProfilePicture(){
        final String PREFS_NAME = "MyPrefsFile";
        final String KEY_HAS_RUN = "hasRun";
        // Get SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        // Check if the method has run before
        boolean hasRun = settings.getBoolean(KEY_HAS_RUN, false);
        if (!hasRun) {
            // Run the method
            reloadActivity();

            // Update the SharedPreferences to mark that the method has run
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(KEY_HAS_RUN, true);
            editor.apply();
        }
    }
}