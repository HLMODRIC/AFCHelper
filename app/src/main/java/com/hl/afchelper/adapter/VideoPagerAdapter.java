package com.hl.afchelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hl.afchelper.ui.fragment.base.PagerFragment;

/**
 * Created by huanglei on 2018/3/6.
 */

public class VideoPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public VideoPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return PagerFragment.newInstance("select * from tab_name where id between 200 and 299");
            case 1:
                return PagerFragment.newInstance("select * from tab_name where id between 300 and 399");
            case 2:
                return PagerFragment.newInstance("select * from tab_name where id between 400 and 499");
            case 3:
                return PagerFragment.newInstance("select * from tab_name where id between 500 and 599");
            default:
                return null;
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
