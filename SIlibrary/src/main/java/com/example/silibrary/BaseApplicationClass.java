package com.example.silibrary;

import android.app.Application;

import androidx.annotation.CallSuper;

import com.example.silibrary.utils.CustomValues;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

public abstract class BaseApplicationClass extends Application {

    @Inject
    CustomValues customValues;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();

        if (customValues.isDebug()) Stetho.initializeWithDefaults(this);
    }
}
