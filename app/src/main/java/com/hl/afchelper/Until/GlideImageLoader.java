package com.hl.afchelper.Until;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hl.afchelper.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by huanglei on 2018/3/12.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        RequestOptions options = new RequestOptions ()
                .diskCacheStrategy (DiskCacheStrategy.ALL)
                .error(R.drawable.load_error);

        Glide.with(context)
                .load(path)
                .apply (options)
                .into(imageView);
    }
}
