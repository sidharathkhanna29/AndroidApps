package com.example.android.musicstudioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView nowplaying = (TextView) findViewById(R.id.now_playing);
        nowplaying.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent np_intent = new Intent(MainActivity.this, NowPlayingPlaylist.class);
                startActivity(np_intent);
            }
        });

        TextView lib = (TextView) findViewById(R.id.all_songs);
        lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lib_intent = new Intent(MainActivity.this, media_library.class);
                startActivity(lib_intent);
            }
        });

        TextView search = (TextView) findViewById(R.id.search_songs);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search_intent = new Intent(MainActivity.this, search_songs.class);
                startActivity(search_intent);
            }
        });


    }
}
