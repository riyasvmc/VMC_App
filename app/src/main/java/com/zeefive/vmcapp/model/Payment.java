package com.zeefive.vmcapp.model;

import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

// [START blog_user_class]
@IgnoreExtraProperties
public class Payment implements Serializable {
    public static final String ITEM = "item";
    private String key;
    private Account account;
    private String payableAmount;
    private String paidAmount;
    private Object createdAt;

    public Payment() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Payment(String key, Account account, String payableAmount, String paidAmount, Object createdAt) {
        this.key = key;
        this.account = account;
        this.payableAmount = payableAmount;
        this.paidAmount = paidAmount;
        this.createdAt = createdAt;
    }

    public String getKey() {
        return key;
    }

    public Account getAccount() {
        return account;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}