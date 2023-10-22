package com.example.myadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Expenses extends AppCompatActivity{



    ImageView fab;
    RecyclerView recyclerView;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItems> classItems = new ArrayList<>();
    EditText class_edit;
    EditText subject_edit;
    long lastid;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        database = FirebaseDatabase.getInstance().getReference();
        fab = findViewById(R.id.plus);
        fab.setOnClickListener(v -> showDialog());

        recyclerView = findViewById(R.id.expenses_rc);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        classAdapter = new ClassAdapter(this,classItems);
        recyclerView.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));
        loadData();

    }

    private void loadData() {

        FirebaseDatabase.getInstance().getReference().child("Expenses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int getCOunt = (int) snapshot.getChildrenCount();
                //Toast.makeText(Expenses.this, "Inside Expenses", Toast.LENGTH_SHORT).show();
                if(getCOunt >0){
                    classItems.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ClassItems classItems1 = dataSnapshot.getValue(ClassItems.class);
                        lastid = classItems1.getId();
                        classItems.add(classItems1);
                    }

                    classAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
    }

    private void gotoItemActivity(int position) {
        Intent i = new Intent(this , StudentActivity.class);

        i.putExtra("className",classItems.get(position).getName());
        i.putExtra("position" , position);
        i.putExtra("cid" , classItems.get(position).getId());
        startActivity(i);
    }

    private void showDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((className , subjectName)->{
            addClass(subjectName);
        });


    }

    private void addClass(String className ) {
        database.child("Expenses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                long cid = lastid+1;
                Toast.makeText(Expenses.this, "cid = "+cid, Toast.LENGTH_SHORT).show();
                ClassItems classItem = new ClassItems(String.valueOf(cid),className ,"A");
                database.child("Expenses").child(String.valueOf(cid)).setValue(classItem);
                classItems.add(classItem);
                classAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager() , MyDialog.CLASS_UPDATE_DIALOG);
        dialog.setListener((className , subjectName)->{
            updateTheClass(position ,subjectName);
        });
    }

    private void updateTheClass(int position1 , String subjectName)
    {
        database.child("Expenses").child(String.valueOf(classItems.get(position1).getId())).child("name").setValue(subjectName);
        classItems.get(position1).setName(subjectName);
        classAdapter.notifyItemChanged(position1);

    }
    private void deleteClass(int position) {

        database.child("Expenses").child(String.valueOf(position+1)).removeValue();
        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }

}

