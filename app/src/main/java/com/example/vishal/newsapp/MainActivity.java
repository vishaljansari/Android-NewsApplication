package com.example.vishal.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = (ProgressBar)findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.news_fetch_data);

        fetchURL();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.headlines,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int isclicked = item.getItemId();
        if(isclicked == R.id.action_search){
            textView.setText("");
            fetchURL();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void fetchURL(){
        NewsHeadlinesFetchTask task = new NewsHeadlinesFetchTask();

        URL url = NetworkUtils.makeURL();
        task.execute(url);
    }


    class NewsHeadlinesFetchTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... urls){

            try{
                return NetworkUtils.getResponseFromHttpUrl(urls[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            textView.setText(value);

            loading.setVisibility(View.GONE);
        }
    }
}

