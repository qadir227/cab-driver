package com.cabdespatch.driverapp.beta.services;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;

import com.cabdespatch.driverapp.beta.CustomViewGroup;


public class UiBlockerService extends Service
{

	private CustomViewGroup oViewLock;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		//UiBlockerService.STOP = (!(Boolean.valueOf(SETTINGSMANAGER.SETTINGS.LOCK_DOWN.getValue(this))));
		
		//this.doCoolLock();
		
	/*	class CoolThread extends Thread
		{
			@Override
			public void run()
			{
				while (!(UiBlockerService.STOP))
				{
					try
					{
						Thread.sleep(5000);
												
					}
					catch(Exception ex)
					{
						
					}
				}
				
				UiBlockerService.this.removeLock();
				UiBlockerService.this.stopSelf();
				UiBlockerService.STOP = false;
				
			}
		}
		
		new CoolThread().start();*/
		
		return Service.START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private void removeLock()
	{
		WindowManager manager = ((WindowManager) getApplicationContext()
	            .getSystemService(Context.WINDOW_SERVICE));
		
		manager.removeView(this.oViewLock);
	}
	
	private void doCoolLock()
	{
		WindowManager manager = ((WindowManager) getApplicationContext()
	            .getSystemService(Context.WINDOW_SERVICE));

		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		localLayoutParams.gravity = Gravity.TOP;
		localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|

        // this is to enable the notification to recieve touch events
        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

        // Draws over status bar
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

	    localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
	    localLayoutParams.height = (int) (this.getStatusBarHeight());
	    //localLayoutParams.format = PixelFormat.TRANSPARENT;

	    
	    this.oViewLock = new CustomViewGroup(this);
	    manager.addView(this.oViewLock, localLayoutParams);
	}
	
	private int getStatusBarHeight() {
	    int result = 0;
	    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
	    if (resourceId > 0) {
	        result = getResources().getDimensionPixelSize(resourceId);
	    }
	    return result;
	}

}
