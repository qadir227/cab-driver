package com.cabdespatch.driverapp.beta;

import android.content.Context;

import com.cabdespatch.driverapp.beta.activities.ErrorActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class plotList 
{
	private double vNo;
    public List<plot> plots;
    private int ubound;


    public plotList()
    {
        plots = new ArrayList<plot>();
        ubound = 0;
    }
    
    public List<plot> getPlotAsList()
    {
    	return plots;
    }

    public double getVersionNumber()
    {
        return vNo;
    }

    public void setVersionNumber(double _version)
    {
        vNo = _version;
    }

    public Boolean addPlot(plot _plot)
    {
        for (plot p : plots)
        {
            if (p.getShortName().equals(_plot.getShortName()))
            {
                return false;
            }
            else
            {
                continue;
            }
        }

        //no plot found with same short name - Add it!
        _plot.setIndex(ubound);
        ubound++;
        plots.add(_plot);
        return true;         
    }

    public plot getPlotByIndex(int index)
    {
        plot pl = plot.errorPlot();

        for (plot p : plots)
        {
            if (p.index() == index)
            {
                pl = p;
            }
        }
        return pl;
    }

    public plot getPlotByLongName(String longName)
    {
        plot pl = plot.errorPlot();

        for (plot p : plots)
        {
            if (p.getLongName().equals(longName))
            {
                pl = p;
            }
        }
        return pl;
    }

    public plot getPlotByShortName(String shortName)
    {
        plot pl = plot.errorPlot();

        for (plot p : plots)
        {
            if (p.getShortName().equals(shortName))
            {
                pl = p;
            }
        }
        return pl;
    }

    //type 0 for plots, 1 for ranks;
    public static plotList fromString(Context _c, String _zones, int _type)
    {
    	
    	Boolean _namesOnly = false;
		
		//converting a list to a Queue in Java. Got to love that Syntax! :(
		Queue<String> zonesData = HandyTools.Conversions.Arrays.toQueue(_zones.split("\\^"));

            //version number is first in the queue
            plotList pl = new plotList();
            plotList pl_sorted = new plotList();

            String versionNumber = zonesData.remove();

        if(versionNumber.equals("null"))
        {
            return new plotList();
        }
        else
        {

            pl.setVersionNumber(Double.valueOf(versionNumber));

            //now let's get all the plots :D
            while (zonesData.size()>0)
            {
                String zone = zonesData.remove();
                String[] z = zone.split("@");
                if (z.length>=3)
                {
                    Boolean isPlotFlagDown = false;
                    String speakAs = "";

                    //combatibility with beta versions; am I kind or what??
                    if(z.length>=4)
                    {
                        if (z[3].equals("1")) {isPlotFlagDown = true;}
                    }
                    if(z.length>=5)
                    {
                        speakAs = z[4];
                    }

                    plot p = new plot(z[2], z[0], isPlotFlagDown, speakAs);

                    if(!(_namesOnly))
                    {
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
                    }

                    pl.addPlot(p);

                    //now put all the flagdowns at the beginning of the list
                    //should have done this in SocketServer really!
                    //flagdowns first...
                    /*
                    for(plot r:pl.getPlotAsList())
                    {
                        if (r.isRank()) { pl_sorted.addPlot(r); }
                    }
                    //and non flags...
                    for(plot r:pl.getPlotAsList())
                    {
                        if (!(r.isRank())) { pl_sorted.addPlot(r); }
                    }*/

                    if(_type==0)
                    {
                        //plots
                        for(plot standard_plot:pl.getPlotAsList())
                        {
                            if(!(standard_plot.isRank()))
                            {
                                pl_sorted.addPlot(standard_plot);
                            }
                        }
                    }
                    else if (_type==1)
                    {
                        //ranks
                        for(plot rank_plot:pl.getPlotAsList())
                        {
                            if(rank_plot.isRank())
                            {
                                pl_sorted.addPlot(rank_plot);
                            }
                        }
                    }
                    else if(_type==99)
                    {
                        pl_sorted = pl;
                    }

                }
                else
                {
                    ErrorActivity.handleError(_c,  new ErrorActivity.ERRORS.ZONE_DATA_SKIPPED(zone));
                }

            }

            pl_sorted.setVersionNumber(pl.getVersionNumber());
            return pl_sorted;
        }

    }
}
