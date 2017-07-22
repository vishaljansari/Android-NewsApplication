package com.example.vishal.newsapp.Database;

import android.provider.BaseColumns;

/**
 * Created by VISHAL on 7/20/2017.
 */

public class Contract {

    public static class TABLE_NEWSFEED implements BaseColumns{

        public static final String TABLE_NAME = "newsitems";

        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_PUBLISHED_AT = "publishedAt";

    }
}
