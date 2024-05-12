package com.example.quizapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapplication.designpattern.User;
import com.example.quizapplication.utils.DatabaseUtilities;
import com.example.quizapplication.models.JapaneseData;
import com.example.quizapplication.R;
import com.example.quizapplication.utils.StrictModeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    TextView textViewKanji, textViewChoiceOne, textViewChoiceTwo, textViewChoiceThree, textViewChoiceFour, textViewChoiceFive;
    TextView[] textViews;
    TextView textViewScore;
    ArrayList<JapaneseData> data = new ArrayList<>();
    DatabaseUtilities databaseUtilities;
    Random random;
    JSONArray jsonArray;
    JSONArray jsonArrayCopy;
    String answer;
    String level;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        StrictModeUtils.enableStrictMode();
        User user = User.getInstance(); // SINGLETON, BASE ON THE LOGGED IN USER
        // TODO: HANDLE USER AND IMPLEMENT IN DATABASE
        user.setUserScore(new int[10]);
        // TODO: STORE IN DATABASE, USE "SET VALUE" TO REPLACE ONLY HIGHEST SCORE, SIZE 10 REPRESENTS 10 LEVELS

        random = new Random();
        textViews = new TextView[5];
        textViewKanji = findViewById(R.id.textViewKanji);
        textViews[0] = findViewById(R.id.textViewChoiceOne);
        textViews[1] = findViewById(R.id.textViewChoiceTwo);
        textViews[2] = findViewById(R.id.textViewChoiceThree);
        textViews[3] = findViewById(R.id.textViewChoiceFour);
        textViews[4] = findViewById(R.id.textViewChoiceFive);
        textViewScore = findViewById(R.id.textViewScore);
        Intent intent = getIntent();
        level = intent.getStringExtra("key");
        try {
            DatabaseUtilities.getKanji(level, getApplicationContext(), (data) -> {
                Log.d("JSON Data", String.valueOf(data.length()));
                this.jsonArray = data;
                this.jsonArrayCopy = data;
                System.out.println("Data " + data.length() + "\n JsonArray " + jsonArray.length() );
                kanjiChanger();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("SetTextI18n")
    private void kanjiChanger() {
        textViewManipulator();
        if(jsonArray.length() == 0){
            Toast.makeText(this, "No more kanji", Toast.LENGTH_SHORT).show();
            return;
        }
        int indexKanji = random.nextInt(this.jsonArray.length());
        int indexChoice = random.nextInt(5);
        try {
            // SET ALL 5
            HashSet<Integer> set = new HashSet<>();
            while(set.size() < 5){
                int indexKanjiForSet = random.nextInt(jsonArrayCopy.length());
                if(indexKanjiForSet != indexKanji){
                    set.add(indexKanjiForSet);
                }
            }
            Iterator<Integer> it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                JSONObject jsonObject = jsonArrayCopy.getJSONObject(it.next());
                String furigana = jsonObject.getString("furigana");
                String english = jsonObject.getString("english");
                // SETTERS
                textViews[i].setText(furigana + " " + english);
                i++;
            }
            // OVERRIDE
            JSONObject jsonObject = jsonArrayCopy.getJSONObject(indexKanji);
            String furigana = jsonObject.getString("furigana");
            String english = jsonObject.getString("english");
            // SETTERS
            textViewKanji.setText(jsonArray.getJSONObject(indexKanji).getString("kanji"));
            answer = furigana + " " + english;
            textViews[indexChoice].setText(furigana + " " + english);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
            jsonArray.remove(indexKanji);
        }
    }
    public void clickTextViewChoiceOne(View view) {
        System.out.println("CLICKED ONE");
        checkAnswerAndUpdateScore(0);
        kanjiChanger();
    }
    public void clickTextViewChoiceTwo(View view) {
        System.out.println("CLICKED TWO");
        checkAnswerAndUpdateScore(1);
        kanjiChanger();
    }
    public void clickTextViewChoiceThree(View view) {
        System.out.println("CLICKED THREE");
        checkAnswerAndUpdateScore(2);
        kanjiChanger();
    }
    public void clickTextViewChoiceFour(View view) {
        System.out.println("CLICKED FOUR");
        checkAnswerAndUpdateScore(3);
        kanjiChanger();
    }
    public void clickTextViewChoiceFive(View view) {
        System.out.println("CLICKED FIVE");
        checkAnswerAndUpdateScore(4);
        kanjiChanger();
    }
    @SuppressLint("SetTextI18n")
    private void checkAnswerAndUpdateScore(int index) {
        if (textViews[index].getText().toString().equals(answer)) {
            score++;
            textViewScore.setText("Score: " + score);
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }
    }
    private void textViewManipulator() {
        for(int i = 0; i < 5; i++){
            textViews[i].setVisibility(View.INVISIBLE);
            int finalI = i;
            new Handler().postDelayed(()->{
                textViews[finalI].setVisibility(View.VISIBLE);
            }, 500);
        }
    }
}