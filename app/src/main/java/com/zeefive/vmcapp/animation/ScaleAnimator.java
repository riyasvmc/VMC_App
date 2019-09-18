package com.zeefive.vmcapp.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

public class ScaleAnimator {

    private static final int MIN = 400;
    private static final int MAX = 400;

    private final Random random;

    public ScaleAnimator() {
        random = new Random();
    }

    public void animate(@NonNull final View target) {

        final ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(target,"scaleX",5.5f);

        valueAnimator.setDuration(randInt(MIN, MAX));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                //reverse animation
                animate(target);
            }
        });

        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }

    private int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}