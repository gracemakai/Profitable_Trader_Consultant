package com.grace.profitabletraderconsultant.Navigation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.grace.profitabletraderconsultant.Individual_Product;
import com.grace.profitabletraderconsultant.InformationInput.Product;
import com.grace.profitabletraderconsultant.Navigation.MyAdapter;
import com.grace.profitabletraderconsultant.R;
import com.grace.profitabletraderconsultant.RecyclerTouchListener;
import com.grace.profitabletraderconsultant.for_fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements for_fragments {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private TextView nameBox;
    private TextView typeBox;
    private TextView countyBox;
    private String Phone;
    List<Product> productList = new ArrayList<>();

    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        //recyclerView
        recyclerView = root.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mAdapter = new MyAdapter(productList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = productList.get(position);
                Toast.makeText(getContext(), product.getProduct() + " is selected!", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("product", product.getProduct());
                bundle.putString("price", product.getPrice());
                Intent intent = new Intent(view.getContext(), Individual_Product.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        nameBox = root.findViewById(R.id.businessNameOutput);
        typeBox = root.findViewById(R.id.businessType);
        countyBox = root.findViewById(R.id.businessLocation);

        Bundle arguments = getArguments();
       // String phones = arguments.getString("Phone");
        create();

        //Bundle bundle = getIntent().getExtras();
//        String phones = getArguments().getString("Phone");
  //      nameBox.setText(phones);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                productList.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }

    public void create() {
        Phone = "0719700918";
        DatabaseReference databaseReferenceCompany = FirebaseDatabase.getInstance().getReference("User").child(Phone).child("Company");
        databaseReferenceCompany.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String NameBox = (String) dataSnapshot.child("businessName").getValue();
                    nameBox.setText(NameBox);
                    String TypeBox = (String) dataSnapshot.child("businessType").getValue();
                    typeBox.setText(TypeBox);
                    String CountyBox = (String) dataSnapshot.child("county").getValue();
                    countyBox.setText(CountyBox);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference databaseReferenceProduct = FirebaseDatabase.getInstance().getReference("User").child(Phone).child("Product");
        databaseReferenceProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Product>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Product>>() {};
                Map<String, Product> hashMap = dataSnapshot.getValue(genericTypeIndicator);
                for (Map.Entry<String, Product> entry : hashMap.entrySet()) {
                    Product product = entry.getValue();
                    productList.add(product);
                    mAdapter.notifyDataSetChanged();
                    Log.i(TAG, product.getProduct());
                    Log.i(TAG, product.getPrice());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}