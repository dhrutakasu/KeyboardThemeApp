package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;

import com.theme.keyboardthemeapp.ModelClass.JokeModelItem;
import com.theme.keyboardthemeapp.ModelClass.StatusItem;
import com.theme.keyboardthemeapp.UI.Fragment.QuoteFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter  extends FragmentPagerAdapter {
    private final ArrayList<JokeModelItem> StatusItems;

    public PageAdapter(Context context, FragmentManager supportFragmentManager, ArrayList<JokeModelItem> statusItemArrayList) {
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
