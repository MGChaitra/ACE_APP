package com.example.myadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Notice_delete_page extends AppCompatActivity {

    private RecyclerView addNoticeRecycler;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_delete_page);

        addNoticeRecycler=findViewById(R.id.addNoticeRecycler);
        progressBar=findViewById(R.id.progressBar);

        reference= FirebaseDatabase.getInstance().getReference().child("Notice");

        list=new ArrayList<>();
        adapter=new NoticeAdapter(addNoticeRecycler.getContext(), list);

        addNoticeRecycler.setLayoutManager(new LinearLayoutManager(this));
        addNoticeRecycler.setHasFixedSize(true);

        addNoticeRecycler.setAdapter(adapter);



        getNotice();


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteNotice(item.getGroupId());
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

    private void updateTheClass(int position, String subjectName) {
        String key = list.get(position).getKey();
        reference.child(key).child("title").setValue(subjectName);
        list.get(position).setTitle(subjectName);
        adapter.notifyItemChanged(position);

    }

    private void deleteNotice(int position) {

        String key = list.get(position).getKey();
        reference.child(key).removeValue();
        list.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void getNotice() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()>0) {

                    list.clear();

                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        NoticeData data = snapshot1.getValue(NoticeData.class);
                        list.add(data);
                    }
                }
                Collections.reverse(list);

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Notice_delete_page.this,databaseError.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }
}