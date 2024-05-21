package com.example.quizapplication.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizapplication.R;
import com.example.quizapplication.utils.DatabaseUtilities;

import org.json.JSONArray;
import org.json.JSONException;

public class Study extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        // TODO: IMPLEMENT STUFF
        // @ Elijah Reference
        DatabaseUtilities.getKanji("Level 1", getApplicationContext()  ,(o) -> {
            JSONArray jsonArray = o;
            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    System.out.println(jsonArray.getJSONObject(i).getString("mnemonic"));
                    System.out.println(jsonArray.getJSONObject(i).getString("kanji"));
                    System.out.println(jsonArray.getJSONObject(i).getString("furigana"));
                    System.out.println(jsonArray.getJSONObject(i).getString("english"));
                    // @ DatabaseUtilities line 101, 102, 103 and 104
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}