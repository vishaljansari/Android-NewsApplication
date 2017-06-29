package com.example.vishal.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG ="MainActivity" ;
    public ArrayList<NewsItem> newsList;
    private ProgressBar loading;
    private RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = new ArrayList<NewsItem>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        adapter = new NewsAdapter(newsList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        loading = (ProgressBar)findViewById(R.id.progressBar);

        fetchURL();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.headlines, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int isclicked = item.getItemId();
        if(isclicked == R.id.action_search){
            recyclerView.setAdapter(null);
            fetchURL();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchURL(){

        NewsHeadlinesFetchTask task = new NewsHeadlinesFetchTask();

        URL url = NetworkUtils.makeURL();
        task.execute(url);
    }

    class NewsHeadlinesFetchTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... urls) {
            try {
                String data = NetworkUtils.getResponseFromHttpUrl(urls[0]);

                JSONObject jobj = new JSONObject(data);
                JSONArray jsonArray = jobj.getJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject mainObject = jsonArray.getJSONObject(i);

                    String author = mainObject.getString("author");
                    String description = mainObject.getString("description");
                    String published = mainObject.getString("publishedAt");
                    String title = mainObject.getString("title");
                    String url = mainObject.getString("url");
                    String urlToImage = mainObject.getString("urlToImage");

                    newsList.add(new NewsItem(author, title, description, url, urlToImage, published));

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsList;
        }

            @Override

            protected void onPostExecute(final ArrayList<NewsItem> newsArrayList){

                loading.setVisibility(View.GONE);

                super.onPostExecute(newsArrayList);
                adapter.notifyDataSetChanged();
                if (newsArrayList != null) {

                    NewsAdapter adapter = new NewsAdapter(newsArrayList, new NewsAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int clickedItemIndex) {
                            String url = newsArrayList.get(clickedItemIndex).getUrl();
                            Log.d(TAG, String.format("Url %s", url));
                            openWebPage(url);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        }

        public void openWebPage(String url) {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }


