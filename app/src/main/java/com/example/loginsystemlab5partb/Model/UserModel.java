package com.example.loginsystemlab5partb.Model;

public class UserModel {
    private int UserID;
    private String UserName,Password;

    public UserModel(int userID, String userName, String password) {
        UserID = userID;
        UserName = userName;
        Password = password;
    }

    public int getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }
}
