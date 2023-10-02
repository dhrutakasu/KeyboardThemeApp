package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.content.SharedPreferences;

import com.theme.keyboardthemeapp.Constants;

public class ConstantPreferences {
    private static final String COUNTRY_NAME_IN = "COUNTRY_NAME_IN";
    private static final String COUNTRY_NAME_OUT = "COUNTRY_NAME_OUT";
    private static final String PHOTO_PATH = "PHOTO_PATH";
    private static final String IN_APP = "IN_APP";
    private static final String NAME_PREFERENCE = "NAME_PREFERENCE";
    private static final String MUCH_ADS = "MUCH_ADS";
    private static final String NEW_LANGUAGE = "NEW_LANGUAGE";
    private static final String NO_ADS = "NO_ADS";
    private static final String RATED_NAME = "RATED_NAME";
    private static final String SO_REQUEST_NAME = "SO_REQUEST_NAME";
    private static final String TRANSLATE_CAMERA = "TRANSLATE_CAMERA";
    private String CountryNameIn;
    private String CountryNameOut;
    private String PhotoPath;
    private SharedPreferences.Editor editor;
    private int InApp;
    private boolean MuchAds;
    private boolean NewLanguage;
    private boolean NoAds;
    private SharedPreferences sharedPreferences;
    private boolean Rated;
    private int SoRequest;
    private boolean TranslateCamera;

    public ConstantPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_PREFERENCE, 0);
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
        getAllValue();
    }

    public void getAllValue() {
        SoRequest = sharedPreferences.getInt(SO_REQUEST_NAME, 0);
        Rated = sharedPreferences.getBoolean(RATED_NAME, false);
        MuchAds = sharedPreferences.getBoolean(MUCH_ADS, false);
        NewLanguage = sharedPreferences.getBoolean(NEW_LANGUAGE, false);
        InApp = sharedPreferences.getInt(IN_APP, 0);
        TranslateCamera = sharedPreferences.getBoolean(TRANSLATE_CAMERA, false);
        NoAds = sharedPreferences.getBoolean(NO_ADS, false);
        CountryNameIn = sharedPreferences.getString(COUNTRY_NAME_IN, Constants.SpeakLanguageName);
        CountryNameOut = sharedPreferences.getString(COUNTRY_NAME_OUT, "Hindi");
        PhotoPath = sharedPreferences.getString(PHOTO_PATH, "");
    }

    public String getCountryNameIn() {
        return CountryNameIn;
    }
}
