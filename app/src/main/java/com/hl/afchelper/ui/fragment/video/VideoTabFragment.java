package com.hl.afchelper.ui.fragment.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.adapter.PagerAdapter;
import com.hl.afchelper.base.BaseMainFragment;
import com.hl.afchelper.ui.view.MyToolBar;
import com.squareup.leakcanary.RefWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huanglei on 2018/3/29.
 */

public class VideoTabFragment extends BaseMainFragment {

    Unbinder unbinder;
    @BindView(R.id.view_toolbar)
    View mViewToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    MyToolBar mToolbar;
    @BindView(R.id.tl_video)
    TabLayout mTlVideo;
    @BindView(R.id.vp_video)
    ViewPager mVpVideo;
    private View view;

    public static VideoTabFragment newInstance() {
        Bundle args = new Bundle ();
        VideoTabFragment fragment = new VideoTabFragment ();
        fragment.setArguments (args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.fragment_video, container, false);

        initData ();
        initView ();
        unbinder = ButterKnife.bind (this, view);
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 初始化布局
     */
    private void initView() {
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView (savedInstanceState);
        //这里注意的是，因为我是在fragment中创建MyFragmentPagerAdapter，所以要传getChildFragmentManager()
        mVpVideo.setAdapter (new PagerAdapter (getChildFragmentManager (), new String[]{"TVM", "GATE", "POST", "其他"}));
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        mTlVideo.setupWithViewPager (mVpVideo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        RefWatcher refWatcher = MyApplication.getRefWatcher (getActivity ());
        refWatcher.watch (this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }
}
