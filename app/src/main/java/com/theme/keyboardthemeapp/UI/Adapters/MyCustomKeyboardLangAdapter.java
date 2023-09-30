package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.theme.keyboardthemeapp.APPUtils.DictionaryLoadTask;
import com.theme.keyboardthemeapp.APPUtils.HindiKeyboardView;
import com.theme.keyboardthemeapp.APPUtils.HindiKeypad;
import com.theme.keyboardthemeapp.APPUtils.HindiUtils;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class MyCustomKeyboardLangAdapter extends BaseAdapter {
    Context c;
    SharedPreferences.Editor edit;
    Typeface fontstyle;
    private LayoutInflater infalter;
    HindiKeyboardView kv;
    ArrayList<String> list;
    SharedPreferences prefs;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public MyCustomKeyboardLangAdapter(Context context, ArrayList<String> arrayList, HindiKeyboardView hindiKeyboardView) {
        this.c = context;
        this.list = arrayList;
        this.kv = hindiKeyboardView;
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
        Constants.selectedLanguageName = new MySharePref(c.getApplicationContext()).getPrefString(MySharePref.LANGUAGE_NAME, Constants.selectedLanguageName);
        if (Constants.selectedLanguageName.equals(this.list.get(i))) {
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
                viewHolder2.Mediumtext.setTextSize(Constants.pxFromDp(MyCustomKeyboardLangAdapter.this.c, 8.0f));
            } else if (action == 1) {
                new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.FONT_STYLE, new MySharePref(c.getApplicationContext()).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle));
                new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.BACKGROUND_FONT_STYLE, new MySharePref(c.getApplicationContext()).getPrefInt(MySharePref.BACKGROUND_FONT_STYLE, 0));
                new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, i);
                new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.CHANGE_LANGUAGE, i);
                new MySharePref(c.getApplicationContext()).putPrefString(MySharePref.LANGUAGE_NAME, list.get(i));
                Constants.FlagChangeLanguage = i;
                Constants.selectedLanguageName = list.get(i);
//                MyCustomKeyboardLangAdapter.this.edit.putInt("lang_flg", HindiUtils.flg_lang_change);
//                    MyCustomKeyboardLangAdapter.this.edit.putInt("CurrentFontStyle", HindiUtils.CurrentFontStyle);
//                    MyCustomKeyboardLangAdapter.this.edit.putInt("EnglishCurrentFontStyle", HindiUtils.EnglishCurrentFontStyle);
//                MyCustomKeyboardLangAdapter.this.edit.putString("SelectedLangName", MyCustomKeyboardLangAdapter.this.list.get(i));
//                if (HindiUtils.isUpHoneycomb) {
//                    MyCustomKeyboardLangAdapter.this.edit.apply();
//                } else {
//                    MyCustomKeyboardLangAdapter.this.edit.commit();
//                }
                try {
                    DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(MyCustomKeyboardLangAdapter.this.c, Constants.ChangeLanguage);
                    if (HindiUtils.isUpHoneycomb) {
                        dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                    } else {
                        dictionaryLoadTask.execute(new String[0]);
                    }
                    HindiUtils.dictionaryisLoad = true;
                } catch (Exception unused) {
                }
                HindiKeypad.setKeyboardView();
                MyCustomKeyboardLangAdapter.this.kv.setPopup();
                MyCustomKeyboardLangAdapter.this.kv.dismissLangPopup();
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
