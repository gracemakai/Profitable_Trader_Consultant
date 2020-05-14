package com.grace.profitabletraderconsultant.Navigation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grace.profitabletraderconsultant.InformationInput.Company;
import com.grace.profitabletraderconsultant.Navigation.Navigation;
import com.grace.profitabletraderconsultant.R;

public class Edit_Company_Info extends AppCompatActivity {

    String Phone;
    EditText nameBox;
    Spinner typeBox, countyBox, subCountyInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__company__info);

        nameBox = findViewById(R.id.businessName);
        typeBox = findViewById(R.id.businessType);
        countyBox = findViewById(R.id.county);
        subCountyInput = findViewById(R.id.sub_county);

        countyBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        if (user != null) {
            Phone = user.getPhoneNumber();
            Phone = Phone.replaceAll("\\D", "");
            Phone = Phone.replaceFirst("254", "");
            Phone = "0" + Phone;
        }

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = nameBox.getText().toString();
                String Type = typeBox.getSelectedItem().toString();
                String County = countyBox.getSelectedItem().toString();
                String SubCounty = subCountyInput.getSelectedItem().toString();
                Company company = new Company(Name, Type, County, SubCounty, Phone);
                create(company);
                startActivity(new Intent(Edit_Company_Info.this, Navigation.class));
            }
        });

    }

    private void create(Company company) {

        DatabaseReference databaseReferenceCompany = FirebaseDatabase.getInstance().getReference("User").child(Phone).child("Company");
        databaseReferenceCompany.setValue(company);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Company");
        databaseReference.setValue(company);

    }

    public void getCounty(String County) {
        switch (County) {
            case "Baringo":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Baringo, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Bomet":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Bomet, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Bungoma":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Bungoma, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Busia":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Busia, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Elgeyo Marakwet":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Elgeyo_Marakwet, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Embu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Embu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Garissa":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Garissa, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Homa Bay":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Homa_Bay, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Isiolo":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Isiolo, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kajiado":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kajiado, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kakamega":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kakamega, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kericho":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kericho, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kiambu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kiambu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kilifi":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kilifi, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kirinyaga":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kirinyaga, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kisii":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kisii, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kisumu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kisumu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kitui":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kitui, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Kwale":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Kwale, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Laikipia":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Laikipia, R.layout.support_simple_spinner_dropdown_item));
                break;
            case "Lamu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Lamu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Machakos":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Machakos, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Makueni":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Makueni, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Mandera":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Mandera, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Meru":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Meru, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Migori":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Migori, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Marsabit":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Marsabit, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Mombasa":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Mombasa, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Muranga":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Muranga, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nairobi":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Nairobi, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nakuru":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Nakuru, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nandi":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Nandi, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Narok":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Narok, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nyamira":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Nyamira, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nyandarua":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Nyandarua, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Nyeri":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Nyeri, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Samburu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Samburu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Siaya":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Siaya, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Taita Taveta":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Taita_Taveta, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Tana River":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Tana_River, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Tharaka Nithi":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Tharaka_Nithi, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Trans Nzoia":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Trans_Nzoia, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Turkana":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Turkana, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Uasin Gishu":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Uasin_Gishu, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Vihiga":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Vihiga, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "Wajir":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.Wajir, R.layout.support_simple_spinner_dropdown_item));
                break;

            case "West Pokot":
                subCountyInput.setAdapter(ArrayAdapter.createFromResource(this, R.array.West_Pokot, R.layout.support_simple_spinner_dropdown_item));
                break;


        }
    }
}
