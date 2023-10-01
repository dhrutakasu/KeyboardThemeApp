package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.theme.keyboardthemeapp.APPUtils.CustomKeypad;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.StickerModel;
import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;

public class StickerAdapter extends BaseAdapter {
    RelativeLayout bg;
    Display display;
    String folder;
    ArrayList<StickerModel> iconarr = null;
    private LayoutInflater infalter;
    int items = 0;
    Context mContext = null;
    LinearLayout.LayoutParams params;
    RelativeLayout.LayoutParams paramsPortrait;

    public long getItemId(int i) {
        return (long) i;
    }

    public StickerAdapter(Context context, ArrayList<StickerModel> arrayList, int i, int i2, String str) {
        this.mContext = context;
        this.folder = str;
        this.iconarr = arrayList;
        this.items = i;
        this.infalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.display = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        this.paramsPortrait = new RelativeLayout.LayoutParams(-1, i2 / 2);
        if (this.mContext.getResources().getConfiguration().orientation == 1) {
            this.params = new LinearLayout.LayoutParams((this.display.getWidth() / 3) - Constants.pxFromDp(context, 4.0f), -2);
        } else {
            this.params = new LinearLayout.LayoutParams((this.display.getWidth() / 4) - Constants.pxFromDp(context, 4.0f), -2);
        }
    }

    public int getCount() {
        return this.iconarr.size();
    }

    public String getItem(int i) {
        return this.iconarr.get(i).path;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.infalter.inflate(R.layout.gif_item, (ViewGroup) null);
            viewHolder.icon = (ImageView) view2.findViewById(R.id.imageView1);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.bg = (RelativeLayout) view2.findViewById(R.id.lybg);
        viewHolder.bg.setLayoutParams(this.params);
        viewHolder.icon.setLayoutParams(this.paramsPortrait);
        StickerModel stickerModel = this.iconarr.get(i);
        view2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((CustomKeypad) CustomKeypad.service).StickerClick(i, mContext.getFilesDir().getAbsolutePath()  + "/sticker/" + StickerAdapter.this.folder + "/");
            }
        });
        return view2;
    }

    class ViewHolder {
        RelativeLayout bg;
        ImageView icon;

        ViewHolder() {
        }
    }

    public void setSelected(int i, boolean z) {
        this.iconarr.get(i).isSelected = z;
        notifyDataSetChanged();
    }
}
