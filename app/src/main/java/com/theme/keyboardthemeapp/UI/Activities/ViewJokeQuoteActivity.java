package com.theme.keyboardthemeapp.UI.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.StatusItem;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.PageAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewJokeQuoteActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private ArrayList<StatusItem> statusItemArrayList;
    private int QuotePos;
    private ViewPager PagerQuote;
    private ImageView ImgWhatsapp, ImgFacebook, ImgShare, ImgCopy;
    private ClipboardManager manager;

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
        ImgShare = (ImageView) findViewById(R.id.ImgShare);
        ImgCopy = (ImageView) findViewById(R.id.ImgCopy);
    }

    private void initIntents() {
        Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra(Constants.BUNDLE_LIST);
        QuotePos = intent.getIntExtra(Constants.QUOTE_POS, 0);
//        statusItemArrayList = (ArrayList<StatusItem>) args.getSerializable(Constants.ARRAY_LIST_QUOTE);
        statusItemArrayList = JokesActivity.statusItems;
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
        ImgWhatsapp.setOnClickListener(this);
        ImgFacebook.setOnClickListener(this);
        ImgShare.setOnClickListener(this);
        ImgCopy.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.Jockes));
        PageAdapter pageAdapter = new PageAdapter(context, getSupportFragmentManager(), statusItemArrayList);
        PagerQuote.setAdapter(pageAdapter);
        PagerQuote.setCurrentItem(QuotePos);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
            case R.id.ImgWhatsapp:
                GotoSharePackage("com.whatsapp", statusItemArrayList.get(QuotePos).getStatus());
                break;
            case R.id.ImgFacebook:
                GotoSharePackage("com.facebook.orca", statusItemArrayList.get(QuotePos).getStatus());
                break;
            case R.id.ImgShare:
                GotoShare();
                break;
            case R.id.ImgCopy:
                GotoCopy();
                break;
        }
    }

    private void GotoSharePackage(String packageName, String statusTxt) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage(packageName);
            intent.putExtra(Intent.EXTRA_TEXT, statusTxt);
            startActivity(Intent.createChooser(intent, "Share with"));
        } catch (Exception e) {
            Log.e("Error on sharing", e + " ");
            Toast.makeText(context, "App not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void GotoShare() {
        Intent intentQuoteShare = new Intent(Intent.ACTION_SEND);
        intentQuoteShare.setType("text/plain");
        intentQuoteShare.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        intentQuoteShare.putExtra(Intent.EXTRA_TEXT, statusItemArrayList.get(QuotePos).getStatus());
        startActivity(intentQuoteShare);
    }

    private void GotoCopy() {
        manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("simple text", statusItemArrayList.get(QuotePos).getStatus()));
        Toast.makeText(context, "copy successfully", Toast.LENGTH_SHORT).show();
    }
}