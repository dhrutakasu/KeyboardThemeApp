package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.MyCustomKeyboardLangAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomKeyboardView extends KeyboardView {
    Context context;
    Typeface fontstyle;
    int hintColorCode;
    String hitLatter;
    int keyHeight;
    int keyWidth;
    List<Keyboard.Key> keys;
    private PopupWindow langPopup;
    MyCustomKeyboardLangAdapter langadp;
    int[] letterX;
    int[] letterY;
    int[] mOffsetInWindow;
    int mPopupPreviewOffsetX;
    int mPopupPreviewOffsetY;
    int mPreviewOffset;
    int mWindowY;
    Paint newpaint;
    NinePatchDrawable npd;
    NinePatchDrawable npdDelete;
    NinePatchDrawable npdDone;
    NinePatchDrawable npdShiftOff;
    NinePatchDrawable npdShiftOn;
    NinePatchDrawable npdSpace;
    NinePatchDrawable npd_presed;
    Drawable popupDrawable;
    int[] previewLayout;
    int[] previewRes;
    Paint simplePaint;
    int textColorCode;
    List<Keyboard.Key> totalkey;
    View vlng;
    int x;
    int y;

    private String[] deafultchar = {"44", "46", "-97886", "64", "35", "36", "37", "38", "40", "41", "42", "43", "45", "33", "34", "39", "58", "59", "47", "63", "126", "177", "215", "247", "8226", "176", "96", "180", "123", "125", "169", "163", "8364", "94", "174", "165", "95", "43", "91", "93", "161", "60", "62", "162", "124", "92", "191", "-6003", "-1763", "8230"};
    private ArrayList<String> defaultCharacter = new ArrayList<>(Arrays.asList(deafultchar));

    public CustomKeyboardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.keys = null;
        this.totalkey = null;
        this.x = 0;
        this.y = 0;
        this.textColorCode = -1;
        this.hintColorCode = -1;
        this.previewRes = new int[]{R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg};
        this.fontstyle = null;
        this.newpaint = new Paint();
        this.simplePaint = new Paint();
        this.keyWidth = 0;
        this.keyHeight = 0;
        this.hitLatter = new String();
        this.letterX = null;
        this.letterY = null;
        this.previewLayout = new int[]{R.layout.keypad_prev, R.layout.keypad_prev1, R.layout.keypad_prev2, R.layout.keypad_prev3, R.layout.keypad_prev4, R.layout.keypad_prev5, R.layout.keypad_prev6, R.layout.keypad_prev7, R.layout.keypad_prev8, R.layout.keypad_prev9};
        this.mOffsetInWindow = null;
        this.mPreviewOffset = 0;
        this.textColorCode = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        this.context = context;
        setPreviewEnabled(false);
        init();
        this.npd = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.key_unpresed);
        this.npd_presed = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.key_presed);
        this.popupDrawable = getResources().getDrawable(this.previewRes[0]);
        this.npdShiftOn = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_shift_on);
        this.npdSpace = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_space);
        this.npdDelete = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_back);
        this.npdDone = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_enter);
    }

    public CustomKeyboardView(Context context) {
        super(context, null);
        this.keys = null;
        this.totalkey = null;
        this.x = 0;
        this.y = 0;
        this.textColorCode = -1;
        this.hintColorCode = -1;
        this.previewRes = new int[]{R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg};
        this.fontstyle = null;
        this.newpaint = new Paint();
        this.simplePaint = new Paint();
        this.keyWidth = 0;
        this.keyHeight = 0;
        this.hitLatter = new String();
        this.letterX = null;
        this.letterY = null;
        this.previewLayout = new int[]{R.layout.keypad_prev, R.layout.keypad_prev1, R.layout.keypad_prev2, R.layout.keypad_prev3, R.layout.keypad_prev4, R.layout.keypad_prev5, R.layout.keypad_prev6, R.layout.keypad_prev7, R.layout.keypad_prev8, R.layout.keypad_prev9};
        this.mOffsetInWindow = null;
        this.mPreviewOffset = 0;
        this.textColorCode = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        this.context = context;
        setPreviewEnabled(false);
        init();
        this.npd = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.key_unpresed);
        this.npd_presed = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.key_presed);
        this.popupDrawable = getResources().getDrawable(this.previewRes[0]);
        this.npdShiftOn = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_shift_on);
        this.npdSpace = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_space);
        this.npdDelete = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_back);
        this.npdDone = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_enter);
    }

    public CustomKeyboardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.keys = null;
        this.totalkey = null;
        this.x = 0;
        this.y = 0;
        this.textColorCode = -1;
        this.hintColorCode = -1;
        this.previewRes = new int[]{R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg};
        this.fontstyle = null;
        this.newpaint = new Paint();
        this.simplePaint = new Paint();
        this.keyWidth = 0;
        this.keyHeight = 0;
        this.hitLatter = new String();
        this.letterX = null;
        this.letterY = null;
        this.previewLayout = new int[]{R.layout.keypad_prev, R.layout.keypad_prev1, R.layout.keypad_prev2, R.layout.keypad_prev3, R.layout.keypad_prev4, R.layout.keypad_prev5, R.layout.keypad_prev6, R.layout.keypad_prev7, R.layout.keypad_prev8, R.layout.keypad_prev9};
        this.mOffsetInWindow = null;
        this.mPreviewOffset = 0;
        this.textColorCode = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        this.context = context;
        setPreviewEnabled(false);
        init();
        this.npd = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.key_unpresed);
        this.npd_presed = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.key_presed);
        this.popupDrawable = getResources().getDrawable(this.previewRes[0]);
        this.npdShiftOn = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_shift_on);
        this.npdSpace = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_space);
        this.npdDelete = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_back);
        this.npdDone = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.btn_enter);
    }

    public void init() {
        this.letterX = new int[126];
        this.letterY = new int[126];
        if (Constants.ChangeLanguage == 0) {
            Typeface createFromAsset = Typeface.createFromAsset(getContext().getAssets(), Constants.HindiFontList[new MySharePref(getContext()).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle)]);
            this.fontstyle = createFromAsset;
            this.newpaint.setTypeface(createFromAsset);
        } else {
            Typeface createFromAsset2 = Typeface.createFromAsset(getContext().getAssets(), Constants.FontList[new MySharePref(context).getPrefInt(MySharePref.BACKGROUND_FONT_STYLE, 0)]);
            this.fontstyle = createFromAsset2;
            this.newpaint.setTypeface(createFromAsset2);
        }
        this.newpaint.setTextAlign(Paint.Align.CENTER);
        this.newpaint.setColor(this.textColorCode);
        this.newpaint.setStrokeWidth(0.3f);
        this.newpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.newpaint.setStrokeWidth(1.0f);
        this.newpaint.setStyle(Paint.Style.FILL);
        this.newpaint.setAntiAlias(true);
        this.simplePaint.setTypeface(Typeface.DEFAULT);
        this.simplePaint.setTextAlign(Paint.Align.CENTER);
        this.simplePaint.setColor(this.textColorCode);
        this.simplePaint.setStrokeWidth(0.3f);
        this.simplePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.simplePaint.setStrokeWidth(1.0f);
        this.simplePaint.setStyle(Paint.Style.FILL);
        this.simplePaint.setAntiAlias(true);
        setPopup();
        invalidate();
    }

    private boolean hasCharKey(Keyboard.Key key) {
        if (key.codes[0] != -2830) {
            if (!((key.codes[0] == -9789020) | (key.codes[0] == -2831) | (key.codes[0] == -5000) | (key.codes[0] == -6002) | (key.codes[0] == -6003) | (key.codes[0] == -1762) | (key.codes[0] == -1763) | (key.codes[0] == -97886) | (key.codes[0] == -97890) | (key.codes[0] == -9789001) | (key.codes[0] == -972550) | (key.codes[0] == -978903) | (key.codes[0] == -99255) | (key.codes[0] == -97255) | (key.codes[0] == -978901) | (key.codes[0] == -978902))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.keys = getKeyboard().getKeys();
        this.totalkey = getKeyboard().getKeys();
        int i = 0;
        for (Keyboard.Key key : this.keys) {
            if (key.label != null) {
                int i2 = key.codes[0];
                if (i2 == -978903) {
                    key.repeatable = false;
                } else if (i2 == -1) {
                    key.repeatable = false;
                } else if (i2 == -5) {
                    key.repeatable = true;
                } else if (i2 == -4) {
                    key.repeatable = false;
                } else {
                    key.repeatable = false;
                    ArrayList<String> arrayList = defaultCharacter;
                    if (!arrayList.contains(key.codes[0] + "")) {
                        if (key.label.toString().equals("?123")) {
                            this.newpaint.setTextSize((int) getResources().getDimension(R.dimen.key123_text_size));
                        } else if (key.label.toString().equals("sym")) {
                            this.newpaint.setTextSize((int) getResources().getDimension(R.dimen.keysym_text_size));
                        } else {
                            this.newpaint.setTextSize((int) getResources().getDimension(R.dimen.key_text_size));
                        }
                        String charSequence = key.label.toString();
                        if (Constants.ChangeLanguage == 0 && charSequence.equalsIgnoreCase("abc")) {
                            charSequence = "abc";
                        }
                        if (CustomKeypad.CapsLock) {
                            charSequence = charSequence.toUpperCase();
                        }
                        canvas.drawText(charSequence, key.x + (key.width / 2), key.y + (key.height / 2) + ((int) getResources().getDimension(com.intuit.sdp.R.dimen._22sdp)), this.newpaint);
                    } else {
                        if (key.label.toString().equals("?123")) {
                            this.simplePaint.setTextSize((int) getResources().getDimension(R.dimen.key123_text_size));
                        } else if (key.label.toString().equals("sym")) {
                            this.simplePaint.setTextSize((int) getResources().getDimension(R.dimen.keysym_text_size));
                        } else {
                            this.simplePaint.setTextSize((int) getResources().getDimension(R.dimen.key_text_size));
                        }
                        String charSequence2 = key.label.toString();
                        if (CustomKeypad.CapsLock) {
                            charSequence2 = charSequence2.toUpperCase();
                        }
                        canvas.drawText(charSequence2, key.x + (key.width / 2), key.y + (key.height / 2) + ((int) getResources().getDimension(com.intuit.sdp.R.dimen._22sdp)), this.simplePaint);
                    }
                    if (hasCharKey(key)) {
                        this.letterX[i] = key.x;
                        this.letterY[i] = key.y;
                        this.keyHeight = key.height;
                        this.keyWidth = key.width;
                        i++;
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.x = (int) motionEvent.getX();
        this.y = (int) motionEvent.getY();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int pointerCount = motionEvent.getPointerCount();
        try {
            int i = 0;
            for (Keyboard.Key key : this.keys) {
                int i2 = key.codes[0];
                if (i2 != -978903 && i2 != -97886 && i2 != -1 && i2 != 32 && i2 != -6003 && i2 != -6002 && i2 != -2831 && i2 != -2830 && i2 != -5 && i2 != -4) {
                    try {
                        if (hasCharKey(key) && pointerCount == 1) {
                            if (x >= this.letterX[i] && x <= this.letterX[i] + this.keyWidth && y >= this.letterY[i] && y <= this.letterY[i] + this.keyHeight) {
                                if (this.hitLatter == null || this.hitLatter.contains("null") || this.hitLatter.length() <= 0) {
                                    this.hitLatter += ((Object) key.label) + "";
                                } else if (!this.hitLatter.endsWith(key.label.toString())) {
                                    this.hitLatter += ((Object) key.label) + "";
                                    showPreviewPopup(key.codes[0], key);
                                }
                            }
                            i++;
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        } catch (Exception unused2) {
        }
        motionEvent.getAction();
        postInvalidate();
        return super.onTouchEvent(motionEvent);
    }

    public void onPressKey(int i) {
        if (i == -1 || i == -5 || i == -978903 || i == -4 || i == 32 || i == -2830 || i == -2831 || i == -6002 || i == -6003 || i == -1762 || i == -1763 || i == -97890 || i == -9789001 || i == -972550 || i == -978901 || i == -978902 || i == -9789020 || i == -99255 || i == -97255 || i == -5000) {
            return;
        }
        for (Keyboard.Key key : this.keys) {
            if (key.codes[0] == i) {
                showPreviewPopup(i, key);
                return;
            }
        }
    }

    private void showPreviewPopup(int i, Keyboard.Key key) {
        try {
            if (key.codes[0] != -97886 && new MySharePref(getContext()).getPrefBoolean(MySharePref.POPUP, true)) {
               Constants.TxtView.setText(key.label.toString());
                if (getResources().getConfiguration().orientation == 1) {
                    showKey(Constants.TxtView, key, 20, 15);
                    return;
                } else {
                    showKey(Constants.TxtView, key, 40, 10);
                    return;
                }
            }
            showLongPressPreviewPopup(key, (char) key.codes[0]);
        } catch (Exception unused) {
            System.out.println("----- - - -unused : " + unused.getMessage());
            unused.printStackTrace();
        }
    }

    private void showKey(TextView textView, Keyboard.Key key, int i, int i2) {
        if (key == null) {
            return;
        }
        textView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int max = Math.max(textView.getMeasuredWidth(), key.width + textView.getPaddingLeft() + textView.getPaddingRight()) + i2;
        int i3 = key.height + i;
        int i4 = key.x - ((max - key.width) / 2);
        int i5 = (key.y - i3) + this.mPreviewOffset;
        if (this.mOffsetInWindow == null) {
            int[] iArr = new int[2];
            this.mOffsetInWindow = iArr;
            getLocationInWindow(iArr);
            int[] iArr2 = this.mOffsetInWindow;
            iArr2[0] = iArr2[0] + this.mPopupPreviewOffsetX;
            iArr2[1] = iArr2[1] + this.mPopupPreviewOffsetY;
            int[] iArr3 = new int[2];
            getLocationOnScreen(iArr3);
            this.mWindowY = iArr3[1];
        }
        int[] iArr4 = this.mOffsetInWindow;
        int i6 = i4 + iArr4[0];
        int i7 = i5 + iArr4[1];
        if (this.mWindowY + i7 < 0) {
            if (key.x + key.width <= getWidth() / 2) {
                double d = key.width;
                Double.isNaN(d);
                i6 += (int) (d * 2.5d);
            } else {
                double d2 = key.width;
                Double.isNaN(d2);
                i6 -= (int) (d2 * 2.5d);
            }
            i7 += i3;
        }
        if (Constants.popupScreen.isShowing()) {
            Constants.popupScreen.update(i6 - 5, i7 - 20, max, i3);
            return;
        }
        Constants.popupScreen.setWidth(max);
        Constants.popupScreen.setHeight(i3);
        Constants.popupScreen.showAtLocation(this, 0, i6, i7 - 18);
    }

    private void showLongPressPreviewPopup(Keyboard.Key key, char c) {
        this.langadp = new MyCustomKeyboardLangAdapter(this.context, Constants.languegesArray, this);
        View inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.langlist_popup, (ViewGroup) null, false);
        this.vlng = inflate;
        ((ListView) inflate.findViewById(R.id.keyboardLangList)).setAdapter((ListAdapter) this.langadp);
        Typeface createFromAsset = Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-Medium.otf");
       Constants.TxtLongPressView = (TextView) this.vlng.findViewById(R.id.textView1);
       Constants.TxtLongPressView.setTypeface(createFromAsset);
        if (Constants.popupWindow == null) {
            Constants.popupWindow = new PopupWindow(this.context);
        }
        Constants.popupWindow.setContentView(this.vlng);
        Constants.popupWindow.setBackgroundDrawable(null);
        Constants.popupWindow.setTouchable(true);
        Constants.popupWindow.setAnimationStyle(R.style.PreviewPopupAnimation);
        if (Constants.TxtLongPressView != null) {
            showKeypopup(key, Constants.pxFromDp(this.context, 130.0f), Constants.pxFromDp(this.context, 100.0f),Constants.TxtLongPressView);
        }
    }

    private void showKeypopup(Keyboard.Key key, int i, int i2, TextView textView) {
        int pxFromDp;
        int pxFromDp2;
        dismissLangPopup();
        if (key == null) {
            return;
        }
        textView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int max = Math.max(textView.getMeasuredWidth(), key.width + textView.getPaddingLeft() + textView.getPaddingRight()) + i2;
        int i3 = key.height + i;
        int i4 = key.x - ((max - key.width) / 2);
        int i5 = (key.y - i3) + this.mPreviewOffset;
        if (this.mOffsetInWindow == null) {
            int[] iArr = new int[2];
            this.mOffsetInWindow = iArr;
            getLocationInWindow(iArr);
            int[] iArr2 = this.mOffsetInWindow;
            iArr2[0] = iArr2[0] + this.mPopupPreviewOffsetX;
            iArr2[1] = iArr2[1] + this.mPopupPreviewOffsetY;
            int[] iArr3 = new int[2];
            getLocationOnScreen(iArr3);
            this.mWindowY = iArr3[1];
        }
        int[] iArr4 = this.mOffsetInWindow;
        int i6 = i4 + iArr4[0];
        int i7 = i5 + iArr4[1];
        if (this.mWindowY + i7 < 0) {
            if (key.x + key.width <= getWidth() / 2) {
                double d = key.width;
                Double.isNaN(d);
                i6 += (int) (d * 2.5d);
            } else {
                double d2 = key.width;
                Double.isNaN(d2);
                i6 -= (int) (d2 * 2.5d);
            }
            i7 += i3;
        }
        if (getResources().getConfiguration().orientation == 1) {
            pxFromDp = i6 + Constants.pxFromDp(this.context, 10.0f);
            pxFromDp2 = Constants.pxFromDp(this.context, 35.0f);
        } else {
            pxFromDp = i6 + Constants.pxFromDp(this.context, 14.0f);
            pxFromDp2 = Constants.pxFromDp(this.context, 26.0f);
        }
        int i8 = i7 + pxFromDp2;
        if (Constants.popupWindow.isShowing()) {
            Constants.popupWindow.update(pxFromDp, i8, max, i3);
            return;
        }
        Constants.popupWindow.setWidth(max);
        Constants.popupWindow.setHeight(i3);
        Constants.popupWindow.showAtLocation(this, 0, pxFromDp, i8);
    }

    public void dismissPreviewPopup() {
        try {
            if (Constants.popupScreen.isShowing()) {
                Constants.popupScreen.dismiss();
            }
        } catch (Exception unused) {
        }
        invalidateAllKeys();
    }

    public void dismissLangPopup() {
        try {
            if (Constants.popupWindow.isShowing()) {
                Constants.popupWindow.dismiss();
            }
        } catch (Exception unused) {
        }
        invalidateAllKeys();
    }

    public void setOnlineKeyboard(NinePatchDrawable ninePatchDrawable, NinePatchDrawable ninePatchDrawable2, int i, NinePatchDrawable ninePatchDrawable3, NinePatchDrawable ninePatchDrawable4, NinePatchDrawable ninePatchDrawable5, NinePatchDrawable ninePatchDrawable6, NinePatchDrawable ninePatchDrawable7, Drawable drawable) {
        this.npd = ninePatchDrawable;
        this.npd_presed = ninePatchDrawable2;
        this.npdDelete = ninePatchDrawable6;
        this.npdDone = ninePatchDrawable7;
        this.npdShiftOff = ninePatchDrawable4;
        this.npdShiftOn = ninePatchDrawable5;
        this.npdSpace = ninePatchDrawable3;
        this.textColorCode = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        this.popupDrawable = drawable;
        this.newpaint.setColor(new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)));
        this.simplePaint.setColor(new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)));
        try {
            this.npdShiftOn.setColorFilter(new PorterDuffColorFilter(this.textColorCode, PorterDuff.Mode.SRC_IN));
            this.npdShiftOff.setColorFilter(new PorterDuffColorFilter(this.textColorCode, PorterDuff.Mode.SRC_IN));
            this.npdSpace.setColorFilter(new PorterDuffColorFilter(this.textColorCode, PorterDuff.Mode.SRC_IN));
            this.npdDone.setColorFilter(new PorterDuffColorFilter(this.textColorCode, PorterDuff.Mode.SRC_IN));
            this.npdDelete.setColorFilter(new PorterDuffColorFilter(this.textColorCode, PorterDuff.Mode.SRC_IN));
        } catch (Exception e) {
            Log.e("EROOR:", "" + e);
        }
        setPopup();
        invalidate();
    }

    public void setPopup() {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (Constants.popupScreen == null) {
            Constants.popupScreen = new PopupWindow(this.context);
        }
        if (Constants.TxtView == null) {
            if (new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0) > 9) {
               Constants.TxtView = (TextView) layoutInflater.inflate(this.previewLayout[0], (ViewGroup) null, false);
            } else {
               Constants.TxtView = (TextView) layoutInflater.inflate(this.previewLayout[new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0) ], (ViewGroup) null, false);
            }
        }
        if (new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0)  > 9) {
            if (new MySharePref(context).getPrefInt(MySharePref.PREVIOUS_THEME, -1)  != -1 && new MySharePref(context).getPrefInt(MySharePref.PREVIOUS_THEME, -1)  != 0) {
               Constants.TxtView = null;
               Constants.TxtView = (TextView) layoutInflater.inflate(this.previewLayout[0], (ViewGroup) null, false);

                new MySharePref(context).putPrefInt(MySharePref.PREVIOUS_THEME, new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0));
            }
        } else if (new MySharePref(context).getPrefInt(MySharePref.PREVIOUS_THEME, -1)  != -1 && new MySharePref(context).getPrefInt(MySharePref.PREVIOUS_THEME, -1)  != new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0)) {
           Constants.TxtView = null;
           Constants.TxtView = (TextView) layoutInflater.inflate(this.previewLayout[new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0) ], (ViewGroup) null, false);
            new MySharePref(context).putPrefInt(MySharePref.PREVIOUS_THEME, new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0));
        }
        if (Constants.ChangeLanguage == 0) {
           Constants.TxtView.setTypeface(this.fontstyle);
        } else {
           Constants.TxtView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (Constants.ChangeLanguage == 0) {
           Constants.TxtView.setPadding(0, 15, 0, 0);
        } else {
           Constants.TxtView.setPadding(0, 10, 0, 0);
        }

        StringBuilder builderPreview = new StringBuilder();
        int PreviewInt = 0;
        while (true) {
            String[] PreviewStrArr = Constants.ThemePreviewTextColor;
            if (PreviewInt < PreviewStrArr.length) {
                builderPreview.append(PreviewStrArr[PreviewInt]);
                builderPreview.append(",");
                PreviewInt++;
            } else {
                String string2 = new MySharePref(getContext()).getPrefString(MySharePref.ISPREVIEW_COLOR_CODE, builderPreview.toString());
                Constants.ThemePreviewTextColor = new String[10];
                Constants.ThemePreviewTextColor = string2.split(",");
                break;
            }
        }

        if (new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0) > 9) {
           Constants.TxtView.setTextColor(Color.parseColor(Constants.ThemePreviewTextColor[0]));
        } else {
           Constants.TxtView.setTextColor(Color.parseColor(Constants.ThemePreviewTextColor[Constants.SelectTheme]));
        }
        Constants.popupScreen.setContentView(Constants.TxtView);
        Constants.popupScreen.setBackgroundDrawable(null);
        Constants.popupScreen.setTouchable(false);
        Constants.popupScreen.setAnimationStyle(R.style.PreviewPopupAnimation);
    }
}
