package com.zeefive.vmcapp.model;

import android.text.format.DateUtils;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.DecimalFormat;

// [START blog_user_class]
@IgnoreExtraProperties
public class Purchase implements Serializable {
    private String key;
    private String title;
    private String projectKey;
    private Project project;
    private String quantity;
    private String rate;
    private int amount;
    private String from;
    private Shop shop;
    private Object createdAt;

    public Purchase() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Purchase(String key, String title, String projectKey, Project project, String quantity, String rate, int amount, String from, Shop shop, Object createdAt) {
        this.key = key;
        this.title = title;
        this.projectKey = projectKey;
        this.project = project;
        this.quantity = quantity;
        this.rate = rate;
        this.amount = amount;
        this.from = from;
        this.shop = shop;
        this.createdAt = createdAt;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public String getTitle() {
        return title;
    }

    public void setCreatedAt(Object createdAt){
        this.createdAt = createdAt;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public Project getProject(){
        return project;
    }

    public String getQuantity(){
        return quantity;
    }

    public String getRate(){
        return rate;
    }

    public int getAmount() {
        return amount;
    }

    public String getFrom() {
        return from;
    }

    public Shop getShop(){
        return shop;
    }

    @Exclude
    public String getSubTitle(){
        return getFormattedCreatedAt() + " \u00B7 " + ((getShop()!=null)? "Ok : " + getShop().getTitle() : getFrom());
    }

    @Exclude
    public String getFormattedAmount(){
        Double amount = Double.parseDouble(String.valueOf(getAmount()));
        DecimalFormat format = new DecimalFormat("#,###");
        String formated_amount = format.format(amount);
        return "\u20B9" + formated_amount + "/-";
    }

    @Exclude
    public  String getFormattedCreatedAt(){
        return DateUtils.getRelativeTimeSpanString((long) getCreatedAt(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}