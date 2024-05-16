package com.cabdespatch.driverapp.beta.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cabdespatch.driverapp.beta.AndroidBug5497Workaround;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.activities2017.LoginActivity;
import com.cabdespatch.driverapp.beta.autoplot.cdAutoPlot;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.pdaLocation;
import com.cabdespatch.driverapp.beta.plot;
import com.cabdespatch.driverapp.beta.plotList;

import java.io.File;


public class AutoPlot extends AnyActivity
{

	EditText txtLat;
	EditText txtLon;
	plotList appPlotList;
	plot currentPlot;
	TextView lblInfo;
	
	@Override
	protected void onPause()
	{
		super.onPause();
		this.finish();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_auto_plot);
		AndroidBug5497Workaround.assistActivity(this);
		
		this.txtLat = (EditText) this.findViewById(R.id.autoplot_lat);
		this.txtLon = (EditText) this.findViewById(R.id.autoplot_lon);
		this.lblInfo = (TextView) this.findViewById(R.id.autoplot_info);
		
		TextView lblAllPlots = (TextView) this.findViewById(R.id.autoplot_allplots);
		
		appPlotList = SETTINGSMANAGER.getPlotsAll(this);

		for(plot pl:appPlotList.getPlotAsList())
		{
			lblAllPlots.append(pl.getShortName() + " (" + pl.getLongName() + " )\n");
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.auto_plot, menu);
		return true;
	}

	/*
	public void btnLock_Click(View _v)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			ComponentName deviceAdmin = new ComponentName(this, AdminReceiver.class);
			DevicePolicyManager dpm  = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
			if (!dpm.isAdminActive(deviceAdmin))
			{
				cdToast.showShort(this, "This app is not a device admin!");
			}
			if (dpm.isDeviceOwnerApp(getPackageName()))
			{
				dpm.setLockTaskPackages(deviceAdmin, new String [] {getPackageName()});
			}
			else
			{
				cdToast.showShort(this, "This app is not the device owner!");
			}

			this.startLockTask();
		}
		else
		{
			cdToast.showShort(this, "Lockdown not supported");
		}
	}*/

	public void btnExit_Click(View _v)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			this.stopLockTask();
		}
		this.finish();
	}

	public void btnActivity_Click(View _v)
	{
		Intent i = new Intent(this, LoginActivity.class);
        Globals.StartActivity(this, i);
    }

	public void autoPlotClick(View _v)
	{
		
		Boolean didSomeStuff = false;
		
		if(!(this.txtLat.getText().toString().equals(""))) 
		{
			if(!(this.txtLon.getText().toString().equals("")))
			{
				Double lat = Double.valueOf(this.txtLat.getText().toString());
				Double lon = Double.valueOf(this.txtLon.getText().toString());
				
				if(this.currentPlot==null)
				{
					this.currentPlot = plot.errorPlot();
				}
				
				pdaLocation p = new pdaLocation(this, lat, lon, 0, 0, 0, -1.0, this.currentPlot);
				
				this.currentPlot = cdAutoPlot.getAutoPlot(SETTINGSMANAGER.getPlotsNormal(this), SETTINGSMANAGER.getPlotsRank(this), p, this.currentPlot);
				
				String info = "Lat: " + txtLat.getText().toString() + "\n";
				info += ("Lon: " + txtLon.getText().toString() + "\n");
				info += ("Plot: " + this.currentPlot.getShortName() + " (" + this.currentPlot.getLongName() + ")");
				
				this.txtLat.setText("");
				this.txtLon.setText("");
				
				this.lblInfo.setText(info);
				
				didSomeStuff = true;
				//plot p = this.AutoPlot.getAutoPlot(new pdaLocation(_v.getContext(),lat,lon, 0, 0, 0,plot.errorPlot()), new plot("", "", false));
				//Toast.makeText(_v.getContext(), p.getLongName() + "(" + p.getShortName() + ")", Toast.LENGTH_LONG).show();
			}	
		}
		
		if(!(didSomeStuff))
		{
			cdToast.showLong(this, "Try putting some values in first");
		}
		
		
	}

	public void plotSQLSpecialClick(View _v)
    {
        File f = new File(Environment.getExternalStorageDirectory(), "zones.cfg");
        if(f.exists())
        {
            try
            {
                String path = f.getAbsolutePath();
                String s = Globals.FileTools.readFileToString(path);
                plotList p = plotList.fromString(this, s, 99);

                generatePlotSQL(p);

            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();;
            }
        }

    }
	
	public void plotSQLClick(View _v)
	{
	    generatePlotSQL(this.appPlotList);
	}

	private void generatePlotSQL(plotList p)
    {
        Toast.makeText(this, "Starting...", Toast.LENGTH_LONG).show();

        String vbNewLine = "\n";
        String sql = "/*Awesome Cab Despatch Power */" + vbNewLine + vbNewLine + vbNewLine + vbNewLine;

        for (plot pl:p.getPlotAsList())
        {
            String longName = pl.getLongName();
            if(longName.equals(""))
            {
                longName = pl.getShortName();
            }

            sql += "/*" + longName + "*/" + vbNewLine;
            String thisPlot =  "INSERT INTO tblAreas(Area, Zone, AreaDescription) Values('$1','$2','$3');";
            thisPlot = thisPlot.replace("$1", pl.getShortName());
            thisPlot = thisPlot.replace("$2", pl.getShortName());
            thisPlot = thisPlot.replace("$3", pl.getLongName());

            sql += thisPlot + vbNewLine;

            sql +="/* Ploygon: */" + vbNewLine;

            for(double[] pnt:pl.getPoints())
            {
                String thisPoint = "INSERT INTO tblPlotPolygons(strPlotName, dblLat, dblLon) Values('$1', '$2', '$3');";
                thisPoint = thisPoint.replace("$1", pl.getShortName());
                thisPoint = thisPoint.replace("$2",String.valueOf(pnt[0]));
                thisPoint = thisPoint.replace("$3",String.valueOf(pnt[1]));

                sql += thisPoint + vbNewLine;

            }

            sql += vbNewLine;
        }


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "you@youremail.address" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Transaction Log");
        intent.putExtra(Intent.EXTRA_TEXT, sql);

        this.startActivityForResult(intent, 300);

        //File file = this.getExternalFilesDir(null);
        //file = new File(file, "ZONE.SQL");

        //Globals.FileTools.writeStringToFile(sql, file.getAbsolutePath());

        //Toast.makeText(this, "...finished: " + this.getExternalFilesDir(STORAGE_SERVICE), Toast.LENGTH_SHORT).show();
    }
	
	/*private plotList readZoneFile(String _absoluteFileLocation)
	{
		try
		{
			String zones = global.FileTools.readFileToString(_absoluteFileLocation);
			
			//converting a list to a Queue in Java. Got to love that Syntax! :(
			Queue<String> zonesData = new HandyTools.Conversions.Arrays.toQueue(zones.split("\\^"));
								
			//version number is first in the queue
			plotList pl = new plotList();
			plotList pl_sorted = new plotList();
			
			//String x = zonesData.remove();
			
			pl.setVersionNumber(Double.valueOf(zonesData.remove()));
			
			//now let's get all the plots :D
			while (zonesData.size()>0)
			{
				String zone = zonesData.remove();
				String[] z = zone.split("@");
				if (z.length>=3)
				{
					Boolean isPlotFlagDown = false;
					
					//combatibility with beta versions; am I kind or what??
					if(z.length==4)
					{
						if (z[3].equals("1")) {isPlotFlagDown = true;}
					}
					plot p = new plot(z[2], z[0], isPlotFlagDown);
					String[] points = z[1].split(";");
					
					for (String pnt : points)
					{
						String[] pointData = pnt.split("#");
						if (pointData.length == 2)
						{
							double[] point = new double[2];
							point[0] = Double.valueOf(pointData[0]);
							point[1] = Double.valueOf(pointData[1]);
							
							p.addPoint(point);
						}
																
					}
												
					p.loopPlot();							
					pl.addPlot(p);
					
					//now put all the flagdowns at the beginning of the list
					//should have done this in SocketServer really!														
					//flagdowns first...
					for(plot r:pl.getPlotAsList())
					{
						if (r.isRank()) { pl_sorted.addPlot(r); }
					}
					//and non flags...
					for(plot r:pl.getPlotAsList())
					{
						if (!(r.isRank())) { pl_sorted.addPlot(r); }
					}
					
				}
				else
				{
					//skipped zone
				}
										
			}
			
			pl_sorted.setVersionNumber(pl.getVersionNumber());
			return pl_sorted;
			
		} 
		catch (IOException e)
		{
			//CLAY - Zones could not be read!
			return null;
		}
	
	}*/

}
