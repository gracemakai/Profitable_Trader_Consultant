package com.grace.profitabletraderconsultant.Navigation.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grace.profitabletraderconsultant.InformationInput.Company;
import com.grace.profitabletraderconsultant.InformationInput.CompanyAdapter;
import com.grace.profitabletraderconsultant.InformationInput.CompanyInfo;
import com.grace.profitabletraderconsultant.Navigation.MyAdapter;
import com.grace.profitabletraderconsultant.R;
import com.grace.profitabletraderconsultant.for_fragments;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements for_fragments {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private HomeViewModel homeViewModel;
    private TextView nameBox;
    private TextView typeBox;
    private TextView countyBox;
    private DatabaseReference databaseReference;
    CompanyAdapter companyAdapter;
    List <Company> companies;
    int position;


    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //gettting a reference of user node
        databaseReference = FirebaseDatabase.getInstance().getReference("Member/Phone/Company");

        //recyclerView
        recyclerView = root.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        nameBox = root.findViewById(R.id.businessNameOutput);
        typeBox = root.findViewById(R.id.businessType);
        countyBox = root.findViewById(R.id.businessLocation);

       /* Company company = new Company() ;
        nameBox.setText(String.valueOf(company.getBusinessName()));
        typeBox.setText(String.valueOf(company.getBusinessType()));
        countyBox.setText(String.valueOf(company.getCounty()));

        */
        Company company = new Company() ;
        nameBox.setText(String.valueOf(company.getBusinessName()));
        typeBox.setText(String.valueOf(company.getBusinessType()));
        countyBox.setText(String.valueOf(company.getCounty()));

        final List<String> input = new ArrayList<>();

        for (int i = 0; i < 100; i++){
            input.add("Test" + 1);

            mAdapter = new MyAdapter(input);
            recyclerView.setAdapter(mAdapter);
        }

        ItemTouchHelper.SimpleCallback simpleItemTouchCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                input.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //final TextView textView = root.findViewById(R.id.businessName);

       /* homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        */

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                companies = new ArrayList<>();
                for (DataSnapshot companySnapshot: dataSnapshot.getChildren()){
                    Company company = companySnapshot.getValue(Company.class);
                    companies.add(company);
                }
                companyAdapter = new CompanyAdapter(getActivity(), companies);
                recyclerView.setAdapter(companyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}