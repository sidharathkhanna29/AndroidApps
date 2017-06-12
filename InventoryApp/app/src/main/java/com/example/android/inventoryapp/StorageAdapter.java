package com.example.android.inventoryapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ItemsContract;

/**
 * Created by hp on 01-Jun-17.
 */

public class StorageAdapter extends CursorAdapter {

    public StorageAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView titleColumn = (TextView)view.findViewById(R.id.Item_name);
        TextView quantityColumn = (TextView) view.findViewById(R.id.quantity);
        TextView priceColoumn = (TextView) view.findViewById(R.id.price);

        Button button_sell = (Button) view.findViewById(R.id.sell_button);
        String productName = cursor.getString(cursor.getColumnIndexOrThrow(ItemsContract.StoreDataEntry.COLUMN_ITEM_NAME));
        String productQuantity = cursor.getString(cursor.getColumnIndexOrThrow(ItemsContract.StoreDataEntry.COLUMN_ITEM_QUANTITY));
        String productPrice = cursor.getString(cursor.getColumnIndexOrThrow(ItemsContract.StoreDataEntry.COLUMN_ITEM_PRICE));
        final int quantity = Integer.parseInt(productQuantity);
        long id = cursor.getLong(cursor.getColumnIndex(ItemsContract.StoreDataEntry._ID));
        final Uri currentUri = ContentUris.withAppendedId(ItemsContract.StoreDataEntry.CONTENT_URI, id);
        titleColumn.setText(productName);
        priceColoumn.setText("Price: " + productPrice);
        quantityColumn.setText("Quantity: " + productQuantity);
        button_sell .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("quantity", " quantity= " + quantity);
                ContentResolver resolver = view.getContext().getContentResolver();
                ContentValues values = new ContentValues();
                if (quantity > 0) {
                    int q = quantity;
                    values.put(ItemsContract.StoreDataEntry.COLUMN_ITEM_QUANTITY, --q);
                    resolver.update(
                            currentUri,
                            values,
                            null,
                            null
                    );
                    context.getContentResolver().notifyChange(currentUri, null);
                } else {
                    Toast.makeText(context, "Item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

