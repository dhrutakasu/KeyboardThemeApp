package com.theme.keyboardthemeapp.APPUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.UserDictionary;

import com.theme.keyboardthemeapp.Constants;
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

    public void onProgressUpdate(String... strArr) {
    }

    public DictionaryLoadTask(Context context, int i) {
        this.mContext = context;
        this.ID = i;
    }

    public String doInBackground(String... strArr) {
        String str;
        try {
            this.mContext.getResources().getString(R.string.dict_name2);
            int i = this.ID;
            if (i == 0) {
                AddDictionaryWord(this.mContext.getResources().getString(R.string.dict_name2));
            } else if (i == 1) {
                if (CustomKeypad.CheckLanguage) {
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

    public void onPostExecute(String str) {
        try {
            Constants.DictionaryWordLoad = true;
            HashSet hashSet = new HashSet();
            hashSet.addAll(Constants.SuggestionWordsList);
            Constants.SuggestionWordsList.clear();
            Constants.SuggestionWordsList.addAll(hashSet);
        } catch (Exception unused) {
        }
    }

    public void onPreExecute() {
        Constants.SuggestionWordsList.clear();
        Constants.SuggestionWordsList = null;
        Constants.SuggestionWordsList = new ArrayList<>();
    }

    public void getMobileData() {
        try {
            Cursor query = this.mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (query.getCount() > 0) {
                while (query.moveToNext()) {
                    @SuppressLint("Range") String string = query.getString(query.getColumnIndex("display_name"));
                    if (string.contains(" ")) {
                        String[] split = string.split(" ");
                        for (int i = 0; i < split.length; i++) {
                            if (!Constants.SuggestionWordsList.contains(split[i])) {
                                Constants.SuggestionWordsList.add(split[i]);
                            }
                        }
                    } else if (!Constants.SuggestionWordsList.contains(string)) {
                        Constants.SuggestionWordsList.add(string);
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
                        if (!Constants.SuggestionWordsList.contains(split2[i2])) {
                            Constants.SuggestionWordsList.add(split2[i2]);
                        }
                    }
                } else if (!Constants.SuggestionWordsList.contains(string2)) {
                    Constants.SuggestionWordsList.add(string2);
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
                    Constants.SuggestionWordsList.add(readLine);
                } else {
                    bufferedReader.close();
                    return;
                }
            }
        } catch (IOException unused) {
        }
    }
}
