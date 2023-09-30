package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.util.Log;

import com.theme.keyboardthemeapp.Constants;

import java.util.List;

/* loaded from: classes2.dex */
public class MyKeypadDataView extends Keyboard {
    Context c;
    int keyboardHeight;
    int lastHeight;
    int mKeyHeight;
    int mVerticleGap;
    int margin;
    int set;

    public MyKeypadDataView(Context context, int i) {
        super(context, i);
        this.set = 0;
        this.c = context;
        this.lastHeight = getKeyHeight() + getVerticalGap();
        setKeyHeight(this.mKeyHeight);
    }

    public MyKeypadDataView(Context context, int i, int i2, int i3) {
        super(context, i);
        this.set = 0;
        this.c = context;
        this.set = i3;
        this.keyboardHeight = i2;
        if (i3 == 0) {
            if (Constants.ChangeLanguage == 0) {
                this.mKeyHeight = this.keyboardHeight / 4;
            } else {
                this.mKeyHeight = this.keyboardHeight / 4;
            }
        } else {
            this.mKeyHeight = i2 / 4;
        }
        this.lastHeight = getKeyHeight() + getVerticalGap();
        setKeyHeight(this.mKeyHeight);
    }

    public void setkeyBoardHeight(int i) {
        this.keyboardHeight = i;
        if (this.set == 0) {
            if (Constants.ChangeLanguage == 0) {
                this.mKeyHeight = this.keyboardHeight / 4;
            } else {
                this.mKeyHeight = this.keyboardHeight / 4;
            }
        } else {
            this.mKeyHeight = i / 4;
        }
        this.lastHeight = getKeyHeight() + getVerticalGap();
        setKeyHeight(this.mKeyHeight);
        getNearestKeys(0, 0);
    }

    @Override // android.inputmethodservice.Keyboard
    public int getHeight() {
        return this.keyboardHeight;
    }

    @Override // android.inputmethodservice.Keyboard
    protected void setKeyHeight(int i) {
        if (Constants.ChangeLanguage == 2 || Constants.ChangeLanguage == 3 || Constants.ChangeLanguage == 10 || Constants.ChangeLanguage == 11 || Constants.ChangeLanguage == 12) {
            i -= HindiUtils.pxFromDp(this.c, 3.0f);
        }
        int i2 = 0;
        for (Key key : getKeys()) {
            key.height = i;
            if (key.y != 0) {
                if (key.y - i2 > 0) {
                    Log.d("main", "k.y: " + key.y);
                    this.margin = this.margin + this.mKeyHeight + this.mVerticleGap;
                    i2 = key.y;
                }
                key.y = this.margin;
            }
        }
        super.setKeyHeight(i);
    }

    @Override // android.inputmethodservice.Keyboard
    public int[] getNearestKeys(int i, int i2) {
        List<Key> keys = getKeys();
        int i3 = 0;
        for (Key key : (Key[]) keys.toArray(new Key[keys.size()])) {
            if (key.isInside(i, i2)) {
                return new int[]{i3};
            }
            i3++;
        }
        return new int[0];
    }
}
