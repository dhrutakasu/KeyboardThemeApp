package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.UI.Adapters.GifAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class GifThemeDownloader extends AsyncTask<String, Void, Void> {

    private final ArrayList<CategoriesItem> gifArray;
    private final int Pos;
    private final ImageView ivDownloadGif, ivCheckGif;
    private final GifAdapter adapter;
    private View layoutProgress;
    private String fileName = null;
    private Context context;
    String Tostmsg = "";


    public GifThemeDownloader(Context context, View layoutProgress, ArrayList<CategoriesItem> gifArray, int pos, ImageView ivDownloadGif, ImageView ivCheckGif, GifAdapter adapter) {
        this.context = context;
        this.layoutProgress = layoutProgress;
        this.gifArray = gifArray;
        this.Pos = pos;
        this.ivDownloadGif = ivDownloadGif;
        this.ivCheckGif = ivCheckGif;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        layoutProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(String... params) {
        String path = params[0];
        int fileLength;
        try {
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            fileLength = urlConnection.getContentLength();
            File newFolder = new File(context.getFilesDir(), "Gif/");
            if (!(newFolder.exists())) {
                newFolder.mkdirs();
            }
            fileName = gifArray.get(Pos).getId();
            File inputFile = new File(newFolder, fileName + ".png");
            InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
            byte[] buffer = new byte[1024];
            long total = 0;
            int count;
            OutputStream outputStream = new FileOutputStream(inputFile);
            while ((count = inputStream.read(buffer)) != -1) {
                total += count;
                outputStream.write(buffer, 0, count);
                int progress = (int) total * 100 / fileLength;
            }
            inputStream.close();
            outputStream.close();
            Glide.with(context).asFile().load(params[1] + gifArray.get(Pos).getId() + ".gif").into(new CustomTarget<File>() {
                @Override
                public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                    File destinationFile = new File(newFolder, fileName + ".gif");
                    try {
                        copyFile(resource, destinationFile);
                        copyFile(resource, new File(context.getFilesDir(), "Gif_save.gif"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Tostmsg = "Failed to save image.";
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        Tostmsg = "Download Complete.";
    }

    @Override
    protected void onPostExecute(Void unused) {
        File GIF = new File(context.getFilesDir(), "Gif/" + gifArray.get(Pos).getId() + ".gif");
        File THUMB = new File(context.getFilesDir(), "Gif/" + gifArray.get(Pos).getId() + ".png");
        new MySharePref(context).putPrefString(MySharePref.SELECT_GIF_THEME_GIF, GIF.getAbsolutePath());
        new MySharePref(context).putPrefString(MySharePref.SELECT_GIF_THEME_THUMB, THUMB.getAbsolutePath());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivDownloadGif.setVisibility(View.GONE);
                ivCheckGif.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(context, "Background set successfully", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
//        Constants.copyFile(GIF,new File(context.getFilesDir(), "Gif_save.gif"));
        new MySharePref(context).putPrefBoolean(MySharePref.DEFAULT_GIF, true);
        new MySharePref(context).putPrefInt(MySharePref.DEFAULT_THEME, Pos);
        new MySharePref(context).putPrefBoolean(MySharePref.SAVE_IMAGE,false);
        MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, (path, uri) -> {
            // TODO Auto-generated method stub

        });
    }
}
