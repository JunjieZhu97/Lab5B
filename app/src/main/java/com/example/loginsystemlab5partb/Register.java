package com.example.loginsystemlab5partb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginsystemlab5partb.DB.UserDB;
import com.example.loginsystemlab5partb.Model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    TextInputEditText tietUserName,tietPassword,tietCPassword;
    Button btnRegister;
    UserDB db=new UserDB(Register.this);
    List<UserModel> userModelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tietUserName=findViewById(R.id.tietUserName);
        tietPassword=findViewById(R.id.tietPassword);
        tietCPassword=findViewById(R.id.tietCPassword);
        btnRegister=findViewById(R.id.btnRegister);
        getSupportActionBar().setTitle("Register");


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //data validation(blank)
                if(tietUserName.getText().toString().isEmpty() || tietPassword.getText().toString().isEmpty() || tietCPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(Register.this, "Please Fill All Information !!", Toast.LENGTH_SHORT).show();
                    if(tietUserName.getText().toString().isEmpty())
                        tietUserName.setError("Fill UserName");
                    if(tietPassword.getText().toString().isEmpty())
                    tietPassword.setError("Fill Password");
                    if(tietCPassword.getText().toString().isEmpty())
                    tietCPassword.setError("Confirm Password");
                }
                else

                    {
                        //data validation(Uniqueness)
                        userModelList=db.GetUserForValidate(tietUserName.getText().toString());
                        if(userModelList.size()>0)
                        {
                            Toast.makeText(Register.this, "Sorry, There is a User Already with this Name", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            //data validation(Password=ConfirmPassword)
                            String Pass=tietPassword.getText().toString();
                            String CPassword=tietCPassword.getText().toString();

                            if(!Pass.equals(CPassword))
                            {
                                Toast.makeText(Register.this, "Your Password Do Not Match!!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(db.InsertUser(tietUserName.getText().toString(),
                                        tietPassword.getText().toString()))
                                {
                                    Toast.makeText(Register.this, "Register Successfully !!", Toast.LENGTH_LONG).show();
                                    Intent i=new Intent(Register.this,UploadStatus.class);
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(Register.this, "Register Fail", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }


                }
            }
        });
    }
}
