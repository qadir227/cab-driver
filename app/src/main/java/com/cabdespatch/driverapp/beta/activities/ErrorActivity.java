package com.cabdespatch.driverapp.beta.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity.ERRORS.REPORTABLE_ERROR;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
//import com.splunk.mint.Mint;

import org.apache.http.util.ExceptionUtils;

import java.util.HashMap;

public class ErrorActivity extends Activity
{
	public static final String _ERROR = "error";
	
	public static class ERRORS
	{
		public static abstract class ERROR
		{
			private boolean oSendBugReport;
			protected String oErrorDetail;
			private Exception oException;
			
			public ERROR(String _errorDetail, Boolean _sendBugReport)
			{
				this.oErrorDetail = _errorDetail;
				this.oSendBugReport = _sendBugReport;
				this.oException = null;
			}
			
			public ERROR(Exception _exception, Boolean _sendBugReport)
			{
				this.oErrorDetail = null;
				this.oSendBugReport = _sendBugReport;
				this.oException = _exception;
			}
			
			public Exception getException()
			{
				return this.oException;
			}
			
			@Override
			public String toString()
			{
				if(this.oException==null)
				{
					return this.oErrorDetail;
				}
				else
				{
					return this.oException.toString();
				}
			}
			
			public boolean isReportable()
			{
				return this.oSendBugReport;
			}
			
			public String getErrorDetail()
			{
				return this.oErrorDetail;
			}

		}
		
		public static class NON_REPORTABLE_ERROR extends ERROR
		{
			public NON_REPORTABLE_ERROR(String _errorDetail)
			{
				super(_errorDetail, false);
			}
			
			public NON_REPORTABLE_ERROR(Exception ex)
			{
				super(ex, false);
			}

	
		}
		
		public static abstract class REPORTABLE_ERROR extends ERROR
		{
			protected String oTag;
						
			abstract void dothrow() throws Exception;
			
			public void dothrow_useme() throws Exception
			{
				if (this.getException()==null)
			/*	{
                    Mint.addExtraData("DETAIL", this.oErrorDetail);
                    Mint.addExtraData("TAG", this.oTag);
					this.dothrow();
					Mint.removeExtraData("DETAIL");
					Mint.removeExtraData("TAG");
				}
				else */
				{
					throw this.getException();
				}
			}
			
			public REPORTABLE_ERROR(String _tag, String _errorDetail)
			{
				super(_errorDetail, true);
                this.oErrorDetail = _errorDetail;
				this.oTag = _tag;
			}
			
			public REPORTABLE_ERROR(String _tag, Exception ex)
			{
				super(ex, true);
				this.oTag = _tag;
			}
			
			public String getErrorTag()
			{
				return this.oTag;
			}
		}
		
		public static class UNHANDLED_ACTIVITY_IN_BROADCAST_HANDLER extends REPORTABLE_ERROR
		{
			public class UnhandledActivityInBroadcastHandlerException extends Exception{}
			
			public UNHANDLED_ACTIVITY_IN_BROADCAST_HANDLER(String _activity)
			{
				super("Unhandled Activity in BrodcastHandler.java", _activity);
			}

			@Override
			protected void dothrow() throws Exception
			{				
				throw new UnhandledActivityInBroadcastHandlerException();
			}
		}
		
		public static class INAPPROPRIATE_JOB_STATE_ON_JOB_BUTTON_PRESS extends REPORTABLE_ERROR
		{
			@SuppressWarnings("serial")
			public class InappropriateJobStateOnJobButtonPressException extends Exception {}
			
			public INAPPROPRIATE_JOB_STATE_ON_JOB_BUTTON_PRESS()
			{
				super("Job Button Pressed with Inappropriate State","Job Button Pressed with Inappropriate State");
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new InappropriateJobStateOnJobButtonPressException();
			}
		}
		
		public static class INVALID_RUN_MODE extends REPORTABLE_ERROR
		{			
			public class InvalidRunModeException extends Exception {}
			
			public INVALID_RUN_MODE(String _runmode)
			{
				super("Invalid Run Mode", _runmode);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new InvalidRunModeException();
			}
		}
		
		public static class CLASS_TYPE_NOT_FOUND_FOR_STATUS extends REPORTABLE_ERROR
		{
			public class ClassTypeNotFoundForStatusException extends Exception {}
			
			public CLASS_TYPE_NOT_FOUND_FOR_STATUS(String _status)
			{
				super("NO class type found for status", _status);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new ClassTypeNotFoundForStatusException();
			}
		}
		
		public static class ZONE_DATA_SKIPPED extends REPORTABLE_ERROR
		{
			public class ZoneDataSkippedException extends Exception{}
			
			public ZONE_DATA_SKIPPED(String _zone)
			{
				super("Zone data skipped", _zone);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new ZoneDataSkippedException();
			}
		}
		
		public static class UNHANDLED_APP_STATE extends REPORTABLE_ERROR
		{
			public class UnhandledAppStateException extends Exception {}
			
			public UNHANDLED_APP_STATE(String _appstate)
			{
				super("Unhandled AppState", _appstate);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new UnhandledAppStateException();
			}
		}
		
		public static class UNHANDLED_BROADCAST_ACTION extends REPORTABLE_ERROR
		{
			public class UnhandledBroadcastActionException extends Exception{}
			
			public UNHANDLED_BROADCAST_ACTION(String _action)
			{
				super("Unhandled broadcast action", _action);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new UnhandledBroadcastActionException();
			}
		}


		public static class ZONE_FILE_ERROR extends REPORTABLE_ERROR
		{
			public class ZoneFileErrorException extends Exception {}
			
