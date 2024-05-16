package com.cabdespatch.driverapp.beta.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.CabDespatchNetwork;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.cabdespatchGPS;
import com.cabdespatch.driverapp.beta.fragments2017.TickingFragment;
import com.cabdespatch.driverapp.beta.services.DataService;

public class StatusBar extends TickingFragment
{
	
	private TextView lblStatusBar;
	private TextView lblMockLocations;
	private ImageButton btnNetwork;
	private ImageButton btnGPS;
	private ImageButton btnBatteryStatus;
    private ProgressBar prgNetwork;
	private boolean debug = false;

    public static final Integer AFFERMATIVE_MESSAGE_DELAY = 2000;

    private View rootView;



    public static class PowerUtil
	{
	    public static boolean isConnected(Context context) 
	    {
	        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
	        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
	    }
	    
	    public static int getBatteryLevel(Context context) 
	    {
	        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
	        return level;
	    }
	    
	    
	}

	@Override
    public void onViewCreated(View v, Bundle savedInstanceState)
	{

        if(this.debug)
        {
            v.setBackgroundColor(getResources().getColor(R.color.Pink));
        }
        /*Boolean fixedGPS =Boolean.valueOf( SETTINGSMANAGER.SETTINGS.USE_FIXED_LOCATION.getValue(this.getContext()));
        if(fixedGPS)
        {
            v.setBackgroundColor(getResources().getColor(color.Blue));
        }*/


        this.lblStatusBar = (TextView) v.findViewById(R.id.fragStatus_lblStatusBar);
        //this.lblStatusBar.setHorizontallyScrolling(true);


        this.btnGPS = (ImageButton) v.findViewById(R.id.fragStatus_btnStatusGPS);
        this.btnGPS.setEnabled(false);

        this.btnNetwork = (ImageButton) v.findViewById(R.id.fragStatus_btnStatusData);
        Boolean hasNetwork = (STATUSMANAGER.getBoolean(this.getContext(), STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION));
        this.btnNetwork.setEnabled(hasNetwork);

        this.btnBatteryStatus = (ImageButton) v.findViewById(R.id.fragStatus_btnBatteryStatus);
        this.btnBatteryStatus.setVisibility(View.GONE);

        this.prgNetwork = (ProgressBar) v.findViewById(R.id.fragStatus_prgStatusData);

        this.updateBatteryMeter(PowerUtil.getBatteryLevel(this.getContext()), PowerUtil.isConnected(this.getContext()));

        this.lblMockLocations = v.findViewById(R.id.lbl_mock_locations);

        if((STATUSMANAGER.hasDebugConnector(this.getContext())) && (Globals.isDebug(getContext())))
		//if((true) && (BuildConfig.DEBUG))
        {
            View grp = v.findViewById(R.id.layFixedLocations);
            grp.setVisibility(View.VISIBLE);

            CompoundButton radFl1 = (CompoundButton) v.findViewById(R.id.btn_fixed_loc_1);
            CompoundButton radFl2 = (CompoundButton) v.findViewById(R.id.btn_fixed_loc_2);
            CompoundButton radGps = (CompoundButton) v.findViewById(R.id.btn_loc_gps);

            Integer src = SETTINGSMANAGER.SETTINGS.LOCATION_REPORT_SOURCE.parseInteger(v.getContext());
            switch (src)
            {
                case 1:
                    radFl1.setChecked(true);
                    break;
                case 2:
                    radFl2.setChecked(true);
                    break;
                default:
                    radGps.setChecked(true);
                    break;
            }

            radFl1.setOnCheckedChangeListener(new locationButtonListener());
            radFl2.setOnCheckedChangeListener(new locationButtonListener());
            radGps.setOnCheckedChangeListener(new locationButtonListener());
        }
        else
        {
            SETTINGSMANAGER.SETTINGS.LOCATION_REPORT_SOURCE.putValue(getContext(), "0");
        }


	}

