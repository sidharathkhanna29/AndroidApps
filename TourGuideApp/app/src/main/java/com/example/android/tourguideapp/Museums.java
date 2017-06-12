package com.example.android.tourguideapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by hp on 21-May-17.
 */

public class Museums  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_list);

        final ArrayList<TourGuide> tourGuides = new ArrayList<TourGuide>();
        tourGuides.add(new TourGuide(R.string.museum1, R.drawable.govt_museum_and_art_gallery));
        tourGuides.add(new TourGuide(R.string.lcc, R.drawable.lecorbusier));

        ListView list1 = (ListView)findViewById(R.id.list_items);
        TourGuideAdapter adapter1 = new TourGuideAdapter(this,tourGuides);
        list1.setAdapter(adapter1);
    }
}