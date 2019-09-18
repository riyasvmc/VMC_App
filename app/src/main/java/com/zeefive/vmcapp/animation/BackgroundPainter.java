package com.zeefive.vmcapp.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

public class BackgroundPainter {

    private static final int MIN = 2000;
    private static final int MAX = 8000;

    private final Random random;

    public BackgroundPainter() {
        random = new Random();
    }

    public void animate(@NonNull final View target, @ColorInt final int color1,
                        @ColorInt final int color2) {

        final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), color1, color2);
        // ObjectAnimator anim = ObjectAnimator.ofFloat(tv,"scaleX",0.5f);

        valueAnimator.setDuration(randInt(MIN, MAX));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                target.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                //reverse animation
                animate(target, color2, color1);
            }
        });

        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }

    private int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}