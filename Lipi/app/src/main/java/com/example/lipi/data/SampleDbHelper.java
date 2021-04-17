package com.example.lipi.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lipi.data.SampleContract.ErrorEntry;

/**
 * Database helper for error app. Manages database creation and version management.
 */
public class SampleDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = SampleDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "sample.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link SampleDbHelper}.
     *
     * @param context of the app
     */
    public SampleDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the Error table

        String SQL_CREATE_ERROR_TABLE = "CREATE TABLE " + ErrorEntry.TABLE_NAME + " ("
                + ErrorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ErrorEntry.COLUMN_ERROR_CODE + " VARCHAR, "
                + ErrorEntry.COLUMN_ERROR_DESCRIPTION + " INTEGER, "
                + ErrorEntry.COLUMN_ERROR_DATE + " TEXT, "
                + ErrorEntry.COLUMN_ERROR_TIME + " TEXT, "
                + ErrorEntry.COLUMN_MACHINE_ID + " INTEGER, "
                + ErrorEntry.COLUMN_MACHINE_LOCATION + " TEXT, "
                + ErrorEntry.COLUMN_INSTALLATION_DATE + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ERROR_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
    }
}
