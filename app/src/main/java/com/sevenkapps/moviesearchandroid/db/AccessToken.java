package com.sevenkapps.moviesearchandroid.db;

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
                    COLUMN_NAME_CREATED_AT + " DATETIME," +
                    COLUMN_NAME_VALID + "BOOLEAN)";

    public static final String SQL_DELETE_ACCESS_TOKENS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
