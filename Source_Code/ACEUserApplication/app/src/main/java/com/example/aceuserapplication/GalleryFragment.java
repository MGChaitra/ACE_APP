package com.example.aceuserapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GalleryFragment extends Fragment {


    RecyclerView convoRecycler,otherRecycler;
    GalleryAdapter adapter;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);
        convoRecycler=view.findViewById(R.id.convoRecycler);
        otherRecycler=view.findViewById(R.id.otherRecycler);

        reference= FirebaseDatabase.getInstance().getReference().child("gallery");

        getConvoImage();
        getOtherImage();

        return view;
    }

    private void getOtherImage() {
        reference.child("Other events").addValueEventListener(new ValueEventListener() { // Check the spelling and capitalization
            // Rest of the code.

            List<String> imageList=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1:dataSnapshot.getChildren()){

                    String data=(String) snapshot1.getValue();
                    imageList.add(data);

                }
                Collections.reverse(imageList);
                adapter= new GalleryAdapter(getContext(),imageList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                otherRecycler.setLayoutManager(layoutManager);
                otherRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getConvoImage() {
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1:dataSnapshot.getChildren()){

                    String data=(String) snapshot1.getValue();
                    imageList.add(data);

                }
                Collections.reverse(imageList);
                adapter= new GalleryAdapter(getContext(),imageList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                convoRecycler.setLayoutManager(layoutManager);
                convoRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}