package com.theme.keyboardthemeapp.UI.Fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

public class FontSettingFragment extends Fragment implements View.OnClickListener {

    private View FontView;
    private ImageView IvKeyTextPin, IvPreviewPin;
    private View ViewPreviewColor, ViewTextColor;
    private int FontColor, PreviewColor;


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

        Constants.TextColorCode = new MySharePref(getContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, -1);
        Constants.PreviewColorCode = new MySharePref(getContext()).getPrefInt(MySharePref.PREVIEW_IS_COLOR_CODE, -1);
        FontColor = Constants.TextColorCode;
        PreviewColor = Constants.PreviewColorCode;

        StringBuilder builderText = new StringBuilder();
        int TextInt = 0;
        while (true) {
            String[] TextStrArr = Constants.ThemeTextColor;
            if (TextInt >= TextStrArr.length) {
                break;
            }
            builderText.append(TextStrArr[TextInt]);
            builderText.append(",");
            TextInt++;
        }
        String string = new MySharePref(getContext()).getPrefString(MySharePref.ISTEXT_COLOR_CODE, builderText.toString());
        Constants.ThemeTextColor = new String[10];
        Constants.ThemeTextColor = string.split(",");

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

        GradientDrawable gradientDrawable = (GradientDrawable) ViewTextColor.getBackground();
        if (Constants.SelectTheme > 9) {
            gradientDrawable.setColor(Color.parseColor(Constants.ThemeTextColor[0]));
        } else {
            gradientDrawable.setColor(Color.parseColor(Constants.ThemeTextColor[Constants.SelectTheme]));
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
                    DisplayFontColor();
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

    private void DisplayFontColor() {
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
        new MySharePref(getContext()).putPrefBoolean(MySharePref.ISCOLOR_CODE_CHANGE, true);
        new MySharePref(getContext()).putPrefInt(MySharePref.TEXT_IS_COLOR_CODE, Constants.TextColorCode);
        new MySharePref(getContext()).putPrefString(MySharePref.ISTEXT_COLOR_CODE, sb.toString());
        GradientDrawable gradientDrawable = (GradientDrawable) ViewTextColor.getBackground();
        if (Constants.SelectTheme > 9) {
            gradientDrawable.setColor(Color.parseColor(Constants.ThemeTextColor[0]));
        } else {
            gradientDrawable.setColor(Color.parseColor(Constants.ThemeTextColor[Constants.SelectTheme]));
        }
    }

    private void GotoPreview() {
        ColorPickerDialogBuilder
                .with(getContext())
                .setTitle("Choose color")
                .initialColor(-1)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(selectedColor -> {
                })
                .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                    PreviewColor = selectedColor;
                    DisplayPreviewColor();
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

    private void DisplayPreviewColor() {
        String format2 = String.format("Current color: %08x", new Object[]{Integer.valueOf(PreviewColor)});
        Constants.ThemePreviewTextColor[Constants.SelectTheme] = "#" + format2.substring(format2.length() - 6, format2.length());
        Constants.PreviewColorCode = PreviewColor;
        StringBuilder sb2 = new StringBuilder();
        for (String append2 : Constants.ThemePreviewTextColor) {
            sb2.append(append2);
            sb2.append(",");
        }
        new MySharePref(getContext()).putPrefString(MySharePref.ISPREVIEW_COLOR_CODE, sb2.toString());
        new MySharePref(getContext()).putPrefInt(MySharePref.PREVIEW_IS_COLOR_CODE, Constants.PreviewColorCode);

        ((GradientDrawable) ViewPreviewColor.getBackground()).setColor(Color.parseColor(Constants.ThemePreviewTextColor[Constants.SelectTheme]));
    }
}