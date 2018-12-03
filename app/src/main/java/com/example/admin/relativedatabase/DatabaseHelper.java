package com.example.admin.relativedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 09-Apr-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME_DOC = "Doctor Database";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_USER = "tb1_user";
    public static final String COL_USER_ID = "tb1_user_id";
    public static final String COL_USER_NAME = "tb1_user_name";
    public static final String COL_USER_EMAIL = "tb1_user_email";
    public static final String COL_USER_PASS = "tab1_user_pass";
    public static final String COL_USER_CON_PASS = "tab1_user_con_pass";

    public static final String TABLE_NAME_DOC = "tb2_doctor";
    public static final String COL_DOC_ID = "tb2_id";
    public static final String COL_DOC_NAME = "tb2_name";
    public static final String COL_DOC_DETAILS = "tab2_details";
    public static final String COL_DOC_EMAIL = "tb2_email";
    public static final String COL_DOC_PHONE = "tb2_phone";

    public static final String TABLE_NAME_PRE = "tb3_prescription";
    public static final String COL_PRE_ID = "tb3_pre_id";
    public static final String COL_PRE_DATE = "tb3_pre_date";
    public static final String COL_PRE_IMAGE = "tb3_pre_image";

    public static final String CREATE_USER_TABLE = "create table "+TABLE_NAME_USER+"("+
            COL_USER_ID+" integer primary key, "+
            COL_USER_NAME+" text, "+
            COL_USER_EMAIL+" text, "+
            COL_USER_PASS+" text, "+
            COL_USER_CON_PASS+" text );";

    public static final String CREATE_DOC_TABLE = "create table "+TABLE_NAME_DOC+"("+
            COL_DOC_ID+" integer primary key, "+
            COL_USER_ID+" integer, "+
            COL_DOC_NAME+" text, "+
            COL_DOC_DETAILS+" text, "+
            COL_DOC_EMAIL+" text, "+
            COL_DOC_PHONE+" text );";

    public static final String CREATE_PRE_TABLE = "create table "+TABLE_NAME_PRE+"("+
            COL_PRE_ID+" integer primary key, "+
            COL_DOC_ID+" integer, "+
            COL_PRE_DATE+" text, "+
            COL_DOC_NAME+" text, "+
            COL_DOC_DETAILS+" text, "+
            COL_PRE_IMAGE+" text );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME_DOC, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_DOC_TABLE);
        db.execSQL(CREATE_PRE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist "+CREATE_USER_TABLE);
        db.execSQL("drop table if exist "+TABLE_NAME_DOC);
        db.execSQL("drop table if exist "+CREATE_PRE_TABLE);
        onCreate(db);
    }
}
