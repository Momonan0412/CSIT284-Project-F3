package com.example.quizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.quizapplication.activities.SignIn;
import com.example.quizapplication.activities.SignUp;
import com.example.quizapplication.utils.DatabaseUtilities;
import com.example.quizapplication.utils.ScrapperTask;
import com.google.firebase.FirebaseApp;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    int indexCount = 0;
    RelativeLayout loadingPanel;
    Button btnSignIn, btnSignUp;
    ConstraintLayout textViewPleaseWait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.activity_front_design);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        loadingPanel = findViewById(R.id.loadingPanel);
        btnSignIn.setVisibility(View.INVISIBLE);
        btnSignUp.setVisibility(View.INVISIBLE);
        textViewPleaseWait = findViewById(R.id.textViewPleaseWait);
//        DatabaseUtilities.insertCurrentDateAndStreakDecider("JohnnyTest", 0);
        AtomicInteger databaseSize = new AtomicInteger();
        for(int i = 1; i <= 10; i++){
            int finalI = i;
            DatabaseUtilities.getKanji("Level " + i, getApplicationContext()  ,(o) -> {
                System.out.println("Level " + finalI + " -> " + o.length());
                databaseSize.addAndGet(o.length());
                System.out.println("Total Kanji Table Size: " + databaseSize);
                // TODO: Implement something that would update the table
                //  ALWAYS CHECK HOW MANY KANJI ARE THERE
                // @ DATE: 5/7/2024 CURRENT COUNT 351
                indexCount += finalI;
                if(indexCount == 55 && databaseSize.get() != 351){
                    ScrapperTask task = new ScrapperTask(getApplicationContext(), btnSignIn, btnSignUp, loadingPanel, textViewPleaseWait);
                    task.execute();
                } else if (indexCount == 55){
                    System.out.println("The index count is: " + indexCount + " and database size is: " + databaseSize.get());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Set the visibility of buttons and other views after the delay
                            btnSignIn.setVisibility(View.VISIBLE);
                            btnSignUp.setVisibility(View.VISIBLE);
                            loadingPanel.setVisibility(View.GONE);
                            textViewPleaseWait.setVisibility(View.GONE);
                        }
                    }, 2000); // 2000 milliseconds = 2 seconds
                }
            });
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }
}