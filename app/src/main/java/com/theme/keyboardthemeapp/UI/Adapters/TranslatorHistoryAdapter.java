package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.TranslatorModel;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Activities.ViewTranslatedWordsActivity;

import java.util.ArrayList;

public class TranslatorHistoryAdapter extends RecyclerView.Adapter<TranslatorHistoryAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<TranslatorModel> translatorModels;
    private final TranslatorHistoryAdapter.getDeleteData getDeleteData;

    public TranslatorHistoryAdapter(Context context, ArrayList<TranslatorModel> translatorModels, getDeleteData getDeleteData) {
        this.context = context;
        this.translatorModels = translatorModels;
        this.getDeleteData = getDeleteData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TxtTranslatorStr.setTextColor(context.getResources().getColor(R.color.black));
        holder.TxtTranslatorStr.setGravity(Gravity.TOP);
        holder.TxtTranslatorStr.setPadding((int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp),
                (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._8sdp));
        holder.TxtTranslatorStr.setText(translatorModels.get(position).getInputStr());
        holder.TxtTranslatorStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ViewTranslatedWordsActivity.class)
                        .putExtra(Constants.TRANSLATOR_DATA, translatorModels.get(position).getId()));
            }
        });
        holder.ImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeleteData.DeleteClick(translatorModels.get(position).getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return translatorModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TxtTranslatorStr;
        private final ImageView ImgDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtTranslatorStr = itemView.findViewById(R.id.TxtTranslatorStr);
            ImgDelete = itemView.findViewById(R.id.ImgDelete);
        }
    }

    public interface getDeleteData {
        void DeleteClick(int Id, int position);
    }
}
