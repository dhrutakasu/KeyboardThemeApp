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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
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
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.CompletionInfo;
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
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.theme.keyboardthemeapp.UI.Adapters.FarsiEmojiAdapter;
import com.theme.keyboardthemeapp.UI.Adapters.StickerAdapter;
import com.theme.keyboardthemeapp.UI.Adapters.StickerListAdapter;
import com.theme.keyboardthemeapp.ngonngu.NgonNguIn;
//import com.common.Common_Preferences;
//import com.common.Common_Resource;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.koushikdutta.async.http.AsyncHttpGet;
//import com.my.stylsihtext.fencyart.fencyfont.fencytext.SessionManager;
//import com.ngonngu.NgonNguIn;
//import com.tech.lang.keyboard.hindikeyboard.adapter.EmojiAdapter;
//import com.tech.lang.keyboard.hindikeyboard.adapter.FarsiEmojiAdapter;
//import com.tech.lang.keyboard.hindikeyboard.adapter.StickerAdapter;
//import com.tech.lang.keyboard.hindikeyboard.adapter.StickerListAdapter;
//import com.tech.lang.keyboard.hindikeyboard.adapter.StickerModel;
//import com.theme.keyboardthemeapp.Constants;
//import com.theme.keyboardthemeapp.R;
//import com.vanniktech.emoji.EmojiPopup;
//import com.vanniktech.emoji.emoji.Cars;
//import com.vanniktech.emoji.emoji.Electronics;
//import com.vanniktech.emoji.emoji.Food;
//import com.vanniktech.emoji.emoji.Nature;
//import com.vanniktech.emoji.emoji.People;
//import com.vanniktech.emoji.emoji.Sport;
//import com.vanniktech.emoji.emoji.Symbols;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.http.HttpHost;
//import org.apache.http.HttpStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class HindiKeypad extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    public static final int TYPE_ASSET = 0;
    public static final int TYPE_LOCAL = 1;
    public static final int TYPE_ONLINE = 2;
    static TextView Timer = null;
    public static boolean caps = false;
    public static int[] char_colorCodes = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    public static boolean checkLanguage;
    public static InputMethodService ims;
    static TextView speakstring;
    public static int toLevel = 0;
    View.OnClickListener ChangeSpeakLang = new View.OnClickListener() {
        public void onClick(View view) {
            if (new MySharePref(ctx).getPrefString(MySharePref.LANGUAGE_NAME, "hi").equals(HindiKeypad.this.getResources().getString(R.string.speak_lang_2))) {
                new MySharePref(ctx).putPrefString(MySharePref.LANGUAGE_NAME, "en");
            } else {
                new MySharePref(ctx).putPrefString(MySharePref.LANGUAGE_NAME, getResources().getString(R.string.speak_lang_2));
            }
            HindiKeypad.this.btnspeaklang.setText(new MySharePref(ctx).getPrefString(MySharePref.LANGUAGE_NAME, "hi"));
        }
    };
    View.OnClickListener CloseDialog = new View.OnClickListener() {
        public void onClick(View view) {
            HindiKeypad.this.speak_lay.setVisibility(View.GONE);
            HindiKeypad.this.bottomlay.setVisibility(View.VISIBLE);
        }
    };
    String ContryNameIn;
    ArrayList<String> FinalString = new ArrayList<>();
    String FinalWord = "";
    String FolderPathOfSticker = "";
    Drawable Inputlang_off_drawable;
    Drawable Inputlang_on_drawable;
    LinearLayout LangChange;
    View.OnClickListener OnClickTheme = new View.OnClickListener() {
        public void onClick(View view) {
            try {
                if (!Constants.previewActivityisOpen) {
                    Constants.wordExist = true;
                    Intent intent = new Intent(HindiKeypad.this.getApplicationContext(), ThemeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("flgbool", true);
                    HindiKeypad.this.startActivity(intent);
                } else if (ImagePreviewActivity.act == null) {
                    Constants.wordExist = true;
                    Intent intent2 = new Intent(HindiKeypad.this.getApplicationContext(), ThemeActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent2.putExtra("flgbool", false);
                    HindiKeypad.this.startActivity(intent2);
                }
            } catch (Exception unused) {
            }
        }
    };
    ListView SelectedLanglist;
    Drawable Speak_btn_drawable;
    View.OnClickListener SpeakbtnDialog = new View.OnClickListener() {
        public void onClick(View view) {
            HindiKeypad.this.speak_lay.setLayoutParams(new FrameLayout.LayoutParams(-1, HindiKeypad.this.speaklayheight));
            HindiKeypad.speakstring.setText("Tap to Speak!!");
            HindiKeypad.this.speaktotextbtn.setVisibility(View.GONE);
            HindiKeypad.this.speak_lay.setVisibility(View.VISIBLE);
            HindiKeypad.this.bottomlay.setVisibility(View.GONE);
            if (HindiKeypad.this.Timercounter != null) {
                HindiKeypad.this.Timercounter.cancel();
            }
            HindiKeypad.this.Timercounter = new CountDownTimer(14000, 1000) {
                public void onTick(long j) {
                    TextView textView = HindiKeypad.Timer;
                    textView.setText("00:" + (j / 1000));
                }

                public void onFinish() {
                    HindiKeypad.Timer.setText("done!");
                    HindiKeypad.this.speak_lay.setVisibility(View.GONE);
                    HindiKeypad.this.bottomlay.setVisibility(View.VISIBLE);
                }
            }.start();
            if (!HindiKeypad.this.isSpeechRecoAvalable) {
                HindiKeypad.this.dialogAskInstallSTT().show();
            } else if (!HindiKeypad.this.isOnline()) {
                HindiKeypad.this.showToast("Opps! No Internet Access, Please Try Again", 1);
            } else if (Arrays.asList(HindiKeypad.this.commonResource.getNovoice()).contains(HindiKeypad.this.commonResource.getContriesin()[HindiKeypad.this.mNgonNguIn.getmPosition()])) {
                HindiKeypad hindiKeypad = HindiKeypad.this;
                hindiKeypad.showToast("Opps! " + HindiKeypad.this.mNgonNguIn.getmLanguageName() + " language was not supported Speech to Text", 1);
            } else {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        if (!HindiKeypad.this.vib.hasVibrator()) {
                            HindiKeypad.this.vib.vibrate(40);
                        }
                    }
                    int unused = HindiKeypad.this.thoiGianCho = 100;
                    Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                    intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
                    intent.putExtra("calling_package", HindiKeypad.this.getApplicationContext().getPackageName());
                    intent.putExtra("android.speech.extra.MAX_RESULTS", 5);
                    intent.putExtra("android.speech.extras.SPEECH_INPUT_MINIMUM_LENGTH_MILLIS", 3000);
                    intent.putExtra("android.speech.extra.LANGUAGE", HindiUtils.speakLangName);
                    Intent unused2 = HindiKeypad.this.recognizerIntent = intent;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                        HindiKeypad.this.mSpeechReco.setRecognitionListener(HindiKeypad.this.mRecoListener);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                            HindiKeypad.this.mSpeechReco.startListening(intent);
                        }
                    }
                } catch (Exception e) {
                    HindiKeypad.this.showToast(e.getMessage(), 1);
                }
            }
            Log.d("mToggleSpeech", "onTrue");
        }
    };
    AdapterView.OnItemClickListener SuggectionItemClickEvent = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            CharSequence charSequence;
            String str = (String) adapterView.getItemAtPosition(i);
            if (!HindiKeypad.this.word.equals("")) {
                if (str.equals("Touch to add")) {
                    if (!Constants.SuggestionWordsList.contains(HindiKeypad.this.word.toLowerCase())) {
                        Constants.SuggestionWordsList.add(HindiKeypad.this.word.toLowerCase());
                    }
                    Toast.makeText(HindiKeypad.this.getApplicationContext(), "Word Added Successfully", Toast.LENGTH_LONG).show();
                    HindiKeypad.this.word = "";
                    HindiKeypad hindiKeypad = HindiKeypad.this;
                    hindiKeypad.getGujarati(hindiKeypad.word);
                } else if (HindiKeypad.this.result.contains("Touch to add")) {
                    if (!Constants.SuggestionWordsList.contains(HindiKeypad.this.word.toLowerCase())) {
                        HindiUtils.SuggestionWords.add(HindiKeypad.this.word.toLowerCase());
                    }
                    Toast.makeText(HindiKeypad.this.getApplicationContext(), "Word Added Successfully", Toast.LENGTH_LONG).show();
                    HindiKeypad.this.word = "";
                    HindiKeypad hindiKeypad2 = HindiKeypad.this;
                    hindiKeypad2.getGujarati(hindiKeypad2.word);
                } else {
                    CharSequence charSequence2 = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
                        charSequence2 = HindiKeypad.this.getCurrentInputConnection().getExtractedText(new ExtractedTextRequest(), 0).text;
                    }
                    if (charSequence2.toString().contains(" ")) {
                        charSequence = HindiKeypad.this.getCurrentInputConnection().getTextBeforeCursor(999999, 0);
                    } else {
                        charSequence = "";
                    }
                    String s = charSequence + "";
                    HindiKeypad.this.getCurrentInputConnection().deleteSurroundingText(charSequence2.toString().length(), 0);
                    if (charSequence.toString().contains(" ")) {
                        int lastIndexOf = charSequence.toString().lastIndexOf(" ");
                        str = charSequence.toString().substring(0, lastIndexOf) + " " + str;
                    }
                    HindiKeypad.this.getCurrentInputConnection().commitText(str, 0);
                    HindiKeypad.this.word = "";
                    HindiKeypad hindiKeypad3 = HindiKeypad.this;
                    hindiKeypad3.getGujarati(hindiKeypad3.word);
                }
                HindiKeypad.this.hintword.setVisibility(View.GONE);
                HindiKeypad.this.result = null;
                HindiKeypad.this.result = new ArrayList<>();
                HindiKeypad.this.hlist.setAdapter((ListAdapter) new ArrayAdapter(HindiKeypad.this.getApplicationContext(), R.layout.hint_item_view, HindiKeypad.this.result));
                HindiKeypad.this.mainMenu.setVisibility(View.VISIBLE);
            }
        }
    };
    CountDownTimer Timercounter;
    //    AdRequest adRequest;
