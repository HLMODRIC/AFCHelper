package com.hl.afchelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hl.afchelper.ui.fragment.base.PagerFragment;

/**
 * Created by huanglei on 2018/3/6.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public PagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PagerFragment.newInstance("select * from tab_name where id between 0 and 99");
        } else {
            return PagerFragment.newInstance("select * from tab_name where id between 100 and 199");
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
