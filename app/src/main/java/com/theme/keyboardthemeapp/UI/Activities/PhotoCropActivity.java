package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Cropper.CropImageView;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

import java.io.File;
import java.io.FileOutputStream;

public class PhotoCropActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView ImgBack, ImgDone;
    private TextView TxtTitle;
    private String path;
    private CropImageView IvCropper;
    private View LayoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_crop);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgDone = (ImageView) findViewById(R.id.ImgDone);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        IvCropper = (CropImageView) findViewById(R.id.IvCropper);
        LayoutProgress = (View) findViewById(R.id.LayoutProgress);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
        ImgDone.setOnClickListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        ImgDone.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.str_crop);

        Display window = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        window.getMetrics(metrics);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Constants.width = displayMetrics.widthPixels;
        Constants.height = displayMetrics.heightPixels;
        int height = metrics.heightPixels / 3;
        int width = metrics.widthPixels / 2;

        if (Constants.KeyboardHeight == -1) {
            Constants.KeyboardHeight = width;
        }

        path = getIntent().getStringExtra(Constants.FILE_PATH);
        Bitmap file = BitmapFactory.decodeFile(path);
        IvCropper.setAspectRatio(10, 7);
        IvCropper.setFixedAspectRatio(true);
        IvCropper.setImageBitmap(file);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
            case R.id.ImgDone:
                new SaveCropedImage(IvCropper.getCroppedImage()).execute(new Void[0]);
                break;
        }
    }

    private class SaveCropedImage extends AsyncTask<Void, Void, Void> {
        private Bitmap croppedImage;

        public SaveCropedImage(Bitmap croppedImage) {
            this.croppedImage = croppedImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LayoutProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            Constants.width = displayMetrics.widthPixels;
            Constants.height = displayMetrics.heightPixels;
            File file2 = new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg");

            try {
                croppedImage = Bitmap.createScaledBitmap(croppedImage, Constants.height, Constants.KeyboardHeight, false);
                croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file2));
            } catch (Exception unused2) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            new MySharePref(context).putPrefBoolean(MySharePref.SAVE_IMAGE,true);
            setResult(RESULT_OK);
            finish();
        }
    }
}