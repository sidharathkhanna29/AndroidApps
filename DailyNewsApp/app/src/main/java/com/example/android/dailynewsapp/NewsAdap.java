package com.example.android.dailynewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hp on 29-May-17.
 */

public class NewsAdap extends ArrayAdapter<News> {
    public NewsAdap(Context context, List<News> news) {
        super(context, 0, news);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View list_item_view = convertView;
        if (list_item_view == null) {
            list_item_view = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_items, parent, false);
        }
        News curr_news = getItem(position);
        TextView news_title = (TextView) list_item_view.findViewById(R.id.title);
        String newsTitle = curr_news.getTitle();
        news_title.setText(newsTitle + ": ");
        TextView bulletIns = (TextView) list_item_view.findViewById(R.id.options);
        String bullet_in = curr_news.getBulletIns();
        bulletIns.setText(bullet_in);
        return list_item_view;
    }
}

