package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.theme.keyboardthemeapp.UI.Fragment.FancyTextFragment;
import com.theme.keyboardthemeapp.UI.Fragment.TextArtFragment;

import java.util.ArrayList;

public class TextArtPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<String> items;

    public TextArtPagerAdapter(FragmentManager supportFragmentManager, ArrayList<String> strings) {
        super(supportFragmentManager);
        this.items = strings;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).toString();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return TextArtFragment.newInstance(items.get(position).toString());
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
