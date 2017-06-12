package com.example.android.tourguideapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by hp on 21-May-17.
 */

public class SightsAndLandmarks extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_list);

        final ArrayList<TourGuide> tourGuides = new ArrayList<TourGuide>();
        tourGuides.add(new TourGuide(R.string.lake, R.drawable.sukhna_lake));
        tourGuides.add(new TourGuide(R.string.rock, R.drawable.rock_garden));
        tourGuides.add(new TourGuide(R.string.capitol, R.drawable.capitol));
        tourGuides.add(new TourGuide(R.string.ohm, R.drawable.open_hand_monument));

        ListView list1 = (ListView)findViewById(R.id.list_items);
        TourGuideAdapter adapter1 = new TourGuideAdapter(this,tourGuides);
        list1.setAdapter(adapter1);
    }
}
