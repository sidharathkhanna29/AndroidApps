package com.example.android.dailynewsapp;

/**
 * Created by hp on 29-May-17.
 */

public class News {
    private String category;
    private String options;
    private String news_Url;

    public News(String title, String head, String url) {
        category = title;
        options = head;
        news_Url = url;
    }

    public String getTitle() {
        return category;
    }

    public String getBulletIns() {
        return options;
    }

    public String getURL() {
        return news_Url;
    }
}
