package com.example.quizapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.ChooseLevel;
import com.example.quizapplication.designpattern.User;
import com.example.quizapplication.utils.DatabaseUtilities;

public class MainMenu extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button btnStudy, btnReview, btnQuiz, btnExit;
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
        user = User.getInstance();
        txtUsername = findViewById(R.id.txtUsername);
        imageView = findViewById(R.id.imgUserPP);

        user.setUserProfilePicture(imageView);
        // TODO: STUDY AND CHECK IF THIS IS RELEVANT @profilePictureGetter, line 260

        // TODO: UPDATE THE PROFILE PICTURE WHENEVER THE USER CHANGES
        DatabaseUtilities.profilePictureUpdateChecker(user.getUsername(), getApplicationContext());
        // What if! Implement call to update profile picture? Or?
        System.out.println(user.getUsername());
        System.out.println(user.getUserProfilePicture());
        imageView = user.getUserProfilePicture(); // does not work, initial plan
        
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        txtUsername.setText(extras.getString("USERNAME_KEY"));
        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo something where makita nimo tanan cards not pina flashcard
            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo something where flashcard pina quizlet and now dis wid spaced repition
            }
        });
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ChooseLevel.class));
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
}