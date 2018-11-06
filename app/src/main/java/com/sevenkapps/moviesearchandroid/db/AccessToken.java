package com.sevenkapps.moviesearchandroid.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccessToken {

    public static final String TABLE_NAME = "access_tokens";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TOKEN = "token";
    public static final String COLUMN_NAME_TYPE = "type";
    public static final String COLUMN_NAME_CREATED_AT = "created_at";
    public static final String COLUMN_NAME_VALID = "valid";

    public static final String SQL_CREATE_ACCESS_TOKENS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TOKEN + " TEXT," +
                    COLUMN_NAME_TYPE + " TEXT," +
                    COLUMN_NAME_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    COLUMN_NAME_VALID + " BOOLEAN)";

    public static final String SQL_DELETE_ACCESS_TOKENS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static void createToken(Context context, String token, String type) {
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        db.execSQL("update " + TABLE_NAME + " set " + COLUMN_NAME_VALID + " = 0;");
        db.execSQL("insert into " + TABLE_NAME + " (" +
                COLUMN_NAME_TOKEN + ", " +
                COLUMN_NAME_TYPE + ", " +
                COLUMN_NAME_VALID + ") values ('" +
                token + "', '" +
                type + "', " +
                "1);"); //TODO
    }

    public static String getToken(Context context) {
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        Cursor res = db.rawQuery("select " + COLUMN_NAME_TOKEN + " from " + TABLE_NAME + " where " + COLUMN_NAME_VALID + " = 1 limit 1;", null);
        if (res!=null && res.moveToFirst()) {
            return res.getString(res.getColumnIndex(COLUMN_NAME_TOKEN));
        } else {
            return null;
        }
    }
}
