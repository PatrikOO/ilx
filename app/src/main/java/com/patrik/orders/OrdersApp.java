package com.patrik.orders;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.patrik.orders.di.AppInjector;
import com.patrik.orders.util.ReleaseTree;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


/**
 * Application class with initialization which have to be done on application start
 */
public class OrdersApp extends Application implements HasActivityInjector {


    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);
        Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new ReleaseTree());

        AppInjector.init(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}