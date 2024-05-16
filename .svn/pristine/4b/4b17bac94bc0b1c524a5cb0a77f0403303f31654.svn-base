package com.cabdespatch.driverapp.beta.autoplot;

import com.cabdespatch.driverapp.beta.pdaLocation;
import com.cabdespatch.driverapp.beta.plot;
import com.cabdespatch.driverapp.beta.plotList;

import java.util.List;


public class cdAutoPlot
{


    private static boolean pointInPolygon(double lat, double lon, plot _pl)
    {
        List<double[]> points = _pl.getPoints();
        int j = points.size() - 1;
        int i;

        boolean isIn = false;

        for (i=0; i<points.size(); i++)
        {
            if (points.get(i)[1]<lon && points.get(j)[1]>=lon ||  points.get(j)[1]<lon && points.get(i)[1]>=lon)
            {
                if (points.get(i)[0]+(lon-points.get(i)[1])/(points.get(j)[1]-points.get(i)[1])*(points.get(j)[0]-points.get(i)[0])<lat)
                {
                    isIn=(!(isIn));
                }
            }
            j=i;
        }

        return isIn;
    }

	private static boolean pointInPolygon(pdaLocation _p, plot _pl)
	{
		double lat = _p.getLat();
		double lon = _p.getLon();
		
	    return  pointInPolygon(lat, lon, _pl);
	}


    private static plot getPlotFromPoint(plotList _plots, Point _point)
    {
        plot p = null;

        //if the driver isn't a flagdown driver we pass in a
        //null plot list.... so we'll just return a null value
        //and won't bother plotting at all...
        if(!(_plots==null))
        {
            for(plot _p:_plots.getPlotAsList())
            {
               /* if(_p.getShortName().toLowerCase().equals("rm14"))
                {
                    Debug.waitForDebugger();
                }*/

                Polygon poly  = _p.getPolygon();
                if(!(poly==null))
                {
                    if(_p.getPolygon().contains(_point))
                    {
                        p = _p;
                        break;
                    }
                }
            }

            if(p==null)
            {

                for(plot _p:_plots.getPlotAsList())
                {
                    if(pointInPolygon(_point.x, _point.y, _p))
                    {
                        p = _p;
                    }
                }

            }
        }


        return p;
    }


    //pass in a null for _ranks if driver is not
    //a flagdown driver
	public static plot getAutoPlot(plotList _plots, plotList _ranks, pdaLocation _location, plot _currentPlot)
	{
		Point currentPoint = new Point((float) _location.getLat(), (float) _location.getLon());

		plot plotFound = plot.errorPlot();
		boolean doautoplot=true;

		if(_currentPlot==null)
		{
			doautoplot=true;
		}
		else if(_currentPlot.getPolygon()==null)
		{
			doautoplot=true;
		}
		else if (_currentPlot.getPolygon().contains(currentPoint))
		{
            plotFound = _currentPlot;
            doautoplot=false;

            //do rank-side autoplot
            plot rankPlot = getPlotFromPoint(_ranks, currentPoint);
            if(!(rankPlot==null))
            {
                //send the *same* plot back but as
                //a rank
                //plotFound = plotFound.asRank();
                plotFound = rankPlot;
            }



		}
		
		
		if(doautoplot)
		{
			plot normalPlot = getPlotFromPoint(_plots, currentPoint);
            if(!(normalPlot==null))
            {
                plot rankPlot = getPlotFromPoint(_ranks, currentPoint);
                if(rankPlot==null)
                {
                    plotFound = normalPlot;
                }
                else
                {
                    plotFound = rankPlot;
                }
            }

		}
		
		return plotFound;

	}
	
}
