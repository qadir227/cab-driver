package com.cabdespatch.driverapp.beta.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;

import java.io.File;

public class Dialog_Update extends Dialog
{
    public interface OnUpdatePackageDownloadListener
    {
        public enum FAIL_REASON
        {
            COULD_NOT_DELETE_FILE,DOWNLOAD_FAILED;
        }

        public void onUpdatePackageDownloadComplete();
        public void onUpdatePackageDownloadFailed(FAIL_REASON _reason);
        public void onUpdatePackageDownloadCancelled();
    }

    private OnUpdatePackageDownloadListener oListener;

    public Dialog_Update(Context _c)
    {
        super(_c);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        this.setCancelable(false);

        this.setContentView(R.layout.dialog_update);

        class Downloader extends Thread
        {
            @Override
            public void run()
            {
                Boolean docont = true;

                File f = new File(Globals.getUpdateStorageLocation(Dialog_Update.this.getContext()), "update.apk");
                if(f.exists())
                {
                    if(!(f.delete()))
                    {
                        docont = false;
                        Dialog_Update.this.oListener.onUpdatePackageDownloadFailed(OnUpdatePackageDownloadListener.FAIL_REASON.COULD_NOT_DELETE_FILE);
                        Dialog_Update.this.dismiss();
                    }
                }

                if(docont)
                {
                    if(Globals.WebTools.getFileHTTP(f.getAbsolutePath(), Globals.getCabDespatchDataUrl() + "driver2.apk"))
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Globals.StartActivity(Dialog_Update.this.getContext(), intent);

                                Dialog_Update.this.oListener.onUpdatePackageDownloadComplete();
                        Dialog_Update.this.dismiss();
                    }
                    else
                    {
                        Dialog_Update.this.oListener.onUpdatePackageDownloadFailed(OnUpdatePackageDownloadListener.FAIL_REASON.DOWNLOAD_FAILED);
                        Dialog_Update.this.dismiss();
                    }
                }


            }
        }

        new Downloader().start();
    }

    public void setOnPackageDownloadListener(OnUpdatePackageDownloadListener _l)
    {
        this.oListener = _l;
    }

    @Override
    public void onBackPressed()
    {
        this.oListener.onUpdatePackageDownloadCancelled();
        this.dismiss();
    }

}
