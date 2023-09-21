package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.R;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<CategoriesItem> gifArray;

    public GifAdapter(Context context, ArrayList<CategoriesItem> gifArray) {
        this.context = context;
        this.gifArray = gifArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_gif, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with((context)).load(Constants.BASE_URL + Constants.GIF_THUMB_URL + gifArray.get(position).getId() + ".png")
                .placeholder(R.drawable.place_holder).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.IvGif);
        holder.IvGif.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.IvCheckGif.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(context.getFilesDir(), "Gif/" + gifArray.get(position).getId() + ".png");
                if (file.exists()){

                }else {
                    if (Status.RUNNING == PRDownloader.getStatus(getDownloadId)) {
                        PRDownloader.pause(getDownloadId);
                        return;
                    }

                    File filepath = new File(getCacheDirPath(context, AppConstants.GifFolder).getAbsolutePath());
                    getDownloadId = PRDownloader.download(objlist.getCallerscreenlink(), String.valueOf(filepath), ExtensionGif)
                            .build()
                            .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                @Override
                                public void onStartOrResume() {

                                }
                            })
                            .setOnPauseListener(new OnPauseListener() {
                                @Override
                                public void onPause() {
                                }
                            })
                            .setOnCancelListener(new OnCancelListener() {
                                @Override
                                public void onCancel() {
                                }
                            })
                            .setOnProgressListener(new OnProgressListener() {
                                @Override
                                public void onProgress(Progress progress) {
                                    long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                }
                            })

                            .start(new OnDownloadListener() {

                                @Override
                                public void onDownloadComplete() {

                                }

                                @Override
                                public void onError(Error error) {

                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gifArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView IvGif, IvCheckGif, IvDownloadGif;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvGif = itemView.findViewById(R.id.IvGif);
            IvCheckGif = itemView.findViewById(R.id.IvCheckGif);
            IvDownloadGif = itemView.findViewById(R.id.IvDownloadGif);
        }
    }
}
