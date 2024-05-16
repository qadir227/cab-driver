package com.cabdespatch.driverapp.beta;

/**
 * Created by Pleng on 14/12/2014.
 */
public class MileageTool
{
    protected Double getCrudeMileage(Double _fromLat, Double _fromLon, Double _toLat, Double _toLon)
    {
        Double x = _toLat - _fromLat;
        x = x * 69.1;

        Double y = _toLon - _fromLon;
        y = y * 53.0;

        x = x * x;
        y = y * y;

        x = x + y;

        return Math.sqrt(x);
    }
}
