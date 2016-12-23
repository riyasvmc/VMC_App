package com.zeefive.vmcapp.model;

import android.text.TextUtils;
import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// [START blog_user_class]
@IgnoreExtraProperties
public class Work implements Serializable {
    private String key;
    private String title;
    private String projectKey;
    private List<String> tags = new ArrayList<>();
    private Object createdAt;

    public Work() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Work(String key, String title, String projectKey, List<String> tags, Object createdAt) {
        this.key = key;
        this.title = title;
        this.projectKey = projectKey;
        this.tags = tags;
        this.createdAt = createdAt;
    }

    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return key;
    }

    public String getProjectKey(){
        return projectKey;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTags(){
        return tags;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt){
        this.createdAt = createdAt;
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }

    @Exclude
    public String getSubTitle(){
        return getFormattedCreatedAt() + " ";
    }
}