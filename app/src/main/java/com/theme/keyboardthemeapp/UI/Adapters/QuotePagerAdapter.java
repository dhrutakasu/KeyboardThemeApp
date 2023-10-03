package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;

import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.UI.Fragment.QuoteFragment;
import com.theme.keyboardthemeapp.UI.Fragment.QuoteListFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class QuotePagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<CategoriesItem> categoriesItems;

    public QuotePagerAdapter(Context context, FragmentManager supportFragmentManager, ArrayList<CategoriesItem> categoriesItemArrayList) {
        super(supportFragmentManager);
        this.categoriesItems = categoriesItemArrayList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoriesItems.get(position).getName();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return QuoteListFragment.newInstance(String.valueOf(categoriesItems.get(position).getId()), categoriesItems.get(position).getName());
    }

    @Override
    public int getCount() {
        return categoriesItems.size();
    }
}
