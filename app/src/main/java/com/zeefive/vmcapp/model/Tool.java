package com.zeefive.vmcapp.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Tool implements Serializable {
    private String key;
    private String title;
    private String location;

    public Tool() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Tool(String key, String title, String location) {
        this.key = key;
        this.title = title;
        this.location = location;
    }

    public String getKey(){
        return key;
    }
    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }
}