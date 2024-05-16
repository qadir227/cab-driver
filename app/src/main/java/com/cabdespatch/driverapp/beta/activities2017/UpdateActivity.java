package com.cabdespatch.driverapp.beta.activities2017;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.webkit.MimeTypeMap;

import com.cabdespatch.driverapp.beta.BuildConfig;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_Update;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.dialog_update);

        Intent i = generateCustomChooserIntent(this, Globals.getCabDespatchDataUrl());
        if (i==null)
        {
            cdToast.showLong(this, R.string.no_browser_installed);
        }
        else
        {
            startActivity(i);
        }
        finish();
    }

    public Intent generateCustomChooserIntent(Context context, String url)
    {
        Uri fakeUri = Uri.parse("https://www.google.com");
        Uri realUri = Uri.parse(url);
        Intent shareIntent = new Intent(Intent.ACTION_VIEW, fakeUri);
        List<ResolveInfo> resInfo;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
        {
            resInfo = context.getPackageManager().queryIntentActivities(
                    shareIntent, PackageManager.MATCH_ALL);
        }
        else
        {
            resInfo = context.getPackageManager().queryIntentActivities(
                    shareIntent, 0);
        }

        if (!resInfo.isEmpty())
        {
            List<Intent> targetedShareIntents = removeCurrentApp(context, realUri, resInfo);

            if (!targetedShareIntents.isEmpty())
            {
                // pass new Intent to create no chooser in first row
                Intent chooserIntent = Intent.createChooser(
                        targetedShareIntents.get(0), "Open link with");
                targetedShareIntents.remove(0);

                // pass extra intent chooser
                chooserIntent.putExtra(
                        Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[targetedShareIntents.size()]));
                return chooserIntent;
            }
        }

        // there is no appropriate intent to run
        return  null;
    }

    private List<Intent> removeCurrentApp(Context context, Uri realUri, List<ResolveInfo> resInfo) {
        List<Intent> targetedShareIntents = new ArrayList<>();
        String currentPackageName = context.getPackageName();
        for (ResolveInfo resolveInfo : resInfo) {
            // do not include my app in intent chooser dialog
            if (resolveInfo.activityInfo == null) {
                continue;
            }
            String packageName = resolveInfo.activityInfo.packageName;
            if (currentPackageName.equalsIgnoreCase(packageName)) {
                continue;
            }
            else if(packageName.toLowerCase().contains("webview"))
            {
                continue;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, realUri);

            intent.setClassName(
                    resolveInfo.activityInfo.applicationInfo.packageName,
                    resolveInfo.activityInfo.name);
            intent.setPackage(packageName);
            targetedShareIntents.add(intent);
        }
        return targetedShareIntents;
    }
}



        /*
        class Downloader extends Thread
        {
            @Override
            public void run()
            {
                Boolean docont = true;
/*
                String url = Globals.getCabDespatchDataUrl() + "driver.apk";
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setDescription("Update For Cab Despatch");
                request.setTitle("Drivers App");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.apk");

// get download service and enqueue file
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);

                */
                /*
                File f = new File(Globals.getUpdateStorageLocation(UpdateActivity.this), "driver.apk");
                if(f.exists())
                {
                    if(!(f.delete()))
                    {
                        docont = false;
                        UpdateActivity.this.onUpdatePackageDownloadFailed(Dialog_Update.OnUpdatePackageDownloadListener.FAIL_REASON.COULD_NOT_DELETE_FILE);
                        //UpdateActivity.this.dismiss();
                    }
                }

                if(docont)
                {
                    if(Globals.WebTools.getFileHTTP(f.getAbsolutePath(), Globals.getCabDespatchDataUrl() + "driver.apk"))
                    {
                        UpdateActivity.this.onUpdatePackageDownloadComplete(f);
                    }
                    else
                    {
                        UpdateActivity.this.onUpdatePackageDownloadFailed(Dialog_Update.OnUpdatePackageDownloadListener.FAIL_REASON.DOWNLOAD_FAILED);
                        //Dialog_Update.this.dismiss();
                    }
                }


            }
        }

        new Downloader().start();*/

/*
    public void onUpdatePackageDownloadComplete(File f)
    {
        /*
    }
        cdToast.showLong(this, "complete");
        Uri apkUri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".provider", f);
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setData(apkUri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    };
    public void onUpdatePackageDownloadFailed(Dialog_Update.OnUpdatePackageDownloadListener.FAIL_REASON _reason)
    {
        cdToast.showLong(this, "failed");
    };
    public void onUpdatePackageDownloadCancelled()
    {

    };*/