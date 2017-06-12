package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.inventoryapp.data.ItemsContract.StoreDataEntry;

/**
 * Created by hp on 01-Jun-17.
 */

public class Assistance extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;
    public Assistance(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_STORE_TABLE =  "CREATE TABLE " + StoreDataEntry.TABLE_NAME + " ("
                + StoreDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StoreDataEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + StoreDataEntry.COLUMN_ITEM_QUANTITY + "  INTEGER NOT NULL DEFAULT 0, "
                + StoreDataEntry.COLUMN_ITEM_PRICE + " INTEGER NOT NULL, "
                + StoreDataEntry.COLUMN_ITEM_RETAILER + " TEXT NOT NULL ,"
                + StoreDataEntry.COLUMN_ITEM_IMAGE + " TEXT NOT NULL );";
        db.execSQL(SQL_CREATE_STORE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
