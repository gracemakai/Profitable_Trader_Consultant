package com.grace.profitabletraderconsultant.InformationInput;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grace.profitabletraderconsultant.Navigation.Navigation;
import com.grace.profitabletraderconsultant.R;

public class ProductInfo extends Fragment {

    private Spinner productInput;
    private EditText priceIput;
    private Button next;
    private String Phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View context = inflater.inflate(R.layout.fragment_product_info, container, false);

        productInput = context.findViewById(R.id.product);
        priceIput = context.findViewById(R.id.price);
        next = context.findViewById(R.id.nexte);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String product = productInput.getSelectedItem().toString();
                String price = priceIput.getText().toString();
                createProduct(product, price);
                Intent intent = new Intent(v.getContext(), Navigation.class);
                startActivity(intent);
            }
        });
        return context;
    }

    private void createProduct(final String Product, final String Price){

        Bundle bundle = getArguments();

        //Saving according to it's county
        String county = bundle.getString("County");
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Products").child(county);
        Product product2 = new Product(Product, Price);
        databaseReference1.push().setValue(product2);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        Product product = new Product(Product, Price);
        databaseReference.push().setValue(product);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Phone = user.getPhoneNumber();
            Phone = Phone.replaceAll("\\D", "");
            Phone = Phone.replaceFirst("254", "");
            Phone = "0" + Phone;

        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(Phone).child("Product");
        Product product1 = new Product(Product, Price);
        reference.push().setValue(product1);

    }
}
