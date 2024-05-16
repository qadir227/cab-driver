package com.cabdespatch.driverapp.beta.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.UnfairMeterLocal;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.fragments2017.TickingFragment;
import com.cabdespatch.driverapp.beta.pdaLocation;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;


public class FairMeterFragment extends TickingFragment
{

    private TextView fLblQickFare;
    public static final Integer ANIMATION_SPEED = 1200;
    private static final Integer SECONDS_BETWEEN_UPDATE_REQUESTS = 30;

    public FairMeterFragment()
    {

    }


    private String fMeterMode;

    @Override
    public void onCreate(Bundle _savedInstace)
    {
        super.onCreate(_savedInstace);
        this.currentCountStart = System.currentTimeMillis();

        this.fMeterMode = SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(getContext());

    }

    private long currentCountStart = -1;


    @Override
    public final void onTick()
    {

        if(this.fMeterMode.equals(SETTINGSMANAGER.METER_TYPE.NONE))
        {
            //do nothing
        }
        else
        {
            long millis = System.currentTimeMillis() - currentCountStart;
            int seconds = (int) Math.floor(millis / 1000);

            if(seconds % 2 == 0)
            {
                doUpdate();
            }


            if(seconds >= SECONDS_BETWEEN_UPDATE_REQUESTS)
            {
                if(!(isLocalMeter(getContext())))
                {
                    BROADCASTERS.meterRequestUpdate(this.getContext());
                }
                currentCountStart = System.currentTimeMillis();
            }



            if(this.oShown)
            {
                this.fLblQickFare.setVisibility(View.VISIBLE);
            }
            else
            {
                this.fLblQickFare.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onBroadcastReceived(Context _context, Intent _intent)
    {
        //do nothing
    }

    @Override
    public View onCreateTickingView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_fair_meter, null);

        this.fLblQickFare = (TextView) v.findViewById(R.id.lblFare);
        Double fare;
        if(isLocalMeter(getContext()))
        {
            if(UnfairMeterLocal.isLive())
            {
                fare = UnfairMeterLocal.getFare(getContext());
            }
            else
            {
                fare = UnfairMeterLocal.getMinFare();
            }

        }
        else
        {
            fare = STATUSMANAGER.getCurrentJob(this.getContext()).getMeterPrice();
        }

        String meterText = "";
        if(fare > 0)
        {
            meterText = getString(R.string.fair_meter_current_fare);
            meterText += (" £" + HandyTools.Strings.formatPrice(fare));
        }
        else
        {
            meterText = getString(R.string.fair_meter_awaiting_fare);
        }
        //String currentFareText = getString(R.string.fair_meter_current_fare);
        //currentFareText += (" £" + HandyTools.Strings.formatPrice(STATUSMANAGER.getCurrentJob(this.getContext()).getMeterPrice()));
        this.fLblQickFare.setText(meterText);
        this.oShown = false;

        //updateFare();
        return v;
    }

    private Double oldFare;
    private boolean doAnimations()
    {
        return false;
    }

    //CLAY
    private Boolean isLocalMeter(Context _c)
    {
        return SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(_c).equals(SETTINGSMANAGER.METER_TYPE.LOCAL_UNFAIR_METER);
    }

    private void updateFare()
    {
        cabdespatchJob j = STATUSMANAGER.getCurrentJob(getContext());
        Boolean doFareUpdate = (j.getJobStatus()== cabdespatchJob.JOB_STATUS.POB);
        if(!(doFareUpdate))
        {
            if(j.isFlagDown())
            {
                doFareUpdate = (j.getJobStatus()== cabdespatchJob.JOB_STATUS.STC);
            }
        }

        if(doFareUpdate)
        {
            Double fare;

            if(isLocalMeter(getContext()))
            {
                //pdaLocation l = STATUSMANAGER.getCurrentLocation(getContext());


                if(UnfairMeterLocal.isLive())
                {
                    fare = UnfairMeterLocal.getFare(getContext());
                }
                else
                {
                    //this should never happen because we call Start() in DataService now...
                    //fare = UnfairMeterLocal.Start(getContext());
                    fare = 0d;
                }

                if(fare > 0)
                {
                    j.setMeterPrice(fare);
                    STATUSMANAGER.setCurrentJob(getContext(), j);
                }
                else
                {
                    fare = j.getMeterPrice();
                }

            }
            else
            {
                fare = STATUSMANAGER.getCurrentJob(this.getContext()).getMeterPrice();
            }



            if (oldFare == null)
            {
                oldFare = fare - 1; //so they will never be equal at the start... no matter what weird numbers get sent through
            }


            String currentFareText = getString(R.string.fair_meter_current_fare);
            currentFareText += (" £" + HandyTools.Strings.formatPrice(fare));

            String fareText = currentFareText;

            double diff = oldFare - fare;

            if(((Math.abs(diff) > 100)&&(!(oldFare<=0)) && (!(isLocalMeter(getContext())))))
            {
                //do nothing.... must be an error price...
            }
            else
            {
                if (!(oldFare.equals(fare)))
                {
                    oldFare = fare;

                    if (this.fLblQickFare.getVisibility() == View.VISIBLE)
                    {
                        if(doAnimations())
                        {
                            animateFare(fareText);
                        }
                        else
                        {
                            fLblQickFare.setText(fareText);
                        }
                    }
                    else
                    {
                        //our first showing.... just slide in from right
                        //now moved into .show() - duh!
                    }

                }
            }
        }
    }

    private void animateFare(final String _fareText)
    {
        //slide out to the left, then in from the right
        //ViewFader.fadeIn(this.btnMeter);
        YoYo.with(Techniques.SlideOutLeft)
                .duration(FairMeterFragment.ANIMATION_SPEED)
                .withListener(new Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        fLblQickFare.setText(_fareText);
                        YoYo.with(Techniques.SlideInRight)
                                .duration(FairMeterFragment.ANIMATION_SPEED)
                                .playOn(FairMeterFragment.this.getView());
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                })
                .playOn(FairMeterFragment.this.getView());
    }



