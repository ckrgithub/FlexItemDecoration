package com.ckr.flexitemdecoration.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ckr.flexitemdecoration.view.BaseFragment;

import java.util.List;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    List<BaseFragment> fragmentList;
    String[] titles;

    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
