package com.theme.keyboardthemeapp;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePref {
    public static final String ENABLE_KEYBOARD = "ENABLE_KEYBOARD";
    public static final String ACTIVATE_KEYBOARD = "ACTIVATE_KEYBOARD";
    public static final String AUTO_CAPITALIZE = "AUTO_CAPITALIZE";
    public static final String SUGGESTION = "SUGGESTION";
    public static final String POPUP = "POPUP";
    public static final String TYPING = "TYPING";
    public static final String PORTRAIT_HEIGHT = "PORTRAIT_HEIGHT";
    public static final String LANDSCAPE_HEIGHT = "LANDSCAPE_HEIGHT";
    public static final String SUGGESTION_TEXT = "SUGGESTION_TEXT";
    public static final String TEXT_COLOR = "TEXT_COLOR";
    public static final String PERVIEW_COLOR = "PERVIEW_COLOR";
    public static final String SOUND_GENERAL = "SOUND_GENERAL";
    public static final String VIBRATION = "VIBRATION";
    public static final String PERVIEW_GENERAL = "PERVIEW_GENERAL";
    public static final String KEYBOARD_POTRAIT_HEIGHT = "KEYBOARD_POTRAIT_HEIGHT";
    public static final String KEYBOARD_LANDSCAP_HEIGHT = "KEYBOARD_LANDSCAP_HEIGHT";
    public static final String PROGRESS_DEFAULT = "PROGRESS_DEFAULT";
    public static final String PROGRESS_DEFAULT_LANDSCAP = "PROGRESS_DEFAULT_LANDSCAP";
    public static final String SUGGESTION_TEXT_SIZE = "SUGGESTION_TEXT_SIZE";
    public static final String SOUND_PROGRESS = "SOUND_PROGRESS";
    public static final String SOUND_ENABLE = "SOUND_ENABLE";
    public static final String COPY_SERVICE = "COPY_SERVICE";
    public static final String ISTEXT_COLOR = "ISTEXT_COLOR";
    public static final String ISCOLOR_CODE_CHANGE = "ISCOLOR_CODE_CHANGE";
    public static final String ISTEXT_COLOR_CODE = "ISTEXT_COLOR_CODE";
    public static final String ISPREVIEW_COLOR_CODE = "ISPREVIEW_COLOR_CODE";
    public static final String TEXT_IS_COLOR_CODE = "TEXT_IS_COLOR_CODE";
    public static final String PREVIEW_IS_COLOR_CODE = "PREVIEW_IS_COLOR_CODE";
    public static final String FONT_STYLE = "FONT_STYLE";
    public static final String SELECT_GIF_THEME_GIF = "SELECT_GIF_THEME_GIF";
    public static final String SELECT_GIF_THEME_THUMB = "SELECT_GIF_THEME_THUMB";
    public static final String SELECT_THEME_THUMB = "SELECT_THEME_THUMB";
    public static final String SELECT_THEME = "SELECT_THEME";


    private SharedPreferences sharedPreferences;
    static final String MySharePref = "MySharePref";

    public MySharePref(Context context) {
        sharedPreferences = context.getSharedPreferences(MySharePref, Context.MODE_PRIVATE);
    }

    public int getPrefInt(String str, int i) {
        return sharedPreferences.getInt(str, i);
    }

    public void putPrefInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public String getPrefString(String str, String s) {
        return sharedPreferences.getString(str, s);
    }

    public void putPrefString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public void putPrefBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public boolean getPrefBoolean(String str, boolean i) {
        return sharedPreferences.getBoolean(str, i);
    }

    public void putPrefLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public long getPrefLong(String key, long i) {
        return sharedPreferences.getLong(key, i);
    }
}
