package com.grace.profitabletraderconsultant;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Constants {
    String County;
    ConstantsModel constantsModel = new ConstantsModel();
    public static String getPhone() {
        String Phone = null;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Phone = user.getPhoneNumber();
            Phone = Phone.replaceAll("\\D", "");
            Phone = Phone.replaceFirst("254", "");
            Phone = "0" + Phone;
        }
        return Phone;
    }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(getPhone()).child("Company");

    public String Name(){
        final String[] Name = {"Name"};
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Name[0] = dataSnapshot.child("businessName").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return Name[0];
    }

    public String Type(){
        final String[] Type = {"Type"};
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Type[0] = dataSnapshot.child("businessType").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return Type[0];
    }

    public String County(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               County = dataSnapshot.child("county").getValue().toString();
               constantsModel.setCounty(County);
                Log.d("Constants Constants Constants",constantsModel.getCounty() );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("Constants Constants Constants",constantsModel.getCounty());
        return constantsModel.getCounty();
    }

    public String SubCounty(){
        final String[] SubCounty = {"SubCounty"};
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SubCounty[0] = dataSnapshot.child("subCounty").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return SubCounty[0];
    }
}

