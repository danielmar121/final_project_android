package com.daniel.final_project.activities;

import android.app.Application;

import com.daniel.final_project.services.MyFireBase;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Init firebase
        MyFireBase.init(this);

        // Init ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }
}
