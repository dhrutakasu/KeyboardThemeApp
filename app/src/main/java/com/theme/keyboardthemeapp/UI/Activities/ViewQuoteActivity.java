package com.theme.keyboardthemeapp.UI.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.QuoteModelItem;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Adapters.QuoteAdapter;
import com.theme.keyboardthemeapp.UI.Adapters.QuotePageAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewQuoteActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private ArrayList<QuoteModelItem> statusItemArrayList;
    private int QuotePos;
    private ViewPager PagerQuote;
    private ImageView ImgWhatsapp, ImgFacebook, ImgShareJoke, ImgCopy;
    private ClipboardManager manager;
    private String QuoteTitle;
    private View LayoutProgress;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_joke_quote);
        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        PagerQuote = (ViewPager) findViewById(R.id.PagerQuote);
        ImgWhatsapp = (ImageView) findViewById(R.id.ImgWhatsapp);
        ImgFacebook = (ImageView) findViewById(R.id.ImgFacebook);
        ImgShareJoke = (ImageView) findViewById(R.id.ImgShareJoke);
        ImgCopy = (ImageView) findViewById(R.id.ImgCopy);
        LayoutProgress = (View) findViewById(R.id.LayoutProgress);
    }

    private void initIntents() {
        Intent intent = getIntent();
        QuotePos = intent.getIntExtra(Constants.QUOTE_POS, 0);
        QuoteTitle = intent.getStringExtra(Constants.TITLES);
        statusItemArrayList = Constants.statusItems;
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
        ImgWhatsapp.setOnClickListener(this);
        ImgFacebook.setOnClickListener(this);
        ImgShareJoke.setOnClickListener(this);
        ImgCopy.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initActions() {
        if (AdsClass.isInternetOn(context)) {
            AdsClass.showNative250(this, Constants.NativaAds, findViewById(R.id.FlNative), Constants.Show);
        }
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(QuoteTitle);
        GetQuotesList();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    private void GetQuotesList() {
        if (Constants.isNetworkAvailableoRnOT(context)) {
            LayoutProgress.setVisibility(View.VISIBLE);
            RetrofitInterface downloadService = RetrofitInstance.createService(RetrofitInterface.class, Constants.BASE_URL);
            Call<List<QuoteModelItem>> call = downloadService.getQuotesData(Constants.QUOTE_BASE_URL + Constants.PosQuoteStatus);
            call.enqueue(new Callback<List<QuoteModelItem>>() {
                @Override
                public void onResponse(Call<List<QuoteModelItem>> call, Response<List<QuoteModelItem>> response) {
                    if (response.isSuccessful()) {
                        LayoutProgress.setVisibility(View.GONE);
                        Constants.statusItems = new ArrayList<>();
                        Constants.statusItems.addAll((ArrayList<QuoteModelItem>) response.body());
                        QuotePageAdapter pageAdapter = new QuotePageAdapter(context, getSupportFragmentManager(), (ArrayList<QuoteModelItem>) response.body());
                        PagerQuote.setAdapter(pageAdapter);
                        PagerQuote.setCurrentItem(QuotePos);
                    }
                }

                @Override
                public void onFailure(Call<List<QuoteModelItem>> call, Throwable t) {
                    LayoutProgress.setVisibility(View.GONE);
                }
            });
        } else {
            Constants.NoInternetConnection(ViewQuoteActivity.this);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
            case R.id.ImgWhatsapp:
                GotoSharePackage("com.whatsapp", "", statusItemArrayList.get(QuotePos).getStatus());
                break;
            case R.id.ImgFacebook:
                GotoSharePackage("com.facebook.orca", "com.facebook.katana", statusItemArrayList.get(QuotePos).getStatus());
                break;
            case R.id.ImgShareJoke:
                GotoShare();
                break;
            case R.id.ImgCopy:
                GotoCopy();
                break;
        }
    }

    private void GotoSharePackage(String packageName, String packageName2, String statusTxt) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage(packageName);
            intent.putExtra(Intent.EXTRA_TEXT, statusTxt);
            startActivity(Intent.createChooser(intent, "Share with"));
        } catch (Exception e) {
            if (packageName2.equalsIgnoreCase("")) {
                Toast.makeText(context, "App not Installed", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    getPackageManager().getPackageInfo(packageName2, 0);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage(packageName);
                    intent.putExtra(Intent.EXTRA_TEXT, statusTxt);
                    startActivity(Intent.createChooser(intent, "Share with"));
                } catch (Exception ex) {
                    Toast.makeText(context, "App not Installed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void GotoShare() {
        Intent intentQuoteShare = new Intent();
        intentQuoteShare.setAction(Intent.ACTION_SEND);
        intentQuoteShare.setType("text/plain");
        intentQuoteShare.putExtra(Intent.EXTRA_SUBJECT, "Share Your Text To");
        intentQuoteShare.putExtra(Intent.EXTRA_TEXT, statusItemArrayList.get(QuotePos).getStatus());
        startActivity(Intent.createChooser(intentQuoteShare, "Share via"));
    }

    private void GotoCopy() {
        manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("simple text", statusItemArrayList.get(QuotePos).getStatus()));
        Toast.makeText(context, "copy successfully", Toast.LENGTH_SHORT).show();
    }
}