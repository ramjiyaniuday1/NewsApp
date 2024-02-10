package com.news.app;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
