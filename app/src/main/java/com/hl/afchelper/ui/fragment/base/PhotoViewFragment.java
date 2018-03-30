package com.hl.afchelper.ui.fragment.base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.adapter.HackyViewPager;
import com.hl.afchelper.adapter.RecyclerAdapter;
import com.hl.afchelper.entity.Data;
import com.hl.afchelper.entity.db.MyDBOpenHelper;
import com.hl.afchelper.listener.OnItemClickListener;
import com.hl.afchelper.ui.fragment.MainFragment;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class PhotoViewFragment extends SupportFragment{
    public static final String ARGS_IMGURLS = "imgUrls";
    public static final String ARGS_POSITION = "position";
    private View view;

    private List<View> guideViewList = new ArrayList<View>();

    private int startPos;
    private ArrayList<String> imgUrls;

    public static PhotoViewFragment newInstance(ArrayList<String> imageUrl, Integer position) {

        Bundle args = new Bundle();
        args.putStringArrayList (ARGS_IMGURLS,imageUrl);
        args.putInt (ARGS_POSITION,position);
        PhotoViewFragment fragment = new PhotoViewFragment ();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgUrls = getArguments ().getStringArrayList (ARGS_IMGURLS);
        startPos = getArguments ().getInt (ARGS_POSITION);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_photoview, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView (savedInstanceState);

        HackyViewPager viewPager = view.findViewById(R.id.pager);
        LinearLayout guideGroup = view.findViewById (R.id.guideGroup);

        ImageAdapter mAdapter = new ImageAdapter(getActivity ());
        mAdapter.setDatas(imgUrls);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < guideViewList.size(); i++) {
                    guideViewList.get(i).setSelected(i == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls);

    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if (imgUrls != null && imgUrls.size() > 0) {
            guideViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                View view = new View(getActivity ());
                view.setSelected(i == startPos);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    private class ImageAdapter extends PagerAdapter {

        private List<String> datas = new ArrayList<> ();
        private LayoutInflater inflater;
        private Context context;
        private ImageView smallImageView = null;

        public void setDatas(List<String> datas) {
            if (datas != null)
                this.datas = datas;
        }


        public ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from (context)
            ;
        }


        @Override
        public int getCount() {
            if (datas == null) return 0;
            return datas.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);

            if (view != null) {
                final PhotoView imageView = (PhotoView ) view.findViewById(R.id.image);

                imageView.setOnPhotoTapListener(new OnPhotoTapListener () {
                    @Override
                    public void onPhotoTap(ImageView view, float x, float y) {
                        getActivity().onBackPressed();
                    }
                });

                //长按事件
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                   @Override
                    public boolean onLongClick(View v) {
                       Toast.makeText (getActivity (),"已保存！",Toast.LENGTH_SHORT).show ();
                       return false;
                   }
                });


                //预览imageView
                smallImageView = new ImageView(context);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(50, 50);
                layoutParams.gravity = Gravity.CENTER;
                smallImageView.setLayoutParams(layoutParams);
                smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                ((FrameLayout) view).addView(smallImageView);


                //loading
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;

                final String imgurl = datas.get(position);

                RequestOptions options = new RequestOptions ()
                        .diskCacheStrategy (DiskCacheStrategy.ALL)
                        .error(R.drawable.load_error);

                Glide.with(getActivity ())
                        .load(imgurl)
                        .apply (options)
                        .into(imageView);

                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


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
