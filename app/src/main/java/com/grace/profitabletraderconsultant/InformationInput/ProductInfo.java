package com.grace.profitabletraderconsultant.InformationInput;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grace.profitabletraderconsultant.Navigation.Navigation;
import com.grace.profitabletraderconsultant.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class ProductInfo extends Fragment {

    private FirebaseUser currentUser;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private Spinner productInput;
    private EditText priceIput;
    private Button next;
    private String product;
    private String price;
    private String userId;
    private String key;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View context = inflater.inflate(R.layout.fragment_product_info, container, false);


        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Product");

        productInput = context.findViewById(R.id.product);
        priceIput = context.findViewById(R.id.price);

        productInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next = context.findViewById(R.id.nexte);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Navigation.class);
                startActivity(intent);

                price = priceIput.getText().toString();

                createProduct(product, price, key);

            }
        });
        return context;
    }


    public void createProduct(String product, String price, String key){

        userId = mFirebaseDatabase.push().getKey();

        Company company = new Company(product, price, key);
        mFirebaseDatabase.child("Member/User/Company").child("Product").setValue(company);

        addProductChangeListener();

    }

    private void addProductChangeListener(){

        mFirebaseDatabase.child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Company company = dataSnapshot.getValue(Company.class);

                //Check if null
                if (company == null){
                    Log.e(TAG, "Product data is null");
                    return;
                }

                Log.e(TAG,"Product data is changed " + company.Product + ", " + company.Price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


                // Failed to read value
                Log.e(TAG, "Failed to read product information", databaseError.toException());
            }
        });
    }

}

















