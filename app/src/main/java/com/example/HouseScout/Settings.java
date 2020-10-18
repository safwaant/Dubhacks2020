package com.example.HouseScout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText zip;
    String zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        zip = findViewById(R.id.ZipcodeSearch);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        zip.setText(prefs.getString("zipcode", "98052"));

        Toolbar registrationToolbar = (Toolbar) findViewById(R.id.registrationToolbar);
        setSupportActionBar(registrationToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void saveSettings(View view) {
        zipcode = zip.getText().toString();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("zipcode", zipcode);
        editor.apply();

        startActivity(new Intent(this, HouseListing.class));
    }
}