package com.hl.afchelper;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import com.gyf.barlibrary.ImmersionBar;
import com.hl.afchelper.Until.ConfigUtil;
import com.hl.afchelper.Until.FileUtils;
import com.hl.afchelper.entity.db.DBManager;

import com.hl.afchelper.ui.fragment.MainFragment;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by huanglei on 2018/3/28.
 */

public class MainActivity extends SupportActivity implements EasyPermissions.PermissionCallbacks {
    protected ImmersionBar mImmersionBar;
    private final int WRITE_AND_MOUNT = 1;
    private static String COPY_SUCCESS = "copy_success";
    private boolean isCopySuccess;
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.me().refreshResources(this);
         //数据库操作
        DBManager.openDatabase(getApplicationContext ());
        if (!EasyPermissions.hasPermissions(getApplicationContext (), perms)) {
            EasyPermissions.requestPermissions(this, "视频播放需要读写外部存储，请授权", WRITE_AND_MOUNT, perms);
        }
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性


        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        isCopySuccess = ConfigUtil.getBoolean (COPY_SUCCESS, false);
        if (!isCopySuccess) {
            final ProgressDialog dialog = new ProgressDialog (MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
            dialog.setTitle("提示");
            dialog.setMessage("正在传输视频文件到SD卡");
            dialog.show();

            FileUtils.getInstance(getApplicationContext ()).copyAssetsToSD("video","AFCHelper").setFileOperateCallback(new FileUtils.FileOperateCallback(){
                @Override
                public void onSuccess() {
                    // TODO: 文件复制成功时，主线程回调
                    ConfigUtil.putBoolean (COPY_SUCCESS, true);
                    dialog.cancel();
                }

                @Override
                public void onFailed(String error) {
                    // TODO: 文件复制失败时，主线程回调
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
                    normalDialog.setTitle("注意");
                    normalDialog.setMessage("复制失败，请预留足够的存储空间后重试!");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                    System.exit (0);
                                }});
                    normalDialog.show();
                }
            });
            final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
            normalDialog.setTitle("使用说明");
            normalDialog.setMessage("本软件是AFC委外员工学习工具，内有地铁及设备重要数据，严禁将本软件发布至网络或转发给他人，如有违反，后果自负！");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                            dialog.cancel ();
                        }});
            normalDialog.show();
        }

    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        RefWatcher refWatcher = MyApplication.getRefWatcher (this);
        refWatcher.watch (this);
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator ();
    }


}
