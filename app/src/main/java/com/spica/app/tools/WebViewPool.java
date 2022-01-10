package com.spica.app.tools;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import com.spica.app.R;
import java.util.ArrayList;
import java.util.List;

/**
 * webview 复用池
 */
public class WebViewPool {
  private static final String DEMO_URL = "https://www.baidu.com";
  private static final String APP_CACAHE_DIRNAME = "webCache";
  private static List<WebView> available = new ArrayList<>();
  private static List<WebView> inUse = new ArrayList<>();
  private static final byte[] lock = new byte[]{};
  private static int maxSize = 2;
  private int currentSize = 0;
  private static long startTimes = 0;
  private static volatile WebViewPool instance = null;

  public static WebViewPool getInstance() {
    if (instance == null) {
      synchronized (WebViewPool.class) {
        if (instance == null) {
          instance = new WebViewPool();
        }
      }
    }
    return instance;
  }

  /**
   * Webview 初始化
   * 最好放在application oncreate里
   */
  public static void init(Context context) {
    for (int i = 0; i < maxSize; i++) {
      WebView webView = new WebView(context);
      initWebSeting(context, webView);
      //webView.loadUrl(DEMO_URL);
      available.add(webView);
    }
  }

  /**
   * 获取webview
   */
  public WebView getWebView(Context context) {
    synchronized (lock) {
      WebView webView;
      if (available.size() > 0) {
        webView = available.get(0);
        available.remove(0);
        currentSize++;
        inUse.add(webView);
      } else {
        webView = new WebView(context);
        initWebSeting(context, webView);
        inUse.add(webView);
        currentSize++;
      }
      return webView;
    }
  }

  /**
   * 回收webview ,不解绑
   *
   * @param webView 需要被回收的webview
   */
  public void removeWebView(WebView webView) {
    webView.loadUrl("");
    webView.stopLoading();
    webView.setWebChromeClient(null);
    webView.setWebViewClient(null);
    webView.clearCache(true);
    webView.clearHistory();
    synchronized (lock) {
      inUse.remove(webView);
      if (available.size() < maxSize) {
        available.add(webView);
      } else {
      }
      currentSize--;
    }
  }

  /**
   * 回收webview ,解绑
   *
   * @param webView 需要被回收的webview
   */
  public void removeWebView(ViewGroup viewGroup, WebView webView) {
    viewGroup.removeView(webView);
    webView.loadUrl("");
    webView.stopLoading();
    webView.setWebChromeClient(null);
    webView.setWebViewClient(null);
    webView.clearCache(true);
    webView.clearHistory();
    synchronized (lock) {
      inUse.remove(webView);
      if (available.size() < maxSize) {
        available.add(webView);
      } else {
        webView = null;
      }
      currentSize--;
    }
  }

  /**
   * 设置webview池个数
   *
   * @param size webview池个数
   */
  public void setMaxPoolSize(int size) {
    synchronized (lock) {
      maxSize = size;
    }
  }

  private static void initWebSeting(Context context, WebView webView) {
    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    webView.setLayoutParams(params);
    WebSettings webSettings = webView.getSettings();
    //设置自适应屏幕，两者合用
    webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
    webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

    //缩放操作
    webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
    webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
    webSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件

    //其他细节操作
    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
    webSettings.setAllowFileAccess(true); //设置可以访问文件
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
    webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
    webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

    webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
    webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
    webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能

    String cacheDirPath = context.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
    webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

    //页面白屏问题
    webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    webView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
  }
}