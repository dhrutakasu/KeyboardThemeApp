package com.theme.keyboardthemeapp.UI.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

public class DisplaySettingFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private View DisplayView;
    private SeekBar SeekPHeight, SeekLHeight, SeekSuggestionText;
    private int KeyboardPotraitHeight = 0;
    private int KeyboardLandHeight = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DisplayView = inflater.inflate(R.layout.fragment_display_setting, container, false);
        if (DisplayView != null) {
            initViews();
            initListeners();
            initActions();
        }
        return DisplayView;
    }

    private void initViews() {
        SeekPHeight = (SeekBar) DisplayView.findViewById(R.id.SeekPHeight);
        SeekLHeight = (SeekBar) DisplayView.findViewById(R.id.SeekLHeight);
        SeekSuggestionText = (SeekBar) DisplayView.findViewById(R.id.SeekSuggestionText);
    }

    private void initListeners() {
        SeekPHeight.setOnSeekBarChangeListener(this);
        SeekLHeight.setOnSeekBarChangeListener(this);
        SeekSuggestionText.setOnSeekBarChangeListener(this);
    }

    private void initActions() {
        SeekPHeight.setMax(100);
        SeekLHeight.setMax(100);
        SeekSuggestionText.setMax(9);
        Constants.ProgressPotraitDefault = new MySharePref(getContext()).getPrefInt(MySharePref.KEYBOARD_POTRAIT_HEIGHT, 2);
        Constants.ProgressLandscapDefault = new MySharePref(getContext()).getPrefInt(MySharePref.KEYBOARD_LANDSCAP_HEIGHT, 2);
        Constants.SuggestionText = new MySharePref(getContext()).getPrefInt(MySharePref.SUGGESTION_TEXT_SIZE, 16);
        if (Constants.KeyboardPHeight == -1) {
            Constants.KeyboardPHeight = KeyboardPotraitHeight;
            SeekPHeight.setProgress(25);
        } else {
            SeekPHeight.setProgress(Constants.ProgressPotraitDefault);
        }
        if (Constants.KeyboardLHeight == -1) {
            Constants.DpToPx(getActivity().getApplicationContext(), 20);
            Constants.KeyboardLHeight = KeyboardLandHeight;
            SeekLHeight.setProgress(50);
        } else {
            SeekLHeight.setProgress(Constants.ProgressLandscapDefault);
        }

        SeekSuggestionText.setProgress(Constants.SuggestionText % 10);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.SeekPHeight:
                int progress = SeekPHeight.getProgress();
                int i = getProgress(progress);
                if (i == 0) {
                    KeyboardPotraitHeight = KeyboardPotraitHeight - Constants.pxFromDp(getContext(), 20.0f);
                } else if (i == 1) {
                    KeyboardPotraitHeight = KeyboardPotraitHeight;
                } else if (i == 2) {
                    KeyboardPotraitHeight = KeyboardPotraitHeight + Constants.pxFromDp(getContext(), 60.0f);
                } else if (i == 3) {
                    KeyboardPotraitHeight = KeyboardPotraitHeight + Constants.pxFromDp(getContext(), 110.0f);
                } else if (i != 4) {
                    KeyboardPotraitHeight = KeyboardPotraitHeight;
                } else {
                    KeyboardPotraitHeight = KeyboardPotraitHeight + Constants.pxFromDp(getContext(), 160.0f);
                }
                Constants.ProgressPotraitDefault = progress;
                new MySharePref(getContext()).putPrefInt(MySharePref.KEYBOARD_POTRAIT_HEIGHT, Constants.KeyboardPHeight);
                new MySharePref(getContext()).putPrefInt(MySharePref.PROGRESS_DEFAULT, progress);
                break;
            case R.id.SeekLHeight:
                int progressLand = SeekLHeight.getProgress();
                int iLand = getProgress(progressLand);
                if (iLand == 0) {
                    Constants.DpToPx(getContext(), 20);
                    Constants.KeyboardLHeight = KeyboardLandHeight - Constants.pxFromDp(getContext(), 20.0f);
                } else if (iLand == 1) {
                    Constants.KeyboardLHeight = KeyboardLandHeight - Constants.pxFromDp(getContext(), 10.0f);
                } else if (iLand == 2) {
                    Constants.KeyboardLHeight = KeyboardLandHeight;
                } else if (iLand == 3) {
                    Constants.KeyboardLHeight = KeyboardLandHeight + Constants.pxFromDp(getContext(), 20.0f);
                } else if (iLand != 4) {
                    Constants.KeyboardLHeight = KeyboardLandHeight;
                } else {
                    Constants.KeyboardLHeight = KeyboardLandHeight + Constants.pxFromDp(getContext(), 40.0f);
                }
                Constants.ProgressLandscapDefault = progressLand;

                new MySharePref(getContext()).putPrefInt(MySharePref.KEYBOARD_LANDSCAP_HEIGHT, Constants.KeyboardPHeight);
                new MySharePref(getContext()).putPrefInt(MySharePref.PROGRESS_DEFAULT_LANDSCAP, progressLand);
                break;
            case R.id.SeekSuggestionText:
                Constants.SuggestionText = SeekSuggestionText.getProgress() + 10;
                new MySharePref(getContext()).putPrefInt(MySharePref.SUGGESTION_TEXT_SIZE, Constants.SuggestionText);
                break;
        }
    }

    public int getProgress(int i) {
        if (i <= 20) {
            return 0;
        }
        if (i <= 40) {
            return 1;
        }
        if (i <= 60) {
            return 2;
        }
        if (i <= 80) {
            return 3;
        }
        return i <= 100 ? 4 : 0;
    }
}