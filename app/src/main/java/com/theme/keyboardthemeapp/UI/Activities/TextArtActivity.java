package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.TextArtPagerAdapter;

import java.util.ArrayList;

public class TextArtActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private TabLayout TabArtTxt;
    private ViewPager PagerArtTxt;
    private TextArtPagerAdapter textArtPagerAdapter;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_art);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        TabArtTxt = (TabLayout) findViewById(R.id.TabArtTxt);
        PagerArtTxt = (ViewPager) findViewById(R.id.PagerArtTxt);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.Text_art));
        strings = new ArrayList<>();
        strings.add("Nature");
        strings.add("Mood");
        strings.add("Love");
        strings.add("Fun");
        strings.add("Food");
        strings.add("Celebration");
        strings.add("Eraster");
        strings.add("Christmas");
        strings.add("emojiart1");
        strings.add("emojiart2");
        strings.add("Flowers");
        strings.add("Gesture");
        strings.add("Greetings");
        strings.add("Kiss");
        strings.add("Life");
        strings.add("Newyear");
        strings.add("Pet");
        strings.add("Valentine");
        strings.add("Weather");
        for (int i = 0; i < strings.size(); i++) {
            TabArtTxt.addTab(TabArtTxt.newTab().setText(strings.get(i).toString()));
        }
        textArtPagerAdapter = new TextArtPagerAdapter(getSupportFragmentManager(), strings);
        PagerArtTxt.setAdapter(textArtPagerAdapter);
        TabArtTxt.setupWithViewPager(PagerArtTxt);
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