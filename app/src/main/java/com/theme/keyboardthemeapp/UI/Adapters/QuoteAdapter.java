package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theme.keyboardthemeapp.ModelClass.QuoteModelItem;
import com.theme.keyboardthemeapp.ModelClass.StatusItem;
import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<QuoteModelItem> statusItems;
    private final setQuoteListener quoteListener;
    private final Typeface txtTypeface;

    public QuoteAdapter(Context context, ArrayList<QuoteModelItem> statusItems, setQuoteListener quoteListener) {
        this.context = context;
        this.statusItems = statusItems;
        this.quoteListener = quoteListener;
        txtTypeface = Typeface.createFromAsset(context.getAssets(), "FontMedium.otf");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_joke, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TxtJokeQuote.setText(statusItems.get(position).getStatus());
        holder.TxtJokeQuote.setTypeface(txtTypeface);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quoteListener.QuoteListen(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusItems.size();
    }

    public interface setQuoteListener {
        void QuoteListen(int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TxtJokeQuote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtJokeQuote = itemView.findViewById(R.id.TxtJokeQuote);
        }
    }
}
