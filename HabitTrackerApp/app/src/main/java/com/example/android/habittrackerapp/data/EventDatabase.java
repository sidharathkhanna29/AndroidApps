package com.example.android.habittrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittrackerapp.data.EventCategories.GetEvent;
/**
 * Created by hp on 31-May-17.
 */

public class EventDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "trackEvent.db";
    private static final int DB_VERSION = 1;

    public EventDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase sqLDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + GetEvent.TABLE_NAME + " ("
                + GetEvent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GetEvent.EventName + " TEXT NOT NULL, "
                + GetEvent.EventDuration + " TEXT, "
                + GetEvent.EventTime + " TEXT NOT NULL);";
        sqLDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
