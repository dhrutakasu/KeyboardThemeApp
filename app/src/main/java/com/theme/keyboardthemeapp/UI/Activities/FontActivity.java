package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.FonstStyleAdapter;

public class FontActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView ImgBack, ImgMore;
    private TextView TxtTitle;
    private RecyclerView RvFontList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_activity);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgMore = (ImageView) findViewById(R.id.ImgMore);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        RvFontList = (RecyclerView) findViewById(R.id.RvFontList);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        ImgMore.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.font_style);
        RvFontList.setLayoutManager(new LinearLayoutManager(context));
        RvFontList.setAdapter(new FonstStyleAdapter(context));
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