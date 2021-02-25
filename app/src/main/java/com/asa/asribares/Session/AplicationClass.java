package com.asa.asribares.Session;

import android.app.Application;

import com.mazenrashed.printooth.Printooth;

public class AplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Printooth.INSTANCE.init(this);
    }
}
