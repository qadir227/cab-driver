package com.cabdespatch.driverapp.beta.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SOUNDMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.USERREQUESTS;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.services.DataService;

public class PanicActivity extends AnyActivity
{


    @Override
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		this.setContentView(R.layout.activity_panic);
		this.setBackground();

        STATUSMANAGER.aquireLock();

		SOUNDMANAGER.panic(this);
		
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
        //STATUSMANAGER.releaseLock(this);
		this.finish();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();

        //STATUSMANAGER.aquireLock();
		
		ImageButton btnBack = (ImageButton) this.findViewById(R.id.frmPanic_btnBack);
		btnBack.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
                SOUNDMANAGER.stopCurrentSound(PanicActivity.this);
                STATUSMANAGER.releaseLock(PanicActivity.this);
				STATUSMANAGER.putBoolean(v.getContext(), STATUSMANAGER.STATUSES.IS_PANIC, false);
				PanicActivity.this.onBackPressed();
			}
		});
		btnBack.setVisibility(View.VISIBLE);
		
		LinearLayout btnCall999 = (LinearLayout) this.findViewById(R.id.frmPanic_btn999);
		btnCall999.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent i999 = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:999"));
                Globals.StartActivity(PanicActivity.this, i999);
            }
		});
		
		LinearLayout btnSendCars = (LinearLayout) this.findViewById(R.id.frmPanic_btnSendCars);
		btnSendCars.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(BROADCASTERS.USER_REQUEST);
				i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
				i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.DATA_MESSAGE);
				i.putExtra(DataService._MESSAGEEXTRA, "SEND CARS PLEASE");
				
				PanicActivity.this.sendBroadcast(i);
				cdToast.show(PanicActivity.this, "Message Sent: Send Cars Please", Toast.LENGTH_LONG);
				
			}
		});
	}

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
