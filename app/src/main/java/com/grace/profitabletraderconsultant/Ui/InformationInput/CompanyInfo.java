package com.grace.profitabletraderconsultant.Ui.InformationInput;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.grace.profitabletraderconsultant.Models.Company;
import com.grace.profitabletraderconsultant.R;

public class CompanyInfo extends Fragment {

    private Button Save;
    private EditText businessNameInput;
    private Spinner businessTypeInput;
    private Spinner countyInput, subCountyInput;
    private String Phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View context =  inflater.inflate(R.layout.fragment_company_info, container, false);
        //Initializing
        businessNameInput = context.findViewById(R.id.businessName);
        businessTypeInput = context.findViewById(R.id.businessType);
        countyInput = context.findViewById(R.id.county);
        subCountyInput = context.findViewById(R.id.sub_county);

        countyInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCounty = parent.getItemAtPosition(position).toString();
                getCounty(selectedCounty);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Phone = user.getPhoneNumber();
            Phone = Phone.replaceAll("\\D", "");
            Phone = Phone.replaceFirst("254", "");
            Phone = "0" + Phone;

        }

        Save = context.findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = businessNameInput.getText().toString();
                String type = businessTypeInput.getSelectedItem().toString();
                String county = countyInput.getSelectedItem().toString();
                String subCounty = subCountyInput.getSelectedItem().toString();

                Bundle bundle = new Bundle();
                bundle.putString("County", county);
                ProductInfo productInfo = new ProductInfo();
                productInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                   fragmentTransaction.replace(R.id.viewPager, productInfo);
                   fragmentTransaction.commit();

               Company company = new Company(name, type, county, subCounty,Phone);
               createBusiness(company);
            }
        });
        return context;
    }

    public void createBusiness(Company company) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Company");
        databaseReference.setValue(company);

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User").child(Phone).child("Company");
        databaseReference1.setValue(company);

    }

    public void getCounty(String County){
        switch (County) {
            case "Baringo":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Baringo, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Bomet":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Bomet, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Bungoma":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Bungoma, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Busia":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Busia, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Elgeyo Marakwet":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Elgeyo_Marakwet, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Embu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Embu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Garissa":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Garissa, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Homa Bay":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Homa_Bay, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Isiolo":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Isiolo, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kajiado":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kajiado, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kakamega":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kakamega, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kericho":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kericho, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kiambu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kiambu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kilifi":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kilifi, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kirinyaga":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kirinyaga, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kisii":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kisii, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kisumu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kisumu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kitui":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kitui, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kwale":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Kwale, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Laikipia":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Laikipia, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Lamu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Lamu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Machakos":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Machakos, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Makueni":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Makueni, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Mandera":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Mandera, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Meru":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Meru, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Migori":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Migori, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Marsabit":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Marsabit, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Mombasa":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Mombasa, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Muranga":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Muranga, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nairobi":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Nairobi, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nakuru":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Nakuru, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nandi":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Nandi, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Narok":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Narok, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nyamira":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Nyamira, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nyandarua":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Nyandarua, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nyeri":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Nyeri, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Samburu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Samburu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Siaya":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Siaya, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Taita Taveta":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Taita_Taveta, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Tana River":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Tana_River, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Tharaka Nithi":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Tharaka_Nithi, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Trans Nzoia":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Trans_Nzoia, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Turkana":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Turkana, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Uasin Gishu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Uasin_Gishu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Vihiga":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Vihiga, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Wajir":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.Wajir, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "West Pokot":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.West_Pokot, R.layout.support_simple_spinner_dropdown_item));
                break;


        }
    }
    }


