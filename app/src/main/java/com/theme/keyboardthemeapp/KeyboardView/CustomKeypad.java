package com.theme.keyboardthemeapp.KeyboardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
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
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
import com.theme.keyboardthemeapp.APPUtils.ConstantPreferences;
import com.theme.keyboardthemeapp.APPUtils.ConstantResource;
import com.theme.keyboardthemeapp.Task.DictionaryTask;
import com.theme.keyboardthemeapp.UI.Adapters.EmojiListAdapter;
import com.theme.keyboardthemeapp.APPUtils.HorizontalListView;
import com.theme.keyboardthemeapp.APPUtils.MyKeypadDataView;
import com.theme.keyboardthemeapp.APPUtils.RepeatButtonLisern;
import com.theme.keyboardthemeapp.APPUtils.SetVibrateComp;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.ModelClass.StickerModel;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Activities.SettingActivity;
import com.theme.keyboardthemeapp.UI.Activities.ThemeActivity;
import com.theme.keyboardthemeapp.Translate.TranslateIn;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

import hani.momanii.supernova_emoji_library.emoji.Cars;
import hani.momanii.supernova_emoji_library.emoji.Electr;
import hani.momanii.supernova_emoji_library.emoji.Food;
import hani.momanii.supernova_emoji_library.emoji.Nature;
import hani.momanii.supernova_emoji_library.emoji.People;
import hani.momanii.supernova_emoji_library.emoji.Sport;
import hani.momanii.supernova_emoji_library.emoji.Symbols;

