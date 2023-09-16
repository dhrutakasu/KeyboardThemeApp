package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.FancyTextPagerAdapter;

import java.util.ArrayList;

public class FancyTextActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private TabLayout TabFancyTxt;
    private ViewPager PagerFancyTxt;
    private FancyTextPagerAdapter fancyTextPagerAdapter;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_text);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        TabFancyTxt = (TabLayout) findViewById(R.id.TabFancyTxt);
        PagerFancyTxt = (ViewPager) findViewById(R.id.PagerFancyTxt);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.Fancy_Txt));
        strings = new ArrayList<>();
        strings.add("Fancy");
        strings.add("Decorative");
        strings.add("ASCII art");
        for (int i = 0; i < strings.size(); i++) {
            TabFancyTxt.addTab(TabFancyTxt.newTab().setText(strings.get(i).toString()));
        }
        fancyTextPagerAdapter = new FancyTextPagerAdapter(context, getSupportFragmentManager(), strings);
        PagerFancyTxt.setAdapter(fancyTextPagerAdapter);
        TabFancyTxt.setupWithViewPager(PagerFancyTxt);
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