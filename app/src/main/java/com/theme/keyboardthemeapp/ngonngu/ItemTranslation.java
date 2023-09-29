package com.theme.keyboardthemeapp.ngonngu;

import android.content.Context;

public class ItemTranslation {
    private String docmauin;
    private String docmauout;
    private int id;
    private String lagin;
    private String lagout;
    private NgonNguIn mNgonNguIn;
    private NgonNguOut mNgonNguOut;
    Context mcontext;
    private String textin;
    private String textout;

    public ItemTranslation() {
    }

    public ItemTranslation(int i, NgonNguIn ngonNguIn, NgonNguOut ngonNguOut) {
        this.id = i;
        this.mNgonNguIn = ngonNguIn;
        this.mNgonNguOut = ngonNguOut;
        this.lagin = ngonNguIn.getmLanguageName();
        this.lagout = this.mNgonNguOut.getmLanguageName();
        this.textin = this.mNgonNguIn.getmText();
        this.textout = this.mNgonNguOut.getmText();
        this.docmauin = this.mNgonNguIn.getmCodeDocmau();
        this.docmauout = this.mNgonNguOut.getmCodeDocmau();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public NgonNguIn getmNgonNguIn() {
        return this.mNgonNguIn;
    }

    public void setmNgonNguIn(NgonNguIn ngonNguIn) {
        this.mNgonNguIn = ngonNguIn;
        this.lagin = ngonNguIn.getmLanguageName();
        this.textin = ngonNguIn.getmText();
        this.docmauin = ngonNguIn.getmCodeDocmau();
    }

    public NgonNguOut getmNgonNguOut() {
        return this.mNgonNguOut;
    }

    public void setmNgonNguOut(NgonNguOut ngonNguOut) {
        this.mNgonNguOut = ngonNguOut;
        this.lagout = ngonNguOut.getmLanguageName();
        this.textout = ngonNguOut.getmText();
        this.docmauout = ngonNguOut.getmCodeDocmau();
    }

    public String getDocmauin() {
        return this.docmauin;
    }

    public void setDocmauin(String str) {
        this.docmauin = str;
    }

    public String getDocmauout() {
        return this.docmauout;
    }

    public void setDocmauout(String str) {
        this.docmauout = str;
    }

    public ItemTranslation(int i, String str, String str2, String str3, String str4) {
        this.id = i;
        this.lagin = str;
        this.lagout = str2;
        this.textin = str3;
        this.textout = str4;
    }

    public String getLagin() {
        return this.lagin;
    }

    public void setLagin(String str) {
        this.lagin = str;
    }

    public String getLagout() {
        return this.lagout;
    }

    public void setLagout(String str) {
        this.lagout = str;
    }

    public String getTextin() {
        return this.textin;
    }

    public void setTextin(String str) {
        this.textin = str;
    }

    public String getTextout() {
        return this.textout;
    }

    public void setTextout(String str) {
        this.textout = str;
    }
}
