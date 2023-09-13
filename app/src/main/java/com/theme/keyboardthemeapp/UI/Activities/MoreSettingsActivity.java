package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.theme.keyboardthemeapp.R;

public class MoreSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView ImgBack, ImgHome;
    private CardView CardShare, CardMore, CardPrivacy, CardRateUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_settings);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgHome = (ImageView) findViewById(R.id.ImgHome);
        CardShare = (CardView) findViewById(R.id.CardShare);
        CardMore = (CardView) findViewById(R.id.CardMore);
        CardPrivacy = (CardView) findViewById(R.id.CardPrivacy);
        CardRateUs = (CardView) findViewById(R.id.CardRateUs);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
        ImgHome.setOnClickListener(this);
        CardShare.setOnClickListener(this);
        CardMore.setOnClickListener(this);
        CardPrivacy.setOnClickListener(this);
        CardRateUs.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        ImgHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
            case R.id.ImgHome:
                GotoHome();
                break;
            case R.id.CardShare:
                GotoShareApp();
                break;
            case R.id.CardMore:
                GotoMore();
                break;
            case R.id.CardRateUs:
                GotoRateUs();
                break;
            case R.id.CardPrivacy:
                GotoPrivacy();
                break;
        }
    }

    private void GotoHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void GotoShareApp() {
        try {
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentShare.setType("text/plain");
            intentShare.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_message) + context.getPackageName());
            Intent createChooser = Intent.createChooser(intentShare, "Share via");
            createChooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(createChooser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GotoRateUs() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    private void GotoPrivacy() {
        startActivity(new Intent(context, PrivacyPolicyActivity.class));
    }

    private void GotoMore() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=my+keyboard+app")));
    }
}