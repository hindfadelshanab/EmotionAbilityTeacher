package com.nuwa.robot.r2022.emotionalability.utils;

import android.app.Application;
import android.content.Context;


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

//        LocaleHelper.setLocale(context , preferenceManager.getString(Constants.LANGUAGE) );

        // on below line we are setting realm configuration
        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        // below line is to allow write
                        // data to database on ui thread.
                        .allowWritesOnUiThread(true)
                        // below line is to delete realm
                        // if migration is needed.
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
