package com.zeefive.vmcapp.model;

public class SpinnerItem {
    private String key;
    private String title;

    public SpinnerItem(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public String getTitle(){
        return title;
    }
}
