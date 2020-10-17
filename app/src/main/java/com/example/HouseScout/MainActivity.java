package com.example.HouseScout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.HouseScout.R;

public class MainActivity extends AppCompatActivity {
        EditText usernameLogin, passwordLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwordLogin = (EditText) findViewById(R.id.UsernameLogin);
        usernameLogin = (EditText) findViewById(R.id.PasswordLogin);
    }
}