package com.zeefive.vmcapp.model;

import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Account implements Serializable {
    @Exclude public static final String ITEM = "item";
    private String key;
    private String title;

    public Account() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Account(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public void setKey(String key) {
        this.key = key;
    }
}