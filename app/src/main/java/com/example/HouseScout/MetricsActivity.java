package com.example.HouseScout;

import androidx.appcompat.app.AppCompatActivity;

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



        metric.setText(data.test_rating);
        commute.setText(data.commute_time);
        medianIncome.setText(data.medianIncome);
        unemployment.setText(data.unemployment);
    }
}