package com.fittrack29.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.FrameLayout;
import android.graphics.Color;

public class MainActivity extends Activity {
  private WebView webView;
  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    getWindow().setStatusBarColor(Color.rgb(11,23,18));
    getWindow().setNavigationBarColor(Color.rgb(11,23,18));
    FrameLayout root = new FrameLayout(this);
    webView = new WebView(this);
    webView.setBackgroundColor(Color.rgb(11,23,18));
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setAllowFileAccess(true);
    webView.setWebViewClient(new WebViewClient());
    root.addView(webView, new FrameLayout.LayoutParams(-1,-1));
    setContentView(root);
    webView.loadUrl("https://lazyboy-2003.github.io/fittrack-29/");
  }
  @Override public void onBackPressed() {
    if (webView != null && webView.canGoBack()) webView.goBack();
    else super.onBackPressed();
  }
  @Override protected void onDestroy() {
    if (webView != null) { webView.destroy(); webView = null; }
    super.onDestroy();
  }
}