    private void doUpdate()
    {
        if(this.oShown)
        {
            updateFare();
        }
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
    public void onStop()
    {
        super.onStop();
    }

    private Boolean oShown;

    public void show(Context _c)
    {
        //Log.w("Show", "Show");
        //this.oShown = true;

        String meterType = SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(_c);

        if (!(meterType.equals(SETTINGSMANAGER.METER_TYPE.NONE)))
        {
            this.oShown = true;
            if (this.fLblQickFare.getVisibility() == View.VISIBLE)
            {
                //do nothing
            }
            else
            {
                YoYo.with(Techniques.SlideInRight)
                        .duration(FairMeterFragment.ANIMATION_SPEED)
                        .withListener(new Animator.AnimatorListener()
                        {
                            @Override
                            public void onAnimationStart(Animator animation)
                            {
                                FairMeterFragment.this.getView().setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation)
                            {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {}

                            @Override
                            public void onAnimationRepeat(Animator animation) {}
                        })
                        .playOn(FairMeterFragment.this.getView());

                BROADCASTERS.meterRequestUpdate(_c);
            }
        }
    }

    public void hide()
    {
        this.oShown = false;
        //this.getView().setVisibility(View.GONE);
    }

    /*
    private Updater fUpdater;

    public class Updater extends Thread
    {
        private Boolean oPendingCanx;
        private Boolean oFinished;
        private int tickCount;

        private Updater()
        {
            oPendingCanx = false;
            oFinished = false;
            oPaused = true;

            tickCount = 1;
        }

        public void run()
        {
            while (!(oPendingCanx))
            {
                try
                {
                    Thread.sleep(5000);



                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            doLog("Finished...", "FairMeterFinished");
            oFinished = true;
        }

        private void doLog(String _tag, String _message)
        {
            //Log.w(_tag, _message);
        }

        private Boolean oPaused;
        public void pause()
        {
            this.oPaused = true;
        }

        public void unPause()
        {
            this.oPaused = false;
        }

        public void cancel()
        {
            this.oPendingCanx = true;
        }

        public Boolean isFinished()
        {
            return oFinished;
        }



    }*/
}