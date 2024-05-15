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
    Button btnStudy, btnReview, btnQuiz, btnExit, btnReloadProfilePicture;
    TextView txtUsername;
    ImageView imageView;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;
    User user;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_HAS_RUN = "hasRun";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_main_menu);
        btnStudy = findViewById(R.id.btnStudy);
        btnReview = findViewById(R.id.btnReview);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnExit = findViewById(R.id.btnExit);
        btnReloadProfilePicture = findViewById(R.id.btnReloadProfilePicture);
        user = User.getInstance();
        txtUsername = findViewById(R.id.txtUsername);
        Glide.with(getApplicationContext())
                .load(user.getProfilePictureURL())
                .preload();
        imageView = findViewById(R.id.imgUserPP);

        Glide.with(getApplicationContext())
                .load(user.getProfilePictureURL())
                .into(imageView);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        txtUsername.setText(extras.getString("USERNAME_KEY"));

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

        btnStudy.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Study.class));
        });
        btnReview.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Review.class));
        });
        btnQuiz.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ChooseLevel.class)));
        btnExit.setOnClickListener(v -> finish());

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
}