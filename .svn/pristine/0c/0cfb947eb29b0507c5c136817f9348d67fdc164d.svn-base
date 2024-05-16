package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BuildConfig;
import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER.SETTINGS;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.services.DataService;
import com.cabdespatch.libcabapiandroid.Apis.Activation.Checkin;
import com.cabdespatch.libcabapiandroid.Apis.AnyApiRequest;

import java.io.File;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
 
public class Dialog_Startup extends Dialog
{

	public enum _STARTUPDIALOGRESULT
	{
		PASS,
        STORAGE_NOT_READABLE,
        STORAGE_NOT_WRITABLE,
		COULD_NOT_CREATE_FOLDER,
		COULD_NOT_GET_SETTINGS_FILE,COULD_NOT_WRITE_SETTINGS_FILE,
		COULD_NOT_GET_ZONE_FILE,COULD_NOT_WRITE_ZONE_FILE,
		COULD_NOT_GET_MESSAGES,COULD_NOT_WRITE_MESSAGES,
		COULD_NOT_CREATE_SOUND_FOLDERS,COULD_NOT_GET_SOME_SOUND_FILES,
		COULD_NOT_CREATE_ZONE_SOUNDS_FOLDER,COULD_NOT_GET_ZONE_SOUNDS, COULD_NOT_EXTRACT_ZONE_SOUNDS,
		UPDTAE_AVAILABLE,UPDATE_REQUIRED,
		BILLING_VERIFICATION_REQUIRED,RE_REGISTRATION_REQUIRED,COULD_NOT_VERIFY_DEVICE;
	}
	
	
	public enum _STARTPHASE
	{
		
		GET_SETTINGS(100),GET_PLOTS(200),GET_MESSAGES(300),GET_SOUNDS(400),GET_ZONE_SOUNDS(500),CHECK_IN_AT_CABDESPATCH(1000);
		
		private int value;
		
        private _STARTPHASE(int value) 
        {
                this.value = value;
        }
        
        public Integer getValue()
        {
        	return value;
        }
	}


	private TextView oLblStatus;
	private StartupDialogResultListener oListener;
	private Globals oGlobals;

	public interface StartupDialogResultListener
	{
		public void OnStartupDialogResult(_STARTUPDIALOGRESULT result, String message, Exception ex);
	}

//	public class DialogResult
//	{
//		private _STARTUPDIALOGRESULT oResult;
//		private String oMessage;
//		private Exception oException;
//
//
//
//		DialogResult(_STARTUPDIALOGRESULT _result, String _message, Exception _ex)
//		{
//			this.oResult = _result;
//			this.oMessage = _message;
//			this.oException = _ex;
//		}
//
//		public _STARTUPDIALOGRESULT getResult() { return this.oResult;}
//		public String getMessage() { return this.oMessage;}
//	}
	
	public Dialog_Startup(Context _context, int _preDelay, String _CompanyID, String _DriverNo, StartupDialogResultListener _listener)
	{
		super(_context, false, null);

		oListener = _listener;

		char[] DriverNo = _DriverNo.toCharArray();
		Boolean hasFirstDigit = false;
		_DriverNo = "";

		for(char c:DriverNo)
		{
			if(hasFirstDigit)
			{
				_DriverNo += String.valueOf(c);
			}
			else
			{
				if(String.valueOf(c).equals("0"))
				{
					//do nothing
				}
				else
				{
					_DriverNo += String.valueOf(c);
					hasFirstDigit = true;
				}
			}
		}

		this.oGlobals = new Globals(_context.getAssets(), _context.getResources(), (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE),  this.OnGlobalError());
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.dialog_startup);

		this.oLblStatus = (TextView) this.findViewById(R.id.dlgStarup_lblProgress);

