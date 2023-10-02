package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class HintWordListAdapter extends BaseAdapter {
    private Typeface WordTypeface;
    private final LayoutInflater layoutInflater;
    private final ArrayList<String> LanguageNameArrayList;
    private Context context;
    private final int ThemeNo;

    public long getItemId(int position) {
        return (long) position;
    }

    public HintWordListAdapter(Context context, ArrayList<String> LanguageNameArrayList, int SelectedTheme) {
        this.context = context;
        this.LanguageNameArrayList = LanguageNameArrayList;
        this.ThemeNo = SelectedTheme;
        if (Constants.ChangeLanguage == 0) {
            this.WordTypeface = Typeface.createFromAsset(context.getAssets(), Constants.HindiFontList[new MySharePref(this.context).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle)]);
        }
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.LanguageNameArrayList.size();
    }

    public String getItem(int position) {
        return this.LanguageNameArrayList.get(position);
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.layoutInflater.inflate(R.layout.layout_hint_item_view, (ViewGroup) null);
            ViewHolder holder = new ViewHolder();
            holder.Mediumtext = (TextView) view.findViewById(R.id.TxtHintItem);
            holder.Mediumtext.setTextSize((float) new MySharePref(context.getApplicationContext()).getPrefInt(MySharePref.SUGGESTION_TEXT_SIZE, 16));
            if (Constants.ChangeLanguage == 0) {
                holder.Mediumtext.setTypeface(this.WordTypeface);
            } else {
                holder.Mediumtext.setTypeface(Typeface.DEFAULT);
            }
            view.setTag(holder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.Mediumtext.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        if (new MySharePref(context).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle) == 0) {
            viewHolder.Mediumtext.setPadding(15, 15, 15, 0);
        } else {
            viewHolder.Mediumtext.setPadding(15, 5, 15, 0);
        }
        if (this.LanguageNameArrayList.get(position).equals("Touch to add")) {
            viewHolder.Mediumtext.setTypeface(Typeface.DEFAULT);
        }
        viewHolder.Mediumtext.setText(this.LanguageNameArrayList.get(position));

        StringBuilder builder = new StringBuilder();
        int TextInt = 0;
        while (true) {
            String[] TextStrArr = Constants.ThemeTextColor;
            if (TextInt >= TextStrArr.length) {
                break;
            }
            builder.append(TextStrArr[TextInt]);
            builder.append(",");
            TextInt++;
        }
        String prefString = new MySharePref(context.getApplicationContext()).getPrefString(MySharePref.ISTEXT_COLOR_CODE, builder.toString());
        Constants.ThemeTextColor = new String[10];
        Constants.ThemeTextColor = prefString.split(",");

        if (this.ThemeNo > 9) {
            viewHolder.Mediumtext.setTextColor(Color.parseColor(Constants.ThemeTextColor[0]));
        } else {
            viewHolder.Mediumtext.setTextColor(Color.parseColor(Constants.ThemeTextColor[Constants.SelectTheme]));
        }
        view.setBackgroundColor(0);
        return view;
    }

    static class ViewHolder {
        protected TextView Mediumtext;

        ViewHolder() {
        }
    }
}
