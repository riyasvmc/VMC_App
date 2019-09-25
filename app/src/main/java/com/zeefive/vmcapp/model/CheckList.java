package com.zeefive.vmcapp.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class CheckList implements Serializable {
    @Exclude
    public static final String ITEM = "CheckList";
    private String key;
    private String title;

    public CheckList() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public CheckList(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }
}
// [END blog_user_class]
