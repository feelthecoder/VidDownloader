/*
 * *
 *  * Created by Debarun Lahiri on 3/17/23, 11:37 PM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 3/17/23, 10:26 PM
 *
 */

package com.feelthecoder.viddownloader.extraFeatures;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.feelthecoder.viddownloader.databinding.ActivityTikTokWebviewBinding;
import com.feelthecoder.viddownloader.utils.Constants;
import com.feelthecoder.viddownloader.utils.DownloadFileMain;

public class TikTokWebview extends AppCompatActivity {

    String latsresult = "";
    private ActivityTikTokWebviewBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityTikTokWebviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        if (Build.VERSION.SDK_INT >= 24) {

            binding.webViewscan.clearFormData();

            CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webViewscan, true);
            binding.webViewscan.getSettings().setSaveFormData(true);
            binding.webViewscan.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 10;TXY567) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/8399.0.9993.96 Mobile Safari/599.36");
            binding.webViewscan.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            binding.webViewscan.setWebViewClient(new MyBrowserClient());

            binding.webViewscan.getSettings().setAllowFileAccess(true);

            binding.webViewscan.addJavascriptInterface(new MyJavaScriptInterface1(), "HTMLOUT");
            binding.webViewscan.getSettings().setJavaScriptEnabled(true);
            binding.webViewscan.getSettings().setDefaultTextEncodingName("UTF-8");
            binding.webViewscan.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            binding.webViewscan.getSettings().setDatabaseEnabled(true);
            binding.webViewscan.getSettings().setBuiltInZoomControls(false);
            binding.webViewscan.getSettings().setSupportZoom(true);
            binding.webViewscan.getSettings().setUseWideViewPort(true);
            binding.webViewscan.getSettings().setDomStorageEnabled(true);
            binding.webViewscan.getSettings().setAllowFileAccess(true);
            binding.webViewscan.getSettings().setLoadWithOverviewMode(true);
            binding.webViewscan.getSettings().setLoadsImagesAutomatically(true);
            binding.webViewscan.getSettings().setBlockNetworkImage(false);
            binding.webViewscan.getSettings().setBlockNetworkLoads(false);
            binding.webViewscan.getSettings().setLoadWithOverviewMode(true);


            binding.webViewscan.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {

//                    String nametitle = "tiktokvideo_" +
//                            System.currentTimeMillis();
//
//                    new downloadFile().Downloading(TikTokWebview.this, url, nametitle, ".mp4");


            });

            binding.webViewscan.setWebChromeClient(new WebChromeClient() {

                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && binding.progressBar.getVisibility() == View.GONE) {
                        binding.progressBar.setVisibility(View.VISIBLE);

                    }

                    binding.progressBar.setProgress(progress);
                    if (progress == 100) {
                        binding.progressBar.setVisibility(View.GONE);

                    }
                }
            });


            binding.webViewscan.loadUrl(Constants.tiktokWebviewUrl);
        } else {

            binding.webViewscan.clearFormData();
            binding.webViewscan.getSettings().setSaveFormData(true);


            CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webViewscan, true);
            binding.webViewscan.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 10;TXY567) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/8399.0.9993.96 Mobile Safari/599.36");
            binding.webViewscan.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            binding.webViewscan.setWebViewClient(new MyBrowserClient());

            binding.webViewscan.getSettings().setAllowFileAccess(true);

            binding.webViewscan.addJavascriptInterface(new MyJavaScriptInterface1(), "HTMLOUT");
            binding.webViewscan.getSettings().setJavaScriptEnabled(true);
            binding.webViewscan.getSettings().setDefaultTextEncodingName("UTF-8");
            binding.webViewscan.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            binding.webViewscan.getSettings().setDatabaseEnabled(true);
            binding.webViewscan.getSettings().setBuiltInZoomControls(false);
            binding.webViewscan.getSettings().setSupportZoom(false);
            binding.webViewscan.getSettings().setUseWideViewPort(true);
            binding.webViewscan.getSettings().setDomStorageEnabled(true);
            binding.webViewscan.getSettings().setAllowFileAccess(true);
            binding.webViewscan.getSettings().setLoadWithOverviewMode(true);
            binding.webViewscan.getSettings().setLoadsImagesAutomatically(true);
            binding.webViewscan.getSettings().setBlockNetworkImage(false);
            binding.webViewscan.getSettings().setBlockNetworkLoads(false);
            binding.webViewscan.getSettings().setLoadWithOverviewMode(true);


            binding.webViewscan.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {

//                    String nametitle = "tiktokvideo_" +
//                            System.currentTimeMillis();
//
//                    new downloadFile().Downloading(TikTokWebview.this, url, nametitle, ".mp4");


            });

            binding.webViewscan.setWebChromeClient(new WebChromeClient() {

                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && binding.progressBar.getVisibility() == View.GONE) {
                        binding.progressBar.setVisibility(View.VISIBLE);

                    }

                    binding.progressBar.setProgress(progress);
                    if (progress == 100) {
                        binding.progressBar.setVisibility(View.GONE);

                    }
                }
            });

            binding.webViewscan.loadUrl(Constants.tiktokWebviewUrl);
        }


        binding.downloadFAB.setOnClickListener(v -> {

            if (latsresult != null && !latsresult.equals("") && latsresult.contains("http")) {

                DownloadFileMain.startDownloading(TikTokWebview.this, latsresult, "TikTokWeb_" + System.currentTimeMillis(), ".mp4");

            }


        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    class MyJavaScriptInterface1 {
        @SuppressWarnings("unused")
        @JavascriptInterface
        public void showHTML(final String myurl, final String html) {
            if (!html.equals(latsresult)) {

                binding.downloadFAB.setVisibility(View.VISIBLE);
                latsresult = html;
                System.out.println("myhtml URL is =" + myurl);
                System.out.println("myhtml res =" + html);

            }
        }
    }


    private class MyBrowserClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {

            new Handler().postDelayed(() -> {
                //<a href="https://www.tiktok.com/@kamranalilail/video/6926337490991189250" class="jsx-179939359 jsx-2715883145 item-video-card-wrapper">

                binding.webViewscan.loadUrl("javascript:window.HTMLOUT.showHTML('" + url + "',''+document.getElementsByTagName('video')[0].getAttribute(\"src\"));");
                //   binding.webViewscan.loadUrl("javascript:window.HtmlViewer.showHTML('"+ url + "','<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

            }, 500);

        }

    }
}
