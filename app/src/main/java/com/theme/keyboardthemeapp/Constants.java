package com.theme.keyboardthemeapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import com.karumi.dexter.PermissionToken;
import com.theme.keyboardthemeapp.KeyboardService.HindiKeypad;
import com.theme.keyboardthemeapp.ModelClass.CategoriesItem;
import com.theme.keyboardthemeapp.ModelClass.StatusItem;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class Constants {
    public static final int OVERLAY_REQUEST_CODE = 101;
    public static final String ARRAY_LIST_QUOTE = "ARRAY_LIST_QUOTE";
    public static final String BUNDLE_LIST = "BUNDLE_LIST";
    public static final String QUOTE_POS = "QUOTE_POS";
    public static final String TITLES = "TITLES";
    public static ArrayList<StatusItem> statusItems = new ArrayList<>();
    public static ArrayList<CategoriesItem> categoriesItems = new ArrayList<>();
    public static boolean enableKeyboard = true;
    public static String[][] nameStyle = null;
    public static String[] Celebration_art = {".\n      ✨。 🌟。    ✨\n 。     ☁  🎄  ☁   。。\n     ✨    🎄🎄。 。   ✨\n✨ ☁  🎄⛄🎄 ☁  ✨\n     。 🎄🍰🎀🎄 。✨\n 。   🎄🔔🎄🐻🎄。。\n   ✨  🎄🎄🎀🎄。\n。。🎄🎄⚾🎄🎄。 ✨\n     🎄🎄🎀🎄🍭🎄\n  🎄🎐🎄🎄🔱🎄🎄\n✨ 🎄🎀🎄🎄💓🎄✨\n   🎄🎄🍀🎄🎄🎀🎄\n🎄💎🎄🎀💛🎄🎄🎄\n                MERRY\n      ✨CHRISTMAS ✨\n   🎁🎁🎁🎁🎁🎁🎁", ".\n       ✨        ✨       ✨\n       ✴️       ✴️       ✴️\n       ✴️       ✴️       ✴️\n🍪🍪🍪🍪🍪🍪🍪🍪\n🍰🍰🍰🍰🍰🍰🍰🍰\n🍪🍪🍪🍪🍪🍪🍪🍪\n🍰🍰🍰🍰🍰🍰🍰🍰\n🍪🍪🍪🍪🍪🍪🍪🍪\n🍫🍫🍫🍫🍫🍫🍫🍫", ".\n           🔥🔥🔥🔥\n        ⭐|WINNER|⭐\n   ⭐🏆🏆💓🏆🏆⭐\n     ⭐🏆🏆 🏆🏆⭐\n       ⭐🏆 💓 🏆⭐\n                🏆 🏆\n                   🏆\n                🏆 🏆\n             🎉 🎉 🎉", ".\n 💗💗💗💗💗💗💗\n   🎉✨*🌟✨*😍🎵\n      Congratulations!!\n ❤❤❤❤❤❤❤\n  😘*✨🎵*✨🌺🎉\n 💛💛💛💛💛💛💛", ".\n   🎂　 🌟　˚　 。   🎶\n              🔥🔥🔥\n      🍴 😘│ │ │🍅\n      ✨ ┎─────┒🎉\n  *  🎵┃🎂🎂🎂┃ ˚💐\n     🍊┎┸────┸┒🎉\n    。┃~ⓗⓐⓟⓟⓨ~┃🎵\n       ┎┸──────┸┒\n    ┃ⓑⓘⓡⓣⓗⓓⓐⓨ┃\n 🍓🍓🍓🍓🍓🍓🍓🍓\n         ┸────────┸", ".\n  §💛❤ For You ❤💛§\n  §🎀Valentine's Day🎀§\n    ✨🌹🌹    🌹🌹✨\n   🌹🎁👫🌹👫🎁🌹\n   🌹💎🎉💏🎉💎🌹\n ✨ 🌹💋 💍 💋🌹 ✨\n       ✨ 🌹💝🌹  ✨\n            ✨  🌹 ✨\n         🍃     🌹\n             🍃 🌹", ".\n🐻🌟。❤。😉。🍀🌻\n 💝。🎐🎁 。🎉🌟🌸\n 🎀✨。＼｜／。🌺 🔔\n 🍻 Happy New Year! 🍊\n🎵 💜。／｜＼。💎 🎈 \n🍀。☀。 🌹。🌙。💓\n💋🌟。 😍。 🎶✨🎁", ".\n  🎅🎅🎅🎅🎅🎅🎅\n  🎅🔔🔔🔔🔔🔔🎅\n  🎅🔔🎁🎁🎁🔔🎅\n  🎅🔔🎁🎄🎁🔔🎅\n  🎅🔔🎁🎄🎁🔔🎅\n  🎅🔔🎁🎁🎁🔔🎅\n  🎅🔔🔔🔔🔔🔔🎅\n  🎅🎅🎅🎅🎅🎅🎅\n               MERRY\n           CHRISTMAS", ".\n     🍀.          🍀.      🍀 \n🍀🍀🌺🍀🍀🌺🍀🍀\n     🍀          🍀.       🍀\n🍀🍀🌺🍀🍀🌺🍀🍀\n      ST. PATRICK'S DAY\n🍀🍀🌺🍀🍀🌺🍀🍀\n     🍀.        🍀.        🍀\n🍀🍀🌺🍀🍀🌺🍀🍀\n     🍀.        🍀.        🍀", ".\n       💜💛       💚💙\n   💙🌸🌸❤🌸🌸❤\n    💛🌸I💗U Mom!💜\n         💚🌸🌸🌸💛\n              💜🌸❤\n                   💙", ".\n           👿            👿\n        🎃🎃🎃🎃🎃\n  🎃🎃🔥🎃🎃🔥🎃\n🎃🎃🎃🎃🎃🎃🎃🎃\n🎃🔥🔻🔻🔻🔻🔥🎃\n🎃🎃🔥🔺🔺🔥🎃🎃\n     🎃🎃🎃🎃🎃🎃", ".\n  🌟🌟🌟🌟🌟🌟🌟\n 🌟         Happy         🌟\n 🌟        father`s        🌟\n 🌟      💗day💗      🌟\n 🌟            👱           🌟\n 🌟       👦👔🙆     🌟\n 🌟       👕👖👗     🌟\n 🌟       💙💜💚     🌟\n 🌟 🌟🌟🌟🌟🌟🌟", ".\n  ✨🌹🌹🌹🌹🌹✨\n  🌹✨✨🎀✨✨🌹\n  🌹🌟🎁🙆🎁🌟🌹\n  🌹🌟🎁💎🎁🌟🌹\n  🌹🌟🎁💓🎁🌟🌹\n  🌹    Mom Happy    🌹\n   ✨🌹🌹🌹🌹🌹✨", ".\n               Ⓗⓐⓟⓟⓨ \n             Ⓢⓟⓡⓘⓝⓖ\n         Ⓕⓔⓢⓣⓘⓥⓐⓛ\n✨🎉✨🎉✨🎉✨🎉✨\n🎉💐🌷💐🌷💐🌷💐🎉\n✨🌷🎁🎂🎁🎂🎁🌷✨\n🎉💐🎂🌟💗🌟🎂💐🎉\n✨🌷🎁💗㊗💗🎁🌷✨\n🎉💐🎂🌟💗🌟🎂💐🎉\n✨🌷🎁🎂🎁🎂🎁🌷✨\n🎉💐🌷💐🌷💐🌷💐🎉\n✨🎉✨🎉✨🎉✨🎉✨", ".\n   ㊗Happy new year 🎉\n✨✨🎉🎉🎉🎉✨✨\n   🎉🌸🌺🌸🌺🌸🎉\n   🌺╗╔╔╗╔╗╔╗╗╔ 🌸\n   🌸╠╣╠╣╠╝╠╝╚╣ 🌺\n   🎉🌺new   year🌸🎉\n✨🎉🌸🌺🌸🌺🎉✨\n✨✨🎉🌺🌸🎉✨✨\n✨✨✨🎉🎉✨✨✨", ".\n🎂🎂🎂🎂🎂🎂🎂🎂\n🌺🍊🍏🍓🍀🌺🍃❤\n🍰🍰🍓🍓🍓🍓🍰🍰\n🍰🍰   H A P P Y   🍰🍰\n🍏🍊🍓🍉🍊🍏🍓🍉\n🎀    B I R T H D A Y   🎀\n🌺🍊🍉🍏🍀🌺🍃🌺\n🎂🎂🎂🎂🎂🎂🎂🎂", ".\n        🎈🎈🎈🎈🎈\n         ✨my dear✨\n       🌹🎩🎩🎩🌹\n       🌹🎩🎩🎩🌹\n       🎩🎩🎩🎩🎩\n       🌹😊😊😊🌹\n       😊♥😊♥😊\n       💗😊😊😊💗\n       😊💗💗💗😊\n       🌹😊😊😊🌹\n       💎💎happy 💎\n         🎶valentine's\n       🎁day🎁🎁🎁\n       🔔🔔🔔🔔🔔", ".\n  *  🎵┃🎂🎂🎂┃ ˚💐😄😊😃☺😉😍😘😳\n  ❤*New*YearBegins❤\n👦👧👩👨👶👵👴👱\n  💗~GODbless'YOU'💗\n👲👳👮👼👸🙆🙇👽\n  💛& GoodLuckToU~💛\n🐶🐹🐰🐯🐻🐷🐮🐵\n  💜Happy NewYear 💜\n😚😁😌😜😝🎅🎃🎶", ".\n   。      ☁   。。   🌙。\n       *        🎄        *    。\n   🚀      💝💓      。\n  。。  🌟👼✨\n*       🌸🐷🎀🌺    ☁\n *    🐚🐔⛄🐭🍚\n            🌵🍃👗       *\n☁    🐳💎📫🚙🐬\n      ・Happy Holiday・ *", ".\n *    🐚🐔⛄🐭🍚          💓  +:\n       🌸🐷🎀🌺    ☁         🍸***🔥***🍸\n          ✨+:.┃:+✨\n          🎀┏┻┓🎀\n          🔔┃    ┃🔔\n          🌹┃    ┃🌹\n               Cheers\n          🌹┃    ┃🌹\n          🎁┃    ┃🎁\n          🎁┗━┛🎁", ".\n😄🌟🌟🎵🎵🌟🌟😍\n🔑🎂❤❤❤❤🎂🔑\n🌹 💛*YOU ARE*💛 🌹\n🌹💗💗~MY~💗💗🌹\n🌹 💚 +LOVELY+💚 🌹\n🌹💜VALENTINE 💜🌹\n🔑🎂❤❤❤❤🎂🔑\n😚🌟🌟🎵🎵🌟🌟😃", ".\n       🌸🐷🎀🌺    ☁                \n             * V👀V。*💗˚\n             ˚(=🔴=) 🌟 。\n     ┏👏━━🎄🎅┓\n      ❆😊HAPPY😄❆\n       🎂 HOLIDAY 🎂", ".\n                   🌟              \n                   🔔\n                 🎅🎅\n              🔔🔔🔔\n           🎅🎅🎅🎅\n                🔔🔔\n             🎅🎅🎅\n          🔔🔔🔔🔔\n       🎅🎅🎅🎅🎅       ", ".\n        🎈     🎈     🎈\n    🎈🎊🎉🎂🎉🎊🎈\n    ✨🎉🎁🎊🎁🎉✨\n       😖🌟⭐✨⭐✨\n        ✨🌟⭐🌟😖\n             😖🌟😖"};
    public static String[] Christmas = {".🎈\n\n            🏃\n🌾🌾🌾🌾🌾🌾"};
    public static String[] Easter_art = {".\n  ☀☀☁☁☀☁☀\n  ☁☁☁👼☁☁☁\n  ☁ Happy Easter! ☁\n  🐔🐣🐣🐣🐣🐣🐔\n  🌸🍃🌸🍃🌸🍃🌸\n  🍃🍃🍃🍃🍃🍃🍃", ".\n                🐣🐣\n             🐣🐣🐣\n           🐣🐣🐣🐣\n             🐣🐣🐣\n                HAPPY\n              EASTER\n      🎀🎀🎀🎀🎀🎀", ".\n❄❄❄🐔🐔❄❄❄\n❄❄🐔🔴🔴🐔❄❄\n❄🐔🔴🔴🔴🔴🐔❄\n❄🐔🔴🔴🔴🔴🐔❄\n🐔🔴🔴🐣🐣🔴🔴🐔\n🐔🔴🐣🐣🐣🐣🔴🐔\n🐔🔴🐣🐣🐣🐣🔴🐔\n🐔🔴🔴🐣🐣🔴🔴🐔\n❄🐔🔴🔴🔴🔴🐔❄\n❄❄🐔🐔🐔🐔❄❄", ".\n     🍳🍳🐥🐥🍳🍳\n     🍳 🐥  🐥  🐥 🍳\n     🍳🐥🐥🐥🐥🍳\n     🍳🐥🐥🐥🐥🍳\n     🍳 🐥  🐥  🐥 🍳\n     🍳🍳🍳🍳🍳🍳\n 🐣HAPPY EASTER🐣", ".\n🌺🌺🌺🌺🌺🌺🌺🌺\n🌺🌸🌸🌸🌸🌸🌸🌺\n🌺🌸🐣   HAPPY 🌸🌺\n🌺🌸 EASTER🐣 🌸🌺\n🌺🌸🌸🌸🌸🌸🌸🌺\n🌺🌺🌺🌺🌺🌺🌺🌺", ".\n           💐👫😻🐀\n      🚗🌹🐝🐻🍦🍧\n  🎎🎅👻🎃🍎🍫📓\n😍 HAPPY   EASTER 🍀\n   ☔️⚡️🐍🐔🌄💃🔑\n     🍳📣🎐🔮🍒🍩\n           😌👯👣💍"};
    public static String[] Food_art = {".\n                🌴🌴\n              🍎🍎🍎\n           🍎🍎🍎🍎\n              🍎🍎🍎", ".\n     🍏🍏\n  🍏 🍎🍎\n 🍏🍎🍎🍎\n 🍏🍎🍎🍎🍎\n   🍏 🍎🍎🍎🍎\n       🍏 🍎🍎🍎🍎\n           🍏🍎 🍎 🍎 🍎\n                🍏🍏🍏🍏🍏", ".\n  🍷🍷🍷🌟🍷🍷🍋\n  🍷🍷🍷🍷🍷🍋🍷\n  🍷🍷🍷🍷🍋🍷🍷\n  🍊🍊🍊🍊🍊🍊🍊\n  🍷🍊🍊🍊🍊🍊🍷\n  🍷🍷🍊🍊🍊🌟🍷\n  🍷🍷🍷🍊🍷🍷🍷\n  🍷🌟🍷🍊🍷🍷🍷\n  🍷🍷🍷🍊🍷🍷🍷\n  🍷🍊🍊🍊🍊🍊🍷\n  🍒🍒🍒🍒🍒🍒🍒", ".\n     💨💨\n     💨💨💨💨\n     🍻🍻🍻🍻🍻\n     🍻🍻🍻🍻   🍻\n     🍻🍻🍻🍻      🍻\n     🍻🍻🍻🍻      🍻\n     🍻🍻🍻🍻   🍻\n     🍻🍻🍻🍻🍻", ".\n  🍺🍻🍺🍻🍺🍻🍺\n   ┳╮🍺🎵🍻🎵🍺\n   ┣┻╮*╭╮╭╮┣╮\n   ┃✨┃┣┛┣┛┃\n   ┻━╯╰╯╰╯┻\n 🎵🍻🎵🍺🎵🍻🎵", ".\n      🔴         🍞🔳🍳\n       \\=☕__      \\u0020| /\n                          /  \n      Breakfast in bed?", ".\n                🍔🍔\n         🍔 🍔 🍔 🍔\n      🍔 🍔 🍔 🍔 🍔\n     🍩🍩🍩🍩🍩🍩\n  🍔🍔🍔🍔🍔🍔🍔", ".\n          🍭🍭\n     🍭🍭🍭🍭\n   🍭🍭🍭🍭🍭\n        🍭🍭🍭\n                 🍭\n                     🍭  \n                         🍭\n                             🍭", ".\n                   🍦\n                 🍦🍦\n              🍦🍦🍦\n           🍦🍦🍦🍦\n           🍯🍯🍯🍯\n             🍯 🍯 🍯\n             🍯 🍯 🍯\n              🍯🍯🍯", ".\n     🍒 🍒 🍒 🍒\n        🍒  🍒  🍒   🍒\n        🍒        🍒\n        🍒          🍒\n      🍒 🍒     🍒🍒\n   🍒 🍒 🍒🍒 🍒 🍒\n   🍒 🍒 🍒 🍒🍒 🍒\n       🍒🍒        🍒🍒", ".\n                 🍕🍕🍕\n            🍕🍕🍕🍕\n         🍕 🍕 🍕 🍕\n                🍕 🍕 🍕\n                  🍕🍕🍕\n                       🍕🍕\n                            🍕", ".\n       💭  💭  💭\n       💭  💭  💭\n       ☕️☕️☕️☕️\n     ☕️☕️☕️☕️  ☕️\n    ☕☕️☕️☕️      ☕️\n    ☕️☕️☕️☕️      ☕️\n     ☕️☕️☕️☕️    ☕️\n       ☕️ ☕️ ☕️ ☕️", ".\n   🌿🌿\n   🌿   🌿\n        🌿🌿\n           🍍🍍\n           🍍 🍍 🍍\n           🍍🍍🍍🍍\n                🍍 🍍 🍍\n                     🍍🍍", ".\n🍰🍰🍰🍫🍰🍰🍰🍰\n🍰🍰🍫🍫🍫🍰🍰🍰\n🍰🍫🍫🍫🍫🍫🍰🍰\n🍫🍫🍫🍫🍫🍫🍫🍰\n🍰🍫🍫🍫🍫🍫🍫🍫\n🍰🍰🍫🍫🍫🍫🍫🍰\n🍰🍰🍰🍫🍫🍫🍰🍰\n🍰🍰🍰🍰🍫🍰🍰🍰", ".\n🍋🍋🍋🍋🍋🍋🍋🍋\n🍋🍋🍋🍪🍪🍋🍋🍋\n🍋🍋🍪🍪🍪🍪🍋🍋\n🍋🍪🍪🍪🍪🍪🍪🍋\n🍋🍪🍪🍪🍪🍪🍪🍋\n🍋🍋🍪🍪🍪🍪🍋🍋\n🍋🍋🍋🍪🍪🍋🍋🍋\n🍋🍋🍋🍋🍋🍋🍋🍋", ".\n             🍋 🍋\n         🍋 🍋🍋🍋\n        🍋🍋🍋🍋🍋\n          🍋 🍋 🍋 🍋\n              🍋 🍋 🍋\n                   🍋🍋"};
    public static String[] Fun_art = {".\n  🌏🌏🌏😍🌏🌏🌏\n  🌏🌏😍😍😍🌏🌏\n  🌏😍🌏😍🌏😍🌏\n  😍🌏🌏😍🌏🌏😍\n  😍🌏🌏😍🌏🌏🌏\n  🌏😍🌏😍🌏🌏🌏\n  🌏🌏😍😍😍🌏🌏\n  🌏🌏🌏😍🌏😍🌏\n  🌏🌏🌏😍🌏🌏😍\n  😍🌏🌏😍🌏🌏😍\n  🌏😍🌏😍🌏😍🌏\n  🌏🌏😍😍😍🌏🌏\n  🌏🌏🌏😍🌏🌏🌏", ".\n     🐵🐵⚪⚪🐵🐵\n🐵🐵⚪⚪⚪⚪🐵🐵\n🐵🐵⚪⚪⚪⚪🐵🐵\n🐵🐵⭕️⚪⚪⭕️🐵🐵\n     ⚪⚪⚪⚪⚪⚪\n        ⚪⚪♦⚪⚪\n             ⚪👅⚪", ".\n           🌚🌚🌚🌚\n        🌚🌝🌚🌝🌚\n      🌚 🌚 🌚 🌚 🌚\n        🌚🐁🌞🐁🌚\n           🌚🌚🌚🌚\n              🌚🌚🌚\n     🌚🌚🐁🐁🌚🌚\n  🌚🌚 🐁🐁🐁🌚🌚\n  🌚🌚 🐁🐁🐁🌚🌚\n     🌚🌚🐁🐁🌚🌚\n       🌚🌚🌚🌚🌚\n           🐁🐁🐁🐁", ".\n             👜👜👜\n            👜       👜\n      👛👜 👛 👜👛 \n    👛👛👛👛👛👛\n  👛👛👛👛👛👛👛\n  👛👛👛👛👛👛👛", ".\n  💦💦💦✨💦💦💦\n  💦💦💦☔💦💦💦\n  💦💦☔☔☔💦💦\n  💦☔☔☔☔☔💦\n  ☔☔☔☔☔☔☔\n  💦💦💦🌂💦💦💦\n  💦💦💦🌂💦💦💦\n  💦🌂💦🌂💦💦💦\n  💦💦🌂💦💦💦💦\n  💦💦💦💦💦💦💦", ".\n  ⭕️🍻🍻🍻🍻🍻⭕️\n  🍻⭕️🍻🍻🍻⭕️🍻\n  🍻🍻⭕️🍻⭕️🍻🍻\n  🍻🍻🍻⭕️🍻🍻🍻\n  🍻🍻⭕️🍻⭕️🍻🍻\n  🍻⭕️🍻🍻🍻⭕️🍻\n  ⭕️🍻🍻🍻🍻🍻⭕️", ".\n  ⭕️🚬🚬🚬🚬🚬⭕️\n  🚬⭕️🚬🚬🚬⭕️🚬\n  🚬🚬⭕️🚬⭕🚬🚬\n  🚬🚬🚬⭕️🚬🚬🚬\n  🚬🚬⭕️🚬⭕️🚬🚬\n  🚬⭕️🚬🚬🚬⭕️🚬\n  ⭕️🚬🚬🚬🚬🚬⭕️", ".\n💭💭💭-  🚀 -💭✈️💭\n💭💭💭🚀🚀💭💭💭\n💭💭💭🚀🚀💭💭💭\n💭💭💭🚀🚀💭💭💭\n💭💭🚀🚀🚀🚀💭💭\n💭🚀🚀🚀🚀🚀🚀💭\n🚀🚀🚀🚀🚀🚀🚀🚀\n🚀🚀💭🚀🚀💭🚀🚀\n🚀💭💭🚀🚀💭💭🚀\n💭💭💭🚀🚀💭💭💭\n💭💭🚀🚀🚀🚀💭💭\n💭💭💭💭💭💭💭💭", ".\n   (👈👈MAKE WAY❕)\n      ￣￣￣￣￣￣￣￣￣\n          🍉🍘🍟🍺🍢🍛\n   ┏┯🍦🍚🍲🍰🍅🍧\n┏┛😁┗☕🍙🍔🍞🍡\n┗🔳━━🔳✨┛💨💨", ".\n     🍏🍏🍏🍏🍏🍏\n     🍏🍏🍏🍏 An🍏\n     🍏 Apple 🍏  A 🍏\n     🍏🍏  Day  🍏 🍏\n     🍏🍏   Keeps    🍏\n     🍏The Doctor    🍏\n     🍏🍏 Away!🍏🍏\n     🍏🍏🍏🍏🍏🍏", ".\n    ☀   ☁       ☁   ☁\n    ☁     ☁    🍀🍀  ☁\n        🏢🏢🏢🏢🏢\n        🏢🙇🏢🏢🏢\n        🏢🏢🏢🏢🏢\n        🏢🏢🏢✋🏢\n        🏢👪🏢🏢🏢\n        🏢🏢🏢🏢🏢\n        🏢🏢🏢👫🏢\n        🏢👯🏢🏢🏢\n        🏢🏢🏢🏢🏢\n   🏢🏢🏢💏🏢🏢🏢", ".\n📱📱📱📱📱📱📱📱\n🙅🙅🙅STOP🙅🙅🙅\n  ⚠⚠ PLAYING ⚠⚠\n  👉YOUR IPHONE!!📢\n😡😡😡🙇🙇😡😡😡\n📱📱📱📱📱📱📱📱", ".\n                   😊🍛\n              ☕👔👆\n                   👖\n                 👟👟", ".\n                   🎩     💶\n                   👩     💰 \n              🔫👕👉💰\n                   👖\n                 👢👢\n              💀💀💀\n           💀💀💀💀\n         💀💀💀💀💀\n      💀💀💀💀💀💀\n    💀💀💀💀💀💀💀", ".\n  👯👯👯📢👯👯👯\n  👯👯📢👯📢👯👯\n  👯📢👯👯👯📢👯\n  👯👯📢👯📢👯👯\n  👯👯👯📢👯👯👯\n    TIME🕖TO AWAKE\n    DO NOT FALL 🙅👎\n  A SLEEP AGAIN😱🔫", ".\n  🌳🌲🍀🌺🌳🍀🌲\n  🌲🌻🌳🌲🍀🌸🌳\n  🌳💬  i'm in park!  🌲\n  👩🌳🌻🌳🍀🌲🍀\n  🌳🌺🍀🌳🌲🍀🌳\n  🍀🌳🌳🌲🌻🌳🌲", ".\n                                   🎩\n                              🎩😍\n                         🎩😃👔\n                   🎩😊👔👖\n              🎩😊👔👖👟\n         🎩😏👔👖👟💰\n   🎩😕👔👖👟💰💰\n   😣👕👖👟💰💰💰\n   👕👖👟💰💰💰💰\n   👖👟💰💰💰💰💰\n   👟💰💰💰💰💰💰", ".\n          🍺🍺             👨\n   __🍺🍺🍺__/👈 👔\n   🍺🍞🍺🍺/        💼\n     📼📼📼 /          👖\n  🔘            🔘      👟👟 ", ".\n                  😜💙\n            💻🎽📲\n                  👖\n                👟👟\n  I⃣T⃣ M⃣A⃣N⃣", ".\n   🌙  ☁☁      🌟  👀\n       ✦     🚥           ☁\n   🌟    [￣✡￣]  🌟\n          [   👽/  ]      🚀\n   |￣￣     ▲    ￣￣|  🌟\n     🐒     | |  UFO /\n    ￣￣￣  ⚡￣￣￣ ✦\n      ⚠   ⚡⚡\n   🌟   ⚡⚡⚡💨 😱", ".\n    🇰🇷👩🌟🌟🇬🇧🎅\n    🇷🇺🌟🎶🎩🌟👳\n    🇺🇸🌟👋😄🌟👶\n    👮🇮🇹🌟🌟👸🇪🇸\n❕HELLO❤WORLD❕", ".\n  🕝            ✅✨❌❌\n                  ❌✅💹✨\n       👵      ✅😒📝📝\n  🎍👕👆      👕📝\n  📼📼📼      👖\n  🔲🔲🔲    👟👟", ".\n               Let`s go\n           🎱🎮🎵⛳\n           👾🎸🎳🚴\n           🏄🎷🏂⚽", ".\n⚡    ☁☁   '  ' ☁\n   ☁  '  💦 '     ''   ⚡\n ☁ '   '  ''⚡  ☁☁☁\n''   💦  '    '    ''   💦\n'' ☁  ☔    ' ☂ ☂   ☁\n 🐸☝ 🙆💦   ☔  ☂\n🌂ⓡⓐⓘⓝⓨⓓⓐⓨ💦", ".\n  🚑🚜🚒🚕💭💭💭\n  🚥💭💭💭🚛🚗🚜\n  🚑💭⚠🚜💭💭💭\n  🚕🚗🚙🚛🚚🚐💭\n  🚥💭💭🚑🚒🚓💭", ".\n              ❄  🏄\n        😪 ☕  🏊 😄 💦\n   ⛄😖 🏂  ⛳ 😎 👙\n        😬 🎿  ☀ 🍧\n              ❄   🌴", ".\n        🔫🔫🔫🔫🔫\n        🔫0⃣0⃣7⃣🔫\n        🔫🔫🔫🔫🔫", ".\n  🐱🐱🐱🐱🐱🐱🐱\n  🐱😂😍😃😍😔🐱\n  🐱😂💰💰💰😍🐱\n  🐱😢💰😘💰😃🐱\n  🐱😔💰💰💰😍🐱\n  🐱😍😔😢😂😂🐱\n  🐱🐱🐱🐱🐱🐱🐱", ".\n  💩💩💩💩💩💩💩\n  💩💀🔚✈➕🍺💩\n  💩💩💩💩💩💩💩", ".\n             *    💰 🌙\n                💰💰  \n             💰🔝💰\n           💰🔝🔝💰   *   \n          💰 🔝🔝 💰    \n         💰 💰 💰 💰 🌟\n         💰 🔥 🔥 💰", ".\n     🔆\n  🎌🏠🌲🏆👏🏠🏤\n      🚴     🏇🏇🏇🏇\n  🏁🏠✋🏠⌚🏠🌲", ".\n             📴📴📴\n             📴📵📴\n             📴📴📴", ".\n        📺📺📺📺📺\n        📺👽👽👽📺\n        📺👽👽👽📺\n        📺👽👽👽📺\n        📺📺📺📺📺", ".\n            ✨       🌙\n        ✨          ✨\n              ✨\n        ⛽     🚚    🌵", ".\n          🐟\n               \\ \n                 \\😮\n                  👕\n                👟👟"};
    public static String[] Love_art = {".\n     ⭐⭐⭐⭐⭐⭐\n        ❄❄❄❄❄\n  ❄❄❄❄❄❄❄\n  ❄❄❄💍❄❄❄\n       ❄❄❄❄❄\n             ❄❄❄\n                  ❄", ".\n        😍😍     😍😍\n    😍         😍         😍\n     😍                     😍\n        😍                😍\n           😍         😍\n               😍 😍\n                  😍", ".\n        ❤                ❤\n     ❤ ❤          ❤ ❤\n   ❤❤❤❤❤❤❤\n     ❤❤❤❤❤❤\n        ❤❤❤❤❤\n           ❤❤❤❤\n              ❤❤❤\n                 ❤❤\n                    ❤", ".\n       💖 💖      💖 💖\n💘💏💏💖💖💏💏💘\n💘💏💏💏💏💏💏💘\n💘💏💏💏💏💏💏💘\n      💘💏💏💏💏💘\n           💘💏💏💘\n                 💘💘", ".\n☁️☁️☁️✨☁️✨☁️☁️\n☁️☁️☁️☁💎☁️☁️☁️\n☁️☁️☁️💍💍💍☁️☁️\n☁️☁️💍☁️☁️☁️💍☁️\n☁💍☁️☁️☁️☁️💍☁️\n☁️💍☁️☁️☁️☁️💍☁️\n☁️💍☁️☁️☁️💍☁️☁️\n☁️☁️💍💍💍☁️☁️☁️\n☁️☁️☁️☁️☁️☁️☁️☁️", ".\n☁️☁️☁️✨☁️✨☁️☁️\n☁️☁️☁️☁💎☁️☁️☁️\n☁️☁️☁️💍💍💍☁️☁️\n☁️☁️💍☁️☁️☁️💍☁️\n☁💍☁️☁️☁️☁️💍☁️\n☁️💍☁️☁️☁️☁️💍☁️\n☁️💍☁️☁️☁️💍☁️☁️\n☁️☁️💍💍💍☁️☁️☁️\n☁️☁️☁️☁️☁️☁️☁️☁️.\n  §💛❤ For You ❤💛§\n  §🎀Valentine's Day🎀§\n    ✨🌹🌹    🌹🌹✨\n   🌹🎁👫🌹👫🎁🌹\n   🌹💎🎉💏🎉💎🌹\n ✨ 🌹💋 💍 💋🌹 ✨\n       ✨ 🌹💝🌹  ✨\n            ✨  🌹 ✨\n         🍃     🌹\n             🍃 🌹", ".\n💗💗💗          🍀🍀🍀\n💗👧✌➕📩👋👱🍀\n💗💗💗          🍀🍀🍀  ", ".\n     💋 💋       💋 💋\n💋✨✨💋💋✨✨💋\n💋✨⭐✨✨⭐✨💋\n💋✨⭐⭐⭐⭐✨💋\n   💋✨⭐😍⭐✨💋\n     💋✨⭐⭐✨💋\n            💋 ⭐ 💋\n                   💋", ".\n       💓💓💓💓💓\n       💎💎💎💎💎\n       💎💏💘 💑💎\n       💎💎💎💎💎\n       💓💓💓💓💓", ".\n       🎩      💍    👒\n       👨     🍀    👩\n       👔👍🌹👈💓\n       👖                👗\n     👟👟          👠👠\n  🌻🌻🌻🌻🌻🌻🌻\n  🌸🌸🌸🌸🌸🌸🌸", ".\n  💓🌹🌹💓🌹🌹💓\n  🌹😍😍😍😍😍🌹\n  🌹😍😍😍😍😍🌹\n  💓🌹😍😍😍🌹💓\n  💓💓🌹😍🌹💓💓\n  💓💓💓🌹💓💓💓", ".\n             💗You💗\n             ✨are✨\n                  👸\n            ✌👗🌹\n                  👖\n                👟👟\n  🌟MY PRINCESS!🌟", ".\n  ☀☀☀☀☀☀☀\n  ☀🌹🌹☀🌹🌹☀\n  🌹☀☀🌹☀☀🌹\n  🌹 MISS ☀  YOU 🌹\n  ☀🌹☀☀☀🌹☀\n  ☀☀🌹☀🌹☀☀\n  ☀☀☀🌹☀☀☀", ".\n  🌙🌙🌙🌹🌙🌙🌙\n  🌙🌙🌹🌙🌹🌙🌙\n  🌙🌹🌙🌙🌙🌹🌙\n  🌹 LOVE 🌙  YOU 🌹\n  🌹🌙🌙🌹🌙🌙🌹\n  🌙🌹🌹🌙🌹🌹🌙\n  🌙🌙🌙🌙🌙🌙🌙", ".\n        🌹🌹     🌹🌹\n    🌹         🌹         🌹\n   🌹  ❤️❤️❤️❤️  🌹\n       🌹      💎       🌹\n    。   🌹           🌹  。\n   🎈  🌟🌹 🌹          。\n      。    🎶🌹    🍀  。", ".\n     💐　      ☁      ＋🌟\n🌸  ＼ 😄 / ＼ 😊 /\n  Ⓛⓘⓥⓔ ⓘⓝ ⓛⓞⓥⓔ\n           👖        👗\n         👟👟   👡👡", ".\n  🌹🌹❤️🅰❤🌹🌹\n        🌹🌹💗🌹🌹\n          🌹🎉🎉🌹\n             🌹💝🌹\n                ❤️❤️\n             🌟❤🌟", ".\n                  🐷\n              🐷  🐷\n            🐷 ❤ 🐷\n     ✨PIGGY LOVE!✨\n 🎁happy anniversary!🎁\n ❤I love you so much!❤", ".\n☀☁☀🌞💛🌞☀☁\n       Love is in the air!\n  ☁☁👦❤👩☁☁\n  ☁☁👵❤👴☁☁\n  ☁☁😍❤🐱☁☁\n  ☁☁👲❤👧☁☁\n  🌷🌷🌷🌷🌷🌷🌷", ".\n  😘😘😘😘😘😘😘\n  😘💓💓😘💓💓😘\n  💓😘😘💓😘😘💓\n    💓MISS 😘 YOU💓\n  😘💓😘😘😘💓😘\n  😘😘💓😘💓😘😘\n  😘😘😘💓😘😘😘", ".\n🎁 LOVE LOVE LOVE 🎁\n🎁 😘~💍👈 😍❤ 🎁\n🎁 ONLY FOR * YOU *🎁", ".\n         。✨🎩    \n         ＊🌹😉~🌟\n         、☝👔👍+\n         ℱǾℜ ƳǾǓ❤", ".\n  ❤💛💙💚💓💙❤\n  📢  Where is Love  📡\n  📢😳😳😳😳😳📡\n  📢   😞😞😞😞  🔊\n  📢     😖😖😖     🔊\n  📢        😭😭       📡", ".\n🎶🎶🎶🎶🎶🎶🎶🎶\n🎶🎶🎶🎶🎶🎶🎶🎶\n🎶 🎶 ᎾNᏞY 4 Ꮜ  🎶 🎶\n🎶🎶🎶🎶🎶🎶🎶🎶\n 。*   🎶🎶🎶, ': 、'\n  +  *. 、'🎶🎶。*   ∴\n  ∴ 。  : *,🎶 . *.. 、 。\n🌙🌟✨' ~🎷👄✨🌟", ".\n  ☁☁☁☁☁☁☁\n  ☀☀☔☔☔☀☀\n  ✨✨🎩✨✨✨✨\n  ✨✨😃✨😄✨✨\n  ✨✨🎓✨👘✨✨\n🍀🌻🌺🎾🎾🌹🌷🌸\n🌴🌴🌴🌴🌴🌴🌴🌴", ".\n  💓🔫🔫💓🔫🔫💓\n  🔫🔫🔫🔫🔫🔫🔫\n  🔫🔫🔫🔫🔫🔫🔫\n  💓🔫🔫🔫🔫🔫💓\n  💓💓🔫🔫🔫💓💓\n  💓💓💓🔫💓💓💓", ".\n        🐼💚     💜🐼\n     💜        🐶        💚\n     🐶                    🐶\n       💚                💜\n          🐼          🐼\n              💜   💚\n                  🐶", ".\n                 💍👫\n           💭💍👫💑\n      😔👦💍👫💒👰\n           💭💍👫💑\n                💍👫", ".\n        😝🍭      🍭😝\n     🍭        😝        🍭\n      😝                     😝\n        🍭                🍭\n           😝          😝\n              🍭   🍭\n                  😝", ".\n        👰💍👰💎👰\n        💎💭💓💭💍\n        👰💓👩💓👰\n        💍💭💓💭💍\n        👰💎👰💍👰", ".\n  📝📝📝📝📝📝📝\n  📝💗📩💗📩💗📝\n  📝💗📬📬📬💗📝\n  📝💗📬😍📬💗📝\n  📝💗📬📬📬💗📝\n  📝💗📩💗📩💗📝\n  📝📝📝📝📝📝📝", ".\n  💓🎸🎸💓🎸🎸💓\n  🎸🎸🎸🎸🎸🎸🎸\n  🎸🎸🎸🎸🎸🎸🎸\n  💓🎸🎸🎸🎸🎸💓\n  💓💓🎸🎸🎸💓💓\n  💓💓💓🎸💓💓💓", ".\n        ✨ /■ ■ ■ \\~🎶\n           |   ❤ ❤  |  ✨\n          |       👃   👂\n       ✨ ＿💋＿/\n      Falling in LOVE\n    💘💓💏🎉💑💗\n       I Love You🌹😘", ".\n  💎💎💎💎💎💎💎\n  💎   ┌──────┐    💎 \n  💎   │ ❤ I  ❤│    💎\n  💎   │ ❤ L ❤│    💎\n  💎   │ ❤ u ❤│    💎\n  💎   └──────┘    💎\n  💎💎💎💎💎💎💎", ".\n  🎶😍😍🎶😍😍🎶\n  😍😍😍😍😍😍😍\n  😍😍😍😍😍😍😍\n  🎶😍😍😍😍😍🎶\n  🎶🎶😍😍😍🎶🎶\n  🎶🎶🎶😍🎶🎶🎶"};
    public static String[] Mood_art = {".\n      😡                😡😡\n      😡😡           😡😡\n      😡😡😡      😡😡\n      😡😡😡😡😡😡\n      😡😡      😡😡😡\n      😡😡           😡😡\n      😡😡                 😡\n            😠😠😠😠\n      😠😠😠😠😠😠\n      😠😠           😠😠\n      😠😠           😠😠\n      😠😠           😠😠\n      😠😠           😠😠\n      😠😠😠😠😠😠\n            😠😠😠😠", ".\n          😄          😄\n            😄      😄\n               😄😄\n                  😄\n                  😄\n\n             😄😄😄\n             😄\n             😄😄😄\n             😄\n             😄😄😄\n\n              😄😄😄\n          😄          \n            😄😄😄\n                          😄\n           😄😄😄", ".\n💔          💔      🙈🙈\n💔💔     💔  🙈        🙈\n💔    💔 💔  🙈        🙈\n💔          💔      🙈🙈", ".\n           💬💬💬💬\n        💬🌹🌹🌹 💬\n        💬 I'm sorry! 💬\n          💬💬💬💬\n         😥💦\n         🙏\n         👖\n       👟👟", ".\n     😂😂😂😂😂😂\n     😂👏👏👏👏😂\n     😂👏  LOL  👏😂\n     😂👏🎵🎵👏😂\n     😂👏👏👏👏😂\n     😂😂😂😂😂😂", ".\n  ❤💔💔💔💔💔❤\n  📞     Waiting 4       📞\n  📱  your message   📱\n  📢😞😔😞😔😞📢\n  📱   😭😭😰😰   📱\n  📞     😢😢😢       📞\n  ❤💔💔💔💔💔❤", ".\n😡😡😡🔥🔥👿👿👿\n😡🔥🔥🔥🔥🔥👿🔥\n😡😡😡🔥🔥🔥👿🔥\n😡🔥🔥🔥🔥🔥👿🔥\n😡🔥🔥🔥🔥🔥👿🔥\n😡🔥🔥🔥🔥👿👿👿\n👿👿👿🔥🔥😡😡😡\n👿🔥🔥👿🔥😡🔥🔥\n👿👿👿🔥🔥😡😡😡\n👿👿🔥🔥🔥😡🔥🔥\n👿🔥👿🔥🔥😡🔥🔥\n👿🔥🔥👿🔥😡😡😡", ".\n❌ ..ⒷⓁⓊⒺ ⒹⒶⓎ.. ❌\n☁☁☁☁☁☁☁☁\n☔☔☔☔☔☔☔☔\n😞😔😓😥😣😨😢😞\n👎👎👎👎👎👎👎👎\n💬💦💬💦💬💦💬💦\n💔💔💔💔💔💔💔💔\n💩💀💩💀💩💀💩💀", ".\n😭😥😭😥😭😥😭😥\n  💔💔    FEEL   💔💔 \n     ALONE UNHAPPY  \n😥😭😥😭😥😭😥😭", ".\n        💜💛💗💚💙\n        ♥ CHEER ♥\n        .*🌟╦╦╔╗🌟*.\n        *,🌟║║╠╝🌟,*\n        :*🌟╚╝╩+🌟*:\n        🍀🍀🍀🍀🍀", ".\n😜😃😊 😜😀😛😊😃\n😌😂👑    BE   😊😃😜\n💛😊😄     H    😌🌟😂\n😚😄💦     A    💛😊😄\n😃🌟😛     P     😚😄💦\n😃😍😊     P    😝💁😏  \n 😇🐷🌟    Y     🐷😃🌟\n😜😝🙆🙆😝😃😍😊", ".\n    💬💬💬💬💬💬\n💬     Lunch time!!!      💬\n     💬💬💬💬💬💬\n      😀😃😍😃😀\n  👌👕👕👔👗👕✌\n  🔳🍀🍀🍀🍀🍀🔳\n  🔳🔳🔳🔳🔳🔳🔳", ".\n     ☁☁☁☁☁☁\n     💦💦💦😢💦💦\n     💦💦👋👕💦💦\n     💦💦💦👖💦💦\n     💦💦💦👟👟💦\n     🌳🌳🚂🚌🚌🚌      \n     🎶🎶🎶🎶🎶🎶", ".\n      😝😝😝😝😝\n 😝😊😊😊😊😊😝\n 😝😝😊😊😊😝😝\n 😝😊😊😊😊😊😝👍\n 😝😊😊😝😊😊😝\n      😝😝😝😝😝", ".\n                   💩\n                💩💩\n           💩💩💩💩\n        💩💩🎱💩💩\n   💩💩💩💩💩💩💩\n💩💩💩💩💩💩💩💩", ".\n        🆒😎🆒😎🆒\n        😎🆒😎🆒😎\n        🆒😎🆒😎🆒\n        😎🆒😎🆒😎\n        🆒😎🆒😎🆒", ".\n 🎤    😄   🎶🎶🎶🎶\n           👕\n           |   |\n         👟👟", ".\n                   🎩\n                   😡💨\n              👊👕👊\n                 👟👟", ".\n     🍃💦🍂💦🍁💦\n     💦🍁💦💦💦🍃\n     🍀💦😔💦🍂💦\n     💦🍂👕🍁💦🍀\n     💦💦👖💦💦💦\n     🌾🌾👟👟🌾🌾", ".\n              💢     🕑\n              😕     💈\n         💐👕     💈\n              👖     💈\n            👟👟  💈", ".\n       😊😊        😊😊\n    😊🎯☺   😛🎯😊\n   😊😊☺😛😛😊😊\n    😊📘☺😛📘😊✌\n           😊📘📘😊\n                   ☺", ".\n 👎👎\n 👎👎\n   👎\n    👎\n  🚬🚬🚬🚬🚬🚬🚬\n  💩💩💩💩💩💩💩", ".\n           😱😱😱😱\n           😱✨✨✨\n           😱✨✨✨\n           😱😱😱😱\n           😱✨✨✨\n           😱✨✨✨", ".\n                  🎩\n          👂💰💰👂\n                  👃\n            🚬⭕💨\n           💪 👔✌\n                 /    \n             👟   👟", ".\n🚥⚠⚠⚠⚠⚠⚠🚥\n⚠🚚🚓🚕🚲⛵💢⚠\n⚠🚃🚗🚙🚌🚓🚒⚠\n⚠🚤🚑💢🚚🚲🚙⚠\n⚠🚓🚒🚃🚕💢🚗⚠\n😡   TRAFFIC    JAM   😡\n🚧⚠⚠⚠⚠⚠⚠🚧", ".\n     🕛|🕐🕑🕒🕓🕔\n     💣 |＿    ＿   |  ＿⚡\n     💣 |   |  |＿| |  |＿|⚡\n     💣 |   |  |＿  |  |    ⚡\n     💣❕🏃~  ＿    ⚡\n     💣 |ー|ー|   |＿|   ❕\n     💣 |    |    |   |＿ 😱\n    🕕🕖🕗🕘🕙🕚", ".\n           💬💬💬💬\n         💬facepalm!💬\n          💬 💬💬💬\n        💬\n       😑😑😑😑😑\n       😑😑👋👋😑\n       😑😑👋👋👋\n       😑😑👋👋👋\n       😑😑😑👋👋\n       😑😑😑😑😑", ".\n        😭😭😭😭😭\n        😭❌😭😭❌\n        😭😭😭😭😭\n        😭😭🚧🚧😭\n        🚧😭😭🚧😭\n        😭😭😭😭😭", ".\n           💩💩💩💩\n           💩🐖💨💩\n           💩💩💩💩", ".\n👏😃👏😜👏😊👏😍\n👏😊👏😎👏😃👏😜\n👏😃👏😜👏😊👏😍\n👏😊👏😜👏😃👏😎\n👏😃👏😜👏😊👏😍\n👏😊👏😎👏😃👏😜"};
    public static String[] Nature_art = {".\n                ☘☘\n              ☘🌸☘\n           ☘🌸🌸☘\n        ☘🌸🌸🌸☘\n           ☘🌸🌸☘\n              ☘🌸☘\n                 ☘☘\n                   🌰\n                   🌰\n                   🌰\n        ✳✳✳✳✳", ".\n                   🔶\n       🔶       🔶       🔶\n              🔶🔶🔶\n   🔶🔶🔶🔶🔶🔶🔶\n              🔶🔶🔶\n       🔶       🔶       🔶\n                   🔶", ".\n               😍 😘\n            😘 ❤️ 😍\n               😍 😘\n                   🌵\n                      🌵\n                   🌵\n              🍂🍂🍂", ".\n           😍😍😍😍\n        😍😍😍😍😍\n     😍😍😍😍😍😍\n  😍😍😍😍😍😍😍\n  😍😍😍😍😍😍😍\n     😍😍😍😍😍😍\n          😍😍😍😍\n               🍔🍔\n               🍔🍔\n               🍔🍔\n      🌿🌿🌿🌿🌿\n🍃🌿🌿🌿🌿🌿🍃"};
    public static String[] decorative = {"•?((¯°·._.• abc •._.·°¯))؟•", "¸,ø¤º°`°º¤ø,¸¸,ø¤º° abc °º¤ø,¸¸,ø¤º°`°º¤ø,¸", "°°°·.°·..·°¯°·._.· abc ·._.·°¯°·.·° .·°°°", "ıllıllı abc ıllıllı", "°°°·.°·..·°¯°·._.· abc ·._.·°¯°·.·° .·°°°", "•´¯`•. abc .•´¯`•", "×º°�?˜`�?°º× abc ×º°�?˜`�?°º×", "•]••´º´•» abc «•´º´••[•", "]|I{•------» abc «------•}I|[", "§.•´¨'°÷•..× abc ×,.•´¨'°÷•..§", "•°¯`•• abc ••´¯°•", "(¯`·.¸¸.·´¯`·.¸¸.-> abc <-.¸¸.·´¯`·.¸¸.·´¯)", "*´¯`*.¸¸.*´¯`* abc *´¯`*.¸¸.*´¯`*", "(¯`·.¸¸.-> °º abc º° <-.¸¸.·´¯)", "°·.¸.·°¯°·.¸.·°¯°·.¸.-> abc <-.¸.·°¯°·.¸.·°¯°·.¸.·°", "|!¤*'~``~'*¤!| abc |!¤*'~``~'*¤!|", "._|.<(+_+)>.|_. abc ._|.<(+_+)>.|_.", "•._.••´¯``•.¸¸.•` abc `•.¸¸.•´´¯`••._.•", "¸„.-•~¹°�?ˆ˜¨ abc ¨˜ˆ�?°¹~•-.„¸", "(¯´•._.• abc •._.•´¯)", "••¤(`×[¤ abc ¤]×´)¤••", "•´¯`•» abc «•´¯`•", "`•.,¸¸,.•´¯ abc ¯`•.,¸¸,.•´", "¸,ø¤º°`°º¤ø,¸ abc ¸,ø¤º°`°º¤ø,", ".o0×X×0o. abc .o0×X×0o.", ",-*'^'~*-.,_,.-*~ abc ~*-.,_,.-*~'^'*-,", "`•.¸¸.•´´¯`••._.• abc •._.••`¯´´•.¸¸.•`", "—(••÷[ abc ]÷••)—", "¤¸¸.•´¯`•¸¸.•..>> abc <<..•.¸¸•´¯`•.¸¸¤", "••.•´¯`•.•• abc ••.•´¯`•.••", ".•°¤*(¯`★´¯)*¤° abc °¤*(¯´★`¯)*¤°•.", "๑۞๑,¸¸,ø¤º°`°๑۩ abc ๑۩ ,¸¸,ø¤º°`°๑۞๑", "-漫~*'¨¯¨'*·舞~ abc ~舞*'¨¯¨'*·~漫-", "★·.·´¯`·.·★ abc ★·.·´¯`·.·★", "�? ▂ ▄ ▅ ▆ ▇ █ abc █ ▇ ▆ ▅ ▄ ▂ �?", "▀▄▀▄▀▄ abc ▄▀▄▀▄▀", "(-_-) abc (-_-)", "▌│█║▌║▌║ abc ║▌║▌║█│▌"};

    static {
        String[][] strArr = new String[24][];
        nameStyle = strArr;
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

    public static int getInputText(char c) {
//        for (char c : inputText.toCharArray()) {
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
                throw new IllegalStateException("Unexpected value: " + s.toLowerCase());
        }
//        }
        return InputInt;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isConnected();
    }


    public static boolean IsEnableKeyboard(Context context) {
        return ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).getEnabledInputMethodList().toString().contains(context.getPackageName());
    }

    public static boolean IsActivateKeyboard(Context context) {
        return new ComponentName(context, HindiKeypad.class).equals(ComponentName.unflattenFromString(Settings.Secure.getString(context.getContentResolver(), "default_input_method")));
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
        builder.setTitle((CharSequence) "Need Permissions");
        builder.setMessage((CharSequence) "This app needs permissions to use this feature. You can grant them in app settings.");
        builder.setPositiveButton((CharSequence) "GOTO SETTINGS", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings(activity);
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showPermissionDialog(final Activity activity, final PermissionToken permissionToken) {
        new AlertDialog.Builder(activity
        ).setMessage((int) R.string.MSG_ASK_PERMISSION).setNegativeButton("Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permissionToken.cancelPermissionRequest();
            }
        }).setPositiveButton("Ok", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
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
        intent.setData(Uri.fromParts("package", activity.getPackageName(), (String) null));
        activity.startActivityForResult(intent, 101);
    }

}
