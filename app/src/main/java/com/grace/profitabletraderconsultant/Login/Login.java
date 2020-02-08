package com.grace.profitabletraderconsultant.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.grace.profitabletraderconsultant.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText Phone = findViewById(R.id.phone);
        Button submit = findViewById(R.id.sign_In);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneText = Phone.getText().toString().trim();

                if (phoneText.isEmpty() || phoneText.length() < 10){
                    Phone.setError("Enter a valid mobile number");
                    Phone.requestFocus();
                    return;
                }

                Intent intent = new Intent(Login.this, verification.class);
                intent.putExtra("Phone", phoneText);
                startActivity(intent);
            }
        });

    }
}
