package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.theme.keyboardthemeapp.Constants;

import java.lang.reflect.Method;

public class SetVibrateCompact {
    private static final Method METHOD_hasVibrator = getMethod(Vibrator.class, "hasVibrator", new Class[0]);
    private static final SetVibrateCompact sInstance = new SetVibrateCompact();
    private Vibrator mVibrator;

    private SetVibrateCompact() {
    }

    public static SetVibrateCompact getInstance(Context context) {
        SetVibrateCompact setVibrateCompact = sInstance;
        if (setVibrateCompact.mVibrator == null) {
            setVibrateCompact.mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        return sInstance;
    }

    public boolean hasVibrator() {
        Vibrator vibrator = this.mVibrator;
        if (vibrator == null) {
            return false;
        }
        return ((Boolean) invoke(vibrator, true, METHOD_hasVibrator, new Object[0])).booleanValue();
    }

    public void vibrate(long j) {
        this.mVibrator.vibrate(j);
    }

    public static Method getMethod(Class<?> cls, String str, Class<?>... clsArr) {
        if (cls != null && !TextUtils.isEmpty(str)) {
            try {
                return cls.getMethod(str, clsArr);
            } catch (NoSuchMethodException | SecurityException unused) {
            }
        }
        return null;
    }

    public static Object invoke(Object obj, Object obj2, Method method, Object... objArr) {
        if (method == null) {
            return obj2;
        }
        try {
            return method.invoke(obj, objArr);
        } catch (Exception e) {
            Log.e("Exception", "Exception in invoke: " + e.getClass().getSimpleName());
            return obj2;
        }
    }

}
