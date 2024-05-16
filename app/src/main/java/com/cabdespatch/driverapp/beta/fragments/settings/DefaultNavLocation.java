package com.cabdespatch.driverapp.beta.fragments.settings;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;

public class DefaultNavLocation extends SettingsFragment
{

    @Override
    public View onCreateSettingsFragmentView(LayoutInflater inflater)
    {
        View v = inflater.inflate(R.layout.fragment_settings_navlocation, null);

        String val = SETTINGSMANAGER.SETTINGS.NAVIGATION_LOCATION.getValue(this.getContext());
        if(val.equals(Settable.NOT_SET)) { val = ""; }

        EditText txtNavLoc = (EditText) v.findViewById(R.id.fragSettings_NavLocation_txtNavLocations);
        txtNavLoc.setText(val);
        txtNavLoc.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable txt)
            {
                if(!(txt.toString().trim().equals("")))
                {
                    SETTINGSMANAGER.SETTINGS.NAVIGATION_LOCATION.putValue(DefaultNavLocation.this.getContext(), txt.toString());
                }

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