package com.example.quizapplication.models;

public class JapaneseData {
    private String kanji;
    private String english;
    private String furigana;

    public JapaneseData(String kanji, String english, String furigana) {
        this.kanji = kanji;
        this.english = english;
        this.furigana = furigana;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kanji == null) ? 0 : kanji.hashCode());
        result = prime * result + ((english == null) ? 0 : english.hashCode());
        result = prime * result + ((furigana == null) ? 0 : furigana.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JapaneseData other = (JapaneseData) obj;
        if (kanji == null) {
            if (other.kanji != null)
                return false;
        } else if (!kanji.equals(other.kanji))
            return false;
        if (english == null) {
            if (other.english != null)
                return false;
        } else if (!english.equals(other.english))
            return false;
        if (furigana == null) {
            if (other.furigana != null)
                return false;
        } else if (!furigana.equals(other.furigana))
            return false;
        return true;
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
