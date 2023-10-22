package com.example.myadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter classAdapter;
    double totalPrice;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<CartItem> cartItems = new ArrayList<>();
    TextView tP;
    Button buy;
    String name;
    long cid;
    long lastid;
    int positionClass;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        tP = findViewById(R.id.totalP);
        buy = findViewById(R.id.buyNowBtn);

        Intent i = getIntent();
        name = i.getStringExtra("className");
        positionClass = i.getIntExtra("position",-1);
        cid = i.getLongExtra("cid",-1);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(StudentActivity.this , Bill.class);
                //finish();
                //startActivity(i);
            }
        });





        loadData();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        classAdapter = new CartAdapter(this,cartItems);
        recyclerView.setAdapter(classAdapter);
        if(totalPrice == 0){
            buy.setEnabled(false);
        }
        classAdapter.setOnRemoveClickListener(position-> removeItem(position));
        classAdapter.setOnItemClickListner(position -> changestatus(position));

    }

    private void changestatus(int position) {

        String status = cartItems.get(position).getStatus();

        if(status.equals("P")) status = "A";
        else status = "P";

        databaseReference.child("Expense").child(String.valueOf(cid)).child(String.valueOf(cartItems.get(position).getIId())).child("status").setValue(status);
        cartItems.get(position).setStatus(status);
        classAdapter.notifyItemChanged(position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addStudent:showStudentDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showStudentDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.STUDENT_ADD_DIALOG);
        dialog.setListener((roll,name)-> addItem(roll , name));
    }

    private void addItem(String name , String price) {

        databaseReference.child("Expense").child(String.valueOf(cid)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(String.valueOf(cid))){
                    Toast.makeText(StudentActivity.this, "Not valid", Toast.LENGTH_SHORT).show();
                }else{
                    long id = lastid+1;
                    lastid = id;
                    CartItem cartItem = new CartItem(id,name,price,"A");
                    databaseReference.child("Expense").child(String.valueOf(cid)).child(String.valueOf(id)).setValue(cartItem);
                    cartItems.add(cartItem);
                    classAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void removeItem(int position) {

        totalPrice -= Float.parseFloat(cartItems.get(position).getPrice());
        databaseReference.child("Expense").child(String.valueOf(cid)).child(String.valueOf(position+1)).removeValue();
        tP.setText(String.valueOf(totalPrice));
        cartItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }

    private void loadData() {

        FirebaseDatabase.getInstance().getReference().child("Expense").child(String.valueOf(cid)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int getCOunt = (int) snapshot.getChildrenCount();
                if(getCOunt >0){
                    cartItems.clear();
                    totalPrice = 0;
                    int count = 0;
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CartItem classItems1 = dataSnapshot.getValue(CartItem.class);
                        lastid = classItems1.getIId();
                        cartItems.add(classItems1);
                        totalPrice += Float.parseFloat(classItems1.getPrice());
                        if(classItems1.getStatus().equals("P")){
                            count+=1;
                        }
                    }
                    tP.setText(String.valueOf(totalPrice));

                    if(count == snapshot.getChildrenCount()){
                        databaseReference.child("Expenses").child(String.valueOf(cid)).child("statusClass").setValue("P");
                    }
                    else {
                        databaseReference.child("Expenses").child(String.valueOf(cid)).child("statusClass").setValue("A");

                    }


                    classAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}