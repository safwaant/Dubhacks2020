package com.example.HouseScout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.HouseScout.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
        EditText emailLogin, passwordLogin;
        Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwordLogin = (EditText) findViewById(R.id.UsernameLogin);
        emailLogin = (EditText) findViewById(R.id.PasswordLogin);
        loginButton = (Button) findViewById(R.id.button);
      //  mFireBaseAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                if(email.isEmpty()){
                    sendErrorMessage(1);
                }else if(password.isEmpty()){
                    sendErrorMessage(2);
                }else if(password.isEmpty() && email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void sendErrorMessage(int error){
        if(error == 1){
            emailLogin.setError("Enter a Email");
            emailLogin.requestFocus();
        }else if(error == 2){
            passwordLogin.setError("Enter a Username");
            passwordLogin.requestFocus();
        }
    }
}