package com.hl.afchelper.ui.fragment.base;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.adapter.RecyclerAdapter;
import com.hl.afchelper.base.BaseBackFragment;
import com.hl.afchelper.entity.Data;
import com.hl.afchelper.entity.db.MyDBOpenHelper;
import com.hl.afchelper.listener.OnItemClickListener;
import com.hl.afchelper.ui.fragment.MainFragment;
import com.hl.afchelper.ui.view.MyToolBar;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ListFragment extends BaseBackFragment {

    private ArrayList<Data> datas;
    private String sql;
    private static final String SQL = "sql";
    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private View view;

    public static ListFragment newInstance(String sql) {
        Bundle args = new Bundle ();
        args.putString ("sql", sql);
        ListFragment fragment = new ListFragment ();
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        //绑定控件
        sql = getArguments ().getString (SQL);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.fragment_list, container, false);
        //初始化数据和布局
        initData ();
        initView ();
        return attachToSwipeBack (view);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd (savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿

        _mActivity.getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        mRecyclerView = null;
        _mActivity.getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput ();
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        RefWatcher refWatcher = MyApplication.getRefWatcher (getActivity ());
        refWatcher.watch (this);
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
        mRecyclerView = view.findViewById (R.id.recycler_list);
        mAdapter = new RecyclerAdapter (_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager (_mActivity);
        mRecyclerView.setLayoutManager (manager);
        // Init Datas
        datas = new ArrayList<> ();
        getData (sql);
        mAdapter.setDatas (datas);
        mRecyclerView.setAdapter (mAdapter);

        mAdapter.setOnItemClickListener (new OnItemClickListener () {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                // 因为启动的MsgFragment是MainFragment的兄弟Fragment,所以需要MainFragment.start()

                // 也可以像使用getParentFragment()的方式,拿到父Fragment来操作 或者使用 EventBusActivityScope
                extraTransaction()
                        .start(ContentFragment.newInstance (datas.get (position)));

            }
        });
    }

    private void getData(String sql) {
        //db数据库
        MyDBOpenHelper dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase ();
        Cursor mCursor = dbRead.rawQuery (sql, null);
        datas = new ArrayList<> ();
        while (mCursor.moveToNext ()) {
            Integer mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            String titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            String contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            Data data = new Data (mid, titleStr, contentStr);
            datas.add (data);
        }
        mCursor.close ();
        dbHelper.close ();
        dbRead.close ();
    }
}


