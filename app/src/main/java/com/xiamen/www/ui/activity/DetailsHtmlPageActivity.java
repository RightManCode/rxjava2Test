package com.xiamen.www.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.xiamen.www.R;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by admin on 2018/2/6.
 */

public class DetailsHtmlPageActivity extends ToolbarBaseActivity {
    private WebView webview;
    private ProgressBar pb;
    private Map<Integer, Integer> index = new HashMap<>();
    private String imgUrl = "";

    @Nullable
    @Override
    public CharSequence getSubTitle() {
        return "我的GitHub,欢迎Star";
    }

    @Override
    public void initDoSomething() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        webview = findViewById(R.id.webview);
        pb = findViewById(R.id.pb);
        init(webview);
        String url = getIntent().getStringExtra("url");
        if (url != null && !"".equals(url)) {
            webview.loadUrl(url);
        } else {
            Toast.makeText(this, "暂未开放", Toast.LENGTH_SHORT).show();
        }
        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                int type = result.getType();
                if (type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    imgUrl = result.getExtra();
                    setDataPopup(imgUrl);
                }
                return false;
            }
        });
    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.detailspagel;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(final WebView webView) {
        webview.requestFocusFromTouch();
        WebSettings settings = webView.getSettings();
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        //缩放开关，仅仅支持双击缩放
        settings.setSupportZoom(true);
        //设置是否可缩放，会出现缩放工具
        settings.setBuiltInZoomControls(true);
        //隐藏缩放工具
        settings.setDisplayZoomControls(false);
        //WebView.setWebContentsDebuggingEnabled(true);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                pb.setVisibility(View.VISIBLE);
                pb.setProgress(progress);
                if (progress == 100)
                    pb.setVisibility(View.GONE);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                //加这段可以证webview中的alert弹出来
                return super.onJsAlert(view, url, message, result);
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }


        });
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.setAcceptFileSchemeCookies(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        webView.setWebViewClient(setWebViewClient());
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemNavigationBar();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 加入竖屏要处理的代码
            showSystemNavigationBar();
        }
    }

    private void showSystemNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void hideSystemNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    public void onBackPressed() {
        try {
            if (webview.canGoBack()) {
                Log.e("cww", "canBack");
                int currentIndex = webview.copyBackForwardList().getCurrentIndex();
                if (currentIndex == 1 && index.get(0) > 1) {
                    finish();
                } else if (index.get(currentIndex) > 1) {
                    while (currentIndex >= 0 && index.get(currentIndex) > 0) {
                        currentIndex--;
                    }
                    webview.goBackOrForward(currentIndex - 1);
                } else {
                    webview.goBack();
                }
            } else {
                Log.e("cww", "NotcanBack");
                finish();
            }
        } catch (Exception e) {
            finish();
        }
    }

    public static void clearCookies(Context context) {
        @SuppressWarnings("unused")
        CookieSyncManager cookieSyncMngr =
                CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.removeAllViews();
            webview.destroy();
        }
        clearCookies(this);

    }

    private WebViewClient setWebViewClient() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView wv, String url) {
                if (url == null) return false;

                try {
                    if (url.startsWith("weixin://") //微信
                            || url.startsWith("alipays://") //支付宝
                            || url.startsWith("mailto://") //邮件
                            || url.startsWith("tel://")//电话
                            || url.startsWith("dianping://")//大众点评
                            || url.startsWith("mqqapi://")//QQ钱包
                        //其他自定义的scheme
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        Log.e("cww", "url1" + url);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.getSettings().setBlockNetworkImage(false);
                int currentIndex = view.copyBackForwardList().getCurrentIndex();
                boolean b = index.containsKey(currentIndex);
                if (b) {
                    int i = index.get(currentIndex) + 1;
                    index.put(currentIndex, i);
                } else {
                    index.put(currentIndex, 1);
                }
            }

        };
        return webViewClient;
    }


    public void setDataPopup(final String imgUrl) {
        final String fileName1 = UUID.randomUUID().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("点击确定保存图片");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveImage(imgUrl, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/", fileName1 + ".png");
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void saveImage(String imgUrl, String destFileDir, String destFileName) {
        Rx2AndroidNetworking.download(imgUrl, destFileDir, destFileName)
                .setTag("saveImage")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        Log.e("cww", "图片正在下载");
                    }
                }).startDownload(new com.androidnetworking.interfaces.DownloadListener() {
            @Override
            public void onDownloadComplete() {
                Log.e("cww", "保存图片成功！");
            }

            @Override
            public void onError(ANError anError) {
                Log.e("cww", "保存图片失败！");
            }
        });
    }


    private final static int FILE_CHOOSER_RESULT_CODE = 10000;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }
}
