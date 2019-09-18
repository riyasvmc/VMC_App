package com.zeefive.vmcapp.activity;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.View;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.animation.ScaleAnimator;

public class ActivityAbout extends ActivityBase {
    public static final String TITLE = "About";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setUpActionBar(TITLE, true);
        getSupportActionBar().hide();


        // paint animated background
        View view = findViewById(R.id.imageView);
        ScaleAnimator mScaleAnimator = new ScaleAnimator();
        mScaleAnimator.animate(view);
    }
}