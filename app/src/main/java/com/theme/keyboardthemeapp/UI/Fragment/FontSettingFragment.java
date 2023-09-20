package com.theme.keyboardthemeapp.UI.Fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

public class FontSettingFragment extends Fragment implements View.OnClickListener {

    private View FontView;
    private ImageView IvKeyTextPin, IvPreviewPin;
    private View ViewPreviewColor, ViewTextColor;
    private int FontColor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FontView = inflater.inflate(R.layout.fragment_font_setting, container, false);
        if (FontView != null) {
            initViews();
            initListeners();
            initActions();
        }
        return FontView;
    }

    private void initViews() {
        IvKeyTextPin = (ImageView) FontView.findViewById(R.id.IvKeyTextPin);
        IvPreviewPin = (ImageView) FontView.findViewById(R.id.IvPreviewPin);
        ViewPreviewColor = (View) FontView.findViewById(R.id.ViewPreviewColor);
        ViewTextColor = (View) FontView.findViewById(R.id.ViewTextColor);
    }

    private void initListeners() {
        IvKeyTextPin.setOnClickListener(this);
        IvPreviewPin.setOnClickListener(this);
    }

    private void initActions() {
        ViewTextColor.setBackgroundResource(R.drawable.roundrect);
        GradientDrawable gradientDrawable = (GradientDrawable) ViewTextColor.getBackground();
        if (Constants.SelectTheme > 9) {
            gradientDrawable.setColor(Color.parseColor(Constants.ThemePreviewTextColor[0]));
        } else {
            gradientDrawable.setColor(Color.parseColor(Constants.ThemePreviewTextColor[Constants.SelectTheme]));
        }
        ViewPreviewColor.setBackgroundResource(R.drawable.roundrect);
        GradientDrawable gradientDrawable2 = (GradientDrawable) ViewPreviewColor.getBackground();
        if (Constants.SelectTheme > 9) {
            gradientDrawable2.setColor(Color.parseColor(Constants.ThemePreviewTextColor[0]));
        } else {
            gradientDrawable2.setColor(Color.parseColor(Constants.ThemePreviewTextColor[Constants.SelectTheme]));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvKeyTextPin:
                GotoText();
                break;
            case R.id.IvPreviewPin:
                GotoPreview();
                break;
        }
    }

    private void GotoText() {
        ColorPickerDialogBuilder
                .with(getContext())
                .setTitle("Choose color")
                .initialColor(-1)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(selectedColor -> {
                })
                .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                    FontColor = selectedColor;
                    DisplayFontColor(true);
                    if (allColors != null) {
                        StringBuilder sb = null;
                        for (Integer num : allColors) {
                            if (num != null) {
                                if (sb == null) {
                                    sb = new StringBuilder("Color List:");
                                }
                                sb.append("\r\n#" + Integer.toHexString(num.intValue()).toUpperCase());
                            }
                        }
                    }
                })
                .setNegativeButton("cancel", (dialog, which) -> {
                }).showColorEdit(true).setColorEditTextColor(getResources().getColor(R.color.white)).build().show();
    }

    private void DisplayFontColor(boolean b) {
        if (b) {
            String format = String.format("Current color: %08x", new Object[]{Integer.valueOf(FontColor)});
            if (Constants.SelectTheme > 9) {
                Constants.ThemeTextColor[0] = "#" + format.substring(format.length() - 6, format.length());
            } else {
                Constants.ThemeTextColor[Constants.SelectTheme] = "#" + format.substring(format.length() - 6, format.length());
            }
            StringBuilder sb = new StringBuilder();
            for (String append : Constants.ThemeTextColor) {
                sb.append(append);
                sb.append(",");
            }
            Constants.IsTextColor = true;
            Constants.TextColorCode = FontColor;
            Constants.IsColorCodeChange = true;
            new MySharePref(getContext()).putPrefBoolean(MySharePref.ISTEXT_COLOR, true);
            new MySharePref(getContext()).putPrefBoolean(MySharePref.ISTEXT_COLOR_CODE, true);
            new MySharePref(getContext()).putPrefInt(MySharePref.ISTEXT_COLOR_CODE, Constants.TextColorCode);
            GradientDrawable gradientDrawable = (GradientDrawable) ViewTextColor.getBackground();
            if (Constants.SelectTheme > 9) {
                gradientDrawable.setColor(Color.parseColor(Constants.ThemeTextColor[0]));
            } else {
                gradientDrawable.setColor(Color.parseColor(Constants.ThemeTextColor[Constants.SelectTheme]));
            }
        } else {
            String format2 = String.format("Current color: %08x", new Object[]{Integer.valueOf(FontColor)});
            Constants.ThemePreviewTextColor[Constants.SelectTheme] = "#" + format2.substring(format2.length() - 6, format2.length());
            StringBuilder sb2 = new StringBuilder();
            for (String append2 : Constants.ThemePreviewTextColor) {
                sb2.append(append2);
                sb2.append(",");
            }
            new MySharePref(getContext()).putPrefString(MySharePref.ISPREVIEW_COLOR_CODE, sb2.toString());

            ((GradientDrawable) ViewPreviewColor.getBackground()).setColor(Color.parseColor(Constants.ThemePreviewTextColor[Constants.SelectTheme]));
        }
    }

    private void GotoPreview() {

    }
}