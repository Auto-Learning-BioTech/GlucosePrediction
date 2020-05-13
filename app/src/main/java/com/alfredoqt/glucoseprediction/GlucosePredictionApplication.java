package com.alfredoqt.glucoseprediction;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class GlucosePredictionApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
