/*
 * *
 *  * Created by Debarun Lahiri on 2/27/23, 1:22 AM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 2/26/23, 11:10 PM
 *
 */

package com.feelthecoder.viddownloader.facebookstorysaver;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.feelthecoder.viddownloader.R;
import com.feelthecoder.viddownloader.activities.MainActivity;
import com.feelthecoder.viddownloader.databinding.ActivityFacebookPrivateWebviewBinding;
import com.feelthecoder.viddownloader.utils.DownloadFileMain;
import com.feelthecoder.viddownloader.utils.iUtils;

import java.util.HashMap;
import java.util.Map;

public class FacebookPrivateWebview extends AppCompatActivity implements View.OnClickListener {


    private ActivityFacebookPrivateWebviewBinding binding;
    private Uri myvideoUrl;
    Map<String, String> extraHeaders = new HashMap<>();
    private int rand_int1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            binding = ActivityFacebookPrivateWebviewBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);
            extraHeaders.put("x-requested-with", "XMLHttpRequest");
            extraHeaders.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");


            binding.btnBackBrowse.setOnClickListener(this);
            binding.btnCloseBrowse.setOnClickListener(this);
            binding.btnHomeBrowse.setOnClickListener(this);
            binding.btnNextBrowse.setOnClickListener(this);
            binding.btnPasteUrl.setOnClickListener(this);
            binding.btnlogoutfb.setOnClickListener(this);


            webviewUtils();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    void webviewUtils() {

        binding.webViewFacebook.setVerticalScrollBarEnabled(false);
        binding.webViewFacebook.setHorizontalScrollBarEnabled(false);
        binding.webViewFacebook.getSettings().setJavaScriptEnabled(true);
        binding.webViewFacebook.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webViewFacebook.getSettings().setBuiltInZoomControls(true);
        binding.webViewFacebook.getSettings().setDisplayZoomControls(true);
        binding.webViewFacebook.getSettings().setUseWideViewPort(true);
        binding.webViewFacebook.getSettings().setLoadWithOverviewMode(true);
        binding.webViewFacebook.getSettings().setAllowFileAccess(true);
        binding.webViewFacebook.getSettings().setDefaultTextEncodingName("UTF-8");
        binding.webViewFacebook.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        binding.webViewFacebook.getSettings().setDatabaseEnabled(true);
        binding.webViewFacebook.getSettings().setBuiltInZoomControls(false);
        binding.webViewFacebook.getSettings().setSupportZoom(true);
        binding.webViewFacebook.getSettings().setUseWideViewPort(true);
        binding.webViewFacebook.getSettings().setDomStorageEnabled(true);
        binding.webViewFacebook.getSettings().setLoadsImagesAutomatically(true);
        binding.webViewFacebook.getSettings().setBlockNetworkImage(false);
        binding.webViewFacebook.getSettings().setBlockNetworkLoads(false);
        binding.webViewFacebook.getSettings().setLoadWithOverviewMode(true);

//        int rand_int1 = iUtils.getRandomNumber(iUtils.UserAgentsListLogin.length);
//        binding.webViewFacebook.getSettings().setUserAgentString(iUtils.UserAgentsListLogin[rand_int1]);

        rand_int1 = iUtils.getRandomNumber(2);

        if (rand_int1 == 0) {
            binding.webViewFacebook.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.0.2; SAMSUNG SM-G925F Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/4.0 Chrome/104.0.0.0 Safari/537.36");
        } else {
            binding.webViewFacebook.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Safari/537.36");
        }

        binding.webViewFacebook.addJavascriptInterface(this, "FBDownloader");
        binding.webViewFacebook.setWebViewClient(new WebViewClient() {
            public void onLoadResource(WebView webView, String str) {
                try {
                    binding.txtLinkWeb.setText(webView.getOriginalUrl());
                    if (webView.getOriginalUrl() != null) {
                        if (!webView.getOriginalUrl().equals("https://www.facebook.com/")) {

                            if (rand_int1 == 0) {
                                binding.webViewFacebook.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.0.2; SAMSUNG SM-G925F Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/4.0 Chrome/104.0.0.0 Safari/537.36");
                            } else {
                                binding.webViewFacebook.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Safari/537.36");
                            }
                        }
                        //  Hawk.put("ADDRESS_BROWSE", webView.getOriginalUrl());
                        if (binding.webViewFacebook.canGoBack()) {
                            binding.btnBackBrowse.setAlpha(1.0f);
                        } else {
                            binding.btnBackBrowse.setAlpha(0.5f);
                        }
                        if (binding.webViewFacebook.canGoForward()) {
                            binding.btnNextBrowse.setAlpha(1.0f);
                        } else {
                            binding.btnNextBrowse.setAlpha(0.5f);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                binding.webViewFacebook.loadUrl("javascript:(function prepareVideo() { var el = document.querySelectorAll('div[data-sigil]');for(var i=0;i<el.length; i++){var sigil = el[i].dataset.sigil;if(sigil.indexOf('inlineVideo') > -1){delete el[i].dataset.sigil;var jsonData = JSON.parse(el[i].dataset.store);console.log(el[i].dataset.store);el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\",\"'+jsonData['videoID']+'\");');}}})()");
                // binding.webViewFacebook.loadUrl("javascript: (function prepareVideo() {    var cl = document.querySelectorAll('div[class='_53mw _4gbu']');    var el = document.querySelectorAll('div[data-sigil]');    for (var i = 0; i < el.length; i++) {        var sigil = el[i].dataset.sigil;        if (sigil.indexOf('inlineVideo') > -1) {            delete el[i].dataset.sigil;            var jsonData = JSON.parse(el[i].dataset.store);            console.log(el[i].dataset.store);            el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"' + jsonData['src'] + '\",\"' + jsonData['videoID'] + '\");');            var buttonEl = document.createElement('a');            buttonEl.href = jsonData['src'];            var buttonTextEl = document.createElement('span');            buttonTextEl.className = 'picon-p-add-news';            buttonTextEl.innerText = 'DOWNLOAD_Video';            buttonEl.appendChild(buttonTextEl);            cl[i].appendChild(buttonEl);        }    }})()");
                binding.webViewFacebook.loadUrl("javascript:( window.onload=prepareVideo;)()");


            }

            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);

                System.out.println("srcissss = " + str);
                if (str.contains(".mp4?")) {
                    String substring = str.substring(str.lastIndexOf("/") + 1, str.lastIndexOf("."));
                    processVideo(str, substring);
                }

                if (binding.webViewFacebook.canGoBack()) {
                    binding.btnBackBrowse.setAlpha(1.0f);
                } else {
                    binding.btnBackBrowse.setAlpha(0.5f);
                }
                if (binding.webViewFacebook.canGoForward()) {
                    binding.btnNextBrowse.setAlpha(1.0f);
                } else {
                    binding.btnNextBrowse.setAlpha(0.5f);
                }
            }
        });
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieSyncManager.getInstance().startSync();
        binding.webViewFacebook.loadUrl("https://www.facebook.com/", extraHeaders);
    }


    @JavascriptInterface
    public void processVideo(String str, String str2) {
        try {
            myvideoUrl = Uri.parse(str);
            System.out.println("NAME_VIDEO=" + str2 + ".mp4");
            System.out.println("URL_VIDEO=" + str);


            if (!isFinishing()) {
                new AlertDialog.Builder(FacebookPrivateWebview.this)
                        .setTitle(getString(R.string.savedstt))
                        .setMessage(R.string.Areyousureyou_download)

                        .setPositiveButton(R.string.yes, (dialog, which) -> DownloadFileMain.startDownloading(FacebookPrivateWebview.this, myvideoUrl.toString(), str2, ".mp4"))

                        .setNegativeButton(R.string.negative_btn, null)
                        .setIcon(R.drawable.ic_appicon)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void onBackPressed() {
        try {
            if (binding.webViewFacebook.canGoBack()) {
                binding.webViewFacebook.goBack();
                return;
            }
            super.onBackPressed();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        try {


            switch (view.getId()) {
                case R.id.btnBackBrowse:
                    binding.webViewFacebook.goBack();
                    return;
                case R.id.btnCloseBrowse:
                    startActivity(new Intent(FacebookPrivateWebview.this, MainActivity.class));
                    finish();
                    return;
                case R.id.btnHomeBrowse:
                    binding.webViewFacebook.loadUrl("https://www.facebook.com/", extraHeaders);
                    return;
                case R.id.btnNextBrowse:
                    binding.webViewFacebook.goForward();
                    return;

                case R.id.btnlogoutfb:


                    if (!isFinishing()) {
                        new AlertDialog.Builder(FacebookPrivateWebview.this)
                                .setTitle(R.string.logout)
                                .setMessage(R.string.areyousure_logout)

                                .setPositiveButton(R.string.yes, (dialog, which) -> {

                                    binding.webViewFacebook.clearHistory();
                                    binding.webViewFacebook.clearFormData();
                                    binding.webViewFacebook.clearCache(true);
                                    CookieSyncManager.createInstance(FacebookPrivateWebview.this);
                                    CookieManager cookieManager = CookieManager.getInstance();
                                    cookieManager.removeAllCookie();

                                    binding.webViewFacebook.loadUrl("https://www.facebook.com/", extraHeaders);

                                })

                                .setNegativeButton(R.string.negative_btn, null)
                                .setIcon(R.drawable.ic_appicon)
                                .show();
                    }

                    return;
                case R.id.btnPasteUrl:
                    binding.txtNumberClipboard.setVisibility(View.GONE);

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    String text = clipboard.getText().toString();
                    binding.txtLinkWeb.setText(text);
                    binding.webViewFacebook.loadUrl(text, extraHeaders);
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            Log.d("error", "" + e.getLocalizedMessage());

        }
    }

}