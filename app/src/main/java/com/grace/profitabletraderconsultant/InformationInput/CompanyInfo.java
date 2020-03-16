package com.grace.profitabletraderconsultant.InformationInput;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grace.profitabletraderconsultant.R;
import com.grace.profitabletraderconsultant.for_fragments;

public class CompanyInfo extends Fragment implements for_fragments {

    private Button Save;
    private EditText businessNameInput;
    private Spinner businessTypeInput;
    private Spinner countyInput;
    private String Phone;

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
                String name = businessNameInput.getText().toString();
                String type = businessTypeInput.getSelectedItem().toString();
                String county = countyInput.getSelectedItem().toString();

                Bundle bundle = new Bundle();
                bundle.putString("County", county);
                ProductInfo productInfo = new ProductInfo();
                productInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                   fragmentTransaction.replace(R.id.viewPager, productInfo);
                   fragmentTransaction.commit();


               Company company = new Company(name, type, county);

               createBusiness(company);
            }
        });
        return context;
    }

    public void createBusiness(Company company) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Company");
        databaseReference.setValue(company);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Phone = user.getPhoneNumber();
            Phone = Phone.replaceAll("\\D", "");
            Phone = Phone.replaceFirst("254", "");
            Phone = "0" + Phone;

        }

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User").child(Phone).child("Company");
        databaseReference1.setValue(company);

    }

    }



