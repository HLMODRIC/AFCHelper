package com.hl.afchelper.ui.fragment.knowledge;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.adapter.PagerAdapter;
import com.hl.afchelper.base.BaseMainFragment;
import com.squareup.leakcanary.RefWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huanglei on 2018/3/29.
 */

public class KnowledgeTabFragment extends BaseMainFragment {
    @BindView(R.id.tl_knowledge)
    TabLayout mTlKnowledge;
    @BindView(R.id.vp_knowledge)
    ViewPager mVpKnowledge;
    Unbinder unbinder;
    private View view;

    public static KnowledgeTabFragment newInstance() {
        Bundle args = new Bundle ();
        KnowledgeTabFragment fragment = new KnowledgeTabFragment ();
        fragment.setArguments (args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.fragment_knowledge, container, false);
        unbinder = ButterKnife.bind (this, view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView (savedInstanceState);
        //这里注意的是，因为我是在fragment中创建MyFragmentPagerAdapter，所以要传getChildFragmentManager()
        mVpKnowledge.setAdapter (new PagerAdapter (getChildFragmentManager (),new String[]{"理论","维护"}));
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        mTlKnowledge.setupWithViewPager (mVpKnowledge);
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
