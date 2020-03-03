package com.grace.profitabletraderconsultant.InformationInput;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grace.profitabletraderconsultant.Navigation.ui.home.HomeFragment;
import com.grace.profitabletraderconsultant.R;


public class ProductInfo extends Fragment {

    private Spinner productInput;
    private EditText priceIput;
    private Button next;
    private String phones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View context = inflater.inflate(R.layout.fragment_product_info, container, false);

        productInput = context.findViewById(R.id.product);
        priceIput = context.findViewById(R.id.price);

        phones = getArguments().getString("Phone");
        final Bundle data = new Bundle();
        data.putString("Phone", phones);

        final HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(data);

        next = context.findViewById(R.id.nexte);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String product = productInput.getSelectedItem().toString();
                String price = priceIput.getText().toString();

                createProduct(product, price);
                getFragmentManager().beginTransaction().replace(R.id.viewPager,homeFragment).commit();
               /* Intent intent = new Intent(v.getContext(),homeFragment.getClass());
                intent.putExtras(data);
                startActivity(intent);

                */
            }
        });
        return context;
    }

    private void createProduct(String Product, String Price){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        Product product = new Product(Product, Price);
        databaseReference.push().setValue(product);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(phones).child("Product");
        Product product1 = new Product(Product, Price);
        reference.push().setValue(product1);

    }
}
