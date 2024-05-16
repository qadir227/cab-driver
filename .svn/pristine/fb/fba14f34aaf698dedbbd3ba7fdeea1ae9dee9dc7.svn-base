package com.cabdespatch.driverapp.beta.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import androidx.annotation.Nullable;
import android.telephony.TelephonyManager;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.BroadcastHandler;
import com.cabdespatch.driverapp.beta.CabDespatchNetwork;
import com.cabdespatch.driverapp.beta.CabDespatchNetworkFire;
import com.cabdespatch.driverapp.beta.CabDespatchNetworkHttp;
import com.cabdespatch.driverapp.beta.CabDespatchNetworkOldSty;
import com.cabdespatch.driverapp.beta.CabDespatchNetworkSignalR;
import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.EncryptionHandler;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.InteropServices;
import com.cabdespatch.driverapp.beta.JOBHISTORYMANAGER;
import com.cabdespatch.driverapp.beta.NOTIFIERS;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.SOUNDMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER.APP_STATE;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.USERREQUESTS;
import com.cabdespatch.driverapp.beta.UnfairMeterLocal;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;
import com.cabdespatch.driverapp.beta.activities.ResumerActivity;
import com.cabdespatch.driverapp.beta.activities2017.LoggedInHost;
import com.cabdespatch.driverapp.beta.cabdespatchGPS;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.cabdespatchJob.JOB_STATUS;
import com.cabdespatch.driverapp.beta.cabdespatchMessageSys;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_Update;
import com.cabdespatch.driverapp.beta.fragments.StatusBar;
import com.cabdespatch.driverapp.beta.payment.CreditCardHandler;
import com.cabdespatch.driverapp.beta.pdaLocation;
import com.cabdespatch.driverapp.beta.plot;
import com.cabdespatch.driverapp.beta.plotList;
import com.cabdespatch.driverapp.beta.priorityString;
import com.cabdespatch.driverapp.beta.sQueue;
import com.cabdespatch.driverapp.beta.uiMessage;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Date;
import java.util.Queue;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by User on 07/02/2018.
 */

public class DataService extends Service implements Dialog_Update.OnUpdatePackageDownloadListener
{


    public static boolean addPushMessage(String _message)
    {
        if(getInstance() == null)
        {
            return false;
        }
        else
        {
            if(getInstance().oNetwork == null)
            {
                return false;
            }
            else
            {
                getInstance().oNetwork.addPushMessage(_message);
                return true;
            }
        }
    }

    private static DataService sInstance;
    public static final boolean NETWORK_DEBUG = true;
    private static int NETWORK_MESSAGE_ID = 0;
    private boolean fireData;
    private boolean isFireData()
    {
        return fireData;
    }
    private static DataService getInstance() { return sInstance; }

    private static DataServiceLooper sLooper;
    private static DataServiceLooper getServiceLooper() { return sLooper; }

    private static boolean is_data_waiting = false;
    public static void adviseDataWaiting()
    {
        is_data_waiting = true;
    }
    private static boolean isDataWaiting()
    {
        return is_data_waiting;
    }
    private static void clearDataWaiting()
    {
        is_data_waiting = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        //do nothing
        return null;
    }


    /*
    Maintence & Looper
     */

    private static Boolean STOP_PENDING = false;
    private static Boolean IS_RUNNING = false;
    private static Boolean setFakeLocation = false;

    CabDespatchNetwork oNetwork;
    private cabdespatchGPS gps;
    private MessageHandler oMessageHandler;
    cabdespatchMessageSys oMessageSys;

    String oDriverNo;
    String oCompanyId;

    protected long lastPingTime;
    protected long pingFrequency;

    private static Boolean screen_is_on;
    private static Long job_offer_time;


