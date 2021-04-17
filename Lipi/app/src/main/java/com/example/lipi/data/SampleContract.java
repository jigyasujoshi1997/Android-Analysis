package com.example.lipi.data;

import android.provider.BaseColumns;

public class SampleContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private SampleContract() {
    }

    /**
     * Inner class that defines constant values for the error database table.
     * Each entry in the table represents a single error.
     */
    public static final class ErrorEntry implements BaseColumns {
        /**
         * Name of database table for errors
         */
        public final static String TABLE_NAME = "error";
        /**
         * Unique ID number for the error (only for use in the database table).
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;
        /**
         * Error Code.
         * Type: VARCHAR
         */
        public final static String COLUMN_ERROR_CODE = "code";
        /**
         * Machine location of the error.
         * Type: TEXT
         */
        public final static String COLUMN_MACHINE_LOCATION = "location";
        /**
         * Description of Error.
         * Type: INTEGER
         */
        public final static String COLUMN_ERROR_DESCRIPTION = "description";
        /**
         * MACHINE ID.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_MACHINE_ID = "mid";
        /**
         * Installation Date Type= TEXT
         */
        public final static String COLUMN_INSTALLATION_DATE = "installDate";
        /**
         * Error Date and Time Type= TEXT
         */
        public final static String COLUMN_ERROR_DATE = "errorDate";
        public final static String COLUMN_ERROR_TIME = "errorTime";

        public static final int ERROR_UNKNOWN = 0;
        public static final int ERROR_TYPE_ONE = 1;
        public static final int ERROR_TYPE_TWO = 2;
        public static final int ERROR_TYPE_THREE = 3;
        public static final int ERROR_TYPE_FOUR = 4;
    }
}
