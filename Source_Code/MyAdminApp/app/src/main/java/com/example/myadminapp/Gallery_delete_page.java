package com.example.myadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gallery_delete_page extends AppCompatActivity {

    RecyclerView convoRecycler,otherRecycler;
    GalleryAdapter oadapter;
    GalleryAdapter cadapter;

    DatabaseReference reference;

    List<String> oimageList;
    List<String> oimageListKey;
    List<String> cimageList;
    List<String> cimageListKey;
    RecyclerView.LayoutManager olayoutManager;
    RecyclerView.LayoutManager clayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_delete_page);

        convoRecycler=findViewById(R.id.convoRecycler);
        otherRecycler=findViewById(R.id.otherRecycler);

        oimageList = new ArrayList<>();
        oimageListKey = new ArrayList<>();
        cimageList = new ArrayList<>();
        cimageListKey = new ArrayList<>();

        oadapter= new GalleryAdapter(Gallery_delete_page.this,oimageList);
        olayoutManager = new LinearLayoutManager(Gallery_delete_page.this, LinearLayoutManager.HORIZONTAL, false);
        otherRecycler.setLayoutManager(olayoutManager);
        otherRecycler.setAdapter(oadapter);


        cadapter= new GalleryAdapter(Gallery_delete_page.this,cimageList);
        clayoutManager = new LinearLayoutManager(Gallery_delete_page.this, LinearLayoutManager.HORIZONTAL, false);
        convoRecycler.setLayoutManager(clayoutManager);
        convoRecycler.setAdapter(cadapter);


        reference= FirebaseDatabase.getInstance().getReference().child("gallery");

        getConvoImage();
        getOtherImage();
    }

    private void getOtherImage() {
        reference.child("Other events").addValueEventListener(new ValueEventListener() { // Check the spelling and capitalization
            // Rest of the code.



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {
                    if(oimageList.size()>0) {
                        oimageList.clear();
                        oimageListKey.clear();
                    }
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                        String data = (String) snapshot1.getValue();
                        String dataKey = (String) snapshot1.getKey();
                        oimageList.add(data);
                        oimageListKey.add(dataKey);

                    }
                }
                Collections.reverse(oimageList);
                Collections.reverse(oimageListKey);
                oadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getConvoImage() {
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {

                    if(cimageList.size()>0) {
                        cimageList.clear();
                        cimageListKey.clear();
                    }
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                        String data = (String) snapshot1.getValue();
                        String dataKey = (String) snapshot1.getKey();
                        cimageList.add(data);
                        cimageListKey.add(dataKey);

                    }
                }
                Collections.reverse(cimageList);
                Collections.reverse(cimageListKey);
                cadapter.notifyDataSetChanged();

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
                deleteGalleryC(item.getGroupId());
                break;
            case 1:
                deleteGalleryO(item.getItemId());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteGalleryC(int position) {
        reference.child("Convocation").child(cimageListKey.get(position)).removeValue();
        cimageList.remove(position);
        cimageListKey.remove(position);
        cadapter.notifyItemRemoved(position);
    }

    private void deleteGalleryO(int position){
        reference.child("Other events").child(oimageListKey.get(position)).removeValue();
        oimageList.remove(position);
        oimageListKey.remove(position);
        oadapter.notifyItemRemoved(position);
    }
}