package com.cabdespatch.driverapp.beta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import org.joda.time.DateTime;

public class cabdespatchGPS implements LocationListener
{

    public static long FIX_NEVER_OBTAINED = -100;
    //private static Boolean HAS_FIX = false;
    private static DateTime dateTimeLastFix = new DateTime();
    public static Boolean locationFromMokSauce = false;

    public static Boolean hasFix()
    {
        DateTime now = new DateTime();
        return (dateTimeLastFix.plusSeconds(5).getMillis() > now.getMillis());
    }

    public static Boolean isLocationFromMockSource()
    {
        if(hasFix())
        {
            return locationFromMokSauce;
        }
        else
        {
            return false;
        }
    }

    private LocationManager _gpsLocationManager;
    private Location currentLocation;

    private Context context;
    private plot currentPlot;
    //private plot holdingPlotPlot;
    private long holdingPlotTime;

    private Double bestAccuracyThresh;
    private Double slackAccuracyMultiplier;

    private boolean inAutoPlot = false;

    public cabdespatchGPS(Context _c,  Boolean _active)
    {
        Globals.registerBugHandler(_c);

        dateTimeLastFix = new DateTime();

        bestAccuracyThresh = SETTINGSMANAGER.SETTINGS.GPS_BEST_ACCURACY_THRESH.parseDouble(_c);
        slackAccuracyMultiplier = SETTINGSMANAGER.SETTINGS.GPS_SLACK_ACCURACY_MULTIPLIER.parseDouble(_c);
        //Debug.waitForDebugger();

        DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,"LOC", "START");
        DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,"LOC", "ACTIVE " + (_active?"Y":"N"));

        _gpsLocationManager =  (LocationManager) _c.getSystemService(Context.LOCATION_SERVICE);

        this.context = _c;
        this.currentPlot = plot.errorPlot();

        String bestProvider = LocationManager.GPS_PROVIDER;
        //STATUSMANAGER.setCurrentLocation(context, getPdaLocation(this.currentPlot));

        /*currentLocation = _gpsLocationManager.getLastKnownLocation(bestProvider);
        if(currentLocation==null)
        {
        	currentLocation = new Location(bestProvider);
        }*/

