package com.example.android.tourguideapp;

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

        TextView landmarks = (TextView) findViewById(R.id.sightsAndLandmarks);
        landmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent attract_intent = new Intent(MainActivity.this, SightsAndLandmarks.class);
                startActivity(attract_intent);
            }
        });

        TextView museums = (TextView) findViewById(R.id.museums);
        museums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hotel_intent = new Intent(MainActivity.this, Museums.class);
                startActivity(hotel_intent);
            }
        });

        TextView gardens = (TextView) findViewById(R.id.gardens);
        gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restaurent_intent = new Intent(MainActivity.this, Gardens.class);
                startActivity(restaurent_intent);
            }
        });

        TextView malls = (TextView) findViewById(R.id.shoppingmalls);
        malls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uni_intent = new Intent(MainActivity.this, ShoppingMalls.class);
                startActivity(uni_intent);
            }
        });

    }
}
