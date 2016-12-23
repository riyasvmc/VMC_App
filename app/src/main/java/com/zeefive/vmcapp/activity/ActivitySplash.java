package com.zeefive.vmcapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zeefive.vmcapp.R;

public class ActivitySplash extends ActivityBase {

    public static final int SLEEP_TIME = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        Thread thread = new Thread(){
            public void run(){
                try{
                    Thread.sleep(SLEEP_TIME);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                }
            }
        };
        thread.start();
    }
}

