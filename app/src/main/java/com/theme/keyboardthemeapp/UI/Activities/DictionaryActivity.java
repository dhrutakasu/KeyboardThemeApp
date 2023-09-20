package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.DictionaryTextPagerAdapter;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private TabLayout TabDictionaryTxt;
    private ViewPager PagerDictionaryTxt;
    private DictionaryTextPagerAdapter dictionaryTextPagerAdapter;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        TabDictionaryTxt = (TabLayout) findViewById(R.id.TabDictionaryTxt);
        PagerDictionaryTxt = (ViewPager) findViewById(R.id.PagerDictionaryTxt);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        strings = new ArrayList<>();
        strings.add("Home");
        strings.add("Favourite");
        strings.add("Recent");
        for (int i = 0; i < strings.size(); i++) {
            TabDictionaryTxt.addTab(TabDictionaryTxt.newTab().setText(strings.get(i).toString()));
        }
        Constants.PagerDictionary = PagerDictionaryTxt;
        dictionaryTextPagerAdapter = new DictionaryTextPagerAdapter(getSupportFragmentManager(), strings);
        PagerDictionaryTxt.setAdapter(dictionaryTextPagerAdapter);
        TabDictionaryTxt.setupWithViewPager(PagerDictionaryTxt);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
        }
    }
}