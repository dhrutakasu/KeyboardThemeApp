package com.theme.keyboardthemeapp.ngonngu;

import android.content.Context;


import com.theme.keyboardthemeapp.APPUtils.Common_Resource;
import com.theme.keyboardthemeapp.APPUtils.LanguageY;

import java.util.Arrays;

public class NgonNguOut extends NgonNgu {
    private boolean isTextToSpeech = true;
    private String mCodeDocmau;
    private String mCodeLanguage;
    private Context mContext;
    private String mLanguageName;
    private int mPosition;
    private String mText;

    public NgonNguOut(Context context, String str) {
        super(str);
        this.mContext = context;
        this.mLanguageName = str;
        Common_Resource common_Resource = new Common_Resource(context);
        this.mPosition = common_Resource.getPosition(this.mLanguageName, common_Resource.getContriesout());
        this.mCodeDocmau = common_Resource.getCodelangout()[this.mPosition];
        this.mCodeLanguage = common_Resource.getFromLanguageName(str, common_Resource.getContriesout(), common_Resource.getCodelangout());
        this.mText = "";
        if (getLanguageY().equals(LanguageY.TAGALOG)) {
            this.mCodeDocmau = "fil";
        }
        this.isTextToSpeech = !Arrays.asList(common_Resource.getNoSpeaker()).contains(this.mLanguageName);
    }

    public String getmText() {
        return this.mText;
    }

    public void setmText(String str) {
        this.mText = str;
    }

    public String getmLanguageName() {
        return this.mLanguageName;
    }

    public void setmLanguageName(String str) {
        this.mLanguageName = str;
    }

    public String getmCodeDocmau() {
        return this.mCodeDocmau;
    }

    public void setmCodeDocmau(String str) {
        this.mCodeDocmau = str;
    }

    public String getmCodeLanguage() {
        return this.mCodeLanguage;
    }

    public void setmCodeLanguage(String str) {
        this.mCodeLanguage = str;
    }

    public boolean isTextToSpeech() {
        return this.isTextToSpeech;
    }

    public void updateNgonNguOut(String str) {
        this.mLanguageName = str;
        Common_Resource common_Resource = new Common_Resource(this.mContext);
        this.mPosition = common_Resource.getPosition(this.mLanguageName, common_Resource.getContriesout());
        this.mCodeDocmau = common_Resource.getCodelangout()[this.mPosition];
        if (getLanguageY().equals(LanguageY.TAGALOG)) {
            this.mCodeDocmau = "fil";
        }
        this.mCodeLanguage = common_Resource.getFromLanguageName(str, common_Resource.getContriesout(), common_Resource.getCodelangout());
    }

    public LanguageY getLanguageY() {
        return LanguageY.fromString(getmCodeLanguage());
    }

    public int getmPosition() {
        return this.mPosition;
    }
}
