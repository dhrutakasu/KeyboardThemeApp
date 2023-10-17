package com.theme.keyboardthemeapp.KeyboardView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
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
import com.theme.keyboardthemeapp.UI.Adapters.CustomKeyboardLanguageAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomKeyboardView extends KeyboardView {
    public static Context context;
    public static Typeface fontTypeface;
    public static int HintColorCode;
    public static String HitLetter;
    public static int KeyHeight;
    public static int KeyWidth;
    public static List<Keyboard.Key> KeboardKeys;
    public static CustomKeyboardLanguageAdapter LanguageAdapter;
    public static int[] LetterXInts;
    public static int PopupPreviewOffsetYInt;
    public static int[] LetterYInts;
    public static int PopupPreviewOffsetXInt;
    public static int WindowYInt;
    public static Paint NewPaint;
    public static int[] PreviewLayoutInts;
    public static int[] OffsetInWindowInts;
    public static int TxtColorCodeInt;
    public static int PreviewOffsetInt;
    public static Drawable PopupDrawable;
    public static int[] PreviewResInts;
    public static Paint SimplePaint;
    public static List<Keyboard.Key> TotalKeyboardKey;
    public static View ViewLanguage;
    public static int XInt;
    public static int YInt;

    public static String[] CharStrs = {"44", "46", "-97886", "64", "35", "36", "37", "38", "40", "41", "42", "43", "45", "33", "34", "39", "58", "59", "47", "63", "126", "177", "215", "247", "8226", "176", "96", "180", "123", "125", "169", "163", "8364", "94", "174", "165", "95", "43", "91", "93", "161", "60", "62", "162", "124", "92", "191", "-6003", "-1763", "8230"};
    public static ArrayList<String> DefaultCharacterList = new ArrayList<>(Arrays.asList(CharStrs));

    public CustomKeyboardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        KeboardKeys = null;
        TotalKeyboardKey = null;
        XInt = 0;
        YInt = 0;
        TxtColorCodeInt = -1;
        HintColorCode = -1;
        PreviewResInts = new int[]{R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg};
        fontTypeface = null;
        NewPaint = new Paint();
        SimplePaint = new Paint();
        KeyWidth = 0;
        KeyHeight = 0;
        HitLetter = new String();
        LetterXInts = null;
        LetterYInts = null;
        PreviewLayoutInts = new int[]{R.layout.layout_keypad_prev, R.layout.layout_keypad_prev1, R.layout.layout_keypad_prev2, R.layout.layout_keypad_prev3, R.layout.layout_keypad_prev4, R.layout.layout_keypad_prev5, R.layout.layout_keypad_prev6, R.layout.layout_keypad_prev7, R.layout.layout_keypad_prev8, R.layout.layout_keypad_prev9};
        OffsetInWindowInts = null;
        PreviewOffsetInt = 0;
        TxtColorCodeInt = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        this.context = context;
        setPreviewEnabled(false);
        InitActions();
        PopupDrawable = getResources().getDrawable(PreviewResInts[0]);
    }

    public CustomKeyboardView(Context context) {
        super(context, null);
        KeboardKeys = null;
        TotalKeyboardKey = null;
        XInt = 0;
        YInt = 0;
        TxtColorCodeInt = -1;
        HintColorCode = -1;
        PreviewResInts = new int[]{R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg};
        fontTypeface = null;
        NewPaint = new Paint();
        SimplePaint = new Paint();
        KeyWidth = 0;
        KeyHeight = 0;
        HitLetter = new String();
        LetterXInts = null;
        LetterYInts = null;
        PreviewLayoutInts = new int[]{R.layout.layout_keypad_prev, R.layout.layout_keypad_prev1, R.layout.layout_keypad_prev2, R.layout.layout_keypad_prev3, R.layout.layout_keypad_prev4, R.layout.layout_keypad_prev5, R.layout.layout_keypad_prev6, R.layout.layout_keypad_prev7, R.layout.layout_keypad_prev8, R.layout.layout_keypad_prev9};
        OffsetInWindowInts = null;
        PreviewOffsetInt = 0;
        TxtColorCodeInt = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        this.context = context;
        setPreviewEnabled(false);
        InitActions();
        PopupDrawable = getResources().getDrawable(PreviewResInts[0]);
    }

    public CustomKeyboardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        KeboardKeys = null;
        TotalKeyboardKey = null;
        XInt = 0;
        YInt = 0;
        TxtColorCodeInt = -1;
        HintColorCode = -1;
        PreviewResInts = new int[]{R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg};
        fontTypeface = null;
        NewPaint = new Paint();
        SimplePaint = new Paint();
        KeyWidth = 0;
        KeyHeight = 0;
        HitLetter = new String();
        LetterXInts = null;
        LetterYInts = null;
        PreviewLayoutInts = new int[]{R.layout.layout_keypad_prev, R.layout.layout_keypad_prev1, R.layout.layout_keypad_prev2, R.layout.layout_keypad_prev3, R.layout.layout_keypad_prev4, R.layout.layout_keypad_prev5, R.layout.layout_keypad_prev6, R.layout.layout_keypad_prev7, R.layout.layout_keypad_prev8, R.layout.layout_keypad_prev9};
        OffsetInWindowInts = null;
        PreviewOffsetInt = 0;
        TxtColorCodeInt = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        this.context = context;
        setPreviewEnabled(false);
        InitActions();
        PopupDrawable = getResources().getDrawable(PreviewResInts[0]);
    }

    public void InitActions() {
        LetterXInts = new int[126];
        LetterYInts = new int[126];
        if (Constants.ChangeLanguage == 0) {
            Typeface createFromAsset = Typeface.createFromAsset(getContext().getAssets(), Constants.HindiFontList[new MySharePref(getContext()).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle)]);
            fontTypeface = createFromAsset;
            NewPaint.setTypeface(createFromAsset);
        } else {
            Typeface createFromAsset2 = Typeface.createFromAsset(getContext().getAssets(), Constants.FontList[new MySharePref(context).getPrefInt(MySharePref.BACKGROUND_FONT_STYLE, 0)]);
            fontTypeface = createFromAsset2;
            NewPaint.setTypeface(createFromAsset2);
        }
        NewPaint.setTextAlign(Paint.Align.CENTER);
        NewPaint.setColor(TxtColorCodeInt);
        NewPaint.setStrokeWidth(0.3f);
        NewPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        NewPaint.setStrokeWidth(1.0f);
        NewPaint.setStyle(Paint.Style.FILL);
        NewPaint.setAntiAlias(true);
        SimplePaint.setTypeface(Typeface.DEFAULT);
        SimplePaint.setTextAlign(Paint.Align.CENTER);
        SimplePaint.setColor(TxtColorCodeInt);
        SimplePaint.setStrokeWidth(0.3f);
        SimplePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        SimplePaint.setStrokeWidth(1.0f);
        SimplePaint.setStyle(Paint.Style.FILL);
        SimplePaint.setAntiAlias(true);
        SetPopupVIew();
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
        KeboardKeys = getKeyboard().getKeys();
        TotalKeyboardKey = getKeyboard().getKeys();
        int val = 0;
        for (Keyboard.Key key : KeboardKeys) {
            if (key.label != null) {
                int code = key.codes[0];
                if (code == -978903) {
                    key.repeatable = false;
                } else if (code == -1) {
                    key.repeatable = false;
                } else if (code == -5) {
                    key.repeatable = true;
                } else if (code == -4) {
                    key.repeatable = false;
                } else {
                    key.repeatable = false;
                    ArrayList<String> defaultCharacterList = DefaultCharacterList;
                    if (!defaultCharacterList.contains(key.codes[0] + "")) {
                        if (key.label.toString().equals("?123")) {
                            NewPaint.setTextSize((int) getResources().getDimension(R.dimen.key123_text_size));
                        } else if (key.label.toString().equals("sym")) {
                            NewPaint.setTextSize((int) getResources().getDimension(R.dimen.keysym_text_size));
                        } else {
                            NewPaint.setTextSize((int) getResources().getDimension(R.dimen.key_text_size));
                        }
                        String label = key.label.toString();
                        if (Constants.ChangeLanguage == 0 && label.equalsIgnoreCase("abc")) {
                            label = "abc";
                        }
                        if (CustomKeypad.CapsLock) {
                            label = label.toUpperCase();
                        }
                        canvas.drawText(label, key.x + (key.width / 2), key.y + (key.height / 2) + ((int) getResources().getDimension(com.intuit.sdp.R.dimen._22sdp)), NewPaint);
                    } else {
                        if (key.label.toString().equals("?123")) {
                            SimplePaint.setTextSize((int) getResources().getDimension(R.dimen.key123_text_size));
                        } else if (key.label.toString().equals("sym")) {
                            SimplePaint.setTextSize((int) getResources().getDimension(R.dimen.keysym_text_size));
                        } else {
                            SimplePaint.setTextSize((int) getResources().getDimension(R.dimen.key_text_size));
                        }
                        String labels = key.label.toString();
                        if (CustomKeypad.CapsLock) {
                            labels = labels.toUpperCase();
                        }
                        canvas.drawText(labels, key.x + (key.width / 2), key.y + (key.height / 2) + ((int) getResources().getDimension(com.intuit.sdp.R.dimen._22sdp)), SimplePaint);
                    }
                    if (hasCharKey(key)) {
                        LetterXInts[val] = key.x;
                        LetterYInts[val] = key.y;
                        KeyHeight = key.height;
                        KeyWidth = key.width;
                        val++;
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        XInt = (int) event.getX();
        YInt = (int) event.getY();
        float x = event.getX();
        float y = event.getY();
        int pointerCount = event.getPointerCount();
        try {
            int val = 0;
            for (Keyboard.Key key : KeboardKeys) {
                int code = key.codes[0];
                if (code != -978903 && code != -97886 && code != -1 && code != 32 && code != -6003 && code != -6002 && code != -2831 && code != -2830 && code != -5 && code != -4) {
                    try {
                        if (hasCharKey(key) && pointerCount == 1) {
                            if (x >= LetterXInts[val] && x <= LetterXInts[val] + KeyWidth && y >= LetterYInts[val] && y <= LetterYInts[val] + KeyHeight) {
                                if (HitLetter == null || HitLetter.contains("null") || HitLetter.length() <= 0) {
                                    HitLetter += ((Object) key.label) + "";
                                } else if (!HitLetter.endsWith(key.label.toString())) {
                                    HitLetter += ((Object) key.label) + "";
                                    ShowPopupView(key);
                                }
                            }
                            val++;
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        } catch (Exception unused2) {
        }
        event.getAction();
        postInvalidate();
        return super.onTouchEvent(event);
    }

    public void onPressKey(int code) {
        if (code == -1 || code == -5 || code == -978903 || code == -4 || code == 32 || code == -2830 || code == -2831 || code == -6002 || code == -6003 || code == -1762 || code == -1763 || code == -97890 || code == -9789001 || code == -972550 || code == -978901 || code == -978902 || code == -9789020 || code == -99255 || code == -97255 || code == -5000) {
            return;
        }
        for (Keyboard.Key key : KeboardKeys) {
            if (key.codes[0] == code) {
                ShowPopupView(key);
                return;
            }
        }
    }

    private void ShowPopupView(Keyboard.Key key) {
        try {
            if (key.codes[0] != -97886 && new MySharePref(getContext()).getPrefBoolean(MySharePref.POPUP, true)) {
                Constants.TxtView.setText(key.label.toString());
                if (getResources().getConfiguration().orientation == 1) {
                    ShowKeyCode(Constants.TxtView, key, 20, 15);
                } else {
                    ShowKeyCode(Constants.TxtView, key, 40, 10);
                }
                return;
            }
            ShowLongPreviewPopupView(key);
        } catch (Exception unused) {
            unused.printStackTrace();
        }
    }

    private void ShowKeyCode(TextView TxtView, Keyboard.Key key, int valI, int vals) {
        if (key == null) {
            return;
        }
        TxtView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int max = Math.max(TxtView.getMeasuredWidth(), key.width + TxtView.getPaddingLeft() + TxtView.getPaddingRight()) + vals;
        int height = key.height + valI;
        int width = key.x - ((max - key.width) / 2);
        int offset = (key.y - height) + PreviewOffsetInt;
        if (OffsetInWindowInts == null) {
            int[] ints = new int[2];
            OffsetInWindowInts = ints;
            getLocationInWindow(ints);
            int[] inWindowInts = OffsetInWindowInts;
            inWindowInts[0] = inWindowInts[0] + PopupPreviewOffsetXInt;
            inWindowInts[1] = inWindowInts[1] + PopupPreviewOffsetYInt;
            int[] iarr3 = new int[2];
            getLocationOnScreen(iarr3);
            WindowYInt = iarr3[1];
        }
        int[] inWindowInts = OffsetInWindowInts;
        int widthS = width + inWindowInts[0];
        int Offsets = offset + inWindowInts[1];
        if (WindowYInt + Offsets < 0) {
            if (key.x + key.width <= getWidth() / 2) {
                double width1 = key.width;
                Double.isNaN(width1);
                widthS += (int) (width1 * 2.5d);
            } else {
                double width1 = key.width;
                Double.isNaN(width1);
                widthS -= (int) (width1 * 2.5d);
            }
            Offsets += height;
        }
        if (Constants.popupScreen.isShowing()) {
            Constants.popupScreen.update(widthS - 5, Offsets - 20, max, height);
            return;
        }
        Constants.popupScreen.setWidth(max);
        Constants.popupScreen.setHeight(height);
        Constants.popupScreen.showAtLocation(this, 0, widthS, Offsets - 18);
    }

    private void ShowLongPreviewPopupView(Keyboard.Key key) {
        LanguageAdapter = new CustomKeyboardLanguageAdapter(context, Constants.LangugaeArr, this);
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_language_list_popup, (ViewGroup) null, false);
        ViewLanguage = view;
        ((ListView) view.findViewById(R.id.keyboardLanguageList)).setAdapter((ListAdapter) LanguageAdapter);
        Typeface fromAsset = Typeface.createFromAsset(getContext().getAssets(), "AvenirLTStd-Medium.otf");
        Constants.TxtLongPressView = (TextView) ViewLanguage.findViewById(R.id.TxtKeyboardPopup);
        Constants.TxtLongPressView.setTypeface(fromAsset);
        if (Constants.popupWindow == null) {
            Constants.popupWindow = new PopupWindow(context);
        }
        Constants.popupWindow.setContentView(ViewLanguage);
        Constants.popupWindow.setBackgroundDrawable(null);
        Constants.popupWindow.setTouchable(true);
        Constants.popupWindow.setAnimationStyle(R.style.PreviewPopupAnimation);
        if (Constants.TxtLongPressView != null) {
            showKeypopup(key, Constants.pxFromDp(context, 130.0f), Constants.pxFromDp(context, 100.0f), Constants.TxtLongPressView);
        }
    }

    private void showKeypopup(Keyboard.Key key, int heightS, int maxS, TextView TxtViews) {
        int pxFromDp;
        int pxFromDp2;
        DismissLanguagePopup();
        if (key == null) {
            return;
        }
        TxtViews.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int max = Math.max(TxtViews.getMeasuredWidth(), key.width + TxtViews.getPaddingLeft() + TxtViews.getPaddingRight()) + maxS;
        int heights = key.height + heightS;
        int widths = key.x - ((max - key.width) / 2);
        int offset = (key.y - heights) + PreviewOffsetInt;
        if (OffsetInWindowInts == null) {
            int[] ints = new int[2];
            OffsetInWindowInts = ints;
            getLocationInWindow(ints);
            int[] inWindowInts = OffsetInWindowInts;
            inWindowInts[0] = inWindowInts[0] + PopupPreviewOffsetXInt;
            inWindowInts[1] = inWindowInts[1] + PopupPreviewOffsetYInt;
            int[] iarr3 = new int[2];
            getLocationOnScreen(iarr3);
            WindowYInt = iarr3[1];
        }
        int[] offsetInWindowInts = OffsetInWindowInts;
        int w = widths + offsetInWindowInts[0];
        int o = offset + offsetInWindowInts[1];
        if (WindowYInt + o < 0) {
            if (key.x + key.width <= getWidth() / 2) {
                double d = key.width;
                Double.isNaN(d);
                w += (int) (d * 2.5d);
            } else {
                double d2 = key.width;
                Double.isNaN(d2);
                w -= (int) (d2 * 2.5d);
            }
            o += heights;
        }
        if (getResources().getConfiguration().orientation == 1) {
            pxFromDp = w + Constants.pxFromDp(context, 10.0f);
            pxFromDp2 = Constants.pxFromDp(context, 35.0f);
        } else {
            pxFromDp = w + Constants.pxFromDp(context, 14.0f);
            pxFromDp2 = Constants.pxFromDp(context, 26.0f);
        }
        int i = o + pxFromDp2;
        if (Constants.popupWindow.isShowing()) {
            Constants.popupWindow.update(pxFromDp, i, max, heights);
            return;
        }
        Constants.popupWindow.setWidth(max);
        Constants.popupWindow.setHeight(heights);
        Constants.popupWindow.showAtLocation(this, 0, pxFromDp, i);
    }

    public void DismissPreviewPopup() {
        try {
            if (Constants.popupScreen.isShowing()) {
                Constants.popupScreen.dismiss();
            }
        } catch (Exception unused) {
        }
        invalidateAllKeys();
    }

    public void DismissLanguagePopup() {
        try {
            if (Constants.popupWindow.isShowing()) {
                Constants.popupWindow.dismiss();
            }
        } catch (Exception unused) {
        }
        invalidateAllKeys();
    }

    public void setOnlineKeyboard(Drawable PUpdrawable) {
        TxtColorCodeInt = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        PopupDrawable = PUpdrawable;
        NewPaint.setColor(new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)));
        SimplePaint.setColor(new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)));
        SetPopupVIew();
        invalidate();
    }

    public void SetPopupVIew() {
        LayoutInflater service = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (Constants.popupScreen == null) {
            Constants.popupScreen = new PopupWindow(context);
        }
        if (Constants.TxtView == null) {
            if (new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0) > 9) {
                Constants.TxtView = (TextView) service.inflate(PreviewLayoutInts[0], (ViewGroup) null, false);
            } else {
                Constants.TxtView = (TextView) service.inflate(PreviewLayoutInts[new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0)], (ViewGroup) null, false);
            }
        }
        if (new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0) > 9) {
            if (new MySharePref(context).getPrefInt(MySharePref.PREVIOUS_THEME, -1) != -1 && new MySharePref(context).getPrefInt(MySharePref.PREVIOUS_THEME, -1) != 0) {
                Constants.TxtView = null;
                Constants.TxtView = (TextView) service.inflate(PreviewLayoutInts[0], (ViewGroup) null, false);

                new MySharePref(context).putPrefInt(MySharePref.PREVIOUS_THEME, new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0));
            }
        } else if (new MySharePref(context).getPrefInt(MySharePref.PREVIOUS_THEME, -1) != -1 && new MySharePref(context).getPrefInt(MySharePref.PREVIOUS_THEME, -1) != new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0)) {
            Constants.TxtView = null;
            Constants.TxtView = (TextView) service.inflate(PreviewLayoutInts[new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0)], (ViewGroup) null, false);
            new MySharePref(context).putPrefInt(MySharePref.PREVIOUS_THEME, new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0));
        }
        if (Constants.ChangeLanguage == 0) {
            Constants.TxtView.setTypeface(fontTypeface);
        } else {
            Constants.TxtView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (Constants.ChangeLanguage == 0) {
            Constants.TxtView.setPadding(0, 15, 0, 0);
        } else {
            Constants.TxtView.setPadding(0, 10, 0, 0);
        }

        StringBuilder builder = new StringBuilder();
        int PreviewInt = 0;
        while (true) {
            String[] PreviewStrArr = Constants.ThemePreviewTextColor;
            if (PreviewInt < PreviewStrArr.length) {
                builder.append(PreviewStrArr[PreviewInt]);
                builder.append(",");
                PreviewInt++;
            } else {
                String prefString = new MySharePref(getContext()).getPrefString(MySharePref.ISPREVIEW_COLOR_CODE, builder.toString());
                Constants.ThemePreviewTextColor = new String[10];
                Constants.ThemePreviewTextColor = prefString.split(",");
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
