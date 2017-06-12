package com.example.android.tourguideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hp on 21-May-17.
 */

public class TourGuideAdapter extends ArrayAdapter<TourGuide> {
    public TourGuideAdapter(Context context, ArrayList<TourGuide> tourGuides) {

        super(context , 0 , tourGuides);
    }

    @Override
    public View getView (int pos , View currentview , ViewGroup parent) {
        View listItemView = currentview;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item , parent , false);
        }

        TourGuide currentdetails = getItem(pos);

        ImageView guideImageView = (ImageView) listItemView.findViewById(R.id.images);

        if (currentdetails.hasImage()) {
            guideImageView.setImageResource(currentdetails.getImage_id());
        }
        else {
            guideImageView.setVisibility(View.GONE);
        }

        TextView guidTextview = (TextView) listItemView.findViewById(R.id.names);
        guidTextview.setText(currentdetails.getString_id());

        return listItemView;
    }
}
