package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.theme.keyboardthemeapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {
    private final Context context;
    private final int[] ints;
    private final getBackground background;

    public ColorAdapter(Context context, int[] ints, getBackground background) {
        this.context = context;
        this.ints = ints;
        this.background = background;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_color, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == 0) {
            holder.ViewColor.setBackgroundResource(R.drawable.ic_custome_selector);
        } else {
            holder.ViewColor.setBackgroundColor(ints[position]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background.setBackground(position, ints);
            }
        });
    }

    public interface getBackground {
        void setBackground(int position, int[] ints);
    }

    @Override
    public int getItemCount() {
        return ints.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ViewColor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ViewColor = itemView.findViewById(R.id.ViewColor);
        }
    }
}
