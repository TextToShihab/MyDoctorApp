package com.example.admin.relativedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Admin on 10-Apr-17.
 */

public class PreDatabaseSource {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Prescription prescription;

    public PreDatabaseSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open(){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public boolean addPre(Prescription prescription){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_DOC_ID, prescription.getDocId());
        values.put(DatabaseHelper.COL_PRE_DATE, prescription.getPreDate());
        values.put(DatabaseHelper.COL_PRE_IMAGE, prescription.getPreImage());
        values.put(DatabaseHelper.COL_DOC_NAME, prescription.getPreDocName());
        values.put(DatabaseHelper.COL_DOC_DETAILS, prescription.getPreDocDetails());
        long PreId = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_PRE,null,values);
        this.close();
        if (PreId > 0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<Prescription> getAllPre(){
        ArrayList<Prescription>prescriptions = new ArrayList<>();
        this.open();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME_PRE,null,null,null,null,
                null,null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for (int i = 0; cursor.getCount() > i; i++ ){
                int docId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DOC_ID));
                int preId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PRE_ID));
                String preDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PRE_DATE));
                String preImage = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PRE_IMAGE));
                String preDocName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOC_NAME));
                String preDocDetails = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DOC_DETAILS));

                prescription = new Prescription(docId, preImage, preDate,preDocName, preDocDetails, preId);
                prescriptions.add(prescription);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return prescriptions;
    }

    public boolean updatePre(Prescription prescription, int preid){
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_DOC_ID,prescription.getDocId());
        values.put(DatabaseHelper.COL_PRE_ID,prescription.getPreId());
        values.put(DatabaseHelper.COL_PRE_DATE,prescription.getPreDate());
        values.put(DatabaseHelper.COL_PRE_IMAGE, prescription.getPreImage());
        values.put(DatabaseHelper.COL_DOC_NAME, prescription.getPreDocName());
        values.put(DatabaseHelper.COL_DOC_DETAILS, prescription.getPreDocDetails());
        int updateDocId = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME_PRE, values,
                DatabaseHelper.COL_PRE_ID+" = ?",new String[]{String.valueOf(preid)});
        this.close();
        if (updateDocId > 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean deletePre(int preId) {
        this.open();
        int deletePreId = sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME_PRE,
                DatabaseHelper.COL_PRE_ID + " = ?", new String[]{String.valueOf(preId)});
        this.close();
        if (deletePreId > 0) {
            return true;
        } else {
            return false;
        }
    }
}
