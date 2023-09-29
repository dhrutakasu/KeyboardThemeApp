package com.theme.keyboardthemeapp.APPUtils;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class RepeatButtonListener implements View.OnTouchListener {
    /* access modifiers changed from: private */
    public final View.OnClickListener clickListener;
    /* access modifiers changed from: private */
    public View downView;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    private Runnable handlerRunnable = new Runnable() {
        public void run() {
            RepeatButtonListener.this.handler.postDelayed(this, (long) RepeatButtonListener.this.normalInterval);
            RepeatButtonListener.this.clickListener.onClick(RepeatButtonListener.this.downView);
        }
    };
    private int initialInterval;
    /* access modifiers changed from: private */
    public final int normalInterval;

    public RepeatButtonListener(int i, int i2, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            throw new IllegalArgumentException("null runnable");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("negative interval");
        } else {
            this.initialInterval = i;
            this.normalInterval = i2;
            this.clickListener = onClickListener;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.handler.removeCallbacks(this.handlerRunnable);
            this.handler.postDelayed(this.handlerRunnable, (long) this.initialInterval);
            this.downView = view;
            this.clickListener.onClick(view);
            return true;
        } else if (action != 1 && action != 3) {
            return false;
        } else {
            this.handler.removeCallbacks(this.handlerRunnable);
            this.downView = null;
            return true;
        }
    }
}
