package com.cabdespatch.driverapp.beta;

/**
 * Created by Pleng on 15/01/2016.
 */
import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by Pleng on 30/10/2015.
 */
public abstract class PauseAndRun extends AsyncTask<Integer, Void, Void>
{

    public void Start(Integer _preDelay)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, _preDelay);
        }
        else
        {
            this.execute(_preDelay);
        }
    }

    @Override
    protected final Void doInBackground(Integer...  params)
    {
        try
        {
            Thread.sleep(params[0]);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected abstract void onPostExecute(Void _void);
}
