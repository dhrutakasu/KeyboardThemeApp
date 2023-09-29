package com.theme.keyboardthemeapp.ngonngu;

import android.content.Context;

import com.theme.keyboardthemeapp.APPUtils.Common_Resource;
import com.theme.keyboardthemeapp.APPUtils.LanguageY;

import java.util.Arrays;

public class NgonNguIn extends NgonNgu {
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

    public NgonNguIn(Context context, String str) {
        super(str);
        this.mContext = context;
        this.mLanguageName = str;
        Common_Resource common_Resource = new Common_Resource(context);
        this.mPosition = common_Resource.getPosition(this.mLanguageName, common_Resource.getContriesin());
        this.mCodeDocmau = common_Resource.getCodelangin()[this.mPosition];
        this.mCodeSpeech = common_Resource.getFromLanguageName(str, common_Resource.getContriesin(), common_Resource.getCodespeech());
        this.mCodeOCR = common_Resource.getFromLanguageName(str, common_Resource.getCameraOcr(), common_Resource.getCodeocr());
        this.mCodeLanguage = common_Resource.getFromLanguageName(str, common_Resource.getContriesin(), common_Resource.getCodelangin());
        this.mText = "";
        if (!getmCodeOCR().isEmpty()) {
            this.isOcrTextAPI = Arrays.asList(common_Resource.getOcrTextAPI()).contains(this.mLanguageName);
        }
        this.isSpeechRecognition = !Arrays.asList(common_Resource.getNovoice()).contains(this.mLanguageName);
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
        Common_Resource common_Resource = new Common_Resource(this.mContext);
        setmPosition(common_Resource.getPosition(getmLanguageName(), common_Resource.getContriesin()));
        setmCodeDocmau(common_Resource.getCodelangin()[getmPosition()]);
//        if (getLanguageY().equals(LanguageY.TAGALOG)) {
//            setmCodeDocmau("fil");
//        }
        setmCodeSpeech(common_Resource.getFromLanguageName(str, common_Resource.getContriesin(), common_Resource.getCodespeech()));
        setmCodeOCR(common_Resource.getFromLanguageName(str, common_Resource.getCameraOcr(), common_Resource.getCodeocr()));
        setmCodeLanguage(common_Resource.getFromLanguageName(str, common_Resource.getContriesin(), common_Resource.getCodelangin()));
        if (!getmCodeOCR().isEmpty()) {
            this.isOcrTextAPI = Arrays.asList(common_Resource.getOcrTextAPI()).contains(this.mLanguageName);
        }
        this.isSpeechRecognition = !Arrays.asList(common_Resource.getNovoice()).contains(this.mLanguageName);
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
