package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.util.Log;

import com.theme.keyboardthemeapp.Constants;

import java.util.List;

public class MyKeypadDataView extends Keyboard {
    Context context;
    int keyboardHeight;
    int lastHeight;
    int KeyHeight;
    int VerticleGap;
    int margin;
    int set;

    public MyKeypadDataView(Context context, int i, int i2, int i3) {
        super(context, i);
        this.set = 0;
        this.context = context;
        this.set = i3;
        this.keyboardHeight = i2;
        if (i3 == 0) {
            this.KeyHeight = this.keyboardHeight / 4;
        } else {
            this.KeyHeight = i2 / 4;
        }
        this.lastHeight = getKeyHeight() + getVerticalGap();
        setKeyHeight(this.KeyHeight);
    }

    @Override
    public int getHeight() {
        return this.keyboardHeight;
    }

    @Override
    protected void setKeyHeight(int height) {
        if (Constants.ChangeLanguage == 2 || Constants.ChangeLanguage == 3 || Constants.ChangeLanguage == 10 || Constants.ChangeLanguage == 11 || Constants.ChangeLanguage == 12) {
            height -= Constants.pxFromDp(this.context, 3.0f);
        }
        int i = 0;
        for (Key key : getKeys()) {
            key.height = height;
            if (key.y != 0) {
                if (key.y - i > 0) {
                    Log.d("main", "k.y: " + key.y);
                    this.margin = this.margin + this.KeyHeight + this.VerticleGap;
                    i = key.y;
                }
                key.y = this.margin;
            }
        }
        super.setKeyHeight(height);
    }

    @Override
    public int[] getNearestKeys(int i, int i2) {
        List<Key> keys = getKeys();
        int val = 0;
        for (Key key : (Key[]) keys.toArray(new Key[keys.size()])) {
            if (key.isInside(i, i2)) {
                return new int[]{val};
            }
            val++;
        }
        return new int[0];
    }
}
