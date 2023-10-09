package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_font_style, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TxtFontStyle.setTextColor(context.getResources().getColor(R.color.black));
        AssetManager assets = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(assets, "FontLists/FontStyle" + (position + 1) + ".ttf");
        holder.TxtFontStyle.setTypeface(typeface);
        holder.TxtFontStyle.setText(fontname);
        Constants.FontStyle = new MySharePref(context).getPrefInt(MySharePref.FONT_STYLE, 0);
        if (Constants.FontStyle == position) {
            holder.CheckFontStyle.setChecked(true);
        } else {
            holder.CheckFontStyle.setChecked(false);
        }
        holder.CheckFontStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.FontStyle = position;
                new MySharePref(context).putPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.FontStyle = position;
                new MySharePref(context).putPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        String[] list;
        try {
            list = context.getAssets().list("FontLists");
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
