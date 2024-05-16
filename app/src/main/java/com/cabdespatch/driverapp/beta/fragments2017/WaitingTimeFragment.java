package com.cabdespatch.driverapp.beta.fragments2017;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.USERREQUESTS;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.services.DataService;


/**
 * Created by Pleng on 16/02/2017.
 */

public class WaitingTimeFragment extends CountingFragment
{
    private boolean stopCounting;

    @Override
    protected Settable getCounterSave()
    {
        return STATUSMANAGER.STATUSES.COUNTER_WAITING_TIME;
    }

    @Override
    protected void finishCounting()
    {
        super.finishCounting();
        stopCounting = true;
    }

    @Override
    protected View onCreateCountingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState)
    {
        View v = _inflater.inflate(R.layout.activity_waitingtime, null);
        stopCounting = false;

        this.waitingTimeStart = STATUSMANAGER.getLong(this.getContext(), STATUSMANAGER.STATUSES.CURRENT_WAITING_TIME_START);
        this.lblAdditionalTime = (TextView) v.findViewById(R.id.frmWaitingTime_lblTimeElapsed);
        this.lblAdditionalTime.setVisibility(View.INVISIBLE);

        int totalSecondsSoFar = STATUSMANAGER.getCurrentJob(this.getContext()).getWaitingTime();
        int totalMinsSoFar = (totalSecondsSoFar / 60);
        int totalRemainSecs = (totalSecondsSoFar % 60);

        TextView lblCurrentAllocation = (TextView) v.findViewById(R.id.frmWaitingTime_lblCurrentAllocation);
        lblCurrentAllocation.setText(String.format("This job has presently accumulated %d minutes and %02d seconds of waiting time.", totalMinsSoFar,totalRemainSecs));

        btnAdd = (ImageButton) v.findViewById(R.id.frmWaitingTime_btnAdd);
        btnCancel = (ImageButton) v.findViewById(R.id.frmWaitingTime_btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                finishCounting();
                String[] waittime = WaitingTimeFragment.this.lblAdditionalTime.getText().toString().split(":");
                int waitingsSecs = (Integer.valueOf(waittime[0]) * 60) + Integer.valueOf(waittime[1]);

                cabdespatchJob j = STATUSMANAGER.getCurrentJob(WaitingTimeFragment.this.getContext());
                j.addWaitingTime(waitingsSecs);

                STATUSMANAGER.setCurrentJob(WaitingTimeFragment.this.getContext(), j);

                STATUSMANAGER.setAppState(WaitingTimeFragment.this.getContext(), STATUSMANAGER.APP_STATE.ON_JOB);

                Intent addwt = new Intent(BROADCASTERS.USER_REQUEST);
                addwt.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
                addwt.putExtra(DataService._MESSAGEDATA, USERREQUESTS.WAITING_TIME_END);

                WaitingTimeFragment.this.getContext().sendBroadcast(addwt);

                btnAdd.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);

                Globals.CrossFunctions.Back(v);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                finishCounting();

                STATUSMANAGER.setAppState(WaitingTimeFragment.this.getContext(), STATUSMANAGER.APP_STATE.ON_JOB);

                btnAdd.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);

                Globals.CrossFunctions.Back(v);
            }
        });

        return v;
    }

    @Override
    public void onTick(long _milliseconds)
    {
        long secondsUsed = getSeconds(_milliseconds);

        int minutesUsed = (int) Math.floor(secondsUsed / 60);
        int secondsRemain = (int) Math.floor(secondsUsed % 60);

        this.lblAdditionalTime.setVisibility(View.VISIBLE);
        this.lblAdditionalTime.setText(Integer.toString(minutesUsed) + ":" + String.format("%02d", secondsRemain));
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

    private long waitingTimeStart;
    private TextView lblAdditionalTime;

    private ImageButton btnAdd;
    private ImageButton btnCancel;



    @Override
    public void onSpeedChanged(Boolean _overSafetyPanelThreshold)
    {
        super.onSpeedChanged(_overSafetyPanelThreshold);
        if(_overSafetyPanelThreshold)
        {
            //we're above saftey panel threshold
            //finish waiting time.
            Intent addwt = new Intent(BROADCASTERS.USER_REQUEST);
            addwt.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
            addwt.putExtra(DataService._MESSAGEDATA, USERREQUESTS.WAITING_TIME_AUTO_END);

            WaitingTimeFragment.this.getContext().sendBroadcast(addwt);

            btnCancel.setVisibility(View.INVISIBLE);
        }
    }

}
