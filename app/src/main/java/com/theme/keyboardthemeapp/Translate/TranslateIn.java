package com.theme.keyboardthemeapp.Translate;

import android.content.Context;

import com.theme.keyboardthemeapp.APPUtils.ConstantResource;
import com.theme.keyboardthemeapp.APPUtils.LanguageY;

import java.util.Arrays;

public class TranslateIn extends Translation {
    private boolean isOcrTextAPI = false;
    private boolean isSpeechRecognition = true;
    private boolean isTextToSpeech = true;
    private String mCodeDocmau;
    private String mCodeLanguage;
    private String mCodeOCR;
    private String mCodeSpeech;
    private Context mContext;
    private Integer mIdImageFlag;
    private String mLanguageName;
    private int mPosition;
    private String mText;

    public TranslateIn(Context context, String str) {
        super(str);
        this.mContext = context;
        this.mLanguageName = str;
        ConstantResource constant_Resource = new ConstantResource(context);
        this.mPosition = constant_Resource.getPosition(this.mLanguageName, constant_Resource.getCountriesIn());
        this.mCodeDocmau = constant_Resource.getCodeLanguageIn()[this.mPosition];
        this.mCodeSpeech = constant_Resource.getFromLanguageName(str, constant_Resource.getCountriesIn(), constant_Resource.getCodeSpeech());
        this.mCodeOCR = constant_Resource.getFromLanguageName(str, constant_Resource.getCameraOcr(), constant_Resource.getCodeOcr());
        this.mCodeLanguage = constant_Resource.getFromLanguageName(str, constant_Resource.getCountriesIn(), constant_Resource.getCodeLanguageIn());
        this.mText = "";
        if (!getmCodeOCR().isEmpty()) {
            this.isOcrTextAPI = Arrays.asList(constant_Resource.getOcrTextAPI()).contains(this.mLanguageName);
        }
        this.isSpeechRecognition = !Arrays.asList(constant_Resource.getNoVoice()).contains(this.mLanguageName);
        this.isTextToSpeech = true;
    }

    public String getmCodeSpeech() {
        return this.mCodeSpeech;
    }

    public void setmCodeSpeech(String str) {
        this.mCodeSpeech = str;
    }

    public String getmCodeOCR() {
        return this.mCodeOCR;
    }

    public void setmCodeOCR(String str) {
        this.mCodeOCR = str;
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

    public void updateNgonNguIn(String str) {
        setmLanguageName(str);
        ConstantResource constant_Resource = new ConstantResource(this.mContext);
        setmPosition(constant_Resource.getPosition(getmLanguageName(), constant_Resource.getCountriesIn()));
        setmCodeDocmau(constant_Resource.getCodeLanguageIn()[getmPosition()]);
        setmCodeSpeech(constant_Resource.getFromLanguageName(str, constant_Resource.getCountriesIn(), constant_Resource.getCodeSpeech()));
        setmCodeOCR(constant_Resource.getFromLanguageName(str, constant_Resource.getCameraOcr(), constant_Resource.getCodeOcr()));
        setmCodeLanguage(constant_Resource.getFromLanguageName(str, constant_Resource.getCountriesIn(), constant_Resource.getCodeLanguageIn()));
        if (!getmCodeOCR().isEmpty()) {
            this.isOcrTextAPI = Arrays.asList(constant_Resource.getOcrTextAPI()).contains(this.mLanguageName);
        }
        this.isSpeechRecognition = !Arrays.asList(constant_Resource.getNoVoice()).contains(this.mLanguageName);
    }

    public LanguageY getLanguageY() {
        return LanguageY.fromString(getmCodeLanguage());
    }

    public boolean isOCRTextAPI() {
        return this.isOcrTextAPI;
    }

    public boolean isSpeechRecognition() {
        return this.isSpeechRecognition;
    }

    public int getmPosition() {
        return this.mPosition;
    }

    public void setmPosition(int i) {
        this.mPosition = i;
    }
}
