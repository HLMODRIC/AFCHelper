package com.hl.afchelper.ui.fragment.search;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.base.BaseMainFragment;
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
        //initData ();
        //initView ();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData(){}
    /**
     * 初始化布局
     *//*
    private void initView(){
        Button searchButton = view.findViewById (R.id.search_bt_1);
        Button searchButton2 = view.findViewById (R.id.search_bt_2);
        Button searchButton3 = view.findViewById (R.id.search_bt_3);
        Button searchButton4 = view.findViewById (R.id.search_bt_4);
        Button searchButton5 = view.findViewById (R.id.search_bt_5);
        Button searchButton6 = view.findViewById (R.id.search_bt_6);
        final SearchDataHelper searchDataHelper = new SearchDataHelper ();
        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.search_button_text_1);
                getData (searchDataHelper.ShowData (tableName));
            }
        });

        searchButton2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.search_button_text_2);
                getData (searchDataHelper.ShowData (tableName));
            }
        });

        searchButton3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.search_button_text_3);
                getData (searchDataHelper.ShowData (tableName));

            }
        });
        searchButton4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.search_button_text_4);
                getData (searchDataHelper.ShowData (tableName));

            }
        });
        searchButton5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.search_button_text_5);
                getData (searchDataHelper.ShowData (tableName));

            }
        });
        searchButton6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                tableName = getResources ().getString (R.string.search_button_text_6);
                getData (searchDataHelper.ShowData (tableName));

            }
        });
    }
*/
    @Override public void onDestroy() {
        super.onDestroy();
        System.gc ();
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
