package com.example.quizapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.utils.DatabaseUtilities;
import com.example.quizapplication.R;
import com.example.quizapplication.designpattern.User;

public class SignIn extends AppCompatActivity {
    EditText editTextUsernameInSignIn, editTextPasswordInSignIn;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnSignIn = findViewById(R.id.btnSignIn);
        editTextPasswordInSignIn = findViewById(R.id.editTextPasswordInSignIn);
        editTextUsernameInSignIn = findViewById(R.id.editTextUsernameInSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Signing In", Toast.LENGTH_SHORT).show();
                DatabaseUtilities.signInUser(editTextUsernameInSignIn.getText().toString(),
                        editTextPasswordInSignIn.getText().toString(), getApplicationContext(), (user)->{
                            if (user != null) {
//                                Toast.makeText(getApplicationContext(), "Sign In Successful as " + user.getUsername(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), user.getProfilePictureURL(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this, MainMenu.class);
                                intent.putExtra("USERNAME_KEY", user.getUsername());
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}