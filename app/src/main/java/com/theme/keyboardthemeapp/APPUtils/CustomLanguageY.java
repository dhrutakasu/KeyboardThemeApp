package com.theme.keyboardthemeapp.APPUtils;

public enum CustomLanguageY {
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

    CustomLanguageY(String str) {
        this.language = str;
    }

    public static CustomLanguageY fromString(String str) {
        for (CustomLanguageY customLanguageY : values()) {
            if (customLanguageY.toString().equals(str)) {
                return customLanguageY;
            }
        }
        return null;
    }

    public String toString() {
        return this.language;
    }
}
