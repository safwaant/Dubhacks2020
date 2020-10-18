package com.example.HouseScout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView text;
    ArrayList<String> imgs;
    ArrayList<String> prices;
        EditText usernameLogin, passwordLogin;
        Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwordLogin = (EditText) findViewById(R.id.usernameLogin);
        usernameLogin = (EditText) findViewById(R.id.passwordLogin);
        loginButton = (Button)findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HouseListing.class));
            }
        });

        //text = findViewById(R.id.hello);

//        Prices task = new Prices();
//        task.execute();

        Images pics = new Images();
        pics.execute();

        //Log.d("IMAGE", imgs.get(0));

    }

    class Prices extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            Document doc = null;
            String zipcode = "98052";
            String buyPref = "for_sale";
            ArrayList<String> prices = new ArrayList<>();
            String url = "https://www.zillow.com/" + "homes/" + buyPref + "/" + zipcode + "_rb/";
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements photos = doc.select("ul.photo-cards > li");
            for(Element photo: photos) {
                String price = photo.select("div.list-card-price").html();
                prices.add(price);
            }

            return prices;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            text.setText(result.get(0));
            prices = result;
        }
    }

    class Images extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            Document doc = null;
            String zipcode = "98052";
            String buyPref = "for_sale";
            ArrayList<String> imgs = new ArrayList<>();
            String url = "https://www.zillow.com/" + "homes/" + buyPref + "/" + zipcode + "_rb/";
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements photos = doc.select("ul.photo-cards > li");
            for(Element photo: photos) {
                Element anchor = photo.select("div.list-card-top > a").first();
                String src = photo.select("img").attr("src");
                imgs.add(src);
            }

            return imgs;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            text.setText(result.get(0));
        }
    }
}