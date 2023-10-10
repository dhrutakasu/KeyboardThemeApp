package com.theme.keyboardthemeapp.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;

public class SplashScreen extends AppCompatActivity {

    private Context context;
    private Handler handler;
    private long TIME_OUT = 0L;
    private AppOpenAd.AppOpenAdLoadCallback openAdLoadCallback;
    private AppOpenAd openAd;
    private long secondsRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initActions();
    }

    private void initActions() {
        context = this;
        context = this;
        createHandler(TIME_OUT);
    }

    public void showOpenAd() {
        openAdLoadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                openAd = null;
                startMainActivity();
            }

            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAds) {
                openAd = appOpenAds;
                if (openAd != null) {
                    openAd.show(SplashScreen.this);
                    openAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            startMainActivity();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            openAd = null;
                            startMainActivity();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }
                    });
                }
            }
        };
        AdRequest adRequest = new AdRequest.Builder().build();
        AppOpenAd.load(this, Constants.AppOpenAd, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, openAdLoadCallback);
    }

    private void createHandler(long seconds) {
        if (AdsClass.isInternetOn(context)) {
            if (Constants.Show.equalsIgnoreCase("yes")) {
                showOpenAd();
            } else {
                CountDownTimer downTimer = new CountDownTimer(seconds * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondsRemaining = ((millisUntilFinished / 1000) + 1);
                    }

                    @Override
                    public void onFinish() {
                        secondsRemaining = 0;
                        startMainActivity();
                    }
                };
                downTimer.start();
            }
        } else {
            Toast.makeText(context, "Please turn on your internet connection...", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void startMainActivity() {
        AdsClass.loadInterOne(SplashScreen.this,Constants.InterstitialAd);
        AdsClass.showInter(SplashScreen.this, () -> {
            startActivity(new Intent(context, MainActivity.class));
            finish();
        },Constants.Show);
    }
}