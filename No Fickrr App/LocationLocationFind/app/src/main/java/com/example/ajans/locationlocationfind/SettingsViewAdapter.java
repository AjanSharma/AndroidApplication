package com.example.ajans.locationlocationfind;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ajans on 12/24/2017.
 */

public class SettingsViewAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SettingsViewAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new SettingsProfile();
        }else {
            return new SettingsCustom();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return mContext.getString(R.string.settingsProfile);
            case 1: return mContext.getString(R.string.settingsCustom);
            default: return null;
        }
    }
}
