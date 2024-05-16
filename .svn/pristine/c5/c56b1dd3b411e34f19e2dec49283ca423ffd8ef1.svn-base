package com.cabdespatch.driverapp.beta.fragments.settings;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;

public class OfficeNumber extends SettingsFragment
{
	
	
	@Override
	public View onCreateSettingsFragmentView(LayoutInflater inflater)
	{
		View v = inflater.inflate(R.layout.fragment_settings_officenumber, null);
		
		String val = SETTINGSMANAGER.get(OfficeNumber.this.getContext(), SETTINGSMANAGER.SETTINGS.OFFICE_NUMBER);
		
		EditText txtOffNum = (EditText) v.findViewById(R.id.fragSettings_OfficeNumber_txtOfficeNumber);
		txtOffNum.setText(val);
		txtOffNum.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable txt)
			{
				SETTINGSMANAGER.put(OfficeNumber.this.getContext(), SETTINGSMANAGER.SETTINGS.OFFICE_NUMBER, txt.toString());
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub
				
			}});
		
		return v;
	}

}
