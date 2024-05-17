package com.example.quizapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.utils.AndroidUtil;
import com.example.quizapplication.utils.DatabaseUtilities;
import com.example.quizapplication.R;

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
                                Intent intent = new Intent(SignIn.this, MainMenu.class);
                                DatabaseUtilities.dateAndStreakChecker(user.getUsername(),(date, streak)->{
                                    // If date is null then it is the first time signing in, and streak should be 1
                                    if(date != null) {
                                        String currentDateStr = AndroidUtil.currentDateGetter();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        try {
                                            Date lastDate = sdf.parse(date);
                                            Date currentDate = sdf.parse(currentDateStr);
                                            Calendar lastCal = Calendar.getInstance();
                                            Calendar currentCal = Calendar.getInstance();
                                            lastCal.setTime(lastDate);
                                            currentCal.setTime(currentDate);
                                            // Calculate the difference in days
                                            long diffInMillis = currentCal.getTimeInMillis() - lastCal.getTimeInMillis();
                                            long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
                                            if (diffInDays == 1) {
                                                streak++;
                                            } else if (diffInDays > 1) {
                                                streak = 1;
                                            }
                                        } catch (ParseException e) {
                                            throw new RuntimeException(e);
                                        } finally {
                                            DatabaseUtilities.updateUsersDateAndStreak(user.getUsername(), streak);
                                        }
                                    } else {
                                        DatabaseUtilities.updateUsersDateAndStreak(user.getUsername(), 1);
                                    }
                                });
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}