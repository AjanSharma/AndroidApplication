package com.example.ajans.locationlocationfind;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class YourLocationInAddr extends AppCompatActivity {

    TextView addr_text, lat_text, lngg_text;
    String addr1,city1,state1,country1,postalCode1,knownName1,get_longitude1,get_latitude1;

    public YourLocationInAddr() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_location_in_addr);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Number");
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#000000")));

        addr_text = (TextView) findViewById(R.id.ad_text1);
        lat_text = (TextView) findViewById(R.id.ad_text2);
        lngg_text = (TextView) findViewById(R.id.ad_text3);

        SharedPreferences sp5 = getSharedPreferences("MyPref5",MODE_PRIVATE);
        addr1 = sp5.getString("address",null);
        city1 = sp5.getString("city",null);
        state1 = sp5.getString("state",null);
        country1 = sp5.getString("country",null);
        postalCode1 = sp5.getString("postal",null);
        knownName1 = sp5.getString("knownName",null);

        SharedPreferences sp2 = getSharedPreferences("MyPref3",MODE_PRIVATE);
        get_longitude1 = sp2.getString("longitude",null);
        get_latitude1 = sp2.getString("latitude",null);

        addr_text.setText(addr1 + ", " +city1+", "+state1+", "+country1+", "+postalCode1+", "+knownName1 );
        lat_text.setText(get_latitude1);
        lngg_text.setText(get_longitude1);
    }

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
