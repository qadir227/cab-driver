package com.cabdespatch.driverapp.beta.activities2017;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import com.cabdespatch.driverapp.beta.CabDespatchWebViewClient;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;

import java.util.Locale;

public class DriverDocumentActivity extends WebViewActivity
{

    @Override
    protected String GetInitialUrl()
    {
        return "https://www.cabdespatch.com/support/Documents.aspx";
    }

    @Override
    protected CabDespatchWebViewClient GetWebViewClient()
    {
        return new CabDespatchWebViewClient()
        {
            @Override
            public boolean UseDefaultShowHideBehavior()
            {
                return false;
            }

            @Override
            public void onPageLoadStarted(WebView view, String url, Bitmap favicon)
            {
                Commands.HideWebView();
            }

            @Override
            public void onPageLoadFinished(WebView view, String url)
            {
                if(url.toLowerCase().endsWith("techsupport.aspx"))
                {
                    //Toast.makeText(DriverDocumentActivity.this, "This is a test", Toast.LENGTH_LONG).show();
                    populateLogins(view);
                }
                Commands.ShowWebView();
            }


            private ValueCallback<String> noCallbackRequired()
            {
                return new ValueCallback<String>()
                {
                    @Override
                    public void onReceiveValue(String value)
                    {
                        //Toast.makeText(DriverDocumentActivity.this, "JS Finished: " + value,
                                   //    Toast.LENGTH_LONG).show();
                    }
                };

            }

            private void populateLogins(WebView view)
            {
                String cid = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(DriverDocumentActivity.this);
                String dcs = SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(DriverDocumentActivity.this);
                Settable pin = SETTINGSMANAGER.SETTINGS.CIRCUIT_PIN;

                String js = "$('#txtLoginDriver_CompanyID').val('" + cid +"');";
                view.evaluateJavascript(js, noCallbackRequired());

                js = "$('#txtLoginDriver_DriverNumber').val('" + dcs +"');";
                view.evaluateJavascript(js, noCallbackRequired());

                if(pin.isSet(DriverDocumentActivity.this))
                {
                    js = "$('#txtLoginDriver_CircuitPIN').val('" + pin.getValue(DriverDocumentActivity.this) +"');";
                    view.evaluateJavascript(js, noCallbackRequired());

                    js = "$('#btnLoginDriver').click();";
                    view.evaluateJavascript(js, noCallbackRequired());
                    return;
                }

            }

            @Override
            public void onSslError(WebView view, SslErrorHandler handler, SslError error)
            {

            }
        };
    }

}
