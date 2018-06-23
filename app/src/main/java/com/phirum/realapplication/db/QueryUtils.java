package com.phirum.realapplication.db;

import android.database.Cursor;
import android.text.TextUtils;

public class QueryUtils {

    public static int getInt(Cursor cursor, String colName) {
        int result = 0;
        if (!TextUtils.isEmpty(colName)) {
            try {
                if (cursor != null && !cursor.isClosed() && cursor.getCount() > 0) {
                    int index = cursor.getColumnIndex(colName);
                    if (index > -1)
                        result = cursor.getInt(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getString(Cursor cursor, String colName) {
        String result = "";
        if (!TextUtils.isEmpty(colName)) {
            try {
                if (cursor != null && !cursor.isClosed() && cursor.getCount() > 0) {
                    int index = cursor.getColumnIndex(colName);
                    if (index > -1)
                        result = cursor.getString(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
