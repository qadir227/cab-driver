package com.cabdespatch.driverapp.beta.activities2017;

import android.os.Bundle;

import com.cabdespatch.driverapp.beta.LoopingPauseAndRun;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;

/**
 * Created by Pleng on 16/02/2017.
 */

public abstract class TickingActivity extends AnyActivity
{
    protected abstract String getDebugTag();
    protected abstract void onTick(long millis);
    private long currentMillis = -1;


    private void onPreTick(long _millis)
    {
        this.currentMillis = _millis;
        onTick(_millis);
    }

    protected void onPausedTick()
    {
        //this is so instances DataActivity can still check for service even when paused
    }

    private TickerObject oTicker;
   // private Boolean aActive;

    private long currentCountStart = -1;

    /*protected boolean isActive()
    {
        if(aActive==null)
        {
            return false;
        }
        else
        {
            return aActive;
        }
    }*/



    private class TickerObject extends LoopingPauseAndRun
    {


        //active is a misname really
        //it still ticks but sends a "pausedTick" event
        //rather than an active tick event.

        //I cba to think of a better name right now though...
        private  boolean oActive;
        private String oOwner;
        public void setActive()
        {
            oActive = true;
        }

        public void setInactive()
        {
            oActive = false;
        }

        public TickerObject(String _owner)
        {
            super(1000);
            oOwner = _owner;
            setActive();
        }

        @Override
        public void uiWork()
        {
            if(oActive)
            {
                long millis = System.currentTimeMillis() - currentCountStart;
                TickingActivity.this.onPreTick(millis);
            }
            else
            {
                TickingActivity.this.onPausedTick();
            }
            //String output = ("($1) " + oOwner + (oActive?":ACTIVE":":INACTIVE")).replace("$1", String.valueOf(sObjectCount));
            //Log.e("Tick", output);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        setTickerStatePaused();
    }


    private void setTickerStateActive()
    {
        if(this.oTicker==null)
        {
            oTicker = new TickerObject(getDebugTag());
            oTicker.Start();
        }
        else
        {
            oTicker.setActive();
        }
    }

    private void setTickerStatePaused()
    {
        if(this.oTicker==null)
        {
            //do nothing
        }
        else
        {
            this.oTicker.setInactive();
        }
    }

    private void cleanUp()
    {
        if(this.oTicker == null)
        {
            //do nothing;
        }
        else
        {
            this.oTicker.Cancel();
        }
    }

    @Override
    protected void onDestroying()
    {
        cleanUp();
    }

    @Override
    public void finish()
    {
        cleanUp();
        super.finish();
    }

    @Override
    public void onCreate(Bundle _savedState)
    {
        super.onCreate(_savedState);
        startCounting();
        setTickerStateActive();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setTickerStateActive();

        this.setBackground();
    }


    protected void forceActivateTicker()
    {
        setTickerStateActive();
    }

    private void startCounting()
    {
        currentCountStart = System.currentTimeMillis();
    }

    protected int getSeconds(long _timeEllapsed)
    {
        return (int) Math.floor(_timeEllapsed / 1000);
    }

    public int getSeconds() { return getSeconds(currentMillis);}
}
