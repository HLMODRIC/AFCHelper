package com.hl.afchelper.ui.fragment.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hl.afchelper.MyApplication;
import com.hl.afchelper.R;
import com.hl.afchelper.adapter.RecyclerAdapter;
import com.hl.afchelper.base.BaseBackFragment;
import com.hl.afchelper.entity.Data;
import com.hl.afchelper.ui.view.MyToolBar;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ContentFragment extends BaseBackFragment implements View.OnTouchListener {

    private Data datas;
    private List<String> imgUrlList;
    private float x,y;
    private View view;
    private MarkdownView mMarkdownView;
    private MyToolBar mMyToolBar;
    private static final String ARG_DATA = "arg_data";

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
        initData ();
        initView ();
        initWebView ();
        return attachToSwipeBack (view);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd (savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿

        _mActivity.getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
        mMarkdownView = view.findViewById (R.id.markdown_view);
        imgUrlList = extractMessageByRegular (datas.getNew_content ());
    }

    /**
     * 初始化布局
     */
    private void initWebView() {
        InternalStyleSheet css = new Github ();
        css.addRule("table th", "padding: 2px 4px");
        css.addRule("table td", "padding: 2px 4px");
        css.addRule("body",  "padding: 0px");
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
    public static List<String> extractMessageByRegular(String msg){

        List<String> list= new ArrayList<> ();
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

    //JS
    class JsInterface{
        Context context;
        JsInterface(Context context){
            this.context = context;
        }

        //查看图片url
        @JavascriptInterface
        public void click(String url){
            //

        }
    }
}

