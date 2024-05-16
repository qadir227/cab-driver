package com.cabdespatch.driverapp.beta.fragments.settings;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobScreenStart extends SettingsFragment
{

    private Settable setting;
    private boolean innitComplete = false;

    public JobScreenStart()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateSettingsFragmentView(LayoutInflater inflater)
    {
        View v = inflater.inflate(R.layout.fragment_settings_job_screen_start, null);
        this.setting = SETTINGSMANAGER.SETTINGS.JOB_SCREEN_FIRST_SLIDE;

        RadioGroup grp = (RadioGroup) v.findViewById(R.id.RadioGroup);
        AnyActivity a = (AnyActivity) this.getActivity();

        for(View child:a.getViews(grp))
        {
            RadioButton b = (RadioButton) child;
            String tag = b.getTag().toString();
            if(tag.equals(this.setting.getValue(this.getActivity())))
            {
                b.setChecked(true);
            }

            b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if(isChecked)
                    {
                        if(JobScreenStart.this.innitComplete)
                        {
                            String newValue = buttonView.getTag().toString();
                            JobScreenStart.this.setting.putValue(JobScreenStart.this.getContext(), newValue);
                        }
                    }
                }

            });
        }

        this.innitComplete = true;

        return v;
    }


}
