package com.cabdespatch.driverapp.beta.activities2017;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.JOBHISTORYMANAGER;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;
import com.cabdespatch.driverapp.beta.cabdespatchJob;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Queue;

public class ViewJobHistory extends AnyActivity
{
	ViewFlipper flip;
	int currentItem;
	int itemCount;
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		this.setContentView(R.layout.activity_viewinflipper);
		this.setBackground();
		
		TextView lblTitle = (TextView) this.findViewById(R.id.frmViewInFlipper_lblTitle);
		lblTitle.setText(R.string.jobhistory);
		
		this.flip = (ViewFlipper) this.findViewById(R.id.frmViewInFlipper_flip);
				
		Queue<cabdespatchJob> items = JOBHISTORYMANAGER.getHistoricalJobs(this);
		this.currentItem = 1;
		this.itemCount = items.size();
		
		while(items.size()>0)
		{
			this.flip.addView(this.getJobHistoryView(items.remove()));
		}
		
				
		this.flip.setOnTouchListener(Globals.FlipperTouch());
		
			
		ImageButton btnBack = (ImageButton) this.findViewById(R.id.frmViewInFlipper_btnBack);
		btnBack.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				ViewJobHistory.this.onBackPressed();
			}
		});
		
		ImageButton btnMenu = (ImageButton) this.findViewById(R.id.frmViewInFlipper_btnMenu);
		btnMenu.setVisibility(View.INVISIBLE);
		
	
	}

    @Override
    public void onBackPressed()
    {
        finish();
    }
	   
	
	private View getJobHistoryView(cabdespatchJob _j)
	{
		View v = this.getLayoutInflater().inflate(R.layout.messageitem, null);
		//v.setTag(_m.getMessage());
		
		DateTimeFormatter formatheader = DateTimeFormat.forPattern("dd/MM/yyyy 'at' HH:mm");
		DateTimeFormatter formatrequested = DateTimeFormat.forPattern("HH:mm");
		
		TextView header = (TextView) v.findViewById(R.id.msgitemdetails);
		String datetime = formatheader.print(_j.getDateTimeReceived());
		
		String count = "[" + String.valueOf(this.currentItem++) + "/" + String.valueOf(this.itemCount) + "]\n";
		
		header.setText(count + " " + datetime);
		
		TextView t = (TextView) v.findViewById(R.id.messageitem_messagetext);
		
		String timefor = _j.getTime();

        if(timefor.toUpperCase().contains("ASAP"))
        {
            //do nothing
        }
        else
        {
            String[] actualTimeEles = timefor.split(" ");

            if (actualTimeEles.length >= 2 )
            {
                String actualTime = actualTimeEles[1];
                if(actualTime.length() >= 5)
                {
                    timefor = actualTime.substring(0, 5);
                }
                else
                {
                    ErrorActivity.handleError(this, new ErrorActivity.ERRORS.INVALID_JOB_TIME_STRING(actualTime, "actualTime"));
                }

            }
            else
            {
                ErrorActivity.handleError(this, new ErrorActivity.ERRORS.INVALID_JOB_TIME_STRING(timefor, ""));
            }
        }


		
		/*if(!(timeFor.contains("ASAP")))
		{
			timeFor = formatrequested.print(new DateTime(timeFor));
		}*/
		
		String jobDetail = "";
		jobDetail += ("Requested Time: " + timefor + "\n\n");
		jobDetail += ("From: " + _j.getFromAddress() + "\n");
		jobDetail += ("To: " + _j.getToAddress() + "\n\n");
		
		jobDetail += ("Price: " + _j.getPrice() + "\n");
		
		
		t.setText(jobDetail);
		
		//v.setOnTouchListener(this.FlipperTouch());
		
		return v;
		

	}


    @Override
	public void onPause()
	{
		super.onPause();
		this.finish();
	}

}
