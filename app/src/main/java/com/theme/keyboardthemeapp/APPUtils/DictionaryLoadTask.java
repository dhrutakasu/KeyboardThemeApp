package com.theme.keyboardthemeapp.APPUtils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.UserDictionary;

import com.theme.keyboardthemeapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class DictionaryLoadTask extends AsyncTask<String, String, String> {
    int ID = 0;
    Context mContext;
    private String resp = "load";

    /* access modifiers changed from: protected */
    public void onProgressUpdate(String... strArr) {
    }

    public DictionaryLoadTask(Context context, int i) {
        this.mContext = context;
        this.ID = i;
    }

    /* access modifiers changed from: protected */
    public String doInBackground(String... strArr) {
        String str;
        try {
            this.mContext.getResources().getString(R.string.dict_name2);
            int i = this.ID;
            if (i == 0) {
                AddDictionaryWord(this.mContext.getResources().getString(R.string.dict_name2));
            } else if (i == 1) {
                if (HindiKeypad.checkLanguage) {
                    str = this.mContext.getResources().getString(R.string.dict_name2);
                } else {
                    str = this.mContext.getResources().getString(R.string.dict_name1);
                }
                AddDictionaryWord(str);
                getMobileData();
            }
        } catch (Exception unused) {
        }
        return this.resp;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String str) {
        try {
            HindiUtils.dictionaryisLoad = true;
            HashSet hashSet = new HashSet();
            hashSet.addAll(HindiUtils.SuggestionWords);
            HindiUtils.SuggestionWords.clear();
            HindiUtils.SuggestionWords.addAll(hashSet);
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        HindiUtils.SuggestionWords.clear();
        HindiUtils.SuggestionWords = null;
        HindiUtils.SuggestionWords = new ArrayList<>();
    }

    public void getMobileData() {
        try {
            Cursor query = this.mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (query.getCount() > 0) {
                while (query.moveToNext()) {
                    String string = query.getString(query.getColumnIndex("display_name"));
                    if (string.contains(" ")) {
                        String[] split = string.split(" ");
                        for (int i = 0; i < split.length; i++) {
                            if (!HindiUtils.SuggestionWords.contains(split[i])) {
                                HindiUtils.SuggestionWords.add(split[i]);
                            }
                        }
                    } else if (!HindiUtils.SuggestionWords.contains(string)) {
                        HindiUtils.SuggestionWords.add(string);
                    }
                }
            }
        } catch (Exception unused) {
        }
        try {
            Cursor query2 = this.mContext.getContentResolver().query(UserDictionary.Words.CONTENT_URI, null, null, null, null);
            while (query2.moveToNext()) {
                String string2 = query2.getString(query2.getColumnIndex("word"));
                if (string2.contains(" ")) {
                    String[] split2 = string2.split(" ");
                    for (int i2 = 0; i2 < split2.length; i2++) {
                        if (!HindiUtils.SuggestionWords.contains(split2[i2])) {
                            HindiUtils.SuggestionWords.add(split2[i2]);
                        }
                    }
                } else if (!HindiUtils.SuggestionWords.contains(string2)) {
                    HindiUtils.SuggestionWords.add(string2);
                }
            }
        } catch (Exception unused2) {
        }
    }

    public void AddDictionaryWord(String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.mContext.getAssets().open(str)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    HindiUtils.SuggestionWords.add(readLine);
                } else {
                    bufferedReader.close();
                    return;
                }
            }
        } catch (IOException unused) {
        }
    }
}
