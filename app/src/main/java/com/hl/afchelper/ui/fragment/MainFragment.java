package com.hl.afchelper.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.ui.fragment.home.HomeTabFragment;
import com.hl.afchelper.ui.fragment.knowledge.KnowledgeTabFragment;
import com.hl.afchelper.ui.fragment.search.SearchTabFragment;
import com.hl.afchelper.ui.fragment.video.VideoTabFragment;
import com.hl.afchelper.ui.view.BottomBar;
import com.hl.afchelper.ui.view.BottomBarTab;
import com.squareup.leakcanary.RefWatcher;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class MainFragment extends SupportFragment {
     public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;


    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(HomeTabFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeTabFragment.newInstance();
            mFragments[SECOND] = KnowledgeTabFragment.newInstance();
            mFragments[THIRD] = VideoTabFragment.newInstance();
            mFragments[FOUR] = SearchTabFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(KnowledgeTabFragment.class);
            mFragments[THIRD] = findChildFragment(VideoTabFragment.class);
            mFragments[FOUR] = findChildFragment(SearchTabFragment.class);
        }
    }

    private void initView(View view) {
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);

        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_home,getString(R.string.tab_menu_home)))
                .addItem(new BottomBarTab (_mActivity, R.drawable.tab_theory,getString(R.string.tab_menu_knowledge)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_video,getString(R.string.tab_menu_video)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.tab_search,getString(R.string.tab_menu_search)));


        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

                mBottomBar.getItem(FIRST);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }

        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        RefWatcher refWatcher = MyApplication.getRefWatcher (getActivity ());
        refWatcher.watch (this);
    }
}
