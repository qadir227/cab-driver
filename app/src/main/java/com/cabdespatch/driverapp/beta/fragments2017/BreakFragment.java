package com.cabdespatch.driverapp.beta.fragments2017;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.USERREQUESTS;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.services.DataService;

import java.text.DecimalFormat;

/**
 * Created by Pleng on 15/02/2017.
 */

public class BreakFragment extends CountingFragment
{

    private long currentBreakStart = -1;

    public BreakFragment()
    {
        this.format = new DecimalFormat("00");
    }

    @Override
    public void onTick(long _millis)
    {
        updateTimeRemaining(_millis);
    }

    @Override
    protected void onBroadcastReceived(Context _context, Intent _intent)
    {
        //do nothing
    }

    @Override
    protected void onStopping()
    {

    }

    @Override
    public void onResuming()
    {

    }

    @Override
    protected Settable getCounterSave()
    {
        return STATUSMANAGER.STATUSES.COUNTER_BREAK;
    }

    @Override
    protected View onCreateCountingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState)
    {
        View v = _inflater.inflate(R.layout.activity_break, null);

        this.txtBreak = (TextView) v.findViewById(R.id.frmBreak_lblTimeRemaining);
        txtBreak.setVisibility(View.INVISIBLE);
        this.breakTotalMinutesAvailable = Integer.valueOf(SETTINGSMANAGER.get(this.getContext(), SETTINGSMANAGER.SETTINGS.TOTAL_BREAK_MINUTES_AVAILABLE));

        this.currentBreakStart = STATUSMANAGER.getLong(this.getContext(), STATUSMANAGER.STATUSES.CURRENT_BREAK_START);
        if(previousBreakUsedSeconds == -1)
        {
            this.previousBreakUsedSeconds = STATUSMANAGER.getInt(this.getContext(), STATUSMANAGER.STATUSES.TOTAL_BREAK_USED_SECONDS);
        }

        this.btnBack = (ImageButton) v.findViewById(R.id.frmbreak_btnBack);
        this.btnBack.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                BreakFragment.this.endBreak(v, false);
            }
        });

        return v;
    }


    private int breakTotalMinutesAvailable = -1;
    private int previousBreakUsedSeconds = -1;
    DecimalFormat format;

    private TextView txtBreak;
    private ImageButton btnBack;



    protected boolean updateTimeRemaining(long _timeUsed)
    {

        long timeUsed = _timeUsed;
        int secondsUsed = getSeconds(_timeUsed);
        secondsUsed = secondsUsed + this.previousBreakUsedSeconds;

        int totalSecondsAvailable = this.breakTotalMinutesAvailable * 60;
        int currentSecondsAvailable = totalSecondsAvailable - secondsUsed;

        int remainingMinutes = (int) Math.floor(currentSecondsAvailable / 60);
        int remainingSeconds = currentSecondsAvailable % 60;

        String s = ((String.valueOf(remainingMinutes) + ":" + (format.format(remainingSeconds))));

        if((remainingMinutes<=0) && (remainingSeconds<=0))
        {
            cdToast.show(this.getContext(), "No more break remaining", Toast.LENGTH_LONG);
            //this.endBreak(this.btnBack, _firstRun);
            btnBack.setVisibility(View.INVISIBLE);
            BROADCASTERS.Logout(this.getContext(), "BREAK OVER","My break has expired");

            return false;
        }
        else
        {
            txtBreak.setVisibility(View.VISIBLE);
            txtBreak.setText(s);
            return true;
        }
    }

    protected void endBreak(View v, Boolean _firstRun)
    {

        long currentTime = System.currentTimeMillis();
        long timeUsed = currentTime - this.currentBreakStart;
        int secondsUsed = (int) Math.floor(timeUsed / 1000);
        secondsUsed = secondsUsed + this.previousBreakUsedSeconds;

        int totalSecondsUsed = this.previousBreakUsedSeconds + secondsUsed;
        STATUSMANAGER.putInt(this.getContext(), STATUSMANAGER.STATUSES.TOTAL_BREAK_USED_SECONDS, totalSecondsUsed);
        //normally this should be handled in the dataservice RunTimer (and is for the sake of consistency)
        //but the USERREQUEST.Back handler checks the status of the Boolean value to decide whether to
        //show the break screen or not. So it needs to be set here first...
        STATUSMANAGER.setAppState(v.getContext(), STATUSMANAGER.APP_STATE.LOGGED_ON);

        Intent i = new Intent(BROADCASTERS.USER_REQUEST);
        i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
        i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.BREAK_END);

        this.finishCounting();

        this.getContext().sendBroadcast(i);
        Globals.CrossFunctions.Back(v);


    }
}
