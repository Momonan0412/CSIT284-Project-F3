package com.example.quizapplication;

public class JapaneseData {
    private String kanji;
    private String english;
    private String furigana;

    public JapaneseData(String kanji, String english, String furigana) {
        this.kanji = kanji;
        this.english = english;
        this.furigana = furigana;
    }
    public JapaneseData() {
        // For the getKanji method, since we don't need any parameters.
        // @this line "JapaneseData japaneseData = snapshot.getValue(JapaneseData.class);"
    }
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getFurigana() {
        return furigana;
    }

    public void setFurigana(String furigana) {
        this.furigana = furigana;
    }
}
