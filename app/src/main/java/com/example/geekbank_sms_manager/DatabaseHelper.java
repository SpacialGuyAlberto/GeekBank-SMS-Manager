package com.example.geekbank_sms_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smsManager.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SMS = "sms";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    public static final String COLUMN_MESSAGE_BODY = "messageBody";
    public static final String COLUMN_DATE = "date";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_SMS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PHONE_NUMBER + " TEXT, " +
                    COLUMN_MESSAGE_BODY + " TEXT, " +
                    COLUMN_DATE + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        onCreate(db);
    }
}