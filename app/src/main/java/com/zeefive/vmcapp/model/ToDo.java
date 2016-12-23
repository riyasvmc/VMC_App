package com.zeefive.vmcapp.model;

import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class ToDo implements Serializable {
    private String key;
    private String title;
    private Project project;
    private boolean done;
    private int priority;
    private Object createdAt;

    public ToDo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ToDo(String key, String title, Project project, boolean done, int priority, Object createdAt) {
        this.key = key;
        this.title = title;
        this.project = project;
        this.done = done;
        this.priority = priority;
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

    @Exclude
    public String getSubTitle() {
        if(project != null) return project.getTitle();
        else return null;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }

    public Project getProject() {
        if(project == null){
            return new Project();
        }else {
            return project;
        }
    }

    public boolean isDone() {
        return done;
    }

    public int getPriority(){
        return priority;
    }
}