package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;

public class SetVibrateComp {
    private static final SetVibrateComp S_INSTANCE = new SetVibrateComp();
    private Vibrator mVibrator;

    private SetVibrateComp() {
    }

    public static SetVibrateComp getInstance(Context context) {
        SetVibrateComp setVibrateComp = S_INSTANCE;
        if (setVibrateComp.mVibrator == null) {
            setVibrateComp.mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        return S_INSTANCE;
    }

    public void vibrate(long j) {
        this.mVibrator.vibrate(j);
    }

    public static Method getMethod(Class<?> aClass, String str, Class<?>... clsArr) {
        if (aClass != null && !TextUtils.isEmpty(str)) {
            try {
                return aClass.getMethod(str, clsArr);
            } catch (NoSuchMethodException | SecurityException unused) {
            }
        }
        return null;
    }
}
