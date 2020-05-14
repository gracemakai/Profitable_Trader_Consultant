package com.grace.profitabletraderconsultant.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grace.profitabletraderconsultant.InformationInput.Input;
import com.grace.profitabletraderconsultant.R;
import com.grace.profitabletraderconsultant.User;

import java.util.concurrent.TimeUnit;

public class verification extends AppCompatActivity {

    private String mVerificationId;
    public String userId;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public String phone;
    public String extendedPhone;
    EditText editTextCode;
    Bundle bundle;
    private static final String TAG = verification.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        //Initializing objects
        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Members");

        editTextCode = findViewById(R.id.code);

        //Getting phone number from login activity and sending the code to the number
        bundle = getIntent().getExtras();
        phone = bundle.getString("phone");
        phone = phone.replaceFirst("^0*", "");
        extendedPhone = "+254" + phone;
        sendVerificationCode(phone);

        //If the automatic sms detection didn't work
        Button sendCode = findViewById(R.id.verify);
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString();

                if (code.isEmpty() || code.length() < 6){
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }

                verifyVerificationCode(code);

                if (currentUser != null){
                    createUser(extendedPhone);
                } else {
                    updateUser(extendedPhone);
                }
            }
        });
    }
    //Method that sends verification code
    private void sendVerificationCode(String phone){

        PhoneAuthProvider.getInstance().verifyPhoneNumber
            (
                    "+254" + phone,
                    60,
                    TimeUnit.SECONDS,
                    TaskExecutors.MAIN_THREAD,
                    mCallbacks);
    }

    //To detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code that was sent
            String code = phoneAuthCredential.getSmsCode();

            //If the code is not detected directly
            if (code != null){
                editTextCode.setText(code);
                verifyVerificationCode(code);
            }
        }


        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(verification.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //Storing the verification sent
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code){

        //Creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(verification.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Intent intent = new Intent(verification.this, Input.class);
                    intent.putExtra("Phone", phone);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                /*    boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                    if (!isNew){
                        Toast.makeText(verification.this, "Not new", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(verification.this, Navigation.class);
                        intent.putExtra("Phone", phone);
                        startActivity(intent);
                    }else {

                        Intent intent = new Intent(verification.this, Input.class);
                        intent.putExtra("Phone", phone);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        Bundle bundle = new Bundle();
                        bundle.putString("Phone", phone);
                        Intent intent1 = new Intent(verification.this, ProductInfo.class);
                        intent1.putExtras(bundle);
                    }

                 */

                }else {


                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        String message = "Invalid code entered";
                        Toast.makeText(verification.this, message, Toast.LENGTH_SHORT).show();
                    }else {

                        String message = "Something is wrong. We will fix it soon";
                        Toast.makeText(verification.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void createUser(String phone){
            if (currentUser != null) {
                userId = mFirebaseDatabase.push().getKey();
            }
            User user = new User(extendedPhone);
            mFirebaseDatabase.setValue(user);

            addUserChangeListener();
        }

        /**
         * User data change listener
         */
        private void addUserChangeListener() {
            // User data change listener
            mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    // Check for null
                    if (user == null) {
                        Log.e(TAG, "User data is null!");
                        return;
                    }

                    Log.e(TAG, "User data is changed!" + user.phone);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read user", error.toException());
                }
            });
        }

        private void updateUser(String phone) {
            // updating the user via child nodes
            if (!TextUtils.isEmpty(phone))
                mFirebaseDatabase.child("phone").setValue(phone);

        }
    }



















