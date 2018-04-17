package com.hl.afchelper.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.adapter.HackyViewPager;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class ImageViewFragment extends SupportFragment{
    public static final String ARGS_IMGURLS = "imgUrls";
    private View view;
    private SubsamplingScaleImageView imageView;
    private String imgUrls;

    public static ImageViewFragment newInstance(String imageUrl) {

        Bundle args = new Bundle();
        args.putString (ARGS_IMGURLS,imageUrl);
        ImageViewFragment fragment = new ImageViewFragment ();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgUrls = getArguments ().getString (ARGS_IMGURLS);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_imageview, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView (savedInstanceState);
         imageView = view.findViewById(R.id.imageView);
        imageView.setImage(ImageSource.asset(imgUrls));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        imageView.recycle ();

    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        RefWatcher refWatcher = MyApplication.getRefWatcher (getActivity ());
        refWatcher.watch (this);
    }
    }
