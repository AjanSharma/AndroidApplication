package com.example.ajans.locationlocationfind;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsCustom extends Fragment implements View.OnClickListener {

    RadioButton btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    RadioGroup radioGroup;
    int interval;
    String intervalString;


    public SettingsCustom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vv = inflater.inflate(R.layout.fragment_settings_custom, container, false);
        btn1 = (RadioButton) vv.findViewById(R.id.zMin);
            btn1.setOnClickListener(this);
        btn2 = (RadioButton) vv.findViewById(R.id.fMin);
            btn2.setOnClickListener(this);
        btn3 = (RadioButton) vv.findViewById(R.id.sMin);
            btn3.setOnClickListener(this);
        btn4 = (RadioButton) vv.findViewById(R.id.tMin);
            btn4.setOnClickListener(this);
        btn5 = (RadioButton) vv.findViewById(R.id.foMin);
            btn5.setOnClickListener(this);
        btn6 = (RadioButton) vv.findViewById(R.id.fiMin);
            btn6.setOnClickListener(this);
        btn7 = (RadioButton) vv.findViewById(R.id.siMin);
            btn7.setOnClickListener(this);
        radioGroup = (RadioGroup) vv.findViewById(R.id.radioG1);

        return vv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       // RadioBtn();

    }
//    public void onRadioButtonClicked(View view){
//
//
//
//    }

    @Override
    public void onClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.zMin:
                if (checked){
                    interval = 5000;
                }
                break;
            case R.id.fMin:
                if (checked){
                    interval = 60000;
                }
                break;
            case R.id.sMin:
                if (checked){
                    interval = 300000;
                }
                break;
            case R.id.tMin:
                if (checked){
                    interval = 600000;
                }
                break;
            case R.id.foMin:
                if (checked){
                    interval = 1200000;
                }
                break;
            case R.id.fiMin:
                if (checked){
                    interval = 2400000;
                }
                break;
            case R.id.siMin:
                if (checked){
                    interval = 3600000;
                }
                break;
        }

        intervalString = Integer.toString(interval);
        SharedPreferences sp10 = getActivity().getSharedPreferences("myPref10", Context.MODE_PRIVATE);
        SharedPreferences.Editor e10 = sp10.edit();
        e10.putString("intervalS",intervalString);
        e10.commit();
    }

//    private void RadioBtn() {
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                if (btn1.isChecked()){
//                    interval = 5000;
//                } else if (btn2.isChecked()){
//                    interval = 60000;
//                } else if (btn3.isChecked()){
//                    interval = 300000;
//                } else if (btn4.isChecked()){
//                    interval = 600000;
//                } else if (btn5.isChecked()){
//                    interval = 1200000;
//                } else if (btn6.isChecked()){
//                    interval = 2400000;
//                } else if (btn7.isChecked()){
//                    interval = 3600000;
//                }
//
//                intervalString = Integer.toString(interval);
//                SharedPreferences sp10 = getActivity().getSharedPreferences("myPref10", Context.MODE_PRIVATE);
//                SharedPreferences.Editor e10 = sp10.edit();
//                e10.putString("intervalS",intervalString);
//                e10.commit();
//            }
//        });
//    }
}
