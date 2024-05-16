package com.cabdespatch.driverapp.beta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.cabdespatch.driverapp.beta.STATUSMANAGER.Status._RESET_TYPE;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;
import com.cabdespatch.driverapp.beta.cabdespatchJob.JOB_STATUS;

import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class STATUSMANAGER
{

    private static Boolean sIsLocked = false;
	public static AnyActivity CURRENT_ACTIVITY;

    public static Boolean aquireLock()
    {
        if (sIsLocked)
        {
            //another activity has not yet released
            //it's lock
            return false;
        }
        else
        {
            sIsLocked = true;
            return true;
        }
    }

    public static Boolean hasDebugConnector(Context _c)
    {
        return STATUSMANAGER.getBoolean(_c, STATUSES.HAS_DEBUG_CONNECTOR);
    }

    public static void releaseLock(Context _c)
    {
        sIsLocked = false;
        BROADCASTERS.ReleaseLock(_c);
    }

    public static Boolean isLocked()
    {
        return sIsLocked;
    }

    public static enum INSTALL_SOURCE
    {
        GOOGLE_PLAY,CAB_DESPATCH_HOSTED;
    }
    public static class APP_STATE
	{
		public static final String LAUNCHER = "launcher";
		public static final String LOGGED_OFF = "loggedoff";
		public static final String LOGGED_ON = "loggedon";
		public static final String ON_JOB = "onjob";
        public static final String PAUSED = "paused"; //awaiting response from server to user action
		public static final String ON_BREAK = "onbreak";
		public static final String WAITING_TIME = "waitingtime";
	}
	public static class JOB_STATE
	{
		public static final String ON_ROUTE = "ort";
		public static final String SOON_TO_PICKUP = "stp";
		public static final String PASSENGER_ON_BOARD = "pob";
		public static final String SOON_TO_CLEAR = "stc";
	}
	
	public static int BATTERY_LEVEL = -1;
    public static Boolean BATTERY_CHARGING = false;
    private static double OLD_SPEED = 0;

    /**
     *
     * @deprecated use SETTABLE instead
     */
    @Deprecated
	public static class Status<T>
	{
		//Java has no way to find out the TYPE
		//of a generic class
		
		//I mean.... for real?!
		
		private String oKey;
		private T oDefaultValue;
        public static final String NOT_SET = "_NOT_SET";
		
		public enum _RESET_TYPE
		{
			STRING, INTEGER, LONG, BOOLEAN;
		}
		
		
		public Status(String _key, T _defaultValue)
		{
			this.oKey = _key;
			this.oDefaultValue = _defaultValue;
		}


		
		public String getKey() { return this.oKey; }
		public T getDefaultValue() { return this.oDefaultValue; }

		public void reset(Context _c, _RESET_TYPE type)
		{
			SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
			switch (type)
			{
			case BOOLEAN:
				e.putBoolean(this.oKey, (Boolean) this.oDefaultValue);
				break;
			case INTEGER:
				e.putInt(this.oKey, (Integer) this.oDefaultValue);
				break;
			case LONG:
				e.putLong(this.oKey, (Long) this.oDefaultValue);
				break;
			case STRING:
				e.putString(this.oKey, (String) this.oDefaultValue);
				break;
			default:
				ErrorActivity.handleError(_c, new ErrorActivity.ERRORS.CLASS_TYPE_NOT_FOUND_FOR_STATUS(this.getKey()));
				break;
			
			}
			
			e.commit();
		}
	}

	public static class DriverMessage
    {
        public static class BOX
        {
            public static final Integer INBOX = 100;
            public static final Integer OUTBOX = 200;
            public static final Integer ALL = 99999;
        }

        public static class TYPE
        {
            public static final Integer DATA_MESSAGE = 100;
            public static final Integer SMS = 200;
        }

        public static class MODE
        {
            public static final Integer DISPLAY = 1;
            public static final Integer SPEAK = 2;
            public static final Integer DISPLAY_AND_SPEAK = 3;

            public static final Integer DEFAULT = MODE.DISPLAY;
        }



        private String oID;
        private Integer oType;
        private Integer oBox;
        private Integer oMode;
        private Long oDateTime;
        private String oMessage;
        private Boolean oConfirmationRequired;
        private Boolean oConfirmationReceived;
        private Boolean oHasBeenDisplayed;
        private Boolean oIsJobLess;
        private Boolean oDriverLess;
        private Integer oTimeOutSeconds = 30;

        private static final char SPLITTER = (char) 167;
        private static Integer lastID;

        private Boolean shouldBeDriverLess()
        {
            List<String> dLessMsgs = new ArrayList<String>();

            dLessMsgs.add("your device is incorrectly configured".toUpperCase());
            dLessMsgs.add("Incorrect PIN entered".toUpperCase());
            dLessMsgs.add("You are not permitted to use this vehicle".toUpperCase());

            Boolean match = false;
            for(String s:dLessMsgs)
            {
                if(oMessage.toUpperCase().contains(s))
                {
                    match = true;
                    break;
                }
            }

            return match;
        }

        private DriverMessage(Integer _mode, Integer _type, Integer _box, Long _dateTime, String _id, String _message, Boolean _confirmationRequired, Boolean _isJobLess)
        {
            this.oID = _id;

            this.oType = _type; this.oBox = _box;
            this.oDateTime = _dateTime; this.oMessage = _message;
            this.oConfirmationRequired = _confirmationRequired;
            this.oConfirmationReceived = false;
            this.oHasBeenDisplayed = false;
            this.oIsJobLess = _isJobLess;
            this.oDriverLess = shouldBeDriverLess();
            this.oMode = _mode;

            //clay
            //this.oConfirmationRequired = true;
        }

		public DriverMessage(Integer _mode, Integer _type, Integer _box, Long _dateTime, Integer _id, String _message, Boolean _confirmationRequired, Boolean _isJobLess)
		{
            if(_id < 0)
            {
                if(lastID==null)
                {
                    lastID = 0;
                }
                lastID += 1;

                this.oID = "ANDID_" + _id.toString();
            }
            else
            {
                this.oID = "SERID_" + _id.toString();
            }


			this.oType = _type; this.oBox = _box;
			this.oDateTime = _dateTime; this.oMessage = _message;
            this.oConfirmationRequired = _confirmationRequired;
            this.oConfirmationReceived = false;
            this.oHasBeenDisplayed = false;
            this.oIsJobLess = _isJobLess;
            this.oDriverLess = shouldBeDriverLess();
            this.oMode = _mode;

            //clay
            //this.oConfirmationRequired = true;
		}

        public DriverMessage(Integer _mode, Integer _type, Integer _box, Long _dateTime, String _message, Boolean _confirmationRequired, Boolean _isJobLess)
        {
            this(_mode, _type, _box, _dateTime, -999, _message, _confirmationRequired, _isJobLess);

            //clay
            //this.oConfirmationRequired = true;
        }

        public Boolean isConfirmationRequired() { return  this.oConfirmationRequired; }
        public Boolean hasConfirmationBeenReceived() { return this.oConfirmationReceived; }
        public Boolean hasBeenDisplayed() { return this.oHasBeenDisplayed; }
		public String getMessage() { return this.oMessage; }
		public Long getDateTime() { return this.oDateTime; }
		public Integer getType() { return this.oType; }
		public Integer getMode() { return  this.oMode; }
        public String getID() { return this.oID; }
        public Boolean isJobLess() {return this.oIsJobLess; }
        public Boolean isDriverLess() { return this.oDriverLess; }
        public String getIDForConfirmation()
        {
            String[] retVals = this.oID.split("_");

            return retVals[1];
        }
		public Integer getBox() { return this.oBox; }

        @Override
        public String toString()
        {
            String ret = "";
            ret += addElement(oID.toString(),false);
            ret += addElement(oType.toString(), false);
            ret += addElement(oBox.toString(),false);
            ret += addElement(oDateTime.toString(),false);
            ret += addElement(oMessage.toString(),false);
            ret += addElement(oConfirmationRequired.toString(),false);
            ret += addElement(oConfirmationReceived.toString(),false);
            ret += addElement(oHasBeenDisplayed.toString(),false);
            ret += addElement(oIsJobLess.toString(), false);
            ret += addElement(oMode.toString(), true);

            return ret;
        }

        public String getSubStringMatcher()
        {
            String ret = "";
            ret += addElement(oID.toString(),false);
            ret += addElement(oType.toString(), false);
            ret += addElement(oBox.toString(),false);
            ret += addElement(oDateTime.toString(),false);
            ret += addElement(oMessage.toString(),true);

            return ret;
        }


        public static DriverMessage asDisplayed(DriverMessage _msg)
        {
            DriverMessage m = DriverMessage.parse(_msg.toString());
            m.oHasBeenDisplayed = true;
            return m;
        }

        public static DriverMessage asConfirmed(DriverMessage _msg)
        {
            DriverMessage m = DriverMessage.parse(_msg.toString());
            m.oConfirmationReceived = true;
            return m;
        }

        public static DriverMessage parse(String _data)
        {
            String[] eles = _data.split(String.valueOf(SPLITTER));

            String id = eles[0];
            Integer type = Integer.valueOf(eles[1]);
            Integer box = Integer.valueOf(eles[2]);
            Long dateTime = Long.valueOf(eles[3]);
            String message = eles[4];
            Boolean confRequired = Boolean.valueOf(eles[5]);
            Boolean confRecd = Boolean.valueOf(eles[6]);
            Boolean displayed = Boolean.valueOf(eles[7]);
            Integer mode = MODE.DEFAULT;
            Boolean isJobLess = false;
            if(eles.length > 8)
            {
                isJobLess = Boolean.valueOf(eles[8]);
            }
            if(eles.length > 9){ mode = Integer.valueOf(eles[9]);}

            DriverMessage m = new DriverMessage(mode, type, box, dateTime, id, message, confRequired, isJobLess);
            if(confRecd) { m = DriverMessage.asConfirmed(m); }
            if(displayed) { m = DriverMessage.asDisplayed(m); }

            return m;
        }

        private String addElement(String _element, Boolean _isFinal)
        {
            if(_isFinal)
            {
                return _element;
            }
            else
            {
                return _element + SPLITTER;
            }
        }

	}
	
	//private static pdaLocation currentLocation = null;
	//private static List <DriverMessage> driverMessages;


    private static SettableSet DriverMessages = new SettableSet("_DRIVERMESSAGESRECD");

	
	public static class STATUSES
	{
        public static final Status<Boolean> ALLOW_LOCAL_SMS            = new Status<Boolean>("_ALLOW_LOCAL_SMS", false);
        public static       Status<Boolean> JOB_ACCEPT_PENDING         = new Status<Boolean>("_JOB_ACCEPT_PENDING", false);
        //Remember to add to reset sub...
        public static       Status<Boolean> HAS_NETWORK_CONNECTION     = new Status<Boolean>("_HASNETWORK", false);
        public static       Status<Boolean> HAS_VALID_GPS              = new Status<Boolean>("_HASVALIDGPS", false);
        public static       Status<String>  CURRENT_APP_STATE          = new Status<String>("_APPSTATE", APP_STATE.LAUNCHER);
        public static       Status<String>  JOB_STATE                  = new Status<String>("_JOBSTATE", "null");
        public static       Status<String>  CURRENT_JOB                = new Status<String>("_CURRENTJOB", "null");
        public static       Status<String>  CURRENT_ACTIVITY           = new Status<String>("_CURRENTACTIVITY", "null");
        public static       Status<String>  STATUS_BAR_TEXT            = new Status<String>("_STATUSBARTEXT", "");
        public static       Status<Integer> CHECK_IN_ATTEMPTS          = new Status<Integer>("_PREVIOUSDRIVERSTATUS", 7); //
        public static       Status<Integer> CHECK_IN_ATTEMPTS_FAKE     = new Status<Integer>("_INVALIDATION", 7); //
        public static       Status<String>  CARS_AVAILIABLE            = new Status<String>("_CARSAVAILIABLE", "");
        public static       Status<String>  WORK_AVAILIABLE            = new Status<String>("_WORKAVAILIABLE", "");
        public static       Status<String>  PENDING_DRIVER_MESSAGES    = new Status<String>("_PENDINGDRIVERMESSAGES", "");
        public static       Status<String>  CURRENT_LAT                = new Status<String>("_CURRENT_LAT", "-1");
        public static       Status<String>  CURRENT_LON                = new Status<String>("_CURRENT_LON", "-1");
        public static       Status<String>  CURRENT_PLOT               = new Status<String>("_CURRENT_PLOT", plot.ERROR_PLOT);
        public static       Status<Boolean> CURRENT_PLOT_IS_RANK       = new Status<Boolean>("_CURRENT_PLOT_IS_RANK", false);
        //NOTE Speed is in meters per second. To get mph, use STATUSMANAGER.getSpeedAsMPH();
        public static       Status<String>  CURRENT_SPEED              = new Status<String>("_CURRENT_SPEED", "-1");
        public static       Status<Boolean> NO_NO_SHOW                 = new Status<Boolean>("_NO_NO_SHOW", false);
        public static       Status<String>  CURRENT_FIX_ACCURACY       = new Status<String>("_CURRENT_FIX_ACCURACY", "0");
        public static       Status<Long>    CURRENT_BREAK_START        = new Status<Long>("_CURRENT_BREAK_START", 0l);
        public static       Status<Long>    CURRENT_WAITING_TIME_START = new Status<Long>("_CURRENT_WAIT_TIME_START", 0l);
        public static       Status<Integer> TOTAL_BREAK_USED_SECONDS   = new Status<Integer>("_TOTAL_BREAK_USED_SECONDS", 0);
        public static       Status<Boolean> IS_PANIC                   = new Status<Boolean>("_IS_PANIC", false);
        public static       Status<String>  STORED_PASSWORD            = new Status<String>("_STORED_PASSWORD", "");
        private static       Status<String>  ACTING_DRIVER_NO           = new Status<String>("_ACTING_DRIVER_NO", "null");
        public static       Status<String>  ACTING_VEHICLE_NO          = new Status<String>("_ACTING_VEHICLE_NO", "null");
        public static       Status<String>  ACTING_PIN_NO              = new Status<String>("_ACTING_PIN_NO", "null");
        public static       Status<String>  TRAP_OVERALL               = new Status<String>("TRAP_OVERALL", "?/?");
        public static       Status<String>  TRAP_INDIVIDUAL            = new Status<String>("TRAP_INDIVIDUAL", "?/?");
        public static       Status<Integer> DATA_SERVICE_KILL_COUNT    = new Status<Integer>("_DATA_SERVICE_KILL_COUNT", 0);
        public static       Status<Boolean> HAS_DEBUG_CONNECTOR        = new Status<Boolean>("_HAS_CAKE", false);
        public static       Status<String>  LAST_ENGINEER_SESSION      = new Status<String>("_ENGINEER_SESSION_DATE", new DateTime().minusDays(5).toString());
        public static       Status<String>  FUTURE_JOBS                = new Status<String>("_FUTURE_WORK", "");
        public static       Status<Boolean> HAS_BEEN_LOGGED_ON         = new Status<Boolean>("_HAS_BEEN_LOGGED_ON", false);
        public static       Status<String>  PENDING_ACTIVITY           = new Status<String>("_PENDING_ACTIVITY", Status.NOT_SET);
        public static       Status<Boolean> ON_RANK                    = new Status<Boolean>("_ON_RANK", false);
        public static       Settable PAY_AND_GO_BALANCE         = new Settable( "PG_BALANCE", -1);
        //public static       Settable PAY_AND_GO_PAYMENT_INTENT_CLIENT_SECRET = new Settable("PG_INTENT", "");
        //public static       Settable PAY_AND_GO_PAYMENT_INTENT_PUBLISHABLE_KEY = new Settable("PG_KEY", "");

        //retired when ticking meter handling was moved into data service
        //public static       Settable METER_CACHE = new Settable("_XMTRCXH", 0);
        public static final Settable TICKING_METER_VALUE = new Settable("_XMTMV1", 0);

        public static final Settable COUNTER_WAITING_TIME = new Settable("COUNTER_WAITING_TIME", "-123456");
        public static final Settable COUNTER_BREAK = new Settable("COUNTER_BREAK", "-123456");
        public static final Settable COUNTER_JOB_OFFER = new Settable("COUNTER_JOB_OFFER", "-123456");
        public static final Settable REQUEST_BREAK_ON_CLEAR = new Settable("BRK_ON_CLR", false);

        private static final Settable FIREBASE_INSTANCE_ID = new Settable("xinstF", "");

        public static final Settable HIDE_MENU_BUTTON = new Settable("hmb_brkcht", true);

        public static final Settable ACTIVATION_KEY = new Settable("new_v_device_key", Settable.NOT_SET);

        public static final Settable MOCK_IMEI = new Settable("mim_56465", Settable.NOT_SET);
        public static final Settable MOCK_SIM_NO = new Settable("msno_48798749", Settable.NOT_SET);

        public static final Settable PROXY_ACCESS_TOKEN = new Settable("proxy_access_token", Settable.NOT_SET);
    }

    public static String getEmptyFirebaseId()
    {
        return  STATUSES.FIREBASE_INSTANCE_ID.getDefaultValue();
    }

    public static String getFirebaseInstanceId(Context _context)
    {
    	if(SETTINGSMANAGER.SETTINGS.FIRE_DATA.parseBoolean(_context))
		{
			return STATUSES.FIREBASE_INSTANCE_ID.getValue(_context);
		}
		else
		{
			return "";
		}

    }

    public static void setFirebaseInstanceId(Context _context, String _id)
    {
        STATUSES.FIREBASE_INSTANCE_ID.putValue(_context, _id);
    }


    public static List<String> getWorkAvailable(Context _c)
    {
        String work = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.WORK_AVAILIABLE);

        List<String> workList = new ArrayList<String>();

        String[] w = work.split("-");

        for (String p : w)
        {
            if (!(p.isEmpty()))
            {
                workList.add(p);
            }
        }

        return workList;
    }

    /*
    public static void addPendingDriverMessage(Context _c, String _message)
    {
        String messages = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.PENDING_DRIVER_MESSAGES);
        if (!(messages.isEmpty()))
        {
            messages += String.valueOf(cabdespatchJob.SPLITTER);
        }
        messages += _message;

        STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.PENDING_DRIVER_MESSAGES, messages);
    }

    public static List<String> getPendingDriverMessages(Context _c)
    {
        String messages = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.PENDING_DRIVER_MESSAGES);

        STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.PENDING_DRIVER_MESSAGES, "");

        String[] msgs = messages.split(String.valueOf(cabdespatchJob.SPLITTER));

        List<String> items = new LinkedList<String>();
        for (String s : msgs)
        {
            if (!(s.isEmpty()))
            {
                items.add(s);
            }
        }

        return items;
    } */



    public static paramGroup getCarsAvailable(Context _c)
    {
        String cars = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CARS_AVAILIABLE);

        paramGroup carList = new paramGroup();

        String[] c = cars.split("-");

        for (String v : c)
        {
            String[] plotandcount = v.split(":");

            if (plotandcount.length == 2)
            {
                carList.addParam(plotandcount[0], plotandcount[1]);
            }
        }

        return carList; //param.getName() - short plot name, param.getValue() - amount of cars in plot
    }

    public static paramGroup getFutureJobs(Context _c)
    {
        String cars = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.FUTURE_JOBS);

        paramGroup jobList = new paramGroup();

        String[] c = cars.split("-");

        for (String v : c)
        {
            String[] plotandcount = v.split(":");

            if (plotandcount.length == 2)
            {
                jobList.addParam(plotandcount[0], plotandcount[1]);
            }
        }

        return jobList; //param.getName() - short plot name, param.getValue() - amount of jobs in plot
    }

    public static Boolean getBoolean(Context _c, Status<Boolean> _s)
    {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        return p.getBoolean(_s.getKey(), _s.getDefaultValue());
    }

    public static Boolean putBoolean(Context _c, Status<Boolean> _s, Boolean _value)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putBoolean(_s.getKey(), _value);
        return e.commit();
    }

    public static String getString(Context _c, Status<String> _s)
    {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        String retVal = p.getString(_s.getKey(), _s.getDefaultValue());
        return retVal;
    }

    public static Boolean putString(Context _c, Status<String> _s, String _value)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putString(_s.getKey(), _value);
        return e.commit();
    }

    public static int getInt(Context _c, Status<Integer> _s)
    {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        return p.getInt(_s.getKey(), _s.getDefaultValue());
    }

    public static Boolean putInt(Context _c, Status<Integer> _s, int _value)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putInt(_s.getKey(), _value);
        return e.commit();
    }

    public static Long getLong(Context _c, Status<Long> _s)
    {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        return p.getLong(_s.getKey(), _s.getDefaultValue());
    }

    public static Boolean putLong(Context _c, Status<Long> _s, long _value)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putLong(_s.getKey(), _value);
		return e.commit();
	}
	
	public static String getStatusBarText(Context _c) 
	{ 
		return STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.STATUS_BAR_TEXT); 
	}

    public static void setStatusBarText(final Context _c, String _text)
    {
        STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.STATUS_BAR_TEXT, _text);

        Intent Message = new Intent(BROADCASTERS.STATUS_UPDATE);
        _c.sendBroadcast(Message);
    }
	
	public static void setStatusBarText(final Context _c, final String _text, int _preDelay)
	{
        new PauseAndRun()
        {
            @Override
            protected void onPostExecute(Void _void)
            {
                setStatusBarText(_c, _text);
            }
        }.Start(_preDelay);

	}
	
	public static void setStatusBarText(Context _c)
	{
		Boolean loggedIn = (!(STATUSMANAGER.getAppState(_c).equals((STATUSMANAGER.APP_STATE.LOGGED_OFF))));
		
		if(loggedIn)
		{
			String p = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_PLOT);
			String text="[" + p + "] ";
			
			if(STATUSMANAGER.getCurrentJob(_c).getJobStatus()==JOB_STATUS.NOT_ON_JOB)
			{
				text += STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.TRAP_INDIVIDUAL);
				text += ",";
				text += STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.TRAP_OVERALL);		
			}
			
			STATUSMANAGER.setStatusBarText(_c, text);
					
		}
		
			
		
	}
	
	public static cabdespatchJob getCurrentJob(Context _c)
	{
		String job = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_JOB);
		return cabdespatchJob.unpack(job);
	}
	
	public static void setCurrentJob(Context _c, cabdespatchJob _j)
	{
		String jobString = _j.pack();
		STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_JOB, jobString);
	}
	

	public static pdaLocation getCurrentLocation(Context _c)
	{

		String sCurrentPlot = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_PLOT);
		Double lat = Double.valueOf(STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LAT));
		Double lon = Double.valueOf(STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LON));
		Double acc = Double.valueOf(STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_FIX_ACCURACY));
		Double speed = Double.valueOf(STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_SPEED));
        Boolean currentPlotIsRank = STATUSMANAGER.getBoolean(_c, STATUSES.CURRENT_PLOT_IS_RANK);

		return new pdaLocation(_c, lat, lon, acc, speed, 0, SETTINGSMANAGER.getPlotsAll(_c), sCurrentPlot, currentPlotIsRank);
	}

	private static DateTime LastSpeedCheck = DateTime.now();

	public static void setCurrentLocation(Context _c, pdaLocation _p)
	{
		
		//STATUSMANAGER.currentLocation = _p;
		if(!(_p.getPlot().getShortName().equals(plot.ERROR_PLOT)))
		{
			STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_PLOT, String.valueOf(_p.getPlot().getShortName()));
		}

        STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_LAT, String.valueOf(_p.getLat()));
		STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_LON, String.valueOf(_p.getLon()));
        //NOTE Speed is in meters per second. To get mph, use STATUSMANAGER.getSpeedAsMPH();
		STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_SPEED, String.valueOf(_p.getSpeed()));
		STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_FIX_ACCURACY, String.valueOf(_p.getAccuracy()));
        putBoolean(_c, STATUSES.CURRENT_PLOT_IS_RANK, _p.getPlot().isRank());

        Double newSpeed = Double.valueOf(_p.getSpeed());
        if(!(newSpeed.equals(OLD_SPEED)))
        {
            OLD_SPEED = newSpeed;
            BROADCASTERS.SpeedChanged(_c);
        }
        else if (LastSpeedCheck.plusMinutes(1).getMillis()  < DateTime.now().getMillis())
        {
            LastSpeedCheck = DateTime.now();
            BROADCASTERS.SpeedChanged(_c);

        }
	}

    public static Double getSpeedAsMPH(Context _c)
    {
        Double cSpeed = Double.valueOf(STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_SPEED));
        cSpeed *= 2.2369; //meters per seconf to miles per hour

        return  cSpeed;
    }


    private static List<DriverMessage> getDriverMessages(Context _c, Integer _box, Boolean _onlyUnread)
    {
        List<DriverMessage> messages = new ArrayList<DriverMessage>();

        synchronized (DriverMessages)
        {
            for(String m:DriverMessages.getEntries(_c))
            {
                DriverMessage msg = DriverMessage.parse(m);
                if((msg.getBox().equals(_box)) || (_box.equals(DriverMessage.BOX.ALL)))
                {
                    if(_onlyUnread)
                    {
                        if(!(msg.hasBeenDisplayed()))
                        {
                            messages.add(msg);
                        }
                    }
                    else
                    {
                        messages.add(msg);
                    }
                }
            }
        }


        class messageSorter implements Comparator<DriverMessage>
        {

            @Override
            public int compare(DriverMessage lhs, DriverMessage rhs)
            {
                return lhs.getDateTime().compareTo(rhs.getDateTime());
            }

            @Override
            public boolean equals(Object object)
            {
                return false;
            }
        }

        Collections.sort(messages, new messageSorter());
        return  messages;
    }

    public static List<DriverMessage> getDriverMessages(Context _c, Integer _box)
    {
        return getDriverMessages(_c, _box, false);
    }

    public static DriverMessage getDriverMessageById(Context _c, String _id)
    {
        for (DriverMessage m:getDriverMessages(_c, DriverMessage.BOX.ALL))
        {
            if (m.getID().equals(_id))
            {
                return m;
            }
        }

        return null;
    }

    public static List<DriverMessage> getUnreadDriverMessages(Context _c)
    {
        return getDriverMessages(_c, DriverMessage.BOX.INBOX, true);
    }

    public static DriverMessage getFirstUnconfirmedDriverMessage(Context _c)
    {
        List<DriverMessage> messages = getDriverMessages(_c, DriverMessage.BOX.INBOX, false); //even if it's been displayed
                                                                                              //it may not have been confirmed

        for(DriverMessage m:messages)
        {
            if(m.isConfirmationRequired())
            {
                if(!(m.hasConfirmationBeenReceived()))
                {
                    return m;
                }
            }
        }

        return null;
    }

    public static void clearDriverMessages(Context _c, Boolean _keepDriverlessMessages)
    {
        if(_keepDriverlessMessages)
        {
            List<DriverMessage> driverLessMessages = new ArrayList<>();
            for(DriverMessage m:getDriverMessages(_c, DriverMessage.BOX.INBOX))
            {
                if(m.isDriverLess())
                {
                    driverLessMessages.add(m);
                }
            }
            STATUSMANAGER.DriverMessages.reset(_c);
            for(DriverMessage keep:driverLessMessages)
            {
                STATUSMANAGER.addDriverMessage(_c, keep);
            }
        }
        else
        {
            STATUSMANAGER.DriverMessages.reset(_c);
        }
    }

    public static Boolean addDriverMessage(Context _c, DriverMessage _msg)
    {
        return DriverMessages.addValue(_c, _msg.toString());
    }

    public static Boolean addErrorAsDriverMessage(Context c, String msg)
    {
        STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage
                (STATUSMANAGER.DriverMessage.MODE.DISPLAY,
                        STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
                        STATUSMANAGER.DriverMessage.BOX.INBOX,
                        new Date().getTime(),
                        msg,
                        false, true);

        return addDriverMessage(c, m);
    }

    public static DriverMessage setDriverMessageToDisplayed(Context _c, DriverMessage _msg)
    {
        if(_msg==null)
        {
            return _msg; //CLAY uhmmm...?
        }
        else
        {
            if((DriverMessages.removeSubStringMatches(_c, _msg.getSubStringMatcher())) > 0)
            {
                DriverMessage newMessage = DriverMessage.asDisplayed(_msg);
                if (DriverMessages.addValue(_c, newMessage.toString()))
                {
                    return newMessage;
                }
                else
                {
                    return _msg;
                }
            }
            else
            {
                return _msg;
            }
        }
    }

    public static DriverMessage setDriverMessageToConfirmed(Context _c, DriverMessage _msg)
    {
        if(_msg==null)
        {
            return _msg; //CLAY uhmmm...?
        }
        else
        {
            if((DriverMessages.removeSubStringMatches(_c, _msg.getSubStringMatcher())) > 0)
            {
                DriverMessage newMessage = DriverMessage.asConfirmed(_msg);
                if(DriverMessages.addValue(_c, newMessage.toString()))
                {
                    return  newMessage;
                }
                else
                {
                    return  _msg;
                }
            }
            else
            {
                return _msg;
            }
        }
    }

    /*
	
	public static List<DriverMessage> getDriverMessages(Context _c)
	{
		if (STATUSMANAGER.driverMessages == null ) STATUSMANAGER.driverMessages = new ArrayList<DriverMessage>();
		return STATUSMANAGER.driverMessages;
	}
	
	public static List<DriverMessage> getDriverMessages(Context _c, DriverMessage.BOX _box)
	{
		if (STATUSMANAGER.driverMessages == null ) STATUSMANAGER.driverMessages = new ArrayList<DriverMessage>();
		List<DriverMessage> matches = new ArrayList<DriverMessage>();
		
		for(DriverMessage m:STATUSMANAGER.driverMessages)
		{
			if (m.getBox() == _box)
			{
				matches.add(m);
			}
		}
		
		return matches;
	}
	
	public static void addDriverMessage(DriverMessage m)
	{
		if (STATUSMANAGER.driverMessages == null ) STATUSMANAGER.driverMessages = new ArrayList<DriverMessage>();
		STATUSMANAGER.driverMessages.add(0, m);
	}
	*/
	
	public static boolean isLoggedIn(Context _c)
	{
		String currentappstate = STATUSMANAGER.getAppState(_c);
		
		if(currentappstate.equals(STATUSMANAGER.APP_STATE.LOGGED_OFF))
		{
			return false;
		}
		else if(currentappstate.equals(STATUSMANAGER.APP_STATE.LAUNCHER))
		{
			return false;
		}
		else
		{
			return true;
		}
						
	}
	
	
	public static String getAppState (Context _c)
	{
		return STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_APP_STATE);
	}
	
	public static void setAppState(Context _c, String _appState)
	{
		STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_APP_STATE, _appState);
	}
	
	public static void clearPinLogin(Context _c)
	{
		STATUSMANAGER.STATUSES.ACTING_DRIVER_NO.reset(_c,_RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.ACTING_VEHICLE_NO.reset(_c,_RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.ACTING_PIN_NO.reset(_c,_RESET_TYPE.STRING);
        STATUSMANAGER.clearDriverMessages(_c, true);
        JOBHISTORYMANAGER.clearJobHistory(_c);
	}
	
	public static void configPinLogin(Context _c, String _driverNo, String _vehicleNo, String _pinNo)
	{
		STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.ACTING_DRIVER_NO, _driverNo);
		STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.ACTING_VEHICLE_NO, _vehicleNo);
		STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.ACTING_PIN_NO, _pinNo);
	}

    public static String getAppVersionNumber(Context _c)
    {
        String verNo = getAppVersion(_c);

        if(verNo.contains("-"))
		{
			verNo = verNo.split("-")[0];
		}
		return verNo.substring(0, verNo.length() -1);

    }

    public static Integer getAppRevision()
	{
		return 0;
	}


    /**
     * <strong>NOTE this will include an alphabetic suffix to denote install source</strong>
     *
     * Use getAppVersionNumber(Context) to return a string with the suffix removed
     *
     * @param _c Any suitable context
     * @return a string containing the version number, including install location suffix
     */
    public static String getAppVersion(Context _c)
    {
        String versionName = "UNKNOWN";
        try
        {
            versionName = _c.getPackageManager().getPackageInfo(_c.getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
		catch (Exception ex)
		{
			//package manager has died... Android you are so ****
			ex.printStackTrace();
		}

        return versionName;
    }


    public static String getExquisiteAppVersion(Context _c)
    {
    	if(getAppRevision()>0)
		{
			return getAppVersion(_c) + "\nRevision " + String.valueOf(getAppRevision());
		}
		else
		{
			return getAppVersion(_c);
		}

    }

    public static String getInstallDate(Context _c)
    {
        String installDate = "UNKNOWN";

        ApplicationInfo appInfo = null;
        try
        {
            appInfo = _c.getPackageManager().getApplicationInfo(_c.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        String appFile = appInfo.sourceDir;
        long installed = new File(appFile).lastModified(); //Epoch Time
        DateTime dt = new DateTime(installed);
        installDate = dt.toString("dd/MM/yyyy HH:mm");

        return installDate;
    }

    public static INSTALL_SOURCE getInstallSource(Context _c)
    {
        return  INSTALL_SOURCE.CAB_DESPATCH_HOSTED;
        /*
        String appVersion = STATUSMANAGER.getAppVersion(_c);

        if(appVersion.endsWith("p"))
        {
            return INSTALL_SOURCE.GOOGLE_PLAY;
        }
        else if(appVersion.endsWith("c"))
        {
            return INSTALL_SOURCE.CAB_DESPATCH_HOSTED;
        }
        else
        {
            return null;
        }*/
    }

    public static Boolean hasConflictingPackages(Context _c)
    {
        Boolean conflict = isAppInstalled(_c, "com.cabdespatch.driverapp.beta");

        if(_c.getPackageName().equals("com.cabdespatch.driverapp.beta"))
        {
            return false;
        }
        else
        {
            return conflict;
        }
    }

    private static Boolean isAppInstalled(Context _c, String packageName) {
        PackageManager pm = _c.getPackageManager();
        boolean installed = false;
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            if(pi.applicationInfo.packageName.equals(packageName))
            {
                installed = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static boolean isFlightModeEnabled(Context context)
    {
        boolean mode = false;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            // API 17 onwards
            mode = Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        } else {
            // API 16 and earlier.
            mode = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        }
        return mode;
    }

    private static final String COMMAND_FLIGHT_MODE_1 = "settings put global airplane_mode_on";
    private static final String COMMAND_FLIGHT_MODE_2 = "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state";
    private static void executeCommandWithoutWait(Context context, String option, String command) {
        boolean success = false;
        String su = "su";
        for (int i=0; i < 3; i++) {
            // "su" command executed successfully.
            if (success) {
                // Stop executing alternative su commands below.
                break;
            }
            if (i == 1) {
                su = "/system/xbin/su";
            } else if (i == 2) {
                su = "/system/bin/su";
            }
            // execute command
            try
            {
                Runtime.getRuntime().exec(new String[]{su, option, command});
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void setFlightMode(Context context, Boolean _enable)
    {
        int enable = (_enable ? 1:0);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
        {
            // API 17 onwards.
            if (true) //assume we have root
            {
                //int enabled = isFlightModeEnabled(context) ? 0 : 1;
                // Set Airplane / Flight mode using su commands.
                String command = COMMAND_FLIGHT_MODE_1 + " " + enable;
                executeCommandWithoutWait(context, "-c", command);
                command = COMMAND_FLIGHT_MODE_2 + " " + enable;
                executeCommandWithoutWait(context, "-c", command);
            }
            else
            {
                //do nothing
            }
        }
        else
        {
            // API 16 and earlier.
            boolean enabled = isFlightModeEnabled(context);
            Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enabled ? 0 : 1);
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", !enabled);
            context.sendBroadcast(intent);
        }
    }

    private static Integer PIN_LOGIN_NO_DRIVER;
    public static String getActingDriverNumber(Context _c)
    {
        if(PIN_LOGIN_NO_DRIVER == null)
        {
            Integer i = new Random().nextInt(99999);
            PIN_LOGIN_NO_DRIVER = 0 - i;
        }

        String actingNumber =  getString(_c, STATUSES.ACTING_DRIVER_NO);
        if(actingNumber.equals(STATUSES.ACTING_DRIVER_NO.getDefaultValue()))
        {
            return PIN_LOGIN_NO_DRIVER.toString();
        }
        else
        {
            return actingNumber;
        }
    }

    public static void setActingDriverNo(Context _c)
    {
        putString(_c, STATUSES.ACTING_DRIVER_NO, SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(_c));
    }

	public static void Reset(Context _c)
	{
		STATUSMANAGER.STATUSES.CURRENT_APP_STATE.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.CARS_AVAILIABLE.reset(_c, _RESET_TYPE.STRING);
		// do not reset STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS
		STATUSMANAGER.STATUSES.CURRENT_ACTIVITY.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.CURRENT_BREAK_START.reset(_c, _RESET_TYPE.LONG);
		STATUSMANAGER.STATUSES.CURRENT_JOB.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.CURRENT_LAT.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.CURRENT_LON.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.CURRENT_PLOT.reset(_c, _RESET_TYPE.STRING);
        STATUSES.CURRENT_PLOT_IS_RANK.reset(_c, _RESET_TYPE.BOOLEAN);
		STATUSMANAGER.STATUSES.CURRENT_SPEED.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION.reset(_c, _RESET_TYPE.BOOLEAN);
		STATUSMANAGER.STATUSES.HAS_VALID_GPS.reset(_c, _RESET_TYPE.BOOLEAN);
		STATUSMANAGER.STATUSES.JOB_STATE.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.NO_NO_SHOW.reset(_c, _RESET_TYPE.BOOLEAN);
		STATUSMANAGER.STATUSES.PENDING_DRIVER_MESSAGES.reset(_c, _RESET_TYPE.STRING);

		STATUSES.TRAP_INDIVIDUAL.reset(_c, _RESET_TYPE.STRING);
		STATUSES.TRAP_OVERALL.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.STATUS_BAR_TEXT.reset(_c, _RESET_TYPE.STRING);

		STATUSMANAGER.STATUSES.TOTAL_BREAK_USED_SECONDS.reset(_c, _RESET_TYPE.INTEGER);
		STATUSMANAGER.STATUSES.WORK_AVAILIABLE.reset(_c, _RESET_TYPE.STRING);
		STATUSMANAGER.STATUSES.IS_PANIC.reset(_c,_RESET_TYPE.BOOLEAN);
		STATUSMANAGER.STATUSES.STORED_PASSWORD.reset(_c,  _RESET_TYPE.STRING);
        STATUSMANAGER.STATUSES.HAS_BEEN_LOGGED_ON.reset(_c, _RESET_TYPE.BOOLEAN);
        STATUSES.REQUEST_BREAK_ON_CLEAR.reset(_c);

        STATUSES.ON_RANK.reset(_c, _RESET_TYPE.BOOLEAN);
        STATUSES.HIDE_MENU_BUTTON.reset(_c);

        STATUSMANAGER.DriverMessages.reset(_c);


        resetTimers(_c);
	}

    public static void resetTimers(Context _c)
    {
        STATUSES.COUNTER_BREAK.reset(_c);
        STATUSES.COUNTER_JOB_OFFER.reset(_c);
        STATUSES.COUNTER_WAITING_TIME.reset(_c);
    }
	
}
