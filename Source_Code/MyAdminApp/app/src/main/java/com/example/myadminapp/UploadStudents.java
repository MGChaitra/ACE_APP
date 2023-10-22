package com.example.myadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class UploadStudents extends AppCompatActivity {

    ImageView reg , org , part;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_students);
        reg = findViewById(R.id.regImg);
        org = findViewById(R.id.orgImg);
        part = findViewById(R.id.imageView3);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UploadStudents.this , Registered.class);
                startActivity(i);
            }
        });

        org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UploadStudents.this , Organising.class);
                startActivity(i);
            }
        });

        part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UploadStudents.this , Participating.class);
                startActivity(i);
            }
        });
    }
}