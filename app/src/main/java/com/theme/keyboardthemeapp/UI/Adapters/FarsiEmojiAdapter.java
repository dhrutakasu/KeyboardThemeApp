package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.theme.keyboardthemeapp.APPUtils.HindiKeypad;
import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class FarsiEmojiAdapter extends BaseAdapter {
    RelativeLayout bg;
    ImageView icon;
    ArrayList<String> iconarr;
    private LayoutInflater infalter;
    int items;
    Context mContext;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public FarsiEmojiAdapter(Context context, ArrayList<String> arrayList, int i) {
        this.iconarr = null;
        this.mContext = null;
        this.items = 0;
        this.mContext = context;
        this.iconarr = arrayList;
        this.items = i;
        this.infalter = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.iconarr.size();
    }

    @Override // android.widget.Adapter
    public String getItem(int i) {
        return this.iconarr.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            ViewHolder viewHolder2 = new ViewHolder();
            View inflate = this.infalter.inflate(R.layout.emoji_item, (ViewGroup) null);
            inflate.setTag(viewHolder2);
            viewHolder = viewHolder2;
            view = inflate;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        this.icon = (ImageView) view.findViewById(R.id.imageView1);
        viewHolder.bg = (RelativeLayout) view.findViewById(R.id.lybg);
        Log.d("position", i + "");
        this.icon.setBackgroundResource(getResId(this.iconarr.get(i), Drawable.class));
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.tech.lang.keyboard.hindikeyboard.adapter.FarsiEmojiAdapter.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    if (FarsiEmojiAdapter.this.items == 0) {
                        ((HindiKeypad) HindiKeypad.ims).clickevent(i);
                    } else if (FarsiEmojiAdapter.this.items == 1) {
                        ((HindiKeypad) HindiKeypad.ims).clickeventfl(i);
                    } else if (FarsiEmojiAdapter.this.items == 2) {
                        ((HindiKeypad) HindiKeypad.ims).clickeventfg(i);
                    } else if (FarsiEmojiAdapter.this.items == 3) {
                        ((HindiKeypad) HindiKeypad.ims).clickeventcar(i);
                    } else if (FarsiEmojiAdapter.this.items == 4) {
                        ((HindiKeypad) HindiKeypad.ims).clickeventsymbol(i);
                    }
                } else {
                    motionEvent.getAction();
                }
                return true;
            }
        });
        return view;
    }

    public int getResId(String str, Class<?> cls) {
        return this.mContext.getResources().getIdentifier(str, "drawable", this.mContext.getPackageName());
    }

    /* loaded from: classes2.dex */
    class ViewHolder {
        RelativeLayout bg;
        ImageView icon;

        ViewHolder() {
        }
    }
}