		new DoStuff(oListener).execute(_CompanyID, _DriverNo, String.valueOf(_preDelay));

	}
	
			
	private class DoStuff extends AsyncTask<String,String,String> implements AnyApiRequest.AnyApiListener
	{

		StartupDialogResultListener oListener;
		public DoStuff(StartupDialogResultListener listener)
		{
			oListener = listener;
		}

		private void logStage(String _stage)
		{
			Context con = Dialog_Startup.this.getContext();
			DEBUGMANAGER.Log(con, "STAGE", _stage);
		}

		@Override
		protected String doInBackground(String... params)
		{ 
			logStage("1");

			//Debug.waitForDebugger();

			Globals g = Dialog_Startup.this.oGlobals;
			
			String iCompanyId=params[0];
			String iDriverNo=params[1];
            Integer preDelay = Integer.valueOf(params[2]);

			DEBUGMANAGER.Log(Dialog_Startup.this.getContext(), "Pre Delay", params[2]);

            if(preDelay > 0)
            {
                try
                {
                    Thread.sleep(preDelay);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
			
			String iMessage = "";
			Exception iEx = null;
			_STARTUPDIALOGRESULT iResult = _STARTUPDIALOGRESULT.PASS;

            HandyTools.StorageHelper hlpStore = new HandyTools.StorageHelper();
             if(hlpStore.isExternalStorageWritable())
            {
                //do nothing
            }
            else
            {

                if(hlpStore.isExternalStorageReadable())
                {
					oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.STORAGE_NOT_READABLE, "Could not access storage", null);
					dismiss();
					return "";
                }
                else
                {
					oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.STORAGE_NOT_WRITABLE, "Could not access storage", null);
					dismiss();
					return "";
                }
            }

			logStage("2");
			//settings file
			if (SETTINGSMANAGER.get(Dialog_Startup.this.getContext(), SETTINGS.COMPANY_ID).equals(SETTINGS.COMPANY_ID.getDefaultValue()))
			{

				this.publishProgress("Getting Settings File...");
				String url = Globals.getCabDespatchDataUrl() + "pdasettings/index.aspx" + Globals.WebTools.getAuthQueryString(Dialog_Startup.this.getContext(), iCompanyId, iDriverNo, "0") + Globals.rot13("&method=get");

				try
				{
					String s = Globals.WebTools.getWebPage(url);

					if ((s.equals("n") || s.equals("")))
					{
						oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.COULD_NOT_GET_SETTINGS_FILE, "Could not download settings", null);
						dismiss();
						return "";
					}
					else
					{
						//we get all the relevent settings on first login
						//disable this here as it can cause issues
							/*paramGroup sets = SETTINGSMANAGER.readSettingsData(s);
							if(SETTINGSMANAGER.putGroup(Dialog_Startup.this.getContext(), sets))
							{*/
						if(true)
						{

							SETTINGSMANAGER.SETTINGS.COMPANY_ID.putValue(Dialog_Startup.this.getContext(), iCompanyId);
							SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.putValue(Dialog_Startup.this.getContext(), iDriverNo);
						}
						else
						{
							oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.COULD_NOT_WRITE_MESSAGES, "Could not save settings", null);
							dismiss();
							return "";
						}

					}

				}
				catch (Exception ex)
				{
					oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.COULD_NOT_GET_SETTINGS_FILE, "Could not download settings", ex);
					dismiss();
					return "";
				}

			}

			logStage("3");
			//ZoneFile
			String zonefile = SETTINGSMANAGER.get(Dialog_Startup.this.getContext(), SETTINGSMANAGER.SETTINGS.PLOTS);
			if(zonefile.equals(SETTINGSMANAGER.SETTINGS.PLOTS.getDefaultValue()))
			{

				this.publishProgress("Getting zones");

				Globals.ZoneFileResult r = Globals.getZoneFile(Dialog_Startup.this.getContext(), iCompanyId, iDriverNo);
				if(r.wasSuccessful())
				{
					SETTINGSMANAGER.putZoneFile(Dialog_Startup.this.getContext(), r.getZoneFile());
				}
				else
				{
					oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.COULD_NOT_GET_ZONE_FILE, "Could not download settings", r.getException());
					dismiss();
					return "";
				}

			}

			SETTINGSMANAGER.loadPlots(Dialog_Startup.this.getContext(), false);


			logStage("4");
			//SOUNDS
			List<String> soundfiles = new ArrayList<String>();
			soundfiles.add(Globals.getSoundsFileLocation(Dialog_Startup.this.getContext()) + "youarein.wav");
			soundfiles.add(Globals.getSoundsFileLocation(Dialog_Startup.this.getContext()) + "workavail.wav");

			Boolean getnewmessagesounds = true;
			File dirnewmessages = new File(Globals.getNewMessageSoundsFileLocation(Dialog_Startup.this.getContext()));
			if((dirnewmessages.exists()))
			{
				if(Globals.FileTools.getFileEntries(dirnewmessages.getAbsolutePath()).size() > 0)
				{
					getnewmessagesounds=false;
				}
			}
			else
			{
				if(!(dirnewmessages.mkdirs()))
				{
					getnewmessagesounds=false;
					oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.COULD_NOT_CREATE_SOUND_FOLDERS, "Could not create sound folders", null);
					dismiss();
					return "";
				}
			}

			Boolean getjoboffersounds = true;
			File dirjoboffer = new File(Globals.getJobOfferSoundsFileLocation(Dialog_Startup.this.getContext()));
			if((dirjoboffer.exists()))
			{
				if(Globals.FileTools.getFileEntries(dirjoboffer.getAbsolutePath()).size()>0)
				{
					getjoboffersounds = false;
				}
			}
			else
			{
				if(!(dirjoboffer.mkdirs()))
				{
					getjoboffersounds = false;
					oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.COULD_NOT_CREATE_SOUND_FOLDERS, "Could not create sound folders", null);
					return "";
				}
			}



			if(getnewmessagesounds)
			{
				soundfiles.add(Globals.getNewMessageSoundsFileLocation(Dialog_Startup.this.getContext()) + "newmessage1.wav");
			}
			if (getjoboffersounds)
			{
				soundfiles.add(Globals.getJobOfferSoundsFileLocation(Dialog_Startup.this.getContext()) + "joboffer1.mp3");

				if (Globals.isDebug(getContext()))
				{
					soundfiles.add(Globals.getJobOfferSoundsFileLocation(Dialog_Startup.this.getContext()) + "joboffer2.mp3");
					soundfiles.add(Globals.getJobOfferSoundsFileLocation(Dialog_Startup.this.getContext()) + "joboffer3.mp3");
					soundfiles.add(Globals.getJobOfferSoundsFileLocation(Dialog_Startup.this.getContext()) + "joboffer4.mp3");
					soundfiles.add(Globals.getJobOfferSoundsFileLocation(Dialog_Startup.this.getContext()) + "joboffer5.mp3");
				}
			}

			int x=0;
			for(String s:soundfiles)
			{
				x++;

				String local = s;
				String filename = new File(s).getName();
				String remote = Globals.getCabDespatchDataUrl() + "files/" + filename;

				if (!(new File(local).exists()))
				{
					this.publishProgress("Getting sounds: " + String.valueOf(x) + "/" + String.valueOf(soundfiles.size()));

					if(!(g.WebTools.getFileHTTP(local, remote)))
					{
						oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.COULD_NOT_GET_SOME_SOUND_FILES, "Could not create sound folders", null);
						dismiss();
						return "";
					}
				}
			}


			logStage("5");
			this.publishProgress(Dialog_Startup.this.getContext().getString(R.string.checking_in));

			Context context = Dialog_Startup.this.getContext();
			return CheckinWithCabDespatch(iCompanyId, Globals.CrossFunctions.getDeviceIdentifier_wasIMEI(context), STATUSMANAGER.STATUSES.ACTIVATION_KEY.getValue(context),
					Double.valueOf(STATUSMANAGER.getAppVersionNumber(context)));

			//check in at cab despatch
