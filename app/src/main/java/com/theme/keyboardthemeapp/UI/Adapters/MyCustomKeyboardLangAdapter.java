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
        this.fontstyle = Typeface.createFromAsset(context.getAssets(), "rob.ttf");
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.c);
        this.prefs = defaultSharedPreferences;
        this.edit = defaultSharedPreferences.edit();
        this.infalter = (LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
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
        if (HindiUtils.selectedLangName.equals(this.list.get(i))) {
            viewHolder2.Mediumtext.setTextColor(-16776961);
            TextView textView = viewHolder2.Mediumtext;
            Context context = this.c;
            textView.setTextSize(Constants.pxFromDp(context, context.getResources().getDimension(com.intuit.ssp.R.dimen._17ssp)));
        } else {
            viewHolder2.Mediumtext.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            TextView textView2 = viewHolder2.Mediumtext;
            Context context2 = this.c;
            textView2.setTextSize(Constants.pxFromDp(context2, context2.getResources().getDimension(com.intuit.ssp.R.dimen._17ssp) - 2.0f));
        }
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.tech.lang.keyboard.hindikeyboard.adapter.MyCustomKeyboardLangAdapter.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    viewHolder2.Mediumtext.setTextColor(-16776961);
                    viewHolder2.Mediumtext.setTextSize(Constants.pxFromDp(MyCustomKeyboardLangAdapter.this.c, MyCustomKeyboardLangAdapter.this.c.getResources().getDimension(com.intuit.ssp.R.dimen._17ssp)));
                } else if (action == 1) {
                    HindiUtils.flg_lang_change = i;
                    HindiUtils.CurrentLang = i;
                    new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.FONT_STYLE, new MySharePref(c.getApplicationContext()).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle));
                    new MySharePref(c.getApplicationContext()).putPrefInt(MySharePref.BACKGROUND_FONT_STYLE, new MySharePref(c.getApplicationContext()).getPrefInt(MySharePref.BACKGROUND_FONT_STYLE, 0));
                    MyCustomKeyboardLangAdapter.this.edit.putInt("lang_flg", HindiUtils.flg_lang_change);
//                    MyCustomKeyboardLangAdapter.this.edit.putInt("CurrentFontStyle", HindiUtils.CurrentFontStyle);
//                    MyCustomKeyboardLangAdapter.this.edit.putInt("EnglishCurrentFontStyle", HindiUtils.EnglishCurrentFontStyle);
                    MyCustomKeyboardLangAdapter.this.edit.putString("SelectedLangName", MyCustomKeyboardLangAdapter.this.list.get(i));
                    if (HindiUtils.isUpHoneycomb) {
                        MyCustomKeyboardLangAdapter.this.edit.apply();
                    } else {
                        MyCustomKeyboardLangAdapter.this.edit.commit();
                    }
                    try {
                        DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(MyCustomKeyboardLangAdapter.this.c, HindiUtils.CurrentLang);
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
            }
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
