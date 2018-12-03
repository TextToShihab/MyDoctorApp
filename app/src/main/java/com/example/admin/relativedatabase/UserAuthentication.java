package com.example.admin.relativedatabase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 09-Apr-17.
 */

public class UserAuthentication {

    private static final String PREFERENCE_NAME = "User Authentication";
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "email_address";
    private static final String USER_PASSWORD = "Password";
    public static final int USER_EMPTY_ID = 0;
    public static final String DEFAULT_MSG = "No value found";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserAuthentication(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveNowUser(int userId, String userEmail, String userPass){
        editor.putInt(USER_ID,userId);
        editor.putString(USER_EMAIL,userEmail);
        editor.putString(USER_PASSWORD,userPass);
        editor.commit();
    }

    public void deleteNowUser(){
        editor.remove(USER_ID);
        editor.remove(USER_EMAIL);
        editor.remove(USER_PASSWORD);
        editor.commit();
    }
    public int getUserId(){
        return sharedPreferences.getInt(USER_ID,USER_EMPTY_ID);
    }

    public String getUserEmail(){
        return sharedPreferences.getString(USER_EMAIL,DEFAULT_MSG);
    }

    public String getUserPass(){
        return sharedPreferences.getString(USER_PASSWORD,DEFAULT_MSG);
    }

}
