package com.zeefive.vmcapp.activity;

import android.os.Bundle;

import com.zeefive.vmcapp.R;

public class AboutActivity extends ActivityBase {
    public static final String TITLE = "About";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setUpActionBar(TITLE, true);
    }
}