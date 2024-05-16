package com.cabdespatch.driverapp.beta.dialogs;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.cdToast;

public class Dialog_InstallCabLock extends PseudoDialog implements View.OnClickListener
{
	public static long DOWNLOAD_ID;

	public Dialog_InstallCabLock(Context _c, ViewGroup _container)
	{
		super(_c, _container, R.layout.dialog_installcablock);

		View btnDownload = findViewById(R.id.btnDownloadNow);
		btnDownload.setOnClickListener(this);

		View btnShowDownloads = findViewById(R.id.btnShowDownloads);
		btnShowDownloads.setOnClickListener(this);
	}

	private void startDownload()
	{
		DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
		Uri Download_Uri = Uri.parse("https://www.cabdespatch.com/android/cablock.apk");

		DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
		request.setAllowedOverRoaming(false);
		request.setTitle(getContext().getString(R.string.cablock_name));
		request.setVisibleInDownloadsUi(true);
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/CabDespatch/"  + "/" + "cablocak" + ".apk");

		DOWNLOAD_ID = downloadManager.enqueue(request);
	}

	public static void showDownloads(Context _context)
	{
		_context.startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
		cdToast.setTempBottomGravity();
		cdToast.showLong(_context, R.string.install_cablack_on_show_downloads_prompt);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnDownloadNow:
				startDownload();
				break;
			case R.id.btnShowDownloads:
				showDownloads(getContext());
				break;
		}
	}
}
