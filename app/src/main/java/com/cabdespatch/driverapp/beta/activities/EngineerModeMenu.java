package com.cabdespatch.driverapp.beta.activities;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.activities2017.ViewJobHistory;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._BUTTON;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._SHOWBUTTONS;

import org.joda.time.DateTime;

import java.io.File;

public class EngineerModeMenu extends AnyActivity {



	private void configureCheckBoxSetting(int checkboxId, final Settable setting)
	{
		CheckBox chk = findViewById(checkboxId);
		boolean currentValue = setting.parseBoolean(this);
		chk.setChecked(currentValue);

		chk.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				setting.putValue(EngineerModeMenu.this, isChecked);
			}
		});
	}

	@Override
	protected void onResume() 
	{
        //test for svn
		super.onResume();
		setContentView(R.layout.activity_engineermenu);
		this.setBackground();
		STATUSMANAGER.putString(this, STATUSMANAGER.STATUSES.LAST_ENGINEER_SESSION, new DateTime().toString());

		configureCheckBoxSetting(R.id.engineer_chkYapiDebug, SETTINGSMANAGER.SETTINGS.YAPI_USE_DEBUG_HOST);
		
		CheckBox chkDemoMode = (CheckBox) this.findViewById(R.id.engineer_chkDemo);		
		CheckBox chkLockdown = (CheckBox) findViewById(R.id.engineer_chkLockdown);
		chkLockdown.setEnabled(false);
		if(SETTINGSMANAGER.getLockDownMode(this).equals(SETTINGSMANAGER.LOCK_DOWN_MODES.NONE))
		{
			chkLockdown.setChecked(false);
		}
		else
		{
			chkLockdown.setChecked(true);
		}
		
		CheckBox chkFullScreen = (CheckBox) findViewById(R.id.engineer_chkFullScreen);
		chkFullScreen.setEnabled(false);
		chkFullScreen.setChecked(SETTINGSMANAGER.isFullScreen(this));
		
		CheckBox chkDebugLog = (CheckBox) findViewById(R.id.engineer_chkLogFile);
		chkDebugLog.setEnabled(false);
        chkDebugLog.setChecked(SETTINGSMANAGER.logDebug());

		CheckBox chkFixedLocation = (CheckBox) findViewById(R.id.engineer_chkFixedLocation);

		CheckBox chkFireData = (CheckBox) findViewById(R.id.engineer_chkFireData);
		chkFireData.setEnabled(true);
		chkFireData.setChecked(SETTINGSMANAGER.SETTINGS.FIRE_DATA.parseBoolean(this));
		chkFireData.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b)
			{
				SETTINGSMANAGER.SETTINGS.FIRE_DATA.putValue(EngineerModeMenu.this, b);
			}
		});
				
		Boolean demoMode = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.IS_DEMO_MODE.getValue(this));
		//Boolean lockdown = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.LOCK_DOWN.getValue(this));
		//Boolean fullScreen = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.FULL_SCREEN.getValue(this));
		//Boolean debugMode = false;
		//Boolean fixedLocation = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_FIXED_LOCATION.getValue(this));
		
		chkDemoMode.setChecked(demoMode);

		chkFixedLocation.setVisibility(View.GONE);
		
		chkDemoMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					String title="Demo Mode";
					String message="Demo mode restricts you to logging in with Driver 199 - Are you sure you wish to enable this setting? Do not do this unless you have been advised to do so by a member of Cab Despatch Support";
					
					Dialog_MsgBox confirm = new Dialog_MsgBox(EngineerModeMenu.this, title, message, null, _SHOWBUTTONS.YESNO);
					
					confirm.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
					{
						
						@Override
						public void ButtonPressed(_BUTTON _button)
						{
							switch (_button)
							{
							case NO:
								buttonView.setChecked(false);
								break;
							case OK:
								//never gonna happen
								break;
							case YES:
								SETTINGSMANAGER.SETTINGS.IS_DEMO_MODE.putValue(EngineerModeMenu.this, String.valueOf(true));
								break;
							}
							
						}
					});
					
					confirm.show();
				}
				else
				{
					SETTINGSMANAGER.SETTINGS.IS_DEMO_MODE.putValue(EngineerModeMenu.this, String.valueOf(false));
				}
				
				
			}
		});
		
		/*
		chkLockdown.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean _checked) 
			{
				SETTINGSMANAGER.SETTINGS.LOCK_DOWN.putValue(EngineerModeMenu.this, String.valueOf(_checked));
			}});
		
		chkFullScreen.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean _checked) 
			{
				SETTINGSMANAGER.SETTINGS.FULL_SCREEN.putValue(EngineerModeMenu.this, String.valueOf(_checked));
			}});
		*/
		
		chkDebugLog.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean _checked) 
			{
				
			}});

        /*
		chkFixedLocation.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean _checked) 
			{
				SETTINGSMANAGER.SETTINGS.USE_FIXED_LOCATION.putValue(EngineerModeMenu.this, String.valueOf(_checked));
			}});
		*/
		
		Button btnDebug = (Button) this.findViewById(R.id.engineermode_btnDebug);
		btnDebug.setText("Job\nHistory");
		btnDebug.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(EngineerModeMenu.this, ViewJobHistory.class);
                Globals.StartActivity(EngineerModeMenu.this, i);

            }
		});
		btnDebug.setVisibility(View.GONE);

        EditText txtLoc1Lat = (EditText) this.findViewById(R.id.txt_loc1_lat);
        EditText txtLoc1Lon = (EditText) this.findViewById(R.id.txt_loc1_lon);
        EditText txtLoc2Lat = (EditText) this.findViewById(R.id.txt_loc2_lat);
        EditText txtLoc2Lon = (EditText) this.findViewById(R.id.txt_loc2_lon);

        txtLoc1Lat.setText(SETTINGSMANAGER.SETTINGS.FIXED_LOC_1_LAT.getValue(this));
        txtLoc1Lon.setText(SETTINGSMANAGER.SETTINGS.FIXED_LOC_1_LON.getValue(this));
        txtLoc2Lat.setText(SETTINGSMANAGER.SETTINGS.FIXED_LOC_2_LAT.getValue(this));
        txtLoc2Lon.setText(SETTINGSMANAGER.SETTINGS.FIXED_LOC_2_LON.getValue(this));

        txtLoc1Lat.setOnFocusChangeListener(new latLonBoxFocusListener());
        txtLoc2Lat.setOnFocusChangeListener(new latLonBoxFocusListener());
        txtLoc1Lon.setOnFocusChangeListener(new latLonBoxFocusListener());
        txtLoc2Lon.setOnFocusChangeListener(new latLonBoxFocusListener());


		
	}


    private class latLonBoxFocusListener implements View.OnFocusChangeListener
    {

        @Override
        public void onFocusChange(View view, boolean b)
        {
            if(!(b))
            {
                //we've lost focus
                EditText t = (EditText) view;

                String setting = t.getTag().toString();
                SETTINGSMANAGER.putRaw(EngineerModeMenu.this, setting, t.getText().toString());
            }
        }
    }



    public void btnManageApps_Click(View v)
	{
		Intent intentSettings = new Intent();
		intentSettings.setAction(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
        Globals.StartActivity(this, intentSettings);
    }
	
	public void btnInstallAPK_Click(View v)
	{
		Intent intent = new Intent(this, FileBrowser.class);
		startActivityForResult(intent, 1);
	}
	
	public void btnRootUninstall_Click(View v)
	{
		try
		{
			Intent intent = getPackageManager().getLaunchIntentForPackage("com.rootuninstaller.free");
            Globals.StartActivity(this, intent);
        }
		catch(Exception ex)
		{
			Toast.makeText(this, "com.rootunistaller.free not found", Toast.LENGTH_LONG).show();
		}
	}
	
	public void btnPlotTool_Click(View v)
	{
		Intent intent = new Intent(this, AutoPlot.class);
        Globals.StartActivity(this, intent);
    }
	
	public void btnLaunchURL_Click(View v)
	{
		findViewById(R.id.engineerLayoutLaunchUrl).setVisibility(View.VISIBLE);
	}
	
	public void btnGo_Click(View v)
	{
		try
		{
			TextView t = (TextView) findViewById(R.id.engineerTxtUrl);
			String url = t.getText().toString();
			if (!(url.toLowerCase().contains("http://")))
			{
				url = "http://" + url;
			}
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
            Globals.StartActivity(this, i);
        }
		catch (Exception ex)
		{
			Toast.makeText(this, "Could not launch url", Toast.LENGTH_LONG).show();
		}
	}

	public void btnGPSTest_Click(View v)
	{
		//lazy way :)
		try
		{
			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.chartcross.gpstest");
            Globals.StartActivity(this, LaunchIntent);
        }
		catch (Exception ex)
		{
			try
			{
				String uri="market://details?id=com.chartcross.gpstest";
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                Globals.StartActivity(this, i);
            }
			catch (Exception ex2)
			{
				new Dialog_MsgBox(this, "The application is not installed, and there is no market app on this device. Please install manually").show();;
			}
		}
	}
	
	public void btnClearAGPS_Click(View v)
	{
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.sendExtraCommand(LocationManager.GPS_PROVIDER,"delete_aiding_data", null);
		Bundle bundle = new Bundle();
		locationManager.sendExtraCommand("gps", "force_xtra_injection", bundle);
		locationManager.sendExtraCommand("gps", "force_time_injection", bundle);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//only 1 request for result so no need for selection for now..
		String fileName = "";
		try
		{
			File f = new File(data.getExtras().getString("FILENAME"));
			fileName = f.getAbsolutePath();
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
            Globals.StartActivity(this, intent);
        }
		catch (Exception ex)
		{
			if (fileName.equals(""))
			{
				Toast.makeText(this, "Action cancelled", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(this, "Could not lauch install for " + fileName, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	


}
