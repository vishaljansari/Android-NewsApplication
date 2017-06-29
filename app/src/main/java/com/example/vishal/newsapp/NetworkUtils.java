package com.example.vishal.newsapp;

import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by VISHAL on 6/14/2017.
 */


public class NetworkUtils {


    private static final String NEWSAPP_BASE_URL = " https://newsapi.org/v1/articles";
    private static final String SOURCE_PARAM = "source";
    private static final String source_param_value = "the-next-web";
    private static final String SORT_PARAM = "sortBy";
    private static final String APIKEY_PARAM = "apiKey";
    private static final String sortBy = "latest";
    protected static final String API_KEY= "ea3302f96d0f4831bf9909fb87bc4966";

    protected static URL makeURL(){


        Uri uri = Uri.parse(NEWSAPP_BASE_URL).buildUpon()

                .appendQueryParameter(SOURCE_PARAM, source_param_value)
                .appendQueryParameter(SORT_PARAM, sortBy)
                .appendQueryParameter(APIKEY_PARAM,API_KEY)
                .build();

        URL url = null;
        try{
            url = new URL(uri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }


//    protected String parseJSON() {
//
//        return null ;
//    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");
            return (input.hasNext())? input.next():null;

        }finally{
            urlConnection.disconnect();
        }
    }

}
