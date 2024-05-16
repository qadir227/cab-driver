package com.cabdespatch.driverapp.beta;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.widget.Toast;

import java.io.File;

public abstract class CabDespatchWebViewClient extends WebViewClient
{



    public final static int FILECHOOSER_RESULTCODE=1;

    public static interface CabDespatchWebViewCommandsListener
    {
        void ShowWebView(); void HideWebView();
    }
    private Activity Host;
    protected CabDespatchWebViewCommandsListener Commands;
    public void SetCabDespatchWebViewCommandsListener(Activity activity, CabDespatchWebViewCommandsListener commands)
    {
        Host = activity;
        Commands = commands;
    }

    public abstract boolean UseDefaultShowHideBehavior();

    public abstract void onPageLoadStarted(WebView view, String url, Bitmap favicon);
    public abstract void onPageLoadFinished(WebView view, String url);
    public abstract void onSslError(WebView view, SslErrorHandler handler, SslError error);

    @Override
    public final void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
    {
        onSslError(view, handler, error);
    }

    @Override
    public final void onPageStarted(WebView view, String url, Bitmap favicon)
    {
        if(UseDefaultShowHideBehavior())
        {
            Commands.HideWebView();
        }

        onPageLoadStarted(view, url, favicon);
    }

    @Override
    public final void onPageFinished(WebView view, String url)
    {
        super.onPageFinished(view, url);
        if(UseDefaultShowHideBehavior())
        {
            Commands.ShowWebView();
        }
        onPageLoadFinished(view, url);
    }



    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //final Uri uri = Uri.parse(url);
        return false;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final String uri = request.getUrl().toString();
        return shouldOverrideUrlLoading(view, uri);
    }


}
