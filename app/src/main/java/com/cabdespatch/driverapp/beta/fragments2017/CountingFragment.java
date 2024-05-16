package com.cabdespatch.driverapp.beta.fragments2017;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabdespatch.driverapp.beta.Settable;

/**
 * Created by Pleng on 15/02/2017.
 */

public abstract class CountingFragment extends LoggedInFragment
{
    private long currentCountStart = -1;
    protected abstract Settable getCounterSave();

    @Override
    public final void onTick()
    {
        long millis = System.currentTimeMillis() - currentCountStart;
        this.onTick(millis);
    }

    protected int getSeconds(long _timeEllapsed)
    {
        return (int) Math.floor(_timeEllapsed / 1000);
    }

    @Override
    protected final View onCreateTickingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState)
    {
        return onCreateCountingView(_inflater, _container, _savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        long currentCountValue = getCounterSave().parseLong(this.getContext());

        if(currentCountValue == Long.valueOf(getCounterSave().getDefaultValue()))
        {
            this.currentCountStart = System.currentTimeMillis();
            this.getCounterSave().putValue(this.getContext(), this.currentCountStart);
        }
        else
        {
            this.currentCountStart = currentCountValue;
        }
    }

    @CallSuper
    protected void finishCounting()
    {
        this.getCounterSave().reset(this.getContext());
    }

    protected abstract View onCreateCountingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState);


    public abstract void onTick(long _milliseconds);

}
