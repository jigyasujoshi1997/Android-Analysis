package com.example.lipi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lipi.data.SampleDbHelper;
import com.example.lipi.data.SampleContract.ErrorEntry;


public class View3Activity extends AppCompatActivity {
    private SampleDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_error);
        mDbHelper = new SampleDbHelper(this);
    }
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String COUNT = "count";
        Cursor cursor = db.rawQuery("SELECT *, COUNT(" + ErrorEntry.COLUMN_ERROR_CODE + ") AS " + COUNT +  " FROM " + ErrorEntry.TABLE_NAME + " GROUP BY "
                + ErrorEntry.COLUMN_ERROR_CODE + " ORDER BY " + COUNT + " DESC ",null);
        TextView displayView = (TextView) findViewById(R.id.text_view_error);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The errors table contains <number of rows in Cursor> errors.
            // code - description - datetime - machineid - location - installationdate
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The Error table contains " + cursor.getCount() + " errors.\n\n");
            displayView.append(
                    ErrorEntry.COLUMN_ERROR_CODE + " - " +
                            ErrorEntry.COLUMN_ERROR_DESCRIPTION + " - " +
                            ErrorEntry.COLUMN_ERROR_DATE + " - " +
                            ErrorEntry.COLUMN_ERROR_TIME + " - " +
                            ErrorEntry.COLUMN_MACHINE_ID + " - " +
                            ErrorEntry.COLUMN_MACHINE_LOCATION + " - " +
                            ErrorEntry.COLUMN_INSTALLATION_DATE +
                            "\n");

            // Figure out the index of each column
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
                String currentName = cursor.getString(nameColumnIndex);
                int currentDesc = cursor.getInt(descColumnIndex);
                String currentDate = cursor.getString(eDateColumnIndex);
                String currentTime = cursor.getString(eTimeColumnIndex);
                int currentMachine = cursor.getInt(machineColumnIndex);
                String currentLocation = cursor.getString(locationColumnIndex);
                String currentInstallDate = cursor.getString(installDateColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" +
                        currentName + " - " +
                        currentDesc + " - " +
                        currentDate + " - " +
                        currentTime + " - " +
                        currentMachine + " - " +
                        currentLocation + " - " +
                        currentInstallDate));
            }
        }
        finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }}
}