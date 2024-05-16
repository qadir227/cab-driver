package com.cabdespatch.driverapp.beta;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Pleng on 29/04/2016.
 */
public class Meter_UNUSED extends Service
{

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public static enum STATE
    {
        NOT_YET_INITIALIZED,HIRED,FOR_HIRE,STOPPED;
    }

    private STATE MeterState = STATE.NOT_YET_INITIALIZED;
    private Double DistanceTravelled;
    private Long TimeTravelling;
    private Long TimeStationary;

    private Long oStateChangeTime;

    //pass in distance in meters
    public void addDistance(Double _distance, Double _speed)
    {
        setStateBasedOnSpeed(_speed);
        if(MeterState == STATE.HIRED)
        {
            DistanceTravelled += _distance;
        }
    }

    public void addDistance(pdaLocation _oldLocation, pdaLocation _newLocation)
    {
         if(MeterState == STATE.HIRED)
         {
             addDistance(getCrudeDistance(_oldLocation.getLat(), _oldLocation.getLon(),
                     _newLocation.getLat(), _newLocation.getLon(),0,0), _newLocation.getSpeed());
         }
    }

    private void setStateBasedOnSpeed(Double _speed)
    {
        //CLAY

        //check speed against
    }


    /*
 * Calculate distance between two points in latitude and longitude taking
 * into account height difference. If you are not interested in height
 * difference pass 0.0. Uses Haversine method as its base.
 *
 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
 * el2 End altitude in meters
 * @returns Distance in Meters
 */
    private static double getCrudeDistance(double lat1, double lon1, double lat2,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }


    public Boolean setStateHired()
    {
        if(MeterState == STATE.STOPPED)
        {
            TimeStationary += (System.currentTimeMillis() - oStateChangeTime);
            oStateChangeTime = System.currentTimeMillis();
            MeterState = STATE.HIRED;
            return true;
        }
        else if(MeterState == STATE.FOR_HIRE)
        {
            TimeStationary = 0l;
            oStateChangeTime = System.currentTimeMillis();
            MeterState = STATE.HIRED;
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean setStateStopped()
    {
        if(MeterState == STATE.HIRED)
        {
            TimeTravelling += (System.currentTimeMillis() - oStateChangeTime);
            oStateChangeTime = System.currentTimeMillis();
            MeterState = STATE.STOPPED;
            return true;
        }
        else if(MeterState == STATE.FOR_HIRE)
        {
            TimeTravelling = 0l;
            oStateChangeTime = System.currentTimeMillis();
            MeterState = STATE.STOPPED;
            return true;
        }
        else
        {
            return false;
        }
    }

}
