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
import com.grace.profitabletraderconsultant.Models.ConstantsModel;

public class Constants {
    String SubCounty;
    String County;
    String Name;
    String Type;
    ConstantsModel constantsModel = new ConstantsModel();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(getPhone()).child("Company");

    public Constants() {
    }

    public String getPhone() {
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

    public String Name(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Name = dataSnapshot.child("businessName").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return Name;
    }

    public String Type(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Type = dataSnapshot.child("businessType").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return Type;
    }

    public String County(){

        DatabaseReference databaseReferenceCounty = FirebaseDatabase.getInstance().getReference("User").child(getPhone()).child("Company");
        databaseReferenceCounty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               constantsModel.setCounty(dataSnapshot.child("county").getValue(String.class));
               Log.d("Constants Constants Constants comstants",  constantsModel.getCounty() );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return constantsModel.getCounty();
    }

    public String SubCounty(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SubCounty = dataSnapshot.child("subCounty").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return SubCounty;
    }
}

