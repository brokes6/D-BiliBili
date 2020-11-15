package com.example.dildil.home_page.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;


public class TabAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private FragmentManager mFragmentManager;

    public void setFragments(List<Fragment> list) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (Fragment f : mFragmentManager.getFragments()) {
            transaction.remove(f);
        }
        transaction.commitNow();
        this.list = list;
        notifyDataSetChanged();
    }

    public TabAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragmentCreator =list.get(position);
        return fragmentCreator;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

