package com.theme.keyboardthemeapp.UI.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.theme.keyboardthemeapp.UI.Fragment.DisplaySettingFragment;
import com.theme.keyboardthemeapp.UI.Fragment.FontSettingFragment;
import com.theme.keyboardthemeapp.UI.Fragment.GeneralSettingFragment;
import com.theme.keyboardthemeapp.UI.Fragment.SoundSettingFragment;
import com.theme.keyboardthemeapp.UI.Fragment.TextArtFragment;
import com.theme.keyboardthemeapp.UI.Fragment.TranslateSettingFragment;

import java.util.ArrayList;

public class TextSettingPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<String> items;

    public TextSettingPagerAdapter(FragmentManager supportFragmentManager, ArrayList<String> strings) {
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
        if (position == 0) {
            return new GeneralSettingFragment();
        }
        if (position == 1) {
            return new DisplaySettingFragment();
        }
        if (position == 2) {
            return new FontSettingFragment();
        }
        if (position == 3) {
            return new SoundSettingFragment();
        }
        if (position != 4) {
            return null;
        }
        return new TranslateSettingFragment();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
