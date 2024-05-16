package com.cabdespatch.driverapp.beta;

public class param 
{

	private String pName;
    private String pValue;

    public param(String name, String value)
    {
        pName = name;
        pValue = value;
    }

    public String getName()
    {
        return pName;
    }

    public String getValue()
    {
        return pValue;
    }

    public void setValue(String value)
    {
        pValue = value;
    }
}
