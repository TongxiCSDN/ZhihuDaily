package com.tongxi.zhihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qf on 2016/10/24.
 */
public class HeaderViewPagerAdapter extends FragmentPagerAdapter{

    List<Fragment> fragments;

    public HeaderViewPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    //    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
}
