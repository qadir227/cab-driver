package com.cabdespatch.driverapp.beta.fragments2017;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.activities2017.LoggedInHost;
import com.cabdespatch.driverapp.beta.activities2017.TickingActivity;

/**
 * Created by Pleng on 16/02/2017.
 */

public abstract class TickingFragment extends Fragment
{
    private boolean fActive;
    protected boolean isActive()
    {
        return fActive;
    }

    int fStartSeconds = 0;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return onCreateTickingView(inflater, container, savedInstanceState);

    }

    public void onLocationChange() {}


    @Override
    public final void onPause()
    {
        super.onPause();
        this.fActive = false;
        this.onStopping();
    }

    @Override
    public final void onResume()
    {
        super.onResume();
        this.fActive = true;
        fStartSeconds = getSecondsSinceResume();
        this.onResuming();

    }



    protected final void showMenu()
    {
        LoggedInHost a = (LoggedInHost) getActivity();
        a.showMenu();
    }

    public abstract void onTick();


    public void onPreTick()
    {
        if(fActive)
        {
            onTick();
        }
    }

    public int getSecondsSinceResume()
    {
        try
        {
            TickingActivity a = (TickingActivity) getActivity();
            return a.getSeconds() - fStartSeconds;
        }
        catch (Exception ex)
        {
            //a Ticking Fragment should only be added to a Ticking Activity...
            return -1;
        }
    }

    protected abstract void onBroadcastReceived(Context _context, Intent _intent);

    public void onPreBroadcastReceived(Context _c, Intent _intent)
    {
        if(fActive)
        {
            onBroadcastReceived(_c, _intent);
        }
    }

    protected abstract void onStopping();
    public abstract  void onResuming();
    protected abstract View onCreateTickingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState);
    // protected abstract void onLoggedInViewResuming();

    /*
    protected LoggedInHost getHostActivity()
    {
        return (LoggedInHost) this.getActivity();
    }*/

    protected boolean isDebug()
    {
        return Globals.isDebug(getContext());
    }

}
