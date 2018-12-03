package com.example.admin.relativedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Admin on 09-Apr-17.
 */

public class DocDatabaseSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Doctor doctor;

    public DocDatabaseSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open(){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public boolean addDoctor(Doctor doctor){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_ID, doctor.getUserId());
        values.put(DatabaseHelper.COL_DOC_NAME, doctor.getDocName());
        values.put(DatabaseHelper.COL_DOC_DETAILS,doctor.getDocDetails());
        values.put(DatabaseHelper.COL_DOC_EMAIL, doctor.getDocEmail());
        values.put(DatabaseHelper.COL_DOC_PHONE, doctor.getDocPhone());
        long DocId = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_DOC,null,values);
        this.close();
        if (DocId > 0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<Doctor> getAllDoctor(int nowDocId){
        ArrayList<Doctor>doctors = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME_DOC,null,null,null,null,
                null,null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for (int i = 0; cursor.getCount() > i; i++ ){
                int docId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DOC_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_USER_ID));
                String docName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOC_NAME));
                String docDetails = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOC_DETAILS));
                String docEmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOC_EMAIL));
                String docPhone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOC_PHONE));

                doctor = new Doctor(userId, docName, docDetails, docEmail, docPhone, docId);
                doctors.add(doctor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return doctors;
    }

    public boolean updateDoctor(Doctor doctor, int rowid){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_ID,doctor.getUserId());
        values.put(DatabaseHelper.COL_DOC_NAME,doctor.getDocName());
        values.put(DatabaseHelper.COL_DOC_DETAILS,doctor.getDocDetails());
        values.put(DatabaseHelper.COL_DOC_EMAIL,doctor.getDocEmail());
        values.put(DatabaseHelper.COL_DOC_PHONE,doctor.getDocPhone());
        int updateDocId = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME_DOC, values,
                DatabaseHelper.COL_DOC_ID+" = ?",new String[]{String.valueOf(rowid)});
        this.close();
        if (updateDocId > 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteDoc(int rowId) {
        this.open();
        int deleteDocId = sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME_DOC,
                DatabaseHelper.COL_DOC_ID + " = ?", new String[]{String.valueOf(rowId)});
        this.close();
        if (deleteDocId > 0) {
            return true;
        } else {
            return false;
        }
    }
}
