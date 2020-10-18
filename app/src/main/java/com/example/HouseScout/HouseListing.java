package com.example.HouseScout;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.Metric;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HouseListing extends AppCompatActivity {

    TextView location;
    TextView price;
    ImageView houseImg;
    Button next;
    Metric metric;

    ArrayList<String> prices = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = findViewById(R.id.next);
        location = findViewById(R.id.location);
        price = findViewById(R.id.price);
        houseImg = findViewById(R.id.imageView);

        location.setText("98052");

        Prices priceList = new Prices();
        priceList.execute();

        Metrics score = new Metrics();
        score.execute();

        Images imgs = new Images();
        imgs.execute();

        houseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseListing.this, MetricsActivity.class);
                intent.putExtra(MetricsActivity.METRICS_KEY, metric);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prices priceList = new Prices();
                priceList.execute();

                Images imgs = new Images();
                imgs.execute();
            }
        });

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
            Random rand = new Random();
            int randNum = rand.nextInt(30);
            price.setText(result.get(randNum));
        }
    }

    class Images extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Document doc = null;
            Random rand = new Random();
            int randNum = rand.nextInt(8);
            String zipcode = "98052";
            String buyPref = "for_sale";
            ArrayList<String> imgs = new ArrayList<>();
            String url = "https://www.zillow.com/" + "homes/" + buyPref + "/" + zipcode + "_rb/";
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element photos = doc.select("ul.photo-cards > li").get(randNum);
            Element anchor = photos.select("div.list-card-top > a").first();
            String src = anchor.child(0).attr("src");

            Bitmap bmp = null;
            try {
                URL imgUrl = new URL(src);
                bmp = BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                bmp = null;
            }
            return bmp;

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                houseImg.setImageBitmap(result);
            }
        }
    }


    class Metrics extends AsyncTask<Void, Void, Metric> {
        @Override
        protected Metric doInBackground(Void... voids) {
            Document doc = null;
            String zipcode = "98007";
            String url = "https://www.zipdatamaps.com/" + zipcode + "#:~:text=Zip%20Code%2098052%20is%20located%20in%20the%20state," +
                    "Postal%20Service%20name%20for%2098052%20is%20REDMOND%2C%20Washington.";
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element table = doc.select("tbody").first();
            String scores = table.child(11).child(1).html();
            String commute = table.child(12).child(1).html();
            String medianIncome = table.child(9).child(1).html();
            String unemployment = table.child(8).child(1).child(0).html();

            return new Metric(scores, commute, medianIncome, unemployment);
        }

        @Override
        protected void onPostExecute(Metric m) {
            super.onPostExecute(m);
            metric = m;
        }
    }
}