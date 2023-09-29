package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.content.res.TypedArray;

import com.theme.keyboardthemeapp.R;

/* loaded from: classes.dex */
public class Common_Resource {
    private String[] cameraocr;
    private String[] codelangin;
    private String[] codelangout;
    private String[] codeocr;
    private String[] codespeech;
    private String[] contriesIBMSupoort;
    private String[] contriesin;
    private String[] contriesout;
    private TypedArray flagcontriesin;
    private TypedArray flagcontriesocr;
    private TypedArray flagcontriesout;
    private String[] helloWelcome;
    private String[] listKeyOCR;
    private String[] listKeyYTranslate;
    private Context mcontext;
    private String[] noSpeaker;
    private String[] novoice;
    private String[] ocrTextAPI;

    public Common_Resource(Context context) {
        this.mcontext = context;
    }

    public void repairAllResource() {
        getContriesout();
        getContriesin();
        getCodeocr();
        getCameraOcr();
        getNovoice();
        getCodelangin();
        getCodelangout();
        getCodespeech();
        getOcrTextAPI();
        getNoSpeaker();
        getHelloWelcome();
        getListKeyOCR();
        getListKeyYTranslate();
        getFlagcontriesin();
        getFlagcontriesout();
        getFlagcontriesocr();
    }

    public void recycleIdResource() {
        getFlagcontriesin().recycle();
        getFlagcontriesout().recycle();
        getFlagcontriesocr().recycle();
    }

    public String[] getContriesout() {
        if (this.contriesout == null) {
            this.contriesout = this.mcontext.getResources().getStringArray(R.array.str_language_out);
        }
        return this.contriesout;
    }

    public String[] getContriesin() {
        if (this.contriesin == null) {
            this.contriesin = this.mcontext.getResources().getStringArray(R.array.str_language_in);
        }
        return this.contriesin;
    }

    public String[] getCodeocr() {
        if (this.codeocr == null) {
            this.codeocr = this.mcontext.getResources().getStringArray(R.array.Ocr_Code);
        }
        return this.codeocr;
    }

    public String[] getCameraOcr() {
        if (this.cameraocr == null) {
            this.cameraocr = this.mcontext.getResources().getStringArray(R.array.cameraOcr);
        }
        return this.cameraocr;
    }

    public String[] getNovoice() {
        if (this.novoice == null) {
            this.novoice = this.mcontext.getResources().getStringArray(R.array.noVoice);
        }
        return this.novoice;
    }

    public String[] getCodelangin() {
        if (this.codelangin == null) {
            this.codelangin = this.mcontext.getResources().getStringArray(R.array.str_code_language_in);
        }
        return this.codelangin;
    }

    public String[] getCodelangout() {
        if (this.codelangout == null) {
            this.codelangout = this.mcontext.getResources().getStringArray(R.array.str_code_language_out);
        }
        return this.codelangout;
    }

    public String[] getCodespeech() {
        if (this.codespeech == null) {
            this.codespeech = this.mcontext.getResources().getStringArray(R.array.str_code_speech_in);
        }
        return this.codespeech;
    }

    public String[] getOcrTextAPI() {
        if (this.ocrTextAPI == null) {
            this.ocrTextAPI = this.mcontext.getResources().getStringArray(R.array.Ocr_TextAPI);
        }
        return this.ocrTextAPI;
    }

    public String[] getNoSpeaker() {
        if (this.noSpeaker == null) {
            this.noSpeaker = this.mcontext.getResources().getStringArray(R.array.noSpeaker);
        }
        return this.noSpeaker;
    }

    public String[] getHelloWelcome() {
        if (this.helloWelcome == null) {
            this.helloWelcome = this.mcontext.getResources().getStringArray(R.array.hello_welcome);
        }
        return this.helloWelcome;
    }

    public void setHelloWelcome(String[] strArr) {
        this.helloWelcome = strArr;
    }

    public String[] getListKeyOCR() {
        if (this.listKeyOCR == null) {
            this.listKeyOCR = this.mcontext.getResources().getStringArray(R.array.OCRKeyList);
        }
        return this.listKeyOCR;
    }

    public void setListKeyOCR(String[] strArr) {
        this.listKeyOCR = strArr;
    }

    public String[] getListKeyYTranslate() {
        if (this.listKeyYTranslate == null) {
            this.listKeyYTranslate = this.mcontext.getResources().getStringArray(R.array.YTranslateKeyList);
        }
        return this.listKeyYTranslate;
    }

    public void setListKeyYTranslate(String[] strArr) {
        this.listKeyYTranslate = strArr;
    }

    public String[] getContriesIBMSupoort() {
        if (this.contriesIBMSupoort == null) {
            this.contriesIBMSupoort = this.mcontext.getResources().getStringArray(R.array.ibmtranslate);
        }
        return this.contriesIBMSupoort;
    }

    public void setContriesIBMSupoort(String[] strArr) {
        this.contriesIBMSupoort = strArr;
    }

    public TypedArray getFlagcontriesin() {
        return this.flagcontriesin;
    }

    public TypedArray getFlagcontriesout() {
        return this.flagcontriesout;
    }

    public TypedArray getFlagcontriesocr() {
        return this.flagcontriesocr;
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
