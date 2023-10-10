package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.ModelClass.QuoteCategoryModelItem;
import com.theme.keyboardthemeapp.Task.DictionaryTask;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Dialogs.OverlayPermissionDialog;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.ModelClass.QuoteCategoryModel;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;

import java.io.File;
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
        if (AdsClass.isInternetOn(context)) {
            AdsClass.showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd), Constants.BannerAd, Constants.Show);
        }
        isEnableKeyboard = Constants.IsEnableKeyboard(context);
        isActivateKeyboard = Constants.IsActivateKeyboard(context);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.height = displayMetrics.heightPixels;
        Constants.width = displayMetrics.widthPixels;
        GetQuoteResponse();
//        if (new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").exists()) {
//            new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").delete();
//        }
        System.out.println("--- -- - -Constants.DictionaryWordLoad : " + Constants.DictionaryWordLoad);
        if (!Constants.DictionaryWordLoad) {
            DictionaryTask dictionaryTask = new DictionaryTask(this, 0);
            if (Constants.isUpHoneycombVersion) {
                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                dictionaryTask.execute(new String[0]);
            }
            Constants.DictionaryWordLoad = true;
        }

//        if (new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0) == 0) {
        Constants.getBackground(context, new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0)).getAbsolutePath();
//        }
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
            String s4 = Manifest.permission.RECORD_AUDIO;
            Dexter.withContext(context)
                    .withPermissions(s, s1, s2, s3, s4)
                    .withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                if (checkLocation()) {
                                    checkDrawOverlayPermission();
                                }
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                Constants.showSettingsDialog(MainActivity.this);
                            }
                        }

                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
                            System.out.println("---- -- - - permi :: " + Arrays.toString(permissions.toArray()));
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
            String s4 = Manifest.permission.RECORD_AUDIO;
            Dexter.withContext(context)
                    .withPermissions(s, s1, s2, s3, s4)
                    .withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                if (checkLocation()) {
                                    checkDrawOverlayPermission();
                                }
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


    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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
            Call<List<QuoteCategoryModelItem>> call = downloadService.getQuoteCategoryData(Constants.QUOTES_CATEGORY_URL);
            call.enqueue(new Callback<List<QuoteCategoryModelItem>>() {
                @Override
                public void onResponse(Call<List<QuoteCategoryModelItem>> call, Response<List<QuoteCategoryModelItem>> response) {
                    LayoutProgress.setVisibility(View.GONE);
                    System.out.println("---- - - -qqqq sss: " + response.body().size());
                    for (int i = 0; i < response.body().size(); i++) {
                        System.out.println("---- - - -qqqq : " + response.body().get(i).getCatName());
                    }
                    if (response.isSuccessful()) {
                        Constants.categoriesItems = new ArrayList<>();
                        Constants.categoriesItems.addAll((ArrayList<QuoteCategoryModelItem>) response.body());
                        GotoPermission();
                    }
                }

                @Override
                public void onFailure(Call<List<QuoteCategoryModelItem>> call, Throwable t) {
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
                LoadAd(MoreSettingsActivity.class);
                break;
            case R.id.CardBackground:
                LoadAd(BackgroundActivity.class);
                break;
            case R.id.CardTheme:
                LoadAd(ThemeActivity.class);
                break;
            case R.id.CardGif:
                LoadAd(GIFActivity.class);
                break;
            case R.id.CardFont:
                LoadAd(FontActivity.class);
                break;
            case R.id.CardSetting:
                Constants.wordExist = true;
                LoadAd(SettingActivity.class);
                break;
            case R.id.CardDictionary:
                LoadAd(DictionaryActivity.class);
                break;
            case R.id.CardTranslator:
                LoadAd(TranslatorActivity.class);
                break;
            case R.id.CardArt:
                LoadAd(TextArtActivity.class);
                break;
            case R.id.CardQuotes:
                LoadAd(QuotesActivity.class);
                break;
            case R.id.CardFancyText:
                LoadAd(FancyTextActivity.class);
                break;
            case R.id.CardJokes:
                LoadAd(JokesActivity.class);
                break;
        }
    }

    private void LoadAd(Class aClass) {
        AdsClass.loadInterOne(MainActivity.this, Constants.InterstitialAd);
        AdsClass.showInter(MainActivity.this, () -> {
            startActivity(new Intent(context, aClass));
            finish();
        }, Constants.Show);
    }
}