//    AdView adView;
    FarsiEmojiAdapter adapter = null;
    public Runnable animateDownImage = new C03922();
    public Runnable animateUpImage = new C03911();
    ImageView blackTransparentview;
    private RelativeLayout black_translay;
    public RelativeLayout bottomlay;
    ArrayList<ImageButton> btnArray = new ArrayList<>();
    LinearLayout btnSpeak;
    Button btnspeaklang;
    boolean capital = false;
    int[] capsOnquerty = {R.xml.capson_hindi_default_querty2, R.xml.capson_eng_default_querty0};
    boolean capsonoffflg = false;
    int[] capsquerty = {R.xml.caps_hindi_default_querty2, R.xml.caps_eng_default_querty0};
    String charMain = "";
    boolean checkflg = false;
    ImageButton closedialog;
    Common_Resource commonResource;
    FrameLayout content_frame;
    int counter = 0;
    Context ctx;
    int[] defaultquerty = {R.xml.hindi_default_querty2, R.xml.eng_default_querty0};
    ImageButton delete;
    Drawable deleteDrawable;
    private int[] deleteKeys = {R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back};
    View div1;
    View div2;
    Drawable drop_arrow_drawable;
    SharedPreferences.Editor edit;
    Drawable emojiDrawable;
    //    private EmojiPopup emojiPopup;
    GridView emojigrid = null;
    Drawable enterDrawable;
    private int[] enterKeys = {R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter};
    String finalstr = "";
    public int fromLevel = 0;
    //    GujaratiEditBoxSupport g;
    private int[] generalKeys_presed = {R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed};
    private int[] generalKeys_unpresed = {R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed};
    private RelativeLayout gif_rl_layout;
    ImageView gifimage;
    LinearLayout headertext;
    LinearLayout hintword;
    HorizontalListView hlist;
    HttpURLConnection httpURLConnection;
    ArrayList<String> icons = new ArrayList<>();
    ImageView img;
    boolean isLandscape;
    boolean isPopup;
    public boolean isSpeechRecoAvalable = false;
    public boolean isSpeeching = false;
    private MyKeypadDataView keyboard;
    int keybpardHeight = 0;
    public HindiKeyboardView kv;
    LinearLayout langClick;
    String lastString = "";
    String lastWord = "";
    RelativeLayout layoutofSticker;
    ArrayList<Integer> listinputs = new ArrayList<>();
    private AudioManager mAudioManager;
    private Common_Preferences mCommon_Preferences;
    private boolean mCompletionOn;
    private CompletionInfo[] mCompletions;
    private StringBuilder mComposing = new StringBuilder();
    Context mContext;
    public Handler mDownHandler = new Handler();
    private ClipDrawable mImageDrawable;
    private long mKeypressVibrationDuration = -1;
    private int mLevel = 0;
    NgonNguIn mNgonNguIn;
    private boolean mPredictionOn;
    public RecognitionListener mRecoListener;
    public SpeechRecognizer mSpeechReco;
    private Toast mThongBao;
    public Handler mUpHandler = new Handler();
    private SetVibrateCompact mVibrator;
    LinearLayout mainMenu;
    boolean manageClick = false;
    boolean newcapital = false;
    NinePatchDrawable npd;
    NinePatchDrawable npdDelete;
    NinePatchDrawable npdDone;
    NinePatchDrawable npdShiftOff;
    NinePatchDrawable npdShiftOn;
    NinePatchDrawable npdSpace;
    NinePatchDrawable npd_presed;
    String[] offlineFiles;
    RelativeLayout otherContentLay;
    private RelativeLayout otherContents;
    private int[] popUpDrawables = {R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg};
    Drawable popupDrawable;
    ListView popupLanglist;
    int position;
    SharedPreferences prefs;
    public ProgressBar proTalk;
    private ProgressDialog progressDialog;
    private RelativeLayout r2;
    public Intent recognizerIntent;
    String replaceStr = "";
    private int[] resid = {R.drawable.emoji_presedtheme0, R.drawable.flower_presed0, R.drawable.food_presed0, R.drawable.sport_presed0, R.drawable.car_presed0, R.drawable.electronic_presed0, R.drawable.sign_presed0};
    ArrayList<String> result = null;
    private RelativeLayout rl;
    int selectedTheme = 0;
    private int[] selector = {R.drawable.emoji_unpresedtheme0, R.drawable.flower_unpresed0, R.drawable.food_unpresed0, R.drawable.sport_unpresed0, R.drawable.car_unpresed0, R.drawable.electronic_unpresed0, R.drawable.sign_unpresed0};
    boolean setClick = false;
    Drawable settingDrawable;
    Drawable shiftOffDrawable;
    private int[] shiftOffKeys = {R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off};
    Drawable shiftOnDrawable;
    private int[] shiftOnKeys = {R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on};
    int shiftonoff = 0;
    int small = 0;
    Drawable spaceDrawable;
    private int[] spaceKeys = {R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space};
    LinearLayout speak_lay;
    int speaklayheight = 0;
    ImageButton speaktotextbtn;
    StickerAdapter stickerAdapter;
    HorizontalListView stickerList;
    GridView stickergrid = null;
    StickerListAdapter stickerlistadapter;
    ArrayList<StickerModel> stickers = new ArrayList<>();
    ArrayList<StickerModel> stickersList = new ArrayList<>();
    Drawable strickerDrawable;
    StringBuilder stringBuilder2 = null;
    int textColorCode = -1;
    CharSequence textdatas = "";
    LinearLayout texthintlayout;
    Drawable themeDrawable;
    public int thoiGianCho;
    int tmpHieght;
    int tmpHieght1;
    public boolean tmpShowSuggestion = true;
    boolean tmpshiftonoff = false;
    Typeface typeface;
    ArrayList<String> unicodearray = new ArrayList<>();
    String urlString = "";
    View v;
    public Vibrator vib;
    String word = "";
    ArrayList<String> wordarray = new ArrayList<>();
    private Exception e;
    public static int[] allNumericQuerty = {R.xml.numeric_hindi_querty, R.xml.numeric_querty};

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

    public HindiKeypad() {
        ims = this;
    }

    public static void setKeyboardView() {
        ((HindiKeypad) ims).setKeyboardData();
    }

    public static String getErrorText(int i) {
        switch (i) {
            case 1:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "Network timeout";
                }
                return HindiUtils.e1;
            case 2:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "Network Error, Please Check Your Internet";
                }
                return HindiUtils.e2;
            case 3:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "Audio Error, Please Try Again";
                }
                return HindiUtils.e3;
            case 4:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "I Can not Connect To Server";
                }
                return HindiUtils.e4;
            case 5:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "I Can't Hear You, Please Try Again";
                }
                return HindiUtils.e5;
            case 6:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "Speech Timeout, Please Try Again";
                }
                return HindiUtils.e6;
            case 7:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "I Don't Understand, Please Try Again";
                }
                return HindiUtils.e7;
            case 8:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "Reconizer Busy, Please Try Later";
                }
                return HindiUtils.e8;
            case 9:
                return HindiUtils.e9;
            default:
                if (HindiUtils.speakLangName.equals("en")) {
                    return "Didn't understand, please try again.";
                }
                return HindiUtils.e7;
        }
    }

    public View onCreateInputView() {
        mContext = getApplicationContext();
        this.emojiDrawable = getResources().getDrawable(R.drawable.emojipressxmlwhite);
        this.themeDrawable = getResources().getDrawable(R.drawable.themepressxmlwhite);
        this.strickerDrawable = getResources().getDrawable(R.drawable.stickerpressxmlwhite);
        this.settingDrawable = getResources().getDrawable(R.drawable.settingpressxmlwhite);
        this.shiftOnDrawable = getResources().getDrawable(this.shiftOnKeys[0]);
        this.spaceDrawable = getResources().getDrawable(this.spaceKeys[0]);
        this.enterDrawable = getResources().getDrawable(this.enterKeys[0]);
        this.deleteDrawable = getResources().getDrawable(this.deleteKeys[0]);
        this.Inputlang_on_drawable = getResources().getDrawable(R.drawable.enable);
        this.Inputlang_off_drawable = getResources().getDrawable(R.drawable.disable);
        this.Speak_btn_drawable = getResources().getDrawable(R.drawable.mic_unpresed);
        this.drop_arrow_drawable = getResources().getDrawable(R.drawable.drop_down);
        this.shiftOffDrawable = getResources().getDrawable(this.shiftOffKeys[0]);
        this.manageClick = false;
        initilizeHeight();
//        this.g = new GujaratiEditBoxSupport();
        this.mCommon_Preferences = new Common_Preferences(this);
        this.vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
            Toast.makeText(getApplicationContext(), "Please Connect To Internet for Phonetic Keyboard", Toast.LENGTH_SHORT).show();
        }

        Constants.languegesArray = new ArrayList<>();
        Constants.languegesArray = Constants.getDefaultLanguageArray();
        Constants.FlagChangeLanguage = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, 0);
        Constants.ChangeLanguage = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, 0);

        HindiUtils.setStaticVariable(getApplicationContext());
        boolean isRecognitionAvailable = SpeechRecognizer.isRecognitionAvailable(getBaseContext());
        this.isSpeechRecoAvalable = isRecognitionAvailable;
        if (isRecognitionAvailable) {
            this.mSpeechReco = SpeechRecognizer.createSpeechRecognizer(this, ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));
        } else {
            dialogAskInstallSTT().show();
        }
        this.mRecoListener = new C03953();
        this.commonResource = new Common_Resource(this);
        this.tmpShowSuggestion = true;
        Constants.wordExist = true;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.prefs = defaultSharedPreferences;
        this.edit = defaultSharedPreferences.edit();
        SelectQuery();
        int i = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_THEME, 0);
        this.selectedTheme = i;
        Constants.SelectTheme = i;
        this.checkflg = false;
        this.textColorCode = char_colorCodes[0];
        this.npd = (NinePatchDrawable) getResources().getDrawable(this.generalKeys_unpresed[0]);
        this.npd_presed = (NinePatchDrawable) getResources().getDrawable(this.generalKeys_presed[0]);
        this.npdShiftOff = (NinePatchDrawable) getResources().getDrawable(this.shiftOffKeys[0]);
        this.npdShiftOn = (NinePatchDrawable) getResources().getDrawable(this.shiftOnKeys[0]);
        this.npdSpace = (NinePatchDrawable) getResources().getDrawable(this.spaceKeys[0]);
        this.npdDelete = (NinePatchDrawable) getResources().getDrawable(this.deleteKeys[0]);
        this.npdDone = (NinePatchDrawable) getResources().getDrawable(this.enterKeys[0]);
        this.popupDrawable = getResources().getDrawable(this.popUpDrawables[0]);
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.ISCOLOR_CODE_CHANGE, false)) {
            this.shiftOffDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.shiftOnDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.enterDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.spaceDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.emojiDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.deleteDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.popupDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.themeDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.settingDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.Inputlang_on_drawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.Inputlang_off_drawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.Speak_btn_drawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.drop_arrow_drawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
        }
        DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
        if (HindiUtils.isUpHoneycomb) {
            dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        } else {
            dictionaryLoadTask.execute(new String[0]);
        }
        switch (i) {
            case 0:
                caps = false;
                View inflate = getLayoutInflater().inflate(R.layout.keypad, (ViewGroup) null);
                this.v = inflate;
                this.headertext = (LinearLayout) inflate.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.content_frame = (FrameLayout) this.v.findViewById(R.id.contentframe);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                LinearLayout linearLayout = (LinearLayout) this.v.findViewById(R.id.btnTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }

                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                linearLayout.setOnClickListener(this.OnClickTheme);
                hideProgressDialog();
                String str = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str;
                this.mNgonNguIn = new NgonNguIn(this, str);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                this.blackTransparentview = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                translayout_height(this.keybpardHeight);
                this.blackTransparentview.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView;
                ClipDrawable clipDrawable = (ClipDrawable) imageView.getDrawable();
                this.mImageDrawable = clipDrawable;
                clipDrawable.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(0);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 1:
                caps = false;
                View inflate2 = getLayoutInflater().inflate(R.layout.keypad1, (ViewGroup) null);
                this.v = inflate2;
                this.headertext = (LinearLayout) inflate2.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                hideProgressDialog();
                String str2 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str2;
                this.mNgonNguIn = new NgonNguIn(this, str2);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView2 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView2;
                ClipDrawable clipDrawable2 = (ClipDrawable) imageView2.getDrawable();
                this.mImageDrawable = clipDrawable2;
                clipDrawable2.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView3 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView3;
                imageView3.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(1);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 2:
                caps = false;
                View inflate3 = getLayoutInflater().inflate(R.layout.keypad2, (ViewGroup) null);
                this.v = inflate3;
                this.headertext = (LinearLayout) inflate3.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str3 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str3;
                this.mNgonNguIn = new NgonNguIn(this, str3);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView4 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView4;
                ClipDrawable clipDrawable3 = (ClipDrawable) imageView4.getDrawable();
                this.mImageDrawable = clipDrawable3;
                clipDrawable3.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView5 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView5;
                imageView5.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(2);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 3:
                caps = false;
                View inflate4 = getLayoutInflater().inflate(R.layout.keypad3, (ViewGroup) null);
                this.v = inflate4;
                this.headertext = (LinearLayout) inflate4.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str4 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str4;
                this.mNgonNguIn = new NgonNguIn(this, str4);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView6 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView6;
                ClipDrawable clipDrawable4 = (ClipDrawable) imageView6.getDrawable();
                this.mImageDrawable = clipDrawable4;
                clipDrawable4.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView7 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView7;
                imageView7.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(3);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 4:
                caps = false;
                View inflate5 = getLayoutInflater().inflate(R.layout.keypad4, (ViewGroup) null);
                this.v = inflate5;
                this.headertext = (LinearLayout) inflate5.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str5 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str5;
                this.mNgonNguIn = new NgonNguIn(this, str5);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView8 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView8;
                ClipDrawable clipDrawable5 = (ClipDrawable) imageView8.getDrawable();
                this.mImageDrawable = clipDrawable5;
                clipDrawable5.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView9 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView9;
                imageView9.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(4);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 5:
                caps = false;
                View inflate6 = getLayoutInflater().inflate(R.layout.keypad5, (ViewGroup) null);
                this.v = inflate6;
                this.headertext = (LinearLayout) inflate6.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str6 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str6;
                this.mNgonNguIn = new NgonNguIn(this, str6);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView10 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView10;
                ClipDrawable clipDrawable6 = (ClipDrawable) imageView10.getDrawable();
                this.mImageDrawable = clipDrawable6;
                clipDrawable6.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView11 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView11;
                imageView11.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(5);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 6:
                caps = false;
                View inflate7 = getLayoutInflater().inflate(R.layout.keypad6, (ViewGroup) null);
                this.v = inflate7;
                this.headertext = (LinearLayout) inflate7.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                hideProgressDialog();
                String str7 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str7;
                this.mNgonNguIn = new NgonNguIn(this, str7);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView12 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView12;
                ClipDrawable clipDrawable7 = (ClipDrawable) imageView12.getDrawable();
                this.mImageDrawable = clipDrawable7;
                clipDrawable7.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView13 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView13;
                imageView13.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(6);
                if (!new MySharePref(mContext).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 7:
                caps = false;
                View inflate8 = getLayoutInflater().inflate(R.layout.keypad7, (ViewGroup) null);
                this.v = inflate8;
                this.headertext = (LinearLayout) inflate8.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                LinearLayout linearLayout2 = (LinearLayout) this.v.findViewById(R.id.btnTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                linearLayout2.setOnClickListener(this.OnClickTheme);
                hideProgressDialog();
                String str8 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str8;
                this.mNgonNguIn = new NgonNguIn(this, str8);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView14 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView14;
                ClipDrawable clipDrawable8 = (ClipDrawable) imageView14.getDrawable();
                this.mImageDrawable = clipDrawable8;
                clipDrawable8.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView15 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView15;
                imageView15.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(7);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 8:
                caps = false;
                View inflate9 = getLayoutInflater().inflate(R.layout.keypad8, (ViewGroup) null);
                this.v = inflate9;
                this.headertext = (LinearLayout) inflate9.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                LinearLayout linearLayout3 = (LinearLayout) this.v.findViewById(R.id.btnTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }

                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                linearLayout3.setOnClickListener(this.OnClickTheme);
                hideProgressDialog();
                String str9 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str9;
                this.mNgonNguIn = new NgonNguIn(this, str9);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView16 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView16;
                ClipDrawable clipDrawable9 = (ClipDrawable) imageView16.getDrawable();
                this.mImageDrawable = clipDrawable9;
                clipDrawable9.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView17 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView17;
                imageView17.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(8);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
            case 9:
                caps = false;
                View inflate10 = getLayoutInflater().inflate(R.layout.keypad9, (ViewGroup) null);
                this.v = inflate10;
                this.headertext = (LinearLayout) inflate10.findViewById(R.id.rl_headertext);
                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
                LinearLayout linearLayout4 = (LinearLayout) this.v.findViewById(R.id.btnTheme);
                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
                if (Constants.ChangeLanguage == 1) {
                    this.LangChange.setVisibility(View.VISIBLE);
                } else {
                    this.LangChange.setVisibility(View.GONE);
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    this.LangChange.setBackgroundResource(R.drawable.enable);
                } else {
                    this.LangChange.setBackgroundResource(R.drawable.disable);
                }
                this.LangChange.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                            if (HindiUtils.isUpHoneycomb) {
                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                dictionaryLoadTask.execute(new String[0]);
                            }
                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                            if (Build.VERSION.SDK_INT >= 11) {
                                HindiUtils.isUpHoneycomb = true;
                            }
                            if (HindiUtils.isUpHoneycomb) {
                                HindiKeypad.this.edit.apply();
                            } else {
                                HindiKeypad.this.edit.commit();
                            }
                            new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                            return;
                        }
                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask2.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                    }
                });
                linearLayout4.setOnClickListener(this.OnClickTheme);
                hideProgressDialog();
                String str10 = this.mCommon_Preferences.getmContryNameIn();
                this.ContryNameIn = str10;
                this.mNgonNguIn = new NgonNguIn(this, str10);
                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
                Timer = (TextView) this.v.findViewById(R.id.txttimer);
                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
                ImageView imageView18 = (ImageView) this.v.findViewById(R.id.imgvoid);
                this.img = imageView18;
                ClipDrawable clipDrawable10 = (ClipDrawable) imageView18.getDrawable();
                this.mImageDrawable = clipDrawable10;
                clipDrawable10.setLevel(0);
                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
                this.closedialog.setOnClickListener(this.CloseDialog);
                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
                this.btnspeaklang.setText(HindiUtils.speakLangName);
                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
                ImageView imageView19 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
                this.blackTransparentview = imageView19;
                imageView19.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
                translayout_height(this.keybpardHeight);
                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
                Giflayout_height(this.keybpardHeight);
                initArrayList(this.v);
                allClickEvent(9);
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                    this.gif_rl_layout.setVisibility(View.INVISIBLE);
                    setkeyboardbackground();
                    break;
                } else {
                    this.gif_rl_layout.setVisibility(View.VISIBLE);
                    setkeyboardGif();
                    break;
                }
        }
        if (i > 9) {
            caps = false;
            View inflate11 = getLayoutInflater().inflate(R.layout.keypad, (ViewGroup) null);
            this.v = inflate11;
            this.headertext = (LinearLayout) inflate11.findViewById(R.id.rl_headertext);
            this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
            this.content_frame = (FrameLayout) this.v.findViewById(R.id.contentframe);
            this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
            this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
            LinearLayout linearLayout5 = (LinearLayout) this.v.findViewById(R.id.btnTheme);
            this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
            if (Constants.ChangeLanguage == 1) {
                this.LangChange.setVisibility(View.VISIBLE);
            } else {
                this.LangChange.setVisibility(View.GONE);
            }
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                this.LangChange.setBackgroundResource(R.drawable.enable);
            } else {
                this.LangChange.setBackgroundResource(R.drawable.disable);
            }
            this.LangChange.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                        DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                        if (HindiUtils.isUpHoneycomb) {
                            dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            dictionaryLoadTask.execute(new String[0]);
                        }
                        HindiKeypad.this.edit.putBoolean("chnagelangu", false);
                        if (Build.VERSION.SDK_INT >= 11) {
                            HindiUtils.isUpHoneycomb = true;
                        }
                        if (HindiUtils.isUpHoneycomb) {
                            HindiKeypad.this.edit.apply();
                        } else {
                            HindiKeypad.this.edit.commit();
                        }
                        new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, false);
                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
                        return;
                    }
                    DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), Constants.FlagChangeLanguage);
                    if (HindiUtils.isUpHoneycomb) {
                        dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                    } else {
                        dictionaryLoadTask2.execute(new String[0]);
                    }
                    HindiKeypad.this.edit.putBoolean("chnagelangu", true);
                    if (Build.VERSION.SDK_INT >= 11) {
                        HindiUtils.isUpHoneycomb = true;
                    }
                    if (HindiUtils.isUpHoneycomb) {
                        HindiKeypad.this.edit.apply();
                    } else {
                        HindiKeypad.this.edit.commit();
                    }
                    new MySharePref(getApplicationContext()).putPrefBoolean(MySharePref.TYPING, true);
                    HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
                }
            });
            linearLayout5.setOnClickListener(this.OnClickTheme);
            hideProgressDialog();
            String str11 = this.mCommon_Preferences.getmContryNameIn();
            this.ContryNameIn = str11;
            this.mNgonNguIn = new NgonNguIn(this, str11);
            this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
            this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
            this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
            this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
            this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
            speakstring = (TextView) this.v.findViewById(R.id.speaktext);
            Timer = (TextView) this.v.findViewById(R.id.txttimer);
            this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
            ImageView imageView20 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
            this.blackTransparentview = imageView20;
            imageView20.setAlpha(((float) new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
            translayout_height(this.keybpardHeight);
            this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
            ImageView imageView21 = (ImageView) this.v.findViewById(R.id.imgvoid);
            this.img = imageView21;
            ClipDrawable clipDrawable11 = (ClipDrawable) imageView21.getDrawable();
            this.mImageDrawable = clipDrawable11;
            clipDrawable11.setLevel(0);
            this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
            this.closedialog.setOnClickListener(this.CloseDialog);
            this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
            this.btnspeaklang.setText(HindiUtils.speakLangName);
            this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
            this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
            Giflayout_height(this.keybpardHeight);
            initArrayList(this.v);
            allClickEvent(0);
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                this.gif_rl_layout.setVisibility(View.VISIBLE);
                setkeyboardGif();
            } else {
                this.gif_rl_layout.setVisibility(View.INVISIBLE);
                setkeyboardbackground();
            }
        }
        if (HindiUtils.savephoto) {
            if (i == 1) {
                this.rl.setBackgroundResource(R.drawable.trans2);
            } else if (i == 2) {
                this.rl.setBackgroundResource(R.drawable.trans3);
            }
        }
        for (Keyboard.Key key : this.keyboard.getKeys()) {
            int parseInt = Integer.parseInt("" + key.codes[0]);
            if (parseInt == -978903) {
                key.icon = this.shiftOffDrawable;
            } else if (parseInt == -2830) {
                key.label = key.label;
            } else if (parseInt == -1) {
                key.icon = this.shiftOffDrawable;
            } else if (parseInt == 32) {
                key.icon = this.spaceDrawable;
            } else if (parseInt == -6003) {
                key.label = key.label;
            } else if (parseInt == -6002) {
                key.label = key.label;
            } else if (parseInt == -5) {
                key.icon = this.deleteDrawable;
            } else if (parseInt == -4) {
                key.icon = this.enterDrawable;
            }
        }
        HindiKeyboardView hindiKeyboardView = this.kv;
        if (hindiKeyboardView != null) {
            hindiKeyboardView.dismissLangPopup();
        }
        this.kv.setOnlineKeyboard(this.npd, this.npd_presed, this.textColorCode, this.npdSpace, this.npdShiftOff, this.npdShiftOn, this.npdDelete, this.npdDone, this.popupDrawable);
        this.kv.setBackgroundDrawable(new BitmapDrawable());
        this.kv.setKeyboard(this.keyboard);
        int i2 = getCurrentInputEditorInfo().imeOptions & 1073742079;
        if (!(i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5 || i2 == 6)) {
            try {
                getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
            } catch (Exception unused) {
                if (Constants.ChangeLanguage == 1 && new MySharePref(mContext).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    this.capsonoffflg = false;
                    caps = false;
                    onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                }
            }
        }
        this.kv.setOnKeyboardActionListener(this);
        this.kv.invalidate();
        HindiUtils.commonView = this.v;
        return this.v;
    }

    public void setkeyboardbackground() {
        if (getResources().getConfiguration().orientation == 1) {
//            if (!HindiUtils.ispotraitbgcolorchange) {
            Bitmap decodeFile = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/photo_save.jpeg");
            if (decodeFile != null) {
                this.rl.setBackgroundDrawable(new BitmapDrawable(decodeFile));
            } else {
                this.rl.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
            }
        }

//        } else if (!HindiUtils.islandscapebgcolorchange) {
//            Bitmap decodeFile2 = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/keyboard_image_land.png");
//            if (decodeFile2 != null) {
//                this.rl.setBackgroundDrawable(new BitmapDrawable(decodeFile2));
//            }
//        } else {
//            this.rl.setBackgroundColor(HindiUtils.defaultBgColor);

    }

    public void setkeyboardGif() {
        this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//        if (HindiUtils.SelectedGifPath.substring(0, 8).equals("/storage")) {
        Glide.with((Context) this).asGif().load(new File(getApplicationContext().getFilesDir(), "Gif_save.gif").getAbsolutePath()).placeholder((int) R.drawable.rain).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//        } else if (HindiUtils.SelectedGifPath.substring(0, 4).equals("http")) {
//            Glide.with((Context) this).asGif().load(HindiUtils.SelectedGifPath).placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//        } else {
//            Glide.with((Context) this).asGif().load(Uri.parse(HindiUtils.SelectedGifPath)).placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//        }
    }

    private void allClickEvent(final int i) {
        this.v.findViewById(R.id.btn_emoji).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.onKey(Constants.CODE_EMOJI, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                HindiKeypad.this.setTabBg(0, i);
                HindiKeypad.caps = false;
            }
        });
        this.v.findViewById(R.id.emojis_tab_1_delete).setOnTouchListener(new RepeatButtonListener(400, 100, new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.deleteemoji();
            }
        }));
        this.v.findViewById(R.id.food_tab_1_emoji).setOnTouchListener(new RepeatButtonListener(400, 100, new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.getFood();
                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        }));
        this.v.findViewById(R.id.emojis_tab_1_car).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.getcar();
                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        this.v.findViewById(R.id.emojis_tab_1_symbol).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.getSymbols();
                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        this.v.findViewById(R.id.emojis_tab_1_flower).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.getFlower();
                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        this.v.findViewById(R.id.emojis_tab_1_electronics).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.getElectronics();
                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        this.v.findViewById(R.id.emojis_tab_1_bell).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.getBell();
                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
            }
        });
        this.v.findViewById(R.id.emojis_tab_1_people).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HindiKeypad.this.icons = null;
                HindiKeypad.this.icons = new ArrayList<>();
                HindiKeypad.this.otherContentLay.removeAllViews();
                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
                HindiKeypad.this.initEmojiAdapter();
                HindiKeypad.this.otherContentLay.addView(HindiKeypad.this.emojigrid);
            }
        });
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.ISCOLOR_CODE_CHANGE, false)) {
            this.shiftOnDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.shiftOffDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.spaceDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.enterDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.deleteDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.popupDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.emojiDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.themeDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.strickerDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.settingDrawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.Inputlang_on_drawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.Inputlang_off_drawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.Speak_btn_drawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.drop_arrow_drawable.setColorFilter(new PorterDuffColorFilter(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)), PorterDuff.Mode.SRC_IN));
            this.btnspeaklang.setTextColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white)));
            if (Build.VERSION.SDK_INT >= 16) {
                this.v.findViewById(R.id.btn_emoji).setBackground(this.emojiDrawable);
                this.v.findViewById(R.id.btnTheme).setBackground(this.themeDrawable);
                this.v.findViewById(R.id.btn_setting).setBackground(this.settingDrawable);
                return;
            }
            this.v.findViewById(R.id.btnTheme).setBackgroundDrawable(this.themeDrawable);
            this.v.findViewById(R.id.btn_emoji).setBackgroundDrawable(this.emojiDrawable);
            this.v.findViewById(R.id.btn_setting).setBackgroundDrawable(this.settingDrawable);
        }
    }

    public void onStartInputView(EditorInfo editorInfo, boolean z) {
        super.onStartInputView(editorInfo, z);
        Log.d("main", "start inpitview");
        if (!this.isPopup) {
            setInputView(onCreateInputView());
            this.isPopup = false;
        }
        int i = editorInfo.inputType & 4080;
        if ((editorInfo.inputType & 15) == 1) {
            if (i == 128 || i == 144) {
                this.tmpShowSuggestion = false;
            }
            if (i == 32) {
                this.tmpShowSuggestion = false;
            } else if (i == 16) {
                this.tmpShowSuggestion = false;
            } else if (i != 64) {
                if (i == 176) {
                    this.tmpShowSuggestion = false;
                } else if (i == 160) {
                    int i2 = editorInfo.inputType;
                }
            }
            if ((editorInfo.inputType & 524288) != 0) {
                this.tmpShowSuggestion = false;
            }
            if ((editorInfo.inputType & 32768) == 0) {
                int i3 = editorInfo.inputType;
            }
            if ((editorInfo.inputType & 65536) != 0) {
                this.tmpShowSuggestion = false;
                this.mCompletionOn = isFullscreenMode();
            }
        }
        int i4 = getCurrentInputEditorInfo().imeOptions & 1073742079;
        if (i4 != 2 && i4 != 3 && i4 != 4 && i4 != 5 && i4 != 6) {
            try {
                HindiUtils.showsugg = true;
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && this.tmpShowSuggestion && HindiUtils.selectedLang == 1) {
                    this.capsonoffflg = false;
                    caps = true;
                    this.kv.setShifted(true);
                    this.kv.invalidate();
                    this.kv.invalidateAllKeys();
                }
            } catch (Exception unused) {
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && this.tmpShowSuggestion) {
                    this.capsonoffflg = false;
                    caps = false;
                    this.kv.setShifted(false);
                    this.kv.invalidate();
                    this.kv.invalidateAllKeys();
                }
            }
        }
    }

    public boolean onEvaluateInputViewShown() {
        try {
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.DEFAULT_GIF, false)) {
                if (this.v != null) {
                    if (getResources().getConfiguration().orientation == 1) {
//                        this.isLandscape = false;
//                        if (!HindiUtils.ispotraitbgcolorchange) {
                        Glide.with(this).clear(gifimage);
                        Glide.with((Context) this).asGif().load(new File(getApplicationContext().getFilesDir(), "Gif_save.gif").getAbsolutePath()).placeholder((int) R.drawable.rain).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//                        if (HindiUtils.SelectedGifPath.substring(0, 8).equals("/storage")) {
//                            Glide.with((Context) this).asGif().load(HindiUtils.SelectedGifPath).placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//                        } else if (HindiUtils.SelectedGifPath.substring(0, 4).equals("http")) {
//                            Glide.with((Context) this).asGif().load(HindiUtils.SelectedGifPath).placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//                        } else {
//                            Glide.with((Context) this).asGif().load(Uri.parse(HindiUtils.SelectedGifPath)).placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//                        }
                    } else {
                        this.rl.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
                    }
//                    } else {
//                        this.isLandscape = false;
//                        if (!HindiUtils.islandscapebgcolorchange) {
//                            Glide.with(this).clear(gifimage);
//                            if (HindiUtils.SelectedGifPath.substring(0, 8).equals("/storage")) {
//                                Glide.with((Context) this).asGif().load(HindiUtils.SelectedGifPath).placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//                            } else if (HindiUtils.SelectedGifPath.substring(0, 4).equals("http")) {
//                                Glide.with((Context) this).asGif().load(HindiUtils.SelectedGifPath).placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//                            } else {
//                                Glide.with((Context) this).asGif().load(Uri.parse(HindiUtils.SelectedGifPath)).placeholder((int) R.drawable.ic_launcher).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.gifimage);
//                            }
//                        } else {
//                            this.rl.setBackgroundColor(HindiUtils.defaultBgColor);
//                        }
//                    }
                }
            } else if (this.v != null) {
                if (getResources().getConfiguration().orientation == 1) {
//                    this.isLandscape = false;
//                    if (!HindiUtils.ispotraitbgcolorchange) {
                    Bitmap decodeFile = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/photo_save.jpeg");
                    if (decodeFile != null) {
                        this.rl.setBackgroundDrawable(new BitmapDrawable(decodeFile));
                    }
                } else {
                    this.rl.setBackgroundColor(new MySharePref(getApplicationContext()).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
                }
//                } else {
//                    this.isLandscape = false;
//                    if (!HindiUtils.islandscapebgcolorchange) {
//                        Bitmap decodeFile2 = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/keyboard_image_land.png");
//                        if (decodeFile2 != null) {
//                            this.rl.setBackgroundDrawable(new BitmapDrawable(decodeFile2));
//                        }
//                    } else {
//                        this.rl.setBackgroundColor(HindiUtils.defaultBgColor);
//                    }
//                }
            }
        } catch (Exception unused) {
        }
        return super.onEvaluateInputViewShown();
    }

    public String method(String str) {
        return (str == null || str.length() <= 0 || str.charAt(str.length() + -1) != 'x') ? str : str.substring(0, str.length() - 1);
    }

    @SuppressLint("ResourceType")
    public void onKey(int i, int[] iArr) {
        final int i2 = i;
        System.out.println("----- - - -i2i2i2i2 : " + i2);
        final int[] iArr2 = iArr;
        final InputConnection currentInputConnection = getCurrentInputConnection();
        if (Constants.ChangeLanguage == 1) {
            this.LangChange.setVisibility(View.VISIBLE);
        } else {
            this.LangChange.setVisibility(View.GONE);
        }
        this.kv.setVisibility(View.VISIBLE);
        if (this.headertext.getVisibility() == View.VISIBLE) {
            this.headertext.setVisibility(View.GONE);
        }
        if (HindiUtils.deleteFlg) {
            if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                caps = true;
                this.capsonoffflg = false;
                this.kv.setShifted(true);
            }
            this.kv.invalidateAllKeys();
            HindiUtils.deleteFlg = false;
        } else if (i2 == -978903) {
            Constants.wordExist = true;
            CapsOn();
            caps = true;
            this.capsonoffflg = true;
            this.newcapital = true;
        } else if (i2 == -6003) {
            Constants.wordExist = true;
            this.checkflg = true;
            MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, allNumericQuerty[Constants.ChangeLanguage], this.keybpardHeight, 1);
            this.keyboard = myKeypadDataView;
            this.kv.setKeyboard(myKeypadDataView);
            for (Keyboard.Key key : this.keyboard.getKeys()) {
                int parseInt = Integer.parseInt("" + key.codes[0]);
                if (parseInt == -978903) {
                    key.icon = this.shiftOffDrawable;
                } else if (parseInt == -2830) {
                    key.label = key.label;
                } else if (parseInt == -1) {
                    key.icon = this.shiftOffDrawable;
                } else if (parseInt == 32) {
                    key.icon = this.spaceDrawable;
                } else if (parseInt == -6003) {
                    key.label = key.label;
                } else if (parseInt == -6002) {
                    key.label = key.label;
                } else if (parseInt == -5) {
                    key.icon = this.deleteDrawable;
                } else if (parseInt == -4) {
                    key.icon = this.enterDrawable;
                }
            }
            this.kv.invalidateAllKeys();
            caps = false;
        } else if (i2 == -5000) {
            HindiKeyboardView hindiKeyboardView = this.kv;
            if (hindiKeyboardView != null) {
                hindiKeyboardView.dismissLangPopup();
                this.kv.dismissPreviewPopup();
            }
            if (!this.manageClick) {
                Constants.wordExist = true;
                if (this.mainMenu.getVisibility() == View.GONE) {
                    this.hintword.setVisibility(View.GONE);
                    this.result = null;
                    this.result = new ArrayList<>();
                    this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
                    this.mainMenu.setVisibility(View.VISIBLE);
                }
                this.headertext.setVisibility(View.VISIBLE);
                Constants.temp_flag = 1;
                initEmojiAdapter();
                this.kv.setVisibility(View.GONE);
                this.otherContentLay.setVisibility(View.VISIBLE);
                this.r2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), 17432576));
                this.otherContentLay.removeAllViews();
                this.otherContentLay.addView(this.emojigrid);
            } else {
                this.otherContentLay.removeAllViews();
                this.otherContentLay.setVisibility(View.GONE);
                setKeyboardData();
            }
            this.manageClick = !this.manageClick;
        } else if (i2 == -1763) {
            Constants.wordExist = true;
            this.checkflg = true;
            MyKeypadDataView myKeypadDataView2 = new MyKeypadDataView(this, R.xml.numeric_shift_querty, this.keybpardHeight, 1);
            this.keyboard = myKeypadDataView2;
            this.kv.setKeyboard(myKeypadDataView2);
            for (Keyboard.Key key2 : this.keyboard.getKeys()) {
                int parseInt2 = Integer.parseInt("" + key2.codes[0]);
                if (parseInt2 == -978903) {
                    key2.icon = this.shiftOffDrawable;
                } else if (parseInt2 == -2830) {
                    key2.label = key2.label;
                } else if (parseInt2 == -1) {
                    key2.icon = this.shiftOffDrawable;
                } else if (parseInt2 == 32) {
                    key2.icon = this.spaceDrawable;
                } else if (parseInt2 == -6003) {
                    key2.label = key2.label;
                } else if (parseInt2 == -6002) {
                    key2.label = key2.label;
                } else if (parseInt2 == -5) {
                    key2.icon = this.deleteDrawable;
                } else if (parseInt2 == -4) {
                    key2.icon = this.enterDrawable;
                }
            }
            this.kv.invalidateAllKeys();
            caps = false;
        } else if (i2 == -1) {
            Constants.wordExist = true;
            this.newcapital = false;
            caps = !caps;
            if (Constants.FlagChangeLanguage == 1) {
                if (!new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    if (caps) {
                        this.capsonoffflg = false;
                        this.kv.invalidateAllKeys();
                    } else {
                        this.capsonoffflg = true;
                        this.kv.invalidateAllKeys();
                    }
                    this.kv.setShifted(caps);
                } else if (caps) {
                    SelectQuertyShiftOn();
                    this.capsonoffflg = false;
                    this.kv.invalidateAllKeys();
                } else {
                    SelectQuertyShiftOff();
                    this.capsonoffflg = true;
                    this.kv.invalidateAllKeys();
                }
            } else if (caps) {
                CapsOn();
                caps = true;
                this.capsonoffflg = true;
                this.newcapital = true;
                this.kv.invalidateAllKeys();
            } else {
                SelectQuertyShiftOff();
                this.capsonoffflg = true;
                this.kv.invalidateAllKeys();
            }
        } else if (i2 == 66) {
        } else {
            if (i2 == -2831) {
                Constants.wordExist = true;
                setKeyboardData();
            } else if (i2 == -2830) {
                Constants.wordExist = true;
                this.checkflg = false;
                MyKeypadDataView myKeypadDataView3 = new MyKeypadDataView(this, this.defaultquerty[Constants.ChangeLanguage], this.keybpardHeight, 0);
                this.keyboard = myKeypadDataView3;
                this.kv.setKeyboard(myKeypadDataView3);
                for (Keyboard.Key key3 : this.keyboard.getKeys()) {
                    int parseInt3 = Integer.parseInt("" + key3.codes[0]);
                    if (parseInt3 == -978903) {
                        key3.icon = this.shiftOffDrawable;
                    } else if (parseInt3 == -2830) {
                        key3.label = key3.label;
                    } else if (parseInt3 == -1) {
                        key3.icon = this.shiftOffDrawable;
                    } else if (parseInt3 == 32) {
                        key3.icon = this.spaceDrawable;
                    } else if (parseInt3 == -6003) {
                        key3.label = key3.label;
                    } else if (parseInt3 == -6002) {
                        key3.label = key3.label;
                    } else if (parseInt3 == -5) {
                        key3.icon = this.deleteDrawable;
                    } else if (parseInt3 == -4) {
                        key3.icon = this.enterDrawable;
                    }
                }
                this.kv.invalidateAllKeys();
                try {
                    if (this.newcapital) {
                        CapsOn();
                        this.capsonoffflg = true;
                        caps = true;
                    }
                    char charAt = getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
                    if (Character.isLetter(charAt) && Character.isUpperCase(charAt) && !this.newcapital && Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                        this.capsonoffflg = false;
                        caps = false;
                        onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                    }
                } catch (Exception unused) {
                    if (!this.newcapital && Constants.ChangeLanguage == 1) {
                        caps = true;
                        this.capsonoffflg = false;
                        SelectQuertyShiftOn();
                    }
                }
            } else if (i2 == -5) {
                HindiKeyboardView hindiKeyboardView2 = this.kv;
                if (hindiKeyboardView2 != null) {
                    hindiKeyboardView2.dismissPreviewPopup();
                }
                if (this.mainMenu.getVisibility() == View.GONE) {
                    this.hintword.setVisibility(View.GONE);
                    this.result = null;
                    this.result = new ArrayList<>();
                    this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
                    this.mainMenu.setVisibility(View.VISIBLE);
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
                    if (i3 != 2 && i3 != 3 && i3 != 4 && i3 != 5 && i3 != 6 && !this.newcapital && !this.checkflg && Constants.ChangeLanguage == 1) {
                        deleteText(currentInputConnection.getExtractedText(new ExtractedTextRequest(), 0).text.toString(), charAt2);
                    }
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            HindiKeypad.this.showSuggestion(currentInputConnection, i2, iArr2);
                        }
                    }, 500);
                } catch (Exception e) {
                    HindiUtils.deleteFlg = false;
                    int i4 = getCurrentInputEditorInfo().imeOptions & 1073742079;
                    if (i4 != 2 && i4 != 3 && i4 != 4 && i4 != 5 && i4 != 6) {
                        Log.d("main", "Exception deleting no char " + e);
                        if (Constants.FlagChangeLanguage != 0) {
                            this.capsonoffflg = false;
                            this.kv.setShifted(true);
                            this.kv.invalidate();
                            this.kv.invalidateAllKeys();
                            this.tmpshiftonoff = true;
                        }
                    }
                }

            } else if (i2 == -4) {
                Constants.wordExist = true;
                if (this.mainMenu.getVisibility() == View.GONE) {
                    this.hintword.setVisibility(View.GONE);
                    this.result = null;
                    this.result = new ArrayList<>();
                    this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
                    this.mainMenu.setVisibility(View.VISIBLE);
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
                    if (!this.newcapital && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                        this.capsonoffflg = false;
                        caps = true;
                        this.kv.setShifted(true);
                        this.kv.invalidate();
                        this.kv.invalidateAllKeys();
                    }
                } else {
                    currentInputConnection.performEditorAction(6);
                }
            } else if (i2 != -97886) {
                char c = (char) i2;
                if (Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.TYPING, true)) {
                    if (this.counter == 0 && i2 == 32) {
                        String str = "" + currentInputConnection.getTextBeforeCursor(Integer.MAX_VALUE, 0);
                        this.FinalWord = str;
                        String substring = str.substring(str.lastIndexOf(" ") + 1);
                        this.lastWord = substring;
                        currentInputConnection.deleteSurroundingText(substring.length() + 1, 0);
                        new AsyncTask<Void, Void, Void>() {
                            public void onPreExecute() {
                            }

                            public void onProgressUpdate(Void... voidArr) {
                            }

                            public Void doInBackground(Void... voidArr) {
                                try {
                                    HindiKeypad.this.httpURLConnection = null;
                                    URL url = new URL("http://www.google.com/inputtools/request");
                                    HindiKeypad hindiKeypad = HindiKeypad.this;
                                    hindiKeypad.urlString = "text=" + HindiKeypad.this.lastWord + "&ime=transliteration_en_hi";
                                    HindiKeypad.this.httpURLConnection = (HttpURLConnection) url.openConnection();
                                    HindiKeypad.this.httpURLConnection.setRequestMethod("GET");
                                    HindiKeypad.this.httpURLConnection.setConnectTimeout(5000);
                                    HindiKeypad.this.httpURLConnection.setReadTimeout(5000);
                                    HindiKeypad.this.httpURLConnection.setDoOutput(true);
                                    HindiKeypad.this.httpURLConnection.getOutputStream().write(HindiKeypad.this.urlString.getBytes("UTF8"));
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(HindiKeypad.this.httpURLConnection.getInputStream(), "UTF-8"));
                                    HindiKeypad.this.stringBuilder2 = new StringBuilder();
                                    while (true) {
                                        int read = bufferedReader.read();
                                        if (read == -1) {
                                            break;
                                        }
                                        HindiKeypad.this.stringBuilder2.append((char) read);
                                    }
                                } catch (Exception unused) {
                                }
                                return null;
                            }

                            public void onPostExecute(Void voidR) {
                                Pattern compile = Pattern.compile("\"([^\"]*)\"");
                                Matcher matcher = compile.matcher("" + HindiKeypad.this.stringBuilder2);
                                while (matcher.find()) {
                                    HindiKeypad.this.FinalString.add(matcher.group(1));
                                }
                                try {
                                    Constants.dictionaryword = HindiKeypad.this.FinalString.get(2);
                                    InputConnection inputConnection = currentInputConnection;
                                    inputConnection.commitText(HindiKeypad.this.FinalString.get(2) + " ", 1);
                                    HindiKeypad.this.lastWord = "";
                                    HindiKeypad.this.FinalString.clear();
                                } catch (Exception unused) {
                                    InputConnection inputConnection2 = currentInputConnection;
                                    inputConnection2.commitText(HindiKeypad.this.lastWord + " ", 1);
                                    HindiKeypad.this.lastWord = "";
                                    HindiKeypad.this.FinalString.clear();
                                }
                            }
                        }.execute(new Void[0]);
                        this.counter++;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                HindiKeypad.this.showSuggestion(currentInputConnection, i2, iArr2);
                            }
                        }, 500);
                    }
                    else {
                        this.counter = 0;
                    }
                }
                if (!Character.isLetter(c) || !caps) {
                    currentInputConnection.commitText(String.valueOf(c), 1);
                    if (i2 == 46 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true) && this.tmpShowSuggestion && Constants.FlagChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                        caps = true;
                        this.capsonoffflg = false;
                        this.kv.setShifted(true);
                    }
                    if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SUGGESTION, true) && this.tmpShowSuggestion && !HindiUtils.previewActivityisOpen && !checkLanguage) {
                        showSuggestion(currentInputConnection, i2, iArr2);
                    }
                    if (i2 >= 97 && i2 <= 122) {
                        this.capsonoffflg = true;
                        return;
                    }
                    return;
                }
                currentInputConnection.commitText(String.valueOf(Character.toUpperCase(c)), 1);
                if (!this.capsonoffflg && Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    this.capsonoffflg = true;
                    onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
                }
                if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SUGGESTION, true) && this.tmpShowSuggestion && !HindiUtils.previewActivityisOpen && !checkLanguage) {
                    showSuggestion(currentInputConnection, i2, iArr2);
                }
            }
        }
    }

    public void showSuggestion(InputConnection inputConnection, int i, int[] iArr) {
        try {
            this.word = "";
            if (checkLanguage) {
                this.textdatas = Constants.dictionaryword;
            } else {
                this.textdatas = inputConnection.getTextBeforeCursor(Integer.MAX_VALUE, 0);
            }
            int length = this.textdatas.toString().length();
            if (length >= 1) {
                boolean z = false;
                while (true) {
                    if (length != 0) {
                        int i2 = length - 1;
                        char charAt = this.textdatas.toString().charAt(i2);
                        if (charAt == 10 || charAt == ',') {
                            break;
                        } else if (charAt == '.') {
                            break;
                        } else if (charAt != ' ') {
                            this.word += this.textdatas.toString().charAt(i2);
                            if (Constants.SuggestedView) {
                                this.hintword.setVisibility(View.VISIBLE);
                                this.mainMenu.setVisibility(View.GONE);
                                z = true;
                            }
                            length--;
                        } else {
                            Constants.wordExist = true;
                            if (Constants.SuggestedView && !z) {
                                this.hintword.setVisibility(View.GONE);
                                this.result = null;
                                this.result = new ArrayList<>();
                                this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
                                this.mainMenu.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        break;
                    }
                }
                Constants.wordExist = true;
                if (Constants.SuggestedView && !z) {
                    this.hintword.setVisibility(View.GONE);
                    this.result = null;
                    this.result = new ArrayList<>();
                    this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
                    this.mainMenu.setVisibility(View.VISIBLE);
                }
                this.word = new StringBuilder(this.word).reverse().toString();
                myAsyncTask myasynctask = new myAsyncTask();
                if (HindiUtils.isUpHoneycomb) {
                    myasynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                } else {
                    myasynctask.execute(new Void[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getGujarati(String str) {
        if (!str.equals("")) {
            ArrayList<String> suggestion = HindiUtils.getSuggestion(str);
            this.result = suggestion;
            if (suggestion.size() >= 1) {
                Collections.sort(this.result, new Comparator<String>() {
                    public int compare(String str, String str2) {
                        return str.compareToIgnoreCase(str2);
                    }
                });
                hlist.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(this, this.result, this.selectedTheme, hlist.getWidth()));
            } else if (this.result.size() <= 0) {
                this.result = null;
                ArrayList<String> arrayList = new ArrayList<>();
                this.result = arrayList;
                arrayList.add(str);
                this.result.add("Touch to add");
                hlist.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(this, this.result, this.selectedTheme, hlist.getWidth()));
            }
        } else {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add("");
            hlist.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(this, arrayList2, this.selectedTheme, hlist.getWidth()));
        }
    }

    @SuppressLint("WrongConstant")
    private void playClick(int i) {
        int i2 = i != -5 ? i != -4 ? i != 32 ? 5 : 6 : 8 : 7;
        if (((double) new MySharePref(getApplicationContext()).getPrefFloat(MySharePref.SOUND_PROGRESS_FLOAT, 0.3f)) != 0.0d) {
            this.mAudioManager.playSoundEffect(i2, new MySharePref(getApplicationContext()).getPrefFloat(MySharePref.SOUND_PROGRESS_FLOAT, 0.3f));
        }
    }

    public void onFinishInput() {
        Log.d("main", "finish input");
        HindiKeyboardView hindiKeyboardView = this.kv;
        if (hindiKeyboardView != null) {
            hindiKeyboardView.dismissLangPopup();
        }
        super.onFinishInput();
    }

    public void onPress(int i) {
        this.kv.setPreviewEnabled(false);
        this.kv.dismissLangPopup();
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.POPUP, true)) {
            this.kv.onPressKey(i);
        }
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.VIBRATION, false)) {
            vibrate();
        }
        if (new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.SOUND_ENABLE, true)) {
            playClick(i);
        }
    }

    public void vibrate() {
        long j = this.mKeypressVibrationDuration;
        if (j < 0) {
            HindiKeyboardView hindiKeyboardView = this.kv;
            if (hindiKeyboardView != null) {
                hindiKeyboardView.performHapticFeedback(3, 2);
                return;
            }
            return;
        }
        SetVibrateCompact setVibrateCompact = this.mVibrator;
        if (setVibrateCompact != null) {
            setVibrateCompact.vibrate(j);
        }
    }

    public void onRelease(int i) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                HindiKeypad.this.kv.dismissPreviewPopup();
            }
        }, 100);
    }

    public void initEmojiAdapter() {
        this.otherContentLay.removeAllViews();
        this.icons = null;
        this.icons = new ArrayList<>();
        for (int i = 1; i <= 189; i++) {
            ArrayList<String> arrayList = this.icons;
            arrayList.add("" + i);
        }
        this.emojigrid = null;
        GridView gridView = new GridView(this);
        this.emojigrid = gridView;
        gridView.setNumColumns(8);
        this.emojigrid.setGravity(17);
        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), People.DATA, 0));
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
        this.otherContentLay.removeAllViews();
        this.icons = null;
        this.icons = new ArrayList<>();
        for (int i = 1; i <= 116; i++) {
            ArrayList<String> arrayList = this.icons;
            arrayList.add("f" + i);
        }
        this.emojigrid = null;
        GridView gridView = new GridView(this);
        this.emojigrid = gridView;
        gridView.setNumColumns(8);
        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Nature.DATA, 1));
        this.otherContentLay.addView(this.emojigrid);
    }

    public void getElectronics() {
        this.otherContentLay.removeAllViews();
        this.icons = null;
        this.icons = new ArrayList<>();
        for (int i = 1; i <= 116; i++) {
            ArrayList<String> arrayList = this.icons;
            arrayList.add("f" + i);
        }
        this.emojigrid = null;
        GridView gridView = new GridView(this);
        this.emojigrid = gridView;
        gridView.setNumColumns(8);
        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Electr.DATA, 6));
        this.otherContentLay.addView(this.emojigrid);
    }
    public void getBell() {
        this.otherContentLay.removeAllViews();
        this.icons = null;
        this.icons = new ArrayList<>();
        for (int i = 1; i <= 229; i++) {
            ArrayList<String> arrayList = this.icons;
            arrayList.add("b" + i);
        }
        this.emojigrid = null;
        GridView gridView = new GridView(this);
        this.emojigrid = gridView;
        gridView.setNumColumns(8);
        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Sport.DATA, 2));
        this.otherContentLay.addView(this.emojigrid);
    }

    public void getcar() {
        this.otherContentLay.removeAllViews();
        this.icons = null;
        this.icons = new ArrayList<>();
        for (int i = 1; i <= 98; i++) {
            ArrayList<String> arrayList = this.icons;
            arrayList.add("c" + i);
        }
        this.emojigrid = null;
        GridView gridView = new GridView(this);
        this.emojigrid = gridView;
        gridView.setNumColumns(8);
        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Cars.DATA, 3));
        this.otherContentLay.addView(this.emojigrid);
    }

    public void getFood() {
        this.otherContentLay.removeAllViews();
        this.icons = null;
        this.icons = new ArrayList<>();
        for (int i = 1; i <= 98; i++) {
            ArrayList<String> arrayList = this.icons;
            arrayList.add("c" + i);
        }
        this.emojigrid = null;
        GridView gridView = new GridView(this);
        this.emojigrid = gridView;
        gridView.setNumColumns(8);
        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Food.DATA, 5));
        this.otherContentLay.addView(this.emojigrid);
    }

    public void getSymbols() {
        this.otherContentLay.removeAllViews();
        this.icons = null;
        this.icons = new ArrayList<>();
        for (int i = 1; i <= 206; i++) {
            ArrayList<String> arrayList = this.icons;
            arrayList.add("s" + i);
        }
        this.emojigrid = null;
        GridView gridView = new GridView(this);
        this.emojigrid = gridView;
        gridView.setNumColumns(8);
        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Symbols.DATA, 4));
        this.otherContentLay.addView(this.emojigrid);
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
        Iterator<ImageButton> it2 = this.btnArray.iterator();
        while (it2.hasNext()) {
            ImageButton next = it2.next();
            int parseInt = Integer.parseInt((String) next.getTag());
            if (parseInt == i) {
                next.setBackgroundResource(this.resid[i]);
            } else {
                next.setBackgroundResource(this.selector[parseInt]);
            }
        }
    }

    private void initArrayList(View view) {
        setHintString();
        this.btnArray = null;
        ArrayList<ImageButton> arrayList = new ArrayList<>();
        this.btnArray = arrayList;
        arrayList.add((ImageButton) view.findViewById(R.id.emojis_tab_1_people));
        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_flower));
        this.btnArray.add((ImageButton) view.findViewById(R.id.food_tab_1_emoji));
        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_bell));
        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_car));
        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_electronics));
        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_symbol));
        this.mainMenu = (LinearLayout) this.v.findViewById(R.id.main_patti);
        this.mVibrator = SetVibrateCompact.getInstance(this);
        this.otherContents = (RelativeLayout) this.v.findViewById(R.id.otherContents);
        this.mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        this.otherContentLay = (RelativeLayout) this.v.findViewById(R.id.otherContents);
        this.btnSpeak = (LinearLayout) this.v.findViewById(R.id.btnmic);
        ((LinearLayout) this.v.findViewById(R.id.btn_setting)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(HindiKeypad.this.getApplicationContext(), SettingActivity.class);
                    intent.addFlags(335577088);
                    intent.putExtra("backflg", true);
                    HindiKeypad.this.startActivity(intent);
                } catch (Exception unused) {
                }
            }
        });
        this.btnSpeak.setOnClickListener(this.SpeakbtnDialog);
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

    public int getResId(String str, Class<?> cls) {
        return getResources().getIdentifier(str, "drawable", getPackageName());
    }

    public void SetKeyBoardLayout1() {
        this.newcapital = false;
        onKey(Constants.CODE_ALPHABETS, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
        this.headertext.setVisibility(View.GONE);
        this.kv.setVisibility(View.VISIBLE);
        if (!this.newcapital && Constants.ChangeLanguage == 1 && new MySharePref(getApplicationContext()).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
            this.capsonoffflg = false;
            caps = false;
            onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
        }
    }

    private void SelectQuery() {
        this.keyboard = new MyKeypadDataView(this, this.defaultquerty[Constants.ChangeLanguage], this.keybpardHeight, 0);
    }

    private void SelectQuertyShiftOn() {
        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, this.capsquerty[Constants.ChangeLanguage], this.keybpardHeight, 0);
        this.keyboard = myKeypadDataView;
        this.kv.setKeyboard(myKeypadDataView);
        for (Keyboard.Key key : this.keyboard.getKeys()) {
            int parseInt = Integer.parseInt("" + key.codes[0]);
            if (parseInt == -978903) {
                key.icon = this.shiftOnDrawable;
            } else if (parseInt == -2830) {
                key.label = key.label;
            } else if (parseInt == -1) {
                key.icon = this.shiftOnDrawable;
            } else if (parseInt == 32) {
                key.icon = this.spaceDrawable;
            } else if (parseInt == -6003) {
                key.label = key.label;
            } else if (parseInt == -6002) {
                key.label = key.label;
            } else if (parseInt == -5) {
                key.icon = this.deleteDrawable;
                key.repeatable = true;
            } else if (parseInt == -4) {
                key.icon = this.enterDrawable;
            }
        }
        this.kv.invalidateAllKeys();
    }

    private void SelectQuertyShiftOff() {
        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, this.defaultquerty[Constants.ChangeLanguage], this.keybpardHeight, 0);
        this.keyboard = myKeypadDataView;
        this.kv.setKeyboard(myKeypadDataView);
        for (Keyboard.Key key : this.keyboard.getKeys()) {
            int parseInt = Integer.parseInt("" + key.codes[0]);
            if (parseInt == -978903) {
                key.icon = this.shiftOffDrawable;
            } else if (parseInt == -2830) {
                key.label = key.label;
            } else if (parseInt == -1) {
                key.icon = this.shiftOffDrawable;
            } else if (parseInt == 32) {
                key.icon = this.spaceDrawable;
            } else if (parseInt == -6003) {
                key.label = key.label;
            } else if (parseInt == -6002) {
                key.label = key.label;
            } else if (parseInt == -5) {
                key.icon = this.deleteDrawable;
                key.repeatable = true;
            } else if (parseInt == -4) {
                key.icon = this.enterDrawable;
            }
        }
        this.kv.invalidateAllKeys();
    }

    public void CapsOn() {
        caps = false;
        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, this.capsOnquerty[Constants.ChangeLanguage], this.keybpardHeight, 0);
        this.keyboard = myKeypadDataView;
        this.kv.setKeyboard(myKeypadDataView);
        for (Keyboard.Key key : this.keyboard.getKeys()) {
            int parseInt = Integer.parseInt("" + key.codes[0]);
            if (parseInt == -978903) {
                key.icon = this.shiftOnDrawable;
            } else if (parseInt == -2830) {
                key.label = key.label;
            } else if (parseInt == -1) {
                key.icon = this.shiftOnDrawable;
            } else if (parseInt == 32) {
                key.icon = this.spaceDrawable;
            } else if (parseInt == -6003) {
                key.label = key.label;
            } else if (parseInt == -6002) {
                key.label = key.label;
            } else if (parseInt == -5) {
                key.icon = this.deleteDrawable;
            } else if (parseInt == -4) {
                key.icon = this.enterDrawable;
            }
        }
        this.kv.invalidateAllKeys();
    }

    private void deleteText(String str, char c) {
        if (Character.isLetter(c) && Character.isUpperCase(c)) {
            caps = true;
            this.capsonoffflg = false;
            this.kv.setShifted(true);
            this.kv.invalidateAllKeys();
        } else if ((Character.isLetter(c) && Character.isLowerCase(c)) || c == 10) {
            caps = false;
            this.capsonoffflg = true;
            this.kv.setShifted(false);
            this.kv.invalidateAllKeys();
        }
    }

    public void setKeyboardData() {
        if (Constants.temp_flag == 1) {
            Constants.temp_flag = 0;
            this.headertext.setVisibility(View.GONE);
            this.capsonoffflg = false;
            caps = true;
            this.kv.setVisibility(View.VISIBLE);
            return;
        }
        try {
            this.headertext.setVisibility(View.GONE);
            this.kv.init();
            int i = Constants.FlagChangeLanguage;
            if (i == 0) {
                Constants.ChangeLanguage = 0;
                SetKeyBoardLayout1();
                Constants.selectedLanguageName = "Hindi";
            } else if (i == 1) {
                Constants.ChangeLanguage = 1;
                SetKeyBoardLayout1();
                Constants.selectedLanguageName = "English";
                if (new MySharePref(mContext).getPrefBoolean(MySharePref.AUTO_CAPITALIZE, true)) {
                    caps = true;
                    this.capsonoffflg = false;
                    this.kv.setShifted(true);
                    this.kv.invalidate();
                    this.kv.invalidateAllKeys();
                    this.tmpshiftonoff = false;
                }
            }
            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(getApplicationContext(), Constants.FlagChangeLanguage);
            if (HindiUtils.isUpHoneycomb) {
                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                dictionaryLoadTask.execute(new String[0]);
            }
//            SharedPreferences.Editor edit2 = this.prefs.edit();
//            this.edit = edit2;
//            edit2.putString("SelectedLangName", HindiUtils.selectedLangName);
//            this.edit.putInt("lang_flg", Constants.FlagChangeLanguage);
            new MySharePref(getApplicationContext()).putPrefInt(MySharePref.FLAG_CHANGE_LANGUAGE, Constants.FlagChangeLanguage);
            new MySharePref(ctx).putPrefString(MySharePref.LANGUAGE_NAME, Constants.selectedLanguageName);
//            if (Build.VERSION.SDK_INT >= 11) {
//                HindiUtils.isUpHoneycomb = true;
//            }
//            if (HindiUtils.isUpHoneycomb) {
//                this.edit.apply();
//            } else {
//                this.edit.commit();
//            }
            Constants.temp_flag = 0;
            this.kv.init();
        } catch (Exception unused) {
            unused.getMessage();
        }
    }

    private void setHintString() {
        this.hintword = (LinearLayout) this.v.findViewById(R.id.hintword);
        HorizontalListView horizontalListView = (HorizontalListView) this.v.findViewById(R.id.horizontalListView1);
        this.hlist = horizontalListView;
        hlist.setVisibility(View.VISIBLE);
        this.hlist.setOnItemClickListener(this.SuggectionItemClickEvent);
    }

    public void showdeletehint() {
        if (Constants.SuggestedView) {
            this.word = "";
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
                this.word = trim;
                getGujarati(trim);
                Log.d("TextData", split[split.length - 1]);
                this.hintword.setVisibility(View.VISIBLE);
                this.mainMenu.setVisibility(View.GONE);
            }
            if (textBeforeCursor.toString().length() <= 0) {
                this.word = "";
                getGujarati("");
                this.hintword.setVisibility(View.GONE);
                this.result = null;
                this.result = new ArrayList<>();
                this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
                this.mainMenu.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initilizeHeight() {
        Display defaultDisplay = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        if (getResources().getConfiguration().orientation == 1) {
            if (new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_POTRAIT_HEIGHT, -1) == -1) {
                this.keybpardHeight = displayMetrics.heightPixels / 3;
                this.speaklayheight = (displayMetrics.heightPixels / 3) + (displayMetrics.heightPixels / 10);
            } else {
                this.keybpardHeight = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_POTRAIT_HEIGHT, -1);
                this.speaklayheight = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_POTRAIT_HEIGHT, -1) + (displayMetrics.heightPixels / 10);
            }
        } else if (new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_LANDSCAP_HEIGHT, -1) == -1) {
            this.keybpardHeight = displayMetrics.heightPixels / 2;
            this.speaklayheight = (displayMetrics.heightPixels / 2) + (displayMetrics.heightPixels / 10);
            HindiUtils.checkheight = this.keybpardHeight;
        } else {
            this.keybpardHeight = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_LANDSCAP_HEIGHT, -1);
            this.speaklayheight = new MySharePref(getApplicationContext()).getPrefInt(MySharePref.KEYBOARD_LANDSCAP_HEIGHT, -1) + (displayMetrics.heightPixels / 10);
        }
        this.tmpHieght = Constants.DpToPx(getApplicationContext(), 41);
        this.tmpShowSuggestion = true;
    }

    private void translayout_height(int i) {
        this.tmpHieght1 = HindiUtils.DpToPx(getApplicationContext(), 55);
        ViewGroup.LayoutParams layoutParams = this.black_translay.getLayoutParams();
        layoutParams.height = i + this.tmpHieght1;
        layoutParams.width = -1;
        this.black_translay.setLayoutParams(layoutParams);
    }

    private void Giflayout_height(int i) {
        this.tmpHieght1 = HindiUtils.DpToPx(getApplicationContext(), 55);
        RelativeLayout relativeLayout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = i + this.tmpHieght1;
        layoutParams.width = -1;
        relativeLayout.setLayoutParams(layoutParams);
    }

    public void CategoryClick(int i) {
        StickerModel stickerModel = this.stickersList.get(i);
        HindiUtils.selectedCateGory = stickerModel.path;
        this.stickerlistadapter.notifyDataSetChanged();
        clickCategory(stickerModel.path);
    }

    public void StickerClick(int i, String str) {
        String str2;
        StickerModel stickerModel = this.stickers.get(i);
        this.FolderPathOfSticker = str;
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
        if (HindiUtils.socialPackages.contains(str2)) {
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
            File file = new File(HindiUtils.storePath + "/temp/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(this.FolderPathOfSticker + str + ".png");
            String str2 = HindiUtils.storePath + "/temp/" + str + ".png";
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
            File file = new File(HindiUtils.storePath + "/temp/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(this.FolderPathOfSticker + str + ".png");
            String str2 = HindiUtils.storePath + "/temp/" + str + ".png";
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
                    if (!HindiUtils.socialPackages.contains(str3)) {
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
            File file = new File(HindiUtils.storePath + "/temp/");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(this.FolderPathOfSticker + str + ".png");
            String str3 = HindiUtils.storePath + "/temp/" + str + ".png";
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
            if (this.stickers.get(i).type == 0) {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file3));
            } else {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file3));
            }
            startActivity(intent);
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), "Sorry! Sticker can't Share", Toast.LENGTH_LONG).show();
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x002c */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0059 A[SYNTHETIC, Splitter:B:28:0x0059] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0065 A[SYNTHETIC, Splitter:B:35:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x006c A[SYNTHETIC, Splitter:B:39:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void copyAssetssticker(String r6, File r7) {
        /*
            r5 = this;
            android.content.res.AssetManager r0 = r5.getAssets()
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r2.<init>()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            java.lang.String r3 = "sticker/"
            r2.append(r3)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r2.append(r6)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            java.lang.String r3 = ".png"
            r2.append(r3)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            java.io.InputStream r0 = r0.open(r2)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0038, all -> 0x0034 }
            r2.<init>(r7)     // Catch:{ IOException -> 0x0038, all -> 0x0034 }
            r5.copyFile(r0, r2)     // Catch:{ IOException -> 0x0032, all -> 0x0030 }
            if (r0 == 0) goto L_0x002c
            r0.close()     // Catch:{ IOException -> 0x002c }
        L_0x002c:
            r2.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0061
        L_0x0030:
            r6 = move-exception
            goto L_0x0036
        L_0x0032:
            r7 = move-exception
            goto L_0x003a
        L_0x0034:
            r6 = move-exception
            r2 = r1
        L_0x0036:
            r1 = r0
            goto L_0x0063
        L_0x0038:
            r7 = move-exception
            r2 = r1
        L_0x003a:
            r1 = r0
            goto L_0x0041
        L_0x003c:
            r6 = move-exception
            r2 = r1
            goto L_0x0063
        L_0x003f:
            r7 = move-exception
            r2 = r1
        L_0x0041:
            java.lang.String r0 = "tag"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0062 }
            r3.<init>()     // Catch:{ all -> 0x0062 }
            java.lang.String r4 = "Failed to copy asset file: "
            r3.append(r4)     // Catch:{ all -> 0x0062 }
            r3.append(r6)     // Catch:{ all -> 0x0062 }
            java.lang.String r6 = r3.toString()     // Catch:{ all -> 0x0062 }
            android.util.Log.e(r0, r6, r7)     // Catch:{ all -> 0x0062 }
            if (r1 == 0) goto L_0x005e
            r1.close()     // Catch:{ IOException -> 0x005d }
            goto L_0x005e
        L_0x005d:
        L_0x005e:
            if (r2 == 0) goto L_0x0061
            goto L_0x002c
        L_0x0061:
            return
        L_0x0062:
            r6 = move-exception
        L_0x0063:
            if (r1 == 0) goto L_0x006a
            r1.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x006a
        L_0x0069:
        L_0x006a:
            if (r2 == 0) goto L_0x006f
            r2.close()     // Catch:{ IOException -> 0x006f }
        L_0x006f:
            goto L_0x0071
        L_0x0070:
            throw r6
        L_0x0071:
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tech.lang.keyboard.hindikeyboard.HindiKeypad.copyAssetssticker(java.lang.String, java.io.File):void");
    }

    private void saveImageData(String str) {
        Bitmap changeBackground = changeBackground(BitmapFactory.decodeFile(str, new BitmapFactory.Options()));
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
//                        fileOutputStream = 100;
                    changeBackground.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                    fileOutputStream2.close();
                } catch (Exception e) {
                    this.e = e;
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
                e = e3;
            }
        } catch (Throwable th2) {
            Throwable th = th2;
        }
    }

    private Bitmap changeBackground(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
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

    @SuppressLint("ResourceType")
    private void initStickerList(String str) {
        this.stickersList.clear();
        HindiUtils.selectedCateGory = "category0";
        this.icons = null;
        File file = new File(HindiUtils.storePath + "/" + str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] list = file.list();
        this.offlineFiles = list;
        for (String replace : list) {
            this.stickersList.add(new StickerModel(replace.replace(".png", ""), true, 1, "0"));
        }
        StickerListAdapter stickerListAdapter = new StickerListAdapter(getApplicationContext(), this.stickersList, str);
        this.stickerlistadapter = stickerListAdapter;
        this.stickerList.setAdapter((ListAdapter) stickerListAdapter);
        this.kv.setVisibility(View.GONE);
        this.otherContentLay.setVisibility(View.GONE);
        this.r2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), 17432576));
        this.otherContentLay.removeAllViews();
    }

    @SuppressLint("ResourceType")
    private void initStickerAdapter(String str) {
        this.stickers.clear();
        this.icons = null;
        File file = new File(HindiUtils.storePath + "/sticker/" + str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] list = file.list();
        this.offlineFiles = list;
        for (String replace : list) {
            this.stickers.add(new StickerModel(replace.replace(".png", ""), true, 1, "0"));
        }
        this.stickergrid.setNumColumns(this.isLandscape ? 4 : 3);
        this.stickergrid.setHorizontalSpacing(5);
        this.stickergrid.setVerticalSpacing(5);
        this.stickergrid.setGravity(17);
        StickerAdapter stickerAdapter2 = new StickerAdapter(getApplicationContext(), this.stickers, 0, this.kv.getHeight(), str);
        this.stickerAdapter = stickerAdapter2;
        this.stickergrid.setAdapter(stickerAdapter2);
        this.kv.setVisibility(View.GONE);
        this.otherContentLay.setVisibility(View.GONE);
        this.r2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), 17432576));
        this.otherContentLay.removeAllViews();
    }

    private void clickCategory(String str) {
        this.stickers.clear();
        File file = new File(HindiUtils.storePath + "/sticker/" + str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] list = file.list();
        this.offlineFiles = list;
        for (String replace : list) {
            this.stickers.add(new StickerModel(replace.replace(".png", ""), true, 1, "0"));
        }
        StickerAdapter stickerAdapter2 = new StickerAdapter(getApplicationContext(), this.stickers, 0, this.kv.getHeight(), str);
        this.stickerAdapter = stickerAdapter2;
        this.stickergrid.setAdapter(stickerAdapter2);
    }

    private String[] getImage(String str) throws IOException {
        return getAssets().list(str);
    }

    public AlertDialog dialogAskInstallSTT() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Install Google Voice For Voice Translate");
        builder.setPositiveButton("Install Now!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    HindiKeypad.this.getApplicationContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.android.googlequicksearchbox")));
                } catch (ActivityNotFoundException unused) {
                    HindiKeypad.this.getApplicationContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox")));
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showToast(String str, int i) {
        Toast toast = this.mThongBao;
        if (toast != null) {
            toast.cancel();
        }
        if (str.equalsIgnoreCase("")) {
            this.mThongBao.cancel();
            return;
        }
        Toast makeText = Toast.makeText(this, str, i);
        this.mThongBao = makeText;
        makeText.setGravity(81, 0, 200);
        this.mThongBao.show();
    }

    public void doTheDownAnimation(int i, int i2) {
        int i3 = this.mLevel - 100;
        this.mLevel = i3;
        this.mImageDrawable.setLevel(i3);
        if (this.mLevel >= i2) {
            this.mDownHandler.postDelayed(this.animateDownImage, 10);
            return;
        }
        this.mDownHandler.removeCallbacks(this.animateDownImage);
        this.fromLevel = i2;
    }

    public void doTheUpAnimation(int i, int i2) {
        int i3 = this.mLevel + 100;
        this.mLevel = i3;
        this.mImageDrawable.setLevel(i3);
        if (this.mLevel <= i2) {
            this.mUpHandler.postDelayed(this.animateUpImage, 10);
            return;
        }
        this.mUpHandler.removeCallbacks(this.animateUpImage);
        this.fromLevel = i2;
    }

    public AlertDialog dialogSettingGoogleApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Enable Micro Permission");
        builder.setTitle("SettingActivity Permissions");
        builder.setPositiveButton("Go To SettingActivity", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                HindiKeypad.this.goToGoogleSettings();
            }
        });
        return builder.create();
    }

    public void goToGoogleSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:com.google.android.googlequicksearchbox"));
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void translateWithSpeech() {
        this.speaktotextbtn.setVisibility(View.VISIBLE);
        this.proTalk.setVisibility(View.GONE);
        if (!this.mNgonNguIn.getmText().equals("")) {
            showProgressDialog(this, "Translating...");
            showProgressDialog(this, "Translating...");
        }
    }

    public void showProgressDialog(Context context, String str) {
        hideProgressDialog();
        this.progressDialog.setCancelable(true);
    }

    public void hideProgressDialog() {
        ProgressDialog progressDialog2 = this.progressDialog;
        if (progressDialog2 != null) {
            if (progressDialog2.isShowing()) {
                this.progressDialog.dismiss();
            }
            this.progressDialog = null;
        }
    }

    class myAsyncTask extends AsyncTask<Void, Void, Void> {
        myAsyncTask() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            ArrayList arrayList = new ArrayList();
            arrayList.add("");
            HindiKeypad.this.hlist.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(HindiKeypad.this.getApplicationContext(), arrayList, HindiKeypad.this.selectedTheme, HindiKeypad.this.hlist.getWidth()));
        }

        public Void doInBackground(Void... voidArr) {
            try {
                if (HindiKeypad.this.word.equals("")) {
                    return null;
                }
                HindiKeypad.this.result = HindiUtils.getSuggestion(HindiKeypad.this.word);
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        public void onPostExecute(Void r6) {
            try {
                if (!HindiKeypad.this.word.equals("")) {
                    if (HindiKeypad.this.result.size() >= 1) {
                        Collections.sort(HindiKeypad.this.result, new Comparator<String>() { // from class: com.tech.lang.keyboard.hindikeyboard.HindiKeypad.myAsyncTask.1
                            @Override // java.util.Comparator
                            public int compare(String str, String str2) {
                                return str.compareToIgnoreCase(str2);
                            }
                        });
                        HashSet hashSet = new HashSet();
                        hashSet.addAll(HindiKeypad.this.result);
                        HindiKeypad.this.result.clear();
                        HindiKeypad.this.result.addAll(hashSet);
                        try {
                            if (HindiKeypad.this.result != null) {
                                Collections.sort(HindiKeypad.this.result, new MyComparator());
                            }
                        } catch (Exception unused) {
                        }
                        HindiKeypad.this.hlist.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(HindiKeypad.this.getApplicationContext(), HindiKeypad.this.result, HindiKeypad.this.selectedTheme, HindiKeypad.this.hlist.getWidth()));
                    } else if (HindiKeypad.this.result.size() <= 0) {
                        HindiKeypad.this.result = null;
                        HindiKeypad.this.result = new ArrayList<>();
                        HindiKeypad.this.result.add(HindiKeypad.this.word);
                        HindiKeypad.this.result.add("Touch to add");
                        HindiKeypad.this.hlist.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(HindiKeypad.this.getApplicationContext(), HindiKeypad.this.result, HindiKeypad.this.selectedTheme, HindiKeypad.this.hlist.getWidth()));
                    }
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add("");
                    HindiKeypad.this.hlist.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(HindiKeypad.this.getApplicationContext(), arrayList, HindiKeypad.this.selectedTheme, HindiKeypad.this.hlist.getWidth()));
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

    class C03953 implements RecognitionListener {
        public void onEvent(int i, Bundle bundle) {
        }

        public void onPartialResults(Bundle bundle) {
        }

        C03953() {
        }

        public void onReadyForSpeech(Bundle bundle) {
            int unused = HindiKeypad.this.thoiGianCho = 100;
            Log.d("speechtotext", "onReadyForSpeech");
            HindiKeypad.this.speak_lay.setVisibility(View.VISIBLE);
            HindiKeypad.this.bottomlay.setVisibility(View.GONE);
            boolean unused2 = HindiKeypad.this.isSpeeching = true;
            if (HindiUtils.speakLangName.equals("en")) {
                HindiKeypad.speakstring.setText("Let's Speak");
            } else {
                HindiKeypad.speakstring.setText(HindiKeypad.this.getResources().getString(R.string.error_text10));
            }
        }

        public void onBeginningOfSpeech() {
            Log.d("speechtotext", "onBeginningOfSpeech");
        }

        public void onRmsChanged(float f) {
            int i;
            boolean unused = HindiKeypad.this.isSpeeching = true;
            Log.i("speechtotext", "onRmsChanged: " + f);
            if (f < 0.0f) {
                HindiKeypad hindiKeypad = HindiKeypad.this;
                int unused2 = hindiKeypad.thoiGianCho = hindiKeypad.thoiGianCho - 1;
                if (HindiKeypad.this.thoiGianCho <= 0) {
                    onError(SpeechRecognizer.ERROR_CLIENT);
                }
            }
            if (f < 0.0f) {
                f = 3.0f;
            }
            if (f > 0.0f && HindiKeypad.toLevel != (i = (int) ((f * 10000.0f) / 10.0f)) && i <= 10000) {
                if (i > 10000) {
                    i = HindiKeypad.toLevel;
                }
                int unused3 = HindiKeypad.toLevel = i;
                if (HindiKeypad.toLevel > HindiKeypad.this.fromLevel) {
                    HindiKeypad.this.mDownHandler.removeCallbacks(HindiKeypad.this.animateDownImage);
                    int unused4 = HindiKeypad.this.fromLevel = HindiKeypad.toLevel;
                    HindiKeypad.this.mUpHandler.post(HindiKeypad.this.animateUpImage);
                    return;
                }
                HindiKeypad.this.mUpHandler.removeCallbacks(HindiKeypad.this.animateUpImage);
                int unused5 = HindiKeypad.this.fromLevel = HindiKeypad.toLevel;
                HindiKeypad.this.mDownHandler.post(HindiKeypad.this.animateDownImage);
            }
        }

        public void onBufferReceived(byte[] bArr) {
            Log.d("speechtotext", "onBufferReceiverd");
        }

        public void onEndOfSpeech() {
            HindiKeypad.this.speak_lay.setVisibility(View.GONE);
            HindiKeypad.this.bottomlay.setVisibility(View.VISIBLE);
            if (HindiKeypad.this.isSpeeching) {
                HindiKeypad.this.proTalk.setVisibility(View.VISIBLE);
            }
            Log.d("speechtotext", "onEndOfSpeech");
        }

        public void onError(int i) {
            if (HindiKeypad.this.isSpeeching) {
                HindiKeypad.this.speaktotextbtn.setVisibility(View.VISIBLE);
                HindiKeypad.this.proTalk.setVisibility(View.GONE);
                if (i != 3 || Build.VERSION.SDK_INT < 23) {
                    String errorText = HindiKeypad.getErrorText(i);
                    Log.d("speechtotext", "FAILED " + errorText);
                    HindiKeypad.this.showToast(errorText, 1);
                    boolean unused = HindiKeypad.this.isSpeeching = false;
                } else {
                    HindiKeypad.this.dialogSettingGoogleApp().show();
                }
                HindiKeypad.this.mSpeechReco.destroy();
                return;
            }
            HindiKeypad.this.mSpeechReco.startListening(HindiKeypad.this.recognizerIntent);
            Log.d("speechtotext", "bị Repeat nè ..... ");
        }

        public void onResults(Bundle bundle) {
            Log.d("speechtotext", "onResults");
            boolean unused = HindiKeypad.this.isSpeeching = false;
            HindiKeypad.this.speaktotextbtn.setVisibility(View.VISIBLE);
            HindiKeypad.this.proTalk.setVisibility(View.GONE);
            ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
            if (stringArrayList != null) {
                if (stringArrayList.size() == 1) {
                    HindiKeypad.this.mNgonNguIn.setmText(stringArrayList.get(0));
                } else {
                    InputConnection currentInputConnection = HindiKeypad.this.getCurrentInputConnection();
                    currentInputConnection.commitText("" + stringArrayList.get(0).toString(), 0);
                    Context applicationContext = HindiKeypad.this.getApplicationContext();
                    Toast.makeText(applicationContext, "" + stringArrayList.get(0).toString(), Toast.LENGTH_LONG).setGravity(81, 0, 0);
                }
            }
            HindiKeypad.this.mSpeechReco.destroy();
        }
    }

    class C03922 implements Runnable {
        C03922() {
        }

        public void run() {
            HindiKeypad hindiKeypad = HindiKeypad.this;
            hindiKeypad.doTheDownAnimation(hindiKeypad.fromLevel, HindiKeypad.toLevel);
        }
    }

    class C03911 implements Runnable {
        C03911() {
        }

        public void run() {
            HindiKeypad hindiKeypad = HindiKeypad.this;
            hindiKeypad.doTheUpAnimation(hindiKeypad.fromLevel, HindiKeypad.toLevel);
        }
    }
}
