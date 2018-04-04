package com.hl.afchelper.ui.fragment.base;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.adapter.RecyclerAdapter;
import com.hl.afchelper.entity.Data;
import com.hl.afchelper.entity.db.MyDBOpenHelper;
import com.hl.afchelper.listener.OnItemClickListener;
import com.hl.afchelper.ui.fragment.MainFragment;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class PagerFragment extends SupportFragment{
    private RecyclerView mRecy;
    private ArrayList<Data> mData;
    private String sql;
    private RecyclerAdapter mAdapter;
    private static final String SQL = "sql";

    public static PagerFragment newInstance(String sql) {

        Bundle args = new Bundle();
        args.putString ("sql",sql);
        PagerFragment fragment = new PagerFragment ();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          sql = getArguments().getString (SQL);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_list, container, false);
        MyApplication.me().refreshResources(getActivity ());
        initView(view);
        return view;
    }



    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new RecyclerAdapter (_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        // Init Datas
        mData = new ArrayList<> ();
        getData (sql);
        mAdapter.setDatas(mData);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener () {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                // 因为启动的MsgFragment是MainFragment的兄弟Fragment,所以需要MainFragment.start()

                // 也可以像使用getParentFragment()的方式,拿到父Fragment来操作 或者使用 EventBusActivityScope
                ((MainFragment) getParentFragment().getParentFragment()).startBrotherFragment(ListFragment.newInstance(mData.get (position).getNew_title (),mData.get (position).getNew_content ()));
            }
        });


    }
    private void getData(String sql) {
        //db数据库
        MyDBOpenHelper dbHelper = new MyDBOpenHelper (getActivity ());  //注意：dbHelper的实体化
        //查询数据库
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase ();
        Cursor mCursor = dbRead.rawQuery (sql,null);
        mData = new ArrayList<> ();
        while (mCursor.moveToNext ()) {
           Integer mid = mCursor.getInt (mCursor.getColumnIndex ("id"));
            String titleStr = mCursor.getString (mCursor.getColumnIndex ("title"));
            String contentStr = mCursor.getString (mCursor.getColumnIndex ("content"));
            String imageId = mCursor.getString (mCursor.getColumnIndex ("imageUrl"));
            Data data = new Data (mid, titleStr, contentStr,imageId);
            mData.add (data);
        }
        mCursor.close ();
        dbHelper.close ();
        dbRead.close ();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        RefWatcher refWatcher = MyApplication.getRefWatcher (getActivity ());
        refWatcher.watch (this);
    }

}
