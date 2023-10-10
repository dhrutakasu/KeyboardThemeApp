package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.android.material.tabs.TabLayout;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.QuotePagerAdapter;

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
        //todo urls
      /*  curl --location 'https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/HindiJokes?select=*' \
        --header 'apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhwcXJ6ZmpnY2lsdHlnbXB5anNzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NTc0MTkxNCwiZXhwIjoyMDExMzE3OTE0fQ.DCJOX18WZTsqTz2dq23iBFqpTWTgg5-9f7VsAtLROmY' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhwcXJ6ZmpnY2lsdHlnbXB5anNzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NTc0MTkxNCwiZXhwIjoyMDExMzE3OTE0fQ.DCJOX18WZTsqTz2dq23iBFqpTWTgg5-9f7VsAtLROmY' \
        --data ''
            */
//        https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/keybord_fileparth?select=*,categories(id,name)
//        https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/HindiJokes?select=*
//        https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/HindiJokes?parent_id=eq.1(chnages 1,2,3)

//        http://technoappsolution.com/app/assets/android/hindikeyboard/1.json
//        http://technoappsolution.com/app/assets/android/hindikeyboard/get_categories.json
    }


    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {if (AdsClass.isInternetOn(context)) {
        AdsClass.showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd),Constants.BannerAd,Constants.Show);
    }
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.str_Quote_text));
        if (Constants.categoriesItems.size() > 0) {
            for (int i = 0; i < Constants.categoriesItems.size(); i++) {
                TabQuote.addTab(TabQuote.newTab().setText(Constants.categoriesItems.get(i).getCatName()));
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