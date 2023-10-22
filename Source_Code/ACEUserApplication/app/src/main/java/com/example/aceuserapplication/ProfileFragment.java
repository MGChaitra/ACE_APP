package com.example.aceuserapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    RecyclerView oRecycler,pRecycler;
    ProfileAdapter adapter;

    DatabaseReference reference;

    ArrayList<EventsData> eventOrgList;
    ArrayList<EventsData> eventPatList;

    String usn ;
    TextView u ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        oRecycler=view.findViewById(R.id.organise_event_recycler_Profile);
        pRecycler=view.findViewById(R.id.participate_events_recycler_Profile);
        u = view.findViewById(R.id.name_cus_tv);
        


        Intent i = getActivity().getIntent();
        usn = i.getStringExtra("USN");

        reference= FirebaseDatabase.getInstance().getReference().child("User").child(usn);
        
        u.setText(usn);

        getOrganiseImageP();
        getParticipateImageP();

        return view;
    }

    private void getParticipateImageP() {
        reference.child("Participated").addValueEventListener(new ValueEventListener() { // Check the spelling and capitalization
            // Rest of the code.

            List<String> imageList=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1:dataSnapshot.getChildren()){

                    String data=(String) snapshot1.getValue();
                    imageList.add(data);

                }
                Collections.reverse(imageList);
                adapter= new ProfileAdapter(getContext(),imageList);
                pRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                pRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOrganiseImageP() {
        reference.child("Organised").addValueEventListener(new ValueEventListener() { // Check the spelling and capitalization
            // Rest of the code.

            List<String> imageList=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1:dataSnapshot.getChildren()){

                    String data=(String) snapshot1.getValue();
                    imageList.add(data);

                }
                Collections.reverse(imageList);
                adapter= new ProfileAdapter(getContext(),imageList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                oRecycler.setLayoutManager(layoutManager);
                oRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        
        if(item.getItemId() == 0)
            deletePart();
        
        return super.onContextItemSelected(item);
    }

    private void deletePart() {
    }
}