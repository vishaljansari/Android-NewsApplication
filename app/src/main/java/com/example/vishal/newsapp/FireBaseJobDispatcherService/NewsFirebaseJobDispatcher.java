package com.example.vishal.newsapp.FireBaseJobDispatcherService;

import android.content.Context;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by VISHAL on 7/23/2017.
 */


// IMPLEMENTED FIREBASE JOB DISPATCHER TO SET THE BACKGROUND TASK FOR LATEST UPDATE
public class NewsFirebaseJobDispatcher {

    public static void newsLoader(Context context)
    {
        Driver driver = new GooglePlayDriver(context);

        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job job = firebaseJobDispatcher
                .newJobBuilder()
                .setService(JobDispatcherService.class)
                .setTag("News-dispatch-job")
                .setRecurring(false)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0,60))
                .setReplaceCurrent(true)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        firebaseJobDispatcher.schedule(job);
    }

    public static void stopNewsLoader(Context context)
    {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);
        firebaseJobDispatcher.cancelAll();
    }
}