			public ZONE_FILE_ERROR(Exception ex)
			{
				super("Zone File Error", ex);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new ZoneFileErrorException();
			}
		}
		
		public static class UNHANDLED_USER_REQUEST extends REPORTABLE_ERROR
		{
			public class UnhandledUserRequestException extends Exception {}
			
			public UNHANDLED_USER_REQUEST(String _request)
			{
				super("Unhandled user request", _request);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new UnhandledUserRequestException();
			}
		}
		
		public static class MISFORMED_SETTING_LAYOUT extends REPORTABLE_ERROR
		{
			public class MisformedSettingLayoutException extends Exception{}
			
			public MISFORMED_SETTING_LAYOUT(String _data)
			{
				super("Layout must contain an element with the id 'settingstitle'", _data);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new MisformedSettingLayoutException();
			}
		}
		
		public static class UPDATE_JOB_STATUS_BUTTON_CALLED_WITH_INCORRECT_STATUS extends REPORTABLE_ERROR
		{
			public class UpdateJobStatusButtonCalledWithIncorrectStatusException extends Exception {}
			
			public UPDATE_JOB_STATUS_BUTTON_CALLED_WITH_INCORRECT_STATUS(cabdespatchJob _j)
			{
				super(_j.toString(), _j.getJobStatus().toString());
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new UpdateJobStatusButtonCalledWithIncorrectStatusException();
			}
		}
		
		public static class NO_ACTIVITY_FOUND_TO_RESUME extends REPORTABLE_ERROR
		{
			public class NoActivityFoundToResumeException extends Exception {}
			
			public NO_ACTIVITY_FOUND_TO_RESUME(String _activityrequested)
			{
				super("No Activity found To Resume", _activityrequested);
			}
			
			@Override
			protected void dothrow() throws Exception
			{				
				throw new NoActivityFoundToResumeException();
			}
		}
		
		public static class INVALID_JOB_TIME_STRING extends REPORTABLE_ERROR
		{

			public class InvalidJobTimeStringException extends Exception {}
			
			public INVALID_JOB_TIME_STRING(String _tag, String _errorDetail)
			{
				super(_tag, _errorDetail);
			}

			@Override
			void dothrow() throws Exception
			{
				throw new InvalidJobTimeStringException();
				
			}
			
		}
		
		public static class TEST_ERROR extends REPORTABLE_ERROR
		{

			public class TestErrorException extends Exception {}
						
			public TEST_ERROR(String _tag, String _errorDetail)
			{
				super(_tag, _errorDetail);
			}

			@Override
			void dothrow() throws Exception
			{
				throw new TestErrorException();				
			}
			
		}
		
		public static class UNKNOWN_MESSAGE_TYPE extends REPORTABLE_ERROR
		{
			public class UnkownNetworkMessageException extends Exception{}
			
			public UNKNOWN_MESSAGE_TYPE(String _tag, String _errorDetail)
			{
				super(_tag, _errorDetail);
			}
			
			@Override
			void dothrow() throws Exception
			{
				throw new UnkownNetworkMessageException();
			}
		}
				
				
		
	}
	
	
	public void onCreate(Bundle savedState)
	{
		super.onCreate(savedState);
		
		Intent i = this.getIntent();
		String error = i.getStringExtra(ErrorActivity._ERROR);
		DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR,"ERROR", error);
		Toast.makeText(this, error, Toast.LENGTH_LONG).show();
		this.finish();
	}
	
	private static final void showError(final Context c, final String error)
	{
		//Intent i = new Intent(c, ErrorActivity.class);
		//i.putExtra(ErrorActivity._ERROR, error);
		//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//c.startActivity(i);
		//Looper.prepare();
		
		Handler h = new Handler(c.getMainLooper());
		h.post(new Runnable() {

                   @Override
                   public void run() {
                       Toast.makeText(c, error, Toast.LENGTH_LONG).show();
                   }
               }
        );
		
		//SOUNDMANAGER.speak(c, error);
	
	}

    public static final void genericReportableError(Exception _ex, String _errorDescription)
    {
        HashMap<String, String> details = new HashMap<String, String>();
        details.put("*DETAILS", _errorDescription);
        genericReportableError(_ex, details);
    }

    public static final void genericReportableError(String _errorDescription, HashMap<String, String> _details)
    {
        class GenericErrorException extends Exception{}

        /*   try
        {
            throw new GenericErrorException();
        }
        catch (Exception e)
     {
            _details.put("Error Description", _errorDescription);
            Mint.logExceptionMap(_details, e);
            e.printStackTrace();
        }*/
    }

	public static final void genericReportableError(Exception ex, HashMap<String, String> _details)
	{
		genericReportableError(ex.getStackTrace().toString(), _details);
	}

	public static final void genericReportableError(String _errorDescription)
	{
		HashMap<String, String> _details = new HashMap<String, String>();
		_details.put("Error Description", _errorDescription);

		genericReportableError(_errorDescription, _details);
	}

    public static final void genericReportableError(String _errorDescription, Exception _ex)
    {
        HashMap<String, String> _details = new HashMap<String, String>();
        _details.put("Error Description", _errorDescription);

        genericReportableError(_errorDescription, _details);
    }

    public static final void handleError(Context c, ERRORS.ERROR error)
	{

		//Mint.initAndStartSession(c, "b65adb00");

		//ErrorActivity.showError(c, error.toString());

		if(error.isReportable())
		{
			REPORTABLE_ERROR r = (REPORTABLE_ERROR) error;
			try
			{

				r.dothrow_useme();
			} 
			catch (Exception e)
			{
				//Mint.logExceptionMessage(r.getErrorTag(), r.getErrorDetail(), e);
				e.printStackTrace();
			}
		}
	}
}
