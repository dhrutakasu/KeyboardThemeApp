package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.ModelClass.QuoteCategoryModel;
import com.theme.keyboardthemeapp.ModelClass.StatusItem;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Adapters.JokesAdapter;
import com.theme.keyboardthemeapp.UI.Adapters.QuotePagerAdapter;

import java.util.ArrayList;

public class QuotesActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private TabLayout TabQuote;
    private ViewPager PagerQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        TabQuote = (TabLayout) findViewById(R.id.TabQuote);
        PagerQuote = (ViewPager) findViewById(R.id.PagerQuote);
//        http://technoappsolution.com/app/assets/android/hindikeyboard/1.json
//        http://technoappsolution.com/app/assets/android/hindikeyboard/get_categories.json
    }


    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.Quote_text));
        if (Constants.categoriesItems.size() > 0) {
            for (int i = 0; i < Constants.categoriesItems.size(); i++) {
                System.out.println("---- - - -- - - - - - -qqqqq : " + Constants.categoriesItems.get(i).getName());
                TabQuote.addTab(TabQuote.newTab().setText(Constants.categoriesItems.get(i).getName()));
            }
            QuotePagerAdapter quotePagerAdapter = new QuotePagerAdapter(context, getSupportFragmentManager(), Constants.categoriesItems);
            PagerQuote.setAdapter(quotePagerAdapter);
            TabQuote.setupWithViewPager(PagerQuote);
        }
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