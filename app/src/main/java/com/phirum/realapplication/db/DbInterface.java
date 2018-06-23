package com.phirum.realapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public interface DbInterface<T> {
    Context getContext();

    MySqliteHelper newInstanceDb();

    SQLiteDatabase readMySqliteDb();

    SQLiteDatabase writeMySqliteDb();

    void releaseDb();

    LinkedList<T> getAllData();

    LinkedList<T> getAData();

    T getADataById(String id);

    int size();

    long insertDataToTable(T data);

    long deleteDataFromTable(int id);

    long updateDataToTable(T data);
}
