package com.example.HouseScout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    EditText email, password;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        fAuth = FirebaseAuth.getInstance();
    }

    public void checkAccount(View view) {
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        fAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(MainActivity.this, task -> {
            if(task.isSuccessful()){
                Intent intent = new Intent(getApplicationContext(), HouseListing.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Login Information is Incorrect. Please Try Again.", Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG,task.getException().toString());
            }
        });
    }

    public void launchRegistrationActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }
}