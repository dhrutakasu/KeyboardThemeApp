package com.theme.keyboardthemeapp.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdSize;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.ModelClass.GifModelItem;
import com.theme.keyboardthemeapp.ModelClass.HindithemekeyboardItem;
import com.theme.keyboardthemeapp.Task.GifThemeDownloader;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Adapters.GifAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GIFActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack, ImgMore;
    private TextView TxtTitle;
    private RecyclerView RvGifList;
    private ArrayList<HindithemekeyboardItem> GifArrays = new ArrayList<>();
    private GifAdapter adapter;
    private View LayoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_activity);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgMore = (ImageView) findViewById(R.id.ImgMore);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        RvGifList = (RecyclerView) findViewById(R.id.RvGifList);
        LayoutProgress = (View) findViewById(R.id.LayoutProgress);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
        ImgMore.setOnClickListener(this);
    }

    private void initActions() {
        if (AdsClass.isInternetOn(context)) {
            AdsClass.showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd),Constants.BannerAd,Constants.Show);
        }
        ImgBack.setVisibility(View.VISIBLE);
        ImgMore.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.str_Gif_style);
        if (Constants.isNetworkAvailableoRnOT(context)) {
            getGifs();
        } else {
            Constants.NoInternetConnection(GIFActivity.this);
        }
    }

    private void getGifs() {
        LayoutProgress.setVisibility(View.VISIBLE);
        RetrofitInterface downloadService = RetrofitInstance.createService(RetrofitInterface.class, Constants.BASE_URL);
        Call<List<GifModelItem>> call = downloadService.getGifsData(Constants.GIF_URL);
        call.enqueue(new Callback<List<GifModelItem>>() {
            @Override
            public void onResponse(Call<List<GifModelItem>> call, Response<List<GifModelItem>> response) {
                if (response.isSuccessful()) {
                    GifArrays = new ArrayList<>();
                    GifArrays.addAll((List<HindithemekeyboardItem>) response.body().get(0).getHindithemekeyboard());
                    LayoutProgress.setVisibility(View.GONE);
                    RvGifList.setLayoutManager(new GridLayoutManager(context, 2));
                    adapter = new GifAdapter(context, response.body().get(0).getThumburl() + "/", response.body().get(0).getUrl() + "/", GifArrays, (pos, gifArray, ivGif, ivDownloadGif, ivCheckGif) -> {
                        File THUMB = new File(context.getFilesDir(), "Gif/" + gifArray.get(pos).getId() + ".png");
                        File GIF = new File(context.getFilesDir(), "Gif/" + gifArray.get(pos).getId() + ".gif");
                        if (THUMB.exists()) {
                            new MySharePref(context).putPrefString(MySharePref.SELECT_GIF_THEME_GIF, GIF.getAbsolutePath());
                            new MySharePref(context).putPrefString(MySharePref.SELECT_GIF_THEME_THUMB, THUMB.getAbsolutePath());
                            new MySharePref(context).putPrefString(MySharePref.SELECT_THEME_THUMB, "");
                            new MySharePref(context).putPrefBoolean(MySharePref.SAVE_IMAGE,false);
                            Glide.with(context).asFile().load(GIF).into(new CustomTarget<File>() {
                                @Override
                                public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                    try {
                                        copyFile(resource, new File(context.getFilesDir(), "Gif_save.gif"));
                                        new MySharePref(context).putPrefInt(MySharePref.DEFAULT_THEME, pos);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                            new MySharePref(context).putPrefBoolean(MySharePref.DEFAULT_GIF, true);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(context, "Background set successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setCancelable(false);
                            builder.setMessage(R.string.str_Alert_gif_string)
                                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                        new GifThemeDownloader(context, LayoutProgress, gifArray, pos, ivDownloadGif, ivCheckGif, adapter).execute(response.body().get(0).getThumburl() + "/" + gifArray.get(pos).getId() + ".png", response.body().get(0).getUrl() + "/");
                                    })
                                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
                        }
                    });
                    RvGifList.setAdapter(adapter);
                }
            }

            private void copyFile(File sourceFile, File destFile) throws IOException {
                FileInputStream fis = new FileInputStream(sourceFile);
                FileOutputStream fos = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                fis.close();
                fos.close();
            }
            @Override
            public void onFailure(Call<List<GifModelItem>> call, Throwable t) {
                LayoutProgress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
            case R.id.ImgMore:
                AdsClass.loadInterOne(GIFActivity.this,Constants.InterstitialAd);
                AdsClass.showInter(GIFActivity.this, () -> {
                    startActivity(new Intent(context, MoreSettingsActivity.class));
                    finish();
                },Constants.Show);
                break;
        }
    }
}