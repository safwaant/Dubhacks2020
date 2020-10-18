package com.example.HouseScout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MetricsActivity extends AppCompatActivity {
    public static final String METRICS_KEY = "METRICS_KEY";

    TextView metric;
    TextView commute;
    TextView medianIncome;
    TextView unemployment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        metric = findViewById(R.id.metric);
        commute = findViewById(R.id.commute);
        medianIncome = findViewById(R.id.medianIncome);
        unemployment = findViewById(R.id.unemployment);

        AreaMetric data = getIntent().getExtras().getParcelable(METRICS_KEY);

        String testRatingText = "School Test Performance: " + data.test_rating;
        String commuteTime = "Average Commute Time: " + data.commute_time;
        String income = "Median Income: " + data.medianIncome;
        String unemploy = "Unemployment rate: " + data.unemployment;

        metric.setText(data.test_rating);
        commute.setText(data.commute_time);
        medianIncome.setText(data.medianIncome);
        unemployment.setText(data.unemployment);
    }
}