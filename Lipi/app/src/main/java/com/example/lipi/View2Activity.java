package com.example.lipi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lipi.data.SampleDbHelper;
import com.example.lipi.data.SampleContract.ErrorEntry;

public class View2Activity extends AppCompatActivity {

    private EditText errorCode;
    private SampleDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
        mDbHelper = new SampleDbHelper(this);
    }

    public void SearchErrorButton(View view) {
        errorCode = (EditText) findViewById(R.id.error_code_to_search);
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        String mString = errorCode.getText().toString().trim();
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
        String selection = ErrorEntry.COLUMN_ERROR_CODE + "=?";
        String[] selectionArgs = {mString};
        Cursor cursor = db.query(
                ErrorEntry.TABLE_NAME,
                projection,selection,selectionArgs,null,null,null);
        TextView displayView = (TextView) findViewById(R.id.view_error_code);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The errors table contains <number of rows in Cursor> errors.
            // code - description - datetime - machineId - location - installationDate
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
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
