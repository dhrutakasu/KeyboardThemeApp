/*
 * Copyright 2016 Hani Al Momani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



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
                    ((HindiKeypad) HindiKeypad.ims).clickevent(i);
                } else if (EmojiAdapter.this.items == 1) {
                    ((HindiKeypad) HindiKeypad.ims).clickeventfl(i);
                } else if (EmojiAdapter.this.items == 2) {
                    ((HindiKeypad) HindiKeypad.ims).clickeventfg(i);
                } else if (EmojiAdapter.this.items == 3) {
                    ((HindiKeypad) HindiKeypad.ims).clickeventcar(i);
                } else if (EmojiAdapter.this.items == 4) {
                    ((HindiKeypad) HindiKeypad.ims).clickeventsymbol(i);
                } else if (EmojiAdapter.this.items == 5) {
                    ((HindiKeypad) HindiKeypad.ims).clickeventfood(i);
                } else if (EmojiAdapter.this.items == 6) {
                    ((HindiKeypad) HindiKeypad.ims).clickeventElectronics(i);
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