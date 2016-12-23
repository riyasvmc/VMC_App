package com.zeefive.vmcapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zeefive.vmcapp.activity.ActivityProjectDetail_page_2;
import com.zeefive.vmcapp.activity.ActivityProjectDetail_page_1;
import com.zeefive.vmcapp.activity.ActivityProjectDetail_page_3;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0 : return new ActivityProjectDetail_page_1();
            case 1 : return new ActivityProjectDetail_page_2();
            case 2 : return new ActivityProjectDetail_page_3();
            default: new Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0 : return ActivityProjectDetail_page_1.TITLE;
            case 1 : return ActivityProjectDetail_page_2.TITLE;
            case 2 : return ActivityProjectDetail_page_3.TITLE;
            default: return null;
        }
    }
}
