package com.zeefive.vmcapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zeefive.vmcapp.activity.ActivityProject_page_1;
import com.zeefive.vmcapp.activity.ActivityProject_page_2;

public class ProjectPagerAdapter extends FragmentPagerAdapter {

    public ProjectPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0 : return new ActivityProject_page_1();
            case 1 : return new ActivityProject_page_2();
            default: new Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0 : return ActivityProject_page_1.TITLE;
            case 1 : return ActivityProject_page_2.TITLE;
            default: return null;
        }
    }
}
