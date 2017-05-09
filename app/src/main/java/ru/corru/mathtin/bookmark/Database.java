package ru.corru.mathtin.bookmark;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ru.corru.mathtin.bookmark.HistoryContract.*;
import ru.corru.mathtin.bookmark.FavoriteContract.*;

import java.util.Hashtable;
import java.util.Map;

/**
 *  Author: Daniil [Mathtin] Shigapov
 *  Copyright (c) 2017 Mathtin <wdaniil@mail.ru>
 *  This file is released under the MIT license.
 */

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Ma.Translator";

    private static final String SQL_CREATE_HISTORY_ENTRIES =
            "CREATE TABLE " + HistoryEntry.TABLE_NAME + "( " +
                    HistoryEntry._ID + " INTEGER PRIMARY KEY," +
                    HistoryEntry.COLUMN_NAME_TITLE + " TEXT," +
                    HistoryEntry.COLUMN_NAME_SUBTITLE + " TEXT);";
    private static final String SQL_CREATE_FAVORITE_ENTRIES =
            "CREATE TABLE " + FavoriteEntry.TABLE_NAME + "( " +
                    FavoriteEntry._ID + " INTEGER PRIMARY KEY," +
                    FavoriteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FavoriteEntry.COLUMN_NAME_SUBTITLE + " TEXT);";
    private static final String SQL_DELETE_HISTORY_ENTRIES =
            "DROP TABLE IF EXISTS " + HistoryEntry.TABLE_NAME + "; ";
    private static final String SQL_DELETE_FAVORITE_ENTRIES =
            "DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME + "; ";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HISTORY_ENTRIES);
        db.execSQL(SQL_CREATE_FAVORITE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CREATE_HISTORY_ENTRIES);
        db.execSQL(SQL_DELETE_FAVORITE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long HistoryInsert(String text, String translate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistoryEntry.COLUMN_NAME_TITLE, text);
        values.put(HistoryEntry.COLUMN_NAME_SUBTITLE, translate);
        String selection = HistoryEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { text };
        db.delete(HistoryEntry.TABLE_NAME, selection, selectionArgs);
        return db.insert(HistoryEntry.TABLE_NAME, null, values);
    }

    public long FavoriteInsert(String text, String translate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry.COLUMN_NAME_TITLE, text);
        values.put(FavoriteEntry.COLUMN_NAME_SUBTITLE, translate);
        String selection = FavoriteEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { text };
        db.delete(FavoriteEntry.TABLE_NAME, selection, selectionArgs);
        return db.insert(FavoriteEntry.TABLE_NAME, null, values);
    }

    public void HistoryDelete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = HistoryEntry._ID + " = " + id;
        db.delete(HistoryEntry.TABLE_NAME, selection, null);
    }

    public void FavoriteDelete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = FavoriteEntry._ID + " = " + id;
        db.delete(FavoriteEntry.TABLE_NAME, selection, null);
    }

    public void HistoryDelete(String entry) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = HistoryEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { entry.split("\n")[0] };
        db.delete(HistoryEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void FavoriteDelete(String entry) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = FavoriteEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { entry.split("\n")[0] };
        db.delete(FavoriteEntry.TABLE_NAME, selection, selectionArgs);
    }

    public Map<Long, Entry> ReadHistory() {
        Map<Long, Entry> res = new Hashtable<Long, Entry>();
        SQLiteDatabase db = getWritableDatabase();
        String[] projection = { HistoryEntry._ID,
                HistoryEntry.COLUMN_NAME_TITLE,
                HistoryEntry.COLUMN_NAME_SUBTITLE };
        Cursor cursor = db.query(HistoryEntry.TABLE_NAME, projection, null, null, null, null, null);
        while(cursor.moveToNext()) {
            Long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(HistoryEntry._ID));
            res.put(itemId, new Entry(itemId,
                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryEntry.COLUMN_NAME_SUBTITLE))));
        }
        return res;
    }

    public Map<Long, Entry> ReadFavorite() {
        Map<Long, Entry> res = new Hashtable<Long, Entry>();
        SQLiteDatabase db = getWritableDatabase();
        String[] projection = { FavoriteEntry._ID,
                FavoriteEntry.COLUMN_NAME_TITLE,
                FavoriteEntry.COLUMN_NAME_SUBTITLE };
        Cursor cursor = db.query(FavoriteEntry.TABLE_NAME, projection, null, null, null, null, null);
        while(cursor.moveToNext()) {
            Long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FavoriteEntry._ID));
            res.put(itemId, new Entry(itemId,
                    cursor.getString(cursor.getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FavoriteEntry.COLUMN_NAME_SUBTITLE))));
        }
        return res;
    }
}
