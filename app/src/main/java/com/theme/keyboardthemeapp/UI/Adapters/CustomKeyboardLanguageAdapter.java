package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.theme.keyboardthemeapp.APPUtils.DictionaryTask;
import com.theme.keyboardthemeapp.APPUtils.CustomKeyboardView;
import com.theme.keyboardthemeapp.APPUtils.CustomKeypad;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;

public class CustomKeyboardLanguageAdapter extends BaseAdapter {
    Context c;
    SharedPreferences.Editor edit;
    Typeface fontstyle;
    private LayoutInflater infalter;
    CustomKeyboardView kv;
    ArrayList<String> list;
    SharedPreferences prefs;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public CustomKeyboardLanguageAdapter(Context context, ArrayList<String> arrayList, CustomKeyboardView customKeyboardView) {
        this.c = context;
        this.list = arrayList;
        this.kv = customKeyboardView;
        this.fontstyle = Typeface.createFromAsset(context.getAssets(), "fontRob.ttf");
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.c);
        this.prefs = defaultSharedPreferences;
        this.edit = defaultSharedPreferences.edit();
        this.infalter = (LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public String getItem(int i) {
        return this.list.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.infalter.inflate(R.layout.keyboard_lang_item, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.Mediumtext = (TextView) view.findViewById(R.id.LangName);
            view.setTag(viewHolder);
        }
        final ViewHolder viewHolder2 = (ViewHolder) view.getTag();
        viewHolder2.Mediumtext.setText(this.list.get(i));
        viewHolder2.Mediumtext.setTypeface(this.fontstyle);

        if (this.list.get(i).toLowerCase().contains(new MySharePref(c.getApplicationContext()).getPrefString(MySharePref.LANGUAGE_NAME, Constants.selectedLanguageName))) {
            viewHolder2.Mediumtext.setTextColor(-16776961);
            TextView textView = viewHolder2.Mediumtext;
            Context context = this.c;
            textView.setTextSize(Constants.pxFromDp(context, 8.0f));
        } else {
            viewHolder2.Mediumtext.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            TextView textView2 = viewHolder2.Mediumtext;
            Context context2 = this.c;
            textView2.setTextSize(Constants.pxFromDp(context2, 8.0f - 2.0f));
        }
        view.setOnTouchListener((view2, motionEvent) -> {
            int action = motionEvent.getAction();
            if (action == 0) {
                viewHolder2.Mediumtext.setTextColor(-16776961);
                viewHolder2.Mediumtext.setTextSize(Constants.pxFromDp(CustomKeyboardLanguageAdapter.this.c, 8.0f));
            } else if (action == 1) {
                new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.FONT_STYLE, new MySharePref(c.getApplicationContext()).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle));
                new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.BACKGROUND_FONT_STYLE, new MySharePref(c.getApplicationContext()).getPrefInt(MySharePref.BACKGROUND_FONT_STYLE, 0));
                new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, i);
                new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.CHANGE_LANGUAGE, i);
                new MySharePref(c.getApplicationContext()).putPrefString(MySharePref.LANGUAGE_NAME, list.get(i).substring(0, 2));
                Constants.FlagChangeLanguage = i;
                Constants.selectedLanguageName = list.get(i);
                try {
                    DictionaryTask dictionaryTask = new DictionaryTask(CustomKeyboardLanguageAdapter.this.c, Constants.ChangeLanguage);
                    if (Constants.isUpHoneycombVersion) {
                        dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                    } else {
                        dictionaryTask.execute(new String[0]);
                    }
                    Constants.DictionaryWordLoad = true;
                } catch (Exception unused) {
                }
                CustomKeypad.setKeyboardView();
                CustomKeyboardLanguageAdapter.this.kv.SetPopupVIew();
                CustomKeyboardLanguageAdapter.this.kv.DismissLanguagePopup();
            }
            return true;
        });
        return view;
    }

    /* loaded from: classes2.dex */
    static class ViewHolder {
        protected TextView Mediumtext;

        ViewHolder() {
        }
    }
}
