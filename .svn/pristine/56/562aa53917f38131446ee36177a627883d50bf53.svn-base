package com.cabdespatch.driverapp.beta;

import com.cabdespatch.driverapp.beta.autoplot.Point;
import com.cabdespatch.driverapp.beta.autoplot.Polygon;

import java.util.ArrayList;
import java.util.List;

public class plot
{

    private String pSName;
    private String pLName;
    private List<double[]> pPoints;
    private int idx;
    private boolean rank;
    private String sayAs;

    public void loopPlot()
    {
    	if (pPoints.size() > 1)
    	{
    		pPoints.add(pPoints.get(0));
    	}
    }

    public static String ERROR_PLOT = "NAT";
    //public static String NATIONAL = "NAT";
    
    public plot(String shortname, String longname, Boolean isRank, String speakAs)
    {
        pSName = shortname;
        pLName = longname;
        pPoints = new ArrayList<double[]>();
        idx = -1;
        rank = isRank;
        sayAs = speakAs;
    }

    @Override
    public boolean equals(Object _p)
    {
        //Log.e("EQUALS", "ENTERING EQUALS");
        if(_p.getClass().equals(plot.class))
        {
            plot otherPlot = (plot) _p;
            if(otherPlot.getShortName().equals(this.getShortName()))
            {
                return otherPlot.isRank().equals(this.isRank());
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }


    public String getShortName()
    {
        return pSName;
    }

    public String getLongName()
    {
    	if(pLName.isEmpty())
    	{
    		return pSName;
    	}
    	else
    	{
    		return pLName;
    	}
    }

    public void addPoint(double[] point)
    {

        pPoints.add(point);
    }

    public List<double[]> getPoints()
    {
        return pPoints;
    }
    
    public Polygon getPolygon()
    {
    	Polygon.Builder b = new Polygon.Builder();
    	
    	if(pPoints.size()>2)
    	{
    		for (double[] p:pPoints)
        	{
        		b.addVertex(new Point((float)p[0],(float)p[1]));
        	}
        	
        	b.addVertex(new Point((float)pPoints.get(0)[0],(float)pPoints.get(0)[1]));
        	
        	return b.build();	
    	}
    	else
    	{
    		return null;
    	}
    	
    }

    public int index()
    {
        return idx;
    }

    public void setIndex(int index)
    {
        idx = index;
    }
    
    public Boolean isRank()
    {
    	return rank;
    }
    
    public String getSpeachName()
    {
    	if (this.sayAs.equals(""))
    	{
    		return this.getLongName();
    	}
    	else
    	{
    		return this.sayAs;
    	}
    }

    @Override
    public String toString()
    {
        return (isRank()?"(RANK)":"(NOT RANK)") + this.pSName + " - " + String.valueOf(this.pPoints.size()) + " points in polygon";
    }

    public plot asRank()
    {
        plot newPlot = new plot(this.pSName, this.pLName, true, "");
        for(double[] p:this.getPoints())
        {
            newPlot.addPoint(p);
        }

        return newPlot;
    }
    
    public static plot errorPlot()
    {
    	return new plot(plot.ERROR_PLOT,plot.ERROR_PLOT,false, "");
    }

}
