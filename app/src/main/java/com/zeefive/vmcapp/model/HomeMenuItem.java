package com.zeefive.vmcapp.model;

public class HomeMenuItem {
    private String title;
    private String description;
    private int icon;

    public HomeMenuItem(String title, String description, int icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getIcon() {
        return icon;
    }
}
