package com.phirum.realapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.phirum.realapplication.pojo.Datum;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class UserDbRepo implements DbInterface<Datum> {
    private WeakReference<Context> mWeakReference;
    private MySqliteHelper mySqliteHelper;

    public UserDbRepo(Context context) {
        mWeakReference = new WeakReference<>(context);
        mySqliteHelper = new MySqliteHelper(context);
    }

    @Override
    public Context getContext() {
        return mWeakReference.get();
    }

    @Override
    public MySqliteHelper newInstanceDb() {
        return mySqliteHelper;
    }

    @Override
    public SQLiteDatabase readMySqliteDb() {
        return newInstanceDb().getReadableDatabase();
    }

    @Override
    public SQLiteDatabase writeMySqliteDb() {
        return newInstanceDb().getWritableDatabase();
    }

    @Override
    public void releaseDb() {
        mySqliteHelper.close();
    }

    @Override
    public LinkedList<Datum> getAllData() {
        LinkedList<Datum> linkedList = new LinkedList<>();
        String query = "SELECT DISTINCT * FROM " + MySqliteHelper.TABLE_USER + " ORDER BY " + MySqliteHelper.FIELD_ID + " DESC LIMIT -1 OFFSET 0;";
        Cursor cursor = readMySqliteDb().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Datum item = new Datum();
                item.withId(QueryUtils.getInt(cursor, MySqliteHelper.FIELD_ID));
                item.withFirstName(QueryUtils.getString(cursor, MySqliteHelper.FIELD_NAME));

                item.withPassword(QueryUtils.getString(cursor, MySqliteHelper.FIELD_PASSWORD));
                linkedList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        releaseDb();
        return linkedList;
    }

    @Override
    public LinkedList<Datum> getAData() {
        return null;
    }

    @Override
    public Datum getADataById(String id) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public long insertDataToTable(Datum data) {
        ContentValues values = getContentValues(data);
        return writeMySqliteDb().insertOrThrow(MySqliteHelper.TABLE_USER, null, values);
    }

    @Override
    public long deleteDataFromTable(int id) {
        return newInstanceDb().getWritableDatabase().delete(MySqliteHelper.TABLE_USER, MySqliteHelper.FIELD_ID + " =? ", new
                String[]{String.valueOf(id)});
    }

    @Override
    public long updateDataToTable(Datum data) {
        ContentValues values = getContentValues(data);
        int update = writeMySqliteDb().update(MySqliteHelper.TABLE_USER, values, MySqliteHelper.FIELD_ID + " =? ",
                new String[]{String.valueOf(data.id)});
        writeMySqliteDb().close();
        return update;
    }

    private ContentValues getContentValues(Datum data) {
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.FIELD_NAME, data.firstName);
        values.put(MySqliteHelper.FIELD_PASSWORD, data.password);
        return values;
    }
}
