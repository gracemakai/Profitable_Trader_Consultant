package com.grace.profitabletraderconsultant.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grace.profitabletraderconsultant.Navigation.Navigation;
import com.grace.profitabletraderconsultant.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            startActivity(new Intent(Login.this, Navigation.class));
        }
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

                Bundle bundle = new Bundle();
                bundle.putString("phone", phoneText);
                Intent intent = new Intent(Login.this, verification.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
