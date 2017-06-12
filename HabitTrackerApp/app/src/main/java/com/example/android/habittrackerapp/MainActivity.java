package com.example.android.habittrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittrackerapp.data.EventDatabase;
import com.example.android.habittrackerapp.data.EventCategories.GetEvent;

public class MainActivity extends AppCompatActivity {
    private EventDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fButton = (FloatingActionButton) findViewById(R.id.add_activity);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        database = new EventDatabase(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor cursor = displayDataBaseInfo();
        show(cursor);
    }

    private void show(Cursor cursor) {
        TextView myView = (TextView) findViewById(R.id.data_text_view);
        try {
            myView.setText("\nThe Event Table has " + cursor.getCount() + " Events\n");
            myView.append("ID" + " :: " +
                    GetEvent.EventName + " :: " +
                    GetEvent.EventDuration + " :: " +
                    GetEvent.EventTime + "\n");
            int id_index = cursor.getColumnIndex(GetEvent._ID);
            int name_index = cursor.getColumnIndex(GetEvent.EventName);
            int hour_index = cursor.getColumnIndex(GetEvent.EventDuration);
            int time_index = cursor.getColumnIndex(GetEvent.EventTime);
            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(id_index);
                String currentName = cursor.getString(name_index);
                String currentHour = cursor.getString(hour_index);
                String currentTime = cursor.getString(time_index);

                myView.append(("\n" + currentId + " :: " + currentName + " :: " + currentHour + " :: " + currentTime));
            }
        } finally {
            cursor.close();
        }
    }

    private Cursor displayDataBaseInfo() {
        SQLiteDatabase sqlDb = database.getReadableDatabase();
        String[] event = {
                GetEvent._ID,
                GetEvent.EventName,
                GetEvent.EventDuration,
                GetEvent.EventTime,
        };
        Cursor cursor = sqlDb.query(
                GetEvent.TABLE_NAME,
                event, null, null, null, null, null, null
        );
        return cursor;

    }
}
