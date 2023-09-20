package com.theme.keyboardthemeapp.UI.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

public class TranslateSettingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private View TranslateView;
    private CheckBox CheckTranslate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TranslateView = inflater.inflate(R.layout.fragment_translate_setting, container, false);
        if (TranslateView != null) {
            initViews();
            initListeners();
            initActions();
        }
        return TranslateView;
    }

    private void initViews() {
        CheckTranslate = (CheckBox) TranslateView.findViewById(R.id.CheckTranslate);
    }

    private void initListeners() {
        CheckTranslate.setOnCheckedChangeListener(this);
    }

    private void initActions() {
        Constants.IsCopyService = new MySharePref(getContext()).getPrefBoolean(MySharePref.COPY_SERVICE, true);
        if (Constants.IsCopyService) {
            CheckTranslate.setChecked(true);
        } else {
            CheckTranslate.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Constants.IsCopyService = b;
    }
}