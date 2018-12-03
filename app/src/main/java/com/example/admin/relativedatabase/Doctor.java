package com.example.admin.relativedatabase;

/**
 * Created by Admin on 09-Apr-17.
 */

public class Doctor {

    private String docName,docDetails, docEmail, docPhone;
    private int userId, docId;

    public Doctor(int userId, String docName, String docDetail, String docEmail, String docPhone, int docId) {
        this.docId = docId;
        this.docName = docName;
        this.docDetails = docDetail;
        this.docEmail = docEmail;
        this.docPhone = docPhone;
        this.userId = userId;
    }

    public Doctor(int userId, String docName, String docDetails, String docEmail, String docPhone) {
        this.userId = userId;
        this.docName = docName;
        this.docDetails = docDetails;
        this.docEmail = docEmail;
        this.docPhone = docPhone;
    }

    public Doctor() {
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocEmail() {
        return docEmail;
    }

    public void setDocEmail(String docEmail) {
        this.docEmail = docEmail;
    }

    public String getDocPhone() {
        return docPhone;
    }

    public void setDocPhone(String docPhone) {
        this.docPhone = docPhone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getDocDetails() {
        return docDetails;
    }

    public void setDocDetails(String docDetails) {
        this.docDetails = docDetails;
    }
}
