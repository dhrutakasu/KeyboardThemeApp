package com.theme.keyboardthemeapp.APPUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.view.ViewCompat;


import com.theme.keyboardthemeapp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

public class HindiUtils {
    public static ArrayList<String> AppDataFilelistName = new ArrayList<>();
    public static ArrayList<String> AppDataFilepathlist = new ArrayList<>();
    public static int CurrentFontStyle = 0;
    public static int CurrentLang = 0;
    public static ArrayList<Integer> DownloadedThemeArray = new ArrayList<>();
    public static int DynamicKeyboardHeight = -1;
    public static int DynamicKeyboardHeightLandScape = -1;
    public static int EnglishCurrentFontStyle = 0;
    public static String[] EnglishfontCollectionFromAsset = {"fontList/font7.ttf", "fontList/font9.ttf", "fontList/font10.ttf", "fontList/font12.ttf", "fontList/font14.ttf", "fontList/font15.ttf", "fontList/font16.ttf", "fontList/font17.ttf", "fontList/font18.ttf", "fontList/font19.otf", "fontList/font20.ttf", "fontList/font21.ttf", "fontList/font23.ttf", "fontList/font25.ttf", "fontList/font26.otf", "fontList/font27.otf", "fontList/font28.ttf", "fontList/font29.ttf", "fontList/font30.ttf", "fontList/font31.ttf", "fontList/font32.ttf"};
    public static File Gifdestfilepath = null;
    public static int HourNotification = 20;
    public static final int KEYCODE_ALPHABETS = -2830;
    public static final int KEYCODE_ALPHABETS1 = -2831;
    public static final int KEYCODE_DELETE = -2264;
    public static final int KEYCODE_EMOJI = -5000;
    public static final int KEYCODE_LANGPOPUP = -97886;
    public static final int KEYCODE_NUMBERS = -6002;
    public static final int KEYCODE_NUMBERS1 = -6003;
    public static final int KEYCODE_SYMBOLS = -1762;
    public static final int KEYCODE_SYMBOLS1 = -1763;
    public static int MinuteNotification = 59;
    public static final int NEXT_GO1 = -97890;
    public static final int NEXT_GO2 = -9789001;
    public static final int NEXT_GO3 = -972550;
    public static int NOTIFICATION_ID = 1193131;
    public static int Noti_ID = 1;
    public static final int PREVIOUS_GO0 = -978901;
    public static final int PREVIOUS_GO1 = -978902;
    public static final int PREVIOUS_GO2 = -9789020;
    public static String SDPATH = "";
    public static final int SHIFT_CODE = -978903;
    public static final int START = -99255;
    public static final int STOP = -97255;
    public static int SecondNotification = 59;
    public static String SelectedGifName = "";
    public static String SelectedGifPath = "";
    public static String SelectedThemeName = "";
    public static String[] SingleQuotes = null;
    public static ArrayList<String> SuggestionData = null;
    public static boolean SuggestionView = true;
    public static ArrayList<String> SuggestionWords = new ArrayList<>(25000);
    public static boolean Support = false;
    public static String THEME_PREFS = "THEME_PREFS";
    public static int TabIds = 0;
    public static ArrayList<String> TabName = new ArrayList<>();
    public static ArrayList<String> Tabid = new ArrayList<>();
    public static ArrayList<String> ThumbName = new ArrayList<>();
    public static ArrayList<String> Thumbid = new ArrayList<>();
    public static long afterthisdayShowNotificationForPotrait = System.currentTimeMillis();
    public static String appDataPath = null;
    public static boolean appisOpen = false;
    public static int arcset = 4;
    public static List<String>[] arrayList = null;
    public static int backgroundColorsetid = 0;
    public static int backgroundColorsetidLand = 0;
    public static boolean checkLanguage = true;
    public static int checkheight = 0;
    public static View commonView = null;
    public static int counterFortab = 0;
    public static String dailyJock = null;
    public static boolean dataload = false;
    public static String[] deafultchar = {"44", "46", "-97886", "64", "35", "36", "37", "38", "40", "41", "42", "43", "45", "33", "34", "39", "58", "59", "47", "63", "126", "177", "215", "247", "8226", "176", "96", "180", "123", "125", "169", "163", "8364", "94", "174", "165", "95", "43", "91", "93", "161", "60", "62", "162", "124", "92", "191", "-6003", "-1763", "8230"};
    public static int defaultBgColor = ViewCompat.MEASURED_STATE_MASK;
    public static ArrayList<String> defaultCharacter = new ArrayList<>(Arrays.asList(deafultchar));
    public static boolean deleteFlg = false;
    public static boolean dictionaryisLoad = false;
    public static String e1 = "";
    public static String e2 = "";
    public static String e3 = "";
    public static String e4 = "";
    public static String e5 = "";
    public static String e6 = "";
    public static String e7 = "";
    public static String e8 = "";
    public static String e9 = "";
    public static String enableActivate = "";
    public static int flg_lang_change = 0;
    public static String[] fontCollectionFromAsset = {"font/style1.ttf", "font/style2.ttf", "font/style3.ttf", "font/style4.ttf", "font/style5.ttf", "font/style6.ttf", "font/font7.ttf", "font/style8.ttf", "font/font9.ttf", "font/font10.ttf"};
    public static String fontname = "हिन्दी फ़ॉन्ट शैली";
    public static int h = 0;
    public static int hintColorCode = -1;
    public static long iferaseNoficationThenCallMilliSecond = 86400000;
    public static boolean isCapsOn = true;
    public static boolean isColorCodeChange = false;
    public static boolean isCopyServiceOn = true;
    public static boolean isEditorActivity = false;
    public static boolean isGifApplied = false;
    public static boolean isLandScapePhotoSet = false;
    public static boolean isOnlineThemeDownloaded = false;
    public static boolean isPhotoSet = false;
    public static boolean isPreviewEnabled = true;
    public static boolean isServiceOn = true;
    public static boolean isSoundOn = true;
    public static boolean isSuggestionView = true;
    public static boolean isTextColorSet = false;
    public static boolean isUpHoneycomb = false;
    public static boolean isVibrateOn = false;
    public static boolean is_user_rated = false;
    public static boolean islandscapebgcolorchange = false;
    public static boolean ispotraitbgcolorchange = false;
    public static List<String> l2 = null;
    static String[] lang = {"Hindi", "English"};
    public static ArrayList<String> langueges = new ArrayList<>(Arrays.asList(lang));
    public static float mFxVolume = 0.3f;
    public static String[][] matrix = null;
    public static long onDayMilisecond = 86400000;
    public static boolean onlineThemeSelected = false;
    public static boolean online_flg = false;
    public static String onlinethemepath = "";
    public static PopupWindow p = null;
    public static boolean packageisLoad = false;
    static String[] packages = {"com.whatsapp", "com.facebook.orca", "com.snapchat.android", "com.google.android.talk", "jp.naver.line.android", "com.viber.voip", "com.skype.raider", "com.twitter.android", "com.bsb.hike"};
    public static PopupWindow popup = null;
    public static int position = 0;
    public static boolean previewActivityisOpen = false;
    public static int previousSelectedThemeno = -1;
    public static int progress = 10;
    public static int progressDefault = 0;
    public static int progressDefaultLand = 0;
    public static boolean savephoto = false;
    public static int selectThemeNo = 0;
    public static String selectedCateGory = "category0";
    public static int selectedLang = 0;
    public static String selectedLangName = "Farsi";
    public static boolean serverflg = false;
    public static long sevendayMillisecond = 518400000;
    public static boolean showsugg = false;
    public static ArrayList<String> socialPackages = new ArrayList<>(Arrays.asList(packages));
    public static String speakLangName = "Hindi";
    public static String storePath = null;
    public static int suggestiontextsize = 16;
    public static String tempTemplateItem = "";
    public static int textColorCode = Color.parseColor("#000000");
    public static int themeNo = 0;
    public static String[] themeTextColor = {"#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"};
    public static String[] themepreviewTextColor = {"#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"};
    public static Timer timer = new Timer();
    public static int tmp_flg = 0;
    public static String[] tmppreviewTextColor = {"#FFFFFF", "#000000", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"};
    public static String[] tmpthemeTextColor = {"#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"};
    public static int transparentBlackbg = 0;
    public static int transparentKey = 0;
    public static int transparentTopbg = 0;
    public static TextView tt = null;
    public static TextView tt1 = null;
    public static String viewQuoteheaderText = "";
    public static int w;
    public static boolean wordExist = true;

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public static String getCurrentProcess(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                return getProcessMoreThanLol(context);
            }
            return getProcessOld(context);
        } catch (Exception unused) {
            return "";
        }
    }

    public static boolean isEnglish() {
        return CurrentLang == 1;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static AlertDialog.Builder buildDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        });
        return builder;
    }

    public static AlertDialog.Builder ThemeAvailDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please Connect Internet to get Online Themes..");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder;
    }

    public static String getProcessMoreThanLol(Context context) {
        Field field;
        Integer num;
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
        } catch (Exception unused) {
            field = null;
        }
        Iterator<ActivityManager.RunningAppProcessInfo> it2 = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            ActivityManager.RunningAppProcessInfo next = it2.next();
            if (next.importance == 100 && next.importanceReasonCode == 0) {
                try {
                    num = Integer.valueOf(field.getInt(next));
                } catch (Exception unused2) {
                    num = null;
                }
                if (num != null && num.intValue() == 2) {
                    runningAppProcessInfo = next;
                    break;
                }
            }
        }
        return runningAppProcessInfo.processName;
    }

    public static String getProcessOld(Context context) throws Exception {
        try {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
            if (runningTasks != null) {
                return runningTasks.get(0).topActivity.getPackageName();
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        if (query == null) {
            return uri.getPath();
        }
        query.moveToFirst();
        @SuppressLint("Range") String string = query.getString(query.getColumnIndex("_data"));
        query.close();
        return string;
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf A[Catch:{ Exception -> 0x00d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d2 A[Catch:{ Exception -> 0x00d4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static ArrayList<String> getSuggestion(String r6) {

        /*    int r0 = r6.length()     // Catch:{ Exception -> 0x00d4 }
            r1 = 0
            r2 = 0
            r3 = 1
            if (r0 == r3) goto L_0x008a
            java.lang.String r0 = ""
            boolean r0 = r6.equals(r0)     // Catch:{ Exception -> 0x00d4 }
            if (r0 == 0) goto L_0x0013
            goto L_0x008a
        L_0x0013:
            java.util.ArrayList<java.lang.String> r0 = SuggestionData     // Catch:{ Exception -> 0x00d4 }
            SuggestionData = r1     // Catch:{ Exception -> 0x00d4 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x00d4 }
            r1.<init>()     // Catch:{ Exception -> 0x00d4 }
            SuggestionData = r1     // Catch:{ Exception -> 0x00d4 }
            if (r0 == 0) goto L_0x004e
            r1 = 0
        L_0x0021:
            int r4 = r0.size()     // Catch:{ Exception -> 0x00d4 }
            if (r1 >= r4) goto L_0x004e
            java.lang.Object r4 = r0.get(r1)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r4 = r4.toLowerCase()     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r5 = r6.toLowerCase()     // Catch:{ Exception -> 0x00d4 }
            boolean r4 = r4.startsWith(r5)     // Catch:{ Exception -> 0x00d4 }
            if (r4 == 0) goto L_0x0046
            java.util.ArrayList<java.lang.String> r4 = SuggestionData     // Catch:{ Exception -> 0x00d4 }
            java.lang.Object r5 = r0.get(r1)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x00d4 }
            r4.add(r5)     // Catch:{ Exception -> 0x00d4 }
        L_0x0046:
            boolean r4 = wordExist     // Catch:{ Exception -> 0x00d4 }
            if (r4 != 0) goto L_0x004b
            goto L_0x004e
        L_0x004b:
            int r1 = r1 + 1
            goto L_0x0021
        L_0x004e:
            java.util.ArrayList<java.lang.String> r0 = SuggestionData     // Catch:{ Exception -> 0x00d4 }
            int r0 = r0.size()     // Catch:{ Exception -> 0x00d4 }
            if (r0 > 0) goto L_0x00c7
            r0 = 0
        L_0x0057:
            java.util.ArrayList<java.lang.String> r1 = SuggestionWords     // Catch:{ Exception -> 0x00d4 }
            int r1 = r1.size()     // Catch:{ Exception -> 0x00d4 }
            if (r0 >= r1) goto L_0x00c7
            java.util.ArrayList<java.lang.String> r1 = SuggestionWords     // Catch:{ Exception -> 0x00d4 }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r4 = r6.toLowerCase()     // Catch:{ Exception -> 0x00d4 }
            boolean r1 = r1.startsWith(r4)     // Catch:{ Exception -> 0x00d4 }
            if (r1 == 0) goto L_0x0082
            java.util.ArrayList<java.lang.String> r1 = SuggestionData     // Catch:{ Exception -> 0x00d4 }
            java.util.ArrayList<java.lang.String> r4 = SuggestionWords     // Catch:{ Exception -> 0x00d4 }
            java.lang.Object r4 = r4.get(r0)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x00d4 }
            r1.add(r4)     // Catch:{ Exception -> 0x00d4 }
        L_0x0082:
            boolean r1 = wordExist     // Catch:{ Exception -> 0x00d4 }
            if (r1 != 0) goto L_0x0087
            goto L_0x00c7
        L_0x0087:
            int r0 = r0 + 1
            goto L_0x0057
        L_0x008a:
            SuggestionData = r1     // Catch:{ Exception -> 0x00d4 }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x00d4 }
            r0.<init>()     // Catch:{ Exception -> 0x00d4 }
            SuggestionData = r0     // Catch:{ Exception -> 0x00d4 }
            r0 = 0
        L_0x0094:
            java.util.ArrayList<java.lang.String> r1 = SuggestionWords     // Catch:{ Exception -> 0x00d4 }
            int r1 = r1.size()     // Catch:{ Exception -> 0x00d4 }
            if (r0 >= r1) goto L_0x00c7
            java.util.ArrayList<java.lang.String> r1 = SuggestionWords     // Catch:{ Exception -> 0x00d4 }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r4 = r6.toLowerCase()     // Catch:{ Exception -> 0x00d4 }
            boolean r1 = r1.startsWith(r4)     // Catch:{ Exception -> 0x00d4 }
            if (r1 == 0) goto L_0x00bf
            java.util.ArrayList<java.lang.String> r1 = SuggestionData     // Catch:{ Exception -> 0x00d4 }
            java.util.ArrayList<java.lang.String> r4 = SuggestionWords     // Catch:{ Exception -> 0x00d4 }
            java.lang.Object r4 = r4.get(r0)     // Catch:{ Exception -> 0x00d4 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x00d4 }
            r1.add(r4)     // Catch:{ Exception -> 0x00d4 }
        L_0x00bf:
            boolean r1 = wordExist     // Catch:{ Exception -> 0x00d4 }
            if (r1 != 0) goto L_0x00c4
            goto L_0x00c7
        L_0x00c4:
            int r0 = r0 + 1
            goto L_0x0094
        L_0x00c7:
            java.util.ArrayList<java.lang.String> r6 = SuggestionData     // Catch:{ Exception -> 0x00d4 }
            int r6 = r6.size()     // Catch:{ Exception -> 0x00d4 }
            if (r6 > 0) goto L_0x00d2
            wordExist = r2     // Catch:{ Exception -> 0x00d4 }
            goto L_0x00d4
        L_0x00d2:
            wordExist = r3     // Catch:{ Exception -> 0x00d4 }
        L_0x00d4:
            java.util.ArrayList<java.lang.String> r6 = SuggestionData
            return r6*/

        throw new UnsupportedOperationException("Method not decompiled: com.tech.lang.keyboard.hindikeyboard.HindiUtils.getSuggestion(java.lang.String):java.util.ArrayList");
   }

    public static HintWordAdapter setSuggestionAdapter(Context context, ArrayList<String> arrayList2, int i, int i2) {
        return new HintWordAdapter(context, arrayList2, i, i2);
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

    public static void setPhoto(Context context, int i) {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/keyboard_image.png");
        try {
            AssetManager assets = context.getAssets();
            assets.open("background/" + context.getAssets().list("background")[i]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            AssetManager assets2 = context.getAssets();
            BitmapFactory.decodeStream(assets2.open("background/" + context.getAssets().list("background")[i]), new Rect(0, 0, 0, 0), options);
            options.inSampleSize = calculateInSampleSize(options, w, (int) context.getResources().getDimension(R.dimen.keyboard_height));
            options.inJustDecodeBounds = false;
            AssetManager assets3 = context.getAssets();
            Bitmap.createScaledBitmap(BitmapFactory.decodeStream(assets3.open("background/" + context.getAssets().list("background")[i]), new Rect(0, 0, 0, 0), options), w, (int) context.getResources().getDimension(R.dimen.keyboard_height), false).compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (IOException unused) {
            Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show();
        }
        new File(context.getFilesDir().getAbsolutePath() + "/keyboard_image_land.png");
    }

    public static void setStaticVariable(Context context) {
        e1 = context.getResources().getString(R.string.error_text1);
        e2 = context.getResources().getString(R.string.error_text2);
        e3 = context.getResources().getString(R.string.error_text3);
        e4 = context.getResources().getString(R.string.error_text4);
        e5 = context.getResources().getString(R.string.error_text5);
        e6 = context.getResources().getString(R.string.error_text6);
        e7 = context.getResources().getString(R.string.error_text7);
        e8 = context.getResources().getString(R.string.error_text8);
        e9 = context.getResources().getString(R.string.error_text9);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (Build.VERSION.SDK_INT >= 11) {
            isUpHoneycomb = true;
        }
        appDataPath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName();
        int i = 0;
        themeNo = defaultSharedPreferences.getInt("theme_no", 0);
        is_user_rated = defaultSharedPreferences.getBoolean("israted", false);
        selectThemeNo = themeNo;
        storePath = defaultSharedPreferences.getString("storePath", Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/.stickerData");
        new File(storePath).exists();
        packageisLoad = defaultSharedPreferences.getBoolean("pkgload", false);
        h = defaultSharedPreferences.getInt("UtilHs", 0);
        w = defaultSharedPreferences.getInt("UtilWs", 0);
        flg_lang_change = defaultSharedPreferences.getInt("lang_flg", 0);
        CurrentLang = defaultSharedPreferences.getInt("lang_flg", 0);
        try {
            Support = isSupported(context, context.getResources().getString(R.string.isSupportLang));
        } catch (Exception unused) {
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(THEME_PREFS, 0);
        Support = defaultSharedPreferences.getBoolean("Support", Support);
        isServiceOn = defaultSharedPreferences.getBoolean("isServiceOn", true);
        previousSelectedThemeno = defaultSharedPreferences.getInt("previousSelectedThemeno", -1);
        selectedLangName = defaultSharedPreferences.getString("SelectedLangName", "Hindi");
        speakLangName = defaultSharedPreferences.getString("speakLangName", "hi");
        langueges = new ArrayList<>();
        langueges = getArray("SelectedLanguages", defaultSharedPreferences);
        savephoto = sharedPreferences.getBoolean("savephoto", false);
        CurrentFontStyle = defaultSharedPreferences.getInt("CurrentFontStyle", 0);
        EnglishCurrentFontStyle = sharedPreferences.getInt("EnglishCurrentFontStyle", 0);
        isPhotoSet = sharedPreferences.getBoolean("isPhotoSet", false);
        isOnlineThemeDownloaded = sharedPreferences.getBoolean("isOnlineThemeDownloaded", false);
        SelectedThemeName = sharedPreferences.getString("SelectedThemeName", "kbd0.png");
        isColorCodeChange = sharedPreferences.getBoolean("isColorCodeChange", false);
        defaultBgColor = sharedPreferences.getInt("defaultBgColor", ViewCompat.MEASURED_STATE_MASK);
        backgroundColorsetid = sharedPreferences.getInt("backgroundColorsetid", 0);
        backgroundColorsetidLand = sharedPreferences.getInt("backgroundColorsetidLand", 0);
        ispotraitbgcolorchange = sharedPreferences.getBoolean("ispotraitbgcolorchange", false);
        islandscapebgcolorchange = sharedPreferences.getBoolean("islandscapebgcolorchange", false);
        isLandScapePhotoSet = sharedPreferences.getBoolean("isLandScapePhotoSet", false);
        onlineThemeSelected = sharedPreferences.getBoolean("onlineThemeSelected", false);
        transparentKey = sharedPreferences.getInt("KeyTrans", 0);
        transparentTopbg = sharedPreferences.getInt("transparentTopbg", 0);
        transparentBlackbg = sharedPreferences.getInt("transparentBlackbg", 0);
        isSoundOn = sharedPreferences.getBoolean("soundEnable", true);
        isVibrateOn = sharedPreferences.getBoolean("vibEnable", false);
        isPreviewEnabled = sharedPreferences.getBoolean("prevEnable", true);
        checkLanguage = defaultSharedPreferences.getBoolean("chnagelangu", true);
        isCopyServiceOn = defaultSharedPreferences.getBoolean("isCopyService_On", true);
        SDPATH = defaultSharedPreferences.getString("sdpath", context.getFilesDir().getAbsolutePath() + "/.MyPhotoKeyboardFolder/");
        progress = sharedPreferences.getInt(NotificationCompat.CATEGORY_PROGRESS, 10);
        textColorCode = sharedPreferences.getInt("textColorCode", Color.parseColor("#000000"));
        hintColorCode = sharedPreferences.getInt("hintColorCode", -1);
        mFxVolume = sharedPreferences.getFloat("soundLevel", 0.3f);
        progressDefault = sharedPreferences.getInt("progressDefault", 2);
        progressDefaultLand = sharedPreferences.getInt("progressDefaultLand", 2);
        isTextColorSet = defaultSharedPreferences.getBoolean("isTextColorSet", false);
        isCapsOn = sharedPreferences.getBoolean("capsEnable", true);
        isSuggestionView = sharedPreferences.getBoolean("suggestionEnable", true);
        DynamicKeyboardHeight = sharedPreferences.getInt("keyboardHeight", -1);
        DynamicKeyboardHeightLandScape = sharedPreferences.getInt("keyboardHeightLand", -1);
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (true) {
            String[] strArr = tmpthemeTextColor;
            if (i2 >= strArr.length) {
                break;
            }
            sb.append(strArr[i2]);
            sb.append(",");
            i2++;
        }
        String string = defaultSharedPreferences.getString("textcolor", sb.toString());
        tmpthemeTextColor = new String[10];
        tmpthemeTextColor = string.split(",");
        StringBuilder sb2 = new StringBuilder();
        while (true) {
            String[] strArr2 = tmppreviewTextColor;
            if (i < strArr2.length) {
                sb2.append(strArr2[i]);
                sb2.append(",");
                i++;
            } else {
                String string2 = defaultSharedPreferences.getString("previewtextcolor", sb2.toString());
                tmppreviewTextColor = new String[10];
                tmppreviewTextColor = string2.split(",");
                return;
            }
        }
    }

    public static String getPathFromUriLolipop(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, (String) null, (String[]) null, (String) null);
        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
        query.moveToFirst();
        String string = query.getString(columnIndexOrThrow);
        query.close();
        return string;
    }

    public static void setPhotoPortraitOnly(Context context, int i) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        w = displayMetrics.widthPixels;
        h = displayMetrics.heightPixels;
        File file = new File(context.getFilesDir().getAbsolutePath() + "/keyboard_image.png");
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            AssetManager assets = context.getAssets();
            BitmapFactory.decodeStream(assets.open("background/" + context.getAssets().list("background")[i]), new Rect(0, 0, 0, 0), options);
            options.inSampleSize = calculateInSampleSize(options, w, (int) context.getResources().getDimension(R.dimen.keyboard_height));
            options.inJustDecodeBounds = false;
            AssetManager assets2 = context.getAssets();
            Bitmap.createScaledBitmap(BitmapFactory.decodeStream(assets2.open("background/" + context.getAssets().list("background")[i]), new Rect(0, 0, 0, 0), options), w, (int) context.getResources().getDimension(R.dimen.keyboard_height), false).compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (Exception unused) {
        }
    }

    public static void setPhotoLandscapeOnly(Context context, int i) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        w = displayMetrics.widthPixels;
        h = displayMetrics.heightPixels;
        File file = new File(context.getFilesDir().getAbsolutePath() + "/keyboard_image_land.png");
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            AssetManager assets = context.getAssets();
            BitmapFactory.decodeStream(assets.open("background/" + context.getAssets().list("background")[i]), new Rect(0, 0, 0, 0), options);
            options.inSampleSize = calculateInSampleSize(options, w, (int) context.getResources().getDimension(R.dimen.land_keyboard_height));
            options.inJustDecodeBounds = false;
            AssetManager assets2 = context.getAssets();
            Bitmap.createScaledBitmap(BitmapFactory.decodeStream(assets2.open("background/" + context.getAssets().list("background")[i]), new Rect(0, 0, 0, 0), options), h, (int) context.getResources().getDimension(R.dimen.land_keyboard_height), false).compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (Exception unused) {
        }
    }

    public static ArrayList<String> getArray(String str, SharedPreferences sharedPreferences) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        String string = sharedPreferences.getString(str, "NOPREFSAVED");
        if (string.matches("NOPREFSAVED")) {
            return getDefaultArray();
        }
        try {
            JSONArray jSONArray = new JSONArray(string);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList2.add(jSONArray.getString(i));
            }
            return arrayList2;
        } catch (JSONException unused) {
            return getDefaultArray();
        }
    }

    private static ArrayList<String> getDefaultArray() {
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("Hindi");
        arrayList2.add("English");
        return arrayList2;
    }

    public static int DpToPx(Context context, int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) i, context.getResources().getDisplayMetrics());
    }

    public static int pxFromDp(Context context, float f) {
        return (int) (f * context.getResources().getDisplayMetrics().density);
    }

    public static void deleteCache(Context context) {
        try {
            deleteDir(context.getCacheDir());
        } catch (Exception unused) {
        }
    }

    public static boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
            return file.delete();
        } else if (file == null || !file.isFile()) {
            return false;
        } else {
            return file.delete();
        }
    }
    public static boolean isSupported(Context context, String str) {
        float f = context.getResources().getDisplayMetrics().density;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap createBitmap = Bitmap.createBitmap(200, 80, config);
        Bitmap copy = createBitmap.copy(config, false);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize((float) ((int) (f * 14.0f)));
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(str, (float) ((createBitmap.getWidth() - rect.width()) / 2), (float) ((createBitmap.getHeight() + rect.height()) / 2), paint);
        boolean z = !copy.sameAs(createBitmap);
        copy.recycle();
        createBitmap.recycle();
        return z;
    }
}
