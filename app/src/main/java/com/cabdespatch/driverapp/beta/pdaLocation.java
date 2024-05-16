package com.cabdespatch.driverapp.beta;

import android.content.Context;

import com.cabdespatch.driverapp.beta.autoplot.cdAutoPlot;

import org.joda.time.DateTime;

public class pdaLocation
{
	private double lat;
	private double lon;
	private double acc;
	private double speed;
	private DateTime time;
	private plot locplot;

	private double bearing;
	
	public static pdaLocation pdaLocationWithoutAutoplot(double _lat, double _lon, double _acc, double _speed, long _time, Double _bearing, plot _plot)
	{
		pdaLocation p = new pdaLocation();
		p.lat = _lat;
		p.lon = _lon;
		p.acc = _acc;
		p.speed = _speed;
		//p.time = _time;
		p.locplot = _plot;
		p.bearing = _bearing;
				
		return p;
	}
	
	private pdaLocation()
	{
		
	}


	
	public pdaLocation(Context _c, double _lat, double _lon, double _acc, double _speed, long _time, Double _bearing, plot _currentPlot)
	{
		lat=_lat; lon=_lon; acc=_acc; speed = _speed; bearing = _bearing;
		time = new DateTime(_time);

        boolean isflagdown = SETTINGSMANAGER.SETTINGS.USE_FLAGDOWN.parseBoolean(_c);

        plotList normalPlots = SETTINGSMANAGER.getPlotsNormal(_c);
        plotList rankPlots = (isflagdown?SETTINGSMANAGER.getPlotsRank(_c): null);

		
		this.locplot = cdAutoPlot.getAutoPlot(normalPlots, rankPlots, this, _currentPlot);
	}
	
	public pdaLocation(Context _c, double _lat, double _lon, double _acc, double _speed, long _time, plotList _plots, String _shortPlotName, Boolean _isRank)
	{
		lat=_lat; lon=_lon; acc=_acc; speed = _speed;
		time = new DateTime(_time);
		
		plot iPlot = _plots.getPlotByShortName(_shortPlotName);
        this.locplot=(_isRank?iPlot.asRank():iPlot);
	}
	
	public void overridePlot(plot _p)
	{
		this.locplot = _p;
	}
	
	public plot getPlot()
	{
		return this.locplot;
	}
	
	public double getLat()
	{
		return lat;
	}
	public double  getLon()
	{
		return lon;
	}

	public double getBearing() {return  bearing; }

	public String getLatString(int _precision)
	{
		String retVal = "";
		
		String[] parts = String.valueOf(lat).split("\\."); //<--- this is a RegEx!!
		if (parts.length==0)
		{
			return "0";
		}
		else if (parts.length==1)
		{
			retVal = parts[0];
		}
		else
		{
			if (parts[1].length() > _precision)
			{
				parts[1] = parts[1].substring(0, _precision + 1);
			}
			
			retVal = parts[0] + "." + parts[1];
		}
		
		retVal = String.valueOf(getLat());
		
		return retVal;
	}
	public String getLonString(int _precision)
	{
		String retVal = "";
		
		String[] parts = String.valueOf(lon).split("\\."); //<--- this is a RegEx!!
		if (parts.length==0)
		{
			return "0";
		}
		else if (parts.length==1)
		{
			retVal = parts[0];
		}
		else
		{
			if (parts[1].length() > _precision)
			{
				parts[1] = parts[1].substring(0, _precision + 1);
			}
			
			retVal = parts[0] + "." + parts[1];
		}
		
		retVal = String.valueOf(getLon());
		
		return retVal;
	}
	public String getLatString()
	{
		return getLatString(5);
	}
	public String getLonString()
	{
		return getLonString(5);
	}
	public double getAccuracy()
	{
		return acc;
	}

    /*
    public String getAccuracyTrimmed()
    {
        String retVal = String.valueOf(acc);
        if(retVal.length()>10)
        {
            retVal = retVal.substring(0, 10);
        }

        return retVal;
    }*/

	public double getSpeed()
	{
		return speed;
	}
	public boolean equals(pdaLocation _p)
	{
		boolean match = false;
		
		if (lat == _p.getLat())
		{
			if (lon == _p.getLon())
			{
				if (acc == _p.getAccuracy())
				{
					match = true;
				}
			}
		}

		
		return match;
	}
	
}
