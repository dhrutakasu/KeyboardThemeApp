package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Helper.DatabaseHelper;
import com.theme.keyboardthemeapp.ModelClass.TranslatorModel;
import com.theme.keyboardthemeapp.R;

public class ViewTranslatedWordsActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack, ImgShare;
    private TextView TxtTitle;
    private TextView TxtInputWords, TxtOutputWords;
    private int DatabseId;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_translated_words);
        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgShare = (ImageView) findViewById(R.id.ImgShare);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        TxtInputWords = (TextView) findViewById(R.id.TxtInputWords);
        TxtOutputWords = (TextView) findViewById(R.id.TxtOutputWords);
    }

    private void initIntents() {
        DatabseId = getIntent().getIntExtra(Constants.TRANSLATOR_DATA, -1);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        ImgShare.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.str_my_saved_data);
        helper = new DatabaseHelper(context);
        TranslatorModel translatorModel = helper.getTranslatorsHistoryId(DatabseId);
        TxtInputWords.setText(translatorModel.getInputStr());
        TxtOutputWords.setText(translatorModel.getOutputStr());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
            case R.id.ImgShare:
                GotoShare();
                break;
        }
    }

    private void GotoShare() {
        Intent intentQuoteShare = new Intent(Intent.ACTION_SEND);
        intentQuoteShare.setType("text/plain");
        intentQuoteShare.putExtra(Intent.EXTRA_SUBJECT, "Share with");
        intentQuoteShare.putExtra(Intent.EXTRA_TEXT, TxtOutputWords.getText().toString());
        startActivity(intentQuoteShare);
    }
}