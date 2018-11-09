package com.sevenkapps.moviesearchandroid.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccessToken {

    public static final String TABLE_NAME = "access_tokens";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TOKEN = "token";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_VALID = "valid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";

    public static final String SQL_CREATE_ACCESS_TOKENS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_TOKEN + " TEXT," +
                    COLUMN_TYPE + " TEXT," +
                    COLUMN_IMAGE + " TEXT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    COLUMN_VALID + " BOOLEAN)";

    public static final String SQL_DELETE_ACCESS_TOKENS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static void createToken(Context context, String token, String type, String name, String image) {
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        db.execSQL("update " + TABLE_NAME + " set " + COLUMN_VALID + " = 0;");
        db.execSQL("insert into " + TABLE_NAME + " (" +
                COLUMN_TOKEN + ", " +
                COLUMN_TYPE + ", " +
                COLUMN_NAME + ", " +
                COLUMN_IMAGE + ", " +
                COLUMN_VALID + ") values ('" +
                token + "', '" +
                type + "', '" +
                name + "', '" +
                image + "', " +
                "1);"); //TODO
    }

    public static String getToken(Context context) {
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        Cursor res = db.rawQuery("select " + COLUMN_TOKEN + " from " + TABLE_NAME + " where " + COLUMN_VALID + " = 1 limit 1;", null);
        if (res!=null && res.moveToFirst()) {
            return res.getString(res.getColumnIndex(COLUMN_TOKEN));
        } else {
            return null;
        }
    }

    public static boolean authenticated(Context context) {
        return getToken(context) != null;
    }
}
