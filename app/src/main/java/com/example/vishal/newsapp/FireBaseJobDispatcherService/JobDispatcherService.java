package com.example.vishal.newsapp.FireBaseJobDispatcherService;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.vishal.newsapp.MainActivity;
import com.example.vishal.newsapp.NewsRefresher;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by VISHAL on 7/24/2017.
 */

 public class JobDispatcherService extends JobService {


    AsyncTask mBackgroundTask;


    // CREATED JOB DISPATCHER TO UPDATE THE DATABASE WITH THE LATEST NEWS DATA
    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
       mBackgroundTask= new AsyncTask() {

            @Override
            protected void onPreExecute() {
                Toast.makeText(JobDispatcherService.this,"News Updated.",Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                        NewsRefresher.updateDatabaseFromServer(JobDispatcherService.this);
                return null;
            }

           @Override
           protected void onPostExecute(Object o) {
               jobFinished(job,false);
               super.onPostExecute(o);
           }
       };

            mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}
