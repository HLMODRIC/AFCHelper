package com.hl.afchelper.ui.fragment.knowledge.child;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.afchelper.R;
import com.hl.afchelper.adapter.RecyclerAdapter;
import com.hl.afchelper.entity.Data;
import com.hl.afchelper.entity.db.MyDBOpenHelper;
import com.hl.afchelper.listener.OnItemClickListener;
import com.hl.afchelper.ui.fragment.video.VideoTabFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class KnowledgePagerFragment extends SupportFragment{
    private RecyclerView mRecy;
    private ArrayList<Data> mData;
    private String sql;
    private RecyclerAdapter mAdapter;
    private static final String SQL = "sql";

    private String[] mTitles = new String[]{
            "Use imagery to express a distinctive voice and exemplify creative excellence.",
            "An image that tells a story is infinitely more interesting and informative.",
            "The most powerful iconic images consist of a few meaningful elements, with minimal distractions.",
            "Properly contextualized concepts convey your message and brand more effectively.",
            "Have an iconic point of focus in your imagery. Focus ranges from a single entity to an overarching composition."
    };




    public static KnowledgePagerFragment newInstance(String sql) {


        Bundle args = new Bundle();
        args.putString ("sql",sql);
        KnowledgePagerFragment fragment = new KnowledgePagerFragment ();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sql = getArguments().getString (SQL);
        Log.d ("sql",sql);
        //getData (sql);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_list, container, false);
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
        for (int i = 0; i < 6; i++) {
            Data data = new Data (i,"1111"+i, "1111"+i);
            Log.d ("sql",i+"1111"+i +"1111"+i);
            mData.add(data);
        }
        mAdapter.setDatas(mData);

        mRecy.setAdapter(mAdapter);

       /* mAdapter.setOnItemClickListener(new OnItemClickListener () {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                VideoTabFragment fragment = VideoTabFragment.newInstance(mAdapter.getItem(position));

                // 这里是使用SharedElement的用例
                // LOLLIPOP(5.0)系统的 SharedElement支持有 系统BUG， 这里判断大于 > LOLLIPOP
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    setExitTransition(new Fade ());
                    fragment.setEnterTransition(new Fade());
                    fragment.setSharedElementReturnTransition(new DetailTransition());
                    fragment.setSharedElementEnterTransition(new DetailTransition());

                    // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
                    // 25.1.0+的support包，SharedElement正常
                    extraTransaction()
                            .addSharedElement(((RecyclerAdapter.VH) vh).img, "ll")
                            .addSharedElement(((RecyclerAdapter.VH) vh).tvTitle, "tv")
                            .start(fragment);
                } else {
                    start(fragment);
                }
            }
        });*/


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
            Data data = new Data (mid, titleStr, contentStr);
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

}
