package com.cabdespatch.driverapp.beta;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;

import com.cabdespatch.driverapp.beta.activities.ResumerActivity;
import com.cabdespatch.driverapp.beta.services.DataService;


public class NOTIFIERS 
{
	private class IDS
	{
		private static final int APP_RUNNING = 95;
	}

	private class CHANNELS
	{
		private static final String STANDARD = "REQUIRED_FOR_DATA_SERVICE";
		private static final String SOFTWARE_MANAGEMENT = "SOFTWARE_MANAGEMENT";
	}

	/*
	public static void showInstallCabLock(Context _c)
	{
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
		{
			NotificationChannel notificationChannel = new NotificationChannel(CHANNELS.STANDARD,
					"Software Management", NotificationManager.IMPORTANCE_LOW);


			NotificationManager m = (NotificationManager) _c.getSystemService(Context.NOTIFICATION_SERVICE);
			m.createNotificationChannel(notificationChannel);
		}

		String nTitle = _c.getResources().getString(R.string.notification_cab_lock_install_title);
		String nContent = _c.getResources().getString(R.string.notification_cab_lock_install_detail);

		RemoteViews contentView = new RemoteViews(_c.getPackageName(),
				R.layout.notification);

		contentView.setTextViewText(R.id.notification_title, nTitle);
		contentView.setTextViewText(R.id.notification_content, nContent);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(_c, CHANNELS.STANDARD)
						.setSmallIcon(R.drawable.n_icocar)
						.setLargeIcon(BitmapFactory.decodeResource(_c.getResources(), R.drawable.big_icon))
						.setContentTitle(nTitle)
						.setContentText(nContent)
						.setContent(contentView);




		PendingIntent pi = PendingIntent.getActivity(_c, 0, install, 0);

		mBuilder.setContentIntent(pi);
		NotificationManager m =
				(NotificationManager) _c.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification n = mBuilder.build();
		m.notify(123, n);
	}
	*/



	public static void showAppRunning(DataService _c)
	{
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
		{
			NotificationChannel notificationChannel = new NotificationChannel(CHANNELS.STANDARD,
					"Data Notifications", NotificationManager.IMPORTANCE_LOW);
			//notificationChannel.set

			NotificationManager m = (NotificationManager) _c.getSystemService(Context.NOTIFICATION_SERVICE);
			m.createNotificationChannel(notificationChannel);
		}
		//NOTIFICATION_SHOWING.putValue(_c, true);
		String nTitle = _c.getResources().getString(R.string.app_name);
		String nContent = _c.getResources().getString(R.string.app_still_running);

		RemoteViews contentView = new RemoteViews(_c.getPackageName(),
												   R.layout.notification);

		contentView.setTextViewText(R.id.notification_title, nTitle);
		contentView.setTextViewText(R.id.notification_content, nContent);

		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(_c, CHANNELS.STANDARD)
		        .setSmallIcon(R.drawable.n_icocar)
				.setLargeIcon(BitmapFactory.decodeResource(_c.getResources(), R.drawable.big_icon))
		        .setContentTitle(nTitle)
		        .setContentText(nContent)
				.setContent(contentView)
		.setOngoing(true);

		//NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle();

		Intent resultIntent = new Intent(_c, ResumerActivity.class);
		PendingIntent pi = PendingIntent.getActivity(_c, 0, resultIntent, PendingIntent.FLAG_IMMUTABLE);
				
		mBuilder.setContentIntent(pi);
		NotificationManager m =
		    (NotificationManager) _c.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification n = mBuilder.build();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			_c.startForeground(IDS.APP_RUNNING, n);
		}
		else
		{
			m.notify(IDS.APP_RUNNING, n);
		}

	}

	public static void reset(Context _c)
	{
		String nContent = _c.getResources().getString(R.string.app_still_running);
		updateText(_c, nContent);
	}

	public static void updateText(Context _c, String _text)
	{
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
		{
			NotificationChannel notificationChannel = new NotificationChannel(CHANNELS.STANDARD,
					"Data Notifications", NotificationManager.IMPORTANCE_LOW);
			//notificationChannel.set

			NotificationManager m = (NotificationManager) _c.getSystemService(Context.NOTIFICATION_SERVICE);
			m.createNotificationChannel(notificationChannel);
		}
		//NOTIFICATION_SHOWING.putValue(_c, true);
		String nTitle = _c.getResources().getString(R.string.app_name);
		//String nContent = _c.getResources().getString(R.string.app_still_running);

		RemoteViews contentView = new RemoteViews(_c.getPackageName(),
				R.layout.notification);

		contentView.setTextViewText(R.id.notification_title, nTitle);
		contentView.setTextViewText(R.id.notification_content, _text);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(_c, CHANNELS.STANDARD)
						.setSmallIcon(R.drawable.n_icocar)
						.setLargeIcon(BitmapFactory.decodeResource(_c.getResources(), R.drawable.big_icon))
						.setContentTitle(nTitle)
						.setContentText(_text)
						.setContent(contentView)
						.setOngoing(true);

		//NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle();

		Intent resultIntent = new Intent(_c, ResumerActivity.class);
		PendingIntent pi = PendingIntent.getActivity(_c, 0, resultIntent, PendingIntent.FLAG_IMMUTABLE);

		mBuilder.setContentIntent(pi);
		NotificationManager m =
				(NotificationManager) _c.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification n = mBuilder.build();
		m.notify(IDS.APP_RUNNING, n);

	}
	
	public static void hideAppRunning(DataService _c)
	{
        //NOTIFICATION_SHOWING.putValue(_c, false);
		NotificationManager m =
			    (NotificationManager) _c.getSystemService(Context.NOTIFICATION_SERVICE);


			m.cancel(IDS.APP_RUNNING);

	}
}
