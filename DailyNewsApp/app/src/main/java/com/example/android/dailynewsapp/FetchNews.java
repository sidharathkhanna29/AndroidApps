package com.example.android.dailynewsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 29-May-17.
 */

public class FetchNews {

    private static final String News_Logs = FetchNews.class.getSimpleName();

    private FetchNews() {
    }

    public static List<News> getNewsData(String news_url) {
        URL url = createURL(news_url);
        String json_resp = null;
        try {
            json_resp = HTTPRequest(url);
        } catch (IOException e) {
            Log.e(News_Logs, "Error ocuured while making a HTTP request ", e);
        }
        List<News> recent_news = extractJSONFeature(json_resp);
        return recent_news;
    }

    private static List<News> extractJSONFeature(String json_response) {
        if (TextUtils.isEmpty(json_response)) {
            return null;
        }
        List<News> news = new ArrayList<>();
        try {
            JSONObject bJResponse = new JSONObject(json_response);
            JSONObject currNews = bJResponse.getJSONObject("response");
            JSONArray nArray = currNews.getJSONArray("results");
            for (int i = 0; i < nArray.length(); i++) {
                JSONObject curr_news = nArray.getJSONObject(i);
                String title = curr_news.getString("sectionName");
                String head = curr_news.getString("webTitle");
                String webUrl = curr_news.getString("webUrl");
                News obj = new News(title, head, webUrl);
                news.add(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

    private static String HTTPRequest(URL url) throws IOException {
        String jsResp = "";
        if (url == null) {
            return jsResp;
        }
        HttpURLConnection urlConn = null;
        InputStream is = null;
        try {
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setReadTimeout(1000);
            urlConn.setConnectTimeout(1200);
            urlConn.setRequestMethod("GET");
            urlConn.connect();
            if (urlConn.getResponseCode() == 200) {
                is = urlConn.getInputStream();
                jsResp = readStream(is);
            } else {
                Log.e(News_Logs, "Error in response code" + urlConn.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(News_Logs, "Error while getting news from JSON", e);
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
            if (is != null) {
                is.close();
            }
        }
        return jsResp;
    }

    private static String readStream(InputStream is) throws IOException {
        StringBuilder out = new StringBuilder();
        if (is != null) {
            InputStreamReader ISR = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(ISR);
            String line = br.readLine();
            while (line != null) {
                out.append(line);
                line = br.readLine();
            }
        }
        return out.toString();
    }

    private static URL createURL(String news_URL) {
        URL url = null;
        try {
            url = new URL(news_URL);
        } catch (MalformedURLException e) {
            Log.e(News_Logs, "URL cant be build...Problem", e);
        }
        return url;
    }
}

