package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Dialogs.KeyboardPermissionDialog;
import com.theme.keyboardthemeapp.Dialogs.OverlayPermissionDialog;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.ModelClass.QuoteCategoryModel;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Adapters.QuotePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private boolean isEnableKeyboard;
    private boolean isActivateKeyboard;
    private ImageView ImgMore;
    private View LayoutProgress;
    private CardView CardBackground, CardTheme, CardGif, CardFont, CardSetting, CardDictionary, CardTranslator, CardArt, CardQuotes, CardFancyText, CardJokes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgMore = (ImageView) findViewById(R.id.ImgMore);
        LayoutProgress = (View) findViewById(R.id.LayoutProgress);
        CardBackground = (CardView) findViewById(R.id.CardBackground);
        CardTheme = (CardView) findViewById(R.id.CardTheme);
        CardGif = (CardView) findViewById(R.id.CardGif);
        CardFont = (CardView) findViewById(R.id.CardFont);
        CardSetting = (CardView) findViewById(R.id.CardSetting);
        CardDictionary = (CardView) findViewById(R.id.CardDictionary);
        CardTranslator = (CardView) findViewById(R.id.CardTranslator);
        CardArt = (CardView) findViewById(R.id.CardArt);
        CardQuotes = (CardView) findViewById(R.id.CardQuotes);
        CardFancyText = (CardView) findViewById(R.id.CardFancyText);
        CardJokes = (CardView) findViewById(R.id.CardJokes);
    }

    private void initListeners() {
        ImgMore.setOnClickListener(this);
        CardBackground.setOnClickListener(this);
        CardTheme.setOnClickListener(this);
        CardGif.setOnClickListener(this);
        CardFont.setOnClickListener(this);
        CardSetting.setOnClickListener(this);
        CardDictionary.setOnClickListener(this);
        CardTranslator.setOnClickListener(this);
        CardArt.setOnClickListener(this);
        CardQuotes.setOnClickListener(this);
        CardFancyText.setOnClickListener(this);
        CardJokes.setOnClickListener(this);
    }

    private void initActions() {
        isEnableKeyboard = Constants.IsEnableKeyboard(context);
        isActivateKeyboard = Constants.IsActivateKeyboard(context);
        GetQuoteResponse();
    }

    private void GotoPermission() {
        if (!isEnableKeyboard || !isActivateKeyboard) {
            startActivity(new Intent(context, SetDefaultKeyboardActivity.class));
        }
        ImgMore.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String s = Manifest.permission.READ_MEDIA_IMAGES;
            String s1 = Manifest.permission.ACCESS_COARSE_LOCATION;
            String s2 = Manifest.permission.ACCESS_FINE_LOCATION;
            String s3 = Manifest.permission.CAMERA;
            Dexter.withContext(context)
                    .withPermissions(s, s1, s2, s3)
                    .withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                checkDrawOverlayPermission();
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                Constants.showSettingsDialog(MainActivity.this);
                            }
                        }

                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
                            Constants.showPermissionDialog(MainActivity.this, permissionToken);
                        }
                    })
                    .onSameThread()
                    .check();
        } else {
            String s = Manifest.permission.READ_EXTERNAL_STORAGE;
            String s1 = Manifest.permission.ACCESS_FINE_LOCATION;
            String s2 = Manifest.permission.ACCESS_COARSE_LOCATION;
            String s3 = Manifest.permission.CAMERA;
            Dexter.withContext(context)
                    .withPermissions(s, s1, s2, s3)
                    .withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                checkDrawOverlayPermission();
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                Constants.showSettingsDialog(MainActivity.this);
                            }
                        }

                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
                            Constants.showPermissionDialog(MainActivity.this, permissionToken);
                        }
                    })
                    .onSameThread()
                    .check();
        }
    }

    public void checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                OverlayPermissionDialog overlayPermissionDialog = new OverlayPermissionDialog(MainActivity.this, context, (OverlayPermissionDialog permissionDialog) -> {
                    permissionDialog.dismiss();
                    startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName())), Constants.OVERLAY_REQUEST_CODE);
                });
                overlayPermissionDialog.show();
                WindowManager.LayoutParams attributes = overlayPermissionDialog.getWindow().getAttributes();
                Window exitDialogWindow = overlayPermissionDialog.getWindow();
                attributes.copyFrom(exitDialogWindow.getAttributes());
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
                attributes.gravity = Gravity.CENTER;
                exitDialogWindow.setAttributes(attributes);
                return;
            }
        }
    }

    private void GetQuoteResponse() {
        if (Constants.isNetworkAvailable(context)) {
            LayoutProgress.setVisibility(View.VISIBLE);
            RetrofitInterface downloadService = RetrofitInstance.createService(RetrofitInterface.class, Constants.BASE_URL);
            Call<QuoteCategoryModel> call = downloadService.getQuoteCategoryData(Constants.QUOTES_CATEGORY_URL);
            call.enqueue(new Callback<QuoteCategoryModel>() {
                @Override
                public void onResponse(Call<QuoteCategoryModel> call, Response<QuoteCategoryModel> response) {
                    if (response.isSuccessful()) {
                        Constants.categoriesItems = new ArrayList<>();
                        Constants.categoriesItems = (ArrayList<CategoriesItem>) response.body().getCategories();
                        LayoutProgress.setVisibility(View.GONE);
                        GotoPermission();
                    }
                }

                @Override
                public void onFailure(Call<QuoteCategoryModel> call, Throwable t) {
                    LayoutProgress.setVisibility(View.GONE);
                }
            });
        } else {
            Constants.NoInternetConnection(MainActivity.this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgMore:
                startActivity(new Intent(context, MoreSettingsActivity.class));
                break;
            case R.id.CardBackground:
                startActivity(new Intent(context, BackgroundActivity.class));
                break;
            case R.id.CardTheme:
                startActivity(new Intent(context, ThemeActivity.class));
                break;
            case R.id.CardGif:
                startActivity(new Intent(context, GIFActivity.class));
                break;
            case R.id.CardFont:
                startActivity(new Intent(context, FontActivity.class));
                break;
            case R.id.CardSetting:
                startActivity(new Intent(context, SettingActivity.class));
                break;
            case R.id.CardDictionary:
                startActivity(new Intent(context, DictionaryActivity.class));
                break;
            case R.id.CardTranslator:
                startActivity(new Intent(context, TranslatorActivity.class));
                break;
            case R.id.CardArt:
                startActivity(new Intent(context, TextArtActivity.class));
                break;
            case R.id.CardQuotes:
                startActivity(new Intent(context, QuotesActivity.class));
                break;
            case R.id.CardFancyText:
                startActivity(new Intent(context, FancyTextActivity.class));
                break;
            case R.id.CardJokes:
                startActivity(new Intent(context, JokesActivity.class));
                break;
        }
    }
}