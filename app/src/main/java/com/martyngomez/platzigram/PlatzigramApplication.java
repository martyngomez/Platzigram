package com.martyngomez.platzigram;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by MTN on 12/01/2018.
 */

public class PlatzigramApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

    }
}
