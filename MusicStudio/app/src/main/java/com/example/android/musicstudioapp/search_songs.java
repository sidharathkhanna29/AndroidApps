package com.example.android.musicstudioapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class search_songs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_songs);

        Button search = (Button) findViewById(R.id.final_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search_intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wynk.in"));
                startActivity(search_intent1);
            }
        });

        Button lib = (Button) findViewById(R.id.media_library1);
        lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search_intent2 = new Intent(search_songs.this, media_library.class);
                startActivity(search_intent2);
            }
        });


    }
}
