package com.grace.profitabletraderconsultant.InformationInput;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.grace.profitabletraderconsultant.R;

import java.util.Objects;

public class Input extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.viewPager, new CompanyInfo());
        fragmentTransaction.commit();

    }
}