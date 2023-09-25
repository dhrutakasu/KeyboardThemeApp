package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.theme.keyboardthemeapp.R;

public class ImagePreviewActivity extends AppCompatActivity {

    public static ImagePreviewActivity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
    }
}