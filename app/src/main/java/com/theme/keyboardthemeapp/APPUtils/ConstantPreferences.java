package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.content.SharedPreferences;

import com.theme.keyboardthemeapp.Constants;

/* loaded from: classes.dex */
public class ConstantPreferences {
    private static final String CONTRY_NAME_IN = "contrynamein";
    private static final String CONTRY_NAME_OUT = "contrynameout";
    private static final String CURRENT_PHOTO_PATH = "photopath";
    private static final String IN_APP = "inapp";
    private static final String MAIN_NAME_PREFERENCE = "quanly_request";
    private static final String MUCH_ADS = "muchads";
    private static final String NEW_LANGUAGE = "newlang_speech";
    private static final String NO_ADS = "noads";
    private static final String RATED_NAME = "rated";
    private static final String SO_REQUEST_NAME = "sorequest";
    private static final String TRANSLATE_CAMERA = "translate_camera";
    private String mContryNameIn;
    private String mContryNameOut;
    private String mCurrentPhotoPath;
    private SharedPreferences.Editor mEditor;
    private int mInApp;
    private boolean mMuchAds;
    private boolean mNewLanguage;
    private boolean mNoAds;
    private SharedPreferences mPreference;
    private boolean mRated;
    private int mSoRequest;
    private boolean mTranslateCamera;

    public ConstantPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MAIN_NAME_PREFERENCE, 0);
        this.mPreference = sharedPreferences;
        this.mEditor = sharedPreferences.edit();
        getAllValue();
    }

    public void getAllValue() {
        this.mSoRequest = this.mPreference.getInt(SO_REQUEST_NAME, 0);
        this.mRated = this.mPreference.getBoolean(RATED_NAME, false);
        this.mMuchAds = this.mPreference.getBoolean(MUCH_ADS, false);
        this.mNewLanguage = this.mPreference.getBoolean(NEW_LANGUAGE, false);
        this.mInApp = this.mPreference.getInt(IN_APP, 0);
        this.mTranslateCamera = this.mPreference.getBoolean(TRANSLATE_CAMERA, false);
        this.mNoAds = this.mPreference.getBoolean(NO_ADS, false);
        this.mContryNameIn = this.mPreference.getString(CONTRY_NAME_IN, Constants.SpeakLanguageName);
        this.mContryNameOut = this.mPreference.getString(CONTRY_NAME_OUT, "Hindi");
        this.mCurrentPhotoPath = this.mPreference.getString(CURRENT_PHOTO_PATH, "");
    }

    public void setAllValue(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, boolean z5, String str, String str2, String str3) {
        this.mSoRequest = i;
        this.mRated = z;
        this.mMuchAds = z2;
        this.mNewLanguage = z3;
        this.mInApp = i2;
        this.mTranslateCamera = z4;
        this.mNoAds = z5;
        this.mContryNameIn = str;
        this.mContryNameOut = str2;
        this.mCurrentPhotoPath = str3;
        this.mEditor.putInt(SO_REQUEST_NAME, i);
        this.mEditor.putBoolean(RATED_NAME, this.mRated);
        this.mEditor.putBoolean(MUCH_ADS, this.mMuchAds);
        this.mEditor.putBoolean(NEW_LANGUAGE, this.mNewLanguage);
        this.mEditor.putInt(IN_APP, this.mInApp);
        this.mEditor.putBoolean(TRANSLATE_CAMERA, this.mTranslateCamera);
        this.mEditor.putBoolean(NO_ADS, this.mNoAds);
        this.mEditor.putString(CONTRY_NAME_IN, this.mContryNameIn);
        this.mEditor.putString(CONTRY_NAME_OUT, this.mContryNameOut);
        this.mEditor.putString(CURRENT_PHOTO_PATH, this.mCurrentPhotoPath);
        this.mEditor.apply();
    }

    public int getmSoRequest() {
        return this.mSoRequest;
    }

    public void setmSoRequest(int i) {
        this.mSoRequest = i;
        this.mEditor.putInt(SO_REQUEST_NAME, i);
        this.mEditor.apply();
    }

    public boolean ismRated() {
        return this.mRated;
    }

    public void setmRated(boolean z) {
        this.mRated = z;
        this.mEditor.putBoolean(RATED_NAME, z);
        this.mEditor.apply();
    }

    public boolean ismMuchAds() {
        return this.mMuchAds;
    }

    public void setmMuchAds(boolean z) {
        this.mMuchAds = z;
        this.mEditor.putBoolean(MUCH_ADS, z);
        this.mEditor.apply();
    }

    public boolean ismNewLanguage() {
        return this.mNewLanguage;
    }

    public void setmNewLanguage(boolean z) {
        this.mNewLanguage = z;
        this.mEditor.putBoolean(NEW_LANGUAGE, z);
        this.mEditor.apply();
    }

    public int getmInApp() {
        return this.mInApp;
    }

    public void setmInApp(int i) {
        this.mInApp = i;
        this.mEditor.putInt(IN_APP, i);
        this.mEditor.apply();
    }

    public boolean ismTranslateCamera() {
        return this.mTranslateCamera;
    }

    public void setmTranslateCamera(boolean z) {
        this.mTranslateCamera = z;
        this.mEditor.putBoolean(TRANSLATE_CAMERA, z);
        this.mEditor.apply();
    }

    public boolean ismNoAds() {
        return this.mNoAds;
    }

    public void setmNoAds(boolean z) {
        this.mNoAds = z;
        this.mEditor.putBoolean(NO_ADS, z);
        this.mEditor.apply();
    }

    public String getmContryNameIn() {
        return this.mContryNameIn;
    }

    public void setmContryNameIn(String str) {
        this.mContryNameIn = str;
        this.mEditor.putString(CONTRY_NAME_IN, str);
        this.mEditor.apply();
    }

    public String getmContryNameOut() {
        return this.mContryNameOut;
    }

    public void setmContryNameOut(String str) {
        this.mContryNameOut = str;
        this.mEditor.putString(CONTRY_NAME_OUT, str);
        this.mEditor.apply();
    }

    public String getmCurrentPhotoPath() {
        return this.mCurrentPhotoPath;
    }

    public void setmCurrentPhotoPath(String str) {
        this.mCurrentPhotoPath = str;
        this.mEditor.putString(CURRENT_PHOTO_PATH, str);
        this.mEditor.apply();
    }
}
