package com.example.vishal.newsapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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
import android.widget.Toast;

import com.example.vishal.newsapp.Database.Contract;
import com.example.vishal.newsapp.Database.DataBaseHelper;
import com.example.vishal.newsapp.Database.DatabaseUtils;
import com.example.vishal.newsapp.FireBaseJobDispatcherService.NewsFirebaseJobDispatcher;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


// IMPLEMENTED LOAD CALLBACKS OF  LOADERMANAGER

public class MainActivity extends AppCompatActivity
                          implements LoaderManager.LoaderCallbacks<Void>,
                                     NewsAdapter.ItemClickListener {

    private static final String TAG = "MainActivity";
    private ProgressBar loading;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NewsAdapter newsAdapter;
    LoaderManager loaderManager;
    private Cursor cursor;
    private SQLiteDatabase database;
    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        loading = (ProgressBar) findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        // TO DISPLAY THE NEWS DATA WHEN APP IS INSTALLED FOR FIRST TIME IN PHONE
        if (isFirst) {
            firstLoad();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        NewsFirebaseJobDispatcher.newsLoader(this);
    }


    private void firstLoad() {
        loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }

    // READ THE DATA FROM DATABASE AND SET THE ADAPTER IN RECYCLERVIEW TO SHOW THE DATA AT FIRST.
    @Override
    protected void onStart() {
        super.onStart();
        database = new DataBaseHelper(MainActivity.this).getReadableDatabase();
        cursor=DatabaseUtils.getAllNewsCursor(database);
        newsAdapter = new NewsAdapter(cursor,this);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
        cursor.close();
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

        if (isclicked == R.id.action_search) {
            loading.setVisibility(View.VISIBLE);
            firstLoad();
            Toast.makeText(MainActivity.this,"Updated from Server",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // IMPLEMENTED ASYNCTASKLOADER TO LOAD THE NEWS DATA

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                loading.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                NewsRefresher.updateDatabaseFromServer(MainActivity.this);
                return null;
            }
        };
    }

    // ON FINISHING, SET THE ADAPTER AND DISPLAY THE DATA FROM DATABASE .
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {

        loading.setVisibility(View.GONE);
        database = new DataBaseHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAllNewsCursor(database);
        newsAdapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }
    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {

        this.cursor.moveToPosition(clickedItemIndex);
        String url = this.cursor.getString(this.cursor.getColumnIndex(Contract.TABLE_NEWSFEED.COLUMN_URL));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
