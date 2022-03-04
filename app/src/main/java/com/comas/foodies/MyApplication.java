package com.comas.foodies;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {


    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(false); // disables Firebase local cache, now we can see the same list on both Phones
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