//        if (!(currentLocation == null))
//        {
//            _Lat = currentLocation.getLatitude();
//            _Lon = currentLocation.getLongitude();
//        }
        if(_active)
        {
            doSubscriptionCheck(_c, true);
        }

    }


    private class SubscriptionChecker implements Runnable
    {

        private Context oContext;

        private SubscriptionChecker(Context _c)
        {
            oContext = _c;
        }

        @Override
        public void run()
        {
            cabdespatchGPS.this.doSubscriptionCheck(oContext, false);
        }
    }

    @SuppressLint("MissingPermission")
    private void doSubscriptionCheck(Context _c, Boolean _firstRun)
    {
        if(_firstRun)
        {
            //do nothing
        }
        else
        {
            _gpsLocationManager.removeUpdates(this);
        }

        float maxMetersMoved = 0f;
        if(Boolean.valueOf(SETTINGSMANAGER.SETTINGS.ENFORCE_MINIMUM_MOVEMENT_CRITERIA.getValue(_c)).equals(true))
        {
            maxMetersMoved = 0.2f;
        }

        Boolean useAlternativeProviders = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_ALTERNATIVE_LOCATION_PROVIDERS.getValue(_c));
        if(useAlternativeProviders)
        {

            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);
            if(j.getJobStatus()== cabdespatchJob.JOB_STATUS.POB)
            {
                if(SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(_c).equals(SETTINGSMANAGER.METER_TYPE.LOCAL_UNFAIR_METER))
                {
                    //do not subscribe to alternative location providers
                }
                else
                {
                    _gpsLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, maxMetersMoved, this);
                    _gpsLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 2000, maxMetersMoved, this);
                }
            }
            else
            {
                _gpsLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, maxMetersMoved, this);
                _gpsLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 2000, maxMetersMoved, this);
            }


        }
        else
        {

            //do nothing
        }

        //always request location updates from GPS provider
        _gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, maxMetersMoved, this);
    }

    public void checkSubscription(Context _c)
    {
        Handler h = new Handler(_c.getMainLooper());
        h.post(new SubscriptionChecker(_c));
    }

    public long getCurrentFixTime()
    {
        if(this.currentLocation == null)
        {
            return cabdespatchGPS.FIX_NEVER_OBTAINED;
        }
        else
        {
            return this.currentLocation.getTime();
        }
    }

    public long getTimeOutMillies()
    {
        return 20000;
    }

    public void forceFixedLocationChange()
    {
        this.reallyOnLocationChanged(null, true);
    }

    public boolean isEnabled()
    {
        //return true;
        return _gpsLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private Location pendingLocation;
    private Boolean locationChangeInProgress = false;



    @Override
    public void onLocationChanged(Location location)
    {

        //always set the speed on UnfairMeter
        if(!(location==null))
        {
            if(location.hasSpeed())
            {
                UnfairMeterLocal.setSpeed(Double.valueOf(location.getSpeed()));
            }
        }


        //always update pending location
        //but only ever use it for plot-compare
        //purposes in our jump-detection code
        pendingLocation = location;
        if(!(locationChangeInProgress))
        {
            locationChangeInProgress = true;
            reallyOnLocationChanged(location, false);
        }
    }

    private void reallyOnLocationChanged(Location _location, Boolean _force)
    {
        Integer fixedLocation = SETTINGSMANAGER.SETTINGS.LOCATION_REPORT_SOURCE.parseInteger(context);

        if (fixedLocation > 0)
        {

            locationFromMokSauce = false;
            pdaLocation p = getFixedLocation(fixedLocation);
            STATUSMANAGER.setCurrentLocation(context, p);


            BROADCASTERS.LocationUpdated(context);
            if((p.getPlot().equals(currentPlot)) && (!(_force)))
            {
                //do nothing
            }
            else
            {
                dateTimeLastFix = new DateTime();
                BROADCASTERS.PlotUpdated(context, false);

                //CLAY not really happy about this being here but
                //can't think of anywhere better to put it, unfortunately
                cabdespatchJob j = STATUSMANAGER.getCurrentJob(context);
                if(p.getPlot().getShortName().toUpperCase().equals(j.getFromPlot().toUpperCase()))
                {
                    j.setPickupPlotAsHit();
                    STATUSMANAGER.setCurrentJob(context, j);
                }
            }
            locationChangeInProgress = false;
        }
        else if(!(_location==null))
        {
            //Log.e("LOC", "UPDATE");
            Boolean locChanged = false;
            try
            {

                if(Build.VERSION.SDK_INT >= 18)
                {
                    if (!(Globals.isDebug(context)))
                    {
                        locationFromMokSauce = _location.isFromMockProvider();
                        if(locationFromMokSauce)
                        {
                            BROADCASTERS.Logout(context, "MOCK LOCATION", "I am using mock locations");
                        }
                    }

                }

                float currentAccuracy = 100;
                long currentTime = 1000;

                if(this.inAutoPlot) { return; } //wait until next update...

                if(!(currentLocation ==null))
                {
                    currentAccuracy = currentLocation.getAccuracy();
                    currentTime = currentLocation.getTime();
                }

                if (_location.getAccuracy() < (this.bestAccuracyThresh * this.slackAccuracyMultiplier))
                {
                    if ((_location.getAccuracy() >= currentAccuracy) || (_location.getAccuracy() < this.bestAccuracyThresh))
                    {
                        // if it's within the best accuracy then we'll take it regardless
                        // if it's better than the last fix (which we clearly accepted!) then we'll also take it
                        //currentLocation = _location;
                        locChanged = true;
                    }
                    else if (_location.getTime() >= currentTime + 10000)
                    {
                        //the last fix was 10 seconds ago, and this fix is still within our maximum distance criteria
                        //currentLocation = _location;
                        locChanged = true;
                    }
                    else
                    {
                        locChanged = false;
                    }
                }
                else
                {
                    locChanged = false; //just to reaffirm!
                }

                if(locChanged)
                {
                    dateTimeLastFix = new DateTime();
                }


                if(cabdespatchGPS.hasFix())
                {
                    //do nothing
                }
                else
                {
                    Intent noFix = new Intent(BROADCASTERS.GPS_NO_FIX);
                    //cabdespatchGPS.HAS_FIX = false;
                    context.sendBroadcast(noFix);
                }

                if(_force)
                {
                    locChanged = true;
                }

                if(locChanged)
                {
                    this.processChangeOfLocation(_location);
                }
                else
                {
                    locationChangeInProgress = false;
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                locationChangeInProgress = false;
            }
        }
    }


    private void processChangeOfLocation(final Location _location)
    {

        pdaLocation p = getPdaLocation(this.currentPlot, _location);
        if (p.getPlot().equals(this.currentPlot))
        {
            //this.holdingPlotTime = System.currentTimeMillis();
            this.currentLocation = _location;

            double bearing = (_location.hasBearing()?_location.getBearing():-1);

            p = pdaLocation.pdaLocationWithoutAutoplot(p.getLat(), p.getLon(), p.getAccuracy(), p.getSpeed(), 0, bearing, this.currentPlot);
            STATUSMANAGER.setCurrentLocation(context, p);
            BROADCASTERS.LocationUpdated(context);
            locationChangeInProgress = false;
        }
        else
        {
            class PendingLocationChecker extends Thread
            {
                private pdaLocation oHoldingLocation;
                private Location oLocation;

                public PendingLocationChecker(pdaLocation _holdingPdaLocation, Location _location)
                {
                    oHoldingLocation = _holdingPdaLocation;
                    oLocation = _location;
                }

                @Override
                public void run()
                {
                    super.run();
                    try
                    {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e)
                    {
                        //do nothing
                    }

                    synchronized (cabdespatchGPS.this.pendingLocation)
                    {
                        pdaLocation pendingPdaLocation = getPdaLocation(cabdespatchGPS.this.currentPlot, cabdespatchGPS.this.pendingLocation);
                        if(pendingPdaLocation.getPlot().equals(oHoldingLocation.getPlot()));
                        {
                            cabdespatchGPS.this.currentLocation = oLocation;

                            STATUSMANAGER.setCurrentLocation(context, this.oHoldingLocation);
                            BROADCASTERS.LocationUpdated(context);

                            cabdespatchGPS.this.currentPlot = oHoldingLocation.getPlot();
                            BROADCASTERS.PlotUpdated(context, false);

                        }
                    }

                    locationChangeInProgress = false;
                }
            }

            new PendingLocationChecker(p, _location).start();
        }

        /*
        if (updatePlot)
        {
            this.currentPlot = p.getPlot();
            BROADCASTERS.PlotUpdated(context, false);
        }
        else
        {
            p = pdaLocation.pdaLocationWithoutAutoplot(p.getLat(), p.getLon(), p.getAccuracy(), p.getSpeed(), 0, this.currentPlot);
        }*/


    }

    public void onProviderDisabled(String provider)
    {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider)
    {
        // TODO Auto-generated method stub

    }

    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO Auto-generated method stub
    }



    public void forceAutoPlot(Context _c)
    {
        if(!(this.inAutoPlot))
        {
            Integer thingyMode = SETTINGSMANAGER.SETTINGS.LOCATION_REPORT_SOURCE.parseInteger(_c);
            if(thingyMode > 0)
            {
                STATUSMANAGER.setCurrentLocation(_c, this.getPdaLocation(this.currentPlot, this.currentLocation));
            }
        }
    }

    private pdaLocation getPdaLocation(plot _currentPlot, Location _location)
    {
        //Log.w(String.valueOf(fixedLocation), "FIXEDLOCATION");

        //Log.e("LOC", "REQUESTED");

            if (_location ==null)
            {
                this.inAutoPlot = true;
                pdaLocation p = new pdaLocation(context, 0,0,9999,0,0, -1.0, _currentPlot);
                this.inAutoPlot = false;
                return p;
            }
            else
            {
                this.inAutoPlot = true;
                double bearing = _location.hasBearing()?_location.getBearing():-1;
                pdaLocation p = new pdaLocation(context, _location.getLatitude(), _location.getLongitude(), _location.getAccuracy(), _location.getSpeed(), _location.getTime(), bearing, _currentPlot);
                this.inAutoPlot = false;
                return p;
            }

    }

    private pdaLocation getFixedLocation(int _um)
    {
        Double lat = 0.0;
        Double lon = 0.0;

        switch (_um)
        {
            case 1:
                lat = SETTINGSMANAGER.SETTINGS.FIXED_LOC_1_LAT.parseDouble(context);
                lon = SETTINGSMANAGER.SETTINGS.FIXED_LOC_1_LON.parseDouble(context);
                break;
            case 2:
                lat = SETTINGSMANAGER.SETTINGS.FIXED_LOC_2_LAT.parseDouble(context);
                lon = SETTINGSMANAGER.SETTINGS.FIXED_LOC_2_LON.parseDouble(context);
                break;
        }
        pdaLocation p = new pdaLocation(context, lat,lon,0.1,0,0, 90.0, this.currentPlot);
        this.currentPlot = p.getPlot();
        return p;
    }

    public void cancel()
    {
        this._gpsLocationManager.removeUpdates(this);
    }

}
