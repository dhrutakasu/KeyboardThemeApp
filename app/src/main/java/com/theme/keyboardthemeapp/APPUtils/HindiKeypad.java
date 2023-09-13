//package com.theme.keyboardthemeapp.APPUtils;
//
//import android.app.ActivityManager;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.app.usage.UsageStats;
//import android.app.usage.UsageStatsManager;
//import android.content.ActivityNotFoundException;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ResolveInfo;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffColorFilter;
//import android.graphics.Typeface;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.ClipDrawable;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.NinePatchDrawable;
//import android.inputmethodservice.InputMethodService;
//import android.inputmethodservice.Keyboard;
//import android.inputmethodservice.KeyboardView;
//import android.media.AudioManager;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Parcelable;
//import android.os.Vibrator;
//import android.preference.PreferenceManager;
//import android.speech.RecognitionListener;
//import android.speech.SpeechRecognizer;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Display;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.animation.AnimationUtils;
//import android.view.inputmethod.CompletionInfo;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.ExtractedTextRequest;
//import android.view.inputmethod.InputConnection;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.GridView;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.TreeMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import androidx.appcompat.widget.ActivityChooserView;
//import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
//
//public class HindiKeypad extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
//    public static final int TYPE_ASSET = 0;
//    public static final int TYPE_LOCAL = 1;
//    public static final int TYPE_ONLINE = 2;
//    static TextView Timer = null;
//    public static boolean caps = false;
//    public static int[] char_colorCodes = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
//    public static boolean checkLanguage;
//    public static InputMethodService ims;
//    static TextView speakstring;
//    /* access modifiers changed from: private */
//    public static int toLevel = 0;
//    View.OnClickListener ChangeSpeakLang = new View.OnClickListener() {
//        public void onClick(View view) {
//            if (HindiUtils.speakLangName.equals(HindiKeypad.this.getResources().getString(R.string.speak_lang_2))) {
//                HindiUtils.speakLangName = "en";
//            } else {
//                HindiUtils.speakLangName = HindiKeypad.this.getResources().getString(R.string.speak_lang_2);
//            }
//            HindiKeypad.this.btnspeaklang.setText(HindiUtils.speakLangName);
//            HindiKeypad hindiKeypad = HindiKeypad.this;
//            hindiKeypad.edit = hindiKeypad.prefs.edit();
//            HindiKeypad.this.edit.putString("speakLangName", HindiUtils.speakLangName);
//            if (Build.VERSION.SDK_INT >= 11) {
//                HindiUtils.isUpHoneycomb = true;
//            }
//            if (HindiUtils.isUpHoneycomb) {
//                HindiKeypad.this.edit.apply();
//            } else {
//                HindiKeypad.this.edit.commit();
//            }
//        }
//    };
//    View.OnClickListener CloseDialog = new View.OnClickListener() {
//        public void onClick(View view) {
//            HindiKeypad.this.speak_lay.setVisibility(8);
//            HindiKeypad.this.bottomlay.setVisibility(0);
//        }
//    };
//    String ContryNameIn;
//    ArrayList<String> FinalString = new ArrayList<>();
//    String FinalWord = "";
//    String FolderPathOfSticker = "";
//    Drawable Inputlang_off_drawable;
//    Drawable Inputlang_on_drawable;
//    LinearLayout LangChange;
//    View.OnClickListener OnClickTheme = new View.OnClickListener() {
//        public void onClick(View view) {
//            try {
//                if (!HindiUtils.previewActivityisOpen) {
//                    HindiUtils.wordExist = true;
//                    Intent intent = new Intent(HindiKeypad.this.getApplicationContext(), ApplyKeypadTheme.class);
//                    intent.addFlags(268435456);
//                    intent.putExtra("flgbool", true);
//                    HindiKeypad.this.startActivity(intent);
//                } else if (PreviewActivity.act == null) {
//                    HindiUtils.wordExist = true;
//                    Intent intent2 = new Intent(HindiKeypad.this.getApplicationContext(), ApplyKeypadTheme.class);
//                    intent2.addFlags(268435456);
//                    intent2.putExtra("flgbool", false);
//                    HindiKeypad.this.startActivity(intent2);
//                }
//            } catch (Exception unused) {
//            }
//        }
//    };
//    ListView SelectedLanglist;
//    Drawable Speak_btn_drawable;
//    View.OnClickListener SpeakbtnDialog = new View.OnClickListener() {
//        public void onClick(View view) {
//            HindiKeypad.this.speak_lay.setLayoutParams(new FrameLayout.LayoutParams(-1, HindiKeypad.this.speaklayheight));
//            HindiKeypad.speakstring.setText("Tap to Speak!!");
//            HindiKeypad.this.speaktotextbtn.setVisibility(8);
//            HindiKeypad.this.speak_lay.setVisibility(0);
//            HindiKeypad.this.bottomlay.setVisibility(8);
//            if (HindiKeypad.this.Timercounter != null) {
//                HindiKeypad.this.Timercounter.cancel();
//            }
//            HindiKeypad.this.Timercounter = new CountDownTimer(14000, 1000) {
//                public void onTick(long j) {
//                    TextView textView = HindiKeypad.Timer;
//                    textView.setText("00:" + (j / 1000));
//                }
//
//                public void onFinish() {
//                    HindiKeypad.Timer.setText("done!");
//                    HindiKeypad.this.speak_lay.setVisibility(8);
//                    HindiKeypad.this.bottomlay.setVisibility(0);
//                }
//            }.start();
//            if (!HindiKeypad.this.isSpeechRecoAvalable) {
//                HindiKeypad.this.dialogAskInstallSTT().show();
//            } else if (!HindiKeypad.this.isOnline()) {
//                HindiKeypad.this.showToast("Opps! No Internet Access, Please Try Again", 1);
//            } else if (Arrays.asList(HindiKeypad.this.commonResource.getNovoice()).contains(HindiKeypad.this.commonResource.getContriesin()[HindiKeypad.this.mNgonNguIn.getmPosition()])) {
//                HindiKeypad hindiKeypad = HindiKeypad.this;
//                hindiKeypad.showToast("Opps! " + HindiKeypad.this.mNgonNguIn.getmLanguageName() + " language was not supported Speech to Text", 1);
//            } else {
//                try {
//                    if (!HindiKeypad.this.vib.hasVibrator()) {
//                        HindiKeypad.this.vib.vibrate(40);
//                    }
//                    int unused = HindiKeypad.this.thoiGianCho = 100;
//                    Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
//                    intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
//                    intent.putExtra("calling_package", HindiKeypad.this.getApplicationContext().getPackageName());
//                    intent.putExtra("android.speech.extra.MAX_RESULTS", 5);
//                    intent.putExtra("android.speech.extras.SPEECH_INPUT_MINIMUM_LENGTH_MILLIS", PathInterpolatorCompat.MAX_NUM_POINTS);
//                    intent.putExtra("android.speech.extra.LANGUAGE", HindiUtils.speakLangName);
//                    Intent unused2 = HindiKeypad.this.recognizerIntent = intent;
//                    HindiKeypad.this.mSpeechReco.setRecognitionListener(HindiKeypad.this.mRecoListener);
//                    HindiKeypad.this.mSpeechReco.startListening(intent);
//                } catch (Exception e) {
//                    HindiKeypad.this.showToast(e.getMessage(), 1);
//                }
//            }
//            Log.d("mToggleSpeech", "onTrue");
//        }
//    };
//    AdapterView.OnItemClickListener SuggectionItemClickEvent = new AdapterView.OnItemClickListener() {
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
//            CharSequence charSequence;
//            String str = (String) adapterView.getItemAtPosition(i);
//            if (!HindiKeypad.this.word.equals("")) {
//                if (str.equals("Touch to add")) {
//                    if (!HindiUtils.SuggestionWords.contains(HindiKeypad.this.word.toLowerCase())) {
//                        HindiUtils.SuggestionWords.add(HindiKeypad.this.word.toLowerCase());
//                    }
//                    Toast.makeText(HindiKeypad.this.getApplicationContext(), "Word Added Successfully", 1).show();
//                    HindiKeypad.this.word = "";
//                    HindiKeypad hindiKeypad = HindiKeypad.this;
//                    hindiKeypad.getGujarati(hindiKeypad.word);
//                } else if (HindiKeypad.this.result.contains("Touch to add")) {
//                    if (!HindiUtils.SuggestionWords.contains(HindiKeypad.this.word.toLowerCase())) {
//                        HindiUtils.SuggestionWords.add(HindiKeypad.this.word.toLowerCase());
//                    }
//                    Toast.makeText(HindiKeypad.this.getApplicationContext(), "Word Added Successfully", 1).show();
//                    HindiKeypad.this.word = "";
//                    HindiKeypad hindiKeypad2 = HindiKeypad.this;
//                    hindiKeypad2.getGujarati(hindiKeypad2.word);
//                } else {
//                    CharSequence charSequence2 = HindiKeypad.this.getCurrentInputConnection().getExtractedText(new ExtractedTextRequest(), 0).text;
//                    if (charSequence2.toString().contains(" ")) {
//                        charSequence = HindiKeypad.this.getCurrentInputConnection().getTextBeforeCursor(999999, 0);
//                    } else {
//                        charSequence = "";
//                    }
//                    charSequence + "";
//                    HindiKeypad.this.getCurrentInputConnection().deleteSurroundingText(charSequence2.toString().length(), 0);
//                    if (charSequence.toString().contains(" ")) {
//                        int lastIndexOf = charSequence.toString().lastIndexOf(" ");
//                        str = charSequence.toString().substring(0, lastIndexOf) + " " + str;
//                    }
//                    HindiKeypad.this.getCurrentInputConnection().commitText(str, 0);
//                    HindiKeypad.this.word = "";
//                    HindiKeypad hindiKeypad3 = HindiKeypad.this;
//                    hindiKeypad3.getGujarati(hindiKeypad3.word);
//                }
//                HindiKeypad.this.hintword.setVisibility(8);
//                HindiKeypad.this.result = null;
//                HindiKeypad.this.result = new ArrayList<>();
//                HindiKeypad.this.hlist.setAdapter((ListAdapter) new ArrayAdapter(HindiKeypad.this.getApplicationContext(), R.layout.hint_item_view, HindiKeypad.this.result));
//                HindiKeypad.this.mainMenu.setVisibility(0);
//            }
//        }
//    };
//    CountDownTimer Timercounter;
//    AdRequest adRequest;
//    AdView adView;
//    FarsiEmojiAdapter adapter = null;
//    /* access modifiers changed from: private */
//    public Runnable animateDownImage = new C03922();
//    /* access modifiers changed from: private */
//    public Runnable animateUpImage = new C03911();
//    ImageView blackTransparentview;
//    private RelativeLayout black_translay;
//    /* access modifiers changed from: private */
//    public RelativeLayout bottomlay;
//    ArrayList<ImageButton> btnArray = new ArrayList<>();
//    LinearLayout btnSpeak;
//    Button btnspeaklang;
//    boolean capital = false;
//    int[] capsOnquerty = {R.xml.capson_hindi_default_querty2, R.xml.capson_eng_default_querty0};
//    boolean capsonoffflg = false;
//    int[] capsquerty = {R.xml.caps_hindi_default_querty2, R.xml.caps_eng_default_querty0};
//    String charMain = "";
//    boolean checkflg = false;
//    ImageButton closedialog;
//    Common_Resource commonResource;
//    FrameLayout content_frame;
//    int counter = 0;
//    Context ctx;
//    int[] defaultquerty = {R.xml.hindi_default_querty2, R.xml.eng_default_querty0};
//    ImageButton delete;
//    Drawable deleteDrawable;
//    private int[] deleteKeys = {R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back, R.drawable.btn_back};
//    View div1;
//    View div2;
//    Drawable drop_arrow_drawable;
//    SharedPreferences.Editor edit;
//    Drawable emojiDrawable;
//    private EmojiPopup emojiPopup;
//    GridView emojigrid = null;
//    Drawable enterDrawable;
//    private int[] enterKeys = {R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter, R.drawable.btn_enter};
//    String finalstr = "";
//    /* access modifiers changed from: private */
//    public int fromLevel = 0;
//    GujaratiEditBoxSupport g;
//    private int[] generalKeys_presed = {R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed, R.drawable.key_unpresed};
//    private int[] generalKeys_unpresed = {R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed, R.drawable.key_presed};
//    private RelativeLayout gif_rl_layout;
//    ImageView gifimage;
//    LinearLayout headertext;
//    LinearLayout hintword;
//    HorizontalListView hlist;
//    HttpURLConnection httpURLConnection;
//    ArrayList<String> icons = new ArrayList<>();
//    ImageView img;
//    boolean isLandscape;
//    boolean isPopup;
//    /* access modifiers changed from: private */
//    public boolean isSpeechRecoAvalable = false;
//    /* access modifiers changed from: private */
//    public boolean isSpeeching = false;
//    private MyKeypadDataView keyboard;
//    int keybpardHeight = 0;
//    /* access modifiers changed from: private */
//    public HindiKeyboardView kv;
//    LinearLayout langClick;
//    String lastString = "";
//    String lastWord = "";
//    RelativeLayout layoutofSticker;
//    ArrayList<Integer> listinputs = new ArrayList<>();
//    private AudioManager mAudioManager;
//    private Common_Preferences mCommon_Preferences;
//    private boolean mCompletionOn;
//    private CompletionInfo[] mCompletions;
//    private StringBuilder mComposing = new StringBuilder();
//    Context mContext;
//    /* access modifiers changed from: private */
//    public Handler mDownHandler = new Handler();
//    private ClipDrawable mImageDrawable;
//    private long mKeypressVibrationDuration = -1;
//    private int mLevel = 0;
//    NgonNguIn mNgonNguIn;
//    private boolean mPredictionOn;
//    /* access modifiers changed from: private */
//    public RecognitionListener mRecoListener;
//    /* access modifiers changed from: private */
//    public SpeechRecognizer mSpeechReco;
//    private Toast mThongBao;
//    /* access modifiers changed from: private */
//    public Handler mUpHandler = new Handler();
//    private SetVibrateCompact mVibrator;
//    LinearLayout mainMenu;
//    boolean manageClick = false;
//    boolean newcapital = false;
//    NinePatchDrawable npd;
//    NinePatchDrawable npdDelete;
//    NinePatchDrawable npdDone;
//    NinePatchDrawable npdShiftOff;
//    NinePatchDrawable npdShiftOn;
//    NinePatchDrawable npdSpace;
//    NinePatchDrawable npd_presed;
//    String[] offlineFiles;
//    RelativeLayout otherContentLay;
//    private RelativeLayout otherContents;
//    private int[] popUpDrawables = {R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg, R.drawable.preview_bg};
//    Drawable popupDrawable;
//    ListView popupLanglist;
//    int position;
//    SharedPreferences prefs;
//    /* access modifiers changed from: private */
//    public ProgressBar proTalk;
//    private ProgressDialog progressDialog;
//    private RelativeLayout r2;
//    /* access modifiers changed from: private */
//    public Intent recognizerIntent;
//    String replaceStr = "";
//    private int[] resid = {R.drawable.emoji_presedtheme0, R.drawable.flower_presed0, R.drawable.food_presed0, R.drawable.sport_presed0, R.drawable.car_presed0, R.drawable.electronic_presed0, R.drawable.sign_presed0};
//    ArrayList<String> result = null;
//    private RelativeLayout rl;
//    int selectedTheme = 0;
//    private int[] selector = {R.drawable.emoji_unpresedtheme0, R.drawable.flower_unpresed0, R.drawable.food_unpresed0, R.drawable.sport_unpresed0, R.drawable.car_unpresed0, R.drawable.electronic_unpresed0, R.drawable.sign_unpresed0};
//    boolean setClick = false;
//    Drawable settingDrawable;
//    Drawable shiftOffDrawable;
//    private int[] shiftOffKeys = {R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off, R.drawable.btn_shift_off};
//    Drawable shiftOnDrawable;
//    private int[] shiftOnKeys = {R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on, R.drawable.btn_shift_on};
//    int shiftonoff = 0;
//    int small = 0;
//    Drawable spaceDrawable;
//    private int[] spaceKeys = {R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space, R.drawable.btn_space};
//    LinearLayout speak_lay;
//    int speaklayheight = 0;
//    ImageButton speaktotextbtn;
//    StickerAdapter stickerAdapter;
//    HorizontalListView stickerList;
//    GridView stickergrid = null;
//    StickerListAdapter stickerlistadapter;
//    ArrayList<StickerModel> stickers = new ArrayList<>();
//    ArrayList<StickerModel> stickersList = new ArrayList<>();
//    Drawable strickerDrawable;
//    StringBuilder stringBuilder2 = null;
//    int textColorCode = -1;
//    CharSequence textdatas = "";
//    LinearLayout texthintlayout;
//    Drawable themeDrawable;
//    /* access modifiers changed from: private */
//    public int thoiGianCho;
//    int tmpHieght;
//    int tmpHieght1;
//    public boolean tmpShowSuggestion = true;
//    boolean tmpshiftonoff = false;
//    Typeface typeface;
//    ArrayList<String> unicodearray = new ArrayList<>();
//    String urlString = "";
//    View v;
//    /* access modifiers changed from: private */
//    public Vibrator vib;
//    String word = "";
//    ArrayList<String> wordarray = new ArrayList<>();
//
//    public void onText(CharSequence charSequence) {
//    }
//
//    public void swipeDown() {
//    }
//
//    public void swipeLeft() {
//    }
//
//    public void swipeRight() {
//    }
//
//    public void swipeUp() {
//    }
//
//    public HindiKeypad() {
//        ims = this;
//    }
//
//    public static void setKeyboardView() {
//        ((HindiKeypad) ims).setKeyboardData();
//    }
//
//    public static String getErrorText(int i) {
//        switch (i) {
//            case 1:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "Network timeout";
//                }
//                return HindiUtils.e1;
//            case 2:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "Network Error, Please Check Your Internet";
//                }
//                return HindiUtils.e2;
//            case 3:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "Audio Error, Please Try Again";
//                }
//                return HindiUtils.e3;
//            case 4:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "I Can not Connect To Server";
//                }
//                return HindiUtils.e4;
//            case 5:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "I Can't Hear You, Please Try Again";
//                }
//                return HindiUtils.e5;
//            case 6:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "Speech Timeout, Please Try Again";
//                }
//                return HindiUtils.e6;
//            case 7:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "I Don't Understand, Please Try Again";
//                }
//                return HindiUtils.e7;
//            case 8:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "Reconizer Busy, Please Try Later";
//                }
//                return HindiUtils.e8;
//            case 9:
//                return HindiUtils.e9;
//            default:
//                if (HindiUtils.speakLangName.equals("en")) {
//                    return "Didn't understand, please try again.";
//                }
//                return HindiUtils.e7;
//        }
//    }
//
//    public View onCreateInputView() {
//        this.emojiDrawable = getResources().getDrawable(R.drawable.emojipressxmlwhite);
//        this.themeDrawable = getResources().getDrawable(R.drawable.themepressxmlwhite);
//        this.strickerDrawable = getResources().getDrawable(R.drawable.stickerpressxmlwhite);
//        this.settingDrawable = getResources().getDrawable(R.drawable.settingpressxmlwhite);
//        this.shiftOnDrawable = getResources().getDrawable(this.shiftOnKeys[0]);
//        this.spaceDrawable = getResources().getDrawable(this.spaceKeys[0]);
//        this.enterDrawable = getResources().getDrawable(this.enterKeys[0]);
//        this.deleteDrawable = getResources().getDrawable(this.deleteKeys[0]);
//        this.Inputlang_on_drawable = getResources().getDrawable(R.drawable.enable);
//        this.Inputlang_off_drawable = getResources().getDrawable(R.drawable.disable);
//        this.Speak_btn_drawable = getResources().getDrawable(R.drawable.mic_unpresed);
//        this.drop_arrow_drawable = getResources().getDrawable(R.drawable.drop_down);
//        this.manageClick = false;
//        initilizeHeight();
//        this.g = new GujaratiEditBoxSupport();
//        this.mCommon_Preferences = new Common_Preferences(this);
//        this.vib = (Vibrator) getSystemService("vibrator");
//        if (((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo() == null) {
//            Toast.makeText(getApplicationContext(), "Please Connect To Internet for Phonetic Keyboard", 0).show();
//        }
//        HindiUtils.setStaticVariable(getApplicationContext());
//        boolean isRecognitionAvailable = SpeechRecognizer.isRecognitionAvailable(getBaseContext());
//        this.isSpeechRecoAvalable = isRecognitionAvailable;
//        if (isRecognitionAvailable) {
//            this.mSpeechReco = SpeechRecognizer.createSpeechRecognizer(this, ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));
//        } else {
//            dialogAskInstallSTT().show();
//        }
//        this.mRecoListener = new C03953();
//        this.commonResource = new Common_Resource(this);
//        this.tmpShowSuggestion = true;
//        HindiUtils.wordExist = true;
//        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        this.prefs = defaultSharedPreferences;
//        this.edit = defaultSharedPreferences.edit();
//        SelectQuery();
//        int i = this.prefs.getInt("theme_no", 0);
//        this.selectedTheme = i;
//        HindiUtils.selectThemeNo = i;
//        this.shiftOffDrawable = getResources().getDrawable(this.shiftOffKeys[0]);
//        this.checkflg = false;
//        this.textColorCode = char_colorCodes[0];
//        this.npd = (NinePatchDrawable) getResources().getDrawable(this.generalKeys_unpresed[0]);
//        this.npd_presed = (NinePatchDrawable) getResources().getDrawable(this.generalKeys_presed[0]);
//        this.npdShiftOff = (NinePatchDrawable) getResources().getDrawable(this.shiftOffKeys[0]);
//        this.npdShiftOn = (NinePatchDrawable) getResources().getDrawable(this.shiftOnKeys[0]);
//        this.npdSpace = (NinePatchDrawable) getResources().getDrawable(this.spaceKeys[0]);
//        this.npdDelete = (NinePatchDrawable) getResources().getDrawable(this.deleteKeys[0]);
//        this.npdDone = (NinePatchDrawable) getResources().getDrawable(this.enterKeys[0]);
//        this.popupDrawable = getResources().getDrawable(this.popUpDrawables[0]);
//        if (HindiUtils.isColorCodeChange) {
//            this.shiftOffDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.shiftOnDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.enterDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.spaceDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.emojiDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.deleteDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.popupDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.themeDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.settingDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.Inputlang_on_drawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.Inputlang_off_drawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.Speak_btn_drawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.drop_arrow_drawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//        }
//        switch (i) {
//            case 0:
//                caps = false;
//                View inflate = getLayoutInflater().inflate(R.layout.keypad, (ViewGroup) null);
//                this.v = inflate;
//                this.headertext = (LinearLayout) inflate.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.content_frame = (FrameLayout) this.v.findViewById(R.id.contentframe);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                LinearLayout linearLayout = (LinearLayout) this.v.findViewById(R.id.btnTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                linearLayout.setOnClickListener(this.OnClickTheme);
//                hideProgressDialog();
//                String str = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str;
//                this.mNgonNguIn = new NgonNguIn(this, str);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                this.blackTransparentview = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                translayout_height(this.keybpardHeight);
//                this.blackTransparentview.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView;
//                ClipDrawable clipDrawable = (ClipDrawable) imageView.getDrawable();
//                this.mImageDrawable = clipDrawable;
//                clipDrawable.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(0);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 1:
//                caps = false;
//                View inflate2 = getLayoutInflater().inflate(R.layout.keypad1, (ViewGroup) null);
//                this.v = inflate2;
//                this.headertext = (LinearLayout) inflate2.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                hideProgressDialog();
//                String str2 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str2;
//                this.mNgonNguIn = new NgonNguIn(this, str2);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView2 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView2;
//                ClipDrawable clipDrawable2 = (ClipDrawable) imageView2.getDrawable();
//                this.mImageDrawable = clipDrawable2;
//                clipDrawable2.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView3 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView3;
//                imageView3.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(1);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 2:
//                caps = false;
//                View inflate3 = getLayoutInflater().inflate(R.layout.keypad2, (ViewGroup) null);
//                this.v = inflate3;
//                this.headertext = (LinearLayout) inflate3.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                hideProgressDialog();
//                String str3 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str3;
//                this.mNgonNguIn = new NgonNguIn(this, str3);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView4 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView4;
//                ClipDrawable clipDrawable3 = (ClipDrawable) imageView4.getDrawable();
//                this.mImageDrawable = clipDrawable3;
//                clipDrawable3.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView5 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView5;
//                imageView5.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(2);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 3:
//                caps = false;
//                View inflate4 = getLayoutInflater().inflate(R.layout.keypad3, (ViewGroup) null);
//                this.v = inflate4;
//                this.headertext = (LinearLayout) inflate4.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                hideProgressDialog();
//                String str4 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str4;
//                this.mNgonNguIn = new NgonNguIn(this, str4);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView6 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView6;
//                ClipDrawable clipDrawable4 = (ClipDrawable) imageView6.getDrawable();
//                this.mImageDrawable = clipDrawable4;
//                clipDrawable4.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView7 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView7;
//                imageView7.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(3);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 4:
//                caps = false;
//                View inflate5 = getLayoutInflater().inflate(R.layout.keypad4, (ViewGroup) null);
//                this.v = inflate5;
//                this.headertext = (LinearLayout) inflate5.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                hideProgressDialog();
//                String str5 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str5;
//                this.mNgonNguIn = new NgonNguIn(this, str5);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView8 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView8;
//                ClipDrawable clipDrawable5 = (ClipDrawable) imageView8.getDrawable();
//                this.mImageDrawable = clipDrawable5;
//                clipDrawable5.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView9 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView9;
//                imageView9.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(4);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 5:
//                caps = false;
//                View inflate6 = getLayoutInflater().inflate(R.layout.keypad5, (ViewGroup) null);
//                this.v = inflate6;
//                this.headertext = (LinearLayout) inflate6.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                hideProgressDialog();
//                String str6 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str6;
//                this.mNgonNguIn = new NgonNguIn(this, str6);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView10 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView10;
//                ClipDrawable clipDrawable6 = (ClipDrawable) imageView10.getDrawable();
//                this.mImageDrawable = clipDrawable6;
//                clipDrawable6.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView11 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView11;
//                imageView11.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(5);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 6:
//                caps = false;
//                View inflate7 = getLayoutInflater().inflate(R.layout.keypad6, (ViewGroup) null);
//                this.v = inflate7;
//                this.headertext = (LinearLayout) inflate7.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                ((LinearLayout) this.v.findViewById(R.id.btnTheme)).setOnClickListener(this.OnClickTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                hideProgressDialog();
//                String str7 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str7;
//                this.mNgonNguIn = new NgonNguIn(this, str7);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView12 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView12;
//                ClipDrawable clipDrawable7 = (ClipDrawable) imageView12.getDrawable();
//                this.mImageDrawable = clipDrawable7;
//                clipDrawable7.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView13 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView13;
//                imageView13.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(6);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 7:
//                caps = false;
//                View inflate8 = getLayoutInflater().inflate(R.layout.keypad7, (ViewGroup) null);
//                this.v = inflate8;
//                this.headertext = (LinearLayout) inflate8.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                LinearLayout linearLayout2 = (LinearLayout) this.v.findViewById(R.id.btnTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                linearLayout2.setOnClickListener(this.OnClickTheme);
//                hideProgressDialog();
//                String str8 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str8;
//                this.mNgonNguIn = new NgonNguIn(this, str8);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView14 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView14;
//                ClipDrawable clipDrawable8 = (ClipDrawable) imageView14.getDrawable();
//                this.mImageDrawable = clipDrawable8;
//                clipDrawable8.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView15 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView15;
//                imageView15.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(7);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 8:
//                caps = false;
//                View inflate9 = getLayoutInflater().inflate(R.layout.keypad8, (ViewGroup) null);
//                this.v = inflate9;
//                this.headertext = (LinearLayout) inflate9.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                LinearLayout linearLayout3 = (LinearLayout) this.v.findViewById(R.id.btnTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                linearLayout3.setOnClickListener(this.OnClickTheme);
//                hideProgressDialog();
//                String str9 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str9;
//                this.mNgonNguIn = new NgonNguIn(this, str9);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView16 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView16;
//                ClipDrawable clipDrawable9 = (ClipDrawable) imageView16.getDrawable();
//                this.mImageDrawable = clipDrawable9;
//                clipDrawable9.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView17 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView17;
//                imageView17.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(8);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//            case 9:
//                caps = false;
//                View inflate10 = getLayoutInflater().inflate(R.layout.keypad9, (ViewGroup) null);
//                this.v = inflate10;
//                this.headertext = (LinearLayout) inflate10.findViewById(R.id.rl_headertext);
//                this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//                this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//                this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//                LinearLayout linearLayout4 = (LinearLayout) this.v.findViewById(R.id.btnTheme);
//                this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//                if (HindiUtils.CurrentLang == 1) {
//                    this.LangChange.setVisibility(0);
//                } else {
//                    this.LangChange.setVisibility(8);
//                }
//                if (HindiUtils.checkLanguage) {
//                    this.LangChange.setBackgroundResource(R.drawable.enable);
//                } else {
//                    this.LangChange.setBackgroundResource(R.drawable.disable);
//                }
//                this.LangChange.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View view) {
//                        if (HindiUtils.checkLanguage) {
//                            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                            if (HindiUtils.isUpHoneycomb) {
//                                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                            } else {
//                                dictionaryLoadTask.execute(new String[0]);
//                            }
//                            HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                            if (Build.VERSION.SDK_INT >= 11) {
//                                HindiUtils.isUpHoneycomb = true;
//                            }
//                            if (HindiUtils.isUpHoneycomb) {
//                                HindiKeypad.this.edit.apply();
//                            } else {
//                                HindiKeypad.this.edit.commit();
//                            }
//                            HindiUtils.checkLanguage = false;
//                            HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                            return;
//                        }
//                        DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask2.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = true;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                    }
//                });
//                linearLayout4.setOnClickListener(this.OnClickTheme);
//                hideProgressDialog();
//                String str10 = this.mCommon_Preferences.getmContryNameIn();
//                this.ContryNameIn = str10;
//                this.mNgonNguIn = new NgonNguIn(this, str10);
//                this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//                this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//                this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//                this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//                this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//                speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//                Timer = (TextView) this.v.findViewById(R.id.txttimer);
//                this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//                ImageView imageView18 = (ImageView) this.v.findViewById(R.id.imgvoid);
//                this.img = imageView18;
//                ClipDrawable clipDrawable10 = (ClipDrawable) imageView18.getDrawable();
//                this.mImageDrawable = clipDrawable10;
//                clipDrawable10.setLevel(0);
//                this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//                this.closedialog.setOnClickListener(this.CloseDialog);
//                this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//                this.btnspeaklang.setText(HindiUtils.speakLangName);
//                this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//                ImageView imageView19 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//                this.blackTransparentview = imageView19;
//                imageView19.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//                translayout_height(this.keybpardHeight);
//                this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//                this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//                Giflayout_height(this.keybpardHeight);
//                initArrayList(this.v);
//                allClickEvent(9);
//                if (!HindiUtils.isGifApplied) {
//                    this.gif_rl_layout.setVisibility(4);
//                    setkeyboardbackground();
//                    break;
//                } else {
//                    this.gif_rl_layout.setVisibility(0);
//                    setkeyboardGif();
//                    break;
//                }
//        }
//        if (i > 9) {
//            caps = false;
//            View inflate11 = getLayoutInflater().inflate(R.layout.keypad, (ViewGroup) null);
//            this.v = inflate11;
//            this.headertext = (LinearLayout) inflate11.findViewById(R.id.rl_headertext);
//            this.rl = (RelativeLayout) this.v.findViewById(R.id.contents);
//            this.content_frame = (FrameLayout) this.v.findViewById(R.id.contentframe);
//            this.r2 = (RelativeLayout) this.v.findViewById(R.id.contents1);
//            this.kv = (HindiKeyboardView) this.v.findViewById(R.id.keyboard);
//            LinearLayout linearLayout5 = (LinearLayout) this.v.findViewById(R.id.btnTheme);
//            this.LangChange = (LinearLayout) this.v.findViewById(R.id.changeLang);
//            if (HindiUtils.CurrentLang == 1) {
//                this.LangChange.setVisibility(0);
//            } else {
//                this.LangChange.setVisibility(8);
//            }
//            if (HindiUtils.checkLanguage) {
//                this.LangChange.setBackgroundResource(R.drawable.enable);
//            } else {
//                this.LangChange.setBackgroundResource(R.drawable.disable);
//            }
//            this.LangChange.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    if (HindiUtils.checkLanguage) {
//                        DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                        if (HindiUtils.isUpHoneycomb) {
//                            dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                        } else {
//                            dictionaryLoadTask.execute(new String[0]);
//                        }
//                        HindiKeypad.this.edit.putBoolean("chnagelangu", false);
//                        if (Build.VERSION.SDK_INT >= 11) {
//                            HindiUtils.isUpHoneycomb = true;
//                        }
//                        if (HindiUtils.isUpHoneycomb) {
//                            HindiKeypad.this.edit.apply();
//                        } else {
//                            HindiKeypad.this.edit.commit();
//                        }
//                        HindiUtils.checkLanguage = false;
//                        HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.disable);
//                        return;
//                    }
//                    DictionaryLoadTask dictionaryLoadTask2 = new DictionaryLoadTask(HindiKeypad.this.getApplicationContext(), HindiUtils.flg_lang_change);
//                    if (HindiUtils.isUpHoneycomb) {
//                        dictionaryLoadTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                    } else {
//                        dictionaryLoadTask2.execute(new String[0]);
//                    }
//                    HindiKeypad.this.edit.putBoolean("chnagelangu", true);
//                    if (Build.VERSION.SDK_INT >= 11) {
//                        HindiUtils.isUpHoneycomb = true;
//                    }
//                    if (HindiUtils.isUpHoneycomb) {
//                        HindiKeypad.this.edit.apply();
//                    } else {
//                        HindiKeypad.this.edit.commit();
//                    }
//                    HindiUtils.checkLanguage = true;
//                    HindiKeypad.this.LangChange.setBackgroundResource(R.drawable.enable);
//                }
//            });
//            linearLayout5.setOnClickListener(this.OnClickTheme);
//            hideProgressDialog();
//            String str11 = this.mCommon_Preferences.getmContryNameIn();
//            this.ContryNameIn = str11;
//            this.mNgonNguIn = new NgonNguIn(this, str11);
//            this.speak_lay = (LinearLayout) this.v.findViewById(R.id.speaklay);
//            this.bottomlay = (RelativeLayout) this.v.findViewById(R.id.bottomlayout);
//            this.speaktotextbtn = (ImageButton) this.v.findViewById(R.id.btnspeech);
//            this.btnspeaklang = (Button) this.v.findViewById(R.id.btn_speaklang);
//            this.closedialog = (ImageButton) this.v.findViewById(R.id.btn_closedialog);
//            speakstring = (TextView) this.v.findViewById(R.id.speaktext);
//            Timer = (TextView) this.v.findViewById(R.id.txttimer);
//            this.black_translay = (RelativeLayout) this.v.findViewById(R.id.relay_trans_lay);
//            ImageView imageView20 = (ImageView) this.v.findViewById(R.id.keyboard_black_trans);
//            this.blackTransparentview = imageView20;
//            imageView20.setAlpha(((float) HindiUtils.transparentBlackbg) / 255.0f);
//            translayout_height(this.keybpardHeight);
//            this.proTalk = (ProgressBar) this.v.findViewById(R.id.progressBarTalk);
//            ImageView imageView21 = (ImageView) this.v.findViewById(R.id.imgvoid);
//            this.img = imageView21;
//            ClipDrawable clipDrawable11 = (ClipDrawable) imageView21.getDrawable();
//            this.mImageDrawable = clipDrawable11;
//            clipDrawable11.setLevel(0);
//            this.speaktotextbtn.setOnClickListener(this.SpeakbtnDialog);
//            this.closedialog.setOnClickListener(this.CloseDialog);
//            this.btnspeaklang.setOnClickListener(this.ChangeSpeakLang);
//            this.btnspeaklang.setText(HindiUtils.speakLangName);
//            this.gif_rl_layout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//            this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//            Giflayout_height(this.keybpardHeight);
//            initArrayList(this.v);
//            allClickEvent(0);
//            if (HindiUtils.isGifApplied) {
//                this.gif_rl_layout.setVisibility(0);
//                setkeyboardGif();
//            } else {
//                this.gif_rl_layout.setVisibility(4);
//                setkeyboardbackground();
//            }
//        }
//        if (HindiUtils.savephoto) {
//            if (i == 1) {
//                this.rl.setBackgroundResource(R.drawable.trans2);
//            } else if (i == 2) {
//                this.rl.setBackgroundResource(R.drawable.trans3);
//            }
//        }
//        for (Keyboard.Key key : this.keyboard.getKeys()) {
//            int parseInt = Integer.parseInt("" + key.codes[0]);
//            if (parseInt == -978903) {
//                key.icon = this.shiftOffDrawable;
//            } else if (parseInt == -2830) {
//                key.label = key.label;
//            } else if (parseInt == -1) {
//                key.icon = this.shiftOffDrawable;
//            } else if (parseInt == 32) {
//                key.icon = this.spaceDrawable;
//            } else if (parseInt == -6003) {
//                key.label = key.label;
//            } else if (parseInt == -6002) {
//                key.label = key.label;
//            } else if (parseInt == -5) {
//                key.icon = this.deleteDrawable;
//            } else if (parseInt == -4) {
//                key.icon = this.enterDrawable;
//            }
//        }
//        HindiKeyboardView hindiKeyboardView = this.kv;
//        if (hindiKeyboardView != null) {
//            hindiKeyboardView.dismissLangPopup();
//        }
//        this.kv.setOnlineKeyboard(this.npd, this.npd_presed, this.textColorCode, this.npdSpace, this.npdShiftOff, this.npdShiftOn, this.npdDelete, this.npdDone, this.popupDrawable);
//        this.kv.setBackgroundDrawable(new BitmapDrawable());
//        this.kv.setKeyboard(this.keyboard);
//        int i2 = getCurrentInputEditorInfo().imeOptions & 1073742079;
//        if (!(i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5 || i2 == 6)) {
//            try {
//                getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
//            } catch (Exception unused) {
//                if (HindiUtils.CurrentLang == 1 && HindiUtils.isCapsOn) {
//                    this.capsonoffflg = false;
//                    caps = false;
//                    onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
//                }
//            }
//        }
//        this.kv.setOnKeyboardActionListener(this);
//        this.kv.invalidate();
//        HindiUtils.commonView = this.v;
//        return this.v;
//    }
//
//    public void setkeyboardbackground() {
//        if (getResources().getConfiguration().orientation == 1) {
//            if (!HindiUtils.ispotraitbgcolorchange) {
//                Bitmap decodeFile = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/keyboard_image.png");
//                if (decodeFile != null) {
//                    this.rl.setBackgroundDrawable(new BitmapDrawable(decodeFile));
//                    return;
//                }
//                return;
//            }
//            this.rl.setBackgroundColor(HindiUtils.defaultBgColor);
//        } else if (!HindiUtils.islandscapebgcolorchange) {
//            Bitmap decodeFile2 = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/keyboard_image_land.png");
//            if (decodeFile2 != null) {
//                this.rl.setBackgroundDrawable(new BitmapDrawable(decodeFile2));
//            }
//        } else {
//            this.rl.setBackgroundColor(HindiUtils.defaultBgColor);
//        }
//    }
//
//    public void setkeyboardGif() {
//        this.gifimage = (ImageView) this.v.findViewById(R.id.img_gif);
//        if (HindiUtils.SelectedGifPath.substring(0, 8).equals("/storage")) {
//            Glide.with((Context) this).load(HindiUtils.SelectedGifPath).asGif().placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//        } else if (HindiUtils.SelectedGifPath.substring(0, 4).equals(HttpHost.DEFAULT_SCHEME_NAME)) {
//            Glide.with((Context) this).load(HindiUtils.SelectedGifPath).asGif().placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//        } else {
//            Glide.with((Context) this).load(Uri.parse(HindiUtils.SelectedGifPath)).asGif().placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//        }
//    }
//
//    private void allClickEvent(final int i) {
//        this.v.findViewById(R.id.btn_emoji).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.onKey(HindiUtils.KEYCODE_EMOJI, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
//                HindiKeypad.this.setTabBg(0, i);
//                HindiKeypad.caps = false;
//            }
//        });
//        this.v.findViewById(R.id.emojis_tab_1_delete).setOnTouchListener(new RepeatButtonListener(HttpStatus.SC_BAD_REQUEST, 100, new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.deleteemoji();
//            }
//        }));
//        this.v.findViewById(R.id.food_tab_1_emoji).setOnTouchListener(new RepeatButtonListener(HttpStatus.SC_BAD_REQUEST, 100, new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.getFood();
//                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
//            }
//        }));
//        this.v.findViewById(R.id.emojis_tab_1_car).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.getcar();
//                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
//            }
//        });
//        this.v.findViewById(R.id.emojis_tab_1_symbol).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.getSymbols();
//                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
//            }
//        });
//        this.v.findViewById(R.id.emojis_tab_1_flower).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.getFlower();
//                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
//            }
//        });
//        this.v.findViewById(R.id.emojis_tab_1_electronics).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.getElectronics();
//                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
//            }
//        });
//        this.v.findViewById(R.id.emojis_tab_1_bell).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.getBell();
//                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
//            }
//        });
//        this.v.findViewById(R.id.emojis_tab_1_people).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                HindiKeypad.this.icons = null;
//                HindiKeypad.this.icons = new ArrayList<>();
//                HindiKeypad.this.otherContentLay.removeAllViews();
//                HindiKeypad.this.setTabBg(Integer.parseInt((String) view.getTag()), i);
//                HindiKeypad.this.initEmojiAdapter();
//                HindiKeypad.this.otherContentLay.addView(HindiKeypad.this.emojigrid);
//            }
//        });
//        if (HindiUtils.isColorCodeChange) {
//            this.shiftOnDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.shiftOffDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.spaceDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.enterDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.deleteDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.popupDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.emojiDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.themeDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.strickerDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.settingDrawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.Inputlang_on_drawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.Inputlang_off_drawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.Speak_btn_drawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.drop_arrow_drawable.setColorFilter(new PorterDuffColorFilter(HindiUtils.textColorCode, PorterDuff.Mode.SRC_IN));
//            this.btnspeaklang.setTextColor(HindiUtils.textColorCode);
//            if (Build.VERSION.SDK_INT >= 16) {
//                this.v.findViewById(R.id.btn_emoji).setBackground(this.emojiDrawable);
//                this.v.findViewById(R.id.btnTheme).setBackground(this.themeDrawable);
//                this.v.findViewById(R.id.btn_setting).setBackground(this.settingDrawable);
//                return;
//            }
//            this.v.findViewById(R.id.btnTheme).setBackgroundDrawable(this.themeDrawable);
//            this.v.findViewById(R.id.btn_emoji).setBackgroundDrawable(this.emojiDrawable);
//            this.v.findViewById(R.id.btn_setting).setBackgroundDrawable(this.settingDrawable);
//        }
//    }
//
//    public void onStartInputView(EditorInfo editorInfo, boolean z) {
//        super.onStartInputView(editorInfo, z);
//        Log.d("main", "start inpitview");
//        if (!this.isPopup) {
//            setInputView(onCreateInputView());
//            this.isPopup = false;
//        }
//        int i = editorInfo.inputType & 4080;
//        if ((editorInfo.inputType & 15) == 1) {
//            if (i == 128 || i == 144) {
//                this.tmpShowSuggestion = false;
//            }
//            if (i == 32) {
//                this.tmpShowSuggestion = false;
//            } else if (i == 16) {
//                this.tmpShowSuggestion = false;
//            } else if (i != 64) {
//                if (i == 176) {
//                    this.tmpShowSuggestion = false;
//                } else if (i == 160) {
//                    int i2 = editorInfo.inputType;
//                }
//            }
//            if ((editorInfo.inputType & 524288) != 0) {
//                this.tmpShowSuggestion = false;
//            }
//            if ((editorInfo.inputType & 32768) == 0) {
//                int i3 = editorInfo.inputType;
//            }
//            if ((editorInfo.inputType & 65536) != 0) {
//                this.tmpShowSuggestion = false;
//                this.mCompletionOn = isFullscreenMode();
//            }
//        }
//        int i4 = getCurrentInputEditorInfo().imeOptions & 1073742079;
//        if (i4 != 2 && i4 != 3 && i4 != 4 && i4 != 5 && i4 != 6) {
//            try {
//                HindiUtils.showsugg = true;
//                if (HindiUtils.isCapsOn && this.tmpShowSuggestion && HindiUtils.selectedLang == 1) {
//                    this.capsonoffflg = false;
//                    caps = true;
//                    this.kv.setShifted(true);
//                    this.kv.invalidate();
//                    this.kv.invalidateAllKeys();
//                }
//            } catch (Exception unused) {
//                if (HindiUtils.isCapsOn && this.tmpShowSuggestion) {
//                    this.capsonoffflg = false;
//                    caps = false;
//                    this.kv.setShifted(false);
//                    this.kv.invalidate();
//                    this.kv.invalidateAllKeys();
//                }
//            }
//        }
//    }
//
//    public boolean onEvaluateInputViewShown() {
//        try {
//            if (HindiUtils.isGifApplied) {
//                if (this.v != null) {
//                    if (getResources().getConfiguration().orientation == 1) {
//                        this.isLandscape = false;
//                        if (!HindiUtils.ispotraitbgcolorchange) {
//                            Glide.clear((View) this.gifimage);
//                            if (HindiUtils.SelectedGifPath.substring(0, 8).equals("/storage")) {
//                                Glide.with((Context) this).load(HindiUtils.SelectedGifPath).asGif().placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//                            } else if (HindiUtils.SelectedGifPath.substring(0, 4).equals(HttpHost.DEFAULT_SCHEME_NAME)) {
//                                Glide.with((Context) this).load(HindiUtils.SelectedGifPath).asGif().placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//                            } else {
//                                Glide.with((Context) this).load(Uri.parse(HindiUtils.SelectedGifPath)).asGif().placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//                            }
//                        } else {
//                            this.rl.setBackgroundColor(HindiUtils.defaultBgColor);
//                        }
//                    } else {
//                        this.isLandscape = false;
//                        if (!HindiUtils.islandscapebgcolorchange) {
//                            Glide.clear((View) this.gifimage);
//                            if (HindiUtils.SelectedGifPath.substring(0, 8).equals("/storage")) {
//                                Glide.with((Context) this).load(HindiUtils.SelectedGifPath).asGif().placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//                            } else if (HindiUtils.SelectedGifPath.substring(0, 4).equals(HttpHost.DEFAULT_SCHEME_NAME)) {
//                                Glide.with((Context) this).load(HindiUtils.SelectedGifPath).asGif().placeholder((int) R.drawable.rain).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//                            } else {
//                                Glide.with((Context) this).load(Uri.parse(HindiUtils.SelectedGifPath)).asGif().placeholder((int) R.drawable.ic_launcher).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this.gifimage);
//                            }
//                        } else {
//                            this.rl.setBackgroundColor(HindiUtils.defaultBgColor);
//                        }
//                    }
//                }
//            } else if (this.v != null) {
//                if (getResources().getConfiguration().orientation == 1) {
//                    this.isLandscape = false;
//                    if (!HindiUtils.ispotraitbgcolorchange) {
//                        Bitmap decodeFile = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/keyboard_image.png");
//                        if (decodeFile != null) {
//                            this.rl.setBackgroundDrawable(new BitmapDrawable(decodeFile));
//                        }
//                    } else {
//                        this.rl.setBackgroundColor(HindiUtils.defaultBgColor);
//                    }
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
//            }
//        } catch (Exception unused) {
//        }
//        return super.onEvaluateInputViewShown();
//    }
//
//    public String method(String str) {
//        return (str == null || str.length() <= 0 || str.charAt(str.length() + -1) != 'x') ? str : str.substring(0, str.length() - 1);
//    }
//
//    public void onKey(int i, int[] iArr) {
//        final int i2 = i;
//        final int[] iArr2 = iArr;
//        final InputConnection currentInputConnection = getCurrentInputConnection();
//        if (HindiUtils.CurrentLang == 1) {
//            this.LangChange.setVisibility(0);
//        } else {
//            this.LangChange.setVisibility(8);
//        }
//        this.kv.setVisibility(0);
//        if (this.headertext.getVisibility() == 0) {
//            this.headertext.setVisibility(8);
//        }
//        if (HindiUtils.deleteFlg) {
//            if (HindiUtils.isCapsOn) {
//                caps = true;
//                this.capsonoffflg = false;
//                this.kv.setShifted(true);
//            }
//            this.kv.invalidateAllKeys();
//            HindiUtils.deleteFlg = false;
//        } else if (i2 == -978903) {
//            HindiUtils.wordExist = true;
//            CapsOn();
//            caps = true;
//            this.capsonoffflg = true;
//            this.newcapital = true;
//        } else if (i2 == -6003) {
//            HindiUtils.wordExist = true;
//            this.checkflg = true;
//            MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, Bundle.allNumericQuerty[HindiUtils.CurrentLang], this.keybpardHeight, 1);
//            this.keyboard = myKeypadDataView;
//            this.kv.setKeyboard(myKeypadDataView);
//            for (Keyboard.Key key : this.keyboard.getKeys()) {
//                int parseInt = Integer.parseInt("" + key.codes[0]);
//                if (parseInt == -978903) {
//                    key.icon = this.shiftOffDrawable;
//                } else if (parseInt == -2830) {
//                    key.label = key.label;
//                } else if (parseInt == -1) {
//                    key.icon = this.shiftOffDrawable;
//                } else if (parseInt == 32) {
//                    key.icon = this.spaceDrawable;
//                } else if (parseInt == -6003) {
//                    key.label = key.label;
//                } else if (parseInt == -6002) {
//                    key.label = key.label;
//                } else if (parseInt == -5) {
//                    key.icon = this.deleteDrawable;
//                } else if (parseInt == -4) {
//                    key.icon = this.enterDrawable;
//                }
//            }
//            this.kv.invalidateAllKeys();
//            caps = false;
//        } else if (i2 == -5000) {
//            HindiKeyboardView hindiKeyboardView = this.kv;
//            if (hindiKeyboardView != null) {
//                hindiKeyboardView.dismissLangPopup();
//                this.kv.dismissPreviewPopup();
//            }
//            if (!this.manageClick) {
//                HindiUtils.wordExist = true;
//                if (this.mainMenu.getVisibility() == 8) {
//                    this.hintword.setVisibility(8);
//                    this.result = null;
//                    this.result = new ArrayList<>();
//                    this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
//                    this.mainMenu.setVisibility(0);
//                }
//                this.headertext.setVisibility(0);
//                HindiUtils.tmp_flg = 1;
//                initEmojiAdapter();
//                this.kv.setVisibility(8);
//                this.otherContentLay.setVisibility(0);
//                this.r2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), 17432576));
//                this.otherContentLay.removeAllViews();
//                this.otherContentLay.addView(this.emojigrid);
//            } else {
//                this.otherContentLay.removeAllViews();
//                this.otherContentLay.setVisibility(8);
//                setKeyboardData();
//            }
//            this.manageClick = !this.manageClick;
//        } else if (i2 == -1763) {
//            HindiUtils.wordExist = true;
//            this.checkflg = true;
//            MyKeypadDataView myKeypadDataView2 = new MyKeypadDataView(this, R.xml.numeric_shift_querty, this.keybpardHeight, 1);
//            this.keyboard = myKeypadDataView2;
//            this.kv.setKeyboard(myKeypadDataView2);
//            for (Keyboard.Key key2 : this.keyboard.getKeys()) {
//                int parseInt2 = Integer.parseInt("" + key2.codes[0]);
//                if (parseInt2 == -978903) {
//                    key2.icon = this.shiftOffDrawable;
//                } else if (parseInt2 == -2830) {
//                    key2.label = key2.label;
//                } else if (parseInt2 == -1) {
//                    key2.icon = this.shiftOffDrawable;
//                } else if (parseInt2 == 32) {
//                    key2.icon = this.spaceDrawable;
//                } else if (parseInt2 == -6003) {
//                    key2.label = key2.label;
//                } else if (parseInt2 == -6002) {
//                    key2.label = key2.label;
//                } else if (parseInt2 == -5) {
//                    key2.icon = this.deleteDrawable;
//                } else if (parseInt2 == -4) {
//                    key2.icon = this.enterDrawable;
//                }
//            }
//            this.kv.invalidateAllKeys();
//            caps = false;
//        } else if (i2 == -1) {
//            HindiUtils.wordExist = true;
//            this.newcapital = false;
//            caps = !caps;
//            if (HindiUtils.flg_lang_change == 1) {
//                if (!HindiUtils.isCapsOn) {
//                    if (caps) {
//                        this.capsonoffflg = false;
//                        this.kv.invalidateAllKeys();
//                    } else {
//                        this.capsonoffflg = true;
//                        this.kv.invalidateAllKeys();
//                    }
//                    this.kv.setShifted(caps);
//                } else if (caps) {
//                    SelectQuertyShiftOn();
//                    this.capsonoffflg = false;
//                    this.kv.invalidateAllKeys();
//                } else {
//                    SelectQuertyShiftOff();
//                    this.capsonoffflg = true;
//                    this.kv.invalidateAllKeys();
//                }
//            } else if (caps) {
//                CapsOn();
//                caps = true;
//                this.capsonoffflg = true;
//                this.newcapital = true;
//                this.kv.invalidateAllKeys();
//            } else {
//                SelectQuertyShiftOff();
//                this.capsonoffflg = true;
//                this.kv.invalidateAllKeys();
//            }
//        } else if (i2 == 66) {
//        } else {
//            if (i2 == -2831) {
//                HindiUtils.wordExist = true;
//                setKeyboardData();
//            } else if (i2 == -2830) {
//                HindiUtils.wordExist = true;
//                this.checkflg = false;
//                MyKeypadDataView myKeypadDataView3 = new MyKeypadDataView(this, this.defaultquerty[HindiUtils.CurrentLang], this.keybpardHeight, 0);
//                this.keyboard = myKeypadDataView3;
//                this.kv.setKeyboard(myKeypadDataView3);
//                for (Keyboard.Key key3 : this.keyboard.getKeys()) {
//                    int parseInt3 = Integer.parseInt("" + key3.codes[0]);
//                    if (parseInt3 == -978903) {
//                        key3.icon = this.shiftOffDrawable;
//                    } else if (parseInt3 == -2830) {
//                        key3.label = key3.label;
//                    } else if (parseInt3 == -1) {
//                        key3.icon = this.shiftOffDrawable;
//                    } else if (parseInt3 == 32) {
//                        key3.icon = this.spaceDrawable;
//                    } else if (parseInt3 == -6003) {
//                        key3.label = key3.label;
//                    } else if (parseInt3 == -6002) {
//                        key3.label = key3.label;
//                    } else if (parseInt3 == -5) {
//                        key3.icon = this.deleteDrawable;
//                    } else if (parseInt3 == -4) {
//                        key3.icon = this.enterDrawable;
//                    }
//                }
//                this.kv.invalidateAllKeys();
//                try {
//                    if (this.newcapital) {
//                        CapsOn();
//                        this.capsonoffflg = true;
//                        caps = true;
//                    }
//                    char charAt = getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
//                    if (Character.isLetter(charAt) && Character.isUpperCase(charAt) && !this.newcapital && HindiUtils.CurrentLang == 1 && HindiUtils.isCapsOn) {
//                        this.capsonoffflg = false;
//                        caps = false;
//                        onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
//                    }
//                } catch (Exception unused) {
//                    if (!this.newcapital && HindiUtils.CurrentLang == 1) {
//                        caps = true;
//                        this.capsonoffflg = false;
//                        SelectQuertyShiftOn();
//                    }
//                }
//            } else if (i2 == -5) {
//                HindiKeyboardView hindiKeyboardView2 = this.kv;
//                if (hindiKeyboardView2 != null) {
//                    hindiKeyboardView2.dismissPreviewPopup();
//                }
//                if (this.mainMenu.getVisibility() == 8) {
//                    this.hintword.setVisibility(8);
//                    this.result = null;
//                    this.result = new ArrayList<>();
//                    this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
//                    this.mainMenu.setVisibility(0);
//                }
//                HindiUtils.wordExist = true;
//                try {
//                    char charAt2 = currentInputConnection.getTextBeforeCursor(1, 0).charAt(0);
//                    if (Character.isLetter(charAt2)) {
//                        Log.d("main", "isLetter");
//                    } else if (Character.isISOControl(charAt2)) {
//                        Log.d("main", "isIsoCHar");
//                    } else if (Character.isDigit(charAt2)) {
//                        Log.d("main", "isDigit");
//                    } else if (Character.isHighSurrogate(charAt2)) {
//                        Log.d("main", "isHigh Surrigate");
//                    } else if (Character.isDefined(charAt2)) {
//                        Log.d("main", "isDefined");
//                        if (Character.isHighSurrogate(currentInputConnection.getTextBeforeCursor(2, 0).charAt(0))) {
//                            Log.d("main", "isEmoji");
//                            currentInputConnection.deleteSurroundingText(2, 0);
//                            return;
//                        }
//                    }
//                    currentInputConnection.deleteSurroundingText(1, 0);
//                    int i3 = getCurrentInputEditorInfo().imeOptions & 1073742079;
//                    if (i3 != 2 && i3 != 3 && i3 != 4 && i3 != 5 && i3 != 6 && !this.newcapital && !this.checkflg && HindiUtils.CurrentLang == 1) {
//                        deleteText(currentInputConnection.getExtractedText(new ExtractedTextRequest(), 0).text.toString(), charAt2);
//                    }
//                } catch (Exception e) {
//                    HindiUtils.deleteFlg = false;
//                    int i4 = getCurrentInputEditorInfo().imeOptions & 1073742079;
//                    if (i4 != 2 && i4 != 3 && i4 != 4 && i4 != 5 && i4 != 6) {
//                        Log.d("main", "Exception deleting no char " + e);
//                        if (HindiUtils.flg_lang_change != 0) {
//                            this.capsonoffflg = false;
//                            this.kv.setShifted(true);
//                            this.kv.invalidate();
//                            this.kv.invalidateAllKeys();
//                            this.tmpshiftonoff = true;
//                        }
//                    }
//                }
//            } else if (i2 == -4) {
//                HindiUtils.wordExist = true;
//                if (this.mainMenu.getVisibility() == 8) {
//                    this.hintword.setVisibility(8);
//                    this.result = null;
//                    this.result = new ArrayList<>();
//                    this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
//                    this.mainMenu.setVisibility(0);
//                }
//                int i5 = getCurrentInputEditorInfo().imeOptions & 1073742079;
//                if (i5 == 2) {
//                    currentInputConnection.performEditorAction(2);
//                } else if (i5 == 3) {
//                    currentInputConnection.performEditorAction(3);
//                } else if (i5 == 4) {
//                    currentInputConnection.performEditorAction(4);
//                } else if (i5 == 5) {
//                    currentInputConnection.performEditorAction(5);
//                } else if (i5 != 6) {
//                    currentInputConnection.sendKeyEvent(new KeyEvent(0, 66));
//                    if (!this.newcapital && HindiUtils.isCapsOn) {
//                        this.capsonoffflg = false;
//                        caps = true;
//                        this.kv.setShifted(true);
//                        this.kv.invalidate();
//                        this.kv.invalidateAllKeys();
//                    }
//                } else {
//                    currentInputConnection.performEditorAction(6);
//                }
//            } else if (i2 != -97886) {
//                char c = (char) i2;
//                if (HindiUtils.CurrentLang == 1 && HindiUtils.checkLanguage) {
//                    if (this.counter == 0 && i2 == 32) {
//                        String str = "" + currentInputConnection.getTextBeforeCursor(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0);
//                        this.FinalWord = str;
//                        String substring = str.substring(str.lastIndexOf(" ") + 1);
//                        this.lastWord = substring;
//                        currentInputConnection.deleteSurroundingText(substring.length() + 1, 0);
//                        new AsyncTask<Void, Void, Void>() {
//                            /* access modifiers changed from: protected */
//                            public void onPreExecute() {
//                            }
//
//                            /* access modifiers changed from: protected */
//                            public void onProgressUpdate(Void... voidArr) {
//                            }
//
//                            /* access modifiers changed from: protected */
//                            public Void doInBackground(Void... voidArr) {
//                                try {
//                                    HindiKeypad.this.httpURLConnection = null;
//                                    URL url = new URL("http://www.google.com/inputtools/request");
//                                    HindiKeypad hindiKeypad = HindiKeypad.this;
//                                    hindiKeypad.urlString = "text=" + HindiKeypad.this.lastWord + "&ime=transliteration_en_hi";
//                                    HindiKeypad.this.httpURLConnection = (HttpURLConnection) url.openConnection();
//                                    HindiKeypad.this.httpURLConnection.setRequestMethod(AsyncHttpGet.METHOD);
//                                    HindiKeypad.this.httpURLConnection.setConnectTimeout(5000);
//                                    HindiKeypad.this.httpURLConnection.setReadTimeout(5000);
//                                    HindiKeypad.this.httpURLConnection.setDoOutput(true);
//                                    HindiKeypad.this.httpURLConnection.getOutputStream().write(HindiKeypad.this.urlString.getBytes("UTF8"));
//                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(HindiKeypad.this.httpURLConnection.getInputStream(), "UTF-8"));
//                                    HindiKeypad.this.stringBuilder2 = new StringBuilder();
//                                    while (true) {
//                                        int read = bufferedReader.read();
//                                        if (read == -1) {
//                                            break;
//                                        }
//                                        HindiKeypad.this.stringBuilder2.append((char) read);
//                                    }
//                                } catch (Exception unused) {
//                                }
//                                return null;
//                            }
//
//                            /* access modifiers changed from: protected */
//                            public void onPostExecute(Void voidR) {
//                                Pattern compile = Pattern.compile("\"([^\"]*)\"");
//                                Matcher matcher = compile.matcher("" + HindiKeypad.this.stringBuilder2);
//                                while (matcher.find()) {
//                                    HindiKeypad.this.FinalString.add(matcher.group(1));
//                                }
//                                try {
//                                    UtilSupport.dictionaryword = HindiKeypad.this.FinalString.get(2);
//                                    InputConnection inputConnection = currentInputConnection;
//                                    inputConnection.commitText(HindiKeypad.this.FinalString.get(2) + " ", 1);
//                                    HindiKeypad.this.lastWord = "";
//                                    HindiKeypad.this.FinalString.clear();
//                                } catch (Exception unused) {
//                                    InputConnection inputConnection2 = currentInputConnection;
//                                    inputConnection2.commitText(HindiKeypad.this.lastWord + " ", 1);
//                                    HindiKeypad.this.lastWord = "";
//                                    HindiKeypad.this.FinalString.clear();
//                                }
//                            }
//                        }.execute(new Void[0]);
//                        this.counter++;
//                        new Handler().postDelayed(new Runnable() {
//                            public void run() {
//                                HindiKeypad.this.showSuggestion(currentInputConnection, i2, iArr2);
//                            }
//                        }, 500);
//                    } else {
//                        this.counter = 0;
//                    }
//                }
//                if (!Character.isLetter(c) || !caps) {
//                    currentInputConnection.commitText(String.valueOf(c), 1);
//                    if (i2 == 46 && HindiUtils.isCapsOn && this.tmpShowSuggestion && HindiUtils.flg_lang_change == 1 && HindiUtils.isCapsOn) {
//                        caps = true;
//                        this.capsonoffflg = false;
//                        this.kv.setShifted(true);
//                    }
//                    if (HindiUtils.isSuggestionView && this.tmpShowSuggestion && !HindiUtils.previewActivityisOpen && !checkLanguage) {
//                        showSuggestion(currentInputConnection, i2, iArr2);
//                    }
//                    if (i2 >= 97 && i2 <= 122) {
//                        this.capsonoffflg = true;
//                        return;
//                    }
//                    return;
//                }
//                currentInputConnection.commitText(String.valueOf(Character.toUpperCase(c)), 1);
//                if (!this.capsonoffflg && HindiUtils.CurrentLang == 1 && HindiUtils.isCapsOn) {
//                    this.capsonoffflg = true;
//                    onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
//                }
//                if (HindiUtils.isSuggestionView && this.tmpShowSuggestion && !HindiUtils.previewActivityisOpen && !checkLanguage) {
//                    showSuggestion(currentInputConnection, i2, iArr2);
//                }
//            }
//        }
//    }
//
//    /* access modifiers changed from: package-private */
//    public void showSuggestion(InputConnection inputConnection, int i, int[] iArr) {
//        try {
//            this.word = "";
//            if (checkLanguage) {
//                this.textdatas = UtilSupport.dictionaryword;
//            } else {
//                this.textdatas = inputConnection.getTextBeforeCursor(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0);
//            }
//            int length = this.textdatas.toString().length();
//            if (length >= 1) {
//                boolean z = false;
//                while (true) {
//                    if (length != 0) {
//                        int i2 = length - 1;
//                        char charAt = this.textdatas.toString().charAt(i2);
//                        if (charAt == 10 || charAt == ',') {
//                            break;
//                        } else if (charAt == '.') {
//                            break;
//                        } else if (charAt != ' ') {
//                            this.word += this.textdatas.toString().charAt(i2);
//                            if (HindiUtils.SuggestionView) {
//                                this.hintword.setVisibility(0);
//                                this.mainMenu.setVisibility(8);
//                                z = true;
//                            }
//                            length--;
//                        } else {
//                            HindiUtils.wordExist = true;
//                            if (HindiUtils.SuggestionView && !z) {
//                                this.hintword.setVisibility(8);
//                                this.result = null;
//                                this.result = new ArrayList<>();
//                                this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
//                                this.mainMenu.setVisibility(0);
//                            }
//                        }
//                    } else {
//                        break;
//                    }
//                }
//                HindiUtils.wordExist = true;
//                if (HindiUtils.SuggestionView && !z) {
//                    this.hintword.setVisibility(8);
//                    this.result = null;
//                    this.result = new ArrayList<>();
//                    this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
//                    this.mainMenu.setVisibility(0);
//                }
//                this.word = new StringBuilder(this.word).reverse().toString();
//                myAsyncTask myasynctask = new myAsyncTask();
//                if (HindiUtils.isUpHoneycomb) {
//                    myasynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
//                } else {
//                    myasynctask.execute(new Void[0]);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /* access modifiers changed from: private */
//    public void getGujarati(String str) {
//        if (!str.equals("")) {
//            ArrayList<String> suggestion = HindiUtils.getSuggestion(str);
//            this.result = suggestion;
//            if (suggestion.size() >= 1) {
//                Collections.sort(this.result, new Comparator<String>() {
//                    public int compare(String str, String str2) {
//                        return str.compareToIgnoreCase(str2);
//                    }
//                });
//                HorizontalListView horizontalListView = this.hlist;
//                horizontalListView.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(this, this.result, this.selectedTheme, horizontalListView.getWidth()));
//            } else if (this.result.size() <= 0) {
//                this.result = null;
//                ArrayList<String> arrayList = new ArrayList<>();
//                this.result = arrayList;
//                arrayList.add(str);
//                this.result.add("Touch to add");
//                HorizontalListView horizontalListView2 = this.hlist;
//                horizontalListView2.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(this, this.result, this.selectedTheme, horizontalListView2.getWidth()));
//            }
//        } else {
//            ArrayList arrayList2 = new ArrayList();
//            arrayList2.add("");
//            HorizontalListView horizontalListView3 = this.hlist;
//            horizontalListView3.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(this, arrayList2, this.selectedTheme, horizontalListView3.getWidth()));
//        }
//    }
//
//    private void playClick(int i) {
//        int i2 = i != -5 ? i != -4 ? i != 32 ? 5 : 6 : 8 : 7;
//        if (((double) HindiUtils.mFxVolume) != 0.0d) {
//            this.mAudioManager.playSoundEffect(i2, HindiUtils.mFxVolume);
//        }
//    }
//
//    public void onFinishInput() {
//        Log.d("main", "finish input");
//        HindiKeyboardView hindiKeyboardView = this.kv;
//        if (hindiKeyboardView != null) {
//            hindiKeyboardView.dismissLangPopup();
//        }
//        super.onFinishInput();
//    }
//
//    public void onPress(int i) {
//        this.kv.setPreviewEnabled(false);
//        this.kv.dismissLangPopup();
//        if (HindiUtils.isPreviewEnabled) {
//            this.kv.onPressKey(i);
//        }
//        if (HindiUtils.isVibrateOn) {
//            vibrate();
//        }
//        if (HindiUtils.isSoundOn) {
//            playClick(i);
//        }
//    }
//
//    public void vibrate() {
//        long j = this.mKeypressVibrationDuration;
//        if (j < 0) {
//            HindiKeyboardView hindiKeyboardView = this.kv;
//            if (hindiKeyboardView != null) {
//                hindiKeyboardView.performHapticFeedback(3, 2);
//                return;
//            }
//            return;
//        }
//        SetVibrateCompact setVibrateCompact = this.mVibrator;
//        if (setVibrateCompact != null) {
//            setVibrateCompact.vibrate(j);
//        }
//    }
//
//    public void onRelease(int i) {
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                HindiKeypad.this.kv.dismissPreviewPopup();
//            }
//        }, 100);
//    }
//
//    /* access modifiers changed from: private */
//    public void initEmojiAdapter() {
//        this.otherContentLay.removeAllViews();
//        this.icons = null;
//        this.icons = new ArrayList<>();
//        for (int i = 1; i <= 189; i++) {
//            ArrayList<String> arrayList = this.icons;
//            arrayList.add(NotificationBundleProcessor.PUSH_MINIFIED_BUTTON_ICON + i);
//        }
//        this.emojigrid = null;
//        GridView gridView = new GridView(this);
//        this.emojigrid = gridView;
//        gridView.setNumColumns(8);
//        this.emojigrid.setGravity(17);
//        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
//        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), People.DATA, 0));
//    }
//
//    public void clickeventfg(int i) {
//        getCurrentInputConnection().commitText(Sport.DATA[i].getEmoji(), 1);
//    }
//
//    public void clickevent(int i) {
//        getCurrentInputConnection().commitText(People.DATA[i].getEmoji(), 1);
//    }
//
//    public void clickeventfl(int i) {
//        getCurrentInputConnection().commitText(Nature.DATA[i].getEmoji(), 1);
//    }
//
//    /* access modifiers changed from: private */
//    public void getFlower() {
//        this.otherContentLay.removeAllViews();
//        this.icons = null;
//        this.icons = new ArrayList<>();
//        for (int i = 1; i <= 116; i++) {
//            ArrayList<String> arrayList = this.icons;
//            arrayList.add("f" + i);
//        }
//        this.emojigrid = null;
//        GridView gridView = new GridView(this);
//        this.emojigrid = gridView;
//        gridView.setNumColumns(8);
//        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
//        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Nature.DATA, 1));
//        this.otherContentLay.addView(this.emojigrid);
//    }
//
//    /* access modifiers changed from: private */
//    public void getElectronics() {
//        this.otherContentLay.removeAllViews();
//        this.icons = null;
//        this.icons = new ArrayList<>();
//        for (int i = 1; i <= 116; i++) {
//            ArrayList<String> arrayList = this.icons;
//            arrayList.add("f" + i);
//        }
//        this.emojigrid = null;
//        GridView gridView = new GridView(this);
//        this.emojigrid = gridView;
//        gridView.setNumColumns(8);
//        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
//        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Electronics.DATA, 6));
//        this.otherContentLay.addView(this.emojigrid);
//    }
//
//    /* access modifiers changed from: private */
//    public void getBell() {
//        this.otherContentLay.removeAllViews();
//        this.icons = null;
//        this.icons = new ArrayList<>();
//        for (int i = 1; i <= 229; i++) {
//            ArrayList<String> arrayList = this.icons;
//            arrayList.add("b" + i);
//        }
//        this.emojigrid = null;
//        GridView gridView = new GridView(this);
//        this.emojigrid = gridView;
//        gridView.setNumColumns(8);
//        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
//        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Sport.DATA, 2));
//        this.otherContentLay.addView(this.emojigrid);
//    }
//
//    /* access modifiers changed from: private */
//    public void getcar() {
//        this.otherContentLay.removeAllViews();
//        this.icons = null;
//        this.icons = new ArrayList<>();
//        for (int i = 1; i <= 98; i++) {
//            ArrayList<String> arrayList = this.icons;
//            arrayList.add("c" + i);
//        }
//        this.emojigrid = null;
//        GridView gridView = new GridView(this);
//        this.emojigrid = gridView;
//        gridView.setNumColumns(8);
//        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
//        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Cars.DATA, 3));
//        this.otherContentLay.addView(this.emojigrid);
//    }
//
//    /* access modifiers changed from: private */
//    public void getFood() {
//        this.otherContentLay.removeAllViews();
//        this.icons = null;
//        this.icons = new ArrayList<>();
//        for (int i = 1; i <= 98; i++) {
//            ArrayList<String> arrayList = this.icons;
//            arrayList.add("c" + i);
//        }
//        this.emojigrid = null;
//        GridView gridView = new GridView(this);
//        this.emojigrid = gridView;
//        gridView.setNumColumns(8);
//        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
//        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Food.DATA, 5));
//        this.otherContentLay.addView(this.emojigrid);
//    }
//
//    /* access modifiers changed from: private */
//    public void getSymbols() {
//        this.otherContentLay.removeAllViews();
//        this.icons = null;
//        this.icons = new ArrayList<>();
//        for (int i = 1; i <= 206; i++) {
//            ArrayList<String> arrayList = this.icons;
//            arrayList.add("s" + i);
//        }
//        this.emojigrid = null;
//        GridView gridView = new GridView(this);
//        this.emojigrid = gridView;
//        gridView.setNumColumns(8);
//        this.emojigrid.setLayoutParams(new RelativeLayout.LayoutParams(-1, this.kv.getHeight() - this.tmpHieght));
//        this.emojigrid.setAdapter(new EmojiAdapter(getApplicationContext(), Symbols.DATA, 4));
//        this.otherContentLay.addView(this.emojigrid);
//    }
//
//    /* access modifiers changed from: private */
//    public void deleteemoji() {
//        try {
//            char charAt = getCurrentInputConnection().getTextBeforeCursor(1, 0).charAt(0);
//            if (Character.isLetter(charAt)) {
//                Log.d("main", "isLetter");
//            } else if (Character.isISOControl(charAt)) {
//                Log.d("main", "isIsoCHar");
//            } else if (Character.isDigit(charAt)) {
//                Log.d("main", "isDigit");
//            } else if (Character.isHighSurrogate(charAt)) {
//                Log.d("main", "isHigh Surrigate");
//            } else if (Character.isDefined(charAt)) {
//                Log.d("main", "isDefined");
//                if (Character.isHighSurrogate(getCurrentInputConnection().getTextBeforeCursor(2, 0).charAt(0))) {
//                    Log.d("main", "isEmoji");
//                    getCurrentInputConnection().deleteSurroundingText(2, 0);
//                    return;
//                }
//            }
//            getCurrentInputConnection().deleteSurroundingText(1, 0);
//        } catch (Exception e) {
//            Log.d("main", "Exception deleting no char " + e);
//        }
//    }
//
//    /* access modifiers changed from: private */
//    public void setTabBg(int i, int i2) {
//        Iterator<ImageButton> it2 = this.btnArray.iterator();
//        while (it2.hasNext()) {
//            ImageButton next = it2.next();
//            int parseInt = Integer.parseInt((String) next.getTag());
//            if (parseInt == i) {
//                next.setBackgroundResource(this.resid[i]);
//            } else {
//                next.setBackgroundResource(this.selector[parseInt]);
//            }
//        }
//    }
//
//    private void initArrayList(View view) {
//        setHintString();
//        this.btnArray = null;
//        ArrayList<ImageButton> arrayList = new ArrayList<>();
//        this.btnArray = arrayList;
//        arrayList.add((ImageButton) view.findViewById(R.id.emojis_tab_1_people));
//        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_flower));
//        this.btnArray.add((ImageButton) view.findViewById(R.id.food_tab_1_emoji));
//        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_bell));
//        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_car));
//        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_electronics));
//        this.btnArray.add((ImageButton) view.findViewById(R.id.emojis_tab_1_symbol));
//        this.mainMenu = (LinearLayout) this.v.findViewById(R.id.main_patti);
//        this.mVibrator = SetVibrateCompact.getInstance(this);
//        this.otherContents = (RelativeLayout) this.v.findViewById(R.id.otherContents);
//        this.mAudioManager = (AudioManager) getSystemService("audio");
//        this.otherContentLay = (RelativeLayout) this.v.findViewById(R.id.otherContents);
//        this.btnSpeak = (LinearLayout) this.v.findViewById(R.id.btnmic);
//        ((LinearLayout) this.v.findViewById(R.id.btn_setting)).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                try {
//                    Intent intent = new Intent(HindiKeypad.this.getApplicationContext(), KeyboardSettingActivityGreen.class);
//                    intent.addFlags(335577088);
//                    intent.putExtra("backflg", true);
//                    HindiKeypad.this.startActivity(intent);
//                } catch (Exception unused) {
//                }
//            }
//        });
//        this.btnSpeak.setOnClickListener(this.SpeakbtnDialog);
//    }
//
//    public void clickeventcar(int i) {
//        getCurrentInputConnection().commitText(Cars.DATA[i].getEmoji(), 1);
//    }
//
//    public void clickeventsymbol(int i) {
//        getCurrentInputConnection().commitText(Symbols.DATA[i].getEmoji(), 1);
//    }
//
//    public void clickeventfood(int i) {
//        getCurrentInputConnection().commitText(Food.DATA[i].getEmoji(), 1);
//    }
//
//    public void clickeventElectronics(int i) {
//        getCurrentInputConnection().commitText(Electronics.DATA[i].getEmoji(), 1);
//    }
//
//    public int getResId(String str, Class<?> cls) {
//        return getResources().getIdentifier(str, "drawable", getPackageName());
//    }
//
//    public void SetKeyBoardLayout1() {
//        this.newcapital = false;
//        onKey(HindiUtils.KEYCODE_ALPHABETS, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
//        this.headertext.setVisibility(8);
//        this.kv.setVisibility(0);
//        if (!this.newcapital && HindiUtils.CurrentLang == 1 && HindiUtils.isCapsOn) {
//            this.capsonoffflg = false;
//            caps = false;
//            onKey(-1, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
//        }
//    }
//
//    private void SelectQuery() {
//        this.keyboard = new MyKeypadDataView(this, this.defaultquerty[HindiUtils.CurrentLang], this.keybpardHeight, 0);
//    }
//
//    private void SelectQuertyShiftOn() {
//        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, this.capsquerty[HindiUtils.CurrentLang], this.keybpardHeight, 0);
//        this.keyboard = myKeypadDataView;
//        this.kv.setKeyboard(myKeypadDataView);
//        for (Keyboard.Key key : this.keyboard.getKeys()) {
//            int parseInt = Integer.parseInt("" + key.codes[0]);
//            if (parseInt == -978903) {
//                key.icon = this.shiftOnDrawable;
//            } else if (parseInt == -2830) {
//                key.label = key.label;
//            } else if (parseInt == -1) {
//                key.icon = this.shiftOnDrawable;
//            } else if (parseInt == 32) {
//                key.icon = this.spaceDrawable;
//            } else if (parseInt == -6003) {
//                key.label = key.label;
//            } else if (parseInt == -6002) {
//                key.label = key.label;
//            } else if (parseInt == -5) {
//                key.icon = this.deleteDrawable;
//                key.repeatable = true;
//            } else if (parseInt == -4) {
//                key.icon = this.enterDrawable;
//            }
//        }
//        this.kv.invalidateAllKeys();
//    }
//
//    private void SelectQuertyShiftOff() {
//        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, this.defaultquerty[HindiUtils.CurrentLang], this.keybpardHeight, 0);
//        this.keyboard = myKeypadDataView;
//        this.kv.setKeyboard(myKeypadDataView);
//        for (Keyboard.Key key : this.keyboard.getKeys()) {
//            int parseInt = Integer.parseInt("" + key.codes[0]);
//            if (parseInt == -978903) {
//                key.icon = this.shiftOffDrawable;
//            } else if (parseInt == -2830) {
//                key.label = key.label;
//            } else if (parseInt == -1) {
//                key.icon = this.shiftOffDrawable;
//            } else if (parseInt == 32) {
//                key.icon = this.spaceDrawable;
//            } else if (parseInt == -6003) {
//                key.label = key.label;
//            } else if (parseInt == -6002) {
//                key.label = key.label;
//            } else if (parseInt == -5) {
//                key.icon = this.deleteDrawable;
//                key.repeatable = true;
//            } else if (parseInt == -4) {
//                key.icon = this.enterDrawable;
//            }
//        }
//        this.kv.invalidateAllKeys();
//    }
//
//    public void CapsOn() {
//        caps = false;
//        MyKeypadDataView myKeypadDataView = new MyKeypadDataView(this, this.capsOnquerty[HindiUtils.CurrentLang], this.keybpardHeight, 0);
//        this.keyboard = myKeypadDataView;
//        this.kv.setKeyboard(myKeypadDataView);
//        for (Keyboard.Key key : this.keyboard.getKeys()) {
//            int parseInt = Integer.parseInt("" + key.codes[0]);
//            if (parseInt == -978903) {
//                key.icon = this.shiftOnDrawable;
//            } else if (parseInt == -2830) {
//                key.label = key.label;
//            } else if (parseInt == -1) {
//                key.icon = this.shiftOnDrawable;
//            } else if (parseInt == 32) {
//                key.icon = this.spaceDrawable;
//            } else if (parseInt == -6003) {
//                key.label = key.label;
//            } else if (parseInt == -6002) {
//                key.label = key.label;
//            } else if (parseInt == -5) {
//                key.icon = this.deleteDrawable;
//            } else if (parseInt == -4) {
//                key.icon = this.enterDrawable;
//            }
//        }
//        this.kv.invalidateAllKeys();
//    }
//
//    private void deleteText(String str, char c) {
//        if (Character.isLetter(c) && Character.isUpperCase(c)) {
//            caps = true;
//            this.capsonoffflg = false;
//            this.kv.setShifted(true);
//            this.kv.invalidateAllKeys();
//        } else if ((Character.isLetter(c) && Character.isLowerCase(c)) || c == 10) {
//            caps = false;
//            this.capsonoffflg = true;
//            this.kv.setShifted(false);
//            this.kv.invalidateAllKeys();
//        }
//    }
//
//    public void setKeyboardData() {
//        if (HindiUtils.tmp_flg == 1) {
//            HindiUtils.tmp_flg = 0;
//            this.headertext.setVisibility(8);
//            this.capsonoffflg = false;
//            caps = true;
//            this.kv.setVisibility(0);
//            return;
//        }
//        try {
//            this.headertext.setVisibility(8);
//            this.kv.init();
//            int i = HindiUtils.flg_lang_change;
//            if (i == 0) {
//                HindiUtils.CurrentLang = 0;
//                SetKeyBoardLayout1();
//                HindiUtils.selectedLangName = "Bengali";
//            } else if (i == 1) {
//                HindiUtils.CurrentLang = 1;
//                SetKeyBoardLayout1();
//                HindiUtils.selectedLangName = "English";
//                if (HindiUtils.isCapsOn) {
//                    caps = true;
//                    this.capsonoffflg = false;
//                    this.kv.setShifted(true);
//                    this.kv.invalidate();
//                    this.kv.invalidateAllKeys();
//                    this.tmpshiftonoff = false;
//                }
//            }
//            DictionaryLoadTask dictionaryLoadTask = new DictionaryLoadTask(getApplicationContext(), HindiUtils.flg_lang_change);
//            if (HindiUtils.isUpHoneycomb) {
//                dictionaryLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//            } else {
//                dictionaryLoadTask.execute(new String[0]);
//            }
//            SharedPreferences.Editor edit2 = this.prefs.edit();
//            this.edit = edit2;
//            edit2.putString("SelectedLangName", HindiUtils.selectedLangName);
//            this.edit.putInt("lang_flg", HindiUtils.flg_lang_change);
//            if (Build.VERSION.SDK_INT >= 11) {
//                HindiUtils.isUpHoneycomb = true;
//            }
//            if (HindiUtils.isUpHoneycomb) {
//                this.edit.apply();
//            } else {
//                this.edit.commit();
//            }
//            HindiUtils.tmp_flg = 0;
//            HindiUtils.tmp_flg = 0;
//            this.kv.init();
//        } catch (Exception unused) {
//        }
//    }
//
//    private void setHintString() {
//        this.hintword = (LinearLayout) this.v.findViewById(R.id.hintword);
//        HorizontalListView horizontalListView = (HorizontalListView) this.v.findViewById(R.id.horizontalListView1);
//        this.hlist = horizontalListView;
//        horizontalListView.setVisibility(0);
//        this.hlist.setOnItemClickListener(this.SuggectionItemClickEvent);
//    }
//
//    public void showdeletehint() {
//        if (HindiUtils.SuggestionView) {
//            this.word = "";
//            CharSequence textBeforeCursor = getCurrentInputConnection().getTextBeforeCursor(999999, 0);
//            String replaceAll = textBeforeCursor.toString().replaceAll("\\s", ",").replaceAll("[0-9]+", ",");
//            String str = "";
//            for (int i = 0; i < replaceAll.length(); i++) {
//                char charAt = replaceAll.charAt(i);
//                if (Character.isLetter(charAt)) {
//                    str = str + charAt;
//                } else {
//                    str = str + ",";
//                }
//            }
//            String[] split = str.split(",");
//            if (split.length >= 1) {
//                String trim = split[split.length - 1].trim();
//                this.word = trim;
//                getGujarati(trim);
//                Log.d("TextData", split[split.length - 1]);
//                this.hintword.setVisibility(0);
//                this.mainMenu.setVisibility(8);
//            }
//            if (textBeforeCursor.toString().length() <= 0) {
//                this.word = "";
//                getGujarati("");
//                this.hintword.setVisibility(8);
//                this.result = null;
//                this.result = new ArrayList<>();
//                this.hlist.setAdapter((ListAdapter) new ArrayAdapter(getApplicationContext(), R.layout.hint_item_view, this.result));
//                this.mainMenu.setVisibility(0);
//            }
//        }
//    }
//
//    private void initilizeHeight() {
//        Display defaultDisplay = ((WindowManager) getApplicationContext().getSystemService("window")).getDefaultDisplay();
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        defaultDisplay.getMetrics(displayMetrics);
//        if (getResources().getConfiguration().orientation == 1) {
//            if (HindiUtils.DynamicKeyboardHeight == -1) {
//                this.keybpardHeight = displayMetrics.heightPixels / 3;
//                this.speaklayheight = (displayMetrics.heightPixels / 3) + (displayMetrics.heightPixels / 10);
//            } else {
//                this.keybpardHeight = HindiUtils.DynamicKeyboardHeight;
//                this.speaklayheight = HindiUtils.DynamicKeyboardHeight + (displayMetrics.heightPixels / 10);
//            }
//        } else if (HindiUtils.DynamicKeyboardHeightLandScape == -1) {
//            this.keybpardHeight = displayMetrics.heightPixels / 2;
//            this.speaklayheight = (displayMetrics.heightPixels / 2) + (displayMetrics.heightPixels / 10);
//            HindiUtils.checkheight = this.keybpardHeight;
//        } else {
//            this.keybpardHeight = HindiUtils.DynamicKeyboardHeightLandScape;
//            this.speaklayheight = HindiUtils.DynamicKeyboardHeightLandScape + (displayMetrics.heightPixels / 10);
//        }
//        this.tmpHieght = HindiUtils.DpToPx(getApplicationContext(), 41);
//        this.tmpShowSuggestion = true;
//    }
//
//    private void translayout_height(int i) {
//        this.tmpHieght1 = HindiUtils.DpToPx(getApplicationContext(), 55);
//        ViewGroup.LayoutParams layoutParams = this.black_translay.getLayoutParams();
//        layoutParams.height = i + this.tmpHieght1;
//        layoutParams.width = -1;
//        this.black_translay.setLayoutParams(layoutParams);
//    }
//
//    private void Giflayout_height(int i) {
//        this.tmpHieght1 = HindiUtils.DpToPx(getApplicationContext(), 55);
//        RelativeLayout relativeLayout = (RelativeLayout) this.v.findViewById(R.id.relay_gif_lay);
//        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
//        layoutParams.height = i + this.tmpHieght1;
//        layoutParams.width = -1;
//        relativeLayout.setLayoutParams(layoutParams);
//    }
//
//    public void CategoryClick(int i) {
//        StickerModel stickerModel = this.stickersList.get(i);
//        HindiUtils.selectedCateGory = stickerModel.path;
//        this.stickerlistadapter.notifyDataSetChanged();
//        clickCategory(stickerModel.path);
//    }
//
//    public void StickerClick(int i, String str) {
//        String str2;
//        StickerModel stickerModel = this.stickers.get(i);
//        this.FolderPathOfSticker = str;
//        ActivityManager activityManager = (ActivityManager) getSystemService("activity");
//        if (Build.VERSION.SDK_INT > 20) {
//            List<UsageStats> queryUsageStats = ((UsageStatsManager) getSystemService("usagestats")).queryUsageStats(0, 0, System.currentTimeMillis());
//            if (queryUsageStats != null) {
//                TreeMap treeMap = new TreeMap();
//                for (UsageStats next : queryUsageStats) {
//                    treeMap.put(Long.valueOf(next.getLastTimeUsed()), next);
//                }
//                str2 = ((UsageStats) treeMap.get(treeMap.lastKey())).getPackageName();
//            } else {
//                str2 = "";
//            }
//        } else {
//            str2 = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
//        }
//        if (HindiUtils.socialPackages.contains(str2)) {
//            shareStickerToSocial(i, stickerModel.path, str2);
//        } else if (str2.equals("com.android.mms")) {
//            shareStickerImageTomsg(i, stickerModel.path);
//        } else {
//            shareStickerImage(i, stickerModel.path);
//        }
//    }
//
//    private void shareStickerImageTomsg(int i, String str) {
//        try {
//            File file = new File(HindiUtils.storePath + "/temp/");
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            File file2 = new File(this.FolderPathOfSticker + str + ".png");
//            String str2 = HindiUtils.storePath + "/temp/" + str + ".png";
//            File file3 = new File(str2);
//            if (file3.exists()) {
//                file3.delete();
//            }
//            try {
//                FileUtils.copyFile(file2, file3);
//                saveImageData(str2);
//                file2 = file3;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.SEND");
//            intent.setType("image/png");
//            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
//            intent.setPackage("com.android.mms");
//            intent.setFlags(268468224);
//            startActivity(intent);
//        } catch (Exception unused) {
//            Toast.makeText(getApplicationContext(), "Sorry! this app not perform this action", 1).show();
//        }
//    }
//
//    private void shareStickerImage(int i, String str) {
//        try {
//            ArrayList arrayList = new ArrayList();
//            Intent intent = new Intent("android.intent.action.SEND");
//            intent.setType("image/png");
//            File file = new File(HindiUtils.storePath + "/temp/");
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            File file2 = new File(this.FolderPathOfSticker + str + ".png");
//            String str2 = HindiUtils.storePath + "/temp/" + str + ".png";
//            File file3 = new File(str2);
//            if (file3.exists()) {
//                file3.delete();
//            }
//            try {
//                FileUtils.copyFile(file2, file3);
//                saveImageData(str2);
//                file2 = file3;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            List<ResolveInfo> queryIntentActivities = getPackageManager().queryIntentActivities(intent, 0);
//            if (!queryIntentActivities.isEmpty()) {
//                for (ResolveInfo next : queryIntentActivities) {
//                    String str3 = next.activityInfo.packageName;
//                    if (!HindiUtils.socialPackages.contains(str3)) {
//                        Intent intent2 = new Intent();
//                        intent2.setComponent(new ComponentName(str3, next.activityInfo.name));
//                        intent2.setAction("android.intent.action.SEND");
//                        intent2.setType("image/png");
//                        intent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
//                        intent2.setPackage(str3);
//                        intent2.setFlags(268468224);
//                        arrayList.add(intent2);
//                    }
//                }
//            }
//            ArrayList arrayList2 = new ArrayList(new ArrayList(new HashSet(arrayList)));
//            Intent createChooser = Intent.createChooser((Intent) arrayList2.remove(0), "Choose app to share");
//            createChooser.setFlags(268468224);
//            createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList2.toArray(new Parcelable[0]));
//            startActivity(createChooser);
//        } catch (Exception unused) {
//            Toast.makeText(getApplicationContext(), "Sorry! this app not perform this action", 1).show();
//        }
//    }
//
//    private void shareStickerToSocial(int i, String str, String str2) {
//        try {
//            File file = new File(HindiUtils.storePath + "/temp/");
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            File file2 = new File(this.FolderPathOfSticker + str + ".png");
//            String str3 = HindiUtils.storePath + "/temp/" + str + ".png";
//            File file3 = new File(str3);
//            if (file3.exists()) {
//                file3.delete();
//            }
//            try {
//                FileUtils.copyFile(file2, new File(str3));
//                saveImageData(str3);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Intent intent = new Intent("android.intent.action.SEND");
//            intent.setType("image/png");
//            intent.setPackage(str2);
//            intent.setFlags(268435456);
//            if (this.stickers.get(i).type == 0) {
//                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file3));
//            } else {
//                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file3));
//            }
//            startActivity(intent);
//        } catch (Exception unused) {
//            Toast.makeText(getApplicationContext(), "Sorry! Sticker can't Share", 1).show();
//        }
//    }
//
//    /* JADX WARNING: Failed to process nested try/catch */
//    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x002c */
//    /* JADX WARNING: Removed duplicated region for block: B:28:0x0059 A[SYNTHETIC, Splitter:B:28:0x0059] */
//    /* JADX WARNING: Removed duplicated region for block: B:35:0x0065 A[SYNTHETIC, Splitter:B:35:0x0065] */
//    /* JADX WARNING: Removed duplicated region for block: B:39:0x006c A[SYNTHETIC, Splitter:B:39:0x006c] */
//    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
//    /* Code decompiled incorrectly, please refer to instructions dump. */
//    private void copyAssetssticker(String r6, File r7) {
//        /*
//            r5 = this;
//            android.content.res.AssetManager r0 = r5.getAssets()
//            r1 = 0
//            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x003f, all -> 0x003c }
//            r2.<init>()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
//            java.lang.String r3 = "sticker/"
//            r2.append(r3)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
//            r2.append(r6)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
//            java.lang.String r3 = ".png"
//            r2.append(r3)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
//            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
//            java.io.InputStream r0 = r0.open(r2)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
//            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0038, all -> 0x0034 }
//            r2.<init>(r7)     // Catch:{ IOException -> 0x0038, all -> 0x0034 }
//            r5.copyFile(r0, r2)     // Catch:{ IOException -> 0x0032, all -> 0x0030 }
//            if (r0 == 0) goto L_0x002c
//            r0.close()     // Catch:{ IOException -> 0x002c }
//        L_0x002c:
//            r2.close()     // Catch:{ IOException -> 0x0061 }
//            goto L_0x0061
//        L_0x0030:
//            r6 = move-exception
//            goto L_0x0036
//        L_0x0032:
//            r7 = move-exception
//            goto L_0x003a
//        L_0x0034:
//            r6 = move-exception
//            r2 = r1
//        L_0x0036:
//            r1 = r0
//            goto L_0x0063
//        L_0x0038:
//            r7 = move-exception
//            r2 = r1
//        L_0x003a:
//            r1 = r0
//            goto L_0x0041
//        L_0x003c:
//            r6 = move-exception
//            r2 = r1
//            goto L_0x0063
//        L_0x003f:
//            r7 = move-exception
//            r2 = r1
//        L_0x0041:
//            java.lang.String r0 = "tag"
//            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0062 }
//            r3.<init>()     // Catch:{ all -> 0x0062 }
//            java.lang.String r4 = "Failed to copy asset file: "
//            r3.append(r4)     // Catch:{ all -> 0x0062 }
//            r3.append(r6)     // Catch:{ all -> 0x0062 }
//            java.lang.String r6 = r3.toString()     // Catch:{ all -> 0x0062 }
//            android.util.Log.e(r0, r6, r7)     // Catch:{ all -> 0x0062 }
//            if (r1 == 0) goto L_0x005e
//            r1.close()     // Catch:{ IOException -> 0x005d }
//            goto L_0x005e
//        L_0x005d:
//        L_0x005e:
//            if (r2 == 0) goto L_0x0061
//            goto L_0x002c
//        L_0x0061:
//            return
//        L_0x0062:
//            r6 = move-exception
//        L_0x0063:
//            if (r1 == 0) goto L_0x006a
//            r1.close()     // Catch:{ IOException -> 0x0069 }
//            goto L_0x006a
//        L_0x0069:
//        L_0x006a:
//            if (r2 == 0) goto L_0x006f
//            r2.close()     // Catch:{ IOException -> 0x006f }
//        L_0x006f:
//            goto L_0x0071
//        L_0x0070:
//            throw r6
//        L_0x0071:
//            goto L_0x0070
//        */
//        throw new UnsupportedOperationException("Method not decompiled: com.tech.lang.keyboard.hindikeyboard.HindiKeypad.copyAssetssticker(java.lang.String, java.io.File):void");
//    }
//
//    /* JADX WARNING: Removed duplicated region for block: B:16:0x002c A[SYNTHETIC, Splitter:B:16:0x002c] */
//    /* JADX WARNING: Removed duplicated region for block: B:21:0x0037 A[SYNTHETIC, Splitter:B:21:0x0037] */
//    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
//    /* Code decompiled incorrectly, please refer to instructions dump. */
//    private void saveImageData(String r4) {
//        /*
//            r3 = this;
//            android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
//            r0.<init>()
//            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFile(r4, r0)
//            android.graphics.Bitmap r0 = r3.changeBackground(r0)
//            r1 = 0
//            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0026 }
//            r2.<init>(r4)     // Catch:{ Exception -> 0x0026 }
//            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x0021, all -> 0x001e }
//            r1 = 100
//            r0.compress(r4, r1, r2)     // Catch:{ Exception -> 0x0021, all -> 0x001e }
//            r2.close()     // Catch:{ IOException -> 0x0030 }
//            goto L_0x0034
//        L_0x001e:
//            r4 = move-exception
//            r1 = r2
//            goto L_0x0035
//        L_0x0021:
//            r4 = move-exception
//            r1 = r2
//            goto L_0x0027
//        L_0x0024:
//            r4 = move-exception
//            goto L_0x0035
//        L_0x0026:
//            r4 = move-exception
//        L_0x0027:
//            r4.printStackTrace()     // Catch:{ all -> 0x0024 }
//            if (r1 == 0) goto L_0x0034
//            r1.close()     // Catch:{ IOException -> 0x0030 }
//            goto L_0x0034
//        L_0x0030:
//            r4 = move-exception
//            r4.printStackTrace()
//        L_0x0034:
//            return
//        L_0x0035:
//            if (r1 == 0) goto L_0x003f
//            r1.close()     // Catch:{ IOException -> 0x003b }
//            goto L_0x003f
//        L_0x003b:
//            r0 = move-exception
//            r0.printStackTrace()
//        L_0x003f:
//            throw r4
//        */
//        throw new UnsupportedOperationException("Method not decompiled: com.tech.lang.keyboard.hindikeyboard.HindiKeypad.saveImageData(java.lang.String):void");
//    }
//
//    private Bitmap changeBackground(Bitmap bitmap) {
//        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
//        Canvas canvas = new Canvas(createBitmap);
//        canvas.drawColor(-1);
//        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
//        return createBitmap;
//    }
//
//    private void copyFile(InputStream inputStream, OutputStream outputStream) throws IOException {
//        byte[] bArr = new byte[1024];
//        while (true) {
//            int read = inputStream.read(bArr);
//            if (read != -1) {
//                outputStream.write(bArr, 0, read);
//            } else {
//                return;
//            }
//        }
//    }
//
//    private void initStickerList(String str) {
//        this.stickersList.clear();
//        HindiUtils.selectedCateGory = "category0";
//        this.icons = null;
//        File file = new File(HindiUtils.storePath + "/" + str);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String[] list = file.list();
//        this.offlineFiles = list;
//        for (String replace : list) {
//            this.stickersList.add(new StickerModel(replace.replace(".png", ""), true, 1, SessionManager.IS_COUNT));
//        }
//        StickerListAdapter stickerListAdapter = new StickerListAdapter(getApplicationContext(), this.stickersList, str);
//        this.stickerlistadapter = stickerListAdapter;
//        this.stickerList.setAdapter((ListAdapter) stickerListAdapter);
//        this.kv.setVisibility(8);
//        this.otherContentLay.setVisibility(8);
//        this.r2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), 17432576));
//        this.otherContentLay.removeAllViews();
//    }
//
//    private void initStickerAdapter(String str) {
//        this.stickers.clear();
//        this.icons = null;
//        File file = new File(HindiUtils.storePath + "/sticker/" + str);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String[] list = file.list();
//        this.offlineFiles = list;
//        for (String replace : list) {
//            this.stickers.add(new StickerModel(replace.replace(".png", ""), true, 1, SessionManager.IS_COUNT));
//        }
//        this.stickergrid.setNumColumns(this.isLandscape ? 4 : 3);
//        this.stickergrid.setHorizontalSpacing(5);
//        this.stickergrid.setVerticalSpacing(5);
//        this.stickergrid.setGravity(17);
//        StickerAdapter stickerAdapter2 = new StickerAdapter(getApplicationContext(), this.stickers, 0, this.kv.getHeight(), str);
//        this.stickerAdapter = stickerAdapter2;
//        this.stickergrid.setAdapter(stickerAdapter2);
//        this.kv.setVisibility(8);
//        this.otherContentLay.setVisibility(8);
//        this.r2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), 17432576));
//        this.otherContentLay.removeAllViews();
//    }
//
//    private void clickCategory(String str) {
//        this.stickers.clear();
//        File file = new File(HindiUtils.storePath + "/sticker/" + str);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String[] list = file.list();
//        this.offlineFiles = list;
//        for (String replace : list) {
//            this.stickers.add(new StickerModel(replace.replace(".png", ""), true, 1, SessionManager.IS_COUNT));
//        }
//        StickerAdapter stickerAdapter2 = new StickerAdapter(getApplicationContext(), this.stickers, 0, this.kv.getHeight(), str);
//        this.stickerAdapter = stickerAdapter2;
//        this.stickergrid.setAdapter(stickerAdapter2);
//    }
//
//    private String[] getImage(String str) throws IOException {
//        return getAssets().list(str);
//    }
//
//    /* access modifiers changed from: private */
//    public AlertDialog dialogAskInstallSTT() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Install Google Voice For Voice Translate");
//        builder.setPositiveButton("Install Now!", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//                try {
//                    HindiKeypad.this.getApplicationContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.android.googlequicksearchbox")));
//                } catch (ActivityNotFoundException unused) {
//                    HindiKeypad.this.getApplicationContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox")));
//                }
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//            }
//        });
//        return builder.create();
//    }
//
//    public boolean isOnline() {
//        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    /* access modifiers changed from: package-private */
//    public void showToast(String str, int i) {
//        Toast toast = this.mThongBao;
//        if (toast != null) {
//            toast.cancel();
//        }
//        if (str.equalsIgnoreCase("")) {
//            this.mThongBao.cancel();
//            return;
//        }
//        Toast makeText = Toast.makeText(this, str, i);
//        this.mThongBao = makeText;
//        makeText.setGravity(81, 0, 200);
//        this.mThongBao.show();
//    }
//
//    /* access modifiers changed from: private */
//    public void doTheDownAnimation(int i, int i2) {
//        int i3 = this.mLevel - 100;
//        this.mLevel = i3;
//        this.mImageDrawable.setLevel(i3);
//        if (this.mLevel >= i2) {
//            this.mDownHandler.postDelayed(this.animateDownImage, 10);
//            return;
//        }
//        this.mDownHandler.removeCallbacks(this.animateDownImage);
//        this.fromLevel = i2;
//    }
//
//    /* access modifiers changed from: private */
//    public void doTheUpAnimation(int i, int i2) {
//        int i3 = this.mLevel + 100;
//        this.mLevel = i3;
//        this.mImageDrawable.setLevel(i3);
//        if (this.mLevel <= i2) {
//            this.mUpHandler.postDelayed(this.animateUpImage, 10);
//            return;
//        }
//        this.mUpHandler.removeCallbacks(this.animateUpImage);
//        this.fromLevel = i2;
//    }
//
//    /* access modifiers changed from: private */
//    public AlertDialog dialogSettingGoogleApp() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Please Enable Micro Permission");
//        builder.setTitle("SettingActivity Permissions");
//        builder.setPositiveButton("Go To SettingActivity", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//                HindiKeypad.this.goToGoogleSettings();
//            }
//        });
//        return builder.create();
//    }
//
//    /* access modifiers changed from: private */
//    public void goToGoogleSettings() {
//        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:com.google.android.googlequicksearchbox"));
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setFlags(268435456);
//        startActivity(intent);
//    }
//
//    public void translateWithSpeech() {
//        this.speaktotextbtn.setVisibility(0);
//        this.proTalk.setVisibility(8);
//        if (!this.mNgonNguIn.getmText().equals("")) {
//            showProgressDialog(this, "Translating...");
//            showProgressDialog(this, "Translating...");
//        }
//    }
//
//    public void showProgressDialog(Context context, String str) {
//        hideProgressDialog();
//        this.progressDialog.setCancelable(true);
//    }
//
//    public void hideProgressDialog() {
//        ProgressDialog progressDialog2 = this.progressDialog;
//        if (progressDialog2 != null) {
//            if (progressDialog2.isShowing()) {
//                this.progressDialog.dismiss();
//            }
//            this.progressDialog = null;
//        }
//    }
//
//    class myAsyncTask extends AsyncTask<Void, Void, Void> {
//        myAsyncTask() {
//        }
//
//        /* access modifiers changed from: protected */
//        public void onPreExecute() {
//            super.onPreExecute();
//            ArrayList arrayList = new ArrayList();
//            arrayList.add("");
//            HindiKeypad.this.hlist.setAdapter((ListAdapter) HindiUtils.setSuggestionAdapter(HindiKeypad.this.getApplicationContext(), arrayList, HindiKeypad.this.selectedTheme, HindiKeypad.this.hlist.getWidth()));
//        }
//
//        /* access modifiers changed from: protected */
//        public Void doInBackground(Void... voidArr) {
//            try {
//                if (HindiKeypad.this.word.equals("")) {
//                    return null;
//                }
//                HindiKeypad.this.result = HindiUtils.getSuggestion(HindiKeypad.this.word);
//                return null;
//            } catch (Exception unused) {
//                return null;
//            }
//        }
//
//        /* access modifiers changed from: protected */
//        /* JADX WARNING: Can't wrap try/catch for region: R(6:6|7|8|(1:10)|11|12) */
//        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0051 */
//        /* Code decompiled incorrectly, please refer to instructions dump. */
//        public void onPostExecute(Void r6) {
//            /*
//                r5 = this;
//                java.lang.String r0 = ""
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r1 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.lang.String r1 = r1.word     // Catch:{ Exception -> 0x00e6 }
//                boolean r1 = r1.equals(r0)     // Catch:{ Exception -> 0x00e6 }
//                if (r1 != 0) goto L_0x00c1
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r0 = r0.result     // Catch:{ Exception -> 0x00e6 }
//                int r0 = r0.size()     // Catch:{ Exception -> 0x00e6 }
//                r1 = 1
//                if (r0 < r1) goto L_0x0073
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r0 = r0.result     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad$myAsyncTask$1 r1 = new com.tech.lang.keyboard.hindikeyboard.HindiKeypad$myAsyncTask$1     // Catch:{ Exception -> 0x00e6 }
//                r1.<init>()     // Catch:{ Exception -> 0x00e6 }
//                java.util.Collections.sort(r0, r1)     // Catch:{ Exception -> 0x00e6 }
//                java.util.HashSet r0 = new java.util.HashSet     // Catch:{ Exception -> 0x00e6 }
//                r0.<init>()     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r1 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r1 = r1.result     // Catch:{ Exception -> 0x00e6 }
//                r0.addAll(r1)     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r1 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r1 = r1.result     // Catch:{ Exception -> 0x00e6 }
//                r1.clear()     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r1 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r1 = r1.result     // Catch:{ Exception -> 0x00e6 }
//                r1.addAll(r0)     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x0051 }
//                java.util.ArrayList<java.lang.String> r0 = r0.result     // Catch:{ Exception -> 0x0051 }
//                if (r0 == 0) goto L_0x0051
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x0051 }
//                java.util.ArrayList<java.lang.String> r0 = r0.result     // Catch:{ Exception -> 0x0051 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad$MyComparator r1 = new com.tech.lang.keyboard.hindikeyboard.HindiKeypad$MyComparator     // Catch:{ Exception -> 0x0051 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r2 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x0051 }
//                r1.<init>()     // Catch:{ Exception -> 0x0051 }
//                java.util.Collections.sort(r0, r1)     // Catch:{ Exception -> 0x0051 }
//            L_0x0051:
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HorizontalListView r0 = r0.hlist     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r1 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r2 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r2 = r2.result     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r3 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                int r3 = r3.selectedTheme     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r4 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HorizontalListView r4 = r4.hlist     // Catch:{ Exception -> 0x00e6 }
//                int r4 = r4.getWidth()     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.adapter.HintWordAdapter r1 = com.tech.lang.keyboard.hindikeyboard.HindiUtils.setSuggestionAdapter(r1, r2, r3, r4)     // Catch:{ Exception -> 0x00e6 }
//                r0.setAdapter((android.widget.ListAdapter) r1)     // Catch:{ Exception -> 0x00e6 }
//                goto L_0x00e6
//            L_0x0073:
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r0 = r0.result     // Catch:{ Exception -> 0x00e6 }
//                int r0 = r0.size()     // Catch:{ Exception -> 0x00e6 }
//                if (r0 > 0) goto L_0x00e6
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                r1 = 0
//                r0.result = r1     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x00e6 }
//                r1.<init>()     // Catch:{ Exception -> 0x00e6 }
//                r0.result = r1     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r0 = r0.result     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r1 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.lang.String r1 = r1.word     // Catch:{ Exception -> 0x00e6 }
//                r0.add(r1)     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r0 = r0.result     // Catch:{ Exception -> 0x00e6 }
//                java.lang.String r1 = "Touch to add"
//                r0.add(r1)     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HorizontalListView r0 = r0.hlist     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r1 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                android.content.Context r1 = r1.getApplicationContext()     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r2 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                java.util.ArrayList<java.lang.String> r2 = r2.result     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r3 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                int r3 = r3.selectedTheme     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r4 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HorizontalListView r4 = r4.hlist     // Catch:{ Exception -> 0x00e6 }
//                int r4 = r4.getWidth()     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.adapter.HintWordAdapter r1 = com.tech.lang.keyboard.hindikeyboard.HindiUtils.setSuggestionAdapter(r1, r2, r3, r4)     // Catch:{ Exception -> 0x00e6 }
//                r0.setAdapter((android.widget.ListAdapter) r1)     // Catch:{ Exception -> 0x00e6 }
//                goto L_0x00e6
//            L_0x00c1:
//                java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x00e6 }
//                r1.<init>()     // Catch:{ Exception -> 0x00e6 }
//                r1.add(r0)     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r0 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HorizontalListView r0 = r0.hlist     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r2 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                android.content.Context r2 = r2.getApplicationContext()     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r3 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                int r3 = r3.selectedTheme     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HindiKeypad r4 = com.tech.lang.keyboard.hindikeyboard.HindiKeypad.this     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.HorizontalListView r4 = r4.hlist     // Catch:{ Exception -> 0x00e6 }
//                int r4 = r4.getWidth()     // Catch:{ Exception -> 0x00e6 }
//                com.tech.lang.keyboard.hindikeyboard.adapter.HintWordAdapter r1 = com.tech.lang.keyboard.hindikeyboard.HindiUtils.setSuggestionAdapter(r2, r1, r3, r4)     // Catch:{ Exception -> 0x00e6 }
//                r0.setAdapter((android.widget.ListAdapter) r1)     // Catch:{ Exception -> 0x00e6 }
//            L_0x00e6:
//                super.onPostExecute(r6)
//                return
//            */
//            throw new UnsupportedOperationException("Method not decompiled: com.tech.lang.keyboard.hindikeyboard.HindiKeypad.myAsyncTask.onPostExecute(java.lang.Void):void");
//        }
//    }
//
//    public class MyComparator implements Comparator<String> {
//        public MyComparator() {
//        }
//
//        public int compare(String str, String str2) {
//            if (str.length() > str2.length()) {
//                return 1;
//            }
//            if (str.length() < str2.length()) {
//                return -1;
//            }
//            return str.compareTo(str2);
//        }
//    }
//
//    class C03953 implements RecognitionListener {
//        public void onEvent(int i, Bundle bundle) {
//        }
//
//        public void onPartialResults(Bundle bundle) {
//        }
//
//        C03953() {
//        }
//
//        public void onReadyForSpeech(Bundle bundle) {
//            int unused = HindiKeypad.this.thoiGianCho = 100;
//            Log.d("speechtotext", "onReadyForSpeech");
//            HindiKeypad.this.speak_lay.setVisibility(0);
//            HindiKeypad.this.bottomlay.setVisibility(8);
//            boolean unused2 = HindiKeypad.this.isSpeeching = true;
//            if (HindiUtils.speakLangName.equals("en")) {
//                HindiKeypad.speakstring.setText("Let's Speak");
//            } else {
//                HindiKeypad.speakstring.setText(HindiKeypad.this.getResources().getString(R.string.error_text10));
//            }
//        }
//
//        public void onBeginningOfSpeech() {
//            Log.d("speechtotext", "onBeginningOfSpeech");
//        }
//
//        public void onRmsChanged(float f) {
//            int i;
//            boolean unused = HindiKeypad.this.isSpeeching = true;
//            Log.i("speechtotext", "onRmsChanged: " + f);
//            if (f < 0.0f) {
//                HindiKeypad hindiKeypad = HindiKeypad.this;
//                int unused2 = hindiKeypad.thoiGianCho = hindiKeypad.thoiGianCho - 1;
//                if (HindiKeypad.this.thoiGianCho <= 0) {
//                    onError(5);
//                }
//            }
//            if (f < 0.0f) {
//                f = 3.0f;
//            }
//            if (f > 0.0f && HindiKeypad.toLevel != (i = (int) ((f * 10000.0f) / 10.0f)) && i <= 10000) {
//                if (i > 10000) {
//                    i = HindiKeypad.toLevel;
//                }
//                int unused3 = HindiKeypad.toLevel = i;
//                if (HindiKeypad.toLevel > HindiKeypad.this.fromLevel) {
//                    HindiKeypad.this.mDownHandler.removeCallbacks(HindiKeypad.this.animateDownImage);
//                    int unused4 = HindiKeypad.this.fromLevel = HindiKeypad.toLevel;
//                    HindiKeypad.this.mUpHandler.post(HindiKeypad.this.animateUpImage);
//                    return;
//                }
//                HindiKeypad.this.mUpHandler.removeCallbacks(HindiKeypad.this.animateUpImage);
//                int unused5 = HindiKeypad.this.fromLevel = HindiKeypad.toLevel;
//                HindiKeypad.this.mDownHandler.post(HindiKeypad.this.animateDownImage);
//            }
//        }
//
//        public void onBufferReceived(byte[] bArr) {
//            Log.d("speechtotext", "onBufferReceiverd");
//        }
//
//        public void onEndOfSpeech() {
//            HindiKeypad.this.speak_lay.setVisibility(8);
//            HindiKeypad.this.bottomlay.setVisibility(0);
//            if (HindiKeypad.this.isSpeeching) {
//                HindiKeypad.this.proTalk.setVisibility(0);
//            }
//            Log.d("speechtotext", "onEndOfSpeech");
//        }
//
//        public void onError(int i) {
//            if (HindiKeypad.this.isSpeeching) {
//                HindiKeypad.this.speaktotextbtn.setVisibility(0);
//                HindiKeypad.this.proTalk.setVisibility(8);
//                if (i != 3 || Build.VERSION.SDK_INT < 23) {
//                    String errorText = HindiKeypad.getErrorText(i);
//                    Log.d("speechtotext", "FAILED " + errorText);
//                    HindiKeypad.this.showToast(errorText, 1);
//                    boolean unused = HindiKeypad.this.isSpeeching = false;
//                } else {
//                    HindiKeypad.this.dialogSettingGoogleApp().show();
//                }
//                HindiKeypad.this.mSpeechReco.destroy();
//                return;
//            }
//            HindiKeypad.this.mSpeechReco.startListening(HindiKeypad.this.recognizerIntent);
//            Log.d("speechtotext", "bị Repeat nè ..... ");
//        }
//
//        public void onResults(Bundle bundle) {
//            Log.d("speechtotext", "onResults");
//            boolean unused = HindiKeypad.this.isSpeeching = false;
//            HindiKeypad.this.speaktotextbtn.setVisibility(0);
//            HindiKeypad.this.proTalk.setVisibility(8);
//            ArrayList<String> stringArrayList = bundle.getStringArrayList("results_recognition");
//            if (stringArrayList != null) {
//                if (stringArrayList.size() == 1) {
//                    HindiKeypad.this.mNgonNguIn.setmText(stringArrayList.get(0));
//                } else {
//                    InputConnection currentInputConnection = HindiKeypad.this.getCurrentInputConnection();
//                    currentInputConnection.commitText("" + stringArrayList.get(0).toString(), 0);
//                    Context applicationContext = HindiKeypad.this.getApplicationContext();
//                    Toast.makeText(applicationContext, "" + stringArrayList.get(0).toString(), 1).setGravity(81, 0, 0);
//                }
//            }
//            HindiKeypad.this.mSpeechReco.destroy();
//        }
//    }
//
//    class C03922 implements Runnable {
//        C03922() {
//        }
//
//        public void run() {
//            HindiKeypad hindiKeypad = HindiKeypad.this;
//            hindiKeypad.doTheDownAnimation(hindiKeypad.fromLevel, HindiKeypad.toLevel);
//        }
//    }
//
//    class C03911 implements Runnable {
//        C03911() {
//        }
//
//        public void run() {
//            HindiKeypad hindiKeypad = HindiKeypad.this;
//            hindiKeypad.doTheUpAnimation(hindiKeypad.fromLevel, HindiKeypad.toLevel);
//        }
//    }
//}
