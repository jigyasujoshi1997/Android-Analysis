package com.example.lipi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lipi.data.SampleContract.ErrorEntry;
import com.example.lipi.data.SampleDbHelper;


public class AddErrorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the error's code
     */
    private EditText mErrorCode;
    /**
     * spinner field to enter the error's description
     */
    private Spinner mErrorDescriptionSpinner;
    /**
     * EditText field to enter the error's date
     */
    private EditText mErrorDate;
    private EditText mErrorTime;
    /**
     * EditText field to enter the machine-id
     */
    private EditText mMachineId;
    private EditText mMachineLocation;
    private EditText mInstallationDate;
    /**
     * Description of error.
     */
    private int mErrorDescription = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_error);

        mErrorCode = (EditText) findViewById(R.id.error_code);
        mErrorDescriptionSpinner = (Spinner) findViewById(R.id.error_description);
        setupSpinner();
        mErrorDate = (EditText) findViewById(R.id.error_date);
        mErrorTime = (EditText) findViewById(R.id.error_time);
        mMachineId = (EditText) findViewById(R.id.machine_name);
        mMachineLocation = (EditText) findViewById(R.id.machine_location);
        mInstallationDate = (EditText) findViewById(R.id.installation_date);
    }


    /**
     * Setup the dropdown spinner that allows the user to select the type of the error.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter errorSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_error_options,android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        errorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mErrorDescriptionSpinner.setAdapter(errorSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mErrorDescriptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.error_type_1))) {
                        mErrorDescription = ErrorEntry.ERROR_TYPE_ONE;
                    } else if (selection.equals(getString(R.string.error_type_2))) {
                        mErrorDescription = ErrorEntry.ERROR_TYPE_TWO;
                    } else if (selection.equals(getString(R.string.error_type_3))) {
                        mErrorDescription = ErrorEntry.ERROR_TYPE_THREE;
                    } else if (selection.equals(getString(R.string.error_type_4))) {
                        mErrorDescription = ErrorEntry.ERROR_TYPE_FOUR;
                    } else {
                        mErrorDescription = ErrorEntry.ERROR_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mErrorDescription = 0; // Unknown
            }
        });
    }

    private void insertError() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mErrorCode.getText().toString().trim();
        String mIdString = mMachineId.getText().toString().trim();
        String InstallDateString = mInstallationDate.getText().toString().trim();
        String ErrorDateString = mErrorDate.getText().toString().trim();
        String ErrorTimeString = mErrorTime.getText().toString().trim();
        String mLocString = mMachineLocation.getText().toString().trim();
        int mId = Integer.parseInt(mIdString);

        // Create database helper
        SampleDbHelper mDbHelper = new SampleDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        ContentValues values = new ContentValues();
        values.put(ErrorEntry.COLUMN_ERROR_CODE,nameString);
        values.put(ErrorEntry.COLUMN_ERROR_DESCRIPTION,mErrorDescription);
        values.put(ErrorEntry.COLUMN_ERROR_DATE,ErrorDateString);
        values.put(ErrorEntry.COLUMN_ERROR_TIME,ErrorTimeString);
        values.put(ErrorEntry.COLUMN_MACHINE_ID,mId);
        values.put(ErrorEntry.COLUMN_MACHINE_LOCATION,mLocString);
        values.put(ErrorEntry.COLUMN_INSTALLATION_DATE,InstallDateString);

        // Insert a new row for error in the database, returning the ID of that new row.
        long newRowId = db.insert(ErrorEntry.TABLE_NAME,null,values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this,"Error with saving new error",Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this,"Error saved with row id: " + newRowId,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save error to database
                insertError();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_send:
                String nameString = mErrorCode.getText().toString().trim();
                String mIdString = mMachineId.getText().toString().trim();
                String InstallDateString = mInstallationDate.getText().toString().trim();
                String ErrorDateString = mErrorDate.getText().toString().trim();
                String ErrorTimeString = mErrorTime.getText().toString().trim();
                String mLocString = mMachineLocation.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String message = "Recent Error Report: Error Code- " + nameString + " Error Date- " + ErrorDateString + " Error Time- " + ErrorTimeString +
                        " Machine Id- " + mIdString + " Machine Location- " + mLocString + "Machine Install Date" + InstallDateString;
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,message);
                intent.setType("text/plain");
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
