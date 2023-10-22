package com.example.aceuserapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupUser extends AppCompatActivity {

    EditText usn;
    EditText p;
    Button bS;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);



        usn = findViewById(R.id.editUSNSign);
        p = findViewById(R.id.editPasswordSign);
        bS = findViewById(R.id.butS);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ace-app-7ffd8-default-rtdb.firebaseio.com/");


        bS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = usn.getText().toString();
                String pass = p.getText().toString();
                if(u.length()==0 && pass.length()==0)
                {
                    Toast.makeText(SignupUser.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if(u.length()<10 )
                {
                    Toast.makeText(SignupUser.this, "USN length should be 10", Toast.LENGTH_SHORT).show();
                }
                else if(u.length()>10 )
                {
                    Toast.makeText(SignupUser.this, "USN length should be 10", Toast.LENGTH_SHORT).show();
                }
                else if(pass.length()<4)
                {
                    Toast.makeText(SignupUser.this, "Password min length is 4.", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(u)){
                                Toast.makeText(SignupUser.this, "USN exists", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("User").child(u).child("Password").setValue(pass);
                                Toast.makeText(SignupUser.this, "Registration done", Toast.LENGTH_SHORT).show();
                                finish();
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