//			if(doContinue)
//			{
//
//				String resultstring="x"; //assume fail
//
//				String result = "x";
//				try
//				{
//					String checkinUrl = Globals.getCabDespatchCheckinUrl(getContext()) + "index.aspx" + g.WebTools.getAuthQueryString(Dialog_Startup.this.getContext(), iCompanyId, iDriverNo) + g.rot13("&method=checkin");
//					//CLAY
//					resultstring = g.WebTools.getWebPage(checkinUrl);
//                    //resultstring = g.WebTools.getWebPage("http://192.168.4.20/PDA_Mirror/index.aspx" + g.WebTools.getAuthQueryString(Dialog_Startup.this.getContext(), iCompanyId, iDriverNo) + g.rot13("&method=checkin"));
//					char[] resulteles = resultstring.toCharArray();
//
//					if(resulteles.length >0)
//					{
//						result = String.valueOf(resulteles[0]);
//					}
//				}
//				catch (Exception e)
//				{
//					if(Globals.isDebug(getContext()))
//					{
//						e.printStackTrace();
//					}
//				}
//				if (result.equals("f")) //update required
//				{
//					STATUSMANAGER.putInt(Dialog_Startup.this.getContext(), STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS, 0);
//					iResult = _STARTUPDIALOGRESULT.UPDATE_REQUIRED;
//				}
//				else if (result.equals("a")) //pass - update available!
//		        {
//					STATUSMANAGER.putInt(Dialog_Startup.this.getContext(), STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS, 0);
//		        	iResult = _STARTUPDIALOGRESULT.UPDTAE_AVAILABLE;
//		        }
//		        else if (result.equals("n")) //not registered
//		        {
//					STATUSMANAGER.putInt(Dialog_Startup.this.getContext(), STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS, 0);
//		        	iResult = _STARTUPDIALOGRESULT.BILLING_VERIFICATION_REQUIRED;
//		        }
//		        else if (result.equals("d"))
//		        {
//					STATUSMANAGER.putInt(Dialog_Startup.this.getContext(), STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS, 0);
//		        	iResult = _STARTUPDIALOGRESULT.RE_REGISTRATION_REQUIRED;
//		        }
//		        else if (result.equals("p")) //pass!
//		        {
//					STATUSMANAGER.putInt(Dialog_Startup.this.getContext(), STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS, 0);
//		        	//sucess
//					String[] data = resultstring.split(",");
//					if(data.length>=5)
//					{
//						SETTINGS.IP_ADDRESS.putValue(Dialog_Startup.this.getContext(), data[1]);
//						SETTINGS.PORT_NO.putValue(Dialog_Startup.this.getContext(), data[2]);
//                        String signalRHost = data[3];
//                        if(signalRHost.trim().equals("*"))
//                        {
//                            SETTINGS.SignalRHost.reset(Dialog_Startup.this.getContext());
//                        }
//                        else
//                        {
//                            SETTINGS.SignalRHost.putValue(Dialog_Startup.this.getContext(), data[3]);
//                        }
//
//						String proxy_server_access_token = data[4];
//						STATUSMANAGER.STATUSES.PROXY_ACCESS_TOKEN.putValue(getContext(), proxy_server_access_token);
//					}
//
//				}
//		        else
//		        {
//		        	//non standard result... benefit of doubt for 5 tries
//		        	int trycount = (STATUSMANAGER.getInt(Dialog_Startup.this.getContext(), STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS) / 2);
//		        	if (trycount >=5)
//		        	{
//		        		//5 striked. do not allow progress
//		        		iResult = _STARTUPDIALOGRESULT.COULD_NOT_VERIFY_DEVICE;
//                        iMessage = iCompanyId + "~" + iDriverNo;
//		        	}
//		        	else
//		        	{
//
//		        		trycount +=1;
//		        		STATUSMANAGER.putInt(Dialog_Startup.this.getContext(), STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS, trycount * 2);
//                        STATUSMANAGER.putInt(Dialog_Startup.this.getContext(), STATUSMANAGER.STATUSES.CHECK_IN_ATTEMPTS_FAKE, trycount);
//		        	}
//
//		        }
//			}

			//logStage("Message: " + iMessage);
			//return new DialogResult(iResult, iMessage, iEx);
			//return "";
		}


		private final int TAG_CHECKIN = 123;
		private String CheckinWithCabDespatch(String companyId, String deviceIdentifier, String deviceKey, double appVersion)
		{
			//public static Checkin Obtain(String companyId, String deviceIdentifier, String deviceKey, int appVersion,  AnyApiListener listener)
			Checkin chk = Checkin.Obtain(TAG_CHECKIN, companyId, deviceIdentifier, deviceKey, appVersion, this);
			chk.Go();

			return "";
		}
		
		@Override
		protected void onProgressUpdate(String... progress) 
		{
	         Dialog_Startup.this.oLblStatus.setText(progress[0]);
	    }
		
		@Override
		protected void onPostExecute(String result)
		{
			//do nothing
	    }


		@Override
		public void OnApiRequestProgress(int tag, double progress)
		{

		}

		@Override
		public void OnApiRequestComplete(int tag, AnyApiRequest.AnyApiResult response)
		{
			dismiss();

			if(response.Failed())
			{
				STATUSMANAGER.addErrorAsDriverMessage(Dialog_Startup.this.getContext(), response.getErrorResponse());
				return;
			}

			if(tag==TAG_CHECKIN)
			{
				Checkin.PdaCheckinResponse item = (Checkin.PdaCheckinResponse) response.getResult();
				switch (item.checkinStatus)
				{
					case Checkin.PdaCheckinResponse.CHECKIN_STATUS_FAIL_UNSPECIFIED_ERROR:
						oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.COULD_NOT_VERIFY_DEVICE, "", null);
						return;
					case Checkin.PdaCheckinResponse.CHECKIN_STATUS_FAIL_DEVICE_NOT_ACTIVE:
					case Checkin.PdaCheckinResponse.CHECKIN_STATUS_FAIL_INVALID_DEVICE_KEY:
						oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.BILLING_VERIFICATION_REQUIRED, "", null);
						return;
					case Checkin.PdaCheckinResponse.CHECKIN_STATUS_PASS:
						STATUSMANAGER.STATUSES.PROXY_ACCESS_TOKEN.putValue(Dialog_Startup.this.getContext(), item.token);
						oListener.OnStartupDialogResult(_STARTUPDIALOGRESULT.PASS, "", null);
						return;
				}
			}
		}
	}
	
	private Globals.OnGlobalErrorHandler OnGlobalError()
	{
		return new Globals.OnGlobalErrorHandler() 
		{
			
			@Override
			public void OnError(String _error, Exception _ex) 
			{
				//do nothing..
			}
		};
	}
	
	

}
