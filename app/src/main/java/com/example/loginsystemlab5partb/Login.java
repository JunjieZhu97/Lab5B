package com.example.loginsystemlab5partb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginsystemlab5partb.DB.UserDB;
import com.example.loginsystemlab5partb.Model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    TextInputEditText tietUserName,tietPassword;
    Button btnLogin,btnRegister;
    UserDB userdb=new UserDB(Login.this);
    UserModel userModel;
    SharedPreferences pref;
    private int UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tietUserName=findViewById(R.id.tietUserName);
        tietPassword=findViewById(R.id.tietPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);

        //
        getSupportActionBar().setTitle("Login");
//preferences

        pref=getSharedPreferences("MY_PREF",MODE_PRIVATE);
        UserID=pref.getInt("UserID",0);
        if(UserID>0)
        {
            Intent i=new Intent(Login.this,UploadStatus.class);
            startActivity(i);
        }
        else
            {
            //register process
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Login.this, Register.class);
                    startActivity(i);
                }
            });


//login process
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userModel = userdb.GetUserLogin(tietUserName.getText().toString(),
                            tietPassword.getText().toString());
//user validation (blank)
                    if (tietUserName.getText().toString().isEmpty())
                        tietUserName.setError("Fill UserName !!");
                    if (tietPassword.getText().toString().isEmpty())
                        tietPassword.setError("Fill Password !!");
                    else {
                        //user validation(Exist Data or Not)
                        if (userModel == null) {
                            Toast.makeText(Login.this, "Sorry, There is no User with this Name and Password, Try Again !!", Toast.LENGTH_LONG).show();
                        } else
                            {
                            String name=tietUserName.getText().toString();
                            pref=getSharedPreferences("MY_PREF",MODE_PRIVATE);
                            SharedPreferences.Editor myeditor=pref.edit();
                            myeditor.putInt("UserID",userModel.getUserID());
                            myeditor.apply();
                                Toast.makeText(Login.this, "Welcome "+name+" !!!!!", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(Login.this,UploadStatus.class);
                                startActivity(i);
                        }
                    }
                }
            });

        }
    }
}
