package com.cabdespatch.driverapp.beta.fragments2017;


import android.app.Dialog;

import com.cabdespatch.driverapp.beta.activities2017.LoggedInHost;

public abstract class LoggedInFragment extends TickingFragment
{

    protected Dialog currentDialog;

    public LoggedInFragment()
    {
        // Required empty public constructor
    }

    public void onSpeedChanged(Boolean _overSafetyPanelThreshold)
    {
        //do nothing
    }

    public LoggedInHost getHostActivity()
    {
        return (LoggedInHost) getActivity();
    }


    @Override
    protected void onStopping() {

    }
}
