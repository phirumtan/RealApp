package com.phirum.realapplication.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MySqliteHelper extends SQLiteOpenHelper {
    private static final String TAG = MySqliteHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase"; //Column names...
    public static final String TABLE_USER = "tbl_user";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_IMAGE = "image";
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + " ("
            + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD_NAME + " TEXT, "
            + FIELD_PASSWORD + " TEXT, "
            + FIELD_IMAGE + " TEXT);";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    /**
     * INTEGER, TEXT, BLOB, REAL, NUMERIC
     */
}