    public static void requestStart(final Context _c)
    {
        if(_c==null)
        {
            //do nothing... let's wait for another request to come in with
            //a valid context
        }
        else
        {
            long startdelay = 0;
            if(getInstance()==null)
            {
                //do nothing
            }
            else
            {
                getInstance().requestStop(_c);
                startdelay = 3000; //give the stop command a chance to complete
            }

            Handler h = new Handler(_c.getMainLooper());
            h.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    //it's possible that the context which requested the start has become
                    //null between the previous check and the delayed start request
                    //
                    //if so, there'll be another start request along shortly
                    if(!(_c==null))
                    {
                        Intent i = new Intent(_c, DataService.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        {
                            _c.startForegroundService(i);
                         //   _c.startService(i);
                        }
                        else
                        {
                            _c.startService(i);
                        }
                    }
                }
            }, startdelay);

        }
        DEBUGMANAGER.Log(_c, "DSERV", "Start Requested");

    }

    public static void requestStop(Context _c)
    {
        STOP_PENDING = true;
        DataService s = getInstance();
        if(!(s==null)) { s.doStop(); }
    }

    protected void doStop()
    {
        DEBUGMANAGER.Log(this, "DSERV", "Stopping Now...");
        cleanUp();
        IS_RUNNING = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            this.stopForeground(true);
        }
        else
        {
            NOTIFIERS.hideAppRunning(this);
            this.stopSelf();
        }
    }



    protected void cleanUp()
    {
        if(!(this.gps==null))
        {
            this.gps.cancel();
            this.gps = null;
        }
        if(!(this.oNetwork == null))
        {
            this.oNetwork.Stop();
            this.oNetwork = null;
        }
        if(!(getServiceLooper()==null))
        {
            getServiceLooper().cancel();
        }

        try
        {
            this.unregisterReceiver(this.oMessageHandler);
        }
        catch (Exception ex) {}
        sInstance = null;
    }


    protected boolean isDeveloperEdition()
    {
        return (getPackageName().contains(".devmode"));
    }

    private static Boolean isScreenOn()
    {
        if(screen_is_on==null)
        {
            //assume it's on
            return true;
        }
        else
        {
            return screen_is_on;
        }
    }

    private static Long getJobOfferTime()
    {
        if(job_offer_time==null)
        {
            job_offer_time = System.currentTimeMillis();
        }
        return job_offer_time;
    }

    public void sendPing(Boolean _includeFirebaseId) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidParameterSpecException, BadPaddingException, InvalidKeyException {
        this.lastPingTime = SystemClock.uptimeMillis();
        this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.Ping(DataService.this.getBaseContext(), false, _includeFirebaseId));
    }

    protected void logOffPDA()
    {
        STATUSMANAGER.setCurrentJob(DataService.this, new cabdespatchJob(0d, false));
        STATUSMANAGER.STATUSES.HIDE_MENU_BUTTON.reset(DataService.this);

        Boolean usePinLogin = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_PIN_LOGIN.getValue(DataService.this));
        if(usePinLogin)
        {
            STATUSMANAGER.clearPinLogin(DataService.this);
            DataService.this.oNetwork.requestDriverCallSignChange(DataService.this);
        }

        String currentState = STATUSMANAGER.getString(DataService.this.getBaseContext(), STATUSMANAGER.STATUSES.CURRENT_APP_STATE);
        if((currentState.equals(STATUSMANAGER.APP_STATE.LOGGED_OFF)))
        {
            //do nothing
        }
        else
        {
            STATUSMANAGER.putString(this.getBaseContext(), STATUSMANAGER.STATUSES.CURRENT_APP_STATE, STATUSMANAGER.APP_STATE.LOGGED_OFF);
                /*CLAY should this be an activity switch broadcast??
                Intent loginScreen = new Intent(this, ResumerActivity.class);
                loginScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(loginScreen);*/

            //of course it should, otherwise we're not going to be keeping track of the current activity!

            Intent iLogoff = new Intent(BROADCASTERS.DATA);
            iLogoff.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
            iLogoff.putExtra(_MESSAGEDATA, DataService._ACTIONS.LOGOUT);

            DataService.this.sendBroadcast(iLogoff);
        }

        BROADCASTERS.SwitchActivity(this,  DataService._ACTIVITIES.LOGIN_SCREEN);
    }



    //Legacy... not needed. Remove at leisure
    @Override
    public void onUpdatePackageDownloadComplete()
    {

    }

    @Override
    public void onUpdatePackageDownloadFailed(FAIL_REASON _reason)
    {

    }

    @Override
    public void onUpdatePackageDownloadCancelled()
    {

    }

    protected void handleNoConnection()
    {
        STATUSMANAGER.putBoolean(DataService.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION, false);
        Boolean loggedIn = (STATUSMANAGER.getAppState(DataService.this).equals(STATUSMANAGER.APP_STATE.LOGGED_ON));
        if(!(loggedIn))
        {
            STATUSMANAGER.setStatusBarText(DataService.this.getBaseContext(), "No connection to server");
        }


        Intent Message = new Intent(BROADCASTERS.DATA);
        Message.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
        Message.putExtra(_MESSAGEDATA, _ACTIONS.NETWORK_OFF);

        DataService.this.sendBroadcast(Message);
    }

    public class plotGrabber extends Thread
    {
        @Override
        public void run()
        {
            String companyID = SETTINGSMANAGER.get(DataService.this, SETTINGSMANAGER.SETTINGS.COMPANY_ID);
            String driverNo = SETTINGSMANAGER.get(DataService.this, SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN);

            Globals.ZoneFileResult r = Globals.getZoneFile(DataService.this, companyID, driverNo);

            if(r.wasSuccessful())
            {
                SETTINGSMANAGER.putZoneFile(DataService.this, r.getZoneFile());
                SETTINGSMANAGER.loadPlots(DataService.this, true);
            }
            else
            {
                ErrorActivity.handleError(DataService.this, new ErrorActivity.ERRORS.ZONE_FILE_ERROR(r.getException()));
            }
        }
    }

    private class DataServiceLooper extends Thread
    {

        private Boolean oCancel;
        private Boolean oDead;

        public void cancel() { oCancel = true; }

        public DataServiceLooper()
        {
            oCancel = false;
            oDead = false;
        }

        public Boolean isDead()
        {
            return  oDead;
        }

        @Override
        public void run()
        {
            Boolean doLoop = true;

            while(doLoop)
            {
                if(oCancel)
                {
                    //a stop is pending
                    //set loop to false
                    doLoop = false;
                }
                else
                {
                    /* this all appears to be handled elswehere
                    if((oNetwork==null)||(gps==null))
                    {
                        if(!(getInstance()==null))
                        {
                            sInstance.requestStop(getBaseContext());
                        }
                    }
                    else
                    {

                    }*/

                    if(!((oNetwork==null)||(gps==null)))
                    {
                        try
                        {
                            loopWork();
                        }
                        catch (Exception ex)
                        {
                            DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR, "LoopWork", "Loopwork failed. See below for details");
                            DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR, "LoopWork", ex.toString());
                        }

                    }

                    try
                    {
                       Thread.sleep(200);
                    }
                    catch (Exception ex)
                    {
                       //never mind
                    }
                }
            }

            oDead = true;
        }

        private void deleteSMS(Context _c, String _boxUrl)
        {

            DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN, "TextBack", _boxUrl);

            Uri inboxUri = Uri.parse(_boxUrl);
            int count = 0;
            Cursor c = _c.getContentResolver().query(inboxUri , null, null, null, null);
            while (c.moveToNext()) {
                try {
                    // Delete the SMS
                    String pid = c.getString(0); // Get id;
                    String uri = "content://sms/" + pid;
                    count = _c.getContentResolver().delete(Uri.parse(uri),
                            null, null);

                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN, _boxUrl, pid.toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            c.close();
            c = null;
        }

        private void setJobStatus(cabdespatchJob.JOB_STATUS _status)
        {
            if(!(_status==null))
            {
                cabdespatchJob j = STATUSMANAGER.getCurrentJob(getInstance());
                j.setJobStatus(_status);
                STATUSMANAGER.setCurrentJob(getInstance(), j);
            }

            Intent i = new Intent(BROADCASTERS.JOB_STATUS_UPDATE);
            getInstance().sendBroadcast(i);
        }

        private void startBreak()
        {
            NOTIFIERS.updateText(DataService.this, "On break");
            STATUSMANAGER.setAppState(DataService.this, APP_STATE.ON_BREAK);
            STATUSMANAGER.putLong(DataService.this, STATUSMANAGER.STATUSES.CURRENT_BREAK_START, System.currentTimeMillis());
            BROADCASTERS.SwitchActivity(DataService.this.getBaseContext(), _ACTIVITIES.BREAK);
        }

        private void loopWork() throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidParameterSpecException, BadPaddingException, InvalidKeyException {

            if(UnfairMeterLocal.isLive())
            {
                DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR, "DS", "Meter Update...");
                UnfairMeterLocal.update(DataService.this);
            }


            if(DataService.setFakeLocation)
            {
                DataService.setFakeLocation = false;
                gps.forceFixedLocationChange();
                BROADCASTERS.PlotUpdated(DataService.this, true);
            }

            Long lastmessage = DataService.this.oNetwork.getTimeOfLastAdditionOrAcknowledgement();


            if(lastmessage== CabDespatchNetworkOldSty.NO_DATA_RECEIVED)
            {
                int x=1;
                x +=1;
            }
            else
            {

                int DataServicetimeoutseconds = DataService.this.oNetwork.getTimeOutSeconds();

                Long killtimemillis = (long) (DataServicetimeoutseconds * 1000);
                Long killservicethreashold  = lastmessage + killtimemillis;

                if(System.currentTimeMillis() > killservicethreashold)
                {
                    DataService.this.handleNoConnection();

                    Integer kilsCount = STATUSMANAGER.getInt(DataService.this, STATUSMANAGER.STATUSES.DATA_SERVICE_KILL_COUNT);
                    kilsCount +=1;
                    STATUSMANAGER.putInt(DataService.this, STATUSMANAGER.STATUSES.DATA_SERVICE_KILL_COUNT, kilsCount);
                    SOUNDMANAGER.announceLostSignal(DataService.this);

                    pdaLocation p = STATUSMANAGER.getCurrentLocation(DataService.this);
                    String dbgMessage = "Signal Lost at " + p.getLatString() + "," + p.getLonString();


                    getInstance().requestStop(getInstance());
                }

            if (DataService.STOP_PENDING)
            {
                //do nothing

            }
            else
            {

                if(isDataWaiting())
                {
                    clearDataWaiting();
                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN, "Data Service", "Ping on advice from firebase");
                    DataService.this.sendPing(false);
                }
                else if (SystemClock.uptimeMillis() >= (DataService.this.lastPingTime + DataService.this.pingFrequency))
                {
                    //Log.d(String.valueOf(System.nanoTime()),String.valueOf(DataService.this.lastPingTime));
                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN, "Data Service", "Ping");
                    DataService.this.sendPing(false);
                }


                if (!(isScreenOn()))
                {
                    if (STATUSMANAGER.getCurrentJob(DataService.this).getJobStatus() == JOB_STATUS.UNDER_OFFER)
                    {
                        Long preJobOfferDuration = (System.currentTimeMillis() - getJobOfferTime());
                        if (preJobOfferDuration >= (30 * 1000))
                        {
                            BROADCASTERS.RejectJob(DataService.this);
                            Vibrator v = (Vibrator) DataService.this.getSystemService(Context.VIBRATOR_SERVICE);
                            v.cancel();
                        }
                        else
                        {
                            //do nothing. wait for activity to work it's magic
                        }
                    }
                }

                Queue<String> networkErrorMessags = DataService.this.oNetwork.getErrorMessages();

                boolean didToast = false;

                while (networkErrorMessags.size() > 0)
                {
                    if(NETWORK_DEBUG && false)
                    {
                        if(!(didToast))
                        {
                            didToast = true;
                            cdToast.showShort(DataService.this, "Errors Logged");
                        }

                        STATUSMANAGER.addErrorAsDriverMessage(DataService.this, networkErrorMessags.poll());
                    }
                    else
                    {
                        String garabge = networkErrorMessags.poll(); //thorw away
                    }
                }

                sQueue messages = DataService.this.oNetwork.getMessages();

                while (messages.size() > 0)
                {

                    String data = messages.poll();
                    String statusText = "";
                    //New decrypt if needed
                    // I think im handeling these earlier


                    if (data.equals(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_NOT_READY))
                    {
                        //now handled on a timer basis in runTimer
                        //DataService.this.handleNoConnection();
                        STATUSMANAGER.putBoolean(DataService.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION, false);
                        BROADCASTERS.NETWORK_OFF(DataService.this);

                    }
                    else if (data.equals(CabDespatchNetwork._SPECIALMESSAGES.NETWORK_RECONNECTING))
                    {
                        STATUSMANAGER.putBoolean(DataService.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION, false);
                        BROADCASTERS.NETWORK_RECONNECTING(DataService.this);
                    }
                    else if (data.equals(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_READY))
                    {
                        STATUSMANAGER.putBoolean(DataService.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION, true);

                        Boolean loggedIn = (STATUSMANAGER.getAppState(DataService.this).equals(APP_STATE.LOGGED_ON));
                        if (!(loggedIn))
                        {
                            STATUSMANAGER.setStatusBarText(DataService.this.getBaseContext(), "Server located", StatusBar.AFFERMATIVE_MESSAGE_DELAY);
                        }
                        //pass it on in case there are any activities that need to know about this (ie login screen)
                        Intent Message = new Intent(BROADCASTERS.DATA);
                        Message.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
                        Message.putExtra(_MESSAGEDATA, _ACTIONS.NETWORK_ON);

                        DataService.this.sendBroadcast(Message);
                    }
                    else
                    {
                        final Boolean isLockDown = (!(SETTINGSMANAGER.getLockDownMode(DataService.this).equals(SETTINGSMANAGER.LOCK_DOWN_MODES.NONE)));

                        uiMessage u = DataService.this.oMessageSys.handler.handleMessage(DataService.this.getApplicationContext(), data);
                        switch (u.getMessageType())
                        {
                            case PONG:
                            case NULL:
                            case DO_NOTHING:
                                //do... nothing! :)
                                break;
                            case REBOOT_REQUIRED_FOR_LOGIN:
                                DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.Logoff(DataService.this, "", ""));
                                SETTINGSMANAGER.RESET_PENDING = true;

                                Handler h = new Handler(DataService.this.getMainLooper());
                                h.post(new Runnable()
                                {

                                    @Override
                                    public void run()
                                    {
                                        String message = "A change has been made to your settings which requires that you reboot your device. You will not be able to login until you reboot.";
                                        new Dialog_MsgBox(getApplicationContext(), message, Dialog_MsgBox._SHOWBUTTONS.OK).show();

                                    }
                                });
                                break;
                            case RESTART_FOR_DATASERVICE:
                                //log off
                                DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.Logoff(DataService.this, "", ""));

                                Handler hi = new Handler(DataService.this.getMainLooper());
                                hi.post(new Runnable()
                                {

                                    @Override
                                    public void run()
                                    {
                                        try
                                        {
                                            String message = "Your data settings have changed. Cab Despatch needs to Restart.";
                                            Dialog_MsgBox d = new Dialog_MsgBox(getApplicationContext(), message, Dialog_MsgBox._SHOWBUTTONS.OK);
                                            d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                                            {

                                                @Override
                                                public void ButtonPressed(Dialog_MsgBox._BUTTON _button)
                                                {
                                                    BROADCASTERS.Quit(DataService.this);
                                                    STATUSMANAGER.CURRENT_ACTIVITY.finish();
                                                }
                                            });
                                            d.show();
                                        }
                                        catch (Exception ex)
                                        {
                                            BROADCASTERS.Quit(DataService.this);
                                            cdToast.showLong(DataService.this, "Your data settings have changed. Cab Despatch needs to Restart.");
											STATUSMANAGER.CURRENT_ACTIVITY.finish();
                                        }

                                    }

                                });


                                break;
                            case LOGON:
                                String appstate = STATUSMANAGER.getAppState(DataService.this);

                                STATUSMANAGER.resetTimers(DataService.this);
                                if (appstate.equals(APP_STATE.LOGGED_OFF))
                                {
                                    STATUSMANAGER.putBoolean(DataService.this, STATUSMANAGER.STATUSES.HAS_BEEN_LOGGED_ON, true);

                                    // CLAY2017 BROADCASTERS.SwitchActivity(DataService.this.getBaseContext(), _ACTIVITIES.DRIVER_SCREEN);
                                    STATUSMANAGER.setStatusBarText(DataService.this.getBaseContext(), "Logged in");
                                    STATUSMANAGER.putString(DataService.this.getBaseContext(), STATUSMANAGER.STATUSES.CURRENT_APP_STATE, APP_STATE.LOGGED_ON);

                                    Intent i = new Intent(DataService.this, LoggedInHost.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    DataService.this.startActivity(i);
                                }
                                break;
                            case LOGOFF:
                                statusText = DataService.this.getString(R.string.logged_out);
                            case NOTLOGGEDIN:
                                if (statusText.equals(""))
                                    statusText = DataService.this.getString(R.string.you_have_been_logged_out);

                                Boolean hasBeenLoggedIn = STATUSMANAGER.getBoolean(DataService.this, STATUSMANAGER.STATUSES.HAS_BEEN_LOGGED_ON);
                                if (hasBeenLoggedIn)
                                {
                                    if (!(STATUSMANAGER.getAppState(DataService.this).equals(STATUSMANAGER.APP_STATE.LOGGED_OFF)))
                                    {
                                        STATUSMANAGER.setStatusBarText(DataService.this.getBaseContext(), statusText);
                                    }
                                }
                                DataService.this.logOffPDA();
                                //handled by cabdespatchMessageSys atm
                                //STATUSMANAGER.setAppState(DataService.this, APP_STATE.LOGGED_OFF);
                                break;
                            case JOBWAITING:
                                UnfairMeterLocal.Stop(DataService.this); //reset the meter

                                BROADCASTERS.SwitchActivity(DataService.this.getBaseContext(), _ACTIVITIES.JOB_OFFER);
                                STATUSMANAGER.setAppState(DataService.this, APP_STATE.ON_JOB); //CLAY2017
                                if (isScreenOn())
                                {
                                    if (LoggedInHost.isPaused())
                                    {
                                        ResumerActivity.resume(DataService.this);
                                    }
                                }
                                else
                                {
                                    SOUNDMANAGER.playJobOfferSound(DataService.this);
                                    Vibrator v = (Vibrator) DataService.this.getSystemService(Context.VIBRATOR_SERVICE);
                                    // Vibrate for 500 milliseconds
                                    long[] pattern = {1000, 200, 500, 100, 500, 1000};
                                    v.vibrate(pattern, 0);
                                    job_offer_time = System.currentTimeMillis();
                                }
                                //DataService.this.turnOnScreen();
                                break;
                            case JOB_UPDATE:
                                if (u.getMessageData().equals("Y")) //confirmation required
                                {
                                    STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.MODE.DISPLAY, STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE, STATUSMANAGER.DriverMessage.BOX.INBOX, new Date().getTime(), "Job details have been changed", true, true);
                                    STATUSMANAGER.addDriverMessage(DataService.this, m);
                                }
                                BROADCASTERS.JobAmended(DataService.this);
                                break;
                            case PRICE_UPDATE:
                                BROADCASTERS.PriceUpdated(DataService.this);
                                break;
                            case SETONROUTE:
                                STATUSMANAGER.setAppState(DataService.this, APP_STATE.ON_JOB);

                                cabdespatchJob j = STATUSMANAGER.getCurrentJob(DataService.this);
                                JOBHISTORYMANAGER.addJob(DataService.this, j);

                                if (u.getMessageData().equals("flag"))
                                {
                                    this.setJobStatus(JOB_STATUS.STC);
                                    //UnfairMeterLocal.Start(DataService.this);
                                }
                                else
                                {
                                    Boolean autoAccept = u.getMessageData().equals("Y");

                                    //this.setJobStatus(JOB_STATUS.ON_ROUTE);
                                    cabdespatchJob jor = STATUSMANAGER.getCurrentJob(DataService.this);
                                    jor.setStatusFromPendng();
                                    STATUSMANAGER.setCurrentJob(DataService.this, jor);

                                    if (autoAccept)
                                    {
                                        SOUNDMANAGER.playAutoJobSound(DataService.this);
                                    }
                                }

                                STATUSMANAGER.setStatusBarText(DataService.this); //remove trap info

                                break;
                            case SETSTP:
                                cabdespatchJob j1 = STATUSMANAGER.getCurrentJob(DataService.this);

                                if (j1.getJobStatus() == JOB_STATUS.UNDER_OFFER)
                                {
                                    j1.setPendingStatus(JOB_STATUS.STP);
                                    STATUSMANAGER.setCurrentJob(DataService.this, j1);
                                }
                                else
                                {
                                    this.setJobStatus(JOB_STATUS.STP);
                                }
                                break;
                            case SETPOB:
                                cabdespatchJob j2 = STATUSMANAGER.getCurrentJob(DataService.this);


                                if (j2.getJobStatus() == JOB_STATUS.UNDER_OFFER)
                                {
                                    j2.setPendingStatus(JOB_STATUS.POB);
                                    STATUSMANAGER.setCurrentJob(DataService.this, j2);
                                }
                                else
                                {
                                    this.setJobStatus(JOB_STATUS.POB);
                                }

                                gps.checkSubscription(DataService.this);
                                if(UnfairMeterLocal.isReadyToGo())
                                {
                                    UnfairMeterLocal.Start(DataService.this);
                                }
                                break;
                            case SETSTC:
                                cabdespatchJob j3 = STATUSMANAGER.getCurrentJob(DataService.this);

                                if (j3.getJobStatus() == JOB_STATUS.UNDER_OFFER)
                                {
                                    j3.setPendingStatus(JOB_STATUS.STC);
                                    STATUSMANAGER.setCurrentJob(DataService.this, j3);
                                }
                                else
                                {
                                    this.setJobStatus(JOB_STATUS.STC);
                                }

                                if (u.getMessageData().equals("Y"))
                                {
                                    String priceAmend = getString(R.string.price_amend_short);
                                    priceAmend = priceAmend.replace("$", j3.getPrice());

                                    cdToast.showShort(DataService.this, priceAmend);

                                    if (SETTINGSMANAGER.SETTINGS.SPEAK_PRICES.parseBoolean(DataService.this))
                                    {
                                        SOUNDMANAGER.announcePrice(DataService.this, j3.getPrice());
                                    }
                                    BROADCASTERS.PriceUpdated(DataService.this);
                                }
                                gps.checkSubscription(DataService.this);
                                break;
                            case ANTICHEATRECD:
                                cabdespatchJob jo = STATUSMANAGER.getCurrentJob(DataService.this.getBaseContext());
                                jo.setAntiCheatOn();
                                STATUSMANAGER.setCurrentJob(DataService.this, jo);

                                this.setJobStatus(null); //just send broadcast
                                break;
                            case SETCLEAR:
                                UnfairMeterLocal.Stop(DataService.this); //reset the meter

                                STATUSMANAGER.setAppState(DataService.this, APP_STATE.LOGGED_ON);
                                STATUSMANAGER.setCurrentJob(DataService.this, new cabdespatchJob(0d,false));

                                //CLAY - maybe do an USER_REQUESTS.Back() instead???
                                BROADCASTERS.SwitchActivity(DataService.this, _ACTIVITIES.DRIVER_SCREEN);

                                //we've received a clear ACK, send our plot...
                                BROADCASTERS.PlotUpdated(DataService.this, true);
                                if(u.getMessageData().equals("1"))
                                {
                                    startBreak();
                                }
                                break;
                            case CARSJOBSMESSAGE:
                                if (!(DataService.this.gps.getCurrentFixTime() == cabdespatchGPS.FIX_NEVER_OBTAINED))
                                {
                                    DataService.this.gps.forceAutoPlot(DataService.this);
                                }
                                else
                                {
                                    //do nothing
                                    //cdToast.showShort(DataService.this, "Avoided erroneous fix");
                                }
                                Intent CarsJobs = new Intent(BROADCASTERS.CARS_WORK_UPDATE);
                                DataService.this.sendBroadcast(CarsJobs);
                                break;
                            case FUTUREJOBSMESSAGE:
                                Intent FutureJobs = new Intent(BROADCASTERS.FUTURE_JOBS_UPDATE);
                                DataService.this.sendBroadcast(FutureJobs);
                                break;
                            case SPOKENMESSAGE:
                                SOUNDMANAGER.say(DataService.this, u.getMessageData());
                                break;
                            case DRIVERMESSAGE:
                                //String confRequired = u.getSecondaryData();
                                //new DriverMessageDisplayer(DataService.this.getBaseContext()).start();
                                //DataService.this.turnOnScreen();
                                if (isScreenOn())
                                {
                                    if (LoggedInHost.isPaused())
                                    {

                                        Integer messagecount = STATUSMANAGER.getUnreadDriverMessages(DataService.this).size();

                                        if (messagecount > 0)
                                        {
                                            if (CreditCardHandler.isProcessingPayment())
                                            {
                                                String toastContent = "";

                                                if (messagecount > 1)
                                                {
                                                    toastContent = DataService.this.getString(R.string.messages_waiting);
                                                    toastContent = toastContent.replace("$", String.valueOf(messagecount));
                                                }
                                                else
                                                {
                                                    toastContent = DataService.this.getString(R.string.message_waiting);
                                                }
                                                cdToast.showShort(DataService.this, toastContent);


                                            }
                                            else
                                            {
                                                ResumerActivity.resume(DataService.this);
                                            }
                                        }

                                    }
                                }
                                else
                                {
                                    SOUNDMANAGER.playNewMessageSound(DataService.this);
                                    Vibrator v = (Vibrator) DataService.this.getSystemService(Context.VIBRATOR_SERVICE);
                                    v.vibrate(2000);
                                }
                                break;
                            case PLOTUPDATE:
                                new plotGrabber().start();
                                break;
                            case BREAKSTART:
                                startBreak();
                                break;
                            case BREAKEND:
                                NOTIFIERS.reset(DataService.this);
                                STATUSMANAGER.setAppState(DataService.this, APP_STATE.LOGGED_ON);
                                break;
                            case RESET_PDA:
                                String companyID = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(DataService.this);
                                SETTINGSMANAGER.reset(DataService.this, false);
                                if ((!(u.getMessageData().equals("-1"))))
                                {
                                    SETTINGSMANAGER.SETTINGS.COMPANY_ID.putValue(DataService.this, companyID);
                                    SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.putValue(DataService.this, "*" + u.getMessageData());
                                }
                                BROADCASTERS.Quit(DataService.this);
                                break;
                            case WORK_WAITING_AT_DESTINATION:
                                SOUNDMANAGER.announceWorkWaitingAtDestination(DataService.this);
                                break;
                            case TOAST:
                                cdToast.showLong(DataService.this, data, "Job Totals");
                                break;
                            case NEW_JOB_TOTALS:
                                Intent message = new Intent(BROADCASTERS.JOB_TOTALS_UPDATE);
                                DataService.this.sendBroadcast(message);
                                break;
                            case UPDATE_AVAILABLE:
                                if (!(SETTINGSMANAGER.RESET_PENDING))
                                {
                                    Handler hiua = new Handler(DataService.this.getMainLooper());
                                    hiua.post(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            if (isLockDown)
                                            {
                                                new Dialog_MsgBox(DataService.this, "An update is available. Please see the office").show();
                                            }
                                            else
                                            {
                                                Dialog_MsgBox d = new Dialog_MsgBox(getApplicationContext(), "An update is available. Do you wish to download (saying yes will log you off)", Dialog_MsgBox._SHOWBUTTONS.YESNO);
                                                d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                                                {
                                                    @Override
                                                    public void ButtonPressed(Dialog_MsgBox._BUTTON _button)
                                                    {
                                                        if (_button.equals(Dialog_MsgBox._BUTTON.YES))
                                                        {
                                                            BROADCASTERS.Logout(DataService.this.getBaseContext());
                                                            Globals.CrossFunctions.UpdateApp(DataService.this.getBaseContext());
                                                        }
                                                    }
                                                });
                                                d.show();
                                            }

                                        }
                                    });
                                }
                                break;
                            case UPDATE_REQUIRED:
                                if (!(SETTINGSMANAGER.RESET_PENDING))
                                {
                                    Handler hiub = new Handler(DataService.this.getMainLooper());
                                    hiub.post(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            if (isLockDown)
                                            {
                                                new Dialog_MsgBox(DataService.this, "An update is required before you can log in. Please see the office").show();
                                            }
                                            else
                                            {
                                                Dialog_MsgBox d = new Dialog_MsgBox(getApplicationContext(), "An update is required before you can log in. Do you wish to download now?", Dialog_MsgBox._SHOWBUTTONS.YESNO);
                                                d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                                                {
                                                    @Override
                                                    public void ButtonPressed(Dialog_MsgBox._BUTTON _button)
                                                    {
                                                        if (_button.equals(Dialog_MsgBox._BUTTON.YES))
                                                        {
                                                            BROADCASTERS.Logout(DataService.this.getBaseContext());
                                                            Globals.CrossFunctions.UpdateApp(DataService.this.getBaseContext());
                                                        }
                                                    }
                                                });
                                                d.show();
                                            }

                                        }
                                    });
                                }
                                break;

                            case DATA_WAITING:
                                //cdToast.showLong(DataService.this, "Advised to fetch data");
                                DataService.this.sendPing(false);
                                break;
                            case SEND_SMS:
                                Boolean allowLocal = STATUSMANAGER.getBoolean(DataService.this, STATUSMANAGER.STATUSES.ALLOW_LOCAL_SMS);
                                if(allowLocal)
                                {
                                    String phoneNo = u.getSecondaryData();
                                    String messageText = u.getMessageData();

                                    InteropServices.sendSms(DataService.this, phoneNo, messageText);
                                }
                                else
                                {
                                    STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.MODE.DEFAULT, STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
                                            STATUSMANAGER.DriverMessage.BOX.INBOX,
                                            new Date().getTime(),
                                            DataService.this.getString(R.string.TEXTBACK_SECURITY_ERROR),
                                            false, true);
                                    STATUSMANAGER.addDriverMessage(DataService.this, m);
                                }

                                break;
                            default:
                                //see if any of the other activities are interested in this message
                                Intent Message = new Intent(BROADCASTERS.DATA);
                                Message.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
                                Message.putExtra(_MESSAGEDATA, "misc data recd: " + data);

                                DataService.this.sendBroadcast(Message);
                        }
                    }

                }
            }
                /*  OLD
                try
                {

                    if(DataService.DO_LOG_HEAP)
                    {
                        if(DataService.DO_LOG_COUNT++ > 10)
                        {
                            DataService.DO_LOG_COUNT = 0;
                            Double allocated = new Double(Debug.getNativeHeapAllocatedSize())/new Double((1048576));
                            Double available = new Double(Debug.getNativeHeapSize())/1048576.0;
                            Double free = new Double(Debug.getNativeHeapFreeSize())/1048576.0;
                            DecimalFormat df = new DecimalFormat();
                            df.setMaximumFractionDigits(2);
                            df.setMinimumFractionDigits(2);

                            Log.e("HEAP", "debug. =================================");
                            Log.e("HEAP", "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
                            Log.e("HEAP", "debug.memory: allocated: " + df.format(new Double(Runtime.getRuntime().totalMemory()/1048576)) + "MB of " + df.format(new Double(Runtime.getRuntime().maxMemory()/1048576))+ "MB (" + df.format(new Double(Runtime.getRuntime().freeMemory()/1048576)) +"MB free)");
                        }
                    }

                }
                catch(Exception ex)
                {
                    ErrorActivity.genericReportableError(ex, "COULD NOT SCHEDULE NEW RUN TIMER");
                    DataService.IS_ACTIVE = false;
                }*/
            }
        }

    }

    private class MessageHandler extends BroadcastReceiver
    {

        private void handlePlotUpdate(Context _context, Intent _intent) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidParameterSpecException, BadPaddingException, InvalidKeyException {
            STATUSMANAGER.setStatusBarText(_context);
            //Toast.makeText(DataService.this, "New Plot: " + p.getLongName(), Toast.LENGTH_LONG).show();
            String a = STATUSMANAGER.getAppState(_context);
            Boolean force = _intent.getBooleanExtra(DataService._MESSAGEEXTRA, false);

            if ((a.equals(STATUSMANAGER.APP_STATE.LOGGED_ON) || (force)))
            {
                String plot = STATUSMANAGER.getCurrentLocation(_context).getPlot().getShortName();
                DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.Plot(_context, plot));
            }
        }

        private void handleUserRequest(Context _context, Intent _intent) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidParameterSpecException, BadPaddingException, InvalidKeyException {
            //Debug.waitForDebugger();

            String messagetype = _intent.getStringExtra(DataService._MESSAGETYPE);
            String messagedata = _intent.getStringExtra(DataService._MESSAGEDATA);

            if (messagetype.equals(DataService._MESSAGETYPES.USER_REQUEST))
            {
                if (messagedata.equals(USERREQUESTS.LOGIN))
                {
                    Boolean isPinLogin = _intent.getBooleanExtra(DataService._MESSAGEEXTRA, false);

                    if(isPinLogin)
                    {
                        DataService.this.oNetwork.requestDriverCallSignChange(DataService.this);
                    }

                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    String IMEI = Globals.CrossFunctions.getDeviceIdentifier_wasIMEI(DataService.this);
                    @SuppressLint("MissingPermission") String phoneNumber = telephonyManager.getLine1Number();
                    @SuppressLint("MissingPermission") String networkName = telephonyManager.getNetworkOperatorName();
                    //String simNo = telephonyManager.getSimSerialNumber();
                    String description = Build.MANUFACTURER + " " + Build.MODEL;
                    STATUSMANAGER.STATUSES.ON_RANK.reset(DataService.this, STATUSMANAGER.Status._RESET_TYPE.BOOLEAN);

                    this.sendMessage(DataService.this.oMessageSys.builder.Login(_context, IMEI, networkName, phoneNumber, description));

                    String plot = STATUSMANAGER.getCurrentLocation(_context).getPlot().getShortName();
                    this.sendMessage(DataService.this.oMessageSys.builder.Plot(_context, plot));
                }
                else if (messagedata.equals(USERREQUESTS.LOGOUT))
                {
                    String logTag = _intent.getStringExtra(DataService._LOGTAG);
                    String logMessage = _intent.getStringExtra(DataService._LOGMESSAGE);
                    this.sendMessage(DataService.this.oMessageSys.builder.Logoff(_context, logTag, logMessage));
                }
                else if (messagedata.equals(USERREQUESTS.QUIT))
                {
                    //clay
                    //DataService.this.switchFlightMode(true);
                    requestStop(DataService.this);
                }
                else if (messagedata.equals(USERREQUESTS.METER_REQUEST_FARE_UPDATE))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.FareRequest(_context));
                }
                else if (messagedata.equals(USERREQUESTS.START_FLAGDOWN))
                {
                    if(SETTINGSMANAGER.SETTINGS.NO_FLAGDOWN_WIHOUT_GPS.parseBoolean(DataService.this))
                    {
                        if(cabdespatchGPS.hasFix())
                        {
                            this.sendMessage(DataService.this.oMessageSys.builder.FlagDownStart(_context));
                        }
                        else
                        {
                            String message = DataService.this.getString(R.string.no_flagdown_available_no_gps_fix);

                            STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.MODE.DEFAULT, STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
                                    STATUSMANAGER.DriverMessage.BOX.INBOX,
                                    new Date().getTime(),
                                    message,
                                    false, true);
                            STATUSMANAGER.addDriverMessage(DataService.this, m);
                        }
                    }
                    else
                    {
                        this.sendMessage(DataService.this.oMessageSys.builder.FlagDownStart(_context));
                    }

                }
                else if (messagedata.endsWith(USERREQUESTS.STOP_FLAGDOWN))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.FlagDownStop(_context));
                }
                else if (messagedata.equals(USERREQUESTS.ON_RANK))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.OnRank(_context));
                }
                else if (messagedata.equals(USERREQUESTS.OFF_RANK))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.OffRank(_context));
                }
                else if (messagedata.equals(USERREQUESTS.ACCEPT_JOB))
                {
                    cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
                    j.setJobStatus(cabdespatchJob.JOB_STATUS.ACCEPTING);
                    STATUSMANAGER.setCurrentJob(_context, j);
                    this.sendMessage(DataService.this.oMessageSys.builder.Accept(_context));
                }
                else if (messagedata.equals(USERREQUESTS.REJECT_JOB))
                {
                    cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
                    j.setJobStatus(cabdespatchJob.JOB_STATUS.REJECTING);
                    STATUSMANAGER.setCurrentJob(_context, j);
                    this.sendMessage(DataService.this.oMessageSys.builder.Reject(_context));
                }
                else if (messagedata.equals(USERREQUESTS.SET_STP))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.STP(_context));
                }
                else if (messagedata.equals(USERREQUESTS.SET_POB))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.POB(_context));
                }
                else if (messagedata.equals(USERREQUESTS.SET_STC))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.STC(_context));
                }
                else if (messagedata.equals(USERREQUESTS.SET_CLEAR))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.Clear(_context));
                }
                else if (messagedata.equals(USERREQUESTS.PLOT))
                {
                    String plotString = _intent.getStringExtra(DataService._MESSAGEEXTRA);
                    plotList plots = SETTINGSMANAGER.getPlotsAll(DataService.this);

                    plot newPlot = plots.getPlotByShortName(plotString);


                    pdaLocation oldLoc = STATUSMANAGER.getCurrentLocation(DataService.this);
                    oldLoc.overridePlot(newPlot);

                    STATUSMANAGER.setCurrentLocation(DataService.this, oldLoc);

                    this.sendMessage(DataService.this.oMessageSys.builder.Plot(_context, plotString));
                }
                else if (messagedata.equals(USERREQUESTS.BID))
                {
                    String biddata = _intent.getStringExtra(DataService._MESSAGEEXTRA);
                    this.sendMessage(DataService.this.oMessageSys.builder.Bid(_context, biddata));
                }
                else if (messagedata.equals(USERREQUESTS.BACK))
                {


                    String appState = STATUSMANAGER.getAppState(DataService.this);
                    String activity = "";
                    if (appState.equals(STATUSMANAGER.APP_STATE.ON_BREAK))
                    {
                        activity = DataService._ACTIVITIES.BREAK;
                    }
                    else if (appState.equals(STATUSMANAGER.APP_STATE.WAITING_TIME))
                    {
                        activity = DataService._ACTIVITIES.WAITING_TIME;
                    }
                    else if (appState.equals(STATUSMANAGER.APP_STATE.ON_JOB))
                    {
                        cabdespatchJob j = STATUSMANAGER.getCurrentJob(DataService.this);
                        if (
                                (j.getJobStatus() == JOB_STATUS.NOT_ON_JOB)
                                        || (j.getJobStatus() == JOB_STATUS.REJECTING)
                                        || (j.getJobStatus() == JOB_STATUS.ERROR))
                        {
                            activity = _ACTIVITIES.DRIVER_SCREEN;
                        }
                        else
                        {
                            activity = DataService._ACTIVITIES.JOB_SCREEN;
                        }
                    }
                    else if (appState.equals(STATUSMANAGER.APP_STATE.LOGGED_ON))
                    {
                        activity = DataService._ACTIVITIES.DRIVER_SCREEN;
                    }
                    else if (appState.equals(STATUSMANAGER.APP_STATE.LOGGED_OFF))
                    {
                        activity = DataService._ACTIVITIES.LOGIN_SCREEN;
                    }
                    else if (appState.equals(STATUSMANAGER.APP_STATE.LAUNCHER))
                    {
                        //do nothing
                        return;
                    }
                    else
                    {
                        ErrorActivity.handleError(DataService.this, new ErrorActivity.ERRORS.UNHANDLED_APP_STATE(appState));
                    }

                    BROADCASTERS.SwitchActivity(DataService.this.getBaseContext(),
                            activity,
                            BroadcastHandler.FORCE_ACTIVITY_SWITCH,
                            String.valueOf(true));
                }
                else if (messagedata.equals(USERREQUESTS.BREAK_START))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.BreakStart(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.BREAK_END))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.BreakEnd(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.DATA_MESSAGE))
                {
                    String dataMessage = _intent.getStringExtra(DataService._MESSAGEEXTRA);
                    this.sendMessage(DataService.this.oMessageSys.builder.dataMessage(DataService.this, dataMessage));
                }
                else if (messagedata.equals(USERREQUESTS.CONFIRM_MESSAGE_READ))
                {
                    STATUSMANAGER.DriverMessage m = STATUSMANAGER.DriverMessage.parse(_intent.getStringExtra(DataService._MESSAGEEXTRA));
                    this.sendMessage(DataService.this.oMessageSys.builder.confirmMessageRead(DataService.this, m));
                }
                else if (messagedata.equals(USERREQUESTS.NO_SHOW))
                {
                    String reason = _intent.getStringExtra(DataService._MESSAGEEXTRA);
                    this.sendMessage(DataService.this.oMessageSys.builder.noShow(DataService.this));
                    this.sendMessage(DataService.this.oMessageSys.builder.dataMessage(DataService.this, reason));
                }
                else if (messagedata.equals(USERREQUESTS.PANIC))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.Panic(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.VOICE_REQUEST))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.voiceRequest(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.WAITING_TIME_START))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.WaitingTimeStart(DataService.this));

                    //this is handled by BroadcastHandler... why did we feel the need to put it here an
                    //break things??? hmmm...
                    //STATUSMANAGER.putString(DataService.this, STATUSMANAGER.STATUSES.CURRENT_ACTIVITY, DataService._ACTIVITIES.WAITING_TIME);
                    STATUSMANAGER.setAppState(DataService.this, STATUSMANAGER.APP_STATE.WAITING_TIME);
                    STATUSMANAGER.putLong(DataService.this, STATUSMANAGER.STATUSES.CURRENT_WAITING_TIME_START, System.currentTimeMillis());

                }
                else if (messagedata.equals(USERREQUESTS.WAITING_TIME_END))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.WaitingTimeEnd(DataService.this, false));
                }
                else if (messagedata.equals(USERREQUESTS.WAITING_TIME_AUTO_END))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.WaitingTimeEnd(DataService.this, true));
                }
                else if (messagedata.equals(USERREQUESTS.ON_ROUTE_STOP))
                {
                    this.sendMessage(DataService.this.oMessageSys.builder.OnRouteStop(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.RETURN_JOB))
                {
                    cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
                    j.setJobStatus(cabdespatchJob.JOB_STATUS.REJECTING);
                    STATUSMANAGER.setCurrentJob(_context, j);
                    this.sendMessage(DataService.this.oMessageSys.builder.RejectAfterAccept(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.JOB_TIMEOUT))
                {
                    cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
                    j.setJobStatus(cabdespatchJob.JOB_STATUS.REJECTING);
                    STATUSMANAGER.setCurrentJob(_context, j);
                    this.sendMessage(DataService.this.oMessageSys.builder.JobOfferTimeout(DataService.this));
                }
				/*else if(messagedata.equals(USERREQUESTS.SPECIAL_LOGOUT))
				{
					this.sendMessage(DataService.this.oMessageSys.builder.Logoff(DataService.this, "", ""));
					DataService.this.logOffPDA();
				}*/
                else if (messagedata.equals(USERREQUESTS.PRICE_UPDATE))
                {
                    String price = _intent.getStringExtra(DataService._MESSAGEEXTRA);
                    DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.PriceUpdate(DataService.this, price));
                }
                else if (messagedata.equals(USERREQUESTS.FUTURE_JOBS))
                {
                    DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.FutureJobs(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.JOB_TOTALS))
                {
                    DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.JobTotals(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.CIRCUIT_FEES))
                {
                    DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.CircuitFees(DataService.this));
                }
                else if (messagedata.equals(USERREQUESTS.POD))
                {
                    String data = _intent.getStringExtra(DataService._MESSAGEEXTRA);
                    DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.POD(DataService.this, data));
                }
                else if (messagedata.equals(USERREQUESTS.HISTORY_LOG))
                {
                    String data = _intent.getStringExtra(DataService._MESSAGEEXTRA);
                    DataService.this.oNetwork.sendMessage(DataService.this.oMessageSys.builder.logHistoryText(DataService.this, data));
                }
                else
                {
                    ErrorActivity.handleError(DataService.this, new ErrorActivity.ERRORS.UNHANDLED_USER_REQUEST(messagedata));
                }

            }


        }

        private void sendMessage(priorityString _message) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidParameterSpecException, BadPaddingException, InvalidKeyException {
            DataService.this.oNetwork.sendMessage(_message);
        }


        @Override
        public void onReceive(Context _context, Intent _intent)
        {
            try {
            String action = _intent.getAction();
            //Log.e("BROADCAST", action);
            //DEBUGMANAGER.Log(_context, "BROADCAST", action);

            if (action.equals(BROADCASTERS.PLOT_UPDATED))
            {

                this.handlePlotUpdate(_context, _intent);

            }
            else if (action.equals(BROADCASTERS.USER_REQUEST))
            {
                this.handleUserRequest(_context, _intent);
            }
            else if(action.equals(BROADCASTERS.RESET_FIREBASE_ID))
            {
                DataService.this.sendPing(true);
            }
            else if (action.equals(Intent.ACTION_BATTERY_CHANGED))
            {
                STATUSMANAGER.BATTERY_LEVEL = _intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int status = _intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;

                STATUSMANAGER.BATTERY_CHARGING = isCharging;

            }
            else if (action.equals(Intent.ACTION_SCREEN_ON))
            {
                Vibrator v = (Vibrator) DataService.this.getSystemService(Context.VIBRATOR_SERVICE);
                v.cancel();

                screen_is_on = true;
            }
            else if (action.equals(Intent.ACTION_SCREEN_OFF))
            {
                screen_is_on = false;
            }
            else
            {
                ErrorActivity.handleError(DataService.this, new ErrorActivity.ERRORS.UNHANDLED_BROADCAST_ACTION(action));
            }
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (InvalidParameterSpecException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
    }


    public static void pushFakeLocation()
    {
        setFakeLocation = true;
    }


    public static void checkOutstandingDriverMessages(Context _c)
    {
        Integer messagecount = STATUSMANAGER.getUnreadDriverMessages(_c).size();

        if(messagecount > 0)
        {
            BROADCASTERS.SwitchActivity(_c, DataService._ACTIVITIES.DRIVER_MESSAGE);
        }

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(!(getInstance()==null))
        {
            getInstance().cleanUp();
        }

        IS_RUNNING = false;
    }

    public static Boolean isRunning()
    {
        return IS_RUNNING;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        Boolean doStart = false;

        if(getServiceLooper()==null) { doStart = true; }
        else if (getServiceLooper().isDead()) { doStart = true; }

        NOTIFIERS.showAppRunning(this);

        if(doStart)
        {
           sLooper = new DataServiceLooper();

            IS_RUNNING = true;
            STOP_PENDING = false;
            DEBUGMANAGER.Log(this, "DSERV", "Starting Now...");
            Globals.registerBugHandler(this);

            if(!(getInstance()==null))
            {
                getInstance().cleanUp();
            }
            sInstance = this;

            this.oMessageSys = new cabdespatchMessageSys();
            this.oDriverNo = SETTINGSMANAGER.get(this,SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN);
            this.oCompanyId = SETTINGSMANAGER.get(this,SETTINGSMANAGER.SETTINGS.COMPANY_ID);

            int requestedPingFrequency = Integer.valueOf(SETTINGSMANAGER.SETTINGS.PING_TIME_SECONDS.getValue(this));

            if(NETWORK_DEBUG)
            {
                if(requestedPingFrequency > 5)
                {
                    requestedPingFrequency = 5;
                }
            }
            this.pingFrequency = Long.valueOf(requestedPingFrequency) * 1000;

            //let's not mess about; let's start straight away! :D
            this.lastPingTime = SystemClock.uptimeMillis() -  this.pingFrequency;

            this.oMessageHandler = new DataService.MessageHandler();


            this.registerReceiver(this.oMessageHandler, new IntentFilter(BROADCASTERS.USER_REQUEST));
            this.registerReceiver(this.oMessageHandler, new IntentFilter(BROADCASTERS.PLOT_UPDATED));
            this.registerReceiver(this.oMessageHandler, new IntentFilter(BROADCASTERS.RESET_FIREBASE_ID));
            this.registerReceiver(this.oMessageHandler, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            this.registerReceiver(this.oMessageHandler, new IntentFilter(Intent.ACTION_SCREEN_OFF));
            this.registerReceiver(this.oMessageHandler, new IntentFilter(Intent.ACTION_SCREEN_ON));





            Boolean usePinLogin = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_PIN_LOGIN.getValue(this));

            if(!(STATUSMANAGER.isLoggedIn(this)))
            {
                if(usePinLogin)
                {
                    STATUSMANAGER.clearPinLogin(this);
                }
                else
                {
                    STATUSMANAGER.setActingDriverNo(this);
                    //STATUSMANAGER.putString(this, STATUSMANAGER.STATUSES.ACTING_DRIVER_NO, SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(this));
                }
            }



            try{Thread.sleep(1000);}catch (Exception ex) {}

            String signalRHost = SETTINGSMANAGER.getSignalRHost(this);
            if(false)
            {
                signalRHost = "https://signalrtest.cabdespatch.com/signalr";
                SETTINGSMANAGER.SETTINGS.SignalRHost.putValue(this, signalRHost);
            }


            fireData = SETTINGSMANAGER.SETTINGS.FIRE_DATA.parseBoolean(this);

            if(isFireData() && false) //no firedata
            {
                this.oNetwork = new CabDespatchNetworkFire(oCompanyId, oDriverNo);
            }
            else
            {
                String device_id = Globals.CrossFunctions.getDeviceIdentifier_wasIMEI(this);
                String proxy_token = STATUSMANAGER.STATUSES.PROXY_ACCESS_TOKEN.getValue(this);



                if(NETWORK_DEBUG) //DEBUG :)
                {
                    this.oNetwork = new CabDespatchNetworkHttp(oCompanyId, Integer.valueOf(oDriverNo), device_id, proxy_token);
                }
                else if(SETTINGSMANAGER.SETTINGS.DATA_MODE.getValue(this).equals(CabDespatchNetworkOldSty._CONNECTION_TYPE.ANDROID_HTTP))
                {
                    this.oNetwork = new CabDespatchNetworkHttp(oCompanyId, Integer.valueOf(oDriverNo), device_id, proxy_token);
                }
                else if(signalRHost.equals(Settable.NOT_SET))
                {
                    this.oNetwork = new CabDespatchNetworkOldSty(this, false);
                }
                else
                {
                    this.oNetwork = new CabDespatchNetworkSignalR(this, signalRHost);
                }
            }

            Boolean useGPS = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.ENABLE_GPS.getValue(this));


            try
            {
                this.gps = new cabdespatchGPS(this, useGPS);
            }
            catch (Exception ex)
            {
                //CLAY
                DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR, "GPS", "DEVICE DOES NOT HAVE GPS");
            }


            //CLAY check for null reference if SignalRHost is unavailable
            this.oNetwork.start();


           sLooper.start();
           return Service.START_STICKY;
        }
        else
        {
             sLooper.cancel();
             requestStop(this);
             return Service.START_NOT_STICKY;
        }


    }



