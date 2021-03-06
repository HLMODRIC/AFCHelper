package com.hl.afchelper.ui.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.Until.ConfigUtil;
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
    private static String THEME_KEY = "theme_mode";

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;
    private Boolean isNight;
    private NavigationView navigationView;


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
        MyApplication.me().refreshResources(getActivity ());

        isNight = ConfigUtil.getBoolean (THEME_KEY, false);

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
        navigationView = view.findViewById (R.id.nav_view);
         mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId ()){
                    case R.id.app_bar_switch:
                        Toast.makeText (getContext (),"y",Toast.LENGTH_SHORT).show ();
                }

                //在这里处理item的点击事件
                return true;
            }
        });
         Menu menu = navigationView.getMenu ();
        MenuItem item = menu.findItem (R.id.app_bar_switch);
        Switch aSwitch = item.getActionView().findViewById(R.id.item_switch);
        aSwitch.setChecked(isNight);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    MyApplication.me().setTheme((AppCompatActivity)getActivity (), true);
                }else {
                    MyApplication.me().setTheme((AppCompatActivity)getActivity (), false);
                }
            }
        });

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
