package com.example.quizapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScrapperTask extends AsyncTask<Void, Void, Void> {
    private String url;
    private Document document;
    private final String htmlQueryForKanji = ".character-grid";
    Context applicationContext;
    @SuppressLint("StaticFieldLeak")
    Button btnSignIn;
    @SuppressLint("StaticFieldLeak")
    Button btnSignUp;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout loadingPanel;
    @SuppressLint("StaticFieldLeak")
    ConstraintLayout textViewPleaseWait;
    public ScrapperTask(Context applicationContext, Button btnSignIn, Button btnSignUp,
                        RelativeLayout loadingPanel, ConstraintLayout textViewPleaseWait) {
        this.url = "https://www.wanikani.com/kanji?difficulty=pleasant";
        this.applicationContext = applicationContext;
        this.btnSignIn = btnSignIn;
        this.btnSignUp = btnSignUp;
        this.loadingPanel = loadingPanel;
        this.textViewPleaseWait = textViewPleaseWait;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        updateKanjiTable();
        return null;
    }

    private void updateKanjiTable() {
        try {
            document = Jsoup.connect(url).get();
            Elements kanjiData = document.select(htmlQueryForKanji);
            for (Element k : kanjiData) {
                String level = k.select(".character-grid__header-text").text();
                System.out.println(level);
                Elements dataElements = k.select(".subject-character__content");
                for (Element data : dataElements) {
                    String kanji = data.select(".subject-character__characters").text();
                    String furigana = data.select(".subject-character__reading").text();
                    String englishMeaningURL = "https://www.wanikani.com/kanji/" + kanji;
                    Document englishDocument = Jsoup.connect(englishMeaningURL).get();
                    String getHTMLQueryForEnglishMeaning = ".subject-section__meanings-items";
                    Elements englishMeaningData = englishDocument.select(getHTMLQueryForEnglishMeaning);
                    StringBuilder englishMeanings = new StringBuilder();
                    int size = englishMeaningData.size();
                    for(int i = 0; i < size; i++){
                        String english = englishMeaningData.get(i).text();
                        englishMeanings.append(english);
                        if(i < size - 1){
                            englishMeanings.append(", ");
                        }
                    }
                        DatabaseUtilities.addKanji(level, kanji, furigana, String.valueOf(englishMeanings), applicationContext);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

