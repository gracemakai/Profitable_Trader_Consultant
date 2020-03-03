package com.grace.profitabletraderconsultant.InformationInput;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.grace.profitabletraderconsultant.R;

public class Input extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Bundle bundle = getIntent().getExtras();
        String phone = bundle.getString("Phone");

        CompanyInfo fragment = new CompanyInfo();
        Bundle arguments = new Bundle();
        arguments.putString("Phone", phone);
        fragment.setArguments(arguments);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.viewPager, fragment);
        fragmentTransaction.commit();

    }
}