package com.theme.keyboardthemeapp.UI.Fragment;

import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

public class SoundSettingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private View SoundView;
    private SeekBar SeekSound;
    private CheckBox CheckSound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SoundView = inflater.inflate(R.layout.fragment_sound_setting, container, false);
        if (SoundView != null) {
            initViews();
            initListeners();
            initActions();
        }
        return SoundView;
    }

    private void initViews() {
        SeekSound = (SeekBar) SoundView.findViewById(R.id.SeekSound);
        CheckSound = (CheckBox) SoundView.findViewById(R.id.CheckSound);
    }

    private void initListeners() {
        CheckSound.setOnCheckedChangeListener(this);
        SeekSound.setOnSeekBarChangeListener(this);
    }

    private void initActions() {
        Constants.IsSound = new MySharePref(getContext()).getPrefBoolean(MySharePref.SOUND_ENABLE, true);
        if (Constants.IsSound) {
            CheckSound.setChecked(true);
        } else {
            CheckSound.setChecked(false);
        }
        SeekSound.setMax(100);
        Constants.ProgressSound = new MySharePref(getContext()).getPrefInt(MySharePref.SOUND_PROGRESS, 10);
        SeekSound.setProgress(Constants.ProgressSound);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        new MySharePref(getContext()).putPrefBoolean(MySharePref.SOUND_ENABLE, b);
        Constants.IsSound = b;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Constants.ProgressSound = SeekSound.getProgress();
        float val = ((float) Constants.ProgressSound) / 100.0f;
        Constants.SoundVolume = val;
        new MySharePref(getContext()).putPrefInt(MySharePref.SOUND_PROGRESS, Constants.ProgressSound);
    }
}