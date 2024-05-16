package com.cabdespatch.driverapp.beta.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.USERREQUESTS;
import com.cabdespatch.driverapp.beta.plot;
import com.cabdespatch.driverapp.beta.plotList;
import com.cabdespatch.driverapp.beta.services.DataService;

import java.util.List;

public class PlotList extends AnyActivity
{

	public static String ACTION_SEND_PLOT_UPDATE = "SEND_PLOT_UPDATE";
	
	@Override
	public void onCreate(Bundle _savedState)
	{
        super.onCreate(_savedState);
		this.setContentView(R.layout.activity_list);
		
		TextView lblTitle = (TextView) this.findViewById(R.id.frmlist_title);
		lblTitle.setText("Plot");
		
		final String ACTION = this.getIntent().getAction();
		
		plotList pl = SETTINGSMANAGER.getPlotsAll(this);
		
		ListView l = (ListView) this.findViewById(R.id.frmlist_list);
		l.setAdapter(new PlotAdapter(pl.getPlotAsList()));
		l.setOnItemClickListener(new OnItemClickListener
		(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3)
			{

				if(ACTION.equals(PlotList.ACTION_SEND_PLOT_UPDATE))
				{
					plot p = (plot) arg0.getAdapter().getItem(index);
					Intent i = new Intent(BROADCASTERS.USER_REQUEST);
					i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
					i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.PLOT);
					i.putExtra(DataService._MESSAGEEXTRA, p.getShortName());
					
					PlotList.this.sendBroadcast(i);
					
					STATUSMANAGER.putString(PlotList.this, STATUSMANAGER.STATUSES.CURRENT_PLOT, p.getShortName());
					STATUSMANAGER.setStatusBarText(PlotList.this);

				}
				PlotList.this.finish();

			}
		});
		
		
		
		ImageButton b = (ImageButton) this.findViewById(R.id.frmlist_btnBack);
		b.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				PlotList.this.finish();
				
			}
		});
	
	}
	


    private class PlotAdapter extends BaseAdapter
	{

		private List<plot> oPlotlist;
		
		public PlotAdapter(List<plot> _plots)
		{
			this.oPlotlist = _plots;
		}
		
		@Override
		public int getCount()
		{
			return this.oPlotlist.size();
		}

		@Override
		public Object getItem(int index)
		{
			return this.oPlotlist.get(index);
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
			LinearLayout l = (LinearLayout) PlotList.this.getLayoutInflater().inflate(R.layout.row_plot, null);			
			TextView t = (TextView) l.findViewById(R.id.plotRow_label);
			
			t.setText(this.oPlotlist.get(index).getLongName());
			return l;
		}


		
	}
}
