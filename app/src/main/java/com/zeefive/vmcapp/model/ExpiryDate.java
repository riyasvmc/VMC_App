package com.zeefive.vmcapp.model;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

import hugo.weaving.DebugLog;

// [START blog_user_class]
@IgnoreExtraProperties
public class ExpiryDate implements Serializable {
    @Exclude private static final int NO_DAYS_FOR_WARNING = 30;
    @Exclude public static final String ITEM = "item";
    private String key;
    private String title;
    private int period;
    private Object createdAt;

    public ExpiryDate() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ExpiryDate(String key, String title, int period, Object createdAt) {
        this.key = key;
        this.title = title;
        this.period = period;
        this.createdAt = createdAt;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public String getTitle() {
        return title;
    }

    public int getPeriod(){
        return period;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }

    @Exclude
    public boolean isExpired(){
        // check weather the date passed
        long delta = System.currentTimeMillis() - (long) getCreatedAt();
        if(delta > 0){
            return true;
        }
        return false;
    }

    @DebugLog
    @Exclude
    public boolean isInWarningRange(){
        // check weather the date passed
        long delta = System.currentTimeMillis() - (long) getCreatedAt();
        if(Math.abs(delta /(24*60*60*1000)) < NO_DAYS_FOR_WARNING){
            return true;
        }
        return false;
    }
}