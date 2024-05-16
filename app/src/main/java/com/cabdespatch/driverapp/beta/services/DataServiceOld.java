package com.cabdespatch.driverapp.beta.services;

import android.app.Service;

import com.cabdespatch.driverapp.beta.dialogs.Dialog_Update;


public abstract class DataServiceOld extends Service implements Dialog_Update.OnUpdatePackageDownloadListener
{

	/*
	public static long LAST_WORK_RUN;
	private static DataServiceOld MY_STATIC_REFERENCE;

	public static boolean DO_LOG_HEAP = false;
	public static int DO_LOG_COUNT = 10;

    private static int startCommandCount = 0;

    private static Boolean screen_is_on;
    private static Long job_offer_time;
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


	public static final String _MESSAGETYPE = "MESSAGE_TYPE"; 
	public static final String _MESSAGEDATA = "MESSAGE_DATA";
	public static final String _MESSAGEEXTRA = "MESSAGE_EXTRA";
    public static final String _LOGTAG = "LOG_TAG";
    public static final String _LOGMESSAGE = "LOG_MESSAGE";
	public static final String _CURRENTACTIVITY = "CURRENT_ACTIVITY";

	protected long lastPingTime;
	protected long pingFrequency;
    public static boolean Quit = true;

    private static Boolean setFakeLocation = false;

    public static void pushFakeLocation()
    {
        setFakeLocation = true;
    }



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

	  /*
	  private class DriverMessageDisplayer extends Thread
	  {
		  private Context c;
		  //private String message;
         // private

		  DriverMessageDisplayer(Context _c)
		  {
			  c = _c;
			 // message = _message;
              //we don't track the messages here any more. we just pick the first
              //unconfirmed or unread message hat requires attention
		  }

		  @Override
		  public void run()
		  {
              /*
			  Boolean canSend = false;
			  DEBUGMANAGER.Log(c, "REQUEST", "DRIVER MESSAGE FOUND");
			  while(!(canSend))
			  {
                  Log.e("DriverMessage", "Displaying");
				  canSend = (!(STATUSMANAGER.getCurrentJob(c).getJobStatus().equals(JOB_STATUS.UNDER_OFFER)));
				  if(canSend)
				  {
					  DEBUGMANAGER.Log(c, "SHOW", "SHOWING DRIVER MESSAGE");
					  BROADCASTERS.SwitchActivity(DataServiceOld.this.getBaseContext(),
							  _ACTIVITIES.DRIVER_MESSAGE);
				  }
				  else
				  {
					  try
					  {
						  DEBUGMANAGER.Log(c, "HOLD", "HOLDING DRIVER MESSAGE");
						  Thread.sleep(2000);
					  }
					  catch (InterruptedException ex)
					  {
						  //do nothing
					  }
				  }

			  }

		  }
	  }


	  protected boolean stop;
	  protected cabdespatchGPS gps;
	  protected boolean canSpeak;
	  
	  private class MessageHandler extends BroadcastReceiver
	  {

		  private void handlePlotUpdate(Context _context, Intent _intent)
		  {
			  STATUSMANAGER.setStatusBarText(_context);
			  //Toast.makeText(DataServiceOld.this, "New Plot: " + p.getLongName(), Toast.LENGTH_LONG).show();
			  String a = STATUSMANAGER.getAppState(_context);
			  Boolean force = _intent.getBooleanExtra(DataServiceOld._MESSAGEEXTRA, false);

			  if((a.equals(APP_STATE.LOGGED_ON)||(force)))
			  {
				  String plot = STATUSMANAGER.getCurrentLocation(_context).getPlot().getShortName();
				  DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.Plot(_context, plot));
			  }
		  }

		private void handleUserRequest(Context _context, Intent _intent)
		{
            //Debug.waitForDebugger();

			String messagetype = _intent.getStringExtra(DataServiceOld._MESSAGETYPE);
			String messagedata = _intent.getStringExtra(DataServiceOld._MESSAGEDATA);

			if (messagetype.equals(DataServiceOld._MESSAGETYPES.USER_REQUEST))
			{
				if (messagedata.equals(USERREQUESTS.LOGIN))
				{
					TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

			        String IMEI = telephonyManager.getDeviceId();
			        String phoneNumber = telephonyManager.getLine1Number();
			        String networkName = telephonyManager.getNetworkOperatorName();
			        String simNo = telephonyManager.getSimSerialNumber();
			        String description = Build.MANUFACTURER + " " + Build.MODEL;
                    STATUSMANAGER.STATUSES.ON_RANK.reset(DataServiceOld.this, STATUSMANAGER.Status._RESET_TYPE.BOOLEAN);

					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Login(_context, IMEI, networkName, phoneNumber, description));

					String plot = STATUSMANAGER.getCurrentLocation(_context).getPlot().getShortName();
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Plot(_context, plot));
				}
				else if(messagedata.equals(USERREQUESTS.LOGOUT))
				{
                    String logTag = _intent.getStringExtra(DataServiceOld._LOGTAG);
                    String logMessage = _intent.getStringExtra(DataServiceOld._LOGMESSAGE);
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Logoff(_context, logTag, logMessage));
				}
				else if(messagedata.equals(USERREQUESTS.QUIT))
				{
					//clay
					//DataServiceOld.this.switchFlightMode(true);
                    DataServiceOld.Quit = true;
					stop=true;
				}
                else if(messagedata.equals(USERREQUESTS.METER_REQUEST_FARE_UPDATE))
                {
                    this.sendMessage(DataServiceOld.this.oMessageSys.builder.FareRequest(_context));
                }
				else if(messagedata.equals(USERREQUESTS.START_FLAGDOWN))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.FlagDownStart(_context));
				}
				else if (messagedata.endsWith(USERREQUESTS.STOP_FLAGDOWN))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.FlagDownStop(_context));
				}
				else if(messagedata.equals(USERREQUESTS.ON_RANK))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.OnRank(_context));
				}
				else if(messagedata.equals(USERREQUESTS.OFF_RANK))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.OffRank(_context));
				}
				else if (messagedata.equals(USERREQUESTS.ACCEPT_JOB))
				{
					cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
					j.setJobStatus(cabdespatchJob.JOB_STATUS.ACCEPTING);
					STATUSMANAGER.setCurrentJob(_context, j);
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Accept(_context));
				}
				else if (messagedata.equals(USERREQUESTS.REJECT_JOB))
				{
                    cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
                    j.setJobStatus(cabdespatchJob.JOB_STATUS.REJECTING);
                    STATUSMANAGER.setCurrentJob(_context, j);
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Reject(_context));
				}
				else if (messagedata.equals(USERREQUESTS.SET_STP))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.STP(_context));
				}
				else if (messagedata.equals(USERREQUESTS.SET_POB))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.POB(_context));
				}
				else if (messagedata.equals(USERREQUESTS.SET_STC))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.STC(_context));
				}
				else if (messagedata.equals(USERREQUESTS.SET_CLEAR))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Clear(_context));
				}
				else if (messagedata.equals(USERREQUESTS.PLOT))
				{
					String plotString = _intent.getStringExtra(DataServiceOld._MESSAGEEXTRA);
					plotList plots = SETTINGSMANAGER.getPlotsAll(DataServiceOld.this);

					plot newPlot = plots.getPlotByShortName(plotString);


					pdaLocation oldLoc = STATUSMANAGER.getCurrentLocation(DataServiceOld.this);
					oldLoc.overridePlot(newPlot);

					STATUSMANAGER.setCurrentLocation(DataServiceOld.this, oldLoc);

					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Plot(_context, plotString));
				}
				else if (messagedata.equals(USERREQUESTS.BID))
				{
					String plot = _intent.getStringExtra(DataServiceOld._MESSAGEEXTRA);
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Bid(_context, plot));
				}
				else if (messagedata.equals(USERREQUESTS.BACK))
				{


					String appState = STATUSMANAGER.getAppState(DataServiceOld.this);
                    String activity = "";
					if(appState.equals(STATUSMANAGER.APP_STATE.ON_BREAK))
					{
                        activity = DataServiceOld._ACTIVITIES.BREAK;
					}
					else if(appState.equals(STATUSMANAGER.APP_STATE.WAITING_TIME))
					{
                        activity = DataServiceOld._ACTIVITIES.WAITING_TIME;
					}
					else if(appState.equals(STATUSMANAGER.APP_STATE.ON_JOB))
					{
                        cabdespatchJob j = STATUSMANAGER.getCurrentJob(DataServiceOld.this);
                        if(
                        (j.getJobStatus() == JOB_STATUS.NOT_ON_JOB)
                        || (j.getJobStatus() == JOB_STATUS.REJECTING)
                        || (j.getJobStatus() == JOB_STATUS.ERROR))
                        {
                            activity = _ACTIVITIES.DRIVER_SCREEN;
                        }
                        else
                        {
                            activity = DataServiceOld._ACTIVITIES.JOB_SCREEN;
                        }
					}
					else if(appState.equals(STATUSMANAGER.APP_STATE.LOGGED_ON))
					{
                        activity = DataServiceOld._ACTIVITIES.DRIVER_SCREEN;
					}
					else if(appState.equals(STATUSMANAGER.APP_STATE.LOGGED_OFF))
					{
                        activity =  DataServiceOld._ACTIVITIES.LOGIN_SCREEN;
					}
					else if(appState.equals(STATUSMANAGER.APP_STATE.LAUNCHER))
					{
						//do nothing
						return;
					}
					else
					{
						ErrorActivity.handleError(DataServiceOld.this, new ErrorActivity.ERRORS.UNHANDLED_APP_STATE(appState));
					}

					BROADCASTERS.SwitchActivity(DataServiceOld.this.getBaseContext(),
                            activity,
                            BroadcastHandler.FORCE_ACTIVITY_SWITCH,
                    String.valueOf(true));
				}
				else if (messagedata.equals(USERREQUESTS.BREAK_START))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.BreakStart(DataServiceOld.this));
				}
				else if (messagedata.equals(USERREQUESTS.BREAK_END))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.BreakEnd(DataServiceOld.this));
				}
				else if (messagedata.equals(USERREQUESTS.DATA_MESSAGE))
				{
					String dataMessage = _intent.getStringExtra(DataServiceOld._MESSAGEEXTRA);
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.dataMessage(DataServiceOld.this, dataMessage));
				}
                else if(messagedata.equals(USERREQUESTS.CONFIRM_MESSAGE_READ))
                {
                    STATUSMANAGER.DriverMessage m = STATUSMANAGER.DriverMessage.parse(_intent.getStringExtra(DataServiceOld._MESSAGEEXTRA));
                    this.sendMessage(DataServiceOld.this.oMessageSys.builder.confirmMessageRead(DataServiceOld.this, m));
                }
				else if(messagedata.equals(USERREQUESTS.NO_SHOW))
				{
					String reason = _intent.getStringExtra(DataServiceOld._MESSAGEEXTRA);
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.noShow(DataServiceOld.this));
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.dataMessage(DataServiceOld.this, reason));
				}
				else if(messagedata.equals(USERREQUESTS.PANIC))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Panic(DataServiceOld.this));
				}
				else if(messagedata.equals(USERREQUESTS.VOICE_REQUEST))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.voiceRequest(DataServiceOld.this));
				}
				else if(messagedata.equals(USERREQUESTS.WAITING_TIME_START))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.WaitingTimeStart(DataServiceOld.this));

                    //this is handled by BroadcastHandler... why did we feel the need to put it here an
                    //break things??? hmmm...
					//STATUSMANAGER.putString(DataServiceOld.this, STATUSMANAGER.STATUSES.CURRENT_ACTIVITY, DataServiceOld._ACTIVITIES.WAITING_TIME);
					STATUSMANAGER.setAppState(DataServiceOld.this, STATUSMANAGER.APP_STATE.WAITING_TIME);
					STATUSMANAGER.putLong(DataServiceOld.this, STATUSMANAGER.STATUSES.CURRENT_WAITING_TIME_START, System.currentTimeMillis());

				}
				else if(messagedata.equals(USERREQUESTS.WAITING_TIME_END))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.WaitingTimeEnd(DataServiceOld.this, false));
				}
                else if(messagedata.equals(USERREQUESTS.WAITING_TIME_AUTO_END))
                {
                    this.sendMessage(DataServiceOld.this.oMessageSys.builder.WaitingTimeEnd(DataServiceOld.this, true));
                }
				else if(messagedata.equals(USERREQUESTS.ON_ROUTE_STOP))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.OnRouteStop(DataServiceOld.this));
				}
				else if(messagedata.equals(USERREQUESTS.RETURN_JOB))
				{
                    cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
                    j.setJobStatus(cabdespatchJob.JOB_STATUS.REJECTING);
                    STATUSMANAGER.setCurrentJob(_context, j);
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.RejectAfterAccept(DataServiceOld.this));
				}
                else if(messagedata.equals(USERREQUESTS.JOB_TIMEOUT))
                {
                    cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
                    j.setJobStatus(cabdespatchJob.JOB_STATUS.REJECTING);
                    STATUSMANAGER.setCurrentJob(_context, j);
                    this.sendMessage(DataServiceOld.this.oMessageSys.builder.JobOfferTimeout(DataServiceOld.this));
                }
				/*else if(messagedata.equals(USERREQUESTS.SPECIAL_LOGOUT))
				{
					this.sendMessage(DataServiceOld.this.oMessageSys.builder.Logoff(DataServiceOld.this, "", ""));
					DataServiceOld.this.logOffPDA();
				}
				else if(messagedata.equals(USERREQUESTS.PRICE_UPDATE))
				{
					String price = _intent.getStringExtra(DataServiceOld._MESSAGEEXTRA);
					DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.PriceUpdate(DataServiceOld.this, price));
				}
				else if(messagedata.equals(USERREQUESTS.FUTURE_JOBS))
				{
					DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.FutureJobs(DataServiceOld.this));
				}
				else if(messagedata.equals(USERREQUESTS.JOB_TOTALS))
				{
					DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.JobTotals(DataServiceOld.this));
				}
                else if(messagedata.equals(USERREQUESTS.CIRCUIT_FEES))
                {
                    DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.CircuitFees(DataServiceOld.this));
                }
                else if(messagedata.equals(USERREQUESTS.POD))
                {
                    String data = _intent.getStringExtra(DataServiceOld._MESSAGEEXTRA);
                    DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.POD(DataServiceOld.this, data));
                }
                else if(messagedata.equals(USERREQUESTS.HISTORY_LOG))
                {
                    String data = _intent.getStringExtra(DataServiceOld._MESSAGEEXTRA);
                    DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.logHistoryText(DataServiceOld.this, data));
                }
				else
				{
					ErrorActivity.handleError(DataServiceOld.this, new ErrorActivity.ERRORS.UNHANDLED_USER_REQUEST(messagedata));
				}

			}


		}

		private void sendMessage(priorityString _message)
		{
			DataServiceOld.this.oNetwork.sendMessage(_message);
		}




		@Override
		public void onReceive(Context _context, Intent _intent)
		{
			String action = _intent.getAction();
            //Log.e("BROADCAST", action);
            //DEBUGMANAGER.Log(_context, "BROADCAST", action);

			if(action.equals(BROADCASTERS.PLOT_UPDATED))
			{
				this.handlePlotUpdate(_context, _intent);
			}
			else if(action.equals(BROADCASTERS.USER_REQUEST))
			{
				this.handleUserRequest(_context, _intent);
			}
			else if(action.equals(Intent.ACTION_BATTERY_CHANGED))
			{
				STATUSMANAGER.BATTERY_LEVEL = _intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int status = _intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;

                STATUSMANAGER.BATTERY_CHARGING = isCharging;

            }
			else if(action.equals(Intent.ACTION_SCREEN_ON))
            {
                Vibrator v = (Vibrator) DataServiceOld.this.getSystemService(Context.VIBRATOR_SERVICE);
                v.cancel();

                screen_is_on = true;
            }
            else if(action.equals(Intent.ACTION_SCREEN_OFF))
            {
                screen_is_on = false;
            }
			else
			{
				ErrorActivity.handleError(DataServiceOld.this, new ErrorActivity.ERRORS.UNHANDLED_BROADCAST_ACTION(action));
			}
		}

	  }
	  
	  
	  public static boolean IS_ACTIVE = false;

    /*
    protected void turnOnScreen()
    {
        // turn on screen
        PowerManager p = (PowerManager) getSystemService(POWER_SERVICE);

        PowerManager.WakeLock mWakeLock = p.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
    }
	  
	  private MessageHandler oMessageHandler;
	  Timer oTimer;
	  CabDespatchNetwork oNetwork;
	  String oDriverNo;
	  String oCompanyId;
	  cabdespatchMessageSys oMessageSys;
	  
	  public class plotGrabber extends Thread
	  {
		  @Override
		  public void run()
		  {
			  String companyID = SETTINGSMANAGER.get(DataServiceOld.this, SETTINGSMANAGER.SETTINGS.COMPANY_ID);
			  String driverNo = SETTINGSMANAGER.get(DataServiceOld.this, SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN);
				
			  ZoneFileResult r = Globals.getZoneFile(DataServiceOld.this, companyID, driverNo);
				
				if(r.wasSuccessful())
				{
					SETTINGSMANAGER.putZoneFile(DataServiceOld.this, r.getZoneFile());
					SETTINGSMANAGER.loadPlots(DataServiceOld.this, true);
				}
				else
				{
					ErrorActivity.handleError(DataServiceOld.this, new ErrorActivity.ERRORS.ZONE_FILE_ERROR(r.getException()));
				}
		  }
	  }

	  public static void checkOutstandingDriverMessages(Context _c)
	  {
		  Integer messagecount = STATUSMANAGER.getUnreadDriverMessages(_c).size();

		  if(messagecount > 0)
		  {
			  BROADCASTERS.SwitchActivity(_c, _ACTIVITIES.DRIVER_MESSAGE);
		  }

	  }
	  
	  public void sendPing()
	  {
			this.lastPingTime = SystemClock.uptimeMillis();
		  	//CLAY
			this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.Ping(DataServiceOld.this.getBaseContext(), false));	
	  }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		LAST_WORK_RUN = System.currentTimeMillis();
		MY_STATIC_REFERENCE = this;
          //  Debug.waitForDebugger();
		  //this.doCoolLock();
          //DEBUGMANAGER.Log(this.getBaseContext(), "Data Service", "Starting");
        DEBUGMANAGER.Log(this, "DSERV", "START - " + String.valueOf(++DataServiceOld.startCommandCount));

		  if(STATUSMANAGER.getAppState(this).equals(STATUSMANAGER.APP_STATE.LAUNCHER))
		  {
			  this.stopSelf();
              DataServiceOld.IS_STARTING = false;

			  return Service.START_NOT_STICKY;
		  }
		  else
		  {
			  Boolean flightModeHack = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.FLIGHT_MODE_HACK.getValue(this));
              if(flightModeHack)
              {
                  if(STATUSMANAGER.isLoggedIn(this))
                  {
                      if(STATUSMANAGER.isFlightModeEnabled(this))
                      {
                          STATUSMANAGER.setFlightMode(this, false);
                          SOUNDMANAGER.announceFlightModeDisabled(DataServiceOld.this);
                      }
                  }

              }
				 
				//Debug.waitForDebugger();
			  	Globals.registerBugHandler(this);
				
				NOTIFIERS.showAppRunning(this);
				this.stop = false;  
				this.oMessageSys = new cabdespatchMessageSys();
				this.oDriverNo = SETTINGSMANAGER.get(this,SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN);
				this.oCompanyId = SETTINGSMANAGER.get(this,SETTINGSMANAGER.SETTINGS.COMPANY_ID);

				int requestedPingFrequency = Integer.valueOf(SETTINGSMANAGER.SETTINGS.PING_TIME_SECONDS.getValue(this));
				this.pingFrequency = Long.valueOf(requestedPingFrequency) * 1000;
				
				//let's not mess about; let's start straight away! :D
				this.lastPingTime = SystemClock.uptimeMillis() -  this.pingFrequency;

				this.oMessageHandler = new MessageHandler();

              //DEBUGMANAGER.Log("String", BROADCASTERS.USER_REQUEST);
              //DEBUGMANAGER.Log("String", BROADCASTERS.PLOT_UPDATED);

                DataServiceOld.IS_ACTIVE = true;
                this.registerReceiver(this.oMessageHandler, new IntentFilter(BROADCASTERS.USER_REQUEST));
                this.registerReceiver(this.oMessageHandler, new IntentFilter(BROADCASTERS.PLOT_UPDATED));
                this.registerReceiver(this.oMessageHandler, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                this.registerReceiver(this.oMessageHandler, new IntentFilter(Intent.ACTION_SCREEN_OFF));
                this.registerReceiver(this.oMessageHandler, new IntentFilter(Intent.ACTION_SCREEN_ON));

                //clay
                //this.oDriverNo = "199";
                //this.oCompanyId = "tht100";



                Boolean usePinLogin = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_PIN_LOGIN.getValue(this));

              if(!(STATUSMANAGER.isLoggedIn(this)))
              {
                  if(usePinLogin)
                  {
                      STATUSMANAGER.clearPinLogin(this);
                  }
                  else
                  {
                      STATUSMANAGER.putString(this, STATUSMANAGER.STATUSES.ACTING_DRIVER_NO, SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(this));
                  }
              }


			  if(isDeveloperEdition()||true)
			  {
				  this.oNetwork = new CabDespatchNetworkSignalR(this, "https://signalrtest.cabdespatch.com/signalr");
			  }
			  else
			  {
				  String signalRHost = SETTINGSMANAGER.getSignalRHost(this);

				  if(signalRHost.equals(Settable.NOT_SET))
				  {
					  this.oNetwork = new CabDespatchNetworkOldSty(this, false);
				  }
				  else
				  {
					  this.oNetwork = new CabDespatchNetworkSignalR(this, signalRHost);
				  }
			  }


				//this.oNetwork = new CabDespatchNetworkOldSty(ipAddress, porNo, CabDespatchNetworkOldSty._CONNECTION_TYPE.CLASSIC, false);
				//

				this.oNetwork.start();
				
				oTimer = new Timer();	    	    
			    this.oTimer.schedule(new RunTimer(), 5000);
			    

			    
			    Boolean useGPS = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.ENABLE_GPS.getValue(this));
			    //Boolean fixedLocation = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_FIXED_LOCATION.getValue(this));
			    
			    
			    try
			    {
					this.gps = new cabdespatchGPS(this, useGPS);
			    }
			    catch (Exception ex)
			    {
			    	//CLAY
			    	Log.e("GPS", "DEVICE DOES NOT HAVE GPS");
			    }
			    
			    DataServiceOld.IS_STARTING = false;
			    return Service.START_STICKY;

		  }
		  
	  }


	  @Override
	  public IBinder onBind(Intent intent) 
	  {
	  //TODO for communication return IBinder implementation
	    return null;
	  }
	  
	  @Override
	  public void onDestroy()
	  {
		  super.onDestroy();

          Log.e("DATA SERVICE", "DESTROY");
		  DataServiceOld.IS_STARTING = false;
          DataServiceOld.startCommandCount = 0;
          NOTIFIERS.hideAppRunning(this);

		  if(DataServiceOld.IS_ACTIVE)
		  {
			  DataServiceOld.IS_ACTIVE = false;  
		  }
		  else
		  {
			  //we never actually did anything on start command - so
			  // do nothing now
		  }
		  this.cleanUp();
		  
	  }


    public static boolean IS_STARTING = false;
    public static void StartDataServiceOld(Context _c)
    {

        if(DataServiceOld.IS_STARTING)
        {
            //do nothing
        }
        else
        {
            DataServiceOld.IS_STARTING = true;
            Intent i = new Intent(_c, DataServiceOld.class);
            _c.startService(i);
        }

    }

	  private class RunTimer extends TimerTask
	  {
		  
		private void setJobStatus(cabdespatchJob.JOB_STATUS _status)
		{
			if(!(_status==null))
			{
				cabdespatchJob j = STATUSMANAGER.getCurrentJob(DataServiceOld.this);
				j.setJobStatus(_status);
				STATUSMANAGER.setCurrentJob(DataServiceOld.this, j);
			}
			
			Intent i = new Intent(BROADCASTERS.JOB_STATUS_UPDATE);
			DataServiceOld.this.sendBroadcast(i);
		}
		

          private void runWork()
          {
          	DataServiceOld.LAST_WORK_RUN = System.currentTimeMillis();
              //Log.e("Data Service", "Run");
              if(DataServiceOld.setFakeLocation)
              {
                  DataServiceOld.setFakeLocation = false;
                  gps.forceFixedLocationChange();
                  BROADCASTERS.PlotUpdated(DataServiceOld.this, true);
              }

              Long lastmessage = DataServiceOld.this.oNetwork.getTimeOfLastAdditionOrAcknowledgement();
              if(lastmessage== CabDespatchNetworkOldSty.NO_DATA_RECEIVED)
              {
					int x=1;
					x +=1;
              }
              else
              {

                  int DataServiceOldtimeoutseconds = DataServiceOld.this.oNetwork.getTimeOutSeconds();

                  Long killtimemillis = (long) (DataServiceOldtimeoutseconds * 1000);
                  Long killservicethreashold  = lastmessage + killtimemillis;

                  if(System.currentTimeMillis() > killservicethreashold)
                  {
                      DataServiceOld.this.handleNoConnection();

                      Integer kilsCount = STATUSMANAGER.getInt(DataServiceOld.this, STATUSMANAGER.STATUSES.DATA_SERVICE_KILL_COUNT);
                      kilsCount +=1;
                      STATUSMANAGER.putInt(DataServiceOld.this, STATUSMANAGER.STATUSES.DATA_SERVICE_KILL_COUNT, kilsCount);
                      SOUNDMANAGER.announceLostSignal(DataServiceOld.this);

                      pdaLocation p = STATUSMANAGER.getCurrentLocation(DataServiceOld.this);
                      String dbgMessage = "Signal Lost at " + p.getLatString() + "," + p.getLonString();


                      DataServiceOld.this.stop = true;
                  }
              }



              if (DataServiceOld.this.stop)
              {
                  //NOTIFIERS.hideAppRunning(DataServiceOld.this);
                  SuperKillDataServiceOld();
              }
              else
              {

                  //need to respect ping times!
                  if (SystemClock.uptimeMillis() >= (DataServiceOld.this.lastPingTime + DataServiceOld.this.pingFrequency))
                  {
                      //Log.d(String.valueOf(System.nanoTime()),String.valueOf(DataServiceOld.this.lastPingTime));
                      Log.e("Data Service", "Ping");
                      DataServiceOld.this.sendPing();
                  }

                  if(!(isScreenOn()))
                  {
                      if(STATUSMANAGER.getCurrentJob(DataServiceOld.this).getJobStatus()==JOB_STATUS.UNDER_OFFER)
                      {
                          Long preJobOfferDuration = (System.currentTimeMillis() - getJobOfferTime());
                          if(preJobOfferDuration >= (30 * 1000))
                          {
                              BROADCASTERS.RejectJob(DataServiceOld.this);
                              Vibrator v = (Vibrator) DataServiceOld.this.getSystemService(Context.VIBRATOR_SERVICE);
                              v.cancel();
                          }
                          else
                          {
                              //do nothing. wait for activity to work it's magic
                          }
                      }
                  }

                  sQueue messages = DataServiceOld.this.oNetwork.getMessages();
                  while (messages.size() > 0)
                  {

                      String data = messages.poll();
                      String statusText = "";

                      if(data.equals(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_NOT_READY))
                      {
                          //now handled on a timer basis in runTimer
                          //DataServiceOld.this.handleNoConnection();
                          STATUSMANAGER.putBoolean(DataServiceOld.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION, false);
                          BROADCASTERS.NETWORK_OFF(DataServiceOld.this);

                      }
                      else if(data.equals(CabDespatchNetwork._SPECIALMESSAGES.NETWORK_RECONNECTING))
                      {
                          STATUSMANAGER.putBoolean(DataServiceOld.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION, false);
                          BROADCASTERS.NETWORK_RECONNECTING(DataServiceOld.this);
                      }
                      else if(data.equals(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_READY))
                      {
                          STATUSMANAGER.putBoolean(DataServiceOld.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION, true);

                          Boolean loggedIn = (STATUSMANAGER.getAppState(DataServiceOld.this).equals(APP_STATE.LOGGED_ON));
                          if(!(loggedIn))
                          {
                              STATUSMANAGER.setStatusBarText(DataServiceOld.this.getBaseContext(), "Server located", StatusBar.AFFERMATIVE_MESSAGE_DELAY);
                          }
                          //pass it on in case there are any activities that need to know about this (ie login screen)
                          Intent Message = new Intent(BROADCASTERS.DATA);
                          Message.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
                          Message.putExtra(_MESSAGEDATA, _ACTIONS.NETWORK_ON);

                          DataServiceOld.this.sendBroadcast(Message);
                      }
                      else
                      {
                          final Boolean isLockDown = (!(SETTINGSMANAGER.getLockDownMode(DataServiceOld.this).equals(SETTINGSMANAGER.LOCK_DOWN_MODES.NONE)));

                          uiMessage u = DataServiceOld.this.oMessageSys.handler.handleMessage(DataServiceOld.this.getApplicationContext(),data);
                          switch (u.getMessageType())
                          {
                              case PONG:
                                  //DataServiceOld.this.sendPing();
                                  break;
                              case NULL:
                                  //do nothing
                                  break;
                              case REBOOT_REQUIRED_FOR_LOGIN:
                                  DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.Logoff(DataServiceOld.this, "", ""));
                                  DataServiceOld.this.stop = true;
                                  SETTINGSMANAGER.RESET_PENDING = true;

                                  Handler h = new Handler(DataServiceOld.this.getMainLooper());
                                  h.post(new Runnable()
                                  {

                                      @Override
                                      public void run()
                                      {
                                          String message = "A change has been made to your settings which requires that you reboot your device. You will not be able to login until you reboot.";
                                          new Dialog_MsgBox(DataServiceOld.this, message, _SHOWBUTTONS.OK).show();

                                      }
                                  });
                                  break;
							  case RESTART_FOR_DATASERVICE:
                                  //log off
                                  DataServiceOld.this.oNetwork.sendMessage(DataServiceOld.this.oMessageSys.builder.Logoff(DataServiceOld.this, "", ""));
                                  DataServiceOld.this.stop = true;

                                  Handler hi = new Handler(DataServiceOld.this.getMainLooper());
                                  hi.post(new Runnable()
                                  {

                                      @Override
                                      public void run()
                                      {
                                          String message = "Your data settings have changed. Cab Despatch needs to Restart.";
                                          Dialog_MsgBox d = new Dialog_MsgBox(DataServiceOld.this, message, _SHOWBUTTONS.OK);
                                          d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                                          {

                                              @Override
                                              public void ButtonPressed(_BUTTON _button)
                                              {
                                                  BROADCASTERS.Quit(DataServiceOld.this);
                                              }
                                          });
                                          d.show();
                                      }

                                  });



                                  break;
                              case LOGON:
                                  String appstate = STATUSMANAGER.getAppState(DataServiceOld.this);

                                  STATUSMANAGER.resetTimers(DataServiceOld.this);
                                  if(appstate.equals(APP_STATE.LOGGED_OFF))
                                  {
                                      STATUSMANAGER.putBoolean(DataServiceOld.this, STATUSMANAGER.STATUSES.HAS_BEEN_LOGGED_ON, true);

                                     // CLAY2017 BROADCASTERS.SwitchActivity(DataServiceOld.this.getBaseContext(), _ACTIVITIES.DRIVER_SCREEN);
                                      STATUSMANAGER.setStatusBarText(DataServiceOld.this.getBaseContext(), "Logged in");
                                      STATUSMANAGER.putString(DataServiceOld.this.getBaseContext(), STATUSMANAGER.STATUSES.CURRENT_APP_STATE, APP_STATE.LOGGED_ON);

                                      Intent i = new Intent(DataServiceOld.this, LoggedInHost.class);
                                      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                      DataServiceOld.this.startActivity(i);
                                  }
                                  break;
                              case LOGOFF:
                                  statusText = DataServiceOld.this.getString(R.string.logged_out);
                              case NOTLOGGEDIN:
                                  if (statusText.equals("")) statusText = DataServiceOld.this.getString(R.string.you_have_been_logged_out);

                                  Boolean hasBeenLoggedIn = STATUSMANAGER.getBoolean(DataServiceOld.this, STATUSMANAGER.STATUSES.HAS_BEEN_LOGGED_ON);
                                  if(hasBeenLoggedIn)
                                  {
                                      if(!(STATUSMANAGER.getAppState(DataServiceOld.this).equals(APP_STATE.LOGGED_OFF)))
                                      {
                                          STATUSMANAGER.setStatusBarText(DataServiceOld.this.getBaseContext(), statusText);
                                      }
                                  }
                                  DataServiceOld.this.logOffPDA();
                                  //handled by cabdespatchMessageSys atm
                                  //STATUSMANAGER.setAppState(DataServiceOld.this, APP_STATE.LOGGED_OFF);
                                  break;
                              case JOBWAITING:
                                  BROADCASTERS.SwitchActivity(DataServiceOld.this.getBaseContext(), _ACTIVITIES.JOB_OFFER);
                                  STATUSMANAGER.setAppState(DataServiceOld.this, APP_STATE.ON_JOB); //CLAY2017
                                  if(isScreenOn())
                                  {
                                      if(LoggedInHost.isPaused())
                                      {
                                          ResumerActivity.resume(DataServiceOld.this);
                                      }
                                  }
                                  else
                                  {
                                      SOUNDMANAGER.playJobOfferSound(DataServiceOld.this);
                                      Vibrator v = (Vibrator) DataServiceOld.this.getSystemService(Context.VIBRATOR_SERVICE);
                                      // Vibrate for 500 milliseconds
                                      long[] pattern = {1000,200,500,100,500,1000};
                                      v.vibrate(pattern, 0);
                                      job_offer_time = System.currentTimeMillis();
                                  }
                                  //DataServiceOld.this.turnOnScreen();
                                  break;
                              case JOB_UPDATE:
                                  if(u.getMessageData().equals("Y")) //confirmation required
                                  {
                                      STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE, STATUSMANAGER.DriverMessage.BOX.INBOX, new Date().getTime(), "Job details have been changed", true, true);
                                      STATUSMANAGER.addDriverMessage(DataServiceOld.this, m);
                                  }
                                  BROADCASTERS.JobAmended(DataServiceOld.this);
                                  break;
                              case PRICE_UPDATE:
                                  BROADCASTERS.PriceUpdated(DataServiceOld.this);
                                  break;
                              case SETONROUTE:
                                  STATUSMANAGER.setAppState(DataServiceOld.this, APP_STATE.ON_JOB);

                                  cabdespatchJob j = STATUSMANAGER.getCurrentJob(DataServiceOld.this);
                                  JOBHISTORYMANAGER.addJob(DataServiceOld.this, j);

                                  if(u.getMessageData().equals("flag"))
                                  {
                                      this.setJobStatus(JOB_STATUS.STC);
                                  }
                                  else
                                  {
                                      Boolean autoAccept = u.getMessageData().equals("Y");

                                      //this.setJobStatus(JOB_STATUS.ON_ROUTE);
                                      cabdespatchJob jor = STATUSMANAGER.getCurrentJob(DataServiceOld.this);
                                      jor.setStatusFromPendng();
                                      STATUSMANAGER.setCurrentJob(DataServiceOld.this, jor);

                                      if(autoAccept)
                                      {
                                          SOUNDMANAGER.playAutoJobSound(DataServiceOld.this);
                                      }
                                  }

                                  STATUSMANAGER.setStatusBarText(DataServiceOld.this); //remove trap info

                                  break;
                              case SETSTP:
                                  cabdespatchJob j1 = STATUSMANAGER.getCurrentJob(DataServiceOld.this);

                                  if(j1.getJobStatus()==JOB_STATUS.UNDER_OFFER)
                                  {
                                      j1.setPendingStatus(JOB_STATUS.STP);
                                      STATUSMANAGER.setCurrentJob(DataServiceOld.this, j1);
                                  }
                                  else
                                  {
                                      this.setJobStatus(JOB_STATUS.STP);
                                  }
                                  break;
                              case SETPOB:
                                  cabdespatchJob j2 = STATUSMANAGER.getCurrentJob(DataServiceOld.this);


                                  if(j2.getJobStatus()==JOB_STATUS.UNDER_OFFER)
                                  {
                                      j2.setPendingStatus(JOB_STATUS.POB);
                                      STATUSMANAGER.setCurrentJob(DataServiceOld.this, j2);
                                  }
                                  else
                                  {
                                      this.setJobStatus(JOB_STATUS.POB);
                                  }
                                  break;
                              case SETSTC:
                                  cabdespatchJob j3 = STATUSMANAGER.getCurrentJob(DataServiceOld.this);

                                  if(j3.getJobStatus()==JOB_STATUS.UNDER_OFFER)
                                  {
                                      j3.setPendingStatus(JOB_STATUS.STC);
                                      STATUSMANAGER.setCurrentJob(DataServiceOld.this, j3);
                                  }
                                  else
                                  {
                                      this.setJobStatus(JOB_STATUS.STC);
                                  }

                                  if (u.getMessageData().equals("Y"))
								  {
									  String priceAmend = getString(R.string.price_amend_short);
									  priceAmend = priceAmend.replace("$", j3.getPrice());

									  cdToast.showShort(DataServiceOld.this, priceAmend);

									  if(SETTINGSMANAGER.SETTINGS.SPEAK_PRICES.parseBoolean(DataServiceOld.this))
									  {
									  	SOUNDMANAGER.announcePrice(DataServiceOld.this, j3.getPrice());
									  }
								  }
                                  break;
                              case ANTICHEATRECD:
                                  cabdespatchJob jo = STATUSMANAGER.getCurrentJob(DataServiceOld.this.getBaseContext());
                                  jo.setAntiCheatOn();
                                  STATUSMANAGER.setCurrentJob(DataServiceOld.this, jo);

                                  this.setJobStatus(null); //just send broadcast
                                  break;
                              case SETCLEAR:
                                  STATUSMANAGER.setAppState(DataServiceOld.this, APP_STATE.LOGGED_ON);
                                  STATUSMANAGER.setCurrentJob(DataServiceOld.this, new cabdespatchJob(false));

                                  //CLAY - maybe do an USER_REQUESTS.Back() instead???
                                  BROADCASTERS.SwitchActivity(DataServiceOld.this, _ACTIVITIES.DRIVER_SCREEN);

                                  //we've received a clear ACK, send our plot...
                                  BROADCASTERS.PlotUpdated(DataServiceOld.this, true);
                                  break;
						/*case NEWSETTINGS:

						    New Settings are now sent by Socket Server


							STATUSMANAGER.setStatusBarText(DataServiceOld.this.getBaseContext(), "Downloading new settings");

							String iCompanyId = SETTINGSMANAGER.get(DataServiceOld.this.getBaseContext(), SETTINGSMANAGER.SETTINGS.COMPANY_ID);
							String iDriverNo = SETTINGSMANAGER.get(DataServiceOld.this.getBaseContext(), SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN);

							String url = Globals.CabDespatchDataUrl + "pdasettings/index.aspx" + Globals.WebTools.getAuthQueryString(DataServiceOld.this, iCompanyId, iDriverNo, "0") + Globals.rot13("&method=get");

							String s = "";
							try {
								s = Globals.WebTools.getWebPage(url);
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							if ((s.equals("n") || s.equals("")))
							{
								STATUSMANAGER.setStatusBarText(DataServiceOld.this.getBaseContext(), "Could not download new settings");
							}
							else
							{
								paramGroup sets = SETTINGSMANAGER.readSettingsData(s);
								if(SETTINGSMANAGER.putGroup(DataServiceOld.this.getBaseContext(), sets))
								{
									STATUSMANAGER.setStatusBarText(DataServiceOld.this.getBaseContext(), "New settings saved");

									String iNewVersion = u.getMessageData();
									SETTINGSMANAGER.put(DataServiceOld.this.getBaseContext(), SETTINGSMANAGER.SETTINGS.SETTINGS_VERSION, iNewVersion);


									/*if(STATUSMANAGER.getBoolean(DataServiceOld.this.getBaseContext(), STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION))
									{
										//send this broadcast to re-enable the login button on the main screen
										Intent Message = new Intent(BROADCASTERS.DATA);
										Message.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
										Message.putExtra(_MESSAGEDATA, _ACTIONS.NETWORK_ON);

										DataServiceOld.this.sendBroadcast(Message);
									}
								}
								else
								{
									STATUSMANAGER.setStatusBarText(DataServiceOld.this.getBaseContext(), "Could not save new settings");
								}

							}
							if(!(STATUSMANAGER.isLoggedIn(DataServiceOld.this)))
							{
								if(STATUSMANAGER.getBoolean(DataServiceOld.this.getBaseContext(), STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION))
								{
									BROADCASTERS.Login(DataServiceOld.this);
								}
							}
							break;
                              case CARSJOBSMESSAGE:
                                  if(!(DataServiceOld.this.gps.getCurrentFixTime() == cabdespatchGPS.FIX_NEVER_OBTAINED))
                                  {
                                      DataServiceOld.this.gps.forceAutoPlot(DataServiceOld.this);
                                  }
                                  else
                                  {
                                      //do nothing
                                      //cdToast.showShort(DataServiceOld.this, "Avoided erroneous fix");
                                  }


                                  Intent CarsJobs = new Intent(BROADCASTERS.CARS_WORK_UPDATE);
                                  DataServiceOld.this.sendBroadcast(CarsJobs);
                                  break;
                              case FUTUREJOBSMESSAGE:
                                  Intent FutureJobs = new Intent(BROADCASTERS.FUTURE_JOBS_UPDATE);
                                  DataServiceOld.this.sendBroadcast(FutureJobs);
                                  break;
                              case DRIVERMESSAGE:
                                  //String confRequired = u.getSecondaryData();
								  //new DriverMessageDisplayer(DataServiceOld.this.getBaseContext()).start();
                                  //DataServiceOld.this.turnOnScreen();
                                  if(isScreenOn())
                                  {
                                      if(LoggedInHost.isPaused())
                                      {

										  Integer messagecount = STATUSMANAGER.getUnreadDriverMessages(DataServiceOld.this).size();

										  if(messagecount > 0)
										  {
											  if(CreditCardHandler.isProcessingPayment())
											  {
												  String toastContent = "";

												  if(messagecount>1)
												  {
													  toastContent = DataServiceOld.this.getString(R.string.messages_waiting);
													  toastContent = toastContent.replace("$", String.valueOf(messagecount));
												  }
												  else
												  {
													  toastContent = DataServiceOld.this.getString(R.string.message_waiting);
												  }
												  cdToast.showShort(DataServiceOld.this, toastContent);


											  }
											  else
											  {
												  ResumerActivity.resume(DataServiceOld.this);
											  }
										  }

                                      }
                                  }
                                  else
                                  {
                                      SOUNDMANAGER.playNewMessageSound(DataServiceOld.this);
                                      Vibrator v = (Vibrator) DataServiceOld.this.getSystemService(Context.VIBRATOR_SERVICE);
                                      v.vibrate(2000);
                                  }
                                  break;
                              case PLOTUPDATE:
                                  new plotGrabber().start();
                                  break;
                              case BREAKSTART:
                                  STATUSMANAGER.setAppState(DataServiceOld.this, APP_STATE.ON_BREAK);
                                  STATUSMANAGER.putLong(DataServiceOld.this, STATUSMANAGER.STATUSES.CURRENT_BREAK_START, System.currentTimeMillis());
                                  BROADCASTERS.SwitchActivity(DataServiceOld.this.getBaseContext(), _ACTIVITIES.BREAK);
                                  break;
                              case BREAKEND:
                                  STATUSMANAGER.setAppState(DataServiceOld.this, APP_STATE.LOGGED_ON);
                                  break;
                              case RESET_PDA:
                                  String companyID = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(DataServiceOld.this);
                                  SETTINGSMANAGER.reset(DataServiceOld.this, false);
                                  if((!(u.getMessageData().equals("-1"))))
                                  {
                                      SETTINGSMANAGER.SETTINGS.COMPANY_ID.putValue(DataServiceOld.this, companyID);
                                      SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.putValue(DataServiceOld.this, "*" + u.getMessageData());
                                  }
                                  DataServiceOld.this.stop = true;
                                  BROADCASTERS.Quit(DataServiceOld.this);
                                  break;
                              case WORK_WAITING_AT_DESTINATION:
                                  SOUNDMANAGER.announceWorkWaitingAtDestination(DataServiceOld.this);
                                  break;
                              case TOAST:
                                  cdToast.showLong(DataServiceOld.this, data, "Job Totals");
                                  break;
                              case NEW_JOB_TOTALS:
                                  Intent message = new Intent(BROADCASTERS.JOB_TOTALS_UPDATE);
                                  DataServiceOld.this.sendBroadcast(message);
                                  break;
                              case UPDATE_AVAILABLE:
                                  if(!(SETTINGSMANAGER.RESET_PENDING))
                                  {
                                      Handler hiua = new Handler(DataServiceOld.this.getMainLooper());
                                      hiua.post(new Runnable()
                                      {
                                          @Override
                                          public void run()
                                          {
                                              if(isLockDown)
                                              {
                                                  new Dialog_MsgBox(DataServiceOld.this, "An update is available. Please see the office").show();
                                              }
                                              else
                                              {
                                                  Dialog_MsgBox d = new Dialog_MsgBox(DataServiceOld.this.getBaseContext(), "An update is available. Do you wish to download (saying yes will log you off)", _SHOWBUTTONS.YESNO);
                                                  d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                                                  {
                                                      @Override
                                                      public void ButtonPressed(_BUTTON _button)
                                                      {
                                                          if(_button.equals(_BUTTON.YES))
                                                          {
                                                              BROADCASTERS.Logout(DataServiceOld.this.getBaseContext());
                                                              Globals.CrossFunctions.UpdateApp(DataServiceOld.this.getBaseContext(), DataServiceOld.this);
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
                                  if(!(SETTINGSMANAGER.RESET_PENDING))
                                  {
                                      Handler hiub = new Handler(DataServiceOld.this.getMainLooper());
                                      hiub.post(new Runnable()
                                      {
                                          @Override
                                          public void run()
                                          {
                                              if(isLockDown)
                                              {
                                                  new Dialog_MsgBox(DataServiceOld.this, "An update is required before you can log in. Please see the office").show();
                                              }
                                              else
                                              {
                                                  Dialog_MsgBox d = new Dialog_MsgBox(DataServiceOld.this.getBaseContext(), "An update is required before you can log in. Do you wish to download now?", _SHOWBUTTONS.YESNO);
                                                  d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                                                  {
                                                      @Override
                                                      public void ButtonPressed(_BUTTON _button)
                                                      {
                                                          if(_button.equals(_BUTTON.YES))
                                                          {
                                                              BROADCASTERS.Logout(DataServiceOld.this.getBaseContext());
                                                              Globals.CrossFunctions.UpdateApp(DataServiceOld.this.getBaseContext(), DataServiceOld.this);
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
                                  //cdToast.showLong(DataServiceOld.this, "Advised to fetch data");
                                  DataServiceOld.this.sendPing();
                                  break;
                              case SEND_SMS:
                                  if(STATUSMANAGER.getBoolean(DataServiceOld.this, STATUSMANAGER.STATUSES.ALLOW_LOCAL_SMS))
                                  {

                                      //do additional check here
                                      //just in case somebody manages to remove
                                      //the app as default SMS provider after logging in

                                      Boolean isDefaultSMSProvider = false;
                                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                                      {
                                          isDefaultSMSProvider = Telephony.Sms.getDefaultSmsPackage(DataServiceOld.this).equals(getPackageName());
                                      }

                                      if(isDefaultSMSProvider)
                                      {
                                          SmsManager sm = SmsManager.getDefault();

                                          final String phoneNo = u.getSecondaryData();
                                          final String messageText = u.getMessageData();

                                          String SENT="SMS_SENT";
                                          PendingIntent sentPI=PendingIntent.getBroadcast(DataServiceOld.this, 0, new Intent(SENT), 0);
                                          // smsListener = null;

                                          BroadcastReceiver smsListener=new BroadcastReceiver(){
                                              @Override public void onReceive(Context context, Intent intent)
                                              {
                                                  String extra = "";
                                                  if (getResultCode() == Activity.RESULT_OK)
                                                  {
                                                      extra =  DataServiceOld.this.getString(R.string.TEXTBACK_SENT);

                                                  }
                                                  else
                                                  {
                                                      extra = DataServiceOld.this.getString(R.string.TEXTBACK_FAILED);
                                                  }

                                                  //default android implementations shouldn't add the sent message to the outbox
                                                  //however just in case somebody is using a weird hacky ROM, let's force through
                                                  //some delete commands for extra security
                                                  deleteSMS(context, "content://sms/inbox");
                                                  deleteSMS(context, "content://sms/outbox");
                                                  deleteSMS(context, "content://sms/sent");
                                                  deleteSMS(context, "content://sms/queued");
                                                  deleteSMS(context, "content://sms/failed");
                                                  deleteSMS(context, "content://sms/undelivered");




                                                  STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
                                                          STATUSMANAGER.DriverMessage.BOX.INBOX,
                                                          new Date().getTime(),
                                                          extra,
                                                          false, true);
                                                  STATUSMANAGER.addDriverMessage(DataServiceOld.this, m);
                                                  //new DriverMessageDisplayer(DataServiceOld.this.getBaseContext()).start();

                                                  unregisterReceiver(this);
                                              }
                                          };

                                          IntentFilter filter=new IntentFilter(SENT);
                                          registerReceiver(smsListener,filter);

                                          sm.sendTextMessage(phoneNo, null, messageText, sentPI, null);
                                      }
                                      else
                                      {
                                          STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
                                                  STATUSMANAGER.DriverMessage.BOX.INBOX,
                                                  new Date().getTime(),
                                                  DataServiceOld.this.getString(R.string.TEXTBACK_SECURITY_ERROR),
                                                  false, true);
                                          STATUSMANAGER.addDriverMessage(DataServiceOld.this, m);
                                          //new DriverMessageDisplayer(DataServiceOld.this.getBaseContext()).start();

                                      }


                                  }
                                  else
                                  {
                                      STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
                                              STATUSMANAGER.DriverMessage.BOX.INBOX,
                                              new Date().getTime(),
                                              DataServiceOld.this.getString(R.string.TEXTBACK_DISABLED),
                                              false, true);
                                      STATUSMANAGER.addDriverMessage(DataServiceOld.this, m);
                                      //new DriverMessageDisplayer(DataServiceOld.this.getBaseContext()).start();

                                                                        }
                                  break;
                              default:
                                  //see if any of the other activities are interested in this message
                                  Intent Message = new Intent(BROADCASTERS.DATA);
                                  Message.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
                                  Message.putExtra(_MESSAGEDATA, "misc data recd: " + data);

                                  DataServiceOld.this.sendBroadcast(Message);
                          }
                      }

                  }

                  try
                  {
                      DataServiceOld.this.oTimer.schedule(new RunTimer(), 1000);

                      if(DataServiceOld.DO_LOG_HEAP)
                      {
                          if(DataServiceOld.DO_LOG_COUNT++ > 10)
                          {
                              DataServiceOld.DO_LOG_COUNT = 0;
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
                      DataServiceOld.IS_ACTIVE = false;
                  }

              }
          }

          private void deleteSMS(Context _c, String _boxUrl)
          {

              Log.w("TextBack", _boxUrl);

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

                      Log.w(_boxUrl, pid.toString());
                  }
                  catch (Exception e)
                  {
                      e.printStackTrace();
                  }
              }

              c.close();
              c = null;
          }
		  

		@Override
		public void run() 
		{
			try
            {
                if(DataServiceOld.Quit)
                {
                    stop = true;
                }
                runWork();
            }
            catch (Exception ex)
            {
                ErrorActivity.genericReportableError(ex, "DataServiceOld error in runWork()");
            }

		}
		  
	  }



	private void cleanUp()
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

        try
        {
            this.unregisterReceiver(this.oMessageHandler);
        }
        catch (Exception ex) {}
    }

	protected void handleNoConnection()
	  {
		  STATUSMANAGER.putBoolean(DataServiceOld.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION, false);
			Boolean loggedIn = (STATUSMANAGER.getAppState(DataServiceOld.this).equals(STATUSMANAGER.APP_STATE.LOGGED_ON));						
			if(!(loggedIn))
			{
				STATUSMANAGER.setStatusBarText(DataServiceOld.this.getBaseContext(), "No connection to server");	
			}
			

			Intent Message = new Intent(BROADCASTERS.DATA);
			Message.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
			Message.putExtra(_MESSAGEDATA, _ACTIONS.NETWORK_OFF);
			
			DataServiceOld.this.sendBroadcast(Message);
	  }
	  
	  
	  protected void logOffPDA()
	  {
		  STATUSMANAGER.setCurrentJob(DataServiceOld.this, new cabdespatchJob(false));
			
            String currentState = STATUSMANAGER.getString(DataServiceOld.this.getBaseContext(), STATUSMANAGER.STATUSES.CURRENT_APP_STATE);
            if((currentState.equals(STATUSMANAGER.APP_STATE.LOGGED_OFF)))
            {
                //do nothing
            }
            else
            {
                Boolean usePinLogin = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_PIN_LOGIN.getValue(DataServiceOld.this));
                if(usePinLogin)
                {
                    STATUSMANAGER.clearPinLogin(DataServiceOld.this);
                }


                STATUSMANAGER.putString(this.getBaseContext(), STATUSMANAGER.STATUSES.CURRENT_APP_STATE, STATUSMANAGER.APP_STATE.LOGGED_OFF);
                /*CLAY should this be an activity switch broadcast??
                Intent loginScreen = new Intent(this, ResumerActivity.class);
                loginScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(loginScreen);

                //of course it should, otherwise we're not going to be keeping track of the current activity!

                Intent iLogoff = new Intent(BROADCASTERS.DATA);
                iLogoff.putExtra(_MESSAGETYPE, _MESSAGETYPES.ACTION);
                iLogoff.putExtra(_MESSAGEDATA, DataServiceOld._ACTIONS.LOGOUT);

                DataServiceOld.this.sendBroadcast(iLogoff);
            }

          BROADCASTERS.SwitchActivity(this,  DataServiceOld._ACTIVITIES.LOGIN_SCREEN);
	  }


    @Override
    public void onUpdatePackageDownloadComplete()
    {
        cdToast.showLong(DataServiceOld.this, "DS: DOWNLOAD COMPLETE");
    }

    @Override
    public void onUpdatePackageDownloadFailed(FAIL_REASON _reason)
    {
        cdToast.showLong(DataServiceOld.this, "DS: DOWNLOAD FAILED");
    }

    @Override
    public void onUpdatePackageDownloadCancelled()
    {
        cdToast.showLong(DataServiceOld.this, "DS: DOWNLOAD CANX");
    }

	protected boolean isDeveloperEdition()
	{
		return (getPackageName().contains(".devmode"));
	}

	public static void SuperKillDataServiceOld()
	{
		if(!(MY_STATIC_REFERENCE==null))
		{
			MY_STATIC_REFERENCE.cleanUp();

			DataServiceOld.IS_ACTIVE = false;
			MY_STATIC_REFERENCE.stopSelf();
		}

	}
*/
}
