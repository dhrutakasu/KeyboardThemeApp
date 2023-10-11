package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Helper.DatabaseHelper;
import com.theme.keyboardthemeapp.ModelClass.TranslatorModel;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.TranslatorHistoryAdapter;

import java.util.ArrayList;

public class TranslatorHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private RecyclerView RvTranslatorHistory;
    private DatabaseHelper helper;
    private ArrayList<TranslatorModel> translatorModels = new ArrayList<>();
    private TranslatorHistoryAdapter translatorHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator_history);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        RvTranslatorHistory = (RecyclerView) findViewById(R.id.RvTranslatorHistory);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        if (AdsClass.isInternetOn(context)) {
            AdsClass.showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd), Constants.BannerAd,Constants.Show);
        }
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.str_my_translated_words);
        RvTranslatorHistory.setLayoutManager(new LinearLayoutManager(context));
        helper = new DatabaseHelper(context);
        translatorModels = helper.getTranslatorsHistory();
        translatorHistoryAdapter = new TranslatorHistoryAdapter(context, translatorModels, new TranslatorHistoryAdapter.getDeleteData() {
            @Override
            public void DeleteClick(int Id, int position) {
                helper.getDeleteTranslators(Id);
                translatorModels.remove(position);
                translatorHistoryAdapter.notifyDataSetChanged();
            }
        });
        RvTranslatorHistory.setAdapter(translatorHistoryAdapter);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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