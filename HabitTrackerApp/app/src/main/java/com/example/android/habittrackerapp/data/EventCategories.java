package com.example.android.habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by hp on 31-May-17.
 */

public class EventCategories {
    private EventCategories() {
    }

    public static final class GetEvent implements BaseColumns {
        public final static String TABLE_NAME = "Events";
        public final static String _ID = BaseColumns._ID;
        public final static String EventName = "name";
        public final static String EventDuration = "hours";
        public final static String EventTime = "time";
    }
}

