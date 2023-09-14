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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.JokeModel;
import com.theme.keyboardthemeapp.ModelClass.StatusItem;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInstance;
import com.theme.keyboardthemeapp.Retrofit.RetrofitInterface;
import com.theme.keyboardthemeapp.UI.Adapters.JokesAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class JokesActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private RecyclerView RecyclerJoke;
    private View LayoutProgress;
    public static ArrayList<StatusItem> statusItems = new ArrayList<>();
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
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(getString(R.string.Jockes));
        RecyclerJoke.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        GetJokeResponse();
    }

    private void GetJokeResponse() {
        if (Constants.isNetworkAvailable(context)) {
            LayoutProgress.setVisibility(View.VISIBLE);
            RetrofitInterface downloadService = RetrofitInstance.createService(RetrofitInterface.class, "http://technoappsolution.com/app/");
            Call<JokeModel> call = downloadService.getJokeData("assets/android/hindikeyboard/hindijokes.json");
            call.enqueue(new Callback<JokeModel>() {
                @Override
                public void onResponse(Call<JokeModel> call, Response<JokeModel> response) {
                    if (response.isSuccessful()) {
                        LayoutProgress.setVisibility(View.GONE);
                        System.out.println("---- --- -- JokeBody : " + response.body().toString());
                        statusItems = new ArrayList<>();
                        statusItems = (ArrayList<StatusItem>) response.body().getStatus();
                        System.out.println("---- --- -- Size : " + statusItems.size());
                        jokesAdapter = new JokesAdapter(context, statusItems, pos -> {
                            Intent intent = new Intent(context, ViewJokeQuoteActivity.class);
                            Bundle quote_bundle = new Bundle();
//                            quote_bundle.putSerializable(Constants.ARRAY_LIST_QUOTE, (Serializable) statusItems);
//                            intent.putExtra(Constants.BUNDLE_LIST, quote_bundle);
                            intent.putExtra(Constants.QUOTE_POS, pos);
                            startActivity(intent);
                        });
                        RecyclerJoke.setAdapter(jokesAdapter);
                    }
                }

                @Override
                public void onFailure(Call<JokeModel> call, Throwable t) {
                    LayoutProgress.setVisibility(View.GONE);
                    Log.e("error", "onFailure: ", t);
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