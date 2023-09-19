package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theme.keyboardthemeapp.R;

import java.io.IOException;

public class FonstStyleAdapter extends RecyclerView.Adapter<FonstStyleAdapter.MyViewHolder> {
    private final Context context;
    private String fontname = "हिन्दी फ़ॉन्ट शैली";

    public FonstStyleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_font, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TxtFontStyle.setTextColor(context.getResources().getColor(R.color.black));
        holder.TxtFontStyle.setGravity(Gravity.CENTER_VERTICAL);
        holder.TxtFontStyle.setPadding((int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp));
        AssetManager assets = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(assets, "font/style" + (position + 1) + ".ttf");
        holder.TxtFontStyle.setTypeface(typeface);
        holder.TxtFontStyle.setText(fontname);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        String[] list;
        try {
            list = context.getAssets().list("font");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TxtFontStyle;
        private final CheckBox CheckFontStyle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtFontStyle = itemView.findViewById(R.id.TxtFontStyle);
            CheckFontStyle = itemView.findViewById(R.id.CheckFontStyle);
        }
    }
}
