package com.hl.afchelper;

import android.os.Bundle;
import android.support.annotation.Nullable;


import com.gyf.barlibrary.ImmersionBar;
import com.hl.afchelper.entity.db.DBManager;

import com.hl.afchelper.ui.fragment.MainFragment;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by huanglei on 2018/3/28.
 */

public class MainActivity extends SupportActivity {
    protected ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //数据库操作
        DBManager.openDatabase(getApplicationContext ());
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        setContentView(R.layout.activity_main);

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        RefWatcher refWatcher = MyApplication.getRefWatcher (this);
        refWatcher.watch (this);
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator ();
    }

    @Override
    protected void onResume() {
        MyApplication.me().refreshResources(this);
        super.onResume();
    }
}
