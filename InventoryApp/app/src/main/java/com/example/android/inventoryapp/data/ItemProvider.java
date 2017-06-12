package com.example.android.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static com.example.android.inventoryapp.data.ItemsContract.CONTENT_AUTHORITY;
import static com.example.android.inventoryapp.data.ItemsContract.PATH_GOODS;

import static com.example.android.inventoryapp.data.ItemsContract.StoreDataEntry;

/**
 * Created by hp on 01-Jun-17.
 */

public class ItemProvider extends ContentProvider {
    public static final String LOG_TAG = ContentProvider.class.getSimpleName();
    private static final int ITEMS = 100;
    private static final int ITEMS_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_GOODS,ITEMS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_GOODS + "/#", ITEMS_ID);
    }
    private Assistance assistance;

    @Override
    public boolean onCreate() {
        assistance = new Assistance(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = assistance.getReadableDatabase();
        Cursor dbcursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                dbcursor = database.query(StoreDataEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ITEMS_ID:
                selection = StoreDataEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                dbcursor = database.query(StoreDataEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        dbcursor.setNotificationUri(getContext().getContentResolver(), uri);
        return dbcursor;
    }
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return StoreDataEntry.CONTENT_LIST_TYPE;
            case ITEMS_ID:
                return StoreDataEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return insertGoods(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertGoods(Uri uri, ContentValues values) {
        Log.v("ss","ss");

        SQLiteDatabase database = assistance.getWritableDatabase();
        String name = values.getAsString(StoreDataEntry.COLUMN_ITEM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Product requires a name");
        }
        Integer quantity = values.getAsInteger(StoreDataEntry.COLUMN_ITEM_QUANTITY);
        if (quantity != null && quantity <= 0) {
            throw new IllegalArgumentException("Product requires valid quantity");
        }
        Integer price = values.getAsInteger(StoreDataEntry.COLUMN_ITEM_PRICE);
        if (price != null && price <= 0) {
            throw new IllegalArgumentException("Product requires valid price");
        }
        String seller = values.getAsString(StoreDataEntry.COLUMN_ITEM_RETAILER);
        if (seller == null) {
            throw new IllegalArgumentException("Item requires a Seller");
        }
        long id = database.insert(StoreDataEntry.TABLE_NAME, null, values);
        Log.v("ss"," "+id);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted;
        SQLiteDatabase database = assistance.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                rowsDeleted= database.delete(StoreDataEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return rowsDeleted;
                }
                else
                {
                    return rowsDeleted;
                }
            case ITEMS_ID:
                selection = StoreDataEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(StoreDataEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return rowsDeleted;
                }
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case ITEMS_ID:
                selection = StoreDataEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(StoreDataEntry.COLUMN_ITEM_NAME)) {
            String title = values.getAsString(StoreDataEntry.COLUMN_ITEM_NAME);
            if (title == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        if(values.containsKey(StoreDataEntry.COLUMN_ITEM_QUANTITY)) {
            Integer quantity = values.getAsInteger(StoreDataEntry.COLUMN_ITEM_QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Product requires valid quantity");
            }
        }

        if (values.containsKey(StoreDataEntry.COLUMN_ITEM_PRICE)) {
            Integer price = values.getAsInteger(StoreDataEntry.COLUMN_ITEM_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Product requires valid price");
            }
        }

        if (values.containsKey(StoreDataEntry.COLUMN_ITEM_RETAILER)) {
            String manufacturer = values.getAsString(StoreDataEntry.COLUMN_ITEM_RETAILER);
            if (manufacturer == null) {
                throw new IllegalArgumentException("Product requires a Supplier");
            }
        }
        if (values.containsKey(StoreDataEntry.COLUMN_ITEM_IMAGE)) {
            String image = values.getAsString(StoreDataEntry.COLUMN_ITEM_IMAGE);
            if (image == null) {
                throw new IllegalArgumentException("Product requires valid image");
            }
        }
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = assistance.getWritableDatabase();
        int rowsUpdated = database.update(StoreDataEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}

