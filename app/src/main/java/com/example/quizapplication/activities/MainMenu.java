package com.example.quizapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quizapplication.R;
import com.example.quizapplication.designpattern.User;
import com.example.quizapplication.utils.DatabaseUtilities;

public class MainMenu extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button btnStudy, btnReview, btnQuiz, btnExit, btnReloadProfilePicture;
    TextView txtUsername;
    ImageView imageView;


    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        btnReloadProfilePicture.setOnClickListener((v)->{
            reloadActivity();
        });
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
        // Create an intent to pick an image from the gallery or capture a new image
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            Uri selectedImageUri = data.getData();

            imageView.setImageURI(selectedImageUri);

            Drawable drawable = imageView.getDrawable();
            Bitmap bitmap = null;
            if (drawable instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            }
            assert bitmap != null;
            DatabaseUtilities.uploadImageToStorage(bitmap, user.getUsername(),getApplicationContext());
        }
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