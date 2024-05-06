package com.example.quizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.FirebaseApp;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    DatabaseUtilities databaseUtilities;
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
        AtomicInteger databaseSize = new AtomicInteger();
        for(int i = 1; i <= 10; i++){
            int finalI = i;
            DatabaseUtilities.getKanji("Level " + i, getApplicationContext()  ,(o) -> {
                System.out.println("Level " + finalI + " -> " + o.length());
                databaseSize.addAndGet(o.length());
                System.out.println("Total Kanji Table Size: " + databaseSize);
                // TODO: ALWAYS CHECK HOW MANY KANJI ARE THERE
                // @ DATE: 5/7/2024 CURRENT COUNT 351
            });
        }
        System.out.println(databaseSize);
//        ScrapperTask task = new ScrapperTask(getApplicationContext(), btnSignIn, btnSignUp, loadingPanel, textViewPleaseWait);
//        task.execute();
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), SignIn.class));
//            }
//        });
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), SignUp.class));
//            }
//        });
    }
}