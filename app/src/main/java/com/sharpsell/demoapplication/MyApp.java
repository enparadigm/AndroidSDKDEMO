package com.sharpsell.demoapplication;

import android.app.Application;

import com.enparadigm.sharpsell.sdk.Sharpsell;

public class MyApp extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        Sharpsell.INSTANCE.createSharpsellEngine(this);
    }
}
