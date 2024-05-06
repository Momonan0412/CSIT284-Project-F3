package com.example.quizapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScrapperTask extends AsyncTask<Void, Void, Void> {
    private String url;
    private Document document;
    private String cssQueryForKanji = ".character-grid";
    Context applicationContext;
    @SuppressLint("StaticFieldLeak")
    Button btnSignIn;
    @SuppressLint("StaticFieldLeak")
    Button btnSignUp;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout loadingPanel;
    @SuppressLint("StaticFieldLeak")
    ConstraintLayout textViewPleaseWait;
    public ScrapperTask(Context applicationContext, Button btnSignIn, Button btnSignUp, RelativeLayout loadingPanel, ConstraintLayout textViewPleaseWait) {
        this.url = "https://www.wanikani.com/kanji?difficulty=pleasant";
        this.applicationContext = applicationContext;
        this.btnSignIn = btnSignIn;
        this.btnSignUp = btnSignUp;
        this.loadingPanel = loadingPanel;
        this.textViewPleaseWait = textViewPleaseWait;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            document = Jsoup.connect(url).get();
            Elements kanjiData = document.select(cssQueryForKanji);
            for (Element k : kanjiData) {
                String level = k.select(".character-grid__header-text").text();
                System.out.println(level);
                Elements dataElements = k.select(".subject-character__content");
                for (Element data : dataElements) {
                    String kanji = data.select(".subject-character__characters").text();
                    String furigana = data.select(".subject-character__reading").text();
                    String english = data.select(".subject-character__meaning").text();
//                    System.out.println("Kanji: " + kanji);
//                    System.out.println("Furigana: " + furigana);
//                    System.out.println("English: " + english);
                    DatabaseUtilities.addKanji(level, kanji,english, furigana, applicationContext);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Delay the execution of code for 2 seconds
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
}

