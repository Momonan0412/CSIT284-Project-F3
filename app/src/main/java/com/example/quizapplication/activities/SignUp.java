package com.example.quizapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.utils.DatabaseUtilities;
import com.example.quizapplication.R;
import com.example.quizapplication.callbacks.UserExistCallback;

public class SignUp extends AppCompatActivity {
    EditText editTextUsernameInSignUp, editTextPasswordInSignUp, editTextConfirmPasswordInSignUp;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextPasswordInSignUp = findViewById(R.id.editTextPasswordInSignUp);
        editTextUsernameInSignUp = findViewById(R.id.editTextUsernameInSignUp);
        editTextConfirmPasswordInSignUp = findViewById(R.id.editTextConfirmPasswordInSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            String username = editTextUsernameInSignUp.getText().toString().trim();
            String password = editTextPasswordInSignUp.getText().toString().trim();
            String confirmPassword = editTextConfirmPasswordInSignUp.getText().toString();
            if (username.contains(" ") || password.contains(" ") || confirmPassword.contains(" ")) {
                Toast.makeText(SignUp.this, "No spaces allowed", Toast.LENGTH_SHORT).show();
            } else if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(SignUp.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseUtilities.registerUser(username, password, SignUp.this, userExist -> {
                    if (userExist) {
                        startActivity(new Intent(SignUp.this, SignIn.class));
                    }
                });
            }
        });
    }
}