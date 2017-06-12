package com.example.android.musicstudioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class media_library extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_library);

        Button search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(media_library.this, search_songs.class);
                startActivity(intent1);
            }
        });

        Button playlist_button = (Button) findViewById(R.id.playlistview);
        playlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(media_library.this, NowPlayingPlaylist.class);
                startActivity(intent2);
            }
        });

    }
}
