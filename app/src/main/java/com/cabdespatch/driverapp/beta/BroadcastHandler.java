package com.cabdespatch.driverapp.beta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastHandler extends BroadcastReceiver
{
/*
 * You must register any actions this broadcaster can handle in the
 * manifest. ex:
 * <action android:name="com.cabdespatch.driverapp.HELLO"/>
 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
 */

    public static String FORCE_ACTIVITY_SWITCH = "FORCE_ACTIVITY_SWITCH";
    public static String CORRECT_PACKAGE_CHECK;

    private class IntentWrapper
    {
        private Intent oIntent;
        private Boolean oIsBroadcast;
        private String oActivity;

        public IntentWrapper(Intent _i, Boolean _isBroadcast, String _activity)
        {
            oIntent = _i;
            oIsBroadcast = _isBroadcast;
            oActivity = _activity;
        }

        public Intent getIntent() { return oIntent; }
        public Boolean isBroadcast() { return oIsBroadcast; }
        public String getActivity() { return  oActivity; }
    }

    private static Intent pendingIntent;

    @Override
    public void onReceive(Context _context, Intent _intent)
    {
/*
        Boolean forceSwitch;

        //CLAY this is sometimes being passed in as a string... we need to have a look at this!!!
        //Boolean forceSwitch = _intent.getBooleanExtra(FORCE_ACTIVITY_SWITCH, false);
        try
        {
            forceSwitch= Boolean.valueOf(_intent.getStringExtra(FORCE_ACTIVITY_SWITCH));
        }
        catch (Exception ex)
        {
            forceSwitch = false;
            Log.e("CLAY", "CLAY!!!");
        }
        if((_intent.getAction().equals(BROADCASTERS.LOCK_RELEASE) && (!forceSwitch)))
        {
            //make sure this build flavor is the intended variant
            if(_intent.getStringExtra(CORRECT_PACKAGE_CHECK).equals(MorphStrings.BROADCAST_HEADER))
            {
                if(pendingIntent==null)
                {
                    //Log.e("NULL", "PENDING INTENT IS NULL");
                    //do nothing
                }
                else
                {
                    //Debug.waitForDebugger();
                    handleActivitySwitch(_context, pendingIntent, forceSwitch);
                    pendingIntent = null;
                }
            }
        }
        else
        {
            if(_intent.getAction().equals(BROADCASTERS.ACTIVITY_SWITCHER))
            {
                //DEBUGMANAGER.Log(_context, "SLI", "handle activity switch");
                if(_intent.getStringExtra(CORRECT_PACKAGE_CHECK).equals(MorphStrings.BROADCAST_HEADER))
                {
                    handleActivitySwitch(_context, _intent, forceSwitch);
                }
            }

        }*/
    }

    private void doActivitySwitch(Context _context, Intent _intent, Boolean _force)
    {
        /*
        String messagetype = _intent.getStringExtra(DataService._MESSAGETYPE);
        String messagedata = _intent.getStringExtra(DataService._MESSAGEDATA);

        if (messagetype==null)
        {
            //do nothing
        }
        else if(messagetype.equals(DataService._MESSAGETYPES.ACTIVITY_SWITCH))
        {
            //Log.w("BROADCAST", messagetype);
            //CLAY
            String previousActivity = STATUSMANAGER.getString(_context, STATUSMANAGER.STATUSES.CURRENT_ACTIVITY);
            Boolean forceSwitch = Boolean.valueOf(_intent.getStringExtra(BroadcastHandler.FORCE_ACTIVITY_SWITCH));
            forceSwitch = (forceSwitch || _force);



            if (previousActivity.equals(messagedata))
            {
                if (!(forceSwitch))
                {
                    if (!(previousActivity.equals(DataService._ACTIVITIES.DRIVER_MESSAGE)))
                    {
                        //if it's a driver message we need send the broadcast
                        //otherwise exit
                        return;
                    }
                    else
                    {
                        //we no longer re-send broadcasts as drivermessage screen
                        //can only handle one message at a time

                        //code kept in for now in case of future bug hints
                        return;
                    }
                }
            }


           //handled with StatusManager.AquireLock()
           cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
            if (j.getJobStatus().equals(cabdespatchJob.JOB_STATUS.UNDER_OFFER))
            {
                if (!(messagedata.equals(DataService._ACTIVITIES.JOB_OFFER)))
                {
                    //never switch activities when under offer
                    return;
                }
                else if (previousActivity.equals(DataService._ACTIVITIES.JOB_OFFER))
                {
                    return;
                }
            }


            if((STATUSMANAGER.isLocked() && (!(forceSwitch))))
            {
                pendingIntent = new Intent(_intent.getAction());
                pendingIntent.setData(_intent.getData());
                pendingIntent.putExtras((Bundle) _intent.getExtras().clone());
            }
            else
            {
                DEBUGMANAGER.Log(_context, "Force Switch");
                IntentWrapper w = processMessageData(_intent, _context, messagedata);
                processActivitySwitchIntent(_context, w);
            }


        }
        */
    }

    private void handleActivitySwitch(Context _context, Intent _intent, Boolean _force)
    {
        /*
        HashMap<String, String> unreadConfReqDriverMEssages = SETTINGSMANAGER.getDriverMEssagesRequiringConfirmation(_context);
        if(unreadConfReqDriverMEssages.size() > 0)
        {
            Intent i = new Intent(_context, DriverMessage.class);
            i.putExtra(DataService._MESSAGEEXTRA, unreadConfReqDriverMEssages.);
            i.putExtra(DriverMessage.CONFIRMATION_REQUIRED, true);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
        else
        {
            doActivitySwitch(_context, _intent);
        }
        */

        doActivitySwitch(_context, _intent, _force);
    }

    private void processActivitySwitchIntent(Context _context, IntentWrapper _w)
    {
        STATUSMANAGER.putString(_context, STATUSMANAGER.STATUSES.CURRENT_ACTIVITY, _w.getActivity());

        if (_w.isBroadcast())
        {
            _context.sendBroadcast(_w.getIntent());
            debug(_context, "broadcast sent");
        }
        else
        {
            Intent i = _w.getIntent();

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    /*
    private IntentWrapper processMessageData(Intent _i, Context _c, String _data)
    {
        Intent i = null;
        boolean intentIsBroadcast = false;
        String previousActivity = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_ACTIVITY);

        if (_data.equals(DataService._ACTIVITIES.DRIVER_SCREEN))
        {
            i = new Intent(_c, DriverScreen.class);
        }
        else if (_data.equals(DataService._ACTIVITIES.BREAK))
        {
            i = new Intent(_c, BreakScreen.class);
        }
        else if (_data.equals(DataService._ACTIVITIES.LOGIN_SCREEN))
        {
            i = new Intent(_c, LoginActivity.class);
        }
        else if (_data.equals(DataService._ACTIVITIES.JOB_OFFER))
        {
            i = new Intent(_c, JobOffer.class);
        }
        else if (_data.equals(DataService._ACTIVITIES.JOB_SCREEN))
        {
            i = new Intent(_c, JobScreen.class);
            i.putExtra(JobScreen._NOJOBBUTTON, _i.getStringExtra(JobScreen._NOJOBBUTTON));
        }
        else if (_data.equals(DataService._ACTIVITIES.DRIVER_MESSAGE))
        {
            debug(_c, "Broadcast Received");
            debug(_c, "Activity: " + previousActivity);

            //String message = _i.getStringExtra(DataService._MESSAGEEXTRA);

            /*
            All now handled by locking

            if(STATUSMANAGER.getBoolean(_c, STATUSMANAGER.STATUSES.IS_PANIC))
            {
                intentIsBroadcast = true;
                STATUSMANAGER.addPendingDriverMessage(_c, message);
                i = new Intent(BROADCASTERS.NEW_DRIVER_MESSAGE);
            }
            if(previousActivity.equals(DataService._ACTIVITIES.JOB_OFFER))
            {
                //set current activity back to JobOffer
                STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_ACTIVITY, DataService._ACTIVITIES.JOB_OFFER);
                STATUSMANAGER.addPendingDriverMessage(_c, message);

                intentIsBroadcast = true;
                i = new Intent(BROADCASTERS.NEW_DRIVER_MESSAGE);

            }


            if(previousActivity.equals(DataService._ACTIVITIES.DRIVER_MESSAGE))
            {
                debug(_c, "detected already in activity");
               // intentIsBroadcast = true;
               // i = new Intent(BROADCASTERS.NEW_DRIVER_MESSAGE);
            }
            else
            {
                i = new Intent(_c, DriverMessage.class);
            }

           // i.putExtra(DataService._MESSAGEEXTRA, message);

        }
        else if(_data.equals(DataService._ACTIVITIES.WAITING_TIME))
        {
            i = new Intent(_c, WaitingTime.class);
        }
        else
        {
            ErrorActivity.handleError(_c, new ErrorActivity.ERRORS.UNHANDLED_ACTIVITY_IN_BROADCAST_HANDLER(_data));

            //just to prevent a crash in the if block below
            i = new Intent(BROADCASTERS.NULL);
            intentIsBroadcast = true;
        }



        return  new IntentWrapper(i, intentIsBroadcast, _data);
    }
*/

    private void debug(Context context, String s)
    {
        //cdToast.showShort(context, s);
    }
}
