package com.hl.afchelper.ui.fragment.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.Until.SearchDataHelper;
import com.hl.afchelper.VideoActivity;
import com.hl.afchelper.adapter.ListRecyclerAdapter;
import com.hl.afchelper.base.BaseBackFragment;
import com.hl.afchelper.entity.Data;
import com.hl.afchelper.entity.db.MyDBOpenHelper;
import com.hl.afchelper.listener.OnItemClickListener;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayerStandard;


public class ListFragment extends BaseBackFragment {

    private ArrayList<Data> datas;
    private ArrayList<Data> SearchDatas;
    private String sql;
    private String table;
    private String oldNewsText = null;
    private TextView mToolbarTitle;
    private static final String ARG_SQL = "arg_sql";
    private static final String ARG_TABLE = "arg_table";
    private ListRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SearchDataHelper mSearchDataHelper;
    private View view;
    private Toolbar mToolBar;
    private Cursor mCursor;


    public static ListFragment newInstance(String table,String sql) {
        Bundle args = new Bundle ();
        args.putString (ARG_SQL, sql);
        args.putString (ARG_TABLE,table);
        ListFragment fragment = new ListFragment ();
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
          //绑定控件
        sql = getArguments ().getString (ARG_SQL);
        table = getArguments ().getString (ARG_TABLE);
    }
    @Override
    public void onResume() {
        MyApplication.me().refreshResources(getActivity ());
        super.onResume();
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
        mAdapter = null;
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
        mSearchDataHelper = new SearchDataHelper ();
        mRecyclerView = view.findViewById (R.id.recycler_list);
        mToolBar = view.findViewById (R.id.toolbar_list);
        setHasOptionsMenu(true);
        mToolbarTitle = view.findViewById (R.id.toolbar_title);
        mAdapter = new ListRecyclerAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager (_mActivity);
        mRecyclerView.setLayoutManager (manager);
        //Toolbar
        mToolBar.setTitle ("");
        //生成选项菜单
        mToolBar.inflateMenu (R.menu.list_menu);
        //设置溢出菜单的icon，显示、隐藏溢出菜单弹出的窗口
        mToolBar.showOverflowMenu ();
        ((AppCompatActivity ) getActivity()).setSupportActionBar(mToolBar);
        mToolbarTitle.setText (table);

        // Init Datas
        datas = new ArrayList<> ();
        getData (sql,null);
        mAdapter.setDatas (datas);
        mRecyclerView.setAdapter (mAdapter);

        mAdapter.setOnItemClickListener (new OnItemClickListener () {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                String videoUrl = datas.get (position).getVideoUrl ();
               if ((videoUrl.substring (videoUrl.lastIndexOf (".") + 1).equals ("mp4"))){
                   JZVideoPlayerStandard.startFullscreen(getActivity (),JZVideoPlayerStandard.class, Environment.getExternalStorageDirectory ()+"/AFCHelper/1.mp4", "1111");
                   //Intent intent = new Intent (getActivity (),VideoActivity.class);
                   //startActivity (intent);
               }else {
                   extraTransaction ()
                           .start (ContentFragment.newInstance (datas.get (position)));
               }
            }
        });
    }



    //后退键监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home:
                getActivity ().onBackPressed ();
                break;
        }
        return super.onOptionsItemSelected (item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate (R.menu.list_menu, menu);
        MenuItem item = menu.findItem (R.id.list_search);
        SearchView searchView = ( SearchView ) MenuItemCompat.getActionView (item);
        //默认刚进去就打开搜索栏
        searchView.setIconified (true);
        //设置输入文本的EditText
        SearchView.SearchAutoComplete et = searchView.findViewById (R.id.search_src_text);
        //设置搜索栏的默认提示
        et.setHint ("请输入 " + table + " 相关内容");
        //设置提示文本的颜色
        et.setHintTextColor (Color.WHITE);
        et.setTextSize (TypedValue.COMPLEX_UNIT_SP, 14);
        //设置输入文本的颜色
        et.setTextColor (Color.WHITE);
        //设置提交按钮是否可见
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && newText.length () > 0) {
                        getData (mSearchDataHelper.SearchData (table), newText);
                        mAdapter.setDatas (SearchDatas);
                }else{
                    mAdapter.setDatas (datas);
                }
                return false;


            }
        });
    }




    private void getData(String sql,String newText) {

        //db数据库
        MyDBOpenHelper dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase ();
        if (newText == null) {
            mCursor = dbRead.rawQuery (sql, null);
            datas = new ArrayList<> ();
            while (mCursor.moveToNext ()) {
                Integer mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
                String titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
                String contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
                String imageUrl = mCursor.getString (mCursor.getColumnIndex ("imageUrl"));
                String videoUrl = mCursor.getString (mCursor.getColumnIndex ("videoUrl"));
                Data data = new Data (mid, titleStr, contentStr, imageUrl, videoUrl);
                datas.add (data);
            }
            mCursor.close ();
            dbHelper.close ();
            dbRead.close ();
        } else {
            String[] selectionArgs = {"%" + newText + "%"};
            mCursor = dbRead.rawQuery (sql, selectionArgs);
            SearchDatas = new ArrayList<> ();
            while (mCursor.moveToNext ()) {
                Integer mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
                String titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
                String contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
                String imageUrl = mCursor.getString (mCursor.getColumnIndex ("imageUrl"));
                String videoUrl = mCursor.getString (mCursor.getColumnIndex ("videoUrl"));
                Data data = new Data (mid, titleStr, contentStr, imageUrl, videoUrl);
                SearchDatas.add (data);
            }
            mCursor.close ();
            dbHelper.close ();
            dbRead.close ();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //你的代码
         super.onConfigurationChanged(newConfig);
    }


}


