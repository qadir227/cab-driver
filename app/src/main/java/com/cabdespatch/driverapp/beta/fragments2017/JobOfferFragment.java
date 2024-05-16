package com.cabdespatch.driverapp.beta.fragments2017;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.SOUNDMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.USERREQUESTS;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.services.DataService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Pleng on 12/02/2017.
 */

public class JobOfferFragment extends CountingFragment
{

    private static final int JOB_OFFER_TIMEOUT_SECONDS = 30;

    @Override
    protected View onCreateCountingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState)
    {
        View v = _inflater.inflate(R.layout.activity_joboffer, null);
        countdownCancelled = false;

        STATUSMANAGER.aquireLock();
        STATUSMANAGER.putBoolean(this.getContext(), STATUSMANAGER.STATUSES.JOB_ACCEPT_PENDING, false);

        cabdespatchJob j = STATUSMANAGER.getCurrentJob(this.getContext());

        this.prgCountdown = (ProgressBar) v.findViewById(R.id.frmJobOffer_prgTimeout);
        this.prgCountdown.setMax(JOB_OFFER_TIMEOUT_SECONDS);
        this.prgCountdown.setProgress(JOB_OFFER_TIMEOUT_SECONDS);

        AudioManager audio = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        this.previousMusicVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

        int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float percent = 0.85f;
        int pcVolume = (int) (maxVolume*percent);
        setMusicVolume(pcVolume);


        TextView txtPlot = (TextView) v.findViewById(R.id.frmJobOffer_lblPickupPlot);

        if(j.getShowZoneOnJobOffer())
        {
            txtPlot.setVisibility(View.VISIBLE);
            txtPlot.setText(j.getFromPlot());
        }
        else
        {
            txtPlot.setVisibility(View.GONE);
        }


        this.lblVehicleType = (TextView) v.findViewById(R.id.frmJobOffer_lblVehicleType);
        this.lblVehicleType.setText(j.getVehicleType());

        if(!(j.getVehicleType().equals("")))
        {
            this.flashVehicleType = true;
        }


        TextView txtDistance = (TextView) v.findViewById(R.id.frmJobOffer_lblJobDistance);
        String distance = j.getDistance();
        if(distance.startsWith("-")) { distance = "UNKNOWN"; }
        txtDistance.setText("Distance: " + distance);

        TextView txtGPSPlot = (TextView) v.findViewById(R.id.frmJobOffer_lblGPSPlot);
        txtGPSPlot.setText("GPS Plot: " + STATUSMANAGER.getCurrentLocation(this.getContext()).getPlot().getLongName());

        this.btnAccept = (ImageButton) v.findViewById(R.id.frmJobOffer_btnAccept);
        this.btnAccept.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                JobOfferFragment.this.acceptJob();
            }
        });



        this.btnReject = (ImageButton) v.findViewById(R.id.frmJobOffer_btnReject);
        this.btnReject.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                JobOfferFragment.this.rejectJob();
            }
        });


        int detailsCount = 0;
        if(SETTINGSMANAGER.SETTINGS.JOB_OFFER_DESTINATION_PLOT.parseBoolean(getContext()))
        {
            View vv = v.findViewById(R.id.layDestinationPlot);
            vv.setVisibility(View.VISIBLE);

            TextView tt = v.findViewById(R.id.lblDestinationPlot);
            tt.setText(j.getToPlot());
            detailsCount += 1;
        }
        if(SETTINGSMANAGER.SETTINGS.JOB_OFFER_FARE.parseBoolean(getContext()))
        {
            View vv = v.findViewById(R.id.layFare);
            vv.setVisibility(View.VISIBLE);

            TextView tt = v.findViewById(R.id.lblFare);
            tt.setText(j.getPriceDisplay());
            detailsCount += 1;
        }
        if(detailsCount==2)
        {
            View vvv = v.findViewById(R.id.laySpacer);
            vvv.setVisibility(View.VISIBLE);
        }


        SOUNDMANAGER.playJobOfferSound(this.getContext());

        return v;
    }


    ProgressBar prgCountdown;

    ImageButton btnAccept;
    ImageButton btnReject;
    TextView lblVehicleType;
    private boolean countdownCancelled;

    boolean flashVehicleType;

    Integer previousMusicVolume = null;





    private class SendAccept extends TimerTask
    {

        @Override
        public void run()
        {
            try
            {
                Boolean pendingAccept = STATUSMANAGER.getBoolean(JobOfferFragment.this.getContext(), STATUSMANAGER.STATUSES.JOB_ACCEPT_PENDING);
                if(pendingAccept)
                {
                    BROADCASTERS.AcceptJob(JobOfferFragment.this.getContext());

                    Timer t = new Timer();
                    t.schedule(new SendAccept(), 5 * 1000);
                }
            }
            catch (NullPointerException ex)
            {
                //possible that the job has been accepted and the activity is null
                //therefore sending 'this' into STATUSMANAGER.getCurrentJob
                //could theoritically thrown a null pointer exception

            }
        }
    }

    protected void acceptJob()
    {
        STATUSMANAGER.releaseLock(JobOfferFragment.this.getContext());
        this.showPleaseWait();
        finishCounting();

        SOUNDMANAGER.stopCurrentSound(getContext());

        STATUSMANAGER.putBoolean(JobOfferFragment.this.getContext(), STATUSMANAGER.STATUSES.JOB_ACCEPT_PENDING, true);
        Timer t = new Timer();
        t.schedule(new SendAccept(), 1);


		/* this was really, really dumb!


		class softkill extends Thread
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(30000);
				}
				catch(Exception ex)
				{
					
				}
				
				if(isLive)
				{
					Intent logoffintent = new Intent(BROADCASTERS.USER_REQUEST);
					logoffintent.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
					logoffintent.putExtra(DataService._MESSAGEDATA, USERREQUESTS.SPECIAL_LOGOUT);
					
					this.sendBroadcast(logoffintent);
				}
			}
		}
		
		new softkill().start();

		 */

    }

    private void showPleaseWait()
    {
        STATUSMANAGER.setAppState(this.getContext(), STATUSMANAGER.APP_STATE.PAUSED);

        this.btnAccept.setVisibility(View.INVISIBLE);
        this.btnReject.setVisibility(View.INVISIBLE);

        TableLayout jobdetails = (TableLayout) JobOfferFragment.this.getView().findViewById(R.id.actualcontent);
        jobdetails.setVisibility(View.INVISIBLE);

        LinearLayout pleasewait = (LinearLayout) JobOfferFragment.this.getView().findViewById(R.id.frmJobOffer_layPleaseWait);
        pleasewait.setVisibility(View.VISIBLE);
    }

    @Override
    protected Settable getCounterSave()
    {
        return STATUSMANAGER.STATUSES.COUNTER_JOB_OFFER;
    }



    @Override
    protected void finishCounting()
    {
        super.finishCounting();
        countdownCancelled = true;
    }

    protected void rejectJob()
    {
        cabdespatchJob j = STATUSMANAGER.getCurrentJob(this.getContext());
        finishCounting();
        //can't do it here... jobId is needed by cabdespatchMEssageSystem
        //do it in data service after message has been sent
        //STATUSMANAGER.setCurrentJob(this, new cabdespatchJob(false));
        STATUSMANAGER.releaseLock(this.getContext());

        this.showPleaseWait();
        if(j.getJobStatus().equals(cabdespatchJob.JOB_STATUS.UNDER_OFFER))
        {
            SOUNDMANAGER.stopCurrentSound(this.getContext());
            BROADCASTERS.RejectJob(this.getContext());

            //Globals.CrossFunctions.Back(this.getContext());
        }
        else
        {
            //BROADCASTERS.DriverMessage(this, "CABDESPATCH: REJAVD");
        }


    }
    

    private void setMusicVolume(int _volume)
    {
        if(!(isDebug()))
        {
            AudioManager audio = (AudioManager) this.getContext().getSystemService(Context.AUDIO_SERVICE);
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, _volume, 0);
        }
    }


    private void cleanUp()
    {
        if(!(this.previousMusicVolume == null))
        {
            setMusicVolume(this.previousMusicVolume);
        }
    }

    @Override
    public void onTick(long _millis)
    {
        if(!(countdownCancelled))
        {
            int secondsUsed = getSeconds(_millis);
            if(secondsUsed >= JOB_OFFER_TIMEOUT_SECONDS)
            {
                finishCounting();

                STATUSMANAGER.releaseLock(JobOfferFragment.this.getContext());

                JobOfferFragment.this.prgCountdown.setProgress(0);

                JobOfferFragment.this.btnAccept.setVisibility(View.INVISIBLE);
                JobOfferFragment.this.btnReject.setVisibility(View.INVISIBLE);

                Intent timeoutintent = new Intent(BROADCASTERS.USER_REQUEST);
                timeoutintent.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
                timeoutintent.putExtra(DataService._MESSAGEDATA, USERREQUESTS.JOB_TIMEOUT);
                JobOfferFragment.this.getContext().sendBroadcast(timeoutintent);

                showPleaseWait();
            }
            else
            {
                JobOfferFragment.this.prgCountdown.setProgress(JOB_OFFER_TIMEOUT_SECONDS - secondsUsed);

                //CLAY need to only do this at proper interval...
                if(JobOfferFragment.this.lblVehicleType.getVisibility()==View.VISIBLE)
                {
                    JobOfferFragment.this.lblVehicleType.setVisibility(View.INVISIBLE);
                }
                else
                {
                    JobOfferFragment.this.lblVehicleType.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    protected void onBroadcastReceived(Context _context, Intent _intent)
    {
        //do nothing
    }

    @Override
    protected void onStopping()
    {
        cleanUp();
    }

    @Override
    public void onResuming()
    {

    }


}
