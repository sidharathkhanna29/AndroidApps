package com.example.android.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.booklistingapp.DatahandlerHttp.DatahandlerHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {

    ListView list;
    public TextView empty_view;
    private static String String_url;
    private static final String Title_name = "book_title";
    private static final String Author_name = "book_author";

    ArrayList<HashMap<String, String>> list_books = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        assert button != null;
        list = (ListView) findViewById(R.id.list_item);
        empty_view = (TextView) findViewById(R.id.default_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager conv_mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo network_info = conv_mgr.getActiveNetworkInfo();

                if(network_info != null && network_info.isConnected()) {

                    EditText sys_in = (EditText) findViewById(R.id.name_tobe_searched);
                    String input = sys_in.getText().toString();
                    input = input.replace(" ", "+");
                    String API_KEY = "&key=AIzaSyCvvuHSz0nYnU5awMlCij1IhN-sHX1Q1O4";
                    String_url = "https://www.googleapis.com/books/v1/volumes?q=" + input;
                    Log.v("URL:", String_url);
                    new BOOKloader().execute(String_url);
                }
                else
                    empty_view.setText(R.string.net_connection);
                list.setAdapter(null);
            }
        });
    }

    private class BOOKloader extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list.setAdapter(null);
            Toast.makeText(MainActivity.this, "Please Wait for a while... \n Slow loading...", Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(String... strings) {
            String streaming_data = null;
            String string_url1 = strings[0];

            DatahandlerHttp data_handler = new DatahandlerHttp();
            streaming_data = data_handler.getHttpData(String_url);
            return streaming_data;
        }

        protected void onPostExecute(String stream) {
            if(stream != null) {
                try {
                    JSONObject json_reader = new JSONObject(stream);
                    int items = json_reader.getInt("totalItems");


                    if (items == 0) {
                        empty_view.setText("Book not found... please try again");
                        Toast .makeText(MainActivity.this, "No results found.. Check if the name is valid", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        list.setVisibility(View.VISIBLE);
                        empty_view.setText("");
                        JSONArray arr = json_reader.getJSONArray("items");

                        for (int j = 0; j < arr.length(); j++) {
                            JSONObject obj_book = arr.getJSONObject(j);
                            String name, author;

                            JSONObject details_book = obj_book.optJSONObject("volumeInfo");

                            if (details_book.has("authors")) {
                                author = (details_book.getString("authors"));
                                author = author.replace("[", "");
                                author = author.replace("]", "");
                            } else {
                                author = "";
                            }

                            name = details_book.getString("title");
                            Log.v(Title_name, name);
                            Log.v(Author_name, author);

                            HashMap<String, String> hashmap = new HashMap<String, String>();
                            hashmap.put(Title_name, name);
                            hashmap.put(Author_name, author);

                            list_books.add(hashmap);

                            ListAdapter ladapter = new SimpleAdapter(MainActivity.this, list_books, R.layout.list_item, new String[]{Title_name, Author_name}, new int[]{R.id.books_name, R.id.author_name});
                            list.setAdapter(ladapter);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(MainActivity.this, "Description:\n "+ list_books.get(+i),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                } catch (JSONException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }
}
