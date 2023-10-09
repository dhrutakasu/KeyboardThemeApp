package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.theme.keyboardthemeapp.ModelClass.QuoteModelItem;
import com.theme.keyboardthemeapp.ModelClass.StatusItem;
import com.theme.keyboardthemeapp.UI.Fragment.QuoteFragment;

import java.util.ArrayList;

public class QuotePageAdapter extends FragmentPagerAdapter {
    private final ArrayList<QuoteModelItem> StatusItems;

    public QuotePageAdapter(Context context, FragmentManager supportFragmentManager, ArrayList<QuoteModelItem> statusItemArrayList) {
        super(supportFragmentManager);
        this.StatusItems=statusItemArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return QuoteFragment.newInstance(StatusItems.get(position).getStatus());
    }

    @Override
    public int getCount() {
        return StatusItems.size();
    }
}
