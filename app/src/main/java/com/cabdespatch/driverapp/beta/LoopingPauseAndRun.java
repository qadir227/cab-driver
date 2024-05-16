package com.cabdespatch.driverapp.beta;

import android.os.AsyncTask;
import android.os.Build;


/**
 * Created by Pleng on 03/08/2017.
 */

public abstract class LoopingPauseAndRun extends AsyncTask<Void, Void, Void>
{

    protected static Integer sObjectCount = 0;

    private boolean oCancel;
    private Integer oPause;
    public void Cancel()
    {
        oCancel = true;
    }

    public LoopingPauseAndRun(Integer _pause)
    {
        sObjectCount++;
        oCancel = false;
        oPause = _pause;
    }

    public abstract void uiWork();

    public void Start()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
        {
            this.execute();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
        uiWork();
    }

    @Override
    protected final Void doInBackground(Void... params)
    {
        while(!(this.oCancel))
        {
            try
            {
                Thread.sleep(this.oPause);
                this.publishProgress();
            }
            catch (InterruptedException e)
            {
                //don't care...
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        sObjectCount --;
    }
}
