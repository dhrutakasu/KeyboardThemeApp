package com.theme.keyboardthemeapp.Translate;

import android.content.Context;


import com.theme.keyboardthemeapp.APPUtils.ConstantResource;
import com.theme.keyboardthemeapp.APPUtils.LanguageY;

import java.util.Arrays;

public class TranslationOut extends Translation {
    private boolean isTextToSpeech = true;
    private String mCodeDocmau;
    private String mCodeLanguage;
    private Context mContext;
    private String mLanguageName;
    private int mPosition;
    private String mText;

    public TranslationOut(Context context, String str) {
        super(str);
        this.mContext = context;
        this.mLanguageName = str;
        ConstantResource constant_Resource = new ConstantResource(context);
        this.mPosition = constant_Resource.getPosition(this.mLanguageName, constant_Resource.getContriesout());
        this.mCodeDocmau = constant_Resource.getCodelangout()[this.mPosition];
        this.mCodeLanguage = constant_Resource.getFromLanguageName(str, constant_Resource.getContriesout(), constant_Resource.getCodelangout());
        this.mText = "";
        if (getLanguageY().equals(LanguageY.TAGALOG)) {
            this.mCodeDocmau = "fil";
        }
        this.isTextToSpeech = !Arrays.asList(constant_Resource.getNoSpeaker()).contains(this.mLanguageName);
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
        ConstantResource constant_Resource = new ConstantResource(this.mContext);
        this.mPosition = constant_Resource.getPosition(this.mLanguageName, constant_Resource.getContriesout());
        this.mCodeDocmau = constant_Resource.getCodelangout()[this.mPosition];
        if (getLanguageY().equals(LanguageY.TAGALOG)) {
            this.mCodeDocmau = "fil";
        }
        this.mCodeLanguage = constant_Resource.getFromLanguageName(str, constant_Resource.getContriesout(), constant_Resource.getCodelangout());
    }

    public LanguageY getLanguageY() {
        return LanguageY.fromString(getmCodeLanguage());
    }

    public int getmPosition() {
        return this.mPosition;
    }
}
