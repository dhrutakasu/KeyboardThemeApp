package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.JokeModelItem;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Adapters.JokesAdapter;

import java.util.ArrayList;
import java.util.List;

public class JokesActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private RecyclerView RecyclerJoke;
    private View LayoutProgress;
    private JokesAdapter jokesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        LayoutProgress = (View) findViewById(R.id.LayoutProgress);
        RecyclerJoke = (RecyclerView) findViewById(R.id.RecyclerJoke);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        if (AdsClass.isInternetOn(context)) {
            AdsClass.showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd), Constants.BannerAd, Constants.Show);
        }
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.str_Jockes));
        RecyclerJoke.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        GetJokeResponse();
    }

    private void GetJokeResponse() {
        if (Constants.isNetworkAvailable(context)) {
            LayoutProgress.setVisibility(View.VISIBLE);
            RetrofitInterface downloadService = RetrofitInstance.createService(RetrofitInterface.class, Constants.BASE_URL);
            Call<List<JokeModelItem>> call = downloadService.getJokeData(Constants.JOKE_CATEGORY_URL);
            call.enqueue(new Callback<List<JokeModelItem>>() {
                @Override
                public void onResponse(Call<List<JokeModelItem>> call, Response<List<JokeModelItem>> response) {
                    if (response.isSuccessful()) {
                        LayoutProgress.setVisibility(View.GONE);
                        Constants.jokeModelItems = new ArrayList<>();
                        Constants.jokeModelItems = (ArrayList<JokeModelItem>) response.body();
                        System.out.println("---- -- -- ttt UUL " + Constants.jokeModelItems.size());
                        jokesAdapter = new JokesAdapter(context, Constants.jokeModelItems, pos -> {
                            Intent intent = new Intent(context, ViewJokeActivity.class);
                            intent.putExtra(Constants.QUOTE_POS, pos);
                            intent.putExtra(Constants.TITLES, "Jokes");
                            startActivity(intent);
                        });
                        RecyclerJoke.setAdapter(jokesAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<JokeModelItem>> call, Throwable t) {
                    System.out.println("---- -- -- ttt L " + t.getMessage());
                    LayoutProgress.setVisibility(View.GONE);
                }
            });
        } else {
            Constants.NoInternetConnection(JokesActivity.this);
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