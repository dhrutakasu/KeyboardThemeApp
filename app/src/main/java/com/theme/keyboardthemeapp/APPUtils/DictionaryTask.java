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

public class DictionaryTask extends AsyncTask<String, String, String> {
    private int ID = 0;
    private Context context;
    private String loadStr = "load";

    public void onProgressUpdate(String... strArr) {
    }

    public DictionaryTask(Context context, int id) {
        this.context = context;
        ID = id;
    }

    public String doInBackground(String... strArr) {
        String str;
        try {
            context.getResources().getString(R.string.dict_name2);
            int id = ID;
            if (id == 0) {
                GetDictionaryWords(context.getResources().getString(R.string.dict_name2));
            } else if (id == 1) {
                if (CustomKeypad.CheckLanguage) {
                    str = context.getResources().getString(R.string.dict_name2);
                } else {
                    str = context.getResources().getString(R.string.dict_name1);
                }
                GetDictionaryWords(str);
                GetMobileData();
            }
        } catch (Exception e) {
        }
        return loadStr;
    }

    public void onPostExecute(String str) {
        try {
            Constants.DictionaryWordLoad = true;
            HashSet set = new HashSet();
            set.addAll(Constants.SuggestionWordsList);
            Constants.SuggestionWordsList.clear();
            Constants.SuggestionWordsList.addAll(Constants.SuggestionWordsList);
        } catch (Exception unused) {
        }
    }

    public void onPreExecute() {
        Constants.SuggestionWordsList.clear();
        Constants.SuggestionWordsList = null;
        Constants.SuggestionWordsList = new ArrayList<>();
    }

    public void GetMobileData() {
        try {
            Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String string = cursor.getString(cursor.getColumnIndex("display_name"));
                    if (string.contains(" ")) {
                        String[] splitStrings = string.split(" ");
                        for (int i = 0; i < splitStrings.length; i++) {
                            if (!Constants.SuggestionWordsList.contains(splitStrings[i])) {
                                Constants.SuggestionWordsList.add(splitStrings[i]);
                            }
                        }
                    } else if (!Constants.SuggestionWordsList.contains(string)) {
                        Constants.SuggestionWordsList.add(string);
                    }
                }
            }
        } catch (Exception e) {
        }
        try {
            Cursor cursor = context.getContentResolver().query(UserDictionary.Words.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String string2 = cursor.getString(cursor.getColumnIndex("word"));
                if (string2.contains(" ")) {
                    String[] split = string2.split(" ");
                    for (int i = 0; i < split.length; i++) {
                        if (!Constants.SuggestionWordsList.contains(split[i])) {
                            Constants.SuggestionWordsList.add(split[i]);
                        }
                    }
                } else if (!Constants.SuggestionWordsList.contains(string2)) {
                    Constants.SuggestionWordsList.add(string2);
                }
            }
        } catch (Exception exception) {
        }
    }

    public void GetDictionaryWords(String str) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    Constants.SuggestionWordsList.add(line);
                } else {
                    reader.close();
                    return;
                }
            }
        } catch (IOException ioException) {
        }
    }
}
