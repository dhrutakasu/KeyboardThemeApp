package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.theme.keyboardthemeapp.R;

import java.util.List;

import hani.momanii.supernova_emoji_library.Helper.EmojiconGridView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;
import hani.momanii.supernova_emoji_library.emoji.Emojicon;

public class EmojiListAdapter extends ArrayAdapter<Emojicon> {
    private int items = 0;
    private boolean UseSystemDefault = false;
    EmojiconGridView.OnEmojiconClickedListener onEmojiconClickedListener;


    public EmojiListAdapter(Context context, List<Emojicon> data, boolean useSystemDefault) {
        super(context, R.layout.layout_emojicon, data);
        UseSystemDefault = useSystemDefault;
    }

    public EmojiListAdapter(Context context, Emojicon[] data, boolean useSystemDefault) {
        super(context, R.layout.layout_emojicon, data);
        UseSystemDefault = useSystemDefault;
    }

    public EmojiListAdapter(Context applicationContext, Emojicon[] data, int i) {
        super(applicationContext, i, data);
        this.items = i;
    }


    public void setOnEmojiconClickedListener(EmojiconGridView.OnEmojiconClickedListener listener){
        this.onEmojiconClickedListener = listener;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.layout_emojicon, (ViewGroup) null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (EmojiconTextView) view.findViewById(R.id.TxtViewEmojIcon);
            holder.icon.setUseSystemDefault(UseSystemDefault);

            view.setTag(holder);
        }
        if (getItem(i) != null) {
            ((ViewHolder) view.getTag()).icon.setText(((Emojicon) getItem(i)).getEmoji());
        }
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1) {
                    motionEvent.getAction();
                } else if (EmojiListAdapter.this.items == 0) {
                    ((CustomKeypad) CustomKeypad.service).clickevent(i);
                } else if (EmojiListAdapter.this.items == 1) {
                    ((CustomKeypad) CustomKeypad.service).clickeventfl(i);
                } else if (EmojiListAdapter.this.items == 2) {
                    ((CustomKeypad) CustomKeypad.service).clickeventfg(i);
                } else if (EmojiListAdapter.this.items == 3) {
                    ((CustomKeypad) CustomKeypad.service).clickeventcar(i);
                } else if (EmojiListAdapter.this.items == 4) {
                    ((CustomKeypad) CustomKeypad.service).clickeventsymbol(i);
                } else if (EmojiListAdapter.this.items == 5) {
                    ((CustomKeypad) CustomKeypad.service).clickeventfood(i);
                } else if (EmojiListAdapter.this.items == 6) {
                    ((CustomKeypad) CustomKeypad.service).clickeventElectronics(i);
                }
                return true;
            }
        });
        return view;
    }

    class ViewHolder {
        EmojiconTextView icon;
    }
}