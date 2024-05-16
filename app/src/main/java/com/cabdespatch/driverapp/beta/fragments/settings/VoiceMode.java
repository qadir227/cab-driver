package com.cabdespatch.driverapp.beta.fragments.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;

public class VoiceMode extends SettingsFragment
{
	
	
	@Override
	public View onCreateSettingsFragmentView(LayoutInflater inflater)
	{
		View v = inflater.inflate(R.layout.fragment_settings_voicemode, null);
		
		final Settable setting = SETTINGSMANAGER.SETTINGS.USE_VOICE_REQUEST;
		
		RadioButton radDialOffice = (RadioButton) v.findViewById(R.id.fragSettings_voiceMode_callOffice);
		RadioButton radSendRequest = (RadioButton) v.findViewById(R.id.fragSettings_voiceMode_useRequest);
		
		Boolean useRequest = Boolean.valueOf(setting.getValue(VoiceMode.this.getContext()));
		
		if(useRequest)
		{
			radSendRequest.setChecked(true);
		}
		else
		{
			radDialOffice.setChecked(true);
		}
		
		radDialOffice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					setting.putValue(VoiceMode.this.getContext(), String.valueOf(false));
				}
			}
		});
		
		radSendRequest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					setting.putValue(VoiceMode.this.getContext(), String.valueOf(true));
				}
			}
		});
		
				
		return v;
	}

}
