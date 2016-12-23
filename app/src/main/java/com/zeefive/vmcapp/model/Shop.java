package com.zeefive.vmcapp.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Shop implements Serializable {
    public static final String ITEM = "item";
    private String key;
    private String title;

    public Shop() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Shop(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey(){
        return key;
    }
    public String getTitle() {
        return title;
    }
}