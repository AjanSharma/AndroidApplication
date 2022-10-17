package com.example.ajans.locationlocationfind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddMessage extends AppCompatActivity {

    EditText msg;
    Button saveMsg;
    String message;

    TextView msgTextPaste;
    String msgText;

    public AddMessage() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Message");
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#000000")));

        msgTextPaste = (TextView)findViewById(R.id.msgTextPaste);
        saveMsg = (Button)findViewById(R.id.save_msg);
        msg = (EditText)findViewById(R.id.msg);

        saveMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp1 = getSharedPreferences("myPref1",MODE_PRIVATE);
                message = msg.getText().toString();

                SharedPreferences.Editor e1 = sp1.edit();
                e1.putString("msg1",message);
                e1.commit();
                startActivity(new Intent(AddMessage.this,MainActivity.class));
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp1 = getSharedPreferences("myPref1",MODE_PRIVATE);
        msgText = sp1.getString("msg1",null);
        msgTextPaste.setText(msgText);
    }

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
