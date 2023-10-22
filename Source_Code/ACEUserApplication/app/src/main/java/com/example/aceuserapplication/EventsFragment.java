package com.example.aceuserapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EventsFragment extends Fragment {


    RecyclerView oRecycler,pRecycler;
    EventsAdapter eventsAdapterO , eventsAdapterP;

    DatabaseReference reference;

    ArrayList<EventsData> eventOrgList;
    ArrayList<EventsData> eventPatList;

    String usn ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_events, container, false);
        oRecycler=view.findViewById(R.id.organise_event_recycler);
        pRecycler=view.findViewById(R.id.participate_events_recycler);

        reference= FirebaseDatabase.getInstance().getReference();

        Intent i = getActivity().getIntent();
        usn = i.getStringExtra("USN");


        getOrganiseImage();
        eventOrgList =new ArrayList<>();
        eventsAdapterO= new EventsAdapter(getContext(),eventOrgList);
        RecyclerView.LayoutManager layoutManagerO = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        oRecycler.setLayoutManager(layoutManagerO);
        oRecycler.setAdapter(eventsAdapterO);
        eventsAdapterO.setOnOrgClickListener(position -> putOrgProfile(position));


        getParticipateImage();
        eventPatList =new ArrayList<>();
        eventsAdapterP= new EventsAdapter(getContext(),eventPatList);
        RecyclerView.LayoutManager layoutManagerP = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        pRecycler.setLayoutManager(layoutManagerP);
        pRecycler.setAdapter(eventsAdapterP);
        eventsAdapterP.setOnOrgClickListener(position -> putPatProfile(position));



        return view;
    }

    private void putPatProfile(int position) {

        reference.child("Event Participate").child(eventPatList.get(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(usn))
                {
                    Toast.makeText(getActivity(), "You've already done participating", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    reference.child("Event Participate").child(eventPatList.get(position).getTitle()).child(usn).setValue("Participated");
                    reference.child("User").child(usn).child("Participated").child(eventPatList.get(position).getKey()).setValue(eventPatList.get(position).getTitle());
                    Toast.makeText(getActivity(), "Participated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void putOrgProfile(int position) {

        reference.child("Event Organised").child(eventOrgList.get(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(usn))
                {
                    Toast.makeText(getActivity(), "You've already registered for organising", Toast.LENGTH_SHORT).show();
                }
                else {
                    reference.child("Event Organised").child(eventOrgList.get(position).getTitle()).child(usn).setValue("Organising");
                    reference.child("User").child(usn).child("Organised").child(eventOrgList.get(position).getKey()).setValue(eventOrgList.get(position).getTitle());
                    Toast.makeText(getActivity(), "Organised", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getParticipateImage() {
        reference.child("events").child("Participate").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1:dataSnapshot.getChildren()){

                    EventsData data= snapshot1.getValue(EventsData.class);
                    eventPatList.add(data);


                }
                Collections.reverse(eventPatList);
                eventsAdapterP.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getOrganiseImage() {
        reference.child("events").child("Organise").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1:dataSnapshot.getChildren()){

                    EventsData data= snapshot1.getValue(EventsData.class);
                    eventOrgList.add(data);

                }
                Collections.reverse(eventOrgList);
                eventsAdapterO.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}