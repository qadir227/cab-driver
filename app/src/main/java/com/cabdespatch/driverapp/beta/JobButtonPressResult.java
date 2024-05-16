package com.cabdespatch.driverapp.beta;

/**
 * Created by Pleng on 01/06/2016.
 */
public final class JobButtonPressResult
{
    public static final int ACTION_NONE = 0;
    public static final int ACTION_REQUEST_PRICE = 1;
    public static final int ACTION_POD = 2; //no need to handle this at the moemnt; it's only used in the GLOBALS function
    public static final int ACTION_REQUEST_DRIVER_NOTES = 3;

    private Integer oAction;

    private JobButtonPressResult(Integer _action)
    {
        this.oAction = _action;
    }

    public Integer getAction()
    {
        return this.oAction;
    }

    public static JobButtonPressResult NoActionRequired()
    {
        return new JobButtonPressResult(ACTION_NONE);
    }

    public static JobButtonPressResult RequestPrice()
    {
        return new JobButtonPressResult(ACTION_REQUEST_PRICE);
    }

    public static JobButtonPressResult RequestPOD()
    {
        return  new JobButtonPressResult(ACTION_POD);
    }

    public static JobButtonPressResult RequestDriverNotes()
    {
        return new JobButtonPressResult(ACTION_REQUEST_DRIVER_NOTES);
    }
}
