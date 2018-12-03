package com.example.admin.relativedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Admin on 09-Apr-17.
 */

public class UserDatabaseSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private User user;
    private int userId = 0;

    public UserDatabaseSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open(){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public boolean addUser(User user){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_NAME, user.getUserName());
        values.put(DatabaseHelper.COL_USER_EMAIL,user.getUserEmail());
        values.put(DatabaseHelper.COL_USER_PASS, user.getUserPass());
        values.put(DatabaseHelper.COL_USER_CON_PASS, user.getUserConPass());
        long userId = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_USER,null,values);
        this.close();
        if (userId > 0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<User> getAllUser(){
        ArrayList<User>users = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME_USER,null,null,null,null,
                null,null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for (int i = 0; cursor.getCount() > i; i++ ){
                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_NAME));
                String userEmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_EMAIL));
                String userPass = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_PASS));
                String userConPass = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_CON_PASS));

                user = new User(userName, userEmail, userPass, userConPass, userId);
                users.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return users;
    }

    public boolean matchUser(String userEmailIn, String userPassIn){
        boolean status = false;
        this.open();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME_USER,null,null,null,null,
                null,null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for (int i = 0; cursor.getCount() > i; i++ ){
                String userEmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_EMAIL));
                String userPass = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USER_PASS));
                if(userEmailIn.equals(userEmail) && userPassIn.equals(userPass)){
                    int userNowId = (i+1);
                    Intent intent = new Intent();
                    intent.putExtra("userIdIn", userNowId);
                    this.userId = userNowId;
                    status = true;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return status;
    }

    public boolean updateUser(User user, int rowid){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_NAME,user.getUserName());
        values.put(DatabaseHelper.COL_USER_EMAIL,user.getUserEmail());
        values.put(DatabaseHelper.COL_USER_PASS,user.getUserPass());
        values.put(DatabaseHelper.COL_USER_CON_PASS,user.getUserConPass());
        int updateId = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME_USER, values,
                DatabaseHelper.COL_USER_ID +" = ?",new String[]{String.valueOf(rowid)});
        this.close();
        if (updateId > 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteUser(int rowId) {
        this.open();
        int deleteId = sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME_USER,
                DatabaseHelper.COL_USER_ID + " = ?", new String[]{String.valueOf(rowId)});
        this.close();
        if (deleteId > 0) {
            return true;
        } else {
            return false;
        }
    }
    public int getUserId() {
        return userId;
    }
}
