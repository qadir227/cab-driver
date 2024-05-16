package com.cabdespatch.driverapp.beta;

import android.content.Context;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UnfairMeterLocal
{
    private static volatile Double oPull = 0d;
    private static volatile Double oJourneyPoundsIncluded = 0d;
    private static volatile Double oRunningMile = 0d;
    private static volatile Double oCutOffSpeed;
    private static volatile Double oStationaryFee = 0d; //pence per second
    private static volatile Double oMeterStepIntervals = 0.20;

    //private static volatile Boolean oRetreiveFromCache;

    private static volatile Double oTotalMiles;
    private static volatile Double oTotalTime;
    private static volatile DateTime oPreviousDateTime;
    private static volatile List<String> oLog;
    private static volatile DateTime oLastSpeedUpdate;

    //private static volatile int oTick;

    private static volatile Double oSpeed;

    private static volatile boolean readyToGo = false;


    public static void config(String _data)
    {
        if(_data.equals(""))
        {
            readyToGo = false;
        }
        else
        {
            String[] parts = _data.split("#");
            oPull = Double.valueOf(parts[0]) / 100;
            oJourneyPoundsIncluded = Double.valueOf(parts[1]) / 100;
            oRunningMile = Double.valueOf(parts[2]) / 100;
            oStationaryFee = ((Double.valueOf(parts[3])) / 60) /100;  //pounds per second
            readyToGo = true;
        }


    }

    /*
    public static void setCacheRetreivalStatus(Boolean _shouldRetrieve)
    {
        oRetreiveFromCache = _shouldRetrieve;
    }*/

    public static void setSpeed(Double _speed)
    {
        if(oSpeed==null)
        {
            //do nothing
        }
        else
        {
            synchronized (oLastSpeedUpdate)
            {
                oLastSpeedUpdate = new DateTime();
            }
            synchronized (oSpeed)
            {
                oSpeed = _speed;
            }
        }

    }


    public static Double Start(Context _c)
    {
        oPreviousDateTime = new DateTime();
        oTotalTime = 0d;
        oTotalMiles = 0d;
        oTotalMiles = 0d; oTotalTime = 0d;

        if(oSpeed==null)
        {
            oSpeed = 0d;
        }

        oLog = new ArrayList<>();

        //5miles per hour into xmeters per second
        oCutOffSpeed = 5 * 0.44704;

        if(oLastSpeedUpdate == null)
        {
            oLastSpeedUpdate = new DateTime().minusSeconds(6);
        }


        //STATUSMANAGER.STATUSES.TICKING_METER_VALUE.reset(_c);

        return oPull;
    }

    private static void addToLog(String _data)
    {
        String realData = new DateTime().toString("HH:mm.ss") + " - " + _data;
        oLog.add(realData);
    }


    public static Boolean isLive()
    {
        return (!(oPreviousDateTime == null));
    }

    public static boolean isReadyToGo()
    {
        return  readyToGo;
    }

    public static void Stop(Context _c)
    {
        oPreviousDateTime = null;
        NOTIFIERS.reset(_c);
    }

    public static Double getMinFare()
    {
        return oPull;
    }

    public static String getSummary()
    {
        try
        {
            return "Miles: " + oTotalMiles + " Time: " + String.format("%.2f", (oTotalTime / 60)) + "\nSpeed: " + String.valueOf(oSpeed) + "m/s";
        }
        catch (NullPointerException ex)
        {
            return "Meter is Null";
        }

    }

    public static String getLog()
    {
        StringBuilder b = new StringBuilder();
        for(String s:oLog)
        {
            b.append(s + "\n");
        }
        return b.toString();
    }

    public static void update(Context _c) {
        Double fare;

        if (oPreviousDateTime == null) {
            //remember to call Start() first;
            fare = -1d;
        } else {
            DateTime newDateTime = new DateTime();

            long milliesEllapsed = newDateTime.getMillis() - oPreviousDateTime.getMillis();
            addToLog("    Millis elapsed: " + String.valueOf(milliesEllapsed));
            addToLog("    Speed: " + String.valueOf(oSpeed));

            oPreviousDateTime = new DateTime(newDateTime.getMillis());

            Double secondsEllapsed = (milliesEllapsed) / 1000d;

            long milliesellapsedSinceSpeedUpdate = newDateTime.getMillis() - oLastSpeedUpdate.getMillis();
            if ((milliesellapsedSinceSpeedUpdate / 1000d) > 6) {
                //it has been more than 6 seconds since the last
                //speed update; maybe we're lost in a tunnel??
                synchronized (oSpeed) {
                    oSpeed = 1d; //set speed to be lower than than the cut-off so we'll revert to
                    //charging for time...
                }
            }

            synchronized (oSpeed) {
                if (oSpeed > oCutOffSpeed) {

                    Double metersTravelled = oSpeed * secondsEllapsed; //distance in meters
                    Double milesTravelled = (metersTravelled * 0.000621371);

                    oTotalMiles += milesTravelled;

                } else {

                    oTotalTime += secondsEllapsed;
                }
            }


            Double calculatableMiles = oTotalMiles;
            if (calculatableMiles < 0) {
                calculatableMiles = 0d;
            }
            Double distanceFare = (calculatableMiles * oRunningMile);

            Double timefare = (oTotalTime * oStationaryFee);

            fare = oPull + distanceFare + timefare - oJourneyPoundsIncluded;
            if (fare < oPull) {
                fare = oPull;
            }

            // We need to round the fare to the meter step intervals
            // We always round down so to do this we work out the fare in pence and then modulo it to the step interval in pence
            // this gives us the remainder after the step interval which we just subtract from the fare in pence
            // and then divide the whole lot by 100 to get the fare back in pounds again!
            fare = (((fare * 100) - ((fare * 100) % (oMeterStepIntervals * 100))) / 100);

        }

        STATUSMANAGER.STATUSES.TICKING_METER_VALUE.putValue(_c, fare);

        String currentFareText = _c.getString(R.string.fair_meter_current_fare);
        currentFareText += (" £" + HandyTools.Strings.formatPrice(fare));
        NOTIFIERS.updateText(_c, currentFareText);
    }

    public static Double getFare(Context _c)
    {

        Double fare = STATUSMANAGER.STATUSES.TICKING_METER_VALUE.parseDouble(_c);
        if(fare < 0)
        {
            return fare;
        }
        else
        {
            DecimalFormat df = new DecimalFormat("#####0.00");
            String fareFormat = df.format(fare);
            if(!(fareFormat.endsWith("0")))
            {
                fareFormat = fareFormat.substring(0, fareFormat.length() - 1);
                fareFormat += "0";
            }

            return Double.valueOf(fareFormat);
        }

    }

/*
    public static Double update(Double _lat, Double _lon)
    {
        Double fare = 0d;

        if (oPreviousDateTime==null)
        {
            //remember to call Start() first;
            fare = -1d;
        }
        else
        {
            fare = oPull;

            DateTime newDateTime = new DateTime();
            int secondsElapsed = Seconds.secondsBetween(oPreviousDateTime, newDateTime).getSeconds();
            Double distanceTravelled = distance(oPreviousLat, oPreviousLon, _lat, _lon,'N');

            Double speed = 0d;
            if(distanceTravelled > 0)
            {
                speed = distanceTravelled / (secondsElapsed / 60);
            }

            if(speed > 90) //travelling at more than 90kph??? maybe a bad co-ord?
            {
                fare = -1d;
            }
            else
            {
                if(speed < oCutOffSpeed)
                {
                    oTotalTime += secondsElapsed;
                }
                else
                {
                    oTotalMiles += distanceTravelled;
                }

                Double calculatableMiles = oTotalMiles - oInclusiveMiles;
                if (calculatableMiles < 0) { calculatableMiles =  0d; }
                fare += (calculatableMiles * oRunningMile);
                fare += (oTotalTime * oStationaryFee);

                oPreviousDateTime = newDateTime;
                oPreviousLat = _lat;
                oPreviousLon = _lon;
            }


        }

        if(fare < 0)
        {
            return fare;
        }
        else
        {
            DecimalFormat df = new DecimalFormat("#####0.00");
            String fareFormat = df.format(fare);
            if(!(fareFormat.endsWith("0")))
            {
                fareFormat = fareFormat.substring(0, fareFormat.length() - 1);
                fareFormat += "0";
            }

            return Double.valueOf(fareFormat);
        }

    }
*/

    private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}

