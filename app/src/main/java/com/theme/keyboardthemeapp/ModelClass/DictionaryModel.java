package com.theme.keyboardthemeapp.ModelClass;

public class DictionaryModel {
    String EnglishWord;
    boolean Favorite;
    String HindiWord;
    int WId;

    public DictionaryModel(String englishWord, boolean favorite, String hindiWord, int WId) {
        EnglishWord = englishWord;
        Favorite = favorite;
        HindiWord = hindiWord;
        this.WId = WId;
    }

    public String getEnglishWord() {
        return EnglishWord;
    }

    public void setEnglishWord(String englishWord) {
        EnglishWord = englishWord;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public String getHindiWord() {
        return HindiWord;
    }

    public void setHindiWord(String hindiWord) {
        HindiWord = hindiWord;
    }

    public int getWId() {
        return WId;
    }

    public void setWId(int WId) {
        this.WId = WId;
    }

    @Override
    public String toString() {
        return "DictionaryModel{" +
                "EnglishWord='" + EnglishWord + '\'' +
                ", Favorite=" + Favorite +
                ", HindiWord='" + HindiWord + '\'' +
                ", WId=" + WId +
                '}';
    }
}
