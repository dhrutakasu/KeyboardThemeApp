package com.theme.keyboardthemeapp.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theme.keyboardthemeapp.APPUtils.GifThemeDownloader;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.ModelClass.GifModel;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Adapters.GifAdapter;

import java.io.File;
import java.util.ArrayList;

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
    private ArrayList<CategoriesItem> GifArrays = new ArrayList<>();
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
        ImgBack.setVisibility(View.VISIBLE);
        ImgMore.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.Gif_style);
        if (Constants.isNetworkAvailable(context)) {
            getGifs();
        } else {
            Constants.NoInternetConnection(GIFActivity.this);
        }
    }

    private void getGifs() {
        LayoutProgress.setVisibility(View.VISIBLE);
        RetrofitInterface downloadService = RetrofitInstance.createService(RetrofitInterface.class, Constants.BASE_URL);
        Call<GifModel> call = downloadService.getGifsData(Constants.GIF_URL);
        call.enqueue(new Callback<GifModel>() {
            @Override
            public void onResponse(Call<GifModel> call, Response<GifModel> response) {
                if (response.isSuccessful()) {
                    GifArrays = new ArrayList<>();
                    GifArrays.addAll((ArrayList<CategoriesItem>) response.body().getCategories());
                    LayoutProgress.setVisibility(View.GONE);
                    RvGifList.setLayoutManager(new GridLayoutManager(context, 2));
                    adapter = new GifAdapter(context, response.body().getThumburl() + "/", response.body().getUrl() + "/", GifArrays, (pos, gifArray, ivGif, ivDownloadGif, ivCheckGif) -> {
                        File THUMB = new File(context.getFilesDir(), "Gif/" + gifArray.get(pos).getId() + ".png");
                        File GIF = new File(context.getFilesDir(), "Gif/" + gifArray.get(pos).getId() + ".gif");
                        if (THUMB.exists()) {
                            new MySharePref(context).putPrefString(MySharePref.SELECT_GIF_THEME_GIF, GIF.getAbsolutePath());
                            new MySharePref(context).putPrefString(MySharePref.SELECT_GIF_THEME_THUMB, THUMB.getAbsolutePath());
                            Toast.makeText(context,"Background set successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setCancelable(false);
                            builder.setMessage(R.string.Alert_string)
                                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                        new GifThemeDownloader(context, LayoutProgress, gifArray, pos, ivDownloadGif, ivCheckGif, adapter).execute(response.body().getThumburl() + "/" + gifArray.get(pos).getId() + ".png", response.body().getUrl() + "/");
                                    })
                                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
                        }
                    });
                    RvGifList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GifModel> call, Throwable t) {
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