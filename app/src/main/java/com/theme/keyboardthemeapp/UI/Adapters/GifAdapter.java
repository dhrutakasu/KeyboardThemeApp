package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.ModelClass.HindithemekeyboardItem;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

import java.io.File;
import java.util.ArrayList;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<HindithemekeyboardItem> gifArray;
    private final GifClick gifClick;
    private final String thumbUrl,gifUrl;

    public GifAdapter(Context context, String thumbUrl, String gifUrl, ArrayList<HindithemekeyboardItem> gifArray, GifClick gifClick) {
        this.context = context;
        this.gifArray = gifArray;
        this.thumbUrl = thumbUrl;
        this.gifUrl = gifUrl;
        this.gifClick = gifClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_gif, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        File GifFolder = new File(context.getFilesDir(), "Gif/"+gifArray.get(position).getId() + ".png");
        File GIF = new File(context.getFilesDir(), "Gif/" + gifArray.get(position).getId() + ".gif");
        holder.IvGif.setScaleType(ImageView.ScaleType.FIT_XY);
        if (GifFolder.exists()){
            Glide.with(context).asGif().load(GIF)
                    .placeholder(R.drawable.ic_place_holder).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.IvGif);
            holder.IvDownloadGif.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(thumbUrl + gifArray.get(position).getId() + ".png")
                    .placeholder(R.drawable.ic_place_holder).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.IvGif);
            holder.IvDownloadGif.setVisibility(View.VISIBLE);
        }
        String PrefThumb = new MySharePref(context).getPrefString(MySharePref.SELECT_GIF_THEME_THUMB, "");
        if (PrefThumb.equalsIgnoreCase(GifFolder.getAbsolutePath())){
            holder.IvCheckGif.setVisibility(View.VISIBLE);
        }else {
            holder.IvCheckGif.setVisibility(View.GONE);
        }
        if (holder.IvCheckGif.getVisibility()==View.VISIBLE||holder.IvDownloadGif.getVisibility()==View.VISIBLE){
            holder.ConsIcon.setVisibility(View.VISIBLE);
        }else {
            holder.ConsIcon.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(view -> {
            gifClick.GifListeners(position, gifArray,holder.IvGif, holder.IvDownloadGif, holder.IvCheckGif);
        });
    }

    public interface GifClick{
        void GifListeners(int pos, ArrayList<HindithemekeyboardItem> gifArray, ImageView ivGif, ImageView ivDownloadGif, ImageView ivCheckGif);
    }
    @Override
    public int getItemCount() {
        return gifArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView IvGif, IvCheckGif, IvDownloadGif;

        private final ConstraintLayout ConsIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvGif = itemView.findViewById(R.id.IvGif);
            ConsIcon = itemView.findViewById(R.id.ConsIcon);
            IvCheckGif = itemView.findViewById(R.id.IvCheckGif);
            IvDownloadGif = itemView.findViewById(R.id.IvDownloadGif);
        }
    }
}
