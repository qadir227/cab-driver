package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cabdespatch.driverapp.beta.activities2017.ComposeMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SETTINGSMANAGER 
{
    private static plotList sPlotsAll = null;
	private static plotList sPlotsNormal = null;
    private static plotList sPlotsRank = null;
	private static boolean loadingPlots = false;

	public static boolean RESET_PENDING = false;

	//increase this every time you update the data warning message
	//this will force the dialogue to be shown again at startup...
	public static final Integer CURRENT_DATA_WARNING_VERSION = 2;
	
	public static class LOCK_DOWN_MODES
	{
		public static String NONE = "NONE";
		public static String STANDARD = "STANDARD";
		public static String KIT_KAT = "KIT_KAT";
	}


	public static class RunModes
	{
		public static final int SHORT = 3;
		public static final int NORMAL = 4;
		public static final int LONG = 5;
	}

    public static class CreditCardPayment
    {
        public static Boolean isEnabled(Context _c)
        {
			//return true;
			if(Globals.isDebug(_c))
			{
				return true;
			}
			else
			{
				return SETTINGS.CREDIT_CARD_PAYMENTS_AVAILABLE.parseBoolean(_c);
			}
        }

        public static Double getSurchargePercentage(Context _c)
        {
            return SETTINGS.CREDIT_CARD_SURCHARGE_PERCENTAGE.parseDouble(_c);
        }

        public static Double getSurchargeFixed(Context _c)
        {
            return SETTINGS.CREDIT_CARD_SURCHARGE_FLAT.parseDouble(_c);
        }

        public static Boolean priceAmendAvailable(Context _c)
        {
            return SETTINGS.CREDIT_CARD_SHOW_AMMEND_PRICE.parseBoolean( _c);
        }

    }


	public static class TextTools
	{
		
		public static class SIZE
		{
			public static final Settable DRIVER_MESSAGE = new Settable("FONT_SIZE_DRIVER_MESSAGE", "34");
			public static final Settable JOB_SCREEN_HEADING = new Settable("FONT_SIZE_JOB_SCREEN_HEADING", "24");
			public static final Settable JOB_SCREEN_DETAIL = new Settable("FONT_SIZE_JOB_SCREEN_DETAIL", "20");
			public static final Settable MENU_ITEM = new Settable("FONT_SIZE_MENU_ITEM", "24");
			public static final Settable SETTING_HEADER = new Settable("FONT_SIZE_SETTINGS_HEADER", "20");
		}
		
		public static int getTextSizeDIP(Context _c, Settable _s)
		{												
			return Integer.valueOf(_s.getValue(_c));			
		}
	}



    public static class METER_TYPE
    {
        public static final String NONE = "0";
        public static final String FAIR_METER = "f";
        public static final String LOCAL_UNFAIR_METER = "t";
        public static final String UNFAIR_METER = "u";
    }
	    
	
	public static class SETTINGS
	{
		/*
		???
        public static final Settable FIXED_LOC_1_LAT = new Settable("lat_fixed_1", "51.328182");
        public static final Settable FIXED_LOC_1_LON = new Settable("lon_fixed_1", "-0.199252");
        public static final Settable FIXED_LOC_2_LAT = new Settable("lat_fixed_2", "51.328182");
        public static final Settable FIXED_LOC_2_LON = new Settable("lon_fixed_2", "-0.199252");
        */

		/* Thai
        public static final Settable FIXED_LOC_1_LAT = new Settable("lat_fixed_1", "12.5732");
        public static final Settable FIXED_LOC_1_LON = new Settable("lon_fixed_1", "99.9532");
        public static final Settable FIXED_LOC_2_LAT = new Settable("lat_fixed_2", "12.5795");
        public static final Settable FIXED_LOC_2_LON = new Settable("lon_fixed_2", "99.9527");
        */

		/*
		Thanet
        public static final Settable FIXED_LOC_1_LAT = new Settable("lat_fixed_1", "51.3896");
        public static final Settable FIXED_LOC_1_LON = new Settable("lon_fixed_1", "1.3907");
        public static final Settable FIXED_LOC_2_LAT = new Settable("lat_fixed_2", "51.36091");
        public static final Settable FIXED_LOC_2_LON = new Settable("lon_fixed_2", "1.4339");
		*/

		public static final Settable YAPI_USE_DEBUG_HOST = new Settable("yapi_debug_host", false);
		public static final Settable FIXED_LOC_1_LAT = new Settable("lat_fixed_1", "12.5732");
		public static final Settable FIXED_LOC_1_LON = new Settable("lon_fixed_1", "99.9532");
		public static final Settable FIXED_LOC_2_LAT = new Settable("lat_fixed_2", "12.5795");
		public static final Settable FIXED_LOC_2_LON = new Settable("lon_fixed_2", "99.9527");

        public static final Settable LOCATION_REPORT_SOURCE = new Settable("loc_report_src", "0"); // 0 = device
                                                                                                   // 1 = fixed loc 1
                                                                                                   // 2 = fixed loc 2

		public static final  Settable COMPANY_ID                         = new Settable("cmpID", "null");
		public static final  Settable COMPANY_NAME                       = new Settable("cnm", "Taxi Despatch");
		public static final  Settable DRIVER_CALL_SIGN                   = new Settable("DRV", "-1");
		public static final  Settable CIRCUIT_PIN                   = new Settable("DPCirc", Settable.NOT_SET);
		private static final Settable LOCK_DOWN                          = new Settable("ld", "false");
		private static final Settable KIT_KAT_LOCK_DOWN                  = new Settable("kkld", "false");
		private static final Settable ENABLE_NAVIGATION                  = new Settable("havnav", "true");
		public static final  Settable IP_ADDRESS                         = new Settable("svr", "null");
		public static final  Settable PORT_NO                            = new Settable("p", "-1");
        public static final  Settable SignalRHost                        = new Settable("sigrh", Settable.NOT_SET);
        public static final  Settable PREFER_SIGNAL_R                    = new Settable("pSigR", "false");
		public static final  Settable REQUIRE_MOBILE_DATA                = new Settable("reqmobdat", "true");
		public static final  Settable REQUIRE_GPS                        = new Settable("reqgpps", "true");
		public static final  Settable ENABLE_GPS                         = new Settable("gpse", "true"); //for gebug purposed only...
		public static final  Settable STRICT_WITH_GPS                    = new Settable("strwgps", "true");
		public static final  Settable STRICT_WITHOUT_GPS                 = new Settable("strwogps", "true");
		public static final  Settable FLIGHT_MODE_HACK                   = new Settable("fligtmodehack", "false");
		public static final  Settable USE_ALTERNATIVE_LOCATION_PROVIDERS = new Settable("usealtloc", "true");
		public static final  Settable ENFORCE_MINIMUM_MOVEMENT_CRITERIA  = new Settable("minmovcrit", "true");
		public static final  Settable SPEED_DRIVER_MESSAGE               = new Settable("speeddrivermessage", "30");
		public static final  Settable USE_ANTICHEAT                      = new Settable("antiC", "false");
		public static final  Settable USE_FLAGDOWN                       = new Settable("flg", "false");
		public static final  Settable PLOTS                              = new Settable("sPlots", "null");
		public static final  Settable TOTAL_BREAK_MINUTES_AVAILABLE      = new Settable("brkm", "99");
		public static final  Settable USE_VOICE_REQUEST                  = new Settable("uvr", "true");
		public static final  Settable OFFICE_NUMBER                      = new Settable("offnum", "01222222222");
		public static final  Settable DRIVER_MESSAGE_PRESETS             = new Settable("drivermsgpresets", "");
		public static final  Settable NO_SHOW_REASONS                    = new Settable("noshowmsgpresets", "");
		public static final  Settable RUN_MODE                           = new Settable("rund", "4");
		//public static final Settable HAS_ADMIN_PRIVILAGES = new Settable("hasadminpower", "true");
		public static final  Settable USE_PIN_LOGIN                      = new Settable("upl", "false");
		//public static final  Settable USE_FIXED_LOCATION                 = new Settable("fixedlocation", "false");
		public static final  Settable IS_DEMO_MODE                       = new Settable("demomode", "false");
		private static final Settable FULL_SCREEN                        = new Settable("fullscreen", "true");
		public static final  Settable PING_TIME_SECONDS                  = new Settable("pts4fun", "15");
		public static final  Settable DATA_MODE                          = new Settable("dmod", CabDespatchNetworkOldSty._CONNECTION_TYPE.ANDROID_DEFAULT);
        //public static final  Settable TEMP_FORCE_DATA_CONNLESS           = new Settable("fconless", "false");
		public static final  Settable REQUEST_PRICE_FROM_DRIVER_CASH     = new Settable("prfdc", "false");
		public static final  Settable REQUEST_PRICE_FROM_DRIVER_ACCOUNT  = new Settable("prfda", "false");
		public static final  Settable SHOW_FUTURE_JOBS                   = new Settable("sfj", "false");
		public static final  Settable THANKYOU_MODE                      = new Settable("tqm", "false");
		public static final  Settable NAVIGATION_LOCATION                = new Settable("navl", Settable.NOT_SET);
		//public static final  Settable DATA_SERVICE_TIMEOUT_SECONDS       = new Settable("dservto", "120");
		public static final  Settable JOB_TOTALS                         = new Settable("jobtots", Settable.NOT_SET);
		public static final  Settable JOB_TOTALS_BASE_DATE               = new Settable("jobtotsbase", Settable.NOT_SET);
		public static final  Settable PROMPT_POB_NUDGE                   = new Settable("prpt_pob", "false");
		public static final  Settable PROMPT_HAVE_YOU_MOVED              = new Settable("prpt_haveit", "false");
		public static final  Settable SAFTEY_PANEL_SPEED_THRESH          = new Settable("safe_thresh", "0");
		public static final  Settable DATA_WARNING_ACCEPT_VERSION        = new Settable("data_warn_level", "0");
		public static final  Settable JOB_SCREEN_FIRST_SLIDE             = new Settable("job_screen_first_slide", Settable.NOT_SET);
		public static final  Settable SPEAK_PRICES = new Settable("spkp", "false");
		public static final Settable PLOT_ANNOUNCEMENTS_ENABLED = new Settable("pltann", "true");
		public static final Settable DEBUG_LIST = new Settable("lstDebug", "");
        //public static final Settable METER_ENABLED = new Settable("mtr_act", "false");
		public static final Settable DEFAULT_MESSAGE_TIMEOUT = new Settable("1219-dmto", 30);


        public static final Settable CREDIT_CARD_PAYMENTS_AVAILABLE = new Settable("ccok", "false");
        public static final Settable CREDIT_CARD_SHOW_AMMEND_PRICE = new Settable("ccpaok", "false");
        public static final Settable CREDIT_CARD_SURCHARGE_PERCENTAGE = new Settable("ccscpc", "0");
        public static final Settable CREDIT_CARD_SURCHARGE_FLAT = new Settable("ccscfl", "0");

        public static final Settable ALLOW_DRIVER_TO_CALL_CUSTOMER = new Settable("spnwj", "false");
        //public static final Settable ENABLE_TAXI_METER = new Settable("enable_taxim", "false");
        public static final Settable LONG_PRESS_PANIC = new Settable("lpp", "false");
        public static final Settable SMALL_UI = new Settable("smallui", "false");
        public static final Settable ERROR_MESSAGE = new Settable("uierror", Settable.NOT_SET);
        public static final Settable JOB_DRIVER_NOTES_PRESET = new Settable("xdnps", "");
        public static final Settable JOB_DRIVER_NOTES_MINIMUM_SIZE = new Settable("xdnms", "-1"); //-1: don't prompt,
                                                                                                // 0: allow blank message
                                                                                                  // x: minimum x charecters
        public static final Settable METER_TYPE = new Settable("xdmfu", SETTINGSMANAGER.METER_TYPE.NONE);
        public static final Settable NO_FLAGDOWN_WIHOUT_GPS = new Settable("gpsflag", "false");
        public static final Settable USE_PANIC = new Settable("use_pax", "true");

        public static final Settable GPS_BEST_ACCURACY_THRESH = new Settable("acctrsh", 70);
        public static final Settable GPS_SLACK_ACCURACY_MULTIPLIER = new Settable("gps_slack_multi", 1.5);

        public static final Settable FIRE_DATA = new Settable("xdata_fire", "false");

        public static final Settable JOB_OFFER_DESTINATION_PLOT = new Settable("jo.dest", false);
        public static final Settable JOB_OFFER_FARE = new Settable("jo.fare", false);
	}


    public static final String getTaxiMeterPackageName()
    {
        return "com.planetcoops.android.taximeter";
    }

	public static void reset(Context _c, Boolean _devMode)
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_c);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();

		SETTINGSMANAGER.SETTINGS.DATA_WARNING_ACCEPT_VERSION.putValue(_c, SETTINGSMANAGER.CURRENT_DATA_WARNING_VERSION);
	}



	public static plotList getPlotsNormal(Context _c)
	{

		if (SETTINGSMANAGER.sPlotsNormal == null)
		{
			SETTINGSMANAGER.loadPlots(_c, false);
		}

		while ((SETTINGSMANAGER.loadingPlots))
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return SETTINGSMANAGER.sPlotsNormal;

	}

    public static plotList getPlotsAll(Context _c)
    {

        if (SETTINGSMANAGER.sPlotsAll == null)
        {
            SETTINGSMANAGER.loadPlots(_c, false);
        }

        while ((SETTINGSMANAGER.loadingPlots))
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return SETTINGSMANAGER.sPlotsAll;

    }

    public static plotList getPlotsRank(Context _c)
    {
        if (SETTINGSMANAGER.sPlotsRank == null)
        {
            SETTINGSMANAGER.loadPlots(_c, false);
        }

        while ((SETTINGSMANAGER.loadingPlots))
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return SETTINGSMANAGER.sPlotsRank;
    }


	private static String[] getStringArray(Context _c, Settable _setting, Boolean _includeNewMessageItem)
	{
		String msgs = SETTINGSMANAGER.get(_c, _setting);
		String[] presets = msgs.split(String.valueOf(cabdespatchJob.SPLITTER));

		List<String> allMsgs = new ArrayList<String>();

		if(_includeNewMessageItem)
		{
			allMsgs.add(ComposeMessage.NEW_MESSAGE);
		}

		for (String s : presets)
		{
			if (!(s.isEmpty()))
			{
				allMsgs.add(s);
			}
		}

		return (String[]) allMsgs.toArray(new String[allMsgs.size()]);
	}

	private static Boolean addToStringArray(Context _c, Settable _s, String _message)
	{
		String[] presets = SETTINGSMANAGER.getStringArray(_c, _s, true);
		if (Arrays.asList(presets).contains(_message))
		{
			return false;
		}

		String safemessage = _message.replace(cabdespatchJob.SPLITTER, "!".toCharArray()[0]);

		String msgs = _s.getValue(_c);
		msgs += cabdespatchJob.SPLITTER;
		msgs += safemessage;

		return _s.putValue(_c, msgs);
	}

	private static void removeFromStringArray(Context _c, Settable _s, String _message, String _ignoreValue)
	{
		String[] currentMessages = SETTINGSMANAGER.getStringArray(_c, _s, true);
		_s.putValue(_c, "");

		for (String s : currentMessages)
		{
			if (!(s.equals(_message)))
			{
				if (!(s.equals(_ignoreValue)))
				{
					if (!(s.isEmpty()))
					{
						SETTINGSMANAGER.addToStringArray(_c, _s, s);
					}
				}
			}
		}
	}


	public static String[] getDriverMessagePresets(Context _c)
	{
		return getStringArray(_c, SETTINGSMANAGER.SETTINGS.DRIVER_MESSAGE_PRESETS, true);
	}

	public static String[] getNoShowReasonPresets(Context _c)
	{
		return getStringArray(_c, SETTINGS.NO_SHOW_REASONS, true);
	}


	public static Boolean addDriverMessagePreset(Context _c, String _message)
	{
		return addToStringArray(_c, SETTINGS.DRIVER_MESSAGE_PRESETS, _message);
	}

	public static Boolean addNoShowMessagePreset(Context _c, String _message)
	{
		return addToStringArray(_c, SETTINGS.NO_SHOW_REASONS, _message);
	}

	public static void removeDriverMessage(Context _c, String _message)
	{
		removeFromStringArray(_c, SETTINGS.DRIVER_MESSAGE_PRESETS, _message, ComposeMessage.NEW_MESSAGE);
	}

	public static void removeNoShowMessage(Context _c, String _message)
	{
		removeFromStringArray(_c, SETTINGS.NO_SHOW_REASONS, _message, ComposeMessage.NEW_MESSAGE);
	}

    /*
	public static HashMap<String, String> getDriverMEssagesRequiringConfirmation(Context _c)
	{
		String[] data = getStringArray(_c, SETTINGS.UNREAD_CONFIRMATION_RQUIRED_MESSAGES, false);

		HashMap<String, String> items = new HashMap<String, String>();
		for(String s:data)
		{
			String[] parts = s.split("¬");
			items.put(parts[0], parts[1]);
		}

		return items;
	}

	public static void addDriverMessageConfirmationRequired(Context _c, String _messageID, String _message)
	{
		addToStringArray(_c, SETTINGS.UNREAD_CONFIRMATION_RQUIRED_MESSAGES, _messageID + "¬" + _message);
	}

	public static void setDriverMessageAsRead(Context _c, String _message)
	{
		removeFromStringArray(_c, SETTINGS.UNREAD_CONFIRMATION_RQUIRED_MESSAGES, _message, "");
		STATUSMANAGER.addDriverMessage(new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
				STATUSMANAGER.DriverMessage.BOX.INBOX, System.currentTimeMillis(), _message));
	}*/

	public static String[] getDebugList(Context _c)
	{
		return  getStringArray(_c, SETTINGS.DEBUG_LIST, false);
	}

	public static Boolean addToDebugList(Context _c, String _message)
	{
		return addToStringArray(_c, SETTINGS.DEBUG_LIST, _message);
	}

	public static enum LOAD_MESSAGE_PRESET_TYPE
	{
		DRIVER_MESSAGES, NO_SHOW;
	}


	//rememebr to run loadplots after putting zone file
	public static void putZoneFile(Context _c, String _plots)
	{
		SETTINGSMANAGER.put(_c, SETTINGSMANAGER.SETTINGS.PLOTS, _plots);
	}
	
	public static void loadPlots(final Context _c, final Boolean _announce)
	{
		SETTINGSMANAGER.loadingPlots = true;
		
		new Thread()
		{
			@Override
			public void run()
			{
                try
                {
                    //SETTINGSMANAGER.oPlots = plotList.fromString(_c, "<html><body>This should throw an exception...</body></html>");
                    //SETTINGSMANAGER.oPlots = plotList.fromString(_c, SETTINGSMANAGER.get(_c, SETTINGSMANAGER.SETTINGS.PLOTS));
                    SETTINGSMANAGER.sPlotsNormal = plotList.fromString(_c, SETTINGS.PLOTS.getValue(_c), 0);
                    SETTINGSMANAGER.sPlotsRank = plotList.fromString(_c, SETTINGS.PLOTS.getValue(_c), 1);
                    SETTINGSMANAGER.sPlotsAll = plotList.fromString(_c, SETTINGS.PLOTS.getValue(_c), 99);
                }
                catch (Exception ex)
                {
                    SETTINGS.PLOTS.reset(_c);
                    SETTINGS.ERROR_MESSAGE.putValue(_c, _c.getString(R.string.error_message_data_recovery));
                    BROADCASTERS.Quit(_c);
                }


                SETTINGSMANAGER.loadingPlots = false;
				
				if(_announce)
				{
					STATUSMANAGER.setStatusBarText(_c, "New Plots Downloaded");
				}
			}
		}.start();
		
	}
		
	public static String get(Context _c, Settable _s)
	{
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
		return p.getString(_s.getKey(), _s.getDefaultValue());
	}
	
	public static boolean put(Context _c, Settable _s, String _value)
	{
		return  putRaw(_c, _s.getKey(), _value);
	}

    public static boolean putRaw(Context _c, String _key, String _value)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putString(_key, _value);
        return e.commit();
    }
	
	public static boolean putGroup(Context _c, paramGroup _p)
	{
		
		SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
		for(param p:_p.getParamList())
		{
			e.putString(p.getName(), p.getValue());
		}
		return e.commit();
	}
	
	public static boolean putJobTotal(Context _c, String _data, Boolean _day0)
	{
		String jobTots = "";
		
		if(_data.endsWith(":"))
		{
			_data += "0";
		}
		if (_day0)
		{
			SETTINGSMANAGER.SETTINGS.JOB_TOTALS.reset(_c);
		}
		else
		{
			jobTots = SETTINGSMANAGER.SETTINGS.JOB_TOTALS.getValue(_c);
		}
		if(jobTots.equals(""))
		{
			return SETTINGSMANAGER.SETTINGS.JOB_TOTALS.putValue(_c, _data);
		}
		else
		{
			return SETTINGSMANAGER.SETTINGS.JOB_TOTALS.putValue(_c, jobTots + "#" + _data);
		}
	}
	
	public static String[] getJobTotals(Context _c)
	{
		return SETTINGSMANAGER.SETTINGS.JOB_TOTALS.getValue(_c).split(Pattern.quote("#"));
	}
	
	public static paramGroup readSettingsData(String _settingsData)
	{
		
		try
		{
			_settingsData = Globals.rot13(_settingsData);
					
			String[] settings = _settingsData.split("#");
			
			paramGroup finalSettings = new paramGroup();
			
			for (String setting:settings)
			{
				String[] segments = setting.split("=");
				try
				{
				finalSettings.addParam(segments[0], segments[1]);
				}
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
			}
			
			return finalSettings;
		
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public static String getLockDownMode(Context _c)
	{
				
		Boolean lockdownkitkat = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.KIT_KAT_LOCK_DOWN.getValue(_c));
		if(lockdownkitkat)
		{
			return LOCK_DOWN_MODES.KIT_KAT;
		}
		else
		{
			Boolean lockdownstandard = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.LOCK_DOWN.getValue(_c));
			if(lockdownstandard)
			{
				return LOCK_DOWN_MODES.STANDARD;
			}
			else
			{
				return LOCK_DOWN_MODES.NONE;
			}
		}
	}
	
	public static Boolean isFullScreen(Context _c)
	{
		String ldm = SETTINGSMANAGER.getLockDownMode(_c);
		
		if(ldm.equals(LOCK_DOWN_MODES.KIT_KAT))
		{
			//never full screen in kit kat lockdown mode....
			return false;
		}
		else if (ldm.equals(LOCK_DOWN_MODES.STANDARD))
		{
			//ALAWYS full screen in standard lockdown mode...
			return true;
		}
		else
		{
			//user preference if not locked down
			return Boolean.valueOf(SETTINGSMANAGER.SETTINGS.FULL_SCREEN.getValue(_c));
		}
	}
	
	public static Boolean navigationAvailable(Context _c)
	{
		String companyID = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(_c);
		if (companyID.equals(SETTINGSMANAGER.SETTINGS.COMPANY_ID.getDefaultValue()))
		{
			return false;
		}
		else
		{
			return Boolean.valueOf(SETTINGSMANAGER.SETTINGS.ENABLE_NAVIGATION.getValue(_c));
		}
		
	}

	public static String getSignalRHost(Context _c)
    {
    	String retVal = "";
        Boolean overrideSignalR = (!(SETTINGSMANAGER.SETTINGS.PREFER_SIGNAL_R.parseBoolean(_c)));
        DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN, "Override", String.valueOf(overrideSignalR));

        if(overrideSignalR)
        {
            retVal =  Settable.NOT_SET;
        }
        else
        {
            retVal = SETTINGSMANAGER.SETTINGS.SignalRHost.getValue(_c);
        }

        DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN, "SignalR_Host", retVal);
        return retVal;
    }

    public static Boolean logDebug() { return true; }

	

	
}
