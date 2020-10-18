package com.example.HouseScout;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class HouseListing extends AppCompatActivity {
    ImageButton settingsButton;
    TextView location;
    TextView price;
    ImageView houseImg;
    FloatingActionButton next;
    AreaMetric metric;
    String zipcode = "98052";
    String index = "0";
    TextView houseLink;

    ArrayList<String> prices = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_listing);
        settingsButton = (ImageButton) findViewById(R.id.settingsbutton);
        settingsButton.setOnClickListener(v -> {
            startActivity(new Intent(HouseListing.this, Settings.class));
        });

        next = findViewById(R.id.next);
        location = findViewById(R.id.location);
        price = findViewById(R.id.price);
        houseImg = findViewById(R.id.imageView);
        houseLink = findViewById(R.id.link);

        location.setText(zipcode);

        Prices priceList = new Prices();
        priceList.execute(zipcode);

        Metrics score = new Metrics();
        score.execute(zipcode);

        Images imgs = new Images();
        imgs.execute(zipcode, index);



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
                int temp = Integer.parseInt(index);
                temp++;
                String idx = "" + temp;
                index = idx;

                Images imgs = new Images();
                imgs.execute(zipcode, index);

                Prices priceList = new Prices();
                priceList.execute(zipcode);
            }
        });

    }

    class Prices extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            Document doc = null;
            String zipcode = strings[0];
            //String salary = strings[1];
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
            price.setText(result.get(Integer.parseInt(index)));
        }
    }

    class Images extends AsyncTask<String, Void, Bitmap> {
        int retIdx = 0;
        String zillowLink;
        @Override
        protected Bitmap doInBackground(String... strings) {
            Document doc = null;
            String zipcode = strings[0];
            String idx = strings[1];
            int index = Integer.parseInt(idx);
            String buyPref = "for_sale";
            String url = "https://www.zillow.com/" + "homes/" + buyPref + "/" + zipcode + "_rb/";
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            retIdx = index;
            if(index == 2) {
                retIdx++;
            }
            Element photos = doc.select("ul.photo-cards > li").get(retIdx);
            Element anchor = photos.select("div.list-card-top > a").first();
            String src = anchor.child(0).attr("src");

            zillowLink = photos.select("a").first().attr("abs:href");

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
            index = retIdx + "";
            houseLink.setText(zillowLink);
        }
    }


    class Metrics extends AsyncTask<String, Void, AreaMetric> {
        @Override
        protected AreaMetric doInBackground(String... strings) {
            Document doc = null;
            String zipcode = strings[0];
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

            return new AreaMetric(scores, commute, medianIncome, unemployment);
        }

        @Override
        protected void onPostExecute(AreaMetric m) {
            super.onPostExecute(m);
            metric = m;
        }
    }

}