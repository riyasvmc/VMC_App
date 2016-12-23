package com.zeefive.vmcapp.model;

import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Project implements Serializable {
    @Exclude public static final String ITEM = "item";
    private String key;
    private String title;
    private String location;
    private Object createdAt;
    private boolean completed = false;

    public Project() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Project(String key,String title, String location, Object createdAt, boolean completed) {
        this.key = key;
        this.title = title;
        this.location = location;
        this.createdAt = createdAt;
        this.completed = completed;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }

    public String getLocation() {
        return location;
    }

    public boolean isCompleted() {
        return completed;
    }
}