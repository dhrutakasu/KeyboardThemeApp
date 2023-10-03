package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theme.keyboardthemeapp.Helper.DatabaseHelper;
import com.theme.keyboardthemeapp.ModelClass.TranslatorModel;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.TranslatorHistoryAdapter;

import java.util.ArrayList;

public class TranslatorHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack;
    private TextView TxtTitle;
    private RecyclerView RvTranslatorHistory;
    private DatabaseHelper helper;
    private ArrayList<TranslatorModel> translatorModels = new ArrayList<>();
    private TranslatorHistoryAdapter translatorHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator_history);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        RvTranslatorHistory = (RecyclerView) findViewById(R.id.RvTranslatorHistory);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.str_my_translated_words);
        RvTranslatorHistory.setLayoutManager(new LinearLayoutManager(context));
        helper = new DatabaseHelper(context);
        translatorModels = helper.getTranslatorsHistory();
        translatorHistoryAdapter = new TranslatorHistoryAdapter(context, translatorModels, new TranslatorHistoryAdapter.getDeleteData() {
            @Override
            public void DeleteClick(int Id, int position) {
                helper.getDeleteTranslators(Id);
                translatorModels.remove(position);
                translatorHistoryAdapter.notifyDataSetChanged();
            }
        });
        RvTranslatorHistory.setAdapter(translatorHistoryAdapter);
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