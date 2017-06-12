package com.example.android.booklistingapp.DatahandlerHttp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hp on 28-May-17.
 */

public class DatahandlerHttp {
    static String streaming_data = null;

    public String getHttpData (String string_url) {
        try {
            URL url = new URL(string_url);
            HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();

            if (connection_url.getResponseCode() == 200) {
                InputStream input_stream = new BufferedInputStream(connection_url.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(input_stream));
                StringBuilder stringBuilder = new StringBuilder();
                String str1;

                while ((str1 = br.readLine()) != null) {
                    stringBuilder.append(str1);
                }
                streaming_data = stringBuilder.toString();
                connection_url.disconnect();
            }

        } catch (MalformedURLException exc1) {
            exc1.printStackTrace();
        } catch (IOException exc2) {
            exc2.printStackTrace();
        }
        return streaming_data;
    }
}
