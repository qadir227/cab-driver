package com.cabdespatch.driverapp.beta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import androidx.appcompat.widget.AppCompatTextView;

import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;
import com.cabdespatch.driverapp.beta.activities.POD;
import com.cabdespatch.driverapp.beta.activities.PanicActivity;
import com.cabdespatch.driverapp.beta.activities.PlotList;
import com.cabdespatch.driverapp.beta.activities.ResetApp;
import com.cabdespatch.driverapp.beta.activities2017.ComposeMessage;
import com.cabdespatch.driverapp.beta.activities2017.DocumentScanner;
import com.cabdespatch.driverapp.beta.activities2017.DriverPaymentActivity;
import com.cabdespatch.driverapp.beta.activities2017.JobTotals;
import com.cabdespatch.driverapp.beta.activities2017.PermissionActivity;
import com.cabdespatch.driverapp.beta.activities2017.Settings;
import com.cabdespatch.driverapp.beta.activities2017.UpdateActivity;
import com.cabdespatch.driverapp.beta.activities2017.ViewJobHistory;
import com.cabdespatch.driverapp.beta.activities2017.ViewMessages;
import com.cabdespatch.driverapp.beta.cabdespatchJob.JOB_STATUS;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_GetPrice;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._BUTTON;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._SHOWBUTTONS;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_NavLocation;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_NavLocation.RESULT;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_Update;
import com.cabdespatch.driverapp.beta.services.DataService;
//import com.splunk.mint.Mint;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Globals
{
		
	private static Bitmap BACKGROUND_IMAGE = null;

    public static class MENU_TAGS
    {
        public static final String NONE = "_NONE";
        public static final String EXIT = "_EXIT";
        public static final String GET_PRICE = "GET_PRICE";
        public static final String NEW_ACTIVITY = "_NEW_ACTIVITY";
    }

	public static Bitmap getBackgroundImage(Context _c)
	{
		if(Globals.BACKGROUND_IMAGE==null)
		{
			InputStream s = _c.getResources().openRawResource(R.raw.n_appbackground);
			Globals.BACKGROUND_IMAGE = BitmapFactory.decodeStream(s);
		}
		return Globals.BACKGROUND_IMAGE;
	}
	
	public interface OnGlobalErrorHandler
	{
		public void OnError(String _error, Exception _ex);
	}
	
	public static class ZoneFileResult
	{
		String oZoneFile;
		Boolean oSucess;
		Exception oException;
		
		ZoneFileResult(String _zoneFile)
		{
			
			if (_zoneFile.equals("n") || _zoneFile.equals(""))
			{
				this.oSucess = false;
				this.oException=new Exception ("Server returned: " + _zoneFile);
			}
			else
			{
				this.oZoneFile = _zoneFile;			
				this.oSucess = true;	
			}
			
		}
		
		ZoneFileResult(Exception _exception)
		{
			this.oException = _exception;
			this.oSucess = false;
		}
		
		public Boolean wasSuccessful() { return this.oSucess; }
		public String getZoneFile() { return this.oZoneFile; }
		public Exception getException() { return this.oException; }
		
	}
	
	private OnGlobalErrorHandler oErrorHandler;	

	public interface UnarmPanicListener
	{
		public void unarmPanic();
	}
	
	
	private String DataFolder;


	public static String getCabDespatchCheckinUrl(Context _c)
	{
		//return "https://pdamirror2023.conveyor.cloud/";
		return getCabDespatchDataUrl() + "mirror23/";
	}

	public static String getCabDespatchDataUrl()
	{
		return "https://www.cabdespatch.com/android/";
	}

	public static  _WEBTOOLS WebTools;
	public _APP App;
	
	public static OnTouchListener FlipperTouch()
	{
		return new View.OnTouchListener()
		{
	
			float lastX = -1;
			
			
			@Override
			public boolean onTouch(View v, MotionEvent touchevent)
			{
				
				ViewFlipper flip = (ViewFlipper) v;
				
				if(flip.getChildCount()>1)
				{
					switch (touchevent.getAction())
					{
					case MotionEvent.ACTION_DOWN:
						lastX = touchevent.getX();
						return true;
					case MotionEvent.ACTION_UP:
						float currentX = touchevent.getX();
						
											
						if (lastX < currentX)
						{
							flip.setInAnimation(v.getContext(), R.anim.in_from_left);
							flip.setOutAnimation(v.getContext(), R.anim.out_to_right);
							flip.showPrevious();
							this.lastX = -1;
							return true;
						}

						if (lastX > currentX)
						{
							flip.setInAnimation(v.getContext(), R.anim.in_from_right);
							flip.setOutAnimation(v.getContext(), R.anim.out_to_left);
							flip.showNext();
							this.lastX = -1;
							return true;
						}
						break;
					}	
		
					return false;	
				}
				
				return false;

			}
		};
	}

	
	public Globals(AssetManager _a, Resources _r, TelephonyManager _t, OnGlobalErrorHandler _errorHandler)
	{
		this.DataFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cabdespatch/";

		//this.FileTools = new _FILETOOLS();
		this.WebTools = new _WEBTOOLS();
		
		this.oErrorHandler = _errorHandler; 
		this.App = new _APP();
	}
	
	public static void registerBugHandler(Context _c)
	{
		if((PermissionActivity.checkPermission(_c, Manifest.permission.READ_PHONE_STATE)))
		{
			TelephonyManager tm = (TelephonyManager)_c.getSystemService(Context.TELEPHONY_SERVICE);

	//		Mint.initAndStartSession(_c, "b65adb00");
	//		Mint.addExtraData("Company ID", SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(_c));
	//		Mint.addExtraData("Driver Call Sign", SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(_c));
	//		Mint.addExtraData("IMEI", Globals.CrossFunctions.getDeviceIdentifier_wasIMEI(_c));
		}
	}
	
//	public static boolean activateDevice(Context _c, String _companyId, String _driverNo, String _password)
//	{

	//see package com.cabdespatch.libcabapiandroid.Apis.Activation.Activation

//		try
//		{
//			String result = Globals.WebTools.getWebPage(Globals.getCabDespatchCheckinUrl(_c) + "index.aspx" + Globals.WebTools.getAuthQueryString(_c, _companyId, _driverNo, _password) + Globals.rot13("&method=activate"));
//			if (result.equals("p"))
//			{
//				return true;
//			}
//			else
//			{
//				return false;
//			}
//		}
//		catch (Exception ex)
//		{
//			return false;
//		}
//
//	}
	

	public static String getMessagesFileLocation(Context _c)
	{
		return _c.getExternalFilesDir(null).getAbsolutePath() + "/msg.cfg";
	}

    public static String getUpdateStorageLocation(Context _c)
    {
      return  _c.getExternalFilesDir(null).getAbsolutePath();
    }

    /*
    public static File getDownloadsLocation(Context _c)
	{
		return _c.getFilesDir(Environment.DIRECTORY_DOWNLOADS);
		//return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
	}*/

	public static String getSoundsFileLocation(Context _c)
	{
		return _c.getExternalFilesDir(null).getAbsolutePath() + "/sound/";
	}
	public static String getZoneSoundsFileLocation(Context _c)
	{
		return _c.getExternalFilesDir(null).getAbsolutePath() + "/sound/zone/";
	}
	public static String getNewMessageSoundsFileLocation(Context _c)
	{
		return _c.getExternalFilesDir(null).getAbsolutePath() + "/sound/newmessage/";
	}
	
	public static ZoneFileResult getZoneFile(Context _c, String _companyID, String _driverNo)
	{
		String s = "";
		
		Long sess = System.currentTimeMillis();
		
		String url = Globals.getCabDespatchDataUrl() + "zonefile/index.aspx" + WebTools.getAuthQueryString(_c, _companyID, _driverNo) + Globals.rot13("&method=get") + "&session=" + String.valueOf(sess);
		try 
		{
			s = Globals.WebTools.getWebPage(url) ;
		}
		catch (Exception ex) 
		{
			return new ZoneFileResult(ex);
		}
		
		return new ZoneFileResult(s);
		
	}
	
	public static String getJobOfferSoundsFileLocation(Context _c)
	{
		return _c.getExternalFilesDir(null).getAbsolutePath() + "/sound/joboffer/";
	}

 	public static String rot13(String s) 
	{
		StringBuilder out = new StringBuilder(s.length());
		char[] ca = s.toCharArray();
		for (char c : ca)  
		{
			if (c >= 'a' && c <= 'm') c += 13;
			else if (c >= 'n' && c <= 'z') c -= 13;
			else if (c >= 'A' && c <= 'M') c += 13;
			else if (c >= 'N' && c <= 'Z') c -= 13;
			out.append(c);
		}
		return out.toString();
	}
	
	
	public static class FileTools
	{

		public static List<String> getFileEntries(String _absoluteDirectoryPath)
		{
			List<String> entries = new ArrayList<String>();
			
			File f = new File(_absoluteDirectoryPath);
			
			if (f.isDirectory())
			{
				File[] fileList = f.listFiles();
				
				for (File e : fileList)
				{
					if (e.isFile())
					{
						entries.add(e.getName());
					}
				}
			}
			
			return entries;
			
		}
		public static String readFileToString(String _absoluteFileLocation) throws IOException
		{
			File file = new File(_absoluteFileLocation);
			InputStream in = new FileInputStream(file);
			byte[] b  = new byte[(int) file. length()];
			int len = b.length;
			int total = 0;

			while (total < len)
			{
			  int result = in.read(b, total, len - total);
			  if (result == -1) 
			  {
			    break;
			  }
			  total += result;
			}
			
			in.close();
			
			return new String(b);
		}


		  
		public static Boolean writeStringToFile(String _data, String _absoluteFilePath)
		{
			
			BufferedWriter out1;
			
			try 
			{
				out1 = new BufferedWriter(new FileWriter(_absoluteFilePath));
				out1.write(_data);
				out1.close();
			}
			catch (IOException e)
			{
				out1 = null;
				return false;
			}
			
			return true;
		}
		
		public static Boolean extractZip(String _absoluteZipFileLocation, String _absoluteDestination)
		{
		
			try 
			{
				ZipFile zipFile = new ZipFile(_absoluteZipFileLocation);
				Enumeration<?> enu = zipFile.entries();
				while (enu.hasMoreElements()) {
					ZipEntry zipEntry = (ZipEntry) enu.nextElement();

					String name = zipEntry.getName();
				
					File file = new File(_absoluteDestination + "/" + name);
					if (name.endsWith("/")) {
						file.mkdirs();
						continue;
					}

					File parent = file.getParentFile();
					if (parent != null) {
						parent.mkdirs();
					}

					InputStream is = zipFile.getInputStream(zipEntry);
					FileOutputStream fos = new FileOutputStream(file);
					byte[] bytes = new byte[1024];
					int length;
					while ((length = is.read(bytes)) >= 0) 
					{
						fos.write(bytes, 0, length);
					}
					is.close();
					fos.close();

				}
				zipFile.close();
				return true;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return false;
			}
		}
	}

	public class _WEBTOOLS
	{
		private boolean legacyHttp = true;
		public void launchWebIntent(Context _c, String _url)
		{
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(_url));
			Globals.StartActivity(_c, i);
		}

		private ByteArrayOutputStream getDataFromUrl(String _url) throws IOException
		{
			ByteArrayOutputStream bout = null;
			BufferedInputStream buff = null;
			try
			{
				URL url = new URL(_url);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				try
				{
					buff = new BufferedInputStream(urlConnection.getInputStream());
					bout = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int length;
					while ((length = buff.read(buffer)) != -1) {
						bout.write(buffer, 0, length);
					}

				}
				finally
				{
					urlConnection.disconnect();
				}
			}
			catch (MalformedURLException ex)
			{
				//do nothing?
			}

			return bout;
		}



		public Boolean getFileHTTP(String _destinationFile, String _url)
		{
			if (legacyHttp) { return  getFileHTTPLegacy(_destinationFile, _url);}
			else { return  getFileHTTPModern(_destinationFile, _url);}

		}

		public Boolean getFileHTTPModern(String _destination, String _url)
		{
			Boolean ret = false;
			try
			{
				ByteArrayOutputStream out = getDataFromUrl(_url);

				FileOutputStream fos = null;
				try
				{
					fos = new FileOutputStream(new File(_destination));
					out.writeTo(fos);
				}
				catch(IOException ioe)
				{
					// Handle exception here
					ioe.printStackTrace();
				}
				finally
				{
					fos.close();
					out.close();

				}

				ret = true;
			}
			catch (IOException e)
			{
				ret = false;
			}

			return ret;
		}

		public Boolean getFileHTTPLegacy(String _destination, String _url)
		{
			try
			{
				new DefaultHttpClient().execute(new HttpGet(_url)).getEntity().writeTo(new FileOutputStream(new File(_destination)));
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				return false;
			}

			return true;
		}

		public String getWebPage(String _url) throws IOException
		{
			if (legacyHttp)
			{
				return getWebPageLegacy(_url);
			}
			else
			{
				return getWebPageModern(_url);
			}
		}

		public String getWebPageModern(String _url) throws MalformedURLException, IOException
		{
			ByteArrayOutputStream s = getDataFromUrl(_url);
			String result = s.toString("UTF-8");

			s.close();
			return result;
		}

		public String getWebPageLegacy(String _url) throws IOException
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(_url);
			String html = "ERR";
			
				HttpResponse response = client.execute(request);
					
				InputStream in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder str = new StringBuilder();
				String line = null;
				while((line = reader.readLine()) != null)
				{
				    str.append(line);
				}
				in.close();
				html = str.toString();

			
			return html;
		}



		public String getAuthQueryString(Context _c, String _companyId, String _driverNo)
		{
			TelephonyManager t = (TelephonyManager) _c.getSystemService(Context.TELEPHONY_SERVICE);
			
			String s = "?";
			s += ("i=" + CrossFunctions.getDeviceIdentifier_wasIMEI(_c));
			s += ("&s=" + CrossFunctions.getDeviceIdentifier_wasSIM(_c));
			s += ("&c=" + _companyId);
			s += ("&d=" + _driverNo);
			s += ("&a=" + STATUSMANAGER.getAppVersionNumber(_c));
			s += ("&l=" + URLEncoder.encode(Build.MANUFACTURER + " " + Build.MODEL));
            s += ("&x=" + t.getLine1Number());
			s += ("&p=none");
			s += ("&src=11"); //source is from pda (ie not from web site)
			s = Globals.this.rot13(s);
			//caches! urgh!!!
			s += ("&session=" + String.valueOf(new Random().nextInt(9999)) + String.valueOf(SystemClock.uptimeMillis()) + String.valueOf(new Random().nextInt(9999)));
			String fireId = STATUSMANAGER.getFirebaseInstanceId(_c);
			if(!(fireId.equals("")))
			{
				s+= ("&skyidfb=" + URLEncoder.encode(fireId));
			}
			
			return (s);
		}
		
		public String getAuthQueryString(Context _c, String _companyId, String _driverNo, String _password)
		{								
			//remove blank password!
			return (getAuthQueryString(_c, _companyId, _driverNo) + Globals.this.rot13(("&p=" + _password))).replace(Globals.this.rot13("&p=none"), "");
		}
		
		public String getAuthQueryString(Context _c, String _companyId, String _driverNo, String _password, String _settingsVersion)
		{
			//remove blank password!
			return (getAuthQueryString(_c, _companyId, _driverNo) + Globals.this.rot13(("&p=" + _password) + "&v=" + _settingsVersion)).replace(Globals.this.rot13("&p=none"), "");				
		}
		
		
		public String getDataQueryString(Context _c, String _companyId, String _driverNo, String _password)
		{
			String s = "?";
			
			s += ("c=" + _companyId);
			s += ("&d=" + _driverNo);
			s += ("&p=" + _password);
			s = Globals.this.rot13(s);
			//cache! urgh!!!
			s += ("&session=" + String.valueOf(SystemClock.uptimeMillis()) + String.valueOf(new Random().nextInt(500)));
			
			return (s);
		}
	}
	
	public class _APP
	{
		public int errorStatus = 0;
		public final String Name = "Cab Despatch Android";
		public final String MarketURL = "";
		public String VersionNo = "10";
		public Boolean DownloadedFromMarket = false;
		public final String BrandingStatement = "www.cabdespatch.com";
		
	}

    public static void StartActivity(Context _c, Intent _i)
    {
        _i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        _c.startActivity(_i);
    }


	public static class CrossFunctions
	{
		public static String getDeviceIdentifier_wasIMEI(Context _c)
		{
			String notIMEI = STATUSMANAGER.STATUSES.MOCK_IMEI.getValue(_c);
			if(notIMEI.equals(STATUSMANAGER.STATUSES.MOCK_IMEI.getDefaultValue()))
			{
				String androidIdHex = android.provider.Settings.Secure.getString(_c.getContentResolver(),
					android.provider.Settings.Secure.ANDROID_ID);

				//androidIdHex = "a4z5c06";

				String androidId = "";
				try
				{
					androidId=String.valueOf(Long.parseLong(androidIdHex, 16));
				}
				catch (Exception ex)
				{
					String[] validChars= {"0","1","2","3","4","5","6","7","8","9"};
					StringBuilder newId = new StringBuilder("");
					for(char c:androidIdHex.toCharArray())
					{
						for(String vc:validChars)
						{
							if (vc.equals(String.valueOf(c)))
							{
								newId.append(c);
								break;
							}
						}

					}
					androidId = newId.toString();
				}

				while(androidId.length() < 16)
				{
					androidId += "9";
				}

				notIMEI = "99" + androidId.substring(0, 14);

				STATUSMANAGER.STATUSES.MOCK_IMEI.putValue(_c, notIMEI);
			}
			return notIMEI;
		}

		public static String getDeviceIdentifier_wasSIM(Context _c)
		{
			String notSimNo = STATUSMANAGER.STATUSES.MOCK_SIM_NO.getValue(_c);
			if(notSimNo.equals(STATUSMANAGER.STATUSES.MOCK_SIM_NO.getDefaultValue()))
			{
				String androidIdHex = android.provider.Settings.Secure.getString(_c.getContentResolver(),
					android.provider.Settings.Secure.ANDROID_ID);

				String androidId = "";
				try
				{
				 	androidId=String.valueOf(Long.parseLong(androidIdHex, 16));
				}
				catch (Exception ex)
				{
					String[] validChars= {"0","1","2","3","4","5","6","7","8","9"};
					StringBuilder newId = new StringBuilder("");
					for(char c:androidIdHex.toCharArray())
					{
						for(String vc:validChars)
						{
							if (vc.equals(String.valueOf(c)))
							{
								newId.append(c);
								break;
							}
						}

					}
					androidId = newId.toString();
				}

				while(androidId.length() < 16)
				{
					androidId += "9";
				}


				notSimNo = "99999" + androidId.substring(androidId.length() - 14);
				STATUSMANAGER.STATUSES.MOCK_SIM_NO.putValue(_c, notSimNo);
			}

			return notSimNo;
		}


		@SuppressLint("RestrictedApi")
        public static void makeUniformButtonTextSize(AppCompatTextView... _views)
		{
			float viewTextSize;
			float minSize = Float.MAX_VALUE;
			for (AppCompatTextView t:_views)
			{
				viewTextSize = t.getTextSize();
				if (viewTextSize < minSize) { minSize = viewTextSize; }
			}
			for (AppCompatTextView t:_views)
			{
				t.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);
				t.setTextSize(TypedValue.COMPLEX_UNIT_PX, minSize);
			}


        }

        public interface OnPriceUpdatedListener
        {
            void onPriceUpdated();
        }

        public static Boolean checkForPackageExist(Context _context, String _packageName)
		{
			Boolean found = false;

			PackageManager pm =  _context.getPackageManager();
			Intent i = new Intent("android.intent.action.MAIN");
			i.addCategory(Intent.CATEGORY_LAUNCHER);

			List<ApplicationInfo> lst = pm.getInstalledApplications(PackageManager.GET_META_DATA);
			if (lst != null)
			{
				for (ApplicationInfo appInfo : lst)
				{
					String packageName = appInfo.packageName;

					if(packageName==null)
					{
						continue;
					}
					else if((packageName.equals(_packageName)))
					{
						found = true;
						break;
					}
				}
			}

			return found;
		}

        public static void startSimplePriceRequest(final Activity _c, ViewGroup _container, final Window _w, final View _currentFocus, final OnPriceUpdatedListener _listener)
        {
            Dialog_GetPrice dlg = new Dialog_GetPrice(_c, _container, false);
            dlg.setOnFareSetListener(new Dialog_GetPrice.OnFareSetListener()
            {
                @Override
                public void onFareSet(Double _fare)
                {

                    InputMethodManager inputMethodManager=(InputMethodManager) _c.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    if(_currentFocus!=null)
                    {
                        inputMethodManager.hideSoftInputFromWindow(_currentFocus.getWindowToken(), 0);
                    }

                    _w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    //startTotalFareAdvice(_fare);
                    cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);
                    j.updatePrice(String.valueOf(_fare));
                    STATUSMANAGER.setCurrentJob(_c, j);
                    BROADCASTERS.setJobPrice(_c, String.valueOf(_fare));

                    Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
                    Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
                    Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.SET_CLEAR);

                    _c.sendBroadcast(Message);

                    if(!(_listener==null))
                    {
                        _listener.onPriceUpdated();
                    }

                }
            });
            dlg.show(R.id.dlgPrice_txtFare);
        }

		public static void UpdateApp(Context _c) { UpdateApp(_c, null);}

        public static void UpdateApp(Context _c, Dialog_Update.OnUpdatePackageDownloadListener _l)
        {
            if(STATUSMANAGER.getInstallSource(_c).equals(STATUSMANAGER.INSTALL_SOURCE.CAB_DESPATCH_HOSTED))
            {
            	/*
                Dialog_Update d = new Dialog_Update(_c);
                d.setOnPackageDownloadListener(_l);
                d.show();
                */
				Intent i = new Intent(_c, UpdateActivity.class);
				_c.startActivity(i);
            }
            else
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("market://details?id=" + _c.getPackageName()));
                Globals.StartActivity(_c, i);
            }
        }

		public static void Panic(Context _c)
		{
			Intent panicdata = new Intent(BROADCASTERS.USER_REQUEST);
			panicdata.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
			panicdata.putExtra(DataService._MESSAGEDATA, USERREQUESTS.PANIC);
			
			_c.sendBroadcast(panicdata);
			
			STATUSMANAGER.putBoolean(_c, STATUSMANAGER.STATUSES.IS_PANIC, true);
			
			Intent panic = new Intent(_c, PanicActivity.class);
            Globals.StartActivity(_c, panic);
        }

		public static void armPanic(final ViewGroup _v, final Activity _a, final UnarmPanicListener _l)
		{
			_v.setBackgroundResource(R.color.button_background_panic_armed);

			class panictimer extends Thread
			{
				@Override
				public void run()
				{
					try
					{
						Thread.sleep(3000);

					}
					catch (Exception ex){ /*never mind */ }


					cabdespatchJob j = STATUSMANAGER.getCurrentJob(_v.getContext());

					if((j.getJobStatus()==JOB_STATUS.POB) || (j.getJobStatus()==JOB_STATUS.STC))
					{
						//do nothing - panic should always be armed
					}
					else
					{

						_a.runOnUiThread(new Runnable()
										 {

											 @Override
											 public void run()
											 {
												 _v.setBackgroundResource(R.color.button_background_panic_disarmed);

											 }

										 }
						);

						_l.unarmPanic();
					}
				}
			}

			new panictimer().start();
		}

		public static void armPanic(final ImageButton _b, final Activity _a, final UnarmPanicListener _l)
		{
			_b.setImageResource(R.drawable.btn_panic_armed);
			
			class panictimer extends Thread
			{
				@Override
				public void run()
				{
					try
					{
						Thread.sleep(3000);
						
					}
					catch (Exception ex){ /*never mind */ }
					
					
					cabdespatchJob j = STATUSMANAGER.getCurrentJob(_b.getContext());
					
					if((j.getJobStatus()==JOB_STATUS.POB) || (j.getJobStatus()==JOB_STATUS.STC))
					{
						//do nothing - panic should always be armed
					}
					else
					{

						_a.runOnUiThread(new Runnable()
						{

							@Override
							public void run()
							{
								_b.setImageResource(R.drawable.btn_panic);
								
							}
							
						}
						);
						
						_l.unarmPanic();
					}
				}
			}
			
			new panictimer().start();
		}
		
		public static void Back(View v)
		{
            if(!(v==null))
            {
                v.setVisibility(View.INVISIBLE);

            }

			Back(v.getContext());
		}

		public static void Back(final Context _c)
		{
            /*
			if(DataService.IS_ACTIVE)
			{
				Intent i = new Intent(BROADCASTERS.USER_REQUEST);
				i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
				i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.BACK);

				_c.sendBroadcast(i);
			}
			else
			{
				DEBUGMANAGER.Log(_c, "DataService Not Running");
				DataService.StartDataService(_c);

				new PauseAndRun()
				{

					@Override
					protected void onPostExecute(Void _void)
					{
						Intent i = new Intent(BROADCASTERS.USER_REQUEST);
						i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
						i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.BACK);

						_c.sendBroadcast(i);
					}
				}.Start(1000);

			}*/

		}


		public static JobButtonPressResult jobButtonPressed(View v)
		{

            JobButtonPressResult res = JobButtonPressResult.NoActionRequired();

			cabdespatchJob j = STATUSMANAGER.getCurrentJob(v.getContext());		
			
			Boolean broadcastIntent = true;
			
			Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
			Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
			
			String userRequest="";
			
			switch (j.getJobStatus())
			{
			case ON_ROUTE:
				userRequest = USERREQUESTS.SET_STP;
				break;
			case POB:
				userRequest = USERREQUESTS.SET_STC;
				break;
			case STC:
                res = Globals.CrossFunctions.ClearPressedShouldWeContinue(v.getContext());
                if(res.getAction()==JobButtonPressResult.ACTION_NONE)
                {
                    if(j.isFlagDown())
                    {
                        userRequest = USERREQUESTS.STOP_FLAGDOWN;
                    }
                    else
                    {
                        userRequest = USERREQUESTS.SET_CLEAR;
                    }
                }
                else
                {
                    broadcastIntent = false;
                }
				break;
			case STP:
				userRequest = USERREQUESTS.SET_POB;
				break;
			case ERROR:
			case UNDER_OFFER:
				ErrorActivity.handleError(v.getContext(), new ErrorActivity.ERRORS.INAPPROPRIATE_JOB_STATE_ON_JOB_BUTTON_PRESS());
				break;
			}
			
			Message.putExtra(DataService._MESSAGEDATA, userRequest);
			if(broadcastIntent)
			{
				v.getContext().sendBroadcast(Message);
			}

            return res;
		}
		
		public static JobButtonPressResult ClearPressedShouldWeContinue(final Context _c)
		{
			Boolean askprice = false;
            Boolean showPOD = false;
            Boolean showJobNotes = false;
			
			cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);


			Boolean notesRequested = j.getNotesRequired();
			Integer jobNotesMinChars = SETTINGSMANAGER.SETTINGS.JOB_DRIVER_NOTES_MINIMUM_SIZE.parseInteger(_c);

            if(notesRequested)
            {
                String jobNotes = j.getDriverNotes();
                if(jobNotes.equals(Settable.NOT_SET))
                {
                    //no notes have been set
                    showJobNotes = true;
                }
                else if(jobNotesMinChars > 0)
                {
                    if(jobNotes.length() < jobNotesMinChars)
                    {
                        showJobNotes = true;
                    }
                }
            }

            if(showJobNotes)
            {
                return  JobButtonPressResult.RequestDriverNotes();
            }
            else
            {
                if (j.isCash())
                {
                    askprice = SETTINGSMANAGER.SETTINGS.REQUEST_PRICE_FROM_DRIVER_CASH.parseBoolean(_c);
                }
                else
                {
                    askprice = false;
                    showPOD = true;
                }


                if((askprice))
                {
                    return JobButtonPressResult.RequestPrice();
                }
                else if(showPOD)
                {
                    //CLAY we should handle this with an ACTION_RESULT eventually... but for now we'll
                    //leave it
                    Intent i = new Intent(_c, POD.class);
                    Globals.StartActivity(_c, i);
                    return JobButtonPressResult.RequestPOD();
                }
                else
                {
                    return JobButtonPressResult.NoActionRequired();
                }
            }
		}

        public static void startNavigate(Context _c, String _toLat, String _toLon)
        {
            try
            {
                String navURL = ("google.navigation:q=" + _toLat + "," + _toLon);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                           Uri.parse(navURL));

                Globals.StartActivity(_c, intent);
            }
			catch (android.content.ActivityNotFoundException ex)
            {
                cdToast.showLong(_c, _c.getString(R.string.navigation_app_not_found));
            }
        }
		
 		public static void startNavigate(final Context _c)
		{
			cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);
			
			String fromLat = "0";
			String fromLon = "0";
			String toLat = "0";
			String toLon = "0";
			switch(j.getJobStatus())
			{
				case ON_ROUTE:
					fromLat = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LAT);
					fromLon = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LON);
					toLat = j.getFromLat();
					toLon = j.getFromLon();
					break; 
				case POB:
				case STC:
					fromLat = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LAT);
					fromLon = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LON);
					toLat = j.getToLat();
					toLon = j.getToLon();
					break;
				default:
					fromLat = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LAT);
					fromLon = STATUSMANAGER.getString(_c, STATUSMANAGER.STATUSES.CURRENT_LON);
			}
			
			if(toLat.equals("0"))
			{
				String toLocation = SETTINGSMANAGER.SETTINGS.NAVIGATION_LOCATION.getValue(_c);
				if(toLocation.equals(Settable.NOT_SET))
				{
					Dialog_NavLocation d = new Dialog_NavLocation(_c);
					d.setOnResultListener(new Dialog_NavLocation.OnResultListener() {
						
						@Override
						public void OnResult(RESULT _result) 
						{
								if(_result.equals(RESULT.OK))
								{
									//yay reccursion!
									Globals.CrossFunctions.startNavigate(_c);
								}
						}
					});
					
					d.show();
				}
				else
				{
                    try
                    {
                        String navURL = ("google.navigation:q=" + toLocation);
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                   Uri.parse(navURL));

                        Globals.StartActivity(_c, intent);
                    }
					catch (android.content.ActivityNotFoundException ex)
                    {
                        cdToast.showLong(_c, _c.getString(R.string.navigation_app_not_found));
                    }
				}
			}
			else
			{
				try
				{
                    String navURL = ("google.navigation:q=" + toLat + "," + toLon);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                               Uri.parse(navURL));

                    Globals.StartActivity(_c, intent);
                }
				catch (android.content.ActivityNotFoundException ex)
				{
					cdToast.showLong(_c, _c.getString(R.string.navigation_app_not_found));
				}
			}
			
		}
 		
 		
		
		public static class Menus
		{
			
			public interface OnMenuItemSelectedListener
			{
				public void onItemSelected(String _tag);
			}
			
			private static void setMenuItemImage(LinearLayout _l, int _resource)
			{
				ImageView i = (ImageView) _l.getChildAt(0);
				i.setImageResource(_resource);
				//i.setEnabled(false);
			}
			
			private static void setMenuItemText(LinearLayout _l, String _text)
			{
				TextView t = (TextView) _l.getChildAt(1);
				t.setText(_text);
			}
			
			private static void setMenuItemTextSize(LinearLayout _l)
			{
				TextView t = (TextView) _l.getChildAt(1);
				t.setPadding(_l.getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin), 0,0,0);
				t.setTextSize(TypedValue.COMPLEX_UNIT_SP, SETTINGSMANAGER.TextTools.getTextSizeDIP(t.getContext(), SETTINGSMANAGER.TextTools.SIZE.MENU_ITEM));
			}
			
			private static void setMenuItemTextColour(LinearLayout _l)
			{
				/*int[][] states = new int[][] 
				{
					    new int[] { android.R.attr.state_enabled}, // enabled
					    new int[] {-android.R.attr.state_enabled}
				};
				
				int[] colours = new int[]
				{
						Color.BLACK,
						Color.argb(50, 0, 0, 0)						
				};
				
				
				ColorStateList myList = new ColorStateList(states, colours);*/
				
				TextView t = (TextView) _l.getChildAt(1);
				t.setTextColor(Color.BLACK);

			}
			
			public static void setupMenuItems(final AnyActivity activity, LinearLayout _container, Boolean _loggedIn, final OnMenuItemSelectedListener _listener)
			{
				cabdespatchJob j = STATUSMANAGER.getCurrentJob(_container.getContext());
				Boolean useAntiCheat = Boolean.valueOf(SETTINGSMANAGER.get(_container.getContext(), SETTINGSMANAGER.SETTINGS.USE_ANTICHEAT));
				String currentPlot = STATUSMANAGER.getCurrentLocation(_container.getContext()).getPlot().getShortName();
				String pickupPlot = j.getFromPlot();
				Boolean useFlagDown = Boolean.valueOf(SETTINGSMANAGER.get(_container.getContext(), SETTINGSMANAGER.SETTINGS.USE_FLAGDOWN));
				
				int childCount = _container.getChildCount();
				for(int x=0;x<=(childCount - 1);x++)
				{
					LinearLayout v = (LinearLayout) _container.getChildAt(x);
					v.setVisibility(View.GONE);
				}
				
				LinearLayout btnReset = (LinearLayout) _container.findViewById(R.id.frmMenu_btnReset);
				btnReset.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent reset = new Intent(v.getContext(), ResetApp.class);
                        Globals.StartActivity(v.getContext(), reset);
                                _listener.onItemSelected(MENU_TAGS.NEW_ACTIVITY);
					}
				});
				
				LinearLayout btnSettings = (LinearLayout) _container.findViewById(R.id.frmMenu_btnSettings);
				btnSettings.setOnClickListener(new View.OnClickListener()
				{	
					@Override
					public void onClick(View v)
					{
						Intent settings = new Intent(v.getContext(), Settings.class);
                        Globals.StartActivity(v.getContext(), settings);
                                _listener.onItemSelected(MENU_TAGS.NEW_ACTIVITY);
					}
				});

				LinearLayout btnManageAccount = (LinearLayout) _container.findViewById(R.id.frmMenu_btnManageCircuitFees);
				btnManageAccount.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						DriverPaymentActivity.StartPayAndGoPayment(v.getContext());
						_listener.onItemSelected(MENU_TAGS.NEW_ACTIVITY);
					}
				});

                LinearLayout btnCallCustomer = (LinearLayout) _container.findViewById(R.id.frmMenu_btnCallCustomer);
                btnCallCustomer.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String phoneNumber = STATUSMANAGER.getCurrentJob(v.getContext()).getTelephoneNumber();

                        if((phoneNumber.isEmpty()) || (phoneNumber.equals(cabdespatchJob.EMPTY)))
                        {
                            cdToast.showShort(v.getContext(), R.string.unable_to_call_no_phone_number_provided);
                        }
                        else
                        {
                            Intent call = new Intent(Intent.ACTION_DIAL);
                            call.setData(Uri.parse("tel:" + phoneNumber));
                            v.getContext().startActivity(call);
                        }
                    }
                });
				
				LinearLayout btnFlagDown = (LinearLayout) _container.findViewById(R.id.frmMenu_btnFlagDown);
				btnFlagDown.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent startFlag = new Intent(BROADCASTERS.USER_REQUEST);
						startFlag.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
						startFlag.putExtra(DataService._MESSAGEDATA, USERREQUESTS.START_FLAGDOWN);
						
						v.getContext().sendBroadcast(startFlag);
						
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				}
				);
				
				LinearLayout btnNavigate = (LinearLayout) _container.findViewById(R.id.frmMenu_btnNavigate);
				btnNavigate.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Globals.CrossFunctions.startNavigate(v.getContext());
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				});
							
				LinearLayout btnBreak = (LinearLayout) _container.findViewById(R.id.frmMenu_btnBreak);
				btnBreak.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
						Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
						Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.BREAK_START);
					    
						v.getContext().sendBroadcast(Message);
						
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				});
				
				LinearLayout btnViewMessages = (LinearLayout) _container.findViewById(R.id.frmMenu_btnViewMessages);
				btnViewMessages.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent i = new Intent(v.getContext(), ViewMessages.class);
                        Globals.StartActivity(v.getContext(), i);
                                _listener.onItemSelected(MENU_TAGS.NEW_ACTIVITY);
					}
				});
				if(STATUSMANAGER.getDriverMessages(_container.getContext(), STATUSMANAGER.DriverMessage.BOX.ALL).size()<=0)
				{
					btnViewMessages.setEnabled(false);
				}
                else
                {
                    btnViewMessages.setEnabled(true);
                }
							
				
				LinearLayout btnSendMessage = (LinearLayout) _container.findViewById(R.id.frmMenu_btnSendMessage);
				btnSendMessage.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent i = new Intent(v.getContext(), ComposeMessage.class);
						i.setAction(ComposeMessage._MESSAGE_TYPE.DATA_MESSAGE);
                        Globals.StartActivity(v.getContext(), i);
                                _listener.onItemSelected(MENU_TAGS.NEW_ACTIVITY);
					}
				});
				
				LinearLayout btnVoice = (LinearLayout) _container.findViewById(R.id.frmMenu_btnVoice);
				btnVoice.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Boolean useRequest = Boolean.valueOf(SETTINGSMANAGER.get(v.getContext(), SETTINGSMANAGER.SETTINGS.USE_VOICE_REQUEST));
						
						if(useRequest)
						{
							Intent voiceReq = new Intent(BROADCASTERS.USER_REQUEST);
							voiceReq.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
							voiceReq.putExtra(DataService._MESSAGEDATA, USERREQUESTS.VOICE_REQUEST);
							
							v.getContext().sendBroadcast(voiceReq);
							
							cdToast.showLong(v.getContext(), "Voice Request Sent");
                            _listener.onItemSelected(MENU_TAGS.NONE);

						}
						else
						{
							try
							{
								String officeNumber = SETTINGSMANAGER.get(v.getContext(), SETTINGSMANAGER.SETTINGS.OFFICE_NUMBER);
								Intent voice = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + officeNumber));

								Globals.StartActivity(v.getContext(), voice);
								_listener.onItemSelected(MENU_TAGS.NEW_ACTIVITY);
							}
							catch (SecurityException ex)
							{
								cdToast.showLong(v.getContext(), R.string.permission_error);
							}
						}
						

					}
				});

                /*
				if (Double.valueOf(SETTINGSMANAGER.get(_container.getContext(), SETTINGSMANAGER.SETTINGS.SETTINGS_VERSION)) <= 0)
				{
					//btnVoice.setEnabled(false);
				}
				*/
							
				
				LinearLayout btnJobButton = (LinearLayout) _container.findViewById(R.id.frmMenu_btnJobButton);
				btnJobButton.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
                        JobButtonPressResult res = Globals.CrossFunctions.jobButtonPressed(v);
                        if(res.getAction()==JobButtonPressResult.ACTION_REQUEST_PRICE)
                        {
                            _listener.onItemSelected(MENU_TAGS.GET_PRICE);
                        }
                        else
                        {
                            _listener.onItemSelected(MENU_TAGS.NONE);
                        }
					}
				});
				
				LinearLayout btnPOB = (LinearLayout) _container.findViewById(R.id.frmMenu_btnPOB);
				btnPOB.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
						Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
						Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.SET_POB);
						
						v.getContext().sendBroadcast(Message);
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				});

				LinearLayout btnSTC = (LinearLayout) _container.findViewById(R.id.frmMenu_btnSTC);
				btnSTC.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
						Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
						Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.SET_STC);

						v.getContext().sendBroadcast(Message);
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				});
				
				LinearLayout btnPlotDivert = (LinearLayout) _container.findViewById(R.id.frmMenu_btnPlotDivert);
				btnPlotDivert.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Context c = v.getContext();
						Intent i = new Intent(c, PlotList.class);
						i.setAction(PlotList.ACTION_SEND_PLOT_UPDATE);
                        Globals.StartActivity(c, i);
                                _listener.onItemSelected(MENU_TAGS.NONE);
					}
				});
							
				LinearLayout btnAddStop = (LinearLayout) _container.findViewById(R.id.frmMenu_btnAddStop);
				btnAddStop.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
						Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
						Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.ON_ROUTE_STOP);
					    
						v.getContext().sendBroadcast(Message);
						
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				});
				
				LinearLayout btnWaitingTime = (LinearLayout) _container.findViewById(R.id.frmMenu_btnWaitingTime);
				btnWaitingTime.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
						Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
						Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.WAITING_TIME_START);
					    
						v.getContext().sendBroadcast(Message);
						
						_listener.onItemSelected(MENU_TAGS.NONE);
						
					}
				});
				
				LinearLayout btnNoShow = (LinearLayout) _container.findViewById(R.id.frmMenu_btnNoShow);
				btnNoShow.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent i = new Intent(v.getContext(), ComposeMessage.class);
						i.setAction(ComposeMessage._MESSAGE_TYPE.NO_SHOW);
                        Globals.StartActivity(v.getContext(), i);
                                _listener.onItemSelected(MENU_TAGS.NONE);
					}
				});
				
				//no show		
				
				Boolean isNoNoShow = STATUSMANAGER.getBoolean(_container.getContext(), STATUSMANAGER.STATUSES.NO_NO_SHOW);
				
				if(isNoNoShow)
				{
					//we are noNoShow - do we have override authority (aka the anticheat)?
					if (!(j.overRrideNoNoShow()))
					{
						//no we do not - disable the button
						btnNoShow.setEnabled(false); 
					}
				}
				else if (useAntiCheat)
				{
					//we are not using noNoShow, but are using the antiCheat System
					if (currentPlot.equals(pickupPlot))
					{
						//we are in the correct plot - do nothing
					}
					else
					{
						//we are in the wrong plot - do we have the anticheat?
						//assume if the driver can POB, then he can also no-show
						//(ie he's already been in the correct plot)
						if (!(j.canPOB(_container.getContext(), useAntiCheat)))
						{
							//no we do not - disable the button
							btnNoShow.setEnabled(false);
						}
					}
				}
				
				
				LinearLayout btnReturnJob = (LinearLayout) _container.findViewById(R.id.frmMenu_btnReturnJob);
				btnReturnJob.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						//do this here rather than in data service, otherwise we'll be sent back to job screen...
						STATUSMANAGER.setAppState(v.getContext(), STATUSMANAGER.APP_STATE.LOGGED_ON);
						
						Intent retjob = new Intent(BROADCASTERS.USER_REQUEST);
						retjob.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
						retjob.putExtra(DataService._MESSAGEDATA, USERREQUESTS.RETURN_JOB);
						
						v.getContext().sendBroadcast(retjob);
						
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				});
				
				LinearLayout btnJobHistory = (LinearLayout) _container.findViewById(R.id.frmMenu_btnJobHistory);
				if(JOBHISTORYMANAGER.getHistoricalJobs(_container.getContext()).size() >0)
				{
					btnJobHistory.setOnClickListener(new View.OnClickListener()
					{
						
						@Override
						public void onClick(View v)
						{
							Intent i = new Intent(v.getContext(), ViewJobHistory.class);
                            Globals.StartActivity(v.getContext(), i);
                                    _listener.onItemSelected(MENU_TAGS.NEW_ACTIVITY);
						}
					});	
				}
				else
				{
					btnJobHistory.setEnabled(false);
				}



                LinearLayout btnCircuitFees = (LinearLayout) _container.findViewById(R.id.frmMenu_btnCircuitFees);
                btnCircuitFees.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        BROADCASTERS.CircuitFees(v.getContext());
                        _listener.onItemSelected(MENU_TAGS.NONE);
                    }
                });


				
				LinearLayout btnJobTotals = (LinearLayout) _container.findViewById(R.id.frmMenu_btnJobTotals);
				btnJobTotals.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Intent i =  new Intent(v.getContext(), JobTotals.class);
                        Globals.StartActivity(v.getContext(), i);

                                _listener.onItemSelected(MENU_TAGS.NEW_ACTIVITY);
					}
				});
				
							
				LinearLayout btnLogOff = (LinearLayout) _container.findViewById(R.id.frmMenu_btnLogOff);
				btnLogOff.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,"LOGOFF", "IN MESSAGE MENU");
						BROADCASTERS.Logout(v.getContext());
						
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				});

				View btnScan = _container.findViewById(R.id.frmMenu_btnScanDocument);
				btnScan.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						new DocumentScanner().Start(activity);
						_listener.onItemSelected(MENU_TAGS.NONE);
					}
				});

				LinearLayout btnExit = (LinearLayout) _container.findViewById(R.id.frmMenu_btnExit);
				btnExit.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(final View v)
					{
						Dialog_MsgBox dlgSure = new Dialog_MsgBox(v.getContext(),  "Really Quit?", "Are you sure you wish to quit", _SHOWBUTTONS.YESNO);
						dlgSure.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener() 
						{
							
							@Override
							public void ButtonPressed(_BUTTON _button) 
							{
								if (_button==_BUTTON.YES)
								{
									//stop the data service
									BROADCASTERS.Quit(v.getContext());								    																		
									_listener.onItemSelected(MENU_TAGS.EXIT);
								}
							}
						});
						dlgSure.show();
						
					}
				});
				
				
				btnJobButton.setEnabled(true);
				btnPOB.setEnabled(true);
                //we're on an appropriate status, but do we have the anticheat system enabled?
                if (useAntiCheat)
                {
                	Boolean canPOB = j.canPOB(_container.getContext(), useAntiCheat);

					if ((j.getJobStatus().equals(JOB_STATUS.STP)))
					{
						btnJobButton.setEnabled(canPOB); //the button says POB
					}
					else if ((j.getJobStatus().equals(JOB_STATUS.ON_ROUTE)))
					{
						btnPOB.setEnabled(canPOB);
					}

                }
				
				
				LinearLayout btnDebug = (LinearLayout) _container.findViewById(R.id.frmMenu_btnDebug);
				btnDebug.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(final View v)
					{
						cdToast.showLong(v.getContext(), "Waiting for debugger...");
						class attatcher extends Thread
						{
							@Override
							public void run()
							{
								//Debug.waitForDebugger();
								Handler h = new Handler(v.getContext().getMainLooper());
								h.post(new Runnable()
								{
									@Override
									public void run()
									{
										
										cdToast.showLong(v.getContext(), "Debugger Attached");
									}
								});
								
							}
						}
						
						new attatcher().start();
					}
				});
				
				LinearLayout btnForceQuit = (LinearLayout) _container.findViewById(R.id.frmMenu_btnEngineerQuit);
				btnForceQuit.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						STATUSMANAGER.setAppState(v.getContext(), STATUSMANAGER.APP_STATE.LAUNCHER);
						BROADCASTERS.Quit(v.getContext());						
					}
				});
							
	
				//now show appropriate buttons
				Boolean hasCake = STATUSMANAGER.getBoolean(_container.getContext(), STATUSMANAGER.STATUSES.HAS_DEBUG_CONNECTOR);
				if(hasCake)
				{
					btnDebug.setVisibility(View.VISIBLE);
					btnForceQuit.setVisibility(View.VISIBLE);
				}
				
				Boolean thankYouMode = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.THANKYOU_MODE.getValue(_container.getContext()));
				if(thankYouMode)
				{
					btnReset.setVisibility(View.VISIBLE);;
				}
				
				if (!(_loggedIn))
				{
					//  btnNavigate
					  //btnBreak.setVisibility(View.VISIBLE);
					  btnManageAccount.setVisibility(View.VISIBLE);
					  btnSettings.setVisibility(View.VISIBLE);
					  btnViewMessages.setVisibility(View.VISIBLE);
					  btnSendMessage.setVisibility(View.VISIBLE);
					  btnVoice.setVisibility(View.VISIBLE);
					  btnJobHistory.setVisibility(View.VISIBLE);
					  btnJobTotals.setVisibility(View.VISIBLE);
                      btnCircuitFees.setVisibility(View.VISIBLE);
                      btnScan.setVisibility(View.VISIBLE);
					  btnExit.setVisibility(View.VISIBLE);
					  
					 // btnLogOff.setVisibility(View.VISIBLE);
				}
				else if (j.isFlagDown())
				{
					if(useFlagDown)
					  {
						  btnFlagDown.setVisibility(View.VISIBLE);
						  btnFlagDown.setEnabled(false);
					  }
					
					btnNavigate.setVisibility(View.VISIBLE);
					setMenuItemText(btnNavigate, "Plan Route");
					
					btnViewMessages.setVisibility(View.VISIBLE);
					btnSendMessage.setVisibility(View.VISIBLE);
					btnVoice.setVisibility(View.VISIBLE);
					btnPlotDivert.setVisibility(View.VISIBLE);
					setMenuItemText(btnPlotDivert, "Set Destination Plot");
					btnJobButton.setVisibility(View.VISIBLE);
					setMenuItemImage(btnJobButton, R.drawable.btn_jclear);
					if(BuildConfig.NEW_BRANCH)
					{
						setMenuItemImage(btnJobButton, R.drawable.ico_btn_clear);
					}
					setMenuItemText(btnJobButton, "Clear");
				}
				else
				{
					if(useFlagDown)
					{
					  btnFlagDown.setVisibility(View.VISIBLE);
					  btnFlagDown.setEnabled(false);
					}
					
					
					switch(j.getJobStatus())
					{
						
					
						case NOT_ON_JOB:
						case UNDER_OFFER:
						case ERROR:
						case REJECTING:								
							  btnFlagDown.setEnabled(true);  
							
							  btnNavigate.setVisibility(View.VISIBLE);
							  setMenuItemText(btnNavigate, "Plan Route");
							  
							  btnBreak.setVisibility(View.VISIBLE);
                              btnJobHistory.setVisibility(View.VISIBLE);
							  btnJobTotals.setVisibility(View.VISIBLE);
                              btnCircuitFees.setVisibility(View.VISIBLE);
							  btnViewMessages.setVisibility(View.VISIBLE);
							  btnSendMessage.setVisibility(View.VISIBLE);
							  btnVoice.setVisibility(View.VISIBLE);
							  btnLogOff.setVisibility(View.VISIBLE);
							  break;
						case ACCEPTING:
						case ON_ROUTE:
							  btnNavigate.setVisibility(View.VISIBLE);
							  setMenuItemText(btnNavigate, "Navigate to pickup");
							  
							  btnViewMessages.setVisibility(View.VISIBLE);
							  btnSendMessage.setVisibility(View.VISIBLE);
							  btnVoice.setVisibility(View.VISIBLE);
							  
							  btnJobButton.setVisibility(View.VISIBLE);
							  setMenuItemImage(btnJobButton, R.drawable.btn_jstp);
							  if(BuildConfig.NEW_BRANCH)
							  {
								setMenuItemImage(btnJobButton, R.drawable.ico_btn_stp);
							  }
							  setMenuItemText(btnJobButton, "TextBack");
							  
							  btnPOB.setVisibility(View.VISIBLE);
							  btnWaitingTime.setVisibility(View.VISIBLE);
							  btnNoShow.setVisibility(View.VISIBLE);
							  btnReturnJob.setVisibility(View.VISIBLE);
							  break;
						case STP:
							  btnNavigate.setVisibility(View.VISIBLE);
							  setMenuItemText(btnNavigate, "Navigate to destination");
							  
							  btnViewMessages.setVisibility(View.VISIBLE);
							  btnSendMessage.setVisibility(View.VISIBLE);
							  btnVoice.setVisibility(View.VISIBLE);
							  
							  btnJobButton.setVisibility(View.VISIBLE);
							  setMenuItemImage(btnJobButton, R.drawable.btn_jpob);
							if(BuildConfig.NEW_BRANCH)
							{
								setMenuItemImage(btnJobButton, R.drawable.ico_btn_pob);
							}
							  setMenuItemText(btnJobButton, "POB");
							  
							  btnWaitingTime.setVisibility(View.VISIBLE);
							  btnNoShow.setVisibility(View.VISIBLE);
							  //btnReturnJob.show();
                              String phoneNumber = STATUSMANAGER.getCurrentJob(_container.getContext()).getTelephoneNumber();
                              Boolean showPhoneButton = true;
                              if(phoneNumber.equals(Settable.NOT_SET)) { showPhoneButton = false; } //we set to NOT_SET if the setting is disabled
                              else if(!(SETTINGSMANAGER.getLockDownMode(_container.getContext()).equals(SETTINGSMANAGER.LOCK_DOWN_MODES.NONE))) { showPhoneButton = false; }

                                if(showPhoneButton)
                                {
                                        btnCallCustomer.setVisibility(View.VISIBLE);
                                }
							  break;
						case POB:
							  btnNavigate.setVisibility(View.VISIBLE);
							  setMenuItemText(btnNavigate, "Navigate to destination");
							  
							  btnViewMessages.setVisibility(View.VISIBLE);
							  btnSendMessage.setVisibility(View.VISIBLE);
							  btnVoice.setVisibility(View.VISIBLE);
							  
							  btnJobButton.setVisibility(View.VISIBLE);
							  setMenuItemImage(btnJobButton, R.drawable.n_stc_invert);
							if(BuildConfig.NEW_BRANCH)
							{
								setMenuItemImage(btnJobButton, R.drawable.ico_btn_stc);
							}
							  setMenuItemText(btnJobButton, "STC");
							  
							  btnPlotDivert.setVisibility(View.VISIBLE);
							  
							  btnAddStop.setVisibility(View.VISIBLE);
							  btnWaitingTime.setVisibility(View.VISIBLE);
							 // btnReturnJob.show();
							  break;
						case STC:
							  btnNavigate.setVisibility(View.VISIBLE);
							  setMenuItemText(btnNavigate, "Navigate to destination");
							  
							  btnViewMessages.setVisibility(View.VISIBLE);
							  btnSendMessage.setVisibility(View.VISIBLE);
							  btnVoice.setVisibility(View.VISIBLE);
							  
							  btnJobButton.setVisibility(View.VISIBLE);
							  btnSTC.setVisibility(View.VISIBLE);
							  setMenuItemImage(btnJobButton, R.drawable.btn_jclear);
							if(BuildConfig.NEW_BRANCH)
							{
								setMenuItemImage(btnJobButton, R.drawable.ico_btn_clear);
							}
							  setMenuItemText(btnJobButton, "CLEAR");
							  
							  if (j.isFlagDown())
							  {
								  btnPlotDivert.setVisibility(View.VISIBLE);
							  }
							  else
							  {
								  //btnReturnJob.show();
							  }
							  
							  btnAddStop.setVisibility(View.VISIBLE);
							  btnWaitingTime.setVisibility(View.VISIBLE);
							  
							  break;
					}
					
				}
				
				if(!(SETTINGSMANAGER.navigationAvailable(_container.getContext())))
				{
					btnNavigate.setVisibility(View.GONE);
				}
				
				for(int x=0;x<=(childCount - 1);x++)
				{
					LinearLayout v = (LinearLayout) _container.getChildAt(x);
					v.setGravity(Gravity.CENTER_VERTICAL);
					setMenuItemTextSize(v);
					setMenuItemTextColour(v);
					
					v.clearAnimation();
					
					float newAlpha = 0f;
					
					if(v.isEnabled())
					{
						
						newAlpha = 1f;
					}
					else
					{
						newAlpha = 0.3f;
					}
					
					//this is how we apply alpha to an entire view in android pre SDK 11
					//oh yessss....! 
					
					AlphaAnimation alpha = new AlphaAnimation(newAlpha, newAlpha);
					alpha.setDuration(0); // Make animation instant
					alpha.setFillAfter(true); // Tell it to persist after the animation ends
					// And then on your layout
					v.startAnimation(alpha);
					
				}
			
			}
		}
	}

	public static Boolean isDebug(Context _c)
	{
		if(BuildConfig.DEBUG)
		{
			//CLAY switch on when we want debug messages
			if(_c.getPackageName().toUpperCase().contains("BETA"))
			{
				//we don't want beta builds
				//to show debug info
				return false;
			}
			else
			{
				return true;
			}

		}
		else
		{
			return false;
		}
	}
}
