package com.example.android.dailynewsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by hp on 29-May-17.
 */

public class LoadNews extends AsyncTaskLoader<List<News>> {
private static final String logs = LoadNews.class.getName();
private String news_url;

public LoadNews(Context context, String str) {
        super(context);
        news_url = str;
        }

@Override
protected void onStartLoading() {
        forceLoad();
        }

@Override
public List<News> loadInBackground() {
        if (news_url == null) {
        return null;
        }
        List<News> recent_news = FetchNews.getNewsData(news_url);
        return recent_news;
        }
        }

