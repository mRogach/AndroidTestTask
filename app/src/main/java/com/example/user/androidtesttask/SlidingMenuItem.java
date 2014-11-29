package com.example.user.androidtesttask;

/**
 * Created by User on 28.11.2014.
 */
public class SlidingMenuItem {
    private long id;
    private String title;
    private String icon;

    public SlidingMenuItem(long id, String title, String icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
