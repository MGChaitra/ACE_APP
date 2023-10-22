package com.example.myadminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    CardView notice;
    CardView image;
    CardView event;
    CardView expenses;
    CardView student;
    CardView student22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notice = findViewById(R.id.addNotice);
        event = findViewById(R.id.addEvent);
        image = findViewById(R.id.addGallaryImage);
        expenses = findViewById(R.id.expenses);
        student = findViewById(R.id.student_activity);
        student22 = findViewById(R.id.addGallaryImage222);

        notice.setOnClickListener(this);
        image.setOnClickListener(this);
        event.setOnClickListener(this);
        expenses.setOnClickListener(this);
        student.setOnClickListener(this);
        student22.setOnClickListener(this);
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId() == R.id.addNotice)
        {
            intent = new Intent(MainActivity.this, UploadNotice.class);
            startActivity(intent);
        }

        else if(view.getId() == R.id.addGallaryImage)
        {
            intent = new Intent(MainActivity.this, UploadImages.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.addEvent)
        {
            intent = new Intent(MainActivity.this, UploadEvents.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.expenses)
        {
            intent = new Intent(MainActivity.this, Expenses.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.student_activity)
        {
            intent = new Intent(MainActivity.this, UploadStudents.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.addGallaryImage222)
        {
            intent = new Intent(MainActivity.this, GalleryHere.class);
            startActivity(intent);
        }
    }
}