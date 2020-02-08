package com.grace.profitabletraderconsultant.InformationInput;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grace.profitabletraderconsultant.Login.verification;
import com.grace.profitabletraderconsultant.R;
import com.grace.profitabletraderconsultant.User;
import com.grace.profitabletraderconsultant.for_fragments;
import com.grace.profitabletraderconsultant.for_fragments.forFrag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class CompanyInfo extends Fragment implements for_fragments {



    private FirebaseUser currentUser;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private Activity contexts;
    List<Company> companies;
    private Button Save;
    private EditText businessNameInput;
    private Spinner businessTypeInput;
    private Spinner countyInput;
    private String type;
    private String county;
    private String name;
    private String userId;
    public String user;
    public  String nameOutput;
    public String typeOutput;
    public String countyOutput;
    /*public CompanyInfo(Activity contexts, List<Company> companies) {
        this.contexts = contexts;
        this.companies = companies;
    }

     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View context =  inflater.inflate(R.layout.fragment_company_info, container, false);

        businessNameInput = context.findViewById(R.id.businessName);
        businessTypeInput = context.findViewById(R.id.businessType);
        countyInput = context.findViewById(R.id.county);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user = String.valueOf(currentUser);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Members");

        businessTypeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countyInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                county = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Save = context.findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               fragmentTransaction.replace(R.id.viewPager, new ProductInfo());
               fragmentTransaction.commit();

               name  = businessNameInput.getText().toString();

               createBusiness(name, type, county);
            }
        });
        return context;
    }



    public void createBusiness(String name, String type, String county) {

       // userId = mFirebaseDatabase.push().getKey();
        Company company = new Company(name, type, county);
       // Map<String, Object> childUpdates = new HashMap<>();

      //  mFirebaseDatabase.child("Company").setValue(childUpdates);

        //childUpdates.put("Member/User/Company" + "/", company);
        // childUpdates.put("/Product/" + userId + "/" + userId, CompanyValues);

        mFirebaseDatabase.child("Company").child("/").setValue(company);
       // mFirebaseDatabase.updateChildren(company);


       /* addCompanyChangeListener(new OnCompanyDataReceived() {
            @Override
            public void onCompanyDataReceived(String nameOutput, String typeOutput, String countyOutput) {
                nameOutput = getResources().getString(R.string.name);
            }
        });
    }
    public interface OnCompanyDataReceived{
        void onCompanyDataReceived(String nameOutput, String typeOutput, String countyOutput);
    }

        */
       addCompanyChangeListener();
    }

    private void addCompanyChangeListener(){
        mFirebaseDatabase.child("Members/Company").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Company company = dataSnapshot.getValue(Company.class);
                Company company1 = new Company();
                company1.setBusinessName(dataSnapshot.child("businessName").getValue(String.class));
                company1.setBusinessType(dataSnapshot.child("businessType").getValue(String.class));
                company1.setCounty(dataSnapshot.child("county").getValue(String.class));
               // callback.onCompanyDataReceived(nameOutput, typeOutput, countyOutput);
                //Check if null
                if (company == null){
                    Log.e(TAG, "Company data is null");
                    return;
                }

                Log.e(TAG,"Company data is changed " + company.getBusinessName() + ", " + company.getBusinessType() + ", " + company.getCounty());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


                // Failed to read value
                Log.e(TAG, "Failed to read company data", databaseError.toException());
            }
        });
    }}



