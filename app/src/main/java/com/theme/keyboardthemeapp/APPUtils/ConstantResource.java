package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.content.res.TypedArray;

import com.theme.keyboardthemeapp.R;

public class ConstantResource {
    private String[] CameraOcr;
    private String[] CodeLanguageIn;
    private String[] CodeLanguageOut;
    private String[] CodeOcr;
    private String[] CodeSpeech;
    private String[] CountriesIn;
    private String[] CountriesOut;
    private Context context;
    private String[] NoSpeaker;
    private String[] NoVoice;
    private String[] OcrTextAPI;

    public ConstantResource(Context context) {
        this.context = context;
    }

    public String[] getCountriesOut() {
        if (CountriesOut == null) {
            CountriesOut = context.getResources().getStringArray(R.array.str_language_out);
        }
        return CountriesOut;
    }

    public String[] getCountriesIn() {
        if (CountriesIn == null) {
            CountriesIn = context.getResources().getStringArray(R.array.str_language_in);
        }
        return CountriesIn;
    }

    public String[] getCodeOcr() {
        if (CodeOcr == null) {
            CodeOcr = context.getResources().getStringArray(R.array.Ocr_Code);
        }
        return CodeOcr;
    }

    public String[] getCameraOcr() {
        if (CameraOcr == null) {
            CameraOcr = context.getResources().getStringArray(R.array.cameraOcr);
        }
        return CameraOcr;
    }

    public String[] getNoVoice() {
        if (NoVoice == null) {
            NoVoice = context.getResources().getStringArray(R.array.noVoice);
        }
        return NoVoice;
    }

    public String[] getCodeLanguageIn() {
        if (CodeLanguageIn == null) {
            CodeLanguageIn = context.getResources().getStringArray(R.array.str_code_language_in);
        }
        return CodeLanguageIn;
    }

    public String[] getCodeLanguageOut() {
        if (CodeLanguageOut == null) {
            CodeLanguageOut = context.getResources().getStringArray(R.array.str_code_language_out);
        }
        return CodeLanguageOut;
    }

    public String[] getCodeSpeech() {
        if (CodeSpeech == null) {
            CodeSpeech = context.getResources().getStringArray(R.array.str_code_speech_in);
        }
        return CodeSpeech;
    }

    public String[] getOcrTextAPI() {
        if (OcrTextAPI == null) {
            OcrTextAPI = context.getResources().getStringArray(R.array.Ocr_TextAPI);
        }
        return OcrTextAPI;
    }

    public String[] getNoSpeaker() {
        if (NoSpeaker == null) {
            NoSpeaker = context.getResources().getStringArray(R.array.noSpeaker);
        }
        return NoSpeaker;
    }

    public String getFromLanguageName(String str, String[] strArr, String[] strArr2) {
        for (int i = 0; i < strArr.length; i++) {
            if (str.equalsIgnoreCase(strArr[i])) {
                return strArr2[i];
            }
        }
        return "";
    }

    public int getPosition(String str, String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            if (str.equalsIgnoreCase(strArr[i])) {
                return i;
            }
        }
        return getPosition("English", strArr);
    }
}
