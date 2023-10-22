package com.example.aceuserapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView r;
    DatabaseReference databaseReference;
    EditText usnE;
    EditText passE;
    Button butL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r = findViewById(R.id.reg);
        usnE = findViewById(R.id.editUSNSlogin);
        passE = findViewById(R.id.editPasswordSlogin);
        butL = findViewById(R.id.butL);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ace-app-7ffd8-default-rtdb.firebaseio.com/");

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , SignupUser.class);
                startActivity(i);
            }
        });

        butL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = usnE.getText().toString();
                String p = passE.getText().toString();
                if(u.length()==0 && p.length()==0)
                {
                    Toast.makeText(MainActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if(u.length()<10)
                {
                    Toast.makeText(MainActivity.this, "USN not valid", Toast.LENGTH_SHORT).show();
                }
                else if(u.length()>10 )
                {
                    Toast.makeText(MainActivity.this, "USN length should be 10", Toast.LENGTH_SHORT).show();
                }
                else if(p.length()<4)
                {
                    Toast.makeText(MainActivity.this, "Password not accepted", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(u)){
                                final String pass = snapshot.child(u).child("Password").getValue(String.class);
                                if(pass.equals(p))
                                {
                                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    try {
                                        Intent i = new Intent(MainActivity.this, Homepage.class);
                                        i.putExtra("USN",u);
                                        startActivity(i);
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        Toast.makeText(MainActivity.this, "Cannot open", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                    });
                }
            }
        });
    }
}