package com.example.myadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Participating extends AppCompatActivity {


    DatabaseReference database;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    USNAdapter usnAdapter;
    List<String> items ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participating);
        database = FirebaseDatabase.getInstance().getReference();

        items = new ArrayList<>();

        recyclerView = findViewById(R.id.student_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        usnAdapter = new USNAdapter(this,items);
        recyclerView.setAdapter(usnAdapter);
        usnAdapter.setOnItemClickListener(position -> gotoItem(position));
        loadData();

    }

    private void gotoItem(int position) {
        Intent i = new Intent(Participating.this, PartList.class);
        i.putExtra("title",items.get(position));
        startActivity(i);
    }

    private void loadData() {
        database.child("Event Participate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    String data = (String) snapshot1.getKey();
                    items.add(data);
                    usnAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}