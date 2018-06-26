package com.example.ashwingiri.myreferralapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText etName,etEmail,etReferral;
    Button btSubmit;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etReferral=findViewById(R.id.etReferral);
        btSubmit=findViewById(R.id.btSubmit);

        databaseReference= FirebaseDatabase.getInstance().getReference("User Database");

        btSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addUser();
                    }
                }
        );
    }

    private void addUser(){

        final int[] balance = {100};

        if(!etReferral.getText().toString().isEmpty()){

            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("hhmmss");
            final String referral=etName.getText().toString()+sdf.format(date);
            final boolean[] f = {true};


                databaseReference.child(etReferral.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final User user=dataSnapshot.getValue(User.class);

                        databaseReference.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.hasChild(etReferral.getText().toString())){
                                            if(!etName.getText().toString().isEmpty() && !etEmail.getText().toString().isEmpty() && f[0] ) {

                                                balance[0] +=100;

                                                databaseReference.child(referral).setValue(
                                                        new User(
                                                                etName.getText().toString(),
                                                                etEmail.getText().toString(),
                                                                referral,
                                                                balance[0],
                                                                new ArrayList<String>()
                                                        )
                                                );

                                                Toast.makeText(
                                                        MainActivity.this,
                                                        "Congrats! New User Added",
                                                        Toast.LENGTH_LONG
                                                ).show();

                                                ArrayList<String> temp_arrlist;
                                                if (user.getReferred_user() != null){
                                                    temp_arrlist = user.getReferred_user();
                                                }else{
                                                    temp_arrlist=new ArrayList<>();
                                                }

                                                temp_arrlist.add(referral);
                                                databaseReference.child(etReferral.getText().toString()).setValue(
                                                        new User(
                                                                user.getName(),
                                                                user.getEmail(),
                                                                user.getReferral_code(),
                                                                user.getBalance() + 100,
                                                                temp_arrlist
                                                        )
                                                );
                                                f[0] =false;
                                            }else{
                                                if(etName.getText().toString().isEmpty()){
                                                    Toast.makeText(MainActivity.this,"You should enter a name!",Toast.LENGTH_LONG).show();
                                                } else if(etEmail.getText().toString().isEmpty()){
                                                    Toast.makeText(MainActivity.this,"You should enter Email Id!",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }else{
                                            Toast.makeText(MainActivity.this, "Enter correct referral Key!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        }else{
            if(!etName.getText().toString().isEmpty() && !etEmail.getText().toString().isEmpty()){

                Date date=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("hhmmss");
                String referral=etName.getText().toString()+sdf.format(date);
                databaseReference.child(referral).setValue(
                        new User(
                                etName.getText().toString(),
                                etEmail.getText().toString(),
                                referral,
                                balance[0],
                                new ArrayList<String>()
                        )
                );

                Toast.makeText(
                        MainActivity.this,
                        "Congrats! New User Added",
                        Toast.LENGTH_LONG
                ).show();

            }else{
                if(etName.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"You should enter a name!",Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(MainActivity.this,"You should enter Email Id!",Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}
