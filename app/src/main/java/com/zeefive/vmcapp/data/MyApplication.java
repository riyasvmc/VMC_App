package com.zeefive.vmcapp.data;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if(!FirebaseApp.getApps(this).isEmpty())
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static synchronized MyApplication getInstance() {

        if(instance == null){
            instance = new MyApplication();
        }
        return instance;
    }
}
