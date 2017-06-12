package com.example.android.musicstudioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.android.musicstudioapp.R.id.search_online;

public class NowPlayingPlaylist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_playlist);

        Button mlibrary = (Button) findViewById(R.id.all_songs);
        mlibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mlibrary_intent = new Intent(NowPlayingPlaylist.this, media_library.class);
                startActivity(mlibrary_intent);
            }
        });

        Button search = (Button) findViewById(R.id.search_online);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search_intent = new Intent(NowPlayingPlaylist.this, search_songs.class);
                startActivity(search_intent);
            }
        });
    }
}
