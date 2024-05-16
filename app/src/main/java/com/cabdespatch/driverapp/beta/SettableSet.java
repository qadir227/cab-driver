package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pleng on 06/10/2016.
 */

public class SettableSet
{
    private String oKey;

    public SettableSet(String _key)
    {
        this.oKey = _key;
    }

    public String getKey() { return this.oKey; }
    public Set<String> getDefaultValue() { return new HashSet<String>(); }
    public Set <String> getEntries(Context _c)
    {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        Set<String> entries = p.getStringSet(this.getKey(), this.getDefaultValue());

        Set<String> modifiableEntries = new HashSet<>();
        for(String s:entries)
        {
            modifiableEntries.add(s);
        }

        return modifiableEntries;
    }

    public Boolean addValue(Context _c, String _value)
    {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);
        Set<String> values =  getEntries(_c);
        values.add(_value);

        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putStringSet(this.getKey(), values);

        return e.commit();
    }

    //case insensitive
    public Boolean removeValue(Context _c, String _value)
    {
        HashSet<String> _newEntries = new HashSet<String>();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(_c);

        Set<String> values =  p.getStringSet(this.getKey(), this.getDefaultValue());
        for(String s:values)
        {
            if(!(s.toUpperCase().equals(_value.toUpperCase())))
            {
                _newEntries.add(s);
            }
        }

        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putStringSet(this.getKey(), _newEntries);

        return e.commit();
    }

    //case insensitive
    public Integer removeSubStringMatches(Context _c, String _value)
    {
        HashSet<String> _newEntries = new HashSet<String>();
        Integer removedMessages = 0;

        Set<String> values =  getEntries(_c);
        for(String s:values)
        {
            if(!(s.toUpperCase().contains(_value.toUpperCase())))
            {
                _newEntries.add(s);
            }
            else
            {
                removedMessages += 1;
            }
        }

        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putStringSet(this.getKey(), _newEntries);

        return (e.commit()?removedMessages:-1);
    }


    public Boolean reset(Context _c)
    {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(_c).edit();
        e.putStringSet(this.getKey(), this.getDefaultValue());
        return e.commit();
    }
}