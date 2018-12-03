package com.example.admin.relativedatabase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Admin on 10-Apr-17.
 */

public class Prescription {

    private String preImage, preDate, preDocName, preDocDetails;
    private int docId, preId;
    private Bitmap bitmapImage;

    public Prescription(int docId,String preImage, String preDate,String preDocName, String preDocDetails, int preId) {
        this.docId = docId;
        this.preImage = preImage;
        this.preDate = preDate;
        this.preDocName = preDocName;
        this.preDocDetails = preDocDetails;
        this.preId = preId;
    }

    public Prescription(int docId,String preImage, String preDate,String preDocName, String preDocDetails) {
        this.docId = docId;
        this.preImage = preImage;
        this.preDate = preDate;
        this.preDocName = preDocName;
        this.preDocDetails = preDocDetails;
    }

    public Prescription() {
    }

    public String getPreImage() {
        return preImage;
    }

    public void setPreImage(String preImage) {
        this.preImage = preImage;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getPreDate() {
        return preDate;
    }

    public void setPreDate(String preDate) {
        this.preDate = preDate;
    }

    public int getPreId() {
        return preId;
    }

    public void setPreId(int preId) {
        this.preId = preId;
    }

    public String getPreDocName() {
        return preDocName;
    }

    public void setPreDocName(String preDocName) {
        this.preDocName = preDocName;
    }

    public String getPreDocDetails() {
        return preDocDetails;
    }

    public void setPreDocDetails(String preDocDetails) {
        this.preDocDetails = preDocDetails;
    }

    public Bitmap getBitmapImage() {
        BitmapFactory.Options bmo = new BitmapFactory.Options();
        bmo.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(preImage, bmo);
        bmo.inJustDecodeBounds = false;
        bmo.inSampleSize = 20;
        bmo.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(preImage, bmo);
        return bitmapImage = bitmap;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }
}
