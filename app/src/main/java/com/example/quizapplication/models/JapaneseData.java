package com.example.quizapplication.models;

public class JapaneseData {
    private String kanji;
    private String english;
    private String furigana;
    private String mnemonic;

    public JapaneseData(String kanji, String english, String furigana) {
        this.kanji = kanji;
        this.english = english;
        this.furigana = furigana;
    }
    public JapaneseData(String kanji, String english, String furigana, String mnemonic) {
        this(kanji, english, furigana);
        this.mnemonic = mnemonic;
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

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}
