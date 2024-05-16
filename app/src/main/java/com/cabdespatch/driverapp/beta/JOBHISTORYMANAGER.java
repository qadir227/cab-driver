package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.LinkedList;
import java.util.Queue;

public class JOBHISTORYMANAGER 
{
	private static final int MAX_JOB_COUNT = 50;
	
	public static void addJob(Context _c, cabdespatchJob _j)
	{
		Queue<cabdespatchJob> jobs = new LinkedList<cabdespatchJob>();
		jobs.add(_j);
		
		Queue<cabdespatchJob> history = JOBHISTORYMANAGER.getHistoricalJobs(_c);
		
		while(history.size() > 0)
		{
			jobs.add(history.remove());
		}
		
		saveJobHistory(_c, jobs);
	}
	
	private static void saveJobHistory(Context _c, Queue<cabdespatchJob> _jobs)
	{
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        SharedPreferences.Editor e = p.edit();
		for(int x=0; x<=MAX_JOB_COUNT;x++)
		{
			cabdespatchJob j = _jobs.poll();
			if(!(j==null))
			{
				e.putString("00JX_" + String.valueOf(x), j.pack());	
			}
		}
		
		Boolean comitted = false;
		while(!(comitted))
		{
			comitted = e.commit();
		}
	}

    public static void clearJobHistory(Context _c)
    {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        SharedPreferences.Editor e = p.edit();

        for(int x=0; x<=MAX_JOB_COUNT;x++)
        {
            e.putString("00JX_" + String.valueOf(x), Settable.NOT_SET);
        }

        Boolean comitted = false;
        while(!(comitted))
        {
            comitted = e.commit();
        }

    }
	
	public static Queue<cabdespatchJob> getHistoricalJobs(Context _c)
	{
		Queue<cabdespatchJob> jobs = new LinkedList<cabdespatchJob>();
		
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
		for(int x=0; x<MAX_JOB_COUNT; x++)
		{
			String job = p.getString("00JX_" + String.valueOf(x), Settable.NOT_SET);
			if(!(job.equals(Settable.NOT_SET)))
			{
				try
				{
					cabdespatchJob j = cabdespatchJob.unpack(job);
					jobs.add(j);
				}
				catch (Exception ex)
				{
					//the pack/unpack methods have probably changed
					//since we did this job - let's just loose the data
				}
			}
		}
		
		return jobs;
	}
	
	
}
