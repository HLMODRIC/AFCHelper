package com.hl.afchelper.ui.fragment.search;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.base.BaseMainFragment;
import com.hl.afchelper.ui.fragment.MainFragment;
import com.hl.afchelper.ui.fragment.base.ImageViewFragment;
import com.hl.afchelper.ui.fragment.base.ListFragment;
import com.squareup.leakcanary.RefWatcher;

public class SearchTabFragment extends BaseMainFragment {

    private View view;
    public static SearchTabFragment newInstance() {
        Bundle args = new Bundle ();
        SearchTabFragment fragment = new SearchTabFragment ();
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        MyApplication.me().refreshResources(getActivity ());
        initView ();
        return view;
    }

    /**
     * 初始化布局
     */
    private void initView(){
        Button searchButton = view.findViewById (R.id.search_bt_1);
        Button searchButton2 = view.findViewById (R.id.search_bt_2);
        Button searchButton3 = view.findViewById (R.id.search_bt_3);
        Button searchButton4 = view.findViewById (R.id.search_bt_4);
        Button searchButton5 = view.findViewById (R.id.search_bt_5);
        Button searchButton6 = view.findViewById (R.id.search_bt_6);
        Button searchButton7 = view.findViewById (R.id.search_bt_7);
        Button searchButton8 = view.findViewById (R.id.search_bt_8);
        Button searchButton9 = view.findViewById (R.id.search_bt_9);
        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_1), "select * from chs_code"));
            }
        });

        searchButton2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_2), "select * from bnr_code"));

            }
        });

        searchButton3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_3), "select * from line_marked where id < 100"));


            }
        });
        searchButton4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_4), "select * from mbc_code"));

            }
        });
        searchButton5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_5), "select * from card_code"));

            }
        });
        searchButton6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_6), "select * from line_marked where id between 100 and 199"));

            }
        });
        searchButton7.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_7), "select * from line_marked where id between 200 and 299"));

            }
        });
        searchButton8.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_8), "select * from ip_code"));

            }
        });
        searchButton9.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ListFragment.newInstance (getResources ().getString (R.string.search_button_text_9), "select * from line_marked where id between 300 and 350"));

            }
        });
       /* searchButton6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((MainFragment ) getParentFragment()).startBrotherFragment(ImageViewFragment.newInstance ("1.jpg"));

            }
        });*/
    }


    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
       refWatcher.watch(this);
    }
/*
    private void getData(String sql) {
        //db数据库
        MyDBOpenHelper dbHelper = new MyDBOpenHelper (getActivity ());
        //查询数据库
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase ();
        Cursor cursor = dbRead.rawQuery (sql, null);
        ArrayList<Data> datas = new ArrayList<> ();
        while (cursor.moveToNext ()) {
            String contentStr = cursor.getString (cursor.getColumnIndex ("content"));
            String titleStr = cursor.getString (cursor.getColumnIndex ("title"));
            int mid = cursor.getInt (cursor.getColumnIndex ("id"));
            Data data = new Data (mid,titleStr, contentStr);
            datas.add (data);
        }
        cursor.close ();
        dbHelper.close ();
        Intent intent=new Intent (getActivity (), ListActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList ("data", datas);
        bundle.putString ("table_name",tableName);
        intent.putExtras(bundle);
        bundle.clear ();
        startActivity (intent);
    }*/
}
