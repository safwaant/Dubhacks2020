package com.example.HouseScout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegistrationActivity.class.getSimpleName();
    EditText mfName, mlName, mEmail, mPassword, mPasswordC;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mfName = findViewById(R.id.firstNameInput);
        mlName = findViewById(R.id.lastNameInput);
        mEmail = findViewById(R.id.emailInput);
        mPassword = findViewById(R.id.passwordInput);
        mPasswordC = findViewById(R.id.passwordInput2);
        fAuth.getInstance();

        Toolbar registrationToolbar = (Toolbar) findViewById(R.id.registrationToolbar);
        setSupportActionBar(registrationToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void registerAccount(View view) {
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
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this,"Account created.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                } else {
                    Toast.makeText(RegistrationActivity.this,"Error Occurred. Error: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}