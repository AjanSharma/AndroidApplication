package com.example.ajans.locationlocationfind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.vstechlab.easyfonts.EasyFonts;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private final AppCompatActivity activity = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




            databaseHelper = new DatabaseHelper(activity);

            if (databaseHelper.checkForward()) {
                // mainActivity.click = false;
                Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMain);
                finish();
            }






        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        TextView main_text = (TextView) findViewById(R.id.mainText);
        main_text.setTypeface(EasyFonts.caviarDreamsBoldItalic(this));

        VideoView videoview = (VideoView) findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
        videoview.setVideoURI(uri);
        videoview.start();

        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();

            }
        });

        Button signBtn = (Button)findViewById(R.id.signBtn);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

        Button regisBtn = (Button)findViewById(R.id.registerBtn);
        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
