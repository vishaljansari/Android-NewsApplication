package com.example.vishal.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.vishal.newsapp.Database.*;
import com.example.vishal.newsapp.Database.NewsItem;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by VISHAL on 7/27/2017.
 */
public class NewsRefresher{

    // DELETED OLD DATA FROM DATABASE AND SAVE THE UPDATED DATA TO THE SQLITE DATABASE.
    public static void updateDatabaseFromServer(Context context) {
        URL newsURL = NetworkUtils.makeURL();

        SQLiteDatabase db = new DataBaseHelper(context).getWritableDatabase();

        try {
            DatabaseUtils.deleteDatabase(db);
            String json = NetworkUtils.getResponseFromHttpUrl(newsURL);
            ArrayList<NewsItem> newsItems = NetworkUtils.parseJSON(json);
            DatabaseUtils.updateNewsDatabase(db,newsItems);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        db.close();
        }
    }
