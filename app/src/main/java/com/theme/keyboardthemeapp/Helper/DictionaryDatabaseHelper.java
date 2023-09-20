package com.theme.keyboardthemeapp.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.theme.keyboardthemeapp.ModelClass.DictionaryModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DictionaryDatabaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DICTIONARY_DATABASE_NAME = "DictionaryDatabase.db";
    private static final int DATABASE_VERSION = 1;


    private static final String ENGLISH_TO_HINDI_TABLE_NAME = "Englishtohindi_words";
    private static final String ENGLISH_TO_HINDI_ENGLISH = "EnglishWord";
    private static final String ENGLISH_TO_HINDI_FAVORITE = "FavouriteWord";
    private static final String ENGLISH_TO_HINDI_ID = "WordId";
    private static final String ENGLISH_TO_HINDI_HINDI = "HindiWord";

    private static final String RECENT_TABLE_NAME = "Recent_words";
    private static final String RECENT_ID = "RecentId";
    private static final String RECENT_UPDATE_TIME = "UpdateTime";
    private static final String RECENT_ROW_ID = "RowId";

    public DictionaryDatabaseHelper(Context context) {
        super(context, DICTIONARY_DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        if (!isDatabaseExists()) {
            try {
                copyAssestsDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SQLiteDatabase getDatabase() {
        return SQLiteDatabase.openDatabase(context.getDatabasePath(DICTIONARY_DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }

    private void copyAssestsDatabase() throws IOException {
        InputStream inputStream = context.getAssets().open(DICTIONARY_DATABASE_NAME);
        File contextDatabasePath = context.getDatabasePath(DICTIONARY_DATABASE_NAME);
        if (!contextDatabasePath.exists()) {
            contextDatabasePath.getParentFile().mkdirs();
            contextDatabasePath.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(contextDatabasePath);
        byte[] bytes = new byte[1024];
        while (inputStream.read(bytes) > 0) {
            outputStream.write(bytes);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    private boolean isDatabaseExists() {
        return context.getDatabasePath(DICTIONARY_DATABASE_NAME).exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<String> getEnglishWordMatching(String toString) {
        SQLiteDatabase sQLiteDatabase = getReadableDatabase();
        String[] strArr = {ENGLISH_TO_HINDI_ENGLISH};
        Cursor query = sQLiteDatabase.query(ENGLISH_TO_HINDI_TABLE_NAME, strArr, ENGLISH_TO_HINDI_ENGLISH + " LIKE ?", new String[]{toString + "%"}, null, null, ENGLISH_TO_HINDI_ENGLISH);
        ArrayList arrayList = new ArrayList();
        int columnIndex = query.getColumnIndex(ENGLISH_TO_HINDI_ENGLISH);
        while (query.moveToNext()) {
            arrayList.add(query.getString(columnIndex));
        }
        return arrayList;
    }

    @SuppressLint("Range")
    public DictionaryModel getWordByEnglish(String str) {
        boolean Fav = false;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor query = sqLiteDatabase.query(ENGLISH_TO_HINDI_TABLE_NAME, new String[]{ENGLISH_TO_HINDI_ID, ENGLISH_TO_HINDI_ENGLISH, ENGLISH_TO_HINDI_HINDI, ENGLISH_TO_HINDI_FAVORITE}, ENGLISH_TO_HINDI_ENGLISH + " = ?", new String[]{str}, null, null, null);
        if (!query.moveToNext()) {
            return null;
        }
        int Id = query.getInt(query.getColumnIndex(ENGLISH_TO_HINDI_ID));
        String EngWord = query.getString(query.getColumnIndex(ENGLISH_TO_HINDI_ENGLISH));
        String HindiWord = query.getString(query.getColumnIndex(ENGLISH_TO_HINDI_HINDI));
        if (query.getInt(query.getColumnIndex(ENGLISH_TO_HINDI_FAVORITE)) == 1) {
            Fav = true;
        }
        return new DictionaryModel(EngWord, Fav, HindiWord, Id);
    }

    public void checkifIdExist(String recentWord) {
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try {
            cursor = sqLiteDatabase.query(RECENT_TABLE_NAME, new String[]{RECENT_ID, RECENT_UPDATE_TIME}, RECENT_ID + " = ?", new String[]{recentWord}, null, null, null);
        } catch (Exception unused) {
            cursor = null;
        }
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            UpdateRecent(toString());
        } else {
            InsertRecent(recentWord);
        }
    }

    public void UpdateRecent(String id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECENT_UPDATE_TIME, getUpdateDateTime());
        sqLiteDatabase.update(RECENT_TABLE_NAME, values, RECENT_ID + " = ?", new String[]{id});
    }

    public void InsertRecent(String id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECENT_ID, id);
        values.put(RECENT_UPDATE_TIME, getUpdateDateTime());
    }

    private String getUpdateDateTime() {
        return new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date());
    }

    @SuppressLint("Range")
    public int getWordLastId() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rawQuery = sqLiteDatabase.rawQuery("SELECT  * FROM " + ENGLISH_TO_HINDI_TABLE_NAME, null);
        if (rawQuery.moveToLast()) {
            return rawQuery.getInt(rawQuery.getColumnIndex(ENGLISH_TO_HINDI_ID));
        }
        return -1;
    }

    public void InsertDictionaryWord(DictionaryModel dictionaryModel) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        boolean isFavorite = dictionaryModel.isFavorite();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENGLISH_TO_HINDI_ENGLISH, dictionaryModel.getEnglishWord());
        contentValues.put(ENGLISH_TO_HINDI_HINDI, dictionaryModel.getHindiWord());
        contentValues.put(ENGLISH_TO_HINDI_FAVORITE, Integer.valueOf(isFavorite ? 1 : 0));
        sqLiteDatabase.insert(ENGLISH_TO_HINDI_TABLE_NAME, null, contentValues);
    }

    public void UpdateDictionaryWord(DictionaryModel dictionaryModel) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        boolean isFavorite = dictionaryModel.isFavorite();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENGLISH_TO_HINDI_ENGLISH, dictionaryModel.getEnglishWord());
        contentValues.put(ENGLISH_TO_HINDI_HINDI, dictionaryModel.getHindiWord());
        contentValues.put(ENGLISH_TO_HINDI_FAVORITE, Integer.valueOf(isFavorite ? 1 : 0));
        sqLiteDatabase.update(ENGLISH_TO_HINDI_TABLE_NAME, contentValues, ENGLISH_TO_HINDI_ID + " = ? ", new String[]{String.valueOf(dictionaryModel.getWId())});
    }

    public void DeleteDictionaryWord(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.delete(ENGLISH_TO_HINDI_TABLE_NAME, ENGLISH_TO_HINDI_ID + " = ? ", new String[]{String.valueOf(id)});
    }

    public void AddFavorite(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENGLISH_TO_HINDI_FAVORITE, 1);
        sqLiteDatabase.update(ENGLISH_TO_HINDI_TABLE_NAME, contentValues, ENGLISH_TO_HINDI_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void RemoveFavorite(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENGLISH_TO_HINDI_FAVORITE, 0);
        sqLiteDatabase.update(ENGLISH_TO_HINDI_TABLE_NAME, contentValues, ENGLISH_TO_HINDI_ID + " = ?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public ArrayList<DictionaryModel> getDictionaryFavorite() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor query = sqLiteDatabase.query(ENGLISH_TO_HINDI_TABLE_NAME, new String[]{ENGLISH_TO_HINDI_ID, ENGLISH_TO_HINDI_ENGLISH, ENGLISH_TO_HINDI_HINDI}, ENGLISH_TO_HINDI_FAVORITE + " = ?", new String[]{String.valueOf(1)}, null, null, ENGLISH_TO_HINDI_ENGLISH, String.valueOf(5000));
        ArrayList arrayList = new ArrayList();
        while (query.moveToNext()) {
            arrayList.add(new DictionaryModel(query.getString(query.getColumnIndex(ENGLISH_TO_HINDI_ENGLISH)), true, query.getString(query.getColumnIndex(ENGLISH_TO_HINDI_HINDI)), query.getInt(query.getColumnIndex(ENGLISH_TO_HINDI_ID))));
        }
        return arrayList;
    }

    public void deleteFavourite(int wId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENGLISH_TO_HINDI_FAVORITE, 0);
        sqLiteDatabase.update(ENGLISH_TO_HINDI_TABLE_NAME, contentValues, ENGLISH_TO_HINDI_ID + " = ?", new String[]{String.valueOf(wId)});
    }

    @SuppressLint("Range")
    public ArrayList<DictionaryModel> getDictionaryRecent() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String str = "SELECT * FROM " + ENGLISH_TO_HINDI_TABLE_NAME + " t1 INNER JOIN " + RECENT_TABLE_NAME + " t2 ON t1." + ENGLISH_TO_HINDI_ID + "=t2." + RECENT_ID + " WHERE t2." + RECENT_UPDATE_TIME + "<=\"" + getUpdateDateTime() + "\" ORDER BY t2." + RECENT_UPDATE_TIME + " DESC LIMIT 25";
        Cursor rawQuery = sqLiteDatabase.rawQuery(str, null);
        ArrayList arrayList = new ArrayList();
        while (rawQuery.moveToNext()) {
            arrayList.add(new DictionaryModel(rawQuery.getString(rawQuery.getColumnIndex(ENGLISH_TO_HINDI_ENGLISH)), true, rawQuery.getString(rawQuery.getColumnIndex(ENGLISH_TO_HINDI_HINDI)), rawQuery.getInt(rawQuery.getColumnIndex(ENGLISH_TO_HINDI_ID))));
        }
        return arrayList;
    }

    public void deleteDictionaryRecent(int i) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.delete(RECENT_TABLE_NAME, RECENT_ID + " = ?", new String[]{String.valueOf(i)});
    }
}
