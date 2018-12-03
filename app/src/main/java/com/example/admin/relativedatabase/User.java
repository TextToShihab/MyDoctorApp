package com.example.admin.relativedatabase;

/**
 * Created by Admin on 09-Apr-17.
 */

public class User {

    private String userName, userEmail, userPass, userConPass;
    private int userId;


    public User(String userName, String userEmail, String docDetail, String userConPass, int userId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = docDetail;
        this.userConPass = userConPass;
        this.userId = userId;
    }

    public User(String userName, String userEmail, String userPass, String userConPass) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userConPass = userConPass;
    }

    public User(String userEmail, String userPass) {
        this.userEmail = userEmail;
        this.userPass = userPass;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserConPass() {
        return userConPass;
    }

    public void setUserConPass(String userConPass) {
        this.userConPass = userConPass;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
