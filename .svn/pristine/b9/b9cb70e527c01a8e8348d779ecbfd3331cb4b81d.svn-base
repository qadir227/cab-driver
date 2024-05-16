package com.cabdespatch.driverapp.beta.activities2017;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import android.view.View;
import android.view.WindowManager;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;
import com.cabdespatch.driverapp.beta.pdaLocation;
import com.cabdespatch.driverapp.beta.services.DataService;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Pleng on 15/02/2017.
 */

public abstract class DataActivity extends TickingActivity
{
    private MessageReceiver oMessageReceiver;

    private static Boolean IS_FINISHING;
    private Double Thresh = 0.0;
    private static Boolean isActivityFinishing()
    {
        return (IS_FINISHING==null?false:IS_FINISHING);
    }

    public static class DebugDataMessage
    {
        public static final int DIRECTION_IN = 0;
        public static final int DIRECTION_OUT = 1;

        private int oDirection;
        private String oMessage;

        public  DebugDataMessage(int _direction, String _data)
        {
            oDirection = _direction; oMessage = _data;
        }

        public int getDirection() { return  oDirection; }
        public String getData() { return oMessage; }
    }

    private static Queue<DebugDataMessage> sDataMessages = new LinkedList<>();


    public static void addDebugDataMessage(DebugDataMessage _message)
    {
        sDataMessages.offer(_message);
    }



    private class MessageReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            if(intent.getAction().equals(BROADCASTERS.DATA))
            {
                String message = intent.getStringExtra(DataService._MESSAGEDATA);

                if(message.equals(DataService._ACTIONS.NETWORK_ON))
                {
                    DataActivity.this.onNetworkStateChange(true);
                }
                else if(message.equals(DataService._ACTIONS.NETWORK_OFF))
                {
                    DataActivity.this.onNetworkStateChange(false);
                }
                else if(message.equals(DataService._ACTIONS.LOGOUT))
                {
                    //LoggedInActivity.this.finish();
                }
            }
            else if(intent.getAction().equals(BROADCASTERS.ACTIVITY_SWITCHER))
            {
				/*String activityTag = intent.getStringExtra(DataService._MESSAGEDATA);
				if(!(activityTag.equals(LoggedInActivity.this.getActivityTag())))
				{
					LoggedInActivity.this.finish();
				}*/
            }
            else
            {
                try
                {
                    DataActivity.this.onBroadcastReceived(context, intent);
                }
                catch (Exception ex)
                {
                    ErrorActivity.genericReportableError("Data Activity Broadcast Receipt Error", ex);
                }

            }

        }

    }




    protected abstract void showDriverMessage();
    protected abstract void onNetworkStateChange(Boolean _connected);
    protected abstract void onBroadcastReceived(Context _context, Intent _intent);

    private final void assessSafetyPanel()
    {
        if(Thresh > 0)
        {
            pdaLocation p = STATUSMANAGER.getCurrentLocation(this);

            if(p.getSpeed() > Thresh)
            {
                for (View v : getViewsByTag("safe_hide"))
                {
                    v.setVisibility(View.GONE);
                }
                for (View v : getViewsByTag("safe_show"))
                {
                    v.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                for (View v : getViewsByTag("safe_hide"))
                {
                    v.setVisibility(View.VISIBLE);
                }
                for (View v : getViewsByTag("safe_show"))
                {
                    v.setVisibility(View.GONE);
                }
            }

        }
    }



    @Override
    public void onCreate(Bundle _savledInstance)
    {
        super.onCreate(_savledInstance);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void finish()
    {
        IS_FINISHING = true;
        super.finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        IS_FINISHING = false;
        this.oMessageReceiver = new MessageReceiver();
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.DATA));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.ACTIVITY_SWITCHER));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.LOCATION_UPDATED));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.CARS_WORK_UPDATE));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.FUTURE_JOBS_UPDATE));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.PLOT_UPDATED));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.PRICE_UPDATED));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.JOB_STATUS_UPDATE));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.JOB_AMEND));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(BROADCASTERS.STATUS_UPDATE));
        this.registerReceiver(this.oMessageReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        Thresh = Double.valueOf(SETTINGSMANAGER.SETTINGS.SAFTEY_PANEL_SPEED_THRESH.getValue(this));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.unregisterReceiver(this.oMessageReceiver);
    }


    private long oTickCount = 0;

    @Override
    @CallSuper
    protected void onTick(long _millis)
    {
        if(isActivityFinishing())
        {
            //do nothing
        }
        else
        {
            oTickCount++;

            if(oTickCount%5==0)
            {
                doDriverMessageCheck();
            }

            if((oTickCount==1)||(oTickCount%10==0))
            {
                doDataServiceCheck();
            }

            //check on the 100th itteration and every 2000 after that
            if((oTickCount==100)||(oTickCount%2000==0))
            {
                //Log.e("CAKE", "CAKE CHECK");
                new checkforcake().start();
            }

            if(oTickCount%5==0)
            {
                assessSafetyPanel();
            }
        }
    }

    @Override
    protected void onPausedTick()
    {
        oTickCount++;
        if(oTickCount%10==0)
        {
            doDataServiceCheck();
        }
    }

    private class checkforcake extends Thread
    {

        @Override
        public void run()
        {
            super.run();
            Boolean hasCake = Globals.CrossFunctions.checkForPackageExist(DataActivity.this, "com.cabdespatch.debugconnecter");

            if(!(DataActivity.this==null))
            {
                STATUSMANAGER.putBoolean(DataActivity.this, STATUSMANAGER.STATUSES.HAS_DEBUG_CONNECTOR, hasCake);
            }

        }

    }


    private void doDataServiceCheck()
    {


        Boolean didOutput = false;
        while(sDataMessages.size()>0)
        {
            didOutput = true;
            onLogEvent("Messages since last data check...");
            DebugDataMessage m = sDataMessages.poll();
            String logmessage = (m.getDirection()==DebugDataMessage.DIRECTION_IN?"<-- ":"--> ");
            logmessage += m.getData();
            onLogEvent(logmessage);
        }
        if(!(didOutput))
        {
            onLogEvent("NO Messages since last data check...");
        }

        //onLogEvent("");


        if((DataService.isRunning()))
        {
            onLogEvent("Data Service is Active");
        }
        else
        {
            DataService.requestStart(this);


        }
        //onLogEvent("");
    }

    private Integer oldMessageCount = -1;
    private void doDriverMessageCheck()
    {
        Integer messagecount = STATUSMANAGER.getUnreadDriverMessages(DataActivity.this).size();

        if(messagecount > 0)
        {
                showDriverMessage();
        }
    }

    public abstract void onLogEvent(String _data);

}
