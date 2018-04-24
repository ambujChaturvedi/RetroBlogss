package com.example.user.retroblogs.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 18-02-2018.
 */

public class Post {

    private String UID;
    private String description;
    private String imageUrl;
    private String title;
    private String username;
    private long date;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
    }

    public Post(String UID, String description, String imageUrl, String title, String username, long date) {
        this.UID = UID;
        this.description = description;
        this.imageUrl = imageUrl;
        this.title = title;
        this.username = username;
        this.date = date;

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
