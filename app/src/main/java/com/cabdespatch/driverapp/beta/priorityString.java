package com.cabdespatch.driverapp.beta;

public class priorityString implements Comparable<priorityString>
{
	private String data;
	private int priority;
	private boolean flag;
	private int id;
	static Integer topID;
	
	public int compareTo(priorityString o)
	{
		if (this.id < o.id)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
	
	priorityString()
	{
		if (topID==null)
		{
			topID=1;
		}
		else
		{
			id = ++topID;
		}
		
		data="";
		priority = 0;
		flag = false;
	}
	
	priorityString(String _text)
	{
		if (topID==null)
		{
			topID=1;
		}
		else
		{
			id = ++topID;
		}
		
		data=_text;
		priority = 0;
		flag=false;
	}
	
	public priorityString(String _text, int _priority)
	{
		if (topID==null)
		{
			topID=1;
		}
		else
		{
			id = ++topID;
		}
		
		data=_text;
		priority = _priority;
		flag=false;
	}
	

	
	priorityString(String _text, int _priority, boolean _isFlagged)
	{
		if (topID==null)
		{
			topID=1;
		}
		else
		{
			id = ++topID;
		}
		
		data = _text;
		priority = _priority;
		flag = _isFlagged;
	}
	
	public void setString(String _text)
	{
		data = _text;
	}
	
	public String getString()
	{
		return data;
	}
	
	public void setPriority(int _priority)
	{
		priority = _priority;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public void flag()
	{
		flag = true;
	}
	public void unFlag()
	{
		flag = false;
	}
	public boolean isFlagged()
	{
		return flag;
	}
	public int getID()
	{
		return id;
	}

}
