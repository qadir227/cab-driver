package com.cabdespatch.driverapp.beta.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.activities2017.LoggedInHost;
import com.cabdespatch.driverapp.beta.cdToast;

import java.util.List;

public class ResumerActivity extends AnyActivity
{

    private static String lastBlockedUrl = "";

	@Override 
	public void onCreate(Bundle savedState)
    {
        super.onCreate(savedState);
        Intent i_in = this.getIntent();

        if (i_in.getAction() == null)
        {
            Intent i = new Intent(this, LoggedInHost.class);
            startActivity(i);
            //finish()
        }
        else
        {
            DEBUGMANAGER.Log(this, "RESUMER", i_in.getAction());
            if (i_in.getAction().equals(Intent.ACTION_VIEW))
            {
                String _url = i_in.getDataString();

                if (_url.contains("http"))
                {
                    Boolean lockdown = (!(SETTINGSMANAGER.getLockDownMode(this).equals(SETTINGSMANAGER.LOCK_DOWN_MODES.NONE)));
                    if (lockdown)
                    {
                        cdToast.showLong(this, R.string.warning_web_browsing_unavaliable, R.string.title_not_available);
                        SETTINGSMANAGER.addToDebugList(this, i_in.getDataString());

                       // finish();
                    } else
                    {

                        //JCmb!!!
                        //so the incoming Intent may well have ACTION_DEFAULT applied.

                        //calling intent.createChooser() on this intent will result in no
                        //chooser being created, because there is only one possible
                        //application to handle the intent - thus ResumerActivity just continually
                        //spams to the front of the screen

                        //so we need to make a NEW intent with the same action and data
                        //which will not have the ACTION_DEFAULT flag set

                        //additionally, we better check that there's even a browser installed
                        //because if there isn't, we'll have the same problem (only activity able to handle)
                        //the intent

                        //if the list of potential activities to perform the action is < 2 (knowing that
                        //we are result 1...) then simply don't bother showing a chooser


                        PackageManager pm = getPackageManager();

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(_url));

                        List<ResolveInfo> list = pm
                                .queryIntentActivities(i, 0);

                        if (list.size() < 2)
                        {
                            startActivity(i.createChooser(i, getString(R.string.title_view_website)));
                        } else
                        {
                            cdToast.showShort(this, R.string.no_browser_installed, R.string.title_not_available);
                        }

                        //finish();

                    }
                }
            }
            else
            {
                //action MAIN??
                Intent i = new Intent(this, LoggedInHost.class);
                startActivity(i);
            }

        }

        finish();
    }

    public static void resume(Context _c)
    {
        Intent i = new Intent(_c, ResumerActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _c.startActivity(i);
    }


}
