package com.cabdespatch.driverapp.beta.fragments.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;

public class TaxiMeter extends SettingsFragment
{


    @Override
    public View onCreateSettingsFragmentView(LayoutInflater inflater)
    {
        View v = inflater.inflate(R.layout.fragment_settings_meter, null);
        CheckBox c = (CheckBox) v.findViewById(R.id.chkSettingsMeterEnabled);

        c.setEnabled(HandyTools.ActivityTools.appInstalled(getContext(), SETTINGSMANAGER.getTaxiMeterPackageName()));
        c.setEnabled(true); //CLAY

/*
        c.setChecked(SETTINGSMANAGER.SETTINGS.METER_ENABLED.parseBoolean(getContext()));
        c.setOnCheckedChangeListener(onCheckChanged());
        */

        return v;
    }

    private CompoundButton.OnCheckedChangeListener onCheckChanged()
    {
        return new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                //SETTINGSMANAGER.SETTINGS.METER_ENABLED.putValue(TaxiMeter.this.getContext(), isChecked);
            }
        };
    }


}

