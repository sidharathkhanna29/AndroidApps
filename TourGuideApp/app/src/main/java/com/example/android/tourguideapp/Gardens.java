package com.example.android.tourguideapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by hp on 21-May-17.
 */

public class Gardens  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_list);

        final ArrayList<TourGuide> tourGuides = new ArrayList<TourGuide>();
        tourGuides.add(new TourGuide(R.string.garden1, R.drawable.rose_garden));
        tourGuides.add(new TourGuide(R.string.garden2, R.drawable.shanti_kunj));
        tourGuides.add(new TourGuide(R.string.garden3, R.drawable.terraced_garden));
        tourGuides.add(new TourGuide(R.string.garden4, R.drawable.pinjore_garden));

        ListView list1 = (ListView)findViewById(R.id.list_items);
        TourGuideAdapter adapter1 = new TourGuideAdapter(this,tourGuides);
        list1.setAdapter(adapter1);
    }
}
