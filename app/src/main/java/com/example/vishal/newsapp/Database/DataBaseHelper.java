package com.example.vishal.newsapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by VISHAL on 7/20/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vishaljansarinewsitems.db";
    private static final String TAG = "dbhelper";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryString = "CREATE TABLE " + Contract.TABLE_NEWSFEED.TABLE_NAME + " (" +
                             Contract.TABLE_NEWSFEED._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                             Contract.TABLE_NEWSFEED.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                             Contract.TABLE_NEWSFEED.COLUMN_TITLE + " TEXT NOT NULL, " +
                             Contract.TABLE_NEWSFEED.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                             Contract.TABLE_NEWSFEED.COLUMN_URL + " TEXT NOT NULL, " +
                             Contract.TABLE_NEWSFEED.COLUMN_URL_TO_IMAGE + " TEXT NOT NULL, " +
                             Contract.TABLE_NEWSFEED.COLUMN_PUBLISHED_AT + " DATE );";


        Log.d(TAG, "Create table SQL: " + queryString);
   //     db.execSQL(queryString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // db.execSQL("drop table " + Contract.TABLE_TODO.TABLE_NAME + " if exists;");

    }
}
