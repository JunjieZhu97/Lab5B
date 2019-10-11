package com.example.loginsystemlab5partb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginsystemlab5partb.DB.UserDB;
import com.example.loginsystemlab5partb.Model.StatusModel;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateStatus extends AppCompatActivity {

    TextInputEditText tietUpdateStatus;
    Button btnUpdate;
    UserDB udb=new UserDB(UpdateStatus.this);
    StatusModel smodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        //typecast
        tietUpdateStatus=findViewById(R.id.tietUpdateStatus);
        btnUpdate=findViewById(R.id.btnUpdate);

        //getting old values
        smodel=getIntent().getParcelableExtra("OldValue");
        tietUpdateStatus.setText(smodel.getStatus());

        //update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                udb.UpdateStatus(smodel.getStatusID(),tietUpdateStatus.getText().toString());
                Toast.makeText(UpdateStatus.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
