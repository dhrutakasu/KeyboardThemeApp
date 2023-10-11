package com.theme.keyboardthemeapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.karumi.dexter.PermissionToken;
import com.theme.keyboardthemeapp.KeyboardView.CustomKeypad;
import com.theme.keyboardthemeapp.ModelClass.JokeModelItem;
import com.theme.keyboardthemeapp.ModelClass.QuoteCategoryModelItem;
import com.theme.keyboardthemeapp.ModelClass.QuoteModelItem;
import com.theme.keyboardthemeapp.UI.Adapters.HintWordListAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
    public static final int OVERLAY_REQUEST_CODE = 101;
    public static final String ARRAY_LIST_QUOTE = "ARRAY_LIST_QUOTE";
    public static final String BUNDLE_LIST = "BUNDLE_LIST";
    public static final String QUOTE_POS = "QUOTE_POS";
    public static final String TITLES = "TITLES";
    public static final String BASE_URL1 = "http://technoappsolution.com/app/";
    public static final String BASE_URL = "https://hpqrzfjgciltygmpyjss.supabase.co/rest/v1/";
    public static final String QUOTE_BASE_URL = "QuotesList?parent_id=eq.";
    public static final String GIF_URL = "keybord_fileparth?select=*,hindithemekeyboard(id,name)";
    public static final String THEME_URL1 = "assets/android/keyboard/keyboard.json";
    public static final String THEME_URL = "keybord_fileparth?select=*,keyboard(id,name)";
    public static final String QUOTES_CATEGORY_URL = "QuoteCategories?select=*";
    public static final String JOKE_CATEGORY_URL1 = "assets/android/hindikeyboard/hindijokes.json";
    public static final String JOKE_CATEGORY_URL = "HindiJokes?select=*";
    public static final String FILE_PATH = "FILE_PATH";
    public static ArrayList<QuoteModelItem> statusItems = new ArrayList<>();
    public static ArrayList<JokeModelItem> jokeModelItems = new ArrayList<>();
    public static ArrayList<QuoteCategoryModelItem> categoriesItems = new ArrayList<>();
    public static boolean enableKeyboard = true;
    public static String[][] FancyNameStyle = null;
    public static String[] ArtCelebration = {"\n      ✨。 🌟。    ✨\n 。     ☁  🎄  ☁   。。\n     ✨    🎄🎄。 。   ✨\n✨ ☁  🎄⛄🎄 ☁  ✨\n     。 🎄🍰🎀🎄 。✨\n 。   🎄🔔🎄🐻🎄。。\n   ✨  🎄🎄🎀🎄。\n。。🎄🎄⚾🎄🎄。 ✨\n     🎄🎄🎀🎄🍭🎄\n  🎄🎐🎄🎄🔱🎄🎄\n✨ 🎄🎀🎄🎄💓🎄✨\n   🎄🎄🍀🎄🎄🎀🎄\n🎄💎🎄🎀💛🎄🎄🎄\n                MERRY\n      ✨CHRISTMAS ✨\n   🎁🎁🎁🎁🎁🎁🎁", "       ✨        ✨       ✨\n       ✴️       ✴️       ✴️\n       ✴️       ✴️       ✴️\n🍪🍪🍪🍪🍪🍪🍪🍪\n🍰🍰🍰🍰🍰🍰🍰🍰\n🍪🍪🍪🍪🍪🍪🍪🍪\n🍰🍰🍰🍰🍰🍰🍰🍰\n🍪🍪🍪🍪🍪🍪🍪🍪\n🍫🍫🍫🍫🍫🍫🍫🍫", "           🔥🔥🔥🔥\n        ⭐|WINNER|⭐\n   ⭐🏆🏆💓🏆🏆⭐\n     ⭐🏆🏆 🏆🏆⭐\n       ⭐🏆 💓 🏆⭐\n                🏆 🏆\n                   🏆\n                🏆 🏆\n             🎉 🎉 🎉", " 💗💗💗💗💗💗💗\n   🎉✨*🌟✨*😍🎵\n      Congratulations!!\n ❤❤❤❤❤❤❤\n  😘*✨🎵*✨🌺🎉\n 💛💛💛💛💛💛💛", "   🎂　 🌟　˚　 。   🎶\n              🔥🔥🔥\n      🍴 😘│ │ │🍅\n      ✨ ┎─────┒🎉\n  *  🎵┃🎂🎂🎂┃ ˚💐\n     🍊┎┸────┸┒🎉\n    。┃~ⓗⓐⓟⓟⓨ~┃🎵\n       ┎┸──────┸┒\n    ┃ⓑⓘⓡⓣⓗⓓⓐⓨ┃\n 🍓🍓🍓🍓🍓🍓🍓🍓\n         ┸────────┸", "  §💛❤ For You ❤💛§\n  §🎀Valentine's Day🎀§\n    ✨🌹🌹    🌹🌹✨\n   🌹🎁👫🌹👫🎁🌹\n   🌹💎🎉💏🎉💎🌹\n ✨ 🌹💋 💍 💋🌹 ✨\n       ✨ 🌹💝🌹  ✨\n            ✨  🌹 ✨\n         🍃     🌹\n             🍃 🌹", "🐻🌟。❤。😉。🍀🌻\n 💝。🎐🎁 。🎉🌟🌸\n 🎀✨。＼｜／。🌺 🔔\n 🍻 Happy New Year! 🍊\n🎵 💜。／｜＼。💎 🎈 \n🍀。☀。 🌹。🌙。💓\n💋🌟。 😍。 🎶✨🎁", "  🎅🎅🎅🎅🎅🎅🎅\n  🎅🔔🔔🔔🔔🔔🎅\n  🎅🔔🎁🎁🎁🔔🎅\n  🎅🔔🎁🎄🎁🔔🎅\n  🎅🔔🎁🎄🎁🔔🎅\n  🎅🔔🎁🎁🎁🔔🎅\n  🎅🔔🔔🔔🔔🔔🎅\n  🎅🎅🎅🎅🎅🎅🎅\n               MERRY\n           CHRISTMAS", "     🍀.          🍀.      🍀 \n🍀🍀🌺🍀🍀🌺🍀🍀\n     🍀          🍀.       🍀\n🍀🍀🌺🍀🍀🌺🍀🍀\n      ST. PATRICK'S DAY\n🍀🍀🌺🍀🍀🌺🍀🍀\n     🍀.        🍀.        🍀\n🍀🍀🌺🍀🍀🌺🍀🍀\n     🍀.        🍀.        🍀", "       💜💛       💚💙\n   💙🌸🌸❤🌸🌸❤\n    💛🌸I💗U Mom!💜\n         💚🌸🌸🌸💛\n              💜🌸❤\n                   💙", "           👿            👿\n        🎃🎃🎃🎃🎃\n  🎃🎃🔥🎃🎃🔥🎃\n🎃🎃🎃🎃🎃🎃🎃🎃\n🎃🔥🔻🔻🔻🔻🔥🎃\n🎃🎃🔥🔺🔺🔥🎃🎃\n     🎃🎃🎃🎃🎃🎃", "  🌟🌟🌟🌟🌟🌟🌟\n 🌟         Happy         🌟\n 🌟        father`s        🌟\n 🌟      💗day💗      🌟\n 🌟            👱           🌟\n 🌟       👦👔🙆     🌟\n 🌟       👕👖👗     🌟\n 🌟       💙💜💚     🌟\n 🌟 🌟🌟🌟🌟🌟🌟", "  ✨🌹🌹🌹🌹🌹✨\n  🌹✨✨🎀✨✨🌹\n  🌹🌟🎁🙆🎁🌟🌹\n  🌹🌟🎁💎🎁🌟🌹\n  🌹🌟🎁💓🎁🌟🌹\n  🌹    Mom Happy    🌹\n   ✨🌹🌹🌹🌹🌹✨", "               Ⓗⓐⓟⓟⓨ \n             Ⓢⓟⓡⓘⓝⓖ\n         Ⓕⓔⓢⓣⓘⓥⓐⓛ\n✨🎉✨🎉✨🎉✨🎉✨\n🎉💐🌷💐🌷💐🌷💐🎉\n✨🌷🎁🎂🎁🎂🎁🌷✨\n🎉💐🎂🌟💗🌟🎂💐🎉\n✨🌷🎁💗㊗💗🎁🌷✨\n🎉💐🎂🌟💗🌟🎂💐🎉\n✨🌷🎁🎂🎁🎂🎁🌷✨\n🎉💐🌷💐🌷💐🌷💐🎉\n✨🎉✨🎉✨🎉✨🎉✨", "   ㊗Happy new year 🎉\n✨✨🎉🎉🎉🎉✨✨\n   🎉🌸🌺🌸🌺🌸🎉\n   🌺╗╔╔╗╔╗╔╗╗╔ 🌸\n   🌸╠╣╠╣╠╝╠╝╚╣ 🌺\n   🎉🌺new   year🌸🎉\n✨🎉🌸🌺🌸🌺🎉✨\n✨✨🎉🌺🌸🎉✨✨\n✨✨✨🎉🎉✨✨✨", "🎂🎂🎂🎂🎂🎂🎂🎂\n🌺🍊🍏🍓🍀🌺🍃❤\n🍰🍰🍓🍓🍓🍓🍰🍰\n🍰🍰   H A P P Y   🍰🍰\n🍏🍊🍓🍉🍊🍏🍓🍉\n🎀    B I R T H D A Y   🎀\n🌺🍊🍉🍏🍀🌺🍃🌺\n🎂🎂🎂🎂🎂🎂🎂🎂", "        🎈🎈🎈🎈🎈\n         ✨my dear✨\n       🌹🎩🎩🎩🌹\n       🌹🎩🎩🎩🌹\n       🎩🎩🎩🎩🎩\n       🌹😊😊😊🌹\n       😊♥😊♥😊\n       💗😊😊😊💗\n       😊💗💗💗😊\n       🌹😊😊😊🌹\n       💎💎happy 💎\n         🎶valentine's\n       🎁day🎁🎁🎁\n       🔔🔔🔔🔔🔔", "  *  🎵┃🎂🎂🎂┃ ˚💐😄😊😃☺😉😍😘😳\n  ❤*New*YearBegins❤\n👦👧👩👨👶👵👴👱\n  💗~GODbless'YOU'💗\n👲👳👮👼👸🙆🙇👽\n  💛& GoodLuckToU~💛\n🐶🐹🐰🐯🐻🐷🐮🐵\n  💜Happy NewYear 💜\n😚😁😌😜😝🎅🎃🎶", "   。      ☁   。。   🌙。\n       *        🎄        *    。\n   🚀      💝💓      。\n  。。  🌟👼✨\n*       🌸🐷🎀🌺    ☁\n *    🐚🐔⛄🐭🍚\n            🌵🍃👗       *\n☁    🐳💎📫🚙🐬\n      ・Happy Holiday・ *", " *    🐚🐔⛄🐭🍚          💓  +:\n       🌸🐷🎀🌺    ☁         🍸***🔥***🍸\n          ✨+:.┃:+✨\n          🎀┏┻┓🎀\n          🔔┃    ┃🔔\n          🌹┃    ┃🌹\n               Cheers\n          🌹┃    ┃🌹\n          🎁┃    ┃🎁\n          🎁┗━┛🎁", "😄🌟🌟🎵🎵🌟🌟😍\n🔑🎂❤❤❤❤🎂🔑\n🌹 💛*YOU ARE*💛 🌹\n🌹💗💗~MY~💗💗🌹\n🌹 💚 +LOVELY+💚 🌹\n🌹💜VALENTINE 💜🌹\n🔑🎂❤❤❤❤🎂🔑\n😚🌟🌟🎵🎵🌟🌟😃", "       🌸🐷🎀🌺    ☁                \n             * V👀V。*💗˚\n             ˚(=🔴=) 🌟 。\n     ┏👏━━🎄🎅┓\n      ❆😊HAPPY😄❆\n       🎂 HOLIDAY 🎂", "                   🌟              \n                   🔔\n                 🎅🎅\n              🔔🔔🔔\n           🎅🎅🎅🎅\n                🔔🔔\n             🎅🎅🎅\n          🔔🔔🔔🔔\n       🎅🎅🎅🎅🎅       ", "        🎈     🎈     🎈\n    🎈🎊🎉🎂🎉🎊🎈\n    ✨🎉🎁🎊🎁🎉✨\n       😖🌟⭐✨⭐✨\n        ✨🌟⭐🌟😖\n             😖🌟😖"};
    public static String[] ArtChristmas = {"🎈\n\n            🏃\n🌾🌾🌾🌾🌾🌾"};
    public static String[] ArtEaster = {"  ☀☀☁☁☀☁☀\n  ☁☁☁👼☁☁☁\n  ☁ Happy Easter! ☁\n  🐔🐣🐣🐣🐣🐣🐔\n  🌸🍃🌸🍃🌸🍃🌸\n  🍃🍃🍃🍃🍃🍃🍃", "                🐣🐣\n             🐣🐣🐣\n           🐣🐣🐣🐣\n             🐣🐣🐣\n                HAPPY\n              EASTER\n      🎀🎀🎀🎀🎀🎀", "❄❄❄🐔🐔❄❄❄\n❄❄🐔🔴🔴🐔❄❄\n❄🐔🔴🔴🔴🔴🐔❄\n❄🐔🔴🔴🔴🔴🐔❄\n🐔🔴🔴🐣🐣🔴🔴🐔\n🐔🔴🐣🐣🐣🐣🔴🐔\n🐔🔴🐣🐣🐣🐣🔴🐔\n🐔🔴🔴🐣🐣🔴🔴🐔\n❄🐔🔴🔴🔴🔴🐔❄\n❄❄🐔🐔🐔🐔❄❄", "     🍳🍳🐥🐥🍳🍳\n     🍳 🐥  🐥  🐥 🍳\n     🍳🐥🐥🐥🐥🍳\n     🍳🐥🐥🐥🐥🍳\n     🍳 🐥  🐥  🐥 🍳\n     🍳🍳🍳🍳🍳🍳\n 🐣HAPPY EASTER🐣", "🌺🌺🌺🌺🌺🌺🌺🌺\n🌺🌸🌸🌸🌸🌸🌸🌺\n🌺🌸🐣   HAPPY 🌸🌺\n🌺🌸 EASTER🐣 🌸🌺\n🌺🌸🌸🌸🌸🌸🌸🌺\n🌺🌺🌺🌺🌺🌺🌺🌺", "           💐👫😻🐀\n      🚗🌹🐝🐻🍦🍧\n  🎎🎅👻🎃🍎🍫📓\n😍 HAPPY   EASTER 🍀\n   ☔️⚡️🐍🐔🌄💃🔑\n     🍳📣🎐🔮🍒🍩\n           😌👯👣💍"};
    public static String[] ArtFood = {"               🌴🌴\n              🍎🍎🍎\n           🍎🍎🍎🍎\n              🍎🍎🍎", "     🍏🍏\n  🍏 🍎🍎\n 🍏🍎🍎🍎\n 🍏🍎🍎🍎🍎\n   🍏 🍎🍎🍎🍎\n       🍏 🍎🍎🍎🍎\n           🍏🍎 🍎 🍎 🍎\n                🍏🍏🍏🍏🍏", "  🍷🍷🍷🌟🍷🍷🍋\n  🍷🍷🍷🍷🍷🍋🍷\n  🍷🍷🍷🍷🍋🍷🍷\n  🍊🍊🍊🍊🍊🍊🍊\n  🍷🍊🍊🍊🍊🍊🍷\n  🍷🍷🍊🍊🍊🌟🍷\n  🍷🍷🍷🍊🍷🍷🍷\n  🍷🌟🍷🍊🍷🍷🍷\n  🍷🍷🍷🍊🍷🍷🍷\n  🍷🍊🍊🍊🍊🍊🍷\n  🍒🍒🍒🍒🍒🍒🍒", "     💨💨\n     💨💨💨💨\n     🍻🍻🍻🍻🍻\n     🍻🍻🍻🍻   🍻\n     🍻🍻🍻🍻      🍻\n     🍻🍻🍻🍻      🍻\n     🍻🍻🍻🍻   🍻\n     🍻🍻🍻🍻🍻", "  🍺🍻🍺🍻🍺🍻🍺\n   ┳╮🍺🎵🍻🎵🍺\n   ┣┻╮*╭╮╭╮┣╮\n   ┃✨┃┣┛┣┛┃\n   ┻━╯╰╯╰╯┻\n 🎵🍻🎵🍺🎵🍻🎵", "      🔴         🍞🔳🍳\n       \\=☕__      \\u0020| /\n                          /  \n      Breakfast in bed?", "                🍔🍔\n         🍔 🍔 🍔 🍔\n      🍔 🍔 🍔 🍔 🍔\n     🍩🍩🍩🍩🍩🍩\n  🍔🍔🍔🍔🍔🍔🍔", "          🍭🍭\n     🍭🍭🍭🍭\n   🍭🍭🍭🍭🍭\n        🍭🍭🍭\n                 🍭\n                     🍭  \n                         🍭\n                             🍭", "                   🍦\n                 🍦🍦\n              🍦🍦🍦\n           🍦🍦🍦🍦\n           🍯🍯🍯🍯\n             🍯 🍯 🍯\n             🍯 🍯 🍯\n              🍯🍯🍯", "     🍒 🍒 🍒 🍒\n        🍒  🍒  🍒   🍒\n        🍒        🍒\n        🍒          🍒\n      🍒 🍒     🍒🍒\n   🍒 🍒 🍒🍒 🍒 🍒\n   🍒 🍒 🍒 🍒🍒 🍒\n       🍒🍒        🍒🍒", "                 🍕🍕🍕\n            🍕🍕🍕🍕\n         🍕 🍕 🍕 🍕\n                🍕 🍕 🍕\n                  🍕🍕🍕\n                       🍕🍕\n                            🍕", "       💭  💭  💭\n       💭  💭  💭\n       ☕️☕️☕️☕️\n     ☕️☕️☕️☕️  ☕️\n    ☕☕️☕️☕️      ☕️\n    ☕️☕️☕️☕️      ☕️\n     ☕️☕️☕️☕️    ☕️\n       ☕️ ☕️ ☕️ ☕️", "   🌿🌿\n   🌿   🌿\n        🌿🌿\n           🍍🍍\n           🍍 🍍 🍍\n           🍍🍍🍍🍍\n                🍍 🍍 🍍\n                     🍍🍍", "🍰🍰🍰🍫🍰🍰🍰🍰\n🍰🍰🍫🍫🍫🍰🍰🍰\n🍰🍫🍫🍫🍫🍫🍰🍰\n🍫🍫🍫🍫🍫🍫🍫🍰\n🍰🍫🍫🍫🍫🍫🍫🍫\n🍰🍰🍫🍫🍫🍫🍫🍰\n🍰🍰🍰🍫🍫🍫🍰🍰\n🍰🍰🍰🍰🍫🍰🍰🍰", "🍋🍋🍋🍋🍋🍋🍋🍋\n🍋🍋🍋🍪🍪🍋🍋🍋\n🍋🍋🍪🍪🍪🍪🍋🍋\n🍋🍪🍪🍪🍪🍪🍪🍋\n🍋🍪🍪🍪🍪🍪🍪🍋\n🍋🍋🍪🍪🍪🍪🍋🍋\n🍋🍋🍋🍪🍪🍋🍋🍋\n🍋🍋🍋🍋🍋🍋🍋🍋", "             🍋 🍋\n         🍋 🍋🍋🍋\n        🍋🍋🍋🍋🍋\n          🍋 🍋 🍋 🍋\n              🍋 🍋 🍋\n                   🍋🍋"};
    public static String[] ArtFun = {"  🌏🌏🌏😍🌏🌏🌏\n  🌏🌏😍😍😍🌏🌏\n  🌏😍🌏😍🌏😍🌏\n  😍🌏🌏😍🌏🌏😍\n  😍🌏🌏😍🌏🌏🌏\n  🌏😍🌏😍🌏🌏🌏\n  🌏🌏😍😍😍🌏🌏\n  🌏🌏🌏😍🌏😍🌏\n  🌏🌏🌏😍🌏🌏😍\n  😍🌏🌏😍🌏🌏😍\n  🌏😍🌏😍🌏😍🌏\n  🌏🌏😍😍😍🌏🌏\n  🌏🌏🌏😍🌏🌏🌏", "     🐵🐵⚪⚪🐵🐵\n🐵🐵⚪⚪⚪⚪🐵🐵\n🐵🐵⚪⚪⚪⚪🐵🐵\n🐵🐵⭕️⚪⚪⭕️🐵🐵\n     ⚪⚪⚪⚪⚪⚪\n        ⚪⚪♦⚪⚪\n             ⚪👅⚪", "           🌚🌚🌚🌚\n        🌚🌝🌚🌝🌚\n      🌚 🌚 🌚 🌚 🌚\n        🌚🐁🌞🐁🌚\n           🌚🌚🌚🌚\n              🌚🌚🌚\n     🌚🌚🐁🐁🌚🌚\n  🌚🌚 🐁🐁🐁🌚🌚\n  🌚🌚 🐁🐁🐁🌚🌚\n     🌚🌚🐁🐁🌚🌚\n       🌚🌚🌚🌚🌚\n           🐁🐁🐁🐁", "             👜👜👜\n            👜       👜\n      👛👜 👛 👜👛 \n    👛👛👛👛👛👛\n  👛👛👛👛👛👛👛\n  👛👛👛👛👛👛👛", "  💦💦💦✨💦💦💦\n  💦💦💦☔💦💦💦\n  💦💦☔☔☔💦💦\n  💦☔☔☔☔☔💦\n  ☔☔☔☔☔☔☔\n  💦💦💦🌂💦💦💦\n  💦💦💦🌂💦💦💦\n  💦🌂💦🌂💦💦💦\n  💦💦🌂💦💦💦💦\n  💦💦💦💦💦💦💦", "  ⭕️🍻🍻🍻🍻🍻⭕️\n  🍻⭕️🍻🍻🍻⭕️🍻\n  🍻🍻⭕️🍻⭕️🍻🍻\n  🍻🍻🍻⭕️🍻🍻🍻\n  🍻🍻⭕️🍻⭕️🍻🍻\n  🍻⭕️🍻🍻🍻⭕️🍻\n  ⭕️🍻🍻🍻🍻🍻⭕️", "  ⭕️🚬🚬🚬🚬🚬⭕️\n  🚬⭕️🚬🚬🚬⭕️🚬\n  🚬🚬⭕️🚬⭕🚬🚬\n  🚬🚬🚬⭕️🚬🚬🚬\n  🚬🚬⭕️🚬⭕️🚬🚬\n  🚬⭕️🚬🚬🚬⭕️🚬\n  ⭕️🚬🚬🚬🚬🚬⭕️", "💭💭💭-  🚀 -💭✈️💭\n💭💭💭🚀🚀💭💭💭\n💭💭💭🚀🚀💭💭💭\n💭💭💭🚀🚀💭💭💭\n💭💭🚀🚀🚀🚀💭💭\n💭🚀🚀🚀🚀🚀🚀💭\n🚀🚀🚀🚀🚀🚀🚀🚀\n🚀🚀💭🚀🚀💭🚀🚀\n🚀💭💭🚀🚀💭💭🚀\n💭💭💭🚀🚀💭💭💭\n💭💭🚀🚀🚀🚀💭💭\n💭💭💭💭💭💭💭💭", "   (👈👈MAKE WAY❕)\n      ￣￣￣￣￣￣￣￣￣\n          🍉🍘🍟🍺🍢🍛\n   ┏┯🍦🍚🍲🍰🍅🍧\n┏┛😁┗☕🍙🍔🍞🍡\n┗🔳━━🔳✨┛💨💨", "     🍏🍏🍏🍏🍏🍏\n     🍏🍏🍏🍏 An🍏\n     🍏 Apple 🍏  A 🍏\n     🍏🍏  Day  🍏 🍏\n     🍏🍏   Keeps    🍏\n     🍏The Doctor    🍏\n     🍏🍏 Away!🍏🍏\n     🍏🍏🍏🍏🍏🍏", "    ☀   ☁       ☁   ☁\n    ☁     ☁    🍀🍀  ☁\n        🏢🏢🏢🏢🏢\n        🏢🙇🏢🏢🏢\n        🏢🏢🏢🏢🏢\n        🏢🏢🏢✋🏢\n        🏢👪🏢🏢🏢\n        🏢🏢🏢🏢🏢\n        🏢🏢🏢👫🏢\n        🏢👯🏢🏢🏢\n        🏢🏢🏢🏢🏢\n   🏢🏢🏢💏🏢🏢🏢", "📱📱📱📱📱📱📱📱\n🙅🙅🙅STOP🙅🙅🙅\n  ⚠⚠ PLAYING ⚠⚠\n  👉YOUR IPHONE!!📢\n😡😡😡🙇🙇😡😡😡\n📱📱📱📱📱📱📱📱", "                   😊🍛\n              ☕👔👆\n                   👖\n                 👟👟", "                   🎩     💶\n                   👩     💰 \n              🔫👕👉💰\n                   👖\n                 👢👢\n              💀💀💀\n           💀💀💀💀\n         💀💀💀💀💀\n      💀💀💀💀💀💀\n    💀💀💀💀💀💀💀", "  👯👯👯📢👯👯👯\n  👯👯📢👯📢👯👯\n  👯📢👯👯👯📢👯\n  👯👯📢👯📢👯👯\n  👯👯👯📢👯👯👯\n    TIME🕖TO AWAKE\n    DO NOT FALL 🙅👎\n  A SLEEP AGAIN😱🔫", "  🌳🌲🍀🌺🌳🍀🌲\n  🌲🌻🌳🌲🍀🌸🌳\n  🌳💬  i'm in park!  🌲\n  👩🌳🌻🌳🍀🌲🍀\n  🌳🌺🍀🌳🌲🍀🌳\n  🍀🌳🌳🌲🌻🌳🌲", "                                   🎩\n                              🎩😍\n                         🎩😃👔\n                   🎩😊👔👖\n              🎩😊👔👖👟\n         🎩😏👔👖👟💰\n   🎩😕👔👖👟💰💰\n   😣👕👖👟💰💰💰\n   👕👖👟💰💰💰💰\n   👖👟💰💰💰💰💰\n   👟💰💰💰💰💰💰", "          🍺🍺             👨\n   __🍺🍺🍺__/👈 👔\n   🍺🍞🍺🍺/        💼\n     📼📼📼 /          👖\n  🔘            🔘      👟👟 ", "                  😜💙\n            💻🎽📲\n                  👖\n                👟👟\n  I⃣T⃣ M⃣A⃣N⃣", "   🌙  ☁☁      🌟  👀\n       ✦     🚥           ☁\n   🌟    [￣✡￣]  🌟\n          [   👽/  ]      🚀\n   |￣￣     ▲    ￣￣|  🌟\n     🐒     | |  UFO /\n    ￣￣￣  ⚡￣￣￣ ✦\n      ⚠   ⚡⚡\n   🌟   ⚡⚡⚡💨 😱", "    🇰🇷👩🌟🌟🇬🇧🎅\n    🇷🇺🌟🎶🎩🌟👳\n    🇺🇸🌟👋😄🌟👶\n    👮🇮🇹🌟🌟👸🇪🇸\n❕HELLO❤WORLD❕", "  🕝            ✅✨❌❌\n                  ❌✅💹✨\n       👵      ✅😒📝📝\n  🎍👕👆      👕📝\n  📼📼📼      👖\n  🔲🔲🔲    👟👟", "               Let`s go\n           🎱🎮🎵⛳\n           👾🎸🎳🚴\n           🏄🎷🏂⚽", "⚡    ☁☁   '  ' ☁\n   ☁  '  💦 '     ''   ⚡\n ☁ '   '  ''⚡  ☁☁☁\n''   💦  '    '    ''   💦\n'' ☁  ☔    ' ☂ ☂   ☁\n 🐸☝ 🙆💦   ☔  ☂\n🌂ⓡⓐⓘⓝⓨⓓⓐⓨ💦", "  🚑🚜🚒🚕💭💭💭\n  🚥💭💭💭🚛🚗🚜\n  🚑💭⚠🚜💭💭💭\n  🚕🚗🚙🚛🚚🚐💭\n  🚥💭💭🚑🚒🚓💭", "              ❄  🏄\n        😪 ☕  🏊 😄 💦\n   ⛄😖 🏂  ⛳ 😎 👙\n        😬 🎿  ☀ 🍧\n              ❄   🌴", "        🔫🔫🔫🔫🔫\n        🔫0⃣0⃣7⃣🔫\n        🔫🔫🔫🔫🔫", "  🐱🐱🐱🐱🐱🐱🐱\n  🐱😂😍😃😍😔🐱\n  🐱😂💰💰💰😍🐱\n  🐱😢💰😘💰😃🐱\n  🐱😔💰💰💰😍🐱\n  🐱😍😔😢😂😂🐱\n  🐱🐱🐱🐱🐱🐱🐱", "  💩💩💩💩💩💩💩\n  💩💀🔚✈➕🍺💩\n  💩💩💩💩💩💩💩", "             *    💰 🌙\n                💰💰  \n             💰🔝💰\n           💰🔝🔝💰   *   \n          💰 🔝🔝 💰    \n         💰 💰 💰 💰 🌟\n         💰 🔥 🔥 💰", "     🔆\n  🎌🏠🌲🏆👏🏠🏤\n      🚴     🏇🏇🏇🏇\n  🏁🏠✋🏠⌚🏠🌲", "             📴📴📴\n             📴📵📴\n             📴📴📴", "        📺📺📺📺📺\n        📺👽👽👽📺\n        📺👽👽👽📺\n        📺👽👽👽📺\n        📺📺📺📺📺", "            ✨       🌙\n        ✨          ✨\n              ✨\n        ⛽     🚚    🌵", "          🐟\n               \\ \n                 \\😮\n                  👕\n                👟👟"};
    public static String[] ArtLove = {"     ⭐⭐⭐⭐⭐⭐\n        ❄❄❄❄❄\n  ❄❄❄❄❄❄❄\n  ❄❄❄💍❄❄❄\n       ❄❄❄❄❄\n             ❄❄❄\n                  ❄", "        😍😍     😍😍\n    😍         😍         😍\n     😍                     😍\n        😍                😍\n           😍         😍\n               😍 😍\n                  😍", "        ❤                ❤\n     ❤ ❤          ❤ ❤\n   ❤❤❤❤❤❤❤\n     ❤❤❤❤❤❤\n        ❤❤❤❤❤\n           ❤❤❤❤\n              ❤❤❤\n                 ❤❤\n                    ❤", "       💖 💖      💖 💖\n💘💏💏💖💖💏💏💘\n💘💏💏💏💏💏💏💘\n💘💏💏💏💏💏💏💘\n      💘💏💏💏💏💘\n           💘💏💏💘\n                 💘💘", "☁️☁️☁️✨☁️✨☁️☁️\n☁️☁️☁️☁💎☁️☁️☁️\n☁️☁️☁️💍💍💍☁️☁️\n☁️☁️💍☁️☁️☁️💍☁️\n☁💍☁️☁️☁️☁️💍☁️\n☁️💍☁️☁️☁️☁️💍☁️\n☁️💍☁️☁️☁️💍☁️☁️\n☁️☁️💍💍💍☁️☁️☁️\n☁️☁️☁️☁️☁️☁️☁️☁️", "☁️☁️☁️✨☁️✨☁️☁️\n☁️☁️☁️☁💎☁️☁️☁️\n☁️☁️☁️💍💍💍☁️☁️\n☁️☁️💍☁️☁️☁️💍☁️\n☁💍☁️☁️☁️☁️💍☁️\n☁️💍☁️☁️☁️☁️💍☁️\n☁️💍☁️☁️☁️💍☁️☁️\n☁️☁️💍💍💍☁️☁️☁️\n☁️☁️☁️☁️☁️☁️☁️☁️  §💛❤ For You ❤💛§\n  §🎀Valentine's Day🎀§\n    ✨🌹🌹    🌹🌹✨\n   🌹🎁👫🌹👫🎁🌹\n   🌹💎🎉💏🎉💎🌹\n ✨ 🌹💋 💍 💋🌹 ✨\n       ✨ 🌹💝🌹  ✨\n            ✨  🌹 ✨\n         🍃     🌹\n             🍃 🌹", "💗💗💗          🍀🍀🍀\n💗👧✌➕📩👋👱🍀\n💗💗💗          🍀🍀🍀  ", "     💋 💋       💋 💋\n💋✨✨💋💋✨✨💋\n💋✨⭐✨✨⭐✨💋\n💋✨⭐⭐⭐⭐✨💋\n   💋✨⭐😍⭐✨💋\n     💋✨⭐⭐✨💋\n            💋 ⭐ 💋\n                   💋", "       💓💓💓💓💓\n       💎💎💎💎💎\n       💎💏💘 💑💎\n       💎💎💎💎💎\n       💓💓💓💓💓", "       🎩      💍    👒\n       👨     🍀    👩\n       👔👍🌹👈💓\n       👖                👗\n     👟👟          👠👠\n  🌻🌻🌻🌻🌻🌻🌻\n  🌸🌸🌸🌸🌸🌸🌸", "  💓🌹🌹💓🌹🌹💓\n  🌹😍😍😍😍😍🌹\n  🌹😍😍😍😍😍🌹\n  💓🌹😍😍😍🌹💓\n  💓💓🌹😍🌹💓💓\n  💓💓💓🌹💓💓💓", "             💗You💗\n             ✨are✨\n                  👸\n            ✌👗🌹\n                  👖\n                👟👟\n  🌟MY PRINCESS!🌟", "  ☀☀☀☀☀☀☀\n  ☀🌹🌹☀🌹🌹☀\n  🌹☀☀🌹☀☀🌹\n  🌹 MISS ☀  YOU 🌹\n  ☀🌹☀☀☀🌹☀\n  ☀☀🌹☀🌹☀☀\n  ☀☀☀🌹☀☀☀", "  🌙🌙🌙🌹🌙🌙🌙\n  🌙🌙🌹🌙🌹🌙🌙\n  🌙🌹🌙🌙🌙🌹🌙\n  🌹 LOVE 🌙  YOU 🌹\n  🌹🌙🌙🌹🌙🌙🌹\n  🌙🌹🌹🌙🌹🌹🌙\n  🌙🌙🌙🌙🌙🌙🌙", "        🌹🌹     🌹🌹\n    🌹         🌹         🌹\n   🌹  ❤️❤️❤️❤️  🌹\n       🌹      💎       🌹\n    。   🌹           🌹  。\n   🎈  🌟🌹 🌹          。\n      。    🎶🌹    🍀  。", "     💐　      ☁      ＋🌟\n🌸  ＼ 😄 / ＼ 😊 /\n  Ⓛⓘⓥⓔ ⓘⓝ ⓛⓞⓥⓔ\n           👖        👗\n         👟👟   👡👡", "  🌹🌹❤️🅰❤🌹🌹\n        🌹🌹💗🌹🌹\n          🌹🎉🎉🌹\n             🌹💝🌹\n                ❤️❤️\n             🌟❤🌟", "                  🐷\n              🐷  🐷\n            🐷 ❤ 🐷\n     ✨PIGGY LOVE!✨\n 🎁happy anniversary!🎁\n ❤I love you so much!❤", "☀☁☀🌞💛🌞☀☁\n       Love is in the air!\n  ☁☁👦❤👩☁☁\n  ☁☁👵❤👴☁☁\n  ☁☁😍❤🐱☁☁\n  ☁☁👲❤👧☁☁\n  🌷🌷🌷🌷🌷🌷🌷", "  😘😘😘😘😘😘😘\n  😘💓💓😘💓💓😘\n  💓😘😘💓😘😘💓\n    💓MISS 😘 YOU💓\n  😘💓😘😘😘💓😘\n  😘😘💓😘💓😘😘\n  😘😘😘💓😘😘😘", "🎁 LOVE LOVE LOVE 🎁\n🎁 😘~💍👈 😍❤ 🎁\n🎁 ONLY FOR * YOU *🎁", "         。✨🎩    \n         ＊🌹😉~🌟\n         、☝👔👍+\n         ℱǾℜ ƳǾǓ❤", "  ❤💛💙💚💓💙❤\n  📢  Where is Love  📡\n  📢😳😳😳😳😳📡\n  📢   😞😞😞😞  🔊\n  📢     😖😖😖     🔊\n  📢        😭😭       📡", "🎶🎶🎶🎶🎶🎶🎶🎶\n🎶🎶🎶🎶🎶🎶🎶🎶\n🎶 🎶 ᎾNᏞY 4 Ꮜ  🎶 🎶\n🎶🎶🎶🎶🎶🎶🎶🎶\n 。*   🎶🎶🎶, ': 、'\n  +  *. 、'🎶🎶。*   ∴\n  ∴ 。  : *,🎶 . *.. 、 。\n🌙🌟✨' ~🎷👄✨🌟", "  ☁☁☁☁☁☁☁\n  ☀☀☔☔☔☀☀\n  ✨✨🎩✨✨✨✨\n  ✨✨😃✨😄✨✨\n  ✨✨🎓✨👘✨✨\n🍀🌻🌺🎾🎾🌹🌷🌸\n🌴🌴🌴🌴🌴🌴🌴🌴", "  💓🔫🔫💓🔫🔫💓\n  🔫🔫🔫🔫🔫🔫🔫\n  🔫🔫🔫🔫🔫🔫🔫\n  💓🔫🔫🔫🔫🔫💓\n  💓💓🔫🔫🔫💓💓\n  💓💓💓🔫💓💓💓", "        🐼💚     💜🐼\n     💜        🐶        💚\n     🐶                    🐶\n       💚                💜\n          🐼          🐼\n              💜   💚\n                  🐶", "                 💍👫\n           💭💍👫💑\n      😔👦💍👫💒👰\n           💭💍👫💑\n                💍👫", "        😝🍭      🍭😝\n     🍭        😝        🍭\n      😝                     😝\n        🍭                🍭\n           😝          😝\n              🍭   🍭\n                  😝", "        👰💍👰💎👰\n        💎💭💓💭💍\n        👰💓👩💓👰\n        💍💭💓💭💍\n        👰💎👰💍👰", "  📝📝📝📝📝📝📝\n  📝💗📩💗📩💗📝\n  📝💗📬📬📬💗📝\n  📝💗📬😍📬💗📝\n  📝💗📬📬📬💗📝\n  📝💗📩💗📩💗📝\n  📝📝📝📝📝📝📝", "  💓🎸🎸💓🎸🎸💓\n  🎸🎸🎸🎸🎸🎸🎸\n  🎸🎸🎸🎸🎸🎸🎸\n  💓🎸🎸🎸🎸🎸💓\n  💓💓🎸🎸🎸💓💓\n  💓💓💓🎸💓💓💓", "        ✨ /■ ■ ■ \\~🎶\n           |   ❤ ❤  |  ✨\n          |       👃   👂\n       ✨ ＿💋＿/\n      Falling in LOVE\n    💘💓💏🎉💑💗\n       I Love You🌹😘", "  💎💎💎💎💎💎💎\n  💎   ┌──────┐    💎 \n  💎   │ ❤ I  ❤│    💎\n  💎   │ ❤ L ❤│    💎\n  💎   │ ❤ u ❤│    💎\n  💎   └──────┘    💎\n  💎💎💎💎💎💎💎", "  🎶😍😍🎶😍😍🎶\n  😍😍😍😍😍😍😍\n  😍😍😍😍😍😍😍\n  🎶😍😍😍😍😍🎶\n  🎶🎶😍😍😍🎶🎶\n  🎶🎶🎶😍🎶🎶🎶"};
    public static String[] ArtMood = {"     😡                😡😡\n      😡😡           😡😡\n      😡😡😡      😡😡\n      😡😡😡😡😡😡\n      😡😡      😡😡😡\n      😡😡           😡😡\n      😡😡                 😡\n            😠😠😠😠\n      😠😠😠😠😠😠\n      😠😠           😠😠\n      😠😠           😠😠\n      😠😠           😠😠\n      😠😠           😠😠\n      😠😠😠😠😠😠\n            😠😠😠😠", "          😄          😄\n            😄      😄\n               😄😄\n                  😄\n                  😄\n\n             😄😄😄\n             😄\n             😄😄😄\n             😄\n             😄😄😄\n\n              😄😄😄\n          😄          \n            😄😄😄\n                          😄\n           😄😄😄", "💔          💔      🙈🙈\n💔💔     💔  🙈        🙈\n💔    💔 💔  🙈        🙈\n💔          💔      🙈🙈", "           💬💬💬💬\n        💬🌹🌹🌹 💬\n        💬 I'm sorry! 💬\n          💬💬💬💬\n         😥💦\n         🙏\n         👖\n       👟👟", "     😂😂😂😂😂😂\n     😂👏👏👏👏😂\n     😂👏  LOL  👏😂\n     😂👏🎵🎵👏😂\n     😂👏👏👏👏😂\n     😂😂😂😂😂😂", "  ❤💔💔💔💔💔❤\n  📞     Waiting 4       📞\n  📱  your message   📱\n  📢😞😔😞😔😞📢\n  📱   😭😭😰😰   📱\n  📞     😢😢😢       📞\n  ❤💔💔💔💔💔❤", "😡😡😡🔥🔥👿👿👿\n😡🔥🔥🔥🔥🔥👿🔥\n😡😡😡🔥🔥🔥👿🔥\n😡🔥🔥🔥🔥🔥👿🔥\n😡🔥🔥🔥🔥🔥👿🔥\n😡🔥🔥🔥🔥👿👿👿\n👿👿👿🔥🔥😡😡😡\n👿🔥🔥👿🔥😡🔥🔥\n👿👿👿🔥🔥😡😡😡\n👿👿🔥🔥🔥😡🔥🔥\n👿🔥👿🔥🔥😡🔥🔥\n👿🔥🔥👿🔥😡😡😡", "❌ ..ⒷⓁⓊⒺ ⒹⒶⓎ.. ❌\n☁☁☁☁☁☁☁☁\n☔☔☔☔☔☔☔☔\n😞😔😓😥😣😨😢😞\n👎👎👎👎👎👎👎👎\n💬💦💬💦💬💦💬💦\n💔💔💔💔💔💔💔💔\n💩💀💩💀💩💀💩💀", "😭😥😭😥😭😥😭😥\n  💔💔    FEEL   💔💔 \n     ALONE UNHAPPY  \n😥😭😥😭😥😭😥😭", "        💜💛💗💚💙\n        ♥ CHEER ♥\n        .*🌟╦╦╔╗🌟*        *,🌟║║╠╝🌟,*\n        :*🌟╚╝╩+🌟*:\n        🍀🍀🍀🍀🍀", "😜😃😊 😜😀😛😊😃\n😌😂👑    BE   😊😃😜\n💛😊😄     H    😌🌟😂\n😚😄💦     A    💛😊😄\n😃🌟😛     P     😚😄💦\n😃😍😊     P    😝💁😏  \n 😇🐷🌟    Y     🐷😃🌟\n😜😝🙆🙆😝😃😍😊", "    💬💬💬💬💬💬\n💬     Lunch time!!!      💬\n     💬💬💬💬💬💬\n      😀😃😍😃😀\n  👌👕👕👔👗👕✌\n  🔳🍀🍀🍀🍀🍀🔳\n  🔳🔳🔳🔳🔳🔳🔳", "     ☁☁☁☁☁☁\n     💦💦💦😢💦💦\n     💦💦👋👕💦💦\n     💦💦💦👖💦💦\n     💦💦💦👟👟💦\n     🌳🌳🚂🚌🚌🚌      \n     🎶🎶🎶🎶🎶🎶", "      😝😝😝😝😝\n 😝😊😊😊😊😊😝\n 😝😝😊😊😊😝😝\n 😝😊😊😊😊😊😝👍\n 😝😊😊😝😊😊😝\n      😝😝😝😝😝", "                   💩\n                💩💩\n           💩💩💩💩\n        💩💩🎱💩💩\n   💩💩💩💩💩💩💩\n💩💩💩💩💩💩💩💩", "        🆒😎🆒😎🆒\n        😎🆒😎🆒😎\n        🆒😎🆒😎🆒\n        😎🆒😎🆒😎\n        🆒😎🆒😎🆒", " 🎤    😄   🎶🎶🎶🎶\n           👕\n           |   |\n         👟👟", "                   🎩\n                   😡💨\n              👊👕👊\n                 👟👟", "     🍃💦🍂💦🍁💦\n     💦🍁💦💦💦🍃\n     🍀💦😔💦🍂💦\n     💦🍂👕🍁💦🍀\n     💦💦👖💦💦💦\n     🌾🌾👟👟🌾🌾", "              💢     🕑\n              😕     💈\n         💐👕     💈\n              👖     💈\n            👟👟  💈", "       😊😊        😊😊\n    😊🎯☺   😛🎯😊\n   😊😊☺😛😛😊😊\n    😊📘☺😛📘😊✌\n           😊📘📘😊\n                   ☺", " 👎👎\n 👎👎\n   👎\n    👎\n  🚬🚬🚬🚬🚬🚬🚬\n  💩💩💩💩💩💩💩", "           😱😱😱😱\n           😱✨✨✨\n           😱✨✨✨\n           😱😱😱😱\n           😱✨✨✨\n           😱✨✨✨", "                  🎩\n          👂💰💰👂\n                  👃\n            🚬⭕💨\n           💪 👔✌\n                 /    \n             👟   👟", "🚥⚠⚠⚠⚠⚠⚠🚥\n⚠🚚🚓🚕🚲⛵💢⚠\n⚠🚃🚗🚙🚌🚓🚒⚠\n⚠🚤🚑💢🚚🚲🚙⚠\n⚠🚓🚒🚃🚕💢🚗⚠\n😡   TRAFFIC    JAM   😡\n🚧⚠⚠⚠⚠⚠⚠🚧", "     🕛|🕐🕑🕒🕓🕔\n     💣 |＿    ＿   |  ＿⚡\n     💣 |   |  |＿| |  |＿|⚡\n     💣 |   |  |＿  |  |    ⚡\n     💣❕🏃~  ＿    ⚡\n     💣 |ー|ー|   |＿|   ❕\n     💣 |    |    |   |＿ 😱\n    🕕🕖🕗🕘🕙🕚", "           💬💬💬💬\n         💬facepalm!💬\n          💬 💬💬💬\n        💬\n       😑😑😑😑😑\n       😑😑👋👋😑\n       😑😑👋👋👋\n       😑😑👋👋👋\n       😑😑😑👋👋\n       😑😑😑😑😑", "        😭😭😭😭😭\n        😭❌😭😭❌\n        😭😭😭😭😭\n        😭😭🚧🚧😭\n        🚧😭😭🚧😭\n        😭😭😭😭😭", "           💩💩💩💩\n           💩🐖💨💩\n           💩💩💩💩", "👏😃👏😜👏😊👏😍\n👏😊👏😎👏😃👏😜\n👏😃👏😜👏😊👏😍\n👏😊👏😜👏😃👏😎\n👏😃👏😜👏😊👏😍\n👏😊👏😎👏😃👏😜"};
    public static String[] ArtNature = {"                ☘☘\n              ☘🌸☘\n           ☘🌸🌸☘\n        ☘🌸🌸🌸☘\n           ☘🌸🌸☘\n              ☘🌸☘\n                 ☘☘\n                   🌰\n                   🌰\n                   🌰\n        ✳✳✳✳✳", "                   🔶\n       🔶       🔶       🔶\n              🔶🔶🔶\n   🔶🔶🔶🔶🔶🔶🔶\n              🔶🔶🔶\n       🔶       🔶       🔶\n                   🔶", "               😍 😘\n            😘 ❤️ 😍\n               😍 😘\n                   🌵\n                      🌵\n                   🌵\n              🍂🍂🍂", "           😍😍😍😍\n        😍😍😍😍😍\n     😍😍😍😍😍😍\n  😍😍😍😍😍😍😍\n  😍😍😍😍😍😍😍\n     😍😍😍😍😍😍\n          😍😍😍😍\n               🍔🍔\n               🍔🍔\n               🍔🍔\n      🌿🌿🌿🌿🌿\n🍃🌿🌿🌿🌿🌿🍃"};
    public static String[] FancyDecorative = {"•?((¯°·._.• abc •._.·°¯))؟•", "¸,ø¤º°`°º¤ø,¸¸,ø¤º° abc °º¤ø,¸¸,ø¤º°`°º¤ø,¸", "°°°·.°·..·°¯°·._.· abc ·._.·°¯°·.·° .·°°°", "ıllıllı abc ıllıllı", "°°°·.°·..·°¯°·._.· abc ·._.·°¯°·.·° .·°°°", "•´¯`•. abc .•´¯`•", "×º°�?˜`�?°º× abc ×º°�?˜`�?°º×", "•]••´º´•» abc «•´º´••[•", "]|I{•------» abc «------•}I|[", "§.•´¨'°÷•..× abc ×,.•´¨'°÷•..§", "•°¯`•• abc ••´¯°•", "(¯`·.¸¸.·´¯`·.¸¸.-> abc <-.¸¸.·´¯`·.¸¸.·´¯)", "*´¯`*.¸¸.*´¯`* abc *´¯`*.¸¸.*´¯`*", "(¯`·.¸¸.-> °º abc º° <-.¸¸.·´¯)", "°·.¸.·°¯°·.¸.·°¯°·.¸.-> abc <-.¸.·°¯°·.¸.·°¯°·.¸.·°", "|!¤*'~``~'*¤!| abc |!¤*'~``~'*¤!|", "._|.<(+_+)>.|_. abc ._|.<(+_+)>.|_.", "•._.••´¯``•.¸¸.•` abc `•.¸¸.•´´¯`••._.•", "¸„.-•~¹°�?ˆ˜¨ abc ¨˜ˆ�?°¹~•-.„¸", "(¯´•._.• abc •._.•´¯)", "••¤(`×[¤ abc ¤]×´)¤••", "•´¯`•» abc «•´¯`•", "`•.,¸¸,.•´¯ abc ¯`•.,¸¸,.•´", "¸,ø¤º°`°º¤ø,¸ abc ¸,ø¤º°`°º¤ø,", ".o0×X×0o. abc .o0×X×0o.", ",-*'^'~*-.,_,.-*~ abc ~*-.,_,.-*~'^'*-,", "`•.¸¸.•´´¯`••._.• abc •._.••`¯´´•.¸¸.•`", "—(••÷[ abc ]÷••)—", "¤¸¸.•´¯`•¸¸.•..>> abc <<..•.¸¸•´¯`•.¸¸¤", "••.•´¯`•.•• abc ••.•´¯`•.••", ".•°¤*(¯`★´¯)*¤° abc °¤*(¯´★`¯)*¤°•.", "๑۞๑,¸¸,ø¤º°`°๑۩ abc ๑۩ ,¸¸,ø¤º°`°๑۞๑", "-漫~*'¨¯¨'*·舞~ abc ~舞*'¨¯¨'*·~漫-", "★·.·´¯`·.·★ abc ★·.·´¯`·.·★", "�? ▂ ▄ ▅ ▆ ▇ █ abc █ ▇ ▆ ▅ ▄ ▂ �?", "▀▄▀▄▀▄ abc ▄▀▄▀▄▀", "(-_-) abc (-_-)", "▌│█║▌║▌║ abc ║▌║▌║█│▌"};
    public static String TRANSLATOR_DATA = "TRANSLATOR_DATA";
    public static String RecentWord = null;
    public static boolean EngBool;
    public static String EngWord;
    public static String HindiWord;
    public static ViewPager PagerDictionary;
    public static boolean CheckLan = true;
    public static boolean IsPreview = true;
    public static boolean IsVibrate = false;
    public static boolean IsSuggest = true;
    public static boolean IsCaps = true;
    public static int KeyboardPHeight = -1;
    public static int KeyboardLHeight = -1;
    public static int ProgressSound = 10;
    public static int ProgressPotraitDefault = 0;
    public static int ProgressLandscapDefault = 0;
    public static int SuggestionText = 16;
    public static boolean IsSound = true;
    public static float SoundVolume = 0.3f;
    public static boolean IsCopyService = true;
    public static int SelectTheme = 0;
    public static String[] ThemePreviewTextColor = {"#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"};
    public static String[] ThemeTextColor = {"#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"};
    public static boolean IsTextColor = false;
    public static int TextColorCode = -1;
    public static int PreviewColorCode = -1;
    public static boolean IsColorCodeChange = false;
    public static int FontStyle = 0;
    public static final int[] ColorsList = {0, -14521120, -1092784, -1294214, -5552196, -12627531, -14575885, -10011977, -14273992, -8825528, -16611119, -16742021, -16757440, -13154481, -10453621, -16728876, -12434878, -10354454, -11922292, -6381922, -8825528, -2937041, -12756226, -12232092, -14983648, -37120, -10011977, -8708190, -16725933, -16540699, -720809, -769226, -16742021, -2818048, -16752540, -14606047, -16728155};
    public static String[] FontList = {"FontStyleList/Font7.ttf", "FontStyleList/Font9.ttf", "FontStyleList/Font10.ttf", "FontStyleList/Font12.ttf", "FontStyleList/Font14.ttf", "FontStyleList/Font15.ttf", "FontStyleList/Font16.ttf", "FontStyleList/Font17.ttf", "FontStyleList/Font18.ttf", "FontStyleList/Font19.otf", "FontStyleList/Font20.ttf", "FontStyleList/Font21.ttf", "FontStyleList/Font23.ttf", "FontStyleList/Font25.ttf", "FontStyleList/Font26.otf", "FontStyleList/Font27.otf", "FontStyleList/Font28.ttf", "FontStyleList/Font29.ttf", "FontStyleList/Font30.ttf", "FontStyleList/Font31.ttf", "FontStyleList/Font32.ttf"};
    public static String[] HindiFontList = {"FontLists/FontStyle1.ttf", "FontLists/FontStyle2.ttf", "FontLists/FontStyle3.ttf", "FontLists/FontStyle4.ttf", "FontLists/FontStyle5.ttf", "FontLists/FontStyle6.ttf", "FontLists/FontStyle7.ttf", "FontLists/FontStyle8.ttf", "FontLists/FontStyle9.ttf", "FontLists/FontStyle10.ttf"};
    public static boolean WordExistOrNot = true;
    public static boolean PreviewActivityIsOpenOrNot = false;
    public static int widths = 0;
    public static int heights = 0;
    public static int KeyboardHeight = -1;
    public static String dictionaryword = "";
    public static TextView TxtView = null;
    public static TextView TxtLongPressView = null;
    public static PopupWindow popupWindow = null;
    public static PopupWindow popupScreen = null;
    public static int ChangeLanguage = 0;

    public static boolean SuggestedView = true;
    public static ArrayList<String> SuggestionWordsList = new ArrayList<>(25000);
    public static boolean isUpHoneycombVersion = false;
    public static View KeypedView = null;
    public static int selectedLangName = 0;
    public static boolean DeleteValFlag = false;
    public static boolean PreviewViewisOpen = false;
    public static boolean DictionaryWordLoad = false;
    static String[] PckgeNames = {"com.whatsapp", "com.facebook.orca", "com.snapchat.android", "com.google.android.talk", "jp.naver.line.android", "com.viber.voip", "com.skype.raider", "com.twitter.android", "com.bsb.hike"};
    public static ArrayList<String> PackageNamesList = new ArrayList<>(Arrays.asList(PckgeNames));

    static String[] LanGuagesLists = {"Hindi", "English"};
    public static ArrayList<String> LangugaeArr = new ArrayList<>(Arrays.asList(LanGuagesLists));
    public static String SelectedLanguageName = "Hindi";
    public static String SpeakLanguageName = "hi";
    public static int FlagChangeLanguage = 0;
    public static final int CODE_ALPHABETS = -2830;
    public static final int CODE_EMOJI = -5000;
    public static int temp_flag = 0;
    public static String Error1 = "";
    public static String Error2 = "";
    public static String Error3 = "";
    public static String Error4 = "";
    public static String Error5 = "";
    public static String Error6 = "";
    public static String Error7 = "";
    public static String Error8 = "";
    public static String Error9 = "";
    public static String En_Error1 = "";
    public static String En_Error2 = "";
    public static String En_Error3 = "";
    public static String En_Error4 = "";
    public static String En_Error5 = "";
    public static String En_Error6 = "";
    public static String En_Error7 = "";
    public static String En_Error8 = "";
    public static String En_Error9 = "";
    public static String AppId = "ca-app-pub-3940256099942544~3347511713";
    public static String BannerAd = "ca-app-pub-3940256099942544/6300978111";
    public static String InterstitialAd = "ca-app-pub-3940256099942544/1033173712";
    public static String NativaAds = "ca-app-pub-3940256099942544/2247696110";
    public static String AppOpenAd = "ca-app-pub-3940256099942544/3419835294";
    public static String Show = "no";

    static {
        String[][] strArr = new String[24][];
        FancyNameStyle = strArr;
        strArr[0] = new String[]{"α", "в", "¢", "∂", "є", "ƒ", "g", "н", "ι", "נ", "к", "ℓ", "м", "η", "σ", "ρ", "q", "я", "ѕ", "т", "υ", "ν", "ω", "χ", "у", "z", " "};
        strArr[1] = new String[]{"ɐ", "b", "ς", "d", "є", "Ŧ", "ﻮ", "ђ", "i", "j", "к", "l", "m", "ñ", "Ø", "ק", "ợ", "г", "s", "t", "u", "ש", "w", "ץ", "א", "z", " "};
        strArr[2] = new String[]{"a", "в", "c", "d", "e", "ғ", "g", "н", "ι", "j", "ĸ", "l", "м", "u", "ổ", "p", "q", "r", "ѕ", "т", "υ", "v", "w", "х", "y", "z", " "};
        strArr[3] = new String[]{"Д", "þ", "¢", "Ð", "3", "ƒ", "g", "ђ", "î", "j", "k", "ℓ", "м", "₪", "ø", "Þ", "Q", "Я", "§", "†", "û", "√", "w", "×", "¥", "ž", " "};
        strArr[4] = new String[]{"Ä", "B", "Ċ", "Đ", "Ë", "₣", "Ġ", "Ħ", "Ï", "Ĵ", "Ķ", "Ļ", "M", "Ņ", "Ö", "P", "Ҩ", "Ŗ", "Ś", "Ť", "Ů", "V", "Ŵ", "X", "Ÿ", "Ź", " "};
        strArr[5] = new String[]{"ά", "в", "ς", "∂", "έ", "ғ", "ģ", "ħ", "ί", "ј", "ķ", "Ļ", "м", "ή", "ό", "ρ", "q", "ŕ", "ş", "ţ", "ù", "ν", "ώ", "x", "ч", "ž", " "};
        strArr[6] = new String[]{"┌a┐", "┌b┐", "┌c┐", "┌d┐", "┌e┐", "┌f┐", "┌g┐", "┌h┐", "┌i┐", "┌j┐", "┌k┐", "┌l┐", "┌m┐", "┌n┐", "┌o┐", "┌p┐", "┌q┐", "┌r┐", "┌s┐", "┌t┐", "┌u┐", "┌v┐", "┌w┐", "┌x┐", "┌y┐", "┌z┐", " "};
        strArr[7] = new String[]{"Λ", "B", "C", "D", "Σ", "F", "G", "Ή", "I", "J", "K", "L", "M", "П", "Ө", "P", "Q", "Я", "S", "Ŧ", "Ц", "V", "Щ", "X", "Y", "Z", " "};
        strArr[8] = new String[]{"å", "β", "ç", "ď", "£", "ƒ", "ğ", "Ћ", "!", "j", "ķ", "Ł", "๓", "ñ", "¤", "ק", "ợ", "ř", "§", "†", "µ", "√", "Ψ", "×", "ÿ", "ž", " "};
        strArr[9] = new String[]{"Λ", "Þ", "⊂", "Ð", "ξ", "∫", "g", "∏", "¡", "j", "k", "l", "m", "∩", "♡", "ᕈ", "σ", "®", "§", "t", "∪", "ν", "w", "×", "ÿ", "2", " "};
        strArr[10] = new String[]{"4", "8", "(", "d", "3", "f", "9", "h", "!", "j", "k", "1", "m", "ռ", "o̷", "𝒑", "q", "r", "5", "7", "u", "v", "w", "x", "y", "2", " "};
        strArr[11] = new String[]{"ª", "b", "¢", "Þ", "È", "F", "♀", "Ĥ", "Î", "j", "Κ", "¦", "∞", "η", "◊", "𝖕", "Õ", "r", "S", "⊥", "µ", "√", "w", "×", "ý", "z", " "};
        strArr[12] = new String[]{"[-a-]", "[-b-]", "[-c-]", "[-d-]", "[-e-]", "[-f-]", "[-g-]", "[-h-]", "[-i-]", "[-j-]", "[-k-]", "[-l-]", "[-m-]", "[-n-]", "[-o-]", "[-p-]", "[-q-]", "[-r-]", "[-s-]", "[-t-]", "[-u-]", "[-v-]", "[-w-]", "[-x-]", "[-y-]", "[-z-]", " "};
        strArr[13] = new String[]{"Æ", "þ", "©", "Ð", "∃", "ζ", "∉", "Η", "Ї", "¿", "¤", "∠", "m", "Ñ", "Θ", "¶", "Ø", "Ґ", "Š", "τ", "υ", "¥", "w", "χ", "y", "ž", " "};
        strArr[14] = new String[]{"α", "в", "c", "∂", "ε", "ғ", "g", "н", "ι", "נ", "к", "ℓ", "м", "η", "σ", "ρ", "q", "я", "s", "т", "υ", "v", "ω", "x", "ү", "z", " "};
        strArr[15] = new String[]{"a_", "b_", "c_", "d_", "e_", "f_", "g_", "h_", "i_", "j_", "k_", "l_", "m_", "n_", "o_", "p_", "q_", "r_", "s_", "t_", "u_", "v_", "w_", "x_", "y_", "z_", " "};
        strArr[16] = new String[]{"ä", "b", "ċ", "d", "ë", "f", "ġ", "h", "ï", "j", "k", "l", "m", "ᑎ", "ö", "ᕈ", "q", "r", "s", "t", "ü", "v", "w", "x", "ÿ", "ż", " "};
        strArr[17] = new String[]{"α", "в", "¢", "∂", "є", "f", "g", "н", "ι", "נ", "к", "ℓ", "м", "и", "σ", "ρ", "q", "я", "ѕ", "т", "υ", "ν", "ω", "χ", "у", "z", " "};
        strArr[18] = new String[]{"å", "ß", "¢", "Ð", "ê", "£", "g", "h", "ï", "j", "k", "l", "m", "ñ", "ð", "þ", "q", "r", "§", "†", "µ", "v", "w", "x", "¥", "z", " "};
        strArr[19] = new String[]{"ⓐ", "ⓑ", "ⓒ", "ⓓ", "ⓔ", "ⓕ", "ⓖ", "ⓗ", "ⓘ", "ⓙ", "ⓚ", "ⓛ", "ⓜ", "ⓝ", "ⓞ", "ⓟ", "ⓠ", "ⓡ", "ⓢ", "ⓣ", "ⓤ", "ⓥ", "ⓦ", "ⓧ", "ⓨ", "ⓩ", " "};
        strArr[20] = new String[]{"Á", "ß", "Č", "Ď", "Ĕ", "Ŧ", "Ğ", "Ĥ", "Ĩ", "Ĵ", "Ķ", "Ĺ", "M", "Ń", "Ő", "P", "Q", "Ŕ", "Ś", "Ť", "Ú", "V", "Ŵ", "Ж", "Ŷ", "Ź", " "};
        strArr[21] = new String[]{"ą", "β", "č", "ď", "€", "ƒ", "δ", "Ђ", "ί", "j", "Ќ", "ℓ", "๓", "ŋ", "๏", "ρ", "ợ", "я", "$", "ţ", "µ", "ѵ", "ώ", "ж", "¥", "ź", " "};
        strArr[22] = new String[]{"ค", "๒", "ς", "๔", "є", "Ŧ", "ﻮ", "ђ", "เ", "ן", "к", "l", "๓", "ภ", "๏", "ק", "ợ", "г", "ร", "t", "ย", "ש", "ฬ", "ץ", "א", "z", " "};
        strArr[23] = new String[]{"Ã", "β", "Č", "Ď", "Ẹ", "₣", "Ğ", "Ĥ", "Į", "Ĵ", "Ќ", "Ĺ", "ℳ", "Ň", "Ỗ", "Ҏ", "Q", "Ř", "Ŝ", "Ť", "Ụ", "V", "Ŵ", "Ж", "Ў", "Ż", " "};
    }

    public static int getAlphabatesInputText(char c) {
        String s = String.valueOf(c);
        int InputInt;
        switch (s.toLowerCase()) {
            case "a":
                InputInt = 0;
                break;
            case "b":
                InputInt = 1;
                break;
            case "c":
                InputInt = 2;
                break;
            case "d":
                InputInt = 3;
                break;
            case "e":
                InputInt = 4;
                break;
            case "f":
                InputInt = 5;
                break;
            case "g":
                InputInt = 6;
                break;
            case "h":
                InputInt = 7;
                break;
            case "i":
                InputInt = 8;
                break;
            case "j":
                InputInt = 9;
                break;
            case "k":
                InputInt = 10;
                break;
            case "l":
                InputInt = 11;
                break;
            case "m":
                InputInt = 12;
                break;
            case "n":
                InputInt = 13;
                break;
            case "o":
                InputInt = 14;
                break;
            case "p":
                InputInt = 15;
                break;
            case "q":
                InputInt = 16;
                break;
            case "r":
                InputInt = 17;
                break;
            case "s":
                InputInt = 18;
                break;
            case "t":
                InputInt = 19;
                break;
            case "u":
                InputInt = 20;
                break;
            case "v":
                InputInt = 21;
                break;
            case "w":
                InputInt = 22;
                break;
            case "x":
                InputInt = 23;
                break;
            case "y":
                InputInt = 24;
                break;
            case "z":
                InputInt = 25;
                break;
            default:
                InputInt=-1;
        }
        return InputInt;
    }

    public static boolean isNetworkAvailableoRnOT(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean IsEnableKeyboard(Context context) {
        return ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).getEnabledInputMethodList().toString().contains(context.getPackageName());
    }

    public static boolean IsActivateKeyboard(Context context) {
        return new ComponentName(context, CustomKeypad.class).equals(ComponentName.unflattenFromString(Settings.Secure.getString(context.getContentResolver(), "default_input_method")));
    }

    public static AlertDialog.Builder NoInternetConnection(final Activity activity) {
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

    public static void showSettingsDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permissions to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings(activity);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showPermissionDialog(final Activity activity, final PermissionToken permissionToken) {
        new AlertDialog.Builder(activity
        ).setMessage(R.string.MSG_ASK_PERMISSION).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permissionToken.cancelPermissionRequest();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permissionToken.continuePermissionRequest();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                permissionToken.cancelPermissionRequest();
            }
        }).show();
    }

    private static void openSettings(Activity activity) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivityForResult(intent, 101);
    }

    public static String JSONFromAsset(Context context, String AssetFileName) {
        try {
            InputStream open = context.getAssets().open(AssetFileName);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void hideKeyboard(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
        }
    }

    public static int DpToPx(Context context, int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) i, context.getResources().getDisplayMetrics());
    }

    public static int pxFromDp(Context context, float f) {
        return (int) (f * context.getResources().getDisplayMetrics().density);
    }

    public static int calculateInSize(BitmapFactory.Options options, int width, int height) {
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int val = 1;
        if (outHeight > height || outWidth > width) {
            int ValHeight = outHeight / 2;
            int i7 = outWidth / 2;
            while (ValHeight / val > height && i7 / val > width) {
                val *= 2;
            }
        }
        return val;
    }

    public static Bitmap adjustBitmap(String file, Bitmap bitmapAdujust) {
        try {
            int orientation = new ExifInterface(file).getAttributeInt("Orientation", 1);
            int degree = 0;
            if (orientation == 3) {
                degree = 180;
            } else if (orientation == 6) {
                degree = 90;
            } else if (orientation == 8) {
                degree = 270;
            }
            if (degree != 0) {
                int bitmapWidth = bitmapAdujust.getWidth();
                int bitmapHeight = bitmapAdujust.getHeight();
                Matrix matrix = new Matrix();
                matrix.preRotate(degree);
                bitmapAdujust = Bitmap.createBitmap(bitmapAdujust, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            }
            return bitmapAdujust.copy(Bitmap.Config.ARGB_8888, true);
        } catch (IOException unused) {
            return null;
        }
    }

    public static ArrayList<String> getDefaultLanguageArray() {
        ArrayList<String> LanguageList = new ArrayList<>();
        LanguageList.add("Hindi");
        LanguageList.add("English");
        return LanguageList;
    }

    public static File getBackgroundSave(Context context, int pos) {
        File file = new File(context.getFilesDir() + "/photo_save.jpeg");
        if (!file.exists()) {
            try {
                AssetManager manager = context.getAssets();
                manager.open("ThemeBgLists/" + context.getAssets().list("ThemeBgLists")[pos]);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                AssetManager contextAssets = context.getAssets();
                BitmapFactory.decodeStream(contextAssets.open("ThemeBgLists/" + context.getAssets().list("ThemeBgLists")[pos]), new Rect(0, 0, 0, 0), options);
                options.inSampleSize = Constants.calculateInSize(options, Constants.widths, (int) context.getResources().getDimension(R.dimen.keyboard_height));
                options.inJustDecodeBounds = false;
                Bitmap.createScaledBitmap(BitmapFactory.decodeStream(manager.open("ThemeBgLists/" + context.getAssets().list("ThemeBgLists")[pos]), new Rect(0, 0, 0, 0), options), Constants.widths, (int) context.getResources().getDimension(R.dimen.keyboard_height), false).compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            } catch (IOException unused) {
                Toast.makeText(context, "Exception", Toast.LENGTH_LONG).show();
            }
        }
        return file;
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] projections = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, projections, null, null, null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getPathFromUriLowVersion(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
        query.moveToFirst();
        String string = query.getString(columnIndexOrThrow);
        query.close();
        return string;
    }

    public static void copyFile(File file2, File file3) {
        InputStream inputStream;
        OutputStream outputStream;
        try {
            inputStream = new FileInputStream(file2);
            outputStream = new FileOutputStream(file3);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public static ArrayList<String> getSuggestionWords(String string) {
        ArrayList<String> SuggestionData;
        ArrayList<String> SuggestionWords = new ArrayList<>();

        SuggestionWords.addAll(Constants.SuggestionWordsList);
        SuggestionData = new ArrayList<>();
        if (string.length() >= 1) {
            if (SuggestionData != null) {
                for (int i = 0; i < SuggestionWords.size(); i++) {
                    String item = SuggestionWords.get(i).toLowerCase();
                    String lowerR6 = string.toLowerCase();
                    if (item.contains(lowerR6)) {
                        SuggestionData.add(SuggestionWords.get(i));
                    }
                    if (!WordExistOrNot) {
                        break;
                    }
                }
            }
        }
        if (SuggestionData.size() == 0) {
            for (int i = 0; i < SuggestionWords.size(); i++) {
                String word = SuggestionWords.get(i).toLowerCase();
                String lowerR6 = string.toLowerCase();
                if (word.startsWith(lowerR6)) {
                    SuggestionData.add(SuggestionWords.get(i));
                }
                if (!WordExistOrNot) {
                    break;
                }
            }
        }

        WordExistOrNot = SuggestionData.size() > 0;
        return SuggestionData;
    }

    public static HintWordListAdapter setSuggestionWordsAdapter(Context context, ArrayList<String> stringArrayList, int SelectedTheme) {
        return new HintWordListAdapter(context, stringArrayList, SelectedTheme);
    }
}
