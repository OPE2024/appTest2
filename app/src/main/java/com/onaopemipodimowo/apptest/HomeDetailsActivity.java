package com.onaopemipodimowo.apptest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

public class HomeDetailsActivity extends AppCompatActivity {
    // the movie to display
    Home home;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);
        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);


        // unwrap the movie passed in via intent, using its simple name as a key
        home = (Home) Parcels.unwrap(getIntent().getParcelableExtra(Home.class.getSimpleName()));
        Log.d("HomeDetailsActivity", String.format("Showing details for '%s'", home.getCity()));

        // set the title and overview
        tvTitle.setText(home.getCity());
        tvOverview.setText(home.getState_code());
    }
}