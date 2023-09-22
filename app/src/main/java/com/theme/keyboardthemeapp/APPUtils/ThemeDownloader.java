package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.UI.Activities.ThemeActivity;
import com.theme.keyboardthemeapp.UI.Adapters.ThemeAdapter;

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

public class ThemeDownloader extends AsyncTask<String, Void, Void> {

    private final ArrayList<CategoriesItem> ThemeArray;
    private final int Pos;
    private final ImageView ivDownloadTheme, ivCheckTheme;
    private final ThemeAdapter adapter;
    private final ThemeActivity themeActivity;
    private View layoutProgress;
    private String fileName = null;
    private Context context;
    private URL url;

    public ThemeDownloader(Context context, View layoutProgress, ArrayList<CategoriesItem> ThemeArray, int pos, ImageView ivDownloadTheme, ImageView ivCheckTheme, ThemeAdapter adapter, ThemeActivity themeActivity) {
        this.context = context;
        this.layoutProgress = layoutProgress;
        this.ThemeArray = ThemeArray;
        this.Pos = pos;
        this.ivDownloadTheme = ivDownloadTheme;
        this.ivCheckTheme = ivCheckTheme;
        this.adapter = adapter;
        this.themeActivity = themeActivity;
    }

    @Override
    protected void onPreExecute() {
        layoutProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            File newFolder = new File(context.getFilesDir(), "Theme/");
            if (!(newFolder.exists())) {
                newFolder.mkdirs();
            }
            if (ThemeArray.get(Pos).getName().contains("android_asset")) {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream;
                OutputStream outputStream;
                fileName = ThemeArray.get(Pos).getName().substring(ThemeArray.get(Pos).getName().lastIndexOf("/") + 1);
                String input = ThemeArray.get(Pos).getName();
                inputStream = assetManager.open(input.replace("file:///android_asset/", ""));
                File imageFile = new File(newFolder, fileName);
                outputStream = new FileOutputStream(imageFile);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();
            } else {
                String path = params[0];
                System.out.println("-- - - -- - - -thumbbb PATH: " + path);
                int fileLength = 0;
                fileName = ThemeArray.get(Pos).getName();
                url = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                fileLength = urlConnection.getContentLength();
                File inputFile = new File(newFolder, fileName);
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
            }
            if (ThemeArray.get(Pos).getName().contains("android_asset")) {
                String paths = ThemeArray.get(Pos).getName().substring(ThemeArray.get(Pos).getName().lastIndexOf("/") + 1);
                AssetManager assetManager = context.getAssets();
                InputStream inputStream;
                OutputStream outputStream;
                fileName = ThemeArray.get(Pos).getName().substring(ThemeArray.get(Pos).getName().lastIndexOf("/") + 1);
                String input = "background/" + "bg_" + paths;
                inputStream = assetManager.open(input);
                File imageFile = new File(newFolder, "bg_" + fileName);
                outputStream = new FileOutputStream(imageFile);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();
            } else {
                Glide.with(context).asFile().load(params[1] + ThemeArray.get(Pos).getName()).into(new CustomTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        File destinationFile = new File(newFolder, "bg_" + fileName);
                        try {
                            copyFile(resource, destinationFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("-- - ---- mmm:: " + e.getMessage());
            System.out.println("-- - ---- eee:: " + e.toString());
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
    }

    @Override
    protected void onPostExecute(Void result) {
        if (ThemeArray.get(Pos).getName().contains("android_asset")) {
            File Theme = new File(context.getFilesDir(), "Theme/" + ThemeArray.get(Pos).getName().substring(ThemeArray.get(Pos).getName().lastIndexOf("/") + 1));
            File THUMB = new File(context.getFilesDir(), "Theme/" + "bg_" + ThemeArray.get(Pos).getName().substring(ThemeArray.get(Pos).getName().lastIndexOf("/") + 1));
            new MySharePref(context).putPrefString(MySharePref.SELECT_THEME, Theme.getAbsolutePath());
            new MySharePref(context).putPrefString(MySharePref.SELECT_THEME_THUMB, THUMB.getAbsolutePath());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivDownloadTheme.setVisibility(View.GONE);
                    ivCheckTheme.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    layoutProgress.setVisibility(View.GONE);
                    themeActivity.onBackPressed();
                }
            }, 1000);
        } else {
            File Theme = new File(context.getFilesDir(), "Theme/" + ThemeArray.get(Pos).getName());
            File THUMB = new File(context.getFilesDir(), "Theme/" + ThemeArray.get(Pos).getName());
            new MySharePref(context).putPrefString(MySharePref.SELECT_THEME, Theme.getAbsolutePath());
            new MySharePref(context).putPrefString(MySharePref.SELECT_THEME_THUMB, THUMB.getAbsolutePath());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivDownloadTheme.setVisibility(View.GONE);
                    ivCheckTheme.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    layoutProgress.setVisibility(View.GONE);
                    themeActivity.onBackPressed();
                }
            }, 1000);
        }
        MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, (path, uri) -> {
            // TODO Auto-generated method stub

        });
    }
}
