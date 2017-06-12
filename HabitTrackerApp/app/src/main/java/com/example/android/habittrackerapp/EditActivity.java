package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.habittrackerapp.data.EventCategories.GetEvent;
import com.example.android.habittrackerapp.data.EventDatabase;

/**
 * Created by hp on 31-May-17.
 */

public class EditActivity extends AppCompatActivity {
    private EditText eventName;
    private EditText eventDuration;
    private EditText eventTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        eventName = (EditText) findViewById(R.id.lebel);
        eventDuration = (EditText) findViewById(R.id.time_planned);
        eventTime = (EditText) findViewById(R.id.time_entered);
    }

    private void insertEvents() {
        String nameString = eventName.getText().toString().trim();
        String hoursString = eventDuration.getText().toString().trim();
        String timeString = eventTime.getText().toString().trim();
        EventDatabase database = new EventDatabase(this);
        SQLiteDatabase SQLDataBase = database.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(GetEvent.EventName, nameString);
        v.put(GetEvent.EventDuration, hoursString);
        v.put(GetEvent.EventTime, timeString);

        long nextRowId = SQLDataBase.insert(GetEvent.TABLE_NAME, null, v);

        if (nextRowId == -1) {
            Toast.makeText(this, "Some error in saving details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Record saved at row id : " + nextRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem m) {
        switch (m.getItemId()) {
            case R.id.done_and_save:
                insertEvents();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(m);
    }
}