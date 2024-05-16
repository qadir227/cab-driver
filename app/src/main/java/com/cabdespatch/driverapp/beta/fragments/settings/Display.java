package com.cabdespatch.driverapp.beta.fragments.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;

/**
 * Created by Pleng on 02/07/2016.
 */
public class Display extends SettingsFragment
{


        @Override
        public View onCreateSettingsFragmentView(LayoutInflater inflater)
        {
            View v = inflater.inflate(R.layout.fragment_settings_display, null);
            CheckBox c = (CheckBox) v.findViewById(R.id.chkSettingsSmallScreen);

            c.setChecked(SETTINGSMANAGER.SETTINGS.SMALL_UI.parseBoolean(getContext()));
            c.setOnCheckedChangeListener(onCheckChanged());

            return v;
        }

        private CompoundButton.OnCheckedChangeListener onCheckChanged()
        {
            return new CompoundButton.OnCheckedChangeListener()
            {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    SETTINGSMANAGER.SETTINGS.SMALL_UI.putValue(Display.this.getContext(), isChecked);
                }
            };
        }

}
