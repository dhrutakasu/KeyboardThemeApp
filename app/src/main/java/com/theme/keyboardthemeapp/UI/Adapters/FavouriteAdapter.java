package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theme.keyboardthemeapp.Helper.DictionaryDatabaseHelper;
import com.theme.keyboardthemeapp.ModelClass.DictionaryModel;
import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<DictionaryModel> FavList;
    private final DictionaryDatabaseHelper helper;
    private final FavouriteAdapter.getFavListeners getFavListeners;
    private final String dictionary_Str;

    public FavouriteAdapter(Context context, ArrayList<DictionaryModel> FavList, String dictionary_Str, getFavListeners getFavListeners) {
        this.context = context;
        this.FavList = FavList;
        this.dictionary_Str = dictionary_Str;
        this.getFavListeners = getFavListeners;
        helper = new DictionaryDatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_favourite, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TxtEnglishFav.setText(FavList.get(position).getEnglishWord());
        holder.TxtHindiFav.setText(FavList.get(position).getHindiWord());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFavListeners.ClickFav(FavList.get(position), position);
            }
        });
        holder.IvRemoveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFavListeners.DeleteFav(FavList.get(position).getWId(), position,dictionary_Str);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FavList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TxtEnglishFav, TxtHindiFav;
        private final ImageView IvRemoveFav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtEnglishFav = itemView.findViewById(R.id.TxtEnglishFav);
            TxtHindiFav = itemView.findViewById(R.id.TxtHindiFav);
            IvRemoveFav = itemView.findViewById(R.id.IvRemoveFav);
        }
    }

    public interface getFavListeners {
        void DeleteFav(int FavId, int position, String dictionary_Str);

        void ClickFav(DictionaryModel dictionaryModel, int pos);
    }
}
