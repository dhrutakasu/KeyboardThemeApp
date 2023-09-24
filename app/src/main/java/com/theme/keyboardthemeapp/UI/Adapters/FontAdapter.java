package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.MyViewHolder> {
    private final Context context;
    private final String[] fontStyle;
    private final FontListen listen;

    public FontAdapter(Context context, String[] fontStyle,FontListen listen) {
        this.context = context;
        this.fontStyle = fontStyle;
        this.listen = listen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_font, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TxtFont.setTextColor(context.getResources().getColor(R.color.black));
        Typeface fromAsset = Typeface.createFromAsset(context.getAssets(), fontStyle[position]);
        holder.TxtFont.setTypeface(fromAsset);
        holder.TxtFont.setTextSize(16.0f);
        if (new MySharePref(context).getPrefInt(MySharePref.BACKGROUND_FONT_STYLE, 0) == position) {
            holder.FlMainFont.setBackgroundResource(R.drawable.font_box_presed);
        } else {
            holder.FlMainFont.setBackgroundResource(R.drawable.font_box);
        }
        holder.itemView.setOnClickListener(view -> {
            if (position != new MySharePref(context).getPrefInt(MySharePref.FONT_STYLE, 0)) {
                new MySharePref(context).putPrefInt(MySharePref.BACKGROUND_FONT_STYLE, position);
                new MySharePref(context).putPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, 1);
                new MySharePref(context).putPrefInt(MySharePref.CHANGE_LANGUAGE, 0);
                listen.FontClick(position);
                notifyDataSetChanged();
            }
        });
    }

    public interface FontListen {
        void FontClick(int position);
    }

    @Override
    public int getItemCount() {
        return fontStyle.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout FlMainFont;
        private final TextView TxtFont;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            FlMainFont = itemView.findViewById(R.id.FlMainFont);
            TxtFont = itemView.findViewById(R.id.TxtFont);
        }
    }
}
