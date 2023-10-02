package com.theme.keyboardthemeapp.APPUtils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.StickerModel;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Activities.ImagePreviewActivity;
import com.theme.keyboardthemeapp.UI.Activities.SettingActivity;
import com.theme.keyboardthemeapp.UI.Activities.ThemeActivity;
import com.theme.keyboardthemeapp.Translate.TranslateIn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hani.momanii.supernova_emoji_library.emoji.Cars;
import hani.momanii.supernova_emoji_library.emoji.Electr;
import hani.momanii.supernova_emoji_library.emoji.Food;
import hani.momanii.supernova_emoji_library.emoji.Nature;
import hani.momanii.supernova_emoji_library.emoji.People;
import hani.momanii.supernova_emoji_library.emoji.Sport;
import hani.momanii.supernova_emoji_library.emoji.Symbols;

public class CustomKeypad extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    public static TextView TxtTimer = null;
    public static boolean CapsLock = false;
    public static int[] ColorCodesInts = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    public static boolean CheckLanguage;
    public static InputMethodService service;
    public static TextView TxtSpeak;
    public static int ToLevel = 0;

    public static String StrCountryName;
    public static ArrayList<String> FinalPatternString = new ArrayList<>();
    public static String FinalPatternWord = "";
    public static String FolderPathOfSticker = "";
    public static Drawable DrawableLanguageOff;
    public static Drawable DrawableLanguageOn;
    public static LinearLayout LlLanguageChange;
    public static Drawable DrawableSpeak;
    public static CountDownTimer CountDownTimer;
    public Runnable RunnableAnimateDown = new AnimatedDownImg();
    public Runnable RunnableAnimateUp = new AnimatedUpImg();
    public static ImageView IvBlackTransparent;
    public static RelativeLayout RvBlackTransparent;
    public static RelativeLayout RvBottom;
    public static ArrayList<ImageButton> BtnArray = new ArrayList<>();
    public static LinearLayout LlSpeak;
    public static Button BtnSpeakLanguage;
    public static int[] LayoutCapsOnQWERTY = {R.xml.layout_cap_on_hindi_default_querty2, R.xml.layout_cap_on_eng_default_querty0};
    public static boolean CapsOnOffFlag = false;
    public static int[] LayoutCapsOffQWERTY = {R.xml.layout_cap_hindi_default_querty2, R.xml.layout_cap_eng_default_querty0};
    public static boolean CheckFlag = false;
    public static ImageButton CloseDialog;
    public static ConstantResource constantResource;
    public static FrameLayout FlContentFrame;
    public static int Counter = 0;
    public static int[] DefaultQWERTY = {R.xml.layout_hindi_default_querty2, R.xml.layout_eng_default_querty0};
    public static Drawable DrawableDelete;
    public static int[] DeleteIconsInts = {R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back};
    public static Drawable DrawableDropArrow;
    public static Drawable DrawableEmoji;
    public static GridView GridEmoji = null;
    public static Drawable DrawableEnter;
    private final int[] EnterIconsInts = {R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter};
    public static int FromLevel = 0;
    public static int[] GeneralIconsPresed = {R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed};
    public static int[] GeneralIconsUnpresed = {R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed};
    public static RelativeLayout RlGif;
    public static ImageView ImgGif;
    public static LinearLayout LlHeader;
    public static LinearLayout LlHintWord;
    public static HorizontalListView HListView;
    public static HttpURLConnection urlConnection;
    public static ArrayList<String> IconsList = new ArrayList<>();
    public static ImageView IvImg;
    public static boolean IsPopupView;
    public static boolean IsSpeechRecognizeAvaliable = false;
    public static boolean IsSpeech = false;
    public static MyKeypadDataView keypadDataView;
    public static int keyboardHeight = 0;
    public static CustomKeyboardView customKeyboardView;
    public static String StrLastWord = "";
    public static AudioManager audioManager;
    public static ConstantPreferences constantPreferences;
    public static boolean CompletionOn;
    public static Context context;
    public static Handler HandlerDown = new Handler();
    public static ClipDrawable DrawableImage;
    public static long KeypressVibrationDuration = -1;
    public static int Level = 0;
    public static TranslateIn mTranslateIn;
    public static RecognitionListener recognitionListener;
    public static SpeechRecognizer speechRecognizer;
    public static Toast toast;
    public static Handler UpHandler = new Handler();
    public static SetVibrateComp vibrateCompact;
    public static LinearLayout LlMainMenu;
    public static boolean ManageClick = false;
    public static boolean NewCapital = false;
    public static NinePatchDrawable ninePatchDrawable;
    public static NinePatchDrawable ninePatchDrawableDelete;
    public static NinePatchDrawable ninePatchDrawableDone;
    public static NinePatchDrawable ninePatchDrawableShiftOff;
    public static NinePatchDrawable ninePatchDrawableShiftOn;
    public static NinePatchDrawable ninePatchDrawableSpace;
    public static NinePatchDrawable ninePatchDrawablePresed;
    public static RelativeLayout RlOtherContent;
    public static int[] DrawablePopUpInts = {R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg};
    public static Drawable DrawablePopUp;
    public static ProgressBar progressBarTalk;
    public static ProgressDialog dialog;
    public static RelativeLayout RlMainContents;
    public static Intent IntentRecognizer;
    public static int[] EmojiPressInts = {R.drawable.emoji_presedtheme0, R.drawable.flower_presed0, R.drawable.food_presed0, R.drawable.sport_presed0, R.drawable.car_presed0, R.drawable.electronic_presed0, R.drawable.sign_presed0};
    public static ArrayList<String> resultList = null;
    public static RelativeLayout RlSubContents;
    public static int selectedTheme = 0;
    public static int[] EmojiUnpressInts = {R.drawable.emoji_unpresedtheme0, R.drawable.flower_unpresed0, R.drawable.food_unpresed0, R.drawable.sport_unpresed0, R.drawable.car_unpresed0, R.drawable.electronic_unpresed0, R.drawable.sign_unpresed0};
    public static Drawable DrawableSetting;
    public static Drawable DrawableShiftOff;
    public static int[] ShiftOffInts = {R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off};
    public static Drawable DrawableShiftOn;
    public static int[] ShiftOnInts = {R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on};
    public static Drawable DrawableSpace;
    public static int[] SpaceInts = {R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space};
    public static LinearLayout LlSpeakLay;
    public static int SpeakLayHeight = 0;
    public static ImageButton SpeakToTextBtn;
    public static ArrayList<StickerModel> stickerModels = new ArrayList<>();
    public static Drawable DrawableStricker;
    public static StringBuilder builder = null;
    public static int TextColorCode = -1;
    public static CharSequence TextDatasChar = "";
    public static Drawable DrawableTheme;
    public static int SpeechErrorCode;
    public static int TempHeight;
    public static int TempHeight1;
    public static boolean TempShowSuggestion = true;
    public static boolean TempShiftOnOff = false;
    public static String UrlString = "";
    public static View views;
    public static Vibrator vibrator;
    public static String StrWord = "";
    public static Exception exception;
    public static int[] AllNumericQwerty = {R.xml.layout_numeric_hindi_querty, R.xml.layout_numeric_enlish_querty};


    View.OnClickListener ChangeSpeakingLanguage = view -> {
        if (new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi").equals(getResources().getString(R.string.speak_lang_2))) {
            new MySharePref(getApplicationContext()).putPrefString(MySharePref.DEFULT_LANGUAGE, "en");
        } else {
            new MySharePref(getApplicationContext()).putPrefString(MySharePref.DEFULT_LANGUAGE, getResources().getString(R.string.speak_lang_2));
        }
        BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
    };

    View.OnClickListener CloseDialogListern = view -> {
        LlSpeakLay.setVisibility(View.GONE);
        RvBottom.setVisibility(View.VISIBLE);
    };

    View.OnClickListener ChangeThemeListern = view -> {
        try {
            if (!Constants.previewActivityisOpen) {
                Constants.wordExist = true;
                Intent intent = new Intent(getApplicationContext(), ThemeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else if (ImagePreviewActivity.act == null) {
                Constants.wordExist = true;
                Intent intent2 = new Intent(getApplicationContext(), ThemeActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
            }
        } catch (Exception unused) {
        }
    };

    View.OnClickListener SpeakDialog = new View.OnClickListener() {
        public void onClick(View view) {
            LlSpeakLay.setLayoutParams(new FrameLayout.LayoutParams(-1, SpeakLayHeight));
            TxtSpeak.setText("Tap to Speak!!");
            SpeakToTextBtn.setVisibility(View.GONE);
            LlSpeakLay.setVisibility(View.VISIBLE);
            RvBottom.setVisibility(View.GONE);
            if (CountDownTimer != null) {
                CountDownTimer.cancel();
            }
            CountDownTimer = new CountDownTimer(14000, 1000) {
                public void onTick(long j) {
                    TxtTimer.setText("00:" + (j / 1000));
                }

                public void onFinish() {
                    TxtTimer.setText("done!");
                    LlSpeakLay.setVisibility(View.GONE);
                    RvBottom.setVisibility(View.VISIBLE);
                }
            }.start();
            if (!IsSpeechRecognizeAvaliable) {
                InitDialogInstallTranslate().show();
            } else if (!isOnline()) {
                showToast(getResources().getString(R.string.No_internet), 1);
            } else if (Arrays.asList(constantResource.getNoVoice()).contains(constantResource.getCountriesIn()[mTranslateIn.getmPosition()])) {
                showToast("Opps! " + mTranslateIn.getmLanguageName() + " language was not supported Speech to Text", 1);
            } else {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        if (!vibrator.hasVibrator()) {
                            vibrator.vibrate(40);
                        }
                    }
                    SpeechErrorCode = 100;
                    IntentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplicationContext().getPackageName());
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 3000);
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Constants.SpeakLanguageName);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                        speechRecognizer.setRecognitionListener(recognitionListener);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                            speechRecognizer.startListening(IntentRecognizer);
                        }
                    }
                } catch (Exception e) {
                    showToast(e.getMessage(), 1);
                }
            }
            Log.d("mToggleSpeech", "onTrue");
        }
    };

    AdapterView.OnItemClickListener SuggectionListItemListern = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long j) {
            CharSequence charSequence;
            String str = (String) adapterView.getItemAtPosition(position);
            if (!StrWord.equals("")) {
                if (str.equals("Touch to add")) {
                    if (!Constants.SuggestionWordsList.contains(StrWord.toLowerCase())) {
                        Constants.SuggestionWordsList.add(StrWord.toLowerCase());
                    }
                    Toast.makeText(getApplicationContext(), "Word Added Successfully", Toast.LENGTH_LONG).show();
                    StrWord = "";
                    getWord(StrWord);
                } else if (resultList.contains("Touch to add")) {
                    if (!Constants.SuggestionWordsList.contains(StrWord.toLowerCase())) {
                        Constants.SuggestionWordsList.add(StrWord.toLowerCase());
                    }
                    Toast.makeText(getApplicationContext(), "Word Added Successfully", Toast.LENGTH_LONG).show();
                    StrWord = "";
                    getWord(StrWord);
                } else {
                    CharSequence charSequence2 = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
                        charSequence2 = getCurrentInputConnection().getExtractedText(new ExtractedTextRequest(), 0).text;
                    }
                    if (charSequence2.toString().contains(" ")) {
                        charSequence = getCurrentInputConnection().getTextBeforeCursor(999999, 0);
                    } else {
                        charSequence = "";
                    }
                    String s = charSequence + "";
                    getCurrentInputConnection().deleteSurroundingText(charSequence2.toString().length(), 0);
                    if (charSequence.toString().contains(" ")) {
                        int lastIndexOf = charSequence.toString().lastIndexOf(" ");
                        str = charSequence.toString().substring(0, lastIndexOf) + " " + str;
                    }
                    getCurrentInputConnection().commitText(str, 0);
                    StrWord = "";
                    getWord(StrWord);
                }
                LlHintWord.setVisibility(View.GONE);
                resultList = null;
                resultList = new ArrayList<>();
                HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, resultList));
                LlMainMenu.setVisibility(View.VISIBLE);
            }
        }
    };
    private RelativeLayout otherContents;

    public void onText(CharSequence charSequence) {
    }

    public void swipeDown() {
    }

    public void swipeLeft() {
    }

    public void swipeRight() {
    }

    public void swipeUp() {
    }

    public CustomKeypad() {
        service = this;
    }

    public static void setKeyboardView() {
        ((CustomKeypad) service).setKeyboardData();
    }

    public static String getErrorText(Context applicationContext, int i) {
        Constants.Error1 = applicationContext.getResources().getString(R.string.error_text1);
        Constants.Error2 = applicationContext.getResources().getString(R.string.error_text2);
        Constants.Error3 = applicationContext.getResources().getString(R.string.error_text3);
        Constants.Error4 = applicationContext.getResources().getString(R.string.error_text4);
        Constants.Error5 = applicationContext.getResources().getString(R.string.error_text5);
        Constants.Error6 = applicationContext.getResources().getString(R.string.error_text6);
        Constants.Error7 = applicationContext.getResources().getString(R.string.error_text7);
        Constants.Error8 = applicationContext.getResources().getString(R.string.error_text8);
        Constants.Error9 = applicationContext.getResources().getString(R.string.error_text9);

        Constants.En_Error1 = applicationContext.getResources().getString(R.string.en_error_text1);
        Constants.En_Error2 = applicationContext.getResources().getString(R.string.en_error_text2);
        Constants.En_Error3 = applicationContext.getResources().getString(R.string.en_error_text3);
        Constants.En_Error4 = applicationContext.getResources().getString(R.string.en_error_text4);
        Constants.En_Error5 = applicationContext.getResources().getString(R.string.en_error_text5);
        Constants.En_Error6 = applicationContext.getResources().getString(R.string.en_error_text6);
        Constants.En_Error7 = applicationContext.getResources().getString(R.string.en_error_text7);
        Constants.En_Error8 = applicationContext.getResources().getString(R.string.en_error_text8);
        Constants.En_Error9 = applicationContext.getResources().getString(R.string.en_error_text9);
        switch (i) {
            case 1:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error1;
                }
                return Constants.Error1;
            case 2:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error2;
                }
                return Constants.Error2;
            case 3:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error3;
                }
                return Constants.Error3;
            case 4:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error4;
                }
                return Constants.Error4;
            case 5:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error5;
                }
                return Constants.Error5;
            case 6:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error6;
                }
                return Constants.Error6;
            case 7:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error7;
                }
                return Constants.Error7;
            case 8:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error8;
                }
                return Constants.Error8;
            case 9:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error9;
                }
                return Constants.Error9;
            default:
                if (Constants.SpeakLanguageName.equals("en")) {
                    return Constants.En_Error7;
                }
                return Constants.Error7;
        }
    }

    public View onCreateInputView() {
        if (Build.VERSION.SDK_INT >= 11) {
            Constants.isUpHoneycombVersion = true;
        }
        context = getApplicationContext();
        DrawableEmoji = getResources().getDrawable(R.drawable.emojipressxmlwhite);
        DrawableTheme = getResources().getDrawable(R.drawable.themepressxmlwhite);
        DrawableStricker = getResources().getDrawable(R.drawable.stickerpressxmlwhite);
        DrawableSetting = getResources().getDrawable(R.drawable.settingpressxmlwhite);
        DrawableShiftOn = getResources().getDrawable(ShiftOnInts[0]);
        DrawableSpace = getResources().getDrawable(SpaceInts[0]);
        DrawableEnter = getResources().getDrawable(EnterIconsInts[0]);
        DrawableDelete = getResources().getDrawable(DeleteIconsInts[0]);
        DrawableLanguageOn = getResources().getDrawable(R.drawable.enable);
        DrawableLanguageOff = getResources().getDrawable(R.drawable.disable);
        DrawableSpeak = getResources().getDrawable(R.drawable.mic_unpresed);
        DrawableDropArrow = getResources().getDrawable(R.drawable.drop_down);
        DrawableShiftOff = getResources().getDrawable(ShiftOffInts[0]);
        ManageClick = false;
        InitHeight();
        constantPreferences = new ConstantPreferences(this);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.phonetic_keyboard), Toast.LENGTH_SHORT).show();
        }

        Constants.languegesArray = new ArrayList<>();
        Constants.languegesArray = Constants.getDefaultLanguageArray();
        Constants.FlagChangeLanguage = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, 0);
        Constants.ChangeLanguage = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, 0);

        IsSpeechRecognizeAvaliable=SpeechRecognizer.isRecognitionAvailable(getBaseContext());
        if (IsSpeechRecognizeAvaliable) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));
        } else {
            InitDialogInstallTranslate().show();
        }

        recognitionListener = new RecognitionListern();
        constantResource = new ConstantResource(this);
        TempShowSuggestion = true;
        Constants.wordExist = true;
        CheckFlag = false;

        keypadDataView = new MyKeypadDataView(this, DefaultQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
        selectedTheme = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_THEME, 0);
        Constants.SelectTheme = selectedTheme;
        TextColorCode = ColorCodesInts[0];

        ninePatchDrawable = (NinePatchDrawable) getResources().getDrawable(GeneralIconsUnpresed[0]);
        ninePatchDrawablePresed = (NinePatchDrawable) getResources().getDrawable(GeneralIconsPresed[0]);
        ninePatchDrawableShiftOff = (NinePatchDrawable) getResources().getDrawable(ShiftOffInts[0]);
        ninePatchDrawableShiftOn = (NinePatchDrawable) getResources().getDrawable(ShiftOnInts[0]);
        ninePatchDrawableSpace = (NinePatchDrawable) getResources().getDrawable(SpaceInts[0]);
        ninePatchDrawableDelete = (NinePatchDrawable) getResources().getDrawable(DeleteIconsInts[0]);
        ninePatchDrawableDone = (NinePatchDrawable) getResources().getDrawable(EnterIconsInts[0]);
        DrawablePopUp = getResources().getDrawable(DrawablePopUpInts[0]);
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.ISCOLOR_CODE_CHANGE, false)) {
            DrawableShiftOff.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableShiftOn.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableEnter.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableSpace.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableEmoji.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableDelete.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawablePopUp.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableTheme.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableSetting.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableLanguageOn.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableLanguageOff.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableSpeak.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableDropArrow.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
        }

        DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
        if (Constants.isUpHoneycombVersion) {
            dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            dictionaryTask.execute();
        }
        switch (selectedTheme) {
            case 0:
                CapsLock = false;
                View inflate = getLayoutInflater().inflate(R.layout.layout_keypad, null);
                views = inflate;
                LlHeader = inflate.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                FlContentFrame = views.findViewById(R.id.contentframe);
                RlMainContents = views.findViewById(R.id.contents1);
                customKeyboardView = views.findViewById(R.id.keyboard);
                LinearLayout linearLayout = views.findViewById(R.id.btnTheme);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }

                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }

                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                linearLayout.setOnClickListener(ChangeThemeListern);
                hideProgressDialog();
                String str = constantPreferences.getCountryNameIn();
                StrCountryName = str;
                mTranslateIn = new TranslateIn(this, str);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                IvBlackTransparent = views.findViewById(R.id.keyboard_black_trans);
                translayout_height(keyboardHeight);
                IvBlackTransparent.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView = views.findViewById(R.id.imgvoid);
                IvImg = imageView;
                ClipDrawable clipDrawable = (ClipDrawable) imageView.getDrawable();
                DrawableImage = clipDrawable;
                clipDrawable.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(0);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 1:
                CapsLock = false;
                View inflate2 = getLayoutInflater().inflate(R.layout.layout_keypad1, null);
                views = inflate2;
                LlHeader = inflate2.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                customKeyboardView = views.findViewById(R.id.keyboard);
                views.findViewById(R.id.btnTheme).setOnClickListener(ChangeThemeListern);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                hideProgressDialog();
                String str2 = constantPreferences.getCountryNameIn();
                StrCountryName = str2;
                mTranslateIn = new TranslateIn(this, str2);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView2 = views.findViewById(R.id.imgvoid);
                IvImg = imageView2;
                ClipDrawable clipDrawable2 = (ClipDrawable) imageView2.getDrawable();
                DrawableImage = clipDrawable2;
                clipDrawable2.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView3 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView3;
                imageView3.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(1);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 2:
                CapsLock = false;
                View inflate3 = getLayoutInflater().inflate(R.layout.layout_keypad2, null);
                views = inflate3;
                LlHeader = inflate3.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                views.findViewById(R.id.btnTheme).setOnClickListener(ChangeThemeListern);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                customKeyboardView = views.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str3 = constantPreferences.getCountryNameIn();
                StrCountryName = str3;
                mTranslateIn = new TranslateIn(this, str3);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView4 = views.findViewById(R.id.imgvoid);
                IvImg = imageView4;
                ClipDrawable clipDrawable3 = (ClipDrawable) imageView4.getDrawable();
                DrawableImage = clipDrawable3;
                clipDrawable3.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView5 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView5;
                imageView5.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(2);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 3:
                CapsLock = false;
                View inflate4 = getLayoutInflater().inflate(R.layout.layout_keypad3, null);
                views = inflate4;
                LlHeader = inflate4.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                views.findViewById(R.id.btnTheme).setOnClickListener(ChangeThemeListern);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                customKeyboardView = views.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str4 = constantPreferences.getCountryNameIn();
                StrCountryName = str4;
                mTranslateIn = new TranslateIn(this, str4);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView6 = views.findViewById(R.id.imgvoid);
                IvImg = imageView6;
                ClipDrawable clipDrawable4 = (ClipDrawable) imageView6.getDrawable();
                DrawableImage = clipDrawable4;
                clipDrawable4.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView7 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView7;
                imageView7.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(3);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 4:
                CapsLock = false;
                View inflate5 = getLayoutInflater().inflate(R.layout.layout_keypad4, null);
                views = inflate5;
                LlHeader = inflate5.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                views.findViewById(R.id.btnTheme).setOnClickListener(ChangeThemeListern);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                customKeyboardView = views.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str5 = constantPreferences.getCountryNameIn();
                StrCountryName = str5;
                mTranslateIn = new TranslateIn(this, str5);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView8 = views.findViewById(R.id.imgvoid);
                IvImg = imageView8;
                ClipDrawable clipDrawable5 = (ClipDrawable) imageView8.getDrawable();
                DrawableImage = clipDrawable5;
                clipDrawable5.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView9 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView9;
                imageView9.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(4);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 5:
                CapsLock = false;
                View inflate6 = getLayoutInflater().inflate(R.layout.layout_keypad5, null);
                views = inflate6;
                LlHeader = inflate6.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                views.findViewById(R.id.btnTheme).setOnClickListener(ChangeThemeListern);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                customKeyboardView = views.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str6 = constantPreferences.getCountryNameIn();
                StrCountryName = str6;
                mTranslateIn = new TranslateIn(this, str6);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView10 = views.findViewById(R.id.imgvoid);
                IvImg = imageView10;
                ClipDrawable clipDrawable6 = (ClipDrawable) imageView10.getDrawable();
                DrawableImage = clipDrawable6;
                clipDrawable6.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView11 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView11;
                imageView11.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(5);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 6:
                CapsLock = false;
                View inflate7 = getLayoutInflater().inflate(R.layout.layout_keypad6, null);
                views = inflate7;
                LlHeader = inflate7.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                views.findViewById(R.id.btnTheme).setOnClickListener(ChangeThemeListern);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                customKeyboardView = views.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str7 = constantPreferences.getCountryNameIn();
                StrCountryName = str7;
                mTranslateIn = new TranslateIn(this, str7);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView12 = views.findViewById(R.id.imgvoid);
                IvImg = imageView12;
                ClipDrawable clipDrawable7 = (ClipDrawable) imageView12.getDrawable();
                DrawableImage = clipDrawable7;
                clipDrawable7.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView13 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView13;
                imageView13.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(6);
                if (!new MySharePref(context).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 7:
                CapsLock = false;
                View inflate8 = getLayoutInflater().inflate(R.layout.layout_keypad7, null);
                views = inflate8;
                LlHeader = inflate8.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                customKeyboardView = views.findViewById(R.id.keyboard);
                LinearLayout linearLayout2 = views.findViewById(R.id.btnTheme);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                linearLayout2.setOnClickListener(ChangeThemeListern);
                hideProgressDialog();
                String str8 = constantPreferences.getCountryNameIn();
                StrCountryName = str8;
                mTranslateIn = new TranslateIn(this, str8);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView14 = views.findViewById(R.id.imgvoid);
                IvImg = imageView14;
                ClipDrawable clipDrawable8 = (ClipDrawable) imageView14.getDrawable();
                DrawableImage = clipDrawable8;
                clipDrawable8.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView15 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView15;
                imageView15.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(7);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 8:
                CapsLock = false;
                View inflate9 = getLayoutInflater().inflate(R.layout.layout_keypad8, null);
                views = inflate9;
                LlHeader = inflate9.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                customKeyboardView = views.findViewById(R.id.keyboard);
                LinearLayout linearLayout3 = views.findViewById(R.id.btnTheme);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                linearLayout3.setOnClickListener(ChangeThemeListern);
                hideProgressDialog();
                String str9 = constantPreferences.getCountryNameIn();
                StrCountryName = str9;
                mTranslateIn = new TranslateIn(this, str9);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView16 = views.findViewById(R.id.imgvoid);
                IvImg = imageView16;
                ClipDrawable clipDrawable9 = (ClipDrawable) imageView16.getDrawable();
                DrawableImage = clipDrawable9;
                clipDrawable9.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView17 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView17;
                imageView17.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(8);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 9:
                CapsLock = false;
                View inflate10 = getLayoutInflater().inflate(R.layout.layout_keypad9, null);
                views = inflate10;
                LlHeader = inflate10.findViewById(R.id.rl_headertext);
                RlSubContents = views.findViewById(R.id.contents);
                RlMainContents = views.findViewById(R.id.contents1);
                customKeyboardView = views.findViewById(R.id.keyboard);
                LinearLayout linearLayout4 = views.findViewById(R.id.btnTheme);
                LlLanguageChange = views.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageChange.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                } else {
                    LlLanguageChange.setBackgroundResource(R.drawable.disable);
                }
                LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                linearLayout4.setOnClickListener(ChangeThemeListern);
                hideProgressDialog();
                String str10 = constantPreferences.getCountryNameIn();
                StrCountryName = str10;
                mTranslateIn = new TranslateIn(this, str10);
                LlSpeakLay = views.findViewById(R.id.speaklay);
                RvBottom = views.findViewById(R.id.bottomlayout);
                SpeakToTextBtn = views.findViewById(R.id.btnspeech);
                BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
                CloseDialog = views.findViewById(R.id.btn_closedialog);
                TxtSpeak = views.findViewById(R.id.speaktext);
                TxtTimer = views.findViewById(R.id.txttimer);
                progressBarTalk = views.findViewById(R.id.progressBarTalk);
                ImageView imageView18 = views.findViewById(R.id.imgvoid);
                IvImg = imageView18;
                ClipDrawable clipDrawable10 = (ClipDrawable) imageView18.getDrawable();
                DrawableImage = clipDrawable10;
                clipDrawable10.setLevel(0);
                SpeakToTextBtn.setOnClickListener(SpeakDialog);
                CloseDialog.setOnClickListener(CloseDialogListern);
                BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
                BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
                ImageView imageView19 = views.findViewById(R.id.keyboard_black_trans);
                IvBlackTransparent = imageView19;
                imageView19.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(keyboardHeight);
                RlGif = views.findViewById(R.id.relay_gif_lay);
                ImgGif = views.findViewById(R.id.img_gif);
                Giflayout_height(keyboardHeight);
                initArrayList(views);
                allClickEvent(9);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGif.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    RlGif.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
        }
        if (selectedTheme > 9) {
            CapsLock = false;
            View inflate11 = getLayoutInflater().inflate(R.layout.layout_keypad, null);
            views = inflate11;
            LlHeader = inflate11.findViewById(R.id.rl_headertext);
            RlSubContents = views.findViewById(R.id.contents);
            FlContentFrame = views.findViewById(R.id.contentframe);
            RlMainContents = views.findViewById(R.id.contents1);
            customKeyboardView = views.findViewById(R.id.keyboard);
            LinearLayout linearLayout5 = views.findViewById(R.id.btnTheme);
            LlLanguageChange = views.findViewById(R.id.changeLang);
            if (Constants.ChangeLanguage == 1) {
                LlLanguageChange.setVisibility(View.VISIBLE);
            } else {
                LlLanguageChange.setVisibility(View.GONE);
            }
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                LlLanguageChange.setBackgroundResource(R.drawable.enable);
            } else {
                LlLanguageChange.setBackgroundResource(R.drawable.disable);
            }
            LlLanguageChange.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                        DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                        LlLanguageChange.setBackgroundResource(R.drawable.disable);
                        return;
                    }
                    DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                    if (Constants.isUpHoneycombVersion) {
                        dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        dictionaryTask2.execute();
                    }
                    new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                    LlLanguageChange.setBackgroundResource(R.drawable.enable);
                }
            });
            linearLayout5.setOnClickListener(ChangeThemeListern);
            hideProgressDialog();
            String str11 = constantPreferences.getCountryNameIn();
            StrCountryName = str11;
            mTranslateIn = new TranslateIn(this, str11);
            LlSpeakLay = views.findViewById(R.id.speaklay);
            RvBottom = views.findViewById(R.id.bottomlayout);
            SpeakToTextBtn = views.findViewById(R.id.btnspeech);
            BtnSpeakLanguage = views.findViewById(R.id.btn_speaklang);
            CloseDialog = views.findViewById(R.id.btn_closedialog);
            TxtSpeak = views.findViewById(R.id.speaktext);
            TxtTimer = views.findViewById(R.id.txttimer);
            RvBlackTransparent = views.findViewById(R.id.relay_trans_lay);
            ImageView imageView20 = views.findViewById(R.id.keyboard_black_trans);
            IvBlackTransparent = imageView20;
            imageView20.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
            translayout_height(keyboardHeight);
            progressBarTalk = views.findViewById(R.id.progressBarTalk);
            ImageView imageView21 = views.findViewById(R.id.imgvoid);
            IvImg = imageView21;
            ClipDrawable clipDrawable11 = (ClipDrawable) imageView21.getDrawable();
            DrawableImage = clipDrawable11;
            clipDrawable11.setLevel(0);
            SpeakToTextBtn.setOnClickListener(SpeakDialog);
            CloseDialog.setOnClickListener(CloseDialogListern);
            BtnSpeakLanguage.setOnClickListener(ChangeSpeakingLanguage);
            BtnSpeakLanguage.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
            RlGif = views.findViewById(R.id.relay_gif_lay);
            ImgGif = views.findViewById(R.id.img_gif);
            Giflayout_height(keyboardHeight);
            initArrayList(views);
            allClickEvent(0);
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                RlGif.setVisibility(View.VISIBLE);
                setkeyboardGif();
            } else {
                RlGif.setVisibility(View.INVISIBLE);
                setkeyboardbackground();
            }
        }
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SAVE_IMAGE, false)) {
            if (selectedTheme == 1) {
                RlSubContents.setBackgroundResource(R.drawable.trans2);
            } else if (selectedTheme == 2) {
                RlSubContents.setBackgroundResource(R.drawable.trans3);
            }
        }
        for (Keyboard.Key key : keypadDataView.getKeys()) {
            int parseInt = Integer.parseInt("" + key.codes[0]);
            if (parseInt == -978903) {
                key.icon = DrawableShiftOff;
            } else if (parseInt == -2830) {
                key.label = key.label;
            } else if (parseInt == -1) {
                key.icon = DrawableShiftOff;
            } else if (parseInt == 32) {
                key.icon = DrawableSpace;
            } else if (parseInt == -6003) {
                key.label = key.label;
            } else if (parseInt == -6002) {
                key.label = key.label;
            } else if (parseInt == -5) {
                key.icon = DrawableDelete;
            } else if (parseInt == -4) {
                key.icon = DrawableEnter;
            }
        }
        if (customKeyboardView != null) {
            customKeyboardView.DismissLanguagePopup();
        }
        customKeyboardView.setOnlineKeyboard(ninePatchDrawable, ninePatchDrawablePresed, TextColorCode, ninePatchDrawableSpace, ninePatchDrawableShiftOff, ninePatchDrawableShiftOn, ninePatchDrawableDelete, ninePatchDrawableDone, DrawablePopUp);
        customKeyboardView.setBackgroundDrawable(new BitmapDrawable());
        customKeyboardView.setKeyboard(keypadDataView);
        int i2 = getCurrentInputEditorInfo().imeOptions & 1073742079;
        if (!(i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5 || i2 == 6)) {
            try {
                getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
            } catch (Exception unused) {
                if (Constants.ChangeLanguage == 1 && new MySharePref(context).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    CapsOnOffFlag = false;
                    CapsLock = false;
                    onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                }
            }
        }
        customKeyboardView.setOnKeyboardActionListener(this);
        customKeyboardView.invalidate();
        Constants.KeypedView = views;
        return views;
    }

    public void setkeyboardbackground() {
        if (getResources().getConfiguration().orientation == 1) {
            Bitmap decodeFile = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/photo_save.jpeg");
            if (decodeFile != null) {
                RlSubContents.setBackgroundDrawable(new BitmapDrawable(decodeFile));
            } else {
                RlSubContents.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
            }
        }
    }

    public void setkeyboardGif() {
        ImgGif = views.findViewById(R.id.img_gif);
        Glide.with(this).asGif().load(new File(getApplicationContext().getFilesDir(), "Gif_save.gif").getAbsolutePath()).placeholder(R.drawable.rain).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ImgGif);
    }

    private void allClickEvent(final int i) {
        views.findViewById(R.id.btn_emoji).setOnClickListener(view -> {
            onKey(Constants.CODE_EMOJI, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
            setTabBg(0, i);
            CapsLock = false;
        });
        views.findViewById(R.id.emojis_tab_1_delete).setOnTouchListener(new RepeatButtonListener(400, 100, new View.OnClickListener() {
            public void onClick(View view) {
                deleteemoji();
            }
        }));
        views.findViewById(R.id.food_tab_1_emoji).setOnTouchListener(new RepeatButtonListener(400, 100, new View.OnClickListener() {
            public void onClick(View view) {
                getFood();
                setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        }));
        views.findViewById(R.id.emojis_tab_1_car).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getcar();
                setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        views.findViewById(R.id.emojis_tab_1_symbol).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getSymbols();
                setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        views.findViewById(R.id.emojis_tab_1_flower).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getFlower();
                setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        views.findViewById(R.id.emojis_tab_1_electronics).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getElectronics();
                setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        views.findViewById(R.id.emojis_tab_1_bell).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getBell();
                setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        views.findViewById(R.id.emojis_tab_1_people).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IconsList = null;
                IconsList = new ArrayList<>();
                RlOtherContent.removeAllViews();
                setTabBg(Integer.parseInt((String) view.getTag()), i);
                initEmojiAdapter();
                RlOtherContent.addView(GridEmoji);
            }
        });
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.ISCOLOR_CODE_CHANGE, false)) {
            DrawableShiftOn.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableShiftOff.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableSpace.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableEnter.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableDelete.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawablePopUp.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableEmoji.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableTheme.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableStricker.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableSetting.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableLanguageOn.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableLanguageOff.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableSpeak.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            DrawableDropArrow.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            BtnSpeakLanguage.setTextColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)));
            if (Build.VERSION.SDK_INT >= 16) {
                views.findViewById(R.id.btn_emoji).setBackground(DrawableEmoji);
                views.findViewById(R.id.btnTheme).setBackground(DrawableTheme);
                views.findViewById(R.id.btn_setting).setBackground(DrawableSetting);
                return;
            }
            views.findViewById(R.id.btnTheme).setBackgroundDrawable(DrawableTheme);
            views.findViewById(R.id.btn_emoji).setBackgroundDrawable(DrawableEmoji);
            views.findViewById(R.id.btn_setting).setBackgroundDrawable(DrawableSetting);
        }
    }

    public void onStartInputView(EditorInfo editorInfo, boolean z) {
        super.onStartInputView(editorInfo, z);
        Log.d("main", "start inpitview");
        if (!IsPopupView) {
            setInputView(onCreateInputView());
            IsPopupView = false;
        }
        int i = editorInfo.inputType & 4080;
        if ((editorInfo.inputType & 15) == 1) {
            if (i == 128 || i == 144) {
                TempShowSuggestion = false;
            }
            if (i == 32) {
                TempShowSuggestion = false;
            } else if (i == 16) {
                TempShowSuggestion = false;
            } else if (i != 64) {
                if (i == 176) {
                    TempShowSuggestion = false;
                } else if (i == 160) {
                    int i2 = editorInfo.inputType;
                }
            }
            if ((editorInfo.inputType & 524288) != 0) {
                TempShowSuggestion = false;
            }
            if ((editorInfo.inputType & 32768) == 0) {
                int i3 = editorInfo.inputType;
            }
            if ((editorInfo.inputType & 65536) != 0) {
                TempShowSuggestion = false;
                CompletionOn = isFullscreenMode();
            }
        }
        int i4 = getCurrentInputEditorInfo().imeOptions & 1073742079;
        if (i4 != 2 && i4 != 3 && i4 != 4 && i4 != 5 && i4 != 6) {
            try {
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && TempShowSuggestion && Constants.selectedLangName == 1) {
                    CapsOnOffFlag = false;
                    CapsLock = true;
                    customKeyboardView.setShifted(true);
                    customKeyboardView.invalidate();
                    customKeyboardView.invalidateAllKeys();
                }
            } catch (Exception unused) {
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && TempShowSuggestion) {
                    CapsOnOffFlag = false;
                    CapsLock = false;
                    customKeyboardView.setShifted(false);
                    customKeyboardView.invalidate();
                    customKeyboardView.invalidateAllKeys();
                }
            }
        }
    }

    public boolean onEvaluateInputViewShown() {
        try {
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                if (views != null) {
                    if (getResources().getConfiguration().orientation == 1) {
                        Glide.with(this).clear(ImgGif);
                        Glide.with(this).asGif().load(new File(getApplicationContext().getFilesDir(), "Gif_save.gif").getAbsolutePath()).placeholder(R.drawable.rain).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ImgGif);
                    } else {
                        RlSubContents.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
                    }
                }
            } else if (views != null) {
                if (getResources().getConfiguration().orientation == 1) {
                    Bitmap decodeFile = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/photo_save.jpeg");
                    if (decodeFile != null) {
                        RlSubContents.setBackgroundDrawable(new BitmapDrawable(decodeFile));
                    }
                } else {
                    RlSubContents.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
                }
            }
        } catch (Exception unused) {
        }
        return super.onEvaluateInputViewShown();
    }

    public String method(String str) {
        return (str == null || str.length() <= 0 || str.charAt(str.length() + -1) != 'x') ? str : str.substring(0, str.length() - 1);
    }

    @SuppressLint("ResourceType")
    public void onKey(int code, int[] iArr) {
        final int codes = code;
        System.out.println("----- - - -i2i2i2i2 : " + codes);
        final int[] iArr2 = iArr;
        final InputConnection currentInputConnection = getCurrentInputConnection();
        if (Constants.ChangeLanguage == 1) {
            LlLanguageChange.setVisibility(View.VISIBLE);
        } else {
            LlLanguageChange.setVisibility(View.GONE);
        }
        customKeyboardView.setVisibility(View.VISIBLE);
        if (LlHeader.getVisibility() == View.VISIBLE) {
            LlHeader.setVisibility(View.GONE);
        }
        if (Constants.DeleteValFlag) {
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                CapsLock = true;
                CapsOnOffFlag = false;
                customKeyboardView.setShifted(true);
            }
            customKeyboardView.invalidateAllKeys();
            Constants.DeleteValFlag = false;
        } else if (codes == -978903) {
            Constants.wordExist = true;
            CapsOn();
            CapsLock = true;
            CapsOnOffFlag = true;
            NewCapital = true;
        } else if (codes == -6003) {
            Constants.wordExist = true;
            CheckFlag = true;
            MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, AllNumericQwerty[Constants.ChangeLanguage], keyboardHeight, 1);
            keypadDataView = myKeypadDataView;
            customKeyboardView.setKeyboard(myKeypadDataView);
            for (Keyboard.Key key : keypadDataView.getKeys()) {
                int parseInt = Integer.parseInt("" + key.codes[0]);
                if (parseInt == -978903) {
                    key.icon = DrawableShiftOff;
                } else if (parseInt == -2830) {
                    key.label = key.label;
                } else if (parseInt == -1) {
                    key.icon = DrawableShiftOff;
                } else if (parseInt == 32) {
                    key.icon = DrawableSpace;
                } else if (parseInt == -6003) {
                    key.label = key.label;
                } else if (parseInt == -6002) {
                    key.label = key.label;
                } else if (parseInt == -5) {
                    key.icon = DrawableDelete;
                } else if (parseInt == -4) {
                    key.icon = DrawableEnter;
                }
            }
            customKeyboardView.invalidateAllKeys();
            CapsLock = false;
        } else if (codes == -5000) {
            if (customKeyboardView != null) {
                customKeyboardView.DismissLanguagePopup();
                customKeyboardView.DismissPreviewPopup();
            }
            if (!ManageClick) {
                Constants.wordExist = true;
                if (LlMainMenu.getVisibility() == View.GONE) {
                    LlHintWord.setVisibility(View.GONE);
                    resultList = null;
                    resultList = new ArrayList<>();
                    HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, resultList));
                    LlMainMenu.setVisibility(View.VISIBLE);
                }
                LlHeader.setVisibility(View.VISIBLE);
                Constants.temp_flag = 1;
                initEmojiAdapter();
                customKeyboardView.setVisibility(View.GONE);
                RlOtherContent.setVisibility(View.VISIBLE);
                RlMainContents.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), 17432576));
                RlOtherContent.removeAllViews();
                RlOtherContent.addView(GridEmoji);
            } else {
                RlOtherContent.removeAllViews();
                RlOtherContent.setVisibility(View.GONE);
                setKeyboardData();
            }
            ManageClick = !ManageClick;
        } else if (codes == -1763) {
            Constants.wordExist = true;
            CheckFlag = true;
            MyKeypadDataView myKeypadDataView2 = new MyKeypadDataView(this, R.xml.numeric_shift_querty, keyboardHeight, 1);
            keypadDataView = myKeypadDataView2;
            customKeyboardView.setKeyboard(myKeypadDataView2);
            for (Keyboard.Key key2 : keypadDataView.getKeys()) {
                int parseInt2 = Integer.parseInt("" + key2.codes[0]);
                if (parseInt2 == -978903) {
                    key2.icon = DrawableShiftOff;
                } else if (parseInt2 == -2830) {
                    key2.label = key2.label;
                } else if (parseInt2 == -1) {
                    key2.icon = DrawableShiftOff;
                } else if (parseInt2 == 32) {
                    key2.icon = DrawableSpace;
                } else if (parseInt2 == -6003) {
                    key2.label = key2.label;
                } else if (parseInt2 == -6002) {
                    key2.label = key2.label;
                } else if (parseInt2 == -5) {
                    key2.icon = DrawableDelete;
                } else if (parseInt2 == -4) {
                    key2.icon = DrawableEnter;
                }
            }
            customKeyboardView.invalidateAllKeys();
            CapsLock = false;
        } else if (codes == -1) {
            Constants.wordExist = true;
            NewCapital = false;
            CapsLock = !CapsLock;
            if (Constants.FlagChangeLanguage == 1) {
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    if (CapsLock) {
                        CapsOnOffFlag = false;
                        customKeyboardView.invalidateAllKeys();
                    } else {
                        CapsOnOffFlag = true;
                        customKeyboardView.invalidateAllKeys();
                    }
                    customKeyboardView.setShifted(CapsLock);
                } else if (CapsLock) {
                    SelectQuertyShiftOn();
                    CapsOnOffFlag = false;
                    customKeyboardView.invalidateAllKeys();
                } else {
                    SelectQuertyShiftOff();
                    CapsOnOffFlag = true;
                    customKeyboardView.invalidateAllKeys();
                }
            } else if (CapsLock) {
                CapsOn();
                CapsLock = true;
                CapsOnOffFlag = true;
                NewCapital = true;
                customKeyboardView.invalidateAllKeys();
            } else {
                SelectQuertyShiftOff();
                CapsOnOffFlag = true;
                customKeyboardView.invalidateAllKeys();
            }
        } else if (codes == 66) {
        } else {
            if (codes == -2831) {
                Constants.wordExist = true;
                setKeyboardData();
            } else if (codes == -2830) {
                Constants.wordExist = true;
                CheckFlag = false;
                MyKeypadDataView myKeypadDataView3 = new MyKeypadDataView(this, DefaultQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
                keypadDataView = myKeypadDataView3;
                customKeyboardView.setKeyboard(myKeypadDataView3);
                for (Keyboard.Key key3 : keypadDataView.getKeys()) {
                    int parseInt3 = Integer.parseInt("" + key3.codes[0]);
                    if (parseInt3 == -978903) {
                        key3.icon = DrawableShiftOff;
                    } else if (parseInt3 == -2830) {
                        key3.label = key3.label;
                    } else if (parseInt3 == -1) {
                        key3.icon = DrawableShiftOff;
                    } else if (parseInt3 == 32) {
                        key3.icon = DrawableSpace;
                    } else if (parseInt3 == -6003) {
                        key3.label = key3.label;
                    } else if (parseInt3 == -6002) {
                        key3.label = key3.label;
                    } else if (parseInt3 == -5) {
                        key3.icon = DrawableDelete;
                    } else if (parseInt3 == -4) {
                        key3.icon = DrawableEnter;
                    }
                }
                customKeyboardView.invalidateAllKeys();
                try {
                    if (NewCapital) {
                        CapsOn();
                        CapsOnOffFlag = true;
                        CapsLock = true;
                    }
                    char charAt = getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
                    if (Character.isLetter(charAt) && Character.isUpperCase(charAt) && !NewCapital && Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                        CapsOnOffFlag = false;
                        CapsLock = false;
                        onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                    }
                } catch (Exception unused) {
                    if (!NewCapital && Constants.ChangeLanguage == 1) {
                        CapsLock = true;
                        CapsOnOffFlag = false;
                        SelectQuertyShiftOn();
                    }
                }
            } else if (codes == -5) {
                CustomKeyboardView customKeyboardView2 = customKeyboardView;
                if (customKeyboardView2 != null) {
                    customKeyboardView2.DismissPreviewPopup();
                }
                if (LlMainMenu.getVisibility() == View.GONE) {
                    LlHintWord.setVisibility(View.GONE);
                    resultList = null;
                    resultList = new ArrayList<>();
                    HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, resultList));
                    LlMainMenu.setVisibility(View.VISIBLE);
                }
                Constants.wordExist = true;
                try {
                    char charAt2 = currentInputConnection.getTextBeforeCursor(1, 0).charAt(0);
                    if (Character.isLetter(charAt2)) {
                        Log.d("main", "isLetter");
                    } else if (Character.isISOControl(charAt2)) {
                        Log.d("main", "isIsoCHar");
                    } else if (Character.isDigit(charAt2)) {
                        Log.d("main", "isDigit");
                    } else if (Character.isHighSurrogate(charAt2)) {
                        Log.d("main", "isHigh Surrigate");
                    } else if (Character.isDefined(charAt2)) {
                        Log.d("main", "isDefined");
                        if (Character.isHighSurrogate(currentInputConnection.getTextBeforeCursor(2, 0).charAt(0))) {
                            Log.d("main", "isEmoji");
                            currentInputConnection.deleteSurroundingText(2, 0);
                            return;
                        }
                    }
                    currentInputConnection.deleteSurroundingText(1, 0);
                    int i3 = getCurrentInputEditorInfo().imeOptions & 1073742079;
                    if (i3 != 2 && i3 != 3 && i3 != 4 && i3 != 5 && i3 != 6 && !NewCapital && !CheckFlag && Constants.ChangeLanguage == 1) {
                        deleteText(currentInputConnection.getExtractedText(new ExtractedTextRequest(), 0).text.toString(), charAt2);
                    }
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            showSuggestion(currentInputConnection, codes, iArr2);
                        }
                    }, 500);
                } catch (Exception e) {
                    Constants.DeleteValFlag = false;
                    int i4 = getCurrentInputEditorInfo().imeOptions & 1073742079;
                    if (i4 != 2 && i4 != 3 && i4 != 4 && i4 != 5 && i4 != 6) {
                        Log.d("main", "Exception deleting no char " + e);
                        if (Constants.FlagChangeLanguage != 0) {
                            CapsOnOffFlag = false;
                            customKeyboardView.setShifted(true);
                            customKeyboardView.invalidate();
                            customKeyboardView.invalidateAllKeys();
                            TempShiftOnOff = true;
                        }
                    }
                }

            } else if (codes == -4) {
                Constants.wordExist = true;
                if (LlMainMenu.getVisibility() == View.GONE) {
                    LlHintWord.setVisibility(View.GONE);
                    resultList = null;
                    resultList = new ArrayList<>();
                    HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, resultList));
                    LlMainMenu.setVisibility(View.VISIBLE);
                }
                int i5 = getCurrentInputEditorInfo().imeOptions & 1073742079;
                if (i5 == 2) {
                    currentInputConnection.performEditorAction(2);
                } else if (i5 == 3) {
                    currentInputConnection.performEditorAction(3);
                } else if (i5 == 4) {
                    currentInputConnection.performEditorAction(4);
                } else if (i5 == 5) {
                    currentInputConnection.performEditorAction(5);
                } else if (i5 != 6) {
                    currentInputConnection.sendKeyEvent(new KeyEvent(0, 66));
                    if (!NewCapital && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                        CapsOnOffFlag = false;
                        CapsLock = true;
                        customKeyboardView.setShifted(true);
                        customKeyboardView.invalidate();
                        customKeyboardView.invalidateAllKeys();
                    }
                } else {
                    currentInputConnection.performEditorAction(6);
                }
            } else if (codes != -97886) {
                char c = (char) codes;
                if (Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    if (Counter == 0 && codes == 32) {
                        String str = "" + currentInputConnection.getTextBeforeCursor(Integer.MAX_VALUE, 0);
                        FinalPatternWord = str;
                        String substring = str.substring(str.lastIndexOf(" ") + 1);
                        StrLastWord = substring;
                        currentInputConnection.deleteSurroundingText(substring.length() + 1, 0);
                        new AsyncTask<Void, Void, Void>() {
                            public void onPreExecute() {
                            }

                            public void onProgressUpdate(Void... voidArr) {
                            }

                            public Void doInBackground(Void... voidArr) {
                                try {
                                    urlConnection = null;
                                    URL url = new URL("http://www.google.com/inputtools/request");
                                    UrlString = "text=" + StrLastWord + "&ime=transliteration_en_hi";
                                    urlConnection = (HttpURLConnection) url.openConnection();
                                    urlConnection.setRequestMethod("GET");
                                    urlConnection.setConnectTimeout(5000);
                                    urlConnection.setReadTimeout(5000);
                                    urlConnection.setDoOutput(true);
                                    urlConnection.getOutputStream().write(UrlString.getBytes(StandardCharsets.UTF_8));
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
                                    builder = new StringBuilder();
                                    while (true) {
                                        int read = bufferedReader.read();
                                        if (read == -1) {
                                            break;
                                        }
                                        builder.append((char) read);
                                    }
                                } catch (Exception unused) {
                                }
                                return null;
                            }

                            public void onPostExecute(Void voidR) {
                                Pattern compile = Pattern.compile("\"([^\"]*)\"");
                                Matcher matcher = compile.matcher("" + builder);
                                while (matcher.find()) {
                                    FinalPatternString.add(matcher.group(1));
                                }
                                try {
                                    Constants.dictionaryword = FinalPatternString.get(2);
                                    InputConnection inputConnection = currentInputConnection;
                                    inputConnection.commitText(FinalPatternString.get(2) + " ", 1);
                                    StrLastWord = "";
                                    FinalPatternString.clear();
                                } catch (Exception unused) {
                                    InputConnection inputConnection2 = currentInputConnection;
                                    inputConnection2.commitText(StrLastWord + " ", 1);
                                    StrLastWord = "";
                                    FinalPatternString.clear();
                                }
                            }
                        }.execute();
                        Counter++;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                showSuggestion(currentInputConnection, codes, iArr2);
                            }
                        }, 500);
                    } else {
                        Counter = 0;
                    }
                }
                if (!Character.isLetter(c) || !CapsLock) {
                    currentInputConnection.commitText(String.valueOf(c), 1);
                    if (codes == 46 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && TempShowSuggestion && Constants.FlagChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                        CapsLock = true;
                        CapsOnOffFlag = false;
                        customKeyboardView.setShifted(true);
                    }
                    if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SUGGESTION, true) && TempShowSuggestion && !Constants.PreviewViewisOpen && !CheckLanguage) {
                        showSuggestion(currentInputConnection, codes, iArr2);
                    }
                    if (codes >= 97 && codes <= 122) {
                        CapsOnOffFlag = true;
                        return;
                    }
                    return;
                }
                currentInputConnection.commitText(String.valueOf(Character.toUpperCase(c)), 1);
                if (!CapsOnOffFlag && Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    CapsOnOffFlag = true;
                    onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SUGGESTION, true) && TempShowSuggestion && !Constants.PreviewViewisOpen && !CheckLanguage) {
                    showSuggestion(currentInputConnection, codes, iArr2);
                }
            }
        }
    }

    public void showSuggestion(InputConnection inputConnection, int i, int[] iArr) {
        try {
            StrWord = "";
            if (CheckLanguage) {
                TextDatasChar = Constants.dictionaryword;
            } else {
                TextDatasChar = inputConnection.getTextBeforeCursor(Integer.MAX_VALUE, 0);
            }
            int length = TextDatasChar.toString().length();
            if (length >= 1) {
                boolean z = false;
                while (true) {
                    if (length != 0) {
                        int i2 = length - 1;
                        char charAt = TextDatasChar.toString().charAt(i2);
                        if (charAt == 10 || charAt == ',') {
                            break;
                        } else if (charAt == '.') {
                            break;
                        } else if (charAt != ' ') {
                            StrWord += TextDatasChar.toString().charAt(i2);
                            if (Constants.SuggestedView) {
                                LlHintWord.setVisibility(View.VISIBLE);
                                LlMainMenu.setVisibility(View.GONE);
                                z = true;
                            }
                            length--;
                        } else {
                            Constants.wordExist = true;
                            if (Constants.SuggestedView && !z) {
                                LlHintWord.setVisibility(View.GONE);
                                resultList = null;
                                resultList = new ArrayList<>();
                                HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, resultList));
                                LlMainMenu.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        break;
                    }
                }
                Constants.wordExist = true;
                if (Constants.SuggestedView && !z) {
                    LlHintWord.setVisibility(View.GONE);
                    resultList = null;
                    resultList = new ArrayList<>();
                    HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, resultList));
                    LlMainMenu.setVisibility(View.VISIBLE);
                }
                StrWord = new StringBuilder(StrWord).reverse().toString();
                myAsyncTask myasynctask = new myAsyncTask();
                if (Constants.isUpHoneycombVersion) {
                    myasynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    myasynctask.execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getWord(String str) {
        if (!str.equals("")) {
            ArrayList<String> suggestion = Constants.getSuggestion(str);
            resultList = suggestion;
            if (suggestion.size() >= 1) {
                Collections.sort(resultList, new Comparator<String>() {
                    public int compare(String str, String str2) {
                        return str.compareToIgnoreCase(str2);
                    }
                });
                HListView.setAdapter(Constants.setSuggestionAdapter(this, resultList, selectedTheme, HListView.getWidth()));
            } else if (resultList.size() <= 0) {
                resultList = null;
                ArrayList<String> arrayList = new ArrayList<>();
                resultList = arrayList;
                arrayList.add(str);
                resultList.add("Touch to add");
                HListView.setAdapter(Constants.setSuggestionAdapter(this, resultList, selectedTheme, HListView.getWidth()));
            }
        } else {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add("");
            HListView.setAdapter(Constants.setSuggestionAdapter(this, arrayList2, selectedTheme, HListView.getWidth()));
        }
    }

    @SuppressLint("WrongConstant")
    private void playClick(int i) {
        int i2 = i != -5 ? i != -4 ? i != 32 ? 5 : 6 : 8 : 7;
        if (((double) new MySharePref(getApplicationContext()).getPrefFloat(MySharePref.SOUND_PROGRESS_FLOAT, 0.3f)) != 0.0d) {
            audioManager.playSoundEffect(i2, new MySharePref(getApplicationContext()).getPrefFloat(MySharePref.SOUND_PROGRESS_FLOAT, 0.3f));
        }
    }

    public void onFinishInput() {
        Log.d("main", "finish input");
        if (customKeyboardView != null) {
            customKeyboardView.DismissLanguagePopup();
        }
        super.onFinishInput();
    }

    public void onPress(int i) {
        customKeyboardView.setPreviewEnabled(false);
        customKeyboardView.DismissLanguagePopup();
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.POPUP, true)) {
            customKeyboardView.onPressKey(i);
        }
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.VIBRATION, false)) {
            vibrate();
        }
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SOUND_ENABLE, true)) {
            playClick(i);
        }
    }

    public void vibrate() {
        long j = KeypressVibrationDuration;
        if (j < 0) {
            if (customKeyboardView != null) {
                customKeyboardView.performHapticFeedback(3, 2);
                return;
            }
            return;
        }
        SetVibrateComp setVibrateComp = vibrateCompact;
        if (setVibrateComp != null) {
            setVibrateComp.vibrate(j);
        }
    }

    public void onRelease(int i) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                customKeyboardView.DismissPreviewPopup();
            }
        }, 100);
    }

    public void initEmojiAdapter() {
        RlOtherContent.removeAllViews();
        IconsList = null;
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 189; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("" + i);
        }
        GridEmoji = null;
        GridView gridView = new GridView(this);
        GridEmoji = gridView;
        gridView.setNumColumns(8);
        GridEmoji.setGravity(17);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, customKeyboardView.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), People.DATA, 0));
    }

    public void clickeventfg(int i) {
        getCurrentInputConnection().commitText(Sport.DATA[i].getEmoji(), 1);
    }

    public void clickevent(int i) {
        getCurrentInputConnection().commitText(People.DATA[i].getEmoji(), 1);
    }

    public void clickeventfl(int i) {
        getCurrentInputConnection().commitText(Nature.DATA[i].getEmoji(), 1);
    }

    public void getFlower() {
        RlOtherContent.removeAllViews();
        IconsList = null;
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 116; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("f" + i);
        }
        GridEmoji = null;
        GridView gridView = new GridView(this);
        GridEmoji = gridView;
        gridView.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, customKeyboardView.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Nature.DATA, 1));
        RlOtherContent.addView(GridEmoji);
    }

    public void getElectronics() {
        RlOtherContent.removeAllViews();
        IconsList = null;
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 116; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("f" + i);
        }
        GridEmoji = null;
        GridView gridView = new GridView(this);
        GridEmoji = gridView;
        gridView.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, customKeyboardView.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Electr.DATA, 6));
        RlOtherContent.addView(GridEmoji);
    }

    public void getBell() {
        RlOtherContent.removeAllViews();
        IconsList = null;
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 229; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("b" + i);
        }
        GridEmoji = null;
        GridView gridView = new GridView(this);
        GridEmoji = gridView;
        gridView.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, customKeyboardView.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Sport.DATA, 2));
        RlOtherContent.addView(GridEmoji);
    }

    public void getcar() {
        RlOtherContent.removeAllViews();
        IconsList = null;
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 98; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("c" + i);
        }
        GridEmoji = null;
        GridView gridView = new GridView(this);
        GridEmoji = gridView;
        gridView.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, customKeyboardView.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Cars.DATA, 3));
        RlOtherContent.addView(GridEmoji);
    }

    public void getFood() {
        RlOtherContent.removeAllViews();
        IconsList = null;
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 98; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("c" + i);
        }
        GridEmoji = null;
        GridView gridView = new GridView(this);
        GridEmoji = gridView;
        gridView.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, customKeyboardView.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Food.DATA, 5));
        RlOtherContent.addView(GridEmoji);
    }

    public void getSymbols() {
        RlOtherContent.removeAllViews();
        IconsList = null;
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 206; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("s" + i);
        }
        GridEmoji = null;
        GridView gridView = new GridView(this);
        GridEmoji = gridView;
        gridView.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, customKeyboardView.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Symbols.DATA, 4));
        RlOtherContent.addView(GridEmoji);
    }

    public void deleteemoji() {
        try {
            char charAt = getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
            if (Character.isLetter(charAt)) {
                Log.d("main", "isLetter");
            } else if (Character.isISOControl(charAt)) {
                Log.d("main", "isIsoCHar");
            } else if (Character.isDigit(charAt)) {
                Log.d("main", "isDigit");
            } else if (Character.isHighSurrogate(charAt)) {
                Log.d("main", "isHigh Surrigate");
            } else if (Character.isDefined(charAt)) {
                Log.d("main", "isDefined");
                if (Character.isHighSurrogate(getCurrentInputConnection().getTextBeforeCursor(2, 0).charAt(0))) {
                    Log.d("main", "isEmoji");
                    getCurrentInputConnection().deleteSurroundingText(2, 0);
                    return;
                }
            }
            getCurrentInputConnection().deleteSurroundingText(1, 0);
        } catch (Exception e) {
            Log.d("main", "Exception deleting no char " + e);
        }
    }

    public void setTabBg(int i, int i2) {
        Iterator<ImageButton> it2 = BtnArray.iterator();
        while (it2.hasNext()) {
            ImageButton next = it2.next();
            int parseInt = Integer.parseInt((String) next.getTag());
            if (parseInt == i) {
                next.setBackgroundResource(EmojiPressInts[i]);
            } else {
                next.setBackgroundResource(EmojiUnpressInts[parseInt]);
            }
        }
    }

    private void initArrayList(View view) {
        setHintString();
        BtnArray = null;
        ArrayList<ImageButton> arrayList = new ArrayList<>();
        BtnArray = arrayList;
        arrayList.add(view.findViewById(R.id.emojis_tab_1_people));
        BtnArray.add(view.findViewById(R.id.emojis_tab_1_flower));
        BtnArray.add(view.findViewById(R.id.food_tab_1_emoji));
        BtnArray.add(view.findViewById(R.id.emojis_tab_1_bell));
        BtnArray.add(view.findViewById(R.id.emojis_tab_1_car));
        BtnArray.add(view.findViewById(R.id.emojis_tab_1_electronics));
        BtnArray.add(view.findViewById(R.id.emojis_tab_1_symbol));
        LlMainMenu = views.findViewById(R.id.main_patti);
        vibrateCompact = SetVibrateComp.getInstance(this);
        otherContents = (RelativeLayout) views.findViewById(R.id.otherContents);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        RlOtherContent = views.findViewById(R.id.otherContents);
        LlSpeak = views.findViewById(R.id.btnmic);
        views.findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    intent.addFlags(335577088);
                    intent.putExtra("backflg", true);
                    startActivity(intent);
                } catch (Exception unused) {
                }
            }
        });
        LlSpeak.setOnClickListener(SpeakDialog);
    }

    public void clickeventcar(int i) {
        getCurrentInputConnection().commitText(Cars.DATA[i].getEmoji(), 1);
    }

    public void clickeventsymbol(int i) {
        getCurrentInputConnection().commitText(Symbols.DATA[i].getEmoji(), 1);
    }

    public void clickeventfood(int i) {
        getCurrentInputConnection().commitText(Food.DATA[i].getEmoji(), 1);
    }

    public void clickeventElectronics(int i) {
        getCurrentInputConnection().commitText(Electr.DATA[i].getEmoji(), 1);
    }

    public void SetKeyBoardLayout1() {
        NewCapital = false;
        onKey(Constants.CODE_ALPHABETS, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
        LlHeader.setVisibility(View.GONE);
        customKeyboardView.setVisibility(View.VISIBLE);
        if (!NewCapital && Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
            CapsOnOffFlag = false;
            CapsLock = false;
            onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
        }
    }

    private void SelectQuery() {
        keypadDataView = new MyKeypadDataView(this, DefaultQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
    }

    private void SelectQuertyShiftOn() {
        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, LayoutCapsOffQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
        keypadDataView = myKeypadDataView;
        customKeyboardView.setKeyboard(myKeypadDataView);
        for (Keyboard.Key key : keypadDataView.getKeys()) {
            int parseInt = Integer.parseInt("" + key.codes[0]);
            if (parseInt == -978903) {
                key.icon = DrawableShiftOn;
            } else if (parseInt == -2830) {
                key.label = key.label;
            } else if (parseInt == -1) {
                key.icon = DrawableShiftOn;
            } else if (parseInt == 32) {
                key.icon = DrawableSpace;
            } else if (parseInt == -6003) {
                key.label = key.label;
            } else if (parseInt == -6002) {
                key.label = key.label;
            } else if (parseInt == -5) {
                key.icon = DrawableDelete;
                key.repeatable = true;
            } else if (parseInt == -4) {
                key.icon = DrawableEnter;
            }
        }
        customKeyboardView.invalidateAllKeys();
    }

    private void SelectQuertyShiftOff() {
        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, DefaultQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
        keypadDataView = myKeypadDataView;
        customKeyboardView.setKeyboard(myKeypadDataView);
        for (Keyboard.Key key : keypadDataView.getKeys()) {
            int parseInt = Integer.parseInt("" + key.codes[0]);
            if (parseInt == -978903) {
                key.icon = DrawableShiftOff;
            } else if (parseInt == -2830) {
                key.label = key.label;
            } else if (parseInt == -1) {
                key.icon = DrawableShiftOff;
            } else if (parseInt == 32) {
                key.icon = DrawableSpace;
            } else if (parseInt == -6003) {
                key.label = key.label;
            } else if (parseInt == -6002) {
                key.label = key.label;
            } else if (parseInt == -5) {
                key.icon = DrawableDelete;
                key.repeatable = true;
            } else if (parseInt == -4) {
                key.icon = DrawableEnter;
            }
        }
        customKeyboardView.invalidateAllKeys();
    }

    public void CapsOn() {
        CapsLock = false;
        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, LayoutCapsOnQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
        keypadDataView = myKeypadDataView;
        customKeyboardView.setKeyboard(myKeypadDataView);
        for (Keyboard.Key key : keypadDataView.getKeys()) {
            int parseInt = Integer.parseInt("" + key.codes[0]);
            if (parseInt == -978903) {
                key.icon = DrawableShiftOn;
            } else if (parseInt == -2830) {
                key.label = key.label;
            } else if (parseInt == -1) {
                key.icon = DrawableShiftOn;
            } else if (parseInt == 32) {
                key.icon = DrawableSpace;
            } else if (parseInt == -6003) {
                key.label = key.label;
            } else if (parseInt == -6002) {
                key.label = key.label;
            } else if (parseInt == -5) {
                key.icon = DrawableDelete;
            } else if (parseInt == -4) {
                key.icon = DrawableEnter;
            }
        }
        customKeyboardView.invalidateAllKeys();
    }

    private void deleteText(String str, char c) {
        if (Character.isLetter(c) && Character.isUpperCase(c)) {
            CapsLock = true;
            CapsOnOffFlag = false;
            customKeyboardView.setShifted(true);
            customKeyboardView.invalidateAllKeys();
        } else if ((Character.isLetter(c) && Character.isLowerCase(c)) || c == 10) {
            CapsLock = false;
            CapsOnOffFlag = true;
            customKeyboardView.setShifted(false);
            customKeyboardView.invalidateAllKeys();
        }
    }

    public void setKeyboardData() {
        if (Constants.temp_flag == 1) {
            Constants.temp_flag = 0;
            LlHeader.setVisibility(View.GONE);
            CapsOnOffFlag = false;
            CapsLock = true;
            customKeyboardView.setVisibility(View.VISIBLE);
            return;
        }
        try {
            LlHeader.setVisibility(View.GONE);
            customKeyboardView.InitActions();
            int i = Constants.FlagChangeLanguage;
            if (i == 0) {
                Constants.ChangeLanguage = 0;
                SetKeyBoardLayout1();
                Constants.selectedLanguageName = "Hindi";
            } else if (i == 1) {
                Constants.ChangeLanguage = 1;
                SetKeyBoardLayout1();
                Constants.selectedLanguageName = "English";
                if (new MySharePref(context).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    CapsLock = true;
                    CapsOnOffFlag = false;
                    customKeyboardView.setShifted(true);
                    customKeyboardView.invalidate();
                    customKeyboardView.invalidateAllKeys();
                    TempShiftOnOff = false;
                }
            }
            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
            if (Constants.isUpHoneycombVersion) {
                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                dictionaryTask.execute();
            }
            new MySharePref(getApplicationContext()).putPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, Constants.FlagChangeLanguage);
            new MySharePref(getApplicationContext()).putPrefString(MySharePref.LANGUAGE_NAME, Constants.selectedLanguageName.substring(0, 2).toLowerCase());
            new MySharePref(getApplicationContext()).putPrefString(MySharePref.DEFULT_LANGUAGE, Constants.SpeakLanguageName.substring(0, 2).toLowerCase());
            Constants.temp_flag = 0;
            customKeyboardView.InitActions();
        } catch (Exception unused) {
            unused.getMessage();
        }
    }

    private void setHintString() {
        LlHintWord = views.findViewById(R.id.hintword);
        HorizontalListView horizontalListView = views.findViewById(R.id.horizontalListView1);
        HListView = horizontalListView;
        HListView.setVisibility(View.VISIBLE);
        HListView.setOnItemClickListener(SuggectionListItemListern);
    }

    public void showdeletehint() {
        if (Constants.SuggestedView) {
            StrWord = "";
            CharSequence textBeforeCursor = getCurrentInputConnection().getTextBeforeCursor(999999, 0);
            String replaceAll = textBeforeCursor.toString().replaceAll("\\s", ",").replaceAll("[0-9]+", ",");
            String str = "";
            for (int i = 0; i < replaceAll.length(); i++) {
                char charAt = replaceAll.charAt(i);
                if (Character.isLetter(charAt)) {
                    str = str + charAt;
                } else {
                    str = str + ",";
                }
            }
            String[] split = str.split(",");
            if (split.length >= 1) {
                String trim = split[split.length - 1].trim();
                StrWord = trim;
                getWord(trim);
                Log.d("TextData", split[split.length - 1]);
                LlHintWord.setVisibility(View.VISIBLE);
                LlMainMenu.setVisibility(View.GONE);
            }
            if (textBeforeCursor.toString().length() <= 0) {
                StrWord = "";
                getWord("");
                LlHintWord.setVisibility(View.GONE);
                resultList = null;
                resultList = new ArrayList<>();
                HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, resultList));
                LlMainMenu.setVisibility(View.VISIBLE);
            }
        }
    }

    private void InitHeight() {
        Display defaultDisplay = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        if (getResources().getConfiguration().orientation == 1) {
            if (new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_POTRAIT_HEIGHT, -1) == -1) {
                keyboardHeight = displayMetrics.heightPixels / 3;
                SpeakLayHeight = (displayMetrics.heightPixels / 3) + (displayMetrics.heightPixels / 10);
            } else {
                keyboardHeight = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_POTRAIT_HEIGHT, -1);
                SpeakLayHeight = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_POTRAIT_HEIGHT, -1) + (displayMetrics.heightPixels / 10);
            }
        } else if (new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_LANDSCAP_HEIGHT, -1) == -1) {
            keyboardHeight = displayMetrics.heightPixels / 2;
            SpeakLayHeight = (displayMetrics.heightPixels / 2) + (displayMetrics.heightPixels / 10);
        } else {
            keyboardHeight = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_LANDSCAP_HEIGHT, -1);
            SpeakLayHeight = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_LANDSCAP_HEIGHT, -1) + (displayMetrics.heightPixels / 10);
        }
        TempHeight = Constants.DpToPx(getApplicationContext(), 41);
        TempShowSuggestion = true;
    }

    private void translayout_height(int i) {
        TempHeight1 = Constants.DpToPx(getApplicationContext(), 55);
        ViewGroup.LayoutParams layoutParams = RvBlackTransparent.getLayoutParams();
        layoutParams.height = i + TempHeight1;
        layoutParams.width = -1;
        RvBlackTransparent.setLayoutParams(layoutParams);
    }

    private void Giflayout_height(int i) {
        TempHeight1 = Constants.DpToPx(getApplicationContext(), 55);
        RelativeLayout relativeLayout = views.findViewById(R.id.relay_gif_lay);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = i + TempHeight1;
        layoutParams.width = -1;
        relativeLayout.setLayoutParams(layoutParams);
    }

    public void StickerClick(int i, String str) {
        String str2;
        StickerModel stickerModel = stickerModels.get(i);
        FolderPathOfSticker = str;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 20) {
            List<UsageStats> queryUsageStats = ((UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE)).queryUsageStats(0, 0, System.currentTimeMillis());
            if (queryUsageStats != null) {
                TreeMap treeMap = new TreeMap();
                for (UsageStats next : queryUsageStats) {
                    treeMap.put(Long.valueOf(next.getLastTimeUsed()), next);
                }
                str2 = ((UsageStats) treeMap.get(treeMap.lastKey())).getPackageName();
            } else {
                str2 = "";
            }
        } else {
            str2 = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        if (Constants.socialPackageNames.contains(str2)) {
            shareStickerToSocial(i, stickerModel.path, str2);
        } else if (str2.equals("com.android.mms")) {
            shareStickerImageTomsg(i, stickerModel.path);
        } else {
            shareStickerImage(i, stickerModel.path);
        }
    }

    @SuppressLint("WrongConstant")
    private void shareStickerImageTomsg(int i, String str) {
        try {
            File file = new File(getFilesDir().getAbsolutePath() + "/temp/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(FolderPathOfSticker + str + ".png");
            String str2 = getFilesDir().getAbsolutePath() + "/temp/" + str + ".png";
            File file3 = new File(str2);
            if (file3.exists()) {
                file3.delete();
            }
            Constants.copyFile(file2, file3);
            saveImageData(str2);
            file2 = file3;
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("image/png");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
            intent.setPackage("com.android.mms");
            intent.setFlags(268468224);
            startActivity(intent);
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), "Sorry! this app not perform this action", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("WrongConstant")
    private void shareStickerImage(int i, String str) {
        try {
            ArrayList arrayList = new ArrayList();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/png");
            File file = new File(getFilesDir().getAbsolutePath() + "/temp/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(FolderPathOfSticker + str + ".png");
            String str2 = getFilesDir().getAbsolutePath() + "/temp/" + str + ".png";
            File file3 = new File(str2);
            if (file3.exists()) {
                file3.delete();
            }
            Constants.copyFile(file2, file3);
            saveImageData(str2);
            file2 = file3;
            List<ResolveInfo> queryIntentActivities = getPackageManager().queryIntentActivities(intent, 0);
            if (!queryIntentActivities.isEmpty()) {
                for (ResolveInfo next : queryIntentActivities) {
                    String str3 = next.activityInfo.packageName;
                    if (!Constants.socialPackageNames.contains(str3)) {
                        Intent intent2 = new Intent();
                        intent2.setComponent(new ComponentName(str3, next.activityInfo.name));
                        intent2.setAction("android.intent.action.SEND");
                        intent2.setType("image/png");
                        intent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
                        intent2.setPackage(str3);
                        intent2.setFlags(268468224);
                        arrayList.add(intent2);
                    }
                }
            }
            ArrayList arrayList2 = new ArrayList(new ArrayList(new HashSet(arrayList)));
            Intent createChooser = Intent.createChooser((Intent) arrayList2.remove(0), "Choose app to share");
            createChooser.setFlags(268468224);
            createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList2.toArray(new Parcelable[0]));
            startActivity(createChooser);
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), "Sorry! this app not perform this action", Toast.LENGTH_LONG).show();
        }
    }

    private void shareStickerToSocial(int i, String str, String str2) {
        try {
            File file = new File(getFilesDir().getAbsolutePath() + "/temp/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(FolderPathOfSticker + str + ".png");
            String str3 = getFilesDir().getAbsolutePath() + "/temp/" + str + ".png";
            File file3 = new File(str3);
            if (file3.exists()) {
                file3.delete();
            }
            Constants.copyFile(file2, new File(str3));
            saveImageData(str3);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/png");
            intent.setPackage(str2);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (stickerModels.get(i).type == 0) {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file3));
            } else {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file3));
            }
            startActivity(intent);
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), "Sorry! Sticker can't Share", Toast.LENGTH_LONG).show();
        }
    }

    private void saveImageData(String str) {
        Bitmap changeBackground = changeBackground(BitmapFactory.decodeFile(str, new BitmapFactory.Options()));
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
                    changeBackground.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                    fileOutputStream2.close();
                } catch (Exception e) {
                    exception = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = fileOutputStream;
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                exception = e3;
            }
        } catch (Throwable th2) {
            Throwable th = th2;
        }
    }

    private Bitmap changeBackground(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        return createBitmap;
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    public AlertDialog InitDialogInstallTranslate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.install_voice_translate));
        builder.setPositiveButton(getResources().getString(R.string.install_now), (dialogInterface, i) -> {
            try {
                getApplicationContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.googlequicksearchbox")));
            } catch (ActivityNotFoundException unused) {
                getApplicationContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox")));
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.Cancel), (dialogInterface, i) -> {
        });
        return builder.create();
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showToast(String str, int i) {
        if (toast != null) {
            toast.cancel();
        }
        if (str.equalsIgnoreCase("")) {
            toast.cancel();
            return;
        }
        Toast makeText = Toast.makeText(this, str, i);
        toast = makeText;
        makeText.setGravity(81, 0, 200);
        toast.show();
    }

    public void doTheDownAnimation(int level) {
        int i3 = Level - 100;
        Level = i3;
        DrawableImage.setLevel(i3);
        if (Level >= level) {
            HandlerDown.postDelayed(RunnableAnimateDown, 10);
            return;
        }
        HandlerDown.removeCallbacks(RunnableAnimateDown);
        FromLevel = level;
    }

    public void doTheUpAnimation(int level) {
        int i3 = Level + 100;
        Level = i3;
        DrawableImage.setLevel(i3);
        if (Level <= level) {
            UpHandler.postDelayed(RunnableAnimateUp, 10);
            return;
        }
        UpHandler.removeCallbacks(RunnableAnimateUp);
        FromLevel = level;
    }

    public AlertDialog dialogSettingGoogleApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Enable Micro Permission");
        builder.setTitle("SettingActivity Permissions");
        builder.setPositiveButton("Go To SettingActivity", (dialogInterface, i) -> {
            dialogInterface.cancel();
            goToGoogleSettings();
        });
        return builder.create();
    }

    public void goToGoogleSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:com.google.android.googlequicksearchbox"));
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void showProgressDialog() {
        hideProgressDialog();
        dialog.setCancelable(true);
    }

    public void hideProgressDialog() {
        ProgressDialog progressDialog2 = dialog;
        if (progressDialog2 != null) {
            if (progressDialog2.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }

    class myAsyncTask extends AsyncTask<Void, Void, Void> {
        myAsyncTask() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            ArrayList arrayList = new ArrayList();
            arrayList.add("");
            HListView.setAdapter(Constants.setSuggestionAdapter(getApplicationContext(), arrayList, selectedTheme, HListView.getWidth()));
        }

        public Void doInBackground(Void... voidArr) {
            try {
                if (StrWord.equals("")) {
                    return null;
                }
                resultList = Constants.getSuggestion(StrWord);
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        public void onPostExecute(Void r6) {
            try {
                if (!StrWord.equals("")) {
                    if (resultList.size() >= 1) {
                        Collections.sort(resultList, new Comparator<String>() { // from class: com.tech.lang.keyboard.hindikeyboard.HindiKeypad.myAsyncTask.1
                            @Override // java.util.Comparator
                            public int compare(String str, String str2) {
                                return str.compareToIgnoreCase(str2);
                            }
                        });
                        HashSet hashSet = new HashSet();
                        hashSet.addAll(resultList);
                        resultList.clear();
                        resultList.addAll(hashSet);
                        try {
                            if (resultList != null) {
                                Collections.sort(resultList, new MyComparator());
                            }
                        } catch (Exception unused) {
                        }
                        HListView.setAdapter(Constants.setSuggestionAdapter(getApplicationContext(), resultList, selectedTheme, HListView.getWidth()));
                    } else if (resultList.size() <= 0) {
                        resultList = null;
                        resultList = new ArrayList<>();
                        resultList.add(StrWord);
                        resultList.add("Touch to add");
                        HListView.setAdapter(Constants.setSuggestionAdapter(getApplicationContext(), resultList, selectedTheme, HListView.getWidth()));
                    }
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("");
                    HListView.setAdapter(Constants.setSuggestionAdapter(getApplicationContext(), arrayList, selectedTheme, HListView.getWidth()));
                }
            } catch (Exception unused2) {
            }
            super.onPostExecute(r6);
        }
    }

    public class MyComparator implements Comparator<String> {
        public MyComparator() {
        }

        public int compare(String str, String str2) {
            if (str.length() > str2.length()) {
                return 1;
            }
            if (str.length() < str2.length()) {
                return -1;
            }
            return str.compareTo(str2);
        }
    }

    class RecognitionListern implements RecognitionListener {
        public void onEvent(int i, Bundle bundle) {
        }

        public void onPartialResults(Bundle bundle) {
        }

        RecognitionListern() {
        }

        public void onReadyForSpeech(Bundle bundle) {
            int unused = SpeechErrorCode = 100;
            Log.d("speechtotext", "onReadyForSpeech");
            LlSpeakLay.setVisibility(View.VISIBLE);
            RvBottom.setVisibility(View.GONE);
            boolean unused2 = IsSpeech = true;
            if (Constants.SpeakLanguageName.contains("en")) {
                TxtSpeak.setText(getResources().getString(R.string.en_error_text10));
            } else {
                TxtSpeak.setText(getResources().getString(R.string.error_text10));
            }
        }

        public void onBeginningOfSpeech() {
            Log.d("speechtotext", "onBeginningOfSpeech");
        }

        public void onRmsChanged(float f) {
            int i;
            IsSpeech = true;
            Log.i("speechtotext", "onRmsChanged: " + f);
            if (f < 0.0f) {
                SpeechErrorCode = SpeechErrorCode - 1;
                if (SpeechErrorCode <= 0) {
                    onError(SpeechRecognizer.ERROR_CLIENT);
                }
            }
            if (f < 0.0f) {
                f = 3.0f;
            }
            if (f > 0.0f && ToLevel != (i = (int) ((f * 10000.0f) / 10.0f)) && i <= 10000) {
                if (i > 10000) {
                    i = ToLevel;
                }
                ToLevel = i;
                if (ToLevel > FromLevel) {
                    HandlerDown.removeCallbacks(RunnableAnimateDown);
                    int unused4 = FromLevel = ToLevel;
                    UpHandler.post(RunnableAnimateUp);
                    return;
                }
                UpHandler.removeCallbacks(RunnableAnimateUp);
                int unused5 = FromLevel = ToLevel;
                HandlerDown.post(RunnableAnimateDown);
            }
        }

        public void onBufferReceived(byte[] bArr) {
            Log.d("speechtotext", "onBufferReceiverd");
        }

        public void onEndOfSpeech() {
            LlSpeakLay.setVisibility(View.GONE);
            RvBottom.setVisibility(View.VISIBLE);
            if (IsSpeech) {
                progressBarTalk.setVisibility(View.VISIBLE);
            }
            Log.d("speechtotext", "onEndOfSpeech");
        }

        public void onError(int i) {
            if (IsSpeech) {
                SpeakToTextBtn.setVisibility(View.VISIBLE);
                progressBarTalk.setVisibility(View.GONE);
                if (i != 3 || Build.VERSION.SDK_INT < 23) {
                    String errorText = getErrorText(getApplicationContext(), i);
                    Log.d("speechtotext", "FAILED " + errorText);
                    showToast(errorText, 1);
                    boolean unused = IsSpeech = false;
                } else {
                    dialogSettingGoogleApp().show();
                }
                speechRecognizer.destroy();
                return;
            }
            speechRecognizer.startListening(IntentRecognizer);
            Log.d("speechtotext", "bị Repeat nè ..... ");
        }

        public void onResults(Bundle bundle) {
            Log.d("speechtotext", "onResults");
            boolean unused = IsSpeech = false;
            SpeakToTextBtn.setVisibility(View.VISIBLE);
            progressBarTalk.setVisibility(View.GONE);
            ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
            if (stringArrayList != null) {
                if (stringArrayList.size() == 1) {
                    mTranslateIn.setmText(stringArrayList.get(0));
                } else {
                    InputConnection currentInputConnection = getCurrentInputConnection();
                    currentInputConnection.commitText("" + stringArrayList.get(0), 0);
                    Context applicationContext = getApplicationContext();
                    Toast.makeText(applicationContext, "" + stringArrayList.get(0), Toast.LENGTH_LONG).setGravity(81, 0, 0);
                }
            }
            speechRecognizer.destroy();
        }
    }

    class AnimatedDownImg implements Runnable {
        AnimatedDownImg() {
        }

        public void run() {
            doTheDownAnimation(ToLevel);
        }
    }

    class AnimatedUpImg implements Runnable {
        AnimatedUpImg() {
        }

        public void run() {
            doTheUpAnimation(ToLevel);
        }
    }
}
