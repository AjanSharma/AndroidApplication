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

public class AddNumbers extends AppCompatActivity {

    Button save;
    EditText firstNumber;
    String firstNum;

    TextView textPaste;
    String textPas;

    EditText secondNumber;
    String secondNum;
    EditText thirdNumber;
    String thirdNum;
    EditText fourthNumber;
    String fourthNum;
    EditText fifthNumber;
    String fifthNum;

    public AddNumbers() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_numbers);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Number");
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#000000")));

        save = (Button)findViewById(R.id.save);
        firstNumber = (EditText)findViewById(R.id.firstNumber);
        textPaste = (TextView) findViewById(R.id.textPaste);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstNum = firstNumber.getText().toString();



//                secondNum = secondNumber.getText().toString();
//
//                thirdNum = thirdNumber.getText().toString();
//
//                fourthNum = fourthNumber.getText().toString();
//
//                fifthNum = fifthNumber.getText().toString();



                SharedPreferences sp = getSharedPreferences("myPref",MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putString("first",firstNum);
//                e.putString("second",secondNum);
//                e.putString("third",thirdNum);
//                e.putString("fourth",fourthNum);
//                e.putString("fifth",fifthNum);
                e.commit();
                textPaste.setText(firstNum);
                startActivity(new Intent(AddNumbers.this,MainActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("myPref",MODE_PRIVATE);
        textPas = sp.getString("first",null);
        textPaste.setText(textPas);
    }

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
