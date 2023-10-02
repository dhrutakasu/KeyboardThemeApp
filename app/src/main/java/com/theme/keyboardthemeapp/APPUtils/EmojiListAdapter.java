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


/**
 * @author Hani Al Momani (hani.momanii@gmail.com)
 */

public class EmojiAdapter extends ArrayAdapter<Emojicon> {
    private int items = 0;
    private boolean mUseSystemDefault = false;
    EmojiconGridView.OnEmojiconClickedListener emojiClickListener;


    public EmojiAdapter(Context context, List<Emojicon> data, boolean useSystemDefault) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = useSystemDefault;
    }

    public EmojiAdapter(Context context, Emojicon[] data, boolean useSystemDefault) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = useSystemDefault;
    }

    public EmojiAdapter(Context applicationContext, Emojicon[] data, int i) {
        super(applicationContext, i, data);
        this.items = i;
    }


    public void setEmojiClickListener(EmojiconGridView.OnEmojiconClickedListener listener){
        this.emojiClickListener = listener;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
         /*      View v = convertView;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.emojicon_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (EmojiconTextView) v.findViewById(R.id.emojicon_icon);
            holder.icon.setUseSystemDefault(mUseSystemDefault);

            v.setTag(holder);
        }

        Emojicon emoji = getItem(i);
        ViewHolder holder = (ViewHolder) v.getTag();
        holder.icon.setText(emoji.getEmoji());
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiClickListener.onEmojiconClicked(getItem(i));
            }
        });*/
        if (view == null) {
            view = View.inflate(getContext(), R.layout.emojicon_item, (ViewGroup) null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (EmojiconTextView) view.findViewById(R.id.emojicon_icon);
            holder.icon.setUseSystemDefault(mUseSystemDefault);

            view.setTag(holder);
        }
        if (getItem(i) != null) {
            ((ViewHolder) view.getTag()).icon.setText(((Emojicon) getItem(i)).getEmoji());
        }
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1) {
                    motionEvent.getAction();
                } else if (EmojiAdapter.this.items == 0) {
                    ((CustomKeypad) CustomKeypad.service).clickevent(i);
                } else if (EmojiAdapter.this.items == 1) {
                    ((CustomKeypad) CustomKeypad.service).clickeventfl(i);
                } else if (EmojiAdapter.this.items == 2) {
                    ((CustomKeypad) CustomKeypad.service).clickeventfg(i);
                } else if (EmojiAdapter.this.items == 3) {
                    ((CustomKeypad) CustomKeypad.service).clickeventcar(i);
                } else if (EmojiAdapter.this.items == 4) {
                    ((CustomKeypad) CustomKeypad.service).clickeventsymbol(i);
                } else if (EmojiAdapter.this.items == 5) {
                    ((CustomKeypad) CustomKeypad.service).clickeventfood(i);
                } else if (EmojiAdapter.this.items == 6) {
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