package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.os.Vibrator;

import java.lang.reflect.Method;

public class SetVibrateCompact {
    private static final Method METHOD_hasVibrator = HindiUtils.getMethod(Vibrator.class, "hasVibrator", new Class[0]);
    private static final SetVibrateCompact sInstance = new SetVibrateCompact();
    private Vibrator mVibrator;

    private SetVibrateCompact() {
    }

    public static SetVibrateCompact getInstance(Context context) {
        SetVibrateCompact setVibrateCompact = sInstance;
        if (setVibrateCompact.mVibrator == null) {
            setVibrateCompact.mVibrator = (Vibrator) context.getSystemService("vibrator");
        }
        return sInstance;
    }

    public boolean hasVibrator() {
        Vibrator vibrator = this.mVibrator;
        if (vibrator == null) {
            return false;
        }
        return ((Boolean) HindiUtils.invoke(vibrator, true, METHOD_hasVibrator, new Object[0])).booleanValue();
    }

    public void vibrate(long j) {
        this.mVibrator.vibrate(j);
    }
}
