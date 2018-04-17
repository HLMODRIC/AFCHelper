package com.hl.afchelper.ui.fragment.knowledge;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
        MyApplication.me().refreshResources(getActivity ());
        view = inflater.inflate (R.layout.fragment_knowledge, container, false);
        unbinder = ButterKnife.bind (this, view);
        mVpKnowledge.setAdapter (new PagerAdapter (getChildFragmentManager (),new String[]{"理论","维护"}));
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        mTlKnowledge.setupWithViewPager (mVpKnowledge);
        return view;
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
