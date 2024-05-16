package com.cabdespatch.driverapp.beta.activities2017;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;

public class Settings extends AnyActivity
{

    @Override
	public void onCreate(Bundle _savedState)
	{
		super.onCreate(_savedState);
		this.setContentView(R.layout.activity_settings);
		this.setBackground();
		
		ImageButton btnBack = (ImageButton) this.findViewById(R.id.frmSettings_btnBack);
		btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Settings.this.onBackPressed();
            }
        });
		
		ViewFlipper flip = (ViewFlipper) this.findViewById(R.id.frmSettings_flip);
		flip.setOnTouchListener(Globals.FlipperTouch());
		
				
		
	}

    @Override
    public void onBackPressed()
    {
        finish();
    }

	@Override
	public void onPause()
	{
		super.onPause();
		this.finish();
	}
	
	
	
}
