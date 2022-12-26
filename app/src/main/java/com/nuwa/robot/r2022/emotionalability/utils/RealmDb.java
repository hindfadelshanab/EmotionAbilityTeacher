package com.nuwa.robot.r2022.emotionalability.utils;

import android.app.Application;
import android.content.Context;


import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmDb extends Application {
    private Locale locale;
private static Context context ;
    public  PreferenceManager preferenceManager ;
    public static Context getAppContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        context = getBaseContext();
         preferenceManager = new PreferenceManager(context);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        RealmConfiguration config =
                new RealmConfiguration.Builder()

                        .allowWritesOnUiThread(true)

                        .deleteRealmIfMigrationNeeded()
                        // at last we are calling a method to build.
                        .build();
        // on below line we are setting
        // configuration to our realm database.
        Realm.setDefaultConfiguration(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
