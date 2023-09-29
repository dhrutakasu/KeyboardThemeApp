package com.theme.keyboardthemeapp.APPUtils;

public enum LanguageY {
    AUTODETECT(""),
    CATALAN("ca"),
    CROATIAN("hr"),
    MACEDONIAN("mk"),
    NORWEGIAN("no"),
    SERBIAN("sr"),
    SLOVENIAN("sl"),
    TAGALOG("tl"),
    HINDI("hi"),
    INDONESIAN("id"),
    MALAY("ms"),
    THAI("th"),
    GUJARATI("gu"),
    MARATHI("mr"),
    KANNADA("kn"),
    BURMESE("my"),
    NEPALI("ne"),
    SINHALA("si"),
    LAO("lo");
    
    private final String language;

    private LanguageY(String str) {
        this.language = str;
    }

    public static LanguageY fromString(String str) {
        for (LanguageY languageY : values()) {
            if (languageY.toString().equals(str)) {
                return languageY;
            }
        }
        return null;
    }

    public String toString() {
        return this.language;
    }
}
