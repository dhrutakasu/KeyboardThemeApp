package com.theme.keyboardthemeapp.APPUtils;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class RepeatButtonListener implements View.OnTouchListener {
    public final View.OnClickListener clickListener;
    public View downView;
    public Handler handler = new Handler();
    private Runnable handlerRunnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, (long) normalInterval);
            clickListener.onClick(downView);
        }
    };
    private int initialInterval;
    final int normalInterval;

    public RepeatButtonListener(int internal, int i, View.OnClickListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("null runnable");
        } else if (internal < 0 || i < 0) {
            throw new IllegalArgumentException("negative interval");
        } else {
            initialInterval = internal;
            normalInterval = i;
            clickListener = listener;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            handler.removeCallbacks(handlerRunnable);
            handler.postDelayed(handlerRunnable, (long) initialInterval);
            downView = view;
            clickListener.onClick(view);
            return true;
        } else if (action != 1 && action != 3) {
            return false;
        } else {
            handler.removeCallbacks(handlerRunnable);
            downView = null;
            return true;
        }
    }
}