// Magic Words & Numbers

    public final class _MESSAGETYPES
    {
        public static final String ACTIVITY_SWITCH = "ACTIVITY_SWITCH";
        public static final String ACTION = "ACTION";
        public static final String USER_REQUEST = "USER_REQUEST";
    }

    public final class _ACTIVITIES
    {
        public static final String DRIVER_SCREEN = "DRIVER_SCREEN";
        public static final String JOB_SCREEN = "JOB_SCREEN";
        public static final String LOGIN_SCREEN = "LOGIN_SCREEN";
        public static final String JOB_OFFER = "JOB_OFFER";
        public static final String DRIVER_MESSAGE = "DRIVER_MESSAGE";
        public static final String BREAK = "BREAK";
        public static final String WAITING_TIME = "WAITING_TIME";
    }

    public final class _ACTIONS
    {
        public static final String NETWORK_ON = "NETWORK_ON";
        public static final String NETWORK_OFF = "NETWORK_OFF";
        public static final String NETWORK_RECONNECTING = "NETWORK_RECONNECTING";
        public static final String LOGOUT = "LOGOUT";
    }

    public static final String _MESSAGETYPE = "MESSAGE_TYPE";
    public static final String _MESSAGEDATA = "MESSAGE_DATA";
    public static final String _MESSAGEEXTRA = "MESSAGE_EXTRA";
    public static final String _LOGTAG = "LOG_TAG";
    public static final String _LOGMESSAGE = "LOG_MESSAGE";
    public static final String _CURRENTACTIVITY = "CURRENT_ACTIVITY";


}
