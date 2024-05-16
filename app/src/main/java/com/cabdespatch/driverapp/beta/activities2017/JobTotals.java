package com.cabdespatch.driverapp.beta.activities2017;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.regex.Pattern;


public class JobTotals extends AnyActivity
{
	
	private class BroadcastHandler extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context _c, Intent _intent)
		{
			JobTotals.this.setupListAdapter(true);
		}
	}
	
	LinearLayout oLayBusy;
	BroadcastHandler oBroadcastHandler;
	
	@Override
	public void onResume()
	{
		super.onResume();
		this.setContentView(R.layout.activity_list);
		
		TextView lblTitle = (TextView) this.findViewById(R.id.frmlist_title);
		lblTitle.setText("Job Totals");
		
		this.oLayBusy = (LinearLayout) this.findViewById(R.id.frmlist_busy);
		this.oLayBusy.setVisibility(View.VISIBLE);
		
		this.setupListAdapter(false);
		this.oBroadcastHandler = new BroadcastHandler();
		this.registerReceiver(this.oBroadcastHandler, new IntentFilter(BROADCASTERS.JOB_TOTALS_UPDATE));
		
		BROADCASTERS.JobTotals(this);

        ImageButton btnBack = (ImageButton) this.findViewById(R.id.frmlist_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                JobTotals.this.onBackPressed();
            }
        });
		

		this.setBackground();
				
	}


    @Override
	public void onPause()
	{
		super.onPause();
		this.unregisterReceiver(this.oBroadcastHandler);
		this.finish();
	}
	
	private void setupListAdapter(Boolean _hideBusy)
	{
		String startDate = SETTINGSMANAGER.SETTINGS.JOB_TOTALS_BASE_DATE.getValue(this);
		String[] totals = SETTINGSMANAGER.getJobTotals(this);
		
		if(startDate.equals(Settable.NOT_SET))
		{
			//do nothing
		}
		else
		{
			ListView list = (ListView) this.findViewById(R.id.frmlist_list);		
			list.setAdapter(new TotalsAdapter(totals, new DateTime(startDate)));
		}
		
		if(_hideBusy)
		{
			this.oLayBusy.setVisibility(View.GONE);
		}
	}
	
	private class TotalsAdapter extends BaseAdapter
	{

		private String[] oTotals;
		private DateTime oBaseDate;
		private DecimalFormat oFormat;
		
		public TotalsAdapter(String[] _totals, DateTime _baseDate)
		{
			this.oFormat = new DecimalFormat("0.00");
			this.oFormat.setMaximumFractionDigits(2);
			
			this.oTotals = _totals;
			this.oBaseDate = _baseDate;
		}
		
		@Override
		public int getCount()
		{
			return this.oTotals.length;
		}

		@Override
		public Object getItem(int index)
		{
			return this.oTotals[index];
		}

		@Override
		public long getItemId(int arg0)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int index, View v, ViewGroup arg2)
		{
			String[] totals = this.oTotals[index].split(Pattern.quote(":"));


			LinearLayout l = (LinearLayout) JobTotals.this.getLayoutInflater().inflate(R.layout.row_jobtotal, null);
			DateTime itemDate = this.oBaseDate.minusDays(index);
			
			TextView lblDate = (TextView) l.findViewById(R.id.row_jobtotal_date);
			lblDate.setText(itemDate.toString("EEE d"));
			
			TextView lblAmount = (TextView) l.findViewById(R.id.row_jobtotal_amount);
            try
            {
                lblAmount.setText("£" + this.oFormat.format(Double.valueOf(totals[1])));
            }
            catch (Exception ex)
            {
                HashMap<String, String> a = new HashMap<String, String>();
                a.put("Index " + String.valueOf(index), this.oTotals[index]);
                ErrorActivity.genericReportableError("Could not Create JobTotal", a);
            }

			
			TextView lblJobCount = (TextView) l.findViewById(R.id.row_jobtoal_amountofjobs);
			lblJobCount.setText(lblJobCount.getText().toString().replace("+N", totals[0]));
			
			//TextView t = (TextView) l.findViewById(R.id.plotRow_label);
			
			//t.setText(this.oPlotlist.get(index).getLongName());
			return l;
		}


		
	}

    @Override
    public void onBackPressed()
    {
        this.finish();
    }

}
