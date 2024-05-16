package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.content.Intent;

import com.cabdespatch.driverapp.beta.services.DataService;
import com.cabdespatch.driverapp.beta.services.DataService._MESSAGETYPES;

public class BROADCASTERS
{
    //activity_switch and lock_release have to use fixed
    //headers because they are declared in the manifest and
    //we can't use string resources there

    //instead, we add extra data to the broadcast and do a check
    //on a match of extra data to see if the broadcast is intended
    //for us
    private static final String HEADER = "com.cabdespatch.driverapp.";
    public static final String ACTIVITY_SWITCHER = HEADER + "ACTIVITYSWITCH";
    public static final String LOCK_RELEASE = HEADER + "LOCK_RELEASE";



    //these values are only used by BroadcastReceivers that are declared in code by
    //activities or services. We therefore just make sure the headers are different
    //per flavour, and no additional padding is requires
    public static final String DATA = MorphStrings.BROADCAST_HEADER + "DATAMESSAGE";
    public static final String USER_REQUEST = MorphStrings.BROADCAST_HEADER + "USER_REQUEST";
    public static final String STATUS_UPDATE = MorphStrings.BROADCAST_HEADER + "STATUSUPDATE";
    public static final String JOB_STATUS_UPDATE = MorphStrings.BROADCAST_HEADER + "JOB_STATUS_UPDATE";
    public static final String CARS_WORK_UPDATE = MorphStrings.BROADCAST_HEADER + "CARSWORK";
    public static final String FUTURE_JOBS_UPDATE = MorphStrings.BROADCAST_HEADER + "FUTUREJOBS";
    public static final String NEW_DRIVER_MESSAGE = MorphStrings.BROADCAST_HEADER + "DRIVERMESSAGE_TEXT";
    public static final String LOCATION_UPDATED = MorphStrings.BROADCAST_HEADER + "LOCATIONUPDATE";
    public static final String PRICE_UPDATED = MorphStrings.BROADCAST_HEADER + "PRICE_UPDATE";
    public static final String JOB_AMEND = MorphStrings.BROADCAST_HEADER + "JOB_AMEND";
    public static final String PLOT_UPDATED = MorphStrings.BROADCAST_HEADER + "PLOTUPDATE";
    public static final String NULL = MorphStrings.BROADCAST_HEADER + "NULL";
    public static final String GPS_NO_FIX = MorphStrings.BROADCAST_HEADER + "GPS_NOFIX";
    public static final String SPEAK = MorphStrings.BROADCAST_HEADER + "SPEAK";
    public static final String ANNOUNCE_NO_SIGNAL = MorphStrings.BROADCAST_HEADER + "SPEAKNOSIGNAL";
    public static final String JOB_TOTALS_UPDATE = MorphStrings.BROADCAST_HEADER + "JOB_TOTALS_UPDATE";

    public static final String CONFIRM_MESSAGE = MorphStrings.BROADCAST_HEADER + "CONFIRM_MESSAGE";
    public static final String RESET_FIREBASE_ID = MorphStrings.BROADCAST_HEADER + "RESET_FIREBASE";



    public static void SwitchActivity(Context _c, String _activity, String _extraKey, String _extraValue)
    {
        Intent Message = new Intent(BROADCASTERS.ACTIVITY_SWITCHER);
        Message.putExtra(DataService._MESSAGETYPE, _MESSAGETYPES.ACTIVITY_SWITCH);
        Message.putExtra( DataService._MESSAGEDATA,  _activity);
        if(!(_extraValue.equals("")))
        {
            String extraKey=(_extraKey.equals("")?DataService._MESSAGEEXTRA:_extraKey);
            Message.putExtra(extraKey, _extraValue);
        }

        Message.putExtra(BroadcastHandler.CORRECT_PACKAGE_CHECK, MorphStrings.BROADCAST_HEADER);

        //DEBUGMANAGER.Log(_c, "SLI", "Broadcast sent");
        _c.sendBroadcast(Message);
    }

    public static void forceSwitchActivity(Context _c, String _activity)
    {
        Intent Message = new Intent(BROADCASTERS.ACTIVITY_SWITCHER);
        Message.putExtra(DataService._MESSAGETYPE, _MESSAGETYPES.ACTIVITY_SWITCH);
        Message.putExtra(DataService._MESSAGEDATA,  _activity);
        Message.putExtra(BroadcastHandler.FORCE_ACTIVITY_SWITCH, String.valueOf(true));
        Message.putExtra(BroadcastHandler.CORRECT_PACKAGE_CHECK, MorphStrings.BROADCAST_HEADER);

        //DEBUGMANAGER.Log(_c, "SLI", "Broadcast sent");
        _c.sendBroadcast(Message);
    }

