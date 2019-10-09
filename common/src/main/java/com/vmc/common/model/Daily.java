package com.vmc.common.model;

import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Daily implements Serializable {
    private String site;
    private String work;
    private String date;
    private String submitted_by;

    public Daily() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Daily(String date, String site, String submitted_by, String work) {
        this.site = site;
        this.work = work;
        this.date = date;
        this.submitted_by = submitted_by;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}