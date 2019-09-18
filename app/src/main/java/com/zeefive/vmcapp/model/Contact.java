package com.zeefive.vmcapp.model;

import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Contact implements Serializable {
    @Exclude
    public static final String ITEM = "item";
    private String key;
    private String title;
    private String designation;
    private String phone;
    private String photoUrl;
    private Object createdAt;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Contact(String key, String title, String designation, String phone, String photoUrl, Object createdAt) {
        this.key = key;
        this.title = title;
        this.designation = designation;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getDesignation() {
        return designation;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}