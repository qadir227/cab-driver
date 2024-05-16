package com.cabdespatch.driverapp.beta;

/**
 * Created by Pleng on 06/10/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Pleng on 06/10/2016.
 */

public class Settable
{
    public static final String NOT_SET = "NOT_SET";

    private String oKey;
    private String oDefaultValue;

    public Boolean equals(Context _c, String _value)
    {
        String val = getValue(_c);
        return val.equals(_value);
    }

    public Boolean notEquals(Context _c, String _value)
    {
        return (!(this.equals(_c, _value)));
    }

    public Boolean isSet(Context c)
    {
        return (!(equals(c, Settable.NOT_SET)));
    }

    public Settable(String _key, String _defaultValue)
    {
        this.oKey = _key;
        this.oDefaultValue = _defaultValue;
    }

    public Settable(String _key, Object _defaultValue)
    {
        this(_key, String.valueOf(_defaultValue));
    }

    public String getKey() { return this.oKey; }
    public String getDefaultValue() { return this.oDefaultValue; }
    public String getValue(Context _c)
    {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        return p.getString(this.getKey(), this.getDefaultValue());
    }

    public Boolean putValue(Context _c, String _value)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putString(this.getKey(), _value);
        return e.commit();
    }

    public Boolean putValue(Context _c, Double _value)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putString(this.getKey(), String.valueOf(_value));
        return e.commit();
    }

    public Boolean putValue(Context _c, Long _value)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putString(this.getKey(), String.valueOf(_value));
        return e.commit();
    }

    public Boolean parseBoolean(Context _c)
    {
        return Boolean.valueOf(this.getValue(_c));
    }
    public Double parseDouble(Context _c) { return Double.valueOf(this.getValue(_c)); }
    public Integer parseInteger(Context _c) { return Integer.valueOf(this.getValue(_c)); }
    public long parseLong(Context _c) { return Long.valueOf(this.getValue(_c)); }

    public Boolean putValue(Context _c, Boolean _value)
    {
        return this.putValue(_c, String.valueOf(_value));
    }
    public Boolean putValue(Context _c, Integer _value)
    {
        return this.putValue(_c, String.valueOf(_value));
    }

    public Boolean reset(Context _c)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putString(this.getKey(), this.getDefaultValue());
        return e.commit();
    }
}