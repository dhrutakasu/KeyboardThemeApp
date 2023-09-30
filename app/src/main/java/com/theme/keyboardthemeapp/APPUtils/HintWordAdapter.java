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

public class HintWordAdapter extends BaseAdapter {
    public ArrayList<String> filteredDataArrayList = new ArrayList<>();
    Typeface fontstyle;
    HintFilter hintFilter;
    private LayoutInflater infalter;
    ArrayList<String> langNameArrayList;
    Context mContext = null;
    int textwid;
    int themeNo;

    public long getItemId(int i) {
        return (long) i;
    }

    public HintWordAdapter(Context context, ArrayList<String> arrayList, int i, int i2) {
        this.mContext = context;
        this.langNameArrayList = arrayList;
        this.themeNo = i;
        this.textwid = i2 / 3;
        if (Constants.ChangeLanguage == 0) {
            this.fontstyle = Typeface.createFromAsset(context.getAssets(), Constants.HindiFontList[new MySharePref(mContext).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle)]);
        }
        this.infalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.langNameArrayList.size();
    }

    public String getItem(int i) {
        return this.langNameArrayList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.infalter.inflate(R.layout.hint_item_view, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.Mediumtext = (TextView) view.findViewById(R.id.hint_item);
            viewHolder.Mediumtext.setTextSize((float) new MySharePref(mContext.getApplicationContext()).getPrefInt(MySharePref.SUGGESTION_TEXT_SIZE, 16));
            if (Constants.ChangeLanguage == 0) {
                viewHolder.Mediumtext.setTypeface(this.fontstyle);
            } else {
                viewHolder.Mediumtext.setTypeface(Typeface.DEFAULT);
            }
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder2 = (ViewHolder) view.getTag();
        viewHolder2.Mediumtext.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        if (new MySharePref(mContext).getPrefInt(MySharePref.FONT_STYLE, Constants.FontStyle) == 0) {
            viewHolder2.Mediumtext.setPadding(15, 15, 15, 0);
        } else {
            viewHolder2.Mediumtext.setPadding(15, 5, 15, 0);
        }
        if (this.langNameArrayList.get(i).equals("Touch to add")) {
            viewHolder2.Mediumtext.setTypeface(Typeface.DEFAULT);
        }
        viewHolder2.Mediumtext.setText(this.langNameArrayList.get(i));

        StringBuilder builderText = new StringBuilder();
        int TextInt = 0;
        while (true) {
            String[] TextStrArr = Constants.ThemeTextColor;
            if (TextInt >= TextStrArr.length) {
                break;
            }
            builderText.append(TextStrArr[TextInt]);
            builderText.append(",");
            TextInt++;
        }
        String string = new MySharePref(mContext.getApplicationContext()).getPrefString(MySharePref.ISTEXT_COLOR_CODE, builderText.toString());
        Constants.ThemeTextColor = new String[10];
        Constants.ThemeTextColor = string.split(",");

        if (this.themeNo > 9) {
            viewHolder2.Mediumtext.setTextColor(Color.parseColor(Constants.ThemeTextColor[0]));
        } else {
            viewHolder2.Mediumtext.setTextColor(Color.parseColor(Constants.ThemeTextColor[Constants.SelectTheme]));
        }
        view.setBackgroundColor(0);
        return view;
    }

    static class ViewHolder {
        protected TextView Mediumtext;

        ViewHolder() {
        }
    }

    private class HintFilter extends Filter {
        private HintFilter() {
        }

        public FilterResults performFiltering(CharSequence charSequence) {
            String lowerCase = charSequence.toString().toLowerCase(Locale.US);
            if (lowerCase.startsWith(" ")) {
                lowerCase = lowerCase.substring(1, lowerCase.length());
            }
            HindiUtils.tempTemplateItem = lowerCase;
            FilterResults filterResults = new FilterResults();
            ArrayList<String> arrayList = HintWordAdapter.this.langNameArrayList;
            int size = arrayList.size();
            ArrayList arrayList2 = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                String str = arrayList.get(i);
                if (str.toLowerCase(Locale.US).startsWith(lowerCase)) {
                    arrayList2.add(str);
                }
                filterResults.values = arrayList2;
                filterResults.count = arrayList2.size();
            }
            return filterResults;
        }

        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList unused = HintWordAdapter.this.filteredDataArrayList = (ArrayList) filterResults.values;
            HintWordAdapter.this.notifyDataSetChanged();
        }
    }
}
