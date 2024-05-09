package com.example.quizapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizapplication.R;
import com.example.quizapplication.activities.ChooseLevel;
import com.example.quizapplication.designpattern.User;

public class MainMenu extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button btnStudy, btnReview, btnQuiz, btnExit;
    TextView txtUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnStudy = findViewById(R.id.btnStudy);
        btnReview = findViewById(R.id.btnReview);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnExit = findViewById(R.id.btnExit);
        txtUsername = findViewById(R.id.txtUsername);
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

            ImageView imageView = findViewById(R.id.imgUserPP);
            imageView.setImageURI(selectedImageUri);
            User user = User.getInstance();
            // TODO: BASE IN THE USERNAME, INSERT THE IMAGE IN THE FIREBASE
            // CALL THE uploadImageToStorage METHOD
        }
    }
}