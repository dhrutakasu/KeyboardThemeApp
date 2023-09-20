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

public class GeneralSettingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private View GeneralView;
    private CheckBox CheckAutoCapitalize, CheckPopup, CheckVibrate, CheckSuggestion, CheckTyping;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GeneralView = inflater.inflate(R.layout.fragment_general_setting, container, false);
        if (GeneralView != null) {
            initViews();
            initListeners();
            initActions();
        }
        return GeneralView;
    }

    private void initViews() {
        CheckAutoCapitalize = (CheckBox) GeneralView.findViewById(R.id.CheckAutoCapitalize);
        CheckPopup = (CheckBox) GeneralView.findViewById(R.id.CheckPopup);
        CheckVibrate = (CheckBox) GeneralView.findViewById(R.id.CheckVibrate);
        CheckSuggestion = (CheckBox) GeneralView.findViewById(R.id.CheckSuggestion);
        CheckTyping = (CheckBox) GeneralView.findViewById(R.id.CheckTyping);
    }

    private void initListeners() {
        CheckAutoCapitalize.setOnCheckedChangeListener(this);
        CheckPopup.setOnCheckedChangeListener(this);
        CheckVibrate.setOnCheckedChangeListener(this);
        CheckSuggestion.setOnCheckedChangeListener(this);
        CheckTyping.setOnCheckedChangeListener(this);
    }

    private void initActions() {
        boolean IsCapitalize = new MySharePref(getContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true);
        boolean IsPopup = new MySharePref(getContext()).getPrefBoolean(MySharePref.POPUP, true);
        boolean IsVibration = new MySharePref(getContext()).getPrefBoolean(MySharePref.VIBRATION, false);
        boolean IsSuggest = new MySharePref(getContext()).getPrefBoolean(MySharePref.SUGGESTION, true);
        boolean IsTyping = new MySharePref(getContext()).getPrefBoolean(MySharePref.TYPING, true);

        CheckAutoCapitalize.setChecked(IsCapitalize);
        CheckPopup.setChecked(IsPopup);
        CheckVibrate.setChecked(IsVibration);
        CheckSuggestion.setChecked(IsSuggest);
        CheckTyping.setChecked(IsTyping);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.CheckAutoCapitalize:
                GotoAutoCapitalize(b);
                break;
            case R.id.CheckPopup:
                GotoPopup(b);
                break;
            case R.id.CheckVibrate:
                GotoVibration(b);
                break;
            case R.id.CheckSuggestion:
                GotoSuggestion(b);
                break;
            case R.id.CheckTyping:
                GotoTyping(b);
                break;
        }
    }

    private void GotoAutoCapitalize(boolean b) {
        new MySharePref(getContext()).putPrefBoolean(MySharePref.AUTO_CAPITALIZE, b);
        Constants.IsCaps = b;
    }

    private void GotoPopup(boolean b) {
        new MySharePref(getContext()).putPrefBoolean(MySharePref.POPUP, b);
        Constants.IsPreview = b;
    }

    private void GotoVibration(boolean b) {
        new MySharePref(getContext()).putPrefBoolean(MySharePref.VIBRATION, b);
        Constants.IsVibrate = b;
    }

    private void GotoSuggestion(boolean b) {
        new MySharePref(getContext()).putPrefBoolean(MySharePref.SUGGESTION, b);
        Constants.IsSuggest = b;
    }

    private void GotoTyping(boolean b) {
        new MySharePref(getContext()).putPrefBoolean(MySharePref.TYPING, b);
        Constants.CheckLan = b;
    }
}