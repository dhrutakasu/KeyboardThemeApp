package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Helper.DatabaseHelper;
import com.theme.keyboardthemeapp.ModelClass.TranslatorModel;
import com.theme.keyboardthemeapp.R;

import java.util.Locale;

public class TranslatorActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack, ImgSave, ImgHistory, ImgShare;
    private TextView TxtTitle;
    private ImageView IvClose, IvCopy, IvSpeak, IvChange, IvTranslator, IvCloseBottom, IvCopyBottom, IvSpeakBottom;
    private EditText EdtInputValue, EdtOutputValue;
    private TextView TxtEnglish, TxtHindi;
    private TextToSpeech textToSpeech;
    private View LayoutProgress;
    private Translator englishHindiTranslator;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgSave = (ImageView) findViewById(R.id.ImgSave);
        ImgHistory = (ImageView) findViewById(R.id.ImgHistory);
        ImgShare = (ImageView) findViewById(R.id.ImgShare);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        EdtInputValue = (EditText) findViewById(R.id.EdtInputValue);
        IvClose = (ImageView) findViewById(R.id.IvClose);
        IvCopy = (ImageView) findViewById(R.id.IvCopy);
        IvSpeak = (ImageView) findViewById(R.id.IvSpeak);
        IvChange = (ImageView) findViewById(R.id.IvChange);
        TxtEnglish = (TextView) findViewById(R.id.TxtEnglish);
        TxtHindi = (TextView) findViewById(R.id.TxtHindi);
        IvTranslator = (ImageView) findViewById(R.id.IvTranslator);
        EdtOutputValue = (EditText) findViewById(R.id.EdtOutputValue);
        IvCloseBottom = (ImageView) findViewById(R.id.IvCloseBottom);
        IvCopyBottom = (ImageView) findViewById(R.id.IvCopyBottom);
        IvSpeakBottom = (ImageView) findViewById(R.id.IvSpeakBottom);
        IvChange = (ImageView) findViewById(R.id.IvChange);
        LayoutProgress = (View) findViewById(R.id.LayoutProgress);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
        IvClose.setOnClickListener(this);
        IvCopy.setOnClickListener(this);
        IvSpeak.setOnClickListener(this);
        IvCloseBottom.setOnClickListener(this);
        IvCopyBottom.setOnClickListener(this);
        IvSpeakBottom.setOnClickListener(this);
        IvChange.setOnClickListener(this);
        IvTranslator.setOnClickListener(this);
        ImgSave.setOnClickListener(this);
        ImgHistory.setOnClickListener(this);
        EdtInputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()) {
                    EdtOutputValue.setText(" ");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initActions() {
        if (AdsClass.isInternetOn(context)) {
            AdsClass.showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd), Constants.BannerAd, Constants.Show);
        }
        ImgBack.setVisibility(View.VISIBLE);
        ImgSave.setVisibility(View.VISIBLE);
        ImgHistory.setVisibility(View.VISIBLE);
        ImgShare.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.str_Translator));
        helper = new DatabaseHelper(context);
        textToSpeech = new TextToSpeech(getApplicationContext(), i -> {
            if (i != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });
        if (!Constants.isNetworkAvailableoRnOT(context)) {
            Constants.NoInternetConnection(TranslatorActivity.this);
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

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
            case R.id.IvClose:
                EdtInputValue.setText("");
                EdtOutputValue.setText("");
                break;
            case R.id.IvCopy:
                if (EdtInputValue.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "No Text Available To Copy", Toast.LENGTH_SHORT).show();
                } else {
                    GotoCopy(EdtInputValue);
                }
                break;
            case R.id.IvSpeak:
                if (EdtInputValue.getText().toString().trim().isEmpty()) {
                    textToSpeech.speak("Please Enter Some Text", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    textToSpeech.speak(EdtInputValue.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case R.id.IvCloseBottom:
                EdtOutputValue.setText("");
                break;
            case R.id.IvCopyBottom:
                if (EdtOutputValue.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "No Text Available To Copy", Toast.LENGTH_SHORT).show();
                } else {
                    GotoCopy(EdtOutputValue);
                }
                break;
            case R.id.IvSpeakBottom:
                if (EdtOutputValue.getText().toString().trim().isEmpty()) {
                    textToSpeech.speak("Please Enter Some Text", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    textToSpeech.speak(EdtOutputValue.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case R.id.IvChange:
                GotoSuffl();
                break;
            case R.id.IvTranslator:
                GotoTranslator();
                break;
            case R.id.ImgSave:
                GotoSave();
                break;
            case R.id.ImgHistory:
                GotoHistory();
                break;
        }
    }

    private void GotoShare() {
        if (EdtOutputValue.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "No Text To Share", Toast.LENGTH_SHORT).show();
        } else {
            Intent intentTranslateShare = new Intent();
            intentTranslateShare.setAction(Intent.ACTION_SEND);
            intentTranslateShare.setType("text/plain");
            intentTranslateShare.putExtra(Intent.EXTRA_SUBJECT, "Share Your Text To");
            intentTranslateShare.putExtra(Intent.EXTRA_TEXT, EdtOutputValue.getText().toString());
            startActivity(Intent.createChooser(intentTranslateShare, "Share via"));
        }
    }

    private void GotoCopy(EditText text) {
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("simple text", text.getText().toString()));
        Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show();
    }

    private void GotoSuffl() {
        String TxtOne = TxtEnglish.getText().toString();
        String TxtTwo = TxtHindi.getText().toString();
        String[] splitTxt = (TxtOne + " " + TxtTwo).split("\\s+");
        String Txtstr = splitTxt[0];
        String trimTxt = splitTxt[1].trim();
        String trimSecTxt = Txtstr.trim();
        TxtEnglish.setText(trimTxt);
        TxtHindi.setText(trimSecTxt);
        TxtTitle.setText(trimTxt + "Translator");
    }

    private void GotoTranslator() {
        Constants.hideKeyboard(TranslatorActivity.this);

        if (EdtInputValue.getText().length() == 0) {
            Toast.makeText(context, "Enter Text To Translate", Toast.LENGTH_SHORT).show();
        } else {
            LayoutProgress.setVisibility(View.VISIBLE);
            try {
                if (TxtEnglish.getText().toString().equals("English")) {
                    if (TxtHindi.getText().toString().equals(getResources().getString(R.string.str_hindi))) {
                        if (Constants.isNetworkAvailableoRnOT(context)) {
                            try {
                                TranslatorOptions options = new TranslatorOptions.Builder()
                                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                                        .setTargetLanguage(TranslateLanguage.HINDI)
                                        .build();

                                englishHindiTranslator = Translation.getClient(options);
                                englishHindiTranslator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        englishHindiTranslator.translate(EdtInputValue.getText().toString()).addOnSuccessListener(
                                                translatedText -> {
                                                    EdtOutputValue.setText(translatedText);
                                                    LayoutProgress.setVisibility(View.GONE);
                                                }).addOnFailureListener(
                                                e -> {
                                                    EdtOutputValue.setText(e.getMessage());
                                                    LayoutProgress.setVisibility(View.GONE);
                                                });
                                    }
                                });
                            } catch (Exception e) {
                                LayoutProgress.setVisibility(View.GONE);
                                Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
                                EdtOutputValue.setText("Something Went Wrong.. Plaese Try again!!");
                            }
                        } else {
                            LayoutProgress.setVisibility(View.GONE);
                            EdtOutputValue.setText("Please Check Your Internet Connection...");
                            Constants.NoInternetConnection(TranslatorActivity.this);
                        }
                    }
                } else if (TxtEnglish.getText().toString().equals(getResources().getString(R.string.str_hindi)) || TxtHindi.getText().toString().equals("English")) {
                    if (Constants.isNetworkAvailableoRnOT(context)) {
                        try {
                            TranslatorOptions options_2 = new TranslatorOptions.Builder()
                                    .setSourceLanguage(TranslateLanguage.HINDI)
                                    .setTargetLanguage(TranslateLanguage.ENGLISH)
                                    .build();

                            englishHindiTranslator = Translation.getClient(options_2);
                            englishHindiTranslator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    englishHindiTranslator.translate(EdtInputValue.getText().toString()).addOnSuccessListener(
                                            translatedText -> {
                                                EdtOutputValue.setText(translatedText);
                                                LayoutProgress.setVisibility(View.GONE);
                                            }).addOnFailureListener(
                                            e -> {
                                                EdtOutputValue.setText(e.getMessage());
                                                LayoutProgress.setVisibility(View.GONE);
                                            });
                                }
                            });
                        } catch (Exception e2) {
                            Toast.makeText(context, "" + e2, Toast.LENGTH_LONG).show();
                            EdtOutputValue.setText("Something Went Wrong.. Plaese Try again!!");
                            LayoutProgress.setVisibility(View.GONE);
                        }
                    } else {
                        EdtOutputValue.setText("Please Check Your Internet Connection...");
                        LayoutProgress.setVisibility(View.GONE);
                        Constants.NoInternetConnection(TranslatorActivity.this);
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private void GotoSave() {
        if (!EdtOutputValue.getText().toString().trim().isEmpty() && !EdtInputValue.getText().toString().trim().isEmpty()) {
            TranslatorModel translatorModel = new TranslatorModel(EdtInputValue.getText().toString(), EdtOutputValue.getText().toString());
            helper.InsertWidget(translatorModel);
            Toast.makeText(context, "Data Save", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No Data To Save", Toast.LENGTH_SHORT).show();
        }
    }

    private void GotoHistory() {
        AdsClass.loadInterOne(TranslatorActivity.this, Constants.InterstitialAd);
        AdsClass.showInter(TranslatorActivity.this, () -> {
            startActivity(new Intent(context, TranslatorHistoryActivity.class));
            finish();
        }, Constants.Show);
    }
}