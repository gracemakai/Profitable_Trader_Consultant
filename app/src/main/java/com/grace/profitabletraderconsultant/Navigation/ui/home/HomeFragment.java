package com.grace.profitabletraderconsultant.Navigation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.grace.profitabletraderconsultant.Individual_Product;
import com.grace.profitabletraderconsultant.InformationInput.Product;
import com.grace.profitabletraderconsultant.Navigation.MyAdapter;
import com.grace.profitabletraderconsultant.Navigation.ui.Edit_Company_Info;
import com.grace.profitabletraderconsultant.R;
import com.grace.profitabletraderconsultant.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private TextView nameBox;
    private TextView typeBox;
    private TextView countyBox;
    private TextView subCountyBox;
    private String CountyBox;
    private String Phone;
    private String NameBox;
    private String TypeBox;
    private String SubCounty;
    List<Product> productList = new ArrayList<>();

    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        nameBox = root.findViewById(R.id.businessNameOutput);
        typeBox = root.findViewById(R.id.businessType);
        countyBox = root.findViewById(R.id.businessLocation);
        subCountyBox = root.findViewById(R.id.sub_countyLocation);
        LinearLayout business = root.findViewById(R.id.businessInfo);

        create();

        //To edit business info
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), Edit_Company_Info.class));
            }
        });

        //recyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recycler_View);
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
                bundle.putString("county", CountyBox);
                bundle.putString("sub", SubCounty);
                Intent intent = new Intent(view.getContext(), Individual_Product.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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

    private void create() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Phone = user.getPhoneNumber();
            Phone = Phone.replaceAll("\\D", "");
            Phone = Phone.replaceFirst("254", "");
            Phone = "0" + Phone;
        }
        DatabaseReference databaseReferenceCompany = FirebaseDatabase.getInstance().getReference("User").child(Phone).child("Company");
        databaseReferenceCompany.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    NameBox = (String) dataSnapshot.child("businessName").getValue();
                    nameBox.setText(NameBox);
                    TypeBox = (String) dataSnapshot.child("businessType").getValue();
                    typeBox.setText(TypeBox);
                    CountyBox = (String) dataSnapshot.child("county").getValue();
                    countyBox.setText(CountyBox);
                    SubCounty = (String) dataSnapshot.child("subCounty").getValue();
                    subCountyBox.setText(SubCounty);
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