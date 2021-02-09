package com.daniel.final_project.activities;

import android.app.Application;

import com.daniel.final_project.services.MyFireBase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MyFireBase.init(this);
    }
}
