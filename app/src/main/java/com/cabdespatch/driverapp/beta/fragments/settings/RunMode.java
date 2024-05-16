package com.cabdespatch.driverapp.beta.fragments.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;

public class RunMode extends SettingsFragment
{

    private Settable setting;

    @Override
    public View onCreateSettingsFragmentView(LayoutInflater inflater)
    {
        View v = inflater.inflate(R.layout.fragment_settings_runmode, null);

        this.setting = SETTINGSMANAGER.SETTINGS.RUN_MODE;

        RadioButton btnShort = (RadioButton) v.findViewById(R.id.fragSettings_runmode_short);
        btnShort.setTag(String.valueOf(SETTINGSMANAGER.RunModes.SHORT));
        btnShort.setOnCheckedChangeListener(this.onCheckChanged());

        RadioButton btnNormal = (RadioButton) v.findViewById(R.id.fragSettings_runmode_normal);
        btnNormal.setTag(String.valueOf(SETTINGSMANAGER.RunModes.NORMAL));
        btnNormal.setOnCheckedChangeListener(this.onCheckChanged());

        RadioButton btnLong = (RadioButton) v.findViewById(R.id.fragSettings_runmode_long);
        btnLong.setTag(String.valueOf(SETTINGSMANAGER.RunModes.LONG));
        btnLong.setOnCheckedChangeListener(this.onCheckChanged());

        Integer distance = Integer.valueOf(this.setting.getValue(this.getContext()));

        if (distance==SETTINGSMANAGER.RunModes.SHORT)
        {
            btnShort.setChecked(true);
        }
        else if (distance==SETTINGSMANAGER.RunModes.NORMAL)
        {
            btnNormal.setChecked(true);
        }
        else if (distance==SETTINGSMANAGER.RunModes.LONG)
        {
            btnLong.setChecked(true);
        }
        else
        {
            ErrorActivity.handleError(this.getContext(), new ErrorActivity.ERRORS.INVALID_RUN_MODE(distance.toString()));
        }

        return v;
    }

    private CompoundButton.OnCheckedChangeListener onCheckChanged()
    {
        return new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    String value =  buttonView.getTag().toString();
                    RunMode.this.setting.putValue(RunMode.this.getContext(), value);
                }

            }
        };
    }

}
