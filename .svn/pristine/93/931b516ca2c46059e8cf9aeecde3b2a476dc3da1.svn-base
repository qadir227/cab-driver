package com.cabdespatch.driverapp.beta;


import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Method;

public class cabdespatchServiceDetectors 
{
	public enum NETWORKCONNECTIONTYPE 
	{
		WIFI,GPRS3G,NONE,NULL;
	}

    public static NETWORKCONNECTIONTYPE getNetworkConnectionType(Context c)
    {

        NETWORKCONNECTIONTYPE conType = NETWORKCONNECTIONTYPE.NONE;

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni==null)
        {
            conType = NETWORKCONNECTIONTYPE.NULL;
        }
        else if (ni.getTypeName().equalsIgnoreCase("WIFI"))
        {
            if (ni.isConnected())  {conType = NETWORKCONNECTIONTYPE.WIFI;}
        }
        else if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
        {
            if (ni.isConnected()) { conType = NETWORKCONNECTIONTYPE.GPRS3G; }
        }

        return conType;

    }
	
	public static boolean isGPSEnabled(Context c)
	{
		final LocationManager manager = (LocationManager) c.getSystemService( Context.LOCATION_SERVICE );
	    return (manager.isProviderEnabled( LocationManager.GPS_PROVIDER ));
	}

    public static boolean isMobileDataEnabled(Context c)
    {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean)method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }

        return mobileDataEnabled;
    }

    public static boolean isWiFiEnabled(Context _context)
    {
        WifiManager wifi =(WifiManager)_context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    public static boolean areMockLocationsEnabled(Context _c)
    {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            return false;
        }
        else
        {
            if (Settings.Secure.getString(_c.getContentResolver(),
                    Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
                return false;
            else return true;
        }
    }
}
