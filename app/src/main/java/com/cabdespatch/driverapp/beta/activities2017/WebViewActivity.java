package com.cabdespatch.driverapp.beta.activities2017;

import static com.cabdespatch.driverapp.beta.CabDespatchWebViewClient.FILECHOOSER_RESULTCODE;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cabdespatch.driverapp.beta.CabDespatchWebChromeClient;
import com.cabdespatch.driverapp.beta.CabDespatchWebViewClient;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;

public abstract class WebViewActivity extends AnyActivity implements CabDespatchWebViewClient.CabDespatchWebViewCommandsListener
{


    private CabDespatchWebChromeClient ChromeClient;

    protected abstract String GetInitialUrl();

    @Override
    public void ShowWebView()
    {
        Overlay.setVisibility(View.GONE);
    }

    @Override
    public void HideWebView()
    {
        Overlay.setVisibility(View.VISIBLE);
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==FILECHOOSER_RESULTCODE)
        {
            if (null == Webclient.GetUploadMessage()) return;
            Uri result = intent == null || resultCode != RESULT_OK ? null
                                                                   : intent.getData();
            Webclient.GetUploadMessage().onReceiveValue(result);
            Webclient.ClearUploadMessage();
        }
    }*/


    ViewGroup Overlay;

    protected abstract CabDespatchWebViewClient GetWebViewClient();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView web = findViewById(R.id.web);

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        CabDespatchWebViewClient webclient = GetWebViewClient();
        webclient.SetCabDespatchWebViewCommandsListener(this, this);
        web.setWebViewClient(webclient);

        ChromeClient = new CabDespatchWebChromeClient(this);
        web.setWebChromeClient(ChromeClient);


        Overlay = findViewById(R.id.overlay);
        Overlay.setAlpha(0.5f);

        HideWebView();
        web.loadUrl(GetInitialUrl());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        // manejo de seleccion de archivo
        if (requestCode == FILECHOOSER_RESULTCODE) {

            if (null == ChromeClient.GetUploadMessage() || intent == null || resultCode != RESULT_OK) {
                return;
            }

            Uri[] result = null;
            String dataString = intent.getDataString();

            if (dataString != null) {
                result = new Uri[]{ Uri.parse(dataString) };
            }

            ChromeClient.SetReceivedFileVale(result);
            ChromeClient.ClearUploadMessage();
        }
    }



}