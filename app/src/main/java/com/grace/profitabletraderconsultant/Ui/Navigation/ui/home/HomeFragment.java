package com.grace.profitabletraderconsultant.Ui.Navigation.ui.home;

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
import com.google.firebase.database.ValueEventListener;
import com.grace.profitabletraderconsultant.Constants;
import com.grace.profitabletraderconsultant.Individual_Product;
import com.grace.profitabletraderconsultant.Models.ConstantsModel;
import com.grace.profitabletraderconsultant.Models.Product;
import com.grace.profitabletraderconsultant.R;
import com.grace.profitabletraderconsultant.RecyclerTouchListener;
import com.grace.profitabletraderconsultant.Ui.Navigation.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private MyAdapter mAdapter;
    private TextView nameBox;
    private TextView typeBox;
    private TextView countyBox;
    private TextView subCountyBox;
    private String CountyBox;
    private String Phone;
    private String NameBox;
    private String TypeBox;
    private String SubCountyBox;
    List<Product> productList = new ArrayList<>();
    Constants constants = new Constants();

    String TAG = getClass().getSimpleName();

    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        nameBox = root.findViewById(R.id.businessNameOutput);
        typeBox = root.findViewById(R.id.businessType);
        countyBox = root.findViewById(R.id.businessLocation);
        subCountyBox = root.findViewById(R.id.sub_countyLocation);
        LinearLayout business = root.findViewById(R.id.businessInfo);
        Toast.makeText(getActivity(), getPhone(), Toast.LENGTH_SHORT).show();
        create();

        //To edit business info
      /*  business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), Edit_Company_Info.class));
            }
        });*/

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
                bundle.putString("county", countyBox.getText().toString());
                bundle.putString("sub", subCountyBox.getText().toString());

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

        Phone = getPhone();

        nameBox.setText(constants.Name());
        typeBox.setText(constants.Type());
        countyBox.setText(constants.County());
        subCountyBox.setText(constants.SubCounty());
        FillRecycler();

     /*   DatabaseReference databaseReferenceCompany = FirebaseDatabase.getInstance().getReference("User").child(Phone).child("Company");
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

                    FillRecycler(CountyBox);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    private void FillRecycler(){
        constants.County();
        ConstantsModel constantsModel = new ConstantsModel();

        DatabaseReference databaseReferenceCounty = FirebaseDatabase.getInstance().getReference("User").child(getPhone()).child("Company");
        databaseReferenceCounty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameBox.setText(dataSnapshot.child("businessName").getValue(String.class));
                countyBox.setText(dataSnapshot.child("county").getValue(String.class));
                typeBox.setText(dataSnapshot.child("businessType").getValue(String.class));
                subCountyBox.setText(dataSnapshot.child("subCounty").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReferenceProduct = FirebaseDatabase.getInstance().getReference("User").child(getPhone()).child("Product");
        databaseReferenceProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Product product = new Product();
                    product.setProduct(snapshot.child("product").getValue(String.class));
                    product.setPrice(snapshot.child("price").getValue(String.class));
                    productList.add(product);
                    mAdapter.notifyDataSetChanged();
                    Log.i(TAG, product.getProduct());
                    Log.i(TAG, product.getPrice());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " +databaseError.getDetails() );
            }
        });
    }

    public String getPhone() {
        String Phone = null;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Phone = user.getPhoneNumber();
            Phone = Phone.replaceAll("\\D", "");
            Phone = Phone.replaceFirst("254", "");
            Phone = "0" + Phone;

            Log.i(TAG, Phone);
        }
        return Phone;
    }

}