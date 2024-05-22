package com.example.quizapplication.activities;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

import com.example.quizapplication.R;
import com.example.quizapplication.designpattern.User;
import com.example.quizapplication.models.JapaneseData;
import com.example.quizapplication.utils.DatabaseUtilities;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Review extends AppCompatActivity {
    User user;
    TextView CurrKanji;
    TextView[] ChoiceBoxes;
    ArrayList<String> KanjiReview;
    ArrayList<String> FuriganaReview;
    ArrayList<String> EnglishReview;

    int ItemSize = 0;
    int curr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        user = User.getInstance();
        KanjiReview = new ArrayList<>();
        FuriganaReview = new ArrayList<>();
        EnglishReview = new ArrayList<>();
        ChoiceBoxes = new TextView[5];
        CurrKanji = findViewById(R.id.textViewKanji);
        ChoiceBoxes[0] = findViewById(R.id.txtvChoice1);
        ChoiceBoxes[1] = findViewById(R.id.txtvChoice2);
        ChoiceBoxes[2] = findViewById(R.id.txtvChoice3);
        ChoiceBoxes[3] = findViewById(R.id.txtvChoice4);
        ChoiceBoxes[4] = findViewById(R.id.txtvChoice5);

        DatabaseUtilities.getReviewData(user.getUsername(), (data) -> {
            for(JapaneseData d : data){
                System.out.println(d.getKanji() + " " + d.getFurigana() + " " + d.getEnglish());
                KanjiReview.add(d.getKanji());
                FuriganaReview.add(d.getFurigana());
                EnglishReview.add(d.getEnglish());
                ItemSize++;
            }
            setChoices();
        });
    }

    void setChoices(){
        if(curr == ItemSize){
            Toast.makeText(this, "Finish!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Review.this, MainMenu.class);
            startActivity(intent);
            return;
        }

        Random rand = new Random();
        ArrayList<Integer> choiceNumber = new ArrayList<>();
        int randomNumber;
        for(int i = 0; i < 5; i++){
            choiceNumber.add(i);
        }
        Collections.shuffle(choiceNumber);

        CurrKanji.setText(KanjiReview.get(curr));
        ChoiceBoxes[choiceNumber.get(0)].setText(FuriganaReview.get(curr) + " " + EnglishReview.get(curr));
        for(int i = 1; i < 5; i++){
            randomNumber = rand.nextInt(ItemSize);
            ChoiceBoxes[choiceNumber.get(i)].setText(FuriganaReview.get(randomNumber) + " " + EnglishReview.get(randomNumber));
        }
        curr++;
    }

    public void onClicktxtChoice1(View view){ CheckAnswer(0);}
    public void onClicktxtChoice2(View view){ CheckAnswer(1);}
    public void onClicktxtChoice3(View view){ CheckAnswer(2);}
    public void onClicktxtChoice4(View view){ CheckAnswer(3);}
    public void onClicktxtChoice5(View view){ CheckAnswer(4);}
    void CheckAnswer(int index){
        setChoices();
    }
}