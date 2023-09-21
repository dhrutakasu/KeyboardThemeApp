package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.TextSettingPagerAdapter;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack, ImgMore;
    private TextView TxtTitle;
    private TabLayout TabSettingTxt;
    private ViewPager PagerSettingTxt;
    private TextSettingPagerAdapter textSettingPagerAdapter;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgMore = (ImageView) findViewById(R.id.ImgMore);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        TabSettingTxt = (TabLayout) findViewById(R.id.TabSettingTxt);
        PagerSettingTxt = (ViewPager) findViewById(R.id.PagerSettingTxt);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        ImgMore.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.Setting));
        strings = new ArrayList<>();
        strings.add("General");
        strings.add("Display");
        strings.add("Font");
        strings.add("Sound");
        strings.add("Translate");
        for (int i = 0; i < strings.size(); i++) {
            TabSettingTxt.addTab(TabSettingTxt.newTab().setText(strings.get(i).toString()));
        }
        textSettingPagerAdapter = new TextSettingPagerAdapter(getSupportFragmentManager(), strings);
        PagerSettingTxt.setAdapter(textSettingPagerAdapter);
        TabSettingTxt.setupWithViewPager(PagerSettingTxt);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
            case R.id.ImgMore:
                startActivity(new Intent(context, MoreSettingsActivity.class));
                break;
        }
    }
}