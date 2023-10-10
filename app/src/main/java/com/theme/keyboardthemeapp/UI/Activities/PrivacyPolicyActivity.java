package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;

public class PrivacyPolicyActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack;
    private WebView WebPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        WebPrivacy = (WebView) findViewById(R.id.WebPrivacy);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {if (AdsClass.isInternetOn(context)) {
        AdsClass.showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd), Constants.BannerAd,Constants.Show);
    }
        ImgBack.setVisibility(View.VISIBLE);
        WebPrivacy.setInitialScale(100);
        WebSettings webPrivacySettings = WebPrivacy.getSettings();
        webPrivacySettings.setJavaScriptEnabled(true);
        webPrivacySettings.setTextZoom(webPrivacySettings.getTextZoom() + 70);
        WebPrivacy.loadUrl("file:///android_asset/PrivayPolicy.html");
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