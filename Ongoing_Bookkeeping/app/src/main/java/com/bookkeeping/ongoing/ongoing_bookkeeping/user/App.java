package com.bookkeeping.ongoing.ongoing_bookkeeping.user;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Seulki_Lee on 5/4/2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);


        ParseObject.registerSubclass(Child.class);
        ParseObject.registerSubclass(Parent.class);
        ParseObject.registerSubclass(Record.class);
        ParseObject.registerSubclass(Wish.class);

        Parse.initialize(this, "4N2o43lXnHs6ZHuthUdDsBpBTKsu0s44q4WJoDsN", "6Ft92fn24LevaVQCoac2lgZAlNPBOsZKEWEunnCM");


        Log.i("Application", "Initialized");
    }
}
