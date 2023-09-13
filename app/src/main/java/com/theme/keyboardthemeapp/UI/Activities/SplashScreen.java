package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.theme.keyboardthemeapp.R;

public class SplashScreen extends AppCompatActivity {

    private Context context;
    private Handler handler;
    private long TIME_OUT = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initActions();
    }

    private void initActions() {
        context = this;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        }, TIME_OUT);
    }
}