package com.hl.afchelper.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.hl.afchelper.R;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoPlayerStandard extends JZVideoPlayerStandard {
    public VideoPlayerStandard(Context context) {
        super (context);
    }

    public VideoPlayerStandard(Context context, AttributeSet attrs) {
        super (context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_standard;
    }

    @Override
    public void onClick(View v) {
        if (v.getId () == R.id.back){
            JZVideoPlayer.SAVE_PROGRESS = false;
            JZVideoPlayer.backPress();
        }
        super.onClick (v);
    }
}
