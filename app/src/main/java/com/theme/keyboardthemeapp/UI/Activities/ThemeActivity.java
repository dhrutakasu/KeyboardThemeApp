package com.theme.keyboardthemeapp.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theme.keyboardthemeapp.Task.ThemeDownloader;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.ModelClass.ThemeModelItem;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Adapters.ThemeAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack, ImgMore;
    private TextView TxtTitle;
    private RecyclerView RvThemeList;
    private ArrayList<CategoriesItem> ThemeArrays = new ArrayList<>();
    private ThemeAdapter adapter;
    private View LayoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgMore = (ImageView) findViewById(R.id.ImgMore);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        RvThemeList = (RecyclerView) findViewById(R.id.RvThemeList);
        LayoutProgress = (View) findViewById(R.id.LayoutProgress);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
        ImgMore.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        ImgMore.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.str_Theme_style);
        if (Constants.isNetworkAvailable(context)) {
            getThemes();
        } else {
            Constants.NoInternetConnection(ThemeActivity.this);
        }
    }

    private void getThemes() {
        LayoutProgress.setVisibility(View.VISIBLE);
        try {
            ThemeArrays = new ArrayList<>();
            String[] strings = getAssets().list("ThemesList");
            for (int i = 0; i < strings.length; i++) {
                CategoriesItem categoriesItem = new CategoriesItem();
                categoriesItem.setId(i);
                categoriesItem.setName("file:///android_asset/ThemesList/" + strings[i]);
                ThemeArrays.add(categoriesItem);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RetrofitInterface downloadService = RetrofitInstance.createService(RetrofitInterface.class, Constants.BASE_URL);
        Call<List<ThemeModelItem>> call = downloadService.getThemesData(Constants.THEME_URL);
        call.enqueue(new Callback<List<ThemeModelItem>>() {
            @Override
            public void onResponse(Call<List<ThemeModelItem>> call, Response<List<ThemeModelItem>> response) {
                if (response.isSuccessful()) {
                    ThemeArrays.addAll((ArrayList<CategoriesItem>) response.body().get(0).getCategories());
                    LayoutProgress.setVisibility(View.GONE);
                    RvThemeList.setLayoutManager(new GridLayoutManager(context, 2));
                    adapter = new ThemeAdapter(context, response.body().get(0).getThumburl() + "/", response.body().get(0).getUrl() + "/", ThemeArrays, (pos, ThemeArray, ivTheme, ivDownloadTheme, ivCheckTheme) -> {

                        new MySharePref(context).putPrefInt(MySharePref.PREVIOUS_THEME, new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0) );
                        File THUMB = new File(context.getFilesDir(), "Theme/" + ThemeArray.get(pos).getName());
                        File Theme = new File(context.getFilesDir(), "Theme/" + ThemeArray.get(pos).getName());
                        if (THUMB.exists()) {
                            new MySharePref(context).putPrefString(MySharePref.SELECT_THEME, Theme.getAbsolutePath());
                            new MySharePref(context).putPrefString(MySharePref.SELECT_THEME_THUMB, THUMB.getAbsolutePath());
                            //todo check
                            Constants.copyFile(Theme,new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg"));
                            new MySharePref(context).putPrefInt(MySharePref.DEFAULT_THEME, pos);
                            new MySharePref(context).putPrefBoolean(MySharePref.DEFAULT_GIF, false);
                            new MySharePref(context).putPrefBoolean(MySharePref.SAVE_IMAGE,false);
                            onBackPressed();
                        } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(false);
                                builder.setMessage(R.string.str_Alert_string)
                                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                                            dialogInterface.dismiss();
                                            new ThemeDownloader(context, LayoutProgress, ThemeArray, pos, ivDownloadTheme, ivCheckTheme, adapter, ThemeActivity.this).execute(response.body().get(0).getThumburl() + "/" + ThemeArray.get(pos).getName(), response.body().get(0).getUrl() + "/");
                                        }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
//                            }
                        }
                    });
                    RvThemeList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ThemeModelItem>> call, Throwable t) {
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
                startActivity(new Intent(context, MoreSettingsActivity.class));
                break;
        }
    }
}