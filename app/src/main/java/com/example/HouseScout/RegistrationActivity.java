package com.example.HouseScout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegistrationActivity.class.getSimpleName();
    EditText mfName, mlName, mEmail, mPassword, mPasswordC;
    Button registerButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fAuth = FirebaseAuth.getInstance();
        mfName = findViewById(R.id.firstNameInput);
        mlName = findViewById(R.id.lastNameInput);
        mEmail = findViewById(R.id.emailInput);
        mPassword = findViewById(R.id.passwordInput);
        mPasswordC = findViewById(R.id.passwordInput2);
        registerButton = findViewById(R.id.registerButton);

        Toolbar registrationToolbar = (Toolbar) findViewById(R.id.registrationToolbar);
        setSupportActionBar(registrationToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        registerButton.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String passwordC = mPasswordC.getText().toString().trim();
            String fName = mfName.getText().toString().trim();
            String lName = mlName.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                mEmail.setError("Your email is required.");
                return;
            }
            if(TextUtils.isEmpty(password)){
                mEmail.setError("Your password is required.");
                return;
            }
            if(TextUtils.isEmpty(passwordC)){
                mPasswordC.setError("Your confirmed password is required.");
                return;
            }
            if(TextUtils.isEmpty(fName)){
                mfName.setError("Your first name is required.");
                return;
            }
            if(TextUtils.isEmpty(lName)){
                mlName.setError("Your last name is required.");
                return;
            }
            if(!passwordC.equals(password)){
                mPasswordC.setError("Both passwords don't match. Please try again.");
                return;
            }
            if(password.length() < 6){
                mPassword.setError("Password needs to be longer than 6 characters");
            }
            fAuth.createUserWithEmailAndPassword(email, passwordC).addOnCompleteListener(RegistrationActivity.this, task -> {
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),HouseListing.class);
                    startActivity(intent);
                } else {
                    Log.d(LOG_TAG,task.getException().toString());
                }
            });
            Log.d(LOG_TAG,"Email:"+email);
            Log.d(LOG_TAG,"Password:"+password);
            Log.d(LOG_TAG,"PasswordC:"+passwordC);
            Log.d(LOG_TAG,"First name:"+fName);
            Log.d(LOG_TAG,"Last name:"+lName);
        });
    }
}