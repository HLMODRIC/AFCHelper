package com.hl.afchelper.ui.fragment.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.Until.ConfigUtil;
import com.hl.afchelper.base.BaseBackFragment;
import com.hl.afchelper.entity.Data;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;


public class ContentFragment extends BaseBackFragment implements View.OnTouchListener {

    private Data datas;
    private ArrayList<String> imgUrlList;
    private float x,y;
    private View view;
    private MarkdownView mMarkdownView;
    private static final String ARG_DATA = "arg_data";
    private static String THEME_KEY = "theme_mode";

    public static ContentFragment newInstance(Data data) {
        Bundle args = new Bundle ();
        args.putParcelable (ARG_DATA, data);
        ContentFragment fragment = new ContentFragment ();
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        datas = getArguments ().getParcelable (ARG_DATA);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.fragment_content, container, false);
        //初始化数据和布局
        MyApplication.me().refreshResources(getActivity ());
        initView ();
        mMarkdownView = view.findViewById (R.id.markdown_view);
        if (ConfigUtil.getBoolean (THEME_KEY, false)) {
            mMarkdownView.setBackgroundColor (Color.parseColor("#FF3F3F3F"));
        } else {
            mMarkdownView.setBackgroundColor (Color.parseColor("#FFFFFFFF"));
        }
        return attachToSwipeBack (view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView (savedInstanceState);
        //这里注意的是，因为我是在fragment中创建MyFragmentPagerAdapter，所以要传getChildFragmentManager()
        initWebView ();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd (savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿
        _mActivity.getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
    public void onDestroyView() {
        super.onDestroyView ();
        _mActivity.getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput ();
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        datas = null;
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

        imgUrlList = extractMessageByRegular (datas.getNew_content ());
       Toolbar mToolBar = view.findViewById (R.id.toolbar);
        setHasOptionsMenu(true);
        initToolbarNav (mToolBar);
       TextView mToolbarTitle = view.findViewById (R.id.toolbar_content_title);
        //Toolbar
        mToolbarTitle.setText (datas.getNew_title ());
        mToolBar.setTitle ("");
        //生成选项菜单
        mToolBar.inflateMenu (R.menu.list_menu);
        //设置溢出菜单的icon，显示、隐藏溢出菜单弹出的窗口
        mToolBar.showOverflowMenu ();
        ((AppCompatActivity ) getActivity()).setSupportActionBar(mToolBar);
    }

    /**
     * 初始化布局
     */
    private void initWebView() {
        InternalStyleSheet css = new Github ();
        if (ConfigUtil.getBoolean (THEME_KEY, false)) {
            css.addRule("body",  "font-size: 14.5px", "padding: 0px","color: #c8c8c8","background-color: #FF3F3F3F");
            } else {
            css.addRule("body", "font-size: 14.5px",  "padding: 0px");
        }

        css.addRule("table th", "padding: 2px 4px");
        css.addRule("table td", "padding: 2px 4px");
        css.addRule(".scrollup", "width: 40px", "height: 40px","background-color: #2196F3","bottom: 25px", "right: 25px");
        css.addRule ("h1","font-weight: 700");
        css.addRule ("h2","font-weight: 700");
        css.addRule ("h3","font-weight: 700");
        css.addRule ("h4","font-weight: 700");
        css.addRule ("h5","font-weight: 700");
        css.addRule ("h6","font-weight: 700");
        mMarkdownView.addStyleSheet(css);

        mMarkdownView.loadMarkdown(datas.getNew_content ());
        mMarkdownView.setWebViewClient(new WebViewClient (){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings settings = mMarkdownView.getSettings();
        settings.setJavaScriptEnabled(true); //支持JS
        mMarkdownView.addJavascriptInterface(new JsInterface(getContext()), "imageClick"); //JS交互
        mMarkdownView.setOnTouchListener(this);
    }

    //通过触控的位置来获取图片URL
    private void clickImage(float touchX, float touchY) {
        String js = "javascript:(function(){" +
                "var  obj=document.elementFromPoint("+touchX+","+touchY+");"
                +"if(obj.src!=null){"+ " window.imageClick.click(obj.src);}" +
                "})()";
        mMarkdownView.loadUrl(js);
    }

    //使用正则表达式提取中括号中的内容
    public static ArrayList<String> extractMessageByRegular(String msg){

        ArrayList<String> list= new ArrayList<> ();
        Pattern p = Pattern.compile("!\\[(.*?)\\]\\((.*?)\\)");
        Matcher m = p.matcher(msg);
        while(m.find()){
            list.add(m.group().substring(4, m.group().length()-1));
        }
        return list;
    }

    //返回url在ListPhotos中的下标位置
    private int getUrlPosition(String url) {
        for (int i = 0; i < imgUrlList.size(); i++) {
            if (url.equals(imgUrlList.get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float density = getResources().getDisplayMetrics().density; //屏幕密度
        float touchX = motionEvent.getX() / density;  //必须除以屏幕密度
        float touchY = motionEvent.getY() / density;
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            x = touchX;
            y = touchY;
        }

        if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            float dx = Math.abs(touchX-x);
            float dy = Math.abs(touchY-y);
            if(dx<10.0/density&&dy<10.0/density){
                clickImage(touchX,touchY);
            }
        }
        return false;
    }

    @Override
    protected void initToolbarNav(Toolbar toolbar) {
        super.initToolbarNav (toolbar);
    }

    //JS
    class JsInterface{
        Context context;
        JsInterface(Context context){
            this.context = context;
        }

        //查看图片url
        @JavascriptInterface
        public void click(String url){
            extraTransaction()
                    .start(PhotoViewFragment.newInstance (imgUrlList,getUrlPosition (url)));

        }
    }
}


