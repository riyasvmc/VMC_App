package com.zeefive.vmcapp.model;

import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Log implements Serializable {
    public static final String ITEM = "item";
    private String key;
    private String title;
    private Object createdAt;

    public Log() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Log(String key, String title, Object createdAt) {
        this.key = key;
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getKey(){
        return key;
    }

    public String getTitle() {
        return title;
    }

    @Exclude
    public void setCreatedAt(long createdAt){
        this.createdAt = createdAt;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}