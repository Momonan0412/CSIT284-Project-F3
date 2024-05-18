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
import com.example.quizapplication.utils.StrictModeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    TextView textViewKanji, textViewChoiceOne, textViewChoiceTwo, textViewChoiceThree, textViewChoiceFour, textViewChoiceFive;
    TextView[] textViews;
    TextView textViewScore;
    ArrayList<JapaneseData> data = new ArrayList<>();
    User user;
    Random random;
    JSONArray jsonArray;
    JSONArray jsonArrayCopy;
    String[] answer;
    String level;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        StrictModeUtil.enableStrictMode();
        user = User.getInstance(); // SINGLETON, BASE ON THE LOGGED IN USER
        // TODO: HANDLE USER AND IMPLEMENT IN DATABASE
        user.setUserScore(new int[10]);
        // TODO: STORE IN DATABASE, USE "SET VALUE" TO REPLACE ONLY HIGHEST SCORE, SIZE 10 REPRESENTS 10 LEVELS

        // TODD: STORE ALL THE MISTAKES IN QUIZZES IN HERE!
        user.setUserJapaneseReviewData(new HashSet<>());

        answer = new String[3];
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
                JSONArray jsonArrayCopy = new JSONArray();
                for (int i = 0; i < data.length(); i++) {
                    try {
                        jsonArrayCopy.put(new JSONObject(data.getJSONObject(i).toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                this.jsonArrayCopy = jsonArrayCopy;
                System.out.println("Data " + data.length() + "\n JsonArray " + jsonArray.length());
                System.out.println("Data " + data.length() + "\n JsonArrayCopy " + jsonArrayCopy.length());
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
            Toast.makeText(this, "Finish!", Toast.LENGTH_SHORT).show();
            for(int i = 0; i < 5; i++) {
                textViews[i].setText("");
            }
            DatabaseUtilities.saveJapaneseData(level, user.getUsername(), user.getUserJapaneseReviewData());
            DatabaseUtilities.updateUsersLevelFrequency(user.getUsername(), level);
            Intent intent = new Intent(Quiz.this, ChooseLevel.class);
            startActivity(intent);
            return;
        }
        int indexKanji = random.nextInt(this.jsonArray.length());
        int indexChoice = random.nextInt(5);
        try {
            ArrayList<Integer> indexContains = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                int indexKanjiForSet = random.nextInt(jsonArrayCopy.length());
                System.out.println(indexKanjiForSet);
                indexContains.add(indexKanjiForSet);
                JSONObject jsonObject = jsonArrayCopy.getJSONObject(indexKanjiForSet);
                String furigana = jsonObject.getString("furigana");
                String english = jsonObject.getString("english");
                textViews[i].setText(furigana + " " + english);
            }
            // OVERRIDE
            JSONObject jsonObject = jsonArrayCopy.getJSONObject(indexKanji);
            String furigana = jsonObject.getString("furigana");
            String english = jsonObject.getString("english");
            String kanji = jsonObject.getString("kanji");
            textViews[indexChoice].setText(furigana + " " + english);
            // SETTERS
            textViewKanji.setText(jsonArray.getJSONObject(indexKanji).getString("kanji"));
            answer[0] = kanji;
            answer[1] = english;
            answer[2] = furigana;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("JasonArray Length" + jsonArray.length());
            jsonArray.remove(indexKanji);
            System.out.println("Length of the User's Review Data" + user.getUserJapaneseReviewData().size());
        }
    }
    public void clickTextViewChoiceOne(View view) {
        checkAnswerAndUpdateScore(0);
    }
    public void clickTextViewChoiceTwo(View view) {
        checkAnswerAndUpdateScore(1);
    }
    public void clickTextViewChoiceThree(View view) {
        checkAnswerAndUpdateScore(2);
    }
    public void clickTextViewChoiceFour(View view) {
        checkAnswerAndUpdateScore(3);
    }
    public void clickTextViewChoiceFive(View view) {
        checkAnswerAndUpdateScore(4);
    }
    @SuppressLint("SetTextI18n")
    private void checkAnswerAndUpdateScore(int index) {
        if (textViews[index].getText().toString().equals(answer[2] + " " + answer[1])) {
            score++;
            textViewScore.setText("Score: " + score);
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            user.getUserJapaneseReviewData().add(new JapaneseData(answer[0], answer[1], answer[2]));
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }
        kanjiChanger();
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