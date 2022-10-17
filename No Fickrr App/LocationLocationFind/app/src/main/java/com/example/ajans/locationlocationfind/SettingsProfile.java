package com.example.ajans.locationlocationfind;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsProfile extends Fragment {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    String name, email;
    TextView enterName, enterEmail;
    ImageView aNum, aMsg;


    public SettingsProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vv = inflater.inflate(R.layout.fragment_settings_profile, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        enterName = (TextView) vv.findViewById(R.id.enterName);
        enterEmail = (TextView) vv.findViewById(R.id.enterEmail);
        aNum = (ImageView) vv.findViewById(R.id.aNum);
        aMsg = (ImageView) vv.findViewById(R.id.aMsg);
        return vv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sqLiteDatabase = databaseHelper.getReadableDatabase();
        cursor = databaseHelper.retrieveInfo(sqLiteDatabase);
        if (cursor.moveToFirst()){
            do {
                name = cursor.getString(0);
                email = cursor.getString(1);
            }while (cursor.moveToNext());
        }

        enterName.setText(name);
        enterEmail.setText(email);

        aNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddNumbers.class);
                startActivity(intent);
            }
        });
        aMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),AddMessage.class);
                startActivity(intent1);
            }
        });

    }
}
