package com.theekshana.codefestexamcustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class newsFeedMap extends AppCompatActivity {

    public String location;
    TextView loc;


    public String getNewLoaction() {
        return newLoaction;
    }

    private String newLoaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_map);
        loc = findViewById(R.id.loactionset);
        Bundle bundle =getIntent().getExtras();
        location = bundle.getString("riderDocId");

        loc.setText(location);
         newLoaction = loc.getText().toString();
    }
}