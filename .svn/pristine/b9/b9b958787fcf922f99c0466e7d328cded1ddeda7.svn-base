package com.cabdespatch.driverapp.beta;

import java.util.ArrayList;
import java.util.List;

public class paramGroup 
{
	private List<param> grpData;

    public paramGroup()
    { 
        grpData = new ArrayList<param>();
    }

    public int Count()
    {
    
      return grpData.size();
     
    }

    public String getParamValue(String paramName, String defaultValue)
    {
        String value = defaultValue;

        for (param p : grpData)
        { 
            if (p.getName().equals(paramName)) 
            { 
                value = p.getValue();
                break; 
            } 
        }

        return value;
    }

    public String getParamValue(String paramName)
    {
        return getParamValue(paramName, "");
    }

    public Boolean updateValue(String paramName, String newValue)
    {
        Boolean success = false;
        for (param p : grpData)
        {
            if (p.getName().equals(paramName))
            {
                p.setValue(newValue);
                success = true;
                break;
            }
        }

        return success;
    }

    public Boolean addParam(String name, String value)
    {
        Boolean success = true;

        for (param p : grpData)
        {
            if (p.getName().equals(name))
            {
                success = false;
            }
        }

        //only add if the item doesn't already exist
        if (success) { grpData.add(new param(name, value)); };
        
        return success;
    }

    public Boolean removeParam(String name)
    {
        Boolean success = false;
        int index;
        for (index = 0; index <= (grpData.size() - 1);index++)
        {
            if (grpData.get(index).getName().equals(name))
            {               
                grpData.remove(index);
                success = true;
                break;
            }
        }

        return success;
    }

    public List<param> getParamList()
    {
        return grpData;
    }
    
    @Override
    public String toString()
    {
    	String out = "";
    	for(param p:this.grpData)
    	{
    		out += (p.getName() + " - " + p.getValue());
    	}
    	
    	return out;
    }
}
