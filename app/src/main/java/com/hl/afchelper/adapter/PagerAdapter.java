package com.hl.afchelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hl.afchelper.ui.fragment.home.HomeTabFragment;
import com.hl.afchelper.ui.fragment.knowledge.child.KnowledgePagerFragment;

import java.util.List;

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
            return KnowledgePagerFragment.newInstance("select * from tab_name where id between 1 and 100");
        } else {
            return KnowledgePagerFragment.newInstance("select * from tab_name where id between 200 and 299");
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
