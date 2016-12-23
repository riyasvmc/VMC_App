package com.zeefive.vmcapp.model;

import android.text.TextUtils;
import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Sale implements Serializable {
    private String key;
    private String title;
    private String customer;
    private int quantity;
    private int amount;
    private boolean done;
    private Object createdAt;
    private boolean paid = false;

    public Sale() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Sale(String key, String title, String customer, int quantity, int amount, boolean done, Object createdAt, boolean paid) {
        this.key = key;
        this.title = title;
        this.customer = customer;
        this.quantity = quantity;
        this.amount = amount;
        this.done = done;
        this.createdAt = createdAt;
        this.paid = paid;
    }

    public String getKey(){
        return key;
    }

    public String getTitle() {
        return title;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public int getAmount() {
        return amount;
    }

    public String getCustomer(){
        return customer;
    }

    public int getQuantity(){
        return quantity;
    }

    public boolean isDone() {
        return done;
    }

    @Exclude
    public String getFormattedAmount(){
        return "\u20B9 " + amount;
    }

    @Exclude
    public String getSubTitle(){
        return getCustomer() + " \u00B7 " + getFormattedAmount();
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }

    public boolean isPaid() {
        return paid;
    }
}