package com.zeefive.vmcapp.model;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {
    private String uid;
    private String name;
    private String email;
    private String photo;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String name, String email, String photo) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }
}
// [END blog_user_class]
