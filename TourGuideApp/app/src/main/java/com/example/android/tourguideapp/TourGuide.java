package com.example.android.tourguideapp;

import static android.R.attr.id;

/**
 * Created by hp on 21-May-17.
 */

public class TourGuide {

    private int string_id;
    private static final int NO = -1;
    private int image_id = NO;

    public TourGuide(int id) {
        string_id = id;
    }

    public TourGuide(int id_0, int id_1) {
        string_id = id_0;
        image_id = id_1;
    }

    public int getString_id() {
        return string_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public boolean hasImage() {
        return image_id !=NO;
    }

}
