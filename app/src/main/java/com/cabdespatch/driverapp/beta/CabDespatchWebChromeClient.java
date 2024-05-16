package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class CabDespatchWebChromeClient extends WebChromeClient
{
    private final static int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri[]> mUploadMessage;

    private android.app.Activity Host;


    public  CabDespatchWebChromeClient(android.app.Activity host_activity)
    {
        Host = host_activity;
    }

    public ValueCallback<Uri[]> GetUploadMessage()
    {
        return  mUploadMessage;
    }

    public void SetReceivedFileVale(Uri[] item)
    {
        mUploadMessage.onReceiveValue(item);
    }

    public void ClearUploadMessage()
    {
        mUploadMessage = null;
    }


    // maneja la accion de seleccionar archivos
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

        // asegurar que no existan callbacks
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
        }

        mUploadMessage = filePathCallback;

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*"); // set MIME type to filter

        Host.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE );

        return true;
    }
}

