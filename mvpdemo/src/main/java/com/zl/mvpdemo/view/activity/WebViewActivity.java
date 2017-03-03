package com.zl.mvpdemo.view.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.zl.mvpdemo.R;
import com.zl.mvpdemo.model.bean.GankData;
import com.zl.mvpdemo.presenter.impl.DBPresenter;
import com.zl.mvpdemo.presenter.presenter.IDBPresenter;
import com.zl.mvpdemo.view.widget.MarqueeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZL on 2017/2/23.
 */

public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_AUTHOR = "extra_author";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_GANK = "extra_gank";

    @BindView(R.id.web_webView)
    WebView webWebView;
    @BindView(R.id.title_textSwitcher_webView)
    TextSwitcher titleTextSwitcherWebView;
    @BindView(R.id.toolbar_webView)
    Toolbar toolbarWebView;
    @BindView(R.id.progressBar_webView)
    ContentLoadingProgressBar progressBarWebView;

    private String mUrl;
    private String mTitle;
    private GankData mGankData;

    private boolean isAuthor = false; //显示作者web

    private IDBPresenter<GankData> mDBPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarWebView);
        init();
    }

    private void init() {
        mGankData = (GankData) getIntent().getSerializableExtra(EXTRA_GANK);
        mUrl = mGankData.getUrl();
        mTitle = mGankData.getDesc();
        isAuthor = getIntent().getBooleanExtra(EXTRA_AUTHOR,false);

        mDBPresenter = new DBPresenter(this);

        titleTextSwitcherWebView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                MarqueeTextView textView = new MarqueeTextView(WebViewActivity.this);
                //textView.setTextAppearance(WebViewActivity.this, R.style.WebTitle);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                //textView.postDelayed(() -> textView.setSelected(true), 1738);
                return textView;
            }
        });

        if(mTitle!=null)titleTextSwitcherWebView.setText(mTitle);

        toolbarWebView.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbarWebView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        WebSettings settings = webWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webWebView.setWebChromeClient(new ChromeClient());
        webWebView.setWebViewClient(new LoveClient());

        if (mUrl != null) {
            webWebView.loadUrl(mUrl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:{

                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);
                share_intent.setType("text/plain");
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                share_intent.putExtra(Intent.EXTRA_TEXT, mTitle + ":" + mUrl);
                share_intent = Intent.createChooser(share_intent, "分享");
                startActivity(share_intent);
                break;
            }
            case R.id.action_collect:{
                if(isAuthor)break;
                mDBPresenter.saveToDB(mGankData);
                Toast.makeText(WebViewActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBPresenter.onDestory();
        if (webWebView != null) webWebView.destroy();
    }


    @Override
    protected void onPause() {
        if (webWebView != null) webWebView.onPause();
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (webWebView != null) webWebView.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webWebView.canGoBack()) {
                        webWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBarWebView.setProgress(newProgress);
            if (newProgress == 100) {
                progressBarWebView.setVisibility(View.GONE);
            } else {
                progressBarWebView.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleTextSwitcherWebView.setText(title);
        }
    }

    private class LoveClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            view.reload();
        }
    }

}
