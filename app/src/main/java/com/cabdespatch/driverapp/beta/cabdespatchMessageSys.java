package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;

import com.cabdespatch.driverapp.beta.STATUSMANAGER.APP_STATE;
import com.cabdespatch.driverapp.beta.STATUSMANAGER.DriverMessage.BOX;
import com.cabdespatch.driverapp.beta.STATUSMANAGER.DriverMessage.TYPE;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;
import com.cabdespatch.driverapp.beta.cabdespatchJob.JOB_STATUS;
import com.cabdespatch.driverapp.beta.uiMessage.UIMESSAGE_TYPE;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.text.DecimalFormat;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class cabdespatchMessageSys
{


	private static String dBugFile;
	//private static Boolean createLogFile = false;
	
	/*public static void logDebug( String _data, Boolean _alert  )
    {	
    	try
        {
            File file = new File(dBugFile);
 
    		//if file doesnt exists, then create it
    		if(!file.exists())
            {
    			file.createNewFile();
                //Log.e("FILENAME", dBugFile);
                return;
    		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(file,true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

    	        if (_alert) { LogDebugMessage(bufferWritter, "***** ALERT *****\n"); }

                String actualLogData = "";

    	        if (!(_alert))
    	        {
    	        	//not an alert - timestamp
    	        	DateTime d = new DateTime();
    	        	String logTime = d.dayOfMonth().getAsText() + "/" + d.monthOfYear().getAsShortText() + " - ";
    	        	logTime += d.hourOfDay().getAsText() + "." + d.minuteOfHour().getAsText() + ":   ";
    	        	actualLogData = logTime;
    	        }
                actualLogData += (_data + "\n");
    	        LogDebugMessage(bufferWritter, actualLogData);
    	        
    	        
    	        if (_alert) { bufferWritter.write ("***** ALERT *****\n"); }
    	        bufferWritter.close();
 
    	}
        catch(IOException e)
        {
    		e.printStackTrace();
    	}
    }
*/

	private static  class messageHeaderTypes
	{
		public static  class send
		{
			public static final String LOGIN = "f.";
            public static final String PING = ".";
            public static final String LOGOFF = "e";
            public static final String JOB_ACCEPT = "4";
            public static final String JOB_REJECT = "5";
            public static final String JOB_OFFER_TIMEOUT = "XTO";
            public static final String JOB_STP = "8";
            public static final String JOB_POB = "9";
            public static final String JOB_STC = "a";
            public static final String JOB_CLEAR = "b";
            public static final String BID = "i";
            public static final String FLAGSTART = "r";
            public static final String FLAGSTOP = "s";
            public static final String JOB_RETURN = "I";
            public static final String ADDSTOP = "G1";
            public static final String NOSHOW = "c";
            public static final String WAIT_TIME_START = "u";
            public static final String WAIT_TIME_END = "v";
            public static final String BREAK_START="6";
            public static final String BREAK_END="7";
            public static final String TXMESSAGE="t";
            public static final String VOICEREQUEST="g";
            public static final String PANIC = "d";
            public static final String PLOT = "3";

            public static final String ONRANK = "W";
            public static final String OFFRANK = "X";

            public static final String PRICE_UPDATE = "SB";
            public static final String FUTUREJOBS_REQUEST="JUa";
            public static final String FUTURE_JOBS_BID = "JUb";
            public static final String JOB_TOTALS="F";
            public static final String CIRCUIT_FEES = "O";

            public static final String POD = "C";

            public static final String ADD_TO_JOB_HISTORY = "HS";
            public static final String CONFIRM_MESSAGE_READ = "cX";

            public static final String jobPendingStatus   = "!jP";

		}

		public static class receive
		{
            public static final String DATA_WAITING = "!a";
			public static final String LOGOFF = "e";
            public static final String MESSAGE = "1";
            public static final String JOBOFFER = "q";
            public static final String JOB_AMMEND = "!q";

            public static final String NOTLOGGEDIN = "G";
            public static final String WORKAVAILABLE = "0";
            public static final String FUTUREJOBS="JU";

            public static final String ANTICHEATOVERRIDE = "D";
            public static final String WORK_WAITING_AT_DESTINATION = "T";

            //acks
            public static final String ACK_Logon = "w";
            public static final String ACK_JOBACCEPT = "j";
            public static final String ACK_LOGOFF = "ee";
            public static final String ACK_STP = "k";
            public static final String ACK_POB = "l";
            public static final String ACK_STC = "m";
            public static final String ACK_CLR = "o";
            public static final String ACK_REJECTAFTERACCEPT = "J";
            public static final String ACK_NSH = "n";
            public static final String ACK_BREAK_START = "x";
            public static final String ACK_BREAK_END = "y";
            public static final String ACK_FLAG_START = "z";
            public static final String ACK_FLAG_END = "A";
            public static final String ACK_ON_RANK = "Y";
            public static final String ACK_OFF_RANK = "Z";
            public static final String ACK_PRICE = "SC";

            public static final String NO_NO_SHOW = "Q";

            public static final String NEW_SETTINGS = "SA";
            public static final String NEW_PLOT_VERSION = "p";

            public static final String PING = ".";
            public static final String PONG = "..";
            public static final String RESET_PDA = "XX";
            //public static final String PLOT = "3";


            public static final String MOBILE_UPDATER = "s"; //not used in Android...

            public static final String NEW_UPDATE_AVAILABLE = "UAA";
            public static final String NEW_UPDATE_REQUIRED = "UAR";
            public static final String JOB_TOTALS = "E";



            public static final String CIRCUIT_FEES = "O";
		}
	}

	private static  class messageHeaderData

	{
		public static class send
		{
		    public static final String FIREBASE_ID = "fxid";
			public static final String COMPANY_ID = "xlc";
			public static final String DRIVERNO = "h";
			public static final String PLOT = "e";
            public static final String RANK = "7n";
            public static final String LAT = "f";
            public static final String LON = "g";
            public static final String PHONEDATA = "n";
            public static final String APP_VERSION = "k";
            public static final String APP_REVISION = "kk";
            public static final String WIFI_STATE = "wf";

            public static final String PLOT_VERSION = "l";
            public static final String VEHICLE_NO = "VN";
            public static final String SETTINGS_VER = "SV";
            public static final String WAIT_MINS = "u";
            public static final String TXMESSAGE = "m";
            public static final String SPEED = "t";
            public static final String RUN_DISTANCE = "y";
            public static final String PIN = "K";
            public static final String BATTERY_LEVEL = "J";
            public static final String BATTERY_STATUS = "JJ";
            public static final String DEBUG_INFO = "DI";
            public static final String PRICE = "7";
            public static final String GPS_HASFIX = "-";
            public static final String GPS_ACCURACY = "*a";
            public static final String GPS_SATS = "*";

            public static final String LOG_TAG = "dlt";
            public static final String LOG_MESSAGE = "dlm";

            //job specific
            public static final String jobID = "c";
            public static final String jobMeterPrice = "!7";
            public static final String JOB_DRIVER_NOTES = "jdn";


            public static final String POD = "x";

            public static final String WAITING_TIME_IS_AUTO = "v";

            public static final String JOB_HISTORY_DATA = "HS";
            public static final String FLAG = "FL";
            public static final String MESSAGE_ID = "idx";
            public static final String DRIVERMESSAGE_TEXT = "1";
		}

		public static  class receive
		{
			public static final String FLAG = "FL";
            public static final String DRIVERMESSAGE_TEXT = "1";
            public static final String DRIVERMESSAGE_MODE = "1.1";
            public static final String PLOT           = "e";
            public static final String TRAP           = "i";
            public static final String INDIVIDUALTRAP = "j";

            public static final String CARSAVAILABLE        = "2";
            public static final String WORKAVAILABLE        = "0";
            public static final String FUTUREJOBS           = "JU";
            public static final String NEW_SETTINGS_VERSION = "SA";
            public static final String NEW_SETTINGS_DATA    = "SB";
            public static final String NO_NOSHOW            = "Q";
            public static final String SAFETY_PANEL         = "%";

            //job specific
            public static final String jobPLOT_FROM       = "3";
            public static final String jobPLOT_TO         = "4";
            public static final String jobADDRESS_FROM    = "5";
            public static final String jobADDRESS_TO      = "6";
            public static final String jobPriceLock       = "+7";
            public static final String jobPriceDisplay    = "?7";
            public static final String jobPRICE           = "7";
            public static final String jobMeterPrice      = "!7";
            public static final String jobACCOUNT         = "8";
            public static final String jobCOMMENTS        = "9";
            public static final String jobNAME            = "a";
            public static final String jobTIME            = "b";
            public static final String jobID              = "c";
            public static final String jobDISTANCE        = "c1";
            public static final String jobMODE            = "c2";
            public static final String jobVEHICLETYPE     = "M";
            public static final String fromLat            = "o";
            public static final String fromLon            = "p";
            public static final String toLat              = "q";
            public static final String toLon              = "r";
            public static final String jobSHOWZONEONOFFER = "R";
            public static final String jobFlightNo        = "Fx";
            public static final String jobAutoAccept      = "N";
            public static final String jobNotesRequired   = "jdn";
            public static final String jobPendingStatus   = "!jP";

            public static final String jobAmountPrePaid = "X2";
            public static final String jobBookingFee = "bfx";;

            public static final String viaDetails[] = {"w0", "w1", "w2", "w3", "w4", "w5", "w6", "w7", "w8", "w9"};
            //dealt with in a different way
            //public static final String viaLatLons[] = {"ww0", "ww1", "ww2", "ww3", "ww4", "ww5", "ww6", "ww7", "ww8", "ww9"};

            public static final String DRIVERNO = "h";

            //job totals
            public static final String MONDAY    = "C";
            public static final String TUESDAY   = "D";
            public static final String WEDNESDAY = "E";
            public static final String THURSDAY  = "F";
            public static final String FRIDAY    = "G";
            public static final String SATURDAY  = "H";
            public static final String SUNDAY    = "I";

            //circit fees
            public static final String CIRCUIT_FEES_OUTSTANDING = "Y";
            public static final String CIRCUIT_FEES_ACCOUNTS = "Z";

            public static final String TELEPHONE_NUMBER = "tel";
            public static final String CONFIRMATION_REQUIRED = "crq";
            public static final String MESSAGE_ID = "idx";
            public static final String METER_RATES = "mex";


            public static class PRESET_MESSAGES
            {
                public static final String DRIVER_1 = "1s";
                public static final String DRIVER_2 = "2s";
                public static final String DRIVER_3 = "3s";
                public static final String DRIVER_4 = "4s";
                public static final String DRIVER_5 = "5s";
                public static final String DRIVER_6 = "6s";
                public static final String DRIVER_7 = "7s";

                public static final String NOSHOW_1 = "1n";
                public static final String NOSHOW_2 = "2n";
                public static final String NOSHOW_3 = "3n";
                public static final String NOSHOW_4 = "4n";
                public static final String NOSHOW_5 = "5n";
            }

        }
    }

    public cabdespatchMessageSys()
    {
        //MY_ACTING_DRIVERS_ID = String.valueOf(new Random().nextInt(999) + 5000);
        File f = new File(Environment.getExternalStorageDirectory(), "cabdespatch");
        if(!(f.exists()))
        {
            f.mkdirs();
        }

        f = new File(f, "debug.log");

        dBugFile = f.getAbsolutePath();
        //createLogFile = _createLogFile;
    }

    public build  builder = new build();
    public handle handler = new handle();

    public class build
    {
        public build() {}

        private class messageOut
        {
            private String _builtMessage;

            public messageOut(String _messageType)
            {
                _builtMessage = "[" + _messageType + "]";
            }

            public void add(String _header, String _data)
            {
                _builtMessage += ("\"" + _header + "~" + _data);
            }

            public String getDataMessage()
            {
                _builtMessage += ("\"" + (char) 253);

                /*
                if (createLogFile)
                {
                    //logDebug("sendingOUT >>>   " + _builtMessage, false);
                }*/

                return _builtMessage;
            }
        }

        public priorityString Login(Context _c, String _deviceIMEI, String _deviceNetwork, String _devicePhoneNumber, String _deviceDescription)
        {
            String signalRHost = SETTINGSMANAGER.getSignalRHost(_c);
            String proxyMode=(signalRHost.equals(Settable.NOT_SET)?"10":"20");

            //CLAY!!
            messageOut m = new messageOut(messageHeaderTypes.send.LOGIN);
            m.add(messageHeaderData.send.PHONEDATA, _deviceIMEI + ";" + _deviceNetwork + ":" + _devicePhoneNumber + ":" + _deviceDescription + ":" + proxyMode);
            m.add(messageHeaderData.send.APP_VERSION, STATUSMANAGER.getAppVersionNumber(_c));
            m.add(messageHeaderData.send.PLOT_VERSION, String.valueOf(SETTINGSMANAGER.getPlotsAll(_c).getVersionNumber()));
            m.add(messageHeaderData.send.RUN_DISTANCE, SETTINGSMANAGER.get(_c, SETTINGSMANAGER.SETTINGS.RUN_MODE));


            Boolean usePinLogin = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_PIN_LOGIN.getValue(_c));

            if (usePinLogin)
            {
                String vehicleNo = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.ACTING_VEHICLE_NO);
                String pinNo = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.ACTING_PIN_NO);

                m.add(messageHeaderData.send.VEHICLE_NO, vehicleNo);
                m.add(messageHeaderData.send.PIN, pinNo);
            }
            else
            {
                m.add(messageHeaderData.send.VEHICLE_NO, "0");
            }

            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString logHistoryText(Context _c, String _message)
        {
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            messageOut m = new messageOut(messageHeaderTypes.send.ADD_TO_JOB_HISTORY);
            m.add(messageHeaderData.send.jobID, j.getID());
            m = addMiscBits(_c, m);
            m.add(messageHeaderData.send.JOB_HISTORY_DATA, _message);

            return  new priorityString(m.getDataMessage(), 10);
        }

        public priorityString Plot(Context _c, String _plot)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.PLOT);
            m = addMiscBits(_c, m);
            m.add(messageHeaderData.send.PLOT, _plot);

            return new priorityString(m.getDataMessage(), 10);

        }

        public priorityString POD(Context _c, String _podData)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.POD);
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            m.add(messageHeaderData.send.jobID, j.getID());
            m = addMiscBits(_c, m);
            m.add(messageHeaderData.send.POD, _podData);

            return new priorityString(m.getDataMessage(), 10);
        }

		/*public priorityString Logoff(Context _c)
		{
			return Logoff(_c, "","");
		}*/

        public priorityString Logoff(Context _c, String _logTag, String _logMessage)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.LOGOFF);
            m = addMiscBits(_c, m);

            m.add(messageHeaderData.send.LOG_TAG, _logTag);
            m.add(messageHeaderData.send.LOG_MESSAGE, _logMessage);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString FlagDownStart(Context _c)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.FLAGSTART);
            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString FlagDownStop(Context _c)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.FLAGSTOP);
            m = addMiscBits(_c, m);

            if (SETTINGSMANAGER.SETTINGS.METER_TYPE.notEquals(_c, SETTINGSMANAGER.METER_TYPE.NONE))
            {
                m.add(messageHeaderData.send.jobMeterPrice, STATUSMANAGER.getCurrentJob(_c).getMeterPrice().toString());
            }

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString noShow(Context _c)
        {
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            messageOut m = new messageOut(messageHeaderTypes.send.NOSHOW);
            m.add(messageHeaderData.send.jobID, j.getID());
            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString dataMessage(Context _c, String _message) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidParameterSpecException, BadPaddingException, InvalidKeyException {
            //encrypting message befor it is sent.
            _message = String.valueOf(EncryptionHandler.EncryptString(_message));
            messageOut m = new messageOut(messageHeaderTypes.send.TXMESSAGE);
            m.add(messageHeaderData.send.TXMESSAGE, _message);

            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);
            JOB_STATUS s = j.getJobStatus();

            if(s.equals(JOB_STATUS.ERROR))
            {
                //do nothing
            }
            else if(s.equals(JOB_STATUS.UNDER_OFFER))
            {
                //do nothing
            }
            else if(s.equals(JOB_STATUS.NOT_ON_JOB))
            {
                //do nothing
            }
            else
            {
                m.add(messageHeaderData.send.jobID, j.getID());
            }

            m = addMiscBits(_c, m);
            //m=addMiscBits(m, _s);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString confirmMessageRead(Context _c, STATUSMANAGER.DriverMessage _m)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.CONFIRM_MESSAGE_READ);
            m.add(messageHeaderData.send.MESSAGE_ID, _m.getIDForConfirmation());
            m.add(messageHeaderData.send.DRIVERMESSAGE_TEXT, _m.getMessage());

            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            if(!(_m.isJobLess()))
            {
                m.add(messageHeaderData.send.jobID, j.getID());
            }

            m=addMiscBits(_c, m);

            priorityString p = new priorityString(m.getDataMessage(), 10);

            return p;
        }

        public priorityString BreakStart(Context _c)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.BREAK_START);
            m = addMiscBits(_c, m);
            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString BreakEnd(Context _c)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.BREAK_END);
            m = addMiscBits(_c, m);
            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString WaitingTimeStart(Context _c)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.WAIT_TIME_START);
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            m.add(messageHeaderData.send.jobID, j.getID());
            m.add(messageHeaderData.send.WAIT_MINS, Integer.valueOf((j.getWaitingTime()) / 60).toString());
            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString WaitingTimeEnd(Context _c, Boolean _isAuto)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.WAIT_TIME_END);
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            m.add(messageHeaderData.send.jobID, j.getID());
            m.add(messageHeaderData.send.WAIT_MINS, Integer.valueOf((j.getWaitingTime()) / 60).toString());
            m = addMiscBits(_c, m);

            if(_isAuto)
            {
                m.add(messageHeaderData.send.WAITING_TIME_IS_AUTO, "t"); //any value here. SS just checks if string is blank or not
            }

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString OnRouteStop(Context _c)
        {
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            messageOut m = new messageOut(messageHeaderTypes.send.ADDSTOP);
            m.add(messageHeaderData.send.jobID, j.getID());
            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString RejectAfterAccept(Context _c)
        {
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            messageOut m = new messageOut(messageHeaderTypes.send.JOB_RETURN);
            m.add(messageHeaderData.send.jobID, j.getID());
            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString JobOfferTimeout(Context _c)
        {
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            messageOut m = new messageOut(messageHeaderTypes.send.JOB_OFFER_TIMEOUT);
            m.add(messageHeaderData.send.jobID, j.getID());
            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString Reject(Context _c)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.JOB_REJECT);
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);
            m.add(messageHeaderData.send.jobID, j.getID());

            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }

        public priorityString Accept(Context _c)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.JOB_ACCEPT);
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);
            m.add(messageHeaderData.send.jobID, j.getID());


            JOB_STATUS pendingStatus = j.getPendingStatus();
            String pendingStatusString = "-1";
            switch (pendingStatus)
            {
                case STP:
                    pendingStatusString = "4";
                    break;
                case POB:
                    pendingStatusString = "5";
                    break;
                case STC:
                    pendingStatusString = "6";
                    break;
            }

            m.add(messageHeaderTypes.send.jobPendingStatus, pendingStatusString);


            m = addMiscBits(_c, m);


			return new priorityString(m.getDataMessage(), 10);
		}

		public priorityString Bid(Context _c, String _data)
		{
		    String[] data = _data.split(":");
		    String bidType = data[0];
		    String bidPlot = data[1];

		    String messageType = (bidType.equals("f")?messageHeaderTypes.send.FUTURE_JOBS_BID:messageHeaderTypes.send.BID);
			messageOut m = new messageOut(messageType);
			m=addMiscBits(_c, m, false);
			//plot we are bidding for - a little different than usual plot message!
			m.add(messageHeaderData.send.PLOT, bidPlot);

			return new priorityString(m.getDataMessage(), 10);
		}

		public priorityString STP(Context _c)
		{
			cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

			messageOut m = new messageOut(messageHeaderTypes.send.JOB_STP);
			m=addMiscBits(_c, m);
			m.add(messageHeaderData.send.jobID, j.getID());

			return new priorityString(m.getDataMessage(), 10);
		}

		public priorityString POB(Context _c)
		{
			cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

			messageOut m = new messageOut(messageHeaderTypes.send.JOB_POB);
			m=addMiscBits(_c, m);
			m.add(messageHeaderData.send.jobID, j.getID());

			return new priorityString(m.getDataMessage(), 10);
		}

		public priorityString STC(Context _c)
		{
			cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

			messageOut m = new messageOut(messageHeaderTypes.send.JOB_STC);
			m=addMiscBits(_c, m);
			m.add(messageHeaderData.send.jobID, j.getID());


			String meterPrice = "-1";

            String meterMode = SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(_c);
            if(meterMode.equals(SETTINGSMANAGER.METER_TYPE.NONE))
            {
                //do nothing
            }
            else
            {
                meterPrice = String.valueOf(j.getMeterPrice());
            }
            m.add(messageHeaderData.send.jobMeterPrice, meterPrice);

			return new priorityString(m.getDataMessage(), 10);
		}

        public priorityString FareRequest(Context _c)
        {
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            messageOut m = new messageOut(messageHeaderTypes.send.JOB_STC);
            m.add(messageHeaderData.send.FLAG, "P:" + SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(_c)); //p for passive? w/e don't really matter!
            m=addMiscBits(_c, m);
            m.add(messageHeaderData.send.jobID, j.getID());

            return new priorityString(m.getDataMessage(), 10);
        }

		public priorityString Clear(Context _c)
		{
			cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

			messageOut m = new messageOut(messageHeaderTypes.send.JOB_CLEAR);
			m=addMiscBits(_c, m);
			m.add(messageHeaderData.send.jobID, j.getID());

            String driverNotes = j.getDriverNotes();
            Boolean requestBreak = STATUSMANAGER.STATUSES.REQUEST_BREAK_ON_CLEAR.parseBoolean(_c);

            if((driverNotes.equals(cabdespatchJob.EMPTY)) || (driverNotes.equals("")))
            {
                //do nothing
            }
            else
            {
                m.add(messageHeaderData.send.JOB_DRIVER_NOTES, HandyTools.Strings.replaceNewLines(driverNotes));
            }

            //if we need to add other booleans in the future, use flag to store a bitmap.
            m.add(messageHeaderData.send.FLAG, (requestBreak?"1":"0"));
            STATUSMANAGER.STATUSES.REQUEST_BREAK_ON_CLEAR.reset(_c);

			return new priorityString(m.getDataMessage(), 10);
		}

		public priorityString voiceRequest(Context _c)
		{
			messageOut m = new messageOut(messageHeaderTypes.send.VOICEREQUEST);
			m=addMiscBits(_c, m);

			return new priorityString(m.getDataMessage(), 10);
		}

		public priorityString Panic(Context _c)
		{
			messageOut m = new messageOut(messageHeaderTypes.send.PANIC);

			m=addMiscBits(_c, m);

			return new priorityString(m.getDataMessage(), 100);
		}


		public priorityString Ping(Context _c, Boolean _sendLatLon, Boolean _includeFirebaseID)
		{
			cabdespatchJob j = cabdespatchJob.unpack(STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_JOB));

			messageOut m = new messageOut(messageHeaderTypes.send.PING);

			if (!(j.getID().equals("")))
			{
				m.add(messageHeaderData.send.jobID, j.getID());
			}

			if(_includeFirebaseID)
            {
                String firebaseID = STATUSMANAGER.getFirebaseInstanceId(_c);
                if(firebaseID.equals(""))
                {
                    //do nothing
                }
                else
                {
                    firebaseID = Base64.encodeToString(firebaseID.getBytes(), Base64.DEFAULT);
                }

                m.add(messageHeaderData.send.FIREBASE_ID, firebaseID);
            }

            //m.add(messageHeaderData.send.jobID, "7553");
			m=addMiscBits(_c, m);



			return new priorityString(m.getDataMessage(), 0);
		}

		public priorityString FutureJobs(Context _c)
		{
			messageOut m = new messageOut(messageHeaderTypes.send.FUTUREJOBS_REQUEST);
			m =addMiscBits(_c,m);

			return new priorityString(m.getDataMessage(),0);
		}

		public priorityString OnRank(Context _c)
		{
		    messageOut m = new messageOut(messageHeaderTypes.send.ONRANK);
            m.add(messageHeaderData.send.RANK, STATUSMANAGER.getCurrentLocation(_c).getPlot().getShortName());
            m=addMiscBits(_c, m);
		/*	
			m.add(messageHeaderData.send.COMPANY_ID, _s.getCompanyId());
			m.add(messageHeaderData.send.DRIVERNO, _s.getDriverNo());
			//
			*/

			return new priorityString(m.getDataMessage(), 100);
		}

		public priorityString OffRank(Context _c)
		{

			messageOut m = new messageOut(messageHeaderTypes.send.OFFRANK);
            m=addMiscBits(_c, m);
			/*
			m.add(messageHeaderData.send.COMPANY_ID, _s.getCompanyId());
			m.add(messageHeaderData.send.DRIVERNO, _s.getDriverNo());
			//m=addMiscBits(m, _s);
			*/

			return new priorityString(m.getDataMessage(), 100);
		}

		public priorityString PriceUpdate(Context _c, String _newPrice)
		{
			cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

			messageOut m = new messageOut(messageHeaderTypes.send.PRICE_UPDATE);
			m=addMiscBits(_c, m);
			m.add(messageHeaderData.send.jobID, j.getID());
			m.add(messageHeaderData.send.PRICE, _newPrice);

			return new priorityString(m.getDataMessage(), 10);
		}

		public priorityString JobTotals(Context _c)
		{
			messageOut m = new messageOut(messageHeaderTypes.send.JOB_TOTALS);
			m = addMiscBits(_c, m);

			return new priorityString(m.getDataMessage(), 10);
		}

        public priorityString CircuitFees(Context _c)
        {
            messageOut m = new messageOut(messageHeaderTypes.send.CIRCUIT_FEES);
            m = addMiscBits(_c, m);

            return new priorityString(m.getDataMessage(), 10);
        }


		private messageOut addMiscBits(Context _c, messageOut _m)
		{
			return addMiscBits(_c, _m, true);
		}

		private messageOut addMiscBits(Context _c, messageOut _m, Boolean _includePlot)
		{
			Boolean demoMode = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.IS_DEMO_MODE.getValue(_c));

			_m.add(messageHeaderData.send.COMPANY_ID, SETTINGSMANAGER.get(_c, SETTINGSMANAGER.SETTINGS.COMPANY_ID));

			String driverCallSign = STATUSMANAGER.getActingDriverNumber(_c);

			if(demoMode)
			{
				driverCallSign="199";
			}

			_m.add(messageHeaderData.send.DRIVERNO, driverCallSign);
            _m.add(messageHeaderData.send.APP_VERSION, STATUSMANAGER.getAppVersionNumber(_c));
            _m.add(messageHeaderData.send.APP_REVISION, STATUSMANAGER.getAppRevision().toString());
            _m.add(messageHeaderData.send.WIFI_STATE, (cabdespatchServiceDetectors.isWiFiEnabled(_c)?"1":"0"));

            /*
            String[] lats = lat.split(Pattern.quote("."));
            String[] lons = lon.split(Pattern.quote("."));

            if(lats.length > 1)
            {
                if(lats[1].length() > 5)
                {
                    lat = lats[0] + "." + lats[1].substring(0,5);
                }
            }

            if(lons.length > 1)
            {
                if(lons[1].length() > 5)
                {
                    lon = lons[0] + "." + lons[1].substring(0,5);
                }
            }*/


            DecimalFormat format2dp = new DecimalFormat("#.##");
            Double cSpeed = STATUSMANAGER.getSpeedAsMPH(_c);

			_m.add(messageHeaderData.send.SPEED, format2dp.format(cSpeed));
            _m.add(messageHeaderData.send.GPS_ACCURACY, format2dp.format(STATUSMANAGER.getCurrentLocation(_c).getAccuracy()));
            _m.add(messageHeaderData.send.GPS_HASFIX, String.valueOf(cabdespatchGPS.hasFix()));

            if(cabdespatchGPS.hasFix())
            {
                DecimalFormat format5dp = new DecimalFormat("#.#####");
                String lat = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LAT);
                String lon = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LON);
                _m.add(messageHeaderData.send.LAT, format5dp.format(Double.valueOf(lat)));
                _m.add(messageHeaderData.send.LON, format5dp.format(Double.valueOf(lon)));
            }

            _m.add(messageHeaderData.send.GPS_SATS, "-1");
			_m.add(messageHeaderData.send.BATTERY_LEVEL, String.valueOf(STATUSMANAGER.BATTERY_LEVEL));
            _m.add(messageHeaderData.send.BATTERY_STATUS, STATUSMANAGER.BATTERY_CHARGING?"1":"0");
			_m.add(messageHeaderData.send.DEBUG_INFO, String.valueOf(STATUSMANAGER.getInt(_c, STATUSMANAGER.STATUSES.DATA_SERVICE_KILL_COUNT)));

			
			
			if(_includePlot)
			{
				String a = STATUSMANAGER.getAppState(_c);
				
				if((a.equals(APP_STATE.LOGGED_ON) || (a.equals(APP_STATE.ON_BREAK))))
				{
					
					_m.add(messageHeaderData.send.PLOT, STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_PLOT));
				}
			}


           // DEBUGMANAGER.Log("OUT:",  _m.getDataMessage());
			return _m;

        }
		
	}
	
	public class handle
	{
		public handle(){};


		private uiMessage handleSettingsData(Context _c, String _data)
        {

            uiMessage retMessage = null;
            Boolean rebootRequired = false;
            Boolean restartRequired = false;


            //new Encrypting to socket server
            //cant do this here

            if(!(_data.isEmpty()))
            {
                if(_data.contains("="))
                {
                    String oldLockdownMode = SETTINGSMANAGER.getLockDownMode(_c);
                    String oldIP = SETTINGSMANAGER.SETTINGS.IP_ADDRESS.getValue(_c);
                    String oldPorNo = SETTINGSMANAGER.SETTINGS.PORT_NO.getValue(_c);
                    String oldPingInterval = SETTINGSMANAGER.SETTINGS.PING_TIME_SECONDS.getValue(_c);
                    String oldDataMode = SETTINGSMANAGER.SETTINGS.DATA_MODE.getValue(_c);
                    String oldPinLoginSetting = SETTINGSMANAGER.SETTINGS.USE_PIN_LOGIN.getValue(_c);

                    paramGroup p = SETTINGSMANAGER.readSettingsData(_data);
                    Boolean didSave = false;

                    while(!(didSave))
                    {
                        didSave=SETTINGSMANAGER.putGroup(_c, p);
                        if(!(didSave))
                        {
                            try
                            {
                                Thread.sleep(500);
                            }
                            catch(Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }

                    String newLockdownMode = SETTINGSMANAGER.getLockDownMode(_c);
                    String newIP = SETTINGSMANAGER.SETTINGS.IP_ADDRESS.getValue(_c);
                    String newPorNo = SETTINGSMANAGER.SETTINGS.PORT_NO.getValue(_c);
                    String newPingInterval = SETTINGSMANAGER.SETTINGS.PING_TIME_SECONDS.getValue(_c);
                    String newDataMode = SETTINGSMANAGER.SETTINGS.DATA_MODE.getValue(_c);
                    String newPinLoginSetting = SETTINGSMANAGER.SETTINGS.USE_PIN_LOGIN.getValue(_c);

                    if(!(oldLockdownMode.equals(newLockdownMode))) { rebootRequired = true; }
                    if(!(oldIP.equals(newIP))) { restartRequired=true; }
                    if(!(oldPorNo.equals(newPorNo))) {  restartRequired = true; }
                    if(!(oldPingInterval.equals(newPingInterval))) { restartRequired = true; }
                    if(!(oldDataMode.equals(newDataMode))) { restartRequired = true; }
                    if(!(oldPinLoginSetting.endsWith(newPinLoginSetting))) { restartRequired = true; }

                }
                else
                {
                    //CLAY throw invalid data exception?
                }
            }

            if(rebootRequired)
            {
                retMessage = new uiMessage(UIMESSAGE_TYPE.REBOOT_REQUIRED_FOR_LOGIN, "");
            }
            else if(restartRequired)
            {
                retMessage = new uiMessage(UIMESSAGE_TYPE.RESTART_FOR_DATASERVICE, "");
            }

            return retMessage;
        }

		public uiMessage handleMessage(Context _c, String _message)
		{
            DEBUGMANAGER.Log(_c, "<<<<", _message);

			if (_message.trim().equals(CabDespatchNetwork._SPECIALMESSAGES.PURGE_CHECK_MESSGAE)) { return new uiMessage(UIMESSAGE_TYPE.IGNORE, ""); } //socket server just wants to make sure we're still online! :)
			/*else if (_message.equals(CabDespatchNetwork._SPECIALMESSAGES.NETWORK_READY))	{ return new uiMessage(UIMESSAGE_TYPE.NETWORKREADY,"");	}
			else if (_message.equals(CabDespatchNetwork._SPECIALMESSAGES.NETWORK_NOT_READY)) { return new uiMessage(UIMESSAGE_TYPE.NONETWORK,""); }
            else if (_message.equals(CabDespatchNetwork._SPECIALMESSAGES.NETWORK_RECONNECTING)) { return new uiMessage(UIMESSAGE_TYPE.NETWORK_RECONNECTING,""); }*/
			
			else 
			{ 
				String[] MessageParts = _message.split("\"");
				
				if (MessageParts.length > 0)
				{
					/*
					if (createLogFile)
					{
						logDebug ("messageIn <<<   " + _message, false);
					}
					*/

					paramGroup MessageParticles = new paramGroup();
					
					for (String s: MessageParts)
					{
						String kvp[] = s.split("~");
                        //get all the little bits of data into
						//their nice little containers
						if(kvp.length==2)
						{
							MessageParticles.addParam(kvp[0], kvp[1]);
						}
					
					}
					
					String messageType = MessageParts[0].replace("[", "");
					messageType = messageType.replace("]", "");
					
					handleMiscBits(_c, MessageParticles);
					
					//no switch on strings in Java :(
					
					
					
					if (messageType.equals(messageHeaderTypes.receive.ACK_Logon))
					{

                        uiMessage settingsTest = handleSettingsData(_c, MessageParticles.getParamValue(messageHeaderData.receive.NEW_SETTINGS_DATA));
						if(settingsTest == null)
						{

                            SETTINGSMANAGER.addDriverMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.DRIVER_1));
                            SETTINGSMANAGER.addDriverMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.DRIVER_2));
                            SETTINGSMANAGER.addDriverMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.DRIVER_3));
                            SETTINGSMANAGER.addDriverMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.DRIVER_4));
                            SETTINGSMANAGER.addDriverMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.DRIVER_5));
                            SETTINGSMANAGER.addDriverMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.DRIVER_6));
                            SETTINGSMANAGER.addDriverMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.DRIVER_7));

                            SETTINGSMANAGER.addNoShowMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.NOSHOW_1));
                            SETTINGSMANAGER.addNoShowMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.NOSHOW_2));
                            SETTINGSMANAGER.addNoShowMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.NOSHOW_3));
                            SETTINGSMANAGER.addNoShowMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.NOSHOW_4));
                            SETTINGSMANAGER.addNoShowMessagePreset(_c, MessageParticles.getParamValue(messageHeaderData.receive.PRESET_MESSAGES.NOSHOW_5));

							return new uiMessage(UIMESSAGE_TYPE.LOGON,"");
						}
						else
                        {
                            return settingsTest;
                        }
						
					}
					else if (messageType.equals(messageHeaderTypes.receive.LOGOFF))
					{
                        /*
						if (createLogFile)
						{
							logDebug ("LOGOFF HANDLED", true);
						}
						*/
						//STATUSMANAGER.setAppState(_c, STATUSMANAGER.APP_STATE.LOGGED_OFF);
						return new uiMessage(UIMESSAGE_TYPE.LOGOFF, "You have been logged off...");
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_LOGOFF))
					{
                        /*
						if (createLogFile)
						{
							logDebug ("ACK LOGOFF HANDLED", true);
						}
						*/
						//STATUSMANAGER.setAppState(_c, STATUSMANAGER.APP_STATE.LOGGED_OFF);
						return new uiMessage(UIMESSAGE_TYPE.LOGOFF, "Logged Out");
					}
                    //this is "retrieve new settings from webserver" header
                    //which is obsolete. Settings now come from Socket Server
					/*else if(messageType.equals(messageHeaderTypes.receive.NEW_SETTINGS))
					{
						return new uiMessage(UIMESSAGE_TYPE.NEWSETTINGS, MessageParticles.getParamValue(messageHeaderData.receive.NEW_SETTINGS_VERSION));
					}*/
					else if (messageType.equals(messageHeaderTypes.receive.MESSAGE))
					{

						return handleDriverMessage(_c, MessageParticles);
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_FLAG_START))
					{
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_JOB, new cabdespatchJob(0d, true).pack());
						//UnfairMeterLocal.setCacheRetreivalStatus(false);

                        String meterRates = MessageParticles.getParamValue(messageHeaderData.receive.METER_RATES, "");
                        if(meterRates.equals(""))
                        {
                            //do nothing
                        }
                        else
                        {
                            UnfairMeterLocal.config(meterRates);
                        }

						return new uiMessage(UIMESSAGE_TYPE.SETONROUTE, "flag");
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_FLAG_END))
					{
						String requestBreak = MessageParticles.getParamValue(messageHeaderData.receive.FLAG, "0");
						return new uiMessage(UIMESSAGE_TYPE.SETCLEAR, requestBreak);
					}
					else if (messageType.equals(messageHeaderTypes.receive.JOBOFFER))
					{
                        //cdToast.showLong(_c, _message);
						return handleJobOffer(_c, MessageParticles);
					}
                    else if (messageType.equals(messageHeaderTypes.receive.JOB_AMMEND))
                    {
                        return handleJobAmmend(_c, MessageParticles);
                    }
					else if (messageType.equals(messageHeaderTypes.receive.ACK_JOBACCEPT))
					{
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_APP_STATE, STATUSMANAGER.APP_STATE.ON_JOB);
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.JOB_STATE, STATUSMANAGER.JOB_STATE.ON_ROUTE);
                        STATUSMANAGER.putBoolean(_c, STATUSMANAGER.STATUSES.JOB_ACCEPT_PENDING, false);

                        Boolean autoAccept = Boolean.valueOf(MessageParticles.getParamValue(messageHeaderData.receive.jobAutoAccept, "FALSE"));

						return new uiMessage(UIMESSAGE_TYPE.SETONROUTE, (autoAccept?"Y":"N"));
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_STP))
					{
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_APP_STATE, STATUSMANAGER.APP_STATE.ON_JOB);
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.JOB_STATE, STATUSMANAGER.JOB_STATE.SOON_TO_PICKUP);
						return new uiMessage(UIMESSAGE_TYPE.SETSTP, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_POB))
					{
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_APP_STATE, STATUSMANAGER.APP_STATE.ON_JOB);
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.JOB_STATE, STATUSMANAGER.JOB_STATE.PASSENGER_ON_BOARD);

						return new uiMessage(UIMESSAGE_TYPE.SETPOB, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_STC))
					{
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_APP_STATE, STATUSMANAGER.APP_STATE.ON_JOB);
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.JOB_STATE, STATUSMANAGER.JOB_STATE.SOON_TO_CLEAR);
                        String messageData = "N";

						Double newPrice = Double.valueOf(MessageParticles.getParamValue(messageHeaderData.receive.jobPRICE, "-1"));
						if(newPrice >= 0)
                        {
                            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);
                            j.updatePrice(newPrice);
                            STATUSMANAGER.setCurrentJob(_c, j);
                            messageData = "Y";

                            String priceString = MessageParticles.getParamValue(messageHeaderData.receive.jobPriceDisplay, "");
                            j.setPriceDisplay(priceString);

                            String dataMessage = MessageParticles.getParamValue(messageHeaderData.receive.DRIVERMESSAGE_TEXT, "");
                            if(!(dataMessage.equals("")))
                            {
                                STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.MODE.DEFAULT, TYPE.DATA_MESSAGE, BOX.INBOX, new DateTime().getMillis(), dataMessage, false, false);
                                m = STATUSMANAGER.DriverMessage.asDisplayed(m);
                                STATUSMANAGER.addDriverMessage(_c, m);
                            }

                            STATUSMANAGER.setCurrentJob(_c, j);
                        }

						return new uiMessage(UIMESSAGE_TYPE.SETSTC, messageData);
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_CLR))
					{
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_APP_STATE, STATUSMANAGER.APP_STATE.LOGGED_ON);
						String requestBreak = MessageParticles.getParamValue(messageHeaderData.receive.FLAG, "0");
						return new uiMessage(UIMESSAGE_TYPE.SETCLEAR, requestBreak);
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_NSH))
					{
						STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CURRENT_APP_STATE, STATUSMANAGER.APP_STATE.LOGGED_ON);
						return new uiMessage(UIMESSAGE_TYPE.SETCLEAR, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.ANTICHEATOVERRIDE))
					{
						return new uiMessage(UIMESSAGE_TYPE.ANTICHEATRECD, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.NOTLOGGEDIN))
					{
                        /*
						if (createLogFile)
						{
							logDebug ("NOT LOGGED IN HANDLED", true);
						}
						*/
						STATUSMANAGER.setAppState(_c, STATUSMANAGER.APP_STATE.LOGGED_OFF);
						return new uiMessage(UIMESSAGE_TYPE.NOTLOGGEDIN, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.WORKAVAILABLE))
					{
						return handleCarsAndWork(_c, MessageParticles);
					}
					else if(messageType.equals(messageHeaderTypes.receive.FUTUREJOBS))
					{
						return handleFutureJobs(_c, MessageParticles);
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_BREAK_START))
					{
						return new uiMessage(UIMESSAGE_TYPE.BREAKSTART, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_BREAK_END))
					{
						return new uiMessage(UIMESSAGE_TYPE.BREAKEND, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_REJECTAFTERACCEPT))
					{
						return new uiMessage(UIMESSAGE_TYPE.AKREJECTAFTERACCEPT, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.NEW_PLOT_VERSION))
					{
						return new uiMessage(UIMESSAGE_TYPE.PLOTUPDATE, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.PING))
					{
					    //do nothing
                        //this is legacy code that never really worked in the
                        //first place
						//return new uiMessage(UIMESSAGE_TYPE.PONG, "");
                        return new uiMessage(UIMESSAGE_TYPE.DO_NOTHING, "");
					}
                    else if (messageType.equals(messageHeaderTypes.receive.PONG))
                    {
                        //also do nothing
                        //this is just so that the SignalR network service
                        //will acknowledge that data has been received
                        return new uiMessage(UIMESSAGE_TYPE.DO_NOTHING, "");
                    }
					else if (messageType.equals(messageHeaderTypes.receive.RESET_PDA))
					{
						return new uiMessage(UIMESSAGE_TYPE.RESET_PDA, MessageParticles.getParamValue(messageHeaderData.receive.DRIVERNO));
					}
					else if (messageType.equals(messageHeaderTypes.receive.WORK_WAITING_AT_DESTINATION))
					{
						return new uiMessage(UIMESSAGE_TYPE.WORK_WAITING_AT_DESTINATION, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.ACK_PRICE))
					{
						return new uiMessage(UIMESSAGE_TYPE.IGNORE, "");
					}
					else if (messageType.equals(messageHeaderTypes.receive.JOB_TOTALS))
					{
						return handleJobTotals(_c, MessageParticles);
					}
                    else if (messageType.equals(messageHeaderTypes.receive.MOBILE_UPDATER))
                    {
                        //do nothing... windows mobile only message
                        return new uiMessage(UIMESSAGE_TYPE.UNKNOWN, _message);
                    }
                    else if (messageType.equals(messageHeaderTypes.receive.NEW_UPDATE_AVAILABLE))
                    {
                        return new uiMessage(UIMESSAGE_TYPE.UPDATE_AVAILABLE, "");
                    }
                    else if (messageType.equals(messageHeaderTypes.receive.NEW_UPDATE_REQUIRED))
                    {
                        return new uiMessage(UIMESSAGE_TYPE.UPDATE_REQUIRED, "");
                    }
                    else if (messageType.equals(messageHeaderTypes.receive.CIRCUIT_FEES))
                    {
                        Double circuitFees = Double.valueOf(MessageParticles.getParamValue(messageHeaderData.receive.CIRCUIT_FEES_OUTSTANDING));
                        String[] bits = MessageParticles.getParamValue(messageHeaderData.receive.CIRCUIT_FEES_ACCOUNTS).split(":");
                        Double uncliamedWork = Double.valueOf(bits[0]);

                        String message = "Your outstanding circuit fees are: £" + String.valueOf(circuitFees) + "\n\n";
                        message += "You have £" + bits[0] + " in unclaimed account work, leaving a total balance of £" + String.valueOf(circuitFees - uncliamedWork);

                        return new uiMessage(UIMESSAGE_TYPE.DRIVERMESSAGE, message);
                    }
                    else if (messageType.equals(messageHeaderTypes.receive.DATA_WAITING))
                    {
                        return new uiMessage(UIMESSAGE_TYPE.DATA_WAITING, "");
                    }
                    else if (messageType.equals(messageHeaderTypes.receive.NEW_SETTINGS))
                    {
                        uiMessage settingsTest = handleSettingsData(_c, MessageParticles.getParamValue(messageHeaderData.receive.NEW_SETTINGS_DATA));
                        return (settingsTest==null?new uiMessage(UIMESSAGE_TYPE.DO_NOTHING, ""):settingsTest);
                    }
					else
					{
						ErrorActivity.handleError(_c, new ErrorActivity.ERRORS.UNKNOWN_MESSAGE_TYPE(_message, _message));
						return new uiMessage(UIMESSAGE_TYPE.UNKNOWN, _message);
					}
					
				}
				else 
				{
					//this is a blank message...
					return new uiMessage(UIMESSAGE_TYPE.UNKNOWN, _message);
				}
			}

		}
		
		private uiMessage handleDriverMessage(Context _c, paramGroup Particles)
		{
			String message = Particles.getParamValue(messageHeaderData.receive.DRIVERMESSAGE_TEXT);
            message = EncryptionHandler.decryptString(message);
            message = HandyTools.Strings.parseNewLines(message);
            String telNo = Particles.getParamValue(messageHeaderData.receive.TELEPHONE_NUMBER);
            String confRequired = Particles.getParamValue(messageHeaderData.receive.CONFIRMATION_REQUIRED, "N");
            String messageID = Particles.getParamValue(messageHeaderData.receive.MESSAGE_ID, "-1");
            String messageMode = STATUSMANAGER.DriverMessage.MODE.DEFAULT.toString();
            messageMode = Particles.getParamValue(messageHeaderData.receive.DRIVERMESSAGE_MODE, messageMode);
            Integer iMessageMode = Integer.valueOf(messageMode);
            //CLAY
            //iMessageMode = STATUSMANAGER.DriverMessage.MODE.SPEAK;


            if((telNo.equals("")||(telNo.equals("0"))))
            {

                uiMessage ret;

                //STATUSMANAGER.addDriverMessage(new STATUSMANAGER.DriverMessage(TYPE.DATA_MESSAGE, BOX.INBOX, System.currentTimeMillis(), message));
                Boolean jobLess = false;
                if(message.startsWith("*JOBLESS*"))
                {
                    jobLess = true;
                    message = message.replace("*JOBLESS*", "");
                }

                STATUSMANAGER.DriverMessage d = null;
                if(Integer.valueOf(messageID) < 0)
                {
                    d =  new STATUSMANAGER.DriverMessage(iMessageMode, TYPE.DATA_MESSAGE, BOX.INBOX, System.currentTimeMillis(), message, confRequired.equals("Y"), jobLess);
                }
                else
                {
                    d = new STATUSMANAGER.DriverMessage(iMessageMode, TYPE.DATA_MESSAGE, BOX.INBOX, System.currentTimeMillis(), Integer.valueOf(messageID), message, confRequired.equals("Y"), jobLess);
                }
                if(iMessageMode == STATUSMANAGER.DriverMessage.MODE.SPEAK)
                {
                    //only spoken message
                    d = STATUSMANAGER.DriverMessage.asDisplayed(d);
                    ret = new uiMessage(UIMESSAGE_TYPE.SPOKENMESSAGE, message);
                }
                else
                {
                    ret = new uiMessage(UIMESSAGE_TYPE.DRIVERMESSAGE, message);
                }
                STATUSMANAGER.addDriverMessage(_c, d);

                return ret;
            }
            else
            {
                return new uiMessage(UIMESSAGE_TYPE.SEND_SMS, message, telNo);
            }
		}
		
		private uiMessage handleCarsAndWork(Context _c, paramGroup Particles)
		{

			STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.WORK_AVAILIABLE, Particles.getParamValue(messageHeaderData.receive.WORKAVAILABLE));
			STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.CARS_AVAILIABLE, Particles.getParamValue(messageHeaderData.receive.CARSAVAILABLE));
			
			String serverPlot = Particles.getParamValue(messageHeaderData.receive.PLOT);
			
			if(!(serverPlot.equals("")))
			{
				String myPlot = STATUSMANAGER.getCurrentLocation(_c).getPlot().getShortName();
				
				if(!(myPlot.equals(plot.ERROR_PLOT)))
				{
					if(!(myPlot.equals(serverPlot)))
					{
						BROADCASTERS.PlotUpdated(_c, false);
					}		
				}
			}
			
			return new uiMessage(UIMESSAGE_TYPE.CARSJOBSMESSAGE, Particles.getParamValue(messageHeaderData.receive.PLOT));
		}
		
		private uiMessage handleFutureJobs(Context _c, paramGroup Particles)
		{	
			STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.FUTURE_JOBS, Particles.getParamValue(messageHeaderData.receive.FUTUREJOBS));			
			return new uiMessage(UIMESSAGE_TYPE.FUTUREJOBSMESSAGE, Particles.getParamValue(messageHeaderData.receive.PLOT));
		}
		
		private uiMessage handleJobTotals(Context _c, paramGroup Particles)
		{
			DateTime t = new DateTime();
			SETTINGSMANAGER.SETTINGS.JOB_TOTALS_BASE_DATE.putValue(_c, t.toString());
			
			//I'm sure there's a way to do this with math
			//...but my head hurts
			
			//...of course the *simplest* way would have to have SS
			//provide the data in a simpler format!!!!
			switch(t.dayOfWeek().get())
			{
			case DateTimeConstants.MONDAY:
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.MONDAY), 
						true))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SUNDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SATURDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.FRIDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.THURSDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.WEDNESDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.TUESDAY), 
						false))) {break;}
				break;
			case DateTimeConstants.TUESDAY:
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.TUESDAY), 
						true))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.MONDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SUNDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SATURDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.FRIDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.THURSDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.WEDNESDAY), 
						false))) {break;}
				break;
			case DateTimeConstants.WEDNESDAY:
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.WEDNESDAY), 
						true))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.TUESDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.MONDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SUNDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SATURDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.FRIDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.THURSDAY), 
						false))) {break;}				
				break;
			case DateTimeConstants.THURSDAY:
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.THURSDAY), 
						true))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.WEDNESDAY), 
						false))) {break;}	
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.TUESDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.MONDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SUNDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SATURDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.FRIDAY), 
						false))) {break;}							
				break;
			case DateTimeConstants.FRIDAY:
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.FRIDAY), 
						true))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.THURSDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.WEDNESDAY), 
						false))) {break;}	
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.TUESDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.MONDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SUNDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SATURDAY), 
						false))) {break;}
				break;
			case DateTimeConstants.SATURDAY:
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SATURDAY), 
						true))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.FRIDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.THURSDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.WEDNESDAY), 
						false))) {break;}	
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.TUESDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.MONDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SUNDAY), 
						false))) {break;}			
				break;
			case DateTimeConstants.SUNDAY:
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SUNDAY), 
						true))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.SATURDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.FRIDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.THURSDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.WEDNESDAY), 
						false))) {break;}	
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.TUESDAY), 
						false))) {break;}
				if(!(SETTINGSMANAGER.putJobTotal(_c, 
						Particles.getParamValue(messageHeaderData.receive.MONDAY), 
						false))) {break;}
				
				break;
			}
			return new uiMessage(UIMESSAGE_TYPE.NEW_JOB_TOTALS, Particles.toString());
		}

        private uiMessage handleJobAmmend(Context _c, paramGroup Particles)
        {
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);
            //Boolean confRequired; // = Particles.getParamValue(messageHeaderData.receive.CONFIRMATION_REQUIRED).equals("Y");

            uiMessage ret;

            String price = Particles.getParamValue(messageHeaderData.receive.jobMeterPrice);
            if(price.equals(""))
            {
                //genuine job amend
                j=addOrUpdateJobDetails(_c, j, Particles, true);
                ret = new uiMessage(UIMESSAGE_TYPE.JOB_UPDATE, "Y");
            }
            else
            {
                //only meter price...
                j.setMeterPrice(Double.valueOf(price));
                ret = new uiMessage(UIMESSAGE_TYPE.PRICE_UPDATE, "");
            }


            STATUSMANAGER.setCurrentJob(_c, j);
            return ret;

        }
		private uiMessage handleJobOffer(Context _c, paramGroup Particles)
		{
		    String price = Particles.getParamValue(messageHeaderData.receive.jobPRICE, "0");
            cabdespatchJob j = new cabdespatchJob(Double.valueOf(price), false);
            j = addOrUpdateJobDetails(_c, j, Particles, false);
            j.setJobStatus(JOB_STATUS.UNDER_OFFER);

            String pendingStatus = Particles.getParamValue(messageHeaderData.receive.jobPendingStatus, "-1");
            if(pendingStatus.equals("4"))
            {
                j.setPendingStatus(JOB_STATUS.STP);
            }
            else if(pendingStatus.equals("5"))
            {
                j.setPendingStatus(JOB_STATUS.POB);
                //UnfairMeterLocal.setCacheRetreivalStatus(true);
            }
            else if(pendingStatus.equals("6"))
            {
                j.setPendingStatus(JOB_STATUS.STC);
            }
            else
            {
                //default
                j.setPendingStatus(JOB_STATUS.ON_ROUTE);
            }

            uiMessage ret;

            Boolean autoAccept = Boolean.valueOf(Particles.getParamValue(messageHeaderData.receive.jobAutoAccept, "FALSE"));
            if(autoAccept)
            {
                j.makeAutoAccept();
            }
            STATUSMANAGER.setCurrentJob(_c, j);
            if(autoAccept)
            {
                BROADCASTERS.AcceptJob(_c);
            }

            ret = new uiMessage(UIMESSAGE_TYPE.JOBWAITING, "");



            return ret;

		}

		private cabdespatchJob addOrUpdateJobDetails(Context _c, cabdespatchJob j, paramGroup Particles, Boolean _isUpdate)
        {
            j.setAccount(Particles.getParamValue(messageHeaderData.receive.jobACCOUNT, j.getAccount()));
            j.setComments(Particles.getParamValue(messageHeaderData.receive.jobCOMMENTS, j.getComments()));
            j.setDistance(Particles.getParamValue(messageHeaderData.receive.jobDISTANCE, j.getDistance()));
            j.setFromAddress(Particles.getParamValue(messageHeaderData.receive.jobADDRESS_FROM, j.getFromAddress()));
            j.setFromPlot(Particles.getParamValue(messageHeaderData.receive.jobPLOT_FROM, j.getFromPlot()));
            j.setFromLat(Particles.getParamValue(messageHeaderData.receive.fromLat, j.getFromLat()));
            j.setFromLon(Particles.getParamValue(messageHeaderData.receive.fromLon, j.getFromLon()));
            j.setID(Particles.getParamValue(messageHeaderData.receive.jobID, j.getID()));
            j.setMode(Particles.getParamValue(messageHeaderData.receive.jobMODE, j.getMode()));
            j.setName(Particles.getParamValue(messageHeaderData.receive.jobNAME, j.getName()));
            j.setPriceDisplay(Particles.getParamValue(messageHeaderData.receive.jobPriceDisplay, j.getPriceDisplay()));

            if(_isUpdate)
            {
                String currentPrice = j.getPrice();
                String newPrice = Particles.getParamValue(messageHeaderData.receive.jobPRICE, j.getPrice());
                if(!(currentPrice.equals(newPrice)))
                {
                    j.updatePrice(newPrice);
                }

            }


            j.setTime(Particles.getParamValue(messageHeaderData.receive.jobTIME, j.getTime()));
            j.setToAddress(Particles.getParamValue(messageHeaderData.receive.jobADDRESS_TO, j.getToAddress()));
            j.setToPlot(Particles.getParamValue(messageHeaderData.receive.jobPLOT_TO, j.getToPlot()));
            j.setToLat(Particles.getParamValue(messageHeaderData.receive.toLat, j.getToLat()));
            j.setToLon(Particles.getParamValue(messageHeaderData.receive.toLon, j.getToLon()));
            j.setVehcileType(Particles.getParamValue(messageHeaderData.receive.jobVEHICLETYPE, j.getVehicleType()));
            j.setFlightNo(Particles.getParamValue(messageHeaderData.receive.jobFlightNo, j.getFlightNo()));

            String meterRates = Particles.getParamValue(messageHeaderData.receive.METER_RATES, "");
            UnfairMeterLocal.config(meterRates);

            //compatibility with old version of socket server
            String notesRequiredString = Particles.getParamValue(messageHeaderData.receive.jobNotesRequired, "-1");
            Boolean notesRequired;
            if(notesRequiredString.equals("-1"))
            {
                notesRequired = (SETTINGSMANAGER.SETTINGS.JOB_DRIVER_NOTES_MINIMUM_SIZE.parseInteger(_c) >= 0);
            }
            else
            {
                notesRequired = (notesRequiredString.equals("0")?false:true);
            }
            j.setNotesRequired(notesRequired);

            if(SETTINGSMANAGER.SETTINGS.ALLOW_DRIVER_TO_CALL_CUSTOMER.parseBoolean(_c).equals(true))
            {
                j.setTelephoneNumber(Particles.getParamValue(messageHeaderData.receive.TELEPHONE_NUMBER, j.getTelephoneNumber()));
            }
            else
            {
                j.setTelephoneNumber(Settable.NOT_SET);
            }

            String amountPrepaid = Particles.getParamValue(messageHeaderData.receive.jobAmountPrePaid, j.getAmountPrepaid());
            Double dAmountPrePaid = (amountPrepaid.equals("")?0d:Double.valueOf(amountPrepaid));
            j.setAmountPrePaid(dAmountPrePaid);

            j.setBookingFee(Double.valueOf(Particles.getParamValue(messageHeaderData.receive.jobBookingFee, "0")));

            String showAreaOnOffer = Particles.getParamValue(messageHeaderData.receive.jobSHOWZONEONOFFER, String.valueOf(j.getShowZoneOnJobOffer()));
            boolean showArea = (showAreaOnOffer.equals("1")?true:false);
            j.setShowZoneOnJobOffer(showArea);

            j.clearVias();
            for (String viaHeader:messageHeaderData.receive.viaDetails)
            {

                String via = Particles.getParamValue(viaHeader);

                if (!(via.equals("")))
                {
                    String addressLine = via;

                    /*
                    via address line headers are [wx]... ie [w0] [w1] [w2]
                    via latlons are [wwx]... ie [ww0] [ww1] [ww2]

                    so to find the corresponding lat header for
                    address header we are currently at, we prefix an additional "w"
                 */
                    String latLon = Particles.getParamValue("w" + viaHeader);
                    String[] eles = latLon.split(":");

                    String vLat = cabdespatchJob.JobDetailLocation.DETAIL_UNSET;
                    String vLon = cabdespatchJob.JobDetailLocation.DETAIL_UNSET;

                    if(eles.length == 2)
                    {
                        vLat = eles[0];
                        vLon = eles[1];
                    }

                    j.addVia(new cabdespatchJob.JobDetailLocation(via, vLat, vLon));
                }
            }

            return j;
        }
		
		private void handleMiscBits(Context _c, paramGroup Particles)
		{
			String trap = Particles.getParamValue(messageHeaderData.receive.TRAP);
			String individualTrap = Particles.getParamValue(messageHeaderData.receive.INDIVIDUALTRAP);
			String noNoShow = Particles.getParamValue(messageHeaderData.receive.NO_NOSHOW);
			String safetyPanel = Particles.getParamValue(messageHeaderData.receive.SAFETY_PANEL);
			
			if(noNoShow.equals("1"))
			{
				STATUSMANAGER.putBoolean(_c, STATUSMANAGER.STATUSES.NO_NO_SHOW , true);	
			}
			else if(noNoShow.equals("0"))
			{
				STATUSMANAGER.putBoolean(_c, STATUSMANAGER.STATUSES.NO_NO_SHOW , false);
			}
			
			
			
			if((trap.equals(""))||(individualTrap.equals("")))
			{
				//do nothing
			}
			else
			{
				STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.TRAP_INDIVIDUAL, individualTrap);
				STATUSMANAGER.putString(_c, STATUSMANAGER.STATUSES.TRAP_OVERALL, trap);
				
				STATUSMANAGER.setStatusBarText(_c);
			}
			
			
			/* CLAY
			if (!(trap.equals("")))
			{ _s.setTrap(trap); }
			
			if (!(individualTrap.equals("")))
			{ _s.setIndividualTrap(individualTrap); }
			
			if (!(noNoShow.equals("")))
			{ _s.setNoNoShow((noNoShow.equals("1")?true:false)); }
			
			if (!(safetyPanel.equals("")))
			{
				_s.setSafetyPanelSpeedThesh((safetyPanel.equals("1")?5:-1)); //set the threshold to 5mph if the panel is activated, else set to -1
			}
			*/
		}
		
	}

	
}