    public static void ConfirmMessageRead(Context _c, STATUSMANAGER.DriverMessage _m)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.CONFIRM_MESSAGE_READ);
        i.putExtra(DataService._MESSAGEEXTRA, _m.toString());

        _c.sendBroadcast(i);

    }

    public static void SwitchActivity(Context _c, String _activity, String _extraValue) { SwitchActivity(_c, _activity, "", _extraValue);}
    public static void SwitchActivity(Context _c, String _activty) { SwitchActivity(_c, _activty, "");}

    public static void ReleaseLock(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.LOCK_RELEASE);
        i.putExtra(BroadcastHandler.CORRECT_PACKAGE_CHECK, MorphStrings.BROADCAST_HEADER);
        _c.sendBroadcast(i);
    }

    public static void FutureJobs(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.FUTURE_JOBS);

        _c.sendBroadcast(i);
    }

    public static void PlotUpdated(Context _c, Boolean _force)
    {
        Intent i = new Intent(BROADCASTERS.PLOT_UPDATED);
        i.putExtra(DataService._MESSAGEEXTRA, _force);
        _c.sendBroadcast(i);
    }

    public static void PriceUpdated(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.PRICE_UPDATED);
        _c.sendBroadcast(i);
    }

    public static void JobAmended(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.JOB_AMEND);
        _c.sendBroadcast(i);
    }

    ///type "s" for standard bid, or "f" for future bid
    public static void Bid(Context _c, String _shortPlotName, String _type)
    {
        Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
        Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.BID);

        String extra = _type + ":" + _shortPlotName;
        Message.putExtra(DataService._MESSAGEEXTRA, extra);

        _c.sendBroadcast(Message);
    }

    public static void DriverMessage(Context _c, String _message)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.DATA_MESSAGE);
        i.putExtra(DataService._MESSAGEEXTRA, _message);

        _c.sendBroadcast(i);
    }

    public static void NoShow(Context _c, String _message)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.NO_SHOW);
        i.putExtra(DataService._MESSAGEEXTRA, _message);

        _c.sendBroadcast(i);
    }

    public static void Quit(Context _c)
    {
        Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
        Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.QUIT);

        _c.sendBroadcast(Message);
    }

    public static void JobTotals(Context _c)
    {
        Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
        Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.JOB_TOTALS);

        _c.sendBroadcast(Message);
    }

    public static void Login(Context _c, Boolean _pinLogin)
    {
        Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
        Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.LOGIN);
        Message.putExtra(DataService._MESSAGEEXTRA, _pinLogin);

        _c.sendBroadcast(Message);
    }

    public static void CircuitFees(Context _c)
    {
        Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
        Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.CIRCUIT_FEES);

        _c.sendBroadcast(Message);
    }

    public static void Logout(Context _c)
    {
        Logout(_c, "", "");
    }

    public static void Logout(Context _c, String _logTag, String _logMessage)
    {
        Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
        Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.LOGOUT);
        Message.putExtra(DataService._LOGTAG, _logTag);
        Message.putExtra(DataService._LOGMESSAGE, _logMessage);

        _c.sendBroadcast(Message);
    }

    public static void POD(Context _c, String _data)
    {
        Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
        Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.POD);
        Message.putExtra(DataService._MESSAGEEXTRA, _data);

        _c.sendBroadcast(Message);
    }

    public static void LocationUpdated(Context _c)
    {
        Intent lc = new Intent(BROADCASTERS.LOCATION_UPDATED);
        _c.sendBroadcast(lc);
    }

    public static void SpeedChanged(Context _c)
    {
        Intent lc = new Intent(BROADCASTERS.LOCATION_UPDATED);
        _c.sendBroadcast(lc);
    }

    public static void AcceptJob(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.ACCEPT_JOB);

        _c.sendBroadcast(i);
    }

    public static void RejectJob(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.REJECT_JOB);

        _c.sendBroadcast(i);
    }

    public static void HistoryStringMessage(Context _c, String _message)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, _MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.HISTORY_LOG);
        i.putExtra(DataService._MESSAGEEXTRA, _message);

        _c.sendBroadcast(i);
    }

    public static void setJobPrice(Context _c, String _price)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, _MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.PRICE_UPDATE);
        i.putExtra(DataService._MESSAGEEXTRA, _price);

        _c.sendBroadcast(i);
    }

    public static void onRank(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, _MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.ON_RANK);

        _c.sendBroadcast(i);
    }

    public static void offRank(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, _MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.OFF_RANK);

        _c.sendBroadcast(i);
    }



    public static void meterRequestUpdate(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, _MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.METER_REQUEST_FARE_UPDATE);
        i.putExtra(DataService._MESSAGEEXTRA, SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(_c));

        _c.sendBroadcast(i);
    }

    public static void NETWORK_OFF(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.DATA);
        i.putExtra(DataService._MESSAGEDATA, DataService._ACTIONS.NETWORK_OFF);

        _c.sendBroadcast(i);
    }

    public static void NETWORK_RECONNECTING(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.DATA);
        i.putExtra(DataService._MESSAGEDATA, DataService._ACTIONS.NETWORK_RECONNECTING);

        _c.sendBroadcast(i);
    }

    public static void resetFirebaseID(Context _c)
    {
        Intent i = new Intent(BROADCASTERS.RESET_FIREBASE_ID);
        _c.sendBroadcast(i);
    }



}
