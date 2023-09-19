package com.theme.keyboardthemeapp.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.theme.keyboardthemeapp.ModelClass.TranslatorModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ThemeDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TRANSLATOR_TABLE_NAME = "Translator";
    private static final String TRANSLATOR_ID = "TId";
    private static final String TRANSLATOR_INPUT = "TInput";
    private static final String TRANSLATOR_OUTPUT = "TOutput";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TRANSLATOR_TABLE_NAME + " (" +
                TRANSLATOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TRANSLATOR_INPUT + " TEXT," +
                TRANSLATOR_OUTPUT + " TEXT)";
        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TRANSLATOR_TABLE_NAME;
        db.execSQL(dropTableQuery);
    }

    //todo translator insert
    public int InsertWidget(TranslatorModel translatorModel) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TRANSLATOR_INPUT, translatorModel.getInputStr());
        values.put(TRANSLATOR_OUTPUT, translatorModel.getOutputStr());
        return (int) db.insert(TRANSLATOR_TABLE_NAME, null, values);
    }

    //todo get  translator data
    @SuppressLint("Range")
    public ArrayList<TranslatorModel> getTranslatorsHistory() {
        ArrayList<TranslatorModel> translatorModelArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String table_name = "SELECT *FROM " + TRANSLATOR_TABLE_NAME;
        Cursor cursor = db.rawQuery(table_name, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") TranslatorModel translatorModel = new TranslatorModel(cursor.getInt(cursor.getColumnIndex(TRANSLATOR_ID))
                            , cursor.getString(cursor.getColumnIndex(TRANSLATOR_INPUT))
                            , cursor.getString(cursor.getColumnIndex(TRANSLATOR_OUTPUT)));

                    translatorModelArrayList.add(translatorModel);
                } while (cursor.moveToNext());
            }
        }
        return translatorModelArrayList;
    }

    //todo get Specific translator data
    @SuppressLint("Range")
    public TranslatorModel getTranslatorsHistoryId(int Id) {
        TranslatorModel translatorModelArrayList = null;
        SQLiteDatabase db = getReadableDatabase();
        String table_name = "SELECT *FROM " + TRANSLATOR_TABLE_NAME + " where " + TRANSLATOR_ID + "=?";
        ;
        Cursor cursor = db.rawQuery(table_name, new String[]{String.valueOf(Id)});
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") TranslatorModel translatorModel = new TranslatorModel(cursor.getInt(cursor.getColumnIndex(TRANSLATOR_ID))
                            , cursor.getString(cursor.getColumnIndex(TRANSLATOR_INPUT))
                            , cursor.getString(cursor.getColumnIndex(TRANSLATOR_OUTPUT)));

                    translatorModelArrayList = translatorModel;
                } while (cursor.moveToNext());
            }
        }
        return translatorModelArrayList;
    }

    //todo delete translator data
    public void getDeleteTranslators(int index) {
        SQLiteDatabase database = getReadableDatabase();
        database.delete(TRANSLATOR_TABLE_NAME, TRANSLATOR_ID + "=? ", new String[]{String.valueOf(index)});
    }
}
