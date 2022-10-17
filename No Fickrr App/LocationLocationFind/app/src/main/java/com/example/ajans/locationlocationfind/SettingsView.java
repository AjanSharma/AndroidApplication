package com.example.ajans.locationlocationfind;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#000000")));



        SettingsViewAdapter adapter = new SettingsViewAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.settingsViewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.settingsViewTab);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.settingsBgColor));
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
