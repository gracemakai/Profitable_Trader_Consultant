package com.grace.profitabletraderconsultant.InformationInput;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grace.profitabletraderconsultant.R;
import com.grace.profitabletraderconsultant.for_fragments;

import java.util.List;


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
    private String userId;
    public String user;
    public  String nameOutput;
    public String typeOutput;
    public String countyOutput;
    public Company company;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View context =  inflater.inflate(R.layout.fragment_company_info, container, false);
        //Initializing
        businessNameInput = context.findViewById(R.id.businessName);
        businessTypeInput = context.findViewById(R.id.businessType);
        countyInput = context.findViewById(R.id.county);

        Save = context.findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               fragmentTransaction.replace(R.id.viewPager, new ProductInfo());
               fragmentTransaction.commit();

               String name = businessNameInput.getText().toString();
               String type = businessTypeInput.getSelectedItem().toString();
               String county = countyInput.getSelectedItem().toString();
               Company company = new Company(name, type, county);

                createBusiness(company);

              /* currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = null;
                if (currentUser != null) {
                    uid = currentUser.getUid();
                }
                mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Company").child(uid);

               DatabaseReference databaseReference = mFirebaseDatabase.push();
               String pushId = databaseReference.getKey();
               company.setPushID(pushId);
               databaseReference.setValue(company);

               Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

               name  = businessNameInput.getText().toString();

               //createBusiness(name, type, county);*/
            }
        });
        return context;
    }

    public void createBusiness(Company company) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Company");
        databaseReference.setValue(company);


    }

    }