    private class locationButtonListener implements CompoundButton.OnCheckedChangeListener
    {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean _isChecked)
            {
                if(_isChecked)
                {
                    int putVal = 0;
                    if(compoundButton.getId()==R.id.btn_fixed_loc_1)
                    {
                        putVal = 1;
                    }
                    else if (compoundButton.getId()==R.id.btn_fixed_loc_2)
                    {
                        putVal = 2;
                    }
                    else if (compoundButton.getId()==R.id.btn_loc_gps)
                    {
                        //CLAY just for testing...
                        putVal = 0;
                    }

                    SETTINGSMANAGER.SETTINGS.LOCATION_REPORT_SOURCE.putValue(compoundButton.getContext(), putVal);

					DataService.pushFakeLocation();
				}

            }
    }
	
	@Override
	public void onResuming()
	{

		String status = STATUSMANAGER.getStatusBarText(this.getActivity().getBaseContext());
		this.lblStatusBar.setText(status);
		
		if(STATUSMANAGER.getBoolean(this.getActivity().getBaseContext(), STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION))
		{
			this.btnNetwork.setEnabled(true);
		}
		else
		{
			this.btnNetwork.setEnabled(false);
		}

	}

	@Override
	public void onTick()
	{

		switch (CabDespatchNetwork.getConnectionStatus())
		{
			case CONNECTED:
				this.btnNetwork.setEnabled(true);
				this.btnNetwork.setVisibility(View.VISIBLE);
				this.prgNetwork.setVisibility(View.INVISIBLE);
				break;
			case CONNECTING:
				StatusBar.this.btnNetwork.setEnabled(false);
				StatusBar.this.btnNetwork.setVisibility(View.INVISIBLE);
				StatusBar.this.prgNetwork.setVisibility(View.VISIBLE);
				break;
			case DISCONNECTED:
				this.btnNetwork.setEnabled(false);
				this.btnNetwork.setVisibility(View.VISIBLE);
				this.prgNetwork.setVisibility(View.INVISIBLE);
				break;
		}

		if(cabdespatchGPS.isLocationFromMockSource())
		{
			lblMockLocations.setVisibility(View.VISIBLE);
		}
		else
		{
			lblMockLocations.setVisibility(View.GONE);
		}
		//lblMockLocations.setVisibility((cabdespatchGPS.isLocationFromMockSource()?View.VISIBLE:View.GONE));
	}


	private void handleStatusUpdate()
	{
		this.lblStatusBar.setText(STATUSMANAGER.getStatusBarText(this.getContext()));
	}

	private void handleDataMessage(String _message)
	{
		if(_message.equals(DataService._ACTIONS.NETWORK_ON))
		{

                //dealt with in tick now
		}
		else if(_message.equals(DataService._ACTIONS.NETWORK_OFF))
		{

			//delt with in tick now
		}
		else if(_message.equals(DataService._ACTIONS.NETWORK_RECONNECTING))
		{
			//delt with in tick now
		}
	}

	private void handleBatteryMessage(Intent intent)
	{
		int batLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		int batStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
		StatusBar.this.updateBatteryMeter(batLevel, batStatus);
	}


	@Override
	protected void onBroadcastReceived(Context _context, Intent _intent)
	{
		String message = _intent.getStringExtra(DataService._MESSAGEDATA);

		if(_intent.getAction().equals(BROADCASTERS.STATUS_UPDATE)) { this.handleStatusUpdate(); }
		else if (_intent.getAction().equals(BROADCASTERS.DATA)) { this.handleDataMessage(message);}
		else if (_intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) { this.handleBatteryMessage(_intent); }
		else if (_intent.getAction().equals(BROADCASTERS.LOCATION_UPDATED))
		{
			StatusBar.this.btnGPS.setEnabled(true);
		}

		//done on tic now...
		//else if (_intent.getAction().equals(BROADCASTERS.GPS_NO_FIX)) { StatusBar.this.btnGPS.setEnabled(false); }
	}

	@Override
	protected void onStopping()
	{

	}

	@Override
	protected View onCreateTickingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState)
	{
		if(rootView==null)
		{
			rootView = _inflater.inflate(R.layout.fragment_statusbar, _container, false);
		}
		return rootView;
	}


	
	public void updateBatteryMeter(int _percentage, boolean _isCharging)
	{
		
		int BatteryLevel = _percentage;
		boolean charging = _isCharging;
		
		//Log.w("Battery", String.valueOf(BatteryLevel));
		
		if (BatteryLevel >=98 )
		{
			if(charging)
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_full_charge);
			}
			else
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_full);
			}
		}
		else if (BatteryLevel >= 60 )
		{
			if(charging)
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_high_charge);
			}
			else
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_high);
			}
		}
		else if (BatteryLevel >= 40)
		{
			if(charging)
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_medium_charge);
			}
			else
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_medium);
			}
		}
		else if (BatteryLevel >= 10)
		{
			if(charging)
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_low_charge);
			}
			else
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_low);
			}
		}
		else 
		{
			if(charging)
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_critica_chargel);
			}
			else
			{
				btnBatteryStatus.setImageResource(R.drawable.n_battery_critical);
			}
		}

	}
	
	public void updateBatteryMeter(int _percentage, int _state)
	{
		this.btnBatteryStatus.setVisibility(View.VISIBLE);
		
		int BatteryStatus = _state;
				
		boolean charging=false;
		
		if(BatteryStatus==BatteryManager.BATTERY_STATUS_CHARGING)
		{
			charging = true;
		}
		
		this.updateBatteryMeter(_percentage, charging);
	}
}
