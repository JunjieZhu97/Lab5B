package com.example.loginsystemlab5partb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginsystemlab5partb.Adapter.PostAdapter;
import com.example.loginsystemlab5partb.DB.UserDB;
import com.example.loginsystemlab5partb.Model.StatusModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class UploadStatus extends AppCompatActivity {
    SharedPreferences pref;
    TextInputEditText tietPost;
    Button btnPost;
    RecyclerView rvPost;
    UserDB db=new UserDB(UploadStatus.this);
    int UserID;
    List<StatusModel> statusModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_status);
        getSupportActionBar().setTitle("What's on Your Mind ?");
        tietPost=findViewById(R.id.tietPost);
        btnPost=findViewById(R.id.btnPost);
        rvPost=findViewById(R.id.rvPost);
        pref=getSharedPreferences("MY_PREF",MODE_PRIVATE);
         UserID=pref.getInt("UserID",0);
       btnPost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(tietPost.getText().toString().isEmpty())
               {
                   Toast.makeText(UploadStatus.this, "Enter Post", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   if(db.InsertStatus(UserID,tietPost.getText().toString()))
                   {
                       Toast.makeText(UploadStatus.this, "Posted Successfully", Toast.LENGTH_SHORT).show();
                       tietPost.setText("");
                      onResume();

                   }
                   else
                   {
                       Toast.makeText(UploadStatus.this, "Post Fail", Toast.LENGTH_SHORT).show();
                   }
               }
           }
       });


    }

    @Override
    protected void onResume() {
        super.onResume();
        statusModelList=db.GetStatus();

        rvPost.setLayoutManager(new LinearLayoutManager(UploadStatus.this,LinearLayoutManager.VERTICAL,false));
        rvPost.setHasFixedSize(true);
        rvPost.setAdapter(new PostAdapter(statusModelList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            //calling dialog
            case R.id.Logout:
                AlertDialog.Builder alertdialog=new AlertDialog.Builder(UploadStatus.this);
                alertdialog.setMessage("Are You Sure You Want to Log Out ?");
                alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //removing preferences if user choose Yes
                        pref=getSharedPreferences("MY_PREF",MODE_PRIVATE);
                        SharedPreferences.Editor editor=pref.edit();
                        editor.clear();
                        editor.putBoolean("Login",false);
                        editor.apply();

                        //redirect back to Login
                        Intent intent=new Intent(UploadStatus.this,Login.class);
                        startActivity(intent);
                        Toast.makeText(UploadStatus.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    }
                });
                alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog=alertdialog.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
