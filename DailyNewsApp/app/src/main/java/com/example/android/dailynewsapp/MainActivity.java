package com.example.android.dailynewsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>>,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String news_url = "http://content.guardianapis.com/search";
    private static final int news_loader_Id = 1;
    private NewsAdap newsAdapter;
    private TextView default_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView nListView = (ListView) findViewById(R.id.listView);
        default_text = (TextView) findViewById(R.id.default_text);
        nListView.setEmptyView(default_text);

        newsAdapter = new NewsAdap(this, new ArrayList<News>());
        nListView.setAdapter(newsAdapter);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);
        nListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentNews = newsAdapter.getItem(i);
                Uri news_uri = Uri.parse(currentNews.getURL());
                Intent web_intent = new Intent(Intent.ACTION_VIEW, news_uri);
                startActivity(web_intent);
            }
        });
        ConnectivityManager con_mgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo net_info = con_mgr.getActiveNetworkInfo();
        if (net_info != null && net_info.isConnected()) {
            LoaderManager lManager = getLoaderManager();
            lManager.initLoader(news_loader_Id, null, this);
        } else {
            View progressIcon = findViewById(R.id.loading_bar);
            progressIcon.setVisibility(View.GONE);
            default_text.setText("You seem to be offline");
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.settings_category_key))) {
            newsAdapter.clear();
            default_text.setVisibility(View.GONE);
            View progressIcon = findViewById(R.id.loading_bar);
            progressIcon.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(news_loader_Id, null, this);
        }
        ConnectivityManager con_mgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo net_info = con_mgr.getActiveNetworkInfo();
        if(net_info !=null&&net_info.isConnected())

        {
            LoaderManager lManager = getLoaderManager();
            lManager.initLoader(news_loader_Id, null, this);
        } else

        {
            View progressIcon = findViewById(R.id.loading_bar);
            progressIcon.setVisibility(View.GONE);
            default_text.setText("You seem to be offline");
        }

    }
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(this);
        String category = sPref.getString(
                getString(R.string.settings_category_key), getString(R.string.setCatDefault)
        );
        Uri bUri = Uri.parse(news_url);
        Uri.Builder uBuilder = bUri.buildUpon();
        uBuilder.appendQueryParameter("q", category);
        uBuilder.appendQueryParameter("api-key", "test");
        return new LoadNews(this, uBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        View progressIcon = findViewById(R.id.loading_bar);
        progressIcon.setVisibility(View.GONE);

        default_text.setText(R.string.no_news);
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.m_setting) {
            Intent set_Intent = new Intent(this, ActivityPreferences.class);
            startActivity(set_Intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}