public class CustomKeypad extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    public static TextView TxtCounter = null;
    public static boolean CapsLock = false;
    public static int[] ColorCodesInts = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    public static boolean CheckLanguage;
    public static InputMethodService service;
    public static TextView TxtSpeakMsg;
    public static int ToLevel = 0;

    public static String StrCountryName;
    public static String FolderPathOfSticker = "";
    public static Drawable DrawableLanguageOff;
    public static Drawable DrawableLanguageOn;
    public static LinearLayout LlLanguageView;
    public static Drawable DrawableSpeak;
    public static CountDownTimer CountDownTimer;
    public Runnable RunnableAnimateDown = new AnimatedDownImg();
    public Runnable RunnableAnimateUp = new AnimatedUpImg();
    public static ImageView ImgBlackView;
    public static RelativeLayout RlTranslateView;
    public static RelativeLayout RlBottomView;
    public static ArrayList<ImageButton> BtnArray = new ArrayList<>();
    public static LinearLayout LlSpeak;
    public static Button BtnLanguageType;
    public static int[] LayoutCapsOnQWERTY = {R.xml.layout_cap_on_hindi_default_querty2, R.xml.layout_cap_on_eng_default_querty0};
    public static boolean CapsOnOffFlag = false;
    public static int[] LayoutCapsOffQWERTY = {R.xml.layout_cap_hindi_default_querty2, R.xml.layout_cap_eng_default_querty0};
    public static boolean CheckFlag = false;
    public static ImageButton ImgSpeakPopupClose;
    public static ConstantResource constantResource;
    public static FrameLayout FlContentFrame;
    public static int[] DefaultQWERTY = {R.xml.layout_hindi_default_querty2, R.xml.layout_eng_default_querty0};
    public static Drawable DrawableDelete;
    public static int[] DeleteIconsInts = {R.drawable.ic_btn_back, R.drawable.ic_btn_back, R.drawable.ic_btn_back, R.drawable.ic_btn_back, R.drawable.ic_btn_back, R.drawable.ic_btn_back, R.drawable.ic_btn_back, R.drawable.ic_btn_back, R.drawable.ic_btn_back, R.drawable.ic_btn_back};
    public static Drawable DrawableDropArrow;
    public static Drawable DrawableEmoji;
    public static GridView GridEmoji = null;
    public static Drawable DrawableEnter;
    private final int[] EnterIconsInts = {R.drawable.ic_btn_enter, R.drawable.ic_btn_enter, R.drawable.ic_btn_enter, R.drawable.ic_btn_enter, R.drawable.ic_btn_enter, R.drawable.ic_btn_enter, R.drawable.ic_btn_enter, R.drawable.ic_btn_enter, R.drawable.ic_btn_enter, R.drawable.ic_btn_enter};
    public static int FromLevel = 0;
    public static RelativeLayout RlGifView;
    public static ImageView ImgGIf;
    public static LinearLayout RlHeaderView;
    public static LinearLayout LlHintWord;
    public static HorizontalListView HListView;
    public static ArrayList<String> IconsList = new ArrayList<>();
    public static ImageView ImgUnSpeech;
    public static boolean IsPopupView;
    public static boolean IsSpeechRecognizeAvaliable = false;
    public static boolean IsSpeech = false;
    public static MyKeypadDataView keypadDataView;
    public static int keyboardHeight = 0;
    public static CustomKeyboardView MainKeyboardVies;
    public static AudioManager audioManager;
    public static ConstantPreferences constantPreferences;
    public static boolean CompletionOn;
    public static Context context;
    public static Handler HandlerDown = new Handler();
    public static ClipDrawable DrawableImage;
    public static long KeypressVibrationDuration = -1;
    public static int Level = 0;
    public static TranslateIn TranslateIn;
    public static RecognitionListener recognitionListener;
    public static SpeechRecognizer speechRecognizer;
    public static Toast toast;
    public static Handler UpHandler = new Handler();
    public static SetVibrateComp vibrateCompact;
    public static LinearLayout LlMainMenu;
    public static boolean ManageClick = false;
    public static boolean NewCapital = false;
    public static RelativeLayout RlOtherContent;
    public static int[] DrawablePopUpInts = {R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg, R.drawable.ic_preview_bg};
    public static Drawable DrawablePopUp;
    public static ProgressBar ProgressTalk;
    public static ProgressDialog dialog;
    public static RelativeLayout RlContentsView;
    public static Intent IntentRecognizer;
    public static int[] EmojiPressInts = {R.drawable.ic_emoji_presedtheme0, R.drawable.ic_flower_presed0, R.drawable.ic_food_presed0, R.drawable.ic_sport_presed0, R.drawable.ic_car_presed0, R.drawable.ic_electronic_presed0, R.drawable.ic_sign_presed0};
    public static ArrayList<String> WordResultList = null;
    public static RelativeLayout RlMainContents;
    public static int selectedTheme = 0;
    public static int[] EmojiUnpressInts = {R.drawable.ic_emoji_unpresedtheme0, R.drawable.ic_flower_unpresed0, R.drawable.ic_food_unpresed0, R.drawable.ic_sport_unpresed0, R.drawable.ic_car_unpresed0, R.drawable.ic_electronic_unpresed0, R.drawable.ic_sign_unpresed0};
    public static Drawable DrawableSetting;
    public static Drawable DrawableShiftOff;
    public static int[] ShiftOffInts = {R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off, R.drawable.ic_btn_shift_off};
    public static Drawable DrawableShiftOn;
    public static int[] ShiftOnInts = {R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on, R.drawable.ic_btn_shift_on};
    public static Drawable DrawableSpace;
    public static int[] SpaceInts = {R.drawable.ic_btn_space, R.drawable.ic_btn_space, R.drawable.ic_btn_space, R.drawable.ic_btn_space, R.drawable.ic_btn_space, R.drawable.ic_btn_space, R.drawable.ic_btn_space, R.drawable.ic_btn_space, R.drawable.ic_btn_space, R.drawable.ic_btn_space};
    public static LinearLayout LLSpeackToText;
    public static int SpeakLayHeight = 0;
    public static ImageButton ImageSpeechIcons;
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

    private Messenger messenger;
    static final int MSG_RECOGNIZER_START_LISTENING = 1;
    static final int MSG_RECOGNIZER_CANCEL = 2;

    View.OnClickListener ChangeSpeakingLanguage = view -> {
        if (new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi").equals(getResources().getString(R.string.str_speak_lang_2))) {
            new MySharePref(getApplicationContext()).putPrefString(MySharePref.DEFULT_LANGUAGE, "en");
        } else {
            new MySharePref(getApplicationContext()).putPrefString(MySharePref.DEFULT_LANGUAGE, getResources().getString(R.string.str_speak_lang_2));
        }
        BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
        Constants.SpeakLanguageName = new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi");
    };

    View.OnClickListener CloseSpeackDialog = view -> {
        LLSpeackToText.setVisibility(View.GONE);
        RlBottomView.setVisibility(View.VISIBLE);
    };

    View.OnClickListener ChangeThemeListern = view -> {
        try {
            if (!Constants.PreviewActivityIsOpenOrNot) {
                Constants.WordExistOrNot = true;
                Intent intent = new Intent(getApplicationContext(), ThemeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } catch (Exception unused) {
        }
    };

    View.OnClickListener SpeakViewOpens = new View.OnClickListener() {
        public void onClick(View view) {
            LLSpeackToText.setLayoutParams(new FrameLayout.LayoutParams(-1, SpeakLayHeight));
            TxtSpeakMsg.setText("Tap to Speak!!");
            LLSpeackToText.setVisibility(View.VISIBLE);
            RlBottomView.setVisibility(View.GONE);
            if (CountDownTimer != null) {
                CountDownTimer.cancel();
            }
            CountDownTimer = new CountDownTimer(14000, 1000) {
                public void onTick(long j) {
                    TxtCounter.setText("00:" + (j / 1000));
                }

                public void onFinish() {
                    TxtCounter.setText("done!");
                    LLSpeackToText.setVisibility(View.GONE);
                    RlBottomView.setVisibility(View.VISIBLE);
                }
            }.start();
            if (!IsSpeechRecognizeAvaliable) {
                DialogInstallTranslate().show();
            } else if (!isOnline()) {
                showToastMsges(getResources().getString(R.string.str_No_internet), 1);
            } else if (Arrays.asList(constantResource.getNoVoice()).contains(constantResource.getCountriesIn()[TranslateIn.getmPosition()])) {
                showToastMsges("Opps! " + TranslateIn.getmLanguageName() + " language was not supported Speech to Text", 1);
            } else {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
                messenger = new Messenger(new IncomingHandler(CustomKeypad.this));
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        if (!vibrator.hasVibrator()) {
                            vibrator.vibrate(40);
                        }
                    }
                    SpeechErrorCode = 100;

                    IntentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplicationContext().getPackageName());
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 3000);
                    IntentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                        speechRecognizer.setRecognitionListener(recognitionListener);
                    }
                } catch (Exception e) {
                    showToastMsges(e.getMessage(), 1);
                }
            }
        }
    };

    View.OnTouchListener TouchSpeakView = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                try {
                    ImageSpeechIcons.setSelected(false);
                    ImageSpeechIcons.setBackgroundResource(R.drawable.ic_toggleselector);
                    Message message = Message.obtain(null, MSG_RECOGNIZER_CANCEL);
                    messenger.send(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ImageSpeechIcons.setSelected(true);
                ImageSpeechIcons.setBackgroundResource(R.drawable.ic_toggleselector);
                try {
                    Message message = Message.obtain(null, MSG_RECOGNIZER_START_LISTENING);
                    messenger.send(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        }
    };

    AdapterView.OnItemClickListener SuggestionListItemListern = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long j) {
            CharSequence sequence;
            String strAdd = (String) adapterView.getItemAtPosition(position);
            if (!StrWord.equals("")) {
                if (strAdd.equals("Touch to add")) {
                    if (!Constants.SuggestionWordsList.contains(StrWord.toLowerCase())) {
                        Constants.SuggestionWordsList.add(StrWord.toLowerCase());
                    }
                    Toast.makeText(getApplicationContext(), "Word Added Successfully", Toast.LENGTH_LONG).show();
                    StrWord = "";
                    getSuggestWord(StrWord);
                } else if (WordResultList.contains("Touch to add")) {
                    if (!Constants.SuggestionWordsList.contains(StrWord.toLowerCase())) {
                        Constants.SuggestionWordsList.add(StrWord.toLowerCase());
                    }
                    Toast.makeText(getApplicationContext(), "Word Added Successfully", Toast.LENGTH_LONG).show();
                    StrWord = "";
                    getSuggestWord(StrWord);
                } else {
                    CharSequence charSequence = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
                        charSequence = getCurrentInputConnection().getExtractedText(new ExtractedTextRequest(), 0).text;
                    }
                    if (charSequence.toString().contains(" ")) {
                        sequence = getCurrentInputConnection().getTextBeforeCursor(999999, 0);
                    } else {
                        sequence = "";
                    }
                    String s = sequence + "";
                    getCurrentInputConnection().deleteSurroundingText(charSequence.toString().length(), 0);
                    if (sequence.toString().contains(" ")) {
                        int lastIndexOf = sequence.toString().lastIndexOf(" ");
                        strAdd = sequence.toString().substring(0, lastIndexOf) + " " + strAdd;
                    }
                    getCurrentInputConnection().commitText(strAdd, 0);
                    StrWord = "";
                    getSuggestWord(StrWord);
                }
                LlHintWord.setVisibility(View.GONE);
                WordResultList = null;
                WordResultList = new ArrayList<>();
                HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, WordResultList));
                LlMainMenu.setVisibility(View.VISIBLE);
            }
        }
    };
    private int i;

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
        Constants.Error1 = applicationContext.getResources().getString(R.string.str_error_text1);
        Constants.Error2 = applicationContext.getResources().getString(R.string.str_error_text2);
        Constants.Error3 = applicationContext.getResources().getString(R.string.str_error_text3);
        Constants.Error4 = applicationContext.getResources().getString(R.string.str_error_text4);
        Constants.Error5 = applicationContext.getResources().getString(R.string.str_error_text5);
        Constants.Error6 = applicationContext.getResources().getString(R.string.str_error_text6);
        Constants.Error7 = applicationContext.getResources().getString(R.string.str_error_text7);
        Constants.Error8 = applicationContext.getResources().getString(R.string.str_error_text8);
        Constants.Error9 = applicationContext.getResources().getString(R.string.str_error_text9);

        Constants.En_Error1 = applicationContext.getResources().getString(R.string.str_en_error_text1);
        Constants.En_Error2 = applicationContext.getResources().getString(R.string.str_en_error_text2);
        Constants.En_Error3 = applicationContext.getResources().getString(R.string.str_en_error_text3);
        Constants.En_Error4 = applicationContext.getResources().getString(R.string.str_en_error_text4);
        Constants.En_Error5 = applicationContext.getResources().getString(R.string.str_en_error_text5);
        Constants.En_Error6 = applicationContext.getResources().getString(R.string.str_en_error_text6);
        Constants.En_Error7 = applicationContext.getResources().getString(R.string.str_en_error_text7);
        Constants.En_Error8 = applicationContext.getResources().getString(R.string.str_en_error_text8);
        Constants.En_Error9 = applicationContext.getResources().getString(R.string.str_en_error_text9);
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

    @Override
    public View onCreateInputView() {
        if (Build.VERSION.SDK_INT >= 11) {
            Constants.isUpHoneycombVersion = true;
        }
        context = getApplicationContext();
        DrawableEmoji = getResources().getDrawable(R.drawable.ic_emojipressxmlwhite);
        DrawableTheme = getResources().getDrawable(R.drawable.ic_themepressxmlwhite);
        DrawableStricker = getResources().getDrawable(R.drawable.ic_stickerpressxmlwhite);
        DrawableSetting = getResources().getDrawable(R.drawable.ic_settingpressxmlwhite);
        DrawableShiftOn = getResources().getDrawable(ShiftOnInts[0]);
        DrawableSpace = getResources().getDrawable(SpaceInts[0]);
        DrawableEnter = getResources().getDrawable(EnterIconsInts[0]);
        DrawableDelete = getResources().getDrawable(DeleteIconsInts[0]);
        DrawableLanguageOn = getResources().getDrawable(R.drawable.ic_enable);
        DrawableLanguageOff = getResources().getDrawable(R.drawable.ic_disable);
        DrawableSpeak = getResources().getDrawable(R.drawable.ic_mic_unpresed);
        DrawableDropArrow = getResources().getDrawable(R.drawable.ic_drop_down);
        DrawableShiftOff = getResources().getDrawable(ShiftOffInts[0]);
        DrawablePopUp = getResources().getDrawable(DrawablePopUpInts[0]);
        ManageClick = false;
        InitHeight();
        constantPreferences = new ConstantPreferences(this);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Constants.LangugaeArr = new ArrayList<>();
        Constants.LangugaeArr = Constants.getDefaultLanguageArray();
        Constants.FlagChangeLanguage = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, 0);
        Constants.ChangeLanguage = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, 0);
        Constants.SpeakLanguageName = new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi");
        recognitionListener = new RecognitionListern();
        constantResource = new ConstantResource(this);
        TempShowSuggestion = true;
        Constants.WordExistOrNot = true;
        CheckFlag = false;

        keypadDataView = new MyKeypadDataView(this, DefaultQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
        selectedTheme = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_THEME, 0);
        Constants.SelectTheme = selectedTheme;
        TextColorCode = ColorCodesInts[0];
        IsSpeechRecognizeAvaliable = SpeechRecognizer.isRecognitionAvailable(getBaseContext());
        if (((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_phonetic_keyboard), Toast.LENGTH_SHORT).show();
        }
        if (IsSpeechRecognizeAvaliable) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            messenger = new Messenger(new IncomingHandler(CustomKeypad.this));
        } else {
            DialogInstallTranslate().show();
        }
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

        switch (selectedTheme) {
            case 0:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                FlContentFrame = CustomKeypad.views.findViewById(R.id.FrameeViewContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                LinearLayout LlThemeView = CustomKeypad.views.findViewById(R.id.LlThemeView);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }

                LlLanguageView.setOnClickListener(view -> {
                    if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                        DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask.execute();
                        }

                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                        return;
                    }
                    DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                    if (Constants.isUpHoneycombVersion) {
                        dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        dictionaryTask2.execute();
                    }
                    new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                });
                LlThemeView.setOnClickListener(ChangeThemeListern);
                hideProgressDialog();
                CustomKeypad.StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, CustomKeypad.StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                CustomKeypad.ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                TransprentLayoutHeight(keyboardHeight);
                CustomKeypad.ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                CustomKeypad.ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                CustomKeypad.DrawableImage = (ClipDrawable) CustomKeypad.ImgUnSpeech.getDrawable();
                CustomKeypad.DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);

                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 1:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad1, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                CustomKeypad.views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                hideProgressDialog();
                CustomKeypad.StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, CustomKeypad.StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                CustomKeypad.ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                CustomKeypad.DrawableImage = (ClipDrawable) CustomKeypad.ImgUnSpeech.getDrawable();
                CustomKeypad.DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                CustomKeypad.ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                CustomKeypad.ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 2:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad2, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                CustomKeypad.views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                hideProgressDialog();
                CustomKeypad.StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, CustomKeypad.StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                CustomKeypad.ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                CustomKeypad.DrawableImage = (ClipDrawable) CustomKeypad.ImgUnSpeech.getDrawable();
                CustomKeypad.DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                CustomKeypad.ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                CustomKeypad.ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 3:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad3, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                CustomKeypad.views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                hideProgressDialog();
                CustomKeypad.StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, CustomKeypad.StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                CustomKeypad.ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                CustomKeypad.DrawableImage = (ClipDrawable) CustomKeypad.ImgUnSpeech.getDrawable();
                CustomKeypad.DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                CustomKeypad.ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                CustomKeypad.ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 4:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad4, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                CustomKeypad.views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                hideProgressDialog();
                CustomKeypad.StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, CustomKeypad.StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                CustomKeypad.ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                CustomKeypad.DrawableImage = (ClipDrawable) CustomKeypad.ImgUnSpeech.getDrawable();
                CustomKeypad.DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                CustomKeypad.ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                CustomKeypad.ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 5:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad5, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                CustomKeypad.views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                hideProgressDialog();
                CustomKeypad.StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, CustomKeypad.StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                CustomKeypad.ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                CustomKeypad.DrawableImage = (ClipDrawable) CustomKeypad.ImgUnSpeech.getDrawable();
                CustomKeypad.DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                CustomKeypad.ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                CustomKeypad.ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 6:
                CapsLock = false;
                views = getLayoutInflater().inflate(R.layout.layout_keypad6, null);
                RlHeaderView = views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                hideProgressDialog();
                CustomKeypad.StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, CustomKeypad.StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                DrawableImage = (ClipDrawable) ImgUnSpeech.getDrawable();
                DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(context).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 7:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad7, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                hideProgressDialog();
                StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                DrawableImage = (ClipDrawable) ImgUnSpeech.getDrawable();
                DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                CustomKeypad.ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                CustomKeypad.ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 8:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad8, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                hideProgressDialog();
                StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                CustomKeypad.ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                DrawableImage = (ClipDrawable) CustomKeypad.ImgUnSpeech.getDrawable();
                DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
            case 9:
                CapsLock = false;
                CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad9, null);
                RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
                RlMainContents = CustomKeypad.views.findViewById(R.id.RlMainContents);
                RlContentsView = CustomKeypad.views.findViewById(R.id.RlContentsView);
                MainKeyboardVies = CustomKeypad.views.findViewById(R.id.MainKeyboardVies);
                LlLanguageView = CustomKeypad.views.findViewById(R.id.LlLanguageView);
                if (Constants.ChangeLanguage == 1) {
                    LlLanguageView.setVisibility(View.VISIBLE);
                } else {
                    LlLanguageView.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                } else {
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                }
                LlLanguageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                            if (Constants.isUpHoneycombVersion) {
                                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                dictionaryTask.execute();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                            return;
                        }
                        DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                        if (Constants.isUpHoneycombVersion) {
                            dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            dictionaryTask2.execute();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
                    }
                });
                views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
                hideProgressDialog();
                CustomKeypad.StrCountryName = constantPreferences.getCountryNameIn();
                TranslateIn = new TranslateIn(this, CustomKeypad.StrCountryName);
                LLSpeackToText = CustomKeypad.views.findViewById(R.id.LLSpeackToText);
                RlBottomView = CustomKeypad.views.findViewById(R.id.RlBottomView);
                ImageSpeechIcons = CustomKeypad.views.findViewById(R.id.ImageSpeechIcons);
                BtnLanguageType = CustomKeypad.views.findViewById(R.id.BtnLanguageType);
                ImgSpeakPopupClose = CustomKeypad.views.findViewById(R.id.ImgSpeakPopupClose);
                TxtSpeakMsg = CustomKeypad.views.findViewById(R.id.TxtSpeakMsg);
                TxtCounter = CustomKeypad.views.findViewById(R.id.TxtCounter);
                ProgressTalk = CustomKeypad.views.findViewById(R.id.ProgressTalk);
                CustomKeypad.ImgUnSpeech = CustomKeypad.views.findViewById(R.id.ImgUnSpeech);
                CustomKeypad.DrawableImage = (ClipDrawable) CustomKeypad.ImgUnSpeech.getDrawable();
                CustomKeypad.DrawableImage.setLevel(0);
                ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
                ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
                BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
                BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
                RlTranslateView = CustomKeypad.views.findViewById(R.id.RlTranslateView);
                CustomKeypad.ImgBlackView = CustomKeypad.views.findViewById(R.id.ImgBlackView);
                CustomKeypad.ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                TransprentLayoutHeight(keyboardHeight);
                RlGifView = CustomKeypad.views.findViewById(R.id.RlGifView);
                ImgGIf = CustomKeypad.views.findViewById(R.id.ImgGIf);
                GifLayoutHeight(keyboardHeight);
                initArrayList(CustomKeypad.views);
                AllStickerClickEvent();
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    RlGifView.setVisibility(View.INVISIBLE);
                    SetKeyboardBackgroundImage();
                } else {
                    RlGifView.setVisibility(View.VISIBLE);
                    SetKeyboardGif();
                }
                break;
        }
        if (selectedTheme > 9) {
            CapsLock = false;
            CustomKeypad.views = getLayoutInflater().inflate(R.layout.layout_keypad, null);
            RlHeaderView = CustomKeypad.views.findViewById(R.id.RlHeaderView);
            RlMainContents = views.findViewById(R.id.RlMainContents);
            FlContentFrame = views.findViewById(R.id.FrameeViewContents);
            RlContentsView = views.findViewById(R.id.RlContentsView);
            MainKeyboardVies = views.findViewById(R.id.MainKeyboardVies);
            LlLanguageView = views.findViewById(R.id.LlLanguageView);
            if (Constants.ChangeLanguage == 1) {
                LlLanguageView.setVisibility(View.VISIBLE);
            } else {
                LlLanguageView.setVisibility(View.GONE);
            }
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
            } else {
                LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
            }
            LlLanguageView.setOnClickListener(view -> {
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                    if (Constants.isUpHoneycombVersion) {
                        dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        dictionaryTask.execute();
                    }
                    new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                    LlLanguageView.setBackgroundResource(R.drawable.ic_disable);
                    return;
                }
                DictionaryTask dictionaryTask2 = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
                if (Constants.isUpHoneycombVersion) {
                    dictionaryTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    dictionaryTask2.execute();
                }
                new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                LlLanguageView.setBackgroundResource(R.drawable.ic_enable);
            });
            views.findViewById(R.id.LlThemeView).setOnClickListener(ChangeThemeListern);
            hideProgressDialog();
            String str11 = constantPreferences.getCountryNameIn();
            StrCountryName = str11;
            TranslateIn = new TranslateIn(this, str11);
            LLSpeackToText = views.findViewById(R.id.LLSpeackToText);
            RlBottomView = views.findViewById(R.id.RlBottomView);
            ImageSpeechIcons = views.findViewById(R.id.ImageSpeechIcons);
            BtnLanguageType = views.findViewById(R.id.BtnLanguageType);
            ImgSpeakPopupClose = views.findViewById(R.id.ImgSpeakPopupClose);
            TxtSpeakMsg = views.findViewById(R.id.TxtSpeakMsg);
            TxtCounter = views.findViewById(R.id.TxtCounter);
            RlTranslateView = views.findViewById(R.id.RlTranslateView);
            ImgBlackView = views.findViewById(R.id.ImgBlackView);
            ImgBlackView.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
            TransprentLayoutHeight(keyboardHeight);
            ProgressTalk = views.findViewById(R.id.ProgressTalk);
            ImgUnSpeech = views.findViewById(R.id.ImgUnSpeech);
            DrawableImage = (ClipDrawable) ImgUnSpeech.getDrawable();
            DrawableImage.setLevel(0);
            ImageSpeechIcons.setOnTouchListener(TouchSpeakView);
            ImgSpeakPopupClose.setOnClickListener(CloseSpeackDialog);
            BtnLanguageType.setOnClickListener(ChangeSpeakingLanguage);
            BtnLanguageType.setText(new MySharePref(getApplicationContext()).getPrefString(MySharePref.DEFULT_LANGUAGE, "hi"));
            RlGifView = views.findViewById(R.id.RlGifView);
            ImgGIf = views.findViewById(R.id.ImgGIf);
            GifLayoutHeight(keyboardHeight);
            initArrayList(views);
            AllStickerClickEvent();
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                RlGifView.setVisibility(View.VISIBLE);
                SetKeyboardGif();
            } else {
                RlGifView.setVisibility(View.INVISIBLE);
                SetKeyboardBackgroundImage();
            }
        }
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SAVE_IMAGE, false)) {
            if (selectedTheme == 1) {
                RlMainContents.setBackgroundResource(R.drawable.ic_trans2);
            } else if (selectedTheme == 2) {
                RlMainContents.setBackgroundResource(R.drawable.ic_trans3);
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
        if (MainKeyboardVies != null) {
            MainKeyboardVies.DismissLanguagePopup();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
                BtnLanguageType.setTextColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)));
            }
        }, 500);

        MainKeyboardVies.setOnlineKeyboard(DrawablePopUp);
        MainKeyboardVies.setBackgroundDrawable(new BitmapDrawable());
        MainKeyboardVies.setKeyboard(keypadDataView);
        int Options = getCurrentInputEditorInfo().imeOptions & 1073742079;
        if (!(Options == 2 || Options == 3 || Options == 4 || Options == 5 || Options == 6)) {
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
        MainKeyboardVies.setOnKeyboardActionListener(this);
        MainKeyboardVies.invalidate();
        Constants.KeypedView = views;
        return views;
    }

    public static class IncomingHandler extends Handler {
        private WeakReference<CustomKeypad> mtarget;

        IncomingHandler(CustomKeypad target) {
            mtarget = new WeakReference<CustomKeypad>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            final CustomKeypad target = mtarget.get();
            switch (msg.what) {
                case MSG_RECOGNIZER_START_LISTENING:
                    target.speechRecognizer.startListening(target.IntentRecognizer);
                    break;

                case MSG_RECOGNIZER_CANCEL:
                    target.speechRecognizer.cancel();
                    break;
            }
        }
    }

    public void SetKeyboardBackgroundImage() {
        if (getResources().getConfiguration().orientation == 1) {
            Bitmap decodeFile = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/photo_save.jpeg");
            if (decodeFile != null) {
                RlMainContents.setBackgroundDrawable(new BitmapDrawable(decodeFile));
            } else {
                RlMainContents.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
            }
        }
    }

    public void SetKeyboardGif() {
        ImgGIf = views.findViewById(R.id.ImgGIf);
        Glide.with(this).asGif().load(new File(getApplicationContext().getFilesDir(), "Gif_save.gif").getAbsolutePath()).placeholder(R.drawable.ic_rain).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ImgGIf);
    }

    private void AllStickerClickEvent() {
        views.findViewById(R.id.LlEmojiView).setOnClickListener(view -> {
            onKey(Constants.CODE_EMOJI, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
            SetStickerTabBg(0);
            CapsLock = false;
        });
        views.findViewById(R.id.ImgDeleteEmoji).setOnTouchListener(new RepeatButtonLisern(400, 100, new View.OnClickListener() {
            public void onClick(View view) {
                DeleteSetEmoji();
            }
        }));
        views.findViewById(R.id.ImgFoodEmoji).setOnTouchListener(new RepeatButtonLisern(400, 100, new View.OnClickListener() {
            public void onClick(View view) {
                GetFoodEmoji();
                SetStickerTabBg(Integer.parseInt((String) view.getTag()));
            }
        }));
        views.findViewById(R.id.ImgCarEmoji).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GetCarEmoji();
                SetStickerTabBg(Integer.parseInt((String) view.getTag()));
            }
        });
        views.findViewById(R.id.ImgSymbolsEmoji).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GetSymbolsEmoji();
                SetStickerTabBg(Integer.parseInt((String) view.getTag()));
            }
        });
        views.findViewById(R.id.ImgFlowerEmoji).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GetFlowerEmoji();
                SetStickerTabBg(Integer.parseInt((String) view.getTag()));
            }
        });
        views.findViewById(R.id.ImgElectrEmoji).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GetElectronicsEmoji();
                SetStickerTabBg(Integer.parseInt((String) view.getTag()));
            }
        });
        views.findViewById(R.id.ImgBellEmoji).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GetBellEmoji();
                SetStickerTabBg(Integer.parseInt((String) view.getTag()));
            }
        });
        views.findViewById(R.id.ImgPeopleEmoji).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IconsList = null;
                IconsList = new ArrayList<>();
                RlOtherContent.removeAllViews();
                SetStickerTabBg(Integer.parseInt((String) view.getTag()));
                EmojiAdapter();
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
            BtnLanguageType.setTextColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)));
            if (Build.VERSION.SDK_INT >= 16) {
                views.findViewById(R.id.LlEmojiView).setBackground(DrawableEmoji);
                views.findViewById(R.id.LlThemeView).setBackground(DrawableTheme);
                views.findViewById(R.id.LlSettings).setBackground(DrawableSetting);
                return;
            }
            views.findViewById(R.id.LlThemeView).setBackgroundDrawable(DrawableTheme);
            views.findViewById(R.id.LlEmojiView).setBackgroundDrawable(DrawableEmoji);
            views.findViewById(R.id.LlSettings).setBackgroundDrawable(DrawableSetting);
        }
    }

    public void onStartInputView(EditorInfo editorInfo, boolean z) {
        super.onStartInputView(editorInfo, z);
        if (!IsPopupView) {
            setInputView(onCreateInputView());
            IsPopupView = false;
        }
        int inputTypes = editorInfo.inputType & 4080;
        if ((editorInfo.inputType & 15) == 1) {
            switch (inputTypes) {
                case 128:
                case 144:
                case 32:
                case 16:
                case 176:
                    TempShowSuggestion = false;
                    break;
            }
            switch ((editorInfo.inputType & 524288)) {
                case 0:
                    break;
                default:
                    TempShowSuggestion = false;

            }
            switch ((editorInfo.inputType & 65536)) {
                case 0:
                    break;
                default:
                    TempShowSuggestion = false;
                    CompletionOn = isFullscreenMode();
            }
        }
        int imeOption = getCurrentInputEditorInfo().imeOptions & 1073742079;
        if (imeOption != 2 && imeOption != 3 && imeOption != 4 && imeOption != 5 && imeOption != 6) {
            try {
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && TempShowSuggestion && Constants.selectedLangName == 1) {
                    CapsOnOffFlag = false;
                    CapsLock = true;
                    MainKeyboardVies.setShifted(true);
                    MainKeyboardVies.invalidate();
                    MainKeyboardVies.invalidateAllKeys();
                }
            } catch (Exception unused) {
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && TempShowSuggestion) {
                    CapsOnOffFlag = false;
                    CapsLock = false;
                    MainKeyboardVies.setShifted(false);
                    MainKeyboardVies.invalidate();
                    MainKeyboardVies.invalidateAllKeys();
                }
            }
        }
    }

    public boolean onEvaluateInputViewShown() {
        try {
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                if (views != null) {
                    if (getResources().getConfiguration().orientation == 1) {
                        Glide.with(this).clear(ImgGIf);
                        Glide.with(this).asGif().load(new File(getApplicationContext().getFilesDir(), "Gif_save.gif").getAbsolutePath()).placeholder(R.drawable.ic_rain).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ImgGIf);
                    } else {
                        RlMainContents.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
                    }
                }
            } else if (views != null) {
                if (getResources().getConfiguration().orientation == 1) {
                    Bitmap decodeFile = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/photo_save.jpeg");
                    if (decodeFile != null) {
                        RlMainContents.setBackgroundDrawable(new BitmapDrawable(decodeFile));
                    }
                } else {
                    RlMainContents.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
                }
            }
        } catch (Exception unused) {
        }
        return super.onEvaluateInputViewShown();
    }

    @SuppressLint("ResourceType")
    public void onKey(int primaryCode, int[] keyCodes) {
        final int code = primaryCode;
        final InputConnection connection = getCurrentInputConnection();
        if (Constants.ChangeLanguage == 1) {
            LlLanguageView.setVisibility(View.VISIBLE);
        } else {
            LlLanguageView.setVisibility(View.GONE);
        }
        MainKeyboardVies.setVisibility(View.VISIBLE);
        if (RlHeaderView.getVisibility() == View.VISIBLE) {
            RlHeaderView.setVisibility(View.GONE);
        }
        if (Constants.DeleteValFlag) {
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                CapsLock = true;
                CapsOnOffFlag = false;
                MainKeyboardVies.setShifted(true);
            }
            MainKeyboardVies.invalidateAllKeys();
            Constants.DeleteValFlag = false;
        } else {
            switch (code) {
                case -978903: {
                    Constants.WordExistOrNot = true;
                    CapsLockOn();
                    CapsLock = true;
                    CapsOnOffFlag = true;
                    NewCapital = true;
                }
                break;
                case -6003: {
                    Constants.WordExistOrNot = true;
                    CheckFlag = true;
                    MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, AllNumericQwerty[Constants.ChangeLanguage], keyboardHeight, 1);
                    keypadDataView = myKeypadDataView;
                    MainKeyboardVies.setKeyboard(myKeypadDataView);
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
                    MainKeyboardVies.invalidateAllKeys();
                    CapsLock = false;
                }
                break;
                case -5000: {
                    CustomKeyboardView customKeyboardView = this.MainKeyboardVies;
                    if (customKeyboardView != null) {
                        customKeyboardView.DismissLanguagePopup();
                        customKeyboardView.DismissPreviewPopup();
                    }
                    if (!ManageClick) {
                        Constants.WordExistOrNot = true;
                        if (LlMainMenu.getVisibility() == View.GONE) {
                            LlHintWord.setVisibility(View.GONE);
                            WordResultList = null;
                            WordResultList = new ArrayList<>();
                            HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, WordResultList));
                            LlMainMenu.setVisibility(View.VISIBLE);
                        }
                        RlHeaderView.setVisibility(View.VISIBLE);
                        Constants.temp_flag = 1;
                        EmojiAdapter();
                        customKeyboardView.setVisibility(View.GONE);
                        RlOtherContent.setVisibility(View.VISIBLE);
                        RlContentsView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), 17432576));
                        RlOtherContent.removeAllViews();
                        RlOtherContent.addView(GridEmoji);
                    } else {
                        RlOtherContent.removeAllViews();
                        RlOtherContent.setVisibility(View.GONE);
                        setKeyboardData();
                    }
                    ManageClick = !ManageClick;
                }
                break;
                case -1763: {
                    Constants.WordExistOrNot = true;
                    CheckFlag = true;
                    MyKeypadDataView myKeypadDataView2 = new MyKeypadDataView(this, R.xml.layout_numeric_shift_querty, keyboardHeight, 1);
                    keypadDataView = myKeypadDataView2;
                    MainKeyboardVies.setKeyboard(myKeypadDataView2);
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
                    MainKeyboardVies.invalidateAllKeys();
                    CapsLock = false;
                }
                break;
                case -1: {
                    Constants.WordExistOrNot = true;
                    NewCapital = false;
                    CapsLock = !CapsLock;
                    if (Constants.FlagChangeLanguage == 1) {
                        if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                            if (CapsLock) {
                                CapsOnOffFlag = false;
                                MainKeyboardVies.invalidateAllKeys();
                            } else {
                                CapsOnOffFlag = true;
                                MainKeyboardVies.invalidateAllKeys();
                            }
                            MainKeyboardVies.setShifted(CapsLock);
                        } else if (CapsLock) {
                            SelectQuertyCapital();
                            CapsOnOffFlag = false;
                            MainKeyboardVies.invalidateAllKeys();
                        } else {
                            SelectQuertySmall();
                            CapsOnOffFlag = true;
                            MainKeyboardVies.invalidateAllKeys();
                        }
                    } else if (CapsLock) {
                        CapsLockOn();
                        CapsLock = true;
                        CapsOnOffFlag = true;
                        NewCapital = true;
                        MainKeyboardVies.invalidateAllKeys();
                    } else {
                        SelectQuertySmall();
                        CapsOnOffFlag = true;
                        MainKeyboardVies.invalidateAllKeys();
                    }
                }
                break;
                case -2831: {
                    Constants.WordExistOrNot = true;
                    setKeyboardData();
                }
                break;
                case -2830: {
                    Constants.WordExistOrNot = true;
                    CheckFlag = false;
                    MyKeypadDataView myKeypadDataView3 = new MyKeypadDataView(this, DefaultQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
                    keypadDataView = myKeypadDataView3;
                    MainKeyboardVies.setKeyboard(myKeypadDataView3);
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
                    MainKeyboardVies.invalidateAllKeys();
                    try {
                        if (NewCapital) {
                            CapsLockOn();
                            CapsOnOffFlag = true;
                            CapsLock = true;
                        }
                        char charAt = getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
                        if (Character.isLetter(charAt) && Character.isUpperCase(charAt)
                                && !NewCapital && Constants.ChangeLanguage == 1
                                && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                            CapsOnOffFlag = false;
                            CapsLock = false;
                            onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                        }
                    } catch (Exception unused) {
                        if (!NewCapital && Constants.ChangeLanguage == 1) {
                            CapsLock = true;
                            CapsOnOffFlag = false;
                            SelectQuertyCapital();
                        }
                    }
                }
                break;
                case -5: {
                    CustomKeyboardView keyboardView = MainKeyboardVies;
                    if (keyboardView != null) {
                        keyboardView.DismissPreviewPopup();
                    }
                    if (LlMainMenu.getVisibility() == View.GONE) {
                        LlHintWord.setVisibility(View.GONE);
                        WordResultList = null;
                        WordResultList = new ArrayList<>();
                        HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, WordResultList));
                        LlMainMenu.setVisibility(View.VISIBLE);
                    }
                    Constants.WordExistOrNot = true;
                    try {
                        char charAt2 = connection.getTextBeforeCursor(1, 0).charAt(0);
                        if (Character.isDefined(charAt2)) {
                            Log.d("main", "isDefined");
                            if (Character.isHighSurrogate(connection.getTextBeforeCursor(2, 0).charAt(0))) {
                                Log.d("main", "isEmoji");
                                connection.deleteSurroundingText(2, 0);
                                return;
                            }
                        }
                        connection.deleteSurroundingText(1, 0);
                        int i3 = getCurrentInputEditorInfo().imeOptions & 1073742079;
                        if (i3 != 2 && i3 != 3 && i3 != 4 && i3 != 5 && i3 != 6 && !NewCapital && !CheckFlag && Constants.ChangeLanguage == 1) {
                            DeleteLetters(charAt2);
                        }
                        new Handler().postDelayed(() -> showSuggestionWords(connection), 500);
                    } catch (Exception e) {
                        Constants.DeleteValFlag = false;
                        int i4 = getCurrentInputEditorInfo().imeOptions & 1073742079;
                        if (i4 != 2 && i4 != 3 && i4 != 4 && i4 != 5 && i4 != 6) {
                            Log.d("main", "Exception deleting no char " + e);
                            if (Constants.FlagChangeLanguage != 0) {
                                CapsOnOffFlag = false;
                                MainKeyboardVies.setShifted(true);
                                MainKeyboardVies.invalidate();
                                MainKeyboardVies.invalidateAllKeys();
                                TempShiftOnOff = true;
                            }
                        }
                    }
                }
                break;
                case -4: {
                    Constants.WordExistOrNot = true;
                    if (LlMainMenu.getVisibility() == View.GONE) {
                        LlHintWord.setVisibility(View.GONE);
                        WordResultList = null;
                        WordResultList = new ArrayList<>();
                        HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, WordResultList));
                        LlMainMenu.setVisibility(View.VISIBLE);
                    }
                    int i5 = getCurrentInputEditorInfo().imeOptions & 1073742079;
                    switch (i5) {
                        case 2: {
                            connection.performEditorAction(2);
                        }
                        break;
                        case 3: {
                            connection.performEditorAction(3);
                        }
                        break;
                        case 4: {
                            connection.performEditorAction(4);
                        }
                        break;
                        case 5: {
                            connection.performEditorAction(5);
                        }
                        break;
                        case 6: {
                            connection.performEditorAction(6);
                        }
                        break;
                        default: {
                            connection.sendKeyEvent(new KeyEvent(0, 66));
                            if (!NewCapital && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                                CapsOnOffFlag = false;
                                CapsLock = true;
                                MainKeyboardVies.setShifted(true);
                                MainKeyboardVies.invalidate();
                                MainKeyboardVies.invalidateAllKeys();
                            }
                        }
                    }
                }
                break;
                case -97886: {

                }
                break;
                case 46: {
                    if (!Character.isLetter(primaryCode) || !CapsLock) {
                        connection.commitText(String.valueOf(primaryCode), 1);
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && Constants.FlagChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                            CapsLock = true;
                            CapsOnOffFlag = false;
                            MainKeyboardVies.setShifted(true);
                        }
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SUGGESTION, true) && TempShowSuggestion && !Constants.PreviewViewisOpen && !CheckLanguage) {
                            showSuggestionWords(connection);
                        }
                    }
                }
                break;
                case 32: {
                    char c = (char) code;
                    connection.commitText(String.valueOf(c), 1);
                }
                break;
                default: {
                    char c = (char) code;
                    if (!Character.isLetter(c) || !CapsLock) {
                        connection.commitText(String.valueOf(c), 1);
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SUGGESTION, true) && !Constants.PreviewViewisOpen && !CheckLanguage) {
                            showSuggestionWords(connection);
                        }
                        if (code >= 97 && code <= 122) {
                            CapsOnOffFlag = true;
                            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SUGGESTION, true) && !Constants.PreviewViewisOpen && !CheckLanguage) {
                                showSuggestionWords(connection);
                            }
                            break;
                        }
                        break;
                    }
                    connection.commitText(String.valueOf(Character.toUpperCase(c)), 1);
                    if (!CapsOnOffFlag && Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                        CapsOnOffFlag = true;
                        onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                    }
                    if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SUGGESTION, true) && !Constants.PreviewViewisOpen && !CheckLanguage) {
                        showSuggestionWords(connection);
                    }
                }
            }
        }
    }

    public void showSuggestionWords(InputConnection inputConnection) {
        try {
            StrWord = "";
            if (CheckLanguage) {
                TextDatasChar = Constants.dictionaryword;
            } else {
                TextDatasChar = inputConnection.getTextBeforeCursor(Integer.MAX_VALUE, 0);
            }
            int length = TextDatasChar.toString().length();
            if (length >= 1) {
                boolean b = false;
                while (true) {
                    if (length != 0) {
                        int letter = length - 1;
                        char charAt = TextDatasChar.toString().charAt(letter);
                        if (charAt == 10 || charAt == ',') {
                            break;
                        } else if (charAt == '.') {
                            break;
                        } else if (charAt != ' ') {
                            StrWord += TextDatasChar.toString().charAt(letter);
                            if (Constants.SuggestedView) {
                                LlHintWord.setVisibility(View.VISIBLE);
                                LlMainMenu.setVisibility(View.GONE);
                                b = true;
                            }
                            length--;
                        } else if (charAt == ' ') {

                            break;
                        } else {
                            Constants.WordExistOrNot = true;
                            if (Constants.SuggestedView && !b) {
                                LlHintWord.setVisibility(View.GONE);
                                WordResultList = null;
                                WordResultList = new ArrayList<>();
                                HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, WordResultList));
                                LlMainMenu.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        break;
                    }
                }
                Constants.WordExistOrNot = true;
                if (Constants.SuggestedView && !b) {
                    LlHintWord.setVisibility(View.GONE);
                    WordResultList = null;
                    WordResultList = new ArrayList<>();
                    HListView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.layout_hint_item_view, WordResultList));
                    LlMainMenu.setVisibility(View.VISIBLE);
                }
                StrWord = new StringBuilder(StrWord).reverse().toString();
                SuggetionAsyncTask suggetionAsyncTask = new SuggetionAsyncTask();
                if (Constants.isUpHoneycombVersion) {
                    suggetionAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    suggetionAsyncTask.execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSuggestWord(String str) {
        DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
        if (Constants.isUpHoneycombVersion) {
            dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            dictionaryTask.execute();
        }
        if (!str.equals("")) {
            ArrayList<String> suggestion = Constants.getSuggestionWords(str);
            WordResultList = suggestion;
            if (suggestion.size() >= 1) {
                Collections.sort(WordResultList, (str1, str2) -> str1.compareToIgnoreCase(str2));
                HListView.setAdapter(Constants.setSuggestionWordsAdapter(this, WordResultList, selectedTheme));
            } else if (WordResultList.size() <= 0) {
                WordResultList = null;
                ArrayList<String> arrayList = new ArrayList<>();
                WordResultList = arrayList;
                arrayList.add(str);
                WordResultList.add("Touch to add");
                HListView.setAdapter(Constants.setSuggestionWordsAdapter(this, WordResultList, selectedTheme));
            }
        } else {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add("");
            HListView.setAdapter(Constants.setSuggestionWordsAdapter(this, arrayList2, selectedTheme));
        }
    }

    @SuppressLint("WrongConstant")
    private void OnTapPlaySound(int code) {
        int val = code != -5 ? code != -4 ? code != 32 ? 5 : 6 : 8 : 7;
        if (((double) new MySharePref(getApplicationContext()).getPrefFloat(MySharePref.SOUND_PROGRESS_FLOAT, 0.3f)) != 0.0d) {
            audioManager.playSoundEffect(val, new MySharePref(getApplicationContext()).getPrefFloat(MySharePref.SOUND_PROGRESS_FLOAT, 0.3f));
        }
    }

    @Override
    public void onFinishInput() {
        if (MainKeyboardVies != null) {
            MainKeyboardVies.DismissLanguagePopup();
        }
        super.onFinishInput();
    }

    @Override
    public void onPress(int i) {
        MainKeyboardVies.setPreviewEnabled(false);
        MainKeyboardVies.DismissLanguagePopup();
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.POPUP, true)) {
            MainKeyboardVies.onPressKey(i);
        }
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.VIBRATION, false)) {
            Vibrate();
        }
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SOUND_ENABLE, true)) {
            OnTapPlaySound(i);
        }
    }

    public void Vibrate() {
        long duratio = KeypressVibrationDuration;
        if (duratio < 0) {
            if (MainKeyboardVies != null) {
                MainKeyboardVies.performHapticFeedback(3, 2);
                return;
            }
            return;
        }
        SetVibrateComp setVibrateComp = vibrateCompact;
        if (setVibrateComp != null) {
            setVibrateComp.vibrate(duratio);
        }
    }

    @Override
    public void onRelease(int i) {
        new Handler().postDelayed(() -> MainKeyboardVies.DismissPreviewPopup(), 100);
    }

    public void EmojiAdapter() {
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
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, MainKeyboardVies.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), People.DATA, 0));
    }

    public void Clickeventfood(int i) {
        getCurrentInputConnection().commitText(Sport.DATA[i].getEmoji(), 1);
    }

    public void Clickeventtop(int i) {
        getCurrentInputConnection().commitText(People.DATA[i].getEmoji(), 1);
    }

    public void Clickeventflower(int i) {
        getCurrentInputConnection().commitText(Nature.DATA[i].getEmoji(), 1);
    }

    public void GetFlowerEmoji() {
        RlOtherContent.removeAllViews();
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 116; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("f" + i);
        }
        GridEmoji.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, MainKeyboardVies.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Nature.DATA, 1));
        RlOtherContent.addView(GridEmoji);
    }

    public void GetElectronicsEmoji() {
        RlOtherContent.removeAllViews();
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 116; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("f" + i);
        }
        GridEmoji.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, MainKeyboardVies.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Electr.DATA, 6));
        RlOtherContent.addView(GridEmoji);
    }

    public void GetBellEmoji() {
        RlOtherContent.removeAllViews();
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 229; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("b" + i);
        }
        GridEmoji.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, MainKeyboardVies.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Sport.DATA, 2));
        RlOtherContent.addView(GridEmoji);
    }

    public void GetCarEmoji() {
        RlOtherContent.removeAllViews();
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 98; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("c" + i);
        }
        GridEmoji.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, MainKeyboardVies.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Cars.DATA, 3));
        RlOtherContent.addView(GridEmoji);
    }

    public void GetFoodEmoji() {
        RlOtherContent.removeAllViews();
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 98; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("c" + i);
        }
        GridEmoji.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, MainKeyboardVies.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Food.DATA, 5));
        RlOtherContent.addView(GridEmoji);
    }

    public void GetSymbolsEmoji() {
        RlOtherContent.removeAllViews();
        IconsList = new ArrayList<>();
        for (int i = 1; i <= 206; i++) {
            ArrayList<String> arrayList = IconsList;
            arrayList.add("s" + i);
        }
        GridEmoji.setNumColumns(8);
        GridEmoji.setLayoutParams(new RelativeLayout.LayoutParams(-1, MainKeyboardVies.getHeight() - TempHeight));
        GridEmoji.setAdapter(new EmojiListAdapter(getApplicationContext(), Symbols.DATA, 4));
        RlOtherContent.addView(GridEmoji);
    }

    public void DeleteSetEmoji() {
        try {
            char lattoer = getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
            if (Character.isDefined(lattoer)) {
                if (Character.isHighSurrogate(getCurrentInputConnection().getTextBeforeCursor(2, 0).charAt(0))) {
                    getCurrentInputConnection().deleteSurroundingText(2, 0);
                    return;
                }
            }
            getCurrentInputConnection().deleteSurroundingText(1, 0);
        } catch (Exception e) {
        }
    }

    public void SetStickerTabBg(int i) {
        Iterator<ImageButton> iterator = BtnArray.iterator();
        while (iterator.hasNext()) {
            ImageButton next = iterator.next();
            int tag = Integer.parseInt((String) next.getTag());
            if (tag == i) {
                next.setBackgroundResource(EmojiPressInts[i]);
            } else {
                next.setBackgroundResource(EmojiUnpressInts[tag]);
            }
        }
    }

    private void initArrayList(View view) {
        setHintWords();
        BtnArray = new ArrayList<>();
        BtnArray.add(view.findViewById(R.id.ImgPeopleEmoji));
        BtnArray.add(view.findViewById(R.id.ImgFlowerEmoji));
        BtnArray.add(view.findViewById(R.id.ImgFoodEmoji));
        BtnArray.add(view.findViewById(R.id.ImgBellEmoji));
        BtnArray.add(view.findViewById(R.id.ImgCarEmoji));
        BtnArray.add(view.findViewById(R.id.ImgElectrEmoji));
        BtnArray.add(view.findViewById(R.id.ImgSymbolsEmoji));
        LlMainMenu = views.findViewById(R.id.LlTopBar);
        vibrateCompact = SetVibrateComp.getInstance(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        RlOtherContent = views.findViewById(R.id.RlOtherCon);
        LlSpeak = views.findViewById(R.id.LlMicVIew);
        views.findViewById(R.id.LlSettings).setOnClickListener(new View.OnClickListener() {
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
        LlSpeak.setOnClickListener(SpeakViewOpens);
    }

    public void ClickeventCar(int i) {
        getCurrentInputConnection().commitText(Cars.DATA[i].getEmoji(), 1);
    }

    public void ClickeventSymbol(int i) {
        getCurrentInputConnection().commitText(Symbols.DATA[i].getEmoji(), 1);
    }

    public void clickeventfood(int i) {
        getCurrentInputConnection().commitText(Food.DATA[i].getEmoji(), 1);
    }

    public void clickeventElectronics(int i) {
        getCurrentInputConnection().commitText(Electr.DATA[i].getEmoji(), 1);
    }

    public void SetKeyBoardLayout() {
        NewCapital = false;
        onKey(Constants.CODE_ALPHABETS, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
        RlHeaderView.setVisibility(View.GONE);
        MainKeyboardVies.setVisibility(View.VISIBLE);
        if (!NewCapital && Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
            CapsOnOffFlag = false;
            CapsLock = false;
            onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
        }
    }

    private void SelectQuertyCapital() {
        keypadDataView = new MyKeypadDataView(this, LayoutCapsOffQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
        MainKeyboardVies.setKeyboard(keypadDataView);
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
        MainKeyboardVies.invalidateAllKeys();
    }

    private void SelectQuertySmall() {
        keypadDataView = new MyKeypadDataView(this, DefaultQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
        MainKeyboardVies.setKeyboard(keypadDataView);
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
        MainKeyboardVies.invalidateAllKeys();
    }

    public void CapsLockOn() {
        CapsLock = false;
        keypadDataView = new MyKeypadDataView(this, LayoutCapsOnQWERTY[Constants.ChangeLanguage], keyboardHeight, 0);
        MainKeyboardVies.setKeyboard(keypadDataView);
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
        MainKeyboardVies.invalidateAllKeys();
    }

    private void DeleteLetters(char c) {
        if (Character.isLetter(c) && Character.isUpperCase(c)) {
            CapsLock = true;
            CapsOnOffFlag = false;
            MainKeyboardVies.setShifted(true);
            MainKeyboardVies.invalidateAllKeys();
        } else if ((Character.isLetter(c) && Character.isLowerCase(c)) || c == 10) {
            CapsLock = false;
            CapsOnOffFlag = true;
            MainKeyboardVies.setShifted(false);
            MainKeyboardVies.invalidateAllKeys();
        }
    }

    public void setKeyboardData() {
        if (Constants.temp_flag == 1) {
            Constants.temp_flag = 0;
            RlHeaderView.setVisibility(View.GONE);
            CapsOnOffFlag = false;
            CapsLock = true;
            MainKeyboardVies.setVisibility(View.VISIBLE);
            return;
        }
        try {
            RlHeaderView.setVisibility(View.GONE);
            MainKeyboardVies.InitActions();
            int i = Constants.FlagChangeLanguage;
            if (i == 0) {
                Constants.ChangeLanguage = 0;
                SetKeyBoardLayout();
                Constants.SelectedLanguageName = "Hindi";
            } else if (i == 1) {
                Constants.ChangeLanguage = 1;
                SetKeyBoardLayout();
                Constants.SelectedLanguageName = "English";
                if (new MySharePref(context).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    CapsLock = true;
                    CapsOnOffFlag = false;
                    MainKeyboardVies.setShifted(true);
                    MainKeyboardVies.invalidate();
                    MainKeyboardVies.invalidateAllKeys();
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
            new MySharePref(getApplicationContext()).putPrefString(MySharePref.LANGUAGE_NAME, Constants.SelectedLanguageName.substring(0, 2).toLowerCase());
            new MySharePref(getApplicationContext()).putPrefString(MySharePref.DEFULT_LANGUAGE, Constants.SpeakLanguageName.substring(0, 2).toLowerCase());
            Constants.temp_flag = 0;
            MainKeyboardVies.InitActions();
        } catch (Exception unused) {
            unused.getMessage();
        }
    }

    private void setHintWords() {
        LlHintWord = views.findViewById(R.id.LlHIntView);
        HListView = views.findViewById(R.id.HoriHintList);
        HListView.setVisibility(View.VISIBLE);
        HListView.setOnItemClickListener(SuggestionListItemListern);
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

    private void TransprentLayoutHeight(int i) {
        TempHeight1 = Constants.DpToPx(getApplicationContext(), 55);
        ViewGroup.LayoutParams layoutParams = RlTranslateView.getLayoutParams();
        layoutParams.height = i + TempHeight1;
        layoutParams.width = -1;
        RlTranslateView.setLayoutParams(layoutParams);
    }

    private void GifLayoutHeight(int i) {
        TempHeight1 = Constants.DpToPx(getApplicationContext(), 55);
        RelativeLayout relativeLayout = views.findViewById(R.id.RlGifView);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = i + TempHeight1;
        layoutParams.width = -1;
        relativeLayout.setLayoutParams(layoutParams);
    }

    private Bitmap ChangeBimap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        return createBitmap;
    }

    public AlertDialog DialogInstallTranslate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.str_install_voice_translate));
        builder.setPositiveButton(getResources().getString(R.string.str_install_now), (dialogInterface, i) -> {
            try {
                getApplicationContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.googlequicksearchbox")));
            } catch (ActivityNotFoundException unused) {
                getApplicationContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox")));
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.str_Cancel), (dialogInterface, i) -> {
        });
        return builder.create();
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showToastMsges(String str, int i) {
        if (toast != null) {
            toast.cancel();
        }
        if (str.equalsIgnoreCase("")) {
            toast.cancel();
            return;
        }
        toast = Toast.makeText(this, str, i);
        toast.setGravity(81, 0, 200);
        toast.show();
    }

    public void KeyboardDownAnimation(int level) {
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

    public void KeyboardUpAnimation(int level) {
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

    public void hideProgressDialog() {
        ProgressDialog progressDialog2 = dialog;
        if (progressDialog2 != null) {
            if (progressDialog2.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }

    class SuggetionAsyncTask extends AsyncTask<Void, Void, Void> {
        SuggetionAsyncTask() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            ArrayList arrayList = new ArrayList();
            arrayList.add("");
            DictionaryTask dictionaryTask = new DictionaryTask(getApplicationContext(), Constants.FlagChangeLanguage);
            if (Constants.isUpHoneycombVersion) {
                dictionaryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                dictionaryTask.execute();
            }
            HListView.setAdapter(Constants.setSuggestionWordsAdapter(getApplicationContext(), arrayList, selectedTheme));
        }

        public Void doInBackground(Void... voidArr) {
            try {
                if (StrWord.equals("")) {
                    return null;
                }
                WordResultList = Constants.getSuggestionWords(StrWord);
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        public void onPostExecute(Void r6) {
            try {
                if (!StrWord.equals("")) {
                    if (WordResultList.size() >= 1) {
                        Collections.sort(WordResultList, (str, str2) -> str.compareToIgnoreCase(str2));
                        HashSet hashSet = new HashSet();
                        hashSet.addAll(WordResultList);
                        WordResultList.clear();
                        WordResultList.addAll(hashSet);
                        try {
                            if (WordResultList != null) {
                                Collections.sort(WordResultList, new MyComparator());
                            }
                        } catch (Exception unused) {
                        }
                        HListView.setAdapter(Constants.setSuggestionWordsAdapter(getApplicationContext(), WordResultList, selectedTheme));
                    } else if (WordResultList.size() <= 0) {
                        WordResultList = null;
                        WordResultList = new ArrayList<>();
                        WordResultList.add(StrWord);
                        WordResultList.add("Touch to add");
                        HListView.setAdapter(Constants.setSuggestionWordsAdapter(getApplicationContext(), WordResultList, selectedTheme));
                    }
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("");
                    HListView.setAdapter(Constants.setSuggestionWordsAdapter(getApplicationContext(), arrayList, selectedTheme));
                }
            } catch (Exception unused2) {
                unused2.getMessage();
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
            SpeechErrorCode = 100;
            LLSpeackToText.setVisibility(View.VISIBLE);
            RlBottomView.setVisibility(View.GONE);
            IsSpeech = true;
            if (Constants.SpeakLanguageName.equals("en")) {
                TxtSpeakMsg.setText(getResources().getString(R.string.str_en_error_text10));
            } else {
                TxtSpeakMsg.setText(getResources().getString(R.string.str_error_text10));
            }
        }

        public void onBeginningOfSpeech() {
        }

        public void onRmsChanged(float f) {
            int i;
            IsSpeech = true;
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
        }

        public void onEndOfSpeech() {
            LLSpeackToText.setVisibility(View.GONE);
            RlBottomView.setVisibility(View.VISIBLE);
            if (IsSpeech) {
                ProgressTalk.setVisibility(View.VISIBLE);
            }
        }

        public void onError(int i) {
            if (IsSpeech) {
                ImageSpeechIcons.setVisibility(View.VISIBLE);
                ProgressTalk.setVisibility(View.GONE);
                if (i != 3 || Build.VERSION.SDK_INT < 23) {
                    String errorText = getErrorText(getApplicationContext(), i);
                    TxtSpeakMsg.setText(errorText);
                    showToastMsges(errorText, 1);
                } else {
                    dialogSettingGoogleApp().show();
                }
                speechRecognizer.destroy();
                return;
            }
            speechRecognizer.startListening(IntentRecognizer);
        }

        public void onResults(Bundle bundle) {
            IsSpeech = false;
            ImageSpeechIcons.setVisibility(View.VISIBLE);
            ProgressTalk.setVisibility(View.GONE);
            ArrayList<String> stringArrayList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (stringArrayList != null) {
                if (stringArrayList.size() == 1) {
                    TranslateIn.setmText(stringArrayList.get(0));
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
            KeyboardDownAnimation(ToLevel);
        }
    }

    class AnimatedUpImg implements Runnable {
        AnimatedUpImg() {
        }

        public void run() {
            KeyboardUpAnimation(ToLevel);
        }
    }
}
