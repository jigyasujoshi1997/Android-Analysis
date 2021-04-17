package com.example.lipi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lipi.data.SampleDbHelper;
import com.example.lipi.data.SampleContract.ErrorEntry;

public class ViewErrorActivity extends AppCompatActivity {

    private SampleDbHelper mDbHelper;
    private TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_error);

        mDbHelper = new SampleDbHelper(this);

    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the error database.
     */
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                ErrorEntry._ID,
                ErrorEntry.COLUMN_ERROR_CODE,
                ErrorEntry.COLUMN_ERROR_DESCRIPTION,
                ErrorEntry.COLUMN_ERROR_DATE,
                ErrorEntry.COLUMN_ERROR_TIME,
                ErrorEntry.COLUMN_MACHINE_ID,
                ErrorEntry.COLUMN_MACHINE_LOCATION,
                ErrorEntry.COLUMN_INSTALLATION_DATE
        };
        Cursor cursor = db.query(
                ErrorEntry.TABLE_NAME,
                projection,null,null,null,null,null);
        displayView = (TextView) findViewById(R.id.text_view_error);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The errors table contains <number of rows in Cursor> errors.
            // code - description - datetime - machineid - location - installationdate
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The Error table contains " + cursor.getCount() + " errors.\n\n");
            displayView.append(ErrorEntry._ID + " - " +
                    ErrorEntry.COLUMN_ERROR_CODE + " - " +
                    ErrorEntry.COLUMN_ERROR_DESCRIPTION + " - " +
                    ErrorEntry.COLUMN_ERROR_DATE + " - " +
                    ErrorEntry.COLUMN_ERROR_TIME + " - " +
                    ErrorEntry.COLUMN_MACHINE_ID + " - " +
                    ErrorEntry.COLUMN_MACHINE_LOCATION + " - " +
                    ErrorEntry.COLUMN_INSTALLATION_DATE +
                    "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ErrorEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ErrorEntry.COLUMN_ERROR_CODE);
            int descColumnIndex = cursor.getColumnIndex(ErrorEntry.COLUMN_ERROR_DESCRIPTION);
            int eDateColumnIndex = cursor.getColumnIndex(ErrorEntry.COLUMN_ERROR_DATE);
            int eTimeColumnIndex = cursor.getColumnIndex(ErrorEntry.COLUMN_ERROR_TIME);
            int machineColumnIndex = cursor.getColumnIndex(ErrorEntry.COLUMN_MACHINE_ID);
            int locationColumnIndex = cursor.getColumnIndex(ErrorEntry.COLUMN_MACHINE_LOCATION);
            int installDateColumnIndex = cursor.getColumnIndex(ErrorEntry.COLUMN_INSTALLATION_DATE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentDesc = cursor.getInt(descColumnIndex);
                String currentDate = cursor.getString(eDateColumnIndex);
                String currentTime = cursor.getString(eTimeColumnIndex);
                int currentMachine = cursor.getInt(machineColumnIndex);
                String currentLocation = cursor.getString(locationColumnIndex);
                String currentInstallDate = cursor.getString(installDateColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDesc + " - " +
                        currentDate + " - " +
                        currentTime + " - " +
                        currentMachine + " - " +
                        currentLocation + " - " +
                        currentInstallDate));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertError();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
            case R.id.action_send:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setAction(Intent.ACTION_SEND);
                String displayViewString = displayView.getText().toString().trim();
                intent.putExtra(Intent.EXTRA_TEXT,displayViewString);
                intent.setType("text/plain");
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertError() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ErrorEntry.COLUMN_ERROR_CODE,"ERR545");
        values.put(ErrorEntry.COLUMN_ERROR_DESCRIPTION,ErrorEntry.ERROR_TYPE_ONE);
        values.put(ErrorEntry.COLUMN_ERROR_DATE,"01/05/2019");
        values.put(ErrorEntry.COLUMN_ERROR_TIME,"12:15:15");
        values.put(ErrorEntry.COLUMN_MACHINE_ID,2451);
        values.put(ErrorEntry.COLUMN_MACHINE_LOCATION,"Mumbai");
        values.put(ErrorEntry.COLUMN_INSTALLATION_DATE,"01/06/2017");

        long newRowId = db.insert(ErrorEntry.TABLE_NAME,null,values);
    }
}
