package com.example.loginsystemlab5partb.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.loginsystemlab5partb.Model.StatusModel;
import com.example.loginsystemlab5partb.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "UserDB";
    public static final int DB_VERSION = 1;

    public final String USER_TABLE = "Users";
    public final String STATUS_TABLE = "Status";


    public UserDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE + " (UserID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,UserName TEXT,Password TEXT)");
        db.execSQL("CREATE TABLE " + STATUS_TABLE + " (StatusID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,UserID INTEGER,Status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean InsertUser(String UserName, String Password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("UserName", UserName);
        cv.put("Password", Password);

        try {
            db.insert(USER_TABLE, null, cv);
            db.close();
            return true;

        } catch (Exception e) {
            db.close();

            return false;
        }

    }
    public UserModel GetUserLogin(String UserName ,String Password)
    {
        UserModel umodel=null;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cs=db.rawQuery("SELECT * FROM "+USER_TABLE+" WHERE UserName='"+UserName+"' AND Password='"+Password+"'",null);
        if(cs.moveToFirst())
        {
            umodel=new UserModel(cs.getInt(0),cs.getString(cs.getColumnIndex("UserName")),cs.getString(cs.getColumnIndex("Password")));
        }
        return umodel;
    }
    public List<UserModel> GetUserForValidate(String UserName)
    {
        List<UserModel> userModelList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cs=db.rawQuery("SELECT * FROM "+USER_TABLE+" WHERE UserName='"+UserName+"'",null);
        if(cs.moveToFirst())
        {
            while (!cs.isAfterLast())
            {
                UserModel umodel=new UserModel(cs.getInt(0),cs.getString(cs.getColumnIndex("UserName")),
                        cs.getString(cs.getColumnIndex("Password")));
                userModelList.add(umodel);
                cs.moveToNext();
            }
        }
        cs.close();
        db.close();
        return userModelList;
    }
    public boolean InsertStatus(int UserID,String Status)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("UserID",UserID);
        cv.put("Status",Status);

        try {
            db.insert(STATUS_TABLE,null,cv);
            db.close();
            return true;
        } catch (Exception e) {
            db.close();
            return false;
        }
    }
    public List<StatusModel> GetStatus()
    {
        List<StatusModel> statusModels=new ArrayList<>();
        SQLiteDatabase mydb=this.getReadableDatabase();
        Cursor cursor=mydb.rawQuery("SELECT S.StatusID,S.UserID,U.UserName,S.Status FROM "+STATUS_TABLE+" S  INNER JOIN "+USER_TABLE+" U ON S.UserID=U.UserID;",null);
        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                StatusModel smodel=new StatusModel(cursor.getInt(cursor.getColumnIndex("StatusID")),cursor.getInt(cursor.getColumnIndex("UserID")),
                        cursor.getString(cursor.getColumnIndex("UserName")),cursor.getString(cursor.getColumnIndex("Status")));
                statusModels.add(smodel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();
        return statusModels;
    }
    public void UpdateStatus(int StatusID,String Status)
    {
        SQLiteDatabase mydb=this.getWritableDatabase();
        mydb.execSQL("UPDATE "+STATUS_TABLE+" SET Status='"+Status+"' WHERE StatusID="+StatusID);
        mydb.close();
    }
    public void RemoveStatus(int StatusID)
    {
        SQLiteDatabase mydb=this.getWritableDatabase();
        mydb.execSQL("DELETE FROM "+STATUS_TABLE+" WHERE StatusID="+StatusID);
        mydb.close();
    }
}
