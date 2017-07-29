package com.example.vishal.newsapp.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vishal.newsapp.MainActivity;

import java.util.ArrayList;

/**
 * Created by VISHAL on 7/28/2017.
 */

public class DatabaseUtils {

    // CURSOR METHOD TO READ DATA FROM DATABASE

    public static Cursor getAllNewsCursor(SQLiteDatabase db) {
        Cursor cursor = db.query(Contract.TABLE_NEWSFEED.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.TABLE_NEWSFEED.COLUMN_PUBLISHED_AT + " DESC");
        return cursor;
    }

    // IMPLEMENTED METHOD TO UPDATE DATA FROM API
    public static void updateNewsDatabase(SQLiteDatabase database, ArrayList<NewsItem> newsArrayList)
    {
        try {
            database.beginTransaction();
            for (NewsItem item : newsArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Contract.TABLE_NEWSFEED.COLUMN_AUTHOR, item.getAuthor());
                contentValues.put(Contract.TABLE_NEWSFEED.COLUMN_TITLE, item.getTitle());
                contentValues.put(Contract.TABLE_NEWSFEED.COLUMN_DESCRIPTION, item.getDescription());
                contentValues.put(Contract.TABLE_NEWSFEED.COLUMN_URL, item.getUrl());
                contentValues.put(Contract.TABLE_NEWSFEED.COLUMN_URL_TO_IMAGE, item.getUrlToImage());
                contentValues.put(Contract.TABLE_NEWSFEED.COLUMN_PUBLISHED_AT, item.getPublishedAt());

                database.insert(Contract.TABLE_NEWSFEED.TABLE_NAME, null, contentValues);
            }
            database.setTransactionSuccessful();

        } finally {
            database.endTransaction();
            database.close();
        }
    }

    public static void deleteDatabase(SQLiteDatabase db) {
        db.delete(Contract.TABLE_NEWSFEED.TABLE_NAME, null, null);
    }
}


