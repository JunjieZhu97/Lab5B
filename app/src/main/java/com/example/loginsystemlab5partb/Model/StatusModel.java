package com.example.loginsystemlab5partb.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class StatusModel implements Parcelable {
    private int StatusID,UserID;
    private String UserName,Status;

    public StatusModel(int statusID, int userID, String userName, String status) {
        StatusID = statusID;
        UserID = userID;
        UserName = userName;
        Status = status;
    }

    public int getStatusID() {
        return StatusID;
    }

    public int getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getStatus() {
        return Status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.StatusID);
        dest.writeInt(this.UserID);
        dest.writeString(this.UserName);
        dest.writeString(this.Status);
    }

    protected StatusModel(Parcel in) {
        this.StatusID = in.readInt();
        this.UserID = in.readInt();
        this.UserName = in.readString();
        this.Status = in.readString();
    }

    public static final Parcelable.Creator<StatusModel> CREATOR = new Parcelable.Creator<StatusModel>() {
        @Override
        public StatusModel createFromParcel(Parcel source) {
            return new StatusModel(source);
        }

        @Override
        public StatusModel[] newArray(int size) {
            return new StatusModel[size];
        }
    };
}

