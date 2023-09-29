package com.theme.keyboardthemeapp.UI.Adapters;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.theme.keyboardthemeapp.APPUtils.HindiUtils;
import com.theme.keyboardthemeapp.ModelClass.StickerModel;
import com.theme.keyboardthemeapp.R;

import java.util.ArrayList;

public class StickerListAdapter extends BaseAdapter {
    RelativeLayout bg;
    Display display;
    String folder;
    ArrayList<StickerModel> iconarr = null;
    private LayoutInflater infalter;
    Context mContext = null;
    String parent = HindiUtils.appDataPath;

    public long getItemId(int i) {
        return (long) i;
    }

    public StickerListAdapter(Context context, ArrayList<StickerModel> arrayList, String str) {
        this.mContext = context;
        this.folder = str;
        this.iconarr = arrayList;
        this.infalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.display = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    public int getCount() {
        return this.iconarr.size();
    }

    public String getItem(int i) {
        return this.iconarr.get(i).path;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.infalter.inflate(R.layout.sticker_list_item, (ViewGroup) null);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.icon = (ImageView) view2.findViewById(R.id.imageView1);
        viewHolder.bg = (RelativeLayout) view2.findViewById(R.id.lybg);
        StickerModel stickerModel = this.iconarr.get(i);
        if (HindiUtils.selectedCateGory.equals(stickerModel.path)) {
            viewHolder.bg.setBackgroundResource(R.drawable.select_patti);
        } else {
            viewHolder.bg.setBackgroundColor(0);
        }
        if (stickerModel.type == 0) {
            LoadBuilder<Builders.Any.B> with = Ion.with(this.mContext);
            with.load(HindiUtils.storePath + "/" + this.folder + "/" + stickerModel.path + ".png").intoImageView(viewHolder.icon);
        } else if (stickerModel.type == 1) {
            LoadBuilder<Builders.Any.B> with2 = Ion.with(this.mContext);
            with2.load(HindiUtils.storePath + "/" + this.folder + "/" + stickerModel.path + ".png").intoImageView(viewHolder.icon);
        } else if (stickerModel.type == 2) {
            viewHolder.icon.setImageResource(R.drawable.more_gif_unpresed);
